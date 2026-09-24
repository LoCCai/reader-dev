/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.model.DebugLog
 *  kotlin.Metadata
 *  okhttp3.logging.HttpLoggingInterceptor$Logger
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model;

import kotlin.Metadata;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J*\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\tH\u0016\u00a8\u0006\n"}, d2={"Lio/legado/app/model/DebugLog;", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "log", "", "message", "", "sourceUrl", "msg", "isHtml", "", "reader-pro"})
public interface DebugLog
extends HttpLoggingInterceptor.Logger {
    public void log(@Nullable String var1, @Nullable String var2, boolean var3);

    public void log(@NotNull String var1);
}

