/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  io.legado.app.data.entities.BaseBook
 *  io.legado.app.data.entities.BaseBook$DefaultImpls
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.SearchBook
 *  io.legado.app.utils.GsonExtensionsKt
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.legado.app.data.entities.BaseBook;
import io.legado.app.data.entities.Book;
import io.legado.app.utils.GsonExtensionsKt;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JsonIgnoreProperties(value={"variableMap", "infoHtml", "tocHtml", "origins", "kindList"})
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002B\u00a7\u0001\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0004\u0012\b\b\u0002\u0010\n\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0004\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0004\u0012\b\b\u0002\u0010\u0014\u001a\u00020\b\u00a2\u0006\u0002\u0010\u0015J\u000e\u0010N\u001a\u00020O2\u0006\u0010\u0005\u001a\u00020\u0004J\u0011\u0010P\u001a\u00020\b2\u0006\u0010Q\u001a\u00020\u0000H\u0096\u0002J\t\u0010R\u001a\u00020\u0004H\u00c6\u0003J\u000b\u0010S\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\u000b\u0010T\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\t\u0010U\u001a\u00020\u0004H\u00c6\u0003J\t\u0010V\u001a\u00020\u0012H\u00c6\u0003J\u000b\u0010W\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\t\u0010X\u001a\u00020\bH\u00c6\u0003J\t\u0010Y\u001a\u00020\u0004H\u00c6\u0003J\t\u0010Z\u001a\u00020\u0004H\u00c6\u0003J\t\u0010[\u001a\u00020\bH\u00c6\u0003J\t\u0010\\\u001a\u00020\u0004H\u00c6\u0003J\t\u0010]\u001a\u00020\u0004H\u00c6\u0003J\u000b\u0010^\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\u000b\u0010_\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\u000b\u0010`\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\u00ab\u0001\u0010a\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00042\b\b\u0002\u0010\n\u001a\u00020\u00042\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0014\u001a\u00020\bH\u00c6\u0001J\u0013\u0010b\u001a\u00020c2\b\u0010Q\u001a\u0004\u0018\u00010dH\u0096\u0002J\b\u0010e\u001a\u00020\u0004H\u0016J\b\u0010f\u001a\u00020\bH\u0016J\u001a\u0010g\u001a\u00020O2\u0006\u0010h\u001a\u00020\u00042\b\u0010i\u001a\u0004\u0018\u00010\u0004H\u0016J\u000e\u0010j\u001a\u00020O2\u0006\u0010k\u001a\u00020\u0004J\u0006\u0010l\u001a\u00020mJ\t\u0010n\u001a\u00020\u0004H\u00d6\u0001R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u0004X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u0003\u001a\u00020\u0004X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0018\"\u0004\b\u001c\u0010\u001aR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0018\"\u0004\b\u001e\u0010\u001aR\u001c\u0010\u001f\u001a\u0004\u0018\u00010\u0004X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0018\"\u0004\b!\u0010\u001aR\u001c\u0010\r\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0018\"\u0004\b#\u0010\u001aR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0018\"\u0004\b%\u0010\u001aR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0018\"\u0004\b'\u0010\u001aR\u001a\u0010\t\u001a\u00020\u0004X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0018\"\u0004\b)\u0010\u001aR\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0018\"\u0004\b+\u0010\u001aR\u001a\u0010\u0006\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0018\"\u0004\b-\u0010\u001aR\u001a\u0010\u0014\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101RF\u00105\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u000103j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`42\u001a\u00102\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u000103j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`4@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u001c\u0010<\u001a\u0004\u0018\u00010\u0004X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\u0018\"\u0004\b>\u0010\u001aR\u001a\u0010\u0010\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u0018\"\u0004\b@\u0010\u001aR\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bA\u0010/\"\u0004\bB\u00101R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bC\u0010\u0018\"\u0004\bD\u0010\u001aR7\u0010E\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040Fj\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`G8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\bJ\u0010K\u001a\u0004\bH\u0010IR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0004X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bL\u0010\u0018\"\u0004\bM\u0010\u001a\u00a8\u0006o"}, d2={"Lio/legado/app/data/entities/SearchBook;", "Lio/legado/app/data/entities/BaseBook;", "", "bookUrl", "", "origin", "originName", "type", "", "name", "author", "kind", "coverUrl", "intro", "wordCount", "latestChapterTitle", "tocUrl", "time", "", "variable", "originOrder", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;I)V", "_userNameSpace", "getAuthor", "()Ljava/lang/String;", "setAuthor", "(Ljava/lang/String;)V", "getBookUrl", "setBookUrl", "getCoverUrl", "setCoverUrl", "infoHtml", "getInfoHtml", "setInfoHtml", "getIntro", "setIntro", "getKind", "setKind", "getLatestChapterTitle", "setLatestChapterTitle", "getName", "setName", "getOrigin", "setOrigin", "getOriginName", "setOriginName", "getOriginOrder", "()I", "setOriginOrder", "(I)V", "<set-?>", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "origins", "getOrigins", "()Ljava/util/LinkedHashSet;", "getTime", "()J", "setTime", "(J)V", "tocHtml", "getTocHtml", "setTocHtml", "getTocUrl", "setTocUrl", "getType", "setType", "getVariable", "setVariable", "variableMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getVariableMap", "()Ljava/util/HashMap;", "variableMap$delegate", "Lkotlin/Lazy;", "getWordCount", "setWordCount", "addOrigin", "", "compareTo", "other", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "", "getUserNameSpace", "hashCode", "putVariable", "key", "value", "setUserNameSpace", "nameSpace", "toBook", "Lio/legado/app/data/entities/Book;", "toString", "reader-pro"})
public final class SearchBook
implements BaseBook,
Comparable<SearchBook> {
    @NotNull
    private String bookUrl;
    @NotNull
    private String origin;
    @NotNull
    private String originName;
    private int type;
    @NotNull
    private String name;
    @NotNull
    private String author;
    @Nullable
    private String kind;
    @Nullable
    private String coverUrl;
    @Nullable
    private String intro;
    @Nullable
    private String wordCount;
    @Nullable
    private String latestChapterTitle;
    @NotNull
    private String tocUrl;
    private long time;
    @Nullable
    private String variable;
    private int originOrder;
    @Nullable
    private String infoHtml;
    @Nullable
    private String tocHtml;
    @NotNull
    private final transient Lazy variableMap$delegate;
    @NotNull
    private transient String _userNameSpace;
    @Nullable
    private LinkedHashSet<String> origins;

    public SearchBook(@NotNull String bookUrl, @NotNull String origin, @NotNull String originName, int type, @NotNull String name, @NotNull String author, @Nullable String kind, @Nullable String coverUrl, @Nullable String intro, @Nullable String wordCount, @Nullable String latestChapterTitle, @NotNull String tocUrl, long time, @Nullable String variable, int originOrder) {
        Intrinsics.checkNotNullParameter((Object)bookUrl, (String)"bookUrl");
        Intrinsics.checkNotNullParameter((Object)origin, (String)"origin");
        Intrinsics.checkNotNullParameter((Object)originName, (String)"originName");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)author, (String)"author");
        Intrinsics.checkNotNullParameter((Object)tocUrl, (String)"tocUrl");
        this.bookUrl = bookUrl;
        this.origin = origin;
        this.originName = originName;
        this.type = type;
        this.name = name;
        this.author = author;
        this.kind = kind;
        this.coverUrl = coverUrl;
        this.intro = intro;
        this.wordCount = wordCount;
        this.latestChapterTitle = latestChapterTitle;
        this.tocUrl = tocUrl;
        this.time = time;
        this.variable = variable;
        this.originOrder = originOrder;
        this.variableMap$delegate = LazyKt.lazy((Function0)((Function0)new /* Unavailable Anonymous Inner Class!! */));
        this._userNameSpace = "";
    }

    public /* synthetic */ SearchBook(String string, String string2, String string3, int n, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, long l, String string12, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 1) != 0) {
            string = "";
        }
        if ((n3 & 2) != 0) {
            string2 = "";
        }
        if ((n3 & 4) != 0) {
            string3 = "";
        }
        if ((n3 & 8) != 0) {
            n = 0;
        }
        if ((n3 & 0x10) != 0) {
            string4 = "";
        }
        if ((n3 & 0x20) != 0) {
            string5 = "";
        }
        if ((n3 & 0x40) != 0) {
            string6 = null;
        }
        if ((n3 & 0x80) != 0) {
            string7 = null;
        }
        if ((n3 & 0x100) != 0) {
            string8 = null;
        }
        if ((n3 & 0x200) != 0) {
            string9 = null;
        }
        if ((n3 & 0x400) != 0) {
            string10 = null;
        }
        if ((n3 & 0x800) != 0) {
            string11 = "";
        }
        if ((n3 & 0x1000) != 0) {
            l = 0L;
        }
        if ((n3 & 0x2000) != 0) {
            string12 = null;
        }
        if ((n3 & 0x4000) != 0) {
            n2 = 0;
        }
        this(string, string2, string3, n, string4, string5, string6, string7, string8, string9, string10, string11, l, string12, n2);
    }

    @NotNull
    public String getBookUrl() {
        return this.bookUrl;
    }

    public void setBookUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.bookUrl = string;
    }

    @NotNull
    public final String getOrigin() {
        return this.origin;
    }

    public final void setOrigin(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.origin = string;
    }

    @NotNull
    public final String getOriginName() {
        return this.originName;
    }

    public final void setOriginName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.originName = string;
    }

    public final int getType() {
        return this.type;
    }

    public final void setType(int n) {
        this.type = n;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    public void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.name = string;
    }

    @NotNull
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.author = string;
    }

    @Nullable
    public String getKind() {
        return this.kind;
    }

    public void setKind(@Nullable String string) {
        this.kind = string;
    }

    @Nullable
    public final String getCoverUrl() {
        return this.coverUrl;
    }

    public final void setCoverUrl(@Nullable String string) {
        this.coverUrl = string;
    }

    @Nullable
    public final String getIntro() {
        return this.intro;
    }

    public final void setIntro(@Nullable String string) {
        this.intro = string;
    }

    @Nullable
    public String getWordCount() {
        return this.wordCount;
    }

    public void setWordCount(@Nullable String string) {
        this.wordCount = string;
    }

    @Nullable
    public final String getLatestChapterTitle() {
        return this.latestChapterTitle;
    }

    public final void setLatestChapterTitle(@Nullable String string) {
        this.latestChapterTitle = string;
    }

    @NotNull
    public final String getTocUrl() {
        return this.tocUrl;
    }

    public final void setTocUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.tocUrl = string;
    }

    public final long getTime() {
        return this.time;
    }

    public final void setTime(long l) {
        this.time = l;
    }

    @Nullable
    public final String getVariable() {
        return this.variable;
    }

    public final void setVariable(@Nullable String string) {
        this.variable = string;
    }

    public final int getOriginOrder() {
        return this.originOrder;
    }

    public final void setOriginOrder(int n) {
        this.originOrder = n;
    }

    @Nullable
    public String getInfoHtml() {
        return this.infoHtml;
    }

    public void setInfoHtml(@Nullable String string) {
        this.infoHtml = string;
    }

    @Nullable
    public String getTocHtml() {
        return this.tocHtml;
    }

    public void setTocHtml(@Nullable String string) {
        this.tocHtml = string;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof SearchBook && Intrinsics.areEqual((Object)((SearchBook)other).getBookUrl(), (Object)this.getBookUrl());
    }

    public int hashCode() {
        return this.getBookUrl().hashCode();
    }

    @Override
    public int compareTo(@NotNull SearchBook other) {
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        return other.originOrder - this.originOrder;
    }

    @NotNull
    public HashMap<String, String> getVariableMap() {
        Lazy lazy = this.variableMap$delegate;
        boolean bl = false;
        return (HashMap)lazy.getValue();
    }

    public void putVariable(@NotNull String key, @Nullable String value) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        if (value != null) {
            Map map = this.getVariableMap();
            boolean bl = false;
            map.put(key, value);
        } else {
            this.getVariableMap().remove(key);
        }
        this.variable = GsonExtensionsKt.getGSON().toJson((Object)this.getVariableMap());
    }

    public final void setUserNameSpace(@NotNull String nameSpace) {
        Intrinsics.checkNotNullParameter((Object)nameSpace, (String)"nameSpace");
        this._userNameSpace = nameSpace;
    }

    @NotNull
    public String getUserNameSpace() {
        return this._userNameSpace;
    }

    @Nullable
    public final LinkedHashSet<String> getOrigins() {
        return this.origins;
    }

    public final void addOrigin(@NotNull String origin) {
        Object[] objectArray;
        Intrinsics.checkNotNullParameter((Object)origin, (String)"origin");
        if (this.origins == null) {
            objectArray = new String[]{this.origin};
            this.origins = SetsKt.linkedSetOf((Object[])objectArray);
        }
        if ((objectArray = this.origins) != null) {
            objectArray.add(origin);
        }
    }

    @NotNull
    public final Book toBook() {
        String string = this.getName();
        String string2 = this.getAuthor();
        String string3 = this.getKind();
        String string4 = this.getBookUrl();
        String string5 = this.origin;
        String string6 = this.originName;
        int n = this.type;
        String string7 = this.getWordCount();
        String string8 = this.latestChapterTitle;
        String string9 = this.coverUrl;
        String string10 = this.intro;
        String string11 = this.tocUrl;
        String string12 = this.variable;
        string = new Book(string4, string11, string5, string6, string, string2, string3, null, string9, null, string10, null, null, n, 0L, string8, 0L, 0L, 0, 0, null, 0, 0, 0L, string7, false, 0, 0, false, string12, null, false, null, -553690496, 1, null);
        boolean bl = false;
        boolean bl2 = false;
        String $this$toBook_u24lambda_u2d0 = string;
        boolean bl3 = false;
        $this$toBook_u24lambda_u2d0.setInfoHtml(this.getInfoHtml());
        $this$toBook_u24lambda_u2d0.setTocUrl(this.getTocUrl());
        $this$toBook_u24lambda_u2d0.setUserNameSpace(this.getUserNameSpace());
        return string;
    }

    @NotNull
    public List<String> getKindList() {
        return BaseBook.DefaultImpls.getKindList((BaseBook)this);
    }

    @Nullable
    public String getVariable(@NotNull String key) {
        return BaseBook.DefaultImpls.getVariable((BaseBook)((BaseBook)this), (String)key);
    }

    @NotNull
    public final String component1() {
        return this.getBookUrl();
    }

    @NotNull
    public final String component2() {
        return this.origin;
    }

    @NotNull
    public final String component3() {
        return this.originName;
    }

    public final int component4() {
        return this.type;
    }

    @NotNull
    public final String component5() {
        return this.getName();
    }

    @NotNull
    public final String component6() {
        return this.getAuthor();
    }

    @Nullable
    public final String component7() {
        return this.getKind();
    }

    @Nullable
    public final String component8() {
        return this.coverUrl;
    }

    @Nullable
    public final String component9() {
        return this.intro;
    }

    @Nullable
    public final String component10() {
        return this.getWordCount();
    }

    @Nullable
    public final String component11() {
        return this.latestChapterTitle;
    }

    @NotNull
    public final String component12() {
        return this.tocUrl;
    }

    public final long component13() {
        return this.time;
    }

    @Nullable
    public final String component14() {
        return this.variable;
    }

    public final int component15() {
        return this.originOrder;
    }

    @NotNull
    public final SearchBook copy(@NotNull String bookUrl, @NotNull String origin, @NotNull String originName, int type, @NotNull String name, @NotNull String author, @Nullable String kind, @Nullable String coverUrl, @Nullable String intro, @Nullable String wordCount, @Nullable String latestChapterTitle, @NotNull String tocUrl, long time, @Nullable String variable, int originOrder) {
        Intrinsics.checkNotNullParameter((Object)bookUrl, (String)"bookUrl");
        Intrinsics.checkNotNullParameter((Object)origin, (String)"origin");
        Intrinsics.checkNotNullParameter((Object)originName, (String)"originName");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)author, (String)"author");
        Intrinsics.checkNotNullParameter((Object)tocUrl, (String)"tocUrl");
        return new SearchBook(bookUrl, origin, originName, type, name, author, kind, coverUrl, intro, wordCount, latestChapterTitle, tocUrl, time, variable, originOrder);
    }

    public static /* synthetic */ SearchBook copy$default(SearchBook searchBook2, String string, String string2, String string3, int n, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, long l, String string12, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            string = searchBook2.getBookUrl();
        }
        if ((n3 & 2) != 0) {
            string2 = searchBook2.origin;
        }
        if ((n3 & 4) != 0) {
            string3 = searchBook2.originName;
        }
        if ((n3 & 8) != 0) {
            n = searchBook2.type;
        }
        if ((n3 & 0x10) != 0) {
            string4 = searchBook2.getName();
        }
        if ((n3 & 0x20) != 0) {
            string5 = searchBook2.getAuthor();
        }
        if ((n3 & 0x40) != 0) {
            string6 = searchBook2.getKind();
        }
        if ((n3 & 0x80) != 0) {
            string7 = searchBook2.coverUrl;
        }
        if ((n3 & 0x100) != 0) {
            string8 = searchBook2.intro;
        }
        if ((n3 & 0x200) != 0) {
            string9 = searchBook2.getWordCount();
        }
        if ((n3 & 0x400) != 0) {
            string10 = searchBook2.latestChapterTitle;
        }
        if ((n3 & 0x800) != 0) {
            string11 = searchBook2.tocUrl;
        }
        if ((n3 & 0x1000) != 0) {
            l = searchBook2.time;
        }
        if ((n3 & 0x2000) != 0) {
            string12 = searchBook2.variable;
        }
        if ((n3 & 0x4000) != 0) {
            n2 = searchBook2.originOrder;
        }
        return searchBook2.copy(string, string2, string3, n, string4, string5, string6, string7, string8, string9, string10, string11, l, string12, n2);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SearchBook(bookUrl=").append(this.getBookUrl()).append(", origin=").append(this.origin).append(", originName=").append(this.originName).append(", type=").append(this.type).append(", name=").append(this.getName()).append(", author=").append(this.getAuthor()).append(", kind=").append((Object)this.getKind()).append(", coverUrl=").append((Object)this.coverUrl).append(", intro=").append((Object)this.intro).append(", wordCount=").append((Object)this.getWordCount()).append(", latestChapterTitle=").append((Object)this.latestChapterTitle).append(", tocUrl=");
        stringBuilder.append(this.tocUrl).append(", time=").append(this.time).append(", variable=").append((Object)this.variable).append(", originOrder=").append(this.originOrder).append(')');
        return stringBuilder.toString();
    }

    public SearchBook() {
        this(null, null, null, 0, null, null, null, null, null, null, null, null, 0L, null, 0, Short.MAX_VALUE, null);
    }
}

