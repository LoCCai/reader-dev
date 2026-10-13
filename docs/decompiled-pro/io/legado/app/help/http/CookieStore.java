/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.adapters.ReaderAdapterHelper
 *  io.legado.app.help.http.CookieStore
 *  io.legado.app.help.http.api.CookieManager
 *  io.legado.app.utils.ACache
 *  io.legado.app.utils.NetworkUtils
 *  io.legado.app.utils.TextUtils
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.help.http;

import io.legado.app.adapters.ReaderAdapterHelper;
import io.legado.app.help.http.api.CookieManager;
import io.legado.app.utils.ACache;
import io.legado.app.utils.NetworkUtils;
import io.legado.app.utils.TextUtils;
import java.io.File;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\fJ\u001c\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0003H\u0016J\u0016\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0003J \u0010\u0014\u001a\u0004\u0018\u00010\u00032\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0003H\u0016J\u0018\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u001a\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u001a"}, d2={"Lio/legado/app/help/http/CookieStore;", "Lio/legado/app/help/http/api/CookieManager;", "userNameSpace", "", "(Ljava/lang/String;)V", "cacheInstance", "Lio/legado/app/utils/ACache;", "getCacheInstance", "()Lio/legado/app/utils/ACache;", "getUserNameSpace", "()Ljava/lang/String;", "clear", "", "cookieToMap", "", "cookie", "getCookie", "url", "getKey", "key", "mapToCookie", "cookieMap", "", "removeCookie", "replaceCookie", "setCookie", "reader-pro"})
public final class CookieStore
implements CookieManager {
    @NotNull
    private final String userNameSpace;
    @NotNull
    private final ACache cacheInstance;

    public CookieStore(@NotNull String userNameSpace) {
        Intrinsics.checkNotNullParameter((Object)userNameSpace, (String)"userNameSpace");
        this.userNameSpace = userNameSpace;
        String[] stringArray = new String[]{"storage", "cache", "cookie", this.userNameSpace};
        File cacheDir2 = new File(ReaderAdapterHelper.INSTANCE.getAdapter().getWorkDir(stringArray));
        this.cacheInstance = ACache.Companion.get(cacheDir2, 50000000L, 1000000);
    }

    @NotNull
    public final String getUserNameSpace() {
        return this.userNameSpace;
    }

    @NotNull
    public final ACache getCacheInstance() {
        return this.cacheInstance;
    }

    public void setCookie(@NotNull String url2, @Nullable String cookie) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        String domain = NetworkUtils.INSTANCE.getSubDomain(url2);
        CharSequence charSequence = domain;
        boolean bl = false;
        if (charSequence.length() > 0) {
            charSequence = cookie;
            this.cacheInstance.put(domain, (String)(charSequence == null ? "" : charSequence));
        }
    }

    public void replaceCookie(@NotNull String url2, @NotNull String cookie) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        Intrinsics.checkNotNullParameter((Object)cookie, (String)"cookie");
        if (TextUtils.isEmpty((CharSequence)url2) || TextUtils.isEmpty((CharSequence)cookie)) {
            return;
        }
        String oldCookie = this.getCookie(url2);
        if (TextUtils.isEmpty((CharSequence)oldCookie)) {
            this.setCookie(url2, cookie);
        } else {
            Map cookieMap = this.cookieToMap(oldCookie);
            cookieMap.putAll(this.cookieToMap(cookie));
            String newCookie = this.mapToCookie(cookieMap);
            this.setCookie(url2, newCookie);
        }
    }

    @NotNull
    public String getCookie(@NotNull String url2) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        String domain = NetworkUtils.INSTANCE.getSubDomain(url2);
        CharSequence charSequence = domain;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return "";
        }
        charSequence = this.cacheInstance.getAsString(domain);
        return charSequence == null ? "" : charSequence;
    }

    @NotNull
    public final String getKey(@NotNull String url2, @NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        String cookie = this.getCookie(url2);
        Map cookieMap = this.cookieToMap(cookie);
        String string = (String)cookieMap.get(key);
        return string == null ? "" : string;
    }

    public void removeCookie(@NotNull String url2) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        String domain = NetworkUtils.INSTANCE.getSubDomain(url2);
        CharSequence charSequence = domain;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return;
        }
        this.cacheInstance.remove(domain);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Map<String, String> cookieToMap(@NotNull String cookie) {
        String[] pairArray;
        void $this$toTypedArray$iv;
        List list2;
        Object object;
        Collection $this$dropLastWhile$iv;
        Intrinsics.checkNotNullParameter((Object)cookie, (String)"cookie");
        boolean bl = false;
        Map cookieMap = new LinkedHashMap();
        if (StringsKt.isBlank((CharSequence)cookie)) {
            return cookieMap;
        }
        String[] stringArray = cookie;
        String string = ";";
        int n = 0;
        string = new Regex(string);
        n = 0;
        boolean bl2 = false;
        stringArray = string.split((CharSequence)stringArray, n);
        boolean $i$f$dropLastWhile = false;
        if (!$this$dropLastWhile$iv.isEmpty()) {
            ListIterator iterator$iv = $this$dropLastWhile$iv.listIterator($this$dropLastWhile$iv.size());
            while (iterator$iv.hasPrevious()) {
                String it = (String)iterator$iv.previous();
                boolean bl3 = false;
                object = it;
                boolean bl4 = false;
                if (object.length() == 0) continue;
                list2 = CollectionsKt.take((Iterable)$this$dropLastWhile$iv, (int)(iterator$iv.nextIndex() + 1));
                break;
            }
        } else {
            list2 = CollectionsKt.emptyList();
        }
        $this$dropLastWhile$iv = list2;
        boolean $i$f$toTypedArray = false;
        void thisCollection$iv = $this$toTypedArray$iv;
        String[] stringArray2 = thisCollection$iv.toArray(new String[0]);
        if (stringArray2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        stringArray = pairArray = stringArray2;
        int n2 = 0;
        int n3 = stringArray.length;
        while (n2 < n3) {
            String[] pairs;
            void $this$toTypedArray$iv2;
            List list3;
            Collection $this$dropLastWhile$iv2;
            String pair = stringArray[n2];
            ++n2;
            object = pair;
            String string2 = "=";
            int n4 = 0;
            string2 = new Regex(string2);
            n4 = 0;
            boolean bl5 = false;
            object = string2.split((CharSequence)object, n4);
            boolean $i$f$dropLastWhile2 = false;
            if (!$this$dropLastWhile$iv2.isEmpty()) {
                ListIterator iterator$iv = $this$dropLastWhile$iv2.listIterator($this$dropLastWhile$iv2.size());
                while (iterator$iv.hasPrevious()) {
                    String it = (String)iterator$iv.previous();
                    boolean bl6 = false;
                    CharSequence charSequence = it;
                    boolean bl7 = false;
                    if (charSequence.length() == 0) continue;
                    list3 = CollectionsKt.take((Iterable)$this$dropLastWhile$iv2, (int)(iterator$iv.nextIndex() + 1));
                    break;
                }
            } else {
                list3 = CollectionsKt.emptyList();
            }
            $this$dropLastWhile$iv2 = list3;
            boolean $i$f$toTypedArray2 = false;
            void thisCollection$iv2 = $this$toTypedArray$iv2;
            if (thisCollection$iv2.toArray(new String[0]) == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            if (pairs.length == 1) continue;
            String $this$trim$iv = pairs[0];
            boolean $i$f$trim22 = false;
            CharSequence $this$trim$iv$iv22 = $this$trim$iv;
            boolean $i$f$trim = false;
            int startIndex$iv$iv = 0;
            int endIndex$iv$iv = $this$trim$iv$iv22.length() - 1;
            boolean startFound$iv$iv = false;
            while (startIndex$iv$iv <= endIndex$iv$iv) {
                boolean match$iv$iv;
                int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                char it = $this$trim$iv$iv22.charAt(index$iv$iv);
                boolean bl8 = false;
                boolean bl9 = match$iv$iv = Intrinsics.compare((int)it, (int)32) <= 0;
                if (!startFound$iv$iv) {
                    if (!match$iv$iv) {
                        startFound$iv$iv = true;
                        continue;
                    }
                    ++startIndex$iv$iv;
                    continue;
                }
                if (!match$iv$iv) break;
                --endIndex$iv$iv;
            }
            String key = ((Object)$this$trim$iv$iv22.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
            String value = pairs[1];
            CharSequence $i$f$trim22 = value;
            boolean $this$trim$iv$iv22 = false;
            if (!(!StringsKt.isBlank((CharSequence)$i$f$trim22))) {
                String $this$trim$iv2 = value;
                boolean $i$f$trim3 = false;
                CharSequence $this$trim$iv$iv = $this$trim$iv2;
                boolean $i$f$trim4 = false;
                int startIndex$iv$iv2 = 0;
                int endIndex$iv$iv2 = $this$trim$iv$iv.length() - 1;
                boolean startFound$iv$iv2 = false;
                while (startIndex$iv$iv2 <= endIndex$iv$iv2) {
                    boolean match$iv$iv;
                    int index$iv$iv = !startFound$iv$iv2 ? startIndex$iv$iv2 : endIndex$iv$iv2;
                    char it = $this$trim$iv$iv.charAt(index$iv$iv);
                    boolean bl10 = false;
                    boolean bl11 = match$iv$iv = Intrinsics.compare((int)it, (int)32) <= 0;
                    if (!startFound$iv$iv2) {
                        if (!match$iv$iv) {
                            startFound$iv$iv2 = true;
                            continue;
                        }
                        ++startIndex$iv$iv2;
                        continue;
                    }
                    if (!match$iv$iv) break;
                    --endIndex$iv$iv2;
                }
                if (!Intrinsics.areEqual((Object)((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv2, endIndex$iv$iv2 + 1)).toString(), (Object)"null")) continue;
            }
            Map map = cookieMap;
            String $this$trim$iv3 = value;
            $i$f$trim = false;
            CharSequence $this$trim$iv$iv = $this$trim$iv3;
            boolean $i$f$trim5 = false;
            int startIndex$iv$iv3 = 0;
            int endIndex$iv$iv3 = $this$trim$iv$iv.length() - 1;
            boolean startFound$iv$iv3 = false;
            while (startIndex$iv$iv3 <= endIndex$iv$iv3) {
                boolean match$iv$iv;
                int index$iv$iv = !startFound$iv$iv3 ? startIndex$iv$iv3 : endIndex$iv$iv3;
                char it = $this$trim$iv$iv.charAt(index$iv$iv);
                boolean bl12 = false;
                boolean bl13 = match$iv$iv = Intrinsics.compare((int)it, (int)32) <= 0;
                if (!startFound$iv$iv3) {
                    if (!match$iv$iv) {
                        startFound$iv$iv3 = true;
                        continue;
                    }
                    ++startIndex$iv$iv3;
                    continue;
                }
                if (!match$iv$iv) break;
                --endIndex$iv$iv3;
            }
            String string3 = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv3, endIndex$iv$iv3 + 1)).toString();
            boolean bl14 = false;
            map.put(key, string3);
        }
        return cookieMap;
    }

    @Nullable
    public String mapToCookie(@Nullable Map<String, String> cookieMap) {
        if (cookieMap == null || cookieMap.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (String key : cookieMap.keySet()) {
            boolean bl;
            String value = cookieMap.get(key);
            String string = value;
            if (string == null) {
                bl = false;
            } else {
                CharSequence charSequence = string;
                boolean bl2 = false;
                bl = !StringsKt.isBlank((CharSequence)charSequence);
            }
            if (!bl) continue;
            builder.append(key).append("=").append(value).append(";");
        }
        return builder.deleteCharAt(builder.lastIndexOf(";")).toString();
    }

    public final void clear() {
        this.cacheInstance.clear();
    }
}

