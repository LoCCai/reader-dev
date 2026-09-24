/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.AttemptResult
 *  kotlin.Metadata
 *  kotlin.PublishedApi
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.utils;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\u001b\b\u0001\u0012\b\u0010\u0003\u001a\u0004\u0018\u00018\u0000\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0011\u001a\u0004\u0018\u00018\u0000H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000fJ\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J,\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00018\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\n2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J,\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u0000\"\u0004\b\u0001\u0010\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u0002H\u001a0\u001cH\u0086\b\u00f8\u0001\u0000J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\n8\u00c6\u0002\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\n8\u00c6\u0002\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0015\u0010\u0003\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000f\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\u001f"}, d2={"Lio/legado/app/utils/AttemptResult;", "T", "", "value", "error", "", "(Ljava/lang/Object;Ljava/lang/Throwable;)V", "getError", "()Ljava/lang/Throwable;", "hasValue", "", "getHasValue", "()Z", "isError", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "copy", "(Ljava/lang/Object;Ljava/lang/Throwable;)Lio/legado/app/utils/AttemptResult;", "equals", "other", "hashCode", "", "then", "R", "f", "Lkotlin/Function1;", "toString", "", "reader-pro"})
public final class AttemptResult<T> {
    @Nullable
    private final T value;
    @Nullable
    private final Throwable error;

    @PublishedApi
    public AttemptResult(@Nullable T value, @Nullable Throwable error2) {
        this.value = value;
        this.error = error2;
    }

    @Nullable
    public final T getValue() {
        return (T)this.value;
    }

    @Nullable
    public final Throwable getError() {
        return this.error;
    }

    @NotNull
    public final <R> AttemptResult<R> then(@NotNull Function1<? super T, ? extends R> f) {
        Intrinsics.checkNotNullParameter(f, (String)"f");
        boolean $i$f$then = false;
        AttemptResult this_$iv = this;
        boolean $i$f$isError = false;
        if (this_$iv.getError() != null) {
            return this;
        }
        boolean $i$f$attempt = false;
        Object value$iv = null;
        Throwable error$iv = null;
        try {
            boolean bl = false;
            value$iv = f.invoke(this.getValue());
        }
        catch (Throwable t$iv) {
            error$iv = t$iv;
        }
        return new AttemptResult(value$iv, error$iv);
    }

    public final boolean isError() {
        boolean $i$f$isError = false;
        return this.getError() != null;
    }

    public final boolean getHasValue() {
        boolean $i$f$getHasValue = false;
        return this.getError() == null;
    }

    @Nullable
    public final T component1() {
        return (T)this.value;
    }

    @Nullable
    public final Throwable component2() {
        return this.error;
    }

    @NotNull
    public final AttemptResult<T> copy(@Nullable T value, @Nullable Throwable error2) {
        return new AttemptResult(value, error2);
    }

    public static /* synthetic */ AttemptResult copy$default(AttemptResult attemptResult, Object object, Throwable throwable, int n, Object object2) {
        if ((n & 1) != 0) {
            object = attemptResult.value;
        }
        if ((n & 2) != 0) {
            throwable = attemptResult.error;
        }
        return attemptResult.copy(object, throwable);
    }

    @NotNull
    public String toString() {
        return "AttemptResult(value=" + this.value + ", error=" + this.error + ')';
    }

    public int hashCode() {
        int result2 = this.value == null ? 0 : this.value.hashCode();
        result2 = result2 * 31 + (this.error == null ? 0 : this.error.hashCode());
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AttemptResult)) {
            return false;
        }
        AttemptResult attemptResult = (AttemptResult)other;
        if (!Intrinsics.areEqual((Object)this.value, (Object)attemptResult.value)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.error, (Object)attemptResult.error);
    }
}

