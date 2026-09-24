/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.BookKt
 *  io.legado.app.data.entities.BookKt$logger$1
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  mu.KLogger
 *  mu.KotlinLogging
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.data.entities;

import io.legado.app.data.entities.BookKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import mu.KLogger;
import mu.KotlinLogging;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0011\u0010\u0000\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2={"logger", "Lmu/KLogger;", "getLogger", "()Lmu/KLogger;", "reader-pro"})
public final class BookKt {
    @NotNull
    private static final KLogger logger = KotlinLogging.INSTANCE.logger((Function0)logger.1.INSTANCE);

    @NotNull
    public static final KLogger getLogger() {
        return logger;
    }
}

