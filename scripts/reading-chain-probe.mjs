// 四段链路验证探针（node）：搜索 → 详情 → 目录 → 正文
// 用法：node scripts/reading-chain-probe.mjs [起始下标] [数量] [关键词]
// 输出：scripts/reading-chain-probe-result.json（每源 {name, stage, detail}）
// 目标：仅本机回环自建服务（硬编码 host 校验）
import http from "node:http";
import fs from "node:fs";

const HOST = "127.0.0.1";
const PORT = 8099;

function call(path, timeoutMs = 30000) {
  return new Promise((resolve) => {
    // 回环硬校验：本探针只打本机自建服务
    if (!path.startsWith("/")) throw new Error("path only");
    const req = http.request({ host: HOST, port: PORT, path, method: "GET", timeout: timeoutMs }, (res) => {
      let b = "";
      res.on("data", (c) => (b += c));
      res.on("end", () => { try { resolve(JSON.parse(b)); } catch { resolve({ raw: b.slice(0, 100) }); } });
    });
    req.on("timeout", () => { req.destroy(); resolve({ err: "TIMEOUT" }); });
    req.on("error", (e) => resolve({ err: String(e).slice(0, 50) }));
    req.end();
  });
}

function hostOf(u) { try { return new URL(u.split("#")[0]).hostname; } catch { return u; } }

async function probe(s, key) {
  const q = encodeURIComponent;
  const bs = q(s.bookSourceUrl);
  const r1 = await call(`/reader3/searchBookMulti?key=${q(key)}&bookSourceUrl=${bs}&page=1`);
  const books = Array.isArray(r1.data) ? r1.data : [];
  if (!books.length) return { stage: "search", detail: (r1.errorMsg || "0结果").slice(0, 40) };
  const bu = q(books[0].bookUrl || "");
  const r2 = await call(`/reader3/getBookInfo?url=${bu}&bookSourceUrl=${bs}`);
  if (!r2?.isSuccess) return { stage: "info", detail: (r2?.errorMsg || "").slice(0, 40) };
  if (!r2.data?.name) return { stage: "info", detail: "name为空" };
  const r3 = await call(`/reader3/getBookToc?url=${q(r2.data.tocUrl || books[0].bookUrl)}&bookSourceUrl=${bs}`);
  const ch = Array.isArray(r3?.data) ? r3.data : [];
  if (!r3?.isSuccess) return { stage: "toc", detail: (r3?.errorMsg || "").slice(0, 40) };
  if (!ch.length) return { stage: "toc", detail: "0章" };
  const r4 = await call(`/reader3/getBookContent?url=${q(ch[0].url || "")}&bookSourceUrl=${bs}&index=0`);
  const d = typeof r4?.data === "string" ? r4.data : String(r4?.data?.content ?? "");
  if (!r4?.isSuccess) return { stage: "content", detail: (r4?.errorMsg || "").slice(0, 40) };
  if (d.trim().length < 30) return { stage: "content", detail: `过短(${d.trim().length})` };
  return { stage: "OK", detail: `${r2.data.name.slice(0, 10)} ${ch.length}章` };
}

const [, , fromArg = "0", countArg = "24", keyArg = "剑"] = process.argv;
const FROM = parseInt(fromArg, 10), COUNT = parseInt(countArg, 10), KEY = keyArg;

const sources = (await call("/reader3/getBookSources", 120000)).data || [];
const seen = new Set(), picked = [];
for (const s of sources) {
  if (!s.enabled || !s.searchUrl) continue;
  const h = hostOf(s.bookSourceUrl);
  if (seen.has(h)) continue;
  seen.add(h); picked.push(s);
}
const step = Math.max(1, Math.floor(picked.length / 24));
const sample = picked.filter((_, i) => i % step === 0).slice(0, 24);
const results = [];
for (const s of sample.slice(FROM, FROM + COUNT)) {
  const r = await probe(s, KEY);
  results.push({ name: s.bookSourceName.slice(0, 16), url: s.bookSourceUrl, ...r });
  console.log(r.stage.padEnd(8), s.bookSourceName.slice(0, 14).padEnd(14), r.detail);
}
const outFile = new URL("./reading-chain-probe-result.json", import.meta.url);
fs.writeFileSync(outFile, JSON.stringify(results, null, 1), "utf8");
const buckets = {};
for (const r of results) buckets[r.stage] = (buckets[r.stage] || 0) + 1;
console.log("汇总:", JSON.stringify(buckets));
