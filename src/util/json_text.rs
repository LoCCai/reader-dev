//! 宽容「JSON 文本」反序列化（Gson 兼容）
//!
//! legacy 书源 JSON 中部分声明为字符串的字段，真实数据常以**结构化 JSON**出现
//! （`header` 为对象、`exploreUrl` 为分类数组、`loginUi` 为控件数组——Gson 反序列化
//! 时自动 Map/List 强转，serde 严格 String 直接报错导致整批导入失败）。
//! 此处统一规范为**紧凑 JSON 字符串**存储——消费端（parse_header/parse_explore_entries）
//! 均按字符串解析，无需改动。仅用于导入边界；序列化恒为字符串。

use serde::{Deserialize, Deserializer};

/// null → None；字符串原样；其余 JSON 值 → 紧凑 JSON 字符串
pub fn deserialize_option<'de, D: Deserializer<'de>>(d: D) -> Result<Option<String>, D::Error> {
    Ok(match serde_json::Value::deserialize(d)? {
        serde_json::Value::Null => None,
        serde_json::Value::String(s) => Some(s),
        other => Some(other.to_string()),
    })
}

/// 字符串原样；其余 JSON 值（含 null）→ 紧凑 JSON 字符串（null → "null"，配合 default 使用
/// 时字段缺失不会走到这里）
pub fn deserialize<'de, D: Deserializer<'de>>(d: D) -> Result<String, D::Error> {
    Ok(match serde_json::Value::deserialize(d)? {
        serde_json::Value::Null => "null".to_string(),
        serde_json::Value::String(s) => s,
        other => other.to_string(),
    })
}

#[cfg(test)]
mod tests {
    use super::*;

    #[derive(Deserialize)]
    struct S {
        #[serde(default, deserialize_with = "deserialize_option")]
        h: Option<String>,
        #[serde(default, deserialize_with = "deserialize")]
        e: String,
    }

    #[test]
    fn container_json_normalized_to_compact_string() {
        let s: S = serde_json::from_str(
            r#"{"h":{"User-Agent":"UA/1.0"},"e":[{"title":"玄幻","url":"/x?page={{page}}"}]}"#,
        )
        .unwrap();
        assert_eq!(s.h.as_deref(), Some(r#"{"User-Agent":"UA/1.0"}"#));
        assert!(s.e.starts_with(r#"[{"title":"玄幻""#));
        assert!(s.e.contains(r#""url":"/x?page={{page}}""#));
    }

    #[test]
    fn plain_string_and_null_pass_through() {
        let s: S = serde_json::from_str(r#"{"h":"a=1","e":null}"#).unwrap();
        assert_eq!(s.h.as_deref(), Some("a=1"));
        assert_eq!(s.e, "null");
        // 字段缺失 → String::default()（default 属性，不经 deserialize_with）
        let s: S = serde_json::from_str(r#"{}"#).unwrap();
        assert_eq!(s.h, None);
        assert_eq!(s.e, "");
    }
}
