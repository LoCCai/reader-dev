import { get, post } from './request'
import { useUserStore } from '@/stores/user'
import { openSSEPost } from './sse'
import type { Book, BookChapter, BookContent, BookInfo, ReturnData, SearchBook } from '@/types'

/** GET /reader3/getBookInfo：书籍详情（url + bookSource=origin；cover/name 为回退值——
 *  源详情规则未配置对应字段时后端沿用（legacy BookInfo 合并语义）） */
export function getBookInfo(
  url: string,
  bookSource: string,
  opts?: { silent?: boolean; cover?: string; name?: string },
): Promise<ReturnData<BookInfo>> {
  return get<BookInfo>('/getBookInfo', { url, bookSource, cover: opts?.cover, name: opts?.name }, opts)
}

/**
 * GET /reader3/searchBookSource：换源搜索——按 url（当前书 bookUrl）+ bookSource（当前源）
 * 搜索同书的其他书源，返回 SearchBook[]（每项含新源 origin/originName/tocUrl）。
 * 后端并行实现中（可能 404）：调用方传 { silent: true } 自行降级提示。
 */
export function searchBookSource(
  url: string,
  bookSource: string,
  opts?: { silent?: boolean },
): Promise<ReturnData<SearchBook[]>> {
  return get<SearchBook[]>('/searchBookSource', { url, bookSource }, opts)
}

/**
 * GET /reader3/getAvailableBookSource：本书换源候选（服务端按 书名+作者 精确搜索并
 * 持久化到 book_source_candidates；refresh=0 走缓存秒回，refresh=1 重搜）。
 * 换源弹层即时预载 + SSE 流式搜索合并去重。
 */
export function getAvailableBookSource(
  url: string,
  opts?: { silent?: boolean; refresh?: number },
): Promise<ReturnData<SearchBook[]>> {
  return get<SearchBook[]>('/getAvailableBookSource', { url, refresh: opts?.refresh ?? 0 }, opts)
}

/**
 * POST /reader3/setBookSource：换源主接口（legacy BookController 同名语义）——
 * bookUrl=书架书当前主键，newUrl=新源的书籍链接，bookSourceUrl=新源 URL。
 * 服务端完成：拉新源详情 + 书架主键/origin/tocUrl 切换 + 新源目录预取缓存；
 * 返回更新后的 Book。失败降级 saveBook 补丁路径（仅切 origin/tocUrl）。
 */
export function setBookSource(
  bookUrl: string,
  newUrl: string,
  bookSourceUrl: string,
  opts?: { silent?: boolean },
): Promise<ReturnData<Book>> {
  return post<Book>('/setBookSource', { bookUrl, newUrl, bookSourceUrl }, opts)
}

/** GET /reader3/getBookToc：章节目录（tocUrl=info.tocUrl + bookSource） */
export function getBookToc(
  tocUrl: string,
  bookSource: string,
  opts?: { timeout?: number },
): Promise<ReturnData<BookChapter[]>> {
  return get<BookChapter[]>('/getBookToc', { tocUrl, bookSource }, opts)
}

/* ================= GAP 81：换源 SSE 流式（/reader3/searchBookSourceSSE） ================= */

export interface SourceSSECallbacks {
  /** 单个书源结果到达（data 可能为空数组；lastIndex 为该源序号） */
  onBooks: (lastIndex: number, books: SearchBook[]) => void
  /** 流正常结束（event: end） */
  onEnd: (lastIndex: number, isEnd: boolean) => void
  /** 服务端业务错误（event: error，data 为 ReturnData） */
  onErrorEvent: (ret: ReturnData) => void
  /** 流中途中断（连接断开，非用户取消） */
  onStreamError?: (msg: string) => void
}

/**
 * POST /reader3/searchBookSourceSSE：流式换源（后端逐书源无名 data → event: end，legacy 对齐；
 * 与普通 searchBookSource 同契约 SearchBook[]，增量推送）。
 * 传输层失败 reject → 调用方降级普通 searchBookSource。
 */
export function searchBookSourceSSE(
  url: string,
  bookSource: string,
  cbs: SourceSSECallbacks,
): Promise<{ abort: () => void }> {
  const token = useUserStore().accessToken
  return openSSEPost(
    '/reader3/searchBookSourceSSE',
    { url, bookSource },
    { onBooks: cbs.onBooks, onEnd: cbs.onEnd, onErrorEvent: cbs.onErrorEvent, onStreamError: cbs.onStreamError },
    token,
  )
}

/**
 * GET /reader3/getBookContent：章节正文（chapterUrl + bookSource，正文在 data.content）
 * epubContent=1 且为 EPUB 本地书 → 返回 HTML 结构化正文（legacy 参数对齐；缺省/0 = 纯文本不变）
 */
export function getBookContent(
  chapterUrl: string,
  bookSource: string,
  opts?: { timeout?: number; bookUrl?: string },
  epubContent?: number,
): Promise<ReturnData<BookContent>> {
  return get<BookContent>(
    '/getBookContent',
    {
      chapterUrl,
      bookSource,
      ...(opts?.bookUrl ? { bookUrl: opts.bookUrl } : {}),
      ...(epubContent === 1 ? { epubContent } : {}),
    },
    opts,
  )
}
