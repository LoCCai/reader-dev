/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.init.appCtx
 *  com.htmake.reader.init.appCtx$cacheDir$2
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  org.jetbrains.annotations.NotNull
 */
package com.htmake.reader.init;

import com.htmake.reader.init.appCtx;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/htmake/reader/init/appCtx;", "", "()V", "cacheDir", "", "getCacheDir", "()Ljava/lang/String;", "cacheDir$delegate", "Lkotlin/Lazy;", "reader-pro"})
public final class appCtx {
    @NotNull
    public static final appCtx INSTANCE = new appCtx();
    @NotNull
    private static final Lazy cacheDir$delegate = LazyKt.lazy((Function0)((Function0)cacheDir.2.INSTANCE));

    private appCtx() {
    }

    @NotNull
    public final String getCacheDir() {
        Lazy lazy = cacheDir$delegate;
        boolean bl = false;
        return (String)lazy.getValue();
    }
}

