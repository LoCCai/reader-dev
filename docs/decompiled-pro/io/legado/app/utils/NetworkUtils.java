/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.NetworkUtils
 *  io.legado.app.utils.NetworkUtils$notNeedEncoding$2
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  okhttp3.HttpUrl
 *  okhttp3.Request
 *  okhttp3.Response
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  retrofit2.Response
 */
package io.legado.app.utils;

import io.legado.app.utils.NetworkUtils;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.BitSet;
import java.util.Enumeration;
import java.util.regex.Pattern;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\rJ\u0018\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0010\u001a\u00020\rJ\u0012\u0010\u0011\u001a\u0004\u0018\u00010\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\rJ\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u0010\u0010\u0015\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\rJ\u0012\u0010\u0016\u001a\u00020\r2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0018J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\rJ\u0010\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u000e\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\rR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t\u00a8\u0006!"}, d2={"Lio/legado/app/utils/NetworkUtils;", "", "()V", "IPV4_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "notNeedEncoding", "Ljava/util/BitSet;", "getNotNeedEncoding", "()Ljava/util/BitSet;", "notNeedEncoding$delegate", "Lkotlin/Lazy;", "getAbsoluteURL", "", "baseURL", "Ljava/net/URL;", "relativePath", "getBaseUrl", "url", "getLocalIPAddress", "Ljava/net/InetAddress;", "getSubDomain", "getUrl", "response", "Lretrofit2/Response;", "hasUrlEncoded", "", "str", "isDigit16Char", "c", "", "isIPv4Address", "input", "reader-pro"})
public final class NetworkUtils {
    @NotNull
    public static final NetworkUtils INSTANCE = new NetworkUtils();
    @NotNull
    private static final Lazy notNeedEncoding$delegate = LazyKt.lazy((Function0)((Function0)notNeedEncoding.2.INSTANCE));
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

    private NetworkUtils() {
    }

    @NotNull
    public final String getUrl(@NotNull retrofit2.Response<?> response2) {
        HttpUrl httpUrl;
        Request request;
        Response networkResponse;
        Intrinsics.checkNotNullParameter(response2, (String)"response");
        Response response3 = networkResponse = response2.raw().networkResponse();
        String string = response3 == null ? null : ((request = response3.request()) == null ? null : ((httpUrl = request.url()) == null ? null : httpUrl.toString()));
        return string == null ? response2.raw().request().url().toString() : string;
    }

    private final BitSet getNotNeedEncoding() {
        Lazy lazy = notNeedEncoding$delegate;
        boolean bl = false;
        return (BitSet)lazy.getValue();
    }

    public final boolean hasUrlEncoded(@NotNull String str) {
        Intrinsics.checkNotNullParameter((Object)str, (String)"str");
        boolean needEncode = false;
        int i = 0;
        while (i < str.length()) {
            int c;
            int n = c = str.charAt(i);
            boolean bl = false;
            if (this.getNotNeedEncoding().get(n)) {
                n = i;
                i = n + 1;
                continue;
            }
            if (c == 37 && i + 2 < str.length()) {
                char c1 = str.charAt(++i);
                char c2 = str.charAt(++i);
                if (this.isDigit16Char(c1) && this.isDigit16Char(c2)) {
                    int n2 = i;
                    i = n2 + 1;
                    continue;
                }
            }
            needEncode = true;
            break;
        }
        return !needEncode;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean isDigit16Char(char c) {
        boolean bl;
        boolean bl2;
        if ('0' <= c) {
            if (c <= '9') {
                return true;
            }
            bl2 = false;
        } else {
            bl2 = false;
        }
        if (bl2) return true;
        if ('A' <= c) {
            if (c <= 'F') {
                return true;
            }
            bl = false;
        } else {
            bl = false;
        }
        if (bl) return true;
        if ('a' > c) return false;
        if (c > 'f') return false;
        return true;
    }

    @NotNull
    public final String getAbsoluteURL(@Nullable String baseURL, @NotNull String relativePath) {
        Intrinsics.checkNotNullParameter((Object)relativePath, (String)"relativePath");
        CharSequence charSequence = baseURL;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || charSequence.length() == 0) {
            return relativePath;
        }
        charSequence = relativePath;
        bl = false;
        bl2 = false;
        if (charSequence.length() == 0) {
            return baseURL;
        }
        String relativeUrl = relativePath;
        try {
            URL absoluteUrl = new URL(StringsKt.substringBefore$default((String)baseURL, (String)",", null, (int)2, null));
            URL parseUrl = new URL(absoluteUrl, relativePath);
            String string = parseUrl.toString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"parseUrl.toString()");
            relativeUrl = string;
            return relativeUrl;
        }
        catch (Exception e) {
            e.printStackTrace();
            return relativeUrl;
        }
    }

    @NotNull
    public final String getAbsoluteURL(@Nullable URL baseURL, @NotNull String relativePath) {
        Intrinsics.checkNotNullParameter((Object)relativePath, (String)"relativePath");
        if (baseURL == null) {
            return relativePath;
        }
        String relativeUrl = relativePath;
        try {
            URL parseUrl = new URL(baseURL, relativePath);
            String string = parseUrl.toString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"parseUrl.toString()");
            relativeUrl = string;
            return relativeUrl;
        }
        catch (Exception e) {
            e.printStackTrace();
            return relativeUrl;
        }
    }

    @Nullable
    public final String getBaseUrl(@Nullable String url2) {
        String string;
        if (url2 == null || !StringsKt.startsWith$default((String)url2, (String)"http", (boolean)false, (int)2, null)) {
            return null;
        }
        int index = StringsKt.indexOf$default((CharSequence)url2, (String)"/", (int)9, (boolean)false, (int)4, null);
        if (index == -1) {
            string = url2;
        } else {
            String string2 = url2;
            int n = 0;
            boolean bl = false;
            String string3 = string2.substring(n, index);
            string = string3;
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        }
        return string;
    }

    @NotNull
    public final String getSubDomain(@Nullable String url2) {
        String string;
        String string2 = this.getBaseUrl(url2);
        if (string2 == null) {
            return "";
        }
        String baseUrl = string2;
        if (StringsKt.indexOf$default((CharSequence)baseUrl, (String)".", (int)0, (boolean)false, (int)6, null) == StringsKt.lastIndexOf$default((CharSequence)baseUrl, (String)".", (int)0, (boolean)false, (int)6, null)) {
            string2 = baseUrl;
            int n = StringsKt.lastIndexOf$default((CharSequence)baseUrl, (String)"/", (int)0, (boolean)false, (int)6, null) + 1;
            boolean bl = false;
            String string3 = string2.substring(n);
            string = string3;
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.String).substring(startIndex)");
        } else {
            string2 = baseUrl;
            int n = StringsKt.indexOf$default((CharSequence)baseUrl, (String)".", (int)0, (boolean)false, (int)6, null) + 1;
            boolean bl = false;
            String string4 = string2.substring(n);
            string = string4;
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"(this as java.lang.String).substring(startIndex)");
        }
        return string;
    }

    @Nullable
    public final InetAddress getLocalIPAddress() {
        Enumeration<NetworkInterface> enumeration = null;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                NetworkInterface nif = enumeration.nextElement();
                Enumeration<InetAddress> addresses = nif.getInetAddresses();
                if (addresses == null) continue;
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address.isLoopbackAddress()) continue;
                    String string = address.getHostAddress();
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"address.hostAddress");
                    if (!this.isIPv4Address(string)) continue;
                    return address;
                }
            }
        }
        return null;
    }

    public final boolean isIPv4Address(@NotNull String input) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        return IPV4_PATTERN.matcher(input).matches();
    }
}

