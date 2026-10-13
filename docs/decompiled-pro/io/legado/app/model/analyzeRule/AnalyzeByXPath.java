/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.model.analyzeRule.AnalyzeByXPath
 *  io.legado.app.model.analyzeRule.RuleAnalyzer
 *  io.legado.app.utils.TextUtils
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 *  org.seimicrawler.xpath.JXDocument
 *  org.seimicrawler.xpath.JXNode
 */
package io.legado.app.model.analyzeRule;

import io.legado.app.model.analyzeRule.RuleAnalyzer;
import io.legado.app.utils.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0002\u0010\u0003J\u001d\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\nJ\u0018\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\f\u001a\u0004\u0018\u00010\t2\u0006\u0010\r\u001a\u00020\tJ\u001b\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\u000fJ\u0010\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\tH\u0002R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeByXPath;", "", "doc", "(Ljava/lang/Object;)V", "jxNode", "getElements", "", "Lorg/seimicrawler/xpath/JXNode;", "xPath", "", "getElements$reader_pro", "getResult", "getString", "rule", "getStringList", "getStringList$reader_pro", "parse", "strToJXDocument", "Lorg/seimicrawler/xpath/JXDocument;", "html", "reader-pro"})
public final class AnalyzeByXPath {
    @NotNull
    private Object jxNode;

    public AnalyzeByXPath(@NotNull Object doc) {
        Intrinsics.checkNotNullParameter((Object)doc, (String)"doc");
        this.jxNode = this.parse(doc);
    }

    private final Object parse(Object doc) {
        Object object;
        Object object2 = doc;
        if (object2 instanceof JXNode) {
            object = ((JXNode)doc).isElement() ? doc : this.strToJXDocument(doc.toString());
        } else if (object2 instanceof Document) {
            JXDocument jXDocument = JXDocument.create((Document)((Document)doc));
            Intrinsics.checkNotNullExpressionValue((Object)jXDocument, (String)"create(doc)");
            object = jXDocument;
        } else if (object2 instanceof Element) {
            Element[] elementArray = new Element[]{(Element)doc};
            JXDocument jXDocument = JXDocument.create((Elements)new Elements(elementArray));
            Intrinsics.checkNotNullExpressionValue((Object)jXDocument, (String)"create(Elements(doc))");
            object = jXDocument;
        } else if (object2 instanceof Elements) {
            JXDocument jXDocument = JXDocument.create((Elements)((Elements)doc));
            Intrinsics.checkNotNullExpressionValue((Object)jXDocument, (String)"create(doc)");
            object = jXDocument;
        } else {
            object = this.strToJXDocument(doc.toString());
        }
        return object;
    }

    private final JXDocument strToJXDocument(String html) {
        String html1 = html;
        if (StringsKt.endsWith$default((String)html1, (String)"</td>", (boolean)false, (int)2, null)) {
            html1 = "<tr>" + html1 + "</tr>";
        }
        if (StringsKt.endsWith$default((String)html1, (String)"</tr>", (boolean)false, (int)2, null) || StringsKt.endsWith$default((String)html1, (String)"</tbody>", (boolean)false, (int)2, null)) {
            html1 = "<table>" + html1 + "</table>";
        }
        JXDocument jXDocument = JXDocument.create((String)html1);
        Intrinsics.checkNotNullExpressionValue((Object)jXDocument, (String)"create(html1)");
        return jXDocument;
    }

    private final List<JXNode> getResult(String xPath) {
        Object node = this.jxNode;
        return node instanceof JXNode ? ((JXNode)node).sel(xPath) : ((JXDocument)node).selN(xPath);
    }

    @Nullable
    public final List<JXNode> getElements$reader_pro(@NotNull String xPath) {
        Intrinsics.checkNotNullParameter((Object)xPath, (String)"xPath");
        CharSequence charSequence = xPath;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        ArrayList jxNodes = new ArrayList();
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(xPath, false, 2, null);
        Object object = new String[]{"&&", "||", "%%"};
        ArrayList rules = ruleAnalyzes.splitRule(object);
        if (rules.size() == 1) {
            object = rules.get(0);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"rules[0]");
            return this.getResult((String)object);
        }
        ArrayList<List> results = new ArrayList<List>();
        for (String rl : rules) {
            Intrinsics.checkNotNullExpressionValue((Object)rl, (String)"rl");
            List temp = this.getElements$reader_pro(rl);
            if (temp == null) continue;
            Object object2 = temp;
            boolean bl2 = false;
            if (!(!object2.isEmpty())) continue;
            results.add(temp);
            object2 = temp;
            bl2 = false;
            if (!(!object2.isEmpty()) || !Intrinsics.areEqual((Object)ruleAnalyzes.getElementsType(), (Object)"||")) continue;
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
                            jxNodes.add(temp.get(i));
                        }
                    } while (n <= rl);
                }
            } else {
                for (List temp : results) {
                    jxNodes.addAll(temp);
                }
            }
        }
        return jxNodes;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<String> getStringList$reader_pro(@NotNull String xPath) {
        Intrinsics.checkNotNullParameter((Object)xPath, (String)"xPath");
        ArrayList<String> result2 = new ArrayList<String>();
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(xPath, false, 2, null);
        Object object = new String[]{"&&", "||", "%%"};
        ArrayList rules = ruleAnalyzes.splitRule(object);
        if (rules.size() == 1) {
            object = this.getResult(xPath);
            if (object != null) {
                void $this$mapTo$iv$iv;
                Iterable $this$map$iv = (Iterable)object;
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    JXNode jXNode = (JXNode)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    Boolean bl2 = result2.add(it.asString());
                    collection.add(bl2);
                }
                List cfr_ignored_0 = (List)destination$iv$iv;
            }
            return result2;
        }
        ArrayList<List> results = new ArrayList<List>();
        for (String rl : rules) {
            Intrinsics.checkNotNullExpressionValue((Object)rl, (String)"rl");
            List temp = this.getStringList$reader_pro(rl);
            Object object2 = temp;
            boolean $i$f$mapTo = false;
            if (!(!object2.isEmpty())) continue;
            results.add(temp);
            object2 = temp;
            $i$f$mapTo = false;
            if (!(!object2.isEmpty()) || !Intrinsics.areEqual((Object)ruleAnalyzes.getElementsType(), (Object)"||")) continue;
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

    @Nullable
    public final String getString(@NotNull String rule) {
        Intrinsics.checkNotNullParameter((Object)rule, (String)"rule");
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(rule, false, 2, null);
        Object object = new String[]{"&&", "||"};
        ArrayList rules = ruleAnalyzes.splitRule(object);
        if (rules.size() == 1) {
            object = this.getResult(rule);
            if (object != null) {
                Object object2 = object;
                boolean bl = false;
                boolean bl2 = false;
                Object it = object2;
                boolean bl3 = false;
                return TextUtils.join((CharSequence)"\n", (Iterable)((Iterable)it));
            }
            return null;
        }
        boolean bl = false;
        ArrayList<String> textList = new ArrayList<String>();
        for (String rl : rules) {
            Intrinsics.checkNotNullExpressionValue((Object)rl, (String)"rl");
            String temp = this.getString(rl);
            CharSequence charSequence = temp;
            boolean bl4 = false;
            boolean bl5 = false;
            if (charSequence == null || charSequence.length() == 0) continue;
            textList.add(temp);
            if (!Intrinsics.areEqual((Object)ruleAnalyzes.getElementsType(), (Object)"||")) continue;
            break;
        }
        return CollectionsKt.joinToString$default((Iterable)textList, (CharSequence)"\n", null, null, (int)0, null, null, (int)62, null);
    }
}

