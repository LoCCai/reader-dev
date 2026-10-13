# AnalyzeByRegex.java 逐行审计报告

> 审计代理产出 | 对比基准 reader-pro-3.2.14.jar 反编译源 ↔ master HEAD | 审计范围：全部 118 行、5 个方法

## 方法清单

| # | 方法 | legacy 行号 |
|---|---|---|
| 1 | `<init>` + INSTANCE | 25, 27-28 |
| 2 | `getElement(res, regs, index)` — 单元素路径（首匹配全部捕获组） | 31-65 |
| 3 | `getElement$default` 合成桥 | 67-72 |
| 4 | `getElements(res, regs, index)` — 列表路径（遍历全部匹配） | 75-109 |
| 5 | `getElements$default` 合成桥 | 111-116 |

---

## P0 差异（结构性语义缺失）

### P0-1: 正则列表行 `$N` 列访问机制缺失
legacy getElements 每行 = 定长组向量（arity = groupCount+1），下游字段规则靠 `$N` 读列（makeUpRule L560-573：`result[$N]`）。master 把每行的非空组 trim 后 `join("\n")` 摊平成字符串，无列概念。所有"正则书源列表 + `$2`/`$3` 取书名/URL"的书源在 master 上必然取空。
位置：rule.rs:963-984、search.rs/book.rs 列表消费端。

### P0-2: 空组被丢弃致列序漂移
legacy null→"" 占位保位（getElements L95）；master 空值 trim 后剔除（rule.rs:971-973）。即使实现 $N 也会错位。

### P0-3: && 链无末级组提取切换
legacy `vIndex+1==regs.length` 时切换到 captures 提取路径；master 全部节均过滤拼接（rule.rs:929-948），永不做组提取。

---

## P1 差异

### P1-1: 缺失组提前断链
legacy getElements 填 "" 继续 / getElement NPE 整链失败；master 首个 None 即 break，后续已参与组也被丢弃（rule.rs:968）。配合 $N 缺失升级为 P0 根因之一。

### P1-2: replaceFirst 编译失败回退错误
legacy 编译失败 → 字面 `replaceFirst(pat文本, rep)`，保住原文其余部分。master → 返回 replacement 本身、丢弃原文（rule.rs:2047）。

---

## P2 差异

| # | 项目 | legacy | master |
|---|---|---|---|
| P2-1 | && 节 / ##pat / content 段 trim | 原样编译 | trim 后编译 |
| P2-2 | detect_kind `$N` 启发式 | `\$\d{1,2}` ($1..$99) | 仅 `contains("$1")||contains("$2")` |
| P2-3 | 字段路径 `:` 规则放行 | getString 语境 `:` 不进 Regex | 无条件 Regex |
| P2-4 | 零长度匹配空行过滤 | 产生全空行 | filter 掉（更优，保持） |
| P2-5 | `\d\w\s\b` Unicode 默认面 | ASCII 默认 | Unicode 默认 |
| P2-6 | 变长 lookbehind | Java 支持 | fancy-regex 仅定宽分支→编译失败静默空 |
| P2-7 | 替换串未知 `$N` 引用 | Java 抛异常 | 静默替换为空 |

## 已确认对齐项
- 空白节剔除（splitNotBlank ≡ is_empty→continue）✓
- 无匹配返回类型语义一致 ✓
- content 级 replaceRegex（&& 链 / 无##删除 / 第三段含#判定）逐条一致 ✓
- 替换串 `$1/$2` 已知组引用一致 ✓
- 编译失败可见性差异为 master 增强 ✓

---

## 修复优先级

| 优先级 | 修复项 |
|---|---|
| P0 批 | regex_match 列表语境返回行×组二维结构；字段求值实现 `$N` 列读取；空组占位保留；&& 链末节切换捕获提取 |
| P1 批 | 缺失组不提前断链；replaceFirst 编译失败字面回退 |
| P2 批 | 去 trim 对齐字节级；detect_kind 正则扫 `\$\d{1,2}`；其余记录差异 |
