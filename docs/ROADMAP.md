# Reader-dev 路线图（Roadmap）

> 状态：**Rust 重构已成型（v5.2.4）**——核心功能全部落地；本文档记录已完成项与剩余待办。
> 更新日期：2026-08-09。版本号以 `Cargo.toml` 为准（当前 `5.2.4`）。
> 原则：**只列已实现/已确认的事实**；未实现项一律标注「计划/未实现」。

---

## ✅ 已完成（v5.2.4）

### Rust 重构主体
- [x] axum + SQLite 服务端（`/reader3/*` API 与 legacy 兼容，ReturnData 结构一致）
- [x] JSON→SQLite 自动迁移（检测/备份/逐表/校验回滚/JSON 保留只读归档/raw_json 保底）
- [x] 多用户：注册/登录、权限开关（WebDAV/本地书仓/书源/RSS）、用户管理
- [x] 多设备 token（uuid v4，每用户 5 个并存）+ token 过期（`READER_TOKEN_TTL_DAYS` 默认 30 天）

### 书源与阅读
- [x] legado 多规则引擎全量：CSS / JSONPath / XPath / Regex（fancy-regex lookbehind）/ JS（boa 沙箱 + `java.*`/`source.*` shim + AES）
- [x] 书源管理（增删改/启停/分组/失效检测/导入导出/订阅/header+loginUrl+cookie 编辑）、书源调试（SSE 逐步日志）
- [x] 换源（并发多源 + 书名去重 + 弹层书源名过滤/手动刷新，SSE 流式）
- [x] 阅读器全套（翻页模式/主题/纸纹/简繁/预加载/TTS/快捷键/进度同步/划词朗读/复制本章…）
- [x] 整书缓存、全书内容搜索、阅读统计、书架分组拖拽/置顶/封面墙（三态）
- [x] 双向章节缓存（服务器 / 本机 IndexedDB）、范围缓存（JSON 数值参数兼容）、迁移 `toc_url` 回填、正文 HTML 清洗（v5.0.6/v5.0.7）
- [x] 管理员命名空间与 default 系统配置层分离：管理员默认本人账号（个人书架/进度/书签），显式进入 default 编辑公用数据（书源/规则/RSS 等）；default 中历史个人数据启动时自动回迁管理员本人，配置类数据保留 default，幂等（v5.0.8）
- [x] legacy 全量对齐（v5.0.9）：默认 TXT 目录规则 18 条全量移植（含启用状态）、本地文件名书名/作者解析（《书名》/作者：xx/by 模式）、CBZ ComicInfo.xml 书名/作者与首图封面；完整审计文档见 `docs/legacy-parity/`
- [x] legacy Web UI 批次（v5.1.0）：simple-web 搜索详情弹窗/直接阅读/更新章节/换源、RSS 分类 tab + 分页、14 张内置阅读背景图库、替换规则批量删除与 JSON 导入导出、RSS 源编辑/JSON 导入/sourceIcon、书源订阅批量删除（移除无意义禁用语义）、阅读页详情入口与追更开关
- [x] v5.2.0：阅读中换源（作者/最新章/当前章末尾预览，切换保留进度）、规则引擎修复（JS 搜索 URL、相对 URL、URL/URLSearchParams、书源 jsLib/variable 全局注入、data URI）、chardetng 统计式编码探测、内置反检测浏览器默认兜底、Docker 构建分层复用、移动端宽度自适应
- [x] v5.2.1：MOBI/AZW3 未知编码中文修复（PalmDoc/Huffman 原始字节解压 + chardetng 编码探测），《盗墓笔记重启·极海听雷》样本正文验证通过
- [x] v5.2.2：KindleMOBI 记录尾部 trailing/multibyte 附加数据清理（与 KindleUnpack/Calibre 同口径），PalmDoc 重叠回引按标准展开，修复 4KB 边界后中文乱码与残留 HTML；新增合成 trailing data 回归测试
- [x] v5.2.3：书源导入预览选择/排序（全选/反选/仅新增/重复标记）、按书源分组搜索、书仓目录直接扫描导入书架（重复扫描幂等）、书架已读章节名与未读更新数、正文 script 泄漏清洗、`java.createSymmetricCrypto` 对称解密、暂不加入可返回、移动端竖屏适配、分组 ID 旧数据容错
- [x] v5.2.4：搜索并发提升（多源 24 / SSE 48）、内置反检测浏览器增强（stealth 指纹补齐 userAgentData/deviceMemory/触控/PDF/网络/音频 + 反爬特征识别扩展 + 反爬域名自动优先 30 分钟）、失效书源检测超时修复（后端 96 并发 + 前端 axios 900s 超时 + 返回类型归一）、书架密度按钮/悬浮简介层叠修复、书源管理/文件页/设置页移动端布局修复、正文无换行智能分句
- [x] 主页搜索框 = 全网搜书入口（回车跳搜索页）
- [x] 书级 `@put/@get` 变量贯通搜索 → 详情 → 目录 → 正文（含 `bookUrl`/`tocUrl` 双 key 保存、URL 内嵌 `@get` 拼接）
- [x] 详情封面相对路径转绝对 URL（修复入架后首字封面/佚名/无章节目录）
- [x] JS shim 补齐：`cookie.*` 读写书源 cookie、`java.getCookie/timeFormat/timeFormatUTC`、全局 `gzip`（GZip→base64）
- [x] 前端极简确认弹窗（书架移出/阅读挽留，自绘 `dlg`/`pop-card`）
- [x] 远程书源订阅：宽松类型归一（数组/`bookSourceList`/单对象 + 字符串数字/布尔）+ 抓取/请求超时放宽到 45s/60s
- [x] file input 视觉隐藏兼容 Safari/macOS（本地书/本地书源/封面/背景图选择）
- [x] Release Linux 资产修复：zip 不再空包（移除 `-i .`），新增独立 `reader-dev-linux-x64-musl` 静态二进制

### 本地书 / 协议 / 数据
- [x] 本地书 **9 格式**：EPUB/TXT/MOBI/AZW3/PDF/FB2/DOCX/CBZ/UMD
- [x] 双轨书仓（文件监听 300ms 去抖 + DB 对账；`READER_LOCAL_BOOK_DIR`）+ `migrateLocBook` 迁移工具
- [x] OPDS 1.2 / 2.0 / PSE + 独立 OPDS 账号（sha256+salt）
- [x] WebDAV 服务器（OPTIONS 预检/PROPFIND/GET/PUT/MKCOL/DELETE/MOVE/COPY/LOCK/UNLOCK）
- [x] 备份恢复闭环（backupToWebdav / restoreFromZip / restoreFromWebdav）+ 启动快照（保留 5 份）+ 每日自动备份（`READER_AUTO_BACKUP_HOUR` 默认 03:00，保留 7 份）

### 反爬 / 安全
- [x] **obscura 反检测浏览器集成**（唯一浏览器后端——替代 Chrome/Edge，无回退；stealth 构建：BoringSSL TLS 指纹/反检测/追踪器拦截；`READER_OBSCURA_URL` 直连或 spawn）
- [x] camoufox 强质询兜底（Firefox 内核真实指纹，HTTP 后端；`READER_CAMOUFOX_URL/DISABLE/FIRST/UA`）+ FlareSolverr 可选
- [x] CF 质询/Turnstile 求解 + POST 保真重试 + cookie 按 name 合并复用 + 真实书源 69shuba 实测
- [x] 安全审计 6 个 major 全修（2026-08-06，提交 `e5f12b4`）：SSRF 逐跳校验 / 图片缓存跨用户隔离 / 登录限流直连 IP（XFF 忽略）/ 封面墙 / PWA SW v2 / JS 桥超时 10s
- [x] 上传上限（`READER_UPLOAD_MAX_MB` 默认 100MB，413 明确错误）

### 工程
- [x] 新前端（Vue3 + Vite + Element Plus，15 视图，vue-tsc 严格类型检查 CI）
- [x] CI：rust-ci（fmt/clippy/test）、frontend-ci、release-rust + docker-publish-rust（`v5.*` 标签触发 + `origin/master` 祖先 guard + 多架构镜像）
- [x] Docker 镜像：`debian:trixie-slim`（GLIBC）+ **tini 入口**（1Panel 兼容）+ 内置 obscura/camoufox/python + CA/时区
- [x] Release 资产：`reader-dev-linux-x64-musl`（musl 静态）+ `reader-dev-windows-x64.exe`（签名 job）
- [x] 后端测试 612（规则引擎/迁移/OPDS/9 格式/CF 端到端/Turnstile/WebDAV/obscura）

---

## ⏳ 剩余待办（未实现——如实标注）

| # | 项 | 状态与说明 |
|---|---|---|
| 1 | **Windows 签名发布首次验证** | `build-windows-signed` job 已就绪（Authenticode + PFX secrets，secrets 缺失时拒绝发布）；但 `WINDOWS_CODESIGN_PFX` / `WINDOWS_CODESIGN_PASSWORD` **尚未配置验证过**——首次签名发布需确认证书加载/签名产物可用 |
| 2 | **69shuba 住宅代理验证** | 数据中心 IP 被 Turnstile 风控（实测 `400030` 环境风控——与 UA/指纹无关）；**代码无代理配置项**（`READER_PROXY_URL`/书源代理字段为设计方向，未实现）；需先接线代理配置再做全自动验证 |
| 3 | **argon2 密码哈希升级** | **实现中**（工作区已接线：`src/util/password.rs` argon2id PHC m=65536,t=3,p=4；新用户/改密直接 argon2id，legacy 双 MD5 兼容校验 + 登录自动升级；未合入发布版）。OPDS 独立账号保持 sha256(salt\|\|pwd) |
| 4 | **服务端 TLS + HTTP/2/3** | 计划（未实现）。当前服务端纯 HTTP（TCP 监听，无 TLS/QUIC）；设计：`READER_APP_TLS_CERT`/`READER_APP_TLS_KEY` + quinn+h3 双栈监听 |
| 5 | **客户端 HTTP/3 启用** | reqwest `http3` feature 已编译入但**未调用**（未启用 `reqwest_unstable` cfg）——书源直连实际 HTTP/1.1（也无 `http2` feature）；需 cfg 启用 + QUIC 传输参数指纹细化 |
| 6 | **EPUB zip 炸弹防护** | 条目大小/数量上限缺失（PDF 已有 8MB 解压上限）；当前受 `READER_UPLOAD_MAX_MB` 缓解 |
| 7 | **多实例部署支持** | 单实例假设：SQLite + 内存态缓存（目录/正文/语音列表/登录限流）不跨进程协调；多副本需按实例拆分数据目录或前置外部限流 |
| 8 | **macOS 发布资产** | 当前 Release 仅 `linux-x64-musl` + `windows-x64.exe`（Windows 签名未验证，见 #1） |

---

## 开发与发布策略（当前）

- **分支布局**：`master` = Rust 重构发布主线（本文档）；`legacy` = Kotlin 稳定版（v4.x，ghcr.io/warpdotsys/reader-dev:latest）
- **发布工作流**（`release-rust.yml` + `docker-publish-rust.yml`）：`v5.*` 标签触发 + 发版 guard（要求触发 SHA 为 `origin/master` 祖先，防止误发）+ Linux/Windows 构建并行 + 多架构镜像推送 + GitHub Release 资产
- **版本号**：以 `Cargo.toml` 为准（当前 `5.2.4`）
- 许可策略：**永久不做用户/功能限制**（`READER_APP_USERLIMIT` 等 env 默认宽松）
