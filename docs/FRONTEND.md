# reader-dev 前端规划（Rust 版）

> 决策：**不复用 legacy 前端构建产物**（安全漏洞过多），全新前端。
> 状态：**已成型（v5.2.4）**——全部视图与后端联调完成，见文末「已实现进度」。本文 §1–§6 为技术选型与设计要求（§2 中部分要求尚未落地，见 §7 标注）。

---

## 1. 技术选型

| 项 | 选择 | 理由 |
|---|---|---|
| 框架 | **Vue 3**（Composition API） | 生态成熟、legacy 前端同为 Vue（迁移心智低） |
| 构建 | **Vite** | 现代、快、tree-shaking 好 |
| 语言 | **TypeScript** | 类型安全（API 契约可共享） |
| 状态 | **Pinia** | Vue 3 官方推荐 |
| UI | **Element Plus**（或 Naive UI） | 组件完备（legacy 用 Element UI，迁移平滑） |
| 请求 | **axios** | 与后端 /reader3/* 对接 |
| 内嵌 | **rust-embed**（后端编译时嵌入 dist） | 单二进制全功能 |

## 2. 安全要求（硬性）

> 注：以下为设计要求——其中「CSP 头」与「npm audit 门禁」**尚未落地**（见 §7 未落地清单）。

- **依赖零已知漏洞**：npm audit 门禁（CI 中阻断高危）
- **CSP 头**：后端下发 Content-Security-Policy（禁内联脚本/限制来源）
- 无 `eval`/`new Function`（书源规则 JS 在后端执行，前端不碰）
- 依赖锁定（package-lock.json）+ 定期 dependabot
- 构建产物最小化（Vite 默认）+ SRI（可选）

## 3. 页面结构

```
/                   书架（虚拟列表、分组、搜索入口、书源/书仓入口）
/reader/{url}       阅读页（翻页/滚动/听书/目录/进度）
/search            搜索页（多源并发 + SSE 进度）
/source            书源管理（列表/分组/导入导出/调试）
/rss               RSS 订阅
/user              用户管理（secure 模式）
/files             文件管理（书仓/WebDAV/数据目录）
/settings          设置
```

## 4. API 对接（Rust 后端 /reader3/*）

| 前端功能 | API |
|---|---|
| 登录/注册 | POST /reader3/login（accessToken 持久化） |
| 书架 | GET /reader3/getBookshelf |
| 搜索 | POST /reader3/searchBook / searchBookMulti（+SSE） |
| 详情/目录/正文 | bookInfo / bookToc / bookContent（切片 3-4） |
| 书源 | GET /reader3/getBookSources / saveBookSources |
| 文件 | /reader3/file/*（WebDAV 目录复用） |

## 5. 迭代顺序

1. 脚手架：Vite + Vue3 + TS + Pinia + axios 封装 + 登录页
2. 书架页（虚拟列表）+ 搜索页
3. 阅读页（核心：翻页渲染 + 进度）
4. 书源/设置/文件管理
5. rust-embed 内嵌 + CSP + 构建流水线（GitHub Actions）

## 6. 与后端开发并行

- 后端切片 3-7 进行时，前端按 1-5 顺序并行开发
- API 契约以实际后端为准（联调驱动）

---

## 7. 已实现进度

> 更新于 2026-08-09（v5.2.4——搜索并发提升、内置反检测浏览器增强与反爬域名自动优先、失效书源检测超时修复、书架密度按钮与悬浮简介层叠修复、书源管理/文件页/设置页移动端布局修复、正文无换行智能分句；v5.2.3 完成书源导入预览选择/排序、按书源分组搜索、书仓目录扫描导入、书架已读章节与未读更新数、正文 script 泄漏清洗、`java.createSymmetricCrypto`、暂不加入可返回、移动端竖屏适配）

### ✅ 已完成

| 项 | 说明 |
|---|---|
| **脚手架** | `web-ui/`：Vite + Vue 3.5 + TypeScript + Pinia + Vue Router 4 + Element Plus + axios（依赖锁定） |
| **视图（15）** | 书架/阅读/搜索/详情/探索/书源/文件/用户/规则/RSS/设置/登录/404 等——主页搜索框=全网搜书入口、换源弹层（书源名过滤+刷新）、元数据编辑、书源 header 编辑、分组拖拽、置顶、封面墙三态 |
| **阅读器** | 双翻页模式/主题/纸纹/简繁/亮度滑条/键盘翻页/快捷键/目录高亮/划词朗读/进度同步 |
| **PWA** | SW v2：`/reader3` 与 `/assets/proxy` 网络直连/网络优先（动态路径不缓存，上限 200） |
| **构建/CI** | `npm run build`（vue-tsc 类型检查 + vite）；GitHub Actions `frontend-ci.yml` 自动执行；node:test 单测（`web-ui/src/**/*.test.ts`） |
| **联调** | dev 代理 `/reader3 → localhost:8080`；SSE 流式搜索/调试/缓存进度 |
| **极简弹窗** | 书架移出/阅读挽留等确认改为项目自绘 `dlg`/`pop-card`（8px 圆角/白底/accent 按钮），ElMessageBox 仅保留非业务兜底 |
| **文件选择** | file input 由 `display:none` 改为视觉隐藏（Safari/macOS 程序化 click 不弹选择器的兼容修复） |

### ⚠️ 未落地（如实标注）

- **CSP 头**（§2 要求）：后端当前**未下发** Content-Security-Policy——待办
- **npm audit 门禁**（§2 要求）：CI 中**未配置**阻断高危依赖——待办
- **rust-embed 内嵌 dist**（§1 选型）：未采用——前端产物由 `READER_APP_WEB_ROOT` 指向目录（默认 `web-ui/dist`）

### 目录结构

```
web-ui/
├── index.html / vite.config.ts / tsconfig.json / package.json
└── src/
    ├── main.ts（SW 注册）/ App.vue / env.d.ts
    ├── styles/ / types/（与后端 camelCase 契约一致）
    ├── api/（30 个模块）/ stores/ / router/（登录守卫）
    ├── components/（LogoMark / ErrorBoundary）/ utils/（chinese/uiTheme/readerConfig/…）
    └── views/（15 个视图）
```
