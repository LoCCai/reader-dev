/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.model.analyzeRule.AnalyzeUrl
 *  io.legado.app.utils.HtmlFormatter
 *  io.legado.app.utils.NetworkUtils
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.utils;

import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.utils.NetworkUtils;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\u000e\u001a\u00020\u0004J\u001c\u0010\u000f\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lio/legado/app/utils/HtmlFormatter;", "", "()V", "commentRegex", "Lkotlin/text/Regex;", "formatImagePattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "notImgHtmlRegex", "otherHtmlRegex", "wrapHtmlRegex", "format", "", "html", "otherRegex", "formatKeepImg", "redirectUrl", "Ljava/net/URL;", "reader-pro"})
public final class HtmlFormatter {
    @NotNull
    public static final HtmlFormatter INSTANCE = new HtmlFormatter();
    @NotNull
    private static final Regex wrapHtmlRegex;
    @NotNull
    private static final Regex commentRegex;
    @NotNull
    private static final Regex notImgHtmlRegex;
    @NotNull
    private static final Regex otherHtmlRegex;
    private static final Pattern formatImagePattern;

    private HtmlFormatter() {
    }

    @NotNull
    public final String format(@Nullable String html, @NotNull Regex otherRegex) {
        Intrinsics.checkNotNullParameter((Object)otherRegex, (String)"otherRegex");
        CharSequence charSequence = html;
        if (charSequence == null) {
            return "";
        }
        charSequence = html;
        Object object = wrapHtmlRegex;
        String string = "\n";
        boolean bl = false;
        charSequence = object.replace(charSequence, string);
        object = commentRegex;
        string = "";
        bl = false;
        charSequence = object.replace(charSequence, string);
        object = "";
        boolean bl2 = false;
        charSequence = otherRegex.replace(charSequence, (String)object);
        object = "\\s*\\n+\\s*";
        bl2 = false;
        object = new Regex((String)object);
        String string2 = "\n\u3000\u3000";
        bl = false;
        charSequence = object.replace(charSequence, string2);
        object = "^[\\n\\s]+";
        boolean bl3 = false;
        object = new Regex((String)object);
        String string3 = "\u3000\u3000";
        bl = false;
        charSequence = object.replace(charSequence, string3);
        object = "[\\n\\s]+$";
        boolean bl4 = false;
        object = new Regex((String)object);
        String string4 = "";
        bl = false;
        return object.replace(charSequence, string4);
    }

    public static /* synthetic */ String format$default(HtmlFormatter htmlFormatter, String string, Regex regex, int n, Object object) {
        if ((n & 2) != 0) {
            regex = otherHtmlRegex;
        }
        return htmlFormatter.format(string, regex);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final String formatKeepImg(@Nullable String html, @Nullable URL redirectUrl) {
        String string;
        String string2 = html;
        if (string2 == null) {
            return "";
        }
        String keepImgHtml = this.format(html, notImgHtmlRegex);
        Matcher matcher = formatImagePattern.matcher(keepImgHtml);
        int appendPos = 0;
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String string3;
            String string4;
            String param = null;
            param = "";
            Appendable appendable = sb;
            CharSequence[] charSequenceArray = new CharSequence[2];
            String string5 = keepImgHtml;
            int n = matcher.start();
            boolean bl = false;
            String string6 = string5;
            if (string6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string7 = string6.substring(appendPos, n);
            Intrinsics.checkNotNullExpressionValue((Object)string7, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            charSequenceArray[0] = string7;
            CharSequence[] charSequenceArray2 = charSequenceArray;
            int n2 = 1;
            StringBuilder stringBuilder = new StringBuilder().append("<img src=\"");
            NetworkUtils networkUtils = NetworkUtils.INSTANCE;
            URL uRL = redirectUrl;
            String string8 = matcher.group(1);
            if (string8 == null) {
                string4 = null;
            } else {
                String string9;
                void it;
                String string10 = string8;
                boolean bl2 = false;
                boolean bl3 = false;
                String string11 = string10;
                URL uRL2 = uRL;
                NetworkUtils networkUtils2 = networkUtils;
                StringBuilder stringBuilder2 = stringBuilder;
                int n3 = n2;
                CharSequence[] charSequenceArray3 = charSequenceArray2;
                Appendable appendable2 = appendable;
                boolean bl4 = false;
                Matcher urlMatcher = AnalyzeUrl.Companion.getParamPattern().matcher((CharSequence)it);
                if (urlMatcher.find()) {
                    char c = ',';
                    String string12 = it;
                    int n4 = urlMatcher.end();
                    boolean bl5 = false;
                    String string13 = string12.substring(n4);
                    Intrinsics.checkNotNullExpressionValue((Object)string13, (String)"(this as java.lang.String).substring(startIndex)");
                    string12 = string13;
                    n4 = 0;
                    param = String.valueOf(c) + string12;
                    void var18_22 = it;
                    int n5 = 0;
                    n4 = urlMatcher.start();
                    bl5 = false;
                    String string14 = var18_22.substring(n5, n4);
                    string9 = string14;
                    Intrinsics.checkNotNullExpressionValue((Object)string14, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                } else {
                    string9 = it;
                }
                void var28_34 = string9;
                appendable = appendable2;
                charSequenceArray2 = charSequenceArray3;
                n2 = n3;
                stringBuilder = stringBuilder2;
                networkUtils = networkUtils2;
                uRL = uRL2;
                string4 = var28_34;
            }
            String string15 = string4;
            String string16 = string5 = string15 == null ? matcher.group(2) : string15;
            if (string5 == null) {
                String string17 = matcher.group(3);
                string3 = string17;
                Intrinsics.checkNotNull((Object)string17);
            } else {
                string3 = string5;
            }
            charSequenceArray2[n2] = stringBuilder.append(networkUtils.getAbsoluteURL(uRL, string3)).append(param).append("\">").toString();
            StringsKt.append((Appendable)appendable, (CharSequence[])charSequenceArray);
            appendPos = matcher.end();
        }
        if (appendPos < keepImgHtml.length()) {
            string = keepImgHtml;
            int n = keepImgHtml.length();
            boolean bl = false;
            String string18 = string;
            if (string18 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string19 = string18.substring(appendPos, n);
            Intrinsics.checkNotNullExpressionValue((Object)string19, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            sb.append(string19);
        }
        string = sb.toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"sb.toString()");
        return string;
    }

    public static /* synthetic */ String formatKeepImg$default(HtmlFormatter htmlFormatter, String string, URL uRL, int n, Object object) {
        if ((n & 2) != 0) {
            uRL = null;
        }
        return htmlFormatter.formatKeepImg(string, uRL);
    }

    static {
        String string = "</?(?:div|p|br|hr|h\\d|article|dd|dl)[^>]*>";
        boolean bl = false;
        wrapHtmlRegex = new Regex(string);
        string = "<!--[^>]*-->";
        bl = false;
        commentRegex = new Regex(string);
        string = "</?(?!img)[a-zA-Z]+(?=[ >])[^<>]*>";
        bl = false;
        notImgHtmlRegex = new Regex(string);
        string = "</?[a-zA-Z]+(?=[ >])[^<>]*>";
        bl = false;
        otherHtmlRegex = new Regex(string);
        formatImagePattern = Pattern.compile("<img[^>]*src *= *\"([^\"{]*\\{(?:[^{}]|\\{[^}]+\\})+\\})\"[^>]*>|<img[^>]*data-[^=]*= *\"([^\"]*)\"[^>]*>|<img[^>]*src *= *\"([^\"]*)\"[^>]*>", 2);
    }
}

