# Pro 独有功能实现细节报告（textToSpeechCn/searchChapter/getAllContents/exportToEpub）

## 1. ttsByTextToSpeechCn 中文 TTS 引擎
- 协议：POST https://www.text-to-speech.cn/getSpeek.php（5s 超时）
- 表单 12 字段：language=中文普通话简体 / voice=zh-CN-XiaoxiaoNeural / text / role=0 / style=0 / rate=0 / pitch=0 / kbitrate=audio-16khz-32kbitrate-mono-mp3 / silence="" / styledegree=1 / user_id="" / yzm=""
- 必需头：Origin+Referer+UA（Chrome/113 桌面版）
- 响应 JSON `{download:"<音频URL>"}` → 服务端发 302 让客户端直连源站下载
- base64=1 完全忽略（302 无法转码）
- master 建议：reqwest POST form + 解析 download 字段 + 返回 Location 头

## 2. searchBookContent/searchChapter 章节搜索契约
- SearchResult 完整字段：resultCount/resultCountWithinChapter/resultText/chapterTitle/query/pageSize/chapterIndex/pageIndex/queryIndexInResult/queryIndexInChapter
- 分页游标：lastIndex 前缀自增（首次传 0 会跳过第 0 章）；响应 {list, lastIndex}
- 摘要窗口 ±20 字符；大小写敏感 indexOf 循环找全部位置

## 3. exportToEpub 流水线
- EPUB 2.0 + NCX v2 目录
- 元数据 publisher="Legado"、language="zh"
- setCover：本地路径/缓存/远程三级获取，存 Images/cover.jpg
- setAssets：fonts.css/main.css/logo.png 从 classpath 注入；cover.html/intro.html 两个前置 section
- setEpubContent：逐章 Text/chapter_{i}.html，标题剥 🔒 emoji，fixPic 图片内嵌为 ../Images/{md5-16}.{ext} 并改写 src
- 正文格式化：按行 trim、空行跳过、img 行包 div.duokan-image-single、其余行 p 标签
- zip 首条目 mimetype STORED 未压缩 "application/epub+zip"

## 4. getAllContents 与 cacheBookOnServer 区别
- cacheBookOnServer：网络抓取逐章正文落盘（写文件+图片）
- getAllContents：纯缓存只读组装器，零网络，缺章写"暂无缓存内容。"占位
- exportToTxt 流式追加写盘用 appConfig.exportCharset（默认 UTF-8）
