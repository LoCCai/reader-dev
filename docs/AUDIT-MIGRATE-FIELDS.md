# JSON → SQLite 迁移字段完整性审计报告

- **审计对象**：`src/storage/migrate.rs` 的 11 个迁移函数（`master` @ 8895ae20）
- **基准 1（legacy 实体/文件布局）**：`origin/legacy` 分支
  - 实体类：`io.legado.app.data.entities.*`、`com.htmake.reader.entity.User`
  - 存储层：`com.htmake.reader.db.JSONTable` + `utils/VertExt.getStorage/saveStorage`
    （路径规则 `storage/data/{ns}/{表名}.json`；序列化 = Vert.x `JsonObject.mapFrom` / Jackson bean 规范）
  - 章节缓存：`BookHelp.getBookCacheDir`（`MD5Utils.md5Encode(bookUrl)` 全 32 位小写 hex）+ `%d.txt`
- **基准 2（master 表结构）**：`src/storage/mod.rs` CREATE TABLE 定义
- **方法**：逐字段三方比对（legacy JSON key ↔ Rust serde 映射 ↔ SQLite 列），只读分析，未改动任何代码。

---

## 核心结论

| # | 结论 | 等级 |
|---|---|---|
| 1 | **P1 缺陷：RSS 源迁移文件名不匹配。** legacy 实际写 `data/{ns}/rssSources.json`（复数，`RssSourceController` 全部读写 `"rssSources"`；`backupFileNames` 同为 `rssSources.json`），而 `migrate_rss_sources` / `scan_rss_namespaces` 只找 `rssSource.json`（单数）。**真实 legacy 部署的 RSS 订阅源永远不会被迁移**。测试用例自造的单数文件掩盖了该问题。 | 🔴 P1（丢功能） |
| 2 | 其余 10 个迁移函数字段覆盖完整：所有存在于 legacy 分支实体的字段均有列落位或别名兼容说明，无静默丢字段。 | ✅ |
| 3 | raw_json 保底覆盖率 6/11：users / books / bookmarks / book_sources / rss_sources 有原文保底；replace_rules / txt_toc_rules / http_tts_list / book_groups / user_config / book_chapters 无——但前四者已知字段全部映射（仅未知扩展字段会丢），user_config 为完整 KV 无需保底，book_chapters 见结论 5。 | ⚠️ 可接受 |
| 4 | 健壮性缺口：books/book_sources/rss_sources 走**严格 serde 解析**，单条记录任一字段类型不符（社区书源常见 `"customOrder":"5"` 字符串形态，多来自 webdav 备份还原/手工导入的文件）→ 整条**跳过且不写 raw_json**，数据彻底不落库。建议迁移路径复用 `model::book_source::normalize_book_sources` 的宽松归一。 | 🟡 P2 |
| 5 | 章节缓存只迁「有 `{index}.txt` 正文的章节」，`{md5}.json` 目录中**未缓存章节的 title/url 不落任何表**（toc_cache 不填充）；源失效的离线书目录不可恢复。另幂等判断 `WHERE book_url=?` 未限定 namespace，跨用户同名书会被误跳过。 | 🟡 P2 |
| 6 | 文档性缺陷：`migrate_book_groups` doc 注释仍称「cover/show 无对应列 → 不迁移」，实际代码已迁移 cover/show——注释过时易误导后续审计。 | 🟢 P3 |

---

## 1. migrate_users（data/users.json → users）

legacy 来源：`{username: User}` 对象；User 为 snake_case 字段（`UserController.saveStorage("data","users")`）。
> 注意：任务清单中的 `name/passwordSalt/securityToken/enabled/enableBookSource/webdav*` 等字段属上游 gedoor/reader 项目，**本仓库 legacy 分支全历史（自 a74a0b3e 起）均不存在**，按实际实体审计。

| legacy 字段（JSON key） | SQLite 列（users） | 迁移 | 说明 |
|---|---|---|---|
| username | username / user_namespace | ✅ | 空 key 回退 map 键名；namespace=用户名 |
| password | password | ✅ | |
| salt | salt | ✅ | |
| token | token | ✅ | |
| token_map | token_map | ✅ | Map<token,过期ms> → JSON 字符串 |
| last_login_at | last_login_at | ✅ | |
| created_at | created_at | ✅ | |
| enable_webdav | enable_webdav | ✅ | |
| enable_local_store | enable_local_store | ✅ | |
| enable_book_source | enable_book_source | ✅ | |
| enable_rss_source | enable_rss_source | ✅ | |
| book_source_limit | book_source_limit | ✅ | |
| book_limit | book_limit | ✅ | |
| —（legacy 无） | is_admin / raw_json | ➕ | master 扩展列 |

解析失败的用户以默认值占位（username=map 键），**原文保底在 raw_json** ✅。风险：若类型不符导致解析失败，password/salt 列为空 → 该用户无法登录（raw_json 可人工恢复）。legacy 自身写入类型一致，实际风险低。

## 2. migrate_bookshelves（bookshelf.json → books）

legacy 写入均为 `JsonObject.mapFrom(Book)`（Jackson camelCase；`isInShelf` 经 `@JsonProperty` 定名）。Rust `Book` serde rename 与之一一对应。

| legacy 字段 | SQLite 列（books） | 迁移 | 说明 |
|---|---|---|---|
| bookUrl | book_url（PK 一半） | ✅ | 空 bookUrl 脏数据跳过 |
| tocUrl | toc_url | ✅ | 曾漏写，现有 backfill_toc_url_from_raw 幂等补全 |
| origin / originName / name / author | origin / origin_name / name / author | ✅ | |
| kind | kind | ✅ | |
| customTag / customCoverUrl / customIntro | custom_tag / custom_cover_url / custom_intro | ✅ | |
| coverUrl / intro | cover_url / intro | ✅ | |
| charset | charset | ✅ | |
| type | type | ✅ | Rust 关键字 → book_type |
| group (Long) | group_name | ✅ | SQLite 关键字换名列 |
| latestChapterTitle / latestChapterTime | latest_chapter_title / latest_chapter_time | ✅ | |
| lastCheckTime / lastCheckCount / lastCheckError | last_check_time / last_check_count / last_check_error | ✅ | |
| totalChapterNum | total_chapter_num | ✅ | |
| durChapterTitle / durChapterIndex / durChapterPos / durChapterTime | dur_chapter_title / dur_chapter_index / dur_chapter_pos / dur_chapter_time | ✅ | 进度五件套齐 |
| wordCount | word_count | ✅ | |
| canUpdate | can_update | ✅ | |
| order | order_num | ✅ | 关键字换名列 |
| originOrder | origin_order | ✅ | |
| useReplaceRule | use_replace_rule | ✅ | |
| variable | variable | ✅ | |
| readConfig（嵌套对象） | read_config | ✅ | 整对象 JSON 序列化存文本 |
| isInShelf | is_in_shelf | ✅ | JsonProperty 别名一致 |
| infoHtml / tocHtml | info_html / toc_html | ✅ | 当前 legacy 序列化已忽略二者（JsonIgnoreProperties），旧文件若有亦兼容 |
| displayCover / displayIntro / splitLongChapter / cbz / pdf / localPdf（派生 getter 键） | display_cover / display_intro / split_long_chapter / cbz / pdf / local_pdf | ✅ | Jackson 会把 `getX()/isX()` 派生属性写入 JSON，Rust 侧同名接收 |
| localEpub（legado 扩展键，如存在） | local_epub | ✅ | |
| —（legacy 无） | created_at=0 | ➖ | 元信息丢失：创建时间未知（rowid 保序）；风险极低 |
| —（legacy 无） | user_namespace / raw_json | ➕ | raw_json 每本全量保底 ✅ |

❌ 无缺失项。唯一注意点见核心结论 #4（严格解析跳过整本，无行无 raw_json）。

## 3. migrate_chapter_cache（{书}_{作者}/{md5}/{index}.txt → book_chapters）

文件布局比对结果：

| 布局要素 | legacy 事实 | 迁移实现 | 判定 |
|---|---|---|---|
| 目录名 | `{name}_{author}`（`Book.getBookDir`） | 遍历 ns 下所有子目录找 `{hex}.json`，不依赖精确目录名（兼容改名/换源残留） | ✅ 更宽松 |
| md5 算法 | `MD5Utils.md5Encode(bookUrl)` 全 32 位小写 hex | `util::md5::md5_encode` 全 32 位小写 hex | ✅ 一致 |
| 目录 json | `getUserStorage(ns, "{name}_{author}", md5Encode)` → `{md5(bookUrl)}.json`，元素含 index/title/url | 读同径 `{hex}.json` 数组 | ✅ |
| 正文文件 | `getBookCacheDir()/{%d.txt}` | `{hex}/{index}.txt` | ✅ |

字段处理：

| 项 | 行为 | 判定 |
|---|---|---|
| index 键域 | `as_i64`，负数跳过；与正文文件一一对应 | ✅ |
| title 缺失 | 落空字符串（非 NULL） | ✅ |
| 无 .txt 正文的目录条目 | 跳过不入库（book_chapters 仅存已缓存正文） | ⚠️ 设计如此，但该章 title/url 从此无处可寻（目录 json 不迁移到 toc_cache）→ 离线书目录不可恢复（P2） |
| 幂等 | `SELECT COUNT(*) FROM book_chapters WHERE book_url=?` —— **未带 namespace**：A 用户已迁则 B 用户同书跳过 | ⚠️ 多用户同书场景 B 的缓存丢失（P2） |
| raw_json | 无（表结构无此列；content+title 已是全部有效信息） | N/A |

## 4. migrate_book_sources（bookSource.json → book_sources）

legacy BookSource（camelCase）→ Rust serde → 列：

| legacy 字段 | SQLite 列 | 迁移 |
|---|---|---|
| bookSourceUrl / bookSourceName / bookSourceGroup | book_source_url / book_source_name / book_source_group | ✅ |
| bookSourceType / bookUrlPattern / customOrder | book_source_type / book_url_pattern / custom_order | ✅ |
| enabled / enabledExplore / enabledCookieJar | enabled / enabled_explore / enabled_cookie_jar | ✅ |
| concurrentRate / header / jsLib | concurrent_rate / header / js_lib | ✅（jsLib 经 BaseSource 继承） |
| loginUrl / loginUi / loginCheckJs | login_url / login_ui / login_check_js | ✅ |
| bookSourceComment / variableComment | book_source_comment / variable_comment | ✅ |
| lastUpdateTime / respondTime / weight | last_update_time / respond_time / weight | ✅ |
| exploreUrl / searchUrl | explore_url / search_url | ✅ |
| ruleExplore / ruleSearch / ruleBookInfo / ruleToc / ruleContent | rule_explore / rule_search / rule_book_info / rule_toc / rule_content | ✅ 嵌套对象整体 JSON 化 |
| —（legacy 无，legado 生态扩展键） | rule_related / search_rule / explore_rule / book_info_rule / toc_rule / content_rule / key / tag / logger / variable / proxy_url / login_js | ➕ 社区源若携带即入列 |
| — | user_namespace / raw_json | ➕ raw_json 全量保底 ✅ |

**raw_json 兜底判定：兜得住。** 任务清单关注的全规则字段均在列或在 raw_json。剩余风险即核心结论 #4：严格类型解析失败 → 整源跳过（连 raw_json 都不留）。建议改走 `normalize_book_sources`（已有宽松归一：数字/布尔字符串容错、三种容器形态）。

## 5. migrate_rss_sources（→ rss_sources）

🔴 **文件名缺陷（P1）**：legacy `RssSourceController` 全部经 `getUserStorage/saveUserStorage(userNameSpace, "rssSources")` 读写 → 实际文件 `data/{ns}/rssSources.json`（复数）。迁移读 `rssSource.json`（单数）→ **永远读不到**；`scan_rss_namespaces` 同病。修复建议：两文件名都探测（优先 `rssSources.json`），一处 3 行改动即可。

字段映射（假设文件命中后）：

| legacy 字段 | SQLite 列 | 迁移 | 说明 |
|---|---|---|---|
| sourceUrl | rss_source_url（PK 一半） | ✅ | 空跳过 |
| sourceName | rss_source_name | ✅ | 空时回退 sourceUrl |
| sourceGroup | rss_source_group | ✅ | |
| enabled | enabled | ✅ | |
| sourceIcon / sortUrl / ruleArticles / ruleNextPage / ruleTitle / rulePubDate / ruleDescription / ruleImage / ruleLink / ruleContent / concurrentRate / header | （无独立列） | ✅ 经 raw_json 保底 | API 输出 `rss_source_json` 以 raw_json 为基底、表列覆盖（router.rs:2375），运行期无损 |
| sourceComment / variableComment / loginUrl / loginUi / loginCheckJs / singleUrl / articleStyle / style / enableJs / loadWithBaseUrl / customOrder / enabledCookieJar | （无独立列） | ✅ 经 raw_json 保底 | 同上；其中部分暂无 getter 封装，但原文完整 |
| — | user_namespace / raw_json | ➕ | |

## 6. migrate_bookmarks（bookmark.json → bookmarks）

legacy Bookmark：`{time, bookName, bookAuthor, chapterIndex, chapterPos, chapterName, bookText, content}`（无 bookUrl）。

| legacy 字段 | SQLite 列 | 迁移 | 说明 |
|---|---|---|---|
| time | created_at | ✅ | |
| bookName | book_name ＋ 合成 book_url（`书名` 或 `书名::作者`） | ✅ | legacy 无 URL，取稳定标识；缺 bookName 的脏数据跳过 |
| bookAuthor | book_author | ✅ | |
| chapterIndex | chapter_index | ✅ | |
| chapterPos | paragraph_index | ✅ | |
| chapterName | chapter_name ＋ title | ✅ | 空则回退 bookText 截断/占位；同章多书签 title 加 `@time[#n]` 消歧防主键折叠 |
| bookText | book_text | ✅ | |
| content | content | ✅ | |
| — | user_namespace / raw_json | ➕ | raw_json 原文保底 ✅ |

❌ 无缺失。主键 `(book_url, title)` 折叠风险已被消歧逻辑覆盖。

## 7. migrate_replace_rules（replaceRule.json → replace_rules）

legacy ReplaceRule：`id(Long) / name / group / pattern / replacement / scope / scopeTitle / scopeContent / isEnabled(@JsonProperty) / isRegex(@JsonProperty) / timeoutMillisecond / order`。

| legacy 字段 | SQLite 列 | 迁移 | 说明 |
|---|---|---|---|
| id | id（TEXT PK） | ✅ | Long → 字符串化；缺失补 uuid |
| name | name | ✅ | name/pattern 双空跳过 |
| group | group_name | ✅ | |
| pattern | find | ✅ | 别名映射 |
| replacement | replace | ✅ | |
| scope | scope | ✅ | |
| scopeTitle / scopeContent | scope_title / scope_content | ✅ | 默认 0/1 与 legacy 默认一致 |
| isEnabled | enable | ✅ | `isEnabled` 主键名，兼容 `enabled`/`enable` 变体，缺省 true |
| isRegex | is_regex | ✅ | |
| timeoutMillisecond | timeout_millisecond | ✅ | 缺省 3000 |
| order | order_num | ✅ | 兼容 `orderNum` 变体 |
| probe | — | N/A | 本仓库 legacy ReplaceRule **无 probe 字段**（清单笔误，属 legado 手机端概念） |
| — | user_namespace | ➕ | |
| raw_json | ❌ 无此列 | ⚠️ | 已知字段全覆盖故可接受；未知扩展字段会丢 |

小注：PK 为全局 `id`（不含 namespace），跨用户同毫秒 id 理论冲突，概率可忽略。

## 8. migrate_txt_toc_rules（txtTocRule.json → txt_toc_rules）

legacy TxtTocRule：`id(Long) / name / rule / serialNumber(Int, 默认 -1) / enable`。

| legacy 字段 | SQLite 列 | 迁移 | 说明 |
|---|---|---|---|
| id | id（TEXT PK） | ✅ | Long → 字符串化；缺失补 uuid |
| name / rule | name / rule | ✅ | 双空跳过 |
| serialNumber | serial_number | ✅ | 缺省 0；legacy 显式 -1 原样保留 |
| enable | enable | ✅ | 兼容 `enabled` 变体 |
| example | — | N/A | 清单笔误：本仓库 legacy 无 example 字段 |
| raw_json | ❌ 无 | ⚠️ | 字段全覆盖，可接受 |

## 9. migrate_http_tts（httpTTS.json → http_tts_list）

legacy HttpTTS：`id(Long) / name / url / contentType / concurrentRate / loginUrl / loginUi / header / jsLib / enabledCookieJar / loginCheckJs / lastUpdateTime`（**无 type 字段**）。

| legacy 字段 | SQLite 列 | 迁移 | 说明 |
|---|---|---|---|
| url | url（PK 一半） | ✅ | url 主键语义替代 id |
| id | ❌ 丢弃 | ✅ 设计内 | url 为业务主键；id 丢失不影响功能（仅元信息） |
| name | name | ✅ | url/name 双空跳过 |
| type（legacy 无，防御读取） | type | ✅ 缺省 0 | |
| contentType / concurrentRate / loginUrl / loginUi / header / jsLib / loginCheckJs | 同名 snake 列 | ✅ | |
| enabledCookieJar | enabled_cookie_jar | ✅ | |
| lastUpdateTime | last_update_time | ✅ | |
| raw_json | ❌ 无 | ⚠️ | 字段全覆盖，可接受 |

## 10. migrate_book_groups（bookGroup.json → book_groups）

legacy BookGroup：`groupId / groupName / cover / order / show`。

| legacy 字段 | SQLite 列 | 迁移 | 说明 |
|---|---|---|---|
| groupId | id | ✅ | books.group_name 引用不变 |
| groupName | name | ✅ | 空跳过 |
| cover | cover | ✅ | **doc 注释误称不迁移，代码已迁移（P3 文档修正建议）** |
| show | show | ✅ | 缺省 true |
| order | order_num | ✅ | |
| raw_json | ❌ 无 | ⚠️ | 五字段全覆盖，可接受 |

## 11. migrate_user_configs（userConfig.json → user_config）

legacy 形态：`saveUserConfig` 存整个请求 JsonObject（`{键:值}`，值多为 JSON 字符串，含 `@updateTime` 时间戳键）。

| 形态/字段 | SQLite 列 | 迁移 | 说明 |
|---|---|---|---|
| `{键:值}` 对象 | (user_namespace, ns=key, config=value) | ✅ | 字符串原样、其余 JSON 序列化——与 legacy「值即字符串」语义一致；`@updateTime` 也作为一行保留（忠实原文） |
| `[{key,value}]` 数组 | 同上 | ✅ | 缺 value 存空串；缺 key 跳过 |
| 其他形态 | 跳过并回滚事务 | ✅ 安全 | |
| updated_at | 置 0 | ➖ 元信息丢失（原文件无该时间戳以外的更新时刻），可接受 |

KV 完整迁移，无需 raw_json。

---

## raw_json 保底覆盖率总结

| 表 | raw_json 列 | 已知字段映射完整性 | 评价 |
|---|---|---|---|
| users | ✅ | 全覆盖 | 🟢 双保险 |
| books | ✅ | 全覆盖 | 🟢 双保险（toc_url 补全机制依赖它） |
| book_sources | ✅ | 全覆盖 | 🟢 双保险 |
| rss_sources | ✅ | 仅 6 列落库 | 🟢 raw_json 是主要载体（API 输出以其为基底）——但前提是 P1 文件名修复后才真正有数据 |
| bookmarks | ✅ | 全覆盖 | 🟢 双保险 |
| replace_rules | ❌ | 全覆盖 | 🟡 未知扩展字段丢 |
| txt_toc_rules | ❌ | 全覆盖 | 🟡 同上 |
| http_tts_list | ❌ | 全覆盖（id 有意弃用） | 🟡 同上 |
| book_groups | ❌ | 全覆盖 | 🟡 同上 |
| user_config | ❌（不需要） | KV 完整 | 🟢 |
| book_chapters | ❌（结构不允许） | 仅缓存章节 | 🟡 未缓存章节目录信息丢失（P2） |

## 修复建议清单

1. **P1** `migrate_rss_sources` / `scan_rss_namespaces`：同时探测 `rssSources.json`（legacy 实际名）与 `rssSource.json`，并为既有部署补一次启动扫描。
2. **P2** 书籍/书源/RSS 源解析失败时降级为「宽松归一 + raw_json 落库」，不再整条丢弃（书源可直接复用 `normalize_book_sources`）。
3. **P2** `migrate_chapter_cache` 幂等查询加 `AND user_namespace=?`；评估将 `{md5}.json` 完整目录导入 `toc_cache.chapters_json`（源失效书的目录可离线恢复）。
4. **P3** 修正 `migrate_book_groups` doc 注释（cover/show 已迁移）；考虑给 replace_rules/txt_toc_rules/http_tts_list/book_groups 增加 raw_json 列以彻底对齐保底策略。

## 范围外观察（不计入本次审计结论）

- legacy 备份名单中的 `remoteBookSourceSub.json`（source_subs 数据源订阅）无迁移路径；webdav 进度文件 `{书}_{作者}.json` 亦不在迁移范围。
