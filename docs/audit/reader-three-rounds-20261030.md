# Reader 三轮专项检查、修复与发布候选记录

日期：2026-10-30。对象：LoCCai/reader-dev；参照上游 warpdotsys/reader-dev 的 master。

## 结论与发布状态

本次按用户要求由前端、后端、独立测试三个工程角色参与，主审核对实际部署、Git 来源、跨模块契约及简洁网页。三轮分别检查基础功能、并发与作用范围、跨功能和账号隔离；不是把同一组测试重复三次。

三轮修复及最终验证已完成，位于隔离工作副本。最终结果：后端 lib **826/826**、7 套真实 HTTP/规则集成 **23/23**、前端单测 **102/102**、SFC 操作 **18/18**、Chromium 场景 **15/15**（其中 1 项是基线缺陷证明，其余 14 项为候选验收），类型检查与前端生产构建通过。

**本文不代表已经更新生产**；193 的运行目录、数据库、容器和镜像未改，未发布 GitHub。工程师自测、主审实际执行和独立 QA 验收分别留存。最终复核时生产仍为 `7efd557`、同一镜像与 StartedAt，RestartCount=0、OOMKilled=false；临时回环 Vite 已停止，54173 端口释放。

最高优先级是数据与账号隔离：全局清缓存不能删除其他用户数据及导入书的唯一正文；同一书 URL 的删除、换源、导入等路径也必须按章节行的命名空间过滤；浏览器离线缓存必须区分账号。之后才是取消准确性、搜索覆盖、导出章序及按钮反馈。暂不需要为这些正确性问题引入 Redis 或增加容器内存。

## 1. 线上与 Git 核查

| 项目 | 核查结果 |
|---|---|
| 193 权威源码 | `/home/ccai/docker/reader-dev`，master，`7efd557fe9d612552d5e92dceb214e7cb619693c`，核查时工作树干净 |
| 运行容器 | `reader-dev`，镜像 `reader-dev:local`，193:10002 → 8080；仅 data → /data 持久化绑定 |
| 运行镜像 ID | `sha256:569386069bd69ae8a24a0bb4dc398d1edc6d914a19664eb609e2f3a90286b343` |
| 当前运行状态 | StartedAt 2026-10-27T14:13:33.266837189Z，RestartCount 0，OOMKilled false |
| 主网页 | `/app/web-ui/dist/index.html`；与 193 项目 dist/index.html 的 SHA-256 一致：`4340906728bc0bd29d591aab7e38d91ffb44feb621f3d4b489945ac4df283c61` |
| GitHub fork master | `e29b643535dccc82eeada5600cf7386a24a81a62`，2026-09-17 |
| 上游 master | `37eff8639f6a50c5ff16820c80af087bb4cc1aa6`，2026-08-24 |
| 历史关系 | 193 仓库重新初始化，8 个提交，根提交 `40a78ee` 没有父提交；与 fork/upstream master 均无 merge-base |
| 重要限制 | index.html 一致能证明入口构建文件一致，不能替代整个镜像与 Git 提交的可重复构建证明 |

193 的 8 个提交涵盖 WP1 安全、WP2 规则 URL/分页、WP3 并发锁/隔离/备份/JS/浏览器、WP4 阅读 UI、WP5 SSRF/代理、WP6 镜像/简洁网页及 dockerignore 修复。不能把 346/8 的左右提交数解读成通常意义上的“落后 346、领先 8”，也不能直接 pull 合并或强推覆盖 GitHub。

已准备保留 fork 历史的本地整合分支 `audit/reader-reconcile-193-20261030`：以 origin/master 为父，将 193 **已跟踪的当前源码树**作为部署对照提交，再叠加本轮修复。对照提交 `d1a4cc4` 的树与 `server/master` 完全一致；保留原 GitHub 的历史，不把重新初始化仓库的旧历史直接并入。当前树排除 .env、data、storage、target、work、.mimosa 等运行/构建产物；旧历史仍需按现有仓库权限管理。

审计工作目录：`/www/wwwroot/bot/QIQI/qq/github_git/reader-dev-audit-20261030`。旧的 `qq/github_git/reader-dev` 没有 .git 且代码较旧，没有拿它覆盖 193。

## 2. 实际书源与验证范围

对生产 SQLite 仅做只读汇总，未取用户书架、正文、登录头、Cookie 或凭据：

| 指标 | 数量/说明 |
|---|---|
| 非隐藏书源行 | 2619；跨 3 个 namespace，不能视为 2619 个独立站点 |
| 启用行 | 1965；966 个启用 hostname；62 个分组 |
| 文本 type=0 | 2331 行 / 1733 启用 |
| 音频 type=1 | 47 / 25 |
| 漫画 type=2 | 184 / 175 |
| 文件 type=3 | 31 / 8 |
| 视频 type=4 | 26 / 24 |
| 规则特征 | jsLib 110；loginUrl 406；含 JS 特征 1255；JSONPath 特征 604；XPath 特征 4；这些统计允许重叠 |

因此测试覆盖文本、媒体、导入 TXT/EPUB、独立目录 URL、卷标题、JS/JSONPath/XPath、登录检查、自定义头与 Cookie、搜索单源/分组/精确/分页、缓存失败/取消、双用户同 URL 等。网络取证使用私有 synthetic mock 站点，不向 1965 个启用源批量压测。**本报告不承诺每个实际书源当前可用，也未验证需要真实登录/付费/验证码的站点。**

## 3. 三轮发现与修复记录

### 第一轮：基本操作、数据保护及初始故障复现

| 编号 | 原有问题 | 修复与验收重点 |
|---|---|---|
| R1-01 | getCacheInfo 跨所有 namespace 统计，clearCache 对两张表全表 DELETE | 改为当前 namespace 与可再生缓存范围；事务清理；导入原文不属于可删缓存 |
| R1-02 | 详情不先点目录，直接点缓存会得到 0 章 | 点缓存主动加载 TOC，直接操作可用 |
| R1-03 | 取消按 bookUrl 而非 taskId；POST 创建或 SSE 握手时取消会遗留任务 | 精确 taskId，冻结本次任务身份；晚返回的任务立即取消；旧回调不污染新操作 |
| R1-04 | IndexedDB 写入 false 仍算成功；SSE 未收到 finished 就 EOF 也显示完成 | 失败计数和状态如实反馈，断流提示中断 |
| R1-05 | 服务器网络正文使用 URL hash 作为索引，直接用于范围、排序、TXT/EPUB 导出 | 按 TOC 过滤卷标题后映射真实零基章序，分页先排序再取范围，忽略目录抓取 TTL 对已缓存章序的影响 |

独立 QA 从 `git show 7efd557:...` 加载原 Vue SFC，最初 7 项中 6 失败、1 正向范围测试通过；R1 修复后 7/7 通过。原全表删除 SQL 另从原始函数中提取，在内存 SQLite 的 A/B 用户与本地原文 fixture 执行，证明 2 条目录和 4 条正文全部被删除；该复现未接触生产数据。

### 第二轮：并发、取消边界与搜索覆盖

| 编号 | 原有问题/边界 | 修复与验收重点 |
|---|---|---|
| R2-01 | SSE 默认一批 50 源，界面忽略 lastIndex/isEnd，把第一批当全部结束 | 保留服务端游标，明确“继续搜索其他书源”；前 50 源零命中也可续搜 |
| R2-02 | SSE 降级/更多页面可能丢失单源 URL、分组、精确条件；相同结果页误当耗尽 | 冻结提交时的查询条件；模式切换、降级和更多结果遵守同一搜索范围 |
| R2-03 | 缓存取消后，正在等网络/写入的任务仍可能提交 | 受控 3 并发 future、取消通知、写入与取消确认的共享 gate，取消确认之后禁止再落正文 |
| R2-04 | 同书多任务或重复创建的检查与插入可能竞态，账号间任务不可混用 | namespace 纳入任务身份，原子检查/注册；跨用户 taskId 取消拒绝 |
| R2-05 | 旧书/旧任务回调、卸载与本机下载并发可能污染新状态 | 任务世代与所有权校验；关闭、换书、晚回包和失败路径独立测试 |
| R2-06 | 错误条数无法反映实际失败 | failedCount 按失败章累计，不把“处理过”当作“缓存成功” |

独立组件测试从 7 项扩展到 16，再到 18 项；不是只对源码做字符串匹配，而是编译实际 SFC 并调用其操作，边界处替换网络和本机存储。另有真实 HTTP 延迟站点验证在途三个请求时取消及跨用户任务权限。

第三轮主审与实际 Rust 测试又补了两个边界：每轮新任务使用独立 UUID，同书同范围仅在仍运行时去重复用，迟到的旧 taskId 取消不能打中新一轮；只清 TOC 后不仅保持章序，也持久保留原本入参为空时从 TOC 补齐的章名。两点均由独立 HTTP fixture 在最后候选验证。

### 第三轮：重新调查跨模块、账号切换、EPUB 与简洁网页

| 编号 | 新发现 | 修复与验收重点 |
|---|---|---|
| R3-01 | 浏览器章节 key 仅 bookUrl/chapterUrl，同浏览器 B 可读 A 的离线正文 | IndexedDB key 增加 username 与管理员 defaultConfigMode；历史无 owner 行不自动归属；网络请求冻结原 owner |
| R3-02 | R1 的 TOC 映射仍依赖可清理目录：只清目录后正文无法正确导出 | 加可空 chapter_order 元数据列、幂等兼容回填；目录清除后仍可按章序读取/导出；无法确定旧 hash 次序时明确要求刷新目录 |
| R3-03 | 同 URL 双用户的删除/换源/替换章节，以及本地导入/生成 EPUB 路径仍有 namespace 缺口 | 读写删除章节行均显式按 ns 过滤，签名统一传 ns，双用户同 URL 验证 |
| R3-04 | EPUB 切模式只改状态，未立即按新模式重取；HTML 有正文但复制禁用/章内搜索 0 命中 | 切换即加载；复制使用可见正文并解实体；HTML/原版章内搜索切文本并明确提示 |
| R3-05 | EPUB 原版取文件将 local:// 或 storage/ 书 URL 当普通路径且漏认证 | 新 getBookFile 按当前用户书架归属解析原文件；前端传 token/ns，校验 MIME，错误可见可重试；原版 iframe 保留无脚本沙箱 |
| R3-06 | 媒体书展示文本缓存/文本导出入口；缓存标记混入卷标题且只读首 200 行 | 明确禁用不支持的操作并解释；服务器同样拒绝媒体文本任务；标记按实章映射并续页 |
| R3-07 | 本地书“清缓存”容易混淆原书、服务器与浏览器范围 | 本地书入口明确“清本机缓存”，仅当前 owner 的 IndexedDB；服务器导入原文保留 |
| R3-08 | 简洁页首次点击目录/换源不展开；未入架试读目录漏 source；更新章节没强制刷新 | 修正首次展开、BOOK_URL/TOC_URL/source 参数；更新按钮显式 refresh=1 |
| R3-09 | 简洁页误将换源候选 SearchBook 当 BookSource，且用旧书 URL 配新 origin 写 saveBook | 两处改用真实 origin/originName/bookUrl，POST setBookSource 的旧 URL/新 URL/source 三参数；用返回书 URL 跳转；失败留在原书 |

更正与排除：标准 local:// 的单书 deleteBookCache 在原 handler 中已经受 is_local_book 保护，不能因底层原语直接 DELETE 就断言该入口可删原文。本次对特殊 origin=local 的额外保护属于防御性收敛，未计作标准 local:// 已证实漏洞。对只看公共旧代码提出、而 193 已修复的假设，同样不计为本次漏洞。

## 4. 操作范围与按钮契约

| 入口/操作 | 生效范围 | 行为与限制 |
|---|---|---|
| 搜索、继续其他源、更多页 | 本次提交的 key + group + source + exact；当前用户可用书源 | 书源游标和源内 page 含义不同；结果重复不等同源已耗尽；中断不报全完成 |
| 详情/阅读页缓存到服务器 | 当前书、所选实章范围；当前 ns | UI 1 基转服务端 0 基，排除卷标题；每个任务最多并发 3 个章节请求，准确成功/失败计数 |
| 取消服务器缓存 | 当前精确 taskId；当前 ns | 不按书 URL 批量取消别人的或另一窗口任务；服务端取消确认后，该任务不继续写正文 |
| 取消本机拉取 | 当前本机操作 | 阻止后续本机写入；已经发出的 getBookContent 仍可能在服务器抓源/写缓存，不等同服务器 taskId 取消 |
| 拉取到本机 | 当前书与章范围、当前浏览器 owner | 通过 getBookContent 读取，若服务器缺缓存会先抓源并写服务器，不能把它描述成只在客户端发生 |
| 本机缓存/清本机缓存 | 当前账号 + 默认配置模式 + 当前书的 IndexedDB | 不等同清服务器，不删除原书；历史无 owner 数据不自动被新账号读取 |
| 设置清目录/正文/全部缓存 | 当前 ns 的相应可再生缓存 | 不跨用户、不删导入主数据；只清目录后章序/章名仍保留；不停止任务，任务可能继续回填 |
| 清单书服务器缓存 | 当前 ns、当前书的可再生缓存 | 本地/导入主数据受保护；前端本地书走“清本机”；不停止服务器任务，任务可能继续回填 |
| 网页导出 TXT/EPUB/HTML | 当前书全书，独立于当前阅读章或缓存所选范围；TXT 可选 UTF-8/GBK | 网络抓取式导出与纯缓存 Pro 导出是不同 API；部分失败警告不能瞬间消失；媒体文本导出不支持 |
| 临时/未入架文本 | 本机拉取允许，弹层默认本机；服务端缓存禁用 | 详情书内搜索入口不展示；阅读本章搜索可用；导出提示先入架 |
| Pro exportToTxt/ToEpub/getAllContents | 当前 ns 当前书现有缓存，零补抓 | 按真实章序；getAllContents 缺章占位；纯缓存导出不能暗示全站/整本已抓齐 |
| EPUB 文本/HTML/原版 | 当前章/当前导入原文件 | 切换立即生效；原版只对授权书文件；复制/本章搜索作用域与可见正文一致 |
| 简洁页换源 | 当前已入架书 → 选中候选的新书 URL/source | 服务端完成主键与目录切换；不把旧站书 URL 当新站 URL |

## 5. 测试证据与可信度

### 已执行

- 前端工程师：锁文件安装依赖；多次 vue-tsc + Vite 生产构建通过。
- 主审最终合并执行 utils + API + directives：**102/102**，包含默认 npm test 遗漏的 API/指令测试；不把其中重复执行的 SSE 用例重复计数。
- 独立 QA：真实 SFC 操作 18/18；真实 Chromium 桌面/390px 窄屏 15/15，pageErrors=0。包含原模块 IndexedDB 泄漏复现、候选隔离、搜索→详情直接缓存、延迟取消、范围、部分导出告警、媒体边界、EPUB 复制搜索/原版文件认证、简洁页换源成功失败、清本机缓存的跨用户边界。
- 原版本 Cargo lib 编译成功，依赖未升级。首次带宿主代理环境的 819 项运行出现 43 项失败及两个挂起：已保留原日志，但**不能把这些计为项目缺陷**。主审发现 http_proxy/https_proxy 将本地 mock 请求转发；相同原版 redirect 测试去掉代理后立即通过。原版与候选最终验证统一清除测试进程代理变量并单线程运行，绝未改生产代理或 SSRF 策略。
- 原版本 storage 子集 79/79 通过，作为候选新隔离测试的对照。
- 去代理后的原版全量实际为 **818 通过 / 1 失败，239.23 秒**；唯一剩余失败是禁用浏览器分支的 fixture 使用无法解析的 cf.example.com，提前触发 DNS 校验。候选将该测试目标改为字面公网 IP，通过现有校验后进入浏览器禁用分支，不发起 HTTP，也不放松生产 SSRF。

### 最终后端与集成结果

候选 `cargo test --locked --no-run -j2` 已成功编译 lib、main 与全部 7 套集成二进制。主审因最后一个 UUID 测试断言更新，额外完成 lib 指纹重编，业务与测试源码均已冻结。

独立 QA 直接执行最终二进制：advanced 4/4、audit_cache 4/4、booksource 2/2、bot_api 5/5、health 3/3、source_features 4/4、SSE 1/1，合计 **23/23，0 failed / 0 ignored / 0 filtered，reportedSkips=[]**。最终 audit_cache 四项 3.44 秒，含新旧 UUID 代数隔离与空章名清 TOC 回归。

该批集成测试关闭了 Camoufox 自动启动，验证的是真实 HTTP 规则与 HTTP 回退；不把它冒充动态浏览器导航/验证码求解通过。真实浏览器 UI 是前述 Chromium 独立验收，二者覆盖不同层。

主审直接执行最终 lib 测试二进制：**826 passed / 0 failed / 0 ignored / 0 filtered，272.97 秒，exit 0**。完整结果在 `tests/audit/artifacts/backend-lib-final.log`；最终编译记录为 `backend-compile-all.log` 与 `backend-compile-lib-final.log`。此前 storage 子集暴露的清目录丢标题和旧 namespace fixture 两项已修，最终全量均通过；没有通过排除失败用例取得全绿。

### 证据位置

- `tests/audit/frontend_cache_dialog.mjs`：真实 SFC 操作，可通过 AUDIT_BASELINE=7efd557 复现原始失败。
- `tests/audit/baseline_cache_sql.py`：从原始 SQL 复现全局清理误删。
- `tests/audit/browser_acceptance.mjs`：真实 Chromium + synthetic API，拒绝外网请求。
- `tests/audit/artifacts/browser/report.json` 及 screenshots：最终浏览器结果；`browser-r3-initial` 保留首次 EPUB 失败。
- `tests/e2e_audit_cache.rs`：实际 router + HTTP + 临时 SQLite + 延迟 mock 站点；覆盖双用户、导入原文、TOC 过期/清除、205 章、范围/章序、TXT/EPUB、取消与文件授权。
- `tests/audit/artifacts/backend-baseline-direct.log`、`backend-baseline-storage.log`：原版本去代理全量及 storage 对照记录。
- `tests/audit/artifacts/backend-integration-final/summary.json`：最终 7 套二进制逐项通过数、SHA-256、耗时、环境和完整日志位置。
- `tests/audit/artifacts/frontend-unit-final.tap`、`frontend-final.tap`、`frontend-build-final.log`：102 项单测、18 项交互及最终前端构建原始结果。

未覆盖：全部 live 书源可用性、真实书源登录/付费、生产缓存清理演练、生产数据库迁移、生产容器负载测试、完整镜像可重复构建。浏览器验收使用 synthetic API；服务器端实际行为另以真实 HTTP 集成测试证明，二者不混称。

## 6. 后续顺序与发布约束

1. 优先发布数据保护与账号隔离、取消与章序修复，再处理性能；不要仅上线前端隐藏按钮而让后端保持原危险行为。
2. 先将最终候选与来源对照提交放入 fork 历史上的审阅分支；审查内容后再发布 GitHub。禁止强推 unrelated history，禁止把 .env 或旧运行数据打包进仓库。
3. 新列 chapter_order 为兼容增量：只加可空列和可恢复元数据，不改正文、不删表。所有迁移验证先在测试库完成；生产加载会触发初始化，必须作为此次发布的明确变更核验。
4. 授权发布后沿用现有 reader-dev Compose 构建/启动路径，只更新目标服务；保留当前镜像作为回退，不重启其他 bot/数据站。发布前核对生产源码 HEAD 仍为本次基线，否则先合并新改动。
5. 用临时测试账号在生产做搜索→详情→缓存小范围→取消→阅读→导出验收；不要拿真实导入原文做删除实验。观察 OOM、失败章数、SSE 中断、SQLite busy、原版文件鉴权错误。
6. 下一批 P1 优化：`getBookCacheChapters` 虽然响应最多 200 章，当前内部仍加载该书全部缓存正文后排序切片，大书翻多页会重复全量读取。应先以章序元数据做 SQL LIMIT，再读取该页正文；本次修的是顺序与范围正确性，**没有声称完成大书低内存优化或根治历史 OOM**。
7. 其他后续优化：普通批量搜索和换源服务端全量 spawn/单源超时、失败源健康反馈与可暂停搜索；多书长期任务的全局资源预算；固定测试进程的回环直连环境并给 mock 等待设置超时。SSE 搜索已补单源 45 秒上限与断开取消；不要把它与仍待优化的普通换源搜索混称。必须用性能画像定并发，不按书源数量盲目提高。

生产重启与数据库变更遵循 `/www/wwwroot/bot/QIQI/qq/AGENTS.md` 的适用规则。用户此次要求调研并推进修复，未明确授权 reader-dev 生产重启；因此本次先交付可审阅、经测试的候选，不以旧的其他项目发布授权代替本项目授权。
