import { test } from 'node:test'
import assert from 'node:assert/strict'
import { registerHooks } from 'node:module'

test('cacheBookSSE preserves failedCount through the actual fetch, stream and event parser', { timeout: 10_000 }, async () => {
  const moduleUrl = new URL('./cacheBook.ts', import.meta.url).href
  // Replace only authentication/request boundaries; the production cacheBook
  // module and shared SSE parser are imported and executed unchanged.
  const hooks = registerHooks({
    resolve(specifier, context, nextResolve) {
      if (context.parentURL === moduleUrl) {
        if (specifier === '@/stores/user') {
          return { shortCircuit: true, url: 'data:text/javascript,export const useUserStore=()=>({accessToken:"fixture-token"})' }
        }
        if (specifier === './request') {
          return { shortCircuit: true, url: 'data:text/javascript,export const get=()=>{throw Error("Unexpected ordinary request")};export const post=get' }
        }
        if (specifier === './sse') {
          return { shortCircuit: true, url: new URL('./sse.ts', import.meta.url).href }
        }
      }
      return nextResolve(specifier, context)
    },
  })
  const originalFetch = globalThis.fetch
  let close: (() => void) | undefined
  try {
    const { cacheBookSSE } = await import('./cacheBook.ts')
    const frames: { failedCount?: number; cached: number; total: number; error?: string | null; finished?: boolean }[] = []
    const errors: string[] = []
    let ended!: () => void
    const done = new Promise<void>((resolve) => { ended = resolve })
    globalThis.fetch = async (input, init) => {
      const url = new URL(String(input), 'http://fixture.invalid')
      assert.equal(url.pathname, '/reader3/cacheBookSSE')
      assert.equal(url.searchParams.get('taskId'), 'opaque/task#one')
      assert.equal(url.searchParams.has('url'), false)
      assert.equal(url.searchParams.get('accessToken'), 'fixture-token')
      assert.ok(init?.signal instanceof AbortSignal)
      const raw = [
        'data: {"cached":0,"total":3,"failedCount":-1}\r\n\r\n',
        'event: progress\r\ndata: {"cached":0,"total":3,"failedCount":1,"error":"fixture chapter write failed","finished":false}\r\n\r\n',
        'data: {"cached":1,"total":3,"failedCount":2,"finished":true}\r\n\r\n',
      ].join('')
      const cut = raw.indexOf('failedCount') + 6
      return new Response(new ReadableStream<Uint8Array>({
        start(controller) {
          controller.enqueue(new TextEncoder().encode(raw.slice(0, cut)))
          controller.enqueue(new TextEncoder().encode(raw.slice(cut)))
          controller.close()
        },
      }), { headers: { 'content-type': 'text/event-stream' } })
    }
    const handle = await cacheBookSSE('opaque/task#one', {
      onProgress: (frame) => frames.push(frame),
      onEnd: ended,
      onStreamError: (message) => { errors.push(message); ended() },
    }, true)
    close = handle.close
    await done
    assert.deepEqual(errors, [])
    assert.deepEqual(frames.map((frame) => frame.failedCount), [0, 1, 2])
    assert.equal(frames[1].finished, false)
    assert.equal(frames[1].error, 'fixture chapter write failed')
    assert.equal(frames[2].cached, 1)
    assert.equal(frames[2].total, 3)
    assert.equal(frames[2].finished, true)
  } finally {
    close?.()
    globalThis.fetch = originalFetch
    hooks.deregister()
  }
})
