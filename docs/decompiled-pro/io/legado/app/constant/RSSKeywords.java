/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.constant.RSSKeywords
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.constant;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lio/legado/app/constant/RSSKeywords;", "", "()V", "RSS_ITEM", "", "RSS_ITEM_CATEGORY", "RSS_ITEM_CONTENT", "RSS_ITEM_DESCRIPTION", "RSS_ITEM_ENCLOSURE", "RSS_ITEM_LINK", "RSS_ITEM_PUB_DATE", "RSS_ITEM_THUMBNAIL", "RSS_ITEM_TIME", "RSS_ITEM_TITLE", "RSS_ITEM_TYPE", "RSS_ITEM_URL", "reader-pro"})
public final class RSSKeywords {
    @NotNull
    public static final RSSKeywords INSTANCE = new RSSKeywords();
    @NotNull
    public static final String RSS_ITEM = "item";
    @NotNull
    public static final String RSS_ITEM_TITLE = "title";
    @NotNull
    public static final String RSS_ITEM_LINK = "link";
    @NotNull
    public static final String RSS_ITEM_CATEGORY = "category";
    @NotNull
    public static final String RSS_ITEM_THUMBNAIL = "media:thumbnail";
    @NotNull
    public static final String RSS_ITEM_ENCLOSURE = "enclosure";
    @NotNull
    public static final String RSS_ITEM_DESCRIPTION = "description";
    @NotNull
    public static final String RSS_ITEM_CONTENT = "content:encoded";
    @NotNull
    public static final String RSS_ITEM_PUB_DATE = "pubDate";
    @NotNull
    public static final String RSS_ITEM_TIME = "time";
    @NotNull
    public static final String RSS_ITEM_URL = "url";
    @NotNull
    public static final String RSS_ITEM_TYPE = "type";

    private RSSKeywords() {
    }
}

