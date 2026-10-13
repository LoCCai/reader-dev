/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  io.legado.app.data.entities.BookChapter
 *  io.legado.app.model.analyzeRule.AnalyzeUrl
 *  io.legado.app.model.analyzeRule.RuleDataInterface
 *  io.legado.app.model.analyzeRule.RuleDataInterface$DefaultImpls
 *  io.legado.app.utils.GsonExtensionsKt
 *  io.legado.app.utils.MD5Utils
 *  io.legado.app.utils.NetworkUtils
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.StringCompanionObject
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.MD5Utils;
import io.legado.app.utils.NetworkUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JsonIgnoreProperties(value={"variableMap", "_userNameSpace", "userNameSpace"})
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b)\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0087\b\u0018\u00002\u00020\u0001B\u0095\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0013J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010?\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u000b\u0010@\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010A\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010C\u001a\u00020\u0003H\u00c6\u0003J\t\u0010D\u001a\u00020\u0006H\u00c6\u0003J\t\u0010E\u001a\u00020\u0003H\u00c6\u0003J\t\u0010F\u001a\u00020\u0003H\u00c6\u0003J\t\u0010G\u001a\u00020\nH\u00c6\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010I\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010J\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u009e\u0001\u0010K\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010LJ\u0013\u0010M\u001a\u00020\u00062\b\u0010N\u001a\u0004\u0018\u00010OH\u0096\u0002J\u0006\u0010P\u001a\u00020\u0003J\u0006\u0010Q\u001a\u00020\u0003J\b\u0010R\u001a\u00020\u0003H\u0016J\b\u0010S\u001a\u00020\nH\u0016J\u001a\u0010T\u001a\u00020U2\u0006\u0010V\u001a\u00020\u00032\b\u0010W\u001a\u0004\u0018\u00010\u0003H\u0016J\u000e\u0010X\u001a\u00020U2\u0006\u0010Y\u001a\u00020\u0003J\t\u0010Z\u001a\u00020\u0003H\u00d6\u0001R\u000e\u0010\u0014\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0016\"\u0004\b\u001a\u0010\u0018R\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001f\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0016\"\u0004\b!\u0010\u0018R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010&\"\u0004\b'\u0010(R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0016\"\u0004\b*\u0010\u0018R\u001e\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001f\u001a\u0004\b+\u0010\u001c\"\u0004\b,\u0010\u001eR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u0016\"\u0004\b.\u0010\u0018R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0016\"\u0004\b0\u0010\u0018R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0016\"\u0004\b2\u0010\u0018R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u0016\"\u0004\b4\u0010\u0018R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u0016\"\u0004\b6\u0010\u0018R7\u00107\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000308j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003`98VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b<\u0010=\u001a\u0004\b:\u0010;\u00a8\u0006["}, d2={"Lio/legado/app/data/entities/BookChapter;", "Lio/legado/app/model/analyzeRule/RuleDataInterface;", "url", "", "title", "isVolume", "", "baseUrl", "bookUrl", "index", "", "resourceUrl", "tag", "start", "", "end", "startFragmentId", "endFragmentId", "variable", "(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "_userNameSpace", "getBaseUrl", "()Ljava/lang/String;", "setBaseUrl", "(Ljava/lang/String;)V", "getBookUrl", "setBookUrl", "getEnd", "()Ljava/lang/Long;", "setEnd", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "getEndFragmentId", "setEndFragmentId", "getIndex", "()I", "setIndex", "(I)V", "()Z", "setVolume", "(Z)V", "getResourceUrl", "setResourceUrl", "getStart", "setStart", "getStartFragmentId", "setStartFragmentId", "getTag", "setTag", "getTitle", "setTitle", "getUrl", "setUrl", "getVariable", "setVariable", "variableMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getVariableMap", "()Ljava/util/HashMap;", "variableMap$delegate", "Lkotlin/Lazy;", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lio/legado/app/data/entities/BookChapter;", "equals", "other", "", "getAbsoluteURL", "getFileName", "getUserNameSpace", "hashCode", "putVariable", "", "key", "value", "setUserNameSpace", "nameSpace", "toString", "reader-pro"})
public final class BookChapter
implements RuleDataInterface {
    @NotNull
    private String url;
    @NotNull
    private String title;
    private boolean isVolume;
    @NotNull
    private String baseUrl;
    @NotNull
    private String bookUrl;
    private int index;
    @Nullable
    private String resourceUrl;
    @Nullable
    private String tag;
    @Nullable
    private Long start;
    @Nullable
    private Long end;
    @Nullable
    private String startFragmentId;
    @Nullable
    private String endFragmentId;
    @Nullable
    private String variable;
    @NotNull
    private final transient Lazy variableMap$delegate;
    @NotNull
    private transient String _userNameSpace;

    public BookChapter(@NotNull String url2, @NotNull String title, boolean isVolume, @NotNull String baseUrl, @NotNull String bookUrl, int index, @Nullable String resourceUrl, @Nullable String tag, @Nullable Long start2, @Nullable Long end, @Nullable String startFragmentId, @Nullable String endFragmentId, @Nullable String variable) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter((Object)baseUrl, (String)"baseUrl");
        Intrinsics.checkNotNullParameter((Object)bookUrl, (String)"bookUrl");
        this.url = url2;
        this.title = title;
        this.isVolume = isVolume;
        this.baseUrl = baseUrl;
        this.bookUrl = bookUrl;
        this.index = index;
        this.resourceUrl = resourceUrl;
        this.tag = tag;
        this.start = start2;
        this.end = end;
        this.startFragmentId = startFragmentId;
        this.endFragmentId = endFragmentId;
        this.variable = variable;
        this.variableMap$delegate = LazyKt.lazy((Function0)((Function0)new /* Unavailable Anonymous Inner Class!! */));
        this._userNameSpace = "";
    }

    public /* synthetic */ BookChapter(String string, String string2, boolean bl, String string3, String string4, int n, String string5, String string6, Long l, Long l2, String string7, String string8, String string9, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            string = "";
        }
        if ((n2 & 2) != 0) {
            string2 = "";
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        if ((n2 & 8) != 0) {
            string3 = "";
        }
        if ((n2 & 0x10) != 0) {
            string4 = "";
        }
        if ((n2 & 0x20) != 0) {
            n = 0;
        }
        if ((n2 & 0x40) != 0) {
            string5 = null;
        }
        if ((n2 & 0x80) != 0) {
            string6 = null;
        }
        if ((n2 & 0x100) != 0) {
            l = null;
        }
        if ((n2 & 0x200) != 0) {
            l2 = null;
        }
        if ((n2 & 0x400) != 0) {
            string7 = null;
        }
        if ((n2 & 0x800) != 0) {
            string8 = null;
        }
        if ((n2 & 0x1000) != 0) {
            string9 = null;
        }
        this(string, string2, bl, string3, string4, n, string5, string6, l, l2, string7, string8, string9);
    }

    @NotNull
    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.url = string;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.title = string;
    }

    public final boolean isVolume() {
        return this.isVolume;
    }

    public final void setVolume(boolean bl) {
        this.isVolume = bl;
    }

    @NotNull
    public final String getBaseUrl() {
        return this.baseUrl;
    }

    public final void setBaseUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.baseUrl = string;
    }

    @NotNull
    public final String getBookUrl() {
        return this.bookUrl;
    }

    public final void setBookUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.bookUrl = string;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int n) {
        this.index = n;
    }

    @Nullable
    public final String getResourceUrl() {
        return this.resourceUrl;
    }

    public final void setResourceUrl(@Nullable String string) {
        this.resourceUrl = string;
    }

    @Nullable
    public final String getTag() {
        return this.tag;
    }

    public final void setTag(@Nullable String string) {
        this.tag = string;
    }

    @Nullable
    public final Long getStart() {
        return this.start;
    }

    public final void setStart(@Nullable Long l) {
        this.start = l;
    }

    @Nullable
    public final Long getEnd() {
        return this.end;
    }

    public final void setEnd(@Nullable Long l) {
        this.end = l;
    }

    @Nullable
    public final String getStartFragmentId() {
        return this.startFragmentId;
    }

    public final void setStartFragmentId(@Nullable String string) {
        this.startFragmentId = string;
    }

    @Nullable
    public final String getEndFragmentId() {
        return this.endFragmentId;
    }

    public final void setEndFragmentId(@Nullable String string) {
        this.endFragmentId = string;
    }

    @Nullable
    public final String getVariable() {
        return this.variable;
    }

    public final void setVariable(@Nullable String string) {
        this.variable = string;
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

    public int hashCode() {
        return this.url.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (other instanceof BookChapter) {
            return Intrinsics.areEqual((Object)((BookChapter)other).url, (Object)this.url);
        }
        return false;
    }

    @NotNull
    public final String getAbsoluteURL() {
        String string;
        String string2;
        boolean bl;
        int n;
        Matcher urlMatcher = AnalyzeUrl.Companion.getParamPattern().matcher(this.url);
        if (urlMatcher.find()) {
            String string3 = this.url;
            int n2 = 0;
            n = urlMatcher.start();
            bl = false;
            String string4 = string3;
            if (string4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string5 = string4.substring(n2, n);
            string2 = string5;
            Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        } else {
            string2 = this.url;
        }
        String urlBefore = string2;
        String urlAbsoluteBefore = NetworkUtils.INSTANCE.getAbsoluteURL(this.baseUrl, urlBefore);
        if (urlBefore.length() == this.url.length()) {
            string = urlAbsoluteBefore;
        } else {
            StringBuilder stringBuilder = new StringBuilder().append(urlAbsoluteBefore).append(',');
            String string6 = this.url;
            n = urlMatcher.end();
            bl = false;
            String string7 = string6;
            if (string7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string8 = string7.substring(n);
            Intrinsics.checkNotNullExpressionValue((Object)string8, (String)"(this as java.lang.String).substring(startIndex)");
            string = stringBuilder.append(string8).toString();
        }
        return string;
    }

    @NotNull
    public final String getFileName() {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String string = "%05d-%s.nb";
        Object[] objectArray = new Object[]{this.index, MD5Utils.INSTANCE.md5Encode16(this.title)};
        boolean bl = false;
        String string2 = String.format(string, Arrays.copyOf(objectArray, objectArray.length));
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"java.lang.String.format(format, *args)");
        return string2;
    }

    @Nullable
    public String getVariable(@NotNull String key) {
        return RuleDataInterface.DefaultImpls.getVariable((RuleDataInterface)this, (String)key);
    }

    @NotNull
    public final String component1() {
        return this.url;
    }

    @NotNull
    public final String component2() {
        return this.title;
    }

    public final boolean component3() {
        return this.isVolume;
    }

    @NotNull
    public final String component4() {
        return this.baseUrl;
    }

    @NotNull
    public final String component5() {
        return this.bookUrl;
    }

    public final int component6() {
        return this.index;
    }

    @Nullable
    public final String component7() {
        return this.resourceUrl;
    }

    @Nullable
    public final String component8() {
        return this.tag;
    }

    @Nullable
    public final Long component9() {
        return this.start;
    }

    @Nullable
    public final Long component10() {
        return this.end;
    }

    @Nullable
    public final String component11() {
        return this.startFragmentId;
    }

    @Nullable
    public final String component12() {
        return this.endFragmentId;
    }

    @Nullable
    public final String component13() {
        return this.variable;
    }

    @NotNull
    public final BookChapter copy(@NotNull String url2, @NotNull String title, boolean isVolume, @NotNull String baseUrl, @NotNull String bookUrl, int index, @Nullable String resourceUrl, @Nullable String tag, @Nullable Long start2, @Nullable Long end, @Nullable String startFragmentId, @Nullable String endFragmentId, @Nullable String variable) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter((Object)baseUrl, (String)"baseUrl");
        Intrinsics.checkNotNullParameter((Object)bookUrl, (String)"bookUrl");
        return new BookChapter(url2, title, isVolume, baseUrl, bookUrl, index, resourceUrl, tag, start2, end, startFragmentId, endFragmentId, variable);
    }

    public static /* synthetic */ BookChapter copy$default(BookChapter bookChapter, String string, String string2, boolean bl, String string3, String string4, int n, String string5, String string6, Long l, Long l2, String string7, String string8, String string9, int n2, Object object) {
        if ((n2 & 1) != 0) {
            string = bookChapter.url;
        }
        if ((n2 & 2) != 0) {
            string2 = bookChapter.title;
        }
        if ((n2 & 4) != 0) {
            bl = bookChapter.isVolume;
        }
        if ((n2 & 8) != 0) {
            string3 = bookChapter.baseUrl;
        }
        if ((n2 & 0x10) != 0) {
            string4 = bookChapter.bookUrl;
        }
        if ((n2 & 0x20) != 0) {
            n = bookChapter.index;
        }
        if ((n2 & 0x40) != 0) {
            string5 = bookChapter.resourceUrl;
        }
        if ((n2 & 0x80) != 0) {
            string6 = bookChapter.tag;
        }
        if ((n2 & 0x100) != 0) {
            l = bookChapter.start;
        }
        if ((n2 & 0x200) != 0) {
            l2 = bookChapter.end;
        }
        if ((n2 & 0x400) != 0) {
            string7 = bookChapter.startFragmentId;
        }
        if ((n2 & 0x800) != 0) {
            string8 = bookChapter.endFragmentId;
        }
        if ((n2 & 0x1000) != 0) {
            string9 = bookChapter.variable;
        }
        return bookChapter.copy(string, string2, bl, string3, string4, n, string5, string6, l, l2, string7, string8, string9);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BookChapter(url=").append(this.url).append(", title=").append(this.title).append(", isVolume=").append(this.isVolume).append(", baseUrl=").append(this.baseUrl).append(", bookUrl=").append(this.bookUrl).append(", index=").append(this.index).append(", resourceUrl=").append((Object)this.resourceUrl).append(", tag=").append((Object)this.tag).append(", start=").append(this.start).append(", end=").append(this.end).append(", startFragmentId=").append((Object)this.startFragmentId).append(", endFragmentId=");
        stringBuilder.append((Object)this.endFragmentId).append(", variable=").append((Object)this.variable).append(')');
        return stringBuilder.toString();
    }

    public BookChapter() {
        this(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
    }
}

