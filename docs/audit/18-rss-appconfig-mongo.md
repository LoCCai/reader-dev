# RSS 解析 / RemoteWebview / AppConfig / MongoManager 逐行审计报告

## 一、RSS 自定义规则解析（RssParserByRule）

### P1 高优先缺口
- **nextUrl 分页完全缺失**：legacy `ruleNextPage` 提取 + `"PAGE"` 特判 + 绝对化 + 返回 `[articles, nextUrl]` 二元组给前端翻页；master 定义了 `rule_next_page()` 但全库无调用，仅支持 sortUrl 含 `{{page}}`

### P2
- pubDate 字符串透传不解析 vs master 转 ms 时间戳+按时间倒序排序（语义分歧：master 多了一次 Pro 没有的排序）
- description 空规则→null 信号（驱动内容页按需解析）master 无此区分
- link 绝对化基准 sourceUrl vs resp.url 差异

### 已确认对齐
ruleArticles 空→降级默认 ✓ / `-` 倒序 ✓ / title 空丢条目 ✓ / body 空错误文案一致 ✓ / variable 透传缺失为低影响

---

## 二、RSS 默认解析（RssParserDefault）

### 结论
Pro 仅支持 RSS 2.0 `<item>`；master feed-rs 为超集（RSS 0.9x/2.0/RDF/Atom/JSON Feed）✅

### P2 小项
- `<time>` 自定义标签（中文站常用）master 不识别
- description/content 内嵌 img 兜底封面 Pro 有 master 无
- 条目顺序：Pro 原序 vs master 时间倒序排序（语义分歧）
- author 提取 master 超集 ✓

---

## 三、RemoteWebview 远程渲染服务

### 定位澄清
不是仅前端功能——**书源可调用**。任何源 URL 参数声明 `webView:true` 或携带 webJs/jsLib，AnalyzeUrl 渲染就透明路由到此外部渲染服务。

### 协议
纯 HTTP POST JSON 到 `{remoteWebviewApi}/render.html`：
字段 url/html/headers/js_source/proxy/http_method/body/encode/tag/sourceRegex
响应 StrResponse(url,body) + Set-Cookie 回写用户 cookie jar（按用户隔离）

### master 状态
无任何等价物。webView/jsLib 重源静默降级失败。
建议实现为可选外联模块（未配置时明确报错）。

---

## 四、AppConfig 配置完整性

### 缺失键清单（11 项有效配置）
| 键 | 类型 | 默认 | 影响 |
|---|---|---|---|
| cacheChapterContent | bool | false | 章节缓存开关 |
| debugLog | bool | false | 逐请求规则调试日志 |
| autoClearInactiveUser | int(days) | 0 | 自动清理不活跃用户 |
| exportUseReplace | bool | false | 导出应用替换规则 |
| exportCharset | str | UTF-8 | 导出编码 |
| exportNoChapterName | bool | false | 导出不含章节名 |
| exportPictureFile | bool | false | 导出图片文件 |
| mongoUri/mongoDbName | str | ""/"reader" | MongoDB 连接 |
| shelfUpdateInteval | int(min) | 10 | 书架自动更新间隔 |
| remoteWebviewApi | str | "" | 远程 WebView 渲染服务地址 |
| autoBackupUserData | bool | false | 定时自动备份开关 |
| remoteBookSourceUpdateInterval | int(min) | 720 | 远程书源定时更新(12h) |

### 默认值分歧需决策
| 键 | Pro 默认 | master 默认 | 差异 |
|---|---|---|---|
| userLimit | 15 | 500000 | 多用户配额语义完全不同 |
| userBookLimit | 200 | 500000 | 同上 |
| defaultUserEnableWebdav | false | true | 安全边界 |
| defaultUserEnableLocalStore | false | true | 同上 |

---

## 五、MongoManager 热镜像

### 定位差异
Pro = 存储层热镜像（每次 saveStorage/getStorage 自动同步 KV 文件）；master = 显式 API 冷备/恢复

### 值得吸收的
① READER_APP_MONGOURI/MONGODBNAME 常驻配置 + 启动 ping（可选开启）
② storage JSON 文件级备份可复用 path-keyed 集合思路

### 不应复刻的缺陷
删除复活（本地删除后下次读取被 mongo 读穿透"复活"）、connect 静默失败、registry 重复构建

---

## 按优先级汇总

| # | 项目 | 级别 |
|---|---|---|
| 1 | RSS ruleNextPage 分页 + 二元组响应形状 | 高 |
| 2 | AppConfig 11 个缺失键 + 默认值决策 | 高(P1) |
| 3 | RemoteWebview 外联渲染模块 | 中 |
| 4 | 默认解析 `<time>` 标签 + 内嵌 img 封面兜底 | 低 |
| 5 | Mongo 常驻配置/ping；勿复制删除复活缺陷 | 低 |
