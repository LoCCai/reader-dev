# AnalyzeRule 变量持久化 + splitSource 边界深度审计报告

## 一、put/get 三级互斥写入

legacy 三级：chapter.putVariable → book.putVariable → ruleData.putVariable
- 存储格式 = 实体字段序列化（variable 列 JSON map）
- put 即重序列化实体字段但不落 DB——依赖外层保存
- 第三级仅在 chapter 和 book 都 null 时可达（book===ruleData 时不可达）

get 回退链：**保留键短路先于变量表**（"bookName"→book.name、"title"→chapter.title），再查变量表。

master 差异：
- 变量表优先于内建回退（legacy 内建优先）→ `@put:{title:x}` 后 `@get:{title}` 两边结果不同【P2】

## 二、跨请求持久化

### P1: 正文阶段 @put 不回存
legacy getBookContent 全程核实：analyzeContent 对 book/chapter 的 @put 变更后控制器从不回存。master 同样不回存但靠 BOOK_VARS_CACHE 内存 LRU 维持进程内可见性。
### P1: 进程重启丢失
BOOK_VARS_CACHE 纯内存无磁盘落盘。legacy 至少 ACache bookInfoCache 偶发可活（variable 不在合并白名单所以实际上也丢）——两边都不理想但 master 是净退化。

### 跨阶段断裂
详情 load_book_vars(ns,src,bookUrl) → 存 book_url+toc_url 双键；目录阶段复制到每章节 URL 键；正文按 chapter_url 加载。跳过目录直取正文的请求 miss（拿到空表）。

### 隔离维度对比
| 场景 | legacy 三级 | master 扁平 |
|---|---|---|
| 隔离 | 实体归属隐式隔离 | 显式 ns+source+url（强于 legacy）|
| 容量 | 无上限（随实体）| 512/64/1MB 封顶静默截断 |

## 三、splitSource 边界穷举

| 输入 | allInOne | legacy Mode/段数 | master | 一致？ |
|---|---|---|---|---|
| "" | - | 0 段 | 0 段 | ✅ |
| @css:a | false | Default（保留原文交 JSoup） | Css | ✅ |
| {{$.x}} | false | Regex 强转 + makeUpRule 回填 | JsonPath + expand_inline | ⚠️ 标签不同结果等价 |
| tag.a@text | false | Default | Css | ✅ |
| <js>code</js> | - | Js | Js | ✅ |
| <js>a</js>@Css:b@text | - | [Js]+[Default] 管道 | JS+Css | ✅ |
| :regex | **false** | Default → JSoup 伪类选择器 | **Regex** | ❌ master 无 allInOne 门控 |
| $.json.path | false | Json | JsonPath | ✅ |
| /xpath/expr | false | XPath | XPath | ✅ |
| ##pat##rep | false | Default + makeUpRule 空规则跳分发直接替换 | 显式空体分支纯替换 | ✅ |

补充不一致：
- $N 检测面窄：legacy `\$\d{1,2}` 任一命中即强制 Regex；master 仅 contains("$1")||contains("$2")
- JSON 强制时机：legacy SourceRule 构造期用 isJSON 定死；master 执行期对每段实时检测——JS 链中段输入变 HTML 时两者分叉

## 四、变量持久化改进建议

12-1: BOOK_VARS_CACHE 落 SQLite（ns+source+url 主键），load 侧 DB-backfill
12-2: 正文/目录阶段 load 时合并 load_book_vars(book_url)（book 级作底、章节级覆盖），save 双写
12-3: resolve_get 将 "title"/"bookName" 内建判断提到 vars.get 之前（对齐 legacy 实体字段优先）
