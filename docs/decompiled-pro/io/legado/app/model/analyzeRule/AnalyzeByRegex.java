/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.model.analyzeRule.AnalyzeByRegex
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model.analyzeRule;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J3\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b2\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ7\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00042\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b2\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\r"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeByRegex;", "", "()V", "getElement", "", "", "res", "regs", "", "index", "", "(Ljava/lang/String;[Ljava/lang/String;I)Ljava/util/List;", "getElements", "reader-pro"})
public final class AnalyzeByRegex {
    @NotNull
    public static final AnalyzeByRegex INSTANCE = new AnalyzeByRegex();

    private AnalyzeByRegex() {
    }

    @Nullable
    public final List<String> getElement(@NotNull String res, @NotNull String[] regs, int index) {
        List list2;
        Intrinsics.checkNotNullParameter((Object)res, (String)"res");
        Intrinsics.checkNotNullParameter((Object)regs, (String)"regs");
        int vIndex = index;
        Matcher resM = Pattern.compile(regs[vIndex]).matcher(res);
        if (!resM.find()) {
            return null;
        }
        if (vIndex + 1 == regs.length) {
            int n = 0;
            ArrayList<String> info = new ArrayList<String>();
            n = 0;
            int n2 = resM.groupCount();
            if (n <= n2) {
                int groupIndex;
                do {
                    groupIndex = n++;
                    String string = resM.group(groupIndex);
                    Intrinsics.checkNotNull((Object)string);
                    info.add(string);
                } while (groupIndex != n2);
            }
            list2 = info;
        } else {
            StringBuilder result2 = new StringBuilder();
            do {
                result2.append(resM.group());
            } while (resM.find());
            String string = result2.toString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"result.toString()");
            list2 = this.getElement(string, regs, ++vIndex);
        }
        return list2;
    }

    public static /* synthetic */ List getElement$default(AnalyzeByRegex analyzeByRegex, String string, String[] stringArray, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 0;
        }
        return analyzeByRegex.getElement(string, stringArray, n);
    }

    @NotNull
    public final List<List<String>> getElements(@NotNull String res, @NotNull String[] regs, int index) {
        Intrinsics.checkNotNullParameter((Object)res, (String)"res");
        Intrinsics.checkNotNullParameter((Object)regs, (String)"regs");
        int vIndex = index;
        Matcher resM = Pattern.compile(regs[vIndex]).matcher(res);
        if (!resM.find()) {
            boolean bl = false;
            return new ArrayList();
        }
        if (vIndex + 1 == regs.length) {
            ArrayList books = new ArrayList();
            do {
                int n = 0;
                ArrayList<String> info = new ArrayList<String>();
                n = 0;
                int n2 = resM.groupCount();
                if (n <= n2) {
                    int groupIndex;
                    do {
                        String string;
                        info.add((string = resM.group(groupIndex = n++)) == null ? "" : string);
                    } while (groupIndex != n2);
                }
                books.add(info);
            } while (resM.find());
            return books;
        }
        StringBuilder result2 = new StringBuilder();
        do {
            result2.append(resM.group());
        } while (resM.find());
        String string = result2.toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"result.toString()");
        return this.getElements(string, regs, ++vIndex);
    }

    public static /* synthetic */ List getElements$default(AnalyzeByRegex analyzeByRegex, String string, String[] stringArray, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 0;
        }
        return analyzeByRegex.getElements(string, stringArray, n);
    }
}

