//! 替换规则实体（兼容 legado ReplaceRule / replaceRule.json）
//!
//! - serde：`enabled`/`order`（与前端 ReplaceRule 类型一致）
//! - sqlx：列名 `enable` / `order_num`（order 为 SQLite 关键字）

use serde::{Deserialize, Serialize};
use sqlx::FromRow;

#[derive(Debug, Clone, Serialize, Deserialize, FromRow)]
#[serde(default)]
pub struct ReplaceRule {
    /// 规则 id（前端生成字符串 id；后端缺失时补 uuid）
    pub id: String,
    /// 规则名称（必填）
    pub name: String,
    /// 规则分组（legacy ReplaceRule.group）
    #[sqlx(rename = "group_name")]
    pub group: Option<String>,
    /// 查找内容（必填）
    #[serde(alias = "pattern")]
    pub find: String,
    /// 替换为（可空 = 删除匹配文字）
    #[serde(alias = "replacement")]
    pub replace: String,
    /// 替换范围（legacy ReplaceRule.scope；空 = 全部正文）
    pub scope: Option<String>,
    /// 是否替换标题（legacy scopeTitle）
    #[serde(rename = "scopeTitle")]
    #[sqlx(rename = "scope_title")]
    pub scope_title: bool,
    /// 是否替换正文（legacy scopeContent）
    #[serde(rename = "scopeContent")]
    #[sqlx(rename = "scope_content")]
    pub scope_content: bool,
    /// 是否启用正则匹配（legacy isRegex）
    #[serde(rename = "isRegex")]
    #[sqlx(rename = "is_regex")]
    pub is_regex: bool,
    /// 正则执行超时毫秒（legacy timeoutMillisecond，默认 3000）
    #[serde(rename = "timeoutMillisecond")]
    #[sqlx(rename = "timeout_millisecond")]
    pub timeout_millisecond: i64,
    /// 是否启用（列名 enable，legacy 兼容）
    #[serde(alias = "isEnabled")]
    #[sqlx(rename = "enable")]
    pub enabled: bool,
    /// 排序（order 为 SQLite 关键字 → 列名 order_num）
    #[sqlx(rename = "order_num")]
    pub order: i64,
    /// 命名空间（secure 模式用户名 / default）
    #[serde(skip)]
    #[sqlx(rename = "user_namespace")]
    pub user_namespace: String,
}

impl Default for ReplaceRule {
    fn default() -> Self {
        Self {
            id: String::new(),
            name: String::new(),
            group: None,
            find: String::new(),
            replace: String::new(),
            scope: None,
            scope_title: false,
            scope_content: true,
            is_regex: false,
            timeout_millisecond: 3000,
            enabled: true,
            order: 0,
            user_namespace: String::new(),
        }
    }
}
