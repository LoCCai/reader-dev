/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.ReaderApplication
 *  com.htmake.reader.ReaderApplicationKt
 *  com.htmake.reader.ReaderApplicationKt$logger$1
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  mu.KLogger
 *  mu.KotlinLogging
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.boot.SpringApplication
 */
package com.htmake.reader;

import com.htmake.reader.ReaderApplication;
import com.htmake.reader.ReaderApplicationKt;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import mu.KLogger;
import mu.KotlinLogging;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;

@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0019\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2={"logger", "Lmu/KLogger;", "main", "", "args", "", "", "([Ljava/lang/String;)V", "reader-pro"})
public final class ReaderApplicationKt {
    @NotNull
    private static final KLogger logger = KotlinLogging.INSTANCE.logger((Function0)logger.1.INSTANCE);

    public static final void main(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter((Object)args, (String)"args");
        logger.info("Starting application with args: {}", (Object)args);
        SpringApplication.run(ReaderApplication.class, (String[])Arrays.copyOf(args, args.length));
    }
}

