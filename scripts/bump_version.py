#!/usr/bin/env python3
"""一键统一升版：Cargo.toml + web-ui/package.json + Cargo.lock/package-lock 同步。

用法：python3 scripts/bump_version.py 5.2.5
（只接受 x.y.z 纯版本号；tag 的 v 前缀自己加）
"""
import json
import re
import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent


def main() -> int:
    if len(sys.argv) != 2 or not re.fullmatch(r"\d+\.\d+\.\d+", sys.argv[1]):
        print("用法: python3 scripts/bump_version.py <x.y.z>", file=sys.stderr)
        return 1
    ver = sys.argv[1]

    # 1) 根 Cargo.toml（仅 package 段首个 version）
    cargo = ROOT / "Cargo.toml"
    text = cargo.read_text(encoding="utf-8")
    new_text, n = re.subn(
        r'^(version\s*=\s*)"[^"]+"', rf'\g<1>"{ver}"', text, count=1, flags=re.M
    )
    if n != 1:
        print("FAIL Cargo.toml 未找到 version", file=sys.stderr)
        return 1
    cargo.write_text(new_text, encoding="utf-8")

    # 2) web-ui/package.json
    pkg_path = ROOT / "web-ui" / "package.json"
    pkg = json.loads(pkg_path.read_text(encoding="utf-8"))
    pkg["version"] = ver
    pkg_path.write_text(
        json.dumps(pkg, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )

    # 3) lockfiles 同步（存在才跑；失败不致命——CI 会重新生成）
    for cmd in (
        ["cargo", "update", "-p", "reader-dev", "--precise", ver],
        ["npm", "install", "--package-lock-only", "--prefix", str(ROOT / "web-ui")],
    ):
        try:
            subprocess.run(cmd, cwd=ROOT, capture_output=True, timeout=300, check=False)
        except (OSError, subprocess.TimeoutExpired):
            pass

    # 4) 校验
    r = subprocess.run(
        [sys.executable, str(ROOT / "scripts" / "check_version.py"), f"v{ver}"],
        capture_output=True,
        text=True,
    )
    print(r.stdout, end="")
    if r.returncode != 0:
        print(r.stderr, file=sys.stderr)
        return 1

    print(f"\n已统一升至 {ver}")
    print("后续：git commit + tag v" + ver + " 推送即触发全矩阵发版")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
