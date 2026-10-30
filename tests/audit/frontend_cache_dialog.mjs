// Independent interaction tests against the actual Vue SFC script, with controlled
// transport/storage boundaries. No browser or application database is accessed.
// Run: PATH=/root/.nvm/versions/node/v22.23.4/bin:$PATH node --test tests/audit/frontend_cache_dialog.mjs
// Compare the original source: AUDIT_BASELINE=7efd557 <same command>
import assert from 'node:assert/strict'
import { test } from 'node:test'
import { readFileSync } from 'node:fs'
import { execFileSync } from 'node:child_process'
import { createRequire } from 'node:module'
import { fileURLToPath } from 'node:url'

const root = fileURLToPath(new URL('../../', import.meta.url))
const requireWeb = createRequire(`${root}/web-ui/package.json`)
const { parse, compileScript } = requireWeb('@vue/compiler-sfc')
const ts = requireWeb('typescript')
const vue = requireWeb('vue')
const tick = () => new Promise(resolve => setImmediate(resolve))
const deferred = () => {
  let resolve, reject
  const promise = new Promise((yes, no) => { resolve = yes; reject = no })
  return { promise, resolve, reject }
}
const chapter = i => ({ title: `第${i + 1}章`, url: `https://fixture.invalid/ch/${i}`, index: i, isVolume: false })

function instantiate(relative, mocks = {}, overrides = {}) {
  const source = process.env.AUDIT_BASELINE
    ? execFileSync('git', ['show', `${process.env.AUDIT_BASELINE}:${relative}`], { cwd: root, encoding: 'utf8' })
    : readFileSync(`${root}/${relative}`, 'utf8')
  const { descriptor } = parse(source)
  const compiled = compileScript(descriptor, { id: 'independent-audit' })
  const code = ts.transpileModule(compiled.content, { compilerOptions: {
    module: ts.ModuleKind.CommonJS, target: ts.ScriptTarget.ES2022,
  } }).outputText
  const unmount = []
  const mount = []
  const modules = {
    vue: { ...vue, onBeforeUnmount: cb => unmount.push(cb), onMounted: cb => mount.push(cb) },
    'element-plus': { ElMessage: { warning() {}, error() {}, success() {}, info() {} }, ElMessageBox: {} },
    ...mocks,
  }
  const exports = {}
  new Function('require', 'exports', 'module', code)(name => {
    if (name in modules) return modules[name]
    if (name.endsWith('.vue')) return {}
    return new Proxy({}, { get: () => () => {} })
  }, exports, { exports })
  const events = []
  const props = vue.reactive({ modelValue: true, bookUrl: 'https://fixture.invalid/book/a',
    bookName: '验收书', chapters: [chapter(0), chapter(1)], origin: 'fixture-source',
    defaultFrom: 1, defaultScope: 'all', allowServer: true, ...overrides })
  globalThis.document = { body: { style: { overflow: '' } } }
  const state = exports.default.setup(props, { expose() {}, emit: (...args) => events.push(args) })
  return { state, props, events, unmount, mount, source }
}

function dialog(options = {}) {
  const calls = { start: [], cancel: [], subscribe: [], saves: [], closed: 0 }
  const mocks = {
    '@/api/books': { getBookContent: async () => ({ isSuccess: true, data: { content: '正文' } }), ...options.books },
    '@/api/cacheBook': {
      cacheBookRangeOnServer: (...args) => { calls.start.push(args); return options.start?.(...args) ?? Promise.resolve({ isSuccess: true, data: { taskId: 'task-a', cached: 0, total: 2 } }) },
      cacheBookSSE: (...args) => { calls.subscribe.push(args); return options.subscribe?.(...args) ?? Promise.resolve({ close: () => calls.closed++ }) },
      cancelCacheBook: (...args) => { calls.cancel.push(args); return Promise.resolve({ isSuccess: true }) },
    },
    '@/utils/readerLocalCache': { localCacheOwner: () => 'fixture-owner', saveLocalChapter: async data => { calls.saves.push(data); return options.save ? options.save(data) : (options.saveResult ?? true) } },
  }
  return { ...instantiate('web-ui/src/components/ChapterCacheDialog.vue', mocks, options.props), calls }
}

test('R1: cancelling a delayed start cancels its eventual taskId and never subscribes', async () => {
  const pending = deferred()
  const { state, calls, events } = dialog({ start: () => pending.promise })
  state.start()
  state.cancel()
  pending.resolve({ isSuccess: true, data: { taskId: 'late-task', cached: 0, total: 2 } })
  await tick()
  assert.ok(calls.cancel.some(([key, isId]) => key === 'late-task' && isId === true), 'late server task must be cancelled by exact taskId')
  assert.equal(calls.subscribe.length, 0)
  assert.equal(state.busy.value, false)
  assert.equal(events.filter(([name]) => name === 'done').length, 0)
})

test('R1: cancelling established stream uses exact taskId, not book URL', async () => {
  const { state, calls } = dialog()
  state.start()
  await tick()
  state.cancel()
  assert.deepEqual(calls.cancel, [['task-a', true]])
  assert.equal(calls.closed, 1)
})

test('R1: cancelling during SSE handshake closes the late handle and ignores late completion', async () => {
  const handshake = deferred()
  const { state, calls, events } = dialog({ subscribe: () => handshake.promise })
  state.start()
  await tick()
  state.cancel()
  handshake.resolve({ close: () => calls.closed++ })
  await tick()
  calls.subscribe[0][1].onProgress({ cached: 2, total: 2, finished: true })
  assert.ok(calls.cancel.some(([key, isId]) => key === 'task-a' && isId === true))
  assert.equal(calls.closed, 1)
  assert.equal(events.filter(([name]) => name === 'done').length, 0)
  assert.equal(state.finished.value, false)
})

test('R1: local storage rejecting writes cannot count successfully cached chapters', async () => {
  const { state, events, calls } = dialog({ saveResult: false })
  state.direction.value = 'local'
  state.start()
  await tick()
  assert.equal(calls.saves.length, 2)
  assert.equal(state.cached.value, 0)
  assert.ok(events.every(([name, payload]) => name !== 'done' || payload.saved === 0))
})

test('R1: normal EOF without a finished frame must not report successful caching', async () => {
  const { state, calls, events } = dialog()
  state.start()
  await tick()
  calls.subscribe[0][1].onProgress({ cached: 1, total: 2 })
  calls.subscribe[0][1].onEnd()
  assert.equal(state.finished.value, false)
  assert.equal(events.filter(([name]) => name === 'done').length, 0)
})

test('R1: details cache button loads directory before opening without visiting directory tab', async () => {
  let tocCalls = 0
  const chapters = [chapter(0), { ...chapter(99), isVolume: true }, chapter(1)]
  const { state } = instantiate('web-ui/src/views/BookDetailView.vue', {
    'vue-router': { useRoute: () => ({ params: { url: 'https://fixture.invalid/book/a' }, query: { origin: 'fixture-source' } }), useRouter: () => ({ push() {} }) },
    '@/stores/user': { useUserStore: () => ({ accessToken: '' }) },
    '@/api/books': { getBookToc: async () => { tocCalls++; return { isSuccess: true, data: chapters } } },
  })
  assert.equal(state.activeTab.value, 'detail')
  await state.openCacheDialog()
  assert.equal(tocCalls, 1)
  assert.equal(state.cacheOpen.value, true)
  assert.equal(state.realTocChapters.value.length, 2)
})

test('R1 positive: range selection uses zero-based real chapter positions and emits exact scope', async () => {
  const { state, calls, events } = dialog({ props: { chapters: Array.from({ length: 5 }, (_, i) => chapter(i)) } })
  state.scope.value = 'range'
  state.rangeFrom.value = 2
  state.rangeTo.value = 4
  state.start()
  await tick()
  assert.deepEqual(calls.start[0], ['https://fixture.invalid/book/a', 1, 3])
  calls.subscribe[0][1].onProgress({ cached: 3, total: 3, finished: true })
  assert.deepEqual(events.find(([name]) => name === 'done'), ['done', { direction: 'server', from: 2, to: 4, saved: 3 }])
})

test('R2: an old task callback cannot finish a newly started operation', async () => {
  let nextId = 0
  const { state, calls, events } = dialog({ start: async () => ({ isSuccess: true, data: { taskId: `task-${++nextId}`, cached: 0, total: 2 } }) })
  state.start()
  await tick()
  state.cancel()
  state.start()
  await tick()
  calls.subscribe[0][1].onProgress({ cached: 2, total: 2, finished: true })
  assert.equal(state.busy.value, true)
  assert.equal(state.cached.value, 0)
  assert.equal(events.filter(([name]) => name === 'done').length, 0)
  calls.subscribe[1][1].onProgress({ cached: 2, total: 2, finished: true })
  assert.equal(events.filter(([name]) => name === 'done').length, 1)
})

test('R2: changing books during a delayed start cancels only the original operation', async () => {
  const pending = deferred()
  const { state, props, calls } = dialog({ start: () => pending.promise })
  state.start()
  props.bookUrl = 'https://fixture.invalid/book/b'
  await vue.nextTick()
  pending.resolve({ isSuccess: true, data: { taskId: 'book-a-task', cached: 0, total: 2 } })
  await tick()
  assert.deepEqual(calls.cancel, [['book-a-task', true]])
  assert.equal(calls.subscribe.length, 0)
  assert.equal(state.busy.value, false)
})

test('R2: unmount cancels active task and late completion does not emit done', async () => {
  const { state, calls, unmount, events } = dialog()
  state.start()
  await tick()
  unmount.forEach(callback => callback())
  calls.subscribe[0][1].onProgress({ cached: 2, total: 2, finished: true })
  assert.deepEqual(calls.cancel, [['task-a', true]])
  assert.equal(calls.closed, 1)
  assert.equal(events.filter(([name]) => name === 'done').length, 0)
})

test('R2: cancellation during local content download prevents later IndexedDB writes', async () => {
  const pending = deferred()
  const { state, calls, events } = dialog({ books: { getBookContent: () => pending.promise } })
  state.direction.value = 'local'
  state.start()
  state.cancel()
  pending.resolve({ isSuccess: true, data: { content: 'late body' } })
  await tick()
  assert.equal(calls.saves.length, 0)
  assert.equal(events.filter(([name]) => name === 'done').length, 0)
})

test('R2: a mixed local batch reports only durable chapters and retains a failure indication', async () => {
  const { state, events } = dialog({ save: data => data.index === 0 })
  state.direction.value = 'local'
  state.start()
  await tick()
  assert.equal(state.cached.value, 1)
  assert.equal(state.finished.value, false)
  assert.equal(state.msgError.value, true)
  assert.deepEqual(events.find(([name]) => name === 'done'), ['done', { direction: 'local', from: 1, to: 2, saved: 1 }])
})

test('R2: failed stream reconnection ignores callbacks from the previous connection', async () => {
  const { state, calls } = dialog()
  state.start()
  await tick()
  calls.subscribe[0][1].onStreamError('fixture disconnect')
  assert.equal(state.streamDropped.value, true)
  await state.resubscribeProgress()
  calls.subscribe[0][1].onProgress({ cached: 2, total: 2, finished: true })
  assert.equal(state.busy.value, true)
  calls.subscribe[1][1].onProgress({ cached: 2, total: 2, finished: true })
  assert.equal(state.finished.value, true)
})

function searchView(options = {}) {
  globalThis.localStorage = { getItem: () => null, setItem() {}, removeItem() {} }
  globalThis.window = { addEventListener() {}, removeEventListener() {}, setTimeout, clearTimeout }
  const calls = { stream: [], batch: [], abort: 0 }
  const instance = instantiate('web-ui/src/views/SearchView.vue', {
    'vue-router': { useRoute: () => ({ params: {}, query: {} }), useRouter: () => ({ push() {}, replace() {} }) },
    '@/stores/user': { useUserStore: () => ({ accessToken: '', clear() {} }) },
    '@/api/sources': { getBookSources: async () => ({ isSuccess: true, data: [] }) },
    '@/api/bookshelf': { getBookshelf: async () => ({ isSuccess: true, data: [] }) },
    '@/api/search': {
      searchBookMultiSSE: (...args) => { calls.stream.push(args); return options.stream ? options.stream(...args) : Promise.resolve({ abort: () => calls.abort++ }) },
      searchBookMulti: (...args) => { calls.batch.push(args); return options.batch ? options.batch(...args) : Promise.resolve({ isSuccess: true, data: [] }) },
    },
    '@/utils/searchHistory': { loadSearchHistory: () => [], pushSearchHistory: word => [word], clearSearchHistory() {} },
  })
  return { ...instance, calls }
}

test('R2: SSE handshake failure preserves selected single source and group in batch fallback', async () => {
  const { state, calls } = searchView({ stream: async () => { throw new Error('fixture unsupported SSE') } })
  state.singleSourceUrl.value = 'https://fixture.invalid/source/only'
  state.activeSearchGroup.value = '验收分组'
  state.exact.value = true
  await state.doSearch('特殊&关键词')
  assert.equal(calls.batch.length, 1)
  assert.equal(calls.batch[0][0], '特殊&关键词')
  assert.equal(calls.batch[0][4], true)
  assert.equal(calls.batch[0][5], '验收分组')
  assert.equal(calls.batch[0][6], 'https://fixture.invalid/source/only')
})

test('R2: a partial SSE source window remains resumable even when no books were found', async () => {
  const { state, calls } = searchView()
  await state.doSearch('fixture')
  calls.stream[0][1].onEnd(49, false)
  assert.equal(state.searching.value, false)
  await state.loadMore()
  assert.equal(calls.stream.length, 2, 'partial source window must be continued')
  assert.equal(calls.stream[1][0].lastIndex, 49)
})

test('R2: stopping during search handshake aborts late handle and ignores old results', async () => {
  const pending = deferred()
  const { state, calls } = searchView({ stream: () => pending.promise })
  const running = state.doSearch('original')
  state.stopSearch()
  calls.stream[0][1].onBooks(0, [{ bookUrl: 'https://fixture.invalid/late', origin: 'late', name: 'late' }])
  pending.resolve({ abort: () => calls.abort++ })
  await running
  assert.equal(calls.abort, 1)
  assert.equal(state.results.value.length, 0)
  assert.equal(state.stopped.value, true)
})

test('R3: resuming a source window freezes original keyword, group, source, exact and concurrency', async () => {
  const { state, calls } = searchView()
  state.singleSourceUrl.value = 'original-source'
  state.activeSearchGroup.value = 'original-group'
  state.exact.value = true
  state.searchConcurrent.value = 8
  await state.doSearch('original-keyword')
  calls.stream[0][1].onEnd(49, false)
  state.key.value = 'edited input'
  state.singleSourceUrl.value = 'edited-source'
  state.activeSearchGroup.value = 'edited-group'
  state.exact.value = false
  state.searchConcurrent.value = 64
  await state.loadMore()
  assert.deepEqual(calls.stream[1][0], { key: 'original-keyword', bookSourceGroup: 'original-group',
    bookSourceUrl: 'original-source', lastIndex: 49, searchSize: 50, concurrentCount: 8, exact: true })
})

test('R3: duplicate batch result page does not hide later pages and keeps original source scope', async () => {
  const found = { bookUrl: 'https://fixture.invalid/same', origin: 'original-source', name: 'duplicate' }
  const { state, calls } = searchView({ stream: async () => { throw new Error('fixture fallback') },
    batch: async (_key, _max, _signal, page) => ({ isSuccess: true, data: page <= 2 ? [found] : [] }) })
  state.singleSourceUrl.value = 'original-source'
  await state.doSearch('original-keyword')
  state.key.value = 'edited input'
  state.singleSourceUrl.value = 'edited-source'
  await state.loadMore()
  assert.equal(state.batchExhausted.value, false, 'a duplicate page can precede additional results')
  assert.equal(calls.batch[1][0], 'original-keyword')
  assert.equal(calls.batch[1][6], 'original-source')
  assert.equal(calls.batch[1][1], undefined, 'all selected sources must be eligible in fallback')
  await state.loadMore()
  assert.equal(state.batchExhausted.value, true)
  assert.equal(calls.batch[2][3], 3)
})

test('Follow-up: a nonterminal cache error keeps the exact active task cancellable', async () => {
  const { state, calls, events } = dialog()
  state.start()
  await tick()
  calls.subscribe[0][1].onProgress({ cached: 0, total: 2, failedCount: 1,
    error: '写入章节缓存失败：fixture', finished: false })
  assert.equal(state.busy.value, true, 'a failed chapter does not finish the server task')
  assert.equal(state.finished.value, false)
  assert.equal(state.msgError.value, true)
  assert.match(state.msg.value, /失败/)
  assert.equal(events.filter(([name]) => name === 'done').length, 0)
  state.cancel()
  assert.deepEqual(calls.cancel, [['task-a', true]], 'the task must remain cancellable by its returned ID')
})

test('Follow-up: partial server completion shows failed chapters without claiming full success', async () => {
  const { state, calls, events } = dialog()
  state.start()
  await tick()
  calls.subscribe[0][1].onProgress({ cached: 1, total: 2, failedCount: 1, finished: true })
  assert.equal(state.busy.value, false)
  assert.equal(state.finished.value, false, 'partial persistence must not appear fully successful')
  assert.equal(state.msgError.value, true)
  assert.match(state.msg.value, /1.*失败|失败.*1/)
  assert.deepEqual(events.filter(([name]) => name === 'done').map(([, payload]) => payload),
    [{ direction: 'server', from: 1, to: 2, saved: 1 }])
  const warning = state.msg.value
  calls.subscribe[0][1].onEnd()
  assert.equal(state.msg.value, warning, 'EOF must not overwrite the partial failure warning')
})
