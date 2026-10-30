//! 启动时数据库备份（GAP 148：迁移/升级前自动快照）
//!
//! - 时机：storage 初始化（打开连接/建表/迁移）之前，main.rs 调用；
//! - 行为：`storage/reader.db` → `storage/reader.db.bak-{YYYYMMDDHHMMSS}`；
//! - 保留：最近 `KEEP_BACKUPS`（3）份，更旧的删除；
//! - 禁用：env `READER_DB_BACKUP=0`；
//! - WAL 一致性：若存在 `reader.db-wal`（上次未正常 checkpoint），先用临时连接执行
//!   `PRAGMA wal_checkpoint(TRUNCATE)` 再复制，保证备份为一致快照（尽力而为，失败仅告警）。

use std::path::{Path, PathBuf};

use anyhow::Result;
use sqlx::ConnectOptions as _; // SqliteConnectOptions::connect

/// 保留的备份份数（超出删最旧）——3 份：启动备份为全量文件拷贝，多 GB 库下 5 份磁盘占用可观
pub const KEEP_BACKUPS: usize = 3;

/// 执行启动备份。返回生成的备份路径（禁用/无库文件 → None）
pub async fn backup_reader_db(storage_dir: &Path) -> Result<Option<PathBuf>> {
    // env READER_DB_BACKUP=0 禁用
    if std::env::var("READER_DB_BACKUP")
        .map(|v| v.trim() == "0")
        .unwrap_or(false)
    {
        tracing::info!("READER_DB_BACKUP=0——跳过启动数据库备份");
        return Ok(None);
    }
    let db_path = storage_dir.join("reader.db");
    if !db_path.exists() {
        return Ok(None); // 首次启动无库，无需备份
    }

    // 顺带清理孤儿侧车（reader.db.bak-*-wal/-shm：历史备份方式残留；现行备份为
    // 单文件拷贝不产生侧车——主 bak 已被 prune 删除的侧车成了永不清的死文件）
    let reaped = remove_orphan_backup_sidecars(storage_dir);
    if reaped > 0 {
        tracing::info!("清理孤儿备份侧车文件 {reaped} 个（reader.db.bak-*-wal/-shm）");
    }

    // P1 崩溃循环守卫：现有最新备份不早于库变化 mtime（库未变化）→ 跳过全量拷贝。
    // 此前每次重启无条件复制——OOM 崩溃循环下 3 次/小时拷贝多 GB 库，磁盘与启动延迟双放大。
    // 库变化 mtime 取 max(reader.db, reader.db-wal)：WAL 模式下主库文件 mtime 不随日常
    // 写入推进（写先落 wal），只看主库会把「wal 在涨、库实质已变」误判为未变化 → 永久跳过备份。
    let db_mtime = std::fs::metadata(&db_path).and_then(|m| m.modified()).ok();
    let wal_mtime = std::fs::metadata(storage_dir.join("reader.db-wal"))
        .and_then(|m| m.modified())
        .ok();
    let change_mtime = db_mtime.max(wal_mtime);
    if let Some(db_m) = change_mtime {
        let newest_bak = newest_backup_mtime(storage_dir);
        if let Some(bak_m) = newest_bak {
            if bak_m >= db_m {
                tracing::info!(
                    "库未变化（最新备份 {:?} 不早于库 mtime {:?}），跳过启动备份",
                    bak_m,
                    change_mtime
                );
                return Ok(None);
            }
        }
    }

    // WAL 残留时先 checkpoint（保证副本一致；失败不阻塞备份流程）
    if storage_dir.join("reader.db-wal").exists() {
        let opts = sqlx::sqlite::SqliteConnectOptions::new().filename(&db_path);
        match opts.connect().await {
            Ok(mut conn) => {
                if let Err(e) = sqlx::query("PRAGMA wal_checkpoint(TRUNCATE)")
                    .execute(&mut conn)
                    .await
                {
                    tracing::warn!("备份前 WAL checkpoint 失败（副本可能缺失未落盘事务）: {e}");
                }
                // drop(conn)：关闭连接（sqlx 0.7 无显式 close）
            }
            Err(e) => {
                tracing::warn!("备份前打开数据库失败（跳过 checkpoint）: {e}");
            }
        }
    }

    let ts = chrono::Local::now().format("%Y%m%d%H%M%S%3f");
    // 同名防覆盖：同毫秒（快速连续备份/时钟精度不足）时追加 -1/-2 序号——
    // 否则 prune 排序会把新备份当旧备份删掉（CI tmpfs 上实测 flaky）
    let mut backup_path = storage_dir.join(format!("reader.db.bak-{ts}"));
    let mut seq = 0u32;
    while backup_path.exists() {
        seq += 1;
        backup_path = storage_dir.join(format!("reader.db.bak-{ts}-{seq}"));
    }
    std::fs::copy(&db_path, &backup_path)?;

    let pruned = prune_backups(storage_dir, KEEP_BACKUPS);
    tracing::info!(
        "数据库已备份 → {}（保留最近 {KEEP_BACKUPS} 份，清理 {pruned} 份旧备份）",
        backup_path.display()
    );
    Ok(Some(backup_path))
}

/// 备份侧车文件判定：`reader.db.bak-*-wal` / `reader.db.bak-*-shm`
/// （历史备份方式残留——现行单文件拷贝不产生侧车；混入 mtime 判定与保留计数会误事：
/// 新侧车 mtime 会让守卫误判"已备份"，prune 会把侧车当备份挤占保留名额）
fn is_backup_sidecar_name(name: &str) -> bool {
    (name.ends_with("-wal") || name.ends_with("-shm")) && name.starts_with("reader.db.bak-")
}

/// 主备份文件判定：`reader.db.bak-*` 且非侧车
fn is_main_backup_name(name: &str) -> bool {
    name.starts_with("reader.db.bak-") && !is_backup_sidecar_name(name)
}

/// 清理孤儿侧车（主 bak 文件已不存在的 -wal/-shm 残留）。返回删除数。
fn remove_orphan_backup_sidecars(storage_dir: &Path) -> usize {
    let Ok(entries) = std::fs::read_dir(storage_dir) else {
        return 0;
    };
    let mut deleted = 0;
    for e in entries.flatten() {
        let name = e.file_name().to_string_lossy().into_owned();
        if !is_backup_sidecar_name(&name) {
            continue;
        }
        // 主文件名 = 去掉 -wal/-shm 后缀
        let main_name = name
            .strip_suffix("-wal")
            .or_else(|| name.strip_suffix("-shm"))
            .unwrap_or(name.as_str());
        if !storage_dir.join(main_name).exists() {
            if std::fs::remove_file(e.path()).is_ok() {
                deleted += 1;
            }
        }
    }
    deleted
}

/// 现有 `reader.db.bak-*` 主备份中最新的 mtime（无备份 → None；侧车不计）
fn newest_backup_mtime(storage_dir: &Path) -> Option<std::time::SystemTime> {
    std::fs::read_dir(storage_dir)
        .ok()?
        .flatten()
        .map(|e| e.path())
        .filter(|p| {
            p.file_name()
                .map(|n| is_main_backup_name(&n.to_string_lossy()))
                .unwrap_or(false)
        })
        .filter_map(|p| std::fs::metadata(&p).and_then(|m| m.modified()).ok())
        .max()
}

/// 清理旧备份：仅保留最近 `keep` 份主备份 `reader.db.bak-*`（文件名时间戳定长，
/// 字典序 = 时间序；侧车不占名额）。返回删除数量（顺带清理孤儿侧车，计入总数）。
pub fn prune_backups(storage_dir: &Path, keep: usize) -> usize {
    let Ok(entries) = std::fs::read_dir(storage_dir) else {
        return 0;
    };
    let mut backups: Vec<PathBuf> = entries
        .flatten()
        .map(|e| e.path())
        .filter(|p| {
            p.file_name()
                .map(|n| is_main_backup_name(&n.to_string_lossy()))
                .unwrap_or(false)
        })
        .collect();
    backups.sort();
    let overflow = backups.len().saturating_sub(keep.max(1));
    let mut deleted = 0;
    for old in backups.into_iter().take(overflow) {
        if std::fs::remove_file(&old).is_ok() {
            deleted += 1;
        }
    }
    deleted + remove_orphan_backup_sidecars(storage_dir)
}

#[cfg(test)]
mod tests {
    use super::*;

    /// env 敏感测试串行锁：test_backup_disabled_and_missing_db 会全局设置/移除
    /// READER_DB_BACKUP——与其它调 backup_reader_db 的用例并发时对方偶发读到 "0"
    /// 而误判跳过（flaky），加锁串行（同 camoufox.rs ENV_LOCK 模式）
    static ENV_LOCK: std::sync::Mutex<()> = std::sync::Mutex::new(());

    /// 构造临时 storage 目录（带测试数据库文件）——每测试独立子目录（并行测试不串扰）
    fn temp_storage(tag: &str) -> PathBuf {
        let dir =
            std::env::temp_dir().join(format!("reader-db-backup-{}-{tag}", std::process::id()));
        let _ = std::fs::remove_dir_all(&dir);
        std::fs::create_dir_all(&dir).unwrap();
        dir
    }

    #[tokio::test]
    async fn test_backup_creates_copy_and_prunes() {
        let _g = ENV_LOCK.lock().unwrap();
        let dir = temp_storage("main");
        std::fs::write(dir.join("reader.db"), b"db-bytes").unwrap();
        // 预置 3 份旧备份（mtime 显式设为过去——启动守卫按 mtime 判"库未变则跳过"）
        let past = std::time::SystemTime::now() - std::time::Duration::from_secs(86_400);
        for ts in ["20240101000000", "20240102000000", "20240103000000"] {
            let p = dir.join(format!("reader.db.bak-{ts}"));
            std::fs::write(&p, b"old").unwrap();
            std::fs::File::options()
                .write(true)
                .open(&p)
                .unwrap()
                .set_modified(past)
                .unwrap();
        }
        let backup = backup_reader_db(&dir)
            .await
            .expect("备份应成功")
            .expect("应生成备份");
        let name = backup.file_name().unwrap().to_string_lossy().into_owned();
        assert!(name.starts_with("reader.db.bak-"), "命名: {name}");
        assert_eq!(std::fs::read(&backup).unwrap(), b"db-bytes", "内容一致");
        // 保留最近 3 份：3 旧 + 1 新 = 4 份 → 删最旧 1 份（20240101），剩 3
        let count = std::fs::read_dir(&dir)
            .unwrap()
            .filter(|e| {
                e.as_ref()
                    .unwrap()
                    .file_name()
                    .to_string_lossy()
                    .starts_with("reader.db.bak-")
            })
            .count();
        assert_eq!(count, KEEP_BACKUPS);
        assert!(
            !std::fs::read_dir(&dir).unwrap().any(|e| e
                .unwrap()
                .file_name()
                .to_string_lossy()
                .contains("20240101000000")),
            "最旧备份应被清理"
        );
        // 再备份 2 次 → 每次裁回最近 3（毫秒级时间戳避免同秒覆盖）
        for _ in 0..2 {
            std::fs::write(dir.join("reader.db"), b"db-bytes").unwrap();
            backup_reader_db(&dir).await.unwrap();
            // 60ms：CI tmpfs 复制极快——5ms 会与上次备份同毫秒导致同名覆盖（文件名仅毫秒精度）
            tokio::time::sleep(std::time::Duration::from_millis(60)).await;
        }
        let mut names: Vec<String> = std::fs::read_dir(&dir)
            .unwrap()
            .map(|e| e.unwrap().file_name().to_string_lossy().into_owned())
            .filter(|n| n.starts_with("reader.db.bak-"))
            .collect();
        names.sort();
        assert_eq!(names.len(), KEEP_BACKUPS, "保留最近 3 份: {names:?}");
        assert!(
            !names.iter().any(|n| n.contains("20240101000000")),
            "最旧备份应被清理: {names:?}"
        );
        let _ = std::fs::remove_dir_all(&dir);
    }

    #[tokio::test]
    async fn test_backup_skips_when_db_unchanged() {
        let _g = ENV_LOCK.lock().unwrap();
        let dir = temp_storage("skip");
        std::fs::write(dir.join("reader.db"), b"db-bytes").unwrap();
        let first = backup_reader_db(&dir).await.unwrap();
        assert!(first.is_some(), "库新建后应备份");
        // 库未变化（mtime 不晚于最新备份）→ 跳过
        let second = backup_reader_db(&dir).await.unwrap();
        assert!(second.is_none(), "库未变化应跳过启动备份");
        // 库变化（mtime 更新）→ 重新备份
        std::thread::sleep(std::time::Duration::from_millis(10));
        std::fs::write(dir.join("reader.db"), b"db-bytes-2").unwrap();
        let third = backup_reader_db(&dir).await.unwrap();
        assert!(third.is_some(), "库变化后应重新备份");
        let _ = std::fs::remove_dir_all(&dir);
    }

    #[tokio::test]
    async fn test_backup_disabled_and_missing_db() {
        let _g = ENV_LOCK.lock().unwrap();
        let dir = temp_storage("disabled");
        // 无库文件 → None
        assert!(backup_reader_db(&dir).await.unwrap().is_none());
        // READER_DB_BACKUP=0 → None
        std::fs::write(dir.join("reader.db"), b"x").unwrap();
        std::env::set_var("READER_DB_BACKUP", "0");
        assert!(backup_reader_db(&dir).await.unwrap().is_none());
        std::env::remove_var("READER_DB_BACKUP");
        let _ = std::fs::remove_dir_all(&dir);
    }

    #[test]
    fn test_prune_backups_keep_count() {
        let dir = temp_storage("prune");
        for ts in [
            "20240101000000",
            "20240102000000",
            "20240103000000",
            "20240104000000",
        ] {
            std::fs::write(dir.join(format!("reader.db.bak-{ts}")), b"old").unwrap();
        }
        // 无关文件不受影响
        std::fs::write(dir.join("reader.db"), b"db").unwrap();
        assert_eq!(prune_backups(&dir, 2), 2, "删 2 留 2");
        let remaining: Vec<String> = std::fs::read_dir(&dir)
            .unwrap()
            .map(|e| e.unwrap().file_name().to_string_lossy().into_owned())
            .filter(|n| n.starts_with("reader.db.bak-"))
            .collect();
        assert_eq!(remaining.len(), 2);
        assert!(remaining.iter().any(|n| n.contains("20240104000000")));
        assert!(
            std::fs::read_dir(&dir).unwrap().count() >= 3,
            "reader.db 不受影响"
        );
        let _ = std::fs::remove_dir_all(&dir);
    }

    /// 守卫计入 wal mtime：主库 mtime 不动、仅 wal 更新（WAL 模式常态）→ 不应跳过备份
    #[tokio::test]
    async fn test_backup_guard_counts_wal_mtime() {
        let _g = ENV_LOCK.lock().unwrap();
        let dir = temp_storage("walmtime");
        std::fs::write(dir.join("reader.db"), b"db-bytes").unwrap();
        let first = backup_reader_db(&dir).await.unwrap();
        assert!(first.is_some(), "库新建后应备份");
        // 主库 mtime 未变（最新备份 mtime ≥ 主库 mtime）——若无 wal 只看主库会跳过；
        // 写入 wal 且 mtime 拉到最新备份之后 → 视为库已变化，必须重新备份
        let wal = dir.join("reader.db-wal");
        std::fs::write(&wal, b"w").unwrap();
        let future = std::time::SystemTime::now() + std::time::Duration::from_secs(10);
        std::fs::File::options()
            .write(true)
            .open(&wal)
            .unwrap()
            .set_modified(future)
            .unwrap();
        let second = backup_reader_db(&dir).await.unwrap();
        assert!(second.is_some(), "wal 更新应视为库变化，不应跳过备份");
        let _ = std::fs::remove_dir_all(&dir);
    }

    /// 侧车不占保留名额、不参与最新备份判定；主 bak 删除后的侧车按孤儿清理
    #[test]
    fn test_sidecars_excluded_and_reaped() {
        let dir = temp_storage("sidecar");
        let a = dir.join("reader.db.bak-20240101000000");
        let b = dir.join("reader.db.bak-20240102000000");
        let c = dir.join("reader.db.bak-20240103000000");
        for p in [&a, &b, &c] {
            std::fs::write(p, b"old").unwrap();
        }
        // a 的侧车（主 bak 存在时暂不删）+ 孤儿侧车（主 bak 从不存在）
        let a_wal = dir.join("reader.db.bak-20240101000000-wal");
        std::fs::write(&a_wal, b"w").unwrap();
        let orphan_wal = dir.join("reader.db.bak-20231231000000-wal");
        let orphan_shm = dir.join("reader.db.bak-20231231000000-shm");
        std::fs::write(&orphan_wal, b"w").unwrap();
        std::fs::write(&orphan_shm, b"s").unwrap();

        // 孤儿清理：只删主 bak 不存在的侧车（a-wal 保留）
        assert_eq!(remove_orphan_backup_sidecars(&dir), 2);
        assert!(!orphan_wal.exists() && !orphan_shm.exists());
        assert!(a_wal.exists(), "主 bak 在的侧车暂不删");

        // prune(2)：侧车不占名额 → 删最旧主 a（1 份）+ 顺带清 a-wal 孤儿（1 份）= 2
        assert_eq!(prune_backups(&dir, 2), 2);
        assert!(!a.exists() && !a_wal.exists(), "最旧主与其侧车都应清理");
        assert!(b.exists() && c.exists(), "保留最近 2 份主备份");
        let _ = std::fs::remove_dir_all(&dir);
    }

    /// newest_backup_mtime 不被侧车抬高（侧车 mtime 再新也不算备份）
    #[test]
    fn test_newest_backup_mtime_ignores_sidecars() {
        let dir = temp_storage("newest");
        let main = dir.join("reader.db.bak-20240101000000");
        std::fs::write(&main, b"old").unwrap();
        let past = std::time::SystemTime::now() - std::time::Duration::from_secs(3600);
        std::fs::File::options()
            .write(true)
            .open(&main)
            .unwrap()
            .set_modified(past)
            .unwrap();
        let sidecar = dir.join("reader.db.bak-20240101000000-wal");
        std::fs::write(&sidecar, b"w").unwrap(); // mtime = now（比主新）
        let newest = newest_backup_mtime(&dir).unwrap();
        assert!(
            newest < std::time::SystemTime::now() - std::time::Duration::from_secs(1800),
            "最新备份 mtime 应取主文件（过去），侧车（现在）不计入: {newest:?}"
        );
        let _ = std::fs::remove_dir_all(&dir);
    }
}
