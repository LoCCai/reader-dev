# Pro TTS 库 + DB 抽象层逐行审计报告

## 一、TTSService.java Edge TTS WebSocket 协议

### P0
- Sec-MS-GEC 签名算法疑似不正确：master 输出 FILETIME 小端 hex + "次日日期整数再编码"；公开 edge-tts 参考算法为 SHA256(ticks 十进制+TrustedClientToken) 大写 hex、5 分钟分桶；Version 应为 `1-130.0.xxxx.xx` 版本串。需实测验证（若 Bing 严格校验会 403）。

### P1
- WSS 请求缺 User-Agent 头（legacy Chrome111/Edg111）
- VoiceEnum 缺失 14 个音色：zh-HK×3、zh-TW×3、liaoning-Xiaobei、shaanxi-Xiaoni、zh-CN Xiaoqiu/Xiaorui/Xiaozhen/Yunhao/Yunye/Yunze
- gender 编码不一致（Pro 中文"女/男" vs master "Female/Male"）

### P2
- SSML text 未 XML 转义（Pro 缺陷）；master sanitize 是改进但偏离
- RequestId 带横线 vs Pro 无横线（协议无影响）
- Edge 模式未抑制 style（Pro sendText 非 Azure 强制 setStyle(null)）
- 输出格式固定 24khz/48kbit 不支持动态切换
- Azure WSS 分支缺失（style 功能依赖）

## 二、DB 抽象层

### 结论：SQLTable 是空壳死代码，全工程唯一外部数据库是可选 MongoDB 热镜像

### 核心数据形态覆盖：✅ 全部已核销
users/bookshelf/bookSource/rssSources/bookmark/replaceRule/txtTocRule/httpTTS/bookGroup/userConfig 等全部有对应表

### 差异
- license/activeLicense/privateKey 三文件（LicenseController 授权体系）master 无对应——需产品决策
- Mongo 热镜像语义（file-level read-through 恢复）vs master 仅整库显式 restore——结构性差异 P2
- Pro saveMulti 陈旧 existIndex 错位替换 bug / SQLTable.delete 升序删除错行 bug —— master 架构天然规避 ✓
