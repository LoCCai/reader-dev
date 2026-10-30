# reader-dev 全量修复发布记录（2026-10-30）

用户明确授权“全部发布”后执行，范围为本次三轮修复、后续分页优化和错误状态修复，以及同步至 GitHub master、193 既有 reader-dev Compose。未包含上游 `rust` 翻译分支合并、依赖升级或其他服务发布。

## 源码与回退点

- 原部署：193 `/home/ccai/docker/reader-dev`，`7efd557fe9d612552d5e92dceb214e7cb619693c`，工作树干净。
- 业务与验收候选：fork 谱系 `732ff9d`，后续仅添加构建限流/锁文件和上下文排除规则，发布源码为 `ed79d94eb745074fb1ada3d290109bee7600c531`、完整树 `c2e2b60822f4acf8b62a333d14c76eabe8df062a`。
- GitHub master 已正常快进至该源码提交，审阅分支同步；没有强推。193 使用只含 fork 祖先历史的 Git bundle 同步，完整树相同；旧部署历史保存为 `deployment-history/pre-20261030-7efd557`，当前 master 已跟踪 GitHub origin/master。
- 原镜像 `sha256:569386069bd69ae8a24a0bb4dc398d1edc6d914a19664eb609e2f3a90286b343` 保留为 `reader-dev:rollback-20261030-7efd557`。沿用既有 Compose，仅更新 reader-dev；配置、端口 10002、数据卷和 2 GiB 限额保持原定义。

## 构建与数据库范围

Rust 构建改为 `cargo build --release --locked -j2`，锁定依赖并限制同时编译数；`.dockerignore` 排除 `.env`、`.env.*`、data/work/.mimosa 及数据库文件，防止运行数据和凭据进入构建上下文。基础镜像沿用现有版本，未执行 pull/依赖更新或清理其他项目缓存。

新迁移增加可空 `chapter_order INTEGER`，不重建章节表；新增列的 DDL 单独提交，目录回填另在一个事务内更新现有章节的顺序及非空目录标题，不更新正文、不插入/删除章节。重复/已移除目录 URL 可保持 NULL。旧镜像兼容新增列，回退无需删列或恢复全库；已更新的标题元数据不会随源码回退撤销。

原有 startup backup、缓存 prune、定时任务及本地书对账入口不变；没有额外手工启动全库备份。已确认生产 `storage/data/users.json` 不存在，因此旧 JSON 用户补迁不触发。启动时既有应用行为仍以实际日志和前后聚合核对，不能将所有启动写入都归因于新增回填。

上线前只读聚合：数据库文件 53,485,568 字节；users=2、books=2、book_sources=2619、book_chapters=9、toc_cache=9；9 条正文总计 19,681 字符、NULL 正文=0、孤儿章节=0；尚无 chapter_order 列。未输出现有用户凭据、书名/URL或正文。原始记录见 `tests/audit/artifacts/release-20261030/before.json`。

## 验收范围

发布前已完成后端 830/830、HTTP 集成 31/31、前端 utils/API/directives 103/103、SFC 交互 20/20、类型检查及生产前端构建。性能对照、原三轮浏览器证据及覆盖限制见相应审计报告，不重复计为生产验收。

发布后使用 `tests/audit/release_smoke_fixture.py`：默认 schema-only 只读；显式执行时只建随机临时用户和合成书/正文/目录，真实回环 HTTP 验证分页缺章/范围/游标、跨用户隔离、清自建目录后的排序、TXT/EPUB内容结构及本地原文拒删。拒绝外站地址与重定向，不启动采集、不调用 namespace-wide clearCache，不读取现有凭据。finally 仅按本轮随机 ID、用户 marker 和精确书籍键清理并核对零残留；导出仅在内存处理。额外核对静态资源与运行镜像、健康状态、启动日志、数据库聚合。

## 实际发布与线上验收结果

**已发布成功。** 2026-10-30 15:42:34 UTC 开始切换，沿用原 Compose 执行 `docker compose up -d --no-deps --wait --wait-timeout 120 reader-dev`，仅重建目标容器；新实例 StartedAt 为 `2026-10-30T15:42:35.326488794Z`，Compose 返回 Healthy。没有连续采样中断时长，因此不提供秒级停机承诺。

- 新镜像：`sha256:8b5008039383492e5b176e336fdccc15351ac98882b3909be28afb93370325f0`，保留标签 `reader-dev:release-20261030-ed79d94`；构建成功，基础镜像使用已有缓存，构建上下文 2.67 MB。完整日志见 `tests/audit/artifacts/release-20261030/image-build.log`。
- 运行状态：running / healthy、RestartCount=0、OOMKilled=false、健康检查 FailingStreak=0；连续五次保留的健康检查均成功。内存限额 2,147,483,648 字节、重启策略 unless-stopped；`.env` 权限仍为 0600，未读取其内容。
- 启动：原有应用自动备份完成并按原策略保留三份，清理一份旧备份；未另行启动手工备份。storage 初始化、legacy JSON 跳过、本地书监听及端口监听成功。书架定时检查报告更新一本，前后聚合仍一致。唯一采集到的 WARN 为合成测试故意不带令牌的请求，符合匿名拒绝预期。
- 线上只读 schema 预检成功。合成 HTTP 验收 **17 次请求、7 组断言全部通过，96 ms**：201 条稀疏正文按 200 条分页、真实章位续页/闭区间、跨用户隔离、匿名拒绝、本地原文拒删、TXT 内容顺序与隔离、EPUB 201 章 spine/XML/正文顺序、精确移除测试目录后仍保持顺序、删除单本网络测试书不影响另一用户及本地原文。最终清理 users/books/book_chapters/toc_cache 四表测试残留均为 **0**。
- 数据：users=2、books=2、book_sources=2619、book_chapters=9、toc_cache=9，与发布前一致；9 条正文共 19,681 字符、NULL 正文=0、孤儿章节=0。新增 `chapter_order` 为可空 INTEGER，原 9 条章节均已回填。此处验证的是聚合不变，不能替代逐条正文哈希证明。
- 网页：容器回环 `/`、实际引用的 JS/CSS、`/simple/` 全部 HTTP 200；内容与新镜像磁盘文件逐字节一致。首页 SHA-256 为 `a4cdb7388ced78618c482b61ec27f3bb832c1645d6c6e08cf4fd7af582a6338d`，JS 为 `c4a5d4322b904d80ef43efa546cb218874ae5decced96e02105408bd37842e6a`，CSS 为 `dee9e2bcfb72730a9919d7ae9f84e24db3256d53d08a5606d90addac36ab5b6a`。宿主发布端口 `http://192.168.31.193:10002/` 从本工作机直接访问也返回 200，首页哈希相同。首页 no-cache、带哈希静态资源 immutable 缓存头生效。
- 二进制：线上 `/usr/local/bin/reader-dev` SHA-256 为 `b0f439ce3dd32d8c60cc33cbd2ae08434b4e0bd57d78442edc92ab9a4bf4caae`，与切换前的新镜像检查一致。

原始证据和文件校验清单见 [`tests/audit/artifacts/release-20261030/`](../../tests/audit/artifacts/release-20261030/README.md)。后续提交只补充报告、验收脚本及证据；运行应用和构建定义仍对应 `ed79d94`，不因文档同步重复重建或重启。

## 回退与后续观察

如需回退，193 项目目录内将已保留的 `reader-dev:rollback-20261030-7efd557` 重新标记为 `reader-dev:local`，再沿用同一 Compose 执行 `up -d --no-deps --wait --wait-timeout 120 reader-dev`，并重复健康及页面核验。不要删除新增兼容列或整库恢复；旧源码历史仍保存在 `deployment-history/pre-20261030-7efd557`，后续重新构建前须明确所选源码版本。

本轮没有对生产所有 2619 个书源逐一联网或执行真实账号批量采集，也没有在生产做负载测试；线上合成验收不代表真实外站长期可用。继续观察 OOM、缓存任务取消/失败章数、SQLite busy 和普通批量换源搜索的资源预算；原历史 OOM 不因此次健康检查就宣称根治。剩余优化仍按[三轮主报告](reader-three-rounds-20261030.md)及[分页复审报告](reader-cache-paging-followup-20261030.md)跟踪。
