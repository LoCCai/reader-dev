# JsExtensions 函数体行为差异深审报告

> 审计代理产出 | 2026-08-23 | 对比基准 origin/legacy vs master HEAD
> 来源：JsExtensions 审计代理（网络/缓存/编码/AES/zip/webView 函数逐一对比）
> 注意：部分项可能与 AUDIT-BACKLOG.md E12/E16 已勾选状态重叠（如 connect 失败返回错误文本已修），修复前先核对当前代码现状。

## 发现清单

### P0

| # | 问题 | legacy 证据 | master 现状 | 修复建议 |
|---|------|------------|------------|---------|
| J1 | **unzipFile zip-slip 回归**：legacy 有 `../` 路径防护、master 没有——zip crate `by_index` 不净化 entry 名，恶意 zip 可写穿解压根 | 解压前校验 canonical 路径仍在前缀内 | 无净化直接落盘 | 解压每个 entry 前 canonical 化并校验前缀，越界跳过/报错 |

### P1

- **POST body 无默认 Content-Type**：legacy 按 body 形态三分支判定（form-urlencoded / json / 原样），master 不设默认头 → 服务端解析失败
- **cacheFile saveTime=0 语义反转**：legacy saveTime=0 = 永久缓存 vs master 0 = 每次回源；且裸抓丢 cookie/UA（未带书源 header 上下文）
- **base64Encode 单参默认折行**：legacy 默认 NO_WRAP 不折行 vs master 折行输出（`\n` 会污染后续 URL/签名）；**base64Decode 绑定为 ByteArray 版本**返回 number[] 但 legacy 该签名返回 String
- **connect(url, header) 第二参被忽略**；connect 失败抛异常 vs legacy 返回错误对象/错误文本
- **aes 失败硬抛** vs legacy catch 后返回 null（书源脚本依赖 null 判断走兜底分支）

### P2

- 后缀切分（splitSource 尾段）首尾位差异
- cookie 合并粒度差异（键级合并 vs 整串覆盖的残余场景）
- UA 兜底缺失（未显式设置时应用默认 UA）
- head/post 重定向跟随策略差异
- webView 吞错（JS 异常不上抛，静默空结果）
- AES 裸算法名映射不全 / 零 IV 处理差异
- zip 炸弹上限缺失（解压总大小/条目数无上限）
- getTxtInFolder 不删目录（遗留空文件夹）
- getZipByteArrayContent 二进制 lossy（非 UTF-8 字节经字符串转换损坏）

## 已确认对齐项

- connect 已有 http_response_object 提供 `body()` / `json()` / `url()` / `code()` 方法 ✓
- cookie_subdomain 归一化对齐（两段式注册域键）✓
- readFile / readTxtFile 路径安全为 legacy 超集（更严的穿越防护，有意增强）✓
