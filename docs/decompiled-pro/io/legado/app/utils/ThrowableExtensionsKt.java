/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.ThrowableExtensionsKt
 *  kotlin.ExceptionsKt
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.utils;

import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0003\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0005"}, d2={"msg", "", "", "getMsg", "(Ljava/lang/Throwable;)Ljava/lang/String;", "reader-pro"})
public final class ThrowableExtensionsKt {
    @NotNull
    public static final String getMsg(@NotNull Throwable $this$msg) {
        Intrinsics.checkNotNullParameter((Object)$this$msg, (String)"<this>");
        String stackTrace = ExceptionsKt.stackTraceToString((Throwable)$this$msg);
        CharSequence charSequence = $this$msg.getLocalizedMessage();
        String lMsg = charSequence == null ? "noErrorMsg" : charSequence;
        charSequence = stackTrace;
        boolean bl = false;
        return charSequence.length() > 0 ? stackTrace : lMsg;
    }
}

