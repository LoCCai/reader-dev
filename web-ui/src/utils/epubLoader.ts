/**
 * P0-1 EPUB 原版渲染：.epub 文件加载与结构化解析。
 *
 * 数据流：file/download stream=1 拉取 .epub 字节 → fflate 解压（同步 unzip，
 * 书籍级一次性成本）→ META-INF/container.xml 定位 OPF → manifest/spine
 * 建立章节顺序 → 产出 EpubDoc（资源表 + spine 条目）供 EpubIframe 渲染。
 *
 * 资源引用策略：XHTML/CSS/图片全部以 blob URL 形式注入 iframe srcdoc，
 * 相对路径在生成 srcdoc 时重写为 blob URL；沙箱禁 script，无执行风险。
 */
import { unzipSync, strFromU8 } from 'fflate'

/** OPF manifest 单项 */
export interface EpubManifestItem {
  id: string
  href: string
  mediaType: string
  properties?: string
}

/** spine 单项（阅读顺序） */
export interface EpubSpineItem {
  idref: string
  linear: boolean
}

/** 解析后的 EPUB 文档 */
export interface EpubDoc {
  /** 全部资源：zip 内路径 → 字节 */
  files: Map<string, Uint8Array>
  /** manifest id → 条目 */
  manifest: Map<string, EpubManifestItem>
  /** 阅读顺序（仅 linear=yes 与无标记项） */
  spine: EpubSpineItem[]
  /** OPF 所在目录（相对路径基准） */
  opfDir: string
  /** 已创建的 blob URL（销毁时统一 revoke） */
  blobUrls: Map<string, string>
}

/** container.xml → OPF 路径 */
function findOpfPath(files: Map<string, Uint8Array>): string {
  const container = files.get('META-INF/container.xml')
  if (!container) throw new Error('EPUB 缺少 container.xml')
  const xml = strFromU8(container)
  const m = /full-path\s*=\s*"([^"]+)"/.exec(xml)
  if (!m) throw new Error('container.xml 无 rootfile')
  return m[1]
}

/** 规范化 zip 路径：解 %XX、反斜杠、去 ./ */
function normalizeZipName(name: string): string {
  let n = name.replace(/\\/g, '/')
  try {
    n = decodeURIComponent(n)
  } catch {
    /* 已是明文 */
  }
  while (n.startsWith('./')) n = n.slice(2)
  return n
}

/** 相对路径解析：base 目录 + href → zip 内路径（含 ../ 处理） */
export function resolveHref(opfDir: string, href: string): string {
  const parts = (opfDir ? opfDir.split('/') : []).concat(href.split('/'))
  const out: string[] = []
  for (const p of parts) {
    if (!p || p === '.') continue
    if (p === '..') out.pop()
    else out.push(p)
  }
  return out.join('/')
}

/**
 * 加载并解析 EPUB：
 * - bookUrl 为本地书路径 → file/download?path=...&stream=1 直出字节
 * - 返回 EpubDoc；调用方持有并在切换/卸载时调 destroyEpubDoc 回收 blob URL
 */
export async function loadEpubDoc(bookUrl: string): Promise<EpubDoc> {
  // file/download 需要 path 参数 + accessToken（request 层自动附带 token）
  const res = await fetch(
    `/reader3/file/download?path=${encodeURIComponent(bookUrl)}&stream=1`,
    { credentials: 'same-origin' },
  )
  if (!res.ok) throw new Error(`EPUB 文件获取失败（${res.status}）`)
  const buf = new Uint8Array(await res.arrayBuffer())
  return parseEpubBytes(buf)
}

/** 从字节解析 EPUB（测试可直接喂内存数据） */
export function parseEpubBytes(bytes: Uint8Array): EpubDoc {
  const raw = unzipSync(bytes)
  const files = new Map<string, Uint8Array>()
  for (const [name, data] of Object.entries(raw)) {
    if (name.endsWith('/')) continue
    files.set(normalizeZipName(name), data)
  }

  const opfPath = findOpfPath(files)
  const opfDir = opfPath.includes('/') ? opfPath.slice(0, opfPath.lastIndexOf('/')) : ''
  const opfXml = strFromU8(files.get(opfPath) ?? new Uint8Array())

  // manifest 解析：属性顺序无关，逐 <item> 提取
  const manifest = new Map<string, EpubManifestItem>()
  for (const m of opfXml.matchAll(/<item\b[^>]*>/g)) {
    const tag = m[0]
    const attr = (n: string): string => {
      const r = new RegExp(`${n}\\s*=\\s*"([^"]*)"`).exec(tag)?.[1]
      ?? new RegExp(`${n}\\s*=\\s*'([^']*)'`).exec(tag)?.[1]
      ?? ''
      try {
        return decodeURIComponent(r)
      } catch {
        return r
      }
    }
    const id = attr('id')
    if (!id) continue
    manifest.set(id, {
      id,
      href: resolveHref(opfDir, attr('href')),
      mediaType: attr('media-type'),
      properties: attr('properties') || undefined,
    })
  }

  // spine 解析
  const spine: EpubSpineItem[] = []
  const spineBlock = /<spine\b[^>]*>([\s\S]*?)<\/spine>/i.exec(opfXml)?.[1] ?? ''
  for (const m of spineBlock.matchAll(/<itemref\b[^>]*>/g)) {
    const tag = m[0]
    const idref = /\bidref\s*=\s*"([^"]*)"/.exec(tag)?.[1]
      ?? /\bidref\s*=\s*'([^']*)'/.exec(tag)?.[1]
    if (!idref) continue
    const linear = !/\blinear\s*=\s*["']no["']/i.test(tag)
    spine.push({ idref, linear })
  }

  // spine 口径对齐后端章节表（#11：前后端下标域一致——EpubIframe 用后端目录下标
  // 直接索引 spine）：后端建章节表时跳过 manifest 缺失/非 xhtml/html/读取失败/空文本
  // 项（local_book.rs:448-463），前端原样保留全部 itemref → raw 模式从首个被后端
  // 跳过的页（图片封面等）起整书错一章。此处按同口径过滤。
  // 空文本判定与后端 html_to_text 同口径（见 isHtmlTextEmpty），不再用「块级标签
  // 后紧跟非空白」的近似正则——\S 可匹配 '<' 本身，图片封面页（<p><img/></p>）
  // 被误判非空保留，而后端剔除。
  // 残留限制：后端 toc 骨架目录模式（toc+spin 等）下目录条目并非一一对应 spine，
  // 前端无从对齐——该形态书籍不适用 raw 模式。
  const alignedSpine = spine.filter((sp) => {
    const item = manifest.get(sp.idref)
    if (!item || !/x?html/i.test(item.mediaType)) return false
    const data = files.get(item.href)
    if (!data) return false
    return !isHtmlTextEmpty(strFromU8(data))
  })

  return { files, manifest, spine: alignedSpine, opfDir, blobUrls: new Map() }
}

/**
 * 实体解码（判空口径所需的最小集）：数字引用 + 解码结果为空白的命名实体
 * （&nbsp;/&ensp;/&emsp;/&thinsp;/&Tab;/&NewLine;）。后端 html5ever 全量解码后
 * 经 Rust trim（U+00A0 等属 White_Space）剔除；前端不解码则把 "&nbsp;" 字面量
 * 误判为非空。其余命名实体解码与否不影响判空（字面量与解码值均非空白），
 * 保持原样即可。
 */
function decodeEntitiesForBlankCheck(s: string): string {
  return s.replace(/&(#[0-9]+;?|#x[0-9a-f]+;?|[a-z][a-z0-9]*;)/gi, (all, body: string) => {
    if (body[0] === '#') {
      const hex = body[1] === 'x' || body[1] === 'X'
      const cp = parseInt(body.slice(hex ? 2 : 1), hex ? 16 : 10)
      // HTML5：超范围码点替换为 U+FFFD（非空白，判空等价）
      if (!Number.isFinite(cp) || cp < 0 || cp > 0x10ffff) return '\u{fffd}'
      return String.fromCodePoint(cp)
    }
    const name = body.slice(0, -1).toLowerCase()
    switch (name) {
      case 'nbsp':
        return '\u{a0}'
      case 'ensp':
        return '\u{2002}'
      case 'emsp':
        return '\u{2003}'
      case 'thinsp':
        return '\u{2009}'
      case 'tab':
        return '\t'
      case 'newline':
        return '\n'
      default:
        return all
    }
  })
}

/**
 * 与后端 html_to_text（local_book.rs）同口径的「整页无文本」判定：
 * 1) 块级元素（p|div|h1-h3|li）内文本：剥标签、实体解码、trim 后非空 → 页面非空；
 * 2) 否则 fallback 全文档文本（html5ever 把 style/script 内容解析为原始文本节点，
 *    后端 fallback 一并计入——故此处同样不剥 style/script）trim 后非空 → 非空；
 * 3) 两者皆空 → 后端跳过该页（local_book.rs:461-463），前端同口径剔除。
 * 返回 true 表示页面判空（应从 spine 剔除）。
 */
export function isHtmlTextEmpty(html: string): boolean {
  const noComments = html.replace(/<!--[\s\S]*?-->/g, '')
  const blockRe = /<(p|div|h[1-3]|li)\b[^>]*>([\s\S]*?)<\/\1\b[^>]*>/gi
  for (const m of noComments.matchAll(blockRe)) {
    const text = decodeEntitiesForBlankCheck(m[2].replace(/<[^>]*>/g, '')).trim()
    if (text !== '') return false
  }
  // fallback：全文档文本（对齐后端 html_to_text 的 body 全文本兜底）
  const all = decodeEntitiesForBlankCheck(noComments.replace(/<[^>]*>/g, '')).trim()
  return all === ''
}

/** 取资源为 blob URL（缓存复用） */
export function epubResourceUrl(doc: EpubDoc, zipPath: string): string | null {
  const cached = doc.blobUrls.get(zipPath)
  if (cached) return cached
  const data = doc.files.get(zipPath)
  if (!data) return null
  const item = [...doc.manifest.values()].find((i) => i.href === zipPath)
  const type = mimeOf(zipPath, item?.mediaType)
  const url = URL.createObjectURL(new Blob([data as BlobPart], { type }))
  doc.blobUrls.set(zipPath, url)
  return url
}

function mimeOf(path: string, declared?: string): string {
  if (declared && !declared.includes('/')) return `application/${declared}`
  if (declared) return declared
  if (path.endsWith('.xhtml') || path.endsWith('.html')) return 'application/xhtml+xml'
  if (path.endsWith('.css')) return 'text/css'
  if (path.endsWith('.png')) return 'image/png'
  if (path.endsWith('.jpg') || path.endsWith('.jpeg')) return 'image/jpeg'
  if (path.endsWith('.gif')) return 'image/gif'
  if (path.endsWith('.svg')) return 'image/svg+xml'
  if (path.endsWith('.ttf')) return 'font/ttf'
  if (path.endsWith('.otf')) return 'font/otf'
  if (path.endsWith('.woff')) return 'font/woff'
  if (path.endsWith('.woff2')) return 'font/woff2'
  return 'application/octet-stream'
}

/** 销毁：revoke 全部 blob URL（切书/卸载防泄漏） */
export function destroyEpubDoc(doc: EpubDoc): void {
  for (const url of doc.blobUrls.values()) URL.revokeObjectURL(url)
  doc.blobUrls.clear()
}
