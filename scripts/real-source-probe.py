#!/usr/bin/env python3
"""真实书源导入 + 逐源搜索探针（实测驱动）。

用途：配合真实书源备份验证导入兼容性与端到端搜索链路。
前提：服务已启动（默认 127.0.0.1:8099，非 secure 模式）。

用法：
  python3 scripts/real-source-probe.py <书源备份.json> [每源关键词] [源数量]

注意：若服务进程出站被安全软件按应用拦截（日志见 http_fetch os error 10013），
所有远端搜索都会 EMPTY——需先在安全软件中放行 reader-dev.exe 再测。
"""
import json
import sys
import time
import urllib.error
import urllib.parse
import urllib.request

# 探针目标是**本机自建服务**：仅允许回环地址（脚本自身即客户端，不转发第三方输入）
ALLOWED_HOSTS = ("127.0.0.1", "localhost", "[::1]")
BASE = "http://127.0.0.1:8099"


def _assert_local(base: str) -> None:
    p = urllib.parse.urlparse(base)
    if p.scheme != "http" or p.hostname not in ALLOWED_HOSTS:
        raise SystemExit(f"仅允许本机回环地址: {base}")


def pick_sources(path: str, limit: int):
    """选源：enabled + 有 searchUrl + 主机为 ASCII（剔除全角字符等脏数据），按主机去重。"""
    data = json.load(open(path, encoding="utf-8"))
    seen, picked = set(), []
    for x in data:
        if not (x.get("enabled") and x.get("searchUrl")):
            continue
        try:
            p = urllib.parse.urlparse(x.get("bookSourceUrl", ""))
        except ValueError:
            continue
        if not p.netloc or p.scheme not in ("http", "https"):
            continue
        if not all(c.isascii() for c in p.netloc):
            continue
        if p.netloc in seen:
            continue
        seen.add(p.netloc)
        picked.append(x)
        if len(picked) >= limit:
            break
    return picked


def main() -> int:
    if len(sys.argv) < 2:
        print(__doc__)
        return 1
    _assert_local(BASE)
    path = sys.argv[1]
    keyword = sys.argv[2] if len(sys.argv) > 2 else "剑"
    limit = int(sys.argv[3]) if len(sys.argv) > 3 else 12

    srcs = pick_sources(path, limit)
    print(f"选取 {len(srcs)} 个源")

    req = urllib.request.Request(
        BASE + "/reader3/saveBookSources",
        data=json.dumps(srcs, ensure_ascii=False).encode(),
        headers={"Content-Type": "application/json"},
        method="POST",
    )
    ret = json.loads(urllib.request.urlopen(req, timeout=30).read().decode())
    if not ret.get("isSuccess"):
        print("导入失败:", ret)
        return 2
    print(f"导入成功: {ret['data']['count']} 个")

    ok = empty = err = 0
    for s in srcs:
        q = urllib.parse.urlencode(
            {"key": keyword, "bookSourceUrl": s["bookSourceUrl"], "page": 1}
        )
        t0 = time.time()
        try:
            body = json.loads(
                urllib.request.urlopen(
                    BASE + "/reader3/searchBookMulti?" + q, timeout=45
                ).read().decode()
            )
            data = body.get("data")
            n = len(data) if isinstance(data, list) else 0
            dt = round(time.time() - t0, 1)
            if n:
                ok += 1
                print(f"OK    {n:2}本 {dt:5}s  {s['bookSourceName'][:16]}  {data[0].get('name','')}")
            else:
                empty += 1
                print(f"EMPTY  0本 {dt:5}s  {s['bookSourceName'][:16]}  {body.get('errorMsg','')}")
        except Exception as e:
            err += 1
            print(f"ERR      {round(time.time()-t0,1):5}s  {s['bookSourceName'][:16]}  {str(e)[:70]}")

    print(f"\n汇总: OK={ok} EMPTY={empty} ERR={err}")
    if ok == 0 and empty + err > 0:
        print("提示：全部 EMPTY/ERR 时先检查服务进程出站是否被安全软件拦截"
              "（服务日志 http_fetch os error 10013），再怀疑书源/引擎。")
    return 0


if __name__ == "__main__":
    sys.exit(main())
