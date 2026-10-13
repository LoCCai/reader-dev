/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.BaseSource
 *  io.legado.app.data.entities.RssArticle
 *  io.legado.app.data.entities.RssSource
 *  io.legado.app.exception.NoStackTraceException
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.DebugLog$DefaultImpls
 *  io.legado.app.model.analyzeRule.AnalyzeRule
 *  io.legado.app.model.analyzeRule.AnalyzeRule$SourceRule
 *  io.legado.app.model.analyzeRule.RuleData
 *  io.legado.app.model.analyzeRule.RuleDataInterface
 *  io.legado.app.model.rss.RssParserByRule
 *  io.legado.app.model.rss.RssParserDefault
 *  io.legado.app.utils.NetworkUtils
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model.rss;

import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.RssArticle;
import io.legado.app.data.entities.RssSource;
import io.legado.app.exception.NoStackTraceException;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeRule;
import io.legado.app.model.analyzeRule.RuleData;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.model.rss.RssParserDefault;
import io.legado.app.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0098\u0001\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0010\u0010\r\u001a\f\u0012\b\u0012\u00060\u000fR\u00020\t0\u000e2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u00060\u000fR\u00020\t0\u000e2\u0010\u0010\u0011\u001a\f\u0012\b\u0012\u00060\u000fR\u00020\t0\u000e2\u0010\u0010\u0012\u001a\f\u0012\b\u0012\u00060\u000fR\u00020\t0\u000e2\u0010\u0010\u0013\u001a\f\u0012\b\u0012\u00060\u000fR\u00020\t0\u000e2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002JN\u0010\u0016\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00172\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u00062\b\u0010\u001b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u00a8\u0006 "}, d2={"Lio/legado/app/model/rss/RssParserByRule;", "", "()V", "getItem", "Lio/legado/app/data/entities/RssArticle;", "sourceUrl", "", "item", "analyzeRule", "Lio/legado/app/model/analyzeRule/AnalyzeRule;", "variable", "log", "", "ruleTitle", "", "Lio/legado/app/model/analyzeRule/AnalyzeRule$SourceRule;", "rulePubDate", "ruleDescription", "ruleImage", "ruleLink", "debugLog", "Lio/legado/app/model/DebugLog;", "parseXML", "Lkotlin/Pair;", "", "sortName", "sortUrl", "body", "rssSource", "Lio/legado/app/data/entities/RssSource;", "ruleData", "Lio/legado/app/model/analyzeRule/RuleData;", "reader-pro"})
public final class RssParserByRule {
    @NotNull
    public static final RssParserByRule INSTANCE = new RssParserByRule();

    private RssParserByRule() {
    }

    @NotNull
    public final Pair<List<RssArticle>, String> parseXML(@NotNull String sortName, @NotNull String sortUrl, @Nullable String body, @NotNull RssSource rssSource, @NotNull RuleData ruleData, @Nullable DebugLog debugLog) throws Exception {
        boolean bl;
        Object object;
        Intrinsics.checkNotNullParameter((Object)sortName, (String)"sortName");
        Intrinsics.checkNotNullParameter((Object)sortUrl, (String)"sortUrl");
        Intrinsics.checkNotNullParameter((Object)rssSource, (String)"rssSource");
        Intrinsics.checkNotNullParameter((Object)ruleData, (String)"ruleData");
        String sourceUrl = rssSource.getSourceUrl();
        String nextUrl = null;
        CharSequence charSequence = body;
        boolean bl2 = false;
        boolean bl3 = false;
        if (charSequence == null || StringsKt.isBlank((CharSequence)charSequence)) {
            throw new NoStackTraceException(Intrinsics.stringPlus((String)"error_get_web_content: ", (Object)rssSource.getSourceUrl()));
        }
        String ruleArticles = rssSource.getRuleArticles();
        CharSequence charSequence2 = ruleArticles;
        bl3 = false;
        boolean bl4 = false;
        if (charSequence2 == null || StringsKt.isBlank((CharSequence)charSequence2)) {
            charSequence2 = debugLog;
            if (charSequence2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)charSequence2, (String)sourceUrl, (String)"\u21d2\u5217\u8868\u89c4\u5219\u4e3a\u7a7a, \u4f7f\u7528\u9ed8\u8ba4\u89c4\u5219\u89e3\u6790", (boolean)false, (int)4, null);
            }
            return RssParserDefault.INSTANCE.parseXML(sortName, body, sourceUrl, debugLog);
        }
        bl3 = false;
        List articleList = new ArrayList();
        AnalyzeRule analyzeRule = new AnalyzeRule((RuleDataInterface)ruleData, (BaseSource)rssSource, debugLog);
        AnalyzeRule.setContent$default((AnalyzeRule)analyzeRule, (Object)body, null, (int)2, null).setBaseUrl(sortUrl);
        analyzeRule.setRedirectUrl(sortUrl);
        boolean reverse = false;
        if (StringsKt.startsWith$default((String)ruleArticles, (String)"-", (boolean)false, (int)2, null)) {
            reverse = true;
            object = ruleArticles;
            int n = 1;
            bl = false;
            Object object2 = object;
            if (object2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string = ((String)object2).substring(n);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"(this as java.lang.String).substring(startIndex)");
            ruleArticles = string;
        }
        if ((object = debugLog) != null) {
            DebugLog.DefaultImpls.log$default((DebugLog)object, (String)sourceUrl, (String)"\u250c\u83b7\u53d6\u5217\u8868", (boolean)false, (int)4, null);
        }
        List collections = analyzeRule.getElements(ruleArticles);
        Object object3 = debugLog;
        if (object3 != null) {
            DebugLog.DefaultImpls.log$default((DebugLog)object3, (String)sourceUrl, (String)Intrinsics.stringPlus((String)"\u2514\u5217\u8868\u5927\u5c0f:", (Object)collections.size()), (boolean)false, (int)4, null);
        }
        object3 = rssSource.getRuleNextPage();
        bl = false;
        boolean bl5 = false;
        if (!(object3 == null || object3.length() == 0)) {
            object3 = debugLog;
            if (object3 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object3, (String)sourceUrl, (String)"\u250c\u83b7\u53d6\u4e0b\u4e00\u9875\u94fe\u63a5", (boolean)false, (int)4, null);
            }
            String string = rssSource.getRuleNextPage();
            Intrinsics.checkNotNull((Object)string);
            object3 = string;
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue((Object)locale, (String)"getDefault()");
            bl5 = false;
            Object object4 = object3;
            if (object4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string2 = ((String)object4).toUpperCase(locale);
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"(this as java.lang.String).toUpperCase(locale)");
            if (Intrinsics.areEqual((Object)string2, (Object)"PAGE")) {
                nextUrl = sortUrl;
            } else {
                nextUrl = AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (String)rssSource.getRuleNextPage(), null, (boolean)false, (int)6, null);
                object3 = nextUrl;
                boolean bl6 = false;
                if (object3.length() > 0) {
                    nextUrl = NetworkUtils.INSTANCE.getAbsoluteURL(sortUrl, nextUrl);
                }
            }
            object3 = debugLog;
            if (object3 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object3, (String)sourceUrl, (String)Intrinsics.stringPlus((String)"\u2514", (Object)nextUrl), (boolean)false, (int)4, null);
            }
        }
        List ruleTitle = AnalyzeRule.splitSourceRule$default((AnalyzeRule)analyzeRule, (String)rssSource.getRuleTitle(), (boolean)false, (int)2, null);
        List rulePubDate = AnalyzeRule.splitSourceRule$default((AnalyzeRule)analyzeRule, (String)rssSource.getRulePubDate(), (boolean)false, (int)2, null);
        List ruleDescription = AnalyzeRule.splitSourceRule$default((AnalyzeRule)analyzeRule, (String)rssSource.getRuleDescription(), (boolean)false, (int)2, null);
        List ruleImage = AnalyzeRule.splitSourceRule$default((AnalyzeRule)analyzeRule, (String)rssSource.getRuleImage(), (boolean)false, (int)2, null);
        List ruleLink = AnalyzeRule.splitSourceRule$default((AnalyzeRule)analyzeRule, (String)rssSource.getRuleLink(), (boolean)false, (int)2, null);
        String variable = ruleData.getVariable();
        Iterator iterator = collections.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            int index = n++;
            Object item = iterator.next();
            RssArticle rssArticle = this.getItem(sourceUrl, item, analyzeRule, variable, index == 0, ruleTitle, rulePubDate, ruleDescription, ruleImage, ruleLink, debugLog);
            if (rssArticle == null) continue;
            RssArticle rssArticle2 = rssArticle;
            boolean bl7 = false;
            boolean bl8 = false;
            RssArticle it = rssArticle2;
            boolean bl9 = false;
            it.setSort(sortName);
            it.setOrigin(sourceUrl);
            articleList.add(it);
        }
        if (reverse) {
            CollectionsKt.reverse((List)articleList);
        }
        return new Pair((Object)articleList, (Object)nextUrl);
    }

    private final RssArticle getItem(String sourceUrl, Object item, AnalyzeRule analyzeRule, String variable, boolean log, List<AnalyzeRule.SourceRule> ruleTitle, List<AnalyzeRule.SourceRule> rulePubDate, List<AnalyzeRule.SourceRule> ruleDescription, List<AnalyzeRule.SourceRule> ruleImage, List<AnalyzeRule.SourceRule> ruleLink, DebugLog debugLog) {
        RssArticle rssArticle = new RssArticle(null, null, null, 0L, null, null, null, null, null, false, variable, 1023, null);
        analyzeRule.setRuleData((RuleDataInterface)rssArticle);
        AnalyzeRule.setContent$default((AnalyzeRule)analyzeRule, (Object)item, null, (int)2, null);
        Object object = debugLog;
        if (object != null) {
            object.log(sourceUrl, "\u250c\u83b7\u53d6\u6807\u9898", log);
        }
        rssArticle.setTitle(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, ruleTitle, null, (boolean)false, (int)6, null));
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, Intrinsics.stringPlus((String)"\u2514", (Object)rssArticle.getTitle()), log);
        }
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, "\u250c\u83b7\u53d6\u65f6\u95f4", log);
        }
        rssArticle.setPubDate(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, rulePubDate, null, (boolean)false, (int)6, null));
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, Intrinsics.stringPlus((String)"\u2514", (Object)rssArticle.getPubDate()), log);
        }
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, "\u250c\u83b7\u53d6\u63cf\u8ff0", log);
        }
        object = ruleDescription;
        boolean bl = false;
        boolean bl2 = false;
        if (object == null || object.isEmpty()) {
            rssArticle.setDescription(null);
            object = debugLog;
            if (object != null) {
                object.log(sourceUrl, "\u2514\u63cf\u8ff0\u89c4\u5219\u4e3a\u7a7a\uff0c\u5c06\u4f1a\u89e3\u6790\u5185\u5bb9\u9875", log);
            }
        } else {
            rssArticle.setDescription(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, ruleDescription, null, (boolean)false, (int)6, null));
            object = debugLog;
            if (object != null) {
                object.log(sourceUrl, Intrinsics.stringPlus((String)"\u2514", (Object)rssArticle.getDescription()), log);
            }
        }
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, "\u250c\u83b7\u53d6\u56fe\u7247url", log);
        }
        rssArticle.setImage(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, ruleImage, null, (boolean)true, (int)2, null));
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, Intrinsics.stringPlus((String)"\u2514", (Object)rssArticle.getImage()), log);
        }
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, "\u250c\u83b7\u53d6\u6587\u7ae0\u94fe\u63a5", log);
        }
        rssArticle.setLink(NetworkUtils.INSTANCE.getAbsoluteURL(sourceUrl, AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, ruleLink, null, (boolean)false, (int)6, null)));
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, Intrinsics.stringPlus((String)"\u2514", (Object)rssArticle.getLink()), log);
        }
        if (StringsKt.isBlank((CharSequence)rssArticle.getTitle())) {
            return null;
        }
        return rssArticle;
    }
}

