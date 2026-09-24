/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.exception.ConcurrentException
 *  io.legado.app.exception.NoStackTraceException
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.exception;

import io.legado.app.exception.NoStackTraceException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lio/legado/app/exception/ConcurrentException;", "Lio/legado/app/exception/NoStackTraceException;", "msg", "", "waitTime", "", "(Ljava/lang/String;I)V", "getWaitTime", "()I", "reader-pro"})
public final class ConcurrentException
extends NoStackTraceException {
    private final int waitTime;

    public ConcurrentException(@NotNull String msg, int waitTime) {
        Intrinsics.checkNotNullParameter((Object)msg, (String)"msg");
        super(msg);
        this.waitTime = waitTime;
    }

    public final int getWaitTime() {
        return this.waitTime;
    }
}

