# Reader 前端三轮调查、修复与验收（2026-10-30）

## 基线与边界

- 权威基线：193 服务器 `/home/ccai/docker/reader-dev` 的 clean `master@7efd557`；修改只在本机隔离树 `reader-dev-audit-20261030`。
- 先读取父目录及项目 `AGENTS.md`。旧本机副本仅用于寻找入口，缺陷均重新在上述权威基线核实。
- 本报告覆盖 `web-ui/src` 业务前端。后端、`web-simple`、GitHub 差异与最终集成分别由主代理和后端代理记录。
- 没有修改运行目录、生产浏览器存储、服务器数据库或真实书架；没有重启、发布或推送。
- 依赖使用锁文件 `npm ci --no-audit --no-fund`，安装 112 个包；未升级依赖。Node 为 `/root/.nvm/versions/node/v22.23.4/bin/node`。

## 第一轮：缓存入口与任务生命周期

### 调查和失败证据

1. 详情页初始只加载书籍信息，目录只在用户先点“目录”时获取；“缓存”直接打开空目录弹层，章节数为 0，开始按钮禁用。
2. 服务端启动返回 `taskId`，订阅也使用 `taskId`，取消却发送书籍 URL。启动 POST、SSE 握手尚未返回时取消，没有后续精确取消，也没有关闭迟到句柄。
3. 原任务回调没有操作代数，关窗、换书、开启新任务后，迟到进度可以覆盖当前弹层或发出 `done`。
4. 本机写入函数返回 `false` 表示 IndexedDB 写入失败，批量缓存仍无条件把它计为成功。
5. 缓存进度连接正常 EOF 被直接当作缓存完成，没有要求服务端 `finished` 帧。

独立 QA 直接编译 `git show 7efd557:<真实Vue文件>` 的 script，控制请求、进度和存储边界：6 个缺陷断言失败，章节范围转换正例通过。测试源码见 [frontend_cache_dialog.mjs](../../tests/audit/frontend_cache_dialog.mjs)。

### 修复

- [BookDetailView.vue](../../web-ui/src/views/BookDetailView.vue)：抽取目录加载，直接点“缓存”先取目录，提供加载态与失败提示；切书后丢弃旧目录响应。
- [ChapterCacheDialog.vue](../../web-ui/src/components/ChapterCacheDialog.vue)：每次操作冻结书籍 URL、书源、范围，使用独立 `CacheRun`；取消按返回的 `taskId`，旧服务端缺少该字段才回退 URL。POST 迟到后补发精确取消；SSE 握手迟到关闭句柄；旧连接、旧任务、卸载回调失效。
- 只有明确完成帧才报服务端完成。无完成帧的 EOF 保留后台任务状态和重连入口。
- 本机成功数只统计实际写入成功；失败保留数量与错误提示，全部失败不发成功事件；取消后到来的正文不再开始本机写入。

### 复测与再调查

第一轮独立组件 7/7 通过，完整 `vue-tsc + vite build` 通过。再调查扩展到同书新旧任务、重连、换书、卸载和部分成功，不以重复首轮检查充当第二轮。

## 第二轮：搜索覆盖范围、降级和续搜

### 调查和失败证据

- SSE 固定 `lastIndex=-1/searchSize=50`，前端忽略结束帧 `isEnd=false`，显示已经搜索全部，且没有续搜入口。配置超过 50 个可用书源时，后续源永远不参与。
- SSE 单源搜索降级到普通请求时遗漏 `bookSourceUrl`，范围扩大为多源；普通请求又把 `maxSources` 固定为 50，后端会截断书源列表。
- “加载更多”读取用户此时编辑后的关键词、分组和精确开关，可以把另一范围的结果追加到旧结果。
- 普通搜索一页只有重复书就宣告结束，实际后续页可能仍有新书。
- SSE 正常 EOF 但没有结束事件时，普通搜索不会解除搜索中状态。

### 修复

- [SearchView.vue](../../web-ui/src/views/SearchView.vue)：冻结本次搜索的关键词、源、分组、精确开关、并发数。按服务端结束帧保存游标；未到最后一源时提供“继续搜索其他书源”，无命中批次也可继续。
- [api/search.ts](../../web-ui/src/api/search.ts)：普通请求新增单源参数，省略 `maxSources` 以覆盖已选范围，不再只搜前 50 源。普通结果分页只在空响应时标记结束。
- [api/sse.ts](../../web-ui/src/api/sse.ts)：没有结束/业务错误事件的 EOF 提示中断，不再永久加载；取消保持静默。
- 请求和连接均做过期判断，停止、换搜索、卸载后的迟到结果不能回写。

### 复测与再调查

独立组件用例扩展至 16 项，全部通过，包括：旧缓存回调与新任务隔离、换书、卸载、本机下载中取消、部分写失败、重连旧回调、单源降级、空结果 SSE 续搜、搜索握手中停止。原有 API SSE 单测 8/8 通过；原有 utils 单测 88/88 通过，构建通过。

后端源集合可能受健康状态变化影响，因此不盲目按客户端事件个数推算下一批，续搜使用服务端 `lastIndex`。真实大规模书源不会在此次验收中批量爬取。

## 第三轮：阅读格式、按钮能力、原书文件与用户隔离

### 再调查和失败证据

1. EPUB `text→html` 仅切换排版变量，没有带 `epubContent=1` 重取当前章。独立 Chromium 初次验收实际等待 `.epub-html` 超时，未见对应 HTML 正文请求，证据保留在 [browser-r3-initial/report.json](../../tests/audit/artifacts/browser-r3-initial/report.json)。
2. HTML 章正文在 `chapterHtml`，复制按钮却按空 `paragraphs` 禁用；本章搜索也只扫描 `paragraphs`，与可见正文不一致。
3. 缓存标记混用“实章序号”和“含卷标题的目录数组下标”，且前端只读服务端第一页 200 条。
4. 详情和书架可对音频、漫画、文件、视频触发文本导出/缓存；后端现有管线只支持文本正文。未入架书的导出同样必定报错。部分失败导出的提示只保留 900ms，随后弹层自动关闭。
5. IndexedDB key 仅由书籍 URL 和章节 URL 组成，没有用户归属。独立 Chromium 执行原版模块，确认切换账号后能够读取另一用户在相同 URL 保存的正文。
6. EPUB 原版加载把 `local://` 或 `storage/...` 书籍 URL 当作 `file/download` 的相对文件路径，且遗漏认证 token；后端确认该旧接口不识别上述书籍 URL。

### 修复

- [ReaderView.vue](../../web-ui/src/views/ReaderView.vue)：切换排版立即重取当前章；HTML 复制根据实际纯文本启用并解码 HTML 实体；HTML/原版模式点击本章搜索时切回纯文本，明确提示后在当前章高亮搜索。
- 服务端缓存标记按实章映射回原始目录下标，消费 `nextFrom/hasMore` 续页；本机标记仍按章节 URL 映射。旧书、旧用户、旧标记请求不能覆盖当前页。
- [readerLocalCache.ts](../../web-ui/src/utils/readerLocalCache.ts)：key 和记录加入用户名/管理员 default 配置模式归属；读取、列举、清理均限当前归属；旧无归属记录不自动认领。缓存、预读、正文与编辑请求在发起前冻结归属，晚响应不会写入新账号。
- [BookDetailView.vue](../../web-ui/src/views/BookDetailView.vue) 与 [BookshelfView.vue](../../web-ui/src/views/BookshelfView.vue)：对不支持的媒体文本导出/缓存禁用并解释；未入架导出要求先入架；部分失败导出保持警告直到用户关闭；导出冻结书籍/格式/文件名，切书后旧响应失效，旧自动关闭计时器不关闭新弹层。
- 阅读文本页新增本书导出入口，跳转详情并打开原有格式确认弹层，复用同一下载行为。
- 本地书“清缓存”改为“清本机缓存”，仅清理当前用户此浏览器的章节副本，保留导入原文件和服务器正文，不再调用注定被后端拒绝的本地书 `deleteBookCache`。
- [epubLoader.ts](../../web-ui/src/utils/epubLoader.ts)：消费后端新增的 `getBookFile?url=`，由服务器校验当前用户书架归属并安全定位原文件；传递当前 token 和显式 default 配置范围；要求 EPUB MIME，业务错误展示真实文案并提供重试。

### 按钮作用范围矩阵

| 入口/场景 | 搜索 | 缓存 | 导出/下载 |
| --- | --- | --- | --- |
| 搜书页全部/分组/单源 | 当前搜索快照；SSE 每批 50 源可继续；降级保留范围 | 不在此页操作 | 不在此页操作 |
| 已入架网络文本详情 | 标为“搜索已缓存正文”，查询当前书服务器缓存 | 先加载本书目录；当前章/至末尾/全本/范围；服务器或当前用户本机 | 本书全部章节 TXT/EPUB/HTML，TXT 可选 UTF-8/GBK |
| 未入架文本详情/临时阅读 | 搜书和本章搜索仍按各自范围 | 本机缓存可用；服务端缓存提示先入架 | 禁用并提示先入架 |
| 本地 TXT/EPUB 详情 | 全部本地正文 | 文本缓存；“清本机缓存”只处理浏览器副本 | 支持文本导出；EPUB 原版下载通过书籍归属 API |
| 文本阅读纯文本模式 | 当前章，与目录标题搜索和详情全文搜索分开 | 当前书；当前章序号使用过滤卷标题后的实章位置 | 新增导出入口复用本书详情确认；复制本章/全文维持各自范围 |
| EPUB 净化/原版模式 | 点击本章搜索先切纯文本后执行，界面明确提示 | 仍是本书文本章节缓存，不声称缓存整个原版文件 | HTML复制提取当前章文字；原版文件需要安全下载API |
| 音频/漫画/文件/视频 | 不展示文本阅读工具；详情正文搜索禁用 | 不支持的文本缓存禁用并解释 | 文本格式导出禁用；原有媒体渲染、媒体链接和原文件入口保留 |
| 390px 窄屏 | 文本搜索弹层及工具可到达 | 范围弹层操作可到达 | 本书导出弹层不横向溢出 |

### 已执行的验收与证据

- 独立组件脚本最终 18/18 通过；命令：`node --test tests/audit/frontend_cache_dialog.mjs`。基线使用 `AUDIT_BASELINE=7efd557` 运行同一脚本，首轮缺陷断言失败的结果由 QA 记录；最终说明见 [tests/audit/README.md](../../tests/audit/README.md)。
- 原有 utils + API SSE 合并运行共 96/96 通过：`node --test src/utils/*.test.ts src/api/sse.test.ts`（`web-ui` 目录）。
- `npm run build` 在 getBookFile 适配及最终缓存清理范围确认文案补充后再次通过 `vue-tsc` 与 Vite，Vite 耗时 15.88s；日志 `/tmp/reader-frontend-final-build-20261030.log`。现有 VueUse 包有两条注释位置提示，不影响构建；依赖未改。最后文案补充未重复执行全量单测。
- [browser_acceptance.mjs](../../tests/audit/browser_acceptance.mjs) 使用真实 Chromium、候选 Vite 页面及拦截的合成接口；所有外部网络请求被阻止。最终 [browser/report.json](../../tests/audit/artifacts/browser/report.json) 记录 15/15 项通过（含基线隐私缺陷证明、带认证 getBookFile/真实ZIP原版EPUB/禁脚本iframe、4种媒体、窄屏及主代理维护的轻量版场景），`pageErrors=[]`。跨用户清理、旧无归属记录不可见和旧归属写入拒绝也通过独立验收；额外范围以最终报告实际执行条目为准。
- 可视证据：[详情](../../tests/audit/artifacts/browser/desktop-detail.png)、[阅读页搜索](../../tests/audit/artifacts/browser/desktop-reader-search.png)、[390px导出](../../tests/audit/artifacts/browser/narrow-export.png)。

## 保留行为、未改原因和剩余限制

- 不自动扫完实际配置中全部书源：大量第三方请求会增加负载，且登录/验证码场景需要真实授权状态。界面保留明确续搜操作，避免把第一批冒充全部。
- `clearCache/deleteBookCache` 清理已存缓存，不停止进行中的预缓存任务；任务可能继续写入。设置清理确认和单书服务器缓存清理确认均明确提示先取消任务，避免把清理成功误认为任务已停止；没有扩大到后台任务调度改动。
- 不实现漫画图片打包、音视频下载或媒体转 TXT/EPUB；现有后端文本管线没有对应能力，因此明确禁用而不伪造成功。
- EPUB HTML/原版搜索采用切纯文本后高亮，不宣称在 iframe 原版排版中原位搜索。
- 不把旧无用户归属的浏览器缓存迁移给当前账号，避免把原用户内容分配给新用户；旧数据物理空间仍在浏览器内，需浏览器存储管理处理，当前应用不会读取它。
- 没有导入或测试实际书源登录内容、Cookie、验证码和全部规则组合；没有真实批量爬取 1965 条启用源记录。类型/登录/脚本能力的实际规模是跨命名空间记录统计，并非独立可用书源数。
- 浏览器验收为 Chromium 合成接口与 390px 视口，不等同于 Safari、真实移动设备、实际源站稳定性或生产集成验收。
- getBookFile 与前端需同批集成；当前只在隔离候选树完成，生产尚未加载。最终独立审阅和整合由主代理负责。
