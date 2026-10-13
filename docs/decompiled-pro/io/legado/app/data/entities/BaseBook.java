/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.BaseBook
 *  io.legado.app.model.analyzeRule.RuleDataInterface
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.data.entities;

import io.legado.app.model.analyzeRule.RuleDataInterface;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0017\n\u0002\u0010 \n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u001bH\u0016R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\u0005\"\u0004\b\r\u0010\u0007R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007R\u0018\u0010\u0011\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0005\"\u0004\b\u0013\u0010\u0007R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\u0007R\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u0005\"\u0004\b\u0019\u0010\u0007\u00a8\u0006\u001c"}, d2={"Lio/legado/app/data/entities/BaseBook;", "Lio/legado/app/model/analyzeRule/RuleDataInterface;", "author", "", "getAuthor", "()Ljava/lang/String;", "setAuthor", "(Ljava/lang/String;)V", "bookUrl", "getBookUrl", "setBookUrl", "infoHtml", "getInfoHtml", "setInfoHtml", "kind", "getKind", "setKind", "name", "getName", "setName", "tocHtml", "getTocHtml", "setTocHtml", "wordCount", "getWordCount", "setWordCount", "getKindList", "", "reader-pro"})
public interface BaseBook
extends RuleDataInterface {
    @NotNull
    public String getName();

    public void setName(@NotNull String var1);

    @NotNull
    public String getAuthor();

    public void setAuthor(@NotNull String var1);

    @NotNull
    public String getBookUrl();

    public void setBookUrl(@NotNull String var1);

    @Nullable
    public String getKind();

    public void setKind(@Nullable String var1);

    @Nullable
    public String getWordCount();

    public void setWordCount(@Nullable String var1);

    @Nullable
    public String getInfoHtml();

    public void setInfoHtml(@Nullable String var1);

    @Nullable
    public String getTocHtml();

    public void setTocHtml(@Nullable String var1);

    @NotNull
    public List<String> getKindList();
}

