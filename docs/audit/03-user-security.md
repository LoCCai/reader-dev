# 用户密码加密与会话管理深审报告

> 审计代理产出 | 2026-08-23 | 对比基准 origin/legacy vs master HEAD
> 来源：User 安全审计代理（注册/登录/登出/权限/清理全链路）

## 发现清单

| 严重度 | 问题 | legacy 行为 | master 现状 | 修复建议 |
|--------|------|------------|------------|---------|
| 🔴 P0/P1 | **addUser 创建即签发有效 token**：凭据外泄面扩大（新账号未登录即持有可用会话） | 首次登录才签发 token | 创建时即发 token | addUser 不落 token，首登再签发 |
| 🟠 | **logout 无 token 时行为偏差**：master 回退清主 token vs legacy 不清任何存储态；且响应应为 NEED_LOGIN + 「请重新登录」文案 | 无 token 时不清存储态，响应 NEED_LOGIN+「请重新登录」 | 回退清主 token | 移除回退清除逻辑，逐字对齐响应文案 |
| 🟠 | **bookLimit ≤0 语义反转**：legacy 0=全禁（不允许任何书籍）vs master 0=不限 | ≤0 → 禁止保存书籍 | 0 → 不限制 | 恢复 legacy 语义：bookLimit≤0 一律拒绝新增 |
| 🟡 | **clearInactiveUsers assets/{ns} 孤儿目录**：用户数据目录残留（双方共同缺口） | 同样存在孤儿目录 | 同样未清 | 超越 legacy：删除用户时同步清 assets/{ns} 目录（记为增强） |
| 🟡 | **updateUser 返回形态差异**：master 多 isAdmin 字段且成功返回 null vs legacy 返回用户列表 | 返回受影响用户列表 | 返回 null + 字段超集 | 对齐返回列表形态；isAdmin 超集字段可保留（前端兼容层处理） |

## 已确认对齐项

- genEncryptedPassword 双 MD5 逐字节兼容 ✓
- argon2id 迁移正确（存储格式升级不影响旧校验路径）✓
- 盐生成增强可接受（每用户随机盐，优于 legacy 固定盐）✓
- 密码长度校验统一用 config 是合理修正 ✓
