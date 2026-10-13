/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.entity.User
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.entity;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b9\b\u0086\b\u0018\u00002\u00020\u0001B\u0095\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\u0016\b\u0002\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b\u0018\u00010\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0014J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u000bH\u00c6\u0003J\t\u0010;\u001a\u00020\u000bH\u00c6\u0003J\t\u0010<\u001a\u00020\u0012H\u00c6\u0003J\t\u0010=\u001a\u00020\u0012H\u00c6\u0003J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\t\u0010?\u001a\u00020\u0003H\u00c6\u0003J\t\u0010@\u001a\u00020\u0003H\u00c6\u0003J\t\u0010A\u001a\u00020\bH\u00c6\u0003J\t\u0010B\u001a\u00020\bH\u00c6\u0003J\t\u0010C\u001a\u00020\u000bH\u00c6\u0003J\u0017\u0010D\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b\u0018\u00010\rH\u00c6\u0003J\t\u0010E\u001a\u00020\u000bH\u00c6\u0003J\u0099\u0001\u0010F\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\u0016\b\u0002\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b\u0018\u00010\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u000b2\b\b\u0002\u0010\u0010\u001a\u00020\u000b2\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0012H\u00c6\u0001J\u0013\u0010G\u001a\u00020\u000b2\b\u0010H\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010I\u001a\u00020\u0012H\u00d6\u0001J\t\u0010J\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u0013\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0016\"\u0004\b\u001a\u0010\u0018R\u001a\u0010\t\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u000f\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010\u000e\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010 \"\u0004\b$\u0010\"R\u001a\u0010\u0010\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010 \"\u0004\b&\u0010\"R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b'\u0010 \"\u0004\b(\u0010\"R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u001c\"\u0004\b*\u0010\u001eR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010,\"\u0004\b0\u0010.R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010,\"\u0004\b2\u0010.R(\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b\u0018\u00010\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u0010,\"\u0004\b8\u0010.\u00a8\u0006K"}, d2={"Lcom/htmake/reader/entity/User;", "", "username", "", "password", "salt", "token", "last_login_at", "", "created_at", "enable_webdav", "", "token_map", "", "enable_local_store", "enable_book_source", "enable_rss_source", "book_source_limit", "", "book_limit", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJZLjava/util/Map;ZZZII)V", "getBook_limit", "()I", "setBook_limit", "(I)V", "getBook_source_limit", "setBook_source_limit", "getCreated_at", "()J", "setCreated_at", "(J)V", "getEnable_book_source", "()Z", "setEnable_book_source", "(Z)V", "getEnable_local_store", "setEnable_local_store", "getEnable_rss_source", "setEnable_rss_source", "getEnable_webdav", "setEnable_webdav", "getLast_login_at", "setLast_login_at", "getPassword", "()Ljava/lang/String;", "setPassword", "(Ljava/lang/String;)V", "getSalt", "setSalt", "getToken", "setToken", "getToken_map", "()Ljava/util/Map;", "setToken_map", "(Ljava/util/Map;)V", "getUsername", "setUsername", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "reader-pro"})
public final class User {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String salt;
    @NotNull
    private String token;
    private long last_login_at;
    private long created_at;
    private boolean enable_webdav;
    @Nullable
    private Map<String, Long> token_map;
    private boolean enable_local_store;
    private boolean enable_book_source;
    private boolean enable_rss_source;
    private int book_source_limit;
    private int book_limit;

    public User(@NotNull String username, @NotNull String password, @NotNull String salt, @NotNull String token, long last_login_at, long created_at, boolean enable_webdav, @Nullable Map<String, Long> token_map, boolean enable_local_store, boolean enable_book_source, boolean enable_rss_source, int book_source_limit, int book_limit) {
        Intrinsics.checkNotNullParameter((Object)username, (String)"username");
        Intrinsics.checkNotNullParameter((Object)password, (String)"password");
        Intrinsics.checkNotNullParameter((Object)salt, (String)"salt");
        Intrinsics.checkNotNullParameter((Object)token, (String)"token");
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.token = token;
        this.last_login_at = last_login_at;
        this.created_at = created_at;
        this.enable_webdav = enable_webdav;
        this.token_map = token_map;
        this.enable_local_store = enable_local_store;
        this.enable_book_source = enable_book_source;
        this.enable_rss_source = enable_rss_source;
        this.book_source_limit = book_source_limit;
        this.book_limit = book_limit;
    }

    public /* synthetic */ User(String string, String string2, String string3, String string4, long l, long l2, boolean bl, Map map, boolean bl2, boolean bl3, boolean bl4, int n, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 1) != 0) {
            string = "";
        }
        if ((n3 & 2) != 0) {
            string2 = "";
        }
        if ((n3 & 4) != 0) {
            string3 = "";
        }
        if ((n3 & 8) != 0) {
            string4 = "";
        }
        if ((n3 & 0x10) != 0) {
            l = System.currentTimeMillis();
        }
        if ((n3 & 0x20) != 0) {
            l2 = System.currentTimeMillis();
        }
        if ((n3 & 0x40) != 0) {
            bl = false;
        }
        if ((n3 & 0x80) != 0) {
            map = null;
        }
        if ((n3 & 0x100) != 0) {
            bl2 = false;
        }
        if ((n3 & 0x200) != 0) {
            bl3 = true;
        }
        if ((n3 & 0x400) != 0) {
            bl4 = true;
        }
        if ((n3 & 0x800) != 0) {
            n = 100;
        }
        if ((n3 & 0x1000) != 0) {
            n2 = 200;
        }
        this(string, string2, string3, string4, l, l2, bl, map, bl2, bl3, bl4, n, n2);
    }

    @NotNull
    public final String getUsername() {
        return this.username;
    }

    public final void setUsername(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.username = string;
    }

    @NotNull
    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.password = string;
    }

    @NotNull
    public final String getSalt() {
        return this.salt;
    }

    public final void setSalt(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.salt = string;
    }

    @NotNull
    public final String getToken() {
        return this.token;
    }

    public final void setToken(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.token = string;
    }

    public final long getLast_login_at() {
        return this.last_login_at;
    }

    public final void setLast_login_at(long l) {
        this.last_login_at = l;
    }

    public final long getCreated_at() {
        return this.created_at;
    }

    public final void setCreated_at(long l) {
        this.created_at = l;
    }

    public final boolean getEnable_webdav() {
        return this.enable_webdav;
    }

    public final void setEnable_webdav(boolean bl) {
        this.enable_webdav = bl;
    }

    @Nullable
    public final Map<String, Long> getToken_map() {
        return this.token_map;
    }

    public final void setToken_map(@Nullable Map<String, Long> map) {
        this.token_map = map;
    }

    public final boolean getEnable_local_store() {
        return this.enable_local_store;
    }

    public final void setEnable_local_store(boolean bl) {
        this.enable_local_store = bl;
    }

    public final boolean getEnable_book_source() {
        return this.enable_book_source;
    }

    public final void setEnable_book_source(boolean bl) {
        this.enable_book_source = bl;
    }

    public final boolean getEnable_rss_source() {
        return this.enable_rss_source;
    }

    public final void setEnable_rss_source(boolean bl) {
        this.enable_rss_source = bl;
    }

    public final int getBook_source_limit() {
        return this.book_source_limit;
    }

    public final void setBook_source_limit(int n) {
        this.book_source_limit = n;
    }

    public final int getBook_limit() {
        return this.book_limit;
    }

    public final void setBook_limit(int n) {
        this.book_limit = n;
    }

    @NotNull
    public final String component1() {
        return this.username;
    }

    @NotNull
    public final String component2() {
        return this.password;
    }

    @NotNull
    public final String component3() {
        return this.salt;
    }

    @NotNull
    public final String component4() {
        return this.token;
    }

    public final long component5() {
        return this.last_login_at;
    }

    public final long component6() {
        return this.created_at;
    }

    public final boolean component7() {
        return this.enable_webdav;
    }

    @Nullable
    public final Map<String, Long> component8() {
        return this.token_map;
    }

    public final boolean component9() {
        return this.enable_local_store;
    }

    public final boolean component10() {
        return this.enable_book_source;
    }

    public final boolean component11() {
        return this.enable_rss_source;
    }

    public final int component12() {
        return this.book_source_limit;
    }

    public final int component13() {
        return this.book_limit;
    }

    @NotNull
    public final User copy(@NotNull String username, @NotNull String password, @NotNull String salt, @NotNull String token, long last_login_at, long created_at, boolean enable_webdav, @Nullable Map<String, Long> token_map, boolean enable_local_store, boolean enable_book_source, boolean enable_rss_source, int book_source_limit, int book_limit) {
        Intrinsics.checkNotNullParameter((Object)username, (String)"username");
        Intrinsics.checkNotNullParameter((Object)password, (String)"password");
        Intrinsics.checkNotNullParameter((Object)salt, (String)"salt");
        Intrinsics.checkNotNullParameter((Object)token, (String)"token");
        return new User(username, password, salt, token, last_login_at, created_at, enable_webdav, token_map, enable_local_store, enable_book_source, enable_rss_source, book_source_limit, book_limit);
    }

    public static /* synthetic */ User copy$default(User user, String string, String string2, String string3, String string4, long l, long l2, boolean bl, Map map, boolean bl2, boolean bl3, boolean bl4, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            string = user.username;
        }
        if ((n3 & 2) != 0) {
            string2 = user.password;
        }
        if ((n3 & 4) != 0) {
            string3 = user.salt;
        }
        if ((n3 & 8) != 0) {
            string4 = user.token;
        }
        if ((n3 & 0x10) != 0) {
            l = user.last_login_at;
        }
        if ((n3 & 0x20) != 0) {
            l2 = user.created_at;
        }
        if ((n3 & 0x40) != 0) {
            bl = user.enable_webdav;
        }
        if ((n3 & 0x80) != 0) {
            map = user.token_map;
        }
        if ((n3 & 0x100) != 0) {
            bl2 = user.enable_local_store;
        }
        if ((n3 & 0x200) != 0) {
            bl3 = user.enable_book_source;
        }
        if ((n3 & 0x400) != 0) {
            bl4 = user.enable_rss_source;
        }
        if ((n3 & 0x800) != 0) {
            n = user.book_source_limit;
        }
        if ((n3 & 0x1000) != 0) {
            n2 = user.book_limit;
        }
        return user.copy(string, string2, string3, string4, l, l2, bl, map, bl2, bl3, bl4, n, n2);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User(username=").append(this.username).append(", password=").append(this.password).append(", salt=").append(this.salt).append(", token=").append(this.token).append(", last_login_at=").append(this.last_login_at).append(", created_at=").append(this.created_at).append(", enable_webdav=").append(this.enable_webdav).append(", token_map=").append(this.token_map).append(", enable_local_store=").append(this.enable_local_store).append(", enable_book_source=").append(this.enable_book_source).append(", enable_rss_source=").append(this.enable_rss_source).append(", book_source_limit=");
        stringBuilder.append(this.book_source_limit).append(", book_limit=").append(this.book_limit).append(')');
        return stringBuilder.toString();
    }

    public int hashCode() {
        int result2 = this.username.hashCode();
        result2 = result2 * 31 + this.password.hashCode();
        result2 = result2 * 31 + this.salt.hashCode();
        result2 = result2 * 31 + this.token.hashCode();
        result2 = result2 * 31 + Long.hashCode(this.last_login_at);
        result2 = result2 * 31 + Long.hashCode(this.created_at);
        int n = this.enable_webdav ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result2 = result2 * 31 + n;
        result2 = result2 * 31 + (this.token_map == null ? 0 : ((Object)this.token_map).hashCode());
        int n2 = this.enable_local_store ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        result2 = result2 * 31 + n2;
        int n3 = this.enable_book_source ? 1 : 0;
        if (n3 != 0) {
            n3 = 1;
        }
        result2 = result2 * 31 + n3;
        int n4 = this.enable_rss_source ? 1 : 0;
        if (n4 != 0) {
            n4 = 1;
        }
        result2 = result2 * 31 + n4;
        result2 = result2 * 31 + Integer.hashCode(this.book_source_limit);
        result2 = result2 * 31 + Integer.hashCode(this.book_limit);
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof User)) {
            return false;
        }
        User user = (User)other;
        if (!Intrinsics.areEqual((Object)this.username, (Object)user.username)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.password, (Object)user.password)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.salt, (Object)user.salt)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.token, (Object)user.token)) {
            return false;
        }
        if (this.last_login_at != user.last_login_at) {
            return false;
        }
        if (this.created_at != user.created_at) {
            return false;
        }
        if (this.enable_webdav != user.enable_webdav) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.token_map, (Object)user.token_map)) {
            return false;
        }
        if (this.enable_local_store != user.enable_local_store) {
            return false;
        }
        if (this.enable_book_source != user.enable_book_source) {
            return false;
        }
        if (this.enable_rss_source != user.enable_rss_source) {
            return false;
        }
        if (this.book_source_limit != user.book_source_limit) {
            return false;
        }
        return this.book_limit == user.book_limit;
    }

    public User() {
        this(null, null, null, null, 0L, 0L, false, null, false, false, false, 0, 0, 8191, null);
    }
}

