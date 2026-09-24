/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.help.http.api.CookieManager
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.help.http.api;

import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0004H&J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004H&J \u0010\b\u001a\u0004\u0018\u00010\u00042\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\nH&J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u0004H&J\u0018\u0010\r\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&J\u001a\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H&\u00a8\u0006\u000f"}, d2={"Lio/legado/app/help/http/api/CookieManager;", "", "cookieToMap", "", "", "cookie", "getCookie", "url", "mapToCookie", "cookieMap", "", "removeCookie", "", "replaceCookie", "setCookie", "reader-pro"})
public interface CookieManager {
    public void setCookie(@NotNull String var1, @Nullable String var2);

    public void replaceCookie(@NotNull String var1, @NotNull String var2);

    @NotNull
    public String getCookie(@NotNull String var1);

    public void removeCookie(@NotNull String var1);

    @NotNull
    public Map<String, String> cookieToMap(@NotNull String var1);

    @Nullable
    public String mapToCookie(@Nullable Map<String, String> var1);
}

