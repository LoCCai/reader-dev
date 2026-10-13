//! 宽容 bool 反序列化（Gson 兼容）
//!
//! 真实书源/书架备份（各类 legado 分支导出）普遍以 `1`/`0`、`"true"`/`"1"` 编码布尔值——
//! Kotlin Gson 反序列化时自动强转，而 serde 严格 `bool` 直接报错，导致**整批导入失败**
//! （实测 431 源真实备份 100% 复现）。仅用于导入边界的 Deserialize；序列化恒为真 bool。

use serde::{Deserialize, Deserializer};

#[derive(Deserialize)]
#[serde(untagged)]
enum FlexibleBool {
    Bool(bool),
    Int(i64),
    Str(String),
    Null,
}

/// `true/false/1/0/"true"/"false"/"1"/"0"/"yes"/"no"/"on"/"off"`（忽略大小写与首尾空白）；
/// null → false（Gson 对 primitive bool 的 null 同样落 false）；其余字符串按非空真值处理
pub fn deserialize<'de, D: Deserializer<'de>>(d: D) -> Result<bool, D::Error> {
    match FlexibleBool::deserialize(d)? {
        FlexibleBool::Bool(b) => Ok(b),
        FlexibleBool::Int(i) => Ok(i != 0),
        FlexibleBool::Str(s) => Ok(!matches!(
            s.trim().to_ascii_lowercase().as_str(),
            "false" | "0" | "no" | "off" | ""
        )),
        FlexibleBool::Null => Ok(false),
    }
}

/// `Option<bool>` 变体：字段存在但为 null → None
pub fn deserialize_option<'de, D: Deserializer<'de>>(d: D) -> Result<Option<bool>, D::Error> {
    Ok(match FlexibleBool::deserialize(d)? {
        FlexibleBool::Null => None,
        FlexibleBool::Bool(b) => Some(b),
        FlexibleBool::Int(i) => Some(i != 0),
        FlexibleBool::Str(s) => Some(!matches!(
            s.trim().to_ascii_lowercase().as_str(),
            "false" | "0" | "no" | "off" | ""
        )),
    })
}

#[cfg(test)]
mod tests {
    use super::*;

    #[derive(Deserialize)]
    struct S {
        #[serde(default, deserialize_with = "deserialize")]
        a: bool,
        #[serde(default, deserialize_with = "deserialize_option")]
        b: Option<bool>,
    }

    #[test]
    fn flexible_bool_accepts_gson_style_encodings() {
        for (raw, expect) in [
            (r#"{"a":true}"#, true),
            (r#"{"a":false}"#, false),
            (r#"{"a":1}"#, true),
            (r#"{"a":0}"#, false),
            (r#"{"a":"true"}"#, true),
            (r#"{"a":"0"}"#, false),
            (r#"{"a":null}"#, false),
            (r#"{}"#, false),
        ] {
            let s: S = serde_json::from_str(raw).unwrap();
            assert_eq!(s.a, expect, "{raw}");
        }
    }

    #[test]
    fn flexible_option_bool_null_is_none() {
        let s: S = serde_json::from_str(r#"{"a":1,"b":null}"#).unwrap();
        assert_eq!(s.b, None);
        let s: S = serde_json::from_str(r#"{"a":1,"b":1}"#).unwrap();
        assert_eq!(s.b, Some(true));
        let s: S = serde_json::from_str(r#"{"a":1}"#).unwrap();
        assert_eq!(s.b, None);
    }
}
