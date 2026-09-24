/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.SearchResult
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.data.entities;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b \n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001Bi\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0006H\u00c6\u0003J\t\u0010 \u001a\u00020\u0006H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003Jm\u0010%\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010)\u001a\u00020\u0003H\u00d6\u0001J\t\u0010*\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012\u00a8\u0006+"}, d2={"Lio/legado/app/data/entities/SearchResult;", "", "resultCount", "", "resultCountWithinChapter", "resultText", "", "chapterTitle", "query", "pageSize", "chapterIndex", "pageIndex", "queryIndexInResult", "queryIndexInChapter", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIII)V", "getChapterIndex", "()I", "getChapterTitle", "()Ljava/lang/String;", "getPageIndex", "getPageSize", "getQuery", "getQueryIndexInChapter", "getQueryIndexInResult", "getResultCount", "getResultCountWithinChapter", "getResultText", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "reader-pro"})
public final class SearchResult {
    private final int resultCount;
    private final int resultCountWithinChapter;
    @NotNull
    private final String resultText;
    @NotNull
    private final String chapterTitle;
    @NotNull
    private final String query;
    private final int pageSize;
    private final int chapterIndex;
    private final int pageIndex;
    private final int queryIndexInResult;
    private final int queryIndexInChapter;

    public SearchResult(int resultCount, int resultCountWithinChapter, @NotNull String resultText, @NotNull String chapterTitle, @NotNull String query, int pageSize, int chapterIndex, int pageIndex, int queryIndexInResult, int queryIndexInChapter) {
        Intrinsics.checkNotNullParameter((Object)resultText, (String)"resultText");
        Intrinsics.checkNotNullParameter((Object)chapterTitle, (String)"chapterTitle");
        Intrinsics.checkNotNullParameter((Object)query, (String)"query");
        this.resultCount = resultCount;
        this.resultCountWithinChapter = resultCountWithinChapter;
        this.resultText = resultText;
        this.chapterTitle = chapterTitle;
        this.query = query;
        this.pageSize = pageSize;
        this.chapterIndex = chapterIndex;
        this.pageIndex = pageIndex;
        this.queryIndexInResult = queryIndexInResult;
        this.queryIndexInChapter = queryIndexInChapter;
    }

    public /* synthetic */ SearchResult(int n, int n2, String string, String string2, String string3, int n3, int n4, int n5, int n6, int n7, int n8, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n8 & 1) != 0) {
            n = 0;
        }
        if ((n8 & 2) != 0) {
            n2 = 0;
        }
        if ((n8 & 4) != 0) {
            string = "";
        }
        if ((n8 & 8) != 0) {
            string2 = "";
        }
        if ((n8 & 0x10) != 0) {
            string3 = "";
        }
        if ((n8 & 0x20) != 0) {
            n3 = 0;
        }
        if ((n8 & 0x40) != 0) {
            n4 = 0;
        }
        if ((n8 & 0x80) != 0) {
            n5 = 0;
        }
        if ((n8 & 0x100) != 0) {
            n6 = 0;
        }
        if ((n8 & 0x200) != 0) {
            n7 = 0;
        }
        this(n, n2, string, string2, string3, n3, n4, n5, n6, n7);
    }

    public final int getResultCount() {
        return this.resultCount;
    }

    public final int getResultCountWithinChapter() {
        return this.resultCountWithinChapter;
    }

    @NotNull
    public final String getResultText() {
        return this.resultText;
    }

    @NotNull
    public final String getChapterTitle() {
        return this.chapterTitle;
    }

    @NotNull
    public final String getQuery() {
        return this.query;
    }

    public final int getPageSize() {
        return this.pageSize;
    }

    public final int getChapterIndex() {
        return this.chapterIndex;
    }

    public final int getPageIndex() {
        return this.pageIndex;
    }

    public final int getQueryIndexInResult() {
        return this.queryIndexInResult;
    }

    public final int getQueryIndexInChapter() {
        return this.queryIndexInChapter;
    }

    public final int component1() {
        return this.resultCount;
    }

    public final int component2() {
        return this.resultCountWithinChapter;
    }

    @NotNull
    public final String component3() {
        return this.resultText;
    }

    @NotNull
    public final String component4() {
        return this.chapterTitle;
    }

    @NotNull
    public final String component5() {
        return this.query;
    }

    public final int component6() {
        return this.pageSize;
    }

    public final int component7() {
        return this.chapterIndex;
    }

    public final int component8() {
        return this.pageIndex;
    }

    public final int component9() {
        return this.queryIndexInResult;
    }

    public final int component10() {
        return this.queryIndexInChapter;
    }

    @NotNull
    public final SearchResult copy(int resultCount, int resultCountWithinChapter, @NotNull String resultText, @NotNull String chapterTitle, @NotNull String query, int pageSize, int chapterIndex, int pageIndex, int queryIndexInResult, int queryIndexInChapter) {
        Intrinsics.checkNotNullParameter((Object)resultText, (String)"resultText");
        Intrinsics.checkNotNullParameter((Object)chapterTitle, (String)"chapterTitle");
        Intrinsics.checkNotNullParameter((Object)query, (String)"query");
        return new SearchResult(resultCount, resultCountWithinChapter, resultText, chapterTitle, query, pageSize, chapterIndex, pageIndex, queryIndexInResult, queryIndexInChapter);
    }

    public static /* synthetic */ SearchResult copy$default(SearchResult searchResult, int n, int n2, String string, String string2, String string3, int n3, int n4, int n5, int n6, int n7, int n8, Object object) {
        if ((n8 & 1) != 0) {
            n = searchResult.resultCount;
        }
        if ((n8 & 2) != 0) {
            n2 = searchResult.resultCountWithinChapter;
        }
        if ((n8 & 4) != 0) {
            string = searchResult.resultText;
        }
        if ((n8 & 8) != 0) {
            string2 = searchResult.chapterTitle;
        }
        if ((n8 & 0x10) != 0) {
            string3 = searchResult.query;
        }
        if ((n8 & 0x20) != 0) {
            n3 = searchResult.pageSize;
        }
        if ((n8 & 0x40) != 0) {
            n4 = searchResult.chapterIndex;
        }
        if ((n8 & 0x80) != 0) {
            n5 = searchResult.pageIndex;
        }
        if ((n8 & 0x100) != 0) {
            n6 = searchResult.queryIndexInResult;
        }
        if ((n8 & 0x200) != 0) {
            n7 = searchResult.queryIndexInChapter;
        }
        return searchResult.copy(n, n2, string, string2, string3, n3, n4, n5, n6, n7);
    }

    @NotNull
    public String toString() {
        return "SearchResult(resultCount=" + this.resultCount + ", resultCountWithinChapter=" + this.resultCountWithinChapter + ", resultText=" + this.resultText + ", chapterTitle=" + this.chapterTitle + ", query=" + this.query + ", pageSize=" + this.pageSize + ", chapterIndex=" + this.chapterIndex + ", pageIndex=" + this.pageIndex + ", queryIndexInResult=" + this.queryIndexInResult + ", queryIndexInChapter=" + this.queryIndexInChapter + ')';
    }

    public int hashCode() {
        int result2 = Integer.hashCode(this.resultCount);
        result2 = result2 * 31 + Integer.hashCode(this.resultCountWithinChapter);
        result2 = result2 * 31 + this.resultText.hashCode();
        result2 = result2 * 31 + this.chapterTitle.hashCode();
        result2 = result2 * 31 + this.query.hashCode();
        result2 = result2 * 31 + Integer.hashCode(this.pageSize);
        result2 = result2 * 31 + Integer.hashCode(this.chapterIndex);
        result2 = result2 * 31 + Integer.hashCode(this.pageIndex);
        result2 = result2 * 31 + Integer.hashCode(this.queryIndexInResult);
        result2 = result2 * 31 + Integer.hashCode(this.queryIndexInChapter);
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SearchResult)) {
            return false;
        }
        SearchResult searchResult = (SearchResult)other;
        if (this.resultCount != searchResult.resultCount) {
            return false;
        }
        if (this.resultCountWithinChapter != searchResult.resultCountWithinChapter) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.resultText, (Object)searchResult.resultText)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.chapterTitle, (Object)searchResult.chapterTitle)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.query, (Object)searchResult.query)) {
            return false;
        }
        if (this.pageSize != searchResult.pageSize) {
            return false;
        }
        if (this.chapterIndex != searchResult.chapterIndex) {
            return false;
        }
        if (this.pageIndex != searchResult.pageIndex) {
            return false;
        }
        if (this.queryIndexInResult != searchResult.queryIndexInResult) {
            return false;
        }
        return this.queryIndexInChapter == searchResult.queryIndexInChapter;
    }

    public SearchResult() {
        this(0, 0, null, null, null, 0, 0, 0, 0, 0, 1023, null);
    }
}

