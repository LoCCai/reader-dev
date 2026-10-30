/**
 * C2: epubLoader unit tests (in-memory minimal EPUB via fflate.zipSync).
 */
import { test } from 'node:test'
import assert from 'node:assert/strict'
import { zipSync, strToU8 } from 'fflate'
import {
  parseEpubBytes,
  resolveHref,
  destroyEpubDoc,
  isHtmlTextEmpty,
  type EpubDoc,
} from './epubLoader.ts'

/** Build a minimal EPUB byte stream (one nav-less OPF, two spine chapters) */
function buildMinimalEpub(): Uint8Array {
  const opf = `<?xml version="1.0"?>
<package xmlns="http://www.idpf.org/2007/opf" version="3.0" unique-identifier="id">
  <metadata xmlns:dc="http://purl.org/dc/elements/1.1/">
    <dc:title>Test Book</dc:title>
    <dc:identifier id="id">urn:uuid:test</dc:identifier>
  </metadata>
  <manifest>
    <item id="ch1" href="text/chapter1.xhtml" media-type="application/xhtml+xml"/>
    <item id="ch2" href='text/chapter2.xhtml' media-type="application/xhtml+xml"/>
    <item id="css" href="style/main.css" media-type="text/css"/>
    <item id="img" href="images/cover.png" media-type="image/png"/>
  </manifest>
  <spine>
    <itemref idref="ch1"/>
    <itemref idref="ch2" linear="no"/>
  </spine>
</package>`
  return zipSync({
    mimetype: strToU8('application/epub+zip'),
    'META-INF/container.xml': strToU8(
      `<?xml version="1.0"?><container version="1.0" xmlns="urn:oasis:names:tc:opendocument:xmlns:container"><rootfiles><rootfile full-path="OEBPS/content.opf" media-type="application/oebps-package+xml"/></rootfiles></container>`,
    ),
    'OEBPS/content.opf': strToU8(opf),
    'OEBPS/text/chapter1.xhtml': strToU8('<html><body><p>Chapter one</p></body></html>'),
    'OEBPS/text/chapter2.xhtml': strToU8('<html><body><p>Chapter two</p></body></html>'),
    'OEBPS/style/main.css': strToU8('body{color:red}'),
    'OEBPS/images/cover.png': new Uint8Array([137, 80, 78, 71]),
  })
}

test('C2: parseEpubBytes resolves OPF via container.xml and extracts manifest/spine', () => {
  const doc = parseEpubBytes(buildMinimalEpub())
  // manifest entries resolved relative to OEBPS/
  assert.equal(doc.manifest.size, 4)
  assert.equal(doc.manifest.get('ch1')?.href, 'OEBPS/text/chapter1.xhtml')
  assert.equal(doc.manifest.get('css')?.mediaType, 'text/css')
  assert.equal(doc.manifest.get('img')?.href, 'OEBPS/images/cover.png')
  // spine order preserved; linear=no flagged but still listed (rendering decides)
  assert.deepEqual(
    doc.spine.map((s) => s.idref),
    ['ch1', 'ch2'],
  )
  assert.equal(doc.spine[0]?.linear, true)
  assert.equal(doc.spine[1]?.linear, false)
})

test('#11: spine 与后端章节表口径对齐——跳过 manifest 缺失/非 html/空文本项', () => {
  const opf = `<?xml version="1.0"?>
<package version="3.0">
  <manifest>
    <item id="cover" href="cover.xhtml" media-type="image/png+xml"/>
    <item id="imgonly" href="blank.xhtml" media-type="application/xhtml+xml"/>
    <item id="missing" href="ghost.xhtml" media-type="application/xhtml+xml"/>
    <item id="ch1" href="text/ch1.xhtml" media-type="application/xhtml+xml"/>
    <item id="ch2" href="text/ch2.xhtml" media-type="application/xhtml+xml"/>
  </manifest>
  <spine>
    <itemref idref="cover"/>
    <itemref idref="imgonly"/>
    <itemref idref="missing"/>
    <itemref idref="ch1"/>
    <itemref idref="ch2"/>
  </spine>
</package>`
  const bytes = zipSync({
    'META-INF/container.xml': strToU8(
      `<?xml version="1.0"?><rootfiles><rootfile full-path="book.opf"/></rootfiles>`,
    ),
    'book.opf': strToU8(opf),
    // cover.xhtml：mediaType 非 html → 后端跳过
    'cover.xhtml': strToU8('<html/>'),
    // blank.xhtml：有 p 标签但无文本 → 后端 html_to_text 判空跳过（图片封面页同型）
    'blank.xhtml': strToU8('<html><body><p><img src="a.png"/></p></body></html>'),
    // ghost.xhtml：manifest 声明但 zip 内缺失 → 后端跳过
    'text/ch1.xhtml': strToU8('<html><body><div>第一章内容</div></body></html>'),
    'text/ch2.xhtml': strToU8('<html><body><p>第二章 内容</p></body></html>'),
  })
  const doc = parseEpubBytes(bytes)
  // 对齐后：仅剩两章，与后端目录下标 0..N 一致（原实现会保留全部 5 项导致整书错章）
  assert.deepEqual(
    doc.spine.map((s) => s.idref),
    ['ch1', 'ch2'],
  )
})

// 旧正则 [^<]*\S 的 \S 可匹配 '<' 本身：img-only/SVG 封面/&nbsp; 页被误判非空保留
// （后端 html_to_text 判空剔除 → 整书错一章）；span-only 页则被误判为空剔除（后端
// fallback 全文档文本会保留）——以下用例逐项锁定与后端 html_to_text 的同口径行为。
test('#11: isHtmlTextEmpty 与后端 html_to_text 判空口径逐项对齐', () => {
  // 块级标签内无文本（图片/SVG 封面页）→ 空
  assert.equal(isHtmlTextEmpty('<html><body><p><img src="a.png"/></p></body></html>'), true)
  assert.equal(isHtmlTextEmpty('<html><body><div><image/></div></body></html>'), true)
  // &nbsp;/&#160; 解码后为空白，trim 剔除 → 空
  assert.equal(isHtmlTextEmpty('<html><body><p>&nbsp;</p></body></html>'), true)
  assert.equal(isHtmlTextEmpty('<html><body><li>&#160;</li></body></html>'), true)
  assert.equal(isHtmlTextEmpty('<html><body><h2>&ensp;</h2></body></html>'), true)
  // 纯标签/空白 → 空
  assert.equal(isHtmlTextEmpty('<html><body><p>  </p><div><br/></div></body></html>'), true)
  // 块级文本 → 非空
  assert.equal(isHtmlTextEmpty('<html><body><p>第一章</p></body></html>'), false)
  // span-only（无块级文本）→ 后端 fallback 全文档文本保留 → 非空
  assert.equal(isHtmlTextEmpty('<html><body><span>正文内容</span></body></html>'), false)
  // head <title> 文本被后端 fallback 计入（带书名封面的真实 EPUB 常见形态）→ 非空
  assert.equal(
    isHtmlTextEmpty(
      '<html><head><title>封面</title></head><body><div><img src="c.png"/></div></body></html>',
    ),
    false,
  )
})

test('C2: parseEpubBytes tolerates single-quoted attributes and percent-encoded hrefs', () => {
  const bytes = zipSync({
    'META-INF/container.xml': strToU8(
      `<?xml version="1.0"?><rootfiles><rootfile full-path="book.opf"/></rootfiles>`,
    ),
    'book.opf': strToU8(
      `<package><manifest><item id="a" href='%E6%B5%8B%E8%AF%95.xhtml' media-type="application/xhtml+xml"/></manifest><spine><itemref idref="a"/></spine></package>`,
    ),
    '测试.xhtml': strToU8('<html><body><p>测试内容</p></body></html>'),
  })
  const doc = parseEpubBytes(bytes)
  assert.equal(doc.spine.length, 1)
  // decoded to the actual zip key
  assert.equal(doc.manifest.get('a')?.href, '测试.xhtml')
})

test('C2: resolveHref handles ./ ../ and nested base dirs', () => {
  assert.equal(resolveHref('', 'a.html'), 'a.html')
  assert.equal(resolveHref('OEBPS', './text/x.html'), 'OEBPS/text/x.html')
  assert.equal(resolveHref('OEBPS/text', '../style/main.css'), 'OEBPS/style/main.css')
  assert.equal(resolveHref('a/b', '../../top.html'), 'top.html')
})

test('C2: missing container.xml throws with clear message', () => {
  const bytes = zipSync({ 'x.html': strToU8('<p/>') })
  assert.throws(() => parseEpubBytes(bytes), /container\.xml/)
})

test('C2: destroyEpubDoc clears blob URL table (no-op without URLs)', () => {
  const doc: EpubDoc = parseEpubBytes(buildMinimalEpub())
  assert.equal(doc.blobUrls.size, 0)
  destroyEpubDoc(doc)
  assert.equal(doc.blobUrls.size, 0)
})
