//! 文件系统扫描节流（网盘目录挂载风控保护）
//!
//! 本地书仓把网络盘目录挂载成本地目录后，目录递归扫描会在短时间内对同一目录
//! 发起大量 readdir/stat/read——网盘 API 容易触发风控。这里提供全局令牌间隔：
//! 每次文件系统操作前调用 [`tick`]，保证每秒最多 `READER_DIR_SCAN_RPS` 次
//! （默认 20，范围 1-500；设为 0 表示不限速）。

use std::sync::{LazyLock, Mutex};
use std::time::{Duration, Instant};

/// 默认每秒文件系统操作数（网盘安全优先；本地磁盘可调大）
pub const DEFAULT_RPS: f64 = 20.0;
const MAX_RPS: f64 = 500.0;

/// 令牌间隔状态（可独立构造供测试）
#[derive(Debug)]
pub struct FsRate {
    interval: Duration,
    next_at: Option<Instant>,
}

impl FsRate {
    pub fn new(per_second: f64) -> Self {
        let interval = if per_second.is_finite() && per_second > 0.0 {
            Duration::from_secs_f64(1.0 / per_second.min(MAX_RPS))
        } else {
            Duration::ZERO
        };
        Self {
            interval,
            next_at: None,
        }
    }

    /// 返回下一次 tick 应等待的时长并推进令牌；间隔为 0 时立即返回。
    pub fn tick_sync(&mut self) -> Duration {
        if self.interval.is_zero() {
            return Duration::ZERO;
        }
        let now = Instant::now();
        let next = self.next_at.unwrap_or(now);
        let wait = next.saturating_duration_since(now);
        self.next_at = Some(if now >= next {
            now + self.interval
        } else {
            next + self.interval
        });
        wait
    }
}

fn global_rps() -> f64 {
    std::env::var("READER_DIR_SCAN_RPS")
        .ok()
        .and_then(|v| v.trim().parse::<f64>().ok())
        .unwrap_or(DEFAULT_RPS)
}

static GLOBAL: LazyLock<Mutex<FsRate>> = LazyLock::new(|| Mutex::new(FsRate::new(global_rps())));

/// 每次文件系统扫描操作前调用：全局限速（await 到允许的间隔）。
pub async fn tick() {
    let wait = GLOBAL.lock().unwrap_or_else(|e| e.into_inner()).tick_sync();
    if !wait.is_zero() {
        tokio::time::sleep(wait).await;
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_fs_rate_spaces_ticks_at_requested_rps() {
        let mut rate = FsRate::new(10.0);
        // 首次立即通过
        assert_eq!(rate.tick_sync(), Duration::ZERO);
        // 随后每次至少间隔 100ms
        let wait = rate.tick_sync();
        assert!(
            wait >= Duration::from_millis(99) && wait <= Duration::from_millis(200),
            "10 rps 间隔应约 100ms: {wait:?}"
        );
    }

    #[test]
    fn test_fs_rate_zero_disables_limiting() {
        let mut rate = FsRate::new(0.0);
        assert_eq!(rate.tick_sync(), Duration::ZERO);
        assert_eq!(rate.tick_sync(), Duration::ZERO);
    }
}
