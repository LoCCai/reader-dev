//! Opt-in live-source acceptance. Fresh isolated storage and loopback listener;
//! the private fixture is supplied locally and is never printed or committed.
use reader_dev::{AppConfig, model::{Book, BookSource}};
use serde_json::{json, Value};
use std::time::Duration;

#[tokio::test]
#[ignore = "requires private live-source fixture and external network"]
async fn qweb_real_book_bodies_after_rule_repair() {
    let fixture = std::env::var("READER_AUDIT_PRIVATE_FIXTURE").expect("private fixture path");
    let output = std::env::var("READER_AUDIT_RESULT").expect("sanitized result path");
    let rows: Vec<Value> = serde_json::from_slice(&std::fs::read(fixture).unwrap()).unwrap();
    let dir = std::env::temp_dir().join(format!("reader-qweb-live-{}", uuid::Uuid::new_v4()));
    let mut config = AppConfig::from_env();
    config.work_dir = dir.to_string_lossy().into_owned();
    config.secure = false;
    config.secure_key.clear();
    let storage = reader_dev::storage::init(&config).await.unwrap();
    let listener = tokio::net::TcpListener::bind("127.0.0.1:0").await.unwrap();
    let base = format!("http://{}/reader3", listener.local_addr().unwrap());
    let app = reader_dev::api::router(config, storage.clone());
    let server = tokio::spawn(async move { axum::serve(listener, app).await.unwrap() });
    let client = reqwest::Client::builder().timeout(Duration::from_secs(80)).build().unwrap();
    let mut reports = Vec::new();
    for row in rows {
        let source: BookSource = serde_json::from_value(row["source"].clone()).unwrap();
        let book: Book = serde_json::from_value(row["book"].clone()).unwrap();
        storage.save_book_source("default", &source).await.unwrap();
        storage.upsert_book("default", &book).await.unwrap();
        let request = client.post(format!("{base}/getChapterList")).json(&json!({
            "url": if book.toc_url.is_empty() { &book.book_url } else { &book.toc_url },
            "bookUrl": book.book_url, "bookSource": source.book_source_url, "refresh": false
        })).send().await;
        let toc: Value = match request {
            Ok(response) => response.json().await.unwrap_or(Value::Null),
            Err(_) => Value::Null,
        };
        let chapters = toc["data"].as_array();
        let chapter = chapters.and_then(|rows| rows.iter().find(|c| !c["isVolume"].as_bool().unwrap_or(false)));
        let mut report = json!({"name": book.name, "source_name": source.book_source_name,
            "toc_ok": toc["isSuccess"] == true,
            "chapter_count": chapters.map_or(0, Vec::len), "body_ok": false});
        if let Some(chapter) = chapter {
            let response = client.post(format!("{base}/getBookContent")).json(&json!({
                "url": chapter["url"], "bookUrl": book.book_url,
                "bookSource": source.book_source_url,
                "index": chapter["index"], "refresh": true, "cache": 1
            })).send().await;
            let body: Value = match response {
                Ok(response) => response.json().await.unwrap_or(Value::Null),
                Err(_) => Value::Null,
            };
            let text = body["data"]["content"].as_str().or_else(|| body["data"].as_str()).unwrap_or("");
            let cjk = text.chars().filter(|c| ('\u{4e00}'..='\u{9fff}').contains(c)).count();
            let literal_regex = source.rule_content.as_ref().and_then(|r| r["replaceRegex"].as_str())
                .and_then(|r| r.strip_prefix("##")).map_or(false, |r| text.starts_with(r));
            report["body_ok"] = json!(body["isSuccess"] == true && cjk >= 100 && !literal_regex);
            report["content_chars"] = json!(text.chars().count());
            report["cjk_chars"] = json!(cjk);
            report["regex_artifact"] = json!(literal_regex);
        }
        reports.push(report);
    }
    std::fs::write(output, serde_json::to_vec_pretty(&reports).unwrap()).unwrap();
    server.abort();
    storage.pool.close().await;
    std::fs::remove_dir_all(dir).unwrap();
    // Live availability is recorded per source; a parser regression must not
    // silently pass when a source did return a substantial chapter.
    for report in &reports {
        assert_eq!(report["regex_artifact"].as_bool().unwrap_or(false), false);
    }
}
