//! 定时任务（GAP #57 自动备份 / GAP #101 订阅源与 RSS 自动刷新）
//!
//! - `spawn_schedule_jobs`：书架更新循环（每 10 分钟）顺带刷新订阅源（每 6 小时）
//!   与 RSS 源（每 30 分钟，节流）
//! - `spawn_auto_backup_job`：每天 READER_AUTO_BACKUP_HOUR（默认 03:00）自动备份
//!   各命名空间到 webdav/legado/auto-YYYYMMDD.zip，保留最近 7 份
//! - `refresh_source_sub_core`：订阅抓取核心（saveSourceSub/refreshSourceSub 与定时任务共用）

use std::collections::HashMap;
use std::future::Future;
use std::time::Duration;

use anyhow::{anyhow, Result};
use chrono::Timelike;

use crate::storage::Storage;

/// 订阅源刷新周期（6 小时）
const SUB_REFRESH_INTERVAL: Duration = Duration::from_secs(6 * 3600);
/// RSS 源刷新周期（30 分钟）
const RSS_REFRESH_INTERVAL: Duration = Duration::from_secs(30 * 60);
/// 自动备份保留份数（最近 7 份）
const AUTO_BACKUP_KEEP: usize = 7;

/// book_vars_cache 定期清理参数：超 14 天未更新的行删除 + 总行数裁到 20 万以内
/// （20 万 × ~7KB/行 ≈ 最坏 1.4GB——有界；配合 parser 层写入节流双保险）
const BOOK_VARS_PRUNE_MAX_ROWS: usize = 200_000;
const BOOK_VARS_PRUNE_TTL: Duration = Duration::from_secs(14 * 24 * 3600);
/// js_cache 清理参数：过期行物理删除 + 全局按 rowid 保留最新 2 万行
/// （表无时间戳列；写侧另有 256KB 单值上限）
const JS_CACHE_PRUNE_MAX_ROWS: usize = 20_000;
/// 换源候选 TTL：30 天未更新删除（行内容为全源搜索结果 JSON，几十 KB/行）
const BOOK_CANDIDATES_PRUNE_TTL: Duration = Duration::from_secs(30 * 24 * 3600);

/// 缓存表统一清理（book_vars_cache + js_cache + book_source_candidates）
/// + users.token_map 过期会话清理（原先清理只发生在该用户下一次成功登录时，
/// 整体判定过期的用户不再登录则死会话无限期留存）。
/// 启动时与每小时各跑一次（每日一次赶不上 ~5千行/分钟的写入洪流——实测 9h 54.8 万行）。
pub async fn run_cache_prune(storage: &Storage) {
    match storage
        .prune_book_vars_cache(BOOK_VARS_PRUNE_MAX_ROWS, BOOK_VARS_PRUNE_TTL)
        .await
    {
        Ok((ttl, over)) if ttl + over > 0 => {
            tracing::info!("book_vars_cache 清理：TTL 过期 {ttl} 行，超限裁剪 {over} 行")
        }
        Ok(_) => {}
        Err(e) => tracing::warn!("book_vars_cache 清理失败: {e:#}"),
    }
    match storage.prune_js_cache(JS_CACHE_PRUNE_MAX_ROWS).await {
        Ok((expired, over)) if expired + over > 0 => {
            tracing::info!("js_cache 清理：过期 {expired} 行，超限裁剪 {over} 行")
        }
        Ok(_) => {}
        Err(e) => tracing::warn!("js_cache 清理失败: {e:#}"),
    }
    match storage
        .prune_book_candidates(BOOK_CANDIDATES_PRUNE_TTL)
        .await
    {
        Ok(n) if n > 0 => tracing::info!("book_source_candidates 清理：TTL 过期 {n} 行"),
        Ok(_) => {}
        Err(e) => tracing::warn!("book_source_candidates 清理失败: {e:#}"),
    }
    match storage
        .prune_expired_user_tokens(chrono::Utc::now().timestamp_millis())
        .await
    {
        Ok(n) if n > 0 => tracing::info!("过期会话 token 清理：{n} 条"),
        Ok(_) => {}
        Err(e) => tracing::warn!("过期会话 token 清理失败: {e:#}"),
    }
}
/// 订阅抓取超时（秒）——远程书源 JSON 常达数百 KB（如 yckceo 7595 实测 >15s）
const SUB_FETCH_TIMEOUT_SECS: u64 = 45;

/// 单阶段 panic 隔离：阶段 future 放独立 spawn 执行——panic 只终止该阶段任务，
/// JoinError 记错误日志后返回 None，调度循环存活继续下一 tick。
/// （此前任一阶段 panic 会把整个循环 spawn 任务打掉，定时任务自此静默停摆）
async fn run_stage_isolated<T: Send + 'static>(
    name: &str,
    stage: impl Future<Output = T> + Send + 'static,
) -> Option<T> {
    match tokio::spawn(stage).await {
        Ok(r) => Some(r),
        Err(e) => {
            tracing::error!("定时任务阶段 {name} panic: {e}");
            None
        }
    }
}

/// 启动定时任务（书架更新 + 订阅/RSS 自动刷新；在 lib.rs serve 时调用一次）
pub fn spawn_schedule_jobs(storage: Storage) {
    tokio::spawn(async move {
        let mut interval = tokio::time::interval(Duration::from_secs(10 * 60));
        interval.set_missed_tick_behavior(tokio::time::MissedTickBehavior::Skip);
        let mut last_sub = std::time::Instant::now();
        let mut last_rss = std::time::Instant::now();
        loop {
            interval.tick().await;
            // ① 书架更新（原有 F-35：can_update=1 的书回写最新章节/总数）
            let st = storage.clone();
            match run_stage_isolated("书架更新", async move {
                crate::storage::run_shelf_update(&st).await
            })
            .await
            {
                Some(Ok(n)) => tracing::info!("书架更新检查完成：更新 {n} 本"),
                Some(Err(e)) => tracing::warn!("书架更新检查失败: {e:#}"),
                None => {} // panic 已记日志，循环存活
            }
            // ② 订阅源自动刷新（每 6 小时，节流）
            if last_sub.elapsed() >= SUB_REFRESH_INTERVAL {
                let st = storage.clone();
                match run_stage_isolated("订阅源刷新", async move {
                    run_source_sub_refresh(&st).await
                })
                .await
                {
                    Some(Ok(n)) => tracing::info!("订阅源自动刷新完成：{n} 个订阅"),
                    Some(Err(e)) => tracing::warn!("订阅源自动刷新失败: {e:#}"),
                    None => {}
                }
                last_sub = std::time::Instant::now();
            }
            // ③ RSS 源自动刷新（每 30 分钟，节流）
            if last_rss.elapsed() >= RSS_REFRESH_INTERVAL {
                let st = storage.clone();
                match run_stage_isolated("RSS 刷新", async move {
                    run_rss_refresh(&st).await
                })
                .await
                {
                    Some(Ok(n)) => tracing::info!("RSS 源自动刷新完成：{n} 个源"),
                    Some(Err(e)) => tracing::warn!("RSS 源自动刷新失败: {e:#}"),
                    None => {}
                }
                last_rss = std::time::Instant::now();
            }
        }
    });
}

/// 启动自动备份定时任务（GAP #57）
///
/// 每天 READER_AUTO_BACKUP_HOUR（0-23，默认 3 = 03:00）触发一次（同日不重复）；
/// 环境变量为空 → 默认 3；非法值 → 告警并回退默认。
pub fn spawn_auto_backup_job(storage: Storage) {
    let raw = std::env::var("READER_AUTO_BACKUP_HOUR").unwrap_or_default();
    let hour: i64 = if raw.trim().is_empty() {
        3
    } else {
        match raw.trim().parse::<i64>() {
            Ok(h) if (0..=23).contains(&h) => h,
            _ => {
                tracing::warn!("READER_AUTO_BACKUP_HOUR 非法（{raw}），使用默认 3");
                3
            }
        }
    };
    tracing::info!("自动备份定时任务：每天 {hour:02}:00 执行");
    tokio::spawn(async move {
        let mut interval = tokio::time::interval(Duration::from_secs(10 * 60));
        interval.set_missed_tick_behavior(tokio::time::MissedTickBehavior::Skip);
        let mut last_date: Option<chrono::NaiveDate> = None;
        let mut tick_count: u64 = 0;
        loop {
            interval.tick().await;
            tick_count += 1;
            // 每小时第一个 tick 跑缓存清理（backup 循环复用同一 spawn——无需独立任务）
            if tick_count % 6 == 1 {
                let st = storage.clone();
                let _ =
                    run_stage_isolated("缓存清理", async move { run_cache_prune(&st).await }).await;
            }
            let now = chrono::Local::now();
            if now.hour() as i64 != hour {
                continue;
            }
            if last_date == Some(now.date_naive()) {
                continue; // 当天已备份
            }
            let st = storage.clone();
            match run_stage_isolated("自动备份", async move { run_auto_backup(&st).await }).await {
                Some(Ok(n)) => {
                    tracing::info!("自动备份完成：{} 个命名空间", n);
                    last_date = Some(now.date_naive());
                }
                Some(Err(e)) => tracing::warn!("自动备份失败: {e:#}"),
                None => {} // panic 已记日志，last_date 不置位 → 下一 tick 重试
            }
            // 每日全库快照（GAP 148 补位——此前仅启动时备份一次，长运行进程库损坏
            // 时无近期快照可回滚；backup_reader_db 自带 mtime 守卫，库未变化则跳过）
            let dir = storage.config.storage_dir();
            if let Some(r) = run_stage_isolated("每日数据库备份", async move {
                crate::util::db_backup::backup_reader_db(&dir).await
            })
            .await
            {
                match r {
                    Ok(Some(p)) => tracing::info!("每日数据库备份: {}", p.display()),
                    Ok(None) => {}
                    Err(e) => tracing::warn!("每日数据库备份失败: {e:#}"),
                }
            }
            // bot 导出文件清理（>3 天）——每日定时（此前挂在下载任务上，无人下载则永不清）
            crate::api::bot::sweep_export_files(&storage.config.storage_dir());
            // toc_cache / book_chapters / rss_articles 每日清理（READER_CACHE_TTL_DAYS）
            let st = storage.clone();
            let _ = run_stage_isolated("缓存表清理", async move {
                run_cache_table_cleanup(&st).await
            })
            .await;
        }
    });
}

/// 自动备份（GAP #57）：各命名空间 → webdav/legado/auto-YYYYMMDD.zip（同日跳过）→
/// 保留最近 7 份（删除更旧的 auto-*.zip）。返回执行备份的命名空间数。
pub async fn run_auto_backup(storage: &Storage) -> Result<usize> {
    let nss = storage.schedule_namespaces().await;
    let today = chrono::Local::now().format("%Y%m%d").to_string();
    let mut done = 0usize;
    for ns in nss {
        let legado = storage
            .config
            .storage_dir()
            .join("data")
            .join(&ns)
            .join("webdav")
            .join("legado");
        // 同日已备份 → 跳过（重启/多轮 tick 幂等）
        if legado.join(format!("auto-{today}.zip")).exists() {
            continue;
        }
        match storage
            .write_backup_zip(&ns, &format!("auto-{today}"))
            .await
        {
            Ok(path) => {
                tracing::info!("自动备份 [{ns}]: {}", path);
                done += 1;
            }
            Err(e) => tracing::warn!("自动备份 [{ns}] 失败: {e:#}"),
        }
        // 保留最近 7 份（单命名空间失败不影响清理）
        storage.prune_auto_backups(&ns, AUTO_BACKUP_KEEP);
    }
    Ok(done)
}

/// 缓存表 TTL（天）：env `READER_CACHE_TTL_DAYS`（默认 30；非法/越界回退默认）
fn cache_ttl_days() -> i64 {
    let raw = std::env::var("READER_CACHE_TTL_DAYS").unwrap_or_default();
    if raw.trim().is_empty() {
        return 30;
    }
    match raw.trim().parse::<i64>() {
        Ok(d) if (1..=3650).contains(&d) => d,
        _ => {
            tracing::warn!("READER_CACHE_TTL_DAYS 非法（{raw}），使用默认 30");
            30
        }
    }
}

/// toc_cache / book_chapters / rss_articles 每日清理（READER_CACHE_TTL_DAYS，默认 30 天）：
/// - toc_cache：TTL 外且书不在书架的行（书架书目录缓存不动）；
/// - book_chapters：书不在书架的孤儿行（表无时间戳列，孤儿即判据）；
/// - rss_articles：time 早于 TTL 的行（time=0 缺失时间不清——保守）。
/// 各表删除前行数先 tracing::info（首次上线保守——日志可核对删除规模）。
pub async fn run_cache_table_cleanup(storage: &Storage) {
    let ttl = Duration::from_secs(cache_ttl_days() as u64 * 24 * 3600);
    match storage.prune_toc_cache(ttl).await {
        Ok(n) if n > 0 => tracing::info!("toc_cache 清理完成：删除 {n} 行"),
        Ok(_) => {}
        Err(e) => tracing::warn!("toc_cache 清理失败: {e:#}"),
    }
    match storage.prune_orphan_book_chapters().await {
        Ok(n) if n > 0 => tracing::info!("book_chapters 清理完成：删除 {n} 行"),
        Ok(_) => {}
        Err(e) => tracing::warn!("book_chapters 清理失败: {e:#}"),
    }
    match storage.prune_rss_articles(ttl).await {
        Ok(n) if n > 0 => tracing::info!("rss_articles 清理完成：删除 {n} 行"),
        Ok(_) => {}
        Err(e) => tracing::warn!("rss_articles 清理失败: {e:#}"),
    }
}

/// 订阅源定时刷新（GAP #101）：各命名空间订阅重新拉取并覆盖书源。
/// 返回刷新成功的订阅数（单订阅失败仅告警，不影响其余）。
pub async fn run_source_sub_refresh(storage: &Storage) -> Result<usize> {
    let nss = storage.schedule_namespaces().await;
    let mut refreshed = 0usize;
    for ns in nss {
        let Ok(subs) = storage.get_source_subs(&ns).await else {
            continue;
        };
        for sub in subs {
            if !sub.enabled {
                // 已禁用订阅：保留记录与已导入书源，定时任务跳过自动刷新
                continue;
            }
            match refresh_source_sub_core(storage, &ns, &sub.url, &sub.name, &sub.selected_urls.0)
                .await
            {
                Ok((n, _)) => {
                    tracing::info!("订阅自动刷新 [{ns}] {}：{n} 个书源", sub.name);
                    refreshed += 1;
                }
                Err(e) => tracing::warn!("订阅自动刷新跳过 [{ns}] {}: {e:#}", sub.name),
            }
        }
    }
    Ok(refreshed)
}

/// RSS 源定时刷新（GAP #101）：各命名空间启用的 RSS 源抓取文章入库（第 1 页）。
/// 返回刷新成功的源数（单源失败仅告警）。
pub async fn run_rss_refresh(storage: &Storage) -> Result<usize> {
    let nss = storage.schedule_namespaces().await;
    let mut refreshed = 0usize;
    for ns in nss {
        let Ok(sources) = storage.get_rss_sources(&ns).await else {
            continue;
        };
        for source in sources {
            if !source.enabled {
                continue;
            }
            match crate::service::rss::fetch_articles(&source, 1, None).await {
                Ok(articles) => {
                    if let Err(e) = storage.save_rss_articles(&ns, &articles).await {
                        tracing::warn!("RSS 自动刷新入库失败 [{ns}] {}: {e:#}", source.source_name);
                    } else {
                        refreshed += 1;
                    }
                }
                Err(e) => tracing::warn!("RSS 自动刷新跳过 [{ns}] {}: {e:#}", source.source_name),
            }
        }
    }
    Ok(refreshed)
}

/// 抓取订阅 URL → 校验 → 返回书源数组与响应原文（saveSourceSub / refreshSourceSub /
/// 定时刷新 / 前端导入预览共用）。错误信息即对外文案。
pub async fn fetch_source_sub_sources(
    url: &str,
) -> Result<(Vec<crate::model::BookSource>, String)> {
    let headers_map: HashMap<String, String> = HashMap::new();
    let resp = crate::service::crawler::fetch(
        url,
        &headers_map,
        SUB_FETCH_TIMEOUT_SECS,
        "GET",
        None,
        None,
        None,
    )
    .await
    .map_err(|_| anyhow!("远程书源链接错误"))?;
    // 校验：数组 / {bookSourceList:[...]} / 单对象，字段类型宽松归一（legacy 书源常见字符串数字/布尔）
    let json: serde_json::Value =
        serde_json::from_str(&resp.body).map_err(|_| anyhow!("书源数据格式错误"))?;
    let sources = crate::model::book_source::normalize_book_sources(json);
    if sources.is_empty() || sources.iter().any(|s| s.book_source_url.trim().is_empty()) {
        return Err(anyhow!("书源数据格式错误"));
    }
    Ok((sources, resp.body))
}

/// 订阅抓取核心（saveSourceSub / refreshSourceSub / 定时刷新共用）
///
/// 抓取订阅 URL → 校验书源数组 → 订阅入库（raw_json 存原文）→ 批量导入书源
/// （已存在覆盖；书源数上限整批拒绝）。`selected_urls` 非空时只导入用户勾选的
/// 书源并按勾选顺序写 custom_order（自动刷新沿用同一选择）。错误信息即对外文案：
/// "远程书源链接错误" / "书源数据格式错误" / "超过书源数上限" / "保存失败"。
pub async fn refresh_source_sub_core(
    storage: &Storage,
    ns: &str,
    url: &str,
    name: &str,
    selected_urls: &[String],
) -> Result<(usize, String)> {
    let (mut sources, raw_json) = fetch_source_sub_sources(url).await?;
    if !selected_urls.is_empty() {
        let selected: std::collections::HashSet<&String> = selected_urls.iter().collect();
        let mut picked: Vec<crate::model::BookSource> = sources
            .into_iter()
            .filter(|s| selected.contains(&s.book_source_url))
            .collect();
        // 勾选顺序即排序（与预览弹窗一致）；custom_order 从 0 递增
        for (i, s) in picked.iter_mut().enumerate() {
            s.custom_order = i as i64;
        }
        sources = picked;
    }
    if sources.is_empty() {
        return Err(anyhow!("未选择任何书源"));
    }
    // F-7 书源数上限（已存在覆盖不计名额，超限整批拒绝）
    if let Some(limit) = storage.book_source_limit_for(ns).await.ok().flatten() {
        if limit > 0 {
            let mut new_count = 0i64;
            for s in &sources {
                let exists = storage
                    .find_book_source(ns, &s.book_source_url)
                    .await
                    .ok()
                    .flatten()
                    .is_some();
                if !exists {
                    new_count += 1;
                }
            }
            match storage.count_book_sources(ns).await {
                Ok(count) if count + new_count > limit => {
                    return Err(anyhow!("超过书源数上限"));
                }
                Ok(_) => {}
                Err(_) => return Err(anyhow!("系统错误")),
            }
        }
    }
    storage
        .save_source_sub(ns, url, name, &raw_json, selected_urls)
        .await
        .map_err(|_| anyhow!("保存失败"))?;
    storage
        .save_book_sources(ns, &sources)
        .await
        .map_err(|_| anyhow!("保存失败"))?;
    // 订阅显示名：书源数组首项名称优先（前端不再自行 fetch——避免 CORS），否则保持传入名
    let display_name = sources
        .first()
        .map(|s| s.book_source_name.trim().to_string())
        .filter(|n| !n.is_empty())
        .unwrap_or_else(|| name.to_string());
    Ok((sources.len(), display_name))
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::model::RssSource;
    use crate::storage::Storage;

    async fn test_storage(tag: &str) -> (Storage, std::path::PathBuf) {
        let dir =
            std::env::temp_dir().join(format!("reader-schedule-test-{}-{tag}", std::process::id()));
        let _ = std::fs::remove_dir_all(&dir);
        let mut config = crate::AppConfig::from_env();
        config.work_dir = dir.to_string_lossy().into_owned();
        let storage = crate::storage::init(&config).await.unwrap();
        (storage, dir)
    }

    async fn cleanup(storage: Storage, dir: std::path::PathBuf) {
        storage.pool.close().await;
        let _ = std::fs::remove_dir_all(&dir);
    }

    /// 微型 HTTP 服务器：按请求路径应答（routes: path → body）；返回 base URL
    async fn mock_server(routes: Vec<(&'static str, &'static str)>) -> String {
        let listener = tokio::net::TcpListener::bind("127.0.0.1:0").await.unwrap();
        let addr = listener.local_addr().unwrap();
        tokio::spawn(async move {
            use tokio::io::{AsyncReadExt, AsyncWriteExt};
            let routes: HashMap<String, String> = routes
                .into_iter()
                .map(|(p, b)| (p.to_string(), b.to_string()))
                .collect();
            for _ in 0..10 {
                let Ok((mut sock, _)) = listener.accept().await else {
                    break;
                };
                let mut buf = [0u8; 4096];
                let n = sock.read(&mut buf).await.unwrap_or(0);
                let req = String::from_utf8_lossy(&buf[..n]).to_string();
                let path = req.split_whitespace().nth(1).unwrap_or("/").to_string();
                let body = routes.get(&path).cloned().unwrap_or_default();
                let resp = format!(
                    "HTTP/1.1 200 OK\r\nContent-Type: application/json; charset=utf-8\r\nContent-Length: {}\r\nConnection: close\r\n\r\n{}",
                    body.len(),
                    body
                );
                let _ = sock.write_all(resp.as_bytes()).await;
            }
        });
        format!("http://{addr}")
    }

    const SAMPLE_RSS: &str = r#"<?xml version="1.0" encoding="UTF-8"?>
<rss version="2.0"><channel><title>频道</title><link>https://e.com</link>
<item><title>文章1</title><link>https://e.com/1</link><guid>https://e.com/1</guid></item>
</channel></rss>"#;

    /// GAP #101：订阅源定时刷新——重新拉取订阅并导入/覆盖书源
    #[tokio::test]
    async fn test_run_source_sub_refresh() {
        let _ssrf = crate::service::crawler::ssrf_allow_private_guard(true); // mock 绑定 127.0.0.1（P1 SSRF 校验放行，仅测试）
        let (storage, dir) = test_storage("subrefresh").await;
        let base = mock_server(vec![
            (
                "/sub",
                r#"[{"bookSourceUrl":"https://x.com","bookSourceName":"X源"}]"#,
            ),
            (
                "/off",
                r#"[{"bookSourceUrl":"https://y.com","bookSourceName":"Y源"}]"#,
            ),
        ])
        .await;
        let sub_url = format!("{base}/sub");
        storage
            .save_source_sub("default", &sub_url, "订阅", "[]", &[])
            .await
            .unwrap();
        // 默认全部启用：所有订阅都参与自动刷新
        let second_url = format!("{base}/off");
        storage
            .save_source_sub("default", &second_url, "第二订阅", "[]", &[])
            .await
            .unwrap();

        assert_eq!(run_source_sub_refresh(&storage).await.unwrap(), 2);
        let sources = storage.get_book_sources("default").await.unwrap();
        assert_eq!(sources.len(), 2);
        assert!(sources
            .iter()
            .any(|s| { s.book_source_url == "https://x.com" && s.book_source_name == "X源" }));
        assert!(sources
            .iter()
            .any(|s| { s.book_source_url == "https://y.com" && s.book_source_name == "Y源" }));
        // 订阅 raw_json 已覆盖
        let sub = storage
            .find_source_sub("default", &sub_url)
            .await
            .unwrap()
            .unwrap();
        assert!(sub
            .raw_json
            .as_deref()
            .unwrap_or("")
            .contains("bookSourceUrl"));
        cleanup(storage, dir).await;
    }

    /// 禁用订阅后定时刷新跳过（保留记录与已导入书源）
    #[tokio::test]
    async fn test_run_source_sub_refresh_skips_disabled() {
        let _ssrf = crate::service::crawler::ssrf_allow_private_guard(true);
        let (storage, dir) = test_storage("subrefresh-disabled").await;
        let base = mock_server(vec![
            (
                "/on",
                r#"[{"bookSourceUrl":"https://x.com","bookSourceName":"X源"}]"#,
            ),
            (
                "/off",
                r#"[{"bookSourceUrl":"https://y.com","bookSourceName":"Y源"}]"#,
            ),
        ])
        .await;
        let on_url = format!("{base}/on");
        let off_url = format!("{base}/off");
        storage
            .save_source_sub("default", &on_url, "启用订阅", "[]", &[])
            .await
            .unwrap();
        storage
            .save_source_sub("default", &off_url, "禁用订阅", "[]", &[])
            .await
            .unwrap();
        storage
            .set_source_sub_enabled("default", &off_url, false)
            .await
            .unwrap();

        assert_eq!(run_source_sub_refresh(&storage).await.unwrap(), 1);
        let sources = storage.get_book_sources("default").await.unwrap();
        assert!(sources.iter().any(|s| s.book_source_url == "https://x.com"));
        assert!(
            !sources.iter().any(|s| s.book_source_url == "https://y.com"),
            "禁用订阅不应自动刷新导入"
        );
        let off = storage
            .find_source_sub("default", &off_url)
            .await
            .unwrap()
            .unwrap();
        assert!(!off.enabled, "禁用订阅记录应保留且 enabled=false");
        cleanup(storage, dir).await;
    }

    /// GAP #101：订阅抓取核心校验（非书源数组 → 拒绝）
    #[tokio::test]
    async fn test_refresh_source_sub_core_rejects_bad_data() {
        // P1 SSRF：订阅抓取走 crawler::fetch（入口公网校验）——mock 绑定 127.0.0.1，
        // 持放行守卫（仅测试代码可设置）
        let _ssrf = crate::service::crawler::ssrf_allow_private_guard(true);
        let (storage, dir) = test_storage("subbad").await;
        let base = mock_server(vec![("/bad", "<html>不是json</html>")]).await;
        let err = refresh_source_sub_core(&storage, "default", &format!("{base}/bad"), "订阅", &[])
            .await
            .unwrap_err();
        assert!(err.to_string().contains("书源数据格式错误"), "{err}");
        // 连接失败 → 远程书源链接错误
        let err = refresh_source_sub_core(&storage, "default", "http://127.0.0.1:1/x", "订阅", &[])
            .await
            .unwrap_err();
        assert!(err.to_string().contains("远程书源链接错误"), "{err}");
        cleanup(storage, dir).await;
    }

    /// GAP #101：RSS 源定时刷新——抓取文章入库
    #[tokio::test]
    async fn test_run_rss_refresh() {
        // P1 SSRF：RSS 抓取走 crawler::fetch——mock 绑定 127.0.0.1，持放行守卫
        let _ssrf = crate::service::crawler::ssrf_allow_private_guard(true);
        let (storage, dir) = test_storage("rssrefresh").await;
        let base = mock_server(vec![("/feed.xml", SAMPLE_RSS)]).await;
        storage
            .save_rss_source(
                "default",
                &RssSource {
                    source_url: format!("{base}/feed.xml"),
                    source_name: "RSS".into(),
                    enabled: true,
                    ..Default::default()
                },
            )
            .await
            .unwrap();
        // 禁用的源不刷新
        storage
            .save_rss_source(
                "default",
                &RssSource {
                    source_url: format!("{base}/off.xml"),
                    source_name: "停用".into(),
                    enabled: false,
                    ..Default::default()
                },
            )
            .await
            .unwrap();

        assert_eq!(run_rss_refresh(&storage).await.unwrap(), 1);
        let n: i64 = sqlx::query_scalar("SELECT COUNT(*) FROM rss_articles")
            .fetch_one(&storage.pool)
            .await
            .unwrap();
        assert_eq!(n, 1);
        let art = storage
            .get_rss_article("default", "https://e.com/1")
            .await
            .unwrap()
            .unwrap();
        assert_eq!(art.title, "文章1");
        cleanup(storage, dir).await;
    }

    /// GAP #57：命名空间集合（非 secure → default）
    #[tokio::test]
    async fn test_schedule_namespaces_non_secure() {
        let (mut storage, dir) = test_storage("ns").await;
        assert_eq!(storage.schedule_namespaces().await, vec!["default"]);
        // secure：用户列表 + 残留 default 目录
        storage
            .insert_user(&crate::model::User {
                username: "alice".into(),
                ..Default::default()
            })
            .await
            .unwrap();
        storage.config.secure = true;
        let nss = storage.schedule_namespaces().await;
        assert!(nss.contains(&"alice".to_string()));
        cleanup(storage, dir).await;
    }

    /// 单阶段 panic 隔离：panic 被捕获为 None（记日志），不向调用方传播——
    /// 调度循环不会因单阶段 panic 死亡
    #[tokio::test]
    async fn test_run_stage_isolated_contains_panic() {
        let r: Option<()> = run_stage_isolated("panic阶段", async { panic!("boom") }).await;
        assert!(r.is_none(), "panic 应被隔离为 None 而非传播");
        // 正常阶段输出透传；阶段内 Err 也只透传不 panic
        let r = run_stage_isolated("ok阶段", async { Ok::<usize, anyhow::Error>(3) }).await;
        assert_eq!(r.unwrap().unwrap(), 3);
        let r = run_stage_isolated("err阶段", async { Err::<usize, anyhow::Error>(anyhow!("x")) }).await;
        assert!(r.unwrap().is_err());
    }

    /// 三缓存表清理：书架书保留、TTL/孤儿规则命中删除
    #[tokio::test]
    async fn test_prune_content_caches_respects_shelf_and_ttl() {
        let (storage, dir) = test_storage("cacheclean").await;
        let now = chrono::Utc::now().timestamp_millis();
        let old = now - 40 * 24 * 3600 * 1000; // 40 天前（TTL 30 天外）
        // 书架书一本
        sqlx::query("INSERT INTO books (book_url, user_namespace, name) VALUES ('https://shelf.ed/b', 'default', '在架书')")
            .execute(&storage.pool)
            .await
            .unwrap();

        // toc_cache：在架过期行保留 / 孤儿过期行删除 / 孤儿新鲜行保留
        for (bu, tu, ts) in [
            ("https://shelf.ed/b", "https://shelf.ed/toc", old),
            ("https://gone.ed/b", "https://gone.ed/toc", old),
            ("https://fresh.ed/b", "https://fresh.ed/toc", now),
        ] {
            sqlx::query(
                "INSERT INTO toc_cache (book_url, toc_url, chapters_json, updated_at, user_namespace) VALUES (?1, ?2, '[]', ?3, 'default')",
            )
            .bind(bu)
            .bind(tu)
            .bind(ts)
            .execute(&storage.pool)
            .await
            .unwrap();
        }
        // 书架书的第二写形态：book_url 列存 tocUrl、toc_url 列存 bookUrl——同样保留
        sqlx::query(
            "INSERT INTO toc_cache (book_url, toc_url, chapters_json, updated_at, user_namespace) VALUES ('https://shelf.ed/toc', 'https://shelf.ed/b', '[]', ?1, 'default')",
        )
        .bind(old)
        .execute(&storage.pool)
        .await
        .unwrap();
        assert_eq!(
            storage
                .prune_toc_cache(Duration::from_secs(30 * 24 * 3600))
                .await
                .unwrap(),
            1,
            "只删孤儿过期行（gone.ed）"
        );

        // book_chapters：在架书行保留 / 孤儿行删除
        for bu in ["https://shelf.ed/b", "https://gone.ed/b"] {
            sqlx::query(
                "INSERT INTO book_chapters (book_url, chapter_index, title, content, user_namespace) VALUES (?1, 0, 't', 'c', 'default')",
            )
            .bind(bu)
            .execute(&storage.pool)
            .await
            .unwrap();
        }
        assert_eq!(
            storage.prune_orphan_book_chapters().await.unwrap(),
            1,
            "只删不在书架的孤儿行"
        );

        // rss_articles：过期行删除 / 新鲜行与 time=0 行保留
        for (u, t) in [("https://r.e/old", old), ("https://r.e/new", now), ("https://r.e/zero", 0)] {
            sqlx::query(
                "INSERT INTO rss_articles (url, time, user_namespace) VALUES (?1, ?2, 'default')",
            )
            .bind(u)
            .bind(t)
            .execute(&storage.pool)
            .await
            .unwrap();
        }
        assert_eq!(
            storage
                .prune_rss_articles(Duration::from_secs(30 * 24 * 3600))
                .await
                .unwrap(),
            1,
            "只删 TTL 外文章（time=0 不清）"
        );

        // run_cache_table_cleanup 全链路可跑（TTL env 默认 30 天——上面已删过，幂等为 0）
        run_cache_table_cleanup(&storage).await;
        cleanup(storage, dir).await;
    }
}
