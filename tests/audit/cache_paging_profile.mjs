// Compare two opt-in Rust probe executables against one generated SQLite fixture.
// No production URL, credentials, database path, or external requests are accepted.
import { spawn } from 'node:child_process'
import { createHash } from 'node:crypto'
import { createReadStream } from 'node:fs'
import { mkdtemp, writeFile, rm, stat } from 'node:fs/promises'
import { tmpdir } from 'node:os'
import path from 'node:path'

const [before, after, output] = process.argv.slice(2)
if (!before || !after || !output) {
  throw new Error('usage: node cache_paging_profile.mjs <before-probe> <after-probe> <output.json>')
}
const env = { ...process.env, NO_PROXY: '127.0.0.1,localhost,::1',
  READER_CAMOUFOX_DISABLE: '1', READER_CAMOUFOX_SPAWN: '0' }
for (const key of ['HTTP_PROXY', 'HTTPS_PROXY', 'ALL_PROXY', 'http_proxy', 'https_proxy', 'all_proxy',
  'READER_CAMOUFOX_URL']) delete env[key]

async function run(binary, args, timeoutMs = 120_000) {
  return await new Promise((resolve, reject) => {
    const proc = spawn(binary, args, { env, stdio: ['ignore', 'pipe', 'pipe'] })
    const chunks = []
    const errors = []
    let timedOut = false
    const timer = setTimeout(() => { timedOut = true; proc.kill('SIGKILL') }, timeoutMs)
    proc.stdout.on('data', (chunk) => chunks.push(chunk))
    proc.stderr.on('data', (chunk) => errors.push(chunk))
    proc.on('error', (error) => { clearTimeout(timer); reject(error) })
    proc.on('close', (code, signal) => {
      clearTimeout(timer)
      const stdout = Buffer.concat(chunks).toString('utf8').trim()
      const stderr = Buffer.concat(errors).toString('utf8')
      if (code !== 0) return reject(new Error(JSON.stringify({ binary, args, code, signal, timedOut, stdout, stderr })))
      try { resolve({ data: JSON.parse(stdout), stderr, exitCode: code }) } catch (error) { reject(error) }
    })
  })
}

async function sha256(binary) {
  const digest = createHash('sha256')
  for await (const chunk of createReadStream(binary)) digest.update(chunk)
  return digest.digest('hex')
}
const root = await mkdtemp(path.join(tmpdir(), 'reader-paging-profile-'))
const fixtureDir = path.join(root, 'synthetic-only')
const report = {
  createdAt: new Date().toISOString(),
  comparison: 'd6dc13e-candidate-before-SQL-pagination vs SQL-pagination-candidate',
  toolchain: 'debug locked build; fresh HTTP server+client in each process; Linux VmHWM',
  limits: 'Synthetic, local loopback; warm OS page cache not reset; not production or real browser/camoufox load.',
  binaries: { before: { path: before, sha256: await sha256(before) }, after: { path: after, sha256: await sha256(after) } },
  fixture: null, reads: [], summary: [], assertionsPassed: false,
}
try {
  const prepared = await run(before, ['prepare', fixtureDir], 180_000)
  report.fixture = { ...prepared.data, databaseBytes: (await stat(path.join(fixtureDir, 'storage', 'reader.db'))).size }
  for (const mode of ['toc', 'no-toc']) {
    for (const [from, to] of [[0, 4095], [2048, 2057]]) {
      for (let round = 0; round < 3; round++) {
        // Alternate order to reduce systematic page-cache bias, without flushing host caches.
        for (const version of round % 2 ? ['after', 'before'] : ['before', 'after']) {
          const result = await run(version === 'before' ? before : after,
            ['read', fixtureDir, String(from), String(to), mode])
          report.reads.push({ version, round, ...result.data, stderr: result.stderr, exitCode: result.exitCode })
          process.stdout.write(`${version} ${mode} ${from}..${to} round=${round}: ${result.data.requestMs.toFixed(2)} ms; peak=${result.data.peakRssKiB} KiB\n`)
        }
      }
      const rows = report.reads.filter((row) => row.mode === mode && row.from === from && row.to === to)
      if (new Set(rows.map((row) => row.responseMd5)).size !== 1 || new Set(rows.map((row) => row.responseBytes)).size !== 1) {
        throw new Error(`Response bytes differ for ${mode} ${from}..${to}`)
      }
      for (const version of ['before', 'after']) {
        const sample = rows.filter((row) => row.version === version)
        const median = (key) => sample.map((row) => row[key]).sort((a, b) => a - b)[1]
        report.summary.push({ version, mode, from, to, runs: sample.length,
          medianRequestMs: median('requestMs'), medianPeakRssKiB: median('peakRssKiB'),
          maxPeakRssKiB: Math.max(...sample.map((row) => row.peakRssKiB)),
          returned: sample[0].returned, responseBytes: sample[0].responseBytes, responseMd5: sample[0].responseMd5 })
      }
    }
  }
  report.assertionsPassed = true
} catch (error) {
  report.error = String(error?.stack ?? error)
  throw error
} finally {
  await writeFile(output, `${JSON.stringify(report, null, 2)}\n`)
  await rm(root, { recursive: true, force: true })
}
