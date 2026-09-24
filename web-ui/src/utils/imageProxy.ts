/**
 * legacy imageProxy：设置页开关 reader_image_proxy（默认关）。
 * 开启后远端 http(s) 图片经后端 /assets/proxy 回源（复用书源登录态/UA/Referer 与
 * WebP 转换，防盗链与私网拦截由服务端统一处理）。
 */

const PROXY_KEY = 'reader_image_proxy'

export function imageProxyEnabled(): boolean {
  try {
    return localStorage.getItem(PROXY_KEY) === '1'
  } catch {
    return false
  }
}

export function setImageProxyEnabled(on: boolean): void {
  try {
    localStorage.setItem(PROXY_KEY, on ? '1' : '0')
  } catch {
    /* ignore */
  }
}

export function proxyImageUrl(url: string | null | undefined): string | null | undefined {
  if (!url || !/^https?:\/\//i.test(url)) return url
  // https 页面下的 http 图片会被浏览器 mixed-content 拦截（实测久久小说等
  // http 书源封面在 https 部署全挂）——自动经后端代理，无需用户开关
  const forced = typeof window !== 'undefined'
    && window.location.protocol === 'https:'
    && /^http:\/\//i.test(url)
  if (!forced && !imageProxyEnabled()) return url
  return `/assets/proxy?url=${encodeURIComponent(url)}`
}
