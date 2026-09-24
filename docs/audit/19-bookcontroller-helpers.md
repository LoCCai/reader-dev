# BookController 内部辅助方法逐行审计报告

## mergeBookCacheInfo
master BOOK_INFO_CACHE 介质/TTL/合并方向不同但效果近似，可接受。维持现状。

## saveBookCover
### P1: 扩展名未剥 query
master `file_ext` 对 `https://a/x.png?token=1` 返回 `"png?token=1"` → Windows 下 fs::write 必然失败 → 封面静默不落盘。legacy 显式先切 `?`。
另：仅新入架执行（编辑不处理）；无"文件已存在即跳过"；未用 customCoverUrl。

## searchBookWithSource
P2: 缺失效源前置短路（600s 内已失败的书源仍发请求）——search_one_source 入口加一行 health 快照检查即可。

## getLocalChapterList
P2: 本地书 refresh=1 无效（DB 有章节即直读，用户改文件后无重建入口）；书架书目录 TTL 过短；失效源快照内存 vs legacy 磁盘持久。

## getBookShelfBooks refresh 并发度
legacy 16 并发 vs master 串行——大书架刷新可感知劣化。建议 buffer_unordered(16)。

## setCover
master GAP111 更规范，无需动作。
