/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.entity.MongoFile
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.entity;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0006H\u00c6\u0003J1\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\fR\u001a\u0010\u0007\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010\u00a8\u0006 "}, d2={"Lcom/htmake/reader/entity/MongoFile;", "", "path", "", "content", "created_at", "", "updated_at", "(Ljava/lang/String;Ljava/lang/String;JJ)V", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "getCreated_at", "()J", "setCreated_at", "(J)V", "getPath", "setPath", "getUpdated_at", "setUpdated_at", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "reader-pro"})
public final class MongoFile {
    @NotNull
    private String path;
    @NotNull
    private String content;
    private long created_at;
    private long updated_at;

    public MongoFile(@NotNull String path, @NotNull String content, long created_at, long updated_at) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        Intrinsics.checkNotNullParameter((Object)content, (String)"content");
        this.path = path;
        this.content = content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public /* synthetic */ MongoFile(String string, String string2, long l, long l2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = "";
        }
        if ((n & 2) != 0) {
            string2 = "";
        }
        if ((n & 4) != 0) {
            l = System.currentTimeMillis();
        }
        if ((n & 8) != 0) {
            l2 = System.currentTimeMillis();
        }
        this(string, string2, l, l2);
    }

    @NotNull
    public final String getPath() {
        return this.path;
    }

    public final void setPath(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.path = string;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }

    public final void setContent(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.content = string;
    }

    public final long getCreated_at() {
        return this.created_at;
    }

    public final void setCreated_at(long l) {
        this.created_at = l;
    }

    public final long getUpdated_at() {
        return this.updated_at;
    }

    public final void setUpdated_at(long l) {
        this.updated_at = l;
    }

    @NotNull
    public final String component1() {
        return this.path;
    }

    @NotNull
    public final String component2() {
        return this.content;
    }

    public final long component3() {
        return this.created_at;
    }

    public final long component4() {
        return this.updated_at;
    }

    @NotNull
    public final MongoFile copy(@NotNull String path, @NotNull String content, long created_at, long updated_at) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        Intrinsics.checkNotNullParameter((Object)content, (String)"content");
        return new MongoFile(path, content, created_at, updated_at);
    }

    public static /* synthetic */ MongoFile copy$default(MongoFile mongoFile, String string, String string2, long l, long l2, int n, Object object) {
        if ((n & 1) != 0) {
            string = mongoFile.path;
        }
        if ((n & 2) != 0) {
            string2 = mongoFile.content;
        }
        if ((n & 4) != 0) {
            l = mongoFile.created_at;
        }
        if ((n & 8) != 0) {
            l2 = mongoFile.updated_at;
        }
        return mongoFile.copy(string, string2, l, l2);
    }

    @NotNull
    public String toString() {
        return "MongoFile(path=" + this.path + ", content=" + this.content + ", created_at=" + this.created_at + ", updated_at=" + this.updated_at + ')';
    }

    public int hashCode() {
        int result2 = this.path.hashCode();
        result2 = result2 * 31 + this.content.hashCode();
        result2 = result2 * 31 + Long.hashCode(this.created_at);
        result2 = result2 * 31 + Long.hashCode(this.updated_at);
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MongoFile)) {
            return false;
        }
        MongoFile mongoFile = (MongoFile)other;
        if (!Intrinsics.areEqual((Object)this.path, (Object)mongoFile.path)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.content, (Object)mongoFile.content)) {
            return false;
        }
        if (this.created_at != mongoFile.created_at) {
            return false;
        }
        return this.updated_at == mongoFile.updated_at;
    }

    public MongoFile() {
        this(null, null, 0L, 0L, 15, null);
    }
}

