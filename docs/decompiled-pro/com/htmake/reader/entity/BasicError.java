/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.entity.BasicError
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.entity;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001a\u001a\u00020\nH\u00c6\u0003JE\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\bH\u00d6\u0001J\t\u0010 \u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006!"}, d2={"Lcom/htmake/reader/entity/BasicError;", "", "error", "", "exception", "message", "path", "status", "", "timestamp", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IJ)V", "getError", "()Ljava/lang/String;", "getException", "getMessage", "getPath", "getStatus", "()I", "getTimestamp", "()J", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "reader-pro"})
public final class BasicError {
    @NotNull
    private final String error;
    @NotNull
    private final String exception;
    @NotNull
    private final String message;
    @NotNull
    private final String path;
    private final int status;
    private final long timestamp;

    public BasicError(@NotNull String error2, @NotNull String exception, @NotNull String message, @NotNull String path, int status, long timestamp) {
        Intrinsics.checkNotNullParameter((Object)error2, (String)"error");
        Intrinsics.checkNotNullParameter((Object)exception, (String)"exception");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        this.error = error2;
        this.exception = exception;
        this.message = message;
        this.path = path;
        this.status = status;
        this.timestamp = timestamp;
    }

    @NotNull
    public final String getError() {
        return this.error;
    }

    @NotNull
    public final String getException() {
        return this.exception;
    }

    @NotNull
    public final String getMessage() {
        return this.message;
    }

    @NotNull
    public final String getPath() {
        return this.path;
    }

    public final int getStatus() {
        return this.status;
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

    @NotNull
    public final String component1() {
        return this.error;
    }

    @NotNull
    public final String component2() {
        return this.exception;
    }

    @NotNull
    public final String component3() {
        return this.message;
    }

    @NotNull
    public final String component4() {
        return this.path;
    }

    public final int component5() {
        return this.status;
    }

    public final long component6() {
        return this.timestamp;
    }

    @NotNull
    public final BasicError copy(@NotNull String error2, @NotNull String exception, @NotNull String message, @NotNull String path, int status, long timestamp) {
        Intrinsics.checkNotNullParameter((Object)error2, (String)"error");
        Intrinsics.checkNotNullParameter((Object)exception, (String)"exception");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        return new BasicError(error2, exception, message, path, status, timestamp);
    }

    public static /* synthetic */ BasicError copy$default(BasicError basicError, String string, String string2, String string3, String string4, int n, long l, int n2, Object object) {
        if ((n2 & 1) != 0) {
            string = basicError.error;
        }
        if ((n2 & 2) != 0) {
            string2 = basicError.exception;
        }
        if ((n2 & 4) != 0) {
            string3 = basicError.message;
        }
        if ((n2 & 8) != 0) {
            string4 = basicError.path;
        }
        if ((n2 & 0x10) != 0) {
            n = basicError.status;
        }
        if ((n2 & 0x20) != 0) {
            l = basicError.timestamp;
        }
        return basicError.copy(string, string2, string3, string4, n, l);
    }

    @NotNull
    public String toString() {
        return "BasicError(error=" + this.error + ", exception=" + this.exception + ", message=" + this.message + ", path=" + this.path + ", status=" + this.status + ", timestamp=" + this.timestamp + ')';
    }

    public int hashCode() {
        int result2 = this.error.hashCode();
        result2 = result2 * 31 + this.exception.hashCode();
        result2 = result2 * 31 + this.message.hashCode();
        result2 = result2 * 31 + this.path.hashCode();
        result2 = result2 * 31 + Integer.hashCode(this.status);
        result2 = result2 * 31 + Long.hashCode(this.timestamp);
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BasicError)) {
            return false;
        }
        BasicError basicError = (BasicError)other;
        if (!Intrinsics.areEqual((Object)this.error, (Object)basicError.error)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.exception, (Object)basicError.exception)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.message, (Object)basicError.message)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.path, (Object)basicError.path)) {
            return false;
        }
        if (this.status != basicError.status) {
            return false;
        }
        return this.timestamp == basicError.timestamp;
    }
}

