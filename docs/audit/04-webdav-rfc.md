# WebDAV 服务端 RFC 4918 合规性审计报告

> 审计代理产出 | 2026-08-23 | 对比基准 origin/legacy vs master HEAD
> 来源：WebDAV 审计代理（PROPFIND/MKCOL/PUT/COPY/MOVE/OPTIONS 全方法）

## 发现清单

### P0（互操作性致命）

| # | 问题 | 现状证据 | 修复建议 |
|---|------|---------|---------|
| W1 | **PROPFIND XML 特殊字符未转义**：文件名中的 `& < > "` 未做 XML escape → 一个含 `&` 的文件名打瘫整个目录列表响应 | PROPFIND 响应拼装处 | 对 displayname/href 等 XML 文本统一 xml::escape |
| W2 | **子项 displayname 为空串**：webdav.rs:186 传 `name=""` → 客户端列表全部显示空白 | webdav.rs:186 | 传入真实文件名 |
| W3 | **getlastmodified 格式错误**：使用 ISO 8601 而非 RFC 1123（`HTTP-date`），严格客户端解析失败 | Last-Modified/getlastmodified 输出 | 改为 RFC 1123：`Wed, 21 Oct 2015 07:28:00 GMT` |

### P1

- **href 编码不全**：仅编空格，`#/%/?` 未转义 → 特殊文件名被截断/语义改变。应百分号编码 path 各段（保留 `/`）
- **Overwrite: F 头被忽略**：COPY/MOVE 目标存在时静默覆盖丢数据。RFC 要求目标存在且 Overwrite:F → 412
- **Depth 头不支持**：PROPFIND Depth 0/1/infinity 未区分
- **MKCOL 静默创建多级路径**：父目录不存在时应返回 409（RFC 4918 §9.3）
- **Dav:2 声明但锁是假的**：OPTIONS 响应声明 Class 2 却无 LOCK/UNLOCK 实现 → 客户端误判可锁。要么实现最小 LOCK/lockdiscovery，要么去掉 Dav:2 声明

### P2

- 缺 ETag / Range / If-Match 支持（断点续传与冲突检测不可用）
- 401 WWW-Authenticate 缺 `charset=UTF-8`
- OPTIONS Allow 列表缺 HEAD；缺 MS-Author-Via: DAV 声明（影响 Office 类客户端）
- 错误状态码区分不够：全库无 412/423；PUT 覆盖已存在资源应 204 非 201

## 已确认对齐项

- 基础认证流程可用，主流客户端（RaiDrive/ES 文件浏览器类）基本列目录/上传下载路径可通（在文件名不含特殊字符前提下）
- 目录层级结构与 zip 备份布局与 legacy 一致（books/ 根归并属 F11 待办，不在本报告范围）
