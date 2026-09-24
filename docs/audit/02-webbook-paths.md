# WebBook 四件套非主干路径深审报告

> 审计代理产出 | 2026-08-23 | 对比基准 origin/legacy vs master HEAD
> 来源：WebBook 深审代理（搜索/探索/目录/正文四链路的非主干分支）

## 发现清单

| 严重度 | 问题 | legacy 证据 | master 现状 | 修复建议 |
|--------|------|------------|------------|---------|
| 高 | **getStrResponseAwait WebView 抓取路径完全缺失**：webJs/sourceRegex 的 WebView 执行分支未迁移，仅正文流程传入了 webJs/sourceRegex 参数但无实现 | AnalyzeUrl.kt:345-404 | 无 WebView 抓取回退路径 | 移植 WebView 抓取分支或以等价 headless 方案兜底；至少对 webJs 规则给出明确失败信号 |
| 高 | **卷章节短路缺失**：contentRule 为空 → 直接返回 chapter.url 作为正文；`isVolume && url.startsWith(title)` → 直接返回 tag（卷标题即正文） | contentUrl 短路逻辑 | 未实现，走完整解析链必失败 | 正文入口前置两条短路判断 |
| 中高 | **正文分页终止条件不完整**：缺 visited 集合与 nextChapterUrl 比较，仅靠页数上限 → >5 页长章节静默丢正文 | visited 集 + nextChapterUrl 双终止 | 页数上限单条件 | 分页循环加 visited URL 集与 nextChapterUrl 相等即停 |
| 中 | **目录去重键错误**：去重键应为仅 url（legacy BookChapter.equals 仅比较 url）；且应 keep-last 而非 keep-first | BookChapter.equals 仅 url；同名重复章以后写入者为准 | 键含多余字段、keep-first | 去重键收敛为 url 并改 keep-last |
| 中 | **concurrentRate sleep-per-call 形同虚设**：每次调用前独立 sleep，并发下无法形成全局限速 | 全局并发闸 + 间隔调度 | per-call sleep | 引入进程级限速器（按源维度令牌桶/间隔队列） |

## 已确认对齐项

- chapterUrl 绝对化的 base 选择一致 ✓
- SearchBook.origin 来源一致 ✓
