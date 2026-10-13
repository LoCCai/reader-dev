/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.Cookie
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.data.entities;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t\u00a8\u0006\u0015"}, d2={"Lio/legado/app/data/entities/Cookie;", "", "url", "", "cookie", "(Ljava/lang/String;Ljava/lang/String;)V", "getCookie", "()Ljava/lang/String;", "setCookie", "(Ljava/lang/String;)V", "getUrl", "setUrl", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "reader-pro"})
public final class Cookie {
    @NotNull
    private String url;
    @NotNull
    private String cookie;

    public Cookie(@NotNull String url2, @NotNull String cookie) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        Intrinsics.checkNotNullParameter((Object)cookie, (String)"cookie");
        this.url = url2;
        this.cookie = cookie;
    }

    public /* synthetic */ Cookie(String string, String string2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = "";
        }
        if ((n & 2) != 0) {
            string2 = "";
        }
        this(string, string2);
    }

    @NotNull
    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.url = string;
    }

    @NotNull
    public final String getCookie() {
        return this.cookie;
    }

    public final void setCookie(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.cookie = string;
    }

    @NotNull
    public final String component1() {
        return this.url;
    }

    @NotNull
    public final String component2() {
        return this.cookie;
    }

    @NotNull
    public final Cookie copy(@NotNull String url2, @NotNull String cookie) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        Intrinsics.checkNotNullParameter((Object)cookie, (String)"cookie");
        return new Cookie(url2, cookie);
    }

    public static /* synthetic */ Cookie copy$default(Cookie cookie, String string, String string2, int n, Object object) {
        if ((n & 1) != 0) {
            string = cookie.url;
        }
        if ((n & 2) != 0) {
            string2 = cookie.cookie;
        }
        return cookie.copy(string, string2);
    }

    @NotNull
    public String toString() {
        return "Cookie(url=" + this.url + ", cookie=" + this.cookie + ')';
    }

    public int hashCode() {
        int result2 = this.url.hashCode();
        result2 = result2 * 31 + this.cookie.hashCode();
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Cookie)) {
            return false;
        }
        Cookie cookie = (Cookie)other;
        if (!Intrinsics.areEqual((Object)this.url, (Object)cookie.url)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.cookie, (Object)cookie.cookie);
    }

    public Cookie() {
        this(null, null, 3, null);
    }
}

