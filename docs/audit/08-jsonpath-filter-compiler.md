# JsonPath 过滤表达式解析器逐行审计报告
（AnalyzeByJSonPath.java + 内嵌 json-path-2.6.0.jar FilterCompiler/EvaluatorFactory/ValueNodes）

## 文法（legacy FilterCompiler 递归下降）
OR → AND ('&&' OPERAND)* → OPERAND → '!' 回退 | '(' OR ')' | EXPRESSION
EXPRESSION := VALUE [RELOP VALUE]，RELOP 解析失败回退为 EXISTS 存在性断言
VALUE := 路径($/@) | 字面量('str'/"str"/true/false/null/-num/{json}/[json]/正则/re/flags)

## 操作符全集（21 种）与缺失路径行为矩阵
== != === !== < <= > >= =~ in nin size empty contains all subsetof anyof noneof type matches exists
缺失路径(l=UNDEFINED)：==→false / !=→true / <=→false / >→false / =~ 空串可能 true（怪癖）/ 其余 false

## 松散相等精确条件
NumberNode.equals(StringNode) → BigDecimal 比较（不对称！左数右串=true，左串右数=false）
JsonNode == 结构相等（json-smart 解析）

## master 缺失清单（按书源使用频率）
1. empty true/false — 排除空字段规则全部失效（中频）
2. contains — 标签/分类包含筛选（中低频）
3. =~ 应为全串匹配+标志 — 当前为子串匹配且标志丢失
4. ===/!== TSEQ/TSNE — 被 ==/!= 吞并 rhs 污染
5. 过滤内 $ 根引用与 RHS 路径（@.a==@.b）— 写了就静默全灭
6. indefinite 过滤路径多值语义
7. all/subsetof/anyof/noneof — 极低频
8. $..*、$..[n]、["a","b"] 键联合 — 极低频；键联合当前错位解析为索引

## ABP 应用层差异
A1 替换失败保留字面 vs master 替换空串
A2 平衡括号配对 vs find('}') 截断
A3 getString 不支持 %% 切分 vs master 统一切分
A4 getList/getObject 保结构 vs 一律打平
