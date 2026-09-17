# Bug 修复轮（2026-09-10）：全库体检 + 六项修复

> 背景：切换开发机后首次全库体检。逐项核对 `scripts/bug-audit-report.md`（2026-08-06 轮审计）
> 与 `docs/AUDIT-BACKLOG.md` 在当前 HEAD（37eff86 v6.0.45）的实际存续状态，修复仍存在项。
> 验证：lib 725 全绿 / E2E 全绿 / 前端 node --test 86/86 / `npm run build` 通过。

## 一、旧审计项核实（未修 → 本轮修复）

| # | 项 | 位置 | 修复 |
|---|---|---|---|
| m1 | **MongoDB 恢复丢 token_map**：`insert_user` 的 INSERT OR REPLACE 不绑定 token_map 列，restore_from_mongodb 恢复用户后全部多设备会话清空 | `storage/mod.rs` insert_user | 补绑定 token_map（JSON 字符串，与 migrate_users 一致）；新增回归测试 `test_insert_user_preserves_token_map` |
| m8 | 图片缓存盘命中下发 `immutable, max-age=31536000` 一年——上游换图后浏览器钉死旧封面 | `api/router.rs` assets_proxy | 盘命中改天级 `public, max-age=86400`；同步更新断言与文档注释 |
| m6 | image_cache in-flight 表请求取消泄漏：future 在 gate 锁/回源 await 点被取消时条目永久滞留 | `service/image_cache.rs` | RAII `InflightGuard`：Drop 时按 `Arc::strong_count` 判定——仍有多数参与者引用则留给最后退出者清理 |
| m10 | 监控页每 10s 轮询两个端点计入"今日请求量"，自计数虚增 | `middleware/stats.rs` | getServerStats/getSystemInfo 两端点不计自身 |
| m3 | 监控页标题缺 `route.serverStats` 键，document.title 显示字面量 | `web-ui/src/utils/i18n.ts` | 中英双语补键 |
| R6b | legacy 路由缺失：`POST /reader3/file/importPreview`、`GET+POST /reader3/file/restore` | `api/files.rs` + `api/router.rs` | 按反编译 FileController 语义补齐：importPreview 逐文件 `{book, chapters}` 预览（白名单外整单报错、缺失跳过）；restore 校验 zip/存在性后复用 restore_backup_zip；文案逐字对齐（"不支持导入{ext}格式的书籍文件"/"路径不是zip备份文件"/"路径不存在"）；测试 `test_import_preview_and_restore` |

## 二、核实为已修（文档滞后，本轮在 AUDIT-BACKLOG.md 补记）

- EG1 正则多链 + 全捕获组（rule.rs 1077-1120）
- EG3 JsonPath 裸存在性真值（test_jsonpath_filter_bare_existence）
- EG4 JS cache SQLite 持久化（js.rs JS_CACHE_STORAGE；lib.rs:123 serve() 注册 + load_js_cache_from_db 启动恢复）
- EG5 书源代理作用直连请求（crawler.rs reqwest::Proxy）
- M1 assets/proxy SSRF（逐跳 DNS 解析公网校验，crawler.rs）
- M2 图片缓存键含命名空间 + m7 全 32 位 md5
- M3 登录限流 XFF 伪造（READER_TRUSTED_PROXIES 白名单 + 直连 socket IP）
- M4 书架墙视图三态接线（BookshelfView VIEW_CYCLE）
- M5 sw.js /reader3/* 网络直连
- PJ4 textToSpeechCn（6916b48）
- AR1 isJSON 强转 / AR4 @get:{title,bookName} 回退（rule.rs）

## 三、已知未修（留档，后续批次）

| 项 | 说明 | 评估 |
|---|---|---|
| m9 | image_cache 盘读写在 std Mutex 内同步 IO——大图阻塞 worker 数十 ms | 需重构锁结构（状态锁与 IO 解耦），收益中风险中，暂缓 |
| m12 | camoufox cookie 外传远端求解器 | 默认 127.0.0.1 无风险；需文档标注（SECURITY.md 已有 SSRF/代理章节可并） |
| m13 | login_limit 持续伪造 IP 大表 | MAX_ENTRIES=8192 已封顶 + prune，实际风险低 |
| m14 | MongoDB 备份逐文档往返、无 bulkWrite | 千本以上书架才显著，P2 |
| F1 | saveBook 三分支迁移（部分完成） | 见 backlog |
| F11 | backupToWebdav zip 并入 books/ + backupToMongodb 全命名空间 | 见 backlog |
| PJ2 | /reader3/uploadFile assets 上传语义（同名异义） | 见 backlog |
| EG2 | XPath 非良构 HTML sxd 解析失败 CSS 兜底 | 见 backlog |
| C3 | EPUB 真机渲染验证 | 人工验收项 |

## 四、环境备注（新开发机）

本机（LoCCai）此前无 Rust 工具链（原 `C:\Users\chong\.cargo` 已不存在）：

1. rustup 自带下载器被安全软件静默拦截（os error 10013；curl/PowerShell 正常）——改用
   **standalone 发行版** `rust-1.98.1-x86_64-pc-windows-gnu.tar.xz`（curl 直下）装至
   `C:\Users\LoCCai\rust-gnu`；MSYS2 + mingw64 gcc 16.2.0 作链接器（pacman 走
   清华镜像 + XferCommand=curl；`mirrorlist.mingw/msys` 已配置）。
2. **PATH 顺序坑**：`rust-gnu/bin` 内的 libgcc_s_seh-1.dll/libwinpthread-1.dll 与
   mingw64 冲突——mingw64/bin 必须在 PATH 中先于 rust-gnu/bin，否则 cc1.exe 静默
   exit 1/127（cc-rs 编 C 依赖失败且无报错输出）。构建命令：
   `export PATH="/c/msys64/mingw64/bin:/c/Users/LoCCai/rust-gnu/bin:$PATH"`。
3. Rust 内嵌前端（rust-embed）需先 `cd web-ui && npm ci && npm run build` 生成 dist/。

---

# 第二轮（同日）：遗留 P1/P2 清单

> 验证：cargo test 全绿（725→727 lib + 14 e2e）/ 前端不受影响。

| # | 项 | 处置 |
|---|---|---|
| EG2 | **XPath 非良构 HTML 容错**【P1】 | `parser/xpath.rs`：严格 XML 解析失败时，经 html5ever（scraper 内置 HTML5 容错解析，与 JsoupXpath 的 jsoup 底座同级）重建 DOM，自定义序列化为良构 XML（标签闭合/属性引号/文本与属性转义/命名空间前缀剥离/非法 XML 名称跳过）后求值。良构文档仍走原严格路径（零开销）。覆盖：未闭合标签、未引号属性、void 元素、script 原始文本、实体解码。新增依赖 `ego-tree = "0.6"`（scraper 内部树类型，版本共用）。测试 2 例 + 旧「返回空」断言翻转为新语义 |
| F11 | 备份 zip 并入本地书原文件 | `storage/mod.rs`：`write_backup_zip` 收集 origin=loc_book 且实际存在于 storage 根内的原文件，按相对路径写入 zip `books/` 前缀（对齐 legacy createUserBackup 的 webdav/books 打包；epub 目录书按 index.epub/首个 .epub 实际文件入包）；`restore_backup_zip` 对 books/ 条目组件级防穿越（`..`/点开头/冒号/反斜杠拒收）后写回 storage 根内对应路径，`RestoreCounts` 增加 `bookFiles` 计数（serde default，向前兼容）。测试：跨实例回环 + 穿越拒收 |
| PJ2 | /reader3/uploadFile assets 上传 | 核实已实现（router.rs:496 + 端到端测试 19398），文档漏记，积压清单翻 [x] |

遗留不变：m9/m12/m13/m14、backupToMongodb 全命名空间、C3 人工验收。

---

# 第三轮（同日）：mongodb 备份性能 + 安全标注

| # | 项 | 处置 |
|---|---|---|
| F11 尾 | backupToMongodb 全命名空间遍历 | 核实已实现（`backup_to_mongodb` ns 为空 → `list_namespaces` 逐个备份，router.rs:6716 注释即 legacy 语义；`mongo_backup_ns` 接受 body/query ns），积压清单翻 [x] |
| m14 | MongoDB 备份逐文档往返 | `write_docs` 改为**有界并发（16）replace_one**——千本文档书架墙钟时间约降一个数量级；语义与串行等价（_id 互异、顺序无关）。注：driver 3.8 的 `bulk_write` 仅 MongoDB 8.0+ 服务端可用，备份目标版本不可控，故不用 bulkWrite（实测源码 `action/bulk_write.rs:27`） |
| m12 | camoufox cookie 外传 | `docs/SECURITY.md` 已知限制第 8 条：默认 127.0.0.1 无风险，配置远端地址时的风险与缓解（自控实例/加密隧道/TLS）如实标注 |

剩余留档：m9（image_cache 同步 IO，需锁结构重构）、m13（login_limit 表已 8192 封顶，风险低）、C3（人工验收）。

---

# 第四轮（同日）：m9 image_cache 锁结构重构

| # | 项 | 处置 |
|---|---|---|
| m9 | 图片缓存同步 IO 阻塞 worker | `read_disk`/`evict_lru` 重构为「锁内只做索引查改与字节记账，文件 IO 在锁外」——大图读盘/删除不再拖着索引锁，其他请求的缓存命中路径不再被阻塞。良性竞态（读盘瞬间条目被 LRU 清理）按未命中回源处理，正确性不变；孤儿文件由启动 seed 重新纳管。新增并发压力测试（24 线程 × 144 URL 持续驱逐下记账一致），重复跑 5 次无偶发 |

至此本轮审计清单中可自动化处置项全部清零；剩余：m13（已有 8192 封顶，风险低，留档观察）、C3（EPUB 真机人工验收）。

---

# 第五轮（同日）：C4 SSE e2e——并捕获一个真实并发 bug

| # | 项 | 处置 |
|---|---|---|
| C4 | searchBookMultiSSE 无集成测试 | 新增 `tests/e2e_sse.rs`：起**完整 HTTP 应用**（`router()` 首次用于集成测试）+ 双源 mock 书站 + reqwest 走真实 SSE 流。断言：①不过滤双源结果齐 + `event: end` 收尾；②`bookSourceUrl` 精确单源过滤（origin 全为指定源）；③`concurrentCount=1` 收敛单飞仍正常；④`lastIndex` 越界语义 |
| **新 bug** | **SSE end 的 lastIndex/isEnd 乱序倒退** | 测试首次运行即暴露：并发搜索用 `FuturesUnordered`，`last` 取「最后完成」的索引——乱序完成时 end 事件 `lastIndex` 倒退、`isEnd` 误报 false（2 源场景实测 `isEnd:false`），客户端按 lastIndex 续传会**重复搜索已交付源**。修复：`last` 取已交付最大索引（`i.max(last)`）。修复后 3 次重复运行稳定通过 |

C4 清零。测试缺口清单（REMAINING-WORK C 类）仅剩 C3 人工验收项。

---

# 第七轮（同日）：真实书源实测——导入兼容性修复 + 环境限制确认

按「实测驱动」策略，用 `scripts/backup-book-sources-431.json`（431 个真实书源备份）做导入与逐源搜索实测：

| # | 发现 | 处置 |
|---|---|---|
| **新 bug** | **真实书源备份整批导入失败**（实测 12/12 全拒）：① 布尔字段以 `1/0` 整数编码（Gson 宽容、serde 严格拒绝）；② `header` 为 JSON 对象、`exploreUrl` 为分类数组、`loginUi` 为控件数组（serde String 拒绝容器） | 新增 `util/serde_bool.rs`（bool/1/0/"true"/null 宽容反序列化）与 `util/json_text.rs`（容器→紧凑 JSON 字符串规范），接入 BookSource 的 enabled/enabledExplore/enabledCookieJar/header/exploreUrl/loginUi；`parse_explore_entries` 支持 JSON 数组形态。修复后 12/12 导入成功；单元测试 4 例 |
| 环境 | **reader-dev.exe 出站被安全软件按应用拦截**（http_fetch os error 10013——与 rustup 下载失败同根源；curl/python 白名单放行而新编译 exe 被拦，80zw 经 curl 301 可达而服务进程内 10013） | 非代码问题。已定位：**火绒安全 HipsDaemon（HIPS）**——Windows 防火墙全配置文件出站均为默认放行、无 reader 相关 Block 规则；改名/换路径复测仍 10013（按二进制信誉而非路径拦截）。放行路径：火绒 → 防护策略/信任区，将 reader-dev.exe 加入信任或允许其联网；固化 `scripts/real-source-probe.py`（含本机地址校验与 10013 诊断提示）供放行后复测 |

实测方法论沉淀：导入兼容性这类问题只有真实数据能暴露（431 源备份 100% 复现，而全部 743 个合成测试用例均通过）。

---

# 第八轮（同日）：全量 431 源离线引擎复现——org.jsoup.Jsoup 等 shim 补齐

写离线复现器（全量 431 源的 JS 型 searchUrl 直接过当前引擎的 URL 构造管线，不出网），
把 8 月书源审计报告的「js 缺口 70 源」重新度量：

| 度量 | 修复前 | 修复后 |
|---|---|---|
| URL 构造 OK | 32 | **38** |
| null-to-object | 11 | **4**（剩余为 `.match()` 未命中的离线预期行为） |
| not-callable | 8 | 9（诊断确认多为**书源自身 bug**，如蓝批系模板把 `Array` 当 `String` 调 `.startsWith`） |
| jsLib 函数未定义 | 3 | 3（源引用的辅助函数不在其 jsLib 中——书源侧问题） |

## 引擎修复（实测驱动）

| # | 缺口 | 影响 | 修复 |
|---|---|---|---|
| JS-1 | **`org.jsoup.Jsoup` 类名缺失**——shim 只装了 `org.jsoup.parse`，真实书源标准写法 `org.jsoup.Jsoup.parse(html)` 全部断链 | 42 源 / 184 处调用 | `Jsoup` 类（含 parse）挂到 `org.jsoup.Jsoup`；旧形态 `org.jsoup.parse` 保留 |
| JS-2 | `Jsoup.connect(url)` 缺失 | 4 源 | 极简 Connection shim：`data/header/headers/timeout/ignoreContentType/requestBody/method` 链式 + `get()/post()` 经 java.ajax 管线返回 Document |
| JS-3 | `java.getStringList` 缺失 | 120 处调用 | `java.getString` 列表形态（css_chain 全量结果逐条提取） |
| JS-4 | `java.startBrowser` 缺失 | 178 处调用 | fire-and-forget stub（返回 false 不中断脚本流；真实求解走 crawler webView/camoufox） |
| JS-5 | Response `headers()` 不支持 jsoup Map 语义 | `cs.headers().get("set-cookie")` 型消费（城堡小说） | 双模式：`headers(name)`→值数组（原语义）；`headers()`→含 `.get(name)` 的 Map 对象 |

诊断方法：逐行累积求值定位首个失败行 + `typeof` 全局内省（本轮排除了 org/cookie/baseUrl 等 8 月报告中的缺口——AR5/E12 修复已覆盖）。

剩余实测失败均为**书源侧问题或离线预期行为**：蓝批系模板数组调 startsWith（作者 bug）、
`.match()` 离线未命中、jsLib 未定义的辅助函数、API 型源需在线签名。

---

# 第九轮（同日）：浏览器实测探索/搜索——三个用户可见 bug 修复

用户实测环境（641 源：yckceo 1272/1270 两订阅导入）复现探索页「书源加载失败 重试」并逐层定位：

| # | 症状 | 根因（日志/回声服务器逐层定位） | 修复 |
|---|---|---|---|
| B-1 | 探索页 15s 前端超时 | `getExploreSources` 对 495 探索源逐一执行 `@js:` exploreUrl（含网络）——**实测 36.6s** | `count_explore_entries_offline` 离线计数（JSON 数组/`@js:`→1/多行），复测 **139ms** |
| B-2 | 纵横中文等 API 源探索/搜索 0 结果 | 三层叠加：① bookList 裸键规则 `result.resultList||result.bookList` 被判 CSS（JSON 上必空）且 `||` 未拆分；② bookUrl 单花括号内嵌 `{$.bookId}` 未展开（只支持 `{{}}`）；③ **POST form 体无默认 `Content-Type: application/x-www-form-urlencoded`**——纵横 API 对无 CT 请求返回「链接跳转」空壳（377 字节无数据），legacy OkHttp 默认带 form CT | ① bookList 分派按顶层 `||` 拆分 + CSS 类规则在 JSON 内容强制走 JsonPath（对齐 AR1）；② `expand_single_brace_json`（仅 `$.`/`$[` 开头，避开正则量词）；③ http_fetch 对无显式 CT 的 form 形 POST 体默认补 form 头。复测月票榜 **20 本**（首名《无敌天命》） |
| B-3 | 单 `#` 后缀源（`url#标记`，真实源常见形态）相对探索 URL 拼接错位 | explore_url 相对拼接只按 `##` 切分——单 `#` 后路径被吞进 URL 片段，请求打到站点根 | 与已有 `split("##")` 对齐为按 `#` 切分（base 取 fragment 前）——待办：本轮发现于诊断过程，主源纵横无片段已通；单 # 源修复随 B-2 的 base 计算一并处理见 router（注：实测 `#ZZ` 诊断源暴露，纵横主源无此问题；遗留标记于 follow-up） |

> B-3 说明：诊断源（`#ZZ` 后缀）暴露了单 `#` 片段拼接错位——修复方式与 B-2 的 base
> 计算耦合，本轮先以双 `#`/无 `#` 源验证主链路；单 `#` 源的真实分布与修复验证结转下轮。

验证：cargo test **738 lib + 15 e2e** 全绿（新增离线计数/裸键书单/全链路 POST 后缀 mock 3 组回归）；前端 86/86。
浏览器实测：探索页 495 源秒开、纵横月票榜出书、搜索流式正常。

---

# 第十轮（同日）：正式部署（reader.icai.top）<js> 探索脚本断链修复

用户在正式部署上报探索失败，错误信息即整段 `<js>` 脚本被当作抓取 URL（「目标 URL 非法:
relative URL without a base」）。脚本来自漫画站书源（albums-index 分类形态）：

| # | 根因 | 修复 |
|---|---|---|
| C-1 | `parse_explore_entries` 只认 `@js:`，`<js>...</js>` 整段脚本（legado jsRule 形态，漫画站探索主流写法）落入逐行分支——整段脚本成为条目 URL | 新增整段 `<js>` 处理：提取脚本 → `{{...}}` 模板经 bridge 展开 → eval 取 `[{title,url}]` 数组 |
| C-2 | 脚本内 `{{source.getBookSourceUrl()}}` 模板无展开路径；且 `@js:`/`<js>` 路径用**空默认 bridge**——`source.*` 恒为空 | source shim 补 `getBookSourceUrl()`/`getBookSourceName()` 方法别名（此前仅 `bookSourceUrl` 属性）；新增 `parse_explore_entries_for_source(url, source, ns)`（JsBridge::from_source），getExploreUrls 与书源 debug 均改用 |
| C-3 | 顺带：`entry_type` 的「更新」关键词把漫画站「最近更新」真实分类误判为外链（前端 window.open 打开） | 移除该过泛关键词 |

实现细节坑：`</js>` 为 5 字节而 `<js>` 为 4——首版按等长截断致脚本尾残留 `<`（SyntaxError: abrupt end），改 strip_prefix/suffix。

回归测试：用户报错原文脚本逐字复现（`test_parse_explore_js_script_with_source_url_template`）——断言模板展开为书源 URL、分组/按钮条目（无 url）跳过。
另实测确认 QQ浏览器源（JSON 数组探索）详情/目录/简介全链路正常（圣墟）——用户部署上的「未知书名」为旧构建下游效应，换新构建即解。

验证：cargo test **739 lib + 15 e2e** 全绿；前端 86/86。

---

# 第六轮（同日）：K4 书源书全文检索实现 + 积压清单全面核实刷新

| # | 项 | 处置 |
|---|---|---|
| K4 | **书源书全书搜索**（原「仅支持本地书内容搜索」） | `searchBookContent` 移除对架上书源书的拒绝——legacy searchBookContent 对书源书同样走 `searchChapter` 读**已缓存章节**匹配；master 的 book_chapters 表对书源书同样存缓存正文，直接复用既有搜索路径。测试翻转为缓存命中断言；web-ui cache.ts 契约注释同步 |
| F1 | saveBook 三分支迁移 | 核实已实现（`migrate_local_book_file`：assets 临时上传/localStore/webdav 三分支 → `data/{ns}/{书名}_{作者}/`，含 `test_save_book_local_file_migration`），积压清单翻[x] |
| 文档 | AUDIT-BACKLOG.md 全面刷新 | 逐项对照代码复核：K4/E10/E15/E16/F1/F6/F9/P2 批/PJ2/PJ4/PJ5/EG1/3/4/5 共 16 个「待办」实为已实现未回写，全部翻[x]并注明核实位置；清单自此与代码一致 |

**积压清单（AUDIT-BACKLOG.md）现状**：除「五、有意偏离」与 E 系列 3 个超长尾子项（downloadFile/getFile 文件级 API、AR-P2 边界打磨）外全部清零。REMAINING-WORK D 类（不移植）维持原议。后续按「实测暴露再修」驱动。


---

# 第十一轮（2026-09-14）：三订阅 1291 源实测验证——阅读链路五连修

导入 yckceo 1270/1271/1272 三订阅（2048 条记录，存量 1291 源/978 主机），构建四段链路
探针（搜索→详情→目录→正文，scripts/reading-chain-probe.mjs）按主机去重抽样 24 源三轮验证：

| # | Bug（实测复现） | 根因 | 修复 |
|---|---|---|---|
| D-1 | **156zwcc 搜到书点开全空**（用户报障原文复现） | ① bookUrl 为协议相对 `//host/path`（og:novel:read_url 原样）——`Url::parse` 无 base 报「目标 URL 非法」；② 搜索出口 field_url 对结果 `//` 前缀排除补全（误把 XPath 保守带到结果位置） | ① `normalize_with_source`（fetch_url 入口统一：`//`补 scheme/`/`拼 origin/相对 join）覆盖详情/目录/正文全链路；② 结果分支 `//` 也走 to_absolute |
| D-2 | **详情 name 为空 ×10/24 源**（「未知书名」主因） | 大量源 ruleBookInfo 无 name/author 规则（只有 cover/intro），调用方（搜索点开）又不传名 | 三级回退：规则求值 > 传入名（handler 新增 name 参数） > **页面 h1/og:title/\<title\> 兜底提取**（含验证页标题黑名单与站点尾巴截断） |
| D-3 | **`@html` 提取器返回带壳整串** → 正文管线清洗后空 | scraper 0.20 `parse_fragment` 序列化壳为 `<html>…</html>`（非 `<html><body>`），strip 不匹配 | 三形态前/后缀剥壳（回归测试锁定） |
| D-4 | **单源搜索报「未配置书源」**（实为 600s 失效短路误伤） | 失效过滤无差别拦截——用户点名的源也被跳过且文案误导 | bookSourceUrl 单源指定绕过失效过滤（批量场景保持短路） |
| D-5 | 探针工具修正 | data 形态 `{content}` / 单源参数名 bookSourceUrl | reading-chain-probe.mjs 固化（可复用） |

**三轮探针对比**：info name 空 10→3、未配置书源 4→0、156zwcc 全链路（搜索 165 本/详情/目录
1145 章/正文 2266 字）通。剩余 toc 0 章 ×14 抽查确认多为**站点拦截页**（m.yibige 详情被拦
h1=「访问验证」→ 下游规则求值出垃圾 tocUrl）——站点侧为主，黑名单已防误报，结转下轮
（tocUrl 垃圾值校验 + 拦截页检测）。

验证：cargo test **741 lib + 15 e2e** 全绿；前端 86/86。


---

# 第十二轮（2026-09-15）：QQ浏览器源「未知」六连修（正式部署实测）

用户在正式部署报「📚QQ浏览器」搜到书点开未知。书源调试面板输出直指 `resourceId=` 空参 +
jsError cannot convert null——逐层定位出**六个叠加缺陷**（每层修完暴露下一层）：

| # | 缺陷 | 根因 | 修复 |
|---|---|---|---|
| E-1 | 搜索 bookUrl `resourceId={{book.kind}}` 恒空参 | legado `{{book.xxx}}` 实体字段引用（引当前条目已求值字段）无实现——落 JS 分支无 book 绑定即报错 | 搜索条目 `replace_book_refs`（bookUrl/coverUrl 求值前替换 name/author/kind/intro 等已求值字段） |
| E-2 | 浏览器优先系统性污染 | `browser_first_enabled` 默认 true——所有 GET 先走内置浏览器，**浏览器会话不带书源 header**（Referer/Q-GUID 等），API 站大量返回错误文本被当 body | 默认改 false（legacy 语义：仅 webView 显式/browser_needed 标记/网络层失败兜底走浏览器；`READER_BROWSER_FIRST=1` 可显式开启） |
| E-3 | 登录头空值覆盖 | 搜索 JS putLoginHeader 存的动态头含空 Referer，无条件覆盖源头真实 Referer | merge_login_header 空值不覆盖非空既有值 |
| E-4 | `@js` 动态 header 生成的空 Referer | 「📚」源 header 本身是 @js 脚本，生成 Referer:""（脚本缺陷），QQ 详情/目录 API 一律拒（incorrect referer）——node 对照实验证实**必须有 Referer** | fetch_url 兜底：header 求值后 Referer 为空串 → 回填书源根（非空定向 Referer 不受影响） |
| E-5 | 详情 tocUrl `{{$.resourceID}}`/`{{book.kind}}` 均空 | ① URL 型规则被 field 当字面量返回（{{}} 未展开）；② 详情阶段无 {{book.x}} 替换 | ① evaluated 含未展开 {{}} 时走 expand fallback；② analyze_book_info 的 kind 先求 + `replace_info_book_refs` |
| E-6 | 探针/前序误判 | 正文响应 data 形态是 {content} 对象 | （探针已修） |

**修复后「📚QQ浏览器」全链路**：搜索 20 本（bookUrl resourceId=1134522101 正确）→
详情（name/intro/cover 齐全）→ **目录 1595 章**（第1章 绝顶资质）→ 正文：chapterUrl 的
`{{baseUrl.match(/bookId=(\d+)/)[1]}}` 动态模板在目录条目循环未生效（回退 tocUrl）——
**遗留结转**（目录条目内嵌 JS 正则求值），见第十三轮。

验证：cargo test **743 lib + 15 e2e** 全绿；前端 86/86。


---

# 第十三轮（2026-09-15）：QQ浏览器正文打通 + 试读四报障收口

用户正式部署四报障（试读弹「书籍未加入书架」/阅读无内容/缓存面板 0 章/点章节「未找到
这本书」）——后三个同根：**chapterUrl 多行级联缺失**。实测「📚QQ浏览器」ruleToc.chapterUrl
为 `$.serialID` 换行 `@js:` 独立行 + 多行 JS（`BookID: book.kind` 实体引用 + `result`
前段结果 + 手工拼 POST body URL）。

| # | 修复 | 说明 |
|---|---|---|
| F-1 | `chapter_url_with_cascade`（chapters_from_items 专用） | legado splitSource 级联：按行分段、前段结果注入 result、`@js:` 独立行**吞噬后续所有行为代码**（首版只取同行空串致静默空）、`book.kind/name/bookUrl` 标识符按 vars 预替换字面量 |
| F-2 | `analyze_toc_with_kind` + bookKind 注入链 | 目录阶段 `book.kind` 实体引用需要详情求出的 kind——handler 传参 + **tocUrl `bookId=\d+` 提取兜底**（QQ 型源通用形态） |
| F-3 | 目录缓存污染清理 | 首次坏结果（chapter.url=tocUrl）被 toc 缓存——验证需 clearCache；缓存键含章节 URL 故修复后新缓存自愈 |

**修复后全链路实测**：目录 1610 章（ch0Url = ads-read POST 模板 BookID 正确）→
**正文 2310 字**（第1章「飞羽界，阙天门。徐凡……」）——阅读/缓存（章节列表非空）/点章节
三个报障全部解除；试读浏览器实测**无弹窗**（问题 1 为正文断链下游效应，随修复自解）。

坑记录：诊断期间被 toc 缓存误导两轮（级联已通但响应吃缓存）——缓存键不含规则版本，
同类调试需先 clearCache。


---

# 第十四轮（2026-09-15）：《圣墟》（🏷QQ浏览器）正文打通——URL 后缀对象 body 三连修

用户问"不是应该所有书源写法都兼容吗"——目标确是 legacy 语义全集；legado 规则语法×JS×
组合空间极大，冷门组合只有真实源踩到才暴露，每例固化回归测试使兼容面单调递增。
《圣墟》与《我的师傅》走库里**两条不同 QQ 源**，章节 URL 形态不同，踩出三个新缺口：

| # | 缺口 | 根因 | 修复 |
|---|---|---|---|
| G-1 | chapterUrl 为**跨行 JSON body 的 URL 模板**（`ads-read,{` 换行 `"method"...`） | cascade 按行盲切撕碎 JSON——首段 `ads-read,{` 被当选择器 | `merge_unbalanced_lines`：括号/引号未闭合时并入下一行（平衡感知），闭合后整体成段 |
| G-2 | cascade 选择器段用普通 field 求值 | URL 模板被当 JsonPath 解析成空 → ctx 残留 item 原文 → 拼 `/api/book/%7B...` 乱码 URL | 选择器段改 `field_url_with_vars`（URL 语义：直判+{{}}展开+相对拼接）+ 结果含 `{{` 时再内嵌展开 |
| G-3 | **UrlSuffix.body 严格 String** | 后缀 body 为 JSON 对象（🏷 形态）→ 整个后缀反序列化失败 → `url,{...}` 带尾巴当 URL 请求 → 响应非 JSON 解析空 | body 宽容反序列化（json_text：String 原样/对象→紧凑 JSON 串） |

**实测**：《圣墟》目录 1696 章 → **正文 2252 字**（第1章"大漠孤烟直，长河落日圆"）；
《我的师傅》（📚 源）无回归（2310 字）。两形态回归测试入库
（`test_chapter_url_url_template_multiline_json_body` / `test_url_suffix_object_body`）。

验证：cargo test **746 lib + 15 e2e** 全绿（8 套件）。


---

# 第十五轮（2026-09-16）：久久小说（m.9191net.com）四报障——@html inner/作者清洗/封面回退/mixed-content

| # | 报障 | 根因 | 修复 |
|---|---|---|---|
| H-1 | 详细信息带 HTML 标签 | `@html` 提取器返回 outerHTML（含选中元素自身标签）——legado/jsoup `html()` 是 **innerHTML** | `html_without_scripts` 改 inner（outer 剥首尾标签，子节点全保留）；两个旧断言随语义更新 |
| H-2 | 作者带一串「分类：…状态：…」尾巴 | 源 `##` 多行清洗正则按旧模板书写失配（要求 RAR/ZIP 段而页面已无） | 双兜底：① replaceRegex 行边界兼容（pattern 含字面换行而文本无换行 → `\s+` 宽松重试）② format_book_author 元数据行剔除 + 仍多行取首非空行（作者天然单行） |
| H-3 | 书籍页封面空 | 源 ruleBookInfo 无 coverUrl 规则，详情不回退 | getBookInfo 新增 `cover` 参数（搜索/探索跳转携带），cover 求值空时沿用（legacy BookInfo 合并）；前端 4 处调用透传 |
| H-4 | https 部署下 http 封面全挂 | 浏览器 mixed-content 拦截（久久/大量 http 源） | proxyImageUrl：https 页面 + http 图片自动经 /assets/proxy（无需用户开关） |
| —— | 章节目录只有 1 项「TXT下载」 | **站点为打包下载站**（详情页仅一个下载按钮，正文规则输出下载链接）——源/站点形态，legado 同样 | 非缺陷（向用户说明） |

**实测**：author=金陵雪、intro 纯文本、封面回填、QQ 两书（我的师傅/圣墟）无回归。
验证：cargo test **748 lib + 15 e2e** 全绿；前端 86/86 + build 通过。
诊断坑：进程内详情缓存（book_info_cache）会掩盖 handler 层修复——验证需重启实例。


---

# 第十六轮（2026-09-16）：三源三报障——宜搜 @put 贯通修复 + 两项定性

| 报障 | 定性 | 处置 |
|---|---|---|
| QQ浏览器 resourceId=3 详情空 | **数据源侧**：QQ API 对 resourceId=3 返回 resourceName=""（无效/下架 ID，服务端无数据——node 直连 API 对照确认） | 非引擎 bug。该 URL 的产生入口（换源/推荐）待用户提供路径再查 |
| 宜搜详情 tocUrl `gid=&nid=` 空参 | **引擎 bug ×2**：① 搜索字段路径（field_impl）无 `@put:{k:v}` 处理（只有 rule.rs apply_single 有）——name 规则 `name@put:{nid:nid}` 的变量从未存入；② 即使存了也只在条目局部 vars，循环即丢——未按条目 book_url 落书级变量表 | ① field_impl 开头 split_put + apply_put_vars（rule.rs 开放 pub(crate)）；② analyze_book_list_impl 条目尾部 save_book_vars（键=条目 book_url，详情 @get 同键命中）。实测 tocUrl `gid=100023412&nid=23412` 正确注入 |
| 瑶路书探索失败（camoufox 报错） | **部署侧**：站点全程 Cloudflare 403（直连实测），源写 webView:true 正确；服务器未装 camoufox（需 python3 + 依赖或 READER_CAMOUFOX_URL） | 错误文案扁平化（双层"抓取失败"嵌套 → 单层全链）；根治需服务器部署 camoufox（见 docs/SECURITY.md 已有说明） |

验证：cargo test **748 lib + 15 e2e** 全绿（新增 test_field_put_saved_to_book_vars）。


---

# 第十七轮（2026-09-16）：QQ resourceId=3 追根——推荐聚合卡治理

用户再报「这本书还是不行」（resourceId=3 详情页 + 截图：封面显示「此图片未经允许 不可引用」）。

## 根因链（实测还原）

1. **resourceId=3 的来源**：QQ 搜索接口 `so.html5.qq.com` 的 `$.data.state[*]` 混有
   **推荐聚合卡**（groupID=`tabpage_hub_novel_search_recomm_N`，卡内 items 列多本推荐书）。
   源 kind 规则 `$.groupID##.*_##` 剥前缀后取到**尾号数字**（N=2/3/…按天变化）→
   bookUrl 模板 `?resourceId={{book.kind}}` 拼出 `resourceId=3`。QQ API 对该 ID
   服务端无数据（resourceName=""）→ 死链详情页。
2. **截图里的防盗链封面**：同一聚合卡的 coverUrl（`$..cover_url` 递归命中卡内广告图，
   来自 QQ 严防盗链 CDN）被前端携带到详情页 → 显示「此图片未经允许 不可引用」。
   真实书封面（qbnovel.qq.com/static/…）实测无 Referer 也 200，无防盗链问题。
3. **真实书全链路验证通**：《模拟修仙十年，我天下无敌》（kind=1143352450）
   搜索 21 条第 1 → 详情（混沌冬瓜精）→ 目录 186 章 → 正文「天武大陆，风溪国…」。

## 修复

| # | 问题 | 修复 |
|---|---|---|
| J-1 | 聚合卡 name 为 `$..title` 递归多命中拼接的**多行名**（5 本书名叠一行列） | format_book_name 多行 → 取首非空行（与 format_book_author 同款；书名天然单行） |
| J-2 | 换源匹配双向包含 `ql.contains(&bl)` 对空名恒真 | search_book_source 过滤前先排除空名条目（防御其它路径产出空名） |
| —— | 空名条目（NovelGuid/桩条目） | 第十三轮起 analyze_book_list_impl 已丢弃（name 空 return None），本轮复核确认 |

注：聚合卡条目本身保留（源设计如此，legado 同样产出该卡）；其 kind=尾号死链为
QQ 服务端数据形态，引擎无法凭空补数据——治理目标是呈现一致（单行名）+ 匹配不误吸。

## 探针事故记录（本组两次假阳性，均已修正）

- `String(j.data)` 对 getBookContent 的 `{content}` 对象形态输出 "[object Object]"——
  误判正文 bug，实为 legacy 契约对象（data.content 才是正文）。
- Git Bash curl `--data-urlencode "key=中文"` 命令行编码劣化 → 引擎收到 mojibake key →
  QQ 返回劣化响应（5 条含 recomm_3 卡）→ 一度误判「引擎搜索劣化」。纯 ASCII
  percent-encode 复测 21 条正常。**中文参数探针一律用显式 percent-encode。**

验证：cargo test **749 lib + e2e** 全绿（新增 test_qq_state_list_junk_entries +
format_book_name 多行断言）；8100 实例实测 21 条/0 多行名/真实书第 1。


---

# 第十八轮（2026-09-16）：fqbook.cc 探索失败——https 握手拦截的引擎级降级

## 报障

`探索失败：抓取失败（https://fqbook.cc/ranking.php?t=click）: Connection reset by peer (os error 104)`

## 根因（三栈交叉验证）

fqbook.cc 对**非浏览器 TLS 客户端在握手阶段一律重置连接**——schannel curl / rustls
（引擎）/ node OpenSSL 三栈全被 reset；**明文 http 完全正常**（200，48KB 榜单页）。
legado（OkHttp）同样过不去。浏览器兜底依赖 camoufox 部署，属重依赖路径。

## 修复

| # | 修复 | 说明 |
|---|---|---|
| K-1 | **https→http 同路径降级重试**（http_fetch 直连失败分支，先于浏览器兜底） | 仅「传输/握手层失败」触发（reset/handshake/EOF/超时）；降级失败不吞原错误；成功照常 capture Set-Cookie |
| K-2 | 错误判定用 `{e:#}` **全链格式** | reqwest 顶层 Display 仅 "error sending request for url (...)"，传输层细节在 cause 链——`to_string()` 使分类器永远 miss（should_browser_rescue_error 同修） |
| K-3 | OS 错误串**本地化覆盖**（中文 Windows） | "远程主机强迫关闭…(os error 10054)"——补 10054/10053/10060 数字码与中文文案 |
| K-4 | **find_book_source 空参直拒** | `LIKE '%%'` 命中任意源（fetch_optional 取物理顺序第一行）——调用方漏传 bookSource 时拿到毫不相干的源、用错规则（本轮调试中实测：探索拿 QQ 源 ruleSearch 解析 fqbook 页面） |

证书错误不降级（第十六轮「证书错误不重试」语义——https 证书坏的站强行明文裸奔
风险不对称）；DNS/业务层错误与 scheme 无关，不降级。

## 验证

- 实例实测：点击榜 50 本（郝叔和他的女人…）、分类玄幻 {{page}} 翻页 30 本——
  https 撞墙 URL 全自动走降级
- cargo test **751 lib + e2e** 全绿（新增 test_https_downgrade_candidate /
  test_find_book_source_empty_param_rejected）

## 调试弯路留档（又一次探针参数名事故）

实例探索 n=1 追查两小时：curl/node 复刻全是桌面页、进程内 explore_url 也 50 本。
临时探针揭示 bookList 规则是 `$.data.state[*]`（QQ 源！）——**探针发的是
`bookSourceUrl=` 而 handler 读 `bookSource=`**，空参 + K-4 漏洞 = 拿错源。
与第十三轮 searchBookMulti 参数名事故同型：**探针参数名先抄 handler 源码再发**。


---

# 第十九轮（2026-09-16）：疯读小说四报障——分组头/PC横滚/分页契约/详情「未知」

| # | 报障 | 根因 | 修复 |
|---|---|---|---|
| L-1 | 点击「❀男生频道❀」报 `目标 URL 非法: relative URL without a base` | exploreUrl `title::` **空 url 分组标题行**不满足 `!url.is_empty()` → 落穿到「普通 URL 行」把整行（含 `::`）当可点条目（JSON 数组分支本就过滤空条目） | `::` 分支空 url → continue 跳过（分组标题纯装饰）；前端 openCategory 空分类防御（双保险） |
| L-2 | PC 端分类标签超出屏幕看不到、没法切换（手机可滑动） | `.cats` 横滚容器的 wheel 处理器在 onMounted 时用 `querySelector` 绑定——**元素在 v-else 分支尚未渲染，绑定永远失败** | 改模板 `@wheel.prevent` 绑定（元素渲染即生效），移除生命周期 querySelector hack |
| L-3 | 「后端分页接口待实现：当前仅展示第一页」 | 前端期待 `{books,hasMore}` 对象契约，后端按 legacy 返回纯数组（不能改——legado 客户端兼容） | 前端数组契约原生化：页非空可续页 + **去重后零新增 = 没有更多**（防 {{page}} 缺失的源无限加载）；删除误导文案 |
| L-4 | 探索点开详情「未知」 | legado 语义：书名来自搜索/探索**条目**（疯读 ruleBookInfo 只有 lastChapter），探索页 goBook 跳详情没带 name/author/cover（第十五轮只做了搜索页） | goBook 携带 name/author/cover；BookDetailView 读取 query name/author 接入展示回退链 + 透传接口（后端 name/cover 回退已有） |

**实测**：分类 53 个（63−10 分组头，0 空 url）；第 1/2 页不同书目（{{page}} 生效）；
详情 name 回退正确；目录 530 章；正文 2259 字。
验证：cargo test **752 lib + e2e** 全绿（新增 test_parse_group_header_line_skipped）；
前端 vue-tsc + build + node --test 100/100。

调试留档：详情 name 乱码两小时——bash 探针命令行编码劣化 + **进程内详情缓存**把
乱码结果钉住（重启实例后干净请求即正确）。与第十五轮同坑：缓存键不含参数形态。


---

# 第二十轮（2026-09-17）：legado 对照优化·批次 A——后端就绪接口前端接线

参照 warpdotsys/legado（阅读 Sigma fork）功能面盘点（后端 ~25 个已注册未接接口 + legado
高频项 + AGENTS.md 既定大项），制定四批推进计划（A 接线 / B 阅读器 / C 管理 / D 多媒体）。
本轮为批次 A（全部前端接线 + 1 处后端参数宽容）。

| # | 接线项 | 说明 |
|---|---|---|
| A-1 | **换源主链路** | switchSource 优先 POST /setBookSource（服务端换 bookUrl 主键 + 预取新源目录缓存，legacy 同名语义），成功后路由跳新地址自动重载 + GAP6 进度重定位；失败降级原 saveBook 补丁。换源弹层先 GET /getAvailableBookSource（refresh=0 持久化候选秒回）再 SSE 流式合并（按 origin 去重） |
| A-2 | 书源管理 | 「禁用失效」按钮（POST /disableInvalidBookSources，探测+禁用一体）；「复制」行按钮（克隆全字段，URL+#copy、名称+副本、默认停用） |
| A-3 | RSS 批量导入 | importJson 优先 POST /saveRssSources（单事务），404 降级逐条 |
| A-4 | 备份/会话 | 设置页备份卡新增：WebDAV 恢复（restoreFromWebdav，覆盖/仅缺失双按钮语义）、服务端即时打包下载（user/downloadBackupFile blob）、MongoDB 备份/恢复（uri/db 记忆 localStorage）；logout 先调后端注销 token 再清本地 |
| A-5 | 导出/全文 | export.ts 补 exportToTxt/exportToEpub（Pro 兼容包装）；阅读器加「复制全文」（getAllContents——服务端已缓存章节拼接，未缓存章跳过）；后端 export_common_params 补 url 别名（与 exportBook/getBookInfo 参数宽容一致） |

**已覆盖确认**（调研清单复核）：getRssContent= getRssArticle 别名路由（前端已用主名）；
file/parse、file/restore 已有等价 UI（importPreview/restoreFromZip）；uploadFile 前端已接。

**8100 实测**：getAvailableBookSource 候选返回/持久化 ✓；saveBook→缓存 2 章→getAllContents
total 2 ✓；exportToTxt 13086 字节（url 别名生效）✓；exportToEpub application/epub+zip ✓；
logout 非 secure 返回「不支持的操作」（legacy 语义，前端静默）✓。
验证：cargo test **752 lib** + 前端 vue-tsc/build + **node --test 100/100** 全绿。


---

# 第二十一轮（2026-09-17）：legado 对照优化·批次 B——阅读器体验

| # | 项 | 实现 |
|---|---|---|
| B-1 | **点击区域自定义**（legado ClickActionConfigDialog） | 点击方案新增 custom：3×3 九宫格每格循环切换 上一页/下一页/菜单/无；localStorage `reader_click_zones`；默认布局复刻 auto |
| B-2 | **页眉页脚提示栏**（legado TipConfig） | 页眉=书名·章节名、页脚=章节进度·时钟（30s 刷新）；pointer-events 穿透不挡翻页；设置面板双开关（默认关，opt-in） |
| B-3 | **划词查词**（legado TextActionMenu 查词） | 划词工具条加「查词」→ 弹层展示选中文本 + 有道/汉典/百度/必应词典新页入口（规避 iframe X-Frame 限制） |
| B-4 | **下一章预读 + 服务端缓存修复** | 预读升级：正文走本机缓存管线（抓取→saveLocalChapter，翻章秒开）+ 预热前 5 图；**连带修复：getBookContent 从未传 bookUrl——服务端单章阅读缓存从未写入**（只有整本缓存流程写），现在正文阅读/预读均携带（epubContent=1 不带，防 HTML 污染纯文本缓存）；开关 `reader_preload_next` 默认开 |
| B-5 | **朗读定时停止**（legado TTS 定时） | TTS 面板新增 15/30/60/90 分钟定时（播放中显示剩余）；引擎管理复核确认已完整（服务端优先+localStorage 降级） |
| B-6 | **EPUB 原版渲染主题联动** | EpubIframe 新增 themeOverride/bgColor/textColor props——</head> 前追加 !important 覆盖（原书 CSS 之后注入，优先级最高）；ReaderView 按主题解析色值（custom/深/暖/浅/system 跟随）；开关 `reader_epub_theme_follow` 默认关（保留原书观感） |

移动端专属项（音量键/传感器/电量）不移植。验证：前端 vue-tsc + build +
node --test 100/100 全绿（本批纯前端 + api 参数扩展，后端无改动，752 基线不变）。


---

# 第二十二轮（2026-09-17）：legado 对照优化·批次 C——书籍·书源管理增强

| # | 项 | 实现 |
|---|---|---|
| C-1 | **书籍变量管理 UI**（legado 界面变量编辑） | 新增后端 `GET/POST /reader3/getBookVariables`/`saveBookVariables`（读=bookUrl 级 @put 累积表；写=整体覆盖，数值/布尔宽容转字符串）；详情页「变量」弹层（键值行编辑/增删/保存）。8100 实测写读回显 ✓ |
| C-2 | 目录倒序 + 精简目录（详情页目录 tab） | tocEntries 加 reverse/精简开关——精简=剥离连载尾巴（`（2）/（3）`）后判重只留首个；工具按钮随 hint 行 |
| C-3 | 搜索范围限定 | **复核确认已完整实现**（searchGroups chips + searchBookMulti(SSE) bookSourceGroup 透传） |
| C-4 | 替换规则作用域 | 规则编辑器补全 legacy 全字段：isRegex（正则开关+提示联动）、分组、作用域 scope（按书 bookUrl 正则）、生效位置 scopeTitle/scopeContent 复选；保存透传（后端模型本就支持）；服务端化/批量导入复核已有 |
| C-5 | 书架封面批量补全 | 多选批量栏「补全封面」：选中且缺封面（含 custom 为空）的书逐本 getBookInfo 回填 coverUrl → saveBook，统计 N/M |
| C-6 | 调试面板改进 | 「复制日志」一键导出（源信息+动作+输入+全量日志，失败行 ✗ 前缀）；失败步骤红色高亮已有 |

GAP 199（「最近添加」排序需 books 表加时间戳列）仍留 backlog（schema 变更收益比低）。
验证：cargo test **753 lib**（+test_book_variables_api）+ 前端 vue-tsc/build + 100/100。


---

# 第二十三轮（2026-09-17）：legado 对照优化·批次 D——漫画/EPUB/RSS 多媒体

| # | 项 | 实现 |
|---|---|---|
| D-1 | **漫画条漫竖滑模式**（webtoon，AGENTS.md 大项） | 漫画阅读加「竖滑/横向」切换（localStorage 记忆）：竖滑=整宽纵排连续滚动（无吸附、无边缘点击翻页），当前页按视口 40% 线计算；横向=原翻页模式不变 |
| D-2 | **EPUB HTML 模式编辑放开** | 编辑器支持 HTML 源码编辑（保存→服务端+本机缓存→chapterHtml 即时生效）；纯文本模式读到 HTML 缓存时按 chapterHtmlToPlain 剥离标签降级（防模式互串出满屏标签）；TTS/划词复核已支持 HTML 模式 |
| D-3 | RSS 音视频播放 | **复核确认已完整实现**（文章净化渲染 + enhanceRssMedia：原生 mp4/mp3 直播 + m3u8 动态挂 hls.js，切文销毁） |

CBZ 说明：CBZ 本地书走 zip 图片列表 → bookType=2 漫画管线（现有逐页模式 + 本轮竖滑模式均可用）。
验证：前端 vue-tsc + build + node --test 100/100 全绿（本批纯前端）。


---

# 第二十四轮（2026-09-17）：批次收尾——C4 拖拽排序补齐 + EPUB 链路冒烟

| # | 项 | 说明 |
|---|---|---|
| 补-1 | 替换规则拖拽排序（C4 计划内未竟半项） | 规则表行拖拽（手柄 ⋮⋮ + 落点虚线提示），落点即重排 order 并全量 saveReplaceRules 保存（服务端+本地镜像） |
| 补-2 | EPUB 链路冒烟（REMAINING-WORK C3 的自动化部分） | 构造最小 EPUB3（mimetype/container/opf/spine/2 章+css）→ uploadLocalBook（解析出书名「EPUB 冒烟测试书」/作者/toc）→ getBookToc 2 章 → getBookContent epubContent=1 返回完整 HTML（<p> 结构+正文）——三段全通；原版渲染（epubLoader zip 解析）已有单测覆盖。视觉/内链体验仍属人工验收 |

四批计划至此全部落地（复核确认已实现的 6 项不计入新代码）。遗留 backlog：GAP 199
（books 表时间戳列）、flv/dash 媒体格式、Wi-Fi 传书/MP3 等 D 类不移植项维持原议。
验证：前端 vue-tsc + build + node --test 100/100；后端 753 基线未动。
