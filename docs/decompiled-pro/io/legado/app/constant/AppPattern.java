/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.constant.AppPattern
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  kotlin.text.RegexOption
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.constant;

import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0017\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u000f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\u0011\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u0011\u0010\u0013\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\fR\u0011\u0010\u0015\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\fR\u0011\u0010\u0017\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\fR\u0011\u0010\u001b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\fR\u0011\u0010\u001d\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\fR\u0011\u0010\u001f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\f\u00a8\u0006!"}, d2={"Lio/legado/app/constant/AppPattern;", "", "()V", "EXP_PATTERN", "Ljava/util/regex/Pattern;", "getEXP_PATTERN", "()Ljava/util/regex/Pattern;", "JS_PATTERN", "getJS_PATTERN", "authorRegex", "Lkotlin/text/Regex;", "getAuthorRegex", "()Lkotlin/text/Regex;", "bdRegex", "getBdRegex", "bookFileRegex", "getBookFileRegex", "dataUriRegex", "getDataUriRegex", "debugMessageSymbolRegex", "getDebugMessageSymbolRegex", "fileNameRegex", "getFileNameRegex", "imgPattern", "getImgPattern", "nameRegex", "getNameRegex", "notReadAloudRegex", "getNotReadAloudRegex", "rnRegex", "getRnRegex", "splitGroupRegex", "getSplitGroupRegex", "reader-pro"})
public final class AppPattern {
    @NotNull
    public static final AppPattern INSTANCE = new AppPattern();
    @NotNull
    private static final Pattern JS_PATTERN;
    @NotNull
    private static final Pattern EXP_PATTERN;
    @NotNull
    private static final Pattern imgPattern;
    @NotNull
    private static final Regex dataUriRegex;
    @NotNull
    private static final Regex nameRegex;
    @NotNull
    private static final Regex authorRegex;
    @NotNull
    private static final Regex fileNameRegex;
    @NotNull
    private static final Regex splitGroupRegex;
    @NotNull
    private static final Regex debugMessageSymbolRegex;
    @NotNull
    private static final Regex bookFileRegex;
    @NotNull
    private static final Regex bdRegex;
    @NotNull
    private static final Regex rnRegex;
    @NotNull
    private static final Regex notReadAloudRegex;

    private AppPattern() {
    }

    @NotNull
    public final Pattern getJS_PATTERN() {
        return JS_PATTERN;
    }

    @NotNull
    public final Pattern getEXP_PATTERN() {
        return EXP_PATTERN;
    }

    @NotNull
    public final Pattern getImgPattern() {
        return imgPattern;
    }

    @NotNull
    public final Regex getDataUriRegex() {
        return dataUriRegex;
    }

    @NotNull
    public final Regex getNameRegex() {
        return nameRegex;
    }

    @NotNull
    public final Regex getAuthorRegex() {
        return authorRegex;
    }

    @NotNull
    public final Regex getFileNameRegex() {
        return fileNameRegex;
    }

    @NotNull
    public final Regex getSplitGroupRegex() {
        return splitGroupRegex;
    }

    @NotNull
    public final Regex getDebugMessageSymbolRegex() {
        return debugMessageSymbolRegex;
    }

    @NotNull
    public final Regex getBookFileRegex() {
        return bookFileRegex;
    }

    @NotNull
    public final Regex getBdRegex() {
        return bdRegex;
    }

    @NotNull
    public final Regex getRnRegex() {
        return rnRegex;
    }

    @NotNull
    public final Regex getNotReadAloudRegex() {
        return notReadAloudRegex;
    }

    static {
        Pattern pattern = Pattern.compile("<js>([\\w\\W]*?)</js>|@js:([\\w\\W]*)", 2);
        Intrinsics.checkNotNullExpressionValue((Object)pattern, (String)"compile(\"<js>([\\\\w\\\\W]*?)</js>|@js:([\\\\w\\\\W]*)\", Pattern.CASE_INSENSITIVE)");
        JS_PATTERN = pattern;
        pattern = Pattern.compile("\\{\\{([\\w\\W]*?)\\}\\}");
        Intrinsics.checkNotNullExpressionValue((Object)pattern, (String)"compile(\"\\\\{\\\\{([\\\\w\\\\W]*?)\\\\}\\\\}\")");
        EXP_PATTERN = pattern;
        pattern = Pattern.compile("<img[^>]*src=\"([^\"]*(?:\"[^>]+\\})?)\"[^>]*>");
        Intrinsics.checkNotNullExpressionValue((Object)pattern, (String)"compile(\"<img[^>]*src=\\\"([^\\\"]*(?:\\\"[^>]+\\\\})?)\\\"[^>]*>\")");
        imgPattern = pattern;
        dataUriRegex = new Regex("data:.*?;base64,(.*)");
        nameRegex = new Regex("\\s+\u4f5c\\s*\u8005.*|\\s+\\S+\\s+\u8457");
        authorRegex = new Regex("^\\s*\u4f5c\\s*\u8005[:\uff1a\\s]+|\\s+\u8457");
        fileNameRegex = new Regex("[\\\\/:*?\"<>|.]");
        splitGroupRegex = new Regex("[,;\uff0c\uff1b]");
        debugMessageSymbolRegex = new Regex("[\u21d2\u25c7\u250c\u2514\u2261]");
        bookFileRegex = new Regex(".*\\.(txt|epub|umd)", RegexOption.IGNORE_CASE);
        bdRegex = new Regex("(\\p{P})+");
        rnRegex = new Regex("[\\r\\n]");
        notReadAloudRegex = new Regex("^(\\s|\\p{C}|\\p{P}|\\p{Z}|\\p{S})+$");
    }
}

