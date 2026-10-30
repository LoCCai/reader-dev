# reader-dev 发布候选复审与大书分页优化（2026-10-30）

本批继续三轮审计后的候选 `d6dc13e`，不以尚有其他已知缺陷的最初部署版本充当性能对照。所有测试使用隔离工作树、临时 SQLite 和合成用户；未读取生产正文、修改生产数据库或重启 reader-dev。

## 修改与合约

原 `getBookCacheChapters` 响应最多 200 章，但每个请求都调用 `ordered_cached_chapters` 加载整本正文，随后才按范围截断。新 `cached_chapters_page` 先查询最多 201 条元数据：前 200 条供本页使用，额外一条只判断 `hasMore`；随后按完整复合主键读取最多 200 条正文。两次查询在同一 WAL 读事务中，避免清缓存或刷新目录穿插造成游标与正文不一致。没有新增数据库迁移、索引或依赖。

- 当前或过期目录、替代 TOC 键仍可用；优先使用书架当前 toc_url 对应快照。
- 卷标题不计章位，缺正文保留原章位间隔，重复章节 URL 保留各目录位置和标题；哈希正文优先，兼容旧顺序键。
- 本地与导入书按原存储章位分页；网络书清 TOC 后使用持久 chapter_order 或旧小序号。任何无法明确定位的大哈希仍要求刷新目录，即使请求范围不包含它。
- from/to 仍为闭区间，hasMore 只探测所选范围；nextFrom 为最后返回的真实章位加一。命名空间参与全部查询，空正文仍算已缓存。
- 前端唯一使用方 ReaderView 已按 nextFrom 续页，不需改成分页偏移。

本批另外发现并修复缓存弹层的两项错误：非终态的单章写入错误会提前结束 UI 并隐藏取消入口；部分失败没有明确失败数告警。现在保留任务运行和精确 taskId 取消能力，消费 SSE failedCount；终态明确区分完整成功与部分失败。取消世代与账号隔离逻辑保持既有契约。新增 2 项真实 SFC 交互在修前均失败、修后通过；另用真实 ReadableStream 验证 API 的跨块/CRLF SSE 解析。

## 性能对照方法

`examples/audit_cache_paging.rs` 仅供显式执行的合成探针；不接受生产服务地址，prepare 必须使用新目录，read 必须存在匹配的合成标记。同一 fixture 含两本书：各 4096 章、每章 65536 字节，即单书 256 MiB 正文；一本有过期、替代键的目录，一本仅有持久章序。目录另含卷标题。

生成数据与请求测量在不同进程进行；每次请求启动新进程、真实回环 HTTP 路由与客户端。测试 200 章一页和中间 10 章的闭区间，每场景修前/修后各 3 次交替执行。核对每章位置、标题、正文长度和前缀、hasMore、nextFrom，再比较完整响应字节长度和摘要。记录请求中位耗时和进程 VmHWM；峰值包含服务端、客户端及 JSON 解析，不等同于纯 SQL 内存。

本机 debug 构建，未清除宿主 OS 页缓存，且宿主有其他负载；结果用于验证读取量和内存方向，不作为线上 SLA、浏览器池或整机 OOM 已根治的证据。单章大小和并发请求仍会影响内存，目录元数据仍需随书长解析，整书导出路径仍加载全书。本批未引入 Redis 或提高内存限额。

24 次独立进程读取全部通过，4 个场景的修前/修后响应字节长度与完整摘要一致；生成的约 518 MiB 测试数据库由执行器在 finally 中删除。以下为各 3 次的中位数：

| 目录状态 / 本次返回 | 修前峰值 RSS | 修后峰值 RSS | 修前请求耗时 | 修后请求耗时 |
| --- | ---: | ---: | ---: | ---: |
| 有目录，200 章 | 587.7 MiB | 108.6 MiB | 753.6 ms | 553.6 ms |
| 有目录，中间 10 章 | 565.9 MiB | 52.0 MiB | 481.4 ms | 96.2 ms |
| 无目录，200 章 | 566.1 MiB | 112.0 MiB | 3420.8 ms | 542.9 ms |
| 无目录，中间 10 章 | 565.9 MiB | 51.9 MiB | 5674.3 ms | 131.9 ms |

峰值内存中位数下降约 80%–91%。耗时受本机共享负载/页缓存影响，尤其无目录修前 10 章的单次耗时 1.7–14.9 秒波动，不据此承诺线上固定倍数提速。完整 24 条样本、二进制 SHA-256、fixture 元数据、响应摘要和断言结果见 `tests/audit/artifacts/cache-paging-profile.json`，逐次输出见同名 `.log`。

复现使用相同锁文件和 probe 源码分别构建修前/修后。修前是 `d6dc13e`；在 fork 整合谱系中使用与其源码树完全相同的 `daf2d73`，无需依赖未发布的服务器仓库历史。须先构建前端 dist 供 RustEmbed，再依次 `cargo build --locked -j2 --example audit_cache_paging`，在编译另一版本前保留前一二进制；不要并发共用 Cargo target。随后运行：

```bash
PATH=/root/.nvm/versions/node/v22.23.4/bin:$PATH node tests/audit/cache_paging_profile.mjs /绝对路径/修前probe /绝对路径/修后probe /绝对路径/结果.json
```

执行器只在新建临时目录生成自己的合成库，真实 HTTP 限回环并禁代理，最后清理；不用生产库、全库快照、线上压力测试或清除宿主页缓存。旧代码没有 probe 时，将本候选 `examples/audit_cache_paging.rs` 单独复制到旧工作树的同名位置即可，保持业务代码不变。

## 验收

- 锁定依赖的统一 Rust 全测试编译与 probe 构建通过，未升级依赖；新增 4 项单元测试、独立 8 项 HTTP 测试均通过，无 ignored。覆盖详情见 `tests/audit/README.md`。
- 独立 SQL 审查、实际 EXPLAIN planner 均验证哈希/旧序号与正文联接使用完整复合主键索引。附加合成损坏 UTF-8 探针验证：第 201 章和页外正文不被解码；真正选中时按预期报错。此探针不是正常用户数据，也不单独冒充内存基准。
- 前端 utils/API/directives **103/103**；真实 SFC 交互 **20/20**；vue-tsc 类型检查与 Vite 生产构建通过。原三轮的浏览器 15 场景未在本批重复运行，不重复计为本批通过数。
- 既有 7 套集成 **23/23**，加独立分页 HTTP 共 **31/31**，全部 exit=0、无 ignored/filtered、无超时/程序报告跳过；逐二进制指纹、环境、耗时、计数和原始日志在 `backend-paging-integration/summary.json` 与 `backend-pagination-http/summary.json`。测试禁用实际 Camoufox 自动启动，不将 HTTP fallback 等同浏览器登录/验证码验证。
- 完整 Rust lib **830/830**，0 failed / ignored / filtered，测试本体 **405.30 秒**、exit=0；日志 `backend-paging-lib-final.log`。使用同一最终二进制串行执行，清除全部 HTTP/HTTPS/ALL 代理变量、回环 NO_PROXY，禁实际 Camoufox 自动启动；没有排除失败用例或以新增子集代替全量。

补充证据均使用新名保留：`backend-paging-compile.log`、`backend-paging-example-build.log`、`backend-pagination-unit.log`、`backend-pagination-http/`、`frontend-warning-*.tap` 及类型/构建 log。原三轮日志未覆盖。

## 发布前核对

2026-10-30 本批只读复查：193 源码仍为 `7efd557fe9d612552d5e92dceb214e7cb619693c`，工作树干净；reader-dev 仍使用原镜像 `sha256:569386069bd69ae8a24a0bb4dc398d1edc6d914a19664eb609e2f3a90286b343`，StartedAt 为 2026-10-27T14:13:33.266837189Z，restart=0、OOMKilled=false。GitHub origin/master 仍为 `e29b643535dccc82eeada5600cf7386a24a81a62`。

发布候选保留 fork 正常祖先，部署快照作为独立提交进入审阅分支；不强推 master、不把 193 重建仓库的历史或运行数据并入 GitHub。授权发布时先复核生产 HEAD 是否变化，沿用 `/home/ccai/docker/reader-dev/docker-compose.yml` 仅构建和更新 reader-dev，保留原镜像作为回退。上一批 chapter_order 兼容增量及回填仍是该次上线需核验的数据库变更；本批分页没有再增加 schema 变更。

本批业务与验收提交为本地 `290167c`、fork 整合谱系 `2a46538`，两者完整源码树一致。整合分支 [`audit/reader-reconcile-193-20261030`](https://github.com/LoCCai/reader-dev/tree/audit/reader-reconcile-193-20261030) 已成功推送 GitHub，未修改 master、未合并上游翻译参考分支。推送后的状态说明仅为文档更新，不改已验证业务代码。

当前未构建完整 Docker 运行镜像、未做生产迁移/负载测试、未上线 193。生产重启/数据库操作仍适用 `/www/wwwroot/bot/QIQI/qq/AGENTS.md:67-68` 的明确授权要求："重启任何 bot / 服务前先向用户弹窗提示，经授权后再执行"，"数据库相关处理（读改/迁移/清理等）同样先弹窗提示征得同意"。本次若发布，会仅更新 reader-dev 并执行上一批 chapter_order 兼容元数据初始化；不重启数据站或其他 bot，不擅自备份整库。

后续仍可分别评估：目录缓存标记目前只消费 index，却仍拉取分页正文，可通过兼容的元数据请求减少总网络字节；无目录路径可评估章序查询索引；整书导出可评估流式写入。三者均未混入本批，避免把单次有界读取误写成整站所有缓存/导出已流式化。普通批量换源搜索的资源预算等未完成项继续按三轮主报告跟踪。
