# JsExtensions 同名函数内部实现等价性审计报告

## ajax 正常路径
- Cookie 整体替换 Cookie 头不做按 key 合并【P1】
- UA 兜底 Android Chrome120 Mobile vs legacy 桌面 Chrome75【P1】部分站点按 UA 出不同页面
- 重定向 custom 策略无内置跳数上限【P2】
- 响应解码 charset 已定时不剥 UTF-8 BOM【P2】
- POST 无显式 Content-Type 时 legacy 走 postJson(application/json)，master 发裸 body【P2】
- GET query 不做 analyzeFields 式再编码【P2】
- retry 字段在 java.ajax 路径失效【P2】

## base64 单参版本
- 缺省 flags=0 致 >57 字节折行 vs legacy NO_WRAP 不折【P1】E16 测试钉死了错误行为需同步改
- "base64Decode" 绑到 number[] 版本，legacy 返回 String【P1】
- android.util.Base64 shim DEFAULT 应折行【P2】

## AES 正常路径
- 解密失败抛 JS 异常 vs legacy catch 返回 null【P1】书源 aesDecode(...)?:null 判断直接中断脚本
- Base64 入参严格解码（内嵌换行/空格→失败）vs legacy 宽松跳非字母表字符【P1】
- ECB+IV 静默成功 vs Java 抛异常被 catch 返回 null【P2】
- CBC 空IV零填充可用 vs legacy 随机IV解密必坏（方向有利）【P2】

## getZipStringContent 编码
- 3参 charset 白名单仅 gbk/big5 其余按 UTF-8 lossy——传 UTF-16LE 等产乱码不报错【P1】
- 2参 BOM 输入 legacy 保留 U+FEFF、master 剥除【P2】

## htmlFormat 算法级不等价
- img 整体丢弃（legacy 保留并绝对化）【P1】插图内容丢失
- smart_paragraph_breaks 凭空断行 ≥200 字符文本【P1】legacy 从不改写正文换行结构
- block 标签集差异/全角缩进缺失/comment 处理弱/实体解码差异 【P2】

## 已确认等价
ajax header 合成顺序骨架一致 ✓ / zip 条目匹配精确一致 ✓ / key/iv UTF-8 字节转换一致 ✓ / PKCS5 unpad 成功路径等价 ✓ / cookie_subdomain 归一化含 quirk 复刻 ✓
