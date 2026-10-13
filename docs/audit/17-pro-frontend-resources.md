# reader-pro-3.2.14.jar 前端资源审计报告

## 结构
主前端 BOOT-INF/classes/web/（Vue2+element-ui SPA，3 路由：index/setting/reader）
轻量前端 BOOT-INF/classes/simple-web/（4 页面：index/search/rss/reader + 6 tmpl + 5 中文字体）
书源编辑器 bookSourceDebug/
EPUB 导出模板 epub/

## master 功能缺口
🔴 simple-web 极简多页 UI（面向墨水屏/低配设备）——完全缺失
🔴 DPlayer 视频播放+弹幕（danmaku 发送/flv/dash 直播流）——ReaderView 有 video/hls 但缺弹幕和 flv/dash
🟡 webtorrent/pear-player P2P 流媒体——部分
🟢 书源编辑器独立页——master 内联覆盖基本功能

## master 反向优势
/users 和 /server-stats 独立路由（Pro SPA 仅混入 setting）
