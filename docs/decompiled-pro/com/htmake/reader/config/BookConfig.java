/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.config.BookConfig
 *  kotlin.Metadata
 *  kotlin.io.FilesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package com.htmake.reader.config;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\f"}, d2={"Lcom/htmake/reader/config/BookConfig;", "", "()V", "epubInjectJavascript", "", "getEpubInjectJavascript", "()Ljava/lang/String;", "javascriptVersion", "getJavascriptVersion", "injectJavascriptToEpubChapter", "", "filePath", "reader-pro"})
public final class BookConfig {
    @NotNull
    public static final BookConfig INSTANCE = new BookConfig();
    @NotNull
    private static final String javascriptVersion = "reader-inject-javascript-1.9.0";
    @NotNull
    private static final String epubInjectJavascript = "\n    //<![CDATA[\n    // " + javascriptVersion + "\n    if (!window.reader_inited) {\n        function reader_notify(event, data, id) {\n            if (window.self !== window.top) {\n                window.top.postMessage(JSON.stringify({\n                    id: id,\n                    event: event,\n                    data: data\n                }), '*');\n            }\n        }\n\n        var reader_style_dom = document.createElement('style');\n        var head = document.head || document.getElementsByTagName('head')[0];\n        head.appendChild(reader_style_dom);\n\n        function reader_setStyle(style) {\n            reader_style_dom.textContent = style;\n            reader_notifySize();\n            setTimeout(reader_notifySize, 100);\n        }\n\n        function reader_notifySize() {\n            reader_notify(\"setHeight\", document.documentElement.scrollHeight || document.body.scrollHeight)\n            reader_notify(\"setWidth\", document.documentElement.scrollWidth || document.body.scrollWidth)\n        }\n\n        function reader_listenFromParent(event) {\n            reader_notify('received', {\n                data: event.data\n            })\n            let data;\n            try {\n                data = typeof event.data === 'string' ? JSON.parse(event.data) : event.data;\n            } catch (error) {\n                // console.error(error);\n                return;\n            }\n\n            if (!data) {\n                return;\n            }\n            reader_notify(\"data \", data);\n            if (data.event === 'setStyle') {\n                reader_setStyle(data.style);\n            } else if (data.event === 'execute') {\n                eval(data.script);\n            } else if (data.id) {\n                if (window.nativeCallback[data.id]) {\n                    window.nativeCallback[data.id](data);\n                    delete window.nativeCallback[data.id]\n                }\n            }\n        }\n\n\n        function reader_getLinkElement(element) {\n            if (!element || !element.nodeName) {\n                return false;\n            }\n            if (element.nodeName.toLowerCase() === \"a\") {\n                return element;\n            }\n            return reader_getLinkElement(element.parentNode)\n        }\n\n        function reader_getImageElement(element) {\n            if (!element || !element.nodeName) {\n                return false;\n            }\n            if (element.nodeName.toLowerCase() === \"img\") {\n                return element;\n            }\n        }\n\n        window.document.addEventListener('message', reader_listenFromParent);\n        window.addEventListener('message', reader_listenFromParent);\n        window.addEventListener('load', function() {\n            reader_notifySize();\n            reader_notify(\"load\", window.location.href);\n        });\n        window.addEventListener('resize', reader_notifySize);\n        document.addEventListener('DOMNodeInserted', reader_notifySize, false);\n        document.addEventListener('click', function(event) {\n            var linkElement = reader_getLinkElement(event.target)\n            var imageElement = reader_getImageElement(event.target)\n            if (linkElement) {\n                // \u70b9\u51fb\u94fe\u63a5\u8df3\u8f6c\n                if (linkElement.pathname === window.location.pathname) {\n                    // \u9875\u5185\u8df3\u8f6c\n                    var hashElement = document.querySelector(linkElement.hash)\n                    if (hashElement) {\n                        reader_notify(\"clickHash\", hashElement.getBoundingClientRect())\n                    }\n                } else {\n                    // \u8df3\u8f6c\u5176\u4ed6\u9875\u9762\n                    reader_notify(\"clickA\", event.target.href);\n                }\n            } else if (imageElement) {\n                var imgList = document.querySelectorAll(\"img\");\n                if (imgList.length) {\n                    var imgUrlList = [];\n                    var index = 0;\n                    for (let i = 0; i < imgList.length; i++) {\n                        imgUrlList.push(imgList[i].src);\n                        if (imgList[i] === imageElement) {\n                            index = i;\n                        }\n                    }\n                    reader_notify(\"previewImageList\", {\n                        imageList: imgUrlList,\n                        imageIndex: index\n                    });\n                }\n            } else {\n                reader_notify(\"click\", {\n                    target: event.target.nodeName,\n                    clientX: event.clientX,\n                    clientY: event.clientY\n                });\n            }\n        });\n        document.addEventListener(\"touchstart\", function(event) {\n            // console.log(event)\n            reader_notify(\"touchstart\", {\n                target: event.target.nodeName,\n                touches: [{\n                    clientX: event.touches[0].clientX,\n                    clientY: event.touches[0].clientY,\n                }]\n            });\n        });\n        document.addEventListener(\"touchmove\", function(event) {\n            // console.log(event)\n            event.preventDefault && event.preventDefault();\n            event.stopPropagation && event.stopPropagation();\n            reader_notify(\"touchmove\", {\n                target: event.target.nodeName,\n                touches: [{\n                    clientX: event.touches[0].clientX,\n                    clientY: event.touches[0].clientY,\n                }]\n            });\n\n        });\n        document.addEventListener(\"touchend\", function(event) {\n            // console.log(event)\n            reader_notify(\"touchend\", {\n                target: event.target.nodeName\n            });\n        });\n        window.addEventListener(\"keydown\", function(event) {\n            event.preventDefault();\n            event.stopPropagation();\n            reader_notify(\"keydown\", {\n                key: event.key,\n                keyCode: event.keyCode\n            });\n        });\n        reader_notify(\"inited\");\n\n        window.reader_inited = true;\n    }\n    //]]>\n    ";

    private BookConfig() {
    }

    @NotNull
    public final String getJavascriptVersion() {
        return javascriptVersion;
    }

    @NotNull
    public final String getEpubInjectJavascript() {
        return epubInjectJavascript;
    }

    public final void injectJavascriptToEpubChapter(@NotNull String filePath) {
        String content;
        Intrinsics.checkNotNullParameter((Object)filePath, (String)"filePath");
        File file = new File(filePath);
        if (file.exists() && StringsKt.indexOf$default((CharSequence)(content = FilesKt.readText$default((File)file, null, (int)1, null)), (String)javascriptVersion, (int)0, (boolean)false, (int)6, null) < 0) {
            content = StringsKt.replace$default((String)content, (String)"<head>", (String)("<head><script type=\"text/javascript\">" + epubInjectJavascript + "</script>"), (boolean)false, (int)4, null);
            FilesKt.writeText$default((File)file, (String)content, null, (int)2, null);
        }
    }
}

