# 项目总目标（GOAL）——长期有效，勿忘

## 基础分支决策（已定，勿反复）

**默认分支与开发基础 = `master`**（2026-08-22 定）：
- master 是完整可运行产品：axum+SQLite+boa 规则引擎、634 测试全绿、web-ui 内嵌、
  实际发版至 v5.2.4；v5.0.x→v5.2.4 本身即 "legacy 全量核销" 系列——对齐主体已具规模
- rust 分支的 Rust 源码是 Kotlin 文件逐个机械转译（*.kt ↔ *.rs 成对），无真实服务端
  架构，不作为基础；该分支保持原样不动（Kotlin 历史 + 转译参考 + v6.0.x tag）
- legacy / archive/master-v5.2.4 分支均为只读参考

## 权威参照源

**对齐基准 = reader-pro-3.2.14.jar**（`C:\Users\chong\Downloads\reader-pro-3.2.14.jar`，69.5MB）

这是编译后的 Spring Boot fat JAR，包含全部 Pro 功能。所有后端功能、细节、参数、文案以此 JAR 内的类行为为准——**优先级高于 git legacy 分支**。

关键差异（Pro 版独有，legacy 分支没有的）：
- LicenseController：授权/许可证管理系统
- setEpubContent / exportToEpub / exportToTxt / getAllContents / searchChapter
- saveShelfBookLatestChapter / syncBookProgressFromWebdav / syncFromWebdav
- searchBookWithSource / saveLocalBookCover / setCover
- textToSpeechCn 引擎
- HttpTTS getSpeakStream 完整管线
- mergeBookCacheInfo / saveBookInfoCache 进程内缓存

前端资源也内嵌在 JAR 中（BOOT-INF/classes/static/）。

**使用方式**：
```bash
# 列出类
jar tf C:\Users\chong\Downloads\reader-pro-3.2.14.jar | findstr "controller"
# 反编译单个类（需要 CFR/procyon/cfern 等工具）
```

## 核心目标

**当前 master（Rust 重写版）功能严重缺失、细节不足。以 legacy 分支的 Kotlin 实现为细节基准，
在 Rust 分支上重写/补齐全部功能；UI 设计语言遵循 archive/master-v5.2.4 分支的风格。**

- **功能基准 = legacy ∪ archive/master-v5.2.4 的功能并集**
  - origin/legacy：Kotlin 源码，功能与细节的最终对照标准
    （书源规则引擎语义、API 行为、缓存策略、用户系统、本地书解析、TXT/EPUB 细节等）
  - origin/archive/master-v5.2.4：其 UI 缺失大量功能，仅作设计语言参考；
    它独有的功能也要保留并与 legacy 功能合并
- **UI 设计风格 = archive/master-v5.2.4**（布局 / 视觉 / 交互设计语言）

## 参考分支

| 分支 | 用途 |
|---|---|
| `origin/legacy` | Kotlin 功能细节基准（对齐目标） |
| `origin/archive/master-v5.2.4` | UI 设计语言基准 + 功能合并来源之一 |
| `master` | 工作分支：Rust 源码 + web-ui |

## 工作方式

1. 逐模块审计差异：Rust 现状 vs legacy Kotlin vs archive/master-v5.2.4
2. 在 Rust 中按 legacy 语义实现（细节逐项对齐，不是近似）
3. 每项修复配测试 → cargo test 全绿 → 提交推送 master

## 当前状态（2026-09-29 更新）

### 后端对齐：✅ 完成
- P0 全部 17 项清零；引擎 E1-E16 + AR1-AR5；路由 177 条全对齐
- 测试 760 lib + 15 集成全绿；审计报告固化于 docs/audit/

### 运维加固（2026-09-22~29，PR fix/book-vars-cache #1）
- book_vars_cache 无界膨胀治理（35GB 事故）：src 不落库、写入节流、
  同源内容去重、canonical JSON（BTreeMap）、root/toc 兜底键豁免、
  每小时 prune（TTL 14 天 + 上限 20 万行）
- camoufox 全链修复：python3.12 基底、UBO 插件（AMO 451 → GitHub Releases +
  构建期校验）、GTK3、solver 浏览器死亡自愈 + Rust spawn 失败 60s TTL 重试
- jsLib 注入：header/搜索/目录/正文/探索全链 eval 带 js_lib（ReferenceError 清零）
- cache.putFile/putFileAsBase64/getFile（legado 大值语义，文件 + 哨兵行）
- 日志治理：[cascade] DBG_TOC 门控、html5ever=off、getBookshelf 降 debug
- 备份：保留 3 份 + 启动守卫（库未变跳过全量拷贝）

### UI 大项：✅ 已完成（原 4 项全部落地）
1. EPUB 阅读模式 ✅（ReaderView epubMode + EpubIframe 沙箱渲染）
2. CBZ 漫画模式 ✅（bookType=2 comic-stage）
3. TTS 面板 ✅（edge/textToSpeechCn/type=api 按名解析 + 选中文本朗读）
4. 书源管理页 ✅（调试面板 bookSourceDebugSSE 四动作 + 分组筛选/管理）

### 积压清单
详见 docs/AUDIT-BACKLOG.md 和 docs/audit/ 目录。

