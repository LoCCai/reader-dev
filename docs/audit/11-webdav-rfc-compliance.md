# WebDAV 服务端 RFC 4918 合规性审计报告

## P0
- PROPFIND XML 特殊字符未转义（& < > 引号）——一个 & 文件名打瘫整个列表
- 子项 displayname 为空串（webdav.rs:186 传 name=""）
- getlastmodified 用 ISO 8601 非 RFC1123 格式

## P1
- href 仅编空格（#/%/? 截断）；Overwrite:F 头被忽略（静默覆盖丢数据）；Depth 头不支持
- MKCOL 静默创建多级路径（RFC 要求 409）；Dav:2 声明但锁是假的

## P2
- 缺 ETag/Range/If-Match；401 缺 charset=UTF-8；OPTIONS Allow 缺 HEAD/缺 MS-Author-Via
- 错误状态码区分不够（412/423 全库无）；PUT 覆盖应 204 非 201

## 优先修复序
XML转义+displayname > getlastmodified格式 > href编码 > Overwrite头 > Depth支持 > MKCOL单层 > LOCK降级Dav:1 > ETag/Range
