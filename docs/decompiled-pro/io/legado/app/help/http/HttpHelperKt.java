/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.help.http.HttpHelperKt
 *  io.legado.app.help.http.HttpHelperKt$getProxyClient$proxyAuthenticator$1
 *  io.legado.app.help.http.HttpHelperKt$okHttpClient$2
 *  io.legado.app.help.http.HttpHelperKt$proxyClientCache$2
 *  io.legado.app.model.DebugLog
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlin.sequences.Sequence
 *  kotlin.sequences.SequencesKt
 *  kotlin.text.MatchResult
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  okhttp3.Authenticator
 *  okhttp3.Interceptor
 *  okhttp3.OkHttpClient
 *  okhttp3.OkHttpClient$Builder
 *  okhttp3.logging.HttpLoggingInterceptor
 *  okhttp3.logging.HttpLoggingInterceptor$Level
 *  okhttp3.logging.HttpLoggingInterceptor$Logger
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.help.http;

import io.legado.app.help.http.HttpHelperKt;
import io.legado.app.model.DebugLog;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\f\u001a\u00020\u00012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\"\u001b\u0010\u0000\u001a\u00020\u00018FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003\"'\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00078BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0005\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0010"}, d2={"okHttpClient", "Lokhttp3/OkHttpClient;", "getOkHttpClient", "()Lokhttp3/OkHttpClient;", "okHttpClient$delegate", "Lkotlin/Lazy;", "proxyClientCache", "Ljava/util/concurrent/ConcurrentHashMap;", "", "getProxyClientCache", "()Ljava/util/concurrent/ConcurrentHashMap;", "proxyClientCache$delegate", "getProxyClient", "proxy", "debugLog", "Lio/legado/app/model/DebugLog;", "reader-pro"})
public final class HttpHelperKt {
    @NotNull
    private static final Lazy proxyClientCache$delegate = LazyKt.lazy((Function0)((Function0)proxyClientCache.2.INSTANCE));
    @NotNull
    private static final Lazy okHttpClient$delegate = LazyKt.lazy((Function0)((Function0)okHttpClient.2.INSTANCE));

    private static final ConcurrentHashMap<String, OkHttpClient> getProxyClientCache() {
        Lazy lazy = proxyClientCache$delegate;
        Object var1_1 = null;
        boolean bl = false;
        return (ConcurrentHashMap)lazy.getValue();
    }

    @NotNull
    public static final OkHttpClient getOkHttpClient() {
        Lazy lazy = okHttpClient$delegate;
        Object var1_1 = null;
        boolean bl = false;
        return (OkHttpClient)lazy.getValue();
    }

    @NotNull
    public static final OkHttpClient getProxyClient(@Nullable String proxy, @Nullable DebugLog debugLog) {
        OkHttpClient.Builder builder;
        CharSequence charSequence = proxy;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || StringsKt.isBlank((CharSequence)charSequence)) {
            if (debugLog == null) {
                return HttpHelperKt.getOkHttpClient();
            }
            builder = HttpHelperKt.getOkHttpClient().newBuilder();
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor((HttpLoggingInterceptor.Logger)debugLog);
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor((Interceptor)logInterceptor);
            return builder.build();
        }
        if (debugLog == null && (builder = (OkHttpClient)HttpHelperKt.getProxyClientCache().get(proxy)) != null) {
            OkHttpClient.Builder logInterceptor = builder;
            bl2 = false;
            boolean bl3 = false;
            OkHttpClient.Builder it = logInterceptor;
            boolean bl4 = false;
            return it;
        }
        Regex r = new Regex("(http|socks4|socks5)://(.*):(\\d{2,5})(@.*@.*)?");
        Sequence ms = Regex.findAll$default((Regex)r, (CharSequence)proxy, (int)0, (int)2, null);
        MatchResult group = (MatchResult)SequencesKt.first((Sequence)ms);
        Ref.ObjectRef username = new Ref.ObjectRef();
        username.element = "";
        Ref.ObjectRef password = new Ref.ObjectRef();
        password.element = "";
        String type = Intrinsics.areEqual(group.getGroupValues().get(1), (Object)"http") ? "http" : "socks";
        String host = (String)group.getGroupValues().get(2);
        String[] stringArray = (String[])group.getGroupValues().get(3);
        boolean bl5 = false;
        int port = Integer.parseInt((String)stringArray);
        if (!Intrinsics.areEqual(group.getGroupValues().get(4), (Object)"")) {
            stringArray = new String[]{"@"};
            username.element = StringsKt.split$default((CharSequence)((CharSequence)group.getGroupValues().get(4)), (String[])stringArray, (boolean)false, (int)0, (int)6, null).get(1);
            stringArray = new String[]{"@"};
            password.element = StringsKt.split$default((CharSequence)((CharSequence)group.getGroupValues().get(4)), (String[])stringArray, (boolean)false, (int)0, (int)6, null).get(2);
        }
        if (!Intrinsics.areEqual((Object)type, (Object)"direct") && !Intrinsics.areEqual((Object)host, (Object)"")) {
            OkHttpClient.Builder builder2 = HttpHelperKt.getOkHttpClient().newBuilder();
            if (Intrinsics.areEqual((Object)type, (Object)"http")) {
                builder2.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port)));
            } else {
                builder2.proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(host, port)));
            }
            if (!Intrinsics.areEqual((Object)username.element, (Object)"") && !Intrinsics.areEqual((Object)password.element, (Object)"")) {
                getProxyClient.proxyAuthenticator.1 proxyAuthenticator2 = new /* Unavailable Anonymous Inner Class!! */;
                builder2.proxyAuthenticator((Authenticator)proxyAuthenticator2);
            }
            if (debugLog != null) {
                HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor((HttpLoggingInterceptor.Logger)debugLog);
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder2.addNetworkInterceptor((Interceptor)logInterceptor);
                return builder2.build();
            }
            OkHttpClient proxyClient = builder2.build();
            Map map = HttpHelperKt.getProxyClientCache();
            boolean bl6 = false;
            map.put(proxy, proxyClient);
            return proxyClient;
        }
        return HttpHelperKt.getOkHttpClient();
    }

    public static /* synthetic */ OkHttpClient getProxyClient$default(String string, DebugLog debugLog, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            debugLog = null;
        }
        return HttpHelperKt.getProxyClient((String)string, (DebugLog)debugLog);
    }
}

