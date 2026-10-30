//! /bot/v1 机器人封装 API 端到端测试（经完整 router() 走真实 HTTP）
//!
//! 覆盖：
//! 1. 鉴权：无 Key / 错 Key → 401；health 免鉴权
//! 2. 聚合搜索：双源同书去重（sourceCount=2）、每源成败统计、封面直链
//! 3. 目录（含卷标 total/realChapters）与按 index 取正文（卷标 index → 400）
//! 4. 下载任务：txt 打包 → 轮询 done → 文件下载（含正文与附件头）
//! 5. 换源：候选列表 + 指定切换 + 服务端进度重定位（越界 → 标题匹配缺失 → 就近钳制）

use std::net::SocketAddr;

use axum::response::IntoResponse;
use axum::routing::get;
use axum::Router;
use serde_json::{json, Value};

mod common;

const BOT_KEY: &str = "e2e-bot-key-123";
/// 第二把 key（映射 other 命名空间）——任务归属 ns 校验用例用
const BOT_KEY_OTHER: &str = "e2e-bot-key-other";

/// 全局一次性放行私网（OnceLock 永不 Drop——避免并行测试间 RAII 守卫互相恢复）
fn allow_private_net() -> &'static common::PrivateNetGuard {
    static GUARD: std::sync::OnceLock<common::PrivateNetGuard> = std::sync::OnceLock::new();
    GUARD.get_or_init(common::PrivateNetGuard::on)
}

/// 全局一次性注入 bot key（LazyLock 解析发生在首个请求前）
fn ensure_bot_key_env() {
    static ONCE: std::sync::OnceLock<()> = std::sync::OnceLock::new();
    ONCE.get_or_init(|| {
        std::env::set_var(
            "READER_BOT_API_KEYS",
            format!("{BOT_KEY}:default,{BOT_KEY_OTHER}:other"),
        );
    });
}

/// 全用例串行锁：每 key 并发闸门与全局后台任务 running 上限都是进程级资源，
/// 并行用例互相挤占（429）——本文件用例串行执行保证确定性。
static TEST_SERIALIZE: tokio::sync::Mutex<()> = tokio::sync::Mutex::const_new(());

/// 等待本命名空间全部 bot 任务离开 running（全局 running 槽位进程级共享——
/// 用例结束前排空，避免拖累下一用例）
async fn drain_bot_jobs(client: &reqwest::Client, base: &str, auth: &str) {
    for _ in 0..100 {
        let v: Value = client
            .get(format!("{base}/jobs"))
            .header("Authorization", auth)
            .send()
            .await
            .unwrap()
            .json()
            .await
            .unwrap();
        let running = v["data"]["jobs"]
            .as_array()
            .map(|a| a.iter().any(|j| j["status"] == json!("running")))
            .unwrap_or(false);
        if !running {
            return;
        }
        tokio::time::sleep(std::time::Duration::from_millis(100)).await;
    }
    panic!("10s 内仍有 running 任务未排空");
}

/// 1x1 PNG（封面端点用）
const TINY_PNG: &[u8] = &[
    0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00, 0x00, 0x00, 0x0D, 0x49, 0x48, 0x44, 0x52,
    0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x08, 0x06, 0x00, 0x00, 0x00, 0x1F, 0x15, 0xC4,
    0x89, 0x00, 0x00, 0x00, 0x0D, 0x49, 0x44, 0x41, 0x54, 0x78, 0x9C, 0x62, 0x00, 0x01, 0x00, 0x00,
    0x05, 0x00, 0x01, 0x0D, 0x0A, 0x2D, 0xB4, 0x00, 0x00, 0x00, 0x00, 0x49, 0x45, 0x4E, 0x44, 0xAE,
    0x42, 0x60, 0x82,
];

/// mock 书站：CSS 源 + JSON 源，两源都收录同一本书《斗破苍穹》（搜索去重/换源用）
async fn spawn_mock_site() -> u16 {
    let listener = tokio::net::TcpListener::bind("127.0.0.1:0").await.unwrap();

    let css_search = r##"<!doctype html><html><body>
<ul class="result-list">
  <li class="item">
    <a class="title-link" href="/book/1.html">斗破苍穹</a>
    <span class="author">天蚕土豆</span>
    <img class="cover" src="/img/a.png"/>
  </li>
</ul>
</body></html>"##;

    let css_detail = r##"<!doctype html><html><body>
<h1 class="book-title">斗破苍穹</h1>
<span class="book-author">天蚕土豆</span>
<a class="toc-entry" href="/toc/1.html">开始阅读</a>
</body></html>"##;

    // CSS 目录：卷标行 + 4 章（换源前进度 index=3「第三章 高潮」）
    let css_toc = r##"<!doctype html><html><body>
<ul class="chapter-list">
  <li class="vol">第一卷</li>
  <li class="ch"><a href="/chapter/1.html">第一章 起点</a></li>
  <li class="ch"><a href="/chapter/2.html">第二章 转折</a></li>
  <li class="ch"><a href="/chapter/3.html">第三章 高潮</a></li>
  <li class="ch"><a href="/chapter/4.html">第四章 结局</a></li>
</ul>
</body></html>"##;

    let json_search = json!({
        "code": 0,
        "data": {"list": [{"bid": "/japi/book/1", "bname": "斗破苍穹", "author": "天蚕土豆", "cover": "/jimg/b.png"}]}
    })
    .to_string();
    // JSON 目录只有 2 章：旧进度 index=3 越界 → 标题「第三章 高潮」无命中 → 就近钳制到末章
    let json_detail = json!({"info": {"bname": "斗破苍穹", "author": "天蚕土豆", "intro": "json 源简介", "toc": "/japi/toc/1"}}).to_string();
    let json_toc = json!({"chapters": [
        {"cname": "第一章 起点", "curl": "/japi/chapter/1"},
        {"cname": "第二章 转折", "curl": "/japi/chapter/2"}
    ]})
    .to_string();

    // 慢速书（running 上限用例：详情/目录/章节同构，每章延迟 2s——保证任务在跑窗口足够长）
    let slow_detail = r##"<!doctype html><html><body>
<h1 class="book-title">慢速书</h1>
<span class="book-author">慢作者</span>
<a class="toc-entry" href="/slow/toc/1.html">开始阅读</a>
</body></html>"##;
    let slow_toc = r##"<!doctype html><html><body>
<ul class="chapter-list">
  <li class="ch"><a href="/slow/chapter/1.html">第一章 慢</a></li>
  <li class="ch"><a href="/slow/chapter/2.html">第二章 慢</a></li>
  <li class="ch"><a href="/slow/chapter/3.html">第三章 慢</a></li>
  <li class="ch"><a href="/slow/chapter/4.html">第四章 慢</a></li>
</ul>
</body></html>"##;

    async fn text(body: String, ct: &str) -> axum::response::Response {
        axum::http::Response::builder()
            .header("content-type", ct)
            .body(axum::body::Body::from(body))
            .unwrap()
            .into_response()
    }
    let app = Router::new()
        .route("/search", get(move || async { text(css_search.into(), "text/html; charset=utf-8").await }))
        .route("/book/1.html", get(move || async { text(css_detail.into(), "text/html; charset=utf-8").await }))
        .route("/toc/1.html", get(move || async { text(css_toc.into(), "text/html; charset=utf-8").await }))
        .route(
            "/chapter/:n.html",
            get(|axum::extract::Path(n): axum::extract::Path<String>| async move {
                let title = format!("第{n}章");
                let body = format!(
                    r#"<!doctype html><html><body><h2>{title}</h2><div id="content"><p>{title}的正文内容，测试抓取。</p></div></body></html>"#
                );
                text(body, "text/html; charset=utf-8").await
            }),
        )
        .route("/img/a.png", get(|| async { TINY_PNG.to_vec() }))
        .route("/jimg/b.png", get(|| async { TINY_PNG.to_vec() }))
        .route("/japi/search", get({ let b = json_search.clone(); move || async move { text(b.clone(), "application/json").await } }))
        .route("/japi/book/1", get({ let b = json_detail.clone(); move || async move { text(b.clone(), "application/json").await } }))
        .route("/japi/toc/1", get({ let b = json_toc.clone(); move || async move { text(b.clone(), "application/json").await } }))
        .route("/slow/book/1.html", get(move || async move { text(slow_detail.into(), "text/html; charset=utf-8").await }))
        .route("/slow/toc/1.html", get(move || async move { text(slow_toc.into(), "text/html; charset=utf-8").await }))
        .route(
            "/slow/chapter/:n.html",
            get(|axum::extract::Path(n): axum::extract::Path<String>| async move {
                // 每章 2s 延迟——制造足够长的 running 窗口
                tokio::time::sleep(std::time::Duration::from_secs(2)).await;
                let title = format!("第{n}章 慢");
                let body = format!(
                    r#"<!doctype html><html><body><h2>{title}</h2><div id="content"><p>{title}的慢速正文。</p></div></body></html>"#
                );
                text(body, "text/html; charset=utf-8").await
            }),
        )
        .route(
            "/japi/chapter/:n",
            get(|axum::extract::Path(n): axum::extract::Path<String>| async move {
                let body = json!({"content": [format!("json 第{n}章正文段落一。"), "json 第二段。"]}).to_string();
                text(body, "application/json").await
            }),
        );

    let server = axum::serve(listener, app);
    let addr: SocketAddr = server.local_addr().expect("mock 启动");
    tokio::spawn(async move { server.await });
    addr.port()
}

fn css_source(port: u16) -> Value {
    let base = format!("http://127.0.0.1:{port}");
    json!({
        "bookSourceUrl": format!("{base}/css"),
        "bookSourceName": "CSS 测试源",
        "enabled": true,
        "searchUrl": format!("{base}/search?q={{{{key}}}}"),
        "ruleSearch": {
            "bookList": "ul.result-list@li.item",
            "name": "a.title-link@text",
            "author": "span.author@text",
            "bookUrl": "a.title-link@href",
            "coverUrl": "img.cover@src"
        },
        "ruleBookInfo": {
            "name": "h1.book-title@text",
            "author": "span.book-author@text",
            "tocUrl": "a.toc-entry@href"
        },
        "ruleToc": {
            "chapterList": "ul.chapter-list@li",
            "chapterName": "text",
            "chapterUrl": "a@href",
            "isVolume": "class.vol"
        },
        "ruleContent": { "content": "#content@p@text" }
    })
}

fn json_source(port: u16) -> Value {
    let base = format!("http://127.0.0.1:{port}");
    json!({
        "bookSourceUrl": format!("{base}/japi"),
        "bookSourceName": "JSON 测试源",
        "enabled": true,
        "searchUrl": format!("{base}/japi/search?key={{{{key}}}}"),
        "ruleSearch": {
            "bookList": "$.data.list[*]",
            "name": "$.bname",
            "author": "$.author",
            "bookUrl": "$.bid",
            "coverUrl": "$.cover"
        },
        "ruleBookInfo": {
            "name": "$.info.bname",
            "author": "$.info.author",
            "tocUrl": "$.info.toc"
        },
        "ruleToc": {
            "chapterList": "$.chapters[*]",
            "chapterName": "$.cname",
            "chapterUrl": "$.curl"
        },
        "ruleContent": { "content": "$.content[*]" }
    })
}

/// 起完整应用（注入 bot key + 双源），返回 (地址, css 书 url, css 源 url, json 书 url, json 源 url)
async fn spawn_app() -> (SocketAddr, String, String, String, String) {
    ensure_bot_key_env();
    // 每次调用独立目录（并行测试同 pid → 计数器隔离，避免 SQLite database is locked）
    static SEQ: std::sync::atomic::AtomicUsize = std::sync::atomic::AtomicUsize::new(0);
    let seq = SEQ.fetch_add(1, std::sync::atomic::Ordering::Relaxed);
    let port = spawn_mock_site().await;
    let css = css_source(port);
    let js = json_source(port);
    let base = format!("http://127.0.0.1:{port}");

    let dir = std::env::temp_dir().join(format!(
        "reader-e2e-bot-{pid}-{seq}",
        pid = std::process::id()
    ));
    let _ = std::fs::remove_dir_all(&dir);
    let mut config = reader_dev::AppConfig::from_env();
    config.work_dir = dir.to_string_lossy().into_owned();
    let storage = reader_dev::storage::init(&config).await.unwrap();
    for src in [&css, &js] {
        storage
            .save_book_source(
                "default",
                &serde_json::from_value::<reader_dev::model::BookSource>(src.clone()).unwrap(),
            )
            .await
            .unwrap();
    }

    let app = reader_dev::api::router::router(config, storage);
    let listener = tokio::net::TcpListener::bind("127.0.0.1:0").await.unwrap();
    let addr = listener.local_addr().unwrap();
    tokio::spawn(async move { axum::serve(listener, app).await });

    let css_url = css["bookSourceUrl"].as_str().unwrap().to_string();
    let js_url = js["bookSourceUrl"].as_str().unwrap().to_string();
    (
        addr,
        format!("{base}/book/1.html"),
        css_url,
        format!("{base}/japi/book/1"),
        js_url,
    )
}

/// 解包 {ok,data} 响应；断言 ok 并返回 data
fn unwrap_ok(v: &Value) -> &Value {
    assert_eq!(v["ok"], json!(true), "响应应 ok: {v}");
    &v["data"]
}

#[tokio::test(flavor = "multi_thread")]
async fn bot_api_auth_and_search() {
    let _guard = allow_private_net();
    let _serial = TEST_SERIALIZE.lock().await;
    let (addr, ..) = spawn_app().await;
    let client = reqwest::Client::new();
    let base = format!("http://{addr}/bot/v1");

    // health 免鉴权
    let v: Value = client
        .get(format!("{base}/health"))
        .send()
        .await
        .unwrap()
        .json()
        .await
        .unwrap();
    assert_eq!(v["ok"], json!(true), "health 应 ok: {v}");

    // 无 Key → 401
    let resp = client
        .post(format!("{base}/search"))
        .json(&json!({"key": "斗破"}))
        .send()
        .await
        .unwrap();
    assert_eq!(resp.status(), 401, "无 Key 应 401");
    let v: Value = resp.json().await.unwrap();
    assert_eq!(v["ok"], json!(false));

    // 错 Key → 401
    let resp = client
        .post(format!("{base}/search"))
        .header("Authorization", "Bearer wrong-key")
        .json(&json!({"key": "斗破"}))
        .send()
        .await
        .unwrap();
    assert_eq!(resp.status(), 401, "错 Key 应 401");

    // 正确 Key：双源同书 → 去重 sourceCount=2；每源统计；封面直链
    let v: Value = client
        .post(format!("{base}/search"))
        .header("Authorization", format!("Bearer {BOT_KEY}"))
        .json(&json!({"key": "斗破"}))
        .send()
        .await
        .unwrap()
        .json()
        .await
        .unwrap();
    let data = unwrap_ok(&v);
    let results = data["results"].as_array().expect("results 数组");
    assert!(!results.is_empty(), "应有结果: {v}");
    let hit = results
        .iter()
        .find(|r| r["name"].as_str() == Some("斗破苍穹"))
        .expect("应命中《斗破苍穹》");
    assert_eq!(
        hit["sourceCount"],
        json!(2),
        "双源同书去重后 sourceCount=2: {hit}"
    );
    assert_eq!(hit["author"], json!("天蚕土豆"));
    let cover_api = hit["coverApi"].as_str().expect("coverApi");
    assert!(cover_api.contains("/bot/v1/cover"), "封面直链: {cover_api}");
    assert_eq!(data["sources"]["succeeded"], json!(2), "两源都应成功: {v}");

    // 封面端点：直链可取（PNG 魔数）。测试态未配 READER_PUBLIC_URL → coverApi 为相对路径，拼源
    let cover_url = format!("http://{addr}{cover_api}");
    let resp = client.get(&cover_url).send().await.unwrap();
    assert_eq!(resp.status(), 200, "封面应 200");
    let bytes = resp.bytes().await.unwrap();
    assert!(
        bytes.starts_with(&[0x89, 0x50, 0x4E, 0x47]),
        "PNG 魔数: {bytes:?}"
    );
}

#[tokio::test(flavor = "multi_thread")]
async fn bot_api_toc_content_download_changesource() {
    let _guard = allow_private_net();
    let _serial = TEST_SERIALIZE.lock().await;
    let (addr, css_book, css_src, json_book, json_src) = spawn_app().await;
    let client = reqwest::Client::new();
    let base = format!("http://{addr}/bot/v1");
    let auth = format!("Bearer {BOT_KEY}");

    // ---- 目录：卷标计数 ----
    let v: Value = client
        .post(format!("{base}/book/toc"))
        .header("Authorization", &auth)
        .json(&json!({"bookUrl": css_book, "bookSourceUrl": css_src}))
        .send()
        .await
        .unwrap()
        .json()
        .await
        .unwrap();
    let data = unwrap_ok(&v);
    assert_eq!(data["total"], json!(5), "1 卷标 + 4 章: {v}");
    assert_eq!(data["realChapters"], json!(4));

    // ---- 按 index 取正文：卷标 index=0 → 400；index=1 → 正文 ----
    let resp = client
        .post(format!("{base}/book/content"))
        .header("Authorization", &auth)
        .json(&json!({"bookUrl": css_book, "bookSourceUrl": css_src, "index": 0}))
        .send()
        .await
        .unwrap();
    assert_eq!(resp.status(), 400, "卷标 index 应 400");

    let v: Value = client
        .post(format!("{base}/book/content"))
        .header("Authorization", &auth)
        .json(&json!({"bookUrl": css_book, "bookSourceUrl": css_src, "index": 1}))
        .send()
        .await
        .unwrap()
        .json()
        .await
        .unwrap();
    let data = unwrap_ok(&v);
    let content = data["content"].as_str().expect("正文");
    assert!(content.contains("正文内容"), "正文内容: {content}");

    // ---- 下载任务：txt ----
    let v: Value = client
        .post(format!("{base}/book/download"))
        .header("Authorization", &auth)
        .json(&json!({"bookUrl": css_book, "bookSourceUrl": css_src, "format": "txt"}))
        .send()
        .await
        .unwrap()
        .json()
        .await
        .unwrap();
    let data = unwrap_ok(&v);
    let task_id = data["taskId"].as_str().expect("taskId").to_string();
    assert_eq!(data["total"], json!(4), "4 实章: {v}");

    // 轮询至 done（mock 站点秒回，10s 足够）
    let mut done = false;
    for _ in 0..50 {
        let v: Value = client
            .get(format!("{base}/jobs/{task_id}"))
            .header("Authorization", &auth)
            .send()
            .await
            .unwrap()
            .json()
            .await
            .unwrap();
        if v["data"]["status"] == json!("done") {
            done = true;
            break;
        }
        assert_ne!(v["data"]["status"], json!("error"), "任务不应失败: {v}");
        tokio::time::sleep(std::time::Duration::from_millis(200)).await;
    }
    assert!(done, "任务应在轮询窗口内完成");

    let resp = client
        .get(format!("{base}/jobs/{task_id}/file"))
        .header("Authorization", &auth)
        .send()
        .await
        .unwrap();
    assert_eq!(resp.status(), 200, "文件应可下载");
    assert!(
        resp.headers().get("content-disposition").is_some(),
        "应有附件头"
    );
    let body = resp.text().await.unwrap();
    assert!(body.contains("第一章 起点"), "txt 含章节标题: {body}");
    assert!(body.contains("正文内容"), "txt 含正文: {body}");

    // ---- 换源 ----
    // 先入架（legacy saveBook，非 secure 测试态免鉴权），进度 index=3「第三章 高潮」
    let v: Value = client
        .post(format!("http://{addr}/reader3/saveBook"))
        .json(&json!({
            "bookUrl": css_book,
            "name": "斗破苍穹",
            "author": "天蚕土豆",
            "origin": css_src,
            "originName": "CSS 测试源",
            "tocUrl": css_book,
            "durChapterIndex": 3,
            "durChapterTitle": "第三章 高潮"
        }))
        .send()
        .await
        .unwrap()
        .json()
        .await
        .unwrap();
    assert_eq!(v["isSuccess"], json!(true), "saveBook: {v}");

    // 候选模式：返回候选（不含切换）
    let v: Value = client
        .post(format!("{base}/book/changeSource"))
        .header("Authorization", &auth)
        .json(&json!({"bookUrl": css_book}))
        .send()
        .await
        .unwrap()
        .json()
        .await
        .unwrap();
    let data = unwrap_ok(&v);
    let cands = data["candidates"].as_array().expect("candidates");
    assert!(
        cands
            .iter()
            .any(|c| c["bookSourceUrl"].as_str() == Some(json_src.as_str())),
        "JSON 源应在候选中: {v}"
    );

    // 指定切换：新目录仅 2 章 → 旧 index=3 越界、标题无命中 → 就近钳制到末章 index=1
    let v: Value = client
        .post(format!("{base}/book/changeSource"))
        .header("Authorization", &auth)
        .json(&json!({"bookUrl": css_book, "newBookUrl": json_book, "bookSourceUrl": json_src}))
        .send()
        .await
        .unwrap()
        .json()
        .await
        .unwrap();
    let data = unwrap_ok(&v);
    assert_eq!(data["progress"]["oldIndex"], json!(3), "旧进度: {v}");
    assert_eq!(
        data["progress"]["newIndex"],
        json!(1),
        "越界+标题无命中 → 钳制末章: {v}"
    );
    assert_eq!(data["progress"]["relocated"], json!(true));
    assert_eq!(data["book"]["origin"], json!(json_src), "书源已切换: {v}");
}

/// 死锁回归：并发注册下载任务 + 高频 /jobs 列表循环。
/// 旧实现 JOBS/JOB_ORDER 双锁 AB-BA（注册 map→order、列表 order→map），并发交错即互等
/// 挂死全服务；单表合并后两路径互斥有序——本用例必须在窗口内全部返回（外层 30s 超时兜底，
/// 客户端单请求 10s 超时：死锁复现时请求超时 → 断言失败而非测试挂死）。
#[tokio::test(flavor = "multi_thread")]
async fn bot_jobs_deadlock_regression_concurrent_register_and_list() {
    let _guard = allow_private_net();
    let _serial = TEST_SERIALIZE.lock().await;
    let (addr, css_book, css_src, ..) = spawn_app().await;
    let client = reqwest::Client::builder()
        .timeout(std::time::Duration::from_secs(10))
        .build()
        .unwrap();
    let base = format!("http://{addr}/bot/v1");
    let auth = format!("Bearer {BOT_KEY}");

    // 列表循环（另一线程持续打 /jobs——锁序 order→map 的那条路径）
    let list_client = client.clone();
    let list_base = base.clone();
    let list_auth = auth.clone();
    let lister = tokio::spawn(async move {
        for _ in 0..200 {
            let resp = list_client
                .get(format!("{list_base}/jobs"))
                .header("Authorization", &list_auth)
                .send()
                .await
                .unwrap();
            assert_eq!(resp.status(), 200, "列表请求不应失败/挂死");
            tokio::time::sleep(std::time::Duration::from_millis(5)).await;
        }
    });

    // 并发注册（锁序 map→order 的那条路径；错峰 40ms 展开——扩大与列表循环的
    // 临界区交错窗口。全局 running 上限 2 / 每 key 并发 4 → 部分 429，同样取表锁）
    let mut regs = Vec::new();
    for i in 0..8 {
        let c = client.clone();
        let b = base.clone();
        let a = auth.clone();
        let book = css_book.clone();
        let src = css_src.clone();
        regs.push(tokio::spawn(async move {
            tokio::time::sleep(std::time::Duration::from_millis(i * 40)).await;
            c.post(format!("{b}/book/download"))
                .header("Authorization", &a)
                .json(&json!({"bookUrl": book, "bookSourceUrl": src, "format": "txt"}))
                .send()
                .await
                .unwrap()
        }));
    }

    let all = tokio::time::timeout(std::time::Duration::from_secs(30), async {
        for h in regs {
            let resp = h.await.unwrap();
            let status = resp.status();
            assert!(
                status == 200 || status == 429,
                "注册应 200 或 429（running/并发上限），实际 {status}"
            );
        }
        lister.await.unwrap();
    })
    .await;
    assert!(all.is_ok(), "并发注册+列表循环应在 30s 内完成（死锁回归）");
    // 排空 running 任务再放行下一用例（全局槽位进程级共享）
    drain_bot_jobs(&client, &base, &auth).await;
}

/// 任务归属 ns 校验：other key 访问 default ns 的任务 → 404（status/cancel/file），
/// 列表只含本 ns 任务。
#[tokio::test(flavor = "multi_thread")]
async fn bot_job_ns_isolation_returns_404() {
    let _guard = allow_private_net();
    let _serial = TEST_SERIALIZE.lock().await;
    let (addr, css_book, css_src, ..) = spawn_app().await;
    let client = reqwest::Client::new();
    let base = format!("http://{addr}/bot/v1");

    // default ns 建一个任务
    let v: Value = client
        .post(format!("{base}/book/download"))
        .header("Authorization", format!("Bearer {BOT_KEY}"))
        .json(&json!({"bookUrl": css_book, "bookSourceUrl": css_src, "format": "txt"}))
        .send()
        .await
        .unwrap()
        .json()
        .await
        .unwrap();
    let task_id = unwrap_ok(&v)["taskId"].as_str().unwrap().to_string();

    let other = format!("Bearer {BOT_KEY_OTHER}");
    // status → 404
    let resp = client
        .get(format!("{base}/jobs/{task_id}"))
        .header("Authorization", &other)
        .send()
        .await
        .unwrap();
    assert_eq!(resp.status(), 404, "跨 ns 查状态应 404");
    // cancel → 404
    let resp = client
        .post(format!("{base}/jobs/{task_id}"))
        .header("Authorization", &other)
        .send()
        .await
        .unwrap();
    assert_eq!(resp.status(), 404, "跨 ns 取消应 404");
    // file → 404
    let resp = client
        .get(format!("{base}/jobs/{task_id}/file"))
        .header("Authorization", &other)
        .send()
        .await
        .unwrap();
    assert_eq!(resp.status(), 404, "跨 ns 取文件应 404");
    // 列表：other ns 不含 default ns 的任务
    let v: Value = client
        .get(format!("{base}/jobs"))
        .header("Authorization", &other)
        .send()
        .await
        .unwrap()
        .json()
        .await
        .unwrap();
    let jobs = unwrap_ok(&v)["jobs"].as_array().unwrap();
    assert!(
        jobs.iter().all(|j| j["taskId"].as_str() != Some(task_id.as_str())),
        "other ns 列表不应含 default ns 任务: {v}"
    );
    // 归属 ns 正常访问仍 200
    let resp = client
        .get(format!("{base}/jobs/{task_id}"))
        .header("Authorization", format!("Bearer {BOT_KEY}"))
        .send()
        .await
        .unwrap();
    assert_eq!(resp.status(), 200, "归属 ns 访问应 200");
    drain_bot_jobs(&client, &base, &format!("Bearer {BOT_KEY}")).await;
}

/// 全局后台下载并发上限（跨 key 共享 2 个 running）：慢速书 3 连发 →
/// 恰好 2 个注册成功、第 3 个 429；随后任务正常完成。
#[tokio::test(flavor = "multi_thread")]
async fn bot_download_global_running_cap() {
    let _guard = allow_private_net();
    let _serial = TEST_SERIALIZE.lock().await;
    let (addr, _, css_src, ..) = spawn_app().await;
    let client = reqwest::Client::new();
    let base = format!("http://{addr}/bot/v1");
    let auth = format!("Bearer {BOT_KEY}");
    // 慢速书地址：由 css_src（形如 http://127.0.0.1:port/css）反推 mock 站 base
    let mock_base = css_src.trim_end_matches("/css").to_string();
    let slow_book = format!("{mock_base}/slow/book/1.html");

    let mut handles = Vec::new();
    for _ in 0..3 {
        let c = client.clone();
        let b = base.clone();
        let a = auth.clone();
        let book = slow_book.clone();
        let src = css_src.clone();
        handles.push(tokio::spawn(async move {
            c.post(format!("{b}/book/download"))
                .header("Authorization", &a)
                .json(&json!({"bookUrl": book, "bookSourceUrl": src, "format": "txt"}))
                .send()
                .await
                .unwrap()
                .status()
        }));
    }
    let mut ok = 0;
    let mut busy = 0;
    for h in handles {
        match h.await.unwrap() {
            reqwest::StatusCode::OK => ok += 1,
            reqwest::StatusCode::TOO_MANY_REQUESTS => busy += 1,
            s => panic!("意外状态码 {s}"),
        }
    }
    assert_eq!(ok, 2, "全局 running 上限 2：恰好 2 个注册成功");
    assert_eq!(busy, 1, "第 3 个应 429");
    // 慢速书每章 2s——排空后再放行下一用例
    drain_bot_jobs(&client, &base, &auth).await;
}
