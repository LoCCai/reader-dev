# BookHelp 图片缓存/章节管理 + CookieStore 持久化 + ACache 磁盘缓存 逐行审计报告

## 一、BookHelp（图片缓存/章节文件管理）

### P1
- B1: 文本书内嵌 `<img>` 被剥除——legacy 预下载全部配图并改写链接为服务端缓存地址+data-error 回退；master 文本通道剥除所有标签插图消失。建议：保留 img 标签改写为 /assets/proxy?url=… 或绝对化透传
- B2: getBookContent 缺 `refresh=1` 缓存绕过——legacy refresh>0 直接跳过章节缓存重新抓取；master 前端"刷新本章"永远拿旧缓存须先调 deleteBookCache

### P2
- B3: 图片回源不带书源 header 配置（Referer 防盗链场景可能被拒）
- B4: 图片缓存 512MB 全局 LRU vs legacy 永久 per-book（离线保证弱化）
- B5: deleteBookCache 未拒本地书 / clearCache 跨命名空间
- B6: 并发去重 CopyOnWriteArraySet 竞态窗口 vs master 锁内查盘（master 改进✓）
- B7: md5Encode16 64-bit 键 vs master 完整 32-hex 含 ns 前缀（master 改进✓）
- B8: getImageSuffix URL 猜测扩展名 vs master Content-Type 白名单（master 更可靠✓）

### 已确认对齐
- getContent 缓存命中判定+进度保存语义 ✓
- 缓存键架构替换（文件→SQLite）改进 ✓

## 二、CookieStore（cookie 持久化层）

### P2
- C1: `cookie.setCookie(url,"")` 无法清域名（legacy 空串覆盖=清除语义）——JS 书源依赖此惯用法清登录态会失效
- C2: cookie 值含 '=' 解析差异：legacy 截断到第二个 '='（base64 padding 值丢失），master 保全（更正确但偏离 legacy）
- C3: cookie_subdomain 对非 http 前缀输入比 legacy 宽松
- C4: 缺全命名空间一键清空等价物 `cookie.clear()`

### 已确认对齐
- setCookie 整串覆盖 ✓ / removeCookie 删整域 ✓ / getKey 单键读取 ✓
- replaceCookie 逐键合并（显式优先）✓
- HTTP Set-Cookie 会话自动回存 ✓
- 域名归一化 getSubDomain 含端口/IP quirk 复刻 ✓

## 三、ACache（磁盘缓存）

### P2
- A1: CACHE_STORE 无容量/条数上限（legacy 50MB/百万条 LRU 硬顶）——恶意书源可无限撑大内存+SQLite
- A2: 缺 `cache.getByteArray` JS 面
- A3: 缺 `cookie.clear()` 全命名空间一键清空

### 已确认对齐
- TTL=0 永不过期与 Pro 权威基准对齐 ✓（legacy git 源有 bug，master 天然规避）
- TTL 头结构误判数据损坏风险通过元数据独立列天然规避 ✓
- hashCode 文件名碰撞零误读（完整字符串键+主键约束）✓
- LRU 触发时机仅 put（get 只 touch recency）语义一致 ✓
