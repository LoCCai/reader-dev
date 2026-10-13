/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.init.ReaderAdapter
 *  com.htmake.reader.utils.ExtKt
 *  com.htmake.reader.utils.RemoteWebview
 *  io.legado.app.adapters.ReaderAdapterInterface
 *  io.legado.app.help.http.StrResponse
 *  io.legado.app.model.DebugLog
 *  kotlin.Metadata
 *  kotlin.coroutines.Continuation
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.init;

import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.RemoteWebview;
import io.legado.app.adapters.ReaderAdapterInterface;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u001f\u0010\u0005\u001a\u00020\u00042\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0007\"\u00020\u0004\u00a2\u0006\u0002\u0010\bJ\u0091\u0001\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00042\b\u0010\f\u001a\u0004\u0018\u00010\u00042\b\u0010\r\u001a\u0004\u0018\u00010\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\u00042\b\u0010\u0013\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0017\u001a\u00020\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ!\u0010\u001b\u001a\u00020\u00042\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0007\"\u00020\u0004H\u0016\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u0004H\u0016\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001d"}, d2={"Lcom/htmake/reader/init/ReaderAdapter;", "Lio/legado/app/adapters/ReaderAdapterInterface;", "()V", "getCacheDir", "", "getRelativePath", "subDirFiles", "", "([Ljava/lang/String;)Ljava/lang/String;", "getStrResponseByRemoteWebview", "Lio/legado/app/help/http/StrResponse;", "url", "html", "encode", "tag", "headerMap", "", "sourceRegex", "javaScript", "proxy", "post", "", "body", "userNameSpace", "debugLog", "Lio/legado/app/model/DebugLog;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Lio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWorkDir", "subPath", "reader-pro"})
public final class ReaderAdapter
implements ReaderAdapterInterface {
    @NotNull
    public static final ReaderAdapter INSTANCE = new ReaderAdapter();

    private ReaderAdapter() {
    }

    @NotNull
    public String getWorkDir(@NotNull String subPath) {
        Intrinsics.checkNotNullParameter((Object)subPath, (String)"subPath");
        return ExtKt.getWorkDir((String)subPath);
    }

    @NotNull
    public String getWorkDir(String ... subDirFiles) {
        Intrinsics.checkNotNullParameter((Object)subDirFiles, (String)"subDirFiles");
        return ExtKt.getWorkDir((String)this.getRelativePath(Arrays.copyOf(subDirFiles, subDirFiles.length)));
    }

    @NotNull
    public final String getRelativePath(String ... subDirFiles) {
        String string;
        String string2;
        Intrinsics.checkNotNullParameter((Object)subDirFiles, (String)"subDirFiles");
        StringBuilder path = new StringBuilder("");
        String[] $this$forEach$iv = subDirFiles;
        boolean $i$f$forEach = false;
        String[] stringArray = $this$forEach$iv;
        int n = stringArray.length;
        for (int i = 0; i < n; ++i) {
            String element$iv;
            String it = element$iv = stringArray[i];
            boolean bl = false;
            CharSequence charSequence = it;
            boolean bl2 = false;
            if (!(charSequence.length() > 0)) continue;
            path.append(File.separator).append(it);
        }
        String string3 = path.toString();
        boolean bl = false;
        boolean bl3 = false;
        String it = string3;
        boolean bl4 = false;
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        if (StringsKt.startsWith$default((String)it, (String)"/", (boolean)false, (int)2, null)) {
            string2 = it;
            int n2 = 1;
            boolean bl5 = false;
            String string4 = string2.substring(n2);
            string = string4;
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"(this as java.lang.String).substring(startIndex)");
        } else {
            string = string2 = it;
        }
        return string;
    }

    @NotNull
    public String getCacheDir() {
        String[] stringArray = new String[]{"storage", "cache"};
        return this.getWorkDir(stringArray);
    }

    @Nullable
    public Object getStrResponseByRemoteWebview(@Nullable String url2, @Nullable String html, @Nullable String encode, @Nullable String tag, @Nullable Map<String, String> headerMap, @Nullable String sourceRegex, @Nullable String javaScript, @Nullable String proxy, boolean post, @Nullable String body, @NotNull String userNameSpace, @Nullable DebugLog debugLog, @NotNull Continuation<? super StrResponse> $completion) {
        String encodeStr = encode;
        Map<String, String> map = encode;
        boolean bl = false;
        boolean bl2 = false;
        if (map == null || map.length() == 0) {
            map = headerMap;
            encodeStr = map == null ? null : (String)map.get("charset");
        }
        return RemoteWebview.INSTANCE.getStrResponse(url2, html, encodeStr, tag, headerMap, sourceRegex, javaScript, proxy, post, body, userNameSpace, debugLog, $completion);
    }
}

