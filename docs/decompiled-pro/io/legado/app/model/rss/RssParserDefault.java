/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.RssArticle
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.DebugLog$DefaultImpls
 *  io.legado.app.model.rss.RssParserDefault
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlPullParserFactory
 */
package io.legado.app.model.rss;

import io.legado.app.data.entities.RssArticle;
import io.legado.app.model.DebugLog;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0011\u001a\u00020\u0004H\u0002J<\u0010\u0012\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00132\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2={"Lio/legado/app/model/rss/RssParserDefault;", "", "()V", "RSS_ITEM", "", "RSS_ITEM_CATEGORY", "RSS_ITEM_CONTENT", "RSS_ITEM_DESCRIPTION", "RSS_ITEM_ENCLOSURE", "RSS_ITEM_LINK", "RSS_ITEM_PUB_DATE", "RSS_ITEM_THUMBNAIL", "RSS_ITEM_TIME", "RSS_ITEM_TITLE", "RSS_ITEM_TYPE", "RSS_ITEM_URL", "getImageUrl", "input", "parseXML", "Lkotlin/Pair;", "", "Lio/legado/app/data/entities/RssArticle;", "sortName", "xml", "sourceUrl", "debugLog", "Lio/legado/app/model/DebugLog;", "reader-pro"})
public final class RssParserDefault {
    @NotNull
    public static final RssParserDefault INSTANCE = new RssParserDefault();
    @NotNull
    private static final String RSS_ITEM = "item";
    @NotNull
    private static final String RSS_ITEM_TITLE = "title";
    @NotNull
    private static final String RSS_ITEM_LINK = "link";
    @NotNull
    private static final String RSS_ITEM_CATEGORY = "category";
    @NotNull
    private static final String RSS_ITEM_THUMBNAIL = "media:thumbnail";
    @NotNull
    private static final String RSS_ITEM_ENCLOSURE = "enclosure";
    @NotNull
    private static final String RSS_ITEM_DESCRIPTION = "description";
    @NotNull
    private static final String RSS_ITEM_CONTENT = "content:encoded";
    @NotNull
    private static final String RSS_ITEM_PUB_DATE = "pubDate";
    @NotNull
    private static final String RSS_ITEM_TIME = "time";
    @NotNull
    private static final String RSS_ITEM_URL = "url";
    @NotNull
    private static final String RSS_ITEM_TYPE = "type";

    private RssParserDefault() {
    }

    @NotNull
    public final Pair<List<RssArticle>, String> parseXML(@NotNull String sortName, @NotNull String xml, @NotNull String sourceUrl, @Nullable DebugLog debugLog) throws XmlPullParserException, IOException {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)sortName, (String)"sortName");
        Intrinsics.checkNotNullParameter((Object)xml, (String)"xml");
        Intrinsics.checkNotNullParameter((Object)sourceUrl, (String)"sourceUrl");
        boolean bl2 = false;
        List articleList = new ArrayList();
        RssArticle currentArticle = new RssArticle(null, null, null, 0L, null, null, null, null, null, false, null, 2047, null);
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance((String)"\n        org.kxml2.io.KXmlParser\n        org.kxml2.io.KXmlSerializer\n               ", Thread.currentThread().getContextClassLoader().getClass());
        factory.setNamespaceAware(false);
        XmlPullParser xmlPullParser = factory.newPullParser();
        xmlPullParser.setInput((Reader)new StringReader(xml));
        boolean insideItem = false;
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                if (StringsKt.equals((String)xmlPullParser.getName(), (String)RSS_ITEM, (boolean)true)) {
                    insideItem = true;
                } else if (StringsKt.equals((String)xmlPullParser.getName(), (String)RSS_ITEM_TITLE, (boolean)true)) {
                    if (insideItem) {
                        String string = xmlPullParser.nextText();
                        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"xmlPullParser.nextText()");
                        boolean bl3 = false;
                        currentArticle.setTitle(((Object)StringsKt.trim((CharSequence)string)).toString());
                    }
                } else if (StringsKt.equals((String)xmlPullParser.getName(), (String)RSS_ITEM_LINK, (boolean)true)) {
                    if (insideItem) {
                        String string = xmlPullParser.nextText();
                        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"xmlPullParser.nextText()");
                        boolean bl4 = false;
                        currentArticle.setLink(((Object)StringsKt.trim((CharSequence)string)).toString());
                    }
                } else if (StringsKt.equals((String)xmlPullParser.getName(), (String)RSS_ITEM_THUMBNAIL, (boolean)true)) {
                    if (insideItem) {
                        currentArticle.setImage(xmlPullParser.getAttributeValue(null, RSS_ITEM_URL));
                    }
                } else if (StringsKt.equals((String)xmlPullParser.getName(), (String)RSS_ITEM_ENCLOSURE, (boolean)true)) {
                    String type;
                    if (insideItem && (type = xmlPullParser.getAttributeValue(null, RSS_ITEM_TYPE)) != null && StringsKt.contains$default((CharSequence)type, (CharSequence)"image/", (boolean)false, (int)2, null)) {
                        currentArticle.setImage(xmlPullParser.getAttributeValue(null, RSS_ITEM_URL));
                    }
                } else if (StringsKt.equals((String)xmlPullParser.getName(), (String)RSS_ITEM_DESCRIPTION, (boolean)true)) {
                    if (insideItem) {
                        String description = xmlPullParser.nextText();
                        Intrinsics.checkNotNullExpressionValue((Object)description, (String)RSS_ITEM_DESCRIPTION);
                        String string = description;
                        bl = false;
                        currentArticle.setDescription(((Object)StringsKt.trim((CharSequence)string)).toString());
                        if (currentArticle.getImage() == null) {
                            currentArticle.setImage(this.getImageUrl(description));
                        }
                    }
                } else if (StringsKt.equals((String)xmlPullParser.getName(), (String)RSS_ITEM_CONTENT, (boolean)true)) {
                    if (insideItem) {
                        String string = xmlPullParser.nextText();
                        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"xmlPullParser.nextText()");
                        bl = false;
                        String content = ((Object)StringsKt.trim((CharSequence)string)).toString();
                        currentArticle.setContent(content);
                        if (currentArticle.getImage() == null) {
                            currentArticle.setImage(this.getImageUrl(content));
                        }
                    }
                } else if (StringsKt.equals((String)xmlPullParser.getName(), (String)RSS_ITEM_PUB_DATE, (boolean)true)) {
                    if (insideItem) {
                        int nextTokenType = xmlPullParser.next();
                        if (nextTokenType != 4) continue;
                        String string = xmlPullParser.getText();
                        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"xmlPullParser.text");
                        bl = false;
                        currentArticle.setPubDate(((Object)StringsKt.trim((CharSequence)string)).toString());
                        continue;
                    }
                } else if (StringsKt.equals((String)xmlPullParser.getName(), (String)RSS_ITEM_TIME, (boolean)true) && insideItem) {
                    currentArticle.setPubDate(xmlPullParser.nextText());
                }
            } else if (eventType == 3 && StringsKt.equals((String)xmlPullParser.getName(), (String)RSS_ITEM, (boolean)true)) {
                insideItem = false;
                currentArticle.setOrigin(sourceUrl);
                currentArticle.setSort(sortName);
                articleList.add(currentArticle);
                currentArticle = new RssArticle(null, null, null, 0L, null, null, null, null, null, false, null, 2047, null);
            }
            eventType = xmlPullParser.next();
        }
        RssArticle rssArticle = (RssArticle)CollectionsKt.firstOrNull((List)articleList);
        if (rssArticle != null) {
            RssArticle rssArticle2 = rssArticle;
            bl = false;
            boolean bl5 = false;
            RssArticle it = rssArticle2;
            boolean bl6 = false;
            DebugLog debugLog2 = debugLog;
            if (debugLog2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)sourceUrl, (String)"\u250c\u83b7\u53d6\u6807\u9898", (boolean)false, (int)4, null);
            }
            debugLog2 = debugLog;
            if (debugLog2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)sourceUrl, (String)Intrinsics.stringPlus((String)"\u2514", (Object)it.getTitle()), (boolean)false, (int)4, null);
            }
            debugLog2 = debugLog;
            if (debugLog2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)sourceUrl, (String)"\u250c\u83b7\u53d6\u65f6\u95f4", (boolean)false, (int)4, null);
            }
            debugLog2 = debugLog;
            if (debugLog2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)sourceUrl, (String)Intrinsics.stringPlus((String)"\u2514", (Object)it.getPubDate()), (boolean)false, (int)4, null);
            }
            debugLog2 = debugLog;
            if (debugLog2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)sourceUrl, (String)"\u250c\u83b7\u53d6\u63cf\u8ff0", (boolean)false, (int)4, null);
            }
            debugLog2 = debugLog;
            if (debugLog2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)sourceUrl, (String)Intrinsics.stringPlus((String)"\u2514", (Object)it.getDescription()), (boolean)false, (int)4, null);
            }
            debugLog2 = debugLog;
            if (debugLog2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)sourceUrl, (String)"\u250c\u83b7\u53d6\u56fe\u7247url", (boolean)false, (int)4, null);
            }
            debugLog2 = debugLog;
            if (debugLog2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)sourceUrl, (String)Intrinsics.stringPlus((String)"\u2514", (Object)it.getImage()), (boolean)false, (int)4, null);
            }
            debugLog2 = debugLog;
            if (debugLog2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)sourceUrl, (String)"\u250c\u83b7\u53d6\u6587\u7ae0\u94fe\u63a5", (boolean)false, (int)4, null);
            }
            debugLog2 = debugLog;
            if (debugLog2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)sourceUrl, (String)Intrinsics.stringPlus((String)"\u2514", (Object)it.getLink()), (boolean)false, (int)4, null);
            }
        }
        return new Pair((Object)articleList, null);
    }

    private final String getImageUrl(String input) {
        String url2 = null;
        String string = "(<img [^>]*>)";
        int n = 0;
        boolean bl = false;
        Pattern pattern = Pattern.compile(string, n);
        Intrinsics.checkNotNullExpressionValue((Object)pattern, (String)"java.util.regex.Pattern.compile(this, flags)");
        Pattern patternImg = pattern;
        Matcher matcherImg = patternImg.matcher(input);
        if (matcherImg.find()) {
            String imgTag = matcherImg.group(1);
            String string2 = "src\\s*=\\s*\"([^\"]+)\"";
            int n2 = 0;
            boolean bl2 = false;
            Pattern pattern2 = Pattern.compile(string2, n2);
            Intrinsics.checkNotNullExpressionValue((Object)pattern2, (String)"java.util.regex.Pattern.compile(this, flags)");
            Pattern patternLink = pattern2;
            String string3 = imgTag;
            Intrinsics.checkNotNull((Object)string3);
            Matcher matcherLink = patternLink.matcher(string3);
            if (matcherLink.find()) {
                String string4 = matcherLink.group(1);
                Intrinsics.checkNotNull((Object)string4);
                String string5 = string4;
                bl2 = false;
                String string6 = string5;
                if (string6 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                url2 = ((Object)StringsKt.trim((CharSequence)string6)).toString();
            }
        }
        return url2;
    }
}

