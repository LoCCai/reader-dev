"""Reproduce the exact 7efd557 storage SQL using a private in-memory database.

This intentionally proves the baseline failure, not the patched API behavior.
No application database, environment secrets, or network connection is opened.
"""
import json
import os
import re
import sqlite3
import subprocess
from pathlib import Path

root = Path(__file__).resolve().parents[2]
# This fork-history commit has exactly the deployed 7efd557 tree, without
# publishing the server's unrelated history. It is reachable in a fresh clone.
baseline_ref = os.environ.get("AUDIT_BASELINE_REF", "d1a4cc4")
source = subprocess.check_output(
    ["git", "show", f"{baseline_ref}:src/storage/mod.rs"], cwd=root, text=True
)
clear = source.split("pub async fn clear_cache(", 1)[1].split(
    "// ----------------", 1
)[0]
stat = source.split("pub async fn get_cache_info(", 1)[1].split(
    "pub async fn clear_cache(", 1
)[0]
sql = re.findall(r'sqlx::query\("([^"]+)"\)', clear)
count_sql = re.findall(r'sqlx::query_scalar\("(SELECT COUNT\(\*\)[^"]+)"\)', stat)
assert sql == ["DELETE FROM toc_cache", "DELETE FROM book_chapters"]
db = sqlite3.connect(":memory:")
db.executescript("""
CREATE TABLE toc_cache(user_namespace TEXT, book_url TEXT, chapters_json TEXT);
CREATE TABLE book_chapters(user_namespace TEXT, book_url TEXT, chapter_index INTEGER, content TEXT);
INSERT INTO toc_cache VALUES ('qa-a','https://fixture.invalid/a','[]'),('qa-b','https://fixture.invalid/b','[]');
INSERT INTO book_chapters VALUES
 ('qa-a','https://fixture.invalid/a',0,'a network'),
 ('qa-b','https://fixture.invalid/b',0,'b network'),
 ('qa-a','local://qa-a/book.txt',0,'a only local body'),
 ('qa-b','local://qa-b/book.txt',0,'b only local body');
""")
before = {table: db.execute(f"SELECT COUNT(*) FROM {table}").fetchone()[0]
          for table in ("toc_cache", "book_chapters")}
stat_counts = [db.execute(query).fetchone()[0] for query in count_sql]
for query in sql:
    db.execute(query)
after = {table: db.execute(f"SELECT COUNT(*) FROM {table}").fetchone()[0]
         for table in before}
assert before == {"toc_cache": 2, "book_chapters": 4}
assert stat_counts == [2, 4], stat_counts
assert after == {"toc_cache": 0, "book_chapters": 0}
print(json.dumps({"baseline": "7efd557", "baseline_ref": baseline_ref, "executed_actual_sql": sql,
                  "before": before, "after": after,
                  "confirmed": ["statistics includes both users and local primary data",
                                "clearCache removes both users' cache",
                                "clearCache destroys local primary chapters"]}, ensure_ascii=False, indent=2))
