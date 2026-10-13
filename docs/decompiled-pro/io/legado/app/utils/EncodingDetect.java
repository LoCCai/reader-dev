/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.lib.icu4j.CharsetDetector
 *  io.legado.app.lib.icu4j.CharsetMatch
 *  io.legado.app.utils.EncodingDetect
 *  kotlin.Metadata
 *  kotlin.io.CloseableKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package io.legado.app.utils;

import io.legado.app.lib.icu4j.CharsetDetector;
import io.legado.app.lib.icu4j.CharsetMatch;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004J\u0012\u0010\n\u001a\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\f"}, d2={"Lio/legado/app/utils/EncodingDetect;", "", "()V", "getEncode", "", "file", "Ljava/io/File;", "bytes", "", "filePath", "getFileBytes", "getHtmlEncode", "reader-pro"})
public final class EncodingDetect {
    @NotNull
    public static final EncodingDetect INSTANCE = new EncodingDetect();

    private EncodingDetect() {
    }

    @Nullable
    public final String getHtmlEncode(@NotNull byte[] bytes2) {
        Intrinsics.checkNotNullParameter((Object)bytes2, (String)"bytes");
        try {
            Charset charset = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue((Object)charset, (String)"UTF_8");
            boolean bl = false;
            Document doc = Jsoup.parse((String)new String(bytes2, charset));
            Elements metaTags = doc.getElementsByTag("meta");
            String charsetStr = null;
            for (Element metaTag : metaTags) {
                String string;
                int n;
                boolean bl2;
                CharSequence charSequence = metaTag.attr("charset");
                Intrinsics.checkNotNullExpressionValue((Object)charSequence, (String)"metaTag.attr(\"charset\")");
                charsetStr = charSequence;
                charSequence = charsetStr;
                boolean bl3 = false;
                if (!(charSequence.length() == 0)) {
                    return charsetStr;
                }
                String content = metaTag.attr("content");
                String httpEquiv = metaTag.attr("http-equiv");
                Intrinsics.checkNotNullExpressionValue((Object)httpEquiv, (String)"httpEquiv");
                CharSequence charSequence2 = httpEquiv;
                Object object = Locale.getDefault();
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"getDefault()");
                boolean bl4 = false;
                String string2 = ((String)charSequence2).toLowerCase((Locale)object);
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"(this as java.lang.String).toLowerCase(locale)");
                if (!Intrinsics.areEqual((Object)string2, (Object)"content-type")) continue;
                Intrinsics.checkNotNullExpressionValue((Object)content, (String)"content");
                charSequence2 = content;
                object = Locale.getDefault();
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"getDefault()");
                bl4 = false;
                String string3 = ((String)charSequence2).toLowerCase((Locale)object);
                Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.String).toLowerCase(locale)");
                if (StringsKt.contains$default((CharSequence)string3, (CharSequence)"charset", (boolean)false, (int)2, null)) {
                    charSequence2 = content;
                    object = content;
                    Locale locale = Locale.getDefault();
                    Intrinsics.checkNotNullExpressionValue((Object)locale, (String)"getDefault()");
                    bl2 = false;
                    String string4 = ((String)object).toLowerCase(locale);
                    Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"(this as java.lang.String).toLowerCase(locale)");
                    n = StringsKt.indexOf$default((CharSequence)string4, (String)"charset", (int)0, (boolean)false, (int)6, null) + "charset=".length();
                    bl4 = false;
                    String string5 = ((String)charSequence2).substring(n);
                    string = string5;
                    Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"(this as java.lang.String).substring(startIndex)");
                } else {
                    charSequence2 = content;
                    object = content;
                    Locale locale = Locale.getDefault();
                    Intrinsics.checkNotNullExpressionValue((Object)locale, (String)"getDefault()");
                    bl2 = false;
                    String string6 = ((String)object).toLowerCase(locale);
                    Intrinsics.checkNotNullExpressionValue((Object)string6, (String)"(this as java.lang.String).toLowerCase(locale)");
                    n = StringsKt.indexOf$default((CharSequence)string6, (String)";", (int)0, (boolean)false, (int)6, null) + 1;
                    bl4 = false;
                    String string7 = ((String)charSequence2).substring(n);
                    string = string7;
                    Intrinsics.checkNotNullExpressionValue((Object)string7, (String)"(this as java.lang.String).substring(startIndex)");
                }
                charsetStr = string;
                charSequence2 = charsetStr;
                n = 0;
                if (charSequence2.length() == 0) continue;
                return charsetStr;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return this.getEncode(bytes2);
    }

    @NotNull
    public final String getEncode(@NotNull byte[] bytes2) {
        String string;
        CharsetMatch match;
        Intrinsics.checkNotNullParameter((Object)bytes2, (String)"bytes");
        CharsetMatch charsetMatch = match = new CharsetDetector().setText(bytes2).detect();
        return charsetMatch == null ? "UTF-8" : ((string = charsetMatch.getName()) == null ? "UTF-8" : string);
    }

    @NotNull
    public final String getEncode(@NotNull String filePath) {
        Intrinsics.checkNotNullParameter((Object)filePath, (String)"filePath");
        return this.getEncode(new File(filePath));
    }

    @NotNull
    public final String getEncode(@NotNull File file) {
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        byte[] tempByte = this.getFileBytes(file);
        return this.getEncode(tempByte);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final byte[] getFileBytes(File file) {
        byte[] byteArray2 = new byte[8000];
        try {
            Closeable closeable = new FileInputStream(file);
            boolean bl = false;
            boolean bl2 = false;
            Throwable throwable = null;
            try {
                FileInputStream it = (FileInputStream)closeable;
                boolean bl3 = false;
                int n = it.read(byteArray2);
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
            }
        }
        catch (Exception e) {
            System.err.println(Intrinsics.stringPlus((String)"Error: ", (Object)e));
        }
        return byteArray2;
    }
}

