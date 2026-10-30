//! 登录限流（GAP 61）：内存计数（入口 + 用户名 + IP）
//!
//! - 失败 5 次 → 锁定 5 分钟（返回「尝试过多请稍后」）；
//! - 成功登录 → 计数清零；
//! - 计数键带入口维度（login / webdav / opds）——同账号同 IP 下，某个入口的
//!   连续失败（如 WebDAV 备份端配错密码）不再连坐其他入口的正常登录；
//! - 纯内存（进程内有效；多实例部署需外部限流，见报告说明）。

/// 计数入口：/reader3/login
pub const SCOPE_LOGIN: &str = "login";
/// 计数入口：WebDAV Basic 认证
pub const SCOPE_WEBDAV: &str = "webdav";
/// 计数入口：OPDS Basic 认证
pub const SCOPE_OPDS: &str = "opds";

use std::collections::HashMap;
use std::sync::{LazyLock, Mutex};
use std::time::{Duration, Instant};

/// 失败次数阈值（达到即锁定）
pub const MAX_LOGIN_FAILS: u32 = 5;
/// 锁定时间（5 分钟）
pub const LOCK_DURATION: Duration = Duration::from_secs(300);
/// 内存表上限（防滥用增长；超限时清理过期条目）
const MAX_ENTRIES: usize = 8192;

struct Entry {
    fails: u32,
    /// 最近一次失败时间（过期清理用）
    last_fail: Instant,
    /// 锁定截止（None = 未锁定）
    locked_until: Option<Instant>,
}

static FAILS: LazyLock<Mutex<HashMap<String, Entry>>> =
    LazyLock::new(|| Mutex::new(HashMap::new()));

fn lock() -> std::sync::MutexGuard<'static, HashMap<String, Entry>> {
    FAILS.lock().unwrap_or_else(|e| e.into_inner())
}

/// 登录前检查：锁定中 → Err("尝试过多请稍后")
pub fn check_allowed(scope: &str, username: &str, ip: &str) -> Result<(), String> {
    let mut map = lock();
    prune(&mut map);
    let Some(entry) = map.get_mut(&key(scope, username, ip)) else {
        return Ok(());
    };
    if let Some(until) = entry.locked_until {
        if Instant::now() < until {
            return Err("尝试过多请稍后".to_string());
        }
        // 锁定期已过：清除锁定（保留计数，重新累计）
        entry.locked_until = None;
    }
    Ok(())
}

/// 记录一次登录失败（达到阈值 → 锁定）
pub fn record_failure(scope: &str, username: &str, ip: &str) {
    let mut map = lock();
    prune(&mut map);
    let entry = map.entry(key(scope, username, ip)).or_insert(Entry {
        fails: 0,
        last_fail: Instant::now(),
        locked_until: None,
    });
    entry.fails += 1;
    entry.last_fail = Instant::now();
    // 每次失败都留痕（此前仅第 5 次锁定才告警——前 4 次完全不可见，误锁难定位）
    tracing::info!("登录失败 [{scope}] {username}@{ip}：第 {} 次", entry.fails);
    if entry.fails >= MAX_LOGIN_FAILS {
        entry.locked_until = Some(Instant::now() + LOCK_DURATION);
        tracing::warn!(
            "登录失败 {} 次（[{scope}] {username}@{ip}）——锁定 {} 秒",
            entry.fails,
            LOCK_DURATION.as_secs()
        );
    }
}

/// 登录成功：清除计数与锁定
pub fn reset(scope: &str, username: &str, ip: &str) {
    let mut map = lock();
    map.remove(&key(scope, username, ip));
}

fn key(scope: &str, username: &str, ip: &str) -> String {
    format!("{scope}|{username}|{ip}")
}

/// 过期清理：非锁定且距上次失败超过锁定窗口的条目移除；表超限时全量清理过期
fn prune(map: &mut HashMap<String, Entry>) {
    if map.len() < MAX_ENTRIES {
        map.retain(|_, e| {
            e.locked_until.map(|u| Instant::now() < u).unwrap_or(false)
                || e.last_fail.elapsed() < LOCK_DURATION
        });
    } else {
        map.retain(|_, e| e.locked_until.map(|u| Instant::now() < u).unwrap_or(false));
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_lock_after_5_failures() {
        // 前 4 次失败：仍允许
        for _ in 0..4 {
            assert!(check_allowed(SCOPE_LOGIN, "alice", "1.2.3.4").is_ok());
            record_failure(SCOPE_LOGIN, "alice", "1.2.3.4");
        }
        // 第 5 次失败 → 锁定
        record_failure(SCOPE_LOGIN, "alice", "1.2.3.4");
        let err = check_allowed(SCOPE_LOGIN, "alice", "1.2.3.4").unwrap_err();
        assert_eq!(err, "尝试过多请稍后");
        // 其他用户/IP 不受影响
        assert!(check_allowed(SCOPE_LOGIN, "alice", "5.6.7.8").is_ok());
        assert!(check_allowed(SCOPE_LOGIN, "bob", "1.2.3.4").is_ok());
        // 锁定期内持续拒绝
        assert!(check_allowed(SCOPE_LOGIN, "alice", "1.2.3.4").is_err());
        // 其他入口（webdav/opds）独立计数——/login 锁定不连坐 WebDAV 备份等
        assert!(check_allowed(SCOPE_WEBDAV, "alice", "1.2.3.4").is_ok());
        assert!(check_allowed(SCOPE_OPDS, "alice", "1.2.3.4").is_ok());
    }

    #[test]
    fn test_success_resets_counter() {
        for _ in 0..4 {
            record_failure(SCOPE_LOGIN, "carol", "9.9.9.9");
        }
        reset(SCOPE_LOGIN, "carol", "9.9.9.9");
        assert!(check_allowed(SCOPE_LOGIN, "carol", "9.9.9.9").is_ok());
        // 重置后重新累计：再失败 4 次仍不锁（需满 5 次）
        for _ in 0..4 {
            record_failure(SCOPE_LOGIN, "carol", "9.9.9.9");
        }
        assert!(check_allowed(SCOPE_LOGIN, "carol", "9.9.9.9").is_ok());
        record_failure(SCOPE_LOGIN, "carol", "9.9.9.9");
        assert!(check_allowed(SCOPE_LOGIN, "carol", "9.9.9.9").is_err());
    }

    #[test]
    fn test_lock_expires() {
        for _ in 0..5 {
            record_failure(SCOPE_LOGIN, "dave", "1.1.1.1");
        }
        assert!(check_allowed(SCOPE_LOGIN, "dave", "1.1.1.1").is_err());
        // 手动把锁定截止拨到过去（模拟 5 分钟流逝）→ 解锁
        {
            let mut map = lock();
            let e = map.get_mut("login|dave|1.1.1.1").unwrap();
            e.locked_until = Some(Instant::now() - Duration::from_secs(1));
        }
        assert!(
            check_allowed(SCOPE_LOGIN, "dave", "1.1.1.1").is_ok(),
            "锁定过期应恢复"
        );
    }
}
