//! Cache-page HTTP contract against a private SQLite database and loopback router.
//! These fixtures never fetch a source, run a scheduler, or use a production shelf.
use std::{path::PathBuf, time::Duration};

use reader_dev::{
    model::{Book, BookChapter, User},
    storage::Storage,
    AppConfig,
};
use serde_json::{json, Value};

struct Fixture {
    storage: Storage,
    base: String,
    client: reqwest::Client,
    server: tokio::task::JoinHandle<()>,
    dir: PathBuf,
}

impl Fixture {
    async fn new() -> Self {
        let dir = std::env::temp_dir().join(format!(
            "reader-pagination-http-{}",
            uuid::Uuid::new_v4()
        ));
        let mut config = AppConfig::from_env();
        config.work_dir = dir.to_string_lossy().into_owned();
        config.secure = true;
        config.secure_key.clear();
        config.token_ttl_days = 0;
        let storage = reader_dev::storage::init(&config).await.unwrap();
        for ns in ["pageA", "pageB"] {
            storage
                .insert_user(&User {
                    username: ns.into(),
                    user_namespace: ns.into(),
                    token: "synthetic-page-token".into(),
                    enable_book_source: true,
                    enable_local_store: true,
                    ..Default::default()
                })
                .await
                .unwrap();
        }
        let app = reader_dev::api::router(config, storage.clone());
        let listener = tokio::net::TcpListener::bind("127.0.0.1:0")
            .await
            .unwrap();
        let base = format!("http://{}", listener.local_addr().unwrap());
        let server = tokio::spawn(async move { axum::serve(listener, app).await.unwrap() });
        Self {
            storage,
            base,
            client: reqwest::Client::builder()
                .no_proxy()
                .timeout(Duration::from_secs(10))
                .build()
                .unwrap(),
            server,
            dir,
        }
    }

    async fn book(&self, ns: &str, url: &str, origin: &str, toc_url: &str) {
        self.storage
            .upsert_book(
                ns,
                &Book {
                    book_url: url.into(),
                    name: "synthetic pagination book".into(),
                    origin: origin.into(),
                    toc_url: toc_url.into(),
                    ..Default::default()
                },
            )
            .await
            .unwrap();
    }

    async fn cache(&self, ns: &str, url: &str, key: i64, title: &str, body: &str) {
        self.storage
            .cache_chapter_content(ns, url, key, title, body)
            .await
            .unwrap();
    }

    async fn toc(&self, ns: &str, toc_url: &str, chapters: &[BookChapter]) {
        // getBookToc historically writes under the TOC URL rather than book URL.
        self.storage
            .cache_toc(
                ns,
                toc_url,
                toc_url,
                &serde_json::to_string(chapters).unwrap(),
            )
            .await
            .unwrap();
    }

    async fn page(&self, ns: &str, url: &str, bounds: &[(&str, &str)]) -> Value {
        let mut params = vec![("url", url)];
        params.extend_from_slice(bounds);
        self.client
            .get(format!("{}/reader3/getBookCacheChapters", self.base))
            .header("accessToken", format!("{ns}:synthetic-page-token"))
            .query(&params)
            .send()
            .await
            .unwrap()
            .json()
            .await
            .unwrap()
    }

    async fn post_page(&self, ns: &str, body: Value) -> Value {
        self.client
            .post(format!("{}/reader3/getBookCacheChapters", self.base))
            .header("accessToken", format!("{ns}:synthetic-page-token"))
            .json(&body)
            .send()
            .await
            .unwrap()
            .json()
            .await
            .unwrap()
    }

    async fn clear_toc(&self, ns: &str) {
        let response: Value = self
            .client
            .post(format!("{}/reader3/clearCache", self.base))
            .header("accessToken", format!("{ns}:synthetic-page-token"))
            .json(&json!({"type": "toc"}))
            .send()
            .await
            .unwrap()
            .json()
            .await
            .unwrap();
        assert_eq!(response["isSuccess"], true, "{response}");
    }

    async fn close(self) {
        self.server.abort();
        self.storage.pool.close().await;
        std::fs::remove_dir_all(self.dir).unwrap();
    }
}

fn chapter(url: String, title: String) -> BookChapter {
    BookChapter {
        url,
        title,
        // Directory positions come from non-volume order, not this legacy field.
        index: 90_000,
        ..Default::default()
    }
}

fn hash(url: &str) -> i64 {
    reader_dev::util::md5::chapter_url_hash(url)
}

fn assert_page(response: &Value, url: &str, indices: &[i64], has_more: bool, next: Option<i64>) {
    assert_eq!(response["isSuccess"], true, "{response}");
    assert_eq!(response["data"]["url"], url);
    let rows = response["data"]["chapters"].as_array().unwrap();
    let actual: Vec<_> = rows.iter().map(|row| row["index"].as_i64().unwrap()).collect();
    assert_eq!(actual, indices, "chapter indices must retain directory gaps");
    assert!(rows.len() <= 200);
    assert_eq!(response["data"]["hasMore"], has_more, "{response}");
    assert_eq!(response["data"]["nextFrom"], json!(next), "{response}");
}

#[tokio::test]
async fn sparse_toc_pages_use_real_indices_and_probe_only_inside_inclusive_range() {
    let f = Fixture::new().await;
    let url = "https://pagination.invalid/sparse-book";
    let toc_url = "https://pagination.invalid/sparse-toc";
    f.book("pageA", url, "synthetic-source", toc_url).await;
    let mut toc = Vec::new();
    for i in 0..630 {
        if i % 50 == 0 {
            toc.push(BookChapter {
                is_volume: true,
                title: format!("volume-{i}"),
                url: format!("volume-{i}"),
                ..Default::default()
            });
        }
        let ch_url = format!("https://pagination.invalid/sparse/{i}");
        toc.push(chapter(ch_url.clone(), format!("TOC-{i}")));
        if i % 3 == 1 {
            f.cache("pageA", url, hash(&ch_url), "cached title", &format!("BODY-{i}"))
                .await;
        }
    }
    f.toc("pageA", toc_url, &toc).await;
    sqlx::query("UPDATE toc_cache SET updated_at = 1 WHERE user_namespace = 'pageA'")
        .execute(&f.storage.pool)
        .await
        .unwrap();

    let first = f.page("pageA", url, &[]).await;
    assert_page(&first, url, &(1..=598).step_by(3).collect::<Vec<_>>(), true, Some(599));
    for row in first["data"]["chapters"].as_array().unwrap() {
        let i = row["index"].as_i64().unwrap();
        assert_eq!(row["title"], format!("TOC-{i}"));
        assert_eq!(row["content"], format!("BODY-{i}"));
    }
    let last = f.page("pageA", url, &[("from", "599")]).await;
    assert_page(&last, url, &(601..=628).step_by(3).collect::<Vec<_>>(), false, None);
    let exact = f.page("pageA", url, &[("from", "4"), ("to", "601")]).await;
    assert_page(&exact, url, &(4..=601).step_by(3).collect::<Vec<_>>(), false, None);
    let more = f.page("pageA", url, &[("from", "2"), ("to", "605")]).await;
    assert_page(&more, url, &(4..=601).step_by(3).collect::<Vec<_>>(), true, Some(602));
    let tail = f.page("pageA", url, &[("from", "602"), ("to", "605")]).await;
    assert_page(&tail, url, &[604], false, None);
    let to_only = f.page("pageA", url, &[("to", "4")]).await;
    assert_page(&to_only, url, &[1, 4], false, None);
    let gap = f.page("pageA", url, &[("from", "2"), ("to", "3")]).await;
    assert_page(&gap, url, &[], false, None);
    let outside = f.page("pageA", url, &[("from", "630")]).await;
    assert_page(&outside, url, &[], false, None);
    f.close().await;
}

#[tokio::test]
async fn duplicate_toc_urls_on_page_boundary_retain_each_position_and_title() {
    let f = Fixture::new().await;
    let url = "https://pagination.invalid/duplicate-book";
    let toc_url = "https://pagination.invalid/duplicate-toc";
    f.book("pageA", url, "synthetic-source", toc_url).await;
    let mut toc = Vec::new();
    for i in 0..202 {
        let ch_url = format!("https://pagination.invalid/duplicate/{}", if i == 200 { 199 } else { i });
        toc.push(chapter(ch_url.clone(), format!("POSITION-{i}")));
        f.cache("pageA", url, hash(&ch_url), "cached title", &format!("BODY-{}", if i == 200 { 199 } else { i }))
            .await;
    }
    f.toc("pageA", toc_url, &toc).await;
    assert_eq!(f.storage.count_chapters("pageA", url).await.unwrap(), 201);
    let first = f.page("pageA", url, &[]).await;
    assert_page(&first, url, &(0..200).collect::<Vec<_>>(), true, Some(200));
    assert_eq!(first["data"]["chapters"][199]["content"], "BODY-199");
    let last = f.page("pageA", url, &[("from", "200")]).await;
    assert_page(&last, url, &[200, 201], false, None);
    assert_eq!(last["data"]["chapters"][0]["title"], "POSITION-200");
    assert_eq!(last["data"]["chapters"][0]["content"], "BODY-199");
    let range = f.page("pageA", url, &[("from", "199"), ("to", "200")]).await;
    assert_page(&range, url, &[199, 200], false, None);
    assert_eq!(range["data"]["chapters"][0]["title"], "POSITION-199");
    assert_eq!(range["data"]["chapters"][1]["title"], "POSITION-200");

    f.clear_toc("pageA").await;
    let missing = f.page("pageA", url, &[("from", "0"), ("to", "0")]).await;
    assert_eq!(missing["isSuccess"], false, "{missing}");
    assert_eq!(missing["errorMsg"], "目录缓存缺失，请先刷新目录");
    assert_eq!(f.storage.count_chapters("pageA", url).await.unwrap(), 201);
    f.close().await;
}

#[tokio::test]
async fn local_and_imported_books_page_by_stored_sequential_keys_without_toc() {
    let f = Fixture::new().await;
    let url = "local://pagination/synthetic.txt";
    f.book("pageA", url, "local", "").await;
    for i in 0..205 {
        f.cache("pageA", url, i * 2, &format!("LOCAL-{i}"), &format!("LOCAL-BODY-{i}"))
            .await;
    }
    let first = f.page("pageA", url, &[]).await;
    assert_page(&first, url, &(0..400).step_by(2).collect::<Vec<_>>(), true, Some(399));
    let last = f.page("pageA", url, &[("from", "399")]).await;
    assert_page(&last, url, &[400, 402, 404, 406, 408], false, None);
    assert_eq!(last["data"]["chapters"][0]["content"], "LOCAL-BODY-200");
    let exact = f.page("pageA", url, &[("to", "398")]).await;
    assert_page(&exact, url, &(0..400).step_by(2).collect::<Vec<_>>(), false, None);

    for (other, origin) in [
        ("storage/synthetic.epub", "source-ignored"),
        ("https://pagination.invalid/imported", "loc_book"),
    ] {
        f.book("pageA", other, origin, "").await;
        for i in [2, 7, 9] {
            f.cache("pageA", other, i, &format!("IMPORTED-{i}"), &format!("IMPORTED-BODY-{i}"))
                .await;
        }
        let page = f.page("pageA", other, &[("from", "3"), ("to", "9")]).await;
        assert_page(&page, other, &[7, 9], false, None);
        assert_eq!(page["data"]["chapters"][0]["content"], "IMPORTED-BODY-7");
    }
    f.close().await;
}

#[tokio::test]
async fn no_toc_legacy_sequence_and_persisted_hash_positions_remain_pageable() {
    let f = Fixture::new().await;
    let legacy = "https://pagination.invalid/legacy-sequence";
    f.book("pageA", legacy, "synthetic-source", "").await;
    for i in 0..203 {
        f.cache("pageA", legacy, i * 3, &format!("OLD-{i}"), &format!("OLD-BODY-{i}"))
            .await;
    }
    // Simulate the pre-chapter_order schema's small, sequential cache keys.
    sqlx::query("UPDATE book_chapters SET chapter_order = NULL WHERE user_namespace = 'pageA' AND book_url = ?1")
        .bind(legacy).execute(&f.storage.pool).await.unwrap();
    let first = f.page("pageA", legacy, &[]).await;
    assert_page(&first, legacy, &(0..600).step_by(3).collect::<Vec<_>>(), true, Some(598));
    let last = f.page("pageA", legacy, &[("from", "598")]).await;
    assert_page(&last, legacy, &[600, 603, 606], false, None);

    let mixed = "https://pagination.invalid/persisted-order";
    f.book("pageA", mixed, "synthetic-source", "").await;
    for i in [3, 8] {
        f.cache("pageA", mixed, i, "legacy", &format!("LEGACY-{i}")).await;
    }
    sqlx::query("UPDATE book_chapters SET chapter_order = NULL WHERE user_namespace = 'pageA' AND book_url = ?1")
        .bind(mixed).execute(&f.storage.pool).await.unwrap();
    f.storage.cache_chapter_content_with_order(
        "pageA", mixed, hash("https://pagination.invalid/persisted/chapter"),
        "persisted hash title", "PERSISTED-HASH-BODY", Some(1),
    ).await.unwrap();
    let page = f.page("pageA", mixed, &[]).await;
    assert_page(&page, mixed, &[1, 3, 8], false, None);
    assert_eq!(page["data"]["chapters"][0]["content"], "PERSISTED-HASH-BODY");
    let range = f.page("pageA", mixed, &[("from", "2"), ("to", "7")]).await;
    assert_page(&range, mixed, &[3], false, None);
    f.close().await;
}

#[tokio::test]
async fn toc_legacy_fallback_hash_priority_and_empty_cached_body_keep_their_contract() {
    let f = Fixture::new().await;
    let url = "https://pagination.invalid/mixed-keys-book";
    let toc_url = "https://pagination.invalid/mixed-keys-toc";
    f.book("pageA", url, "synthetic-source", toc_url).await;
    let toc: Vec<_> = (0..5).map(|i| chapter(
        format!("https://pagination.invalid/mixed/{i}"),
        if i == 1 { "TOC-ONE".into() } else { String::new() },
    )).collect();
    f.cache("pageA", url, 0, "shadow title", "SHADOW-LEGACY-ZERO").await;
    f.cache("pageA", url, hash(&toc[0].url), "hash title", "HASH-ZERO").await;
    f.cache("pageA", url, 1, "legacy title", "LEGACY-ONE").await;
    f.cache("pageA", url, hash(&toc[2].url), "empty body title", "").await;
    f.cache("pageA", url, 4, "legacy fallback title", "LEGACY-FOUR").await;
    f.toc("pageA", toc_url, &toc).await;

    let response = f.page("pageA", url, &[]).await;
    assert_page(&response, url, &[0, 1, 2, 4], false, None);
    let rows = response["data"]["chapters"].as_array().unwrap();
    for (row, (title, body)) in rows.iter().zip([
        ("hash title", "HASH-ZERO"),
        ("TOC-ONE", "LEGACY-ONE"),
        ("empty body title", ""),
        ("legacy fallback title", "LEGACY-FOUR"),
    ]) {
        assert_eq!(row["title"], title);
        assert_eq!(row["content"], body);
    }
    let ranged = f.post_page("pageA", json!({"url":url,"from":0,"to":2})).await;
    assert_page(&ranged, url, &[0, 1, 2], false, None);
    f.close().await;
}

#[tokio::test]
async fn unorderable_hash_without_toc_fails_even_for_a_safe_looking_subrange() {
    let f = Fixture::new().await;
    let url = "https://pagination.invalid/missing-order";
    let toc_url = "https://pagination.invalid/missing-order-toc";
    let ch_url = "https://pagination.invalid/unmapped/chapter";
    assert!(hash(ch_url) >= 1_000_000);
    for ns in ["pageA", "pageB"] {
        f.book(ns, url, "synthetic-source", toc_url).await;
        f.cache(ns, url, hash(ch_url), "unmapped", &format!("PRIVATE-{ns}")).await;
    }
    f.cache("pageA", url, 0, "safe-looking legacy row", "LEGACY-BODY").await;
    // Another owner's matching TOC must not resolve pageA's unknown hash.
    f.toc("pageB", toc_url, &[chapter(ch_url.into(), "B directory title".into())]).await;
    for bounds in [vec![], vec![("from", "0"), ("to", "0")], vec![("from", "999999")]] {
        let response = f.page("pageA", url, &bounds).await;
        assert_eq!(response["isSuccess"], false, "{response}");
        assert_eq!(response["errorMsg"], "目录缓存缺失，请先刷新目录");
        assert!(response["data"].is_null());
    }
    let other = f.page("pageB", url, &[]).await;
    assert_page(&other, url, &[0], false, None);
    assert_eq!(other["data"]["chapters"][0]["content"], "PRIVATE-pageB");
    f.close().await;
}

#[tokio::test]
async fn same_book_and_toc_urls_isolate_directory_body_and_has_more_by_namespace() {
    let f = Fixture::new().await;
    let url = "https://pagination.invalid/shared-book";
    let toc_url = "https://pagination.invalid/shared-toc";
    for ns in ["pageA", "pageB"] {
        f.book(ns, url, "synthetic-source", toc_url).await;
    }
    let mut a_toc = Vec::new();
    for i in [1, 0] {
        let ch_url = format!("https://pagination.invalid/shared/{i}");
        a_toc.push(chapter(ch_url.clone(), format!("A-TITLE-{i}")));
        f.cache("pageA", url, hash(&ch_url), "A cached", &format!("A-BODY-{i}")).await;
    }
    f.toc("pageA", toc_url, &a_toc).await;
    let mut b_toc = Vec::new();
    for i in 0..205 {
        let ch_url = format!("https://pagination.invalid/shared/{i}");
        b_toc.push(chapter(ch_url.clone(), format!("B-TITLE-{i}")));
        f.cache("pageB", url, hash(&ch_url), "B cached", &format!("B-BODY-{i}")).await;
    }
    f.toc("pageB", toc_url, &b_toc).await;
    let a = f.page("pageA", url, &[]).await;
    assert_page(&a, url, &[0, 1], false, None);
    for (pos, original) in [(0, 1), (1, 0)] {
        assert_eq!(a["data"]["chapters"][pos]["title"], format!("A-TITLE-{original}"));
        assert_eq!(a["data"]["chapters"][pos]["content"], format!("A-BODY-{original}"));
    }
    let empty = f.page("pageA", url, &[("from", "200")]).await;
    assert_page(&empty, url, &[], false, None);
    let b = f.page("pageB", url, &[]).await;
    assert_page(&b, url, &(0..200).collect::<Vec<_>>(), true, Some(200));
    for (i, row) in b["data"]["chapters"].as_array().unwrap().iter().enumerate() {
        assert_eq!(row["title"], format!("B-TITLE-{i}"));
        assert_eq!(row["content"], format!("B-BODY-{i}"));
    }
    let b_last = f.page("pageB", url, &[("from", "200")]).await;
    assert_page(&b_last, url, &[200, 201, 202, 203, 204], false, None);
    f.close().await;
}

#[tokio::test]
async fn get_and_post_bounds_preserve_errors_and_empty_page_shape() {
    let f = Fixture::new().await;
    let url = "local://pagination/contract.txt";
    f.book("pageA", url, "local", "").await;
    let empty = f.page("pageA", url, &[]).await;
    assert_page(&empty, url, &[], false, None);
    for i in [0, 1, 3] {
        f.cache("pageA", url, i, &format!("TITLE-{i}"), &format!("BODY-{i}")).await;
    }
    let post = f.post_page("pageA", json!({"url":url,"from":1,"to":3})).await;
    assert_page(&post, url, &[1, 3], false, None);
    let string_bounds = f.post_page("pageA", json!({"url":url,"from":"1","to":"1"})).await;
    assert_page(&string_bounds, url, &[1], false, None);
    let max = f.page("pageA", url, &[("from", "9223372036854775807")]).await;
    assert_page(&max, url, &[], false, None);
    for bounds in [
        vec![("from", "-1")],
        vec![("to", "-1")],
        vec![("from", "2"), ("to", "1")],
        vec![("from", "NaN")],
        vec![("to", "1.2")],
        vec![("from", "9223372036854775808")],
    ] {
        let response = f.page("pageA", url, &bounds).await;
        assert_eq!(response["isSuccess"], false, "{response}");
        assert_eq!(response["errorMsg"], "缓存范围参数错误");
    }
    let invalid_post = f.post_page("pageA", json!({"url":url,"from":3,"to":1})).await;
    assert_eq!(invalid_post["isSuccess"], false);
    assert_eq!(invalid_post["errorMsg"], "缓存范围参数错误");
    let missing = f.post_page("pageA", json!({"from":0})).await;
    assert_eq!(missing["isSuccess"], false);
    assert_eq!(missing["errorMsg"], "参数错误");
    let not_owned = f.page("pageB", url, &[]).await;
    assert_eq!(not_owned["isSuccess"], false);
    assert_eq!(not_owned["errorMsg"], "书籍不存在（请先加入书架）");
    let anonymous: Value = f.client
        .get(format!("{}/reader3/getBookCacheChapters", f.base))
        .query(&[("url", url)]).send().await.unwrap().json().await.unwrap();
    assert_eq!(anonymous["isSuccess"], false);
    f.close().await;
}
