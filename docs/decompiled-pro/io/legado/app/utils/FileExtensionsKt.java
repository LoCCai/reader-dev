/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.FileExtensionsKt
 *  io.legado.app.utils.FileUtils
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.utils;

import io.legado.app.utils.FileUtils;
import java.io.File;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000\u0018\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a#\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u001a#\u0010\u0007\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2={"exists", "", "Ljava/io/File;", "subDirFiles", "", "", "(Ljava/io/File;[Ljava/lang/String;)Z", "getFile", "(Ljava/io/File;[Ljava/lang/String;)Ljava/io/File;", "reader-pro"})
public final class FileExtensionsKt {
    @NotNull
    public static final File getFile(@NotNull File $this$getFile, String ... subDirFiles) {
        Intrinsics.checkNotNullParameter((Object)$this$getFile, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)subDirFiles, (String)"subDirFiles");
        String path = FileUtils.INSTANCE.getPath($this$getFile, Arrays.copyOf(subDirFiles, subDirFiles.length));
        return new File(path);
    }

    public static final boolean exists(@NotNull File $this$exists, String ... subDirFiles) {
        Intrinsics.checkNotNullParameter((Object)$this$exists, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)subDirFiles, (String)"subDirFiles");
        return FileExtensionsKt.getFile((File)$this$exists, (String[])Arrays.copyOf(subDirFiles, subDirFiles.length)).exists();
    }
}

