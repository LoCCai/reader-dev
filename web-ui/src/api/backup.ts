import { post } from './request'
import request from './request'
import { downloadFile } from './file'
import type { ReturnData } from '@/types'

/**
 * POST /reader3/backupToWebdav：备份数据到 WebDAV。
 * body { path?: string }：目标子目录（默认 webdav/legado）。
 * GAP 151：路径参数已随请求发送；后端当前固定写入 webdav/legado（create_backup_zip 硬编码），
 * 尚未消费 path 参数——前端先传参预留，后端支持后即可切换目录。
 * 响应：ReturnData<{ path: string }>，path 为备份 zip 的绝对路径
 * （storage/data/{ns}/webdav/legado/backup-{ts}.zip）。
 */
export function backupToWebdav(path?: string): Promise<ReturnData<{ path: string }>> {
  return post<{ path: string }>('/backupToWebdav', path ? { path } : undefined)
}

/**
 * 下载备份 zip：backupToWebdav 返回绝对路径，取其文件名，按「用户数据根（__HOME__）下
 * {dir}/」的相对路径走 GET /reader3/file/download（file/download 的 path 是 home 根下相对路径）。
 * GAP 151：dir 默认 webdav/legado（后端当前固定目录）；传入备份路径配置后与 backupToWebdav(path) 对齐。
 */
export function downloadBackupZip(absPath: string, dir = 'webdav/legado'): Promise<Blob> {
  const name = absPath.split(/[\\/]/).filter(Boolean).pop() || 'backup.zip'
  const cleanDir = dir.trim().replace(/^\/+|\/+$/g, '')
  return downloadFile(cleanDir ? `${cleanDir}/${name}` : name, '__HOME__')
}

/** 备份还原报告（/reader3/restoreFromZip → data） */
export interface RestoreReport {
  restored: Record<string, number>
  skipped: Record<string, number>
}

/**
 * POST /reader3/restoreFromZip：从备份 zip 恢复（multipart：file + overwrite 字段）。
 * overwrite=false（默认）时逐项幂等：已存在数据跳过；true 则覆盖。
 */
export function restoreFromZip(
  file: File | Blob,
  name: string,
  overwrite = false,
): Promise<ReturnData<RestoreReport>> {
  const form = new FormData()
  form.append('file', file, name)
  form.append('overwrite', overwrite ? 'true' : 'false')
  return post<RestoreReport>('/restoreFromZip', form, { timeout: 120_000 })
}

/**
 * POST /reader3/restoreFromWebdav：从 WebDAV 备份恢复（A4 接线）。
 * body { path, overwrite? }——path 为 WebDAV 根下相对路径（backupToWebdav 返回的
 * 绝对路径截掉 .../webdav/ 前缀后的部分，形如 legado/backup-{ts}.zip）。
 */
export function restoreFromWebdav(
  path: string,
  overwrite = false,
): Promise<ReturnData<RestoreReport>> {
  return post<RestoreReport>('/restoreFromWebdav', { path, overwrite }, { timeout: 120_000 })
}

/** MongoDB 备份/恢复参数：uri 缺省走服务端环境变量 READER_MONGODB_URI；db 默认 reader3；
 *  ns 缺省 = 全部命名空间（default + 全部注册用户） */
export interface MongoBackupParams {
  uri?: string
  db?: string
  ns?: string
}

/** POST /reader3/backupToMongodb：备份到 MongoDB（body { uri, db, ns }）→ 服务层报告 */
export function backupToMongodb(
  params: MongoBackupParams = {},
  opts?: { silent?: boolean; timeout?: number },
): Promise<ReturnData<Record<string, unknown>>> {
  return post<Record<string, unknown>>('/backupToMongodb', params, {
    silent: opts?.silent,
    timeout: opts?.timeout ?? 120_000,
  })
}

/** POST /reader3/restoreFromMongodb：从 MongoDB 恢复（ns 缺省 = 全部命名空间逐个恢复） */
export function restoreFromMongodb(
  params: MongoBackupParams = {},
  opts?: { silent?: boolean; timeout?: number },
): Promise<ReturnData<Record<string, unknown>>> {
  return post<Record<string, unknown>>('/restoreFromMongodb', params, {
    silent: opts?.silent,
    timeout: opts?.timeout ?? 120_000,
  })
}

/**
 * GET /reader3/user/downloadBackupFile：服务端即时打包当前用户备份并返回 zip blob
 * （secure 模式需开启 WebDAV 功能）。accessToken 由 request 实例自动携带。
 */
export function downloadBackupFileNow(): Promise<Blob> {
  return request
    .get('/user/downloadBackupFile', { responseType: 'blob', timeout: 120_000 })
    .then((r) => r.data as Blob)
}
