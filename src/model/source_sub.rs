//! 书源订阅（表：source_subs）
//!
//! 订阅远程书源集合链接（url 主键）：raw_json 保存抓取到的完整书源数组 JSON 原文
//! （保底不丢字段），订阅保存/刷新时校验后批量导入 book_sources 表。
//! 订阅支持「禁用」：禁用后不再自动刷新，但保留订阅记录与已导入书源；
//! 重新启用即恢复自动刷新。

use serde::{Deserialize, Serialize};
use sqlx::FromRow;

/// SQLite TEXT 列存 JSON 字符串数组（`["url1","url2"]`），
/// sqlx 0.7 sqlite 对 Vec<String> 无原生 TEXT 解码，故包一层透明 newtype。
#[derive(Debug, Clone, Default, PartialEq, Serialize, Deserialize)]
#[serde(transparent)]
pub struct JsonStringVec(pub Vec<String>);

impl sqlx::Type<sqlx::Sqlite> for JsonStringVec {
    fn type_info() -> <sqlx::Sqlite as sqlx::Database>::TypeInfo {
        <String as sqlx::Type<sqlx::Sqlite>>::type_info()
    }
}

impl<'r> sqlx::Decode<'r, sqlx::Sqlite> for JsonStringVec {
    fn decode(
        value: sqlx::sqlite::SqliteValueRef<'r>,
    ) -> Result<Self, Box<dyn std::error::Error + Send + Sync>> {
        let s = <&str as sqlx::Decode<'r, sqlx::Sqlite>>::decode(value)?;
        Ok(JsonStringVec(serde_json::from_str(s).unwrap_or_default()))
    }
}

impl<'q> sqlx::Encode<'q, sqlx::Sqlite> for JsonStringVec {
    fn encode_by_ref(
        &self,
        buf: &mut Vec<sqlx::sqlite::SqliteArgumentValue<'q>>,
    ) -> sqlx::encode::IsNull {
        let s = serde_json::to_string(&self.0).unwrap_or_else(|_| "[]".to_string());
        buf.push(sqlx::sqlite::SqliteArgumentValue::Text(s.into()));
        sqlx::encode::IsNull::No
    }
}

/// 书源订阅（表：source_subs）
#[derive(Debug, Clone, Default, Serialize, Deserialize, FromRow)]
#[serde(default)]
pub struct SourceSub {
    /// 订阅链接（远程书源集合 URL，主键）
    pub url: String,
    /// 订阅名称
    pub name: String,
    /// 是否启用（禁用后定时任务跳过该订阅，保留记录与已导入书源）
    #[serde(default = "default_true")]
    pub enabled: bool,
    #[serde(skip)]
    #[sqlx(rename = "user_namespace")]
    pub user_namespace: String,
    /// 用户私有“已删除”覆盖标记（普通用户删除 default 系统订阅时复制到本人命名空间并隐藏）
    #[serde(skip)]
    #[sqlx(rename = "hidden")]
    pub hidden: bool,
    /// 抓取到的书源数组 JSON 原文
    #[serde(skip)]
    #[sqlx(rename = "raw_json")]
    pub raw_json: Option<String>,
    /// 用户勾选导入的书源 URL（空数组 = 导入全部；自动刷新沿用该选择）
    #[serde(default)]
    #[sqlx(rename = "selected_urls")]
    pub selected_urls: JsonStringVec,
}

fn default_true() -> bool {
    true
}
