/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.model.Debug
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.DebugLog$DefaultImpls
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model;

import io.legado.app.model.DebugLog;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lio/legado/app/model/Debug;", "Lio/legado/app/model/DebugLog;", "()V", "reader-pro"})
public final class Debug
implements DebugLog {
    @NotNull
    public static final Debug INSTANCE = new Debug();

    private Debug() {
    }

    public void log(@NotNull String message) {
        DebugLog.DefaultImpls.log((DebugLog)this, (String)message);
    }

    public void log(@Nullable String sourceUrl, @Nullable String msg, boolean isHtml) {
        DebugLog.DefaultImpls.log((DebugLog)this, (String)sourceUrl, (String)msg, (boolean)isHtml);
    }
}

