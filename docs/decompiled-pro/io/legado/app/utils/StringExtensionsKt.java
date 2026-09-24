/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.constant.AppPattern
 *  io.legado.app.utils.StringExtensionsKt
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.utils;

import io.legado.app.constant.AppPattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000&\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u001a\f\u0010\u0004\u001a\u00020\u0002*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0005\u001a\u00020\u0006*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0007\u001a\u00020\u0006*\u0004\u0018\u00010\u0002\u001a\f\u0010\b\u001a\u00020\u0006*\u0004\u0018\u00010\u0002\u001a\f\u0010\t\u001a\u00020\u0006*\u0004\u0018\u00010\u0002\u001a\f\u0010\n\u001a\u00020\u0006*\u0004\u0018\u00010\u0002\u001a\u0016\u0010\u000b\u001a\u00020\u0006*\u0004\u0018\u00010\u00022\b\b\u0002\u0010\f\u001a\u00020\u0006\u001a\f\u0010\r\u001a\u00020\u0006*\u0004\u0018\u00010\u0002\u001a\u000e\u0010\u000e\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u0002\u001a)\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u00022\u0012\u0010\u0011\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0010\"\u00020\u0002\u00a2\u0006\u0002\u0010\u0012\u001a'\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u0001\u00a2\u0006\u0002\u0010\u0016\u001a\u0012\u0010\u0017\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002\u001a\u0015\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u0002\u00a2\u0006\u0002\u0010\u001a\u00a8\u0006\u001b"}, d2={"cnCompare", "", "", "other", "htmlFormat", "isAbsUrl", "", "isDataUrl", "isJson", "isJsonArray", "isJsonObject", "isTrue", "nullIsTrue", "isXml", "safeTrim", "splitNotBlank", "", "delimiter", "(Ljava/lang/String;[Ljava/lang/String;)[Ljava/lang/String;", "regex", "Lkotlin/text/Regex;", "limit", "(Ljava/lang/String;Lkotlin/text/Regex;I)[Ljava/lang/String;", "startWithIgnoreCase", "start", "toStringArray", "(Ljava/lang/String;)[Ljava/lang/String;", "reader-pro"})
public final class StringExtensionsKt {
    @Nullable
    public static final String safeTrim(@Nullable String $this$safeTrim) {
        String string;
        CharSequence charSequence = $this$safeTrim;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || StringsKt.isBlank((CharSequence)charSequence)) {
            string = null;
        } else {
            charSequence = $this$safeTrim;
            bl = false;
            CharSequence charSequence2 = charSequence;
            if (charSequence2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
            }
            string = ((Object)StringsKt.trim((CharSequence)charSequence2)).toString();
        }
        return string;
    }

    public static final boolean isAbsUrl(@Nullable String $this$isAbsUrl) {
        CharSequence charSequence = $this$isAbsUrl;
        boolean bl = false;
        boolean bl2 = false;
        return charSequence == null || StringsKt.isBlank((CharSequence)charSequence) ? false : StringsKt.startsWith((String)$this$isAbsUrl, (String)"http://", (boolean)true) || StringsKt.startsWith((String)$this$isAbsUrl, (String)"https://", (boolean)true);
    }

    public static final boolean isDataUrl(@Nullable String $this$isDataUrl) {
        boolean bl;
        String string = $this$isDataUrl;
        if (string == null) {
            bl = false;
        } else {
            boolean bl2;
            String string2 = string;
            boolean bl3 = false;
            boolean bl4 = false;
            String it = string2;
            boolean bl5 = false;
            bl = bl2 = AppPattern.INSTANCE.getDataUriRegex().matches((CharSequence)it);
        }
        return bl;
    }

    public static final boolean isJson(@Nullable String $this$isJson) {
        boolean bl;
        String string = $this$isJson;
        if (string == null) {
            bl = false;
        } else {
            boolean bl2;
            String string2 = string;
            boolean bl3 = false;
            boolean bl4 = false;
            String $this$isJson_u24lambda_u2d1 = string2;
            boolean bl5 = false;
            String string3 = $this$isJson_u24lambda_u2d1;
            boolean bl6 = false;
            String str = ((Object)StringsKt.trim((CharSequence)string3)).toString();
            bl = bl2 = StringsKt.startsWith$default((String)str, (String)"{", (boolean)false, (int)2, null) && StringsKt.endsWith$default((String)str, (String)"}", (boolean)false, (int)2, null) ? true : StringsKt.startsWith$default((String)str, (String)"[", (boolean)false, (int)2, null) && StringsKt.endsWith$default((String)str, (String)"]", (boolean)false, (int)2, null);
        }
        return bl;
    }

    public static final boolean isJsonObject(@Nullable String $this$isJsonObject) {
        boolean bl;
        String string = $this$isJsonObject;
        if (string == null) {
            bl = false;
        } else {
            boolean bl2;
            String string2 = string;
            boolean bl3 = false;
            boolean bl4 = false;
            String $this$isJsonObject_u24lambda_u2d2 = string2;
            boolean bl5 = false;
            String string3 = $this$isJsonObject_u24lambda_u2d2;
            boolean bl6 = false;
            String str = ((Object)StringsKt.trim((CharSequence)string3)).toString();
            bl = bl2 = StringsKt.startsWith$default((String)str, (String)"{", (boolean)false, (int)2, null) && StringsKt.endsWith$default((String)str, (String)"}", (boolean)false, (int)2, null);
        }
        return bl;
    }

    public static final boolean isJsonArray(@Nullable String $this$isJsonArray) {
        boolean bl;
        String string = $this$isJsonArray;
        if (string == null) {
            bl = false;
        } else {
            boolean bl2;
            String string2 = string;
            boolean bl3 = false;
            boolean bl4 = false;
            String $this$isJsonArray_u24lambda_u2d3 = string2;
            boolean bl5 = false;
            String string3 = $this$isJsonArray_u24lambda_u2d3;
            boolean bl6 = false;
            String str = ((Object)StringsKt.trim((CharSequence)string3)).toString();
            bl = bl2 = StringsKt.startsWith$default((String)str, (String)"[", (boolean)false, (int)2, null) && StringsKt.endsWith$default((String)str, (String)"]", (boolean)false, (int)2, null);
        }
        return bl;
    }

    public static final boolean isXml(@Nullable String $this$isXml) {
        boolean bl;
        String string = $this$isXml;
        if (string == null) {
            bl = false;
        } else {
            boolean bl2;
            String string2 = string;
            boolean bl3 = false;
            boolean bl4 = false;
            String $this$isXml_u24lambda_u2d4 = string2;
            boolean bl5 = false;
            String string3 = $this$isXml_u24lambda_u2d4;
            boolean bl6 = false;
            String str = ((Object)StringsKt.trim((CharSequence)string3)).toString();
            bl = bl2 = StringsKt.startsWith$default((String)str, (String)"<", (boolean)false, (int)2, null) && StringsKt.endsWith$default((String)str, (String)">", (boolean)false, (int)2, null);
        }
        return bl;
    }

    public static final boolean isTrue(@Nullable String $this$isTrue, boolean nullIsTrue) {
        CharSequence charSequence = $this$isTrue;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || StringsKt.isBlank((CharSequence)charSequence) || Intrinsics.areEqual((Object)$this$isTrue, (Object)"null")) {
            return nullIsTrue;
        }
        charSequence = $this$isTrue;
        String string = "\\s*(?i)(false|no|not|0)\\s*";
        bl2 = false;
        string = new Regex(string);
        bl2 = false;
        return !string.matches(charSequence);
    }

    public static /* synthetic */ boolean isTrue$default(String string, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        return StringExtensionsKt.isTrue((String)string, (boolean)bl);
    }

    @NotNull
    public static final String htmlFormat(@Nullable String $this$htmlFormat) {
        String string;
        CharSequence charSequence = $this$htmlFormat;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || StringsKt.isBlank((CharSequence)charSequence)) {
            string = "";
        } else {
            charSequence = $this$htmlFormat;
            String string2 = "(?i)<(br[\\s/]*|/*p\\b.*?|/*div\\b.*?)>";
            bl2 = false;
            string2 = new Regex(string2);
            String string3 = "\n";
            boolean bl3 = false;
            charSequence = string2.replace(charSequence, string3);
            string2 = "<[script>]*.*?>|&nbsp;";
            boolean bl4 = false;
            string2 = new Regex(string2);
            String string4 = "";
            bl3 = false;
            charSequence = string2.replace(charSequence, string4);
            string2 = "\\s*\\n+\\s*";
            boolean bl5 = false;
            string2 = new Regex(string2);
            String string5 = "\n\u3000\u3000";
            bl3 = false;
            charSequence = string2.replace(charSequence, string5);
            string2 = "^[\\n\\s]+";
            boolean bl6 = false;
            string2 = new Regex(string2);
            String string6 = "\u3000\u3000";
            bl3 = false;
            charSequence = string2.replace(charSequence, string6);
            string2 = "[\\n\\s]+$";
            boolean bl7 = false;
            string2 = new Regex(string2);
            String string7 = "";
            bl3 = false;
            string = string2.replace(charSequence, string7);
        }
        return string;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String[] splitNotBlank(@NotNull String $this$splitNotBlank, String ... delimiter) {
        void $this$toTypedArray$iv;
        void $this$filterNotTo$iv$iv;
        Collection $this$filterNot$iv;
        String it;
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)$this$splitNotBlank, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)delimiter, (String)"delimiter");
        String string = $this$splitNotBlank;
        boolean bl = false;
        boolean bl2 = false;
        String $this$splitNotBlank_u24lambda_u2d7 = string;
        boolean bl3 = false;
        Iterable $this$map$iv = StringsKt.split$default((CharSequence)$this$splitNotBlank_u24lambda_u2d7, (String[])Arrays.copyOf(delimiter, delimiter.length), (boolean)false, (int)0, (int)6, null);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            String string2 = (String)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl4 = false;
            void var17_17 = it;
            boolean bl5 = false;
            void v0 = var17_17;
            if (v0 == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
            }
            String string3 = ((Object)StringsKt.trim((CharSequence)((CharSequence)v0))).toString();
            collection.add(string3);
        }
        $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$filterNot = false;
        $this$mapTo$iv$iv = $this$filterNot$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterNotTo = false;
        for (Object element$iv$iv : $this$filterNotTo$iv$iv) {
            it = (String)element$iv$iv;
            boolean bl6 = false;
            if (StringsKt.isBlank((CharSequence)it)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filterNot$iv = (List)destination$iv$iv;
        boolean $i$f$toTypedArray = false;
        void thisCollection$iv = $this$toTypedArray$iv;
        String[] stringArray = thisCollection$iv.toArray(new String[0]);
        if (stringArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return stringArray;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String[] splitNotBlank(@NotNull String $this$splitNotBlank, @NotNull Regex regex, int limit) {
        void $this$toTypedArray$iv;
        void $this$filterNotTo$iv$iv;
        Collection $this$filterNot$iv;
        String it;
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)$this$splitNotBlank, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)regex, (String)"regex");
        String string = $this$splitNotBlank;
        boolean bl = false;
        boolean bl2 = false;
        String $this$splitNotBlank_u24lambda_u2d10 = string;
        boolean bl3 = false;
        Object object = $this$splitNotBlank_u24lambda_u2d10;
        boolean bl4 = false;
        object = regex.split((CharSequence)object, limit);
        boolean $i$f$map = false;
        void var10_10 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            String string2 = (String)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl5 = false;
            void var18_18 = it;
            boolean bl6 = false;
            void v0 = var18_18;
            if (v0 == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
            }
            String string3 = ((Object)StringsKt.trim((CharSequence)((CharSequence)v0))).toString();
            collection.add(string3);
        }
        $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$filterNot = false;
        $this$mapTo$iv$iv = $this$filterNot$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterNotTo = false;
        for (Object element$iv$iv : $this$filterNotTo$iv$iv) {
            it = (String)element$iv$iv;
            boolean bl7 = false;
            if (StringsKt.isBlank((CharSequence)it)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filterNot$iv = (List)destination$iv$iv;
        boolean $i$f$toTypedArray = false;
        void thisCollection$iv = $this$toTypedArray$iv;
        String[] stringArray = thisCollection$iv.toArray(new String[0]);
        if (stringArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return stringArray;
    }

    public static /* synthetic */ String[] splitNotBlank$default(String string, Regex regex, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        return StringExtensionsKt.splitNotBlank((String)string, (Regex)regex, (int)n);
    }

    public static final boolean startWithIgnoreCase(@NotNull String $this$startWithIgnoreCase, @NotNull String start2) {
        Intrinsics.checkNotNullParameter((Object)$this$startWithIgnoreCase, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)start2, (String)"start");
        return StringsKt.isBlank((CharSequence)$this$startWithIgnoreCase) ? false : StringsKt.startsWith((String)$this$startWithIgnoreCase, (String)start2, (boolean)true);
    }

    public static final int cnCompare(@NotNull String $this$cnCompare, @NotNull String other) {
        Intrinsics.checkNotNullParameter((Object)$this$cnCompare, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        return $this$cnCompare.compareTo(other);
    }

    @NotNull
    public static final String[] toStringArray(@NotNull String $this$toStringArray) {
        String[] stringArray;
        Intrinsics.checkNotNullParameter((Object)$this$toStringArray, (String)"<this>");
        int codePointIndex = 0;
        try {
            int n = 0;
            String[] stringArray2 = $this$toStringArray;
            int n2 = 0;
            int n3 = $this$toStringArray.length();
            boolean bl = false;
            int n4 = stringArray2.codePointCount(n2, n3);
            stringArray2 = new String[n4];
            while (n < n4) {
                n2 = n++;
                int start2 = codePointIndex;
                String[] stringArray3 = $this$toStringArray;
                int n5 = 1;
                boolean bl2 = false;
                codePointIndex = stringArray3.offsetByCodePoints(start2, n5);
                stringArray3 = $this$toStringArray;
                n5 = 0;
                Intrinsics.checkNotNullExpressionValue((Object)stringArray3.substring(start2, codePointIndex), (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            }
            stringArray = stringArray2;
        }
        catch (Exception e) {
            String[] stringArray4 = new String[]{""};
            Collection $this$toTypedArray$iv = StringsKt.split$default((CharSequence)((CharSequence)$this$toStringArray), (String[])stringArray4, (boolean)false, (int)0, (int)6, null);
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            String[] stringArray5 = thisCollection$iv.toArray(new String[0]);
            if (stringArray5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            stringArray = stringArray5;
        }
        return stringArray;
    }
}

