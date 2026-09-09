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
