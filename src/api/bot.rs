//! /bot/v1：机器人专用封装 API（服务七七等程序化客户端）
//!
//! 与 legacy /reader3 的区别（docs/BOT-API.md 有完整调用文档与示例）：
//! - 专用 API Key 鉴权（env `READER_BOT_API_KEYS`，格式 `key1:ns1,key2:ns2`），
//!   免登录、无 token 轮换/会话互挤——程序化客户端不再走用户会话机制；
//! - 统一响应 `{ok:true,data}` / `{ok:false,error:{code,message}}` + 真实 HTTP 状态码；
//! - 组合能力：聚合搜索（含每源成败统计与封面直链）、按 index 取正文、
//!   换源+服务端进度重定位、下载打包（后台任务 → 轮询 → 文件）；
//! - 复用既有 service 层（search/book/export_book/image_cache），进程内直调；
//! - 每 key 并发上限 4（try_acquire 满即 429），保护生产服务。
//!
//! 不依赖书架：书源书只带 bookUrl+bookSourceUrl（搜索结果即含）即可读目录/正文/下载；
//! 换源是书架概念，仍要求书籍已入架。

use std::collections::HashMap;
use std::sync::{Arc, LazyLock, Mutex};

use axum::body::Bytes;
use axum::extract::{Path, Query, State};
use axum::http::{HeaderMap, StatusCode};
use axum::response::{IntoResponse, Response};
use axum::routing::{get, post};
use axum::Json;
use serde_json::{json, Value};

use crate::api::router::AppState;

/// 每 key 并发上限（搜索/下载等重操作同时进行的上限；满则 429）
const BOT_MAX_CONCURRENCY: usize = 4;
/// 聚合搜索默认参与源数上限（use_count+weight 排序后截断）
const SEARCH_MAX_SOURCES_DEFAULT: usize = 60;
/// maxSources 服务端封顶（客户端传更小取更小；防单请求打满全部书源）
const SEARCH_MAX_SOURCES_LIMIT: usize = 200;
/// 单源搜索超时（书源自身 15s 抓取超时 × 重试，45s 兜底防拖垮整批）
const SEARCH_SOURCE_TIMEOUT_SE: u64 = 45;
/// 导出文件保留时长（过期由下载任务顺带清理）
const EXPORT_FILE_TTL_SE: u64 = 3 * 24 * 3600;
/// 内存任务表上限（超出移除最旧已完成任务）
const JOB_TABLE_CAP: usize = 200;
/// 全局后台下载任务并发上限（跨 key 共享——打包/抓取出网总量保护；满则 429）
const BOT_MAX_RUNNING_JOBS: usize = 2;

/// API Key 表：READER_BOT_API_KEYS="key1:ns1,key2:ns2"（启动解析；key 区分大小写）
static BOT_KEYS: LazyLock<HashMap<String, String>> = LazyLock::new(|| {
    let mut m = HashMap::new();
    if let Ok(raw) = std::env::var("READER_BOT_API_KEYS") {
        for pair in raw.split(',') {
            let pair = pair.trim();
            if pair.is_empty() {
                continue;
            }
            match pair.split_once(':') {
                Some((k, ns)) if !k.is_empty() && !ns.is_empty() => {
                    m.insert(k.to_string(), ns.to_string());
                }
                _ => tracing::warn!(
                    "[bot] READER_BOT_API_KEYS 条目格式无效（应为 key:namespace）: {pair}"
                ),
            }
        }
    }
    m
});

/// 每 key 信号量表（并发上限）
static BOT_SEMS: LazyLock<Mutex<HashMap<String, Arc<tokio::sync::Semaphore>>>> =
    LazyLock::new(|| Mutex::new(HashMap::new()));

/// 后台下载/打包任务
struct BotJob {
    ns: String,
    book_url: String,
    format: String,
    status: String, // running | done | error
    cancelled: bool,
    title: String,
    author: String,
    total: usize,
    done: usize,
    failed: Vec<Value>, // [{index,title,url,error}]
    error: Option<String>,
    file: Option<(std::path::PathBuf, String)>, // (路径, 下载文件名)
    created_ms: i64,
}

impl BotJob {
    fn summary(&self, id: &str) -> Value {
        json!({
            "taskId": id,
            "status": self.status,
            "title": self.title,
            "author": self.author,
            "total": self.total,
            "done": self.done,
            "failedChapters": self.failed,
            "error": self.error,
            "file": self.file.as_ref().map(|_| format!("/bot/v1/jobs/{id}/file")),
            "createdMs": self.created_ms,
        })
    }
}

/// 任务表（单锁）。原 JOBS(map) + JOB_ORDER(创建序) 双锁存在 AB-BA 死锁：
/// 注册路径先锁 JOBS 再锁 JOB_ORDER，sweep/列表路径先锁 JOB_ORDER 再锁 JOBS，
/// 并发交错时互等挂死（实测 6 秒全服务无响应）。合并为单 Mutex 后全部锁点天然串行。
struct BotJobTable {
    /// taskId → 任务
    map: HashMap<String, Arc<Mutex<BotJob>>>,
    /// 任务创建序（/jobs 列表用，FIFO 上限 JOB_TABLE_CAP）
    order: std::collections::VecDeque<String>,
}

static JOB_TABLE: LazyLock<Mutex<BotJobTable>> = LazyLock::new(|| {
    Mutex::new(BotJobTable {
        map: HashMap::new(),
        order: std::collections::VecDeque::new(),
    })
});

/// 挂载入口：/bot/v1 路由组（状态由外层 router() 的 with_state 统一注入，
/// 因此这里返回 Router<AppState> 而非自持状态）
pub fn router() -> axum::Router<AppState> {
    tracing::info!("[bot] /bot/v1 已启用（{} 个 API Key）", BOT_KEYS.len());
    axum::Router::new()
        .route("/health", get(bot_health))
        .route("/search", post(bot_search))
        .route("/cover", get(bot_cover))
        .route("/book/info", post(bot_book_info))
        .route("/book/toc", post(bot_book_toc))
        .route("/book/content", post(bot_book_content))
        .route("/book/changeSource", post(bot_change_source))
        .route("/book/download", post(bot_download))
        .route("/jobs", get(bot_jobs_list))
        .route("/jobs/:taskId", get(bot_job_status).post(bot_job_cancel))
        .route("/jobs/:taskId/file", get(bot_job_file))
}

// ---------- 响应辅助 ----------

fn ok(data: Value) -> Response {
    Json(json!({"ok": true, "data": data})).into_response()
}

fn err(status: StatusCode, code: &str, message: impl Into<String>) -> Response {
    (
        status,
        Json(json!({"ok": false, "error": {"code": code, "message": message.into()}})),
    )
        .into_response()
}

fn err_internal(e: impl std::fmt::Display) -> Response {
    tracing::error!("[bot] 内部错误: {e:#}");
    err(
        StatusCode::INTERNAL_SERVER_ERROR,
        "internal",
        format!("{e:#}"),
    )
}

// ---------- 鉴权 ----------

/// 取 API Key（Authorization: Bearer <key> 或 query apiKey）；返回 (key, namespace)
fn bot_key(headers: &HeaderMap, params: &HashMap<String, String>) -> Option<(String, String)> {
    let bearer = headers
        .get(axum::http::header::AUTHORIZATION)
        .and_then(|v| v.to_str().ok())
        .and_then(|v| v.strip_prefix("Bearer "))
        .map(|v| v.trim().to_string());
    let key = params
        .get("apiKey")
        .cloned()
        .or(bearer)
        .filter(|k| !k.is_empty())?;
    BOT_KEYS.get(&key).map(|ns| (key, ns.clone()))
}

/// 鉴权 + 并发闸门：无 key → 401；并发满 → 429。返回 (ns, key, permit)
fn auth(
    headers: &HeaderMap,
    params: &HashMap<String, String>,
) -> Result<(String, String, tokio::sync::OwnedSemaphorePermit), Response> {
    let Some((key, ns)) = bot_key(headers, params) else {
        return Err(err(
            StatusCode::UNAUTHORIZED,
            "unauthorized",
            "缺少或无效的 API Key（Authorization: Bearer <key> 或 ?apiKey=）",
        ));
    };
    let sem = {
        let mut map = BOT_SEMS.lock().unwrap_or_else(|e| e.into_inner());
        map.entry(key.clone())
            .or_insert_with(|| Arc::new(tokio::sync::Semaphore::new(BOT_MAX_CONCURRENCY)))
            .clone()
    };
    let permit = sem.try_acquire_owned().map_err(|_| {
        err(
            StatusCode::TOO_MANY_REQUESTS,
            "busy",
            "该 Key 并发已达上限，请稍后重试",
        )
    })?;
    Ok((ns, key, permit))
}

/// 解析 JSON body（空/非法 body 返回空对象，参数可全部走 query）
fn body_json(body: &Option<Bytes>) -> Value {
    body.as_ref()
        .and_then(|b| serde_json::from_slice::<Value>(b).ok())
        .unwrap_or(Value::Null)
}

fn p(params: &HashMap<String, String>, body: &Value, key: &str) -> String {
    body.get(key)
        .and_then(|v| v.as_str())
        .map(|s| s.to_string())
        .filter(|s| !s.is_empty())
        .or_else(|| params.get(key).cloned())
        .unwrap_or_default()
}

fn p_i64(params: &HashMap<String, String>, body: &Value, key: &str) -> Option<i64> {
    body.get(key)
        .and_then(|v| v.as_i64())
        .or_else(|| params.get(key).and_then(|v| v.parse().ok()))
}

fn p_bool(params: &HashMap<String, String>, body: &Value, key: &str) -> bool {
    body.get(key)
        .map(|v| v.as_u64() == Some(1) || v.as_bool() == Some(true))
        .or_else(|| params.get(key).map(|v| v == "1" || v == "true"))
        .unwrap_or(false)
}

/// 相对/绝对封面直链：READER_PUBLIC_URL 配置时拼绝对地址。
/// 注意：URL 内嵌完整 apiKey——封面是给程序化客户端直接 GET 的（无 header 场景），
/// 该服务的部署面（内网 10002）与 key 本身同级别敏感。
fn cover_api_url(key: &str, url: &str, referer: &str) -> String {
    let base = std::env::var("READER_PUBLIC_URL").unwrap_or_default();
    format!(
        "{base}/bot/v1/cover?apiKey={}&url={}&referer={}",
        urlencode(key),
        urlencode(url),
        urlencode(referer)
    )
}

fn urlencode(s: &str) -> String {
    let mut out = String::with_capacity(s.len());
    for b in s.bytes() {
        match b {
            b'A'..=b'Z' | b'a'..=b'z' | b'0'..=b'9' | b'-' | b'_' | b'.' | b'~' => {
                out.push(b as char)
            }
            _ => out.push_str(&format!("%{b:02X}")),
        }
    }
    out
}

// ---------- 各端点 ----------

/// GET /bot/v1/health（免鉴权）
async fn bot_health() -> Response {
    ok(json!({
        "version": env!("CARGO_PKG_VERSION"),
        "proxy": std::env::var("READER_HTTP_PROXY").map(|v| !v.trim().is_empty()).unwrap_or(false),
        "apiKeys": BOT_KEYS.len(),
    }))
}

/// POST /bot/v1/search {key, exact?, group?, maxSources?, page?}
/// 聚合多源搜索：按 use_count+weight 排序选源、并发搜、(书名,作者) 去重、
/// 每源成败统计、封面直链。响应 data.results / data.sources / data.tookMs。
async fn bot_search(
    State(state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    body: Option<Bytes>,
) -> Response {
    let (_ns, key, _permit) = match auth(&headers, &params) {
        Ok(x) => x,
        Err(r) => return r,
    };
    let body = body_json(&body);
    let mut search_key = p(&params, &body, "key");
    if search_key.is_empty() {
        return err(StatusCode::BAD_REQUEST, "bad_request", "缺少搜索关键字 key");
    }
    let mut exact = p_bool(&params, &body, "exact");
    if search_key.starts_with('=') {
        exact = true;
        search_key.remove(0);
    }
    let group = p(&params, &body, "group");
    let page = p_i64(&params, &body, "page").unwrap_or(1);
    let max_sources = p_i64(&params, &body, "maxSources")
        .map(|v| v.clamp(1, SEARCH_MAX_SOURCES_LIMIT as i64) as usize)
        .unwrap_or(SEARCH_MAX_SOURCES_DEFAULT);

    let namespace = _ns.clone();
    let sources = match state.storage.get_book_sources(&namespace).await {
        Ok(s) => s,
        Err(e) => return err_internal(e),
    };
    let mut enabled: Vec<crate::model::BookSource> = sources
        .into_iter()
        .filter(|s| s.enabled && s.search_url.is_some())
        .filter(|s| !crate::service::health::is_source_invalid(&namespace, &s.book_source_url))
        .filter(|s| {
            group.is_empty()
                || s.book_source_group
                    .as_deref()
                    .map(|g| {
                        g.split([',', '，', '、', ' ', '\t'])
                            .any(|part| part == group)
                    })
                    .unwrap_or(false)
        })
        .collect();
    let skipped_invalid = 0usize; // 失效源已在过滤时排除（不计入 failed，属常态短路）
                                  // use_count+weight 排序：历史成功率高的源优先参与（use_count 采集自此开始被消费）
    enabled.sort_by(|a, b| {
        b.use_count
            .cmp(&a.use_count)
            .then(b.weight.cmp(&a.weight))
            .then(a.custom_order.cmp(&b.custom_order))
            .then(a.book_source_name.cmp(&b.book_source_name))
    });
    enabled.truncate(max_sources);
    if enabled.is_empty() {
        return err(StatusCode::NOT_FOUND, "no_sources", "无可用书源");
    }

    let started = std::time::Instant::now();
    let semaphore = Arc::new(tokio::sync::Semaphore::new(24));
    let mut handles = Vec::with_capacity(enabled.len());
    for source in enabled {
        let sem = semaphore.clone();
        let key = search_key.clone();
        let ns = namespace.clone();
        let storage = state.storage.clone();
        handles.push((
            source.book_source_name.clone(),
            source.book_source_url.clone(),
            tokio::spawn(async move {
                let _permit = sem.acquire().await;
                tokio::time::timeout(
                    std::time::Duration::from_secs(SEARCH_SOURCE_TIMEOUT_SE),
                    crate::service::search::search_one_source(&storage, &ns, &source, &key, page),
                )
                .await
            }),
        ));
    }
    let mut all = Vec::new();
    let mut failed: Vec<Value> = Vec::new();
    let mut succeeded = 0usize;
    for (name, url, h) in handles {
        match h.await {
            Ok(Ok(Ok(books))) => {
                succeeded += 1;
                all.extend(books);
            }
            Ok(Ok(Err(e))) => failed.push(json!({"name": name, "url": url, "error": format!("{e:#}").chars().take(120).collect::<String>()})),
            Ok(Err(_)) => failed.push(json!({"name": name, "url": url, "error": "搜索超时"})),
            Err(e) => failed.push(json!({"name": name, "url": url, "error": format!("任务异常: {e}")})),
        }
    }
    if exact {
        all = crate::service::search::filter_exact(all, &search_key);
    }
    // (书名,作者) 去重：同书多源只保留一条，但记录 sourceCount 与全部可用源
    // （首发源已按 use_count+weight 排序 → 首个命中即最优源）
    let mut seen: HashMap<String, (usize, Value)> = HashMap::new();
    for b in all {
        let dk = format!("{}_{}", b.name, b.author);
        if dk.is_empty() {
            continue;
        }
        match seen.get_mut(&dk) {
            Some((cnt, val)) => {
                *cnt += 1;
                if let Some(o) = val.as_object_mut() {
                    o.insert("sourceCount".into(), json!(*cnt + 1));
                }
            }
            None => {
                let mut v = serde_json::to_value(&b).unwrap_or(Value::Null);
                if let Some(o) = v.as_object_mut() {
                    let cover = b.cover_url.clone().unwrap_or_default();
                    let referer = if b.origin.is_empty() {
                        b.book_url.clone()
                    } else {
                        b.origin.clone()
                    };
                    o.insert(
                        "coverApi".into(),
                        json!(cover_api_url(&key, &cover, &referer)),
                    );
                    o.insert("sourceCount".into(), json!(1));
                }
                seen.insert(dk, (0, v));
            }
        }
    }
    let mut results: Vec<Value> = seen.into_values().map(|(_, v)| v).collect();
    // 多源命中的书排前（最稳），同数保持引擎返回序
    results.sort_by(|a, b| {
        b.get("sourceCount")
            .and_then(|v| v.as_i64())
            .cmp(&a.get("sourceCount").and_then(|v| v.as_i64()))
    });

    tracing::info!(
        "[bot:{namespace}] 搜索 \"{search_key}\" → {} 条结果（{} 源失败）",
        results.len(),
        failed.len()
    );
    ok(json!({
        "results": results,
        "sources": {
            "succeeded": succeeded,
            "failed": failed,
            "skippedInvalid": skipped_invalid,
        },
        "tookMs": started.elapsed().as_millis() as u64,
    }))
}

/// GET /bot/v1/cover?url=&referer=&apiKey= —— 封面字节（磁盘 LRU 缓存）
async fn bot_cover(
    State(state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
) -> Response {
    let (ns, _key, _permit) = match auth(&headers, &params) {
        Ok(x) => x,
        Err(r) => return r,
    };
    let url = params.get("url").cloned().unwrap_or_default();
    if url.is_empty() {
        return err(StatusCode::BAD_REQUEST, "bad_request", "缺少 url");
    }
    let referer = params.get("referer").cloned().unwrap_or_default();
    let referer = if referer.is_empty() {
        None
    } else {
        Some(referer.as_str())
    };
    match state
        .image_cache
        .get_or_fetch(&ns, &url, referer, 10, 5 * 1024 * 1024)
        .await
    {
        Ok((bytes, content_type, status, _hit)) => {
            if status != 200 || bytes.is_empty() {
                return err(
                    StatusCode::BAD_GATEWAY,
                    "upstream",
                    format!("封面源返回 {status}"),
                );
            }
            let ct = content_type.unwrap_or_else(|| "image/jpeg".into());
            (
                StatusCode::OK,
                [
                    (
                        axum::http::header::CONTENT_TYPE,
                        ct.parse()
                            .unwrap_or(axum::http::HeaderValue::from_static("image/jpeg")),
                    ),
                    (
                        axum::http::header::CACHE_CONTROL,
                        axum::http::HeaderValue::from_static("public, max-age=86400"),
                    ),
                ],
                Bytes::from(bytes),
            )
                .into_response()
        }
        Err(e) => err(
            StatusCode::BAD_GATEWAY,
            "upstream",
            format!("封面获取失败: {e:#}"),
        ),
    }
}

/// 书源定位：书架 origin 优先，bookSourceUrl 参数兜底（书架外的搜索结果书）
async fn resolve_source_for(
    state: &AppState,
    ns: &str,
    book: Option<&crate::model::Book>,
    book_source_url: &str,
) -> Result<crate::model::BookSource, Response> {
    if let Some(b) = book {
        if !b.origin.is_empty() {
            if let Ok(Some(s)) = state.storage.find_book_source(ns, &b.origin).await {
                return Ok(s);
            }
        }
    }
    if !book_source_url.is_empty() {
        if book_source_url.trim_start().starts_with('{') {
            if let Ok(s) = serde_json::from_str(book_source_url) {
                return Ok(s);
            }
        }
        if let Ok(Some(s)) = state.storage.find_book_source(ns, book_source_url).await {
            return Ok(s);
        }
    }
    Err(err(
        StatusCode::BAD_REQUEST,
        "no_source",
        "无法定位书源（书未入架且未传 bookSourceUrl）",
    ))
}

/// POST /bot/v1/book/info {bookUrl, bookSourceUrl?}
async fn bot_book_info(
    State(state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    body: Option<Bytes>,
) -> Response {
    let (ns, _key, _permit) = match auth(&headers, &params) {
        Ok(x) => x,
        Err(r) => return r,
    };
    let body = body_json(&body);
    let book_url = p(&params, &body, "bookUrl");
    if book_url.is_empty() {
        return err(StatusCode::BAD_REQUEST, "bad_request", "缺少 bookUrl");
    }
    let shelf = state.storage.find_book(&ns, &book_url).await.ok().flatten();
    // 书架书直接返回（含进度/分组等完整字段）
    if let Some(b) = shelf.clone() {
        let mut v = serde_json::to_value(&b).unwrap_or(Value::Null);
        if let Some(o) = v.as_object_mut() {
            if let Some(cover) = b.cover_url.clone().filter(|c| !c.is_empty()) {
                let referer = if b.origin.is_empty() {
                    b.book_url.clone()
                } else {
                    b.origin.clone()
                };
                o.insert(
                    "coverApi".into(),
                    json!(cover_api_url(&_key, &cover, &referer)),
                );
            }
        }
        return ok(v);
    }
    // 未入架：需要书源 → 实时解析详情
    let source_url = p(&params, &body, "bookSourceUrl");
    let source = match resolve_source_for(&state, &ns, None, &source_url).await {
        Ok(s) => s,
        Err(r) => return r,
    };
    match crate::service::book::fetch_book_info(&ns, &book_url, &source, None).await {
        Ok(info) => {
            let mut v = serde_json::to_value(&info).unwrap_or(Value::Null);
            if let Some(o) = v.as_object_mut() {
                if let Some(cover) = info.cover_url.clone().filter(|c| !c.is_empty()) {
                    let referer = if info.origin.is_empty() {
                        source.book_source_url.clone()
                    } else {
                        info.origin.clone()
                    };
                    o.insert(
                        "coverApi".into(),
                        json!(cover_api_url(&_key, &cover, &referer)),
                    );
                }
            }
            ok(v)
        }
        Err(e) => err(
            StatusCode::BAD_GATEWAY,
            "upstream",
            format!("获取书籍详情失败: {e:#}"),
        ),
    }
}

/// 取目录（缓存 5 分钟优先）：书架 origin 或 bookSourceUrl 定位书源。
/// 非书架书先解析详情拿 tocUrl（bookUrl 多为详情页而非目录页）。
async fn toc_for(
    state: &AppState,
    ns: &str,
    book_url: &str,
    book: Option<&crate::model::Book>,
    book_source_url: &str,
    refresh: bool,
) -> Result<(crate::model::BookSource, Vec<crate::model::BookChapter>), Response> {
    let source = resolve_source_for(state, ns, book, book_source_url).await?;
    let (toc_url, name) = match book {
        Some(b) => (
            if b.toc_url.is_empty() {
                book_url.to_string()
            } else {
                b.toc_url.clone()
            },
            Some(b.name.clone()),
        ),
        None => {
            let info = crate::service::book::fetch_book_info(ns, book_url, &source, None)
                .await
                .map_err(|e| {
                    err(
                        StatusCode::BAD_GATEWAY,
                        "upstream",
                        format!("获取书籍详情失败: {e:#}"),
                    )
                })?;
            (
                info.toc_url
                    .clone()
                    .filter(|s| !s.is_empty())
                    .unwrap_or_else(|| book_url.to_string()),
                if info.name.is_empty() {
                    None
                } else {
                    Some(info.name)
                },
            )
        }
    };
    if !refresh {
        if let Ok(Some(cached)) = state
            .storage
            .get_toc_cache(ns, &toc_url, 5 * 60 * 1000)
            .await
        {
            if let Ok(chapters) = serde_json::from_str::<Vec<crate::model::BookChapter>>(&cached) {
                return Ok((source, chapters));
            }
        }
    }
    match crate::service::book::analyze_toc(ns, &toc_url, &source, 20, name.as_deref(), book_url)
        .await
    {
        Ok(chapters) => {
            if let Ok(json_str) = serde_json::to_string(&chapters) {
                let _ = state
                    .storage
                    .cache_toc(ns, book_url, &toc_url, &json_str)
                    .await;
            }
            Ok((source, chapters))
        }
        Err(e) => Err(err(
            StatusCode::BAD_GATEWAY,
            "upstream",
            format!("获取目录失败: {e:#}"),
        )),
    }
}

/// POST /bot/v1/book/toc {bookUrl, bookSourceUrl?, refresh?}
async fn bot_book_toc(
    State(state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    body: Option<Bytes>,
) -> Response {
    let (ns, _key, _permit) = match auth(&headers, &params) {
        Ok(x) => x,
        Err(r) => return r,
    };
    let body = body_json(&body);
    let book_url = p(&params, &body, "bookUrl");
    if book_url.is_empty() {
        return err(StatusCode::BAD_REQUEST, "bad_request", "缺少 bookUrl");
    }
    let shelf = state.storage.find_book(&ns, &book_url).await.ok().flatten();
    let refresh = p_bool(&params, &body, "refresh");
    let source_url = p(&params, &body, "bookSourceUrl");
    let (_source, toc) =
        match toc_for(&state, &ns, &book_url, shelf.as_ref(), &source_url, refresh).await {
            Ok(x) => x,
            Err(r) => return r,
        };
    let real = toc.iter().filter(|c| !c.is_volume).count();
    ok(json!({
        "total": toc.len(),
        "realChapters": real,
        "chapters": toc,
    }))
}

/// POST /bot/v1/book/content {bookUrl, index, bookSourceUrl?}
/// 按目录原始下标取正文（volume 下标 400）；缓存优先，miss 回源抓取并落缓存。
async fn bot_book_content(
    State(state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    body: Option<Bytes>,
) -> Response {
    let (ns, _key, _permit) = match auth(&headers, &params) {
        Ok(x) => x,
        Err(r) => return r,
    };
    let body = body_json(&body);
    let book_url = p(&params, &body, "bookUrl");
    let Some(index) = p_i64(&params, &body, "index") else {
        return err(StatusCode::BAD_REQUEST, "bad_request", "缺少 index");
    };
    if book_url.is_empty() {
        return err(StatusCode::BAD_REQUEST, "bad_request", "缺少 bookUrl");
    }
    let shelf = state.storage.find_book(&ns, &book_url).await.ok().flatten();
    let source_url = p(&params, &body, "bookSourceUrl");
    let (source, toc) =
        match toc_for(&state, &ns, &book_url, shelf.as_ref(), &source_url, false).await {
            Ok(x) => x,
            Err(r) => return r,
        };
    if index < 0 || index as usize >= toc.len() {
        return err(
            StatusCode::NOT_FOUND,
            "not_found",
            format!("index 越界（目录共 {} 项）", toc.len()),
        );
    }
    let chapter = &toc[index as usize];
    if chapter.is_volume {
        return err(
            StatusCode::BAD_REQUEST,
            "bad_request",
            "index 指向卷标题行（isVolume），请选真实章节",
        );
    }
    let book_name = shelf.as_ref().map(|b| b.name.clone());
    let idx_hash = crate::util::md5::chapter_url_hash(&chapter.url);
    // 缓存命中
    if let Ok(Some(content)) = state
        .storage
        .get_chapter_content(&ns, &book_url, idx_hash)
        .await
    {
        if !content.trim().is_empty() {
            return ok(json!({"index": index, "title": chapter.title, "content": content}));
        }
    }
    // 回源抓取
    match crate::service::book::analyze_content(
        &ns,
        &chapter.url,
        &source,
        5,
        Some(&chapter.title),
        book_name.as_deref(),
        &book_url,
    )
    .await
    {
        Ok(content) => {
            if state.storage.config.cache_chapter_content {
                let _ = state
                    .storage
                    .cache_chapter_content(&ns, &book_url, idx_hash, &chapter.title, &content)
                    .await;
            }
            tracing::info!(
                "[bot:{ns}] 正文 {book_url}#{index}（{} 字）",
                content.chars().count()
            );
            ok(json!({"index": index, "title": chapter.title, "content": content}))
        }
        Err(e) => err(
            StatusCode::BAD_GATEWAY,
            "upstream",
            format!("获取正文失败: {e:#}"),
        ),
    }
}

/// 换源后阅读位置重定位（移植 web-ui progressRelocate.ts，策略一致）：
/// 1) 旧索引在新目录范围内且非卷标 → 保留；2) 旧章标题精确匹配非卷标章；
/// 3) 就近钳制：从 min(旧索引, 末章) 向前找最近非卷标章。无进度/空目录 → -1。
fn relocate_chapter_index(
    old_index: i64,
    old_title: Option<&str>,
    toc: &[crate::model::BookChapter],
) -> i64 {
    if old_index < 0 || toc.is_empty() {
        return -1;
    }
    let oi = old_index as usize;
    if oi < toc.len() && !toc[oi].is_volume {
        return old_index;
    }
    let title = old_title.unwrap_or("").trim();
    if !title.is_empty() {
        if let Some(hit) = toc
            .iter()
            .position(|c| !c.is_volume && c.title.trim() == title)
        {
            return hit as i64;
        }
    }
    let mut i = oi.min(toc.len() - 1);
    while i > 0 && toc[i].is_volume {
        i -= 1;
    }
    if toc[i].is_volume {
        -1
    } else {
        i as i64
    }
}

/// 换源候选搜索：全部启用可搜索源（排除当前源）并发搜书名 →
/// 书名双向包含过滤 → 每源保留一条；返回序即 use_count+weight 优先序。
async fn find_change_candidates(
    state: &AppState,
    ns: &str,
    name: &str,
    cur_origin: &str,
) -> Vec<(crate::model::BookSource, crate::service::search::SearchBook)> {
    let sources = state.storage.get_book_sources(ns).await.unwrap_or_default();
    let mut sources: Vec<crate::model::BookSource> = sources
        .into_iter()
        .filter(|s| {
            s.enabled
                && s.search_url.is_some()
                && s.book_source_url != cur_origin
                && s.book_source_name != cur_origin
        })
        .collect();
    sources.sort_by(|a, b| {
        b.use_count
            .cmp(&a.use_count)
            .then(b.weight.cmp(&a.weight))
            .then(a.book_source_name.cmp(&b.book_source_name))
    });
    let semaphore = Arc::new(tokio::sync::Semaphore::new(24));
    let mut handles = Vec::new();
    for source in sources {
        let sem = semaphore.clone();
        let key = name.to_string();
        let ns = ns.to_string();
        let storage = state.storage.clone();
        let src = source.clone();
        handles.push((
            source,
            tokio::spawn(async move {
                let _permit = sem.acquire().await;
                tokio::time::timeout(
                    std::time::Duration::from_secs(SEARCH_SOURCE_TIMEOUT_SE),
                    crate::service::search::search_one_source(&storage, &ns, &src, &key, 1),
                )
                .await
            }),
        ));
    }
    let name_l = name.trim().to_lowercase();
    let mut out = Vec::new();
    for (source, h) in handles {
        if let Ok(Ok(Ok(books))) = h.await {
            if let Some(hit) = books.into_iter().find(|b| {
                let bn = b.name.trim().to_lowercase();
                !bn.is_empty() && (bn.contains(&name_l) || name_l.contains(&bn))
            }) {
                out.push((source, hit));
            }
        }
    }
    out
}

/// POST /bot/v1/book/changeSource
/// - {bookUrl}                                → 只返回候选（use_count+weight 排序）
/// - {bookUrl, autoPick:true}                 → 自动切到最优候选
/// - {bookUrl, newBookUrl, bookSourceUrl}     → 切到指定源
/// 切换含服务端进度重定位（标题匹配→就近钳制）并写回。书必须在书架。
async fn bot_change_source(
    State(state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    body: Option<Bytes>,
) -> Response {
    let (ns, _key, _permit) = match auth(&headers, &params) {
        Ok(x) => x,
        Err(r) => return r,
    };
    let body = body_json(&body);
    let book_url = p(&params, &body, "bookUrl");
    if book_url.is_empty() {
        return err(StatusCode::BAD_REQUEST, "bad_request", "缺少 bookUrl");
    }
    let Some(book) = state.storage.find_book(&ns, &book_url).await.ok().flatten() else {
        return err(
            StatusCode::NOT_FOUND,
            "not_found",
            "书籍不在书架（换源是书架操作，请先 saveBook 入架）",
        );
    };
    let auto_pick = p_bool(&params, &body, "autoPick");
    let new_url = p(&params, &body, "newBookUrl");
    let new_source_url = p(&params, &body, "bookSourceUrl");

    // 候选搜索（独立函数见下）；指定切换则直取目标源
    let (target_source, target_book) = if !new_url.is_empty() {
        // 指定切换
        if new_source_url.is_empty() {
            return err(
                StatusCode::BAD_REQUEST,
                "bad_request",
                "指定 newBookUrl 时必须同时传 bookSourceUrl",
            );
        }
        let Some(source) = state
            .storage
            .find_book_source(&ns, &new_source_url)
            .await
            .ok()
            .flatten()
        else {
            return err(StatusCode::NOT_FOUND, "not_found", "指定书源不存在");
        };
        (source, None)
    } else {
        let candidates = find_change_candidates(&state, &ns, &book.name, &book.origin).await;
        if candidates.is_empty() {
            return err(StatusCode::NOT_FOUND, "no_candidate", "未找到换源候选");
        }
        if !auto_pick {
            // 只报候选（含可直接用于指定切换的 newBookUrl/bookSourceUrl）
            let list: Vec<Value> = candidates
                .iter()
                .map(|(s, b)| {
                    json!({
                        "bookSourceUrl": s.book_source_url,
                        "bookSourceName": s.book_source_name,
                        "useCount": s.use_count,
                        "weight": s.weight,
                        "newBookUrl": b.book_url,
                        "name": b.name,
                        "author": b.author,
                        "latestChapterTitle": b.latest_chapter_title,
                    })
                })
                .collect();
            return ok(json!({"bookUrl": book_url, "candidates": list}));
        }
        let (s, b) = candidates.into_iter().next().unwrap();
        (s, Some(b))
    };

    // 目标书详情（指定切换必取；autoPick 从搜索结果补全详情）
    let target_url = target_book
        .as_ref()
        .map(|b| b.book_url.clone())
        .unwrap_or_else(|| new_url.clone());
    let info = match crate::service::book::fetch_book_info(
        &ns,
        &target_url,
        &target_source,
        Some(&book.name),
    )
    .await
    {
        Ok(i) => i,
        Err(e) => {
            return err(
                StatusCode::BAD_GATEWAY,
                "upstream",
                format!("获取新书详情失败: {e:#}"),
            )
        }
    };
    let toc_url_new = info
        .toc_url
        .clone()
        .filter(|s| !s.is_empty())
        .unwrap_or_else(|| target_url.clone());
    let origin_new = if info.origin.is_empty() {
        target_source.book_source_url.clone()
    } else {
        info.origin.clone()
    };

    // 切换（进度行保留 dur_*，旧缓存清理）
    let old_index = book.dur_chapter_index;
    let old_title = book.dur_chapter_title.clone();
    match state
        .storage
        .switch_book_source(
            &ns,
            &book_url,
            &target_url,
            &origin_new,
            &info.origin_name,
            &toc_url_new,
            info.cover_url.as_deref(),
        )
        .await
    {
        Ok(n) if n > 0 => {}
        Ok(_) => {
            return err(
                StatusCode::NOT_FOUND,
                "not_found",
                "书籍信息错误（换源未生效）",
            )
        }
        Err(e) => return err_internal(e),
    }
    // 新目录（预取 + 缓存）→ 重定位进度 → 写回
    let mut relocate_note = "未重定位";
    match crate::service::book::analyze_toc(
        &ns,
        &toc_url_new,
        &target_source,
        20,
        Some(&book.name),
        &target_url,
    )
    .await
    {
        Ok(chapters) => {
            if let Ok(json_str) = serde_json::to_string(&chapters) {
                let _ = state
                    .storage
                    .cache_toc(&ns, &target_url, &toc_url_new, &json_str)
                    .await;
            }
            let new_index = relocate_chapter_index(old_index, old_title.as_deref(), &chapters);
            if new_index >= 0 {
                let new_title = chapters[new_index as usize].title.clone();
                let now = chrono::Utc::now().timestamp_millis();
                let _ = state
                    .storage
                    .update_book_progress(&ns, &target_url, Some(&new_title), new_index, 0, now)
                    .await;
                relocate_note = "已重定位";
            }
        }
        Err(e) => tracing::warn!("[bot:{ns}] 换源后新目录获取失败（进度未重定位）: {e}"),
    }
    // 回读书架书（含切换后字段与进度）
    let updated = state
        .storage
        .find_book(&ns, &target_url)
        .await
        .ok()
        .flatten();
    tracing::info!("[bot:{ns}] 换源 {book_url} → {target_url}（{relocate_note}）");
    ok(json!({
        "book": updated,
        "progress": {
            "oldIndex": old_index,
            "oldTitle": old_title,
            "newIndex": updated.as_ref().map(|b| b.dur_chapter_index),
            "relocated": relocate_note == "已重定位",
        },
    }))
}

/// 导出目录：storage/data/{ns}/bot-exports/
fn export_dir(state: &AppState, ns: &str) -> std::path::PathBuf {
    state
        .storage
        .config
        .storage_dir()
        .join("data")
        .join(ns)
        .join("bot-exports")
}

/// 清理过期导出文件（>3 天，storage/data/{ns}/bot-exports/）。
/// 每日定时任务调用（schedule.rs）——此前挂在下载任务启动时顺带执行，
/// 无人发起新下载则过期文件永不清。
pub fn sweep_export_files(storage_dir: &std::path::Path) {
    let data_dir = storage_dir.join("data");
    let cutoff = std::time::SystemTime::now() - std::time::Duration::from_secs(EXPORT_FILE_TTL_SE);
    if let Ok(rd) = std::fs::read_dir(&data_dir) {
        for ns_entry in rd.flatten() {
            let dir = ns_entry.path().join("bot-exports");
            if let Ok(files) = std::fs::read_dir(&dir) {
                for f in files.flatten() {
                    let mtime_ok = f
                        .metadata()
                        .and_then(|m| m.modified())
                        .map(|t| t < cutoff)
                        .unwrap_or(false);
                    if mtime_ok {
                        let _ = std::fs::remove_file(f.path());
                    }
                }
            }
        }
    }
}

/// 收敛任务表（超上限移除最旧的非运行中任务）；下载任务启动时顺带执行。
/// 运行中任务不回收——队首在跑时本轮停止（表可短暂超上限，任务结束后下一轮再清）。
fn trim_job_table() {
    let mut table = JOB_TABLE.lock().unwrap_or_else(|e| e.into_inner());
    while table.order.len() > JOB_TABLE_CAP {
        let Some(old) = table.order.pop_front() else {
            break;
        };
        let running = table
            .map
            .get(&old)
            .map(|j| j.lock().unwrap_or_else(|e| e.into_inner()).status == "running")
            .unwrap_or(false);
        if running {
            table.order.push_front(old);
            break;
        }
        table.map.remove(&old);
    }
}

/// POST /bot/v1/book/download {bookUrl, bookSourceUrl?, from?, to?, format:txt|epub}
/// 后台任务：目录 → 并发抓章（缓存优先）→ 打包落盘 → 轮询取文件。
async fn bot_download(
    State(state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    body: Option<Bytes>,
) -> Response {
    let (ns, _key, _permit) = match auth(&headers, &params) {
        Ok(x) => x,
        Err(r) => return r,
    };
    let body = body_json(&body);
    let book_url = p(&params, &body, "bookUrl");
    if book_url.is_empty() {
        return err(StatusCode::BAD_REQUEST, "bad_request", "缺少 bookUrl");
    }
    let format = {
        let f = p(&params, &body, "format").to_lowercase();
        match f.as_str() {
            "" => "txt".to_string(),
            "txt" | "epub" => f,
            _ => {
                return err(
                    StatusCode::BAD_REQUEST,
                    "bad_request",
                    "format 仅支持 txt / epub",
                )
            }
        }
    };
    let from = p_i64(&params, &body, "from");
    let to = p_i64(&params, &body, "to");

    let shelf = state.storage.find_book(&ns, &book_url).await.ok().flatten();
    let source_url = p(&params, &body, "bookSourceUrl");
    let (source, toc) =
        match toc_for(&state, &ns, &book_url, shelf.as_ref(), &source_url, false).await {
            Ok(x) => x,
            Err(r) => return r,
        };
    // 实章（滤卷标）+ 范围（0 基闭区间）
    let mut chapters: Vec<(String, String)> = toc
        .iter()
        .filter(|c| !c.is_volume)
        .map(|c| (c.title.clone(), c.url.clone()))
        .collect();
    let total_all = chapters.len();
    if let (Some(f), Some(t)) = (from, to) {
        let (f, t) = (f.max(0) as usize, t.max(0) as usize);
        if f >= chapters.len() || t >= chapters.len() || f > t {
            return err(
                StatusCode::BAD_REQUEST,
                "bad_request",
                format!("范围无效（实章共 {total_all}，from/to 为 0 基闭区间）"),
            );
        }
        chapters = chapters[f..=t].to_vec();
    }
    if chapters.is_empty() {
        return err(StatusCode::NOT_FOUND, "not_found", "目录为空");
    }

    // 任务登记
    let task_id = uuid::Uuid::new_v4().simple().to_string();
    let title = shelf
        .as_ref()
        .map(|b| b.name.clone())
        .unwrap_or_else(|| book_url.clone());
    let author = shelf.as_ref().map(|b| b.author.clone()).unwrap_or_default();
    let job = Arc::new(Mutex::new(BotJob {
        ns: ns.clone(),
        book_url: book_url.clone(),
        format: format.clone(),
        status: "running".into(),
        cancelled: false,
        title: title.clone(),
        author: author.clone(),
        total: chapters.len(),
        done: 0,
        failed: Vec::new(),
        error: None,
        file: None,
        created_ms: chrono::Utc::now().timestamp_millis(),
    }));
    {
        let mut table = JOB_TABLE.lock().unwrap_or_else(|e| e.into_inner());
        // 全局 running 上限（跨 key 共享）：超限 429——注册与计数同锁原子判定
        let running = table
            .map
            .values()
            .filter(|j| j.lock().unwrap_or_else(|e| e.into_inner()).status == "running")
            .count();
        if running >= BOT_MAX_RUNNING_JOBS {
            return err(
                StatusCode::TOO_MANY_REQUESTS,
                "busy",
                format!("后台下载任务并发已达上限（{BOT_MAX_RUNNING_JOBS}），请稍后重试"),
            );
        }
        table.map.insert(task_id.clone(), job.clone());
        table.order.push_back(task_id.clone());
    }
    trim_job_table();

    // 后台执行：并发抓章（缓存优先）→ 打包 → 落盘
    let st = state.clone();
    let ns2 = ns.clone();
    let book_url2 = book_url.clone();
    let title2 = title.clone();
    let author2 = author.clone();
    let format2 = format.clone();
    let intro = shelf
        .as_ref()
        .and_then(|b| b.intro.clone().or(b.custom_intro.clone()));
    let job2 = job.clone();
    let task_id_spawn = task_id.clone();
    let total_spawn = chapters.len();
    tokio::spawn(async move {
        let storage = st.storage.clone();
        let ns = ns2.clone();
        let book_url = book_url2.clone();
        let src = source.clone();
        let book_name = title2.clone();
        let counter = Arc::new(std::sync::atomic::AtomicUsize::new(0));
        let job_ref = job2.clone();
        let total = total_spawn;
        let outcome = crate::service::export_book::fetch_chapters_concurrent(
            chapters,
            4,
            move |_i, chapter_url| {
                let storage = storage.clone();
                let ns = ns.clone();
                let book_url = book_url.clone();
                let src = src.clone();
                let book_name = book_name.clone();
                let counter = counter.clone();
                let job_ref = job_ref.clone();
                async move {
                    let idx = crate::util::md5::chapter_url_hash(&chapter_url);
                    let r = match storage
                        .get_chapter_content(&ns, &book_url, idx)
                        .await
                        .ok()
                        .flatten()
                    {
                        Some(c) if !c.trim().is_empty() => Ok(c),
                        _ => crate::service::book::analyze_content(
                            &ns,
                            &chapter_url,
                            &src,
                            5,
                            None,
                            Some(&book_name),
                            &book_url,
                        )
                        .await
                        .map_err(|e| format!("{e:#}"))
                        .map(|content| {
                            if storage.config.cache_chapter_content {
                                let _ = storage
                                    .cache_chapter_content(&ns, &book_url, idx, "", &content);
                            }
                            content
                        }),
                    };
                    let d = counter.fetch_add(1, std::sync::atomic::Ordering::Relaxed) + 1;
                    if let Ok(mut j) = job_ref.lock() {
                        j.done = d;
                    }
                    r
                }
            },
        )
        .await;
        let mut j = job2.lock().unwrap_or_else(|e| e.into_inner());
        j.done = total.min(j.done.max(0));
        if j.cancelled {
            j.status = "error".into();
            j.error = Some("已取消".into());
            return;
        }
        j.failed = outcome
            .failed
            .iter()
            .map(|f| json!({"index": f.index, "title": f.title, "error": "抓取失败"}))
            .collect();
        if outcome.chapters.is_empty() {
            j.status = "error".into();
            j.error = Some("全部章节抓取失败".into());
            return;
        }
        // 打包
        let export_chapters: Vec<crate::service::export_book::ExportChapter> = outcome
            .chapters
            .iter()
            .map(|(t, c)| crate::service::export_book::ExportChapter {
                title: t.clone(),
                content: c.clone(),
            })
            .collect();
        let (bytes, ext): (Vec<u8>, &str) = match format2.as_str() {
            "epub" => {
                let meta = crate::service::export_book::EpubMeta {
                    description: intro.clone(),
                    language: Some("zh-CN".into()),
                    ..Default::default()
                };
                (
                    crate::service::export_book::build_epub_full(
                        &title2,
                        &author2,
                        &meta,
                        &export_chapters,
                    ),
                    "epub",
                )
            }
            _ => (
                crate::service::export_book::build_txt(&title2, &export_chapters).into_bytes(),
                "txt",
            ),
        };
        let dir = export_dir(&st, &ns2);
        if let Err(e) = std::fs::create_dir_all(&dir) {
            j.status = "error".into();
            j.error = Some(format!("创建导出目录失败: {e}"));
            return;
        }
        let safe_title: String = title2
            .chars()
            .map(|c| match c {
                '/' | '\\' | ':' | '*' | '?' | '"' | '<' | '>' | '|' => '_',
                c => c,
            })
            .take(60)
            .collect();
        let filename = format!("《{safe_title}》作者-{author2}.{ext}");
        let path = dir.join(format!(
            "{}-{task_id_spawn}.{ext}",
            uuid::Uuid::new_v4().simple()
        ));
        if let Err(e) = std::fs::write(&path, &bytes) {
            j.status = "error".into();
            j.error = Some(format!("写文件失败: {e}"));
            return;
        }
        j.status = "done".into();
        j.file = Some((path, filename));
        tracing::info!(
            "[bot:{ns2}] 下载任务 {task_id_spawn} 完成《{title2}》{} 章 / {} 失败",
            outcome.chapters.len(),
            j.failed.len()
        );
    });

    tracing::info!(
        "[bot:{ns}] 下载任务 {task_id} 启动《{title}》{} 章 → {format}",
        total_spawn
    );
    ok(json!({
        "taskId": task_id,
        "status": "running",
        "title": title,
        "total": total_spawn,
        "totalAll": total_all,
        "format": format,
        "poll": format!("/bot/v1/jobs/{task_id}"),
    }))
}

/// GET /bot/v1/jobs —— 最近任务列表（仅本命名空间任务）
async fn bot_jobs_list(
    State(_state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
) -> Response {
    let Ok((ns, ..)) = auth(&headers, &params) else {
        return err(StatusCode::UNAUTHORIZED, "unauthorized", "无效 API Key");
    };
    let table = JOB_TABLE.lock().unwrap_or_else(|e| e.into_inner());
    let mut list = Vec::new();
    for id in table.order.iter().rev() {
        let Some(j) = table.map.get(id) else {
            continue;
        };
        let jg = j.lock().unwrap_or_else(|e| e.into_inner());
        if jg.ns != ns {
            continue; // 任务归属隔离：不跨命名空间展示
        }
        if list.len() >= 50 {
            break;
        }
        list.push(jg.summary(id));
    }
    ok(json!({"jobs": list}))
}

/// GET /bot/v1/jobs/{taskId} —— 任务状态；POST = 取消。
/// 任务归属校验：任务 j.ns != 请求 ns 时按不存在处理（404）——
/// taskId 为 UUID 可枚举性低，但不做校验会跨命名空间泄露任务元数据与产物文件。
async fn bot_job_status(
    State(_state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    Path(task_id): Path<String>,
) -> Response {
    let Ok((ns, ..)) = auth(&headers, &params) else {
        return err(StatusCode::UNAUTHORIZED, "unauthorized", "无效 API Key");
    };
    let table = JOB_TABLE.lock().unwrap_or_else(|e| e.into_inner());
    match table.map.get(&task_id) {
        Some(j) => {
            let jg = j.lock().unwrap_or_else(|e| e.into_inner());
            if jg.ns != ns {
                return err(StatusCode::NOT_FOUND, "not_found", "任务不存在");
            }
            ok(jg.summary(&task_id))
        }
        None => err(
            StatusCode::NOT_FOUND,
            "not_found",
            "任务不存在（重启后任务表清空，文件保留）",
        ),
    }
}

async fn bot_job_cancel(
    State(_state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    Path(task_id): Path<String>,
) -> Response {
    let Ok((ns, ..)) = auth(&headers, &params) else {
        return err(StatusCode::UNAUTHORIZED, "unauthorized", "无效 API Key");
    };
    let table = JOB_TABLE.lock().unwrap_or_else(|e| e.into_inner());
    match table.map.get(&task_id) {
        Some(j) => {
            let mut jg = j.lock().unwrap_or_else(|e| e.into_inner());
            if jg.ns != ns {
                return err(StatusCode::NOT_FOUND, "not_found", "任务不存在");
            }
            match jg.status.as_str() {
                "running" => {
                    jg.cancelled = true;
                    ok(
                        json!({"taskId": task_id, "status": "cancelling", "note": "任务将在当前批次章节完成后终止"}),
                    )
                }
                s => err(
                    StatusCode::CONFLICT,
                    "conflict",
                    format!("任务已结束（{s}），无法取消"),
                ),
            }
        }
        None => err(StatusCode::NOT_FOUND, "not_found", "任务不存在"),
    }
}

/// GET /bot/v1/jobs/{taskId}/file —— 下载打包文件（含任务归属 ns 校验）
async fn bot_job_file(
    State(_state): State<AppState>,
    Query(params): Query<HashMap<String, String>>,
    headers: HeaderMap,
    Path(task_id): Path<String>,
) -> Response {
    let Ok((ns, ..)) = auth(&headers, &params) else {
        return err(StatusCode::UNAUTHORIZED, "unauthorized", "无效 API Key");
    };
    // 克隆句柄后立即释放表锁——避免 fs::read 期间阻塞其它任务端点
    let job = {
        let table = JOB_TABLE.lock().unwrap_or_else(|e| e.into_inner());
        match table.map.get(&task_id) {
            Some(j) if j.lock().unwrap_or_else(|e| e.into_inner()).ns == ns => j.clone(),
            _ => return err(StatusCode::NOT_FOUND, "not_found", "任务不存在"),
        }
    };
    let j = job.lock().unwrap_or_else(|e| e.into_inner());
    match (j.status.as_str(), &j.file) {
        ("done", Some((path, filename))) => {
            let bytes = match std::fs::read(path) {
                Ok(b) => b,
                Err(e) => return err(StatusCode::GONE, "gone", format!("文件已被清理: {e}")),
            };
            let ct = if j.format == "epub" {
                "application/epub+zip"
            } else {
                "text/plain; charset=utf-8"
            };
            let ext = if j.format == "epub" { "epub" } else { "txt" };
            let ascii_fallback = format!("bot-export-{task_id}.{ext}");
            let disp = format!(
                "attachment; filename=\"{ascii_fallback}\"; filename*=UTF-8''{}",
                urlencode(filename)
            );
            (
                StatusCode::OK,
                [
                    (
                        axum::http::header::CONTENT_TYPE,
                        ct.parse().unwrap_or(axum::http::HeaderValue::from_static(
                            "application/octet-stream",
                        )),
                    ),
                    (
                        axum::http::header::CONTENT_DISPOSITION,
                        axum::http::HeaderValue::from_str(&disp)
                            .unwrap_or(axum::http::HeaderValue::from_static("attachment")),
                    ),
                ],
                Bytes::from(bytes),
            )
                .into_response()
        }
        ("running", _) => err(
            StatusCode::CONFLICT,
            "running",
            "任务仍在进行，请轮询 /bot/v1/jobs/{taskId} 等待 done",
        ),
        ("error", _) => err(
            StatusCode::CONFLICT,
            "error",
            j.error.clone().unwrap_or_else(|| "任务失败".into()),
        ),
        _ => err(StatusCode::CONFLICT, "conflict", "任务无产物文件"),
    }
}
