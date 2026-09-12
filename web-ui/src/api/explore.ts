import { get } from './request'
import type { ExploreCategory, ExploreSourceInfo, ReturnData, SearchBook } from '@/types'

/** GET /reader3/getExploreSources：探索书源列表（离线计数——列表页秒开） */
export function getExploreSources(): Promise<ReturnData<ExploreSourceInfo[]>> {
  return get<ExploreSourceInfo[]>('/getExploreSources')
}

/** GET /reader3/getExploreUrls：书源 exploreUrl 集合（bookSource=书源 URL 或完整 JSON）。
 *  @js: 型探索脚本会经后端执行网络请求，放宽到 30s */
export function getExploreUrls(bookSource: string): Promise<ReturnData<ExploreCategory[]>> {
  return get<ExploreCategory[]>('/getExploreUrls', { bookSource }, { timeout: 30000 })
}

/** GET /reader3/exploreBook：探索书单（url=分类 exploreUrl + bookSource + page；后端自动替换 {{page}}/{page}） */
export function exploreBook(
  url: string,
  bookSource: string,
  page = 1,
): Promise<ReturnData<SearchBook[]>> {
  return get<SearchBook[]>('/exploreBook', { url, bookSource, page })
}
