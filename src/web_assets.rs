//! 前端静态资源内嵌（rust-embed——编译时打包 web-ui/dist 进二进制）。
//!
//! 动机：裸二进制分发（Windows exe / Linux musl）不再依赖外部 web-ui/dist 目录
//! （历史 404 事故：外置目录缺失时前端不可访问）。Docker 镜像仍走 COPY dist。
//!
//! 优先级：内嵌资产 > READER_APP_WEB_ROOT 磁盘目录（自定义主题/开发热更仍可用）。
//!
//! 注意：cargo build 前 web-ui/dist 必须已构建（CI 编排：npm build 先于 cargo build）。

use rust_embed::RustEmbed;

#[derive(RustEmbed)]
#[folder = "web-ui/dist/"]
pub struct WebAssets;

/// 取内嵌前端文件（rel 为去除首斜杠的路径；含 SPA index.html）
pub fn get(rel: &str) -> Option<(Vec<u8>, &'static str)> {
    let path = rel.trim_start_matches('/');
    if path.is_empty() {
        return index_html();
    }
    let file = WebAssets::get(path).or_else(|| WebAssets::get(&format!("{path}/index.html")));
    match file {
        Some(f) => {
            let mime = mime_for(path);
            Some((f.data.into_owned(), mime))
        }
        None => index_html(),
    }
}

/// SPA 入口
pub fn index_html() -> Option<(Vec<u8>, &'static str)> {
    WebAssets::get("index.html").map(|f| (f.data.into_owned(), "text/html; charset=utf-8"))
}

/// 按扩展名推断 MIME（与 router.rs mime_for 一致）
pub fn mime_for(path: &str) -> &'static str {
    let ext = path
        .rsplit_once('.')
        .map(|(_, e)| e.to_ascii_lowercase())
        .unwrap_or_default();
    match ext.as_str() {
        "js" | "mjs" => "application/javascript; charset=utf-8",
        "css" => "text/css; charset=utf-8",
        "html" | "htm" => "text/html; charset=utf-8",
        "json" => "application/json; charset=utf-8",
        "png" => "image/png",
        "jpg" | "jpeg" => "image/jpeg",
        "gif" => "image/gif",
        "svg" => "image/svg+xml",
        "webp" => "image/webp",
        "ico" => "image/x-icon",
        "woff" => "font/woff",
        "woff2" => "font/woff2",
        "ttf" => "font/ttf",
        "otf" => "font/otf",
        "txt" => "text/plain; charset=utf-8",
        "map" => "application/json",
        _ => "application/octet-stream",
    }
}
