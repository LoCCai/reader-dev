//! 书签实体（兼容 legacy Bookmark / bookmark.json）
//!
//! - serde：camelCase 与 legacy API 输出一致
//! - sqlx：snake_case 与 bookmarks 表列名一致
//! - 主键：PRIMARY KEY (book_url, title)（任务规格指定）

use serde::{Deserialize, Serialize};
use sqlx::FromRow;

#[derive(Debug, Clone, Default, Serialize, Deserialize, FromRow)]
#[serde(default)]
pub struct Bookmark {
    #[serde(rename = "bookUrl")]
    #[sqlx(rename = "book_url")]
    pub book_url: String,
    /// 书签标题（锚点文本/备注，主键之一）
    pub title: String,
    /// 书名（legacy Bookmark.bookName）
    #[serde(rename = "bookName")]
    #[sqlx(rename = "book_name")]
    pub book_name: String,
    /// 作者（legacy Bookmark.bookAuthor）
    #[serde(rename = "bookAuthor")]
    #[sqlx(rename = "book_author")]
    pub book_author: String,
    /// 段落位置（legacy chapterPos）
    #[serde(rename = "paragraphIndex")]
    #[serde(alias = "chapterPos")]
    #[sqlx(rename = "paragraph_index")]
    pub paragraph_index: i64,
    /// 章节索引
    #[serde(rename = "chapterIndex")]
    #[sqlx(rename = "chapter_index")]
    pub chapter_index: i64,
    /// 章节名（legacy Bookmark.chapterName）
    #[serde(rename = "chapterName")]
    #[sqlx(rename = "chapter_name")]
    pub chapter_name: String,
    /// 书签段落文本（legacy Bookmark.bookText）
    #[serde(rename = "bookText")]
    #[sqlx(rename = "book_text")]
    pub book_text: String,
    /// 书签备注（legacy Bookmark.content）
    pub content: String,
    /// 创建时间（毫秒时间戳）
    #[serde(rename = "createdAt")]
    #[serde(alias = "time")]
    #[sqlx(rename = "created_at")]
    pub created_at: i64,
    /// 命名空间（secure 模式用户名 / default）；不入主键（任务规格）
    #[serde(skip)]
    #[sqlx(rename = "user_namespace")]
    pub user_namespace: String,
}
