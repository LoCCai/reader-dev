<script setup lang="ts">
import { computed, onBeforeUnmount, ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { getBookContent } from '@/api/books'
import { cacheBookRangeOnServer, cacheBookSSE, cancelCacheBook } from '@/api/cacheBook'
import { localCacheOwner, saveLocalChapter } from '@/utils/readerLocalCache'
import type { BookChapter } from '@/types'

interface Props {
  modelValue: boolean
  bookUrl: string
  bookName?: string
  /** 目录实章（已过滤卷标题行；顺序与后端 range 0 基一致） */
  chapters: BookChapter[]
  /** 书源 origin（拉取到本机时逐章 getBookContent 需要） */
  origin?: string
  /** 默认起始章（1 基；目录页单章缓存传入） */
  defaultFrom?: number
  /** 默认范围：目录单章 → chapter；阅读页/详情页 → all */
  defaultScope?: 'chapter' | 'rest' | 'all' | 'range'
  /** 服务端方向是否可用（服务端缓存要求书已加入书架） */
  allowServer?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  bookName: '',
  origin: '',
  defaultFrom: 1,
  defaultScope: 'all',
  allowServer: true,
})

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
  (e: 'done', payload: { direction: 'server' | 'local'; from: number; to: number; saved: number }): void
}>()

type CacheDirection = 'server' | 'local'
type CacheScope = 'chapter' | 'rest' | 'all' | 'range'

const direction = ref<CacheDirection>('server')
const scope = ref<CacheScope>('all')
const rangeFrom = ref(1)
const rangeTo = ref(1)
const busy = ref(false)
const finished = ref(false)
const cached = ref(0)
const total = ref(0)
const msg = ref('')
const msgError = ref(false)
interface CacheRun {
  bookUrl: string
  origin: string
  owner: string
  from: number
  to: number
  cancelled: boolean
  terminal: boolean
  taskKey: string
  taskKeyIsId: boolean
  cancelSent: boolean
  connection: number
  handle: { close: () => void } | null
}
let activeRun: CacheRun | null = null
/** 进度连接中断（任务仍在服务端后台跑）：显示「重新连接进度」入口 */
const streamDropped = ref(false)

const chapterCount = computed(() => props.chapters.length)
const title = computed(() => props.bookName || '本书')

const from = computed(() => {
  if (scope.value === 'range') return Math.max(1, Math.min(rangeFrom.value, chapterCount.value || 1))
  if (scope.value === 'chapter' || scope.value === 'rest') {
    return Math.max(1, Math.min(props.defaultFrom, chapterCount.value || 1))
  }
  return 1
})

const to = computed(() => {
  const n = chapterCount.value || 1
  if (scope.value === 'range') return Math.max(from.value, Math.min(rangeTo.value, n))
  if (scope.value === 'chapter') return from.value
  return n
})

const percent = computed(() => {
  if (total.value <= 0) return finished.value ? 100 : 0
  return Math.min(100, Math.round((cached.value / total.value) * 100))
})

const serverDisabled = computed(() => direction.value === 'server' && !props.allowServer)

function reset() {
  stopRun()
  direction.value = props.allowServer ? 'server' : 'local'
  scope.value = props.defaultScope
  rangeFrom.value = props.defaultFrom
  rangeTo.value = chapterCount.value || 1
  busy.value = false
  finished.value = false
  cached.value = 0
  total.value = 0
  msg.value = ''
  msgError.value = false
  streamDropped.value = false
}

function close() {
  if (busy.value) cancel()
  emit('update:modelValue', false)
}

watch(
  () => props.modelValue,
  (open) => {
    if (open) {
      reset()
      document.body.style.overflow = 'hidden'
    } else {
      stopRun()
      document.body.style.overflow = ''
    }
  },
)

watch(() => props.bookUrl, () => {
  stopRun()
  busy.value = false
  emit('update:modelValue', false)
})

onBeforeUnmount(() => {
  stopRun()
  document.body.style.overflow = ''
})

function isActive(run: CacheRun): boolean {
  return activeRun === run && !run.cancelled && !run.terminal
}

function cancelServerRun(run: CacheRun) {
  if (!run.taskKey || run.cancelSent || run.terminal) return
  run.cancelSent = true
  void cancelCacheBook(run.taskKey, run.taskKeyIsId).catch(() => {
    if (activeRun === run && props.modelValue) {
      msg.value = '取消请求失败，请稍后检查服务器缓存任务'
      msgError.value = true
    }
  })
}

function stopRun() {
  const run = activeRun
  if (!run) return
  run.cancelled = true
  run.connection++
  run.handle?.close()
  run.handle = null
  cancelServerRun(run)
}

function fail(text: string) {
  busy.value = false
  msg.value = text
  msgError.value = true
}

function start() {
  if (busy.value || chapterCount.value === 0) return
  if (serverDisabled.value) {
    ElMessage.warning('服务端缓存需要先把书加入书架')
    return
  }
  busy.value = true
  finished.value = false
  msg.value = ''
  msgError.value = false
  streamDropped.value = false
  const f = from.value
  const t = to.value
  stopRun()
  const run: CacheRun = {
    bookUrl: props.bookUrl, origin: props.origin, owner: localCacheOwner(), from: f, to: t,
    cancelled: false, terminal: false, taskKey: '', taskKeyIsId: false,
    cancelSent: false, connection: 0, handle: null,
  }
  activeRun = run
  if (direction.value === 'server') void startServer(run)
  else void startLocal(run)
}

async function startServer(run: CacheRun) {
  try {
    const res = await cacheBookRangeOnServer(run.bookUrl, run.from - 1, run.to - 1)
    if (!res.isSuccess) throw new Error(res.errorMsg || '缓存启动失败')
    const start = res.data
    const taskId = start?.taskId
    run.taskKey = taskId || run.bookUrl
    run.taskKeyIsId = !!taskId
    // The POST may finish after cancellation or after another book opens.
    if (!isActive(run)) {
      cancelServerRun(run)
      return
    }
    cached.value = start?.cached ?? 0
    total.value = start?.total ?? run.to - run.from + 1
    await subscribeProgress(run)
  } catch (err) {
    if (!isActive(run)) return
    if (run.taskKey) {
      msg.value = '进度连接失败，任务仍在后台运行，可重新连接进度'
      streamDropped.value = true
    } else {
      run.terminal = true
      fail(`缓存失败：${err instanceof Error ? err.message : '请稍后重试'}`)
    }
  }
}

/** 订阅服务端进度流（启动与断线重订阅共用；cacheBookSSE 支持 taskId 精确订阅） */
async function subscribeProgress(run: CacheRun): Promise<void> {
  const connection = ++run.connection
  const current = () => isActive(run) && run.connection === connection
  const handle = await cacheBookSSE(run.taskKey, {
    onProgress: (p) => {
      if (!current()) return
      // 收到进度帧即视为连接恢复
      streamDropped.value = false
      if (typeof p.cached === 'number') cached.value = p.cached
      if (typeof p.total === 'number') total.value = p.total
      if (p.cancelled) {
        run.terminal = true
        busy.value = false
        msg.value = '缓存已取消'
      } else if (p.error) {
        run.terminal = true
        fail(`缓存失败：${p.error}`)
      } else if (p.finished) {
        run.terminal = true
        busy.value = false
        finished.value = true
        msg.value = `已缓存到服务器（${cached.value}/${total.value} 章）`
        emit('done', { direction: 'server', from: run.from, to: run.to, saved: cached.value })
      }
    },
    onEnd: () => {
      if (!current()) return
      // EOF only describes the connection; completion requires a finished frame.
      msg.value = '进度连接已结束，尚未确认任务完成，请重新连接进度'
      msgError.value = false
      streamDropped.value = true
    },
    onStreamError: (m) => {
      // SSE 断线 ≠ 缓存失败：任务仍在服务端后台运行——中性提示并提供 taskId 重订阅入口
      if (!current()) return
      msg.value = `进度连接中断（${m}），任务仍在后台运行`
      msgError.value = false
      streamDropped.value = true
    },
  }, run.taskKeyIsId)
  if (!current()) handle.close()
  else run.handle = handle
}

/** 断线后重新订阅进度（key=taskId 优先，cacheBook.ts 契约） */
async function resubscribeProgress() {
  const run = activeRun
  if (!run || !isActive(run) || !run.taskKey || !busy.value) return
  msg.value = '正在重新连接进度…'
  msgError.value = false
  try {
    run.handle?.close()
    await subscribeProgress(run)
  } catch {
    if (!isActive(run)) return
    // 重连失败：任务仍在后台，保留重试入口
    msg.value = '重连进度失败，任务仍在后台运行，可再试'
  }
}

async function startLocal(run: CacheRun) {
  if (!run.origin) {
    run.terminal = true
    fail('缺少书源信息，无法拉取正文')
    return
  }
  const selected = props.chapters.slice(run.from - 1, run.to).map((ch, i) => ({
    ch,
    index: run.from - 1 + i,
  }))
  total.value = selected.length
  cached.value = 0
  let cursor = 0
  let saved = 0
  const worker = async () => {
    while (isActive(run)) {
      const item = selected[cursor++]
      if (!item) return
      try {
        const res = await getBookContent(item.ch.url, run.origin, { bookUrl: run.bookUrl })
        if (!isActive(run)) return
        const text = res.data?.content ?? ''
        if (res.isSuccess && text) {
          const stored = await saveLocalChapter({
            bookUrl: run.bookUrl,
            owner: run.owner,
            chapterUrl: item.ch.url,
            title: item.ch.title,
            index: item.index,
            content: text,
          })
          if (!isActive(run)) return
          if (stored) saved++
        }
      } catch {
        // 单章失败不中断整批（与服务器任务口径一致）
      }
      if (isActive(run)) cached.value = saved
    }
  }
  await Promise.all(Array.from({ length: Math.min(3, selected.length) }, () => worker()))
  if (!isActive(run)) return
  run.terminal = true
  busy.value = false
  const failed = selected.length - saved
  finished.value = failed === 0
  msgError.value = failed > 0
  msg.value = `已保存到本机缓存 ${saved}/${selected.length} 章${failed ? `，${failed} 章失败（正文获取或本机存储失败）` : ''}`
  if (saved > 0) emit('done', { direction: 'local', from: run.from, to: run.to, saved })
}

function cancel() {
  if (!busy.value) return
  stopRun()
  busy.value = false
  msg.value = '已取消'
  msgError.value = false
}
</script>

<template>
  <Teleport to="body">
    <Transition name="dlg">
      <div v-if="modelValue" class="dlg-overlay" @click.self="close">
        <div class="dlg dlg-cache" role="dialog" aria-modal="true" aria-label="章节缓存" tabindex="-1">
          <div class="dlg-head">
            <h2 class="dlg-title">缓存 · {{ title }}</h2>
            <button class="dlg-close" type="button" title="关闭" @click="close">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linecap="round">
                <path d="M6 6l12 12M18 6L6 18" />
              </svg>
            </button>
          </div>

          <div class="cache-field">
            <span class="field-label">方向</span>
            <div class="seg">
              <button
                class="seg-btn"
                :class="{ active: direction === 'server' }"
                type="button"
                :disabled="busy || !allowServer"
                :title="allowServer ? '' : '服务端缓存需要先把书加入书架'"
                @click="direction = 'server'"
              >
                缓存到服务器
              </button>
              <button
                class="seg-btn"
                :class="{ active: direction === 'local' }"
                type="button"
                :disabled="busy || !origin"
                :title="origin ? '' : '缺少书源信息，无法拉取正文'"
                @click="direction = 'local'"
              >
                拉取到本机
              </button>
            </div>
          </div>

          <div class="cache-field">
            <span class="field-label">范围</span>
            <div class="seg">
              <button
                class="seg-btn"
                :class="{ active: scope === 'chapter' }"
                type="button"
                :disabled="busy"
                @click="scope = 'chapter'"
              >
                当前章
              </button>
              <button
                class="seg-btn"
                :class="{ active: scope === 'rest' }"
                type="button"
                :disabled="busy"
                @click="scope = 'rest'"
              >
                至末尾
              </button>
              <button
                class="seg-btn"
                :class="{ active: scope === 'all' }"
                type="button"
                :disabled="busy"
                @click="scope = 'all'"
              >
                全本
              </button>
              <button
                class="seg-btn"
                :class="{ active: scope === 'range' }"
                type="button"
                :disabled="busy"
                @click="scope = 'range'"
              >
                指定范围
              </button>
            </div>
          </div>

          <div v-if="scope === 'range'" class="range-row">
            <input
              v-model.number="rangeFrom"
              class="range-input"
              type="number"
              min="1"
              :max="chapterCount || 1"
              :disabled="busy"
              aria-label="起始章"
            />
            <span class="range-sep">至</span>
            <input
              v-model.number="rangeTo"
              class="range-input"
              type="number"
              min="1"
              :max="chapterCount || 1"
              :disabled="busy"
              aria-label="结束章"
            />
            <span class="range-total">共 {{ chapterCount }} 章</span>
          </div>

          <div v-if="busy || finished" class="cache-progress">
            <div class="cache-bar">
              <div class="cache-fill" :style="{ width: percent + '%' }"></div>
            </div>
            <span class="cache-percent">
              {{ busy ? `${cached} / ${total}` : finished ? '完成' : `${percent}%` }}
            </span>
          </div>
          <p v-if="msg" class="search-msg" :class="{ error: msgError }">{{ msg }}</p>

          <div class="dlg-actions">
            <template v-if="busy">
              <!-- SSE 断线但任务仍在后台：用 taskId 重新订阅进度 -->
              <button v-if="streamDropped" class="ghost-btn" type="button" @click="resubscribeProgress">
                重新连接进度
              </button>
              <button class="ghost-btn" type="button" @click="cancel">取消</button>
            </template>
            <template v-else>
              <button class="ghost-btn" type="button" @click="close">关闭</button>
              <button
                class="accent-btn"
                type="button"
                :disabled="serverDisabled || chapterCount === 0"
                @click="start"
              >
                {{ direction === 'server' ? '缓存到服务器' : '拉取到本机' }}
              </button>
            </template>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<style scoped>
.dlg-overlay {
  position: fixed;
  inset: 0;
  z-index: 100;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
  background: rgba(24, 24, 27, 0.35);
}
.dlg {
  width: min(440px, 100%);
  max-height: min(560px, 86vh);
  display: flex;
  flex-direction: column;
  padding: 20px;
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: var(--radius);
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.08);
  outline: none;
}
.dlg-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 16px;
}
.dlg-title {
  margin: 0;
  font-size: 15px;
  font-weight: 400;
  letter-spacing: 1px;
  color: var(--text-1);
}
.dlg-close {
  width: 26px;
  height: 26px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  border-radius: 6px;
  background: none;
  color: var(--text-3);
  cursor: pointer;
  transition:
    color 0.2s ease,
    background-color 0.2s ease;
}
.dlg-close:hover {
  color: var(--text-1);
  background: var(--hover);
}
.dlg-close svg {
  width: 13px;
  height: 13px;
}
.cache-field {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 12px;
}
.field-label {
  flex-shrink: 0;
  min-width: 34px;
  font-size: 12px;
  font-weight: 300;
  letter-spacing: 1px;
  color: var(--text-3);
}
.seg {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}
.seg-btn {
  padding: 5px 10px;
  border: 1px solid var(--border);
  border-radius: var(--radius);
  background: none;
  color: var(--text-2);
  font-family: inherit;
  font-size: 12px;
  letter-spacing: 0.5px;
  cursor: pointer;
  transition:
    color 0.2s ease,
    border-color 0.2s ease,
    background-color 0.2s ease;
}
.seg-btn:hover:not(:disabled) {
  color: var(--accent);
  border-color: var(--accent);
}
.seg-btn.active {
  color: var(--on-accent);
  border-color: var(--accent);
  background: var(--accent);
}
.seg-btn:disabled {
  cursor: not-allowed;
  opacity: 0.45;
}
.range-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: -2px 0 12px 44px;
}
.range-input {
  width: 76px;
  height: 30px;
  padding: 0 8px;
  border-radius: var(--radius);
  border: 1px solid var(--border);
  background: var(--bg);
  color: var(--text-1);
  font-family: inherit;
  font-size: 12.5px;
  outline: none;
}
.range-input:focus {
  border-color: var(--accent);
}
.range-sep {
  font-size: 12px;
  color: var(--text-3);
}
.range-total {
  font-size: 11px;
  color: var(--text-3);
}
.cache-progress {
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 12px 0 2px;
}
.cache-bar {
  flex: 1;
  height: 5px;
  border-radius: 999px;
  background: var(--hover);
  overflow: hidden;
}
.cache-fill {
  height: 100%;
  border-radius: 999px;
  background: var(--accent);
  transition: width 0.3s ease;
}
.cache-percent {
  flex-shrink: 0;
  min-width: 56px;
  text-align: right;
  font-size: 11.5px;
  font-weight: 300;
  color: var(--text-3);
  font-variant-numeric: tabular-nums;
}
.search-msg {
  margin: 8px 0 0;
  font-size: 12px;
  color: var(--text-2);
}
.search-msg.error {
  color: var(--danger, #d33);
}
.dlg-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 14px;
}
.ghost-btn {
  padding: 7px 18px;
  border-radius: var(--radius);
  border: 1px solid var(--border-strong);
  background: none;
  color: var(--text-2);
  font-family: inherit;
  font-size: 12.5px;
  font-weight: 400;
  letter-spacing: 1px;
  cursor: pointer;
  transition:
    color 0.2s ease,
    border-color 0.2s ease;
}
.ghost-btn:hover:not(:disabled) {
  color: var(--accent);
  border-color: var(--accent);
}
.ghost-btn:disabled,
.accent-btn:disabled {
  cursor: not-allowed;
  opacity: 0.45;
}
.accent-btn {
  padding: 7px 18px;
  border-radius: var(--radius);
  border: 1px solid var(--accent);
  background: var(--accent);
  color: var(--on-accent);
  font-family: inherit;
  font-size: 12.5px;
  font-weight: 400;
  letter-spacing: 1px;
  cursor: pointer;
  transition:
    background-color 0.2s ease,
    border-color 0.2s ease;
}
.accent-btn:hover:not(:disabled) {
  background: var(--accent-deep);
  border-color: var(--accent-deep);
}
.dlg-enter-active,
.dlg-leave-active {
  transition: opacity 0.18s ease;
}
.dlg-enter-active .dlg,
.dlg-leave-active .dlg {
  transition: transform 0.18s ease;
}
.dlg-enter-from,
.dlg-leave-to {
  opacity: 0;
}
.dlg-enter-from .dlg,
.dlg-leave-to .dlg {
  transform: translateY(8px) scale(0.98);
}
</style>
