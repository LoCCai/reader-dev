/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.MD5Utils
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Charsets
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0007"}, d2={"Lio/legado/app/utils/MD5Utils;", "", "()V", "md5Encode", "", "str", "md5Encode16", "reader-pro"})
public final class MD5Utils {
    @NotNull
    public static final MD5Utils INSTANCE = new MD5Utils();

    private MD5Utils() {
    }

    @NotNull
    public final String md5Encode(@Nullable String str) {
        if (str == null) {
            return "";
        }
        Object reStr = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            Intrinsics.checkNotNullExpressionValue((Object)messageDigest, (String)"getInstance(\"MD5\")");
            MessageDigest md5 = messageDigest;
            Object object = str;
            Charset charset = Charsets.UTF_8;
            int n = 0;
            byte[] byArray = ((String)object).getBytes(charset);
            Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"(this as java.lang.String).getBytes(charset)");
            byte[] byArray2 = md5.digest(byArray);
            Intrinsics.checkNotNullExpressionValue((Object)byArray2, (String)"md5.digest(str.toByteArray())");
            byte[] bytes2 = byArray2;
            StringBuilder stringBuffer = new StringBuilder();
            object = bytes2;
            int n2 = 0;
            n = ((Object)object).length;
            while (n2 < n) {
                Object b = object[n2];
                ++n2;
                int bt = b & 0xFF;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            object = stringBuffer.toString();
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"stringBuffer.toString()");
            reStr = object;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    @NotNull
    public final String md5Encode16(@NotNull String str) {
        String reStr;
        Intrinsics.checkNotNullParameter((Object)str, (String)"str");
        String string = reStr = this.md5Encode(str);
        int n = 8;
        int n2 = 24;
        boolean bl = false;
        String string2 = string;
        if (string2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String string3 = string2.substring(n, n2);
        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        reStr = string3;
        return reStr;
    }
}

