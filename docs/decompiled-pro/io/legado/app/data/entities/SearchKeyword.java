/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.SearchKeyword
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

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00c6\u0003J'\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001e"}, d2={"Lio/legado/app/data/entities/SearchKeyword;", "", "word", "", "usage", "", "lastUseTime", "", "(Ljava/lang/String;IJ)V", "getLastUseTime", "()J", "setLastUseTime", "(J)V", "getUsage", "()I", "setUsage", "(I)V", "getWord", "()Ljava/lang/String;", "setWord", "(Ljava/lang/String;)V", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "reader-pro"})
public final class SearchKeyword {
    @NotNull
    private String word;
    private int usage;
    private long lastUseTime;

    public SearchKeyword(@NotNull String word, int usage, long lastUseTime) {
        Intrinsics.checkNotNullParameter((Object)word, (String)"word");
        this.word = word;
        this.usage = usage;
        this.lastUseTime = lastUseTime;
    }

    public /* synthetic */ SearchKeyword(String string, int n, long l, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            string = "";
        }
        if ((n2 & 2) != 0) {
            n = 1;
        }
        if ((n2 & 4) != 0) {
            l = System.currentTimeMillis();
        }
        this(string, n, l);
    }

    @NotNull
    public final String getWord() {
        return this.word;
    }

    public final void setWord(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.word = string;
    }

    public final int getUsage() {
        return this.usage;
    }

    public final void setUsage(int n) {
        this.usage = n;
    }

    public final long getLastUseTime() {
        return this.lastUseTime;
    }

    public final void setLastUseTime(long l) {
        this.lastUseTime = l;
    }

    @NotNull
    public final String component1() {
        return this.word;
    }

    public final int component2() {
        return this.usage;
    }

    public final long component3() {
        return this.lastUseTime;
    }

    @NotNull
    public final SearchKeyword copy(@NotNull String word, int usage, long lastUseTime) {
        Intrinsics.checkNotNullParameter((Object)word, (String)"word");
        return new SearchKeyword(word, usage, lastUseTime);
    }

    public static /* synthetic */ SearchKeyword copy$default(SearchKeyword searchKeyword, String string, int n, long l, int n2, Object object) {
        if ((n2 & 1) != 0) {
            string = searchKeyword.word;
        }
        if ((n2 & 2) != 0) {
            n = searchKeyword.usage;
        }
        if ((n2 & 4) != 0) {
            l = searchKeyword.lastUseTime;
        }
        return searchKeyword.copy(string, n, l);
    }

    @NotNull
    public String toString() {
        return "SearchKeyword(word=" + this.word + ", usage=" + this.usage + ", lastUseTime=" + this.lastUseTime + ')';
    }

    public int hashCode() {
        int result2 = this.word.hashCode();
        result2 = result2 * 31 + Integer.hashCode(this.usage);
        result2 = result2 * 31 + Long.hashCode(this.lastUseTime);
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SearchKeyword)) {
            return false;
        }
        SearchKeyword searchKeyword = (SearchKeyword)other;
        if (!Intrinsics.areEqual((Object)this.word, (Object)searchKeyword.word)) {
            return false;
        }
        if (this.usage != searchKeyword.usage) {
            return false;
        }
        return this.lastUseTime == searchKeyword.lastUseTime;
    }

    public SearchKeyword() {
        this(null, 0, 0L, 7, null);
    }
}

