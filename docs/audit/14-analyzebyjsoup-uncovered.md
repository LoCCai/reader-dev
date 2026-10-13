# AnalyzeByJSoup 未覆盖部分逐行审计报告

## P1
- @ 切分无平衡组保护：正则回退规则中 @ 位于 (...) 时被切碎（legacy chompRuleBalanced 跳过整组）
- jsoup 子树查询含起点自身，scraper ElementRef.select 排除自身——tag./id./class. 关键字步进少计当前元素
- jsoup 扩展伪类 :eq/:lt/:gt/:contains/:matches 在 scraper 不存在→解析失败静默空

## P2
- html/all 聚合粒度差异（legacy 集合级单条 vs master 逐元素多条→%% 交错分歧）
- attr 值 trim 差异（legacy 原样入列）
- 末段选择器语义超集（master 返回元素 HTML vs legacy attr 查不到恒空）

## 已确认对齐
根上下文/N-1 循环/结果集替换/元素空判空/三种简写截断怪癖/组合器/属性子串/nth-child/not/通配 全 ✓
