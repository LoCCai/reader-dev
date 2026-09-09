# legacy 对齐审计积压清单（2026-08-22 四路子代理逐函数审计产出）

> 来源：四份并行逐行审计（BookController 全量 90 函数 / User+WebDAV / TTS·File·Group·路由表 112 条 / legado 引擎层）。
> 状态标记：[ ] 待修 / [x] 已修 / [~] 有意偏离（记录理由）
> 修复原则：每项配测试；文案逐字对齐；不动 master 自有增强。

## 一、速修批次（薄适配层：路由别名 / 参数键别名 / 字段别名）

- [x] R1 路由别名：`/reader3/httpTTS/list|save|delete|deleteMulti` → 复用现有 getHttpTTSList/saveHttpTTS/deleteHttpTTS/deleteHttpTTSs handler（YueduApi.kt:407-411）
- [x] R2 路由别名：`/reader3/book/tts` GET+POST → 注册现有 tts handler（book/tts 是 legacy 听书主入口）
- [x] R3 方法补齐：`POST /reader3/exportBook`（现仅 GET）
- [x] R4 路由：`/reader3/book/saveBookConfig`（body bookUrl+pdfImageWidth → 存 books.read_config）
- [x] R5 路由：`/reader3/user/downloadBackupFile`
- [x] R6a 路由：`/reader3/file/parse` 已补齐（递归扫描+import 入架）；[x] R6b `/reader3/file/importPreview` 与 `/reader3/file/restore` 已补齐（2026-09-10：files.rs import_preview/restore，restore 复用 restore_backup_zip，文案逐字对齐 legacy）
- [x] K1 deleteBookGroup：兼容 body 键 `groupId`（现仅认 id → 必然参数错误）
- [x] K2 saveBookGroupOrder：兼容 `[{"groupId","order"}]` 形态
- [x] K3 getBookGroups：输出增加 `groupId`/`groupName` 别名字段（legacy 客户端解析依赖）
- [~] K4 searchBookContent：`keyword` 兜底已完成；书源书全文检索仍缺（见 F 批）
- [x] K5 deleteBooks：body 兼容 `[Book...]` 数组形态 + name+author 兜底
- [~] K6 add/removeBookGroupMulti：`bookList:[Book]` 兼容已完成；remove 无 groupId 清空全部为 master 前端依赖行为（有意偏离，见第五节）
- [x] K7 saveBookContent：参数契约对齐 `{url,index,content}`（写 {index}.txt/custom/{index}.txt），兼容现 bookUrl/chapterUrl 形态
- [x] K8 getShelfBookWithCacheInfo：无 url 时返回全书架列表（各书附 cachedChapterCount）

## 二、引擎层批次（真实书源可用性关键）

- [x] E1（含 E2 page 数值注入）【P0】URL 模板通用 `{{js}}` 表达式执行（AnalyzeUrl.kt:129-156；search.rs 仅字面替换）
- [x] E2（随 E1 完成）`page` 以 Number 注入 JS 变量（现为字符串，page+1="11"）
- [x] E3 charset 表单/query 编码（analyzeFields 移植：非 JSON POST body 按 charset 重编码）
- [x] E4 显式 Cookie 头与存储 cookie **逐键合并**（现被整体覆盖，AnalyzeUrl.kt:531-550）
- [x] E5 响应 Set-Cookie 回存 `_cookieJar`（`${domain}_cookieJar` 键 + enabledCookieJar 合并）
- [x] E6 cookie 域键改注册域（getSubDomain 两段式；现 origin 粒度 www/http 分裂）
- [x] E7 翻页 URL 过 {{js}}/<js>/@js: 管线（后缀 method/body 透传待办）
- [x] E8 字段清洗：formatBookName/formatBookAuthor/wordCountFormat/kind 多值逗号拼接（BookList.kt:168-186）
- [x] E9 正文 replaceRegex 走完整规则管线（## 多段链/### replaceFirst/{{js}}；现仅单段 replace_all）
- [~] E10 `src` 绑定：正文/目录/搜索路径已完成（搜索=逐条目 item_html；条目 {{js}} 内嵌同步支持 JS 求值）；book/chapter/title/nextChapterUrl 绑定待补
- [x] E11 新增 `cache` JS 对象 shim（put/get/getInt/…/saveTime 过期；SQLite kv）
- [x] E12 ajaxAll 返回 Response 对象（.body()/.url() 可用）；importScript 返回脚本文本而非 eval 结果；cacheFile 返回内容并带书源 header/cookie；ajax/connect 失败返回错误文本而非抛异常
- [x] E13 css_chain 末段任意属性提取回退（srcset/poster/datetime 等，白名单过窄）
- [x] E14 JsonPath 中部内嵌 `{$.a}x{$.b}` innerRule 扫描
- [~] E15 header proxy 键已完成；UrlOption retry 待办
- [~] E16 base64 flags 变体/base64Decode(ByteArray)/digestBase64Str/logType 已补齐；downloadFile/getFile/aes*ToByteArray 待办

## 三、功能批次

- [ ] [~] F1 本地书导入链：importBookPreview 软兼容字段 ✓、封面下载落盘 ✓；saveBook 三分支迁移仍待办
- [ ] F2 换源链：saveBookSources（每书换源候选持久化）→ searchBookSource(SSE) 补 lastIndex 分页/失效源机制 → getAvailableBookSource 重写为每书 SearchBook 候选列表【已重写：候选持久化表 book_source_candidates + refresh 重搜（origin 集/无候选回退全源精确）】
- [x] F3a cacheBookOnServer 批量 bookUrlList（串行启动；cacheBookSSE 自执行已修） → cacheBookSSE 自执行缓存并推 {cachedCount,successCount,failedCount} → 缓存作业图片下载
- [x] F4 TTS 引擎契约适配器：type=edge/ttsCn/api 分派、voice=源名解析 HttpTTS、{{speakText}}/{{speakSpeed}} 占位符、loginCheckJs/contentType 校验/重试≤5、base64=1 包装、403/404 JSON 化、contentType 透传
- [x] F5 file/parse 目录扫描导入（GET+POST，扩展名白名单 txt/epub/umd/cbz/pdf，import>0 直接入架）
- [ ] F6 getInvalidBookSources 改为运行期失败 600s 快照（sourceUrl/time/error）
- [x] F7 getBookGroups 默认五组播种（-1全部/-2本地/-3音频/-4未分组/-5更新错误，order -10..-6）
- [x] F8 getBookToc refresh 参数生效 + 成功回写 latestChapterTitle/totalChapterNum/lastCheck* + 失败 lastCheckError
- [ ] F9 getBookContent 本地 EPUB(__API_ROOT__)/CBZ(img)/PDF(页图) 三模式
- [~] F10 exportBook：isEpub 参数/《name》作者文件名/Cache-Control:300 已完成；本地原文件直传分支待办
- [x] F11 backupToWebdav zip 并入 books/（2026-09-10：origin=loc_book 原文件按 storage 相对路径入包，restore 回填 + 组件级防穿越；legacy 的 webdav/books 目录在 master 架构下由 storage 根内 loc_book 文件取代）；[x] backupToMongodb 全命名空间遍历（已实现：ns 为空时 list_namespaces 逐个备份——代码核实）
- [x] F12 saveUserConfig @updateTime 戳 + getUserConfig 裸对象直出 + 无配置 err「没有备份文件」

## 四、P2 打磨项（择机）

- [ ] P2 批：SSE concurrentCount 默认 24、searchBookMulti {lastIndex,list} 形状、exploreBook {books,hasMore}、saveBook 返回 Book、mergeBookCacheInfo 进程内书籍信息缓存、webdavList URL 编码全集、MOVE/COPY Overwrite 头、PROPFIND displayname/href、LOCK lockdiscovery、file/download MIME+Range、BookGroup 位掩码 id、/simple-web 路径、/book-assets+/epub 注入、去重键去 trim 等（详见四份审计原文）

## 六、AnalyzeRule 内部方法深审发现（第二轮）

### P1（影响真实书源解析正确性）
- [ ] AR1 isJSON 强转缺失：内容为 JSON 时 legacy 强制所有裸键规则走 JsonPath（ar.kt:469-471）；master detect_kind 无此机制 → 裸键如 `data.list.name` 对 JSON 必空
- [ ] AR2 多命中截断：legacy 单条规则多元素结果 join("\n") 传递全文；master field_impl Css 分支只取 first → intro/kind 多节点只剩第一行
- [ ] AR3 空中间结果链终止：legacy 中间段得 null 后后续所有段跳过返回空；master 以空串继续喂下一段（`class.missing@text@js:x` → legacy ""、master "0"）
- [ ] AR4 body-$N 列表回填缺失 + `@get:{title/bookName}` 内建缺失（legacy get(key) 先查 bookName→book.name 再查变量表；master resolve_get 只读变量表 → `@get:{title}` 恒空）
- [ ] AR5 evalJS 绑定缺口：chapter/title/book/source/nextChapterUrl 在规则引擎任何路径都拿不到；baseUrl 在 rule.rs 路径恒空串

### P2（低优先细节）
- AR-P2 完全越界区间钳位差异（[10:20] len=3→legacy {2} vs master 空）
- AR-P2 html DOM 原地修改副作用 / 多元素条数形态
- AR-P2 outerHtml 别名超集 / attr trim 差异 / @CSS: 缺 tail 抛错 vs 优雅
- AR-P2 JsonPath {$.x} 平衡扫描与失败保留原文回退
- AR-P2 @put 列表规则贯通


## 五、有意偏离（不改，留档）
- ~~非 secure 未配置 secure_key 时写删拒绝~~ 已撤销：恢复 legacy 非 secure 恒放行
- upload 100MB 上限、点开头文件名限制（防炸防隐藏文件）
- deleteFile 防穿越修复了 legacy 可删整个 assets 根的 bug
- clearInactiveUsers 常数时间 secureKey 比较、删除用户数据目录
- format_user 多 isAdmin 字段；RSS 权限用 enable_rss_source（语义更准）







## 七、Pro JAR 反编译深审发现（第三轮：License/File/User + 引擎未覆盖文件）

### 引擎层 P1（影响真实书源）
- [x] EG1 正则多链缺失：`regA&&regB` 逐条过滤 + 全捕获组提取（已实现，rule.rs:1077-1120 实测确认）
- [ ] EG2 XPath 非良构 HTML 解析失败（sxd 严格 XML vs JsoupXpath 强容错）——解析失败时用 CSS 链兜底
- [x] EG3 JsonPath 裸存在性真值（已实现，rule.rs test_jsonpath_filter_bare_existence 确认）
- [x] EG4 cache 重启丢缓存（已实现：js.rs JS_CACHE_STORAGE + SQLite get_js_cache/load_js_cache，serve() 启动注册+恢复——2026-09-10 代码核实，本文档此前漏记）
- [x] EG5 书源代理不作用直连请求（已实现，crawler.rs reqwest::Proxy 确认）

### Pro 独有功能（整模块或端点缺失）
~~PJ1 LicenseController 授权系统~~ 用户决定移除，不实现
- [x] PJ2 uploadFile 同名异义（已实现：router.rs /reader3/uploadFile assets/{ns}/{type}/ 上传返 URL——2026-09-10 代码核实，本文档此前漏记）
- [x] PJ3 file/restore 别名路由（2026-09-10 R6b 补齐 books/ 进度恢复扩展仍待办）
- [x] PJ4 textToSpeechCn 引擎实现（commit 6916b48，本文档此前漏记）
- [ ] PJ5 mergeBookCacheInfo/saveBookInfoCache 进程内书籍信息缓存（已在 P2 批完成 ✓）

### JsonPath 过滤表达式补缺
- [ ] JP1 裸存在性真值修正（null/false/空串键存在即匹配）
- [ ] JP2 缺失路径 != 返回 true
- [ ] JP3 数值与字符串松散相等（@.n == '123' 对数值成立）
- [ ] JP4 高频操作符：=~ 正则、in/nin、size/empty

### 用户安全
- [ ] US1 addUser 不预发有效 token（置空，首登才发——凭据外泄面收窄）
- [ ] US2 logout 无 token 时不清主 token（对齐 legacy）
- [ ] US3 clearInactiveUsers 补删 assets/{username} 孤儿目录
