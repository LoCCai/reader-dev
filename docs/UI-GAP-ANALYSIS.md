# UI Gap Analysis：master web-ui ↔ reader-pro-3.2.14.jar 内嵌前端

> 生成日期：2026-08-24 · 只读分析产物 · 基准 = `C:\Users\chong\Downloads\reader-pro-3.2.14.jar`（权威）∪ `origin/legacy`（可读源码）

## 0. 方法与证据源

| 证据源 | 说明 |
|---|---|
| JAR `BOOT-INF/classes/web/` | Pro 编译产物：`index.html` + chunk `index / index~reader / reader / setting` + 独立调试页 `bookSourceDebug/` + 全局媒体库 `flv/hls/dash/webtorrent/pear-player` + 内置背景图 `bg/*.jpg`(14 张) |
| `origin/legacy` `web/src/` | Pro 前端的可读源码基础（Vue 2 + Element UI）。与 JAR 特征逐一比对吻合：`epub/cbz/pdf/pageMode/epubMode` 在 legacy Reader.vue 与 JAR `reader.js` 中计数一致；`setting` chunk = `ReadSettings.vue`；bg 预设名与 JAR `bg/` 目录同名 |
| `master` `web-ui/src/` | Vue 3 + TS + Vite，15 个路由视图，ReaderView 7690 行 |

结论先行：**页面级 master 已反超 Pro**（Pro 仅 书架/阅读 两路由 + 设置面板 chunk）；差距集中在**阅读器细节交互、阅读偏好管理、TTS 细项、RSS 媒体播放**四个维度。

## 1. 页面级对比

| Pro（legacy/JAR） | master web-ui | 状态 |
|---|---|---|
| `/` Index.vue（书架 + 全部弹窗挂 App.vue） | `/` BookshelfView.vue | ✅ 覆盖并拆分更细 |
| `/reader` Reader.vue | `/reader/:bookUrl` ReaderView.vue | ✅ 见 §3 |
| setting chunk（ReadSettings 面板） | ReaderView 内嵌设置弹层 + `/settings` SettingsView | ✅ |
| `bookSourceDebug/index.html` 独立调试页 | SourceManageView 内 SSE 调试弹窗（search/toc/content 等 action） | ✅ 等价实现 |
| — | `/login` `/book/:url` `/search` `/explore` `/sources` `/rules` `/rss` `/files` `/store` `/users` `/server-stats` `/404` | ➕ master 新增 |

## 2. 差距矩阵（Pro 功能 × master 状态）

图例：✅ 已对齐 · 🟡 部分对齐/实现方式不同 · ❌ 缺失 · ➕ master 独有

### 2.1 阅读器能力

| Pro 功能 | Pro 取值/证据（legacy） | master 现状 | 状态 |
|---|---|---|---|
| 翻页模式 | readMethods：上下滑动/左右滑动/上下滚动/上下滚动2 | pageMode scroll/hslide/slide/flip（flip 含仿真列分页） | ✅ |
| 全屏点击方案 | clickMethods：下一页/自动/不翻页/固定模式 | 仅「点击翻页」开/关 tapZones（左上/右下/中间菜单） | 🟡 |
| 点击区域仿真翻页 | clickMethod=下一页 时点击即仿真翻页动画 | flip 模式已支持仿真；点击区触发已有 nextPage/prevPage 动作 | ✅ |
| 页面模式 | pageModes：自适应/手机模式 | 无对应概念（contentWidth 三档近似） | ❌ |
| 特殊模式 | pageTypes：正常/Kindle（简洁：关动画+精简首页功能） | 有 chromeHidden 隐藏顶栏/底栏，非完整简洁模式 | 🟡 |
| 配置方案多档案 | customConfigList 命名方案 + 新增/删除/切换 | 单套 localStorage 键，无方案档案 | ❌ |
| 日夜自动切换 | config.autoTheme 定时切换白天/黑夜方案 | uiTheme 仅手动/跟随系统 | ❌ |
| 方案同步 | 设置面板 同步/保存/自动同步 开关（saveUserConfig） | readerConfig.ts 上传/下发合并 + SettingsView 备份 | ✅ |
| 阅读主题 | 8 内置主题（含纹理图）+ 自定义色（font/body/content/popup 四色） | light/dark/warm/system/custom（背景/文字/强调三色）+ bg 纹理/preset/上传图 | ✅ |
| 字体 | 内置 宋/楷/黑/仿宋 + 自定义字体上传（custom-* 槽位 ×4） | FontKind ×12 + 自定义字体上传（IndexedDB+@font-face） | ✅ |
| 简繁转换 | chineseFont：简体/繁体/原文 | hanMode auto/simp/trad | ✅ |
| 字号/字重/行高/段距/字距/缩进/对齐/页宽 | 全部具备 | 全部具备（letterSpacing/textAlign 为 master 增强） | ✅ |
| 边距 | topPadding/bottomPadding/horizontalPadding | 仅 contentWidth 控宽 | ❌ |
| 翻页动画时长 | animateMSTime | reader_animate_ms（0-1000ms） | ✅ |
| 自动阅读（滚屏） | autoReadingMethod：像素滚动/段落滚动 + 滚动像素 + 翻页速度 | 自动阅读定时滚动 + 5 档速度，无段落滚动档 | 🟡 |
| 划线选择行为 | selectionAction：操作弹窗/忽略 | 总是提供划线操作（加书签/加净化规则），无「忽略」开关 | 🟡 |
| 快捷键自定义 | quickKeyMode 默认/自定义，10 键位下拉选择 | JSON 导入式自定义（e.code→action）+ 恢复默认 | ✅（交互不同） |
| 请求超时 | chapterRequestTimeout | reader_chapter_timeout | ✅ |
| EPUB 渲染 | epubMode：iframe（默认，原版排版）/解析（HTML 提取） | 仅解析模式（getBookContent n=1 整章 HTML 净化渲染），无 iframe 原版模式 | 🟡 |
| CBZ/漫画 | .cbz 本地书 + bookType=2 cartoon：图片列表渲染 | isComicBook type=2 图片横滑+点击翻页+懒加载 | ✅ |
| PDF | 不内嵌渲染，「读原书」按钮 file/download stream=1 新标签打开 | 无专门入口（FileManage 可预览下载） | 🟡 |
| 音频/视频书 | type=1 audio / type=4 video 播放 | 音频/视频播放器 + 进度记忆 | ✅ |
| 亮度调节 | （App 端功能，web Pro 无滑杆） | brightness filter 0.6-1.4 + 弹层 | ➕ |
| 屏幕常亮/命令面板/i18n/OPDS/导出 epub·txt·html/导入预览/书仓 | 无 | wakeLock/CommandPalette/i18n/opds/exportBook/importBookPreview/StoreView | ➕ |

### 2.2 听书（TTS）

| Pro 功能 | master 现状 | 状态 |
|---|---|---|
| local 引擎（浏览器 speechSynthesis + ttsVoices 列表）+ rate/pitch | 后端合成引擎（POST /reader3/tts blob）+ Edge 语音列表 + 语速 + 音量 | ✅（架构不同，能力等价+） |
| http 引擎（HttpTTS 源管理 getSpeakStream） | HttpTTS 源列表按名分派 + SettingsView 听书源增删改 | ✅ |
| speechPitch 语调调节 | 无 pitch 参数 | ❌ |
| cacheTTSAudio 边听边缓存音频 | 无音频缓存（每次实时合成） | ❌ |
| 朗读段落高亮 + 本章播完自动连播 + 后台恢复 | 均已实现（tts-reading 高亮/GAP 7） | ✅ |

### 2.3 书架能力

| Pro 功能 | master 现状 | 状态 |
|---|---|---|
| 分组 tab 过滤/分组管理 | 多分组（groupIds）、胶囊筛选、重命名/删除/折叠、排序 | ✅ |
| viewCate 视图（list/column/wall）+ virtualOptimize | grid/list/wall + 密度 s/m/l | ✅ |
| bookOrder 排序（durChapterTime 等） | recent/added/name/author/source/group 六种 | ✅ |
| imageProxy 图片代理开关 | imageProxyEnabled + SettingsView 开关 | ✅ |
| 批量多选操作 | 多选 + 批量删除（deleteBooks 降级逐本）+ 批量导出 | ✅ |
| 导入本地书（去重确认） | uploadLocalBook + importBookPreview + GAP 126 同名确认 | ✅ |
| 书架刷新（refresh=1 最新章/总数） | 下拉刷新 + refresh=1 + 未读数角标 | ✅ |
| 一键批量更新全部书籍最新章 | 仅列表级刷新，无逐本 checkUpdate 批量队列 | 🟡 |
| WiFi 局域网传书页 | 无（Web 版本身即服务端，FileManage 上传覆盖同场景） | 🟡 |
| MPCode 公众号二维码弹窗 | 无（推广组件，建议不移植） | ➖ |

### 2.4 搜索/书源/RSS/设置

| Pro 功能 | master 现状 | 状态 |
|---|---|---|
| 搜索方式：单源指定 / 多源 | 仅多源（SSE 并发 48 写死） | 🟡 |
| 搜索书源分组过滤 + 并发线程数选择 | 分组过滤 ✅；并发数不可配 | 🟡 |
| 书源 CRUD/导入导出/失效检测/订阅/登录 header cookie | 全部具备（SourceManageView + sourceSubs + sourceLogin） | ✅ |
| 书源编辑器 CodeJar+Prism JSON 语法高亮整源编辑 | 分字段 textarea + 符号快捷插入栏，无高亮 | 🟡 |
| 换源（BookSource 弹窗保留原进度） | searchBookSource(SSE) + BookDetail 加入书架保留 durChapterProgress | ✅ |
| 书籍信息编辑/自定义封面/变量 | 自定义封面（GAP 19）/简介（GAP 145）/重新扫描 | ✅ |
| 替换规则/书签/TXT 目录规则 | ReplaceRuleView / bookmarks API + 面板 / txtTocRules UI | ✅ |
| RSS 订阅源/文章/星标收藏/正文图片预览/MP3 下载 | 订阅+文章+净化 v-html+图片全屏预览；无星标收藏、无 mp3 下载 | 🟡 |
| RSS 文章富媒体播放（hls/dash/flv/webtorrent/pear-player 全局加载） | sanitize 后纯图文渲染，音视频不播放 | ❌ |
| WebDAV 备份还原/文件管理/用户管理/缓存管理 | 全部具备且更强（restoreFromZip、secureKey 流程等） | ✅ |

## 3. 开发积压清单（按优先级）

### P0 —— 核心阅读交互对齐

| # | 事项 | 现状/目标 | 涉及文件 | 建议方案 |
|---|---|---|---|---|
| P0-1 | **EPUB iframe 原版渲染双模式** | Pro 默认 epubMode=iframe（ShadowIframe 组件，保留原书 CSS/内链跳转/锚点目录定位）；master 只有解析模式 | 新增 `web-ui/src/components/EpubIframe.vue`；改 `views/ReaderView.vue`（isEpubBook 分支、epubLocationChange/epubClickHash 事件接入）、`utils/readerConfig.ts`（新增 `epubMode` 键）、后端复用 `file/download stream=1` 直出 .epub | 用 `<iframe sandbox="allow-same-origin">` 加载 file/download 流（浏览器自带 EPUB 不支持，需引入 epubjs（≈400KB）或复用 legacy ShadowIframe 思路自渲染 unzip+XHTML）。设置弹层加「EPUB 解析/原版」二档开关；iframe 模式下翻页走 location/hash 事件桥接现有 chapter/progress 状态机 |
| P0-2 | **全屏点击四方案** | Pro：下一页/自动/不翻页/固定模式；master 仅开/关 | `views/ReaderView.vue` L423-441（tapZones）、L2071-2090（clickZone 翻页分发）、设置弹层 L4602-4615 | 把 `reader_tap_zones`(bool) 升级为 `reader_click_mode`: `auto(默认左上/右下)｜nextOnly(全屏下一页)｜none｜fixed(固定左侧上一页/右侧下一页)`；复用现有 `handleClickAction('nextPage'/'prevPage')` 动作表 |
| P0-3 | **TTS 语调 + 边听边缓存** | Pro speechPitch/cacheTTSAudio；master 无 | `views/ReaderView.vue` TTS 段 L1513-1930、`api/tts.ts`、`api/httpTts.ts` | ① pitch：Edge 合成请求体加 `pitch` 百分比参数（后端 tts 接口透传），HttpTTS 忽略；② 缓存：播放的 blob 同时写 Cache API（键 `tts:{bookUrl}:{chapterUrl}:{voice}:{rate}`），再次播放先查缓存；设置面板加「听书缓存」清理由 `SettingsView` 缓存管理入口 |

### P1 —— 偏好管理与效率

| # | 事项 | 现状/目标 | 涉及文件 | 建议方案 |
|---|---|---|---|---|
| P1-1 | **阅读配置方案多档案 + 日夜自动切换** | Pro customConfigList 命名方案 + autoTheme 定时切日/夜 | `utils/readerConfig.ts`（包一层 profile 序列化 `{name, config[], autoTheme}` 存 `reader_profiles`）、`views/ReaderView.vue` 设置弹层头部加方案条、`utils/uiTheme.ts`（定时器按 hour 切 light/dark） | ReaderConfig 已是纯对象，天然可序列化为数组；新增/删除/切换仅读写 localStorage；autoTheme 复用 toServerConfig 上传保持多端一致 |
| P1-2 | **自动翻页段落滚动档** | Pro autoReadingMethod 像素/段落两档；master 仅平滑滚动 | `views/ReaderView.vue` L1925-1990（autoTimer） | 增加 `reader_auto_mode: pixel｜para`；para 模式按段落元素 boundingRect 逐段 scrollTo({behavior:'smooth'})，间隔 = autoSpeed 映射表 |
| P1-3 | **划线「忽略」档位** | selectionAction=忽略时不弹操作条 | `views/ReaderView.vue` 划线相关 L2900-2990 | 加 `reader_selection_action: popup｜ignore`；ignore 时 mouseup 不显示浮动操作条（书签/净化规则入口仍留快捷键） |
| P1-4 | **搜索单源指定 + 并发线程可选** | Pro searchType single/multi + concurrentCount 下拉 | `views/SearchView.vue` L22-48（分组过滤处）、`api/search.ts` | 顶部加「全部/单选书源」select（数据源复用 loadSearchGroups 的书源列表）；并发数 `reader_search_concurrent` 8-64 五档，SSE 模式传给后端 count 参数 |
| P1-5 | **RSS 文章音视频播放** | Pro 全局加载 hls/dash/flv/webtorrent/pear-player；master sanitize 后丢媒体 | `views/RssView.vue` L200-230、L621（v-html 容器）、新增 `utils/rssMedia.ts` | sanitize 白名单放行 `<video>/<audio>/<source>` 与常见流媒体 URL；mounted 后扫 `.rss-content video[data-src*=.m3u8]` 动态 import(`hls.js`) 挂接；flv/dash 按需同理（动态 import 避免 bundle 膨胀） |

### P2 —— 补全与打磨

| # | 事项 | 涉及文件 | 建议方案 |
|---|---|---|---|
| P2-1 | 页面模式：自适应/手机模式（手机模式=窄栏单指可达） | `views/ReaderView.vue`、`utils/readerConfig.ts` | 新增 `reader_page_layout: adaptive｜mobile`；mobile 强制 contentWidth=720px + 字号下限 + 隐藏 hover 依赖控件 |
| P2-2 | Kindle 简洁模式补全（全局关动画/去装饰） | `views/ReaderView.vue`（chromeHidden 已有）、`styles/main.css` | `reader_simple_mode` 开关：html 根 class 关 transition/animation、隐藏纹理与封面动效；书架侧同步精简卡片 |
| P2-3 | 阅读 边距 top/bottom/horizontal | `views/ReaderView.vue` contentStyle L495-560、设置弹层 | `reader_pad_top/bottom/h` 三键（0-48px 步进 4），映射 padding 变量 |
| P2-4 | 书源编辑器语法高亮 | `views/SourceManageView.vue` L795-860 | 引入 `codejar`+`prismjs`（Pro 同款，≈30KB gzip）替换规则字段 textarea；保留符号插入栏 |
| P2-5 | 书架批量刷新最新章队列 | `views/BookshelfView.vue` L898+（多选模式）、`api/bookshelf.ts` | 多选操作条加「刷新」：并发 4 逐本 getBookshelf(refresh=1)，进度 toast；完成后统一排序刷新 |
| P2-6 | PDF「读原书」入口 | `views/ReaderView.vue` 非文本书分支 L108-123 | isPdf（bookUrl 以 .pdf 结尾）→ 顶部按钮 window.open(file/download stream=1)，对齐 Pro readOriginal 行为 |
| P2-7 | RSS 星标收藏 + MP3 下载 | `views/RssView.vue`、后端 storage 约定 | 星标存 localStorage(origin+link) 过滤视图；mp3 抓取走 file/upload 落 assets 再下载 |
| P2-8 | WiFi 局域网传书 | 评估后可不做：Web 版即服务端，FileManage 上传已覆盖；如需移动端友好可做一个大文件拖拽上传专页 | `views/FileManageView.vue` 复用 |

## 4. master 独有能力（超出 Pro，保持不动）

命令面板 CommandPalette（Ctrl+K）、i18n 中英双语、OPDS API 封装、服务监控 ServerStatsView、书仓 StoreView、用户管理 UserManageView（多用户/管理模式）、导出 epub/txt/html + GBK、导入预览 importBookPreview、书源远程订阅 sourceSubs UI、txtTocRule 管理 UI、每日阅读统计图表、屏幕常亮 wakeLock、亮度滑杆、正文字距/对齐/字重微调、SW 强制更新。

---
*校验方式：JAR chunk 特征串计数（epub×94/pdf×10/cbz×9/pageMode×11）与 legacy 源码逐一对照；master 侧均以 grep 实证（文中行号为 2026-08-24 master@46e818a3 快照）。*
