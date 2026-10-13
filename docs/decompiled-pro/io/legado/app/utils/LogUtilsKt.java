/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.LogUtilsKt
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010\u0003\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u00a8\u0006\u0003"}, d2={"printOnDebug", "", "", "reader-pro"})
public final class LogUtilsKt {
    public static final void printOnDebug(@NotNull Throwable $this$printOnDebug) {
        Intrinsics.checkNotNullParameter((Object)$this$printOnDebug, (String)"<this>");
        $this$printOnDebug.printStackTrace();
    }
}

