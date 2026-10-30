//! Isolated, opt-in HTTP memory/latency probe. Never point this at production.
//! Prepare once, then run each read in a fresh process; see tests/audit/README.md.
use std::{path::Path, time::{Duration, Instant}};
use reader_dev::{AppConfig, model::{Book, BookChapter, User}};
use serde_json::{json, Value};

const BOOK: &str = "https://fixture.invalid/paging-profile";
const TOC: &str = "https://fixture.invalid/paging-profile/toc";
const NS: &str = "paging-profile";
const MARKER: &str = "audit-cache-paging-fixture.json";
const CHAPTERS: usize = 4096;
const BODY_BYTES: usize = 65536;

fn config(dir: &Path) -> AppConfig {
    let mut config = AppConfig::from_env();
    config.work_dir = dir.to_string_lossy().into_owned();
    config.secure = true;
    config.secure_key = String::new();
    config.token_ttl_days = 0;
    config
}

fn rss_kib(field: &str) -> Option<u64> {
    std::fs::read_to_string("/proc/self/status").ok()?.lines()
        .find_map(|line| line.strip_prefix(field).and_then(|v| v.split_whitespace().next()?.parse().ok()))
}

#[tokio::main(flavor = "multi_thread", worker_threads = 2)]
async fn main() -> anyhow::Result<()> {
    let args: Vec<String> = std::env::args().collect();
    let mode = args.get(1).map(String::as_str).unwrap_or("");
    let dir = std::path::PathBuf::from(args.get(2).ok_or_else(|| anyhow::anyhow!(
        "usage: audit_cache_paging prepare <new-dir> | read <fixture-dir> <from> <to> <toc|no-toc>"
    ))?);
    if mode == "prepare" {
        anyhow::ensure!(!dir.exists(), "prepare requires a new, isolated directory");
        std::fs::create_dir_all(&dir)?;
        let storage = reader_dev::storage::init(&config(&dir)).await?;
        storage.insert_user(&User { username: NS.into(), user_namespace: NS.into(),
            token: "synthetic-profile-token".into(), enable_book_source: true,
            ..Default::default() }).await?;
        let urls = [BOOK.to_string(), format!("{BOOK}/no-toc")];
        for url in &urls {
            storage.upsert_book(NS, &Book { book_url: url.clone(), name: "分页合成基准".into(),
                origin: "synthetic-profile-source".into(), toc_url: if url == BOOK { TOC.into() } else { String::new() },
                ..Default::default() }).await?;
        }
        let mut toc = vec![BookChapter { title: "卷标题".into(), url: "volume".into(),
            is_volume: true, ..Default::default() }];
        let mut tx = storage.pool.begin().await?;
        for i in 0..CHAPTERS {
            let url = format!("https://fixture.invalid/profile/chapter/{i}");
            let title = format!("CH-{i:04}");
            toc.push(BookChapter { url: url.clone(), title: title.clone(), index: i as i64,
                ..Default::default() });
            let prefix = format!("BODY-{i:04} ");
            let content = format!("{prefix}{}", "x".repeat(BODY_BYTES - prefix.len()));
            for book_url in &urls {
                sqlx::query("INSERT INTO book_chapters (book_url, chapter_index, title, content, user_namespace, chapter_order) VALUES (?1,?2,?3,?4,?5,?6)")
                    .bind(book_url).bind(reader_dev::util::md5::chapter_url_hash(&url))
                    .bind(&title).bind(&content).bind(NS).bind(i as i64)
                    .execute(&mut *tx).await?;
            }
        }
        tx.commit().await?;
        // The alternate TOC key and expired timestamp exercise cache-only order.
        storage.cache_toc(NS, TOC, TOC, &serde_json::to_string(&toc)?).await?;
        sqlx::query("UPDATE toc_cache SET updated_at=1 WHERE user_namespace=?1")
            .bind(NS).execute(&storage.pool).await?;
        sqlx::query("PRAGMA wal_checkpoint(TRUNCATE)").execute(&storage.pool).await?;
        storage.pool.close().await;
        let meta = json!({"fixture":"reader-paging-synthetic-v1", "chaptersPerBook":CHAPTERS,
            "bodyBytesPerChapter":BODY_BYTES, "books":2, "namespaces":1});
        std::fs::write(dir.join(MARKER), serde_json::to_vec_pretty(&meta)?)?;
        println!("{meta}");
        return Ok(());
    }
    anyhow::ensure!(mode == "read", "unknown mode");
    let meta: Value = serde_json::from_slice(&std::fs::read(dir.join(MARKER))?)?;
    anyhow::ensure!(meta["fixture"] == "reader-paging-synthetic-v1", "not a synthetic fixture");
    let from = args.get(3).ok_or_else(|| anyhow::anyhow!("missing from"))?.parse::<usize>()?;
    let to = args.get(4).ok_or_else(|| anyhow::anyhow!("missing to"))?.parse::<usize>()?;
    anyhow::ensure!(from <= to && to < CHAPTERS, "range outside synthetic fixture");
    let toc_mode = args.get(5).map(String::as_str).unwrap_or("toc");
    anyhow::ensure!(toc_mode == "toc" || toc_mode == "no-toc", "invalid toc mode");
    let url = if toc_mode == "toc" { BOOK.to_string() } else { format!("{BOOK}/no-toc") };
    let init_started = Instant::now();
    let config = config(&dir);
    let storage = reader_dev::storage::init(&config).await?;
    let initialized_ms = init_started.elapsed().as_secs_f64() * 1000.0;
    let app = reader_dev::api::router(config, storage.clone());
    let listener = tokio::net::TcpListener::bind("127.0.0.1:0").await?;
    let addr = listener.local_addr()?;
    let task = tokio::spawn(async move { axum::serve(listener, app).await.unwrap(); });
    let before_rss = rss_kib("VmRSS:");
    let client = reqwest::Client::builder().no_proxy().timeout(Duration::from_secs(90)).build()?;
    let started = Instant::now();
    let bytes = client.get(format!("http://{addr}/reader3/getBookCacheChapters"))
        .header("accessToken", format!("{NS}:synthetic-profile-token"))
        .query(&[("url", url), ("from", from.to_string()), ("to", to.to_string())])
        .send().await?.error_for_status()?.bytes().await?;
    let request_ms = started.elapsed().as_secs_f64() * 1000.0;
    let reply: Value = serde_json::from_slice(&bytes)?;
    anyhow::ensure!(reply["isSuccess"] == true, "request failed: {reply}");
    let rows = reply["data"]["chapters"].as_array().ok_or_else(|| anyhow::anyhow!("missing chapters"))?;
    let expected_count = (to - from + 1).min(200);
    anyhow::ensure!(rows.len() == expected_count, "incorrect page length");
    for (offset, row) in rows.iter().enumerate() {
        let i = from + offset;
        anyhow::ensure!(row["index"] == i && row["title"] == format!("CH-{i:04}"), "incorrect order/title");
        let body = row["content"].as_str().ok_or_else(|| anyhow::anyhow!("missing content"))?;
        anyhow::ensure!(body.len() == BODY_BYTES && body.starts_with(&format!("BODY-{i:04} ")), "incorrect body");
    }
    let has_more = to - from + 1 > 200;
    anyhow::ensure!(reply["data"]["hasMore"] == has_more, "incorrect hasMore");
    anyhow::ensure!(reply["data"]["nextFrom"] == if has_more { json!(from + 200) } else { Value::Null }, "incorrect nextFrom");
    let result = json!({"mode":toc_mode, "from":from, "to":to, "returned":rows.len(),
        "initializeMs": initialized_ms, "requestMs":request_ms, "responseBytes":bytes.len(),
        "responseMd5":reader_dev::util::md5::md5_bytes_hex(&bytes),
        "beforeRequestRssKiB":before_rss, "peakRssKiB":rss_kib("VmHWM:"),
        "afterResponseRssKiB":rss_kib("VmRSS:"), "assertionsPassed":true});
    println!("{result}");
    task.abort();
    storage.pool.close().await;
    Ok(())
}
