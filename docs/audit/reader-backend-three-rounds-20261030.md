# reader-dev 后端三轮调查、修复与验收（2026-10-30）

基线：193 已确认的干净 `master 7efd557`；候选工作树为
`reader-dev-audit-20261030`，分支 `audit/reader-three-rounds-20261030`。
公开 GitHub master 仅作预调查，所有实现均以 193 克隆的该提交复核。
未在 193 执行数据库查询、迁移、源码覆盖、部署、重启或 Git 推送。
下述数据库验证仅使用临时合成 fixture，不读取用户书架或正文。

## 架构与缓存实际范围

规则执行从 `search` / `book` 经 `crawler` 抓取后进入 CSS/JSONPath/XPath/Regex/Boa
规则引擎；书源 JS bridge 按 namespace 注入。此次没有改变生产 SSRF 策略。
`searchBook` 为单源 JSON，`searchBookMulti` 为并行聚合 JSON，
`searchBookMultiSSE` 为逐源 `data` 事件及最终 `event:end`。
逐源候选必须保留 origin，供主 UI 聚合及换源使用。

| 数据 | 键/范围 | 生命周期/限制 | clearCache 是否清理 |
|---|---|---|---|
| 书籍详情热缓存 | namespace + 书 URL | 10 分钟，FIFO 200 | 否 |
| 目录 `toc_cache` | namespace + book/toc URL | 抓取命中 TTL 5 分钟；旧快照仍可解释已存正文次序 | `toc` / `all`，仅本人 |
| 网络正文 `book_chapters` | namespace + book URL + chapter URL 的 60 位 MD5 键 | 永久直到清理；新增 `chapter_order` 保存安全实章位置；被动阅读写入受 `cache_chapter_content` 控制 | `chapters` / `all`，仅本人可再生正文 |
| 导入本地正文，同表 | namespace + 本地书 URL + 顺序下标 | 原始数据，不是可丢弃缓存 | 保留 |
| 预缓存任务 | namespace + taskId | 运行中；结束保留 1 小时，start/cancel 时回收 | 不停止运行任务 |
| 图片代理磁盘缓存 | MD5(namespace + URL) | 默认 512MB，LRU；同键回源合并 | 否 |
| JS `cache` | namespace + 脚本 key | 脚本 expiry，0 表示永久；内存上限及定时物理清理 | 否 |
| `@put/@get` 书级变量 | namespace + source + book/toc/chapter URL | 内存 512，持久表 14 天 / 20 万行定时裁剪 | 否 |
| 失效源快照 | namespace + source URL | 600 秒 | 否 |
| 浏览器离线缓存 | 当前前端 owner + book URL | 浏览器侧策略 | 服务端 clearCache 不清理 |

统计和清理使用相同的可再生正文谓词；书架、导入本地原文、图片、JS 登录变量及
浏览器缓存不计入此次服务端正文清理范围。尺寸仍沿用 SQLite `length(TEXT)` 的
字符近似值，不是实际磁盘文件字节数。`clearCache` 清理当前已存数据，不停止后台
缓存任务，后续运行任务或新的阅读请求可能重新写入。
`cache_chapter_content=false` 只关闭被动阅读的正文缓存写入；用户显式启动的服务器
缓存任务仍会写入，不能把该配置描述成全部服务器缓存的总开关。

## 第一轮：数据归属、误删与章节顺序

基线确定性问题：

1. `getCacheInfo` / `clearCache` 虽要求登录，但 storage SQL 不含 namespace。
   两个用户缓存同 URL 后，A 可统计和清除 B 的行。
2. `clearCache(chapters/all)` 无条件删除 `book_chapters`，导入 `local://` 正文
   与网络缓存混表，导致本地原文被当作缓存删除。
3. 任务表只按 URL / URL#范围建立键，两个用户同书会复用/观察/取消同一任务；
   运行检查与插入分锁，还可重复启动同一任务。
4. 基线已经有 MD5→目录位置补丁，但仅使用 5 分钟有效目录，按 hash 顺序截前
   200 行再展示 position，范围分页仍不可靠；Pro 只读导出直接按 hash 排序。

修复：scoped 统计/清理事务，共用本地正文保护；任务采用 `(namespace, taskId)`
结构键且原子检查插入；外部 taskId 为每一代任务独立 UUID，运行中相同书/范围才复用；cache-only API 共用 `ordered_cached_chapters`，排除卷标题，
输出安全的零基实章位置。先按目录排序/过滤再截页，`hasMore` 精确探测下一条，
`nextFrom` 返回续页位置。后台任务成功解析目录后保存快照。

验收目标：`audit_r1_clear_cache_scopes_users_and_preserves_primary_local_content`；
模块测试 `cache_clear_is_scoped_and_preserves_imported_bodies`、
`cache_task_progress_and_cancellation_are_namespace_scoped`。

## 第二轮：取消时序、并发和写入真实性

基线残余取消缺陷：虽然已有 abort 剩余 JoinHandle 的修复，但取消检查前取出的
当前 handle 不在残余迭代器中。正在等待的章节可在取消后返回并写库；被 drop 的
当前 handle 仍可继续抓取。全目录提前 spawn 也产生与章节数成正比的排队任务。
写入失败被忽略仍计为 cached，SSE `failedCount` 恒为 0。

修复：以 `buffer_unordered(3)` 直接轮询最多 3 个正文 future，取消时丢弃在途
future，尚未开始的目录项不再调度。Notify 中断目录或正文等待。数据库写入与
取消确认共用异步 gate：取消返回前等待已开始的 commit 完成，然后标记取消；
后续写入取得 gate 后必须检查取消状态。文件型本地书重缓存同样按章检查。
失败抓取/写库计入真实失败数，只有成功持久化才计入 cached。

最终独立复审另外捕获任务世代问题：仅把 namespace 加入原 `url#range` 键，仍会使
已经结束的任务重启时复用同一个外部 ID。迟到旧 cancel 会误伤新任务。修为每代
独立 UUID；运行中相同 ns/书/范围原子复用，重启必须生成新 ID。精确 taskId 查询
不会退化成 URL 查询；legacy URL 操作独立按书查当前任务。新增
`late_cancel_of_old_generation_cannot_cancel_restarted_range`，QA 在真实 HTTP 取消
场景追加“取消首代→重启同范围→迟到旧 ID→新代仍写入 3 章”的断言。

验收目标：`audit_r2_cancellation_during_inflight_fetch_does_not_write_and_is_user_scoped`；
模块测试 `cancel_acknowledgement_waits_for_inflight_commit`。

局限：正在执行的 Boa/spawn_blocking 计算或远端已收到的 HTTP 请求不能保证瞬间
撤回；本次保证该任务取消确认后不会提交新正文，并停止后续章节调度。

## 第三轮：清目录后的导出、同 URL 多用户、本地 EPUB 与搜索契约

由第二轮产物继续复审得到以下跨功能问题：

- 只清目录而保留正文后，源书的 60 位正文键无法重建顺序。新增可空
  `chapter_order` 列；目录刷新事务内更新现有缓存的次序，启动时对历史 NULL
  结合已有目录按当前书 toc 优先、旧到新回填。更新删除/重复的 URL 同时清除
  陈旧位置，绝不把过时位置继续当成当前目录。
- 清目录后的实测又发现入参为空的章节标题未持久化。目录同步及普通正文写入现在
  将已知 TOC 标题一并保存，空标题 UPSERT 不覆盖已有标题；清 TOC 前后的 TXT/EPUB
  产物保持一致。
- 重复 URL 可在目录存在时按两个标题读取同一缓存正文；由于一个 URL 缓存键
  无法单独表达两个次序，持久位置保留 NULL。目录完全清除或历史行根本没有目录
  时明确提示“目录缓存缺失，请先刷新目录”，不随机导出或泄露超出 JS 精度的 hash。
- `delete_book` / `delete_books` / `switch_book_source` / `replace_chapters` 只用
  书架 URL 归属子查询，没有过滤实际章节行 namespace；同 URL 两用户仍会互删。
  本地导入/迁移/替换漏写 ns，本地目录与自动生成 EPUB 全量读取也漏 ns。现已逐层
  传递 namespace，包含 legacy 章节缓存迁移。
- `getBookFile` 新增 GET：按当前用户书架 URL 定位原始 EPUB，成功返回
  `application/epub+zip` 和 `private,no-store`。secure 模式进一步校验当前 ns
  文件根；default 仅额外接受既有外部书仓配置。前端不再把 `local://` 或完整
  `storage/...` 当作 file/download 的 home 相对路径，也不再遗漏鉴权。
- 文本缓存/导出明确拒绝媒体书类型；前端同步解释能力。Pro TXT 导出 MIME 与
  实际 UTF-8/GBK/GB18030 编码一致。
- 搜索 exact 接受 JSON 布尔值，单源 POST 接受与 GET 相同的书源别名；SSE
  点名单源绕过短期失效标记，超大 searchSize 使用饱和加法，单源执行复用 45 秒
  上限，客户端断开时直接取消 stream 内未完成 future。逐源候选保留，不跨源
  删除 UI 所需的 origin。书名/作者去重改为元组，避免下划线串接产生碰撞。
- 批量预缓存将切片前的实章位置直接写入，避免每章反复解析整份目录造成 O(N²)。

验收目标：`audit_r2_expired_toc_hash_rows_page_and_export_in_directory_order`、
`audit_r3_original_epub_file_requires_shelf_and_own_namespace_path`；模块测试
`cached_bodies_follow_expired_directory_order_without_volumes`、
`shared_book_url_replacement_and_deletion_preserve_other_namespace`、
`historical_order_backfill_is_idempotent_and_duplicate_urls_remain_explicit`。

## 测试证据与复现入口

独立 QA 实现：`tests/e2e_audit_cache.rs`；基线 SQL oracle、浏览器检查与回执见
`tests/audit/README.md`、`tests/audit/artifacts/`。所有数据均为合成内容。
root 统一使用 `/tmp/reader-dev-audit-target-20261030` 与 `cargo -j2`；不并行启动
第二个完整构建。QA 对 root 最终编译的 7 个精确集成测试二进制独立串行运行，
**23 passed / 0 failed / 0 ignored / 0 filtered**，无程序报告的跳过：advanced 4、
audit 4、booksource 2、bot_api 5、health 3、source_features 4、SSE 1。逐二进制
SHA-256、退出码、运行时间和完整输出见
`tests/audit/artifacts/backend-integration-final/summary.json` 及同目录日志。

最终四项独立 HTTP 验收包含 UUID 世代与清 TOC 后空标题回归，4/4 通过，测试本体
3.44 秒，日志 `tests/audit/artifacts/backend-http-final.log`。此前 12.23 秒的回执
另存 `backend-http-pre-generation.log`，不用于代表最终版本。root storage 模块首轮
为 81 passed / 2 failed：清目录后章名丢失为新缺陷，已修复 TOC 标题持久化；删用户
fixture 因旧无 namespace 原始 INSERT 与新隔离模型不符，已改成两个用户各有正文的
正确 fixture。

最终候选完整 lib 已实际运行：**826 passed / 0 failed / 0 ignored / 0 filtered**，
272.97 秒，日志 `tests/audit/artifacts/backend-lib-final.log`。上述首轮失败和旧回执
保留为修复过程证据，最终验收以本次全量 lib 及最终独立 HTTP/集成结果为准。

第一轮基线全量运行出现大量 HTTP mock 的 502/挂起。root 随后确认原因是宿主
代理变量影响回环 fixture；清除代理后代表性 redirect 测试立即通过。因此撤回
“SSRF 测试未适配”的初步归因，不把环境失败计为项目缺陷。统一基线/候选命令需：

```sh
env -u http_proxy -u https_proxy -u HTTP_PROXY -u HTTPS_PROXY -u ALL_PROXY -u all_proxy \
  NO_PROXY=127.0.0.1,localhost,::1 CARGO_TARGET_DIR=/tmp/reader-dev-audit-target-20261030 \
  /root/.cargo/bin/cargo test -j2 --test e2e_audit_cache -- --test-threads=1
```

QA 已执行的后端基线证据为 `tests/audit/artifacts/baseline-cache-sql.json`：
从真实 7efd557 提取 SQL，在临时 SQLite 构造两用户目录 2 行、正文 4 行（含本地
主数据），执行后全被清除。此证据是实际 SQL oracle，**不是已运行的基线 HTTP**。
四个独立 HTTP fixture 的最终结果来自上述已编译候选的独立 QA 实跑。

无代理基线完整 lib 运行：818 passed / 1 failed，239.23 秒。唯一失败为
`test_cf_builtin_browser_unavailable_returns_clear_error` 的合成 DNS 名不再解析，
SSRF 校验因而先于预期 disabled 分支返回；root 只把 fixture 改为字面公网 IP，
不触发真实请求，不改变生产逻辑。此前代理导致的批量失败均已排除。

## 排除项、迁移兼容与剩余限制

- **排除误报**：7efd557 的 `deleteBookCache` handler 已拒绝标准 local:// 本地书；
  storage 原语能 DELETE 不能单独证明该 API 会误删。此次共享保护谓词及
  origin=local 收敛记为防御性加固，不计已证实 API 漏洞。
- **排除已修项**：公开 GitHub 预调查中的 POST `=key` 前缀遗漏，在 193 版本已由
  `strip_search_exact_prefix` 修复，没有重复计为本次成果。
- 新列 nullable、幂等补列，正文缓存主键及正文内容不变。旧程序忽略新列；回退源码
  无需删列。迁移和历史回填仅在临时 fixture 验证，**未在生产执行**。
- 无目录的旧 hash 缓存无法凭空恢复顺序，必须刷新目录；重复 URL 在清除目录后
  亦需刷新。错误提示是明确的行为边界。
- 清缓存不停止后台任务，任务可能回填；按用户/书批量取消后再清理属于后续改进。
- **下一批 P1**：`getBookCacheChapters` 响应最多 200 章，但内部仍读取该书全部缓存
  正文再排序切片，大书翻多页会重复全量读取并占用内存。应先按章序元数据排序并用
  SQL LIMIT 取页，再读取该页正文。本次只证明顺序和范围正确，不宣称大书低内存
  优化或历史容器 OOM 已根治。
- `get_available_book_source` 的整批 spawn 和缺少逐源超时仍记录为待优化项，本次
  未扩展为另一次调度重构。
- 本轮不证明全部真实互联网书源可用，也未穷举全部第三方 JS/反爬行为；现有规则
  引擎测试与真实书源上线探测必须分开理解。最终集成进程使用
  `READER_CAMOUFOX_DISABLE=1`、`READER_CAMOUFOX_SPAWN=0` 并去除外部 solver URL；
  WebView HTTP 回退已执行，真实动态浏览器、浏览器登录及验证码求解未执行。
