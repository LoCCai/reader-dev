//! 书源登录态（表：book_source_cookies）——Cookie 管理读取视图

use serde::{Deserialize, Serialize};
use sqlx::FromRow;

/// 单个书源登录态行（Cookie 管理列表）
#[derive(Debug, Clone, Default, Serialize, Deserialize, FromRow)]
#[serde(default)]
pub struct CookieRow {
    /// 书源地址（主键；可能带 `##` 备用地址后缀）
    pub source_url: String,
    /// Cookie 原文（用户本人可见，前端仅展示摘要）
    pub cookie: String,
    /// 浏览器求解/登录后记录的 User-Agent（部分站点 UA 绑定 cookie）
    pub user_agent: String,
    /// 登录成功后 JS 保存的附加请求头
    pub login_header: String,
    /// 最近更新时间（毫秒时间戳）
    pub updated_at: i64,
}
