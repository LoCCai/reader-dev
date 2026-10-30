"""Run already compiled integration binaries serially with isolated test networking.

Pass exact executable paths emitted by the root agent's final cargo --no-run.
This runner never compiles, installs dependencies, or restarts any service.
"""
import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import subprocess
import time

parser = argparse.ArgumentParser()
parser.add_argument("binaries", nargs="+", type=Path)
parser.add_argument("--timeout", type=int, default=120)
parser.add_argument("--output", type=Path, default=Path(__file__).resolve().parent / "artifacts/backend-integration-final")
args = parser.parse_args()
args.output.mkdir(parents=True, exist_ok=True)
env = dict(os.environ)
for name in ("HTTP_PROXY", "HTTPS_PROXY", "ALL_PROXY", "http_proxy", "https_proxy", "all_proxy", "READER_CAMOUFOX_URL"):
    env.pop(name, None)
env.update(NO_PROXY="127.0.0.1,localhost,::1", READER_CAMOUFOX_DISABLE="1", READER_CAMOUFOX_SPAWN="0")
results = []
for binary in args.binaries:
    binary = binary.resolve(strict=True)
    if not re.fullmatch(r"e2e_[a-z_]+-[a-f0-9]+", binary.name) or not os.access(binary, os.X_OK):
        raise SystemExit(f"Refusing unexpected test binary: {binary}")
    started = time.monotonic()
    try:
        result = subprocess.run([str(binary), "--test-threads=1", "--nocapture"],
                                env=env, capture_output=True, text=True, timeout=args.timeout,
                                cwd=Path(__file__).resolve().parents[2])
        output = result.stdout + result.stderr
        code = result.returncode
    except subprocess.TimeoutExpired as error:
        output = (error.stdout or b"") + (error.stderr or b"")
        if isinstance(output, bytes):
            output = output.decode("utf-8", errors="replace")
        output += f"\nQA RUNNER TIMEOUT after {args.timeout}s\n"
        code = 124
    logfile = args.output / f"{binary.name}.log"
    logfile.write_text(output)
    counts = re.search(r"test result: (\w+)\. (\d+) passed; (\d+) failed; (\d+) ignored; (\d+) measured; (\d+) filtered out", output)
    record = {"binary": str(binary), "sha256": hashlib.sha256(binary.read_bytes()).hexdigest(),
              "exitCode": code, "elapsedSeconds": round(time.monotonic() - started, 2), "log": str(logfile),
              "counts": dict(zip(("status", "passed", "failed", "ignored", "measured", "filteredOut"), counts.groups())) if counts else None,
              "reportedSkips": [line for line in output.splitlines() if re.search(r"\bskip(?:ped)?\b|跳过", line, re.I)]}
    results.append(record)
    print(json.dumps(record, ensure_ascii=False), flush=True)
summary = {"environment": {"proxyVariables": "removed", "NO_PROXY": env["NO_PROXY"],
                           "READER_CAMOUFOX_DISABLE": "1", "READER_CAMOUFOX_SPAWN": "0"},
           "limitations": ["Camoufox browser navigation, browser login and CAPTCHA solving were not executed; HTTP WebView fallback is covered."],
           "results": results}
(args.output / "summary.json").write_text(json.dumps(summary, ensure_ascii=False, indent=2))
raise SystemExit(1 if any(result["exitCode"] != 0 for result in results) else 0)
