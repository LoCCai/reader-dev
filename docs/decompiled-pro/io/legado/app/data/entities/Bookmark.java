/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.Bookmark
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

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b!\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BU\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\rJ\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\t\u0010#\u001a\u00020\bH\u00c6\u0003J\t\u0010$\u001a\u00020\bH\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\t\u0010&\u001a\u00020\u0005H\u00c6\u0003J\t\u0010'\u001a\u00020\u0005H\u00c6\u0003JY\u0010(\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010,\u001a\u00020\bH\u00d6\u0001J\t\u0010-\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u001a\u0010\u000b\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000f\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000f\"\u0004\b\u0019\u0010\u0013R\u001a\u0010\t\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0015\"\u0004\b\u001b\u0010\u0017R\u001a\u0010\f\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000f\"\u0004\b\u001d\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006."}, d2={"Lio/legado/app/data/entities/Bookmark;", "", "time", "", "bookName", "", "bookAuthor", "chapterIndex", "", "chapterPos", "chapterName", "bookText", "content", "(JLjava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getBookAuthor", "()Ljava/lang/String;", "getBookName", "getBookText", "setBookText", "(Ljava/lang/String;)V", "getChapterIndex", "()I", "setChapterIndex", "(I)V", "getChapterName", "setChapterName", "getChapterPos", "setChapterPos", "getContent", "setContent", "getTime", "()J", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "reader-pro"})
public final class Bookmark {
    private final long time;
    @NotNull
    private final String bookName;
    @NotNull
    private final String bookAuthor;
    private int chapterIndex;
    private int chapterPos;
    @NotNull
    private String chapterName;
    @NotNull
    private String bookText;
    @NotNull
    private String content;

    public Bookmark(long time, @NotNull String bookName, @NotNull String bookAuthor, int chapterIndex, int chapterPos, @NotNull String chapterName, @NotNull String bookText, @NotNull String content) {
        Intrinsics.checkNotNullParameter((Object)bookName, (String)"bookName");
        Intrinsics.checkNotNullParameter((Object)bookAuthor, (String)"bookAuthor");
        Intrinsics.checkNotNullParameter((Object)chapterName, (String)"chapterName");
        Intrinsics.checkNotNullParameter((Object)bookText, (String)"bookText");
        Intrinsics.checkNotNullParameter((Object)content, (String)"content");
        this.time = time;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.chapterIndex = chapterIndex;
        this.chapterPos = chapterPos;
        this.chapterName = chapterName;
        this.bookText = bookText;
        this.content = content;
    }

    public /* synthetic */ Bookmark(long l, String string, String string2, int n, int n2, String string3, String string4, String string5, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 1) != 0) {
            l = System.currentTimeMillis();
        }
        if ((n3 & 2) != 0) {
            string = "";
        }
        if ((n3 & 4) != 0) {
            string2 = "";
        }
        if ((n3 & 8) != 0) {
            n = 0;
        }
        if ((n3 & 0x10) != 0) {
            n2 = 0;
        }
        if ((n3 & 0x20) != 0) {
            string3 = "";
        }
        if ((n3 & 0x40) != 0) {
            string4 = "";
        }
        if ((n3 & 0x80) != 0) {
            string5 = "";
        }
        this(l, string, string2, n, n2, string3, string4, string5);
    }

    public final long getTime() {
        return this.time;
    }

    @NotNull
    public final String getBookName() {
        return this.bookName;
    }

    @NotNull
    public final String getBookAuthor() {
        return this.bookAuthor;
    }

    public final int getChapterIndex() {
        return this.chapterIndex;
    }

    public final void setChapterIndex(int n) {
        this.chapterIndex = n;
    }

    public final int getChapterPos() {
        return this.chapterPos;
    }

    public final void setChapterPos(int n) {
        this.chapterPos = n;
    }

    @NotNull
    public final String getChapterName() {
        return this.chapterName;
    }

    public final void setChapterName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.chapterName = string;
    }

    @NotNull
    public final String getBookText() {
        return this.bookText;
    }

    public final void setBookText(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.bookText = string;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }

    public final void setContent(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.content = string;
    }

    public final long component1() {
        return this.time;
    }

    @NotNull
    public final String component2() {
        return this.bookName;
    }

    @NotNull
    public final String component3() {
        return this.bookAuthor;
    }

    public final int component4() {
        return this.chapterIndex;
    }

    public final int component5() {
        return this.chapterPos;
    }

    @NotNull
    public final String component6() {
        return this.chapterName;
    }

    @NotNull
    public final String component7() {
        return this.bookText;
    }

    @NotNull
    public final String component8() {
        return this.content;
    }

    @NotNull
    public final Bookmark copy(long time, @NotNull String bookName, @NotNull String bookAuthor, int chapterIndex, int chapterPos, @NotNull String chapterName, @NotNull String bookText, @NotNull String content) {
        Intrinsics.checkNotNullParameter((Object)bookName, (String)"bookName");
        Intrinsics.checkNotNullParameter((Object)bookAuthor, (String)"bookAuthor");
        Intrinsics.checkNotNullParameter((Object)chapterName, (String)"chapterName");
        Intrinsics.checkNotNullParameter((Object)bookText, (String)"bookText");
        Intrinsics.checkNotNullParameter((Object)content, (String)"content");
        return new Bookmark(time, bookName, bookAuthor, chapterIndex, chapterPos, chapterName, bookText, content);
    }

    public static /* synthetic */ Bookmark copy$default(Bookmark bookmark, long l, String string, String string2, int n, int n2, String string3, String string4, String string5, int n3, Object object) {
        if ((n3 & 1) != 0) {
            l = bookmark.time;
        }
        if ((n3 & 2) != 0) {
            string = bookmark.bookName;
        }
        if ((n3 & 4) != 0) {
            string2 = bookmark.bookAuthor;
        }
        if ((n3 & 8) != 0) {
            n = bookmark.chapterIndex;
        }
        if ((n3 & 0x10) != 0) {
            n2 = bookmark.chapterPos;
        }
        if ((n3 & 0x20) != 0) {
            string3 = bookmark.chapterName;
        }
        if ((n3 & 0x40) != 0) {
            string4 = bookmark.bookText;
        }
        if ((n3 & 0x80) != 0) {
            string5 = bookmark.content;
        }
        return bookmark.copy(l, string, string2, n, n2, string3, string4, string5);
    }

    @NotNull
    public String toString() {
        return "Bookmark(time=" + this.time + ", bookName=" + this.bookName + ", bookAuthor=" + this.bookAuthor + ", chapterIndex=" + this.chapterIndex + ", chapterPos=" + this.chapterPos + ", chapterName=" + this.chapterName + ", bookText=" + this.bookText + ", content=" + this.content + ')';
    }

    public int hashCode() {
        int result2 = Long.hashCode(this.time);
        result2 = result2 * 31 + this.bookName.hashCode();
        result2 = result2 * 31 + this.bookAuthor.hashCode();
        result2 = result2 * 31 + Integer.hashCode(this.chapterIndex);
        result2 = result2 * 31 + Integer.hashCode(this.chapterPos);
        result2 = result2 * 31 + this.chapterName.hashCode();
        result2 = result2 * 31 + this.bookText.hashCode();
        result2 = result2 * 31 + this.content.hashCode();
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Bookmark)) {
            return false;
        }
        Bookmark bookmark = (Bookmark)other;
        if (this.time != bookmark.time) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.bookName, (Object)bookmark.bookName)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.bookAuthor, (Object)bookmark.bookAuthor)) {
            return false;
        }
        if (this.chapterIndex != bookmark.chapterIndex) {
            return false;
        }
        if (this.chapterPos != bookmark.chapterPos) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.chapterName, (Object)bookmark.chapterName)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.bookText, (Object)bookmark.bookText)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.content, (Object)bookmark.content);
    }

    public Bookmark() {
        this(0L, null, null, 0, 0, null, null, null, 255, null);
    }
}

