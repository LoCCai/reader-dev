# Bot API（/bot/v1）—— 七七等程序化客户端专用

> 一句话：免登录、真实状态码、一条龙（搜索 → 详情/目录 → 正文 → 换源 → 下载打包 txt/epub）。
> 基地址 `http://192.168.31.193:10002`，文档内 `{BASE}` 均指它。

## 鉴权

- `Authorization: Bearer <API_KEY>`（推荐），或查询参数 `?apiKey=<API_KEY>`（封面直链已内嵌）。
- Key 与命名空间绑定（服务端 env `READER_BOT_API_KEYS`，格式 `key:namespace`）；七七的 Key 绑定 `QIQIBot` 命名空间——书源、缓存、书架数据与网页版 `QIQIBot` 账号互通。
- **不再需要 /reader3/login**：没有 token 轮换、没有会话互挤、不会 401 重登风暴。
- 无 Key/错 Key → `401`；同 Key 并发超过 4 → `429`（稍后重试）。

响应统一为：

```json
{ "ok": true,  "data": { ... } }
{ "ok": false, "error": { "code": "...", "message": "..." } }
```

状态码真实反映结果（400 参数错 / 401 鉴权 / 404 不存在 / 409 状态冲突 / 429 并发满 / 502 上游书源失败）。

## 端点总览

| 端点 | 方法 | 说明 |
|---|---|---|
| `/bot/v1/health` | GET | 版本/代理状态（免鉴权） |
| `/bot/v1/search` | POST | 聚合多源搜索（去重 + 每源统计 + 封面直链） |
| `/bot/v1/cover` | GET | 封面图片字节（搜索结果的 `coverApi` 直接 GET） |
| `/bot/v1/book/info` | POST | 书籍详情（书架书含进度；未入架需 bookSourceUrl） |
| `/bot/v1/book/toc` | POST | 目录（含卷标行，`realChapters` 为真实章数） |
| `/bot/v1/book/content` | POST | **按 index 取正文**（自动查目录，缓存优先，miss 回源） |
| `/bot/v1/book/changeSource` | POST | 换源：查候选 / 自动选优 / 指定切换（含进度重定位） |
| `/bot/v1/book/download` | POST | 下载打包任务（txt/epub，后台并发抓章） |
| `/bot/v1/jobs` | GET | 最近任务列表 |
| `/bot/v1/jobs/{taskId}` | GET/POST | 任务状态 / 取消 |
| `/bot/v1/jobs/{taskId}/file` | GET | 下载产物文件（status=done 后） |

## 典型调用链（python-httpx 示例）

```python
import httpx, time

BASE = "http://192.168.31.193:10002"
KEY = "<七七的 API_KEY>"
H = {"Authorization": f"Bearer {KEY}"}
c = httpx.Client(base_url=BASE, headers=H, timeout=120)

# 1) 搜索（exact=1 精确匹配书名； "=" 前缀同理）
r = c.post("/bot/v1/search", json={"key": "我的妹妹与众不同", "exact": 1}).json()["data"]
book = r["results"][0]           # 多源命中的书排最前（sourceCount 越多越稳）
book_url = book["bookUrl"]; src = book["origin"]
cover = book["coverApi"]         # 绝对 URL，直接 httpx.get(cover) 拿图片字节

# 2) 目录
toc = c.post("/bot/v1/book/toc", json={"bookUrl": book_url, "bookSourceUrl": src}).json()["data"]
print(toc["realChapters"], "章")  # chapters[i] 的 index/isVolume/url 直接可用于下一步

# 3) 第 1 章正文（index 为目录原始下标；卷标行 isVolume=true 不能取正文）
chap = c.post("/bot/v1/book/content",
              json={"bookUrl": book_url, "bookSourceUrl": src, "index": 1}).json()["data"]
print(chap["title"], chap["content"][:100])

# 4) 打包下载整本 txt（epub 同理 format:"epub"；可 from/to 截范围，0 基闭区间）
job = c.post("/bot/v1/book/download",
             json={"bookUrl": book_url, "bookSourceUrl": src, "format": "txt"}).json()["data"]
while True:
    st = c.get(f"/bot/v1/jobs/{job['taskId']}").json()["data"]
    if st["status"] != "running": break
    print(st["done"], "/", st["total"]); time.sleep(2)
txt = c.get(f"/bot/v1/jobs/{job['taskId']}/file").text
```

## 各端点参数与返回细节

### POST /bot/v1/search

请求：`{key, exact?, group?, maxSources?, page?}`（均可省，默认聚合全源、上限 60 源）。

返回 `data`：

- `results[]`：SearchBook（name/author/kind/intro/wordCount/latestChapterTitle/coverUrl/tocUrl/origin/originName/bookUrl）＋ 两列扩展：
  - `sourceCount`：同名同作者有几个源命中（>1 表示这本书多源可换，最稳）
  - `coverApi`：封面直链（内嵌 apiKey，直接 GET 得图片字节）
- `sources`：`{succeeded, failed[{name,url,error}], skippedInvalid}`——哪些源挂了一目了然
- `tookMs`：耗时

排序：`sourceCount` 降序（多源书优先）；同书多源时保留 use_count+weight 最高的源为首。

### POST /bot/v1/book/info

`{bookUrl, bookSourceUrl?}`——书架书直接回书架数据（含 durChapterIndex 进度）；未入架必须带 `bookSourceUrl`（搜索结果的 `origin`），实时解析详情。

### POST /bot/v1/book/toc

`{bookUrl, bookSourceUrl?, refresh?}`——`chapters[]` 含卷标行（`isVolume:true`）；`total` 为原始条数、`realChapters` 为过滤卷标后的真实章数。目录缓存 5 分钟，`refresh:true` 强制回源。

### POST /bot/v1/book/content

`{bookUrl, index, bookSourceUrl?}`——按目录原始下标取正文。index 指向卷标行 → 400。缓存命中零网络；miss 自动回源抓取并落缓存（下次秒回）。

### POST /bot/v1/book/changeSource

三种用法：

1. `{bookUrl}` → 只返回候选 `candidates[]`（use_count+weight 排序，含 `newBookUrl`/`bookSourceUrl` 可直接用于方式 3）
2. `{bookUrl, autoPick:true}` → 自动切到最优候选
3. `{bookUrl, newBookUrl, bookSourceUrl}` → 切到指定源

切换后自动做**进度重定位**（旧章节标题匹配 → 就近钳制，策略与网页端一致）并写回；返回 `{book, progress:{oldIndex,newIndex,oldTitle,relocated}}`。

注意：换源是书架操作，书必须已入架（legacy `POST /reader3/saveBook`，或先用 bot 端点入架后再换）。

### POST /bot/v1/book/download

`{bookUrl, bookSourceUrl?, from?, to?, format?}`——`format` 默认 `txt`（可选 `epub`）；`from/to` 为**实章 0 基闭区间**（卷标已剔除）。后台并发 4 抓章（缓存命中跳过抓取），返回 `{taskId, total, poll}`。

任务状态 `GET /bot/v1/jobs/{taskId}`：`{status: running|done|error, done, total, failedChapters[], error}`；`POST` 同路径为取消。`status=done` 后 `GET /bot/v1/jobs/{taskId}/file` 下载文件（附件头带《书名》作者：xxx.txt）。

产物文件保留 3 天自动清理；服务重启后任务表清空（内存态）但已生成的文件若仍在保留期内可由管理员从 `data/storage/data/QIQIBot/bot-exports/` 取回。

## 与 legacy /reader3 的关系

- `/reader3/*` 全部保持原样（网页端、Legado App、WebDAV、OPDS 不受影响）
- bot API 只是**新增**路由组，进程内复用同一套书源引擎/缓存/导出管线
- bot 下载产生的章节缓存与正文缓存互通：bot 下载过的书，网页端/Legado 打开同一本书直接命中缓存

## 运维备注（部署在 docker/reader-dev）

- Key 配置：`docker-compose.yml` 的 `READER_BOT_API_KEYS`（格式 `key1:ns1,key2:ns2`），改后 `docker compose up -d` 生效
- 出网代理：`READER_HTTP_PROXY` / `READER_CAMOUFOX_PROXY`（当前走宿主 clash 7890）
- 封面直链基地址：`READER_PUBLIC_URL`（当前 `http://192.168.31.193:10002`）
- 回滚：`docker tag reader-dev:local-rollback reader-dev:local && docker compose up -d`
