# reader-dev 独立三轮验收（2026-10-30）

权威修前版本为 193 `/home/ccai/docker/reader-dev` 的 `7efd557`，所有执行仅使用隔离候选、临时数据库、合成用户和本机 fixture。未访问用户正文、未改生产数据库、未重启生产、未推送 GitHub。

## 第一轮：修前行为与最短复现

`frontend_cache_dialog.mjs` 用 Vue 官方 SFC 编译器编译实际组件 `<script setup>`，仅替换 HTTP、IndexedDB 边界，直接调用真实操作函数并检查请求参数与响应式状态。

```bash
PATH=/root/.nvm/versions/node/v22.23.4/bin:$PATH AUDIT_BASELINE=7efd557 node --test --test-name-pattern '^R1' tests/audit/frontend_cache_dialog.mjs
python3 tests/audit/baseline_cache_sql.py
```

基线 7 项中 6 项按正确契约断言失败、1 个范围转换正例通过：延迟启动取消、按 URL 而非 taskId 取消、SSE 握手取消、本机存储 false 仍计成功、无完成帧 EOF 假成功、详情未取目录直接打开空缓存弹层。SQL 脚本提取并执行原版真实 SQL，证明清理跨两用户且删本地唯一正文。基线浏览器还直接执行原版 IndexedDB 模块，证明换账号后仍能读取前账号正文。

证据：`artifacts/frontend-r1-baseline.tap`、`artifacts/baseline-cache-sql.json`。基线预期非零退出，不能当作候选失败。

## 第二轮：修复后交叉故障、并发与取消

在第一轮复测之外追加：旧任务回调与新任务隔离、换书、卸载、正文请求在途取消、本机部分持久化失败、重连旧流回调、SSE 降级保留单源、前 50 源无结果续搜、握手期间停止。

`tests/e2e_audit_cache.rs` 为真实路由、HTTP、私有随机端口书站与临时 SQLite 测试：两用户统计/清理范围、本地原文保护、205 章缓存排序/分页/范围与 TXT/EPUB 实际产物、3 个正文请求在途取消、跨用户 taskId 不可取消。

## 第三轮：端到端与残留审查

SFC 脚本最终 18/18 通过，包含续搜冻结关键词/分组/源/exact/并发和重复分页恢复。浏览器最终 15/15 通过、`pageErrors=[]`：

- 搜索点击结果 → 详情直接范围缓存 → 延迟任务精确取消。
- 当前书 TXT 下载与部分失败警告保留。
- EPUB text → HTML 重取、复制解实体、本章搜索高亮、raw 原文件带会话访问与禁脚本 iframe。
- 账号切换缓存隔离、B 清理不影响 A、无归属旧行不可见、旧 owner 在途写入拒绝。
- 音频/漫画/文件/视频按 type 1/2/3/4 渲染，文本导出/缓存禁用并说明。
- simple 首页入口、独立 TOC/source/book 参数、首点目录/换源、真实 SearchBook 候选换源契约、刷新当前书及换源失败原地保留正文。
- 390px 窄屏阅读导出可达且弹层无横向溢出。

```bash
PATH=/root/.nvm/versions/node/v22.23.4/bin:$PATH node --test tests/audit/frontend_cache_dialog.mjs
# 先在隔离候选 web-ui 运行 Vite（只监听回环地址；无生产后端）。
AUDIT_BASE_URL=http://127.0.0.1:54173 PATH=/root/.nvm/versions/node/v22.23.4/bin:$PATH node tests/audit/browser_acceptance.mjs
# 由根代理统一使用共享 cargo 缓存编译、执行；不要并发全量编译。
cargo test --test e2e_audit_cache -- --test-threads=1
```

浏览器通过现有 Playwright/Chromium 运行，不安装依赖，所有 `/reader3` 接口被合成 fixture 接管，外网请求被拒绝。`artifacts/browser/report.json` 包含每个场景和脱去 token 的请求参数，桌面、阅读、simple、窄屏截图在同目录。最初 EPUB 修复未落地时的失败报告/截图单独保留在 `artifacts/browser-r3-initial`。

浏览器脚本默认复用本机已有 QWeb Playwright 安装。其他环境用 `AUDIT_PLAYWRIGHT_PACKAGE=/绝对路径/已有Playwright项目/package.json` 指定模块解析基点（该项目须已能解析 `@playwright/test`），用 `AUDIT_CHROMIUM=/绝对路径/chrome` 指定浏览器；`AUDIT_BASE_URL`、`AUDIT_BROWSER_OUTPUT` 分别指定候选地址和产物目录。脚本不会自动下载浏览器或安装依赖。

Rust HTTP 测试还覆盖清 TOC 后 range/getAll/TXT/EPUB 一致性、空缓存标题从目录恢复、taskId 新旧代数取消隔离、原版 EPUB 文件当前书架归属与当前用户目录限制、伪造路径和符号链接越界拒绝。最终根代理统一编译后，QA 独立直接执行全部 7 个集成测试二进制，23/23 通过、0 ignored、0 filtered、无程序报告的跳过。本次 4 项 HTTP 验收也在最终产物上通过（测试本体 3.44 秒），正式日志为 `artifacts/backend-http-final.log`；之前未纳入末次代数/空标题回归的候选回执另存 `artifacts/backend-http-pre-generation.log`。

| 最终集成套件 | 通过 |
| --- | ---: |
| e2e_advanced：GBK 显式/自动、POST+JS、WebView HTTP 回退 | 4 |
| e2e_audit_cache：本次独立三轮 HTTP 验收 | 4 |
| e2e_booksource：CSS 与 JSON 完整书籍链 | 2 |
| e2e_bot_api：认证、搜索、正文、下载、换源、限额、归属与死锁 | 5 |
| e2e_health：失败标记、短路、恢复与探测 | 3 |
| e2e_source_features：header/Cookie/loginCheckJs/RSS | 4 |
| e2e_sse：多源过滤与并发 | 1 |

最终集成执行器为 `run_existing_integration.py`，只接收已经编译好的精确二进制路径，逐套设 120 秒超时，所有套件均未超时。逐二进制 SHA-256、完整原始输出、运行时间及环境汇总保存在 `artifacts/backend-integration-final/summary.json` 及同目录日志。执行时清除了全部 HTTP/HTTPS/ALL 代理变量并设置 `NO_PROXY=127.0.0.1,localhost,::1`，避免宿主代理把本机 fixture 请求转为 502；另设置 `READER_CAMOUFOX_DISABLE=1`、`READER_CAMOUFOX_SPAWN=0` 并去除外部 solver URL，因此真实动态浏览器、浏览器登录和验证码求解不算已覆盖，WebView 的 HTTP 回退路径有实际执行。

## 覆盖边界

合成 fixture 能证明接口、缓存、按钮与故障恢复契约，不证明全部当前真实外站健康。当前启用记录包含多命名空间重复源，不能把书源记录数称为独立健康书源数。现成 Rust 引擎测试提供 HTML/CSS、JSONPath、JS、Cookie/header/loginCheckJs 等规则场景；它们仍需根代理实际运行后才计通过。真实站点登录态、付费内容、完整音视频播放、动态验证码与所有移动设备未逐一验收。媒体 fixture 验证分派与按钮范围，外部媒体请求被拒绝，未宣称播放成功。
