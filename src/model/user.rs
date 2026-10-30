//! 用户实体（兼容 legacy User / users.json 全字段，JSON 字段 snake_case 与 legacy 一致）

use serde::{Deserialize, Serialize};
use sqlx::FromRow;

#[derive(Debug, Clone, Default, Serialize, Deserialize, FromRow)]
#[serde(default)]
pub struct User {
    pub username: String,
    pub password: String,
    pub salt: String,
    pub token: String,
    /// 多会话 token → 过期时间（legacy token_map）
    #[serde(default, skip_serializing_if = "Option::is_none")]
    pub token_map: Option<serde_json::Value>,
    pub enable_webdav: bool,
    pub enable_local_store: bool,
    pub enable_book_source: bool,
    pub enable_rss_source: bool,
    pub book_source_limit: i64,
    pub book_limit: i64,
    /// 管理员（secure 模式下可修改 default 系统配置；首个注册用户自动成为管理员）
    #[serde(default)]
    pub is_admin: bool,
    pub last_login_at: i64,
    pub created_at: i64,
    #[serde(skip)]
    #[sqlx(rename = "user_namespace")]
    pub user_namespace: String,
    /// 迁移保底：原始 JSON 全量
    #[serde(skip)]
    #[sqlx(rename = "raw_json")]
    pub raw_json: Option<String>,
}

// ==================== GAP 59 多设备 token（users.token_map） ====================
//
// token_map 存储为 JSON 对象 Map<token, 过期毫秒时间戳>（与 legacy 完全一致）；
// 兼容旧数据形态（JSON 数组——纯 token 列表，无过期信息，视为长期有效）。

/// 会话 token 上限（GAP 59：多设备登录最多 5 个有效 token）
pub const MAX_USER_TOKENS: usize = 5;

/// 解析 token_map → token 列表（None / 非法 JSON / 空 → 空列表）
pub fn token_map_list(token_map: &Option<serde_json::Value>) -> Vec<String> {
    let Some(v) = token_map else { return vec![] };
    match v {
        serde_json::Value::Array(arr) => arr
            .iter()
            .filter_map(|x| x.as_str().map(str::to_string))
            .filter(|s| !s.is_empty())
            .collect(),
        // legacy 形态：{token: 过期时间戳}——键即 token
        serde_json::Value::Object(map) => map.keys().cloned().collect(),
        _ => vec![],
    }
}

/// token_map 是否包含指定 token（GAP 59：resolve_namespace 任一 token 均可通过）
pub fn token_map_contains(token_map: &Option<serde_json::Value>, token: &str) -> bool {
    token_map_list(token_map).iter().any(|t| t == token)
}

/// token 是否有效（含过期检查）：对象形态按过期时间戳判定（已过期 → false）；
/// 数组形态（旧数据）无过期信息 → 视为有效。
pub fn token_map_valid(token_map: &Option<serde_json::Value>, token: &str, now_ms: i64) -> bool {
    let Some(v) = token_map else { return false };
    match v {
        serde_json::Value::Object(map) => match map.get(token) {
            Some(serde_json::Value::Number(n)) => {
                n.as_i64().map(|exp| exp > now_ms).unwrap_or(false)
            }
            // 无过期信息（非数字值/手改数据）→ 视为有效
            Some(_) => true,
            None => false,
        },
        _ => token_map_contains(token_map, token),
    }
}

/// 追加 token（去重；带过期时间戳；清理已过期项；超出上限丢最旧），
/// 返回新 token_map JSON 字符串（legacy 对象形态 Map<token, expire_ms>）。
/// expire_ms 为登录时生成的过期时间戳（ttl<=0 永不过期 → i64::MAX）；
/// 校验路径的滑动续期（storage::touch_user_token）会把活跃 token 的 expire
/// 刷新为「最近使用 + ttl」——故下方的上限淘汰（丢 expire 最早）即丢弃
/// 最久未使用的会话（LRU 语义，活跃会话不会被后登录者挤出）。
pub fn token_map_push(
    token_map: &Option<serde_json::Value>,
    token: &str,
    expire_ms: i64,
    now_ms: i64,
) -> String {
    // 用 Vec 显式管理顺序（不依赖 serde_json::Map 内部顺序，裁剪语义可预测）
    let mut entries: Vec<(String, i64)> = match token_map {
        Some(serde_json::Value::Object(m)) => m
            .iter()
            .filter_map(|(k, v)| v.as_i64().map(|exp| (k.clone(), exp)))
            .collect(),
        // 旧数组形态：转为对象，无过期信息 → 按主 token 同规则（expire_ms）续期
        _ => token_map_list(token_map)
            .into_iter()
            .map(|t| (t, expire_ms))
            .collect(),
    };
    // 清理已过期 token（legacy：tokenMap.values.removeAll { it < now }）
    entries.retain(|(_, exp)| *exp > now_ms);
    // 去重更新 / 追加本 token
    if let Some(e) = entries.iter_mut().find(|(k, _)| *k == token) {
        e.1 = expire_ms;
    } else {
        entries.push((token.to_string(), expire_ms));
    }
    // 上限 5：按过期时间升序丢最旧（相等时保留先插入者，避免误丢最新 token）
    while entries.len() > MAX_USER_TOKENS {
        let min_idx = entries
            .iter()
            .enumerate()
            .min_by_key(|(_, (_, exp))| *exp)
            .map(|(i, _)| i)
            .unwrap_or(0);
        entries.remove(min_idx);
    }
    let map: serde_json::Map<String, serde_json::Value> = entries
        .into_iter()
        .map(|(k, exp)| (k, serde_json::json!(exp)))
        .collect();
    serde_json::to_string(&serde_json::Value::Object(map))
        .unwrap_or_else(|_| serde_json::json!({}).to_string())
}

/// 移除 token，返回 (移除后 token_map JSON, 是否确实移除了该 token)
pub fn token_map_remove(token_map: &Option<serde_json::Value>, token: &str) -> (String, bool) {
    let mut entries: Vec<(String, i64)> = match token_map {
        Some(serde_json::Value::Object(m)) => m
            .iter()
            .filter_map(|(k, v)| v.as_i64().map(|exp| (k.clone(), exp)))
            .collect(),
        _ => token_map_list(token_map)
            .into_iter()
            .map(|t| (t, 0))
            .collect(),
    };
    let before = entries.len();
    entries.retain(|(k, _)| *k != token);
    let removed = entries.len() != before;
    let map: serde_json::Map<String, serde_json::Value> = entries
        .into_iter()
        .map(|(k, exp)| (k, serde_json::json!(exp)))
        .collect();
    let json = serde_json::to_string(&serde_json::Value::Object(map))
        .unwrap_or_else(|_| serde_json::json!({}).to_string());
    (json, removed)
}

/// 滑动续期节流判断（storage::touch_user_token 与调用方前置判断共用）：
/// - token 在对象形态 token_map 中 → 其 expire 已进入后半程（剩余不足 ttl/2）才需续期；
/// - 不在其中（主 token legacy 场景 / 数组形态旧数据）→ 距整体活跃（last_login_at）
///   超过 ttl/2 才需续期（该场景的过期机制即 GAP 118 整体判定）。
/// 按 token 自身 expire 而非共享 last_login_at 节流：多设备各自独立——
/// 其他设备刚登录不会阻止本设备的旧 token 续期。
pub fn token_needs_renewal(
    token_map: &Option<serde_json::Value>,
    token: &str,
    last_login_at: i64,
    now_ms: i64,
    ttl_ms: i64,
) -> bool {
    let cur_expire = match token_map.as_ref() {
        Some(serde_json::Value::Object(m)) => m.get(token).and_then(|x| x.as_i64()),
        _ => None,
    };
    match cur_expire {
        Some(exp) => now_ms.saturating_add(ttl_ms / 2) >= exp,
        None => now_ms.saturating_sub(last_login_at) > ttl_ms / 2,
    }
}

/// 后台清理：移除 token_map 中已过期（expire <= now_ms）的条目，
/// 返回 (清理后 token_map JSON, 清理条数)。数组形态（旧数据，无过期信息）不动。
/// drop_all=true 时全清（GAP 118 整体判定过期的用户——30 天未活跃，token 全部失效）。
pub fn token_map_prune_expired(
    token_map: &Option<serde_json::Value>,
    now_ms: i64,
    drop_all: bool,
) -> (String, usize) {
    let Some(serde_json::Value::Object(m)) = token_map else {
        return (serde_json::json!({}).to_string(), 0); // None / 数组形态：无 JSON 可写
    };
    let before = m.len();
    let entries: Vec<(String, i64)> = m
        .iter()
        .filter_map(|(k, v)| v.as_i64().map(|exp| (k.clone(), exp)))
        .filter(|(_, exp)| !drop_all && *exp > now_ms)
        .collect();
    let pruned = before - entries.len();
    let map: serde_json::Map<String, serde_json::Value> = entries
        .into_iter()
        .map(|(k, exp)| (k, serde_json::json!(exp)))
        .collect();
    let json = serde_json::to_string(&serde_json::Value::Object(map))
        .unwrap_or_else(|_| serde_json::json!({}).to_string());
    (json, pruned)
}

#[cfg(test)]
mod tests {
    use super::*;
    use serde_json::json;

    #[test]
    fn test_token_map_list_formats() {
        // 数组形态（旧数据兼容）
        let m = Some(json!(["t1", "t2"]));
        assert_eq!(token_map_list(&m), vec!["t1", "t2"]);
        // legacy 对象形态（键为 token）
        let m = Some(json!({"t1": 1700000000000i64, "t2": 1700000001000i64}));
        let mut list = token_map_list(&m);
        list.sort();
        assert_eq!(list, vec!["t1", "t2"]);
        // None / 非法
        assert!(token_map_list(&None).is_empty());
        assert!(token_map_list(&Some(json!("x"))).is_empty());
    }

    #[test]
    fn test_token_map_push_dedup_and_cap() {
        let now = 1_700_000_000_000i64;
        let mut m: Option<serde_json::Value> = None;
        for i in 0..7 {
            m = Some(
                serde_json::from_str(&token_map_push(
                    &m,
                    &format!("t{i}"),
                    now + 86_400_000 * 7,
                    now,
                ))
                .unwrap(),
            );
        }
        let list = token_map_list(&m);
        assert_eq!(list.len(), MAX_USER_TOKENS, "上限 5");
        assert_eq!(list, vec!["t2", "t3", "t4", "t5", "t6"], "最旧被丢");
        // 去重：重新推入已存在的 token 不重复
        let m = Some(json!({"t2": now + 1, "t3": now + 1}));
        let m2 = serde_json::from_str::<serde_json::Value>(&token_map_push(&m, "t3", now + 2, now))
            .unwrap();
        assert_eq!(token_map_list(&Some(m2)), vec!["t2", "t3"]);
    }

    #[test]
    fn test_token_map_contains_and_remove() {
        let m = Some(json!({"t1": 1, "t2": 2}));
        assert!(token_map_contains(&m, "t1"));
        assert!(token_map_contains(&m, "t2"));
        assert!(!token_map_contains(&m, "t3"));
        assert!(!token_map_contains(&None, ""));
        let (json, removed) = token_map_remove(&m, "t1");
        assert!(removed);
        let v: serde_json::Value = serde_json::from_str(&json).unwrap();
        assert_eq!(token_map_list(&Some(v)), vec!["t2"]);
        let (json, removed) = token_map_remove(&Some(json!({"t1": 1})), "nope");
        assert!(!removed);
        let v: serde_json::Value = serde_json::from_str(&json).unwrap();
        assert_eq!(token_map_list(&Some(v)), vec!["t1"]);
    }

    #[test]
    fn test_token_map_valid_expiry() {
        let now = 1_700_000_000_000i64;
        let m = Some(json!({"fresh": now + 1000, "stale": now - 1000, "no_exp": "x"}));
        assert!(token_map_valid(&m, "fresh", now));
        assert!(!token_map_valid(&m, "stale", now), "过期 token 失效");
        assert!(token_map_valid(&m, "no_exp", now), "无过期信息 → 视为有效");
        assert!(!token_map_valid(&m, "missing", now));
        // 旧数组形态：无过期信息 → 视为有效
        assert!(token_map_valid(&Some(json!(["t1"])), "t1", now));
    }

    #[test]
    fn test_token_map_push_prunes_expired() {
        let now = 1_700_000_000_000i64;
        let m = Some(json!({"stale": now - 1, "fresh": now + 1000}));
        let m2 = serde_json::from_str::<serde_json::Value>(&token_map_push(
            &m,
            "new_tok",
            now + 1000,
            now,
        ))
        .unwrap();
        let list = token_map_list(&Some(m2));
        assert_eq!(list, vec!["fresh", "new_tok"], "过期项被清理");
    }

    #[test]
    fn test_token_needs_renewal() {
        let now = 1_700_000_000_000i64;
        let ttl = 30 * 86_400_000i64;
        let m = Some(json!({
            // 剩余 29 天（前半程）→ 不需续期
            "early": now + 29 * 86_400_000,
            // 剩余 10 天（后半程）→ 需续期
            "late": now + 10 * 86_400_000,
        }));
        assert!(!token_needs_renewal(&m, "early", now, now, ttl));
        assert!(token_needs_renewal(&m, "late", now, now, ttl));
        // 不在对象 map 中（主 token legacy / 数组形态）→ 按整体活跃（last_login_at）判断
        assert!(
            !token_needs_renewal(&m, "ghost", now, now, ttl),
            "整体活跃在窗口内"
        );
        assert!(
            token_needs_renewal(&m, "ghost", now - 20 * 86_400_000, now, ttl),
            "距上次活跃超 ttl/2 → 需续期"
        );
        // 数组形态（旧数据）：同样按整体活跃判断
        let arr = Some(json!(["t1"]));
        assert!(token_needs_renewal(&arr, "t1", 0, now, ttl));
    }

    #[test]
    fn test_token_map_prune_expired() {
        let now = 1_700_000_000_000i64;
        let m = Some(json!({"stale": now - 1, "fresh": now + 1000, "no_exp": "x"}));
        let (json, pruned) = token_map_prune_expired(&m, now, false);
        let v: serde_json::Value = serde_json::from_str(&json).unwrap();
        assert_eq!(token_map_list(&Some(v)), vec!["fresh"], "仅过期项被清");
        assert_eq!(pruned, 2, "stale 与非数字值均计为清理");
        // drop_all（GAP 118 整体过期用户）→ 全清
        let (json, pruned) = token_map_prune_expired(&m, now, true);
        let v: serde_json::Value = serde_json::from_str(&json).unwrap();
        assert!(token_map_list(&Some(v)).is_empty(), "整体过期全清");
        assert_eq!(pruned, 3);
        // 数组形态（旧数据）不动 → 0 条（不写库）
        let (_, pruned) = token_map_prune_expired(&Some(json!(["t1"])), now, true);
        assert_eq!(pruned, 0);
    }
}
