/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jayway.jsonpath.Predicate
 *  com.jayway.jsonpath.ReadContext
 *  io.legado.app.model.analyzeRule.AnalyzeByJSonPath
 *  io.legado.app.model.analyzeRule.AnalyzeByJSonPath$Companion
 *  io.legado.app.model.analyzeRule.RuleAnalyzer
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model.analyzeRule;

import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.ReadContext;
import io.legado.app.model.analyzeRule.AnalyzeByJSonPath;
import io.legado.app.model.analyzeRule.RuleAnalyzer;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0002\u0010\u0003J\u001d\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\nJ\u0015\u0010\u000b\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\fJ\u0010\u0010\r\u001a\u0004\u0018\u00010\t2\u0006\u0010\b\u001a\u00020\tJ\u001b\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeByJSonPath;", "", "json", "(Ljava/lang/Object;)V", "ctx", "Lcom/jayway/jsonpath/ReadContext;", "getList", "Ljava/util/ArrayList;", "rule", "", "getList$reader_pro", "getObject", "getObject$reader_pro", "getString", "getStringList", "", "getStringList$reader_pro", "Companion", "reader-pro"})
public final class AnalyzeByJSonPath {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private ReadContext ctx;

    public AnalyzeByJSonPath(@NotNull Object json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        this.ctx = Companion.parse(json);
    }

    @Nullable
    public final String getString(@NotNull String rule) {
        Intrinsics.checkNotNullParameter((Object)rule, (String)"rule");
        CharSequence charSequence = rule;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        String result2 = null;
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(rule, true);
        Object object = new String[]{"&&", "||"};
        ArrayList rules = ruleAnalyzes.splitRule((String[])object);
        if (rules.size() == 1) {
            ruleAnalyzes.reSetPos();
            result2 = RuleAnalyzer.innerRule$default((RuleAnalyzer)ruleAnalyzes, (String)"{$.", (int)0, (int)0, (Function1)((Function1)new /* Unavailable Anonymous Inner Class!! */), (int)6, null);
            object = result2;
            boolean bl2 = false;
            if (object.length() == 0) {
                try {
                    Object ob = this.ctx.read(rule, new Predicate[0]);
                    result2 = ob instanceof List ? CollectionsKt.joinToString$default((Iterable)((Iterable)ob), (CharSequence)"\n", null, null, (int)0, null, null, (int)62, null) : ob.toString();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result2;
        }
        boolean bl3 = false;
        ArrayList<String> textList = new ArrayList<String>();
        for (String rl : rules) {
            Intrinsics.checkNotNullExpressionValue((Object)rl, (String)"rl");
            String temp = this.getString(rl);
            CharSequence charSequence2 = temp;
            boolean bl4 = false;
            boolean bl5 = false;
            if (charSequence2 == null || charSequence2.length() == 0) continue;
            textList.add(temp);
            if (!Intrinsics.areEqual((Object)ruleAnalyzes.getElementsType(), (Object)"||")) continue;
            break;
        }
        return CollectionsKt.joinToString$default((Iterable)textList, (CharSequence)"\n", null, null, (int)0, null, null, (int)62, null);
    }

    @NotNull
    public final List<String> getStringList$reader_pro(@NotNull String rule) {
        Intrinsics.checkNotNullParameter((Object)rule, (String)"rule");
        ArrayList<String> result2 = new ArrayList<String>();
        CharSequence charSequence = rule;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return result2;
        }
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(rule, true);
        String[] stringArray = new String[]{"&&", "||", "%%"};
        ArrayList rules = ruleAnalyzes.splitRule(stringArray);
        if (rules.size() == 1) {
            block16: {
                ruleAnalyzes.reSetPos();
                String st2 = RuleAnalyzer.innerRule$default((RuleAnalyzer)ruleAnalyzes, (String)"{$.", (int)0, (int)0, (Function1)((Function1)new /* Unavailable Anonymous Inner Class!! */), (int)6, null);
                CharSequence charSequence2 = st2;
                boolean bl2 = false;
                if (charSequence2.length() == 0) {
                    try {
                        Object obj = this.ctx.read(rule, new Predicate[0]);
                        if (obj instanceof List) {
                            for (Object o : (List)obj) {
                                result2.add(String.valueOf(o));
                            }
                            break block16;
                        }
                        result2.add(obj.toString());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    result2.add(st2);
                }
            }
            return result2;
        }
        ArrayList<List> results = new ArrayList<List>();
        for (String rl : rules) {
            Intrinsics.checkNotNullExpressionValue((Object)rl, (String)"rl");
            List temp = this.getStringList$reader_pro(rl);
            Object object = temp;
            boolean bl3 = false;
            if (!(!object.isEmpty())) continue;
            results.add(temp);
            object = temp;
            bl3 = false;
            if (!(!object.isEmpty()) || !Intrinsics.areEqual((Object)ruleAnalyzes.getElementsType(), (Object)"||")) continue;
        }
        if (results.size() > 0) {
            if (Intrinsics.areEqual((Object)"%%", (Object)ruleAnalyzes.getElementsType())) {
                int n = 0;
                int rl = ((List)results.get(0)).size() + -1;
                if (n <= rl) {
                    do {
                        int i = n++;
                        for (List temp : results) {
                            if (i >= temp.size()) continue;
                            result2.add((String)temp.get(i));
                        }
                    } while (n <= rl);
                }
            } else {
                for (List temp : results) {
                    result2.addAll(temp);
                }
            }
        }
        return result2;
    }

    @NotNull
    public final Object getObject$reader_pro(@NotNull String rule) {
        Intrinsics.checkNotNullParameter((Object)rule, (String)"rule");
        Object object = this.ctx.read(rule, new Predicate[0]);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"ctx.read(rule)");
        return object;
    }

    @Nullable
    public final ArrayList<Object> getList$reader_pro(@NotNull String rule) {
        Intrinsics.checkNotNullParameter((Object)rule, (String)"rule");
        ArrayList<Object> result2 = new ArrayList<Object>();
        CharSequence charSequence = rule;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return result2;
        }
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(rule, true);
        String[] stringArray = new String[]{"&&", "||", "%%"};
        ArrayList rules = ruleAnalyzes.splitRule(stringArray);
        if (rules.size() == 1) {
            stringArray = this.ctx;
            boolean bl2 = false;
            boolean bl3 = false;
            String[] it = stringArray;
            boolean bl4 = false;
            try {
                return (ArrayList)it.read((String)rules.get(0), new Predicate[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ArrayList<ArrayList> results = new ArrayList<ArrayList>();
            for (String rl : rules) {
                Intrinsics.checkNotNullExpressionValue((Object)rl, (String)"rl");
                ArrayList temp = this.getList$reader_pro(rl);
                if (temp == null) continue;
                Object object = temp;
                boolean e = false;
                if (!(!object.isEmpty())) continue;
                results.add(temp);
                object = temp;
                e = false;
                if (!(!object.isEmpty()) || !Intrinsics.areEqual((Object)ruleAnalyzes.getElementsType(), (Object)"||")) continue;
            }
            if (results.size() > 0) {
                if (Intrinsics.areEqual((Object)"%%", (Object)ruleAnalyzes.getElementsType())) {
                    int n = 0;
                    int rl = ((ArrayList)results.get(0)).size();
                    if (n < rl) {
                        do {
                            int i = n++;
                            for (ArrayList temp : results) {
                                Object e;
                                if (i >= temp.size() || (e = temp.get(i)) == null) continue;
                                Object e2 = e;
                                boolean bl5 = false;
                                boolean bl6 = false;
                                Object it = e2;
                                boolean bl7 = false;
                                result2.add(it);
                            }
                        } while (n < rl);
                    }
                } else {
                    for (ArrayList temp : results) {
                        result2.addAll(temp);
                    }
                }
            }
        }
        return result2;
    }
}

