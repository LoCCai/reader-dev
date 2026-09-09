//! XPath 规则执行（sxd-xpath，对齐 legado AnalyzeByXPath）
//!
//! 容错（legado strToJXDocument 同级处理）：
//! - 常见 HTML 命名实体（&nbsp; 等）归一为字符——sxd 仅支持 XML 预定义实体与数字引用
//! - `<td>`/`<tr>`/`<tbody>` 结尾片段自动包裹（表格行/单元格可直接 XPath）
//! - EG2：非良构 HTML（未闭合标签/未引号属性/裸 `&` 等）严格解析失败时，
//!   经 html5ever（HTML5 容错解析，jsoup 同级）重建 DOM 并序列化为良构 XML 再求值
//!   ——对齐 JsoupXpath 基于 jsoup HTML 解析器的强容错

use sxd_document::parser;
use sxd_xpath::nodeset::Node;
use sxd_xpath::{Context, Factory, Value};

/// 执行 XPath，返回字符串列表（对齐 legado getStringList）
pub fn xpath_select(rule: &str, xml: &str) -> Vec<String> {
    let normalized = normalize_html_entities(xml);
    let wrapped = wrap_fragments(&normalized);
    match try_evaluate(rule, &wrapped) {
        Some(result) => result,
        // EG2：严格 XML 解析失败 → html5ever 容错重建良构 XML 后再试
        None => {
            let repaired = repair_to_well_formed_xml(xml);
            try_evaluate(rule, &repaired).unwrap_or_default()
        }
    }
}

/// 编译并求值；文档解析失败返回 None（区别于规则/求值失败返回空列表）
fn try_evaluate(rule: &str, xml: &str) -> Option<Vec<String>> {
    let package = match parser::parse(xml) {
        Ok(p) => p,
        Err(e) => {
            tracing::debug!("XPath 文档解析失败: {e}");
            return None;
        }
    };
    let document = package.as_document();
    let factory = Factory::new();
    let xpath = match factory.build(rule) {
        Ok(Some(x)) => x,
        Ok(None) => {
            tracing::debug!("XPath 规则无效（空表达式） [{rule}]");
            return Some(vec![]);
        }
        Err(e) => {
            tracing::debug!("XPath 规则编译失败 [{rule}]: {e}");
            return Some(vec![]);
        }
    };
    let context = Context::new();
    let value = match xpath.evaluate(&context, document.root()) {
        Ok(v) => v,
        Err(e) => {
            tracing::debug!("XPath 求值失败 [{rule}]: {e}");
            return Some(vec![]);
        }
    };
    Some(value_to_strings(&value))
}

/// EG2：非良构 HTML → 良构 XML。
/// html5ever 按浏览器规则容错解析（自动闭合、纠错、实体解码、标签名小写化——与
/// JsoupXpath 的 jsoup 底座一致），再自序列化为每个标签闭合、属性引号包裹、
/// 文本/属性转义的 XML。非法 XML 名称的元素/属性跳过（如 `svg:svg` 前缀化名保留末段）。
fn repair_to_well_formed_xml(html: &str) -> String {
    let dom = scraper::Html::parse_document(html);
    let mut out = String::with_capacity(html.len() + 64);
    serialize_xml_node(dom.tree.root(), &mut out);
    out
}

/// 深度优先序列化 DOM 树为良构 XML
fn serialize_xml_node(node: ego_tree::NodeRef<'_, scraper::node::Node>, out: &mut String) {
    for child in node.children() {
        match child.value() {
            scraper::node::Node::Document | scraper::node::Node::Fragment => {
                serialize_xml_node(child, out);
            }
            // 良构 XML 不需要 doctype；注释/处理指令对 XPath 取值无贡献，一并略去
            scraper::node::Node::Doctype(_)
            | scraper::node::Node::Comment(_)
            | scraper::node::Node::ProcessingInstruction(_) => {}
            scraper::node::Node::Text(text) => {
                out.push_str(&escape_xml_text(text));
            }
            scraper::node::Node::Element(element) => {
                let Some(name) = sanitize_xml_name(element.name()) else {
                    continue;
                };
                out.push('<');
                out.push_str(name);
                for (attr_name, attr_value) in element.attrs() {
                    if let Some(attr) = sanitize_xml_name(attr_name) {
                        out.push(' ');
                        out.push_str(attr);
                        out.push_str("=\"");
                        out.push_str(&escape_xml_attr(attr_value));
                        out.push('"');
                    }
                }
                if child.children().next().is_none() {
                    out.push_str("/>");
                } else {
                    out.push('>');
                    serialize_xml_node(child, out);
                    out.push_str("</");
                    out.push_str(name);
                    out.push('>');
                }
            }
        }
    }
}

/// XML 名称清洗：去命名空间前缀（xlink:href → href；JsoupXpath 求值不依赖前缀），
/// 非法 XML 名称（首字符非字母/`_`，或含字母数字/`_`/`-`/`.` 之外字符）返回 None
fn sanitize_xml_name(name: &str) -> Option<&str> {
    let name = name.rsplit(':').next()?;
    let mut chars = name.chars();
    match chars.next() {
        Some(c) if c.is_ascii_alphabetic() || c == '_' => {}
        _ => return None,
    }
    if chars.all(|c| c.is_ascii_alphanumeric() || c == '_' || c == '-' || c == '.') {
        Some(name)
    } else {
        None
    }
}

fn escape_xml_text(s: &str) -> String {
    s.replace('&', "&amp;").replace('<', "&lt;").replace('>', "&gt;")
}

fn escape_xml_attr(s: &str) -> String {
    escape_xml_text(s).replace('"', "&quot;")
}

/// 常见 HTML 命名实体 → 字符（sxd 仅认识 XML 预定义实体；amp/lt/gt/quot/apos 由 sxd 原生处理，
/// 不可在此替换——替换出的裸 `&`/`<` 反而破坏 XML）；未知实体保留（解析失败时整体放弃）
fn normalize_html_entities(xml: &str) -> String {
    if !xml.contains('&') {
        return xml.to_string();
    }
    let mut out = String::with_capacity(xml.len());
    let b = xml.as_bytes();
    let mut i = 0;
    while i < b.len() {
        if b[i] == b'&' {
            // 数字引用直接保留（sxd 支持 &#n; / &#xn;）
            if i + 1 < b.len() && b[i + 1] == b'#' {
                out.push('&');
                i += 1;
                continue;
            }
            // 命名实体：&name;
            let mut j = i + 1;
            while j < b.len() && b[j].is_ascii_alphanumeric() {
                j += 1;
            }
            if j > i + 1 && j < b.len() && b[j] == b';' {
                let name = &xml[i + 1..j];
                if let Some(ch) = html_entity(name) {
                    out.push(ch);
                    i = j + 1;
                    continue;
                }
            }
        }
        // 原样复制当前字符（按 UTF-8 边界，勿逐字节）
        let ch = xml[i..].chars().next().unwrap();
        out.push(ch);
        i += ch.len_utf8();
    }
    out
}

pub(crate) fn html_entity(name: &str) -> Option<char> {
    Some(match name {
        "nbsp" => '\u{00A0}',
        "copy" => '\u{00A9}',
        "reg" => '\u{00AE}',
        "trade" => '\u{2122}',
        "hellip" => '\u{2026}',
        "mdash" => '\u{2014}',
        "ndash" => '\u{2013}',
        "middot" => '\u{00B7}',
        "bull" => '\u{2022}',
        "laquo" => '\u{00AB}',
        "raquo" => '\u{00BB}',
        "lsquo" => '\u{2018}',
        "rsquo" => '\u{2019}',
        "ldquo" => '\u{201C}',
        "rdquo" => '\u{201D}',
        "times" => '\u{00D7}',
        "divide" => '\u{00F7}',
        "deg" => '\u{00B0}',
        "plusmn" => '\u{00B1}',
        "micro" => '\u{00B5}',
        "para" => '\u{00B6}',
        "sect" => '\u{00A7}',
        "dagger" => '\u{2020}',
        "Dagger" => '\u{2021}',
        "permil" => '\u{2030}',
        "prime" => '\u{2032}',
        "Prime" => '\u{2033}',
        "infin" => '\u{221E}',
        "ne" => '\u{2260}',
        "le" => '\u{2264}',
        "ge" => '\u{2265}',
        "asymp" => '\u{2248}',
        "equiv" => '\u{2261}',
        "larr" => '\u{2190}',
        "uarr" => '\u{2191}',
        "rarr" => '\u{2192}',
        "darr" => '\u{2193}',
        "harr" => '\u{2194}',
        "crarr" => '\u{21B5}',
        "loz" => '\u{25CA}',
        "spades" => '\u{2660}',
        "clubs" => '\u{2663}',
        "hearts" => '\u{2665}',
        "diams" => '\u{2666}',
        "oelig" => '\u{0153}',
        "OElig" => '\u{0152}',
        "scaron" => '\u{0161}',
        "Scaron" => '\u{0160}',
        "yuml" => '\u{00FF}',
        "fnof" => '\u{0192}',
        "circ" => '\u{02C6}',
        "tilde" => '\u{02DC}',
        "ensp" => '\u{2002}',
        "emsp" => '\u{2003}',
        "thinsp" => '\u{2009}',
        "minus" => '\u{2212}',
        "lowast" => '\u{2217}',
        "radic" => '\u{221A}',
        "prop" => '\u{221D}',
        "ang" => '\u{2220}',
        "and" => '\u{2227}',
        "or" => '\u{2228}',
        "cap" => '\u{2229}',
        "cup" => '\u{222A}',
        "int" => '\u{222B}',
        "there4" => '\u{2234}',
        "sim" => '\u{223C}',
        "cong" => '\u{2245}',
        "sub" => '\u{2282}',
        "sup" => '\u{2283}',
        "nsub" => '\u{2284}',
        "sube" => '\u{2286}',
        "supe" => '\u{2287}',
        "oplus" => '\u{2295}',
        "otimes" => '\u{2297}',
        "perp" => '\u{22A5}',
        "sdot" => '\u{22C5}',
        "lceil" => '\u{2308}',
        "rceil" => '\u{2309}',
        "lfloor" => '\u{230A}',
        "rfloor" => '\u{230B}',
        "lang" => '\u{2329}',
        "rang" => '\u{232A}',
        "Alpha" => 'Α',
        "Beta" => 'Β',
        "Gamma" => 'Γ',
        "Delta" => 'Δ',
        "Epsilon" => 'Ε',
        "Zeta" => 'Ζ',
        "Eta" => 'Η',
        "Theta" => 'Θ',
        "Iota" => 'Ι',
        "Kappa" => 'Κ',
        "Lambda" => 'Λ',
        "Mu" => 'Μ',
        "Nu" => 'Ν',
        "Xi" => 'Ξ',
        "Omicron" => 'Ο',
        "Pi" => 'Π',
        "Rho" => 'Ρ',
        "Sigma" => 'Σ',
        "Tau" => 'Τ',
        "Upsilon" => 'Υ',
        "Phi" => 'Φ',
        "Chi" => 'Χ',
        "Psi" => 'Ψ',
        "Omega" => 'Ω',
        "alpha" => 'α',
        "beta" => 'β',
        "gamma" => 'γ',
        "delta" => 'δ',
        "epsilon" => 'ε',
        "zeta" => 'ζ',
        "eta" => 'η',
        "theta" => 'θ',
        "iota" => 'ι',
        "kappa" => 'κ',
        "lambda" => 'λ',
        "mu" => 'μ',
        "nu" => 'ν',
        "xi" => 'ξ',
        "omicron" => 'ο',
        "pi" => 'π',
        "rho" => 'ρ',
        "sigmaf" => 'ς',
        "sigma" => 'σ',
        "tau" => 'τ',
        "upsilon" => 'υ',
        "phi" => 'φ',
        "chi" => 'χ',
        "psi" => 'ψ',
        "omega" => 'ω',
        "upsih" => 'ϒ',
        "piv" => 'ϖ',
        _ => return None,
    })
}

/// 片段包裹（对齐 legado AnalyzeByXPath.strToJXDocument）：
/// `</td>` 结尾 → 包 `<tr>`；`</tr>`/`</tbody>` 结尾 → 包 `<table>`
fn wrap_fragments(xml: &str) -> String {
    let mut s = xml.to_string();
    if s.trim_end().ends_with("</td>") {
        s = format!("<tr>{s}</tr>");
    }
    if s.trim_end().ends_with("</tr>") || s.trim_end().ends_with("</tbody>") {
        s = format!("<table>{s}</table>");
    }
    s
}

fn value_to_strings(value: &Value) -> Vec<String> {
    match value {
        Value::Nodeset(nodes) => nodes
            .document_order()
            .iter()
            .filter_map(node_to_string)
            .filter(|s| !s.is_empty())
            .collect(),
        Value::String(s) => {
            if s.is_empty() {
                vec![]
            } else {
                vec![s.clone()]
            }
        }
        Value::Number(n) => vec![n.to_string()],
        Value::Boolean(b) => vec![b.to_string()],
    }
}

/// 提取单个节点的字符串值：
/// - 元素：XPath string-value + 块级换行（sxd string_value 会把 `<p>` 段落拼成
///   一行，Reader Dev 纯文本渲染需要块级边界；语义为 XPath 文本的增强）
/// - 属性：属性值
/// - 文本节点：文本内容
/// Root / 注释 / 处理指令 / 命名空间节点不产出结果
fn node_to_string(node: &Node) -> Option<String> {
    match node {
        Node::Element(_) => Some(element_text_keep_blocks(node)),
        Node::Attribute(attr) => Some(attr.value().to_string()),
        Node::Text(text) => Some(text.text().trim().to_string()),
        Node::Root(_) | Node::Comment(_) | Node::ProcessingInstruction(_) | Node::Namespace(_) => {
            None
        }
    }
}

/// 元素文本：递归拼接后代文本节点，块级元素/`<br>` 之间保留换行。
/// sxd_xpath `string_value()` 对元素是纯拼接（`<p>一</p><p>二</p>` → "一二"），
/// 会丢失正文段落；此处按 HTML 块级语义插入 `\n`。
fn element_text_keep_blocks(node: &Node) -> String {
    let mut out = String::new();
    for child in node.children() {
        match child {
            Node::Text(t) => out.push_str(t.text()),
            Node::Element(e) => {
                let name = e.name().local_part().to_ascii_lowercase();
                let block = is_xpath_block_tag(&name);
                if block && !out.is_empty() && !out.ends_with('\n') {
                    out.push('\n');
                }
                let inner = element_text_keep_blocks(&child);
                out.push_str(&inner);
                if block && !inner.is_empty() && !out.ends_with('\n') {
                    out.push('\n');
                }
            }
            _ => {}
        }
    }
    while out.ends_with('\n') {
        out.pop();
    }
    while out.starts_with('\n') {
        out.remove(0);
    }
    out
}

fn is_xpath_block_tag(name: &str) -> bool {
    matches!(
        name,
        "br" | "p"
            | "div"
            | "li"
            | "h1"
            | "h2"
            | "h3"
            | "h4"
            | "h5"
            | "h6"
            | "tr"
            | "dd"
            | "dt"
            | "hr"
            | "section"
            | "article"
            | "blockquote"
            | "table"
            | "ul"
            | "ol"
            | "pre"
            | "header"
            | "footer"
            | "main"
            | "nav"
            | "aside"
            | "summary"
            | "details"
            | "figure"
            | "figcaption"
            | "caption"
            | "thead"
            | "tbody"
            | "tfoot"
            | "center"
            | "address"
            | "fieldset"
            | "legend"
            | "menu"
            | "dir"
            | "colgroup"
    )
}

#[cfg(test)]
mod tests {
    use super::*;

    const XML: &str = r#"<?xml version="1.0" encoding="UTF-8"?>
<library>
  <book id="1">
    <title>三体</title>
    <author>刘慈欣</author>
    <link href="https://example.com/1">链接一</link>
  </book>
  <book id="2">
    <title>流浪地球</title>
    <author>刘慈欣</author>
    <link href="https://example.com/2">链接二</link>
  </book>
</library>"#;

    #[test]
    fn xpath_select_returns_element_text() {
        let result = xpath_select("//book/title", XML);
        assert_eq!(result, vec!["三体", "流浪地球"]);
    }

    #[test]
    fn xpath_element_text_keeps_block_newlines() {
        let xml = r#"<div id="content"><p>第一段</p><p>第二段</p><br/><p>第三段</p></div>"#;
        let r = xpath_select("//div[@id='content']", xml);
        assert_eq!(r, vec!["第一段\n第二段\n第三段"]);
        // 行内元素不产生换行
        let xml2 = r#"<div><span>甲</span><span>乙</span></div>"#;
        let r2 = xpath_select("//div", xml2);
        assert_eq!(r2, vec!["甲乙"]);
    }

    #[test]
    fn xpath_select_returns_attribute_values() {
        let result = xpath_select("//book/link/@href", XML);
        assert_eq!(
            result,
            vec!["https://example.com/1", "https://example.com/2"]
        );
    }

    #[test]
    fn xpath_select_returns_text_nodes_and_strings() {
        let texts = xpath_select("//book/title/text()", XML);
        assert_eq!(texts, vec!["三体", "流浪地球"]);

        // string() 返回 Value::String 分支
        let single = xpath_select("string(//book[1]/title)", XML);
        assert_eq!(single, vec!["三体"]);

        // 无匹配时返回空列表
        assert!(xpath_select("//book/nonexistent", XML).is_empty());
    }

    #[test]
    fn xpath_common_functions() {
        // [@class='x'] 属性谓词
        let xml = r#"<div><p class="hot">热门</p><p class="new">新书</p></div>"#;
        let r = xpath_select("//p[@class='hot']/text()", xml);
        assert_eq!(r, vec!["热门"]);

        // contains()
        let r2 = xpath_select("//p[contains(@class, 'ew')]/text()", xml);
        assert_eq!(r2, vec!["新书"]);

        // position() / 下标
        let r3 = xpath_select("//p[position()=2]/text()", xml);
        assert_eq!(r3, vec!["新书"]);
        let r4 = xpath_select("//p[2]/text()", xml);
        assert_eq!(r4, vec!["新书"]);

        // and / 比较
        let xml2 = r#"<list><item n="1">甲</item><item n="2">乙</item></list>"#;
        let r5 = xpath_select("//item[@n > 1]/text()", xml2);
        assert_eq!(r5, vec!["乙"]);

        // 属性值直接返回（@XPath:div[2]/div/h3/a/@title 形态）
        let xml3 = r#"<r><div><div><h3><a title="T1">x</a></h3></div></div><div><div><h3><a title="T2">y</a></h3></div></div></r>"#;
        let r6 = xpath_select("//div[2]/div/h3/a/@title", xml3);
        assert_eq!(r6, vec!["T2"]);
    }

    #[test]
    fn xpath_html_entities() {
        // &nbsp; 等 HTML 实体归一（sxd 仅支持 XML 预定义实体）
        // 注：sxd 将实体引用解析为独立文本节点，text() 会分段——取元素 string-value 验证
        let xml = r#"<div>书名&nbsp;&amp;&nbsp;作者</div>"#;
        let r = xpath_select("//div", xml);
        assert_eq!(r, vec!["书名\u{00A0}&\u{00A0}作者"]);
        // 属性内实体（真实书源 @XPath:.../@value 形态）
        let xml2 = r#"<data name="A&nbsp;B">x</data>"#;
        let r2 = xpath_select("//data/@name", xml2);
        assert_eq!(r2, vec!["A\u{00A0}B"]);
    }

    #[test]
    fn xpath_fragment_wrapping() {
        // </td> 结尾片段自动包 <tr>（对齐 legado strToJXDocument）
        let td = r#"<td><a href="/b/1">第一章</a></td>"#;
        let r = xpath_select("//td/a/@href", td);
        assert_eq!(r, vec!["/b/1"]);
        // </tr> 结尾自动包 <table>
        let tr = r#"<tr><td>甲</td><td>乙</td></tr>"#;
        let r2 = xpath_select("//tr/td[2]/text()", tr);
        assert_eq!(r2, vec!["乙"]);
    }

    #[test]
    fn xpath_non_wellformed_html_repaired_via_html5ever() {
        // EG2：未闭合标签——严格解析失败 → html5ever 重建良构 XML → 正常取值
        // （旧行为返回空；对齐 JsoupXpath 的 jsoup 容错）
        let html = "<div><p>未闭合";
        assert_eq!(xpath_select("//p/text()", html), vec!["未闭合"]);

        // 未引号属性 + 标签名大小写归一（jsoup 同语义）
        let html2 = r#"<DIV><A href=/book/1>第一章</A></DIV>"#;
        assert_eq!(xpath_select("//a/@href", html2), vec!["/book/1"]);

        // void 元素（未闭合 <br>/<img>）+ 裸 & 实体
        let html3 = r#"<div><img src="a.png"><br>甲&乙<p>段落</p></div>"#;
        assert_eq!(xpath_select("//img/@src", html3), vec!["a.png"]);
        assert_eq!(xpath_select("//p/text()", html3), vec!["段落"]);
        // & 解码：HTML5 规范下裸 & 保留原样（未构成实体）
        assert_eq!(xpath_select("//div/text()", html3).join(""), "甲&乙");

        // table 片段缺闭合（真实书源常见）
        let html4 = "<table><tr><td>1</td><td>2</td>";
        assert_eq!(xpath_select("//tr/td[2]/text()", html4), vec!["2"]);

        // 良构文档仍走严格路径（结果不受影响）
        assert_eq!(
            xpath_select("//book/title", r#"<book><title>三体</title></book>"#),
            vec!["三体"]
        );
    }

    #[test]
    fn xpath_repair_keeps_script_content_escaped() {
        // script/style 原始文本中的 < & 不破坏序列化产物（转义喂 sxd，取值还原原文）
        let html = r#"<html><head><script>if (a < b && c > d) { x("<div>"); }</script></head><body><p>正文</p></body></html>"#;
        // 良构外壳但 script 含裸 < → 严格解析失败 → 修复路径
        assert_eq!(xpath_select("//p/text()", html), vec!["正文"]);
    }
}
