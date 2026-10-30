//! 响应安全头中间件（LOW-9）
//!
//! 为 HTML/JSON 响应补充基础安全响应头（按 Content-Type 判定，不影响二进制/图片等）：
//!
//! | 响应 Content-Type 前缀 | 追加响应头 |
//! | --- | --- |
//! | `text/html`、`application/xhtml+xml`、`application/json` | `X-Content-Type-Options: nosniff` + `X-Frame-Options: SAMEORIGIN` |
//!
//! - nosniff：禁止浏览器对声明类型做 MIME 嗅探——用户可控的 JSON/HTML（如
//!   file/get 返回的文本、上传文件预览）不会被嗅探成可执行类型；
//! - SAMEORIGIN：页面只允许同源 iframe 嵌入，防点击劫持（阅读器前端无跨站
//!   嵌入需求）；
//! - 已带同名头的响应不覆盖（调用方显式设置优先）。

use std::future::Future;
use std::pin::Pin;
use std::task::{Context, Poll};

use axum::body::Body;
use axum::http::{header, HeaderValue, Request, Response};
use tower::{Layer, Service};

const NOSNIFF: HeaderValue = HeaderValue::from_static("nosniff");
const SAMEORIGIN: HeaderValue = HeaderValue::from_static("SAMEORIGIN");

/// 响应是否为 HTML/JSON（按 Content-Type 首段判定，容忍 "; charset=..." 后缀）
fn needs_security_headers(content_type: Option<&HeaderValue>) -> bool {
    content_type
        .and_then(|v| v.to_str().ok())
        .map(|v| v.split(';').next().unwrap_or(v).trim().to_ascii_lowercase())
        .is_some_and(|ct| {
            matches!(
                ct.as_str(),
                "text/html" | "application/xhtml+xml" | "application/json"
            )
        })
}

/// 挂载用 Layer：`router.layer(SecurityHeadersLayer)`
#[derive(Debug, Clone, Copy, Default)]
pub struct SecurityHeadersLayer;

impl<S> Layer<S> for SecurityHeadersLayer {
    type Service = SecurityHeaders<S>;

    fn layer(&self, inner: S) -> Self::Service {
        SecurityHeaders { inner }
    }
}

/// 为响应追加安全头的 Service 包装
#[derive(Debug, Clone)]
pub struct SecurityHeaders<S> {
    inner: S,
}

impl<S> Service<Request<Body>> for SecurityHeaders<S>
where
    S: Service<Request<Body>, Response = Response<Body>> + Clone + Send + 'static,
    S::Future: Send + 'static,
{
    type Response = Response<Body>;
    type Error = S::Error;
    type Future = Pin<Box<dyn Future<Output = Result<Self::Response, Self::Error>> + Send>>;

    fn poll_ready(&mut self, cx: &mut Context<'_>) -> Poll<Result<(), Self::Error>> {
        self.inner.poll_ready(cx)
    }

    fn call(&mut self, req: Request<Body>) -> Self::Future {
        let fut = self.inner.call(req);
        Box::pin(async move {
            let mut resp = fut.await?;
            if needs_security_headers(resp.headers().get(header::CONTENT_TYPE)) {
                if !resp.headers().contains_key(header::X_CONTENT_TYPE_OPTIONS) {
                    resp.headers_mut()
                        .insert(header::X_CONTENT_TYPE_OPTIONS, NOSNIFF);
                }
                if !resp.headers().contains_key(header::X_FRAME_OPTIONS) {
                    resp.headers_mut().insert(header::X_FRAME_OPTIONS, SAMEORIGIN);
                }
            }
            Ok(resp)
        })
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use axum::http::StatusCode;
    use tower::{ServiceBuilder, ServiceExt};

    async fn call_with(content_type: Option<&'static str>) -> Response<Body> {
        let backend = tower::service_fn(move |_: Request<Body>| async move {
            let mut b = axum::response::Response::builder().status(StatusCode::OK);
            if let Some(ct) = content_type {
                b = b.header(header::CONTENT_TYPE, ct);
            }
            Ok::<_, std::convert::Infallible>(b.body(Body::empty()).unwrap())
        });
        let mut svc = ServiceBuilder::new()
            .layer(SecurityHeadersLayer)
            .service(backend);
        svc.ready()
            .await
            .unwrap()
            .call(Request::builder().uri("/").body(Body::empty()).unwrap())
            .await
            .unwrap()
    }

    /// HTML/JSON → nosniff + SAMEORIGIN（含 charset 后缀与 XHTML）
    #[tokio::test]
    async fn html_and_json_get_both_headers() {
        for ct in [
            "text/html",
            "text/html; charset=utf-8",
            "application/xhtml+xml",
            "application/json",
            "application/json; charset=utf-8",
        ] {
            let resp = call_with(Some(ct)).await;
            assert_eq!(
                resp.headers().get(header::X_CONTENT_TYPE_OPTIONS).unwrap(),
                "nosniff",
                "{ct}"
            );
            assert_eq!(
                resp.headers().get(header::X_FRAME_OPTIONS).unwrap(),
                "SAMEORIGIN",
                "{ct}"
            );
        }
    }

    /// 其他类型（图片/二进制/XML/无 Content-Type）不加头
    #[tokio::test]
    async fn other_types_untouched() {
        for ct in [
            Some("image/png"),
            Some("application/octet-stream"),
            Some("application/xml; charset=utf-8"),
            Some("text/plain; charset=utf-8"),
            None,
        ] {
            let resp = call_with(ct).await;
            assert!(
                !resp.headers().contains_key(header::X_CONTENT_TYPE_OPTIONS),
                "{ct:?}"
            );
            assert!(!resp.headers().contains_key(header::X_FRAME_OPTIONS), "{ct:?}");
        }
    }
}
