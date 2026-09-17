//! Pro 独有接口补齐（audit #13 契约对齐）：
//!
//! - `GET/POST /reader3/getAllContents`：整本缓存只读组装器（零网络，缺章占位）
//! - `GET/POST /reader3/searchChapter`：章节标题搜索（contains，{list,lastIndex} 分页）
//! - `GET/POST /reader3/exportToTxt`：服务端 TXT 导出下载（charset 参数支持 GBK 等）
//! - `GET/POST /reader3/exportToEpub`：服务端 EPUB 导出下载
//!
//! 数据源均为 book_chapters 表（目录行 content 为空串——getAllContents 按契约写
//! 「暂无缓存内容。」占位；导出时跳过空章）。

use std::collections::HashMap;

use axum::extract::{Query, State};
use axum::http::HeaderMap;
use axum::http::{header, StatusCode};
use axum::response::{IntoResponse, Response};
use axum::Json;
use serde_json::json;

use crate::api::router::{param_of, resolve_namespace, AppState, ReturnData};

/// GET/POST /reader3/getAllContents：纯缓存只读组装器，零网络。
/// 响应 data：{name, author, total, cachedCount, chapters: [{chapterIndex,title,content}]}
pub async fn get_all_contents(
    State(state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    body: Option<axum::body::Bytes>,
) -> Json<ReturnData> {
    let body_json = body.and_then(|b| serde_json::from_slice::<serde_json::Value>(&b).ok());
    let namespace = match resolve_namespace(&state, &params, &headers).await {
        Ok(ns) => ns,
        Err(ret) => return Json(ret),
    };
    let book_url = param_of(&params, body_json.as_ref(), "bookUrl");
    if book_url.is_empty() {
        return Json(ReturnData::err("参数错误"));
    }
    let book = state
        .storage
        .find_book(&namespace, &book_url)
        .await
        .ok()
        .flatten();
    match state
        .storage
        .list_cached_chapters(&namespace, &book_url)
        .await
    {
        Ok(rows) => {
            const PLACEHOLDER: &str = "暂无缓存内容。";
            let chapters: Vec<serde_json::Value> = rows
                .iter()
                .map(|(idx, title, content)| {
                    json!({
                        "chapterIndex": idx,
                        "title": title,
                        // Pro 语义：未缓存章写占位符
                        "content": if content.trim().is_empty() { PLACEHOLDER } else { content },
                    })
                })
                .collect();
            let cached = rows.iter().filter(|(_, _, c)| !c.trim().is_empty()).count();
            Json(ReturnData::ok(json!({
                "name": book.as_ref().map(|b| b.name.clone()).unwrap_or_default(),
                "author": book.as_ref().map(|b| b.author.clone()).unwrap_or_default(),
                "total": chapters.len(),
                "cachedCount": cached,
                "chapters": chapters,
            })))
        }
        Err(e) => {
            tracing::error!("getAllContents [{namespace}/{book_url}] 失败: {e}");
            Json(ReturnData::err("系统错误"))
        }
    }
}

/// GET/POST /reader3/searchChapter：章节标题 contains 匹配（大小写敏感，对齐 Pro
/// indexOf 语义）。分页：fromIndex 起始扫描位置、pageSize 单页条数（默认 100 上限 500）。
/// 响应 data：{list:[{chapterIndex,title}], lastIndex}——还有更多时 lastIndex=下一扫描
/// 起点（最后命中 index+1），扫尽为 -1。
pub async fn search_chapter(
    State(state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    body: Option<axum::body::Bytes>,
) -> Json<ReturnData> {
    let body_json = body.and_then(|b| serde_json::from_slice::<serde_json::Value>(&b).ok());
    let namespace = match resolve_namespace(&state, &params, &headers).await {
        Ok(ns) => ns,
        Err(ret) => return Json(ret),
    };
    let key = param_of(&params, body_json.as_ref(), "key");
    if key.is_empty() {
        return Json(ReturnData::err("请输入搜索关键字"));
    }
    let book_url = param_of(&params, body_json.as_ref(), "bookUrl");
    if book_url.is_empty() {
        return Json(ReturnData::err("参数错误"));
    }
    let from_index: i64 = param_of(&params, body_json.as_ref(), "fromIndex")
        .parse()
        .unwrap_or(0);
    let page_size: usize = param_of(&params, body_json.as_ref(), "pageSize")
        .parse::<usize>()
        .unwrap_or(100)
        .clamp(1, 500);

    match state
        .storage
        .list_cached_chapters(&namespace, &book_url)
        .await
    {
        Ok(rows) => {
            let hits: Vec<(i64, String)> = rows
                .iter()
                .filter(|(idx, title, _)| *idx >= from_index && title.contains(key.as_str()))
                .map(|(idx, title, _)| (*idx, title.clone()))
                .collect();
            let has_more = hits.len() > page_size;
            let page: Vec<serde_json::Value> = hits
                .iter()
                .take(page_size)
                .map(|(idx, title)| json!({"chapterIndex": idx, "title": title}))
                .collect();
            let last_index = if has_more {
                page.last()
                    .and_then(|v| v.get("chapterIndex").and_then(|x| x.as_i64()))
                    .map(|i| i + 1)
                    .unwrap_or(-1)
            } else {
                -1
            };
            Json(ReturnData::ok(
                json!({ "list": page, "lastIndex": last_index }),
            ))
        }
        Err(e) => {
            tracing::error!("searchChapter [{namespace}/{book_url}] 失败: {e}");
            Json(ReturnData::err("系统错误"))
        }
    }
}

/// 共享：取书 + 已缓存正文 → 导出章节列表（空 content 跳过——目录行不导出）
async fn export_chapters(
    state: &AppState,
    ns: &str,
    book_url: &str,
) -> anyhow::Result<(
    Option<crate::model::book::Book>,
    Vec<crate::service::export_book::ExportChapter>,
)> {
    let book = state.storage.find_book(ns, book_url).await.ok().flatten();
    let rows = state.storage.list_cached_chapters(ns, book_url).await?;
    let chapters = rows
        .into_iter()
        .filter(|(_, _, c)| !c.trim().is_empty())
        .map(|(_, title, content)| crate::service::export_book::ExportChapter { title, content })
        .collect();
    Ok((book, chapters))
}

fn export_common_params(
    params: &HashMap<String, String>,
    body_json: Option<&serde_json::Value>,
) -> (String, String) {
    // bookUrl 为主（Pro 契约）；url 为别名（与 exportBook/getBookInfo 系参数宽容一致）
    let book_url = {
        let primary = param_of(params, body_json, "bookUrl");
        if primary.is_empty() {
            param_of(params, body_json, "url")
        } else {
            primary
        }
    };
    (book_url, param_of(params, body_json, "charset"))
}

/// GET/POST /reader3/exportToTxt：服务端 TXT 导出下载。charset 参数默认 UTF-8
/// （Pro appConfig.exportCharset 对齐；GBK 等经 encode_txt 转码，不可映射字符报错）。
pub async fn export_to_txt(
    State(state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    body: Option<axum::body::Bytes>,
) -> Response {
    let body_json = body.and_then(|b| serde_json::from_slice::<serde_json::Value>(&b).ok());
    let namespace = match resolve_namespace(&state, &params, &headers).await {
        Ok(ns) => ns,
        Err(ret) => return Json(ret).into_response(),
    };
    let (book_url, charset) = export_common_params(&params, body_json.as_ref());
    if book_url.is_empty() {
        return Json(ReturnData::err("参数错误")).into_response();
    }
    let (book, chapters) = match export_chapters(&state, &namespace, &book_url).await {
        Ok(v) => v,
        Err(e) => {
            tracing::error!("exportToTxt [{namespace}/{book_url}] 失败: {e}");
            return Json(ReturnData::err("系统错误")).into_response();
        }
    };
    let name = book
        .as_ref()
        .map(|b| b.name.clone())
        .unwrap_or_else(|| book_url.clone());
    let txt = crate::service::export_book::build_txt(&name, &chapters);
    let encoding: &str = if charset.is_empty() {
        "utf-8"
    } else {
        &charset
    };
    let (bytes, _) = match crate::service::export_book::encode_txt(&txt, encoding) {
        Ok(v) => v,
        Err(e) => return Json(ReturnData::err(format!("编码不支持: {e}"))).into_response(),
    };
    file_download(bytes, &format!("{name}.txt"), "text/plain; charset=utf-8")
}

/// GET/POST /reader3/exportToEpub：服务端 EPUB 导出下载。元数据 publisher="Legado" /
/// language="zh" 对齐 Pro epublib 产物约定；简介/分类透传书籍信息。
pub async fn export_to_epub(
    State(state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    body: Option<axum::body::Bytes>,
) -> Response {
    let body_json = body.and_then(|b| serde_json::from_slice::<serde_json::Value>(&b).ok());
    let namespace = match resolve_namespace(&state, &params, &headers).await {
        Ok(ns) => ns,
        Err(ret) => return Json(ret).into_response(),
    };
    let (book_url, _charset) = export_common_params(&params, body_json.as_ref());
    if book_url.is_empty() {
        return Json(ReturnData::err("参数错误")).into_response();
    }
    let (book, chapters) = match export_chapters(&state, &namespace, &book_url).await {
        Ok(v) => v,
        Err(e) => {
            tracing::error!("exportToEpub [{namespace}/{book_url}] 失败: {e}");
            return Json(ReturnData::err("系统错误")).into_response();
        }
    };
    let name = book
        .as_ref()
        .map(|b| b.name.clone())
        .unwrap_or_else(|| book_url.clone());
    let author = book.as_ref().map(|b| b.author.clone()).unwrap_or_default();
    let meta = crate::service::export_book::EpubMeta {
        description: book.as_ref().and_then(|b| b.intro.clone()),
        language: Some("zh".into()),
        published_at: None,
        publisher: Some("Legado".into()),
        subject: book.as_ref().and_then(|b| b.kind.clone()),
        ..Default::default()
    };
    let bytes = crate::service::export_book::build_epub_full(&name, &author, &meta, &chapters);
    file_download(bytes, &format!("{name}.epub"), "application/epub+zip")
}

fn file_download(bytes: Vec<u8>, filename: &str, content_type: &str) -> Response {
    // RFC 6266/5987：非 ASCII 文件名走 filename*（URL 编码），ASCII 回退名兜底
    let encoded: String = urlencoding::encode(filename).into_owned();
    let ascii_fallback: String = if filename.is_ascii() {
        filename.replace('"', "'")
    } else {
        "download".to_string()
    };
    Response::builder()
        .status(StatusCode::OK)
        .header(header::CONTENT_TYPE, content_type)
        .header(
            header::CONTENT_DISPOSITION,
            format!("attachment; filename=\"{ascii_fallback}\"; filename*=UTF-8''{encoded}"),
        )
        .body(axum::body::Body::from(bytes))
        .unwrap()
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::collections::HashMap;

    async fn test_state(tag: &str) -> (AppState, std::path::PathBuf) {
        let dir = std::env::temp_dir().join(format!(
            "reader-proexport-test-{}-{tag}",
            std::process::id()
        ));
        let _ = std::fs::remove_dir_all(&dir);
        let mut config = crate::AppConfig::from_env();
        config.work_dir = dir.to_string_lossy().into_owned();
        config.token_ttl_days = 0;
        let storage = crate::storage::init(&config).await.unwrap();
        let image_cache = crate::service::image_cache::ImageCache::with_capacity(
            dir.join("storage").join("cache").join("images"),
            1024 * 1024,
        );
        (
            AppState {
                storage,
                image_cache,
            },
            dir,
        )
    }

    fn q(pairs: &[(&str, &str)]) -> Query<HashMap<String, String>> {
        Query(
            pairs
                .iter()
                .map(|(k, v)| (k.to_string(), v.to_string()))
                .collect(),
        )
    }

    fn empty_headers() -> HeaderMap {
        HeaderMap::new()
    }

    /// 种子：书架书 + 目录（2 章空正文行）+ 缓存第 1 章正文
    async fn seed(state: &AppState) {
        state
            .storage
            .upsert_book(
                "default",
                &crate::model::Book {
                    book_url: "https://src/book1".into(),
                    name: "测试书名".into(),
                    author: "作者甲".into(),
                    origin: "https://src".into(),
                    intro: Some("简介文本".into()),
                    kind: Some("玄幻".into()),
                    ..Default::default()
                },
            )
            .await
            .unwrap();
        // 目录两章（content 空）
        state
            .storage
            .cache_chapter_content("default", "https://src/book1", 0, "第一章", "")
            .await
            .unwrap();
        state
            .storage
            .cache_chapter_content("default", "https://src/book1", 1, "第二章", "")
            .await
            .unwrap();
        // 缓存第 1 章正文
        state
            .storage
            .cache_chapter_content(
                "default",
                "https://src/book1",
                0,
                "第一章",
                "第一章的完整正文。",
            )
            .await
            .unwrap();
    }

    #[tokio::test]
    async fn test_get_all_contents_placeholder() {
        let (state, _dir) = test_state("allcontents").await;
        seed(&state).await;
        let ret = get_all_contents(
            State(state.clone()),
            q(&[("bookUrl", "https://src/book1")]),
            empty_headers(),
            None,
        )
        .await;
        assert!(ret.is_success, "{:?}", ret.error_msg);
        let data = ret.0.data.clone();
        let chapters = data.get("chapters").and_then(|v| v.as_array()).unwrap();
        assert_eq!(chapters.len(), 2);
        // 第 0 章已缓存 → 正文；第 1 章未缓存 → 占位符
        assert_eq!(
            chapters[0].get("content").and_then(|v| v.as_str()),
            Some("第一章的完整正文。")
        );
        assert_eq!(
            chapters[1].get("content").and_then(|v| v.as_str()),
            Some("暂无缓存内容。")
        );
        assert_eq!(data.get("name").and_then(|v| v.as_str()), Some("测试书名"));
        assert_eq!(data.get("cachedCount").and_then(|v| v.as_i64()), Some(1));
    }

    #[tokio::test]
    async fn test_search_chapter_pagination() {
        let (state, _dir) = test_state("searchchapter").await;
        for i in 0..5 {
            state
                .storage
                .cache_chapter_content("default", "u:b", i, &format!("番外{i}篇"), "x")
                .await
                .unwrap();
        }
        state
            .storage
            .cache_chapter_content("default", "u:b", 10, "无关章节", "y")
            .await
            .unwrap();

        // 首页 pageSize=3 → 3 条 + lastIndex=下一起点
        let ret = search_chapter(
            State(state.clone()),
            q(&[("bookUrl", "u:b"), ("key", "番外"), ("pageSize", "3")]),
            empty_headers(),
            None,
        )
        .await;
        let data = ret.0.data.clone();
        let list = data.get("list").and_then(|v| v.as_array()).unwrap();
        assert_eq!(list.len(), 3);
        let last = data.get("lastIndex").and_then(|v| v.as_i64()).unwrap();
        assert!(last > 0);

        // 从 lastIndex 续拉 → 剩余 2 条 + lastIndex=-1（扫尽）
        let ret2 = search_chapter(
            State(state.clone()),
            q(&[
                ("bookUrl", "u:b"),
                ("key", "番外"),
                ("pageSize", "3"),
                ("fromIndex", &last.to_string()),
            ]),
            empty_headers(),
            None,
        )
        .await;
        let data2 = ret2.0.data.clone();
        let list2 = data2.get("list").and_then(|v| v.as_array()).unwrap();
        assert_eq!(list2.len(), 2);
        assert_eq!(data2.get("lastIndex").and_then(|v| v.as_i64()), Some(-1));

        // 无命中
        let ret3 = search_chapter(
            State(state),
            q(&[("bookUrl", "u:b"), ("key", "不存在")]),
            empty_headers(),
            None,
        )
        .await;
        let data3 = ret3.0.data.clone();
        assert_eq!(
            data3.get("list").and_then(|v| v.as_array()).unwrap().len(),
            0
        );
    }

    #[tokio::test]
    async fn test_export_txt_utf8_and_gbk() {
        let (state, _dir) = test_state("exptxt").await;
        seed(&state).await;
        // 默认 UTF-8：响应体含中文标题与正文
        let resp = export_to_txt(
            State(state.clone()),
            q(&[("bookUrl", "https://src/book1")]),
            empty_headers(),
            None,
        )
        .await;
        assert_eq!(resp.status(), StatusCode::OK);
        let cd = resp
            .headers()
            .get(header::CONTENT_DISPOSITION)
            .unwrap()
            .to_str()
            .unwrap();
        assert!(cd.contains(".txt"));
        let bytes = axum::body::to_bytes(resp.into_body(), usize::MAX)
            .await
            .unwrap();
        let text = String::from_utf8(bytes.to_vec()).unwrap();
        assert!(text.contains("测试书名"));
        assert!(text.contains("第一章的完整正文。"));

        // GBK 转码：可解码且内容一致
        let resp2 = export_to_txt(
            State(state.clone()),
            q(&[("bookUrl", "https://src/book1"), ("charset", "gbk")]),
            empty_headers(),
            None,
        )
        .await;
        let bytes2 = axum::body::to_bytes(resp2.into_body(), usize::MAX)
            .await
            .unwrap();
        let (decoded, _, _) = encoding_rs::GBK.decode(&bytes2);
        // 仅已缓存的第一章导出（目录行/未缓存章跳过）
        assert!(decoded.contains("第一章的完整正文。"));
        assert!(!decoded.contains("第二章"));

        // 不支持的编码 → 错误 JSON
        let resp3 = export_to_txt(
            State(state),
            q(&[
                ("bookUrl", "https://src/book1"),
                ("charset", "not-a-charset"),
            ]),
            empty_headers(),
            None,
        )
        .await;
        assert_eq!(resp3.status(), StatusCode::OK); // Json 错误包仍 200（ReturnData 契约）
    }

    #[tokio::test]
    async fn test_export_epub_zip() {
        let (state, _dir) = test_state("expepub").await;
        seed(&state).await;
        let resp = export_to_epub(
            State(state),
            q(&[("bookUrl", "https://src/book1")]),
            empty_headers(),
            None,
        )
        .await;
        assert_eq!(resp.status(), StatusCode::OK);
        let ct = resp.headers().get(header::CONTENT_TYPE).unwrap();
        assert_eq!(ct, "application/epub+zip");
        let bytes = axum::body::to_bytes(resp.into_body(), usize::MAX)
            .await
            .unwrap();
        // zip 本地文件头魔数 PK\x03\x04 + mimetype 首条目（Stored 未压缩）
        assert_eq!(&bytes[..4], b"PK\x03\x04");
        // 解压校验：mimetype 内容正确、仅已缓存章导出（目录空行跳过）
        let mut archive = zip::ZipArchive::new(std::io::Cursor::new(bytes.to_vec())).unwrap();
        let mut mimetype = String::new();
        use std::io::Read;
        archive
            .by_name("mimetype")
            .unwrap()
            .read_to_string(&mut mimetype)
            .unwrap();
        assert_eq!(mimetype, "application/epub+zip");
        let mut found_content = false;
        for i in 0..archive.len() {
            let mut f = archive.by_index(i).unwrap();
            let mut buf = String::new();
            let _ = f.read_to_string(&mut buf);
            if buf.contains("第一章的完整正文。") {
                found_content = true;
            }
        }
        assert!(found_content, "EPUB 内应含已缓存章正文");
    }
}
