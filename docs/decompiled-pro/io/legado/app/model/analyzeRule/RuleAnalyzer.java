/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.model.analyzeRule.RuleAnalyzer
 *  io.legado.app.model.analyzeRule.RuleAnalyzer$Companion
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmName
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.reflect.KFunction
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.model.analyzeRule;

import io.legado.app.model.analyzeRule.RuleAnalyzer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 <2\u00020\u0001:\u0001<B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\t2\u0006\u0010$\u001a\u00020\tJ\u0016\u0010%\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\t2\u0006\u0010$\u001a\u00020\tJ\u000e\u0010&\u001a\u00020\u00052\u0006\u0010'\u001a\u00020\u0003J\u001f\u0010(\u001a\u00020\u00052\u0012\u0010'\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030)\"\u00020\u0003\u00a2\u0006\u0002\u0010*J\u0014\u0010+\u001a\u00020\u00172\n\u0010'\u001a\u00020,\"\u00020\tH\u0002J8\u0010-\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u00032\b\b\u0002\u0010/\u001a\u00020\u00172\b\b\u0002\u00100\u001a\u00020\u00172\u0014\u00101\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u000302J,\u0010-\u001a\u00020\u00032\u0006\u00103\u001a\u00020\u00032\u0006\u00104\u001a\u00020\u00032\u0014\u00101\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u000302J\u0006\u00105\u001a\u000206J\u001e\u00107\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u001aj\b\u0012\u0004\u0012\u00020\u0003`\u001bH\u0083\u0010\u00a2\u0006\u0002\b8J2\u00107\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u001aj\b\u0012\u0004\u0012\u00020\u0003`\u001b2\u0012\u00109\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030)\"\u00020\u0003H\u0086\u0010\u00a2\u0006\u0002\u0010:J\u0006\u0010;\u001a\u000206R#\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u001aj\b\u0012\u0004\u0012\u00020\u0003`\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R!\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u001aj\b\u0012\u0004\u0012\u00020\u0003`\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006="}, d2={"Lio/legado/app/model/analyzeRule/RuleAnalyzer;", "", "data", "", "code", "", "(Ljava/lang/String;Z)V", "chompBalanced", "Lkotlin/reflect/KFunction2;", "", "getChompBalanced", "()Lkotlin/reflect/KFunction;", "elementsType", "getElementsType", "()Ljava/lang/String;", "setElementsType", "(Ljava/lang/String;)V", "innerType", "getInnerType", "()Z", "setInnerType", "(Z)V", "pos", "", "queue", "rule", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "ruleTypeList", "getRuleTypeList", "()Ljava/util/ArrayList;", "start", "startX", "step", "chompCodeBalanced", "open", "close", "chompRuleBalanced", "consumeTo", "seq", "consumeToAny", "", "([Ljava/lang/String;)Z", "findToAny", "", "innerRule", "inner", "startStep", "endStep", "fr", "Lkotlin/Function1;", "startStr", "endStr", "reSetPos", "", "splitRule", "splitRuleNext", "split", "([Ljava/lang/String;)Ljava/util/ArrayList;", "trim", "Companion", "reader-pro"})
public final class RuleAnalyzer {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String queue;
    private int pos;
    private int start;
    private int startX;
    @NotNull
    private ArrayList<String> rule;
    private int step;
    @NotNull
    private String elementsType;
    private boolean innerType;
    @NotNull
    private final ArrayList<String> ruleTypeList;
    @NotNull
    private final KFunction<Boolean> chompBalanced;
    private static final char ESC = '\\';

    public RuleAnalyzer(@NotNull String data, boolean code) {
        Intrinsics.checkNotNullParameter((Object)data, (String)"data");
        this.queue = data;
        this.rule = new ArrayList();
        this.elementsType = "";
        this.innerType = true;
        this.ruleTypeList = new ArrayList();
        this.chompBalanced = code ? (KFunction)new /* Unavailable Anonymous Inner Class!! */ : (KFunction)new /* Unavailable Anonymous Inner Class!! */;
    }

    public /* synthetic */ RuleAnalyzer(String string, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = false;
        }
        this(string, bl);
    }

    @NotNull
    public final String getElementsType() {
        return this.elementsType;
    }

    public final void setElementsType(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.elementsType = string;
    }

    public final boolean getInnerType() {
        return this.innerType;
    }

    public final void setInnerType(boolean bl) {
        this.innerType = bl;
    }

    public final void trim() {
        if (this.queue.charAt(this.pos) == '@' || Intrinsics.compare((int)this.queue.charAt(this.pos), (int)33) < 0) {
            RuleAnalyzer ruleAnalyzer = this;
            int n = ruleAnalyzer.pos;
            ruleAnalyzer.pos = n + 1;
            while (this.queue.charAt(this.pos) == '@' || Intrinsics.compare((int)this.queue.charAt(this.pos), (int)33) < 0) {
                ruleAnalyzer = this;
                n = ruleAnalyzer.pos;
                ruleAnalyzer.pos = n + 1;
            }
            this.start = this.pos;
            this.startX = this.pos;
        }
    }

    public final void reSetPos() {
        this.pos = 0;
        this.startX = 0;
    }

    public final boolean consumeTo(@NotNull String seq) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)seq, (String)"seq");
        this.start = this.pos;
        int offset = StringsKt.indexOf$default((CharSequence)this.queue, (String)seq, (int)this.pos, (boolean)false, (int)4, null);
        if (offset != -1) {
            this.pos = offset;
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    public final boolean consumeToAny(String ... seq) {
        Intrinsics.checkNotNullParameter((Object)seq, (String)"seq");
        int pos = this.pos;
        while (pos != this.queue.length()) {
            for (String s : seq) {
                if (!StringsKt.regionMatches$default((String)this.queue, (int)pos, (String)s, (int)0, (int)s.length(), (boolean)false, (int)16, null)) continue;
                this.step = s.length();
                this.pos = pos;
                return true;
            }
            int n = pos;
            pos = n + 1;
        }
        return false;
    }

    private final int findToAny(char ... seq) {
        int pos = this.pos;
        while (pos != this.queue.length()) {
            for (char s : seq) {
                if (this.queue.charAt(pos) != s) continue;
                return pos;
            }
            int n = pos;
            pos = n + 1;
        }
        return -1;
    }

    public final boolean chompCodeBalanced(char open, char close) {
        boolean bl;
        int pos = this.pos;
        int depth = 0;
        int otherDepth = 0;
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        while (pos != this.queue.length()) {
            int n = pos;
            pos = n + 1;
            char c = this.queue.charAt(n);
            if (c != '\\') {
                if (c == '\'' && !inDoubleQuote) {
                    inSingleQuote = !inSingleQuote;
                } else if (c == '\"' && !inSingleQuote) {
                    boolean bl2 = inDoubleQuote = !inDoubleQuote;
                }
                if (!inSingleQuote && !inDoubleQuote) {
                    if (c == '[') {
                        n = depth;
                        depth = n + 1;
                    } else if (c == ']') {
                        n = depth;
                        depth = n + -1;
                    } else if (depth == 0) {
                        if (c == open) {
                            n = otherDepth;
                            otherDepth = n + 1;
                        } else if (c == close) {
                            n = otherDepth;
                            otherDepth = n + -1;
                        }
                    }
                }
            } else {
                n = pos;
                pos = n + 1;
            }
            if (depth > 0 || otherDepth > 0) continue;
        }
        if (depth > 0 || otherDepth > 0) {
            bl = false;
        } else {
            this.pos = pos;
            bl = true;
        }
        return bl;
    }

    public final boolean chompRuleBalanced(char open, char close) {
        boolean bl;
        int pos = this.pos;
        int depth = 0;
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        while (pos != this.queue.length()) {
            int n = pos;
            pos = n + 1;
            char c = this.queue.charAt(n);
            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
            } else if (c == '\"' && !inSingleQuote) {
                boolean bl2 = inDoubleQuote = !inDoubleQuote;
            }
            if (!inSingleQuote && !inDoubleQuote) {
                if (c == '\\') {
                    n = pos;
                    pos = n + 1;
                } else if (c == open) {
                    n = depth;
                    depth = n + 1;
                } else if (c == close) {
                    n = depth;
                    depth = n + -1;
                }
            }
            if (depth > 0) continue;
        }
        if (depth > 0) {
            bl = false;
        } else {
            this.pos = pos;
            bl = true;
        }
        return bl;
    }

    @NotNull
    public final ArrayList<String> splitRule(String ... split) {
        Intrinsics.checkNotNullParameter((Object)split, (String)"split");
        RuleAnalyzer ruleAnalyzer = this;
        String[] stringArray = split;
        while (true) {
            RuleAnalyzer ruleAnalyzer2 = ruleAnalyzer;
            String[] stringArray2 = stringArray;
            if (stringArray2.length == 1) {
                ArrayList arrayList;
                ruleAnalyzer2.elementsType = stringArray2[0];
                if (!ruleAnalyzer2.consumeTo(ruleAnalyzer2.elementsType)) {
                    RuleAnalyzer ruleAnalyzer3 = ruleAnalyzer2;
                    Collection collection = ruleAnalyzer3.rule;
                    String string = ruleAnalyzer2.queue;
                    int n = ruleAnalyzer2.startX;
                    boolean bl = false;
                    String string2 = string;
                    if (string2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string3 = string2.substring(n);
                    Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.String).substring(startIndex)");
                    string = string3;
                    n = 0;
                    collection.add(string);
                    arrayList = ruleAnalyzer2.rule;
                } else {
                    ruleAnalyzer2.step = ruleAnalyzer2.elementsType.length();
                    arrayList = ruleAnalyzer2.splitRuleNext();
                }
                return arrayList;
            }
            if (!ruleAnalyzer2.consumeToAny(Arrays.copyOf(stringArray2, stringArray2.length))) {
                RuleAnalyzer ruleAnalyzer4 = ruleAnalyzer2;
                Collection collection = ruleAnalyzer4.rule;
                String string = ruleAnalyzer2.queue;
                int n = ruleAnalyzer2.startX;
                boolean bl = false;
                String string4 = string;
                if (string4 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string5 = string4.substring(n);
                Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"(this as java.lang.String).substring(startIndex)");
                string = string5;
                n = 0;
                collection.add(string);
                return ruleAnalyzer2.rule;
            }
            int end = ruleAnalyzer2.pos;
            ruleAnalyzer2.pos = ruleAnalyzer2.start;
            do {
                char next;
                Object object;
                int st2;
                if ((st2 = ruleAnalyzer2.findToAny(object = (Object)new char[]{'[', '('})) == -1) {
                    int n;
                    object = new String[1];
                    String string = ruleAnalyzer2.queue;
                    int n2 = ruleAnalyzer2.startX;
                    int n3 = 0;
                    String string6 = string;
                    if (string6 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string7 = string6.substring(n2, end);
                    Intrinsics.checkNotNullExpressionValue((Object)string7, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    object[0] = (char)string7;
                    ruleAnalyzer2.rule = CollectionsKt.arrayListOf((Object[])object);
                    object = ruleAnalyzer2.queue;
                    int n4 = end + ruleAnalyzer2.step;
                    n2 = 0;
                    char[] cArray = object;
                    if (cArray == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string8 = cArray.substring(end, n4);
                    Intrinsics.checkNotNullExpressionValue((Object)string8, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    ruleAnalyzer2.elementsType = string8;
                    ruleAnalyzer2.pos = end + ruleAnalyzer2.step;
                    while (ruleAnalyzer2.consumeTo(ruleAnalyzer2.elementsType)) {
                        object = ruleAnalyzer2;
                        Collection collection = object.rule;
                        String string9 = ruleAnalyzer2.queue;
                        n3 = ruleAnalyzer2.start;
                        n = ruleAnalyzer2.pos;
                        boolean bl = false;
                        String string10 = string9;
                        if (string10 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string11 = string10.substring(n3, n);
                        Intrinsics.checkNotNullExpressionValue((Object)string11, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        string9 = string11;
                        n3 = 0;
                        collection.add(string9);
                        object = ruleAnalyzer2;
                        object.pos += ruleAnalyzer2.step;
                    }
                    object = ruleAnalyzer2;
                    Collection collection = object.rule;
                    String string12 = ruleAnalyzer2.queue;
                    n3 = ruleAnalyzer2.pos;
                    n = 0;
                    String string13 = string12;
                    if (string13 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string14 = string13.substring(n3);
                    Intrinsics.checkNotNullExpressionValue((Object)string14, (String)"(this as java.lang.String).substring(startIndex)");
                    string12 = string14;
                    n3 = 0;
                    collection.add(string12);
                    return ruleAnalyzer2.rule;
                }
                if (st2 > end) {
                    ArrayList arrayList;
                    int n;
                    object = new String[1];
                    String string = ruleAnalyzer2.queue;
                    int n5 = ruleAnalyzer2.startX;
                    int n6 = 0;
                    String string15 = string;
                    if (string15 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string16 = string15.substring(n5, end);
                    Intrinsics.checkNotNullExpressionValue((Object)string16, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    object[0] = (char)string16;
                    ruleAnalyzer2.rule = CollectionsKt.arrayListOf((Object[])object);
                    object = ruleAnalyzer2.queue;
                    int n7 = end + ruleAnalyzer2.step;
                    n5 = 0;
                    char[] cArray = object;
                    if (cArray == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string17 = cArray.substring(end, n7);
                    Intrinsics.checkNotNullExpressionValue((Object)string17, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    ruleAnalyzer2.elementsType = string17;
                    ruleAnalyzer2.pos = end + ruleAnalyzer2.step;
                    while (ruleAnalyzer2.consumeTo(ruleAnalyzer2.elementsType) && ruleAnalyzer2.pos < st2) {
                        object = ruleAnalyzer2;
                        Collection collection = object.rule;
                        String string18 = ruleAnalyzer2.queue;
                        n6 = ruleAnalyzer2.start;
                        n = ruleAnalyzer2.pos;
                        boolean bl = false;
                        String string19 = string18;
                        if (string19 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string20 = string19.substring(n6, n);
                        Intrinsics.checkNotNullExpressionValue((Object)string20, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        string18 = string20;
                        n6 = 0;
                        collection.add(string18);
                        object = ruleAnalyzer2;
                        object.pos += ruleAnalyzer2.step;
                    }
                    if (ruleAnalyzer2.pos > st2) {
                        ruleAnalyzer2.startX = ruleAnalyzer2.start;
                        arrayList = ruleAnalyzer2.splitRuleNext();
                    } else {
                        object = ruleAnalyzer2;
                        Collection collection = object.rule;
                        String string21 = ruleAnalyzer2.queue;
                        n6 = ruleAnalyzer2.pos;
                        n = 0;
                        String string22 = string21;
                        if (string22 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string23 = string22.substring(n6);
                        Intrinsics.checkNotNullExpressionValue((Object)string23, (String)"(this as java.lang.String).substring(startIndex)");
                        string21 = string23;
                        n6 = 0;
                        collection.add(string21);
                        arrayList = ruleAnalyzer2.rule;
                    }
                    return arrayList;
                }
                ruleAnalyzer2.pos = st2;
                char c = next = ruleAnalyzer2.queue.charAt(ruleAnalyzer2.pos) == '[' ? (char)']' : ')';
                if (((Boolean)((Function2)ruleAnalyzer2.chompBalanced).invoke((Object)Character.valueOf(ruleAnalyzer2.queue.charAt(ruleAnalyzer2.pos)), (Object)Character.valueOf(next))).booleanValue()) continue;
                String string = ruleAnalyzer2.queue;
                int n = 0;
                int n8 = ruleAnalyzer2.start;
                boolean bl = false;
                String string24 = string;
                if (string24 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string25 = string24.substring(n, n8);
                Intrinsics.checkNotNullExpressionValue((Object)string25, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                throw new Error(Intrinsics.stringPlus((String)string25, (Object)"\u540e\u672a\u5e73\u8861"));
            } while (end > ruleAnalyzer2.pos);
            ruleAnalyzer2.start = ruleAnalyzer2.pos;
            ruleAnalyzer = ruleAnalyzer2;
            stringArray = Arrays.copyOf(stringArray2, stringArray2.length);
        }
    }

    @JvmName(name="splitRuleNext")
    private final ArrayList<String> splitRuleNext() {
        Object object;
        int n;
        Collection collection;
        Object object2;
        RuleAnalyzer ruleAnalyzer;
        RuleAnalyzer ruleAnalyzer2 = this;
        block0: while (true) {
            int n2;
            ruleAnalyzer = ruleAnalyzer2;
            int end = ruleAnalyzer.pos;
            ruleAnalyzer.pos = ruleAnalyzer.start;
            do {
                char next;
                boolean bl;
                int n3;
                Object object3;
                Object object4;
                int st2;
                if ((st2 = ruleAnalyzer.findToAny(object4 = new char[]{'[', '('})) == -1) {
                    object4 = ruleAnalyzer;
                    object2 = object4.rule;
                    object3 = new String[1];
                    String string = ruleAnalyzer.queue;
                    n3 = ruleAnalyzer.startX;
                    bl = false;
                    String string2 = string;
                    if (string2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    Intrinsics.checkNotNullExpressionValue((Object)string2.substring(n3, end), (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    int n4 = 0;
                    CollectionsKt.addAll((Collection)object2, (Object[])object3);
                    ruleAnalyzer.pos = end + ruleAnalyzer.step;
                    while (ruleAnalyzer.consumeTo(ruleAnalyzer.elementsType)) {
                        object4 = ruleAnalyzer;
                        object2 = object4.rule;
                        object3 = ruleAnalyzer.queue;
                        n4 = ruleAnalyzer.start;
                        n3 = ruleAnalyzer.pos;
                        bl = false;
                        Object object5 = object3;
                        if (object5 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string3 = ((String)object5).substring(n4, n3);
                        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        object3 = string3;
                        n4 = 0;
                        object2.add(object3);
                        object4 = ruleAnalyzer;
                        object4.pos += ruleAnalyzer.step;
                    }
                    object4 = ruleAnalyzer;
                    object2 = object4.rule;
                    object3 = ruleAnalyzer.queue;
                    n4 = ruleAnalyzer.pos;
                    n3 = 0;
                    Object object6 = object3;
                    if (object6 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string4 = ((String)object6).substring(n4);
                    Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"(this as java.lang.String).substring(startIndex)");
                    object3 = string4;
                    n4 = 0;
                    object2.add(object3);
                    return ruleAnalyzer.rule;
                }
                if (st2 > end) {
                    object4 = ruleAnalyzer;
                    object2 = object4.rule;
                    object3 = new String[1];
                    String string = ruleAnalyzer.queue;
                    n3 = ruleAnalyzer.startX;
                    bl = false;
                    String string5 = string;
                    if (string5 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    Intrinsics.checkNotNullExpressionValue((Object)string5.substring(n3, end), (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    object3 = CollectionsKt.arrayListOf((Object[])object3);
                    n2 = 0;
                    CollectionsKt.addAll((Collection)object2, (Iterable)object3);
                    ruleAnalyzer.pos = end + ruleAnalyzer.step;
                    while (ruleAnalyzer.consumeTo(ruleAnalyzer.elementsType) && ruleAnalyzer.pos < st2) {
                        object4 = ruleAnalyzer;
                        object2 = object4.rule;
                        object3 = ruleAnalyzer.queue;
                        n2 = ruleAnalyzer.start;
                        n3 = ruleAnalyzer.pos;
                        bl = false;
                        Object object7 = object3;
                        if (object7 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string6 = ((String)object7).substring(n2, n3);
                        Intrinsics.checkNotNullExpressionValue((Object)string6, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        object3 = string6;
                        n2 = 0;
                        object2.add(object3);
                        object4 = ruleAnalyzer;
                        object4.pos += ruleAnalyzer.step;
                    }
                    if (ruleAnalyzer.pos > st2) {
                        ruleAnalyzer.startX = ruleAnalyzer.start;
                        ruleAnalyzer2 = ruleAnalyzer;
                        continue block0;
                    }
                    object4 = ruleAnalyzer;
                    object2 = object4.rule;
                    object3 = ruleAnalyzer.queue;
                    n2 = ruleAnalyzer.pos;
                    n3 = 0;
                    Object object8 = object3;
                    if (object8 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string7 = ((String)object8).substring(n2);
                    Intrinsics.checkNotNullExpressionValue((Object)string7, (String)"(this as java.lang.String).substring(startIndex)");
                    object3 = string7;
                    n2 = 0;
                    object2.add(object3);
                    return ruleAnalyzer.rule;
                }
                ruleAnalyzer.pos = st2;
                char c = next = ruleAnalyzer.queue.charAt(ruleAnalyzer.pos) == '[' ? (char)']' : ')';
                if (((Boolean)((Function2)ruleAnalyzer.chompBalanced).invoke((Object)Character.valueOf(ruleAnalyzer.queue.charAt(ruleAnalyzer.pos)), (Object)Character.valueOf(next))).booleanValue()) continue;
                object2 = ruleAnalyzer.queue;
                int n5 = 0;
                n2 = ruleAnalyzer.start;
                n3 = 0;
                Object object9 = object2;
                if (object9 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string = ((String)object9).substring(n5, n2);
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                throw new Error(Intrinsics.stringPlus((String)string, (Object)"\u540e\u672a\u5e73\u8861"));
            } while (end > ruleAnalyzer.pos);
            ruleAnalyzer.start = ruleAnalyzer.pos;
            if (!ruleAnalyzer.consumeTo(ruleAnalyzer.elementsType)) {
                RuleAnalyzer ruleAnalyzer3 = ruleAnalyzer;
                collection = ruleAnalyzer3.rule;
                object2 = ruleAnalyzer.queue;
                n = ruleAnalyzer.startX;
                n2 = 0;
                object = object2;
                if (object == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                break;
            }
            ruleAnalyzer2 = ruleAnalyzer;
        }
        String string = ((String)object).substring(n);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"(this as java.lang.String).substring(startIndex)");
        object2 = string;
        n = 0;
        collection.add(object2);
        return ruleAnalyzer.rule;
    }

    @NotNull
    public final String innerRule(@NotNull String inner, int startStep, int endStep, @NotNull Function1<? super String, String> fr) {
        String string;
        StringBuilder stringBuilder;
        int n;
        Intrinsics.checkNotNullParameter((Object)inner, (String)"inner");
        Intrinsics.checkNotNullParameter(fr, (String)"fr");
        StringBuilder st2 = new StringBuilder();
        while (this.consumeTo(inner)) {
            int posPre = this.pos;
            if (this.chompCodeBalanced('{', '}')) {
                CharSequence charSequence = this.queue;
                n = posPre + startStep;
                int n2 = this.pos - endStep;
                boolean bl = false;
                String string2 = charSequence;
                if (string2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string3 = string2.substring(n, n2);
                Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                String frv = (String)fr.invoke((Object)string3);
                charSequence = frv;
                n = 0;
                n2 = 0;
                if (!(charSequence == null || charSequence.length() == 0)) {
                    charSequence = this.queue;
                    n = this.startX;
                    n2 = 0;
                    CharSequence charSequence2 = charSequence;
                    if (charSequence2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string4 = ((String)charSequence2).substring(n, posPre);
                    Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    st2.append(Intrinsics.stringPlus((String)string4, (Object)frv));
                    this.startX = this.pos;
                    continue;
                }
            }
            stringBuilder = this;
            ((RuleAnalyzer)stringBuilder).pos += inner.length();
        }
        if (this.startX == 0) {
            string = "";
        } else {
            stringBuilder = st2;
            boolean bl = false;
            n = 0;
            StringBuilder $this$innerRule_u24lambda_u2d0 = stringBuilder;
            boolean bl2 = false;
            String string5 = this.queue;
            int n3 = this.startX;
            boolean bl3 = false;
            String string6 = string5;
            if (string6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string7 = string6.substring(n3);
            Intrinsics.checkNotNullExpressionValue((Object)string7, (String)"(this as java.lang.String).substring(startIndex)");
            $this$innerRule_u24lambda_u2d0.append(string7);
            String string8 = stringBuilder.toString();
            Intrinsics.checkNotNullExpressionValue((Object)string8, (String)"st.apply {\n            append(queue.substring(startX))\n        }.toString()");
            string = string8;
        }
        return string;
    }

    public static /* synthetic */ String innerRule$default(RuleAnalyzer ruleAnalyzer, String string, int n, int n2, Function1 function1, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 1;
        }
        if ((n3 & 4) != 0) {
            n2 = 1;
        }
        return ruleAnalyzer.innerRule(string, n, n2, function1);
    }

    @NotNull
    public final String innerRule(@NotNull String startStr, @NotNull String endStr, @NotNull Function1<? super String, String> fr) {
        String string;
        int n;
        Intrinsics.checkNotNullParameter((Object)startStr, (String)"startStr");
        Intrinsics.checkNotNullParameter((Object)endStr, (String)"endStr");
        Intrinsics.checkNotNullParameter(fr, (String)"fr");
        StringBuilder st2 = new StringBuilder();
        while (this.consumeTo(startStr)) {
            RuleAnalyzer ruleAnalyzer = this;
            ruleAnalyzer.pos += startStr.length();
            int posPre = this.pos;
            if (!this.consumeTo(endStr)) continue;
            String string2 = this.queue;
            n = this.pos;
            int n2 = 0;
            String string3 = string2;
            if (string3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string4 = string3.substring(posPre, n);
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            String frv = (String)fr.invoke((Object)string4);
            string2 = this.queue;
            n = this.startX;
            n2 = posPre - startStr.length();
            boolean bl = false;
            String string5 = string2;
            if (string5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string6 = string5.substring(n, n2);
            Intrinsics.checkNotNullExpressionValue((Object)string6, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            st2.append(Intrinsics.stringPlus((String)string6, (Object)frv));
            string2 = this;
            ((RuleAnalyzer)string2).pos += endStr.length();
            this.startX = this.pos;
        }
        if (this.startX == 0) {
            string = this.queue;
        } else {
            StringBuilder stringBuilder = st2;
            boolean bl = false;
            n = 0;
            StringBuilder $this$innerRule_u24lambda_u2d1 = stringBuilder;
            boolean bl2 = false;
            String string7 = this.queue;
            int n3 = this.startX;
            boolean bl3 = false;
            String string8 = string7;
            if (string8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string9 = string8.substring(n3);
            Intrinsics.checkNotNullExpressionValue((Object)string9, (String)"(this as java.lang.String).substring(startIndex)");
            $this$innerRule_u24lambda_u2d1.append(string9);
            String string10 = stringBuilder.toString();
            Intrinsics.checkNotNullExpressionValue((Object)string10, (String)"st.apply {\n            append(queue.substring(startX))\n        }.toString()");
            string = string10;
        }
        return string;
    }

    @NotNull
    public final ArrayList<String> getRuleTypeList() {
        return this.ruleTypeList;
    }

    @NotNull
    public final KFunction<Boolean> getChompBalanced() {
        return this.chompBalanced;
    }
}

