/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.Utf8BomUtils
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Charsets
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.utils;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lio/legado/app/utils/Utf8BomUtils;", "", "()V", "UTF8_BOM_BYTES", "", "hasBom", "", "bytes", "removeUTF8BOM", "", "xmlText", "reader-pro"})
public final class Utf8BomUtils {
    @NotNull
    public static final Utf8BomUtils INSTANCE = new Utf8BomUtils();
    @NotNull
    private static final byte[] UTF8_BOM_BYTES;

    private Utf8BomUtils() {
    }

    @NotNull
    public final String removeUTF8BOM(@NotNull String xmlText) {
        boolean containsBOM;
        Intrinsics.checkNotNullParameter((Object)xmlText, (String)"xmlText");
        String string = xmlText;
        Charset charset = Charsets.UTF_8;
        int n = 0;
        byte[] byArray = string.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"(this as java.lang.String).getBytes(charset)");
        byte[] bytes2 = byArray;
        boolean bl = containsBOM = bytes2.length > 3 && bytes2[0] == UTF8_BOM_BYTES[0] && bytes2[1] == UTF8_BOM_BYTES[1] && bytes2[2] == UTF8_BOM_BYTES[2];
        if (containsBOM) {
            int n2 = 3;
            n = bytes2.length - 3;
            boolean bl2 = false;
            return new String(bytes2, n2, n, Charsets.UTF_8);
        }
        return xmlText;
    }

    @NotNull
    public final byte[] removeUTF8BOM(@NotNull byte[] bytes2) {
        boolean containsBOM;
        Intrinsics.checkNotNullParameter((Object)bytes2, (String)"bytes");
        boolean bl = containsBOM = bytes2.length > 3 && bytes2[0] == UTF8_BOM_BYTES[0] && bytes2[1] == UTF8_BOM_BYTES[1] && bytes2[2] == UTF8_BOM_BYTES[2];
        if (containsBOM) {
            byte[] copy = new byte[bytes2.length - 3];
            System.arraycopy(bytes2, 3, copy, 0, bytes2.length - 3);
            return copy;
        }
        return bytes2;
    }

    public final boolean hasBom(@NotNull byte[] bytes2) {
        Intrinsics.checkNotNullParameter((Object)bytes2, (String)"bytes");
        return bytes2.length > 3 && bytes2[0] == UTF8_BOM_BYTES[0] && bytes2[1] == UTF8_BOM_BYTES[1] && bytes2[2] == UTF8_BOM_BYTES[2];
    }

    static {
        byte[] byArray = new byte[]{-17, -69, -65};
        UTF8_BOM_BYTES = byArray;
    }
}

