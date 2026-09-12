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

# 第六轮（同日）：K4 书源书全文检索实现 + 积压清单全面核实刷新

| # | 项 | 处置 |
|---|---|---|
| K4 | **书源书全书搜索**（原「仅支持本地书内容搜索」） | `searchBookContent` 移除对架上书源书的拒绝——legacy searchBookContent 对书源书同样走 `searchChapter` 读**已缓存章节**匹配；master 的 book_chapters 表对书源书同样存缓存正文，直接复用既有搜索路径。测试翻转为缓存命中断言；web-ui cache.ts 契约注释同步 |
| F1 | saveBook 三分支迁移 | 核实已实现（`migrate_local_book_file`：assets 临时上传/localStore/webdav 三分支 → `data/{ns}/{书名}_{作者}/`，含 `test_save_book_local_file_migration`），积压清单翻[x] |
| 文档 | AUDIT-BACKLOG.md 全面刷新 | 逐项对照代码复核：K4/E10/E15/E16/F1/F6/F9/P2 批/PJ2/PJ4/PJ5/EG1/3/4/5 共 16 个「待办」实为已实现未回写，全部翻[x]并注明核实位置；清单自此与代码一致 |

**积压清单（AUDIT-BACKLOG.md）现状**：除「五、有意偏离」与 E 系列 3 个超长尾子项（downloadFile/getFile 文件级 API、AR-P2 边界打磨）外全部清零。REMAINING-WORK D 类（不移植）维持原议。后续按「实测暴露再修」驱动。
