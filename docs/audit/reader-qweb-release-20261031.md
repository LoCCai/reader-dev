# Reader / QWeb reading recovery release (2026-10-31 UTC)

Reader application source is commit `5685c6e`, fast-forwarded from the published
fork's `5a34a0a`. The original audit branch had a different commit history but
the same baseline tree (`7184c161886d1cd5b340f542bebd4928f5b72fea`). The two Rust
source files and opt-in live test exactly match the previously tested candidate.
Private live-source fixtures, credentials and user locators are not committed.

The release corrects `replaceRegex=##pattern` deletion semantics, narrowly
recognizes the resulting broken cached content, honors explicit chapter refresh,
normalizes cache count/state fields and preserves EPUB compatibility parameters.
QWeb/Bridge changes are maintained in the separate Qiqi workspace.

Validation before deployment: 834 Rust library tests and 12 HTTP integration
tests passed; the release build passed. Qiqi reading tests covered 207 unique
cases; frontend verification reported 2,126 passed and 2 skipped, plus 5 browser
acceptance cases. These counts describe their respective scoped suites, not an
all-repository green claim.

## Actual Reader deployment

The authoritative checkout is `/home/ccai/docker/reader-dev` on 193. Existing
Dockerfile and locked dependencies were used without pulling base images or
upgrading dependencies: `cargo build --release --locked -j2`. The optimized
build finished in 13m40s with existing compiler warnings. Only `reader-dev` was
recreated through its existing Compose definition, with the same ports, data
bind and 2 GiB limit.

- Started: `2026-10-31T16:49:53.763217104Z`; healthy, RestartCount 0, OOMKilled false.
- Image: `sha256:691ae4b254841f6c27a3fb248ecc96467534823eda112450edc4c3046197b442`.
- Tag: `reader-dev:release-qweb-20261031-5685c6e`.
- Binary SHA-256: `3138fe94ead9d6128052c477ab2c32ff13043c21e2167fd07136763dc0f06d2b`.
- Rollback tag: `reader-dev:rollback-qweb-20261031-5a34a0a`, preserving the prior
  `8b5008039383492e5b176e336fdccc15351ac98882b3909be28afb93370325f0` image.

The active database is `/data/storage/reader.db`; `/data/reader.db` is an empty
inactive file. Read-only aggregates immediately before/after the cutover matched:
users 2, books 2, book_sources 2619, book_chapters 13, toc_cache 11, database
53,485,568 bytes, existing chapter_order column present. No migration, manual
database restore or bulk cache deletion was performed. Existing application
startup backup/prune behavior remains in effect.

## Live acceptance and limits

Qiqi was restarted only through its existing `qqbot:0.0` / `nb run` process;
QWeb and the hosted Bridge became ready. Six already-running QWeb workers were
rebuilt and recreated individually; schema `20261026_0086`, compatibility and
all seven changed Python file hashes were verified per worker. The optional
dedicated Reader worker was not enabled. The Reader consumer remains hosted by
Qiqi. The existing shared Bot container and unrelated sessions stayed running.

Authenticated QWeb checks observed:

- 华娱情报王: 405 chapters, normal first chapter of 1,249 characters / 986 CJK;
  explicit refresh produced the same digest, without injected regex artifacts.
- 我的贴身校花: 11,677 chapters, normal first chapter of 2,619 characters / 1,888 CJK.
- 禁咒师短命？我拥有不死之身: old source still times out / lacks valid content.
  Metadata remains accessible. Re-source search returned 6 candidates with the
  explicit partial-search notice; a returned candidate resolved to the same
  approved source. No user's preference was switched by acceptance probes.
- Authorizations, managed-source grants, personal source preferences and the
  administrator's 10 reading-progress book IDs matched their pre-release hashes.
- Invalid selection handles return the intended HTTP 410 rejection.

Historical thousands-of-chapters cache was not recovered. A shelf count of zero
does not prove absence of orphaned chapter rows; cache probes for the two tested
QWeb books currently report idle/0. This release did not start full-book caching
or real exports merely for acceptance. Source availability and historical cache
recovery must not be described as universally fixed.

The complete QWeb frontend publication receipt, local/LAN/public asset checks,
runtime results and rollback identifiers are maintained in the Qiqi workspace:
`docs/reader-qweb-chain-recovery-20261031.md` and
`data/reader-chain-audit-20261031/release/`.

Rollback uses the retained compatible image and the same single-service Compose
recreation. It does not require deleting schema columns or restoring the whole
database. Review current user preferences and running jobs before any rollback.
