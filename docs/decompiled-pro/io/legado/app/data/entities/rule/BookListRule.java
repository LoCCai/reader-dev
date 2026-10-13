/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.rule.BookListRule
 *  kotlin.Metadata
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.data.entities.rule;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b \bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\u0005\"\u0004\b\r\u0010\u0007R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007R\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0005\"\u0004\b\u0013\u0010\u0007R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\u0007R\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u0005\"\u0004\b\u0019\u0010\u0007R\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001b\u0010\u0005\"\u0004\b\u001c\u0010\u0007R\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001e\u0010\u0005\"\u0004\b\u001f\u0010\u0007R\u001a\u0010 \u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b!\u0010\u0005\"\u0004\b\"\u0010\u0007\u00a8\u0006#"}, d2={"Lio/legado/app/data/entities/rule/BookListRule;", "", "author", "", "getAuthor", "()Ljava/lang/String;", "setAuthor", "(Ljava/lang/String;)V", "bookList", "getBookList", "setBookList", "bookUrl", "getBookUrl", "setBookUrl", "coverUrl", "getCoverUrl", "setCoverUrl", "intro", "getIntro", "setIntro", "kind", "getKind", "setKind", "lastChapter", "getLastChapter", "setLastChapter", "name", "getName", "setName", "updateTime", "getUpdateTime", "setUpdateTime", "wordCount", "getWordCount", "setWordCount", "reader-pro"})
public interface BookListRule {
    @Nullable
    public String getBookList();

    public void setBookList(@Nullable String var1);

    @Nullable
    public String getName();

    public void setName(@Nullable String var1);

    @Nullable
    public String getAuthor();

    public void setAuthor(@Nullable String var1);

    @Nullable
    public String getIntro();

    public void setIntro(@Nullable String var1);

    @Nullable
    public String getKind();

    public void setKind(@Nullable String var1);

    @Nullable
    public String getLastChapter();

    public void setLastChapter(@Nullable String var1);

    @Nullable
    public String getUpdateTime();

    public void setUpdateTime(@Nullable String var1);

    @Nullable
    public String getBookUrl();

    public void setBookUrl(@Nullable String var1);

    @Nullable
    public String getCoverUrl();

    public void setCoverUrl(@Nullable String var1);

    @Nullable
    public String getWordCount();

    public void setWordCount(@Nullable String var1);
}

