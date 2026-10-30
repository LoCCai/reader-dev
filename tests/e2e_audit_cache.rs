//! Independent three-round acceptance: real router/HTTP and private synthetic data.
//! No production database, shelf, source, credentials, or service is accessed.
use std::{io::{Cursor, Read}, sync::{Arc, atomic::{AtomicUsize, Ordering}}, time::Duration};
use axum::{routing::get, Router};
use reader_dev::{AppConfig, model::{Book, BookChapter, BookSource, User}, storage::Storage};
use serde_json::{json, Value};

mod common;

struct Fixture {
    storage: Storage,
    base: String,
    client: reqwest::Client,
    task: tokio::task::JoinHandle<()>,
    dir: std::path::PathBuf,
}

impl Fixture {
    async fn new() -> Self {
        let dir = std::env::temp_dir().join(format!("reader-independent-audit-{}", uuid::Uuid::new_v4()));
        let mut config = AppConfig::from_env();
        config.work_dir = dir.to_string_lossy().into_owned();
        config.secure = true;
        config.secure_key = String::new();
        config.token_ttl_days = 0;
        let storage = reader_dev::storage::init(&config).await.unwrap();
        for ns in ["auditA", "auditB"] {
            storage.insert_user(&User { username: ns.into(), user_namespace: ns.into(),
                token: "synthetic-audit-token".into(), enable_book_source: true,
                enable_local_store: true, ..Default::default() }).await.unwrap();
        }
        let app = reader_dev::api::router(config, storage.clone());
        let listener = tokio::net::TcpListener::bind("127.0.0.1:0").await.unwrap();
        let base = format!("http://{}", listener.local_addr().unwrap());
        let task = tokio::spawn(async move { axum::serve(listener, app).await.unwrap(); });
        Self { storage, base, client: reqwest::Client::builder().timeout(Duration::from_secs(10)).build().unwrap(), task, dir }
    }
    async fn get(&self, ns: &str, endpoint: &str, params: &[(&str, &str)]) -> reqwest::Response {
        self.client.get(format!("{}/reader3/{endpoint}", self.base))
            .header("accessToken", format!("{ns}:synthetic-audit-token"))
            .query(params).send().await.unwrap()
    }
    async fn json(&self, ns: &str, endpoint: &str, params: &[(&str, &str)]) -> Value {
        self.get(ns, endpoint, params).await.json().await.unwrap()
    }
    async fn post(&self, ns: &str, endpoint: &str, data: Value) -> Value {
        self.client.post(format!("{}/reader3/{endpoint}", self.base))
            .header("accessToken", format!("{ns}:synthetic-audit-token"))
            .json(&data).send().await.unwrap().json().await.unwrap()
    }
    async fn book(&self, ns: &str, url: &str, origin: &str, toc: &str) {
        self.storage.upsert_book(ns, &Book { book_url: url.into(), name: "验收 <书>&".into(),
            origin: origin.into(), toc_url: toc.into(), ..Default::default() }).await.unwrap();
    }
    async fn close(self) {
        self.task.abort();
        self.storage.pool.close().await;
        std::fs::remove_dir_all(self.dir).unwrap();
    }
}

#[tokio::test]
async fn audit_r1_clear_cache_scopes_users_and_preserves_primary_local_content() {
    let f = Fixture::new().await;
    for ns in ["auditA", "auditB"] {
        for (url, origin) in [("https://fixture.invalid/shared", "fixture-source"),
            ("local://same-local.txt", "local"), ("storage/local.epub", "loc_book"),
            ("https://fixture.invalid/imported", "loc_book")] {
            f.book(ns, url, origin, url).await;
            f.storage.cache_chapter_content(ns, url, 0, ns, &format!("{ns} body")).await.unwrap();
        }
        f.storage.cache_toc(ns, "https://fixture.invalid/shared", "https://fixture.invalid/toc", "[]").await.unwrap();
    }
    let info = f.json("auditA", "getCacheInfo", &[]).await;
    assert_eq!(info["data"]["tocCacheCount"], 1, "{info}");
    assert_eq!(info["data"]["chapterCount"], 1, "statistics must reflect exactly the disposable scope: {info}");
    let anonymous: Value = f.client.post(format!("{}/reader3/clearCache", f.base))
        .json(&json!({"type":"all"})).send().await.unwrap().json().await.unwrap();
    assert_eq!(anonymous["isSuccess"], false);
    let cleared = f.post("auditA", "clearCache", json!({"type":"all"})).await;
    assert_eq!(cleared["data"]["deletedChapters"], 1, "{cleared}");
    assert_eq!(cleared["data"]["deletedToc"], 1, "{cleared}");
    assert_eq!(f.storage.count_chapters("auditA", "https://fixture.invalid/shared").await.unwrap(), 0);
    assert_eq!(f.storage.count_chapters("auditB", "https://fixture.invalid/shared").await.unwrap(), 1);
    for url in ["local://same-local.txt", "storage/local.epub", "https://fixture.invalid/imported"] {
        let refused = f.post("auditA", "deleteBookCache", json!({"url":url})).await;
        assert_eq!(refused["isSuccess"], false, "local primary data is not disposable cache: {refused}");
        for ns in ["auditA", "auditB"] {
            assert_eq!(f.storage.get_chapter_content(ns, url, 0).await.unwrap(), Some(format!("{ns} body")));
        }
    }
    let b = f.json("auditB", "getCacheInfo", &[]).await;
    assert_eq!(b["data"]["tocCacheCount"], 1);
    assert_eq!(b["data"]["chapterCount"], 1);
    f.close().await;
}

#[tokio::test]
async fn audit_r2_expired_toc_hash_rows_page_and_export_in_directory_order() {
    let f = Fixture::new().await;
    let url = "https://fixture.invalid/hash-order";
    let toc_url = "https://fixture.invalid/different-toc";
    f.book("auditA", url, "fixture-source", toc_url).await;
    let mut toc = vec![BookChapter { title: "卷标题不可算章节".into(), url: "volume".into(), is_volume: true, ..Default::default() }];
    for i in 0..205 {
        let ch_url = format!("https://fixture.invalid/chapter/{i}");
        toc.push(BookChapter { title: format!("CH-{i:03}"), url: ch_url.clone(), index: i, ..Default::default() });
        let cached_title = if i == 0 { String::new() } else { format!("CH-{i:03}") };
        f.storage.cache_chapter_content("auditA", url, reader_dev::util::md5::chapter_url_hash(&ch_url),
            &cached_title, &format!("BODY-{i:03} <>&")).await.unwrap();
    }
    // Mirrors getBookToc's actual legacy write key, and makes fetch TTL stale.
    f.storage.cache_toc("auditA", toc_url, toc_url, &serde_json::to_string(&toc).unwrap()).await.unwrap();
    sqlx::query("UPDATE toc_cache SET updated_at = 1 WHERE user_namespace = 'auditA'")
        .execute(&f.storage.pool).await.unwrap();
    let first = f.json("auditA", "getBookCacheChapters", &[("url",url)]).await;
    assert_eq!(first["isSuccess"], true, "{first}");
    let page = first["data"]["chapters"].as_array().unwrap();
    assert_eq!(page.len(), 200);
    assert_eq!(first["data"]["hasMore"], true);
    assert_eq!(first["data"]["nextFrom"], 200);
    for (i, row) in page.iter().enumerate() {
        assert_eq!(row["index"], i as i64);
        assert_eq!(row["title"], format!("CH-{i:03}"));
    }
    let last = f.json("auditA", "getBookCacheChapters", &[("url",url),("from","200")]).await;
    assert_eq!(last["data"]["chapters"].as_array().unwrap().len(), 5);
    assert_eq!(last["data"]["hasMore"], false);
    let narrow = f.json("auditA", "getBookCacheChapters", &[("url",url),("from","2"),("to","3")]).await;
    assert_eq!(narrow["data"]["chapters"][0]["content"], "BODY-002 <>&");
    for (from, to) in [("-1","2"),("5","2"),("NaN","3")] {
        assert_eq!(f.json("auditA", "getBookCacheChapters", &[("url",url),("from",from),("to",to)]).await["isSuccess"], false);
    }
    let txt = f.get("auditA", "exportToTxt", &[("bookUrl",url)]).await.text().await.unwrap();
    let positions: Vec<_> = (0..205).map(|i| txt.find(&format!("BODY-{i:03}")).expect("export missing body")).collect();
    assert!(positions.windows(2).all(|w| w[0] < w[1]), "TXT order follows directory, not URL hash");
    assert!(!txt.contains("卷标题不可算章节"));
    let epub = f.get("auditA", "exportToEpub", &[("bookUrl",url)]).await.bytes().await.unwrap();
    let mut archive = zip::ZipArchive::new(Cursor::new(epub)).unwrap();
    let mut mime = String::new();
    archive.by_name("mimetype").unwrap().read_to_string(&mut mime).unwrap();
    assert_eq!(mime, "application/epub+zip");
    let mut opf = String::new();
    archive.by_name("OEBPS/content.opf").unwrap().read_to_string(&mut opf).unwrap();
    assert_eq!(opf.matches("<itemref ").count(), 205);
    for i in [0, 1, 102, 204] {
        let mut body = String::new();
        archive.by_name(&format!("OEBPS/chap_{i:04}.xhtml")).unwrap().read_to_string(&mut body).unwrap();
        assert!(body.contains(&format!("BODY-{i:03}")), "EPUB spine chapter {i} wrong");
        assert!(body.contains("&lt;&gt;&amp;"), "EPUB XML text must be escaped");
    }
    // R3: discarding the fetchable directory must not discard the order of
    // cached primary bodies or make an offline export silently empty.
    let clear_toc = f.post("auditA", "clearCache", json!({"type":"toc"})).await;
    assert_eq!(clear_toc["isSuccess"], true, "{clear_toc}");
    assert_eq!(f.storage.count_chapters("auditA", url).await.unwrap(), 205);
    let after_clear = f.json("auditA", "getBookCacheChapters", &[("url",url),("from","2"),("to","3")]).await;
    assert_eq!(after_clear["data"]["chapters"].as_array().unwrap().len(), 2, "TOC-only clear must preserve usable body order: {after_clear}");
    assert_eq!(after_clear["data"]["chapters"][0]["index"], 2);
    let all = f.json("auditA", "getAllContents", &[("bookUrl",url)]).await;
    assert_eq!(all["data"]["cachedCount"], 205, "{all}");
    assert_eq!(all["data"]["chapters"][204]["content"], "BODY-204 <>&");
    let txt_after = f.get("auditA", "exportToTxt", &[("bookUrl",url)]).await.text().await.unwrap();
    assert_eq!(txt, txt_after, "directory-only clearing must preserve identical TXT export bytes");
    let epub_after = f.get("auditA", "exportToEpub", &[("bookUrl",url)]).await.bytes().await.unwrap();
    let mut archive_after = zip::ZipArchive::new(Cursor::new(epub_after)).unwrap();
    let mut last_body = String::new();
    archive_after.by_name("OEBPS/chap_0204.xhtml").unwrap().read_to_string(&mut last_body).unwrap();
    assert!(last_body.contains("BODY-204"));
    let forbidden = f.json("auditB", "getBookCacheChapters", &[("url",url)]).await;
    assert_eq!(forbidden["isSuccess"], false);
    let other_txt = f.get("auditB", "exportToTxt", &[("bookUrl",url)]).await.text().await.unwrap();
    assert!(!other_txt.contains("BODY-"), "another user must not receive cached bodies");
    f.close().await;
}

#[tokio::test(flavor = "multi_thread", worker_threads = 2)]
async fn audit_r2_cancellation_during_inflight_fetch_does_not_write_and_is_user_scoped() {
    let _private = common::PrivateNetGuard::on();
    let f = Fixture::new().await;
    let hits = Arc::new(AtomicUsize::new(0));
    let gate = Arc::new(tokio::sync::Semaphore::new(0));
    let h = hits.clone();
    let g = gate.clone();
    let site = Router::new().route("/toc", get(|| async {
        "<ol><li><a href='/chapter/1'>One</a></li><li><a href='/chapter/2'>Two</a></li><li><a href='/chapter/3'>Three</a></li></ol>"
    })).route("/chapter/:id", get(move || { let h=h.clone(); let g=g.clone(); async move {
        h.fetch_add(1, Ordering::SeqCst);
        let permit = g.acquire().await.unwrap();
        permit.forget();
        "<div id='content'>DELAYED-BODY</div>"
    }}));
    let listener = tokio::net::TcpListener::bind("127.0.0.1:0").await.unwrap();
    let base = format!("http://{}", listener.local_addr().unwrap());
    let site_task = tokio::spawn(async move { axum::serve(listener, site).await.unwrap(); });
    let src: BookSource = serde_json::from_value(json!({"bookSourceUrl":base,"bookSourceName":"private fixture","enabled":true,
        "ruleToc":{"chapterList":"ol@li","chapterName":"a@text","chapterUrl":"a@href"},
        "ruleContent":{"content":"#content@text"}})).unwrap();
    let url = format!("{base}/book");
    let toc = format!("{base}/toc");
    for ns in ["auditA", "auditB"] {
        f.book(ns, &url, &base, &toc).await;
        f.storage.save_book_source(ns, &src).await.unwrap();
    }
    let started = f.post("auditA", "cacheBookRangeOnServer", json!({"url":url,"from":0,"to":2})).await;
    assert_eq!(started["isSuccess"], true, "{started}");
    let key = started["data"]["taskId"].as_str().unwrap();
    let p = reader_dev::service::cache_job::progress_of_key("auditA", key).unwrap();
    tokio::time::timeout(Duration::from_secs(5), async {
        while hits.load(Ordering::SeqCst) < 3 { tokio::time::sleep(Duration::from_millis(10)).await; }
    }).await.expect("all three fixture requests must be in flight");
    let cross_cancel = f.json("auditB", "cancelCacheBook", &[("taskId",key)]).await;
    assert_eq!(cross_cancel["data"]["cancelled"], false, "another user's exact taskId must not cancel: {cross_cancel}");
    assert!(!p.lock().unwrap().cancelled);
    let cancelled = f.json("auditA", "cancelCacheBook", &[("taskId",key)]).await;
    assert_eq!(cancelled["data"]["cancelled"], true);
    gate.add_permits(3);
    tokio::time::timeout(Duration::from_secs(5), async {
        while !p.lock().unwrap().finished { tokio::time::sleep(Duration::from_millis(10)).await; }
    }).await.expect("cancelled job must settle promptly");
    assert_eq!(f.storage.count_chapters("auditA", &url).await.unwrap(), 0, "no cancelled in-flight body may be committed");
    assert_eq!(f.storage.count_chapters("auditB", &url).await.unwrap(), 0);
    // A new generation of the same range needs a new external taskId. A delayed
    // cancellation from the previous dialog must never cancel the new run.
    let next = f.post("auditA", "cacheBookRangeOnServer", json!({"url":url,"from":0,"to":2})).await;
    assert_eq!(next["isSuccess"], true, "{next}");
    let next_key = next["data"]["taskId"].as_str().unwrap();
    assert_ne!(next_key, key, "taskId must identify one operation generation");
    let next_progress = reader_dev::service::cache_job::progress_of_key("auditA", next_key).unwrap();
    let obsolete = f.json("auditA", "cancelCacheBook", &[("taskId",key)]).await;
    assert_eq!(obsolete["data"]["cancelled"], false, "obsolete cancellation cannot target the replacement run: {obsolete}");
    assert!(!next_progress.lock().unwrap().cancelled);
    gate.add_permits(3);
    tokio::time::timeout(Duration::from_secs(5), async {
        while !next_progress.lock().unwrap().finished { tokio::time::sleep(Duration::from_millis(10)).await; }
    }).await.expect("replacement generation must complete");
    assert_eq!(f.storage.count_chapters("auditA", &url).await.unwrap(), 3);
    site_task.abort();
    f.close().await;
}

#[tokio::test]
async fn audit_r3_original_epub_file_requires_shelf_and_own_namespace_path() {
    let f = Fixture::new().await;
    let own_root = f.storage.config.storage_dir().join("data/auditA");
    let other_root = f.storage.config.storage_dir().join("data/auditB");
    std::fs::create_dir_all(&own_root).unwrap();
    std::fs::create_dir_all(&other_root).unwrap();
    let file = own_root.join("private-original.epub");
    let original = b"PK\x03\x04synthetic-original-file-private-to-auditA";
    std::fs::write(&file, original).unwrap();
    let url = "local://audit/original.epub";
    f.storage.upsert_book("auditA", &Book { book_url: url.into(), name: "private original".into(),
        origin: "local".into(), local_file: Some(file.to_string_lossy().into_owned()), ..Default::default() }).await.unwrap();
    let response = f.get("auditA", "getBookFile", &[("url",url)]).await;
    assert_eq!(response.headers()["content-type"], "application/epub+zip");
    assert_eq!(response.headers()["cache-control"], "private, no-store");
    assert_eq!(response.bytes().await.unwrap().as_ref(), original);
    let forbidden = f.json("auditB", "getBookFile", &[("url",url)]).await;
    assert_eq!(forbidden["isSuccess"], false, "no shelf ownership: {forbidden}");
    // An editable local_file must not turn a shelf entry into arbitrary file access.
    f.storage.upsert_book("auditB", &Book { book_url: url.into(), name: "forged original".into(),
        origin: "local".into(), local_file: Some(file.to_string_lossy().into_owned()), ..Default::default() }).await.unwrap();
    let forged = f.json("auditB", "getBookFile", &[("url",url)]).await;
    assert_eq!(forged["isSuccess"], false, "cross-namespace local_file path: {forged}");
    #[cfg(unix)] {
        let link = other_root.join("private-link.epub");
        std::os::unix::fs::symlink(&file, &link).unwrap();
        f.storage.upsert_book("auditB", &Book { book_url: url.into(), name: "symlink original".into(),
            origin: "local".into(), local_file: Some(link.to_string_lossy().into_owned()), ..Default::default() }).await.unwrap();
        let symlink = f.json("auditB", "getBookFile", &[("url",url)]).await;
        assert_eq!(symlink["isSuccess"], false, "symlink escape must not cross user directory: {symlink}");
    }
    let anonymous: Value = f.client.get(format!("{}/reader3/getBookFile", f.base))
        .query(&[("url",url)]).send().await.unwrap().json().await.unwrap();
    assert_eq!(anonymous["isSuccess"], false);
    f.close().await;
}
