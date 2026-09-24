#!/usr/bin/env python3
"""版本号一致性校验：根 Cargo.toml ↔ web-ui/package.json（可选 --expect 校验 tag）。

用法：
  python3 scripts/check_version.py                 # 只查内部一致
  python3 scripts/check_version.py v5.2.5          # 同时校验 tag 名 == 版本
退出码：0 一致 / 1 不一致（CI 直接失败）
"""
import json
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent


def cargo_version() -> str:
    text = (ROOT / "Cargo.toml").read_text(encoding="utf-8")
    m = re.search(r'^version\s*=\s*"([^"]+)"', text, re.M)
    if not m:
        raise SystemExit("Cargo.toml 无 version 字段")
    return m.group(1)


def npm_version() -> str:
    pkg = json.loads((ROOT / "web-ui" / "package.json").read_text(encoding="utf-8"))
    return str(pkg.get("version", ""))


def main() -> int:
    expect = None
    args = [a for a in sys.argv[1:] if a.startswith("--") is False]
    if args:
        expect = args[0].lstrip("v")

    cv, nv = cargo_version(), npm_version()
    problems = []
    if cv != nv:
        problems.append(f"Cargo.toml({cv}) != web-ui/package.json({nv})")

    print(f"  Cargo.toml        = {cv}")
    print(f"  package.json      = {nv}")

    if expect:
        if cv != expect:
            problems.append(f"tag v{cv} != 实际版本 {expect}")
        else:
            print(f"  tag               = v{expect} ✓")

    if problems:
        for p in problems:
            print(f"FAIL {p}", file=sys.stderr)
        return 1
    print("版本号一致 ✓")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
