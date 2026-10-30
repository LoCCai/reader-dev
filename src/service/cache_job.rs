//! 后台缓存任务（cacheBookOnServer / cacheBookRangeOnServer / cacheBookSSE / cancelCacheBook）
//!
//! 内存任务表（taskId 键）：目录 → 按范围选章 → 逐章 getBookContent 语义抓取 →
//! 写 book_chapters 缓存表，并发 3；SSE 轮询进度 {cached,total,title}；cancel 置取消标记。

use std::collections::HashMap;
use std::sync::{Arc, LazyLock, Mutex};
use std::sync::atomic::{AtomicU64, Ordering};
use std::time::Duration;

use anyhow::{anyhow, Result};
use futures::StreamExt;

/// 缓存任务进度
#[derive(Debug, Clone, Default)]
pub struct CacheProgress {
    /// 已缓存章节数
    pub cached: usize,
    /// Failed fetches or storage writes; never counted as cached.
    pub failed: usize,
    /// 总章节数
    pub total: usize,
    /// 书名
    pub title: String,
    /// 是否结束（成功或失败）
    pub finished: bool,
    /// 结束时间（毫秒时间戳；0 = 未结束）——finished 条目回收判据
    pub finished_ms: i64,
    /// 是否被取消
    pub cancelled: bool,
    /// 错误信息（失败时）
    pub error: Option<String>,
    cancel_notify: Arc<tokio::sync::Notify>,
    write_gate: Arc<tokio::sync::Mutex<()>>,
    book_url: String,
    range: Option<(usize, usize)>,
    generation: u64,
}

/// finished 条目保留时长（结束后 1h 内仍可轮询；start/cancel 时顺带回收更旧的）
const FINISHED_RETENTION_MS: i64 = 3600_000;

/// 内存任务表（url → 进度句柄）
static CACHE_TASKS: LazyLock<Mutex<HashMap<(String, String), Arc<Mutex<CacheProgress>>>>> =
    LazyLock::new(|| Mutex::new(HashMap::new()));
static NEXT_GENERATION: AtomicU64 = AtomicU64::new(1);

/// 查询任务进度（不存在返回 None）
pub fn progress_of(ns: &str, url: &str) -> Option<Arc<Mutex<CacheProgress>>> {
    let map = CACHE_TASKS.lock().unwrap_or_else(|e| e.into_inner());
    map.iter().filter(|((owner, _), p)| {
        let p = p.lock().unwrap_or_else(|e| e.into_inner());
        owner == ns && p.book_url == url && p.range.is_none()
    }).max_by_key(|(_, p)| p.lock().unwrap_or_else(|e| e.into_inner()).generation)
        .map(|(_, p)| p.clone())
}

/// 查询任务进度（taskId 精确键）
pub fn progress_of_key(ns: &str, task_id: &str) -> Option<Arc<Mutex<CacheProgress>>> {
    CACHE_TASKS
        .lock()
        .unwrap_or_else(|e| e.into_inner())
        .get(&(ns.to_string(), task_id.to_string()))
        .cloned()
}

/// 取消当前用户的任务；保留进度供已有 SSE 消费者观察终态。
pub async fn cancel(ns: &str, url: &str) -> bool {
    let ids: Vec<String> = {
        let map = CACHE_TASKS.lock().unwrap_or_else(|e| e.into_inner());
        map.iter().filter_map(|((owner, id), p)| {
            let p = p.lock().unwrap_or_else(|e| e.into_inner());
            (owner == ns && p.book_url == url && !p.finished).then(|| id.clone())
        }).collect()
    };
    let mut cancelled = false;
    for id in ids { cancelled |= cancel_key(ns, &id).await; }
    cancelled
}

/// 顺带清理：finished 且超 1h 的任务条目（任务表此前只增不减——常年运行进程
/// 缓慢积累；运行中与近期完成任务不受影响）
fn reap_finished(map: &mut HashMap<(String, String), Arc<Mutex<CacheProgress>>>) {
    let now = chrono::Utc::now().timestamp_millis();
    map.retain(|_, p| {
        let g = p.lock().unwrap_or_else(|e| e.into_inner());
        !(g.finished && g.finished_ms > 0 && now - g.finished_ms > FINISHED_RETENTION_MS)
    });
}

/// 取消任务（taskId 精确键）
pub async fn cancel_key(ns: &str, task_id: &str) -> bool {
    let progress = {
        let mut map = CACHE_TASKS.lock().unwrap_or_else(|e| e.into_inner());
        reap_finished(&mut map);
        map.get(&(ns.to_string(), task_id.to_string())).cloned()
    };
    match progress {
        Some(p) => {
            let gate = p.lock().unwrap_or_else(|e| e.into_inner()).write_gate.clone();
            // Acknowledgement waits for the one in-flight commit; queued writes
            // must see cancelled after acquiring the same gate.
            let _guard = gate.lock().await;
            let mut p = p.lock().unwrap_or_else(|e| e.into_inner());
            if p.finished { return false; }
            p.cancelled = true;
            p.cancel_notify.notify_one();
            true
        }
        None => false,
    }
}

async fn wait_cancelled(progress: &Arc<Mutex<CacheProgress>>) {
    let notify = {
        let p = progress.lock().unwrap_or_else(|e| e.into_inner());
        if p.cancelled { return; }
        p.cancel_notify.clone()
    };
    notify.notified().await;
}

async fn cache_if_running(
    storage: &crate::storage::Storage,
    progress: &Arc<Mutex<CacheProgress>>,
    ns: &str,
    url: &str,
    index: i64,
    order: Option<i64>,
    title: &str,
    content: &str,
) -> Result<bool> {
    let gate = progress.lock().unwrap_or_else(|e| e.into_inner()).write_gate.clone();
    let _guard = gate.lock().await;
    if progress.lock().unwrap_or_else(|e| e.into_inner()).cancelled {
        return Ok(false);
    }
    storage.cache_chapter_content_with_order(ns, url, index, title, content, order).await?;
    Ok(true)
}

/// 范围切分辅助：越界返回错误
fn slice_range<T: Clone>(items: Vec<T>, range: Option<(usize, usize)>) -> Result<Vec<T>> {
    match range {
        Some((from, to)) => {
            if from >= items.len() || to >= items.len() || from > to {
                return Err(anyhow!("缓存范围无效（共 {} 章）", items.len()));
            }
            Ok(items[from..=to].to_vec())
        }
        None => Ok(items),
    }
}

/// 启动后台缓存任务（同一 url 已运行则复用；已完成的任务会被新任务覆盖）
pub fn start(ns: &str, url: &str, storage: crate::storage::Storage) -> Arc<Mutex<CacheProgress>> {
    start_range(ns, url, None, storage).1
}

/// 启动后台缓存任务并返回 (taskId, 进度句柄)。
/// `range = None` 表示整书；`Some((from, to))` 表示目录实章 0 基闭区间。
pub fn start_range(
    ns: &str,
    url: &str,
    range: Option<(usize, usize)>,
    storage: crate::storage::Storage,
) -> (String, Arc<Mutex<CacheProgress>>) {
    let (task_id, progress) = {
        // Lookup and insertion share one lock: simultaneous callers reuse one job.
        let mut map = CACHE_TASKS.lock().unwrap_or_else(|e| e.into_inner());
        reap_finished(&mut map);
        for ((owner, existing_id), existing) in map.iter() {
            let p = existing.lock().unwrap_or_else(|e| e.into_inner());
            if owner == ns && p.book_url == url && p.range == range && !p.finished && !p.cancelled {
                return (existing_id.clone(), existing.clone());
            }
        }
        let task_id = uuid::Uuid::new_v4().to_string();
        let key = (ns.to_string(), task_id.clone());
        let progress = Arc::new(Mutex::new(CacheProgress {
            title: url.to_string(),
            book_url: url.to_string(),
            range,
            generation: NEXT_GENERATION.fetch_add(1, Ordering::Relaxed),
            ..Default::default()
        }));
        map.insert(key, progress.clone());
        (task_id, progress)
    };
    let ns = ns.to_string();
    let url = url.to_string();
    let progress_for_task = progress.clone();
    tokio::spawn(async move {
        let result = run_job(&ns, &url, range, storage, &progress_for_task).await;
        let mut p = progress_for_task.lock().unwrap_or_else(|e| e.into_inner());
        p.finished = true;
        p.finished_ms = chrono::Utc::now().timestamp_millis();
        match result {
            Ok((title, total, cached)) => {
                p.title = title;
                p.total = total;
                p.cached = cached;
            }
            Err(e) => {
                p.error = Some(e.to_string());
            }
        }
    });
    (task_id, progress)
}

/// 执行缓存任务：目录 → 按范围选章 → 逐章正文 → 写缓存表（并发 3）
async fn run_job(
    ns: &str,
    url: &str,
    range: Option<(usize, usize)>,
    storage: crate::storage::Storage,
    progress: &Arc<Mutex<CacheProgress>>,
) -> Result<(String, usize, usize)> {
    let book = storage
        .find_book(ns, url)
        .await?
        .ok_or_else(|| anyhow!("书籍不存在（请先加入书架）"))?;
    if book.book_type != 0 {
        return Err(anyhow!("暂不支持该书籍类型的文本缓存"));
    }

    // 本地书：章节已在 book_chapters（导入时全量入库）——直接计数
    if url.starts_with("local://") {
        let rows = storage.list_cached_chapters(ns, url).await?;
        let rows = slice_range(rows, range)?;
        return Ok((book.name, rows.len(), rows.len()));
    }

    // 文件型本地书（storage/ 路径或白名单扩展名）：重解析原文件 → 章节落库
    let is_file_book = url.starts_with("storage/")
        || crate::service::local_book::SUPPORTED_EXTENSIONS
            .iter()
            .any(|e| url.to_lowercase().ends_with(&format!(".{e}")));
    if is_file_book {
        let path = crate::api::router::resolve_export_file_path(&storage.config.storage_dir(), url)
            .ok_or_else(|| anyhow!("本地书文件不存在"))?;
        let imported = crate::service::local_book::parse_loc_book_path_blocking(
            &path,
            &[],
            &book.toc_url,
            book.split_long_chapter,
        )
        .await?;
        let pairs: Vec<(String, String)> = imported
            .chapters
            .iter()
            .map(|c| (c.title.clone(), c.content.clone()))
            .collect();
        let selected = slice_range(pairs, range)?;
        let offset = range.map(|(from, _)| from).unwrap_or(0);
        let mut cached = 0;
        for (i, (title, content)) in selected.iter().enumerate() {
            if !cache_if_running(&storage, progress, ns, url, (offset + i) as i64, Some((offset + i) as i64), title, content).await? {
                break;
            }
            cached += 1;
            let mut p = progress.lock().unwrap_or_else(|e| e.into_inner());
            p.cached = cached;
            p.total = selected.len();
            p.title = book.name.clone();
        }
        return Ok((book.name, selected.len(), cached));
    }

    // 书源书：目录（复用规则引擎）→ 并发 3 逐章正文 → 缓存表
    let source = if !book.origin.is_empty() {
        storage.find_book_source(ns, &book.origin).await?
    } else {
        None
    };
    let Some(source) = source else {
        return Err(anyhow!("书源不存在（origin={}）", book.origin));
    };
    let toc_url = if book.toc_url.is_empty() {
        url.to_string()
    } else {
        book.toc_url.clone()
    };
    let toc = tokio::select! {
        biased;
        _ = wait_cancelled(progress) => return Ok((book.name, 0, 0)),
        result = crate::service::book::analyze_toc(ns, &toc_url, &source, 20, Some(&book.name), url) => result?,
    };
    {
        let gate = progress.lock().unwrap_or_else(|e| e.into_inner()).write_gate.clone();
        let _guard = gate.lock().await;
        if progress.lock().unwrap_or_else(|e| e.into_inner()).cancelled {
            return Ok((book.name, 0, 0));
        }
        storage.cache_toc(ns, url, &toc_url, &serde_json::to_string(&toc)?).await?;
    }
    let mut url_counts = HashMap::new();
    for chapter in toc.iter().filter(|c| !c.is_volume) {
        *url_counts.entry(chapter.url.clone()).or_insert(0usize) += 1;
    }
    let chapters: Vec<(Option<i64>, String, String)> = toc
        .into_iter()
        .filter(|c| !c.is_volume)
        .enumerate()
        .map(|(position, c)| {
            let order = if url_counts.get(&c.url) == Some(&1) { Some(position as i64) } else { None };
            (order, c.title, c.url)
        })
        .collect();
    let chapters = slice_range(chapters, range)?;
    let total = chapters.len();
    if total == 0 {
        return Ok((book.name, 0, 0));
    }

    {
        let mut p = progress.lock().unwrap_or_else(|e| e.into_inner());
        p.total = total;
        p.title = book.name.clone();
    }
    // Poll at most three chapter futures. Dropping this stream on cancellation
    // drops all in-flight fetches and never schedules the remaining directory.
    let book_name = &book.name;
    let source = &source;
    let mut fetches = futures::stream::iter(chapters.into_iter().map(|(order, title, ch_url)| async move {
        let content = crate::service::book::analyze_content(
            ns, &ch_url, source, 5, Some(&title), Some(book_name), url,
        ).await?;
        Ok::<_, anyhow::Error>((order, title, crate::util::md5::chapter_url_hash(&ch_url), content))
    })).buffer_unordered(3);
    let mut cached = 0;
    loop {
        let result = tokio::select! {
            biased;
            _ = wait_cancelled(progress) => break,
            result = fetches.next() => match result {
                Some(result) => result,
                None => break,
            },
        };
        match result {
            Ok((order, title, idx, content)) => {
                match cache_if_running(&storage, progress, ns, url, idx, order, &title, &content).await {
                    Ok(true) => cached += 1,
                    Ok(false) => break,
                    Err(e) => {
                        let mut p = progress.lock().unwrap_or_else(|e| e.into_inner());
                        p.failed += 1;
                        p.error = Some(format!("写入章节缓存失败: {e}"));
                    }
                }
            }
            Err(_) => progress.lock().unwrap_or_else(|e| e.into_inner()).failed += 1,
        }
        progress.lock().unwrap_or_else(|e| e.into_inner()).cached = cached;
    }
    drop(fetches);
    Ok((book.name, total, cached))
}

/// 等待任务结束（测试辅助：轮询直到 finished 或超时）
pub async fn wait_finished(ns: &str, url: &str, timeout: Duration) -> bool {
    let started = std::time::Instant::now();
    loop {
        if let Some(p) = progress_of(ns, url) {
            let p = p.lock().unwrap_or_else(|e| e.into_inner());
            if p.finished {
                return true;
            }
        }
        if started.elapsed() > timeout {
            return false;
        }
        tokio::time::sleep(Duration::from_millis(50)).await;
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[tokio::test]
    async fn cache_task_progress_and_cancellation_are_namespace_scoped() {
        let id = format!("https://cache-scope.test/{}", uuid::Uuid::new_v4());
        let alice = Arc::new(Mutex::new(CacheProgress::default()));
        let bob = Arc::new(Mutex::new(CacheProgress::default()));
        {
            let mut map = CACHE_TASKS.lock().unwrap();
            map.insert(("alice".into(), id.clone()), alice.clone());
            map.insert(("bob".into(), id.clone()), bob.clone());
        }
        assert!(Arc::ptr_eq(&progress_of_key("alice", &id).unwrap(), &alice));
        assert!(!cancel_key("charlie", &id).await);
        assert!(cancel_key("alice", &id).await);
        assert!(alice.lock().unwrap().cancelled);
        assert!(!bob.lock().unwrap().cancelled);
        assert!(progress_of_key("alice", &id).is_some());
        let mut map = CACHE_TASKS.lock().unwrap();
        map.remove(&("alice".into(), id.clone()));
        map.remove(&("bob".into(), id));
    }

    #[tokio::test]
    async fn cancel_acknowledgement_waits_for_inflight_commit() {
        let id = format!("https://cancel-gate.test/{}", uuid::Uuid::new_v4());
        let progress = Arc::new(Mutex::new(CacheProgress::default()));
        let gate = progress.lock().unwrap().write_gate.clone();
        let commit = gate.lock().await;
        CACHE_TASKS.lock().unwrap().insert(("alice".into(), id.clone()), progress.clone());
        let task_id = id.clone();
        let cancelling = tokio::spawn(async move { cancel_key("alice", &task_id).await });
        tokio::task::yield_now().await;
        assert!(!cancelling.is_finished(), "must not acknowledge while a commit holds the gate");
        assert!(!progress.lock().unwrap().cancelled);
        drop(commit);
        assert!(cancelling.await.unwrap());
        assert!(progress.lock().unwrap().cancelled);
        tokio::time::timeout(Duration::from_millis(100), wait_cancelled(&progress)).await.unwrap();
        CACHE_TASKS.lock().unwrap().remove(&("alice".into(), id));
    }

    #[tokio::test]
    async fn late_cancel_of_old_generation_cannot_cancel_restarted_range() {
        let storage = crate::storage::Storage {
            pool: sqlx::SqlitePool::connect("sqlite::memory:").await.unwrap(),
            config: crate::AppConfig::from_env(),
        };
        let url = format!("https://generation.test/{}", uuid::Uuid::new_v4());
        let (old_id, old) = start_range("alice", &url, Some((1, 2)), storage.clone());
        let (same_id, same) = start_range("alice", &url, Some((1, 2)), storage.clone());
        assert_eq!(old_id, same_id);
        assert!(Arc::ptr_eq(&old, &same));
        old.lock().unwrap().finished = true;
        let (new_id, new) = start_range("alice", &url, Some((1, 2)), storage.clone());
        assert_ne!(old_id, new_id);
        assert!(!cancel_key("alice", &old_id).await);
        assert!(!new.lock().unwrap().cancelled);
        assert!(cancel_key("alice", &new_id).await);
        assert!(new.lock().unwrap().cancelled);
        let mut map = CACHE_TASKS.lock().unwrap();
        map.remove(&("alice".into(), old_id));
        map.remove(&("alice".into(), new_id));
    }

    #[test]
    fn slice_range_selects_closed_interval() {
        let items = vec![1, 2, 3, 4, 5];
        assert_eq!(slice_range(items.clone(), None).unwrap(), items);
        assert_eq!(
            slice_range(items.clone(), Some((1, 3))).unwrap(),
            vec![2, 3, 4]
        );
        assert_eq!(slice_range(items.clone(), Some((0, 0))).unwrap(), vec![1]);
        assert_eq!(slice_range(items.clone(), Some((4, 4))).unwrap(), vec![5]);
    }

    #[test]
    fn slice_range_rejects_invalid_bounds() {
        let items = vec![1, 2, 3];
        assert!(slice_range(items.clone(), Some((3, 3))).is_err());
        assert!(slice_range(items.clone(), Some((2, 3))).is_err());
        assert!(slice_range(items.clone(), Some((2, 1))).is_err());
    }

    /// finished 条目回收：超 1h 的已完成任务在 start/cancel 时被顺带清理，
    /// 运行中与近期完成任务不受影响
    #[tokio::test]
    async fn finished_tasks_are_reaped_after_retention() {
        let tag = std::process::id();
        let key_old = format!("https://reap.test/old-{tag}");
        let key_fresh = format!("https://reap.test/fresh-{tag}");
        let key_running = format!("https://reap.test/running-{tag}");
        let now = chrono::Utc::now().timestamp_millis();
        {
            let mut map = CACHE_TASKS.lock().unwrap_or_else(|e| e.into_inner());
            map.insert(
                ("default".to_string(), key_old.clone()),
                Arc::new(Mutex::new(CacheProgress {
                    finished: true,
                    finished_ms: now - 2 * FINISHED_RETENTION_MS,
                    ..Default::default()
                })),
            );
            map.insert(
                ("default".to_string(), key_fresh.clone()),
                Arc::new(Mutex::new(CacheProgress {
                    finished: true,
                    finished_ms: now,
                    ..Default::default()
                })),
            );
            map.insert(
                ("default".to_string(), key_running.clone()),
                Arc::new(Mutex::new(CacheProgress {
                    finished: false,
                    finished_ms: now - 2 * FINISHED_RETENTION_MS, // 未结束——finished_ms 不生效
                    ..Default::default()
                })),
            );
        }
        // cancel 不存在的 key 返回 false，但已触发回收
        assert!(!cancel_key("default", &format!("https://reap.test/none-{tag}")).await);
        assert!(progress_of_key("default", &key_old).is_none(), "超时 finished 条目应被回收");
        assert!(progress_of_key("default", &key_fresh).is_some(), "近期 finished 条目保留");
        assert!(progress_of_key("default", &key_running).is_some(), "运行中条目不受影响");
        // 清理测试残留
        let mut map = CACHE_TASKS.lock().unwrap_or_else(|e| e.into_inner());
        map.remove(&("default".to_string(), key_fresh));
        map.remove(&("default".to_string(), key_running));
    }
}
