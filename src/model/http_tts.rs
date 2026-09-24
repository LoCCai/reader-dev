//! HttpTTS 听书源实体（兼容 legado HttpTTS / httpTTS.json）
//!
//! - 表主键：url（任务规格：http_tts_list 表 = url/name/type/user_namespace）
//! - 输出 JSON：`id` 与 `url` 同值（前端 HttpTts 类型 id 兼容；legacy HttpTTS 为 Long id）
//! - type：0=在线合成（http 请求音频），1=本地引擎（预留）

use serde::{Deserialize, Serialize};
use sqlx::FromRow;

#[derive(Debug, Clone, Default, Serialize, Deserialize, FromRow)]
#[serde(default)]
pub struct HttpTts {
    /// 听书源 URL（主键；JSON 输出时同时提供 id 字段，见 handler 的 http_tts_json）
    pub url: String,
    /// 名称（必填）
    pub name: String,
    /// 类型（0=在线合成 / 1=本地引擎；type 为 Rust 关键字 → r#type）
    #[serde(rename = "type")]
    #[sqlx(rename = "type")]
    pub tts_type: i64,
    /// 响应 Content-Type（legacy contentType；为空时按音频流处理）
    #[serde(rename = "contentType")]
    #[sqlx(rename = "content_type")]
    pub content_type: Option<String>,
    /// 并发率（legacy concurrentRate，默认 "0" = 不限制）
    #[serde(rename = "concurrentRate")]
    #[sqlx(rename = "concurrent_rate")]
    pub concurrent_rate: Option<String>,
    /// 登录页地址（legacy loginUrl）
    #[serde(rename = "loginUrl")]
    #[sqlx(rename = "login_url")]
    pub login_url: Option<String>,
    /// 登录 UI 配置（legacy loginUi，JSON 字符串）
    #[serde(rename = "loginUi")]
    #[sqlx(rename = "login_ui")]
    pub login_ui: Option<String>,
    /// 请求头 JSON（legacy header）
    pub header: Option<String>,
    /// JS 依赖库（legacy jsLib）
    #[serde(rename = "jsLib")]
    #[sqlx(rename = "js_lib")]
    pub js_lib: Option<String>,
    /// 是否启用 Cookie Jar（legacy enabledCookieJar）
    #[serde(rename = "enabledCookieJar")]
    #[sqlx(rename = "enabled_cookie_jar")]
    pub enabled_cookie_jar: Option<bool>,
    /// 登录校验 JS（legacy loginCheckJs）
    #[serde(rename = "loginCheckJs")]
    #[sqlx(rename = "login_check_js")]
    pub login_check_js: Option<String>,
    /// 最近更新时间（毫秒时间戳；legacy lastUpdateTime）
    #[serde(rename = "lastUpdateTime")]
    #[sqlx(rename = "last_update_time")]
    pub last_update_time: i64,
    /// 命名空间（secure 模式用户名 / default）
    #[serde(skip)]
    #[sqlx(rename = "user_namespace")]
    pub user_namespace: String,
}
