//! 书架分组实体（兼容 legacy BookGroup / bookGroup.json）
//!
//! - serde：camelCase 与 legacy API 输出一致（id/name/order）
//! - sqlx：`order` 为 SQLite 关键字 → 列名 `order_num`
//! - books.group_name 存分组 id（int 引用）

use serde::{Deserialize, Serialize};
use sqlx::FromRow;

#[derive(Debug, Clone, Serialize, Deserialize, FromRow)]
#[serde(default)]
pub struct BookGroup {
    /// 分组 id（AUTOINCREMENT；>0 时 save 按 id 覆盖）
    pub id: i64,
    /// 分组名（必填）
    pub name: String,
    /// 分组封面（legacy BookGroup.cover；空 = 无封面）
    pub cover: Option<String>,
    /// 是否显示该分组（legacy BookGroup.show；隐藏分组不出现在书架分组栏）
    pub show: bool,
    /// 排序（order 为 SQLite 关键字 → 列名 order_num）
    #[sqlx(rename = "order_num")]
    pub order: i64,
    /// 命名空间（secure 模式用户名 / default）
    #[serde(skip)]
    #[sqlx(rename = "user_namespace")]
    pub user_namespace: String,
}

impl Default for BookGroup {
    fn default() -> Self {
        Self {
            id: 0,
            name: String::new(),
            cover: None,
            show: true,
            order: 0,
            user_namespace: String::new(),
        }
    }
}

/// 分组列表输出（getBookGroups：含组内书数统计）。
/// - `order` 为 legacy/前端字段名（bookshelf.ts BookGroup.order）
/// - `orderNum` 为任务契约字段名（与 order 同值，双字段兼容）
/// - `bookCount`：books.group_name = 分组 id 的书数（COUNT 子查询）
#[derive(Debug, Clone, Default, Serialize)]
#[serde(default)]
pub struct BookGroupWithCount {
    /// 分组 id
    pub id: i64,
    /// 分组名
    pub name: String,
    /// 分组封面（legacy BookGroup.cover）
    pub cover: Option<String>,
    /// 是否显示该分组（legacy BookGroup.show）
    pub show: bool,
    /// 排序（legacy 字段名）
    pub order: i64,
    /// 排序别名（orderNum；与 order 同值）
    #[serde(rename = "orderNum")]
    pub order_num: i64,
    /// 组内书数
    #[serde(rename = "bookCount")]
    pub book_count: i64,
    /// legacy 字段别名（YueduApi getBookGroups 旧客户端按 groupId/groupName 解析）
    #[serde(rename = "groupId")]
    pub group_id: i64,
    /// legacy 字段别名：分组名
    #[serde(rename = "groupName")]
    pub group_name: String,
}
