# AnalyzeByXPath.java 逐行审计报告

## P0（结构性缺失）
- S1: getElements 无服务层实现——@XPath: bookList/chapterList → 0 条目（纯 XPath 书源完全不可用）
- S2: 非良构 HTML/裸 & /未知实体 → sxd 整文档解析失败返回空；legacy jsoup 全容错
- S3: 扩展函数 allText/html/outerHtml/ownText/num/ends-with 未实现 → 编译失败空结果
- S4: getString 元素节点应为 outerHtml 标记，master 输出内文本 → 正文 HTML 保形失效

## P1
- S5: XML 默认命名空间无前缀名不匹配（Atom/OPDS 空结果）
- S6: 组合切分无递归：混合分隔符 a||b&&c 子段编译失败

## P2
- S7: getStringList 元素文本格式差（jsoup 空白折叠 vs master 块级换行+trim+滤空）
- S8: getString 路径 %% 参与切分（legacy 不切）
- S9: 上下文节点求值缺失（JXNode.sel 相对子树）
- S10: 包裹判定 endsWith vs trim_end().endsWith 差异

## 已确认对齐
- 轴支持 child/self/descendant/ancestor/following-sibling 等 ✓
- && 合并/|| 短路/%% 交错截断 ✓
- 属性提取 @href/@src ✓
- 索引语法 [0]/[-1]/[!0] 结果等价 ✓
- master 超集：following/preceding/namespace 轴支持 ✓
