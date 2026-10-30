// Real Chromium DOM acceptance against the candidate Vite UI and intercepted
// synthetic API fixtures. All external network requests are denied.
// AUDIT_BASE_URL=http://127.0.0.1:54173 node tests/audit/browser_acceptance.mjs
import assert from 'node:assert/strict'
import { createRequire } from 'node:module'
import { mkdir, readFile, writeFile } from 'node:fs/promises'
import { fileURLToPath } from 'node:url'
import { execFileSync } from 'node:child_process'
const requireBrowser = createRequire(process.env.AUDIT_PLAYWRIGHT_PACKAGE || '/www/wwwroot/bot/QIQI/qq/qi-dev/web/package.json')
const root = fileURLToPath(new URL('../../', import.meta.url))
const requireWeb = createRequire(`${root}/web-ui/package.json`)
const { zipSync, strToU8 } = requireWeb('fflate')
const { chromium } = requireBrowser('@playwright/test')
const base = process.env.AUDIT_BASE_URL || 'http://127.0.0.1:54173'
const out = process.env.AUDIT_BROWSER_OUTPUT || fileURLToPath(new URL('./artifacts/browser/', import.meta.url))
await mkdir(out, { recursive: true })
const bookUrl = 'https://fixture.invalid/book-a'
const epubUrl = 'local://audit/book.epub'
const source = 'https://fixture.invalid/source-a'
const book = (url = bookUrl, type = 0) => ({ bookUrl: url, name: type === 0 ? '验收之书' : `验收类型${type}`,
  author: 'Fixture', origin: source, originName: '验收书源', tocUrl: `${url}/toc`, type,
  durChapterIndex: 1, durChapterTime: 1, totalChapterNum: 3 })
const books = [book(), book(epubUrl), ...[1,2,3,4].map(i => book(`https://fixture.invalid/type-${i}`, i))]
const toc = url => [
  ...(url === epubUrl ? [] : [{ title: '卷标题', url: 'volume', index: 0, isVolume: true }]),
  ...[0,1,2].map(i => ({ title: `第${i + 1}章`, url: `${url}/chapter/${i}`, index: url === epubUrl ? i : i + 1, isVolume: false })),
]
const rawEpub = zipSync({
  mimetype: strToU8('application/epub+zip'),
  'META-INF/container.xml': strToU8('<container><rootfiles><rootfile full-path="book.opf"/></rootfiles></container>'),
  'book.opf': strToU8('<package><manifest>' + [1,2,3].map(i => `<item id="ch${i}" href="ch${i}.xhtml" media-type="application/xhtml+xml"/>`).join('') + '</manifest><spine>' + [1,2,3].map(i => `<itemref idref="ch${i}"/>`).join('') + '</spine></package>'),
  ...Object.fromEntries([1,2,3].map(i => [`ch${i}.xhtml`, strToU8(`<html><body><p>RAW-CHAPTER-${i} 原版关键词</p></body></html>`)])),
})
const requests = []
const results = []
const browser = await chromium.launch({ headless: true,
  executablePath: process.env.AUDIT_CHROMIUM || '/root/.cache/ms-playwright/chromium-1234/chrome-linux64/chrome',
  args: ['--no-sandbox', '--disable-dev-shm-usage'] })
const context = await browser.newContext({ viewport: { width: 1440, height: 1000 }, acceptDownloads: true })
let activeUser = 'auditA'
let cacheCounter = 0
let rejectSwitch = false
const errors = []
await context.addInitScript(() => {
  localStorage.setItem('reader_access_token', 'auditA:synthetic')
  localStorage.setItem('reader_username', 'auditA')
  localStorage.setItem('reader_lang', 'zh-CN')
  localStorage.setItem('reader_epub_mode', 'text')
  localStorage.setItem('reader_chapter_preload', '0')
  Object.defineProperty(navigator, 'clipboard', { configurable: true, value: {
    writeText: async text => { window.__auditClipboard = text }, readText: async () => window.__auditClipboard || '',
  } })
})
await context.route('**/*', async route => {
  const request = route.request()
  const url = new URL(request.url())
  if (url.origin !== new URL(base).origin) return route.abort('blockedbyclient')
  if (url.pathname.startsWith('/simple/')) {
    const name = url.pathname.slice('/simple/'.length)
    if (!['index.html', 'reader.html', 'search.html', 'zh.js'].includes(name)) return route.abort('blockedbyclient')
    return route.fulfill({ status: 200, contentType: name.endsWith('.js') ? 'application/javascript' : 'text/html', body: await readFile(`${root}/web-simple/${name}`) })
  }
  if (!url.pathname.startsWith('/reader3/')) return route.continue()
  const endpoint = url.pathname.slice('/reader3/'.length)
  let body = {}
  try { body = request.postDataJSON() || {} } catch {}
  const params = { ...Object.fromEntries(url.searchParams), ...body }
  requests.push({ endpoint, params, method: request.method() })
  const respond = data => route.fulfill({ status: 200, contentType: 'application/json', body: JSON.stringify({ isSuccess: true, errorMsg: '', data }) })
  if (endpoint === 'getBookshelf') return respond(books)
  if (endpoint === 'getBookSources') return respond([{ bookSourceUrl: source, bookSourceName: '验收书源', bookSourceGroup: '验收' }])
  if (endpoint === 'getBookInfo') return respond(books.find(b => b.bookUrl === params.url) || book(params.url))
  if (endpoint === 'getBookToc') {
    const ownerUrl = books.find(b => b.tocUrl === params.tocUrl)?.bookUrl || bookUrl
    return respond(toc(ownerUrl))
  }
  if (endpoint === 'getBookContent') {
    const chapterUrl = params.chapterUrl || params.url || ''
    if (chapterUrl.includes('type-1')) return respond({ audioUrl: 'https://fixture.invalid/audio.mp3' })
    if (chapterUrl.includes('type-2')) return respond({ images: ['https://fixture.invalid/comic.png'] })
    if (chapterUrl.includes('type-3')) return respond({ downloadUrl: 'https://fixture.invalid/book.pdf' })
    if (chapterUrl.includes('type-4')) return respond({ videoUrl: 'https://fixture.invalid/video.mp4' })
    const text = `${activeUser}-PRIVATE 正文关键词 & 实体。\n第二段关键词。`
    return respond({ content: Number(params.epubContent) === 1 ? `<p>${text.replaceAll('&', '&amp;')}</p>` : text, chapterWordCount: text.length })
  }
  if (endpoint === 'getShelfBookWithCacheInfo') return respond({ ...book(), cacheChapterCount: 0, cacheSize: 0 })
  if (endpoint === 'getBookCacheChapters') return respond({ url: params.url, chapters: [], hasMore: false })
  if (endpoint === 'getBookFile') return route.fulfill({ status: 200, contentType: 'application/epub+zip', body: Buffer.from(rawEpub) })
  if (endpoint === 'searchBookMulti') return respond([book()])
  if (endpoint === 'getAvailableBookSource') return respond([book(), { ...book('https://fixture.invalid/source-b/book-a'), origin: 'https://fixture.invalid/source-b', originName: '验收新书源' }])
  if (endpoint === 'setBookSource') {
    if (rejectSwitch) return route.fulfill({ status: 200, contentType: 'application/json', body: JSON.stringify({ isSuccess: false, errorMsg: 'fixture source rejected', data: null }) })
    return respond({ ...book(params.newUrl), origin: params.bookSourceUrl, originName: '验收新书源' })
  }
  if (endpoint === 'searchBookMultiSSE') {
    const raw = `event: book\ndata: ${JSON.stringify({ lastIndex: 0, data: [book()] })}\n\nevent: end\ndata: {"lastIndex":0,"isEnd":true}\n\n`
    return route.fulfill({ status: 200, contentType: 'text/event-stream', body: raw })
  }
  if (endpoint === 'cacheBookRangeOnServer') {
    const taskId = `fixture-task-${++cacheCounter}`
    await new Promise(resolve => setTimeout(resolve, 180))
    return respond({ started: true, taskId, cached: 0, total: Number(params.to) - Number(params.from) + 1 })
  }
  if (endpoint === 'cacheBookSSE') return route.fulfill({ status: 200, contentType: 'text/event-stream', body: 'data: {"cached":0,"total":3}\n\n' })
  if (endpoint === 'cancelCacheBook') return respond({ cancelled: true })
  if (endpoint === 'exportBook') return route.fulfill({ status: 200, contentType: 'text/plain',
    headers: { 'X-Export-Warning': encodeURIComponent(JSON.stringify({ failedChapters: [{ index: 2, title: '第三章', error: 'fixture failure' }] })) },
    body: '验收之书\n第一章\nfixture body\n' })
  if (endpoint === 'getUserConfig') return respond(null)
  if (endpoint === 'getReadConfig') return respond({})
  return respond([])
})
const page = await context.newPage()
page.on('pageerror', error => errors.push(error.message))
async function scenario(name, action) {
  try { await action(); results.push({ name, passed: true }) }
  catch (error) { results.push({ name, passed: false, error: error.message }); await page.screenshot({ path: `${out}/failure-${results.length}.png`, fullPage: true }).catch(() => {}) }
}
const waitVisible = async locator => { await locator.waitFor({ state: 'visible', timeout: 12000 }); return locator }
try {
  await scenario('R1 baseline privacy proof: original IndexedDB module returns A private chapter after account switches', async () => {
    const baseline = execFileSync('git', ['show', '7efd557:web-ui/src/utils/readerLocalCache.ts'], { cwd: root, encoding: 'utf8' })
    const javascript = requireWeb('typescript').transpileModule(baseline, { compilerOptions: { target: 9, module: 99 } }).outputText
    await page.goto(`${base}/search`)
    const leaked = await page.evaluate(async code => {
      const cache = await import(URL.createObjectURL(new Blob([code], { type: 'text/javascript' })))
      const bookUrl = 'https://fixture.invalid/private-baseline-only'
      await cache.saveLocalChapter({ bookUrl, chapterUrl: 'chapter', title: 'private', index: 0, content: 'A-BASELINE-PRIVATE' })
      localStorage.setItem('reader_username', 'auditB')
      const readAsB = await cache.getLocalChapter(bookUrl, 'chapter')
      localStorage.setItem('reader_username', 'auditA')
      return readAsB?.content
    }, javascript)
    assert.equal(leaked, 'A-BASELINE-PRIVATE', 'prove the original module has no account boundary')
  })

  await scenario('R3 desktop: search result opens detail; cache immediately loads directory; delayed start cancellation is exact', async () => {
    await page.goto(`${base}/search`)
    await page.locator('.search-input').fill('验收之书')
    await page.locator('.search-bar .search-btn, .search-box .search-btn').count().then(async count => {
      if (count) await page.locator('.search-bar .search-btn, .search-box .search-btn').first().click()
      else await page.getByRole('button', { name: '搜索', exact: true }).first().click()
    })
    await waitVisible(page.locator('.result-name, .book-title, .result-title').filter({ hasText: '验收之书' }).first())
    await page.locator('.result-name, .book-title, .result-title').filter({ hasText: '验收之书' }).first().click()
    await page.waitForURL('**/book/**')
    const before = requests.filter(r => r.endpoint === 'getBookToc').length
    await page.getByRole('button', { name: '缓存', exact: true }).first().click()
    const dialog = await waitVisible(page.getByRole('dialog', { name: '章节缓存' }))
    assert.ok(requests.filter(r => r.endpoint === 'getBookToc').length > before)
    await dialog.getByRole('button', { name: '指定范围', exact: true }).click()
    await dialog.getByLabel('起始章').fill('2')
    await dialog.getByLabel('结束章').fill('3')
    await dialog.locator('.dlg-actions').getByRole('button', { name: '缓存到服务器', exact: true }).click()
    await dialog.getByRole('button', { name: '取消', exact: true }).click()
    await page.waitForTimeout(350)
    const start = requests.filter(r => r.endpoint === 'cacheBookRangeOnServer').at(-1)
    assert.equal(start.params.url, bookUrl)
    assert.equal(start.params.from, 1)
    assert.equal(start.params.to, 2)
    const cancel = requests.filter(r => r.endpoint === 'cancelCacheBook').at(-1)
    assert.equal(cancel?.params.taskId, 'fixture-task-1')
    assert.equal(cancel?.params.url, undefined)
    await dialog.getByRole('button', { name: '关闭', exact: true }).last().click()
    await page.screenshot({ path: `${out}/desktop-detail.png`, fullPage: true })
  })

  await scenario('R3 desktop: export submits current book only and preserves partial-export warning', async () => {
    await page.goto(`${base}/book/${encodeURIComponent(bookUrl)}`)
    await page.getByRole('button', { name: '导出', exact: true }).first().click()
    const dialog = await waitVisible(page.locator('.dlg-export'))
    const downloaded = page.waitForEvent('download')
    await dialog.locator('.dlg-actions').getByRole('button', { name: '导出', exact: true }).click()
    const download = await downloaded
    assert.ok(download.suggestedFilename().endsWith('.txt'))
    const exported = requests.filter(r => r.endpoint === 'exportBook').at(-1)
    assert.equal(exported.params.url, bookUrl)
    assert.equal(exported.params.format, 'txt')
    await page.waitForTimeout(1100)
    assert.equal(await dialog.isVisible(), true, 'partial warning must remain available to read')
    assert.match(await dialog.innerText(), /1 章.*失败/)
  })

  await scenario('R3 desktop: EPUB text to HTML reloads HTML; copy is enabled; in-chapter search finds visible text', async () => {
    await page.goto(`${base}/reader/${encodeURIComponent(epubUrl)}`)
    await waitVisible(page.locator('.reader-content'))
    await page.getByRole('button', { name: '纯文本', exact: true }).click()
    await waitVisible(page.locator('.epub-html'))
    assert.ok(requests.some(r => r.endpoint === 'getBookContent' && Number(r.params.epubContent) === 1))
    const copy = page.getByRole('button', { name: '复制本章', exact: true })
    assert.equal(await copy.isEnabled(), true)
    await copy.click()
    const copied = await page.evaluate(() => window.__auditClipboard)
    assert.match(copied, /正文关键词 & 实体/)
    assert.ok(!copied.includes('<p>'))
    await page.getByRole('button', { name: '搜索', exact: true }).click()
    const input = page.getByPlaceholder('输入关键词，搜索当前章正文')
    await input.fill('关键词')
    await input.press('Enter')
    await waitVisible(page.locator('mark').first())
    await page.screenshot({ path: `${out}/desktop-reader-search.png`, fullPage: true })
  })

  await scenario('R3 EPUB raw: authenticated original file loads into script-disabled iframe in chapter order', async () => {
    await page.goto(`${base}/reader/${encodeURIComponent(epubUrl)}`)
    await waitVisible(page.locator('.reader-content'))
    await page.getByRole('button', { name: '纯文本', exact: true }).click()
    await waitVisible(page.locator('.epub-html'))
    await page.getByRole('button', { name: '净化排版', exact: true }).click()
    await waitVisible(page.locator('.epub-frame'))
    await page.frameLocator('.epub-frame').locator('body').getByText('RAW-CHAPTER-2 原版关键词', { exact: true }).waitFor()
    const file = requests.filter(r => r.endpoint === 'getBookFile').at(-1)
    assert.equal(file.params.url, epubUrl)
    assert.equal(file.params.accessToken, 'auditA:synthetic')
    assert.ok(!(await page.locator('.epub-frame').getAttribute('sandbox')).includes('allow-scripts'))
  })

  await scenario('R3 privacy: a second user cannot read the first user offline chapter cache', async () => {
    activeUser = 'auditA'
    await page.goto(`${base}/reader/${encodeURIComponent(bookUrl)}`)
    await waitVisible(page.locator('.reader-content'))
    assert.match(await page.locator('.reader-content').innerText(), /auditA-PRIVATE/)
    await page.waitForTimeout(150)
    await page.evaluate(() => {
      localStorage.setItem('reader_access_token', 'auditB:synthetic')
      localStorage.setItem('reader_username', 'auditB')
    })
    activeUser = 'auditB'
    // addInitScript intentionally initializes A; use a new page-specific override
    // registered later to simulate the subsequent authenticated browser session.
    await page.addInitScript(() => {
      localStorage.setItem('reader_access_token', 'auditB:synthetic')
      localStorage.setItem('reader_username', 'auditB')
    })
    await page.reload()
    await waitVisible(page.locator('.reader-content'))
    const text = await page.locator('.reader-content').innerText()
    assert.ok(!text.includes('auditA-PRIVATE'), 'user B received user A private IndexedDB content')
    assert.match(text, /auditB-PRIVATE/)
  })

  await scenario('R3 IndexedDB scope: clearing B preserves A; unowned legacy rows stay hidden; stale-owner writes are rejected', async () => {
    const checked = await page.evaluate(async ({ bookUrl }) => {
      const cache = await import('/src/utils/readerLocalCache.ts')
      const store = (await import('/src/stores/user.ts')).useUserStore()
      store.setSession('auditB:synthetic', 'auditB')
      const deletedB = await cache.clearLocalBook(bookUrl)
      store.setSession('auditA:synthetic', 'auditA')
      const a = await cache.getLocalChapter(bookUrl, `${bookUrl}/chapter/0`)
      const oldOwner = cache.localCacheOwner()
      const legacy = await cache.getLocalChapter('https://fixture.invalid/private-baseline-only', 'chapter')
      const legacyUrls = await cache.listLocalChapterUrls('https://fixture.invalid/private-baseline-only')
      store.setSession('auditB:synthetic', 'auditB')
      const staleWrite = await cache.saveLocalChapter({ owner: oldOwner, bookUrl,
        chapterUrl: 'late-request', title: 'late', index: 0, content: 'A-LATE-PRIVATE' })
      return { deletedB, aContent: a?.content, legacy, legacyUrls, staleWrite }
    }, { bookUrl })
    assert.ok(checked.deletedB > 0)
    assert.match(checked.aContent, /auditA-PRIVATE/)
    assert.equal(checked.legacy, null)
    assert.deepEqual(checked.legacyUrls, [])
    assert.equal(checked.staleWrite, false)
  })

  for (const [type, stage] of [[1, 'audio-stage'], [2, 'comic-stage'], [3, 'file-stage'], [4, 'video-stage']]) {
    await scenario(`R3 type ${type}: proper media renderer; text export/cache are disabled with explanation`, async () => {
      const url = `https://fixture.invalid/type-${type}`
      await page.goto(`${base}/reader/${encodeURIComponent(url)}`)
      await waitVisible(page.locator(`.${stage}`))
      assert.equal(await page.getByRole('button', { name: '复制本章', exact: true }).count(), 0)
      await page.goto(`${base}/book/${encodeURIComponent(url)}`)
      for (const name of ['缓存', '导出']) {
        const button = page.getByRole('button', { name, exact: true }).first()
        assert.equal(await button.isDisabled(), true)
        assert.match(await button.getAttribute('title'), /不支持|仅支持|文本/)
      }
    })
  }

  await scenario('R3 simple: shelf entry; independent TOC keeps book/source; first directory/source clicks open panels', async () => {
    await page.goto(`${base}/simple/index.html?accessToken=auditA%3Asynthetic`)
    await waitVisible(page.locator('#list a').first())
    assert.ok((await page.locator('#list a').first().getAttribute('href')).includes('bookSource='))
    const query = new URLSearchParams({ bookUrl, tocUrl: `${bookUrl}/toc`, bookSource: source, accessToken: 'auditA:synthetic' })
    await page.goto(`${base}/simple/reader.html?${query}`)
    await waitVisible(page.locator('#content'))
    await page.waitForFunction(() => document.querySelector('#content')?.textContent?.includes('PRIVATE'))
    const request = requests.filter(r => r.endpoint === 'getBookToc').at(-1)
    assert.equal(request.params.url, bookUrl)
    assert.equal(request.params.tocUrl, `${bookUrl}/toc`)
    assert.equal(request.params.bookSource, source)
    await page.locator('#tocBtn').click()
    assert.equal(await page.locator('#tocPanel').isVisible(), true)
    await page.locator('#srcBtn').click()
    assert.equal(await page.locator('#srcPanel').isVisible(), true)
    await waitVisible(page.locator('.src-cur'))
    assert.equal(await page.locator('.src-cur').innerText(), '当前')
    const savesBefore = requests.filter(r => r.endpoint === 'saveBook').length
    await page.locator('#srcList a').filter({ hasText: '验收新书源' }).click()
    await page.waitForURL('**/simple/reader.html?**bookUrl=https%3A%2F%2Ffixture.invalid%2Fsource-b%2Fbook-a**')
    const switched = requests.filter(r => r.endpoint === 'setBookSource').at(-1)
    assert.equal(switched.params.bookUrl, bookUrl)
    assert.equal(switched.params.newUrl, 'https://fixture.invalid/source-b/book-a')
    assert.equal(switched.params.bookSourceUrl, 'https://fixture.invalid/source-b')
    assert.equal(requests.filter(r => r.endpoint === 'saveBook').length, savesBefore, 'switching must use the actual source-change contract')
    await page.screenshot({ path: `${out}/simple-reader.png`, fullPage: true })
  })

  await scenario('R3 simple: update chapters refreshes only the selected book with its source', async () => {
    await page.goto(`${base}/simple/search.html?accessToken=auditA%3Asynthetic`)
    await page.locator('#key').fill('验收之书')
    await page.locator('#go').click()
    await waitVisible(page.locator('#list a').first())
    await page.locator('#list a').first().click()
    await waitVisible(page.locator('#mRefresh'))
    await page.locator('#mRefresh').click()
    await page.waitForFunction(() => document.querySelector('#mStatus')?.textContent?.includes('已更新'))
    const refreshed = requests.filter(r => r.endpoint === 'getBookToc').at(-1)
    assert.equal(refreshed.params.url, bookUrl)
    assert.equal(refreshed.params.bookSource, source)
    assert.equal(refreshed.params.refresh, '1')
    const savesBefore = requests.filter(r => r.endpoint === 'saveBook').length
    await page.locator('#mSource').click()
    await page.locator('#mSources button').filter({ hasText: '验收新书源' }).click()
    await page.waitForFunction(() => document.querySelector('#mStatus')?.textContent?.includes('已切换'))
    const switched = requests.filter(r => r.endpoint === 'setBookSource').at(-1)
    assert.equal(switched.params.bookUrl, bookUrl)
    assert.equal(switched.params.newUrl, 'https://fixture.invalid/source-b/book-a')
    assert.equal(switched.params.bookSourceUrl, 'https://fixture.invalid/source-b')
    assert.equal(requests.filter(r => r.endpoint === 'saveBook').length, savesBefore)
    await page.locator('#mRead').click()
    await page.waitForURL('**/simple/reader.html?**bookUrl=https%3A%2F%2Ffixture.invalid%2Fsource-b%2Fbook-a**')
  })

  await scenario('R3 simple: failed source change preserves current reader URL and visible content', async () => {
    rejectSwitch = true
    const query = new URLSearchParams({ bookUrl, tocUrl: `${bookUrl}/toc`, bookSource: source, accessToken: 'auditA:synthetic' })
    await page.goto(`${base}/simple/reader.html?${query}`)
    await page.waitForFunction(() => document.querySelector('#content')?.textContent?.includes('PRIVATE'))
    const before = page.url()
    await page.locator('#srcBtn').click()
    await page.locator('#srcList a').filter({ hasText: '验收新书源' }).click()
    await page.waitForFunction(() => document.querySelector('#status')?.textContent?.includes('换源失败'))
    assert.equal(page.url(), before)
    assert.match(await page.locator('#content').innerText(), /PRIVATE/)
    rejectSwitch = false
  })

  await scenario('R3 narrow screen: reader controls and current-book export remain reachable without overflow', async () => {
    await page.setViewportSize({ width: 390, height: 844 })
    await page.goto(`${base}/reader/${encodeURIComponent(bookUrl)}`)
    await waitVisible(page.locator('.reader-content'))
    await page.getByRole('button', { name: '导出', exact: true }).click()
    await waitVisible(page.locator('.dlg-export'))
    assert.ok(page.url().includes(encodeURIComponent(bookUrl)))
    const bounds = await page.locator('.dlg-export').boundingBox()
    assert.ok(bounds.x >= 0 && bounds.x + bounds.width <= 390)
    await page.waitForTimeout(250)
    await page.screenshot({ path: `${out}/narrow-export.png`, fullPage: true, animations: 'disabled' })
  })
} finally {
  await writeFile(`${out}/report.json`, JSON.stringify({ target: base, results, pageErrors: errors,
    requests: requests.map(({ endpoint, params, method }) => ({ endpoint, method, params: Object.fromEntries(Object.entries(params).filter(([key]) => key !== 'accessToken')) })) }, null, 2))
  await browser.close()
}
console.log(JSON.stringify({ results, pageErrors: errors, artifacts: out }, null, 2))
if (results.some(r => !r.passed) || errors.length) process.exitCode = 1
