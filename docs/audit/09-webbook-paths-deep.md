# WebBook 四件套非主干路径深审报告

## P0/P1
- WebView 抓取路径完全缺失（getStrResponseAwait，AnalyzeUrl.kt:345-404）
- 卷章节短路缺失（contentRule 空→返回 chapter.url；isVolume&&url.startsWith(title)→返回 tag）
- 正文分页终止缺 visited 集和 nextChapterUrl 比较（>5 页长章节静默丢正文）
- 目录去重键应为仅 url（BookChapter.equals 仅重写 url）；keep-last 非 keep-first

## P2
- concurrentRate sleep-per-call 并发下形同虚设（需共享滑窗限速器）
- chapterUrl 绝对化 base=resp.url 与 legacy redirectUrl 一致 ✓
- SearchBook.origin 来源一致 ✓
- 多 next URL 并发抓取合并顺序（legacy async 按序拼接 vs master 单链顺序）

## 已确认对齐
- 搜索去重键 name_author ✓
- 字段清洗 formatBookName/Author/wordCountFormat/kind 归一 ✓
- canReName 语义 ✓
- replaceRegex 多段链 ✓
