/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.help.http.Res
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.help.http;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2={"Lio/legado/app/help/http/Res;", "", "url", "", "body", "(Ljava/lang/String;Ljava/lang/String;)V", "getBody", "()Ljava/lang/String;", "getUrl", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "reader-pro"})
public final class Res {
    @NotNull
    private final String url;
    @Nullable
    private final String body;

    public Res(@NotNull String url2, @Nullable String body) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        this.url = url2;
        this.body = body;
    }

    @NotNull
    public final String getUrl() {
        return this.url;
    }

    @Nullable
    public final String getBody() {
        return this.body;
    }

    @NotNull
    public final String component1() {
        return this.url;
    }

    @Nullable
    public final String component2() {
        return this.body;
    }

    @NotNull
    public final Res copy(@NotNull String url2, @Nullable String body) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        return new Res(url2, body);
    }

    public static /* synthetic */ Res copy$default(Res res, String string, String string2, int n, Object object) {
        if ((n & 1) != 0) {
            string = res.url;
        }
        if ((n & 2) != 0) {
            string2 = res.body;
        }
        return res.copy(string, string2);
    }

    @NotNull
    public String toString() {
        return "Res(url=" + this.url + ", body=" + this.body + ')';
    }

    public int hashCode() {
        int result2 = this.url.hashCode();
        result2 = result2 * 31 + (this.body == null ? 0 : this.body.hashCode());
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Res)) {
            return false;
        }
        Res res = (Res)other;
        if (!Intrinsics.areEqual((Object)this.url, (Object)res.url)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.body, (Object)res.body);
    }
}

