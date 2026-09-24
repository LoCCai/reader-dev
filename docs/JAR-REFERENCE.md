# reader-pro-3.2.14.jar 权威参照

## 文件信息
- 路径：`C:\Users\chong\Downloads\reader-pro-3.2.14.jar`
- 大小：69.5MB
- 类型：Spring Boot fat JAR（内嵌 Tomcat + 全部依赖 + 前端资源）

## 对齐优先级
**此 JAR > git origin/legacy 分支 > 任何其他参照**

后端功能、参数、文案、行为细节以此 JAR 内编译类为准。

## Pro 版独有功能（legacy 分支没有）

### 控制器级
- LicenseController（授权管理系统：checkLicense/importLicense/backupFileNames）
- setEpubContent / getEpubContent（EPUB 内容模式切换）
- exportToEpub / exportToTxt（独立导出端点）
- getAllContents（全书内容获取）
- searchChapter（章节内搜索）
- saveShelfBookLatestChapter（最新章节保存）
- syncBookProgressFromWebdav / syncFromWebdav（WebDAV 进度/数据同步）
- searchBookWithSource（带源搜索——getAvailableBookSource 的刷新路径）
- saveLocalBookCover / setCover（本地封面设置）
- textToSpeechCn 引擎（中文 TTS）
- getSpeakStream（TTS 音频流管线）
- mergeBookCacheInfo / saveBookInfoCache（进程内书籍信息缓存）
- FileController.restore（从 zip 备份恢复）
- HttpTTSController 完整 CRUD（list/save/saveMulti/delete/deleteMulti）
- updateRemoteSourceSub（远程书源订阅定时同步）

### 服务层
- com.htmake.reader.lib.tts（Edge TTS 完整库：SSML/TTSService/VoiceEnum/TtsStyleEnum）
- me.ag2s.epublib（EPUB 解析库——比 master 的简易解析器更完整）
- me.ag2s/umdlib（UMD 格式解析）
- MongoManager（MongoDB 备份完整实现）
- RemoteWebview（远程 WebView 渲染服务）
- ACache 磁盘缓存（storage/cache/runtimeCache/{ns}，50MB 上限）
- SpringEvent（应用生命周期事件）

### 配置
- reader.app.secure / secureKey / userLimit / bookLimit / sourceLimit
- reader.app.defaultUser* 系列（新用户默认权限）
- reader.app.allowDebug / debugLog
- MongoDB 连接配置

## JAR 内容结构
```
BOOT-INF/classes/com/htmake/reader/
├── api/
│   ├── ReturnData.kt → 返回信封 {isSuccess, errorMsg, data}
│   ├── YueduApi.kt → 路由注册（110+ 条）
│   └── controller/
│       ├── BaseController.kt → 基类（checkAuth/checkManagerAuth/formatUser/limitConcurrent）
│       ├── CURD.kt → 通用 CRUD 接口
│       ├── BookController.kt (~3600行) → 书籍全部操作
│       ├── BookSourceController.kt → 书源管理
│       ├── UserController.kt → 用户管理
│       ├── BookGroupController.kt → 分组管理
│       ├── BookmarkController.kt → 书签
│       ├── ReplaceRuleController.kt → 替换规则
│       ├── RssSourceController.kt → RSS 源
│       ├── HttpTTSController.kt → HttpTTS 源
│       ├── FileController.kt → 文件管理
│       ├── WebdavController.kt → WebDAV 备份
│       └── LicenseController.kt → [Pro] 授权管理
├── config/AppConfig.kt → 应用配置
├── entity/User.kt, Book.kt, BookChapter.kt 等
├── lib/tts/ → Edge TTS 库
└── utils/ → 工具类
BOOT-INF/classes/io/legado/app/
├── model/analyzeRule/ → 规则引擎（AnalyzeRule/AnalyzeUrl/AnalyzeByJSoup等）
├── model/webBook/ → 网书四件套（WebBook/BookList/BookInfo/BookContent/BookChapterList）
├── model/localBook/ → 本地书解析（TextFile/EpubFile/CbzFile/PdfFile/UmdFile）
├── help/JsExtensions.kt → JS 扩展函数全集
├── help/http/ → HTTP 客户端（OkHttpUtils/CookieStore）
├── help/CacheManager.kt → 缓存管理
└── utils/ → 工具类
```

## 反编译工具推荐
```bash
# CFR（推荐，输出最可读的 Java/Kotlin 混合反编译）
java -jar cfr.jar reader-pro-3.2.14.jar --outputdir decompiled

# 只反编译特定包
java -jar cfr.jar reader-pro-3.2.14.jar --analyseclass BOOT-INF/classes/com/htmake/reader/api/controller/BookController.class
```

## 注意事项
1. JAR 中是编译后的 .class 文件，需要反编译工具才能阅读逻辑
2. Kotlin 编译产物可能与源码有差异（lambda/协程/默认参数等被编译为合成方法）
3. legacy git 分支的 .kt 源码在多数场景下可作为等效参照（两者同源）
4. Pro 版新增功能（License/searchBookWithSource/textToSpeechCn 等）只能从 JAR 反编译获取精确行为
