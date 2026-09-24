# 用户密码加密与会话管理深审报告

## 发现
- 🔴 addUser 创建即签发有效 token（legacy 首登才发）——凭据外泄面扩大
- 🟠 logout 无 token 时 master 清主 token vs legacy 不清任何存储态；响应应 NEED_LOGIN+「请重新登录」
- 🟠 bookLimit ≤0 语义反转（legacy 0=全禁 vs master 0=不限）
- 🟡 clearInactiveUsers assets/{ns} 孤儿目录（双方共同缺口）；master DB 层清理更彻底
- 🟡 updateUser 多 isAdmin 字段+返回 null vs legacy 返回用户列表
- ✅ genEncryptedPassword 双 MD5 逐字节兼容（md5(md5(pwd+salt)+salt)，小写 hex，UTF-8）
- ✅ argon2id 迁移正确（MD5 通过后同明文重哈希，仅 UPDATE password 列）
- ✅ 盐生成增强（CSPRNG + 62 字符全集 vs legacy 61 字符非 CSPRNG）
- ✅ 密码长度校验统一用 config 是合理修正

## 优先修复
addUser 不预发 token > logout 响应契约 > bookLimit 语义决策
