/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.model.analyzeRule.AnalyzeByJSoup
 *  io.legado.app.model.analyzeRule.AnalyzeByJSoup$Companion
 *  io.legado.app.model.analyzeRule.AnalyzeByJSoup$ElementsSingle
 *  io.legado.app.model.analyzeRule.AnalyzeByJSoup$SourceRule
 *  io.legado.app.model.analyzeRule.RuleAnalyzer
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jsoup.nodes.Element
 *  org.jsoup.nodes.TextNode
 *  org.jsoup.select.Elements
 */
package io.legado.app.model.analyzeRule;

import io.legado.app.model.analyzeRule.AnalyzeByJSoup;
import io.legado.app.model.analyzeRule.RuleAnalyzer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000e\u0018\u0000 \u00182\u00020\u0001:\u0003\u0018\u0019\u001aB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0002\u0010\u0003J\u0015\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\nJ\u001a\u0010\u0006\u001a\u00020\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u0002J\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\tH\u0002J\u0018\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\r2\u0006\u0010\u0011\u001a\u00020\tH\u0002J\u0017\u0010\u0012\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0011\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\u0013J\u0015\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\u0015J\u001b\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\r2\u0006\u0010\u0011\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeByJSoup;", "", "doc", "(Ljava/lang/Object;)V", "element", "Lorg/jsoup/nodes/Element;", "getElements", "Lorg/jsoup/select/Elements;", "rule", "", "getElements$reader_pro", "temp", "getResultLast", "", "elements", "lastRule", "getResultList", "ruleStr", "getString", "getString$reader_pro", "getString0", "getString0$reader_pro", "getStringList", "getStringList$reader_pro", "Companion", "ElementsSingle", "SourceRule", "reader-pro"})
public final class AnalyzeByJSoup {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Element element;
    @NotNull
    private static final String[] validKeys;

    public AnalyzeByJSoup(@NotNull Object doc) {
        Intrinsics.checkNotNullParameter((Object)doc, (String)"doc");
        this.element = Companion.parse(doc);
    }

    @NotNull
    public final Elements getElements$reader_pro(@NotNull String rule) {
        Intrinsics.checkNotNullParameter((Object)rule, (String)"rule");
        return this.getElements(this.element, rule);
    }

    @Nullable
    public final String getString$reader_pro(@NotNull String ruleStr) {
        String string;
        Intrinsics.checkNotNullParameter((Object)ruleStr, (String)"ruleStr");
        Object object = ruleStr;
        boolean bl = false;
        if (object.length() == 0) {
            string = null;
        } else {
            List list2 = this.getStringList$reader_pro(ruleStr);
            boolean bl2 = false;
            boolean bl3 = false;
            List it = list2;
            boolean bl4 = false;
            Collection collection = it;
            boolean bl5 = false;
            object = !collection.isEmpty() ? list2 : null;
            string = object == null ? null : CollectionsKt.joinToString$default((Iterable)((Iterable)object), (CharSequence)"\n", null, null, (int)0, null, null, (int)62, null);
        }
        return string;
    }

    @NotNull
    public final String getString0$reader_pro(@NotNull String ruleStr) {
        Intrinsics.checkNotNullParameter((Object)ruleStr, (String)"ruleStr");
        List list2 = this.getStringList$reader_pro(ruleStr);
        boolean bl = false;
        boolean bl2 = false;
        List it = list2;
        boolean bl3 = false;
        return it.isEmpty() ? "" : (String)it.get(0);
    }

    @NotNull
    public final List<String> getStringList$reader_pro(@NotNull String ruleStr) {
        Intrinsics.checkNotNullParameter((Object)ruleStr, (String)"ruleStr");
        ArrayList<String> textS = new ArrayList<String>();
        CharSequence charSequence = ruleStr;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return textS;
        }
        SourceRule sourceRule = new SourceRule(this, ruleStr);
        CharSequence charSequence2 = sourceRule.getElementsRule();
        boolean bl2 = false;
        if (charSequence2.length() == 0) {
            charSequence2 = this.element.data();
            textS.add((String)(charSequence2 == null ? "" : charSequence2));
        } else {
            RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(sourceRule.getElementsRule(), false, 2, null);
            String[] stringArray = new String[]{"&&", "||", "%%"};
            ArrayList ruleStrS = ruleAnalyzes.splitRule(stringArray);
            ArrayList<List> results = new ArrayList<List>();
            for (String ruleStrX : ruleStrS) {
                List list2;
                int n;
                if (sourceRule.isCss()) {
                    Intrinsics.checkNotNullExpressionValue((Object)ruleStrX, (String)"ruleStrX");
                    int lastIndex = StringsKt.lastIndexOf$default((CharSequence)ruleStrX, (char)'@', (int)0, (boolean)false, (int)6, null);
                    String string = ruleStrX;
                    int n2 = 0;
                    boolean bl3 = false;
                    String string2 = string.substring(n2, lastIndex);
                    Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    Object object = this.element.select(string2);
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"element.select(ruleStrX.substring(0, lastIndex))");
                    Elements elements = object;
                    object = ruleStrX;
                    n = lastIndex + 1;
                    n2 = 0;
                    String string3 = ((String)object).substring(n);
                    Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.String).substring(startIndex)");
                    list2 = this.getResultLast(elements, string3);
                } else {
                    Intrinsics.checkNotNullExpressionValue((Object)ruleStrX, (String)"ruleStrX");
                    list2 = this.getResultList(ruleStrX);
                }
                List temp = list2;
                Collection collection = temp;
                boolean bl4 = false;
                n = 0;
                if (collection == null || collection.isEmpty()) continue;
                results.add(temp);
                if (!Intrinsics.areEqual((Object)ruleAnalyzes.getElementsType(), (Object)"||")) continue;
            }
            if (results.size() > 0) {
                if (Intrinsics.areEqual((Object)"%%", (Object)ruleAnalyzes.getElementsType())) {
                    int n = 0;
                    int ruleStrX = ((List)results.get(0)).size() + -1;
                    if (n <= ruleStrX) {
                        do {
                            int i = n++;
                            for (List temp : results) {
                                if (i >= temp.size()) continue;
                                textS.add((String)temp.get(i));
                            }
                        } while (n <= ruleStrX);
                    }
                } else {
                    for (List temp : results) {
                        textS.addAll(temp);
                    }
                }
            }
        }
        return textS;
    }

    private final Elements getElements(Element temp, String rule) {
        block22: {
            block21: {
                if (temp == null) break block21;
                CharSequence charSequence = rule;
                boolean bl = false;
                if (!(charSequence.length() == 0)) break block22;
            }
            return new Elements();
        }
        Elements elements = new Elements();
        SourceRule sourceRule = new SourceRule(this, rule);
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(sourceRule.getElementsRule(), false, 2, null);
        String[] stringArray = new String[]{"&&", "||", "%%"};
        ArrayList ruleStrS = ruleAnalyzes.splitRule(stringArray);
        ArrayList<Elements> elementsList = new ArrayList<Elements>();
        if (sourceRule.isCss()) {
            for (String ruleStr : ruleStrS) {
                Elements tempS = temp.select(ruleStr);
                elementsList.add(tempS);
                if (tempS.size() <= 0 || !Intrinsics.areEqual((Object)ruleAnalyzes.getElementsType(), (Object)"||")) continue;
                break;
            }
        } else {
            for (String ruleStr : ruleStrS) {
                Elements elements2;
                Intrinsics.checkNotNullExpressionValue((Object)ruleStr, (String)"ruleStr");
                RuleAnalyzer rsRule = new RuleAnalyzer(ruleStr, false, 2, null);
                rsRule.trim();
                String[] stringArray2 = new String[]{"@"};
                ArrayList rs = rsRule.splitRule(stringArray2);
                if (rs.size() > 1) {
                    Elements el = new Elements();
                    el.add((Object)temp);
                    for (String rl : rs) {
                        Elements es = new Elements();
                        for (Element et : el) {
                            Intrinsics.checkNotNullExpressionValue((Object)rl, (String)"rl");
                            es.addAll((Collection)this.getElements(et, rl));
                        }
                        el.clear();
                        el.addAll((Collection)es);
                    }
                    elements2 = el;
                } else {
                    elements2 = new ElementsSingle('\u0000', null, null, null, 15, null).getElementsSingle(temp, ruleStr);
                }
                Elements el = elements2;
                elementsList.add(el);
                if (el.size() <= 0 || !Intrinsics.areEqual((Object)ruleAnalyzes.getElementsType(), (Object)"||")) continue;
            }
        }
        if (elementsList.size() > 0) {
            if (Intrinsics.areEqual((Object)"%%", (Object)ruleAnalyzes.getElementsType())) {
                int n = 0;
                int ruleStr = ((Elements)elementsList.get(0)).size();
                if (n < ruleStr) {
                    do {
                        int i = n++;
                        for (Elements es : elementsList) {
                            if (i >= es.size()) continue;
                            elements.add(es.get(i));
                        }
                    } while (n < ruleStr);
                }
            } else {
                for (Elements es : elementsList) {
                    elements.addAll((Collection)es);
                }
            }
        }
        return elements;
    }

    private final List<String> getResultList(String ruleStr) {
        List list2;
        CharSequence charSequence = ruleStr;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        Elements elements = new Elements();
        elements.add((Object)this.element);
        RuleAnalyzer rule = new RuleAnalyzer(ruleStr, false, 2, null);
        rule.trim();
        String[] stringArray = new String[]{"@"};
        ArrayList rules = rule.splitRule(stringArray);
        int last = rules.size() - 1;
        int n = 0;
        if (n < last) {
            do {
                int i = n++;
                Elements es = new Elements();
                for (Element elt : elements) {
                    ElementsSingle elementsSingle = new ElementsSingle('\u0000', null, null, null, 15, null);
                    Intrinsics.checkNotNullExpressionValue((Object)elt, (String)"elt");
                    Object e = rules.get(i);
                    Intrinsics.checkNotNullExpressionValue(e, (String)"rules[i]");
                    es.addAll((Collection)elementsSingle.getElementsSingle(elt, (String)e));
                }
                elements.clear();
                elements = es;
            } while (n < last);
        }
        if (elements.isEmpty()) {
            list2 = null;
        } else {
            Object e = rules.get(last);
            Intrinsics.checkNotNullExpressionValue(e, (String)"rules[last]");
            list2 = this.getResultLast(elements, (String)e);
        }
        return list2;
    }

    private final List<String> getResultLast(Elements elements, String lastRule) {
        ArrayList<String> textS = new ArrayList<String>();
        switch (lastRule) {
            case "text": {
                for (Element element : elements) {
                    String text = element.text();
                    Intrinsics.checkNotNullExpressionValue((Object)text, (String)"text");
                    CharSequence charSequence = text;
                    boolean bl = false;
                    if (!(charSequence.length() > 0)) continue;
                    textS.add(text);
                }
                break;
            }
            case "textNodes": {
                for (Element element : elements) {
                    boolean bl = false;
                    ArrayList<String> tn = new ArrayList<String>();
                    List contentEs = element.textNodes();
                    for (TextNode item : contentEs) {
                        CharSequence charSequence = item.text();
                        Intrinsics.checkNotNullExpressionValue((Object)charSequence, (String)"item.text()");
                        String $this$trim$iv = charSequence;
                        boolean $i$f$trim = false;
                        CharSequence $this$trim$iv$iv = $this$trim$iv;
                        boolean $i$f$trim2 = false;
                        int startIndex$iv$iv = 0;
                        int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                        boolean startFound$iv$iv = false;
                        while (startIndex$iv$iv <= endIndex$iv$iv) {
                            boolean match$iv$iv;
                            int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                            char it = $this$trim$iv$iv.charAt(index$iv$iv);
                            boolean bl2 = false;
                            boolean bl3 = match$iv$iv = Intrinsics.compare((int)it, (int)32) <= 0;
                            if (!startFound$iv$iv) {
                                if (!match$iv$iv) {
                                    startFound$iv$iv = true;
                                    continue;
                                }
                                ++startIndex$iv$iv;
                                continue;
                            }
                            if (!match$iv$iv) break;
                            --endIndex$iv$iv;
                        }
                        String text = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
                        charSequence = text;
                        boolean bl4 = false;
                        if (!(charSequence.length() > 0)) continue;
                        tn.add(text);
                    }
                    Collection collection = tn;
                    boolean bl5 = false;
                    if (!(!collection.isEmpty())) continue;
                    textS.add(CollectionsKt.joinToString$default((Iterable)tn, (CharSequence)"\n", null, null, (int)0, null, null, (int)62, null));
                }
                break;
            }
            case "ownText": {
                for (Element element : elements) {
                    String text = element.ownText();
                    Intrinsics.checkNotNullExpressionValue((Object)text, (String)"text");
                    CharSequence charSequence = text;
                    boolean bl = false;
                    if (!(charSequence.length() > 0)) continue;
                    textS.add(text);
                }
                break;
            }
            case "html": {
                elements.select("script").remove();
                elements.select("style").remove();
                String html = elements.outerHtml();
                Intrinsics.checkNotNullExpressionValue((Object)html, (String)"html");
                CharSequence element = html;
                boolean text = false;
                if (!(element.length() > 0)) break;
                textS.add(html);
                break;
            }
            case "all": {
                textS.add(elements.outerHtml());
                break;
            }
            default: {
                for (Element element : elements) {
                    String url2 = element.attr(lastRule);
                    Intrinsics.checkNotNullExpressionValue((Object)url2, (String)"url");
                    if (StringsKt.isBlank((CharSequence)url2) || textS.contains(url2)) continue;
                    textS.add(url2);
                }
            }
        }
        return textS;
    }

    public static final /* synthetic */ String[] access$getValidKeys$cp() {
        return validKeys;
    }

    static {
        String[] stringArray = new String[]{"class", "id", "tag", "text", "children"};
        validKeys = stringArray;
    }
}

