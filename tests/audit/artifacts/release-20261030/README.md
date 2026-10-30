# 2026-10-30 reader-dev 发布验收证据

应用源码 `ed79d94eb745074fb1ada3d290109bee7600c531`；运行镜像 `sha256:8b5008039383492e5b176e336fdccc15351ac98882b3909be28afb93370325f0`。完整说明见[发布记录](../../../../docs/audit/reader-release-20261030.md)。

| 文件 | 内容 |
|---|---|
| before.json | 切换前只读聚合和原镜像/源码标识；不含现有正文或凭据 |
| image-build.log | 实际 Compose 多阶段镜像构建原始日志，退出码 0 |
| schema-only.jsonl | 发布后预检：只读 SQLite schema，0 HTTP、0 写入 |
| live-smoke.jsonl | 两个随机临时用户的 17 次合成 HTTP 验收、7 组断言及 finally 零残留结果；不含令牌 |
| after.json | 测试清理后的只读聚合、chapter_order 列、线上首页/JS/CSS/轻量页指纹与二进制 SHA-256 |
| host-port-headers.txt / host-port-index.html | 本工作机直连 193:10002 的响应头与公开首页，用于核对实际发布端口 |
| runtime-startup.log | 新实例身份及限幅启动日志；匿名鉴权 WARN 来自合成测试 |
| runtime-final.txt | 切换开始时间、连续健康检查、运行镜像/限额/重启策略，以及仅 .env 权限和字节数 |
| SHA256SUMS | 本目录证据文件的 SHA-256，不包含自身 |

复验脚本：[`release_smoke_fixture.py`](../../release_smoke_fixture.py)。默认只读；显式 `--execute` 才创建、检查并精确清理随机合成数据。脚本只能在目标 reader-dev 容器内运行；任何线上执行仍应在相应授权范围内进行。

本轮没有测试生产全量外站、真实账号登录、全局清缓存或生产高负载，也没有在现有书籍上试删正文。
