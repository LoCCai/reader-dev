# AnalyzeRule 内部方法深审报告

> 审计代理产出 | 2026-08-23 | 对比基准 origin/legacy vs master HEAD
> 来源：第二轮 AnalyzeRule 深审代理（逐方法对比 legacy `ar.kt` vs master 规则引擎）

## 发现清单

### P1（影响真实书源解析正确性）

| # | 问题 | legacy 证据 | master 现状 | 修复建议 |
|---|------|------------|------------|---------|
| AR1 | **isJSON 强转缺失**：内容为 JSON 时 legacy 检测到 JSON 后强制所有裸键规则走 JsonPath（`ar.kt:469-471`）；master `detect_kind` 无此机制 → 裸键规则如 `data.list.name` 对 JSON 内容必空 | ar.kt:469-471 setContent JSON 检测后强转 JsonPath | detect_kind 仅按规则前缀分派 | 在内容侧检测 JSON 后为裸键规则注入 JsonPath 分派路径 |
| AR2 | **多命中截断**：legacy 单条规则命中多元素时 `joinToString("\n")` 全量传递；master `field_impl` Css 分支只取 first → intro/kind 多节点只剩第一行 | joinToString("\n") 全量拼接 | search.rs:1068-1077 Css 分支 `.first()` | Css/多元素分支改为收集全部节点后按 "\n" 连接 |
| AR3 | **空中间结果链终止**：legacy 中间段得 null 后后续所有段跳过、返回空；master 以空串继续喂下一段（`class.missing@text@js:x` → legacy 返回 ""、master 返回 "0"） | 中间段 null 即短路整链 | 空串继续参与下一段求值 | 链式求值中 null/空中间结果直接终止返回空 |
| AR4 | **body-$N 列表回填缺失 + `@get:{title/bookName}` 内建缺失**：legacy `get(key)` 先查 bookName→book.name 再查变量表（`ar.kt:632-645`）；master `resolve_get` 只读变量表 → `@get:{title}` 恒空；body 列表捕获 `$N` 也无回填通道 | ar.kt:632-645 get(key) 三级查找 | resolve_get 只读变量表 | 补 body-$N 回填 + get 内建 title/bookName→book.name 兜底链 |
| AR5 | **evalJS 绑定缺口**：chapter / title / book / source / nextChapterUrl 在规则引擎任何路径都拿不到真实值；baseUrl 在 rule.rs 路径恒为空串 | evalJS 注入完整上下文绑定 | 绑定缺失/恒空 | 在规则引擎入口统一注入 chapter/title/book/source/nextChapterUrl/baseUrl 真实值 |

### P2（低优先细节）

- AR-P2 完全越界区间钳位差异：`[10:20]` len=3 → legacy 得 `{2}`（钳位尾元素）vs master 空
- AR-P2 html DOM 原地修改副作用 / 多元素条数形态差异
- AR-P2 outerHtml 别名超集 / attr trim 差异 / `@CSS:` 缺 tail 时 master 抛错 vs legacy 优雅降级
- AR-P2 JsonPath `{$.x}` 平衡扫描与失败保留原文回退
- AR-P2 `@put` 列表规则贯通（列表上下文中 put 值不随条目迭代）
- AR-P2 html/all 多元素条数形态差异

## 已确认对齐项

- splitSource 前缀检测顺序一致 ✓
- 索引形态 `[!0]` / `[0:1]` / `[-1:0]` 一致 ✓
- textNodes / ownText 一致 ✓
- getStringList 对 String 输入 `split("\n")` 一致 ✓
