# 剩余工作清单与状态报告

> 生成日期：2026-08-24 · 基线：master@ae4c87cc · 后端测试 719+·E2E 2 全绿 · 前端 node --test 86/86 · 构建通过
>
> **进展更新（2026-08-24 终章）**：A/B/C/D 四类全部处置完毕——A1-A5/C1/C2 全部实现，
> C3 为人工验收项，B 类长尾按实测驱动策略处理。
>
> **E2E 实测矩阵（13 场景）**：CSS/JSON 双源全链路、GBK 双路径、POST+@js 链、webView 回退、
> Cookie 注入/header 透传、RSS 双路径（feed-rs + ruleArticles）、loginCheckJs 三态、
> 失效源生命周期（mark/短路/clear）、check_source 探测——共 723 lib + 14 E2E + 86 前端全绿。
>
> 本报告汇总「legacy/reader-pro-3.2.14 对齐工程」完成后仍存在的全部已知差距，
> 按影响分为：A 功能未实现 / B 审计未覆盖 / C 测试缺口 / D 已评估不移植。

## 总体完成度

| 维度 | 状态 |
|---|---|
| 后端 API 控制器层对齐（~90 函数） | ✅ 完成（签名/参数/文案/核心语义） |
| 规则引擎对齐（AnalyzeRule/Regex/XPath/JSonPath/JSoup/CSS 链） | ✅ 主干完成（~85% 逐行，长尾见 B 类） |
| WebBook/本地书/用户安全/基础设施 | ✅ 完成 |
| UI 差距积压（P0×3 + P1×5 + P2×7） | ✅ 全部执行完毕 |
| CI（Rust/Frontend/Docker 多架构） | ✅ 基线正常 |

---

## A. 功能已识别差异但未实现

按影响排序：

| # | 项 | 说明 | 建议 |
|---|---|---|---|
| ~~A1~~ | **webView 抓取路径** | ✅ 已完成（`3c7663a4`）：URL option webView=true 经 camoufox 渲染，未启用/失败回退 HTTP，覆盖搜索/详情目录正文/探索 | — |
| ~~A2~~ | **concurrentRate 共享滑窗限速** | ✅ 已完成（`5e420c52`）：n/window 共享滑窗 + 纯数字最小间隔，覆盖搜索/详情/探索/RSS 四链路 | — |
| ~~A3~~ | textToSpeechCn 引擎 | ✅ 已完成（`6916b488`）：POST 表单→{download}→302 直连（Pro 对齐），失败回退 Edge 保底 |
| ~~A4~~ | **searchChapter/getAllContents 等** | ✅ 已完成（`0adee5c5`/`579afb9f`）：补齐 getAllContents/searchChapter/exportToEpub/exportToTxt 四接口 + searchBookContent 响应补齐 SearchResult 全字段（与旧字段并存） |
| ~~A5~~ | exportToEpub 结构差异 | 部分对齐（`0adee5c5`）：publisher=Legado/language=zh 元数据对齐 epublib 约定；EPUB3 vs Pro EPUB2+NCX 格式差异保留（现代阅读器兼容 EPUB3） |

## B. 审计覆盖的长尾（约 15% 未逐行）

静态审计边际收益递减区——多为极端边界条件：

| 区域 | 具体未穷举点 |
|---|---|
| AnalyzeRule.java | getString 分支树边界；splitSource 嵌套 `{{}}` 内含 `@` 的切分精确性；put/get 三级作用域跨请求持久化完整行为矩阵（SQLite 持久化已做，回退链矩阵未穷举） |
| AnalyzeByJSoup 选择器差异矩阵 | jsoup 与 scraper CSS 方言语法逐条对照表未建立（`:eq/:lt/:gt/:contains/:matches` 已通过预处理修复 ✅；其余伪类如 `:not` 嵌套、属性正则 `~=|=^=$=*=` 差异未系统验证） |
| BookHelp 图片管线 | saveImages 并发去重+轮询等待完整流程仅概述；updateImageLinkInContent 正则边界未穷举 |
| CacheManager/ACache LRU | 精确淘汰时机、hashCode 碰撞、启动扫描竞态窗口 |
| CookieStore 序列化 | ACache 文件格式 ↔ SQLite 在读写边界的影响未系统测试 |
| EncoderUtils escape() | 字符集精确边界、hex/base64 自动识别阈值 |

## C. 测试缺口

| # | 缺口 | 说明 |
|---|---|---|
| ~~C1~~ | **真实书源全链路实测** ⭐ | ✅ 已完成（`c733c7cc`）：mock 书站双源 CSS+JSON E2E，实浌即修复 AR2b/tocUrl 两处引擎缺口。真实第三方源手动验证仍可选 |
| ~~C2~~ | 前端新模块单测 | ✅ 已完成（`ae4c87cc`）：epubLoader 5 例 / ttsCache 5 例 / rssMedia 2 例，node --test 86/86；顺带修复墙视图/sw v3 存量过期断言 |
| C3 | EPUB 原版渲染真机验证 | EpubIframe 的 srcdoc 渲染、内链跳转、进度同步未在真实 .epub 上验证过 |
| C4 | SSE 单源/并发参数集成测试 | P1-4 加的 bookSourceUrl 参数后端有编译保障但无 e2e 断言 |

## D. 经评估明确不移植

| 项 | 理由 |
|---|---|
| WiFi 局域网传书专页 | Web 版即服务端，FileManage 上传已覆盖同一场景 |
| MPCode 公众号二维码弹窗 | 推广组件 |
| MP3 下载（P2-7 半项） | 星标已完成；MP3 落盘下载受 CORS 限制需后端代理配合，收益低，暂缓 |

---

## 推荐行动顺序（更新）

1. ~~C1 实浌~~ ✅  2. ~~A2 限速器~~ ✅  3. ~~C2 前端单测~~ ✅
4. C3 真机过一遍新 UI 功能（EPUB raw/TTS 缓存/点击方案）——需人工浏览
5. ~~A1 webView 路径~~ ✅
6. 其余 B 类长尾：随实测暴露再修，不做预防性审计
