#!/usr/bin/env python3
"""Minimal release smoke for an existing reader-dev instance; Python stdlib only.

Default is schema-only, SQLite mode=ro, with no HTTP request or database writes:
  docker exec -i <reader-container> python3 - --db /app/storage/reader.db < this.py

After reviewing the script and confirming the running instance's database path:
  docker exec -i <reader-container> python3 - --db /app/storage/reader.db --execute < this.py

The executable mode creates two random, non-admin users and only synthetic data.
It never reads existing credentials, copies a database, requests an external host,
starts a cache job, or calls namespace-wide clearCache. Exports stay in memory.
Normal failures, SIGINT and SIGTERM reach exact-fixture cleanup in finally.
SIGKILL/container death cannot run finally: the token-free fixture_scope record
printed before insertion identifies the exact run for operator recovery.
"""

import argparse
from contextlib import closing
import hashlib
import io
import json
import pathlib
import secrets
import signal
import sqlite3
import sys
import time
import urllib.parse
import urllib.request
import zipfile
import xml.etree.ElementTree as ET


REQUIRED = {
    "users": {
        "username", "password", "salt", "token", "token_map", "is_admin",
        "user_namespace", "created_at", "last_login_at", "raw_json",
        "enable_webdav", "enable_local_store", "enable_book_source",
        "enable_rss_source", "book_limit", "book_source_limit",
    },
    "books": {
        "book_url", "user_namespace", "name", "author", "origin", "toc_url",
        "type", "can_update", "is_in_shelf", "total_chapter_num", "created_at",
        "raw_json",
    },
    "book_chapters": {
        "book_url", "chapter_index", "user_namespace", "title", "content",
        "chapter_order",
    },
    "toc_cache": {
        "book_url", "toc_url", "user_namespace", "chapters_json", "updated_at",
    },
}
PRIMARY_KEYS = {
    "users": ["username"],
    "books": ["book_url", "user_namespace"],
    "book_chapters": ["book_url", "chapter_index", "user_namespace"],
    "toc_cache": ["book_url", "user_namespace"],
}
MAX_RESPONSE = 2 * 1024 * 1024


class SmokeFailure(Exception):
    pass


def require(condition, message):
    if not condition:
        raise SmokeFailure(message)


def emit(record):
    print(json.dumps(record, ensure_ascii=False, sort_keys=True), flush=True)


def open_db(path, mode):
    connection = sqlite3.connect(
        "file:" + urllib.parse.quote(str(path), safe="/") + "?mode=" + mode,
        uri=True, timeout=3, isolation_level=None,
    )
    connection.execute("PRAGMA busy_timeout = 3000")
    if mode == "ro":
        connection.execute("PRAGMA query_only = ON")
    else:
        connection.execute("PRAGMA foreign_keys = ON")
    return connection


def inspect_schema(connection):
    result = {}
    for table, required in REQUIRED.items():
        # Identifiers are fixed constants, never user-supplied strings.
        columns = connection.execute('PRAGMA table_info("' + table + '")').fetchall()
        names = {column[1] for column in columns}
        require(required <= names, "missing required columns in " + table)
        primary = [column[1] for column in sorted(columns, key=lambda c: c[5]) if column[5]]
        require(primary == PRIMARY_KEYS[table], "unexpected primary key in " + table)
        for column in columns:
            if column[3] and column[4] is None and column[1] not in required:
                raise SmokeFailure("unknown required insert column in " + table)
        triggers = connection.execute(
            "SELECT name FROM sqlite_master WHERE type = 'trigger' AND tbl_name = ?",
            (table,),
        ).fetchall()
        require(not triggers, "unreviewed trigger on " + table)
        result[table] = {"required_columns_present": True, "primary_key": primary, "triggers": 0}
    return result


class NoRedirect(urllib.request.HTTPRedirectHandler):
    def redirect_request(self, request, fp, code, message, headers, newurl):
        raise SmokeFailure("unexpected HTTP redirect")


class Smoke:
    def __init__(self, connection, base, timeout):
        self.connection = connection
        self.base = base
        self.timeout = timeout
        self.started_ms = int(time.time() * 1000)
        self.deadline = time.monotonic() + 60
        self.run_id = secrets.token_hex(16)
        self.namespaces = ["release_smoke_" + self.run_id + suffix for suffix in ("_a", "_b")]
        self.tokens = {namespace: secrets.token_urlsafe(32) for namespace in self.namespaces}
        self.book = "https://fixture.invalid/release-smoke/" + self.run_id + "/book"
        self.toc = "https://fixture.invalid/release-smoke/" + self.run_id + "/toc"
        self.local = "local://release-smoke/" + self.run_id + "/primary.txt"
        self.marker = json.dumps({"reader_release_smoke_run": self.run_id}, sort_keys=True)
        self.committed = False
        self.checks = []
        self.phase = "fixture_setup"
        self.http_requests = 0
        self.opener = urllib.request.build_opener(urllib.request.ProxyHandler({}), NoRedirect())

    def scope(self):
        return {
            "run_id": self.run_id, "namespaces": self.namespaces,
            "book_urls": [self.book, self.local], "toc_url": self.toc,
            "created_at": self.started_ms, "raw_json_marker": self.marker,
        }

    def body(self, owner, position):
        return "SMOKE:" + self.run_id + ":" + owner + ":%03d:<>&" % position

    def title(self, owner, position):
        return owner + " chapter %03d <>&" % position

    def insert(self, table, values):
        fields = list(values)
        self.connection.execute(
            'INSERT INTO "' + table + '" (' + ",".join(fields) + ") VALUES ("
            + ",".join("?" for unused in fields) + ")",
            [values[field] for field in fields],
        )

    def create(self):
        self.connection.execute("BEGIN IMMEDIATE")
        try:
            for namespace in self.namespaces:
                existing = self.connection.execute(
                    "SELECT EXISTS(SELECT 1 FROM users WHERE username = ? OR user_namespace = ?)",
                    (namespace, namespace),
                ).fetchone()[0]
                require(not existing, "random namespace collision")
                for table in ("books", "book_chapters", "toc_cache"):
                    count = self.connection.execute(
                        'SELECT COUNT(*) FROM "' + table + '" WHERE user_namespace = ?',
                        (namespace,),
                    ).fetchone()[0]
                    require(count == 0, "random namespace contains existing rows")
                self.insert("users", {
                    "username": namespace, "user_namespace": namespace,
                    "password": "disabled-release-smoke-" + secrets.token_hex(32),
                    "salt": secrets.token_hex(16), "token": "",
                    "token_map": json.dumps({self.tokens[namespace]: self.started_ms + 3600000}),
                    "is_admin": 0, "created_at": self.started_ms,
                    "last_login_at": self.started_ms, "raw_json": self.marker,
                    "enable_webdav": 0, "enable_local_store": 0,
                    "enable_book_source": 0, "enable_rss_source": 0,
                    "book_limit": 3, "book_source_limit": 0,
                })
            for namespace, owner, total in [(self.namespaces[0], "A", 401), (self.namespaces[1], "B", 2)]:
                self.insert("books", {
                    "book_url": self.book, "user_namespace": namespace,
                    "name": "release-smoke-" + self.run_id, "author": "synthetic",
                    "origin": "release-smoke-no-source", "toc_url": self.toc,
                    "type": 0, "can_update": 0, "is_in_shelf": 1,
                    "total_chapter_num": total, "created_at": self.started_ms,
                    "raw_json": self.marker,
                })
                directory = [{"title": "volume", "url": "volume", "isVolume": True}]
                for position in range(total):
                    chapter_url = self.toc + "/chapter/" + str(position)
                    title = self.title(owner, position)
                    directory.append({"title": title, "url": chapter_url, "index": position})
                    if owner == "B" or position % 2 == 0:
                        self.insert("book_chapters", {
                            "book_url": self.book, "user_namespace": namespace,
                            "chapter_index": int(hashlib.md5(chapter_url.encode()).hexdigest()[:15], 16),
                            "chapter_order": position, "title": title,
                            "content": self.body(owner, position),
                        })
                self.insert("toc_cache", {
                    "book_url": self.toc, "toc_url": self.toc,
                    "user_namespace": namespace, "chapters_json": json.dumps(directory),
                    "updated_at": 1,
                })
            self.insert("books", {
                "book_url": self.local, "user_namespace": self.namespaces[0],
                "name": "release-smoke-local-" + self.run_id, "author": "synthetic",
                "origin": "local", "toc_url": "", "type": 0, "can_update": 0,
                "is_in_shelf": 1, "total_chapter_num": 1,
                "created_at": self.started_ms, "raw_json": self.marker,
            })
            self.insert("book_chapters", {
                "book_url": self.local, "user_namespace": self.namespaces[0],
                "chapter_index": 0, "chapter_order": 0, "title": "primary local",
                "content": self.body("LOCAL", 0),
            })
            self.connection.commit()
            self.committed = True
        except BaseException:
            self.connection.rollback()
            raise

    def request(self, namespace, endpoint, params=None, payload=None):
        require(time.monotonic() < self.deadline, "60-second HTTP smoke deadline exceeded")
        require(endpoint in {"getBookCacheChapters", "exportToTxt", "exportToEpub", "deleteBookCache"},
                "endpoint is outside the smoke allowlist")
        url = self.base + "/reader3/" + endpoint
        if params:
            url += "?" + urllib.parse.urlencode(params)
        headers = {"User-Agent": "reader-release-smoke/1"}
        if namespace is not None:
            headers["accessToken"] = namespace + ":" + self.tokens[namespace]
        data = None
        if payload is not None:
            headers["Content-Type"] = "application/json"
            data = json.dumps(payload).encode()
        request = urllib.request.Request(url, data=data, headers=headers)
        self.http_requests += 1
        with self.opener.open(request, timeout=min(self.timeout, max(0.1, self.deadline - time.monotonic()))) as response:
            require(response.status == 200, "unexpected HTTP status")
            body = response.read(MAX_RESPONSE + 1)
            require(len(body) <= MAX_RESPONSE, "unexpectedly large HTTP response")
            return response.headers.get("Content-Type", ""), body

    def json_request(self, namespace, endpoint, params=None, payload=None):
        content_type, body = self.request(namespace, endpoint, params, payload)
        require("application/json" in content_type, "expected JSON response")
        return json.loads(body)

    def check(self, name, condition):
        self.checks.append({"name": name, "passed": bool(condition)})
        require(condition, "check failed: " + name)

    def page(self, namespace, owner, indices, more=False, next_from=None, url=None, **bounds):
        url = self.book if url is None else url
        response = self.json_request(namespace, "getBookCacheChapters", {"url": url, **bounds})
        require(response.get("isSuccess") is True, "cache page request failed")
        data = response["data"]
        rows = data["chapters"]
        expected = [{"index": position, "title": self.title(owner, position),
                     "content": self.body(owner, position)} for position in indices]
        if owner == "LOCAL":
            expected[0]["title"] = "primary local"
        require(data.get("url") == url and rows == expected, "cache page does not match its synthetic owner")
        require(data.get("hasMore") is more and data.get("nextFrom") == next_from,
                "cache page cursor/range contract failed")

    def txt(self):
        content_type, body = self.request(self.namespaces[0], "exportToTxt", {"bookUrl": self.book})
        require(content_type.startswith("text/plain"), "TXT content type failed")
        text = body.decode("utf-8")
        positions = [text.find(self.body("A", position)) for position in range(0, 401, 2)]
        require(all(position >= 0 for position in positions)
                and all(left < right for left, right in zip(positions, positions[1:])),
                "TXT is missing or reordering synthetic bodies")
        require(self.body("B", 0) not in text and "volume" not in text, "TXT owner/volume isolation failed")
        return body

    def epub(self):
        content_type, body = self.request(self.namespaces[0], "exportToEpub", {"bookUrl": self.book})
        require(content_type.startswith("application/epub+zip"), "EPUB content type failed")
        with zipfile.ZipFile(io.BytesIO(body)) as archive:
            require(sum(item.file_size for item in archive.infolist()) <= 8 * 1024 * 1024,
                    "unexpected EPUB expanded size")
            require(archive.read("mimetype") == b"application/epub+zip", "EPUB mimetype failed")
            opf = ET.fromstring(archive.read("OEBPS/content.opf"))
            spine = opf.findall(".//{http://www.idpf.org/2007/opf}spine/{http://www.idpf.org/2007/opf}itemref")
            require(len(spine) == 201, "EPUB spine count failed")
            for ordinal, position in enumerate(range(0, 401, 2)):
                raw = archive.read("OEBPS/chap_%04d.xhtml" % ordinal)
                chapter = ET.fromstring(raw)
                require(self.body("A", position) in "".join(chapter.itertext()), "EPUB chapter order/body failed")
                require(b"&lt;" in raw and b"&gt;" in raw and b"&amp;" in raw, "EPUB XML escaping failed")
        return {"bytes": len(body), "spine_count": 201, "sha256": hashlib.sha256(body).hexdigest()}

    def run(self):
        a, b = self.namespaces
        self.phase = "confirm_synthetic_namespace_binding"
        # These positive owner checks fail safely if secure=false or --db points
        # to another instance. No HTTP delete is attempted before all three pass.
        self.page(a, "A", list(range(0, 400, 2)), True, 399)
        self.page(b, "B", [0, 1])
        anonymous = self.json_request(None, "getBookCacheChapters", {"url": self.book})
        self.check("secure synthetic namespaces and anonymous rejection", anonymous.get("isSuccess") is False)
        self.phase = "pagination_and_range"
        self.page(a, "A", [400], **{"from": 399})
        self.page(a, "A", list(range(2, 401, 2)), **{"from": 1, "to": 400})
        self.page(a, "A", [], **{"from": 1, "to": 1})
        self.check("201 sparse bodies, bounded pages, real-index cursor and inclusive range", True)
        self.phase = "local_primary_protection"
        self.page(a, "LOCAL", [0], url=self.local)
        local_delete = self.json_request(a, "deleteBookCache", payload={"url": self.local})
        self.check("local primary body refuses cache deletion",
                   local_delete.get("isSuccess") is False
                   and local_delete.get("errorMsg") == "本地书籍无需删除缓存")
        self.phase = "in_memory_exports"
        before_txt = self.txt()
        epub = self.epub()
        self.check("TXT order/owner and EPUB spine/XML/body order", True)

        # Exact one-row TOC removal, deliberately not /reader3/clearCache.
        self.phase = "exact_synthetic_toc_removal"
        self.connection.execute("BEGIN IMMEDIATE")
        try:
            deleted = self.connection.execute(
                "DELETE FROM toc_cache WHERE user_namespace = ? AND book_url = ? AND toc_url = ?",
                (a, self.toc, self.toc),
            ).rowcount
            require(deleted == 1, "synthetic TOC row missing before exact deletion")
            self.connection.commit()
        except BaseException:
            self.connection.rollback()
            raise
        self.phase = "order_after_toc_removal"
        self.page(a, "A", list(range(0, 400, 2)), True, 399)
        self.page(a, "A", [400], **{"from": 399})
        self.check("clearing own TOC retains page positions and identical TXT", before_txt == self.txt())
        self.phase = "single_synthetic_book_deletion"
        deleted = self.json_request(a, "deleteBookCache", payload={"url": self.book})
        self.check("single synthetic network book deletion succeeds", deleted.get("isSuccess") is True)
        self.page(a, "A", [])
        self.page(b, "B", [0, 1])
        self.page(a, "LOCAL", [0], url=self.local)
        self.check("single-book deletion preserves other namespace and local primary body", True)
        return {"txt_bytes": len(before_txt), "txt_sha256": hashlib.sha256(before_txt).hexdigest(), "epub": epub}

    def cleanup(self):
        if not self.committed:
            return {"needed": False, "transaction_committed": False}
        self.connection.execute("BEGIN IMMEDIATE")
        try:
            for namespace in self.namespaces:
                owned = self.connection.execute(
                    "SELECT COUNT(*) FROM users WHERE username = ? AND user_namespace = ? "
                    "AND created_at = ? AND raw_json = ? AND is_admin = 0",
                    (namespace, namespace, self.started_ms, self.marker),
                ).fetchone()[0]
                require(owned == 1, "fixture user ownership marker changed; cleanup stopped")
                self.connection.execute(
                    "DELETE FROM book_chapters WHERE user_namespace = ? AND book_url IN (?, ?)",
                    (namespace, self.book, self.local),
                )
                self.connection.execute(
                    "DELETE FROM toc_cache WHERE user_namespace = ? AND book_url = ? AND toc_url = ?",
                    (namespace, self.toc, self.toc),
                )
                self.connection.execute(
                    "DELETE FROM books WHERE user_namespace = ? AND book_url IN (?, ?) "
                    "AND created_at = ? AND raw_json = ?",
                    (namespace, self.book, self.local, self.started_ms, self.marker),
                )
                self.connection.execute(
                    "DELETE FROM users WHERE username = ? AND user_namespace = ? "
                    "AND created_at = ? AND raw_json = ? AND is_admin = 0",
                    (namespace, namespace, self.started_ms, self.marker),
                )
            residual = self.residual_counts()
            require(not any(residual.values()), "fixture rows remain; cleanup transaction rolled back")
            self.connection.commit()
            return {"needed": True, "committed": True, "remaining_fixture_rows": residual}
        except BaseException:
            self.connection.rollback()
            raise

    def residual_counts(self):
        residual = {}
        for table in REQUIRED:
            column = "username" if table == "users" else "user_namespace"
            residual[table] = self.connection.execute(
                'SELECT COUNT(*) FROM "' + table + '" WHERE ' + column + " IN (?, ?)",
                self.namespaces,
            ).fetchone()[0]
        return residual


def interrupted(signum, frame):
    raise InterruptedError("smoke interrupted by signal " + str(signum))


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--db", required=True, help="existing SQLite file used by the running reader-dev instance")
    parser.add_argument("--base", default="http://127.0.0.1:8080", help="literal loopback HTTP origin only")
    parser.add_argument("--execute", action="store_true", help="create/run/finally-delete synthetic fixtures; default is schema-only mode=ro")
    parser.add_argument("--timeout", type=float, default=5, help="per-request timeout, 1..15 seconds")
    args = parser.parse_args()
    parsed = urllib.parse.urlsplit(args.base)
    require(parsed.scheme == "http" and parsed.hostname in {"127.0.0.1", "::1"}
            and parsed.username is None and parsed.password is None
            and parsed.path in {"", "/"} and not parsed.query and not parsed.fragment,
            "--base must be a literal loopback HTTP origin")
    require(1 <= args.timeout <= 15, "--timeout must be 1..15 seconds")
    path = pathlib.Path(args.db).resolve(strict=True)
    require(path.is_file(), "--db must be an existing regular SQLite file")
    with closing(open_db(path, "ro")) as readonly:
        schema = inspect_schema(readonly)
    if not args.execute:
        emit({"mode": "schema-only-readonly", "passed": True, "database": str(path),
              "schema": schema, "http_requests": 0, "database_writes": 0})
        return 0

    signal.signal(signal.SIGTERM, interrupted)
    connection = open_db(path, "rw")
    smoke = Smoke(connection, args.base.rstrip("/"), args.timeout)
    report = {"mode": "synthetic-live-http", "database": str(path),
              "base": args.base, "fixture_scope": smoke.scope(), "schema": schema}
    emit({"phase": "fixture_planned", "fixture_scope": smoke.scope()})
    try:
        # Recheck metadata on the write connection; no schema migration occurs.
        inspect_schema(connection)
        smoke.create()
        report["exports"] = smoke.run()
        report["passed"] = True
    except BaseException as error:
        report["passed"] = False
        report["error"] = type(error).__name__ + ": " + str(error)
    finally:
        # Allow this bounded, exact cleanup to finish after an ordinary stop.
        signal.signal(signal.SIGTERM, signal.SIG_IGN)
        signal.signal(signal.SIGINT, signal.SIG_IGN)
        try:
            report["cleanup"] = smoke.cleanup()
        except BaseException as error:
            report["passed"] = False
            report["cleanup"] = {"committed": False, "error": type(error).__name__ + ": " + str(error)}
        report["checks"] = smoke.checks
        report["last_http_phase"] = smoke.phase
        report["http_requests"] = smoke.http_requests
        report["elapsed_ms"] = int(time.time() * 1000) - smoke.started_ms
        connection.close()
    emit(report)
    return 0 if report["passed"] else 1


if __name__ == "__main__":
    try:
        sys.exit(main())
    except Exception as error:
        emit({"passed": False, "phase": "preflight", "error": type(error).__name__ + ": " + str(error)})
        sys.exit(2)
