# RuleAnalyzer 平衡括号解析器逐行审计补充报告

## innerRule 精确行为差异
- D1(P0): 未闭合 {{ 整条原文返回丢弃已展开部分 vs legacy 仅 pos+=2 继续
- D2(P0): 全部失败兜底 legacy 返回 "" 触发回退；master 返回去模板字面文本
- D3(P1): 失败模板应保留 {{...}} 字面；master 蒸发
- D4(P1决策): fr 失败过冲吞噬相邻匹配 quirk 是否复刻
- D5(P1): }} 定位需平衡扫描替换裸 find

## splitCombined 平衡保护
- D7(P1): 朴素切分切进后续平衡组 a&&b[x&&y]c 反例
- D8-D11: {} 保护模型/Error/chompRuleBalanced 变体/负深度策略 P2

修复优先序：R1(expand_inline 三态兜底) → R4(split_combined 组保护) → R2(平衡扫描)
