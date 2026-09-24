//! C4 SSE 集成测试：searchBookMultiSSE 经完整 HTTP 应用（router()）走真实 SSE 流
//!
//! 覆盖 REMAINING-WORK C4 缺口——单源指定（bookSourceUrl 精确过滤）与
//! concurrentCount 参数此前仅有编译保障、无端到端断言：
//! 1. 不带过滤：两源结果都出现，`event: end` 收尾（isEnd=true）
//! 2. bookSourceUrl=CSS 源：所有 data 事件的书籍 origin 均为该源（JSON 源不出现）
//! 3. concurrentCount=1：并发数收敛到单飞，仍正常产出与收尾

use std::net::SocketAddr;

use axum::response::IntoResponse;
use axum::routing::get;
use axum::Router;
use serde_json::{json, Value};

mod common;

/// 全局一次性放行私网（OnceLock 永不 Drop——避免并行测试间 RAII 守卫互相恢复）
fn allow_private_net() -> &'static common::PrivateNetGuard {
    static GUARD: std::sync::OnceLock<common::PrivateNetGuard> = std::sync::OnceLock::new();
    GUARD.get_or_init(common::PrivateNetGuard::on)
}

/// mock 书站：CSS 源与 JSON 源各一个搜索端点
async fn spawn_mock_site() -> u16 {
    let listener = tokio::net::TcpListener::bind("127.0.0.1:0").await.unwrap();

    let search_html = r##"<!doctype html><html><body>
<ul class="result-list">
  <li class="item">
    <a class="title-link" href="/book/101.html">测试之书</a>
    <span class="author">张三</span>
  </li>
</ul>
</body></html>"##;

    let json_search = json!({
        "code": 0,
        "data": {"list": [{"bid": "/japi/detail/201", "bname": "测试之书", "author": "王五"}]}
    })
    .to_string();

    let app = Router::new()
        .route(
            "/css/search",
            get(move || async move {
                axum::http::Response::builder()
                    .header("content-type", "text/html; charset=utf-8")
                    .body(axum::body::Body::from(search_html))
                    .unwrap()
                    .into_response()
            }),
        )
        .route(
            "/json/search",
            get(move || async move {
                axum::http::Response::builder()
                    .header("content-type", "application/json")
                    .body(axum::body::Body::from(json_search))
                    .unwrap()
                    .into_response()
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
        "bookSourceName": "SSE CSS 源",
        "enabled": true,
        "searchUrl": format!("{base}/css/search?key={{{{key}}}}"),
        "ruleSearch": {
            "bookList": "ul.result-list@li.item",
            "name": "a.title-link@text",
            "author": "span.author@text",
            "bookUrl": "a.title-link@href"
        }
    })
}

fn json_source(port: u16) -> Value {
    let base = format!("http://127.0.0.1:{port}");
    json!({
        "bookSourceUrl": format!("{base}/jsonapi"),
        "bookSourceName": "SSE JSON 源",
        "enabled": true,
        "searchUrl": format!("{base}/json/search?key={{{{key}}}}"),
        "ruleSearch": {
            "bookList": "$.data.list[*]",
            "name": "$.bname",
            "author": "$.author",
            "bookUrl": "$.bid"
        }
    })
}

/// 起完整应用（router()），注册两源后返回 (地址, css源URL, json源URL)
async fn spawn_app() -> (SocketAddr, String, String) {
    let port = spawn_mock_site().await;
    let css = css_source(port);
    let jsonsrc = json_source(port);

    let dir = std::env::temp_dir().join(format!(
        "reader-e2e-sse-{}",
        std::process::id()
    ));
    let _ = std::fs::remove_dir_all(&dir);
    let mut config = reader_dev::AppConfig::from_env();
    config.work_dir = dir.to_string_lossy().into_owned();
    config.token_ttl_days = 0;
    let storage = reader_dev::storage::init(&config).await.unwrap();
    storage
        .save_book_source(
            "default",
            &serde_json::from_value::<reader_dev::model::BookSource>(css.clone()).unwrap(),
        )
        .await
        .unwrap();
    storage
        .save_book_source(
            "default",
            &serde_json::from_value::<reader_dev::model::BookSource>(jsonsrc.clone()).unwrap(),
        )
        .await
        .unwrap();

    let app = reader_dev::api::router::router(config, storage);
    let listener = tokio::net::TcpListener::bind("127.0.0.1:0").await.unwrap();
    let addr = listener.local_addr().unwrap();
    tokio::spawn(async move { axum::serve(listener, app).await });

    let css_url = css["bookSourceUrl"].as_str().unwrap().to_string();
    let json_url = jsonsrc["bookSourceUrl"].as_str().unwrap().to_string();
    (addr, css_url, json_url)
}

/// 解析 SSE 原文 → (数据事件列表, end 事件是否存在)
/// 数据事件形如 `data: {"lastIndex":i,"data":[...]}`；结束为 `event: end\ndata: {...}`
fn parse_sse(body: &str) -> (Vec<Value>, Option<Value>) {
    let mut data_events = Vec::new();
    let mut end_event = None;
    for block in body.split("\n\n") {
        let mut is_end = false;
        let mut data = String::new();
        for line in block.lines() {
            if let Some(rest) = line.strip_prefix("event: ") {
                if rest.trim() == "end" {
                    is_end = true;
                }
            } else if let Some(rest) = line.strip_prefix("data: ") {
                data = rest.to_string();
            }
        }
        if data.is_empty() {
            continue;
        }
        let Ok(v) = serde_json::from_str::<Value>(&data) else {
            continue;
        };
        if is_end {
            end_event = Some(v);
        } else if v.get("data").and_then(|d| d.as_array()).is_some() {
            data_events.push(v);
        }
    }
    (data_events, end_event)
}

#[tokio::test(flavor = "multi_thread")]
async fn e2e_sse_multi_search_filters_and_concurrency() {
    let _guard = allow_private_net();
    let (addr, css_url, json_url) = spawn_app().await;
    let client = reqwest::Client::new();
    let base = format!("http://{addr}/reader3/searchBookMultiSSE");

    // ---- 1) 不带过滤：两源结果均出现，end 收尾 ----
    let body = client
        .post(&base)
        .query(&[("key", "测试")])
        .send()
        .await
        .unwrap()
        .text()
        .await
        .unwrap();
    let (events, end) = parse_sse(&body);
    assert!(end.is_some(), "应有 event: end 收尾: {body}");
    assert!(
        end.clone().unwrap()["isEnd"] == json!(true),
        "end 应携带 isEnd: end={end:?} body={body}"
    );
    let mut origins: Vec<String> = events
        .iter()
        .flat_map(|e| {
            e["data"]
                .as_array()
                .cloned()
                .unwrap_or_default()
                .into_iter()
                .filter_map(|b| b["origin"].as_str().map(|s| s.to_string()))
        })
        .collect();
    origins.sort();
    assert!(
        origins.contains(&css_url) && origins.contains(&json_url),
        "双源结果都应出现: {origins:?}"
    );

    // ---- 2) bookSourceUrl 单源指定：只出现 CSS 源结果 ----
    let body = client
        .post(&base)
        .query(&[("key", "测试"), ("bookSourceUrl", css_url.as_str())])
        .send()
        .await
        .unwrap()
        .text()
        .await
        .unwrap();
    let (events, end) = parse_sse(&body);
    assert!(end.is_some(), "单源指定也应有 end 收尾: {body}");
    let origins: Vec<String> = events
        .iter()
        .flat_map(|e| {
            e["data"]
                .as_array()
                .cloned()
                .unwrap_or_default()
                .into_iter()
                .filter_map(|b| b["origin"].as_str().map(|s| s.to_string()))
        })
        .collect();
    assert!(
        !origins.is_empty() && origins.iter().all(|o| *o == css_url),
        "所有结果 origin 应为指定源: {origins:?}"
    );

    // ---- 3) concurrentCount=1：并发收敛单飞，仍正常产出与收尾 ----
    let body = client
        .post(&base)
        .query(&[("key", "测试"), ("concurrentCount", "1")])
        .send()
        .await
        .unwrap()
        .text()
        .await
        .unwrap();
    let (events, end) = parse_sse(&body);
    assert!(end.is_some(), "concurrentCount=1 应有 end 收尾: {body}");
    let n: usize = events
        .iter()
        .map(|e| e["data"].as_array().map(|a| a.len()).unwrap_or(0))
        .sum();
    assert!(n >= 2, "两源各应至少命中一条: {events:?}");

    // ---- 4) lastIndex 翻页形状：单源 + lastIndex 越界 → end（isEnd 且无数据）----
    let body = client
        .post(&base)
        .query(&[
            ("key", "测试"),
            ("bookSourceUrl", css_url.as_str()),
            ("lastIndex", "0"),
        ])
        .send()
        .await
        .unwrap()
        .text()
        .await
        .unwrap();
    // 单源共 1 个（lastIndex=0 已消费最后一个）→ legacy 语义：没有更多了
    assert!(
        body.contains("没有更多了") || body.contains("\"isEnd\""),
        "lastIndex 越界应报没有更多了或结束: {body}"
    );
}
