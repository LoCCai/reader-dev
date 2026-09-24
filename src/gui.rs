//! GUI 变体（feature = "gui"）：服务线程 + tao/wry 窗口 + 托盘常驻。
//!
//! 行为约定：
//! - 启动即拉起服务（空闲端口注入 config.port），窗口就绪后指向 http://127.0.0.1:{port}
//! - **关闭窗口默认收起到系统托盘**（不退出）；托盘菜单「显示主窗口 / 退出」
//! - `--headless` 参数强制纯服务模式（服务器场景复用同一二进制）

use anyhow::Result;
use tao::event::Event;
use tao::event_loop::ControlFlow;
use tao::window::{Window, WindowBuilder};
use tray_icon::TrayIconBuilder;

#[derive(Debug)]
enum GuiEvent {
    TrayShow,
    TrayQuit,
}

/// 选空闲端口（bind :0 后释放——极小竞态窗口可接受）
fn pick_free_port() -> std::io::Result<u16> {
    let l = std::net::TcpListener::bind(("127.0.0.1", 0))?;
    let port = l.local_addr()?.port();
    drop(l);
    Ok(port)
}

fn wait_ready(port: u16, timeout: std::time::Duration) -> bool {
    use std::net::TcpStream;
    let deadline = std::time::Instant::now() + timeout;
    while std::time::Instant::now() < deadline {
        if TcpStream::connect_timeout(
            &format!("127.0.0.1:{port}").parse().unwrap(),
            std::time::Duration::from_millis(400),
        )
        .is_ok()
        {
            return true;
        }
        std::thread::sleep(std::time::Duration::from_millis(150));
    }
    false
}

/// 应用数据目录（服务工作目录；Windows %APPDATA%\reader-dev，Unix XDG/home）
fn data_dir() -> std::path::PathBuf {
    #[cfg(windows)]
    {
        std::env::var_os("APPDATA")
            .map(std::path::PathBuf::from)
            .unwrap_or_else(std::env::temp_dir)
            .join("reader-dev")
    }
    #[cfg(not(windows))]
    {
        std::env::var_os("XDG_DATA_HOME")
            .map(std::path::PathBuf::from)
            .or_else(|| std::env::home_dir().map(|h| h.join(".local/share")))
            .unwrap_or_else(std::env::temp_dir)
            .join("reader-dev")
    }
}

/// 纯色托盘图标 RGBA（蓝底白块 32×32——无 PNG 解码依赖）
fn tray_icon_rgba() -> (Vec<u8>, u32, u32) {
    const S: u32 = 32;
    let m: i64 = 6;
    let mut rgba = Vec::with_capacity((S * S * 4) as usize);
    for y in 0..S {
        for x in 0..S {
            let inner = (m..(S as i64 - m)).contains(&(x as i64))
                && (m..(S as i64 - m)).contains(&(y as i64));
            if inner {
                rgba.extend_from_slice(&[255, 255, 255, 255]);
            } else {
                rgba.extend_from_slice(&[37, 99, 235, 255]);
            }
        }
    }
    (rgba, S, S)
}

/// GUI 入口：起服务线程，主线程窗口事件循环（Windows/macOS 要求主线程跑 loop）
pub fn run(mut config: reader_dev::AppConfig) -> Result<()> {
    let port = pick_free_port()?;
    config.port = port;
    let work = data_dir();
    std::fs::create_dir_all(&work).ok();

    // 服务线程：独立 tokio runtime（主线程被事件循环占用）
    let server_thread = std::thread::spawn(move || {
        let rt = tokio::runtime::Builder::new_multi_thread()
            .enable_all()
            .build()?;
        rt.block_on(config.serve())
    });

    if !wait_ready(port, std::time::Duration::from_secs(15)) {
        return Err(anyhow::anyhow!("服务端 15 秒内未就绪（端口 {port}）"));
    }

    let event_loop = tao::event_loop::EventLoopBuilder::<GuiEvent>::with_user_event().build();
    let proxy = event_loop.create_proxy();

    // 托盘：菜单（显示/退出）事件经 proxy 转 EventLoop user event
    use tray_icon::menu::{Menu, MenuEvent, MenuItem};
    let tray_menu = Menu::new();
    let show_item = MenuItem::with_id(
        "show",
        "\u{663e}\u{793a}\u{4e3b}\u{7a97}\u{53e3}",
        true,
        None,
    );
    let quit_item = MenuItem::with_id("quit", "\u{9000}\u{51fa}", true, None);
    tray_menu.append_items(&[&show_item, &quit_item])?;
    let (rgba, w, h) = tray_icon_rgba();
    let icon = tray_icon::Icon::from_rgba(rgba, w, h)?;
    let _tray = TrayIconBuilder::new()
        .with_menu(Box::new(tray_menu))
        .with_tooltip("reader-dev")
        .with_icon(icon)
        .build()?;

    // tray menu 事件转发线程 → EventLoopProxy
    let menu_proxy = proxy.clone();
    std::thread::spawn(move || {
        let receiver = MenuEvent::receiver();
        for ev in receiver.iter() {
            match ev.id().as_ref() {
                "show" => {
                    let _ = menu_proxy.send_event(GuiEvent::TrayShow);
                }
                "quit" => {
                    let _ = menu_proxy.send_event(GuiEvent::TrayQuit);
                }
                _ => {}
            }
        }
    });

    let window: Window = WindowBuilder::new()
        .with_title("reader-dev")
        .with_inner_size(tao::dpi::LogicalSize::new(1200.0, 800.0))
        .build(&event_loop)?;

    let webview = wry::WebView::new(&window, {
        let mut attrs = wry::WebViewAttributes::default();
        attrs.url = Some(format!("http://127.0.0.1:{port}/").parse()?);
        attrs
    })?;

    event_loop.run(move |event, _target, control_flow| {
        *control_flow = ControlFlow::Wait;
        match event {
            Event::UserEvent(GuiEvent::TrayShow) => {
                window.set_visible(true);
                let _ = window.set_focus();
            }
            Event::UserEvent(GuiEvent::TrayQuit) => {
                // 真正退出：杀服务线程进程树由 serve 结束/进程退出兜底
                *control_flow = ControlFlow::Exit;
            }
            Event::WindowEvent { event: e, .. } => match e {
                tao::event::WindowEvent::CloseRequested => {
                    // 默认收起到托盘：仅隐藏，不退出
                    window.set_visible(false);
                }
                tao::event::WindowEvent::Destroyed => {
                    // 窗口被外部销毁（如任务管理器关窗）→ 收托盘语义下仍保持驻留
                }
                _ => {}
            },
            _ => {
                let _ = &webview; // keep alive
            }
        }
    });

    // 事件循环退出 = 用户点了托盘退出：等服务线程收尾
    let _ = server_thread.join();
    Ok(())
}
