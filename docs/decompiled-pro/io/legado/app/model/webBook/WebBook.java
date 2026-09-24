/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.BaseSource
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookChapter
 *  io.legado.app.data.entities.BookSource
 *  io.legado.app.data.entities.SearchBook
 *  io.legado.app.exception.NoStackTraceException
 *  io.legado.app.help.http.StrResponse
 *  io.legado.app.model.Debug
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.DebugLog$DefaultImpls
 *  io.legado.app.model.analyzeRule.AnalyzeUrl
 *  io.legado.app.model.analyzeRule.RuleDataInterface
 *  io.legado.app.model.webBook.BookChapterList
 *  io.legado.app.model.webBook.BookContent
 *  io.legado.app.model.webBook.BookInfo
 *  io.legado.app.model.webBook.BookList
 *  io.legado.app.model.webBook.WebBook
 *  io.legado.app.model.webBook.WebBook$exploreBook$1
 *  io.legado.app.model.webBook.WebBook$getBookContent$1
 *  io.legado.app.model.webBook.WebBook$getBookInfo$1
 *  io.legado.app.model.webBook.WebBook$getChapterList$1
 *  io.legado.app.model.webBook.WebBook$preciseSearch$1
 *  io.legado.app.model.webBook.WebBook$searchBook$1
 *  io.legado.app.model.webBook.WebBookKt
 *  kotlin.Metadata
 *  kotlin.Result
 *  kotlin.ResultKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.coroutines.jvm.internal.Boxing
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model.webBook;

import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.data.entities.BookSource;
import io.legado.app.data.entities.SearchBook;
import io.legado.app.exception.NoStackTraceException;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.Debug;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.model.webBook.BookChapterList;
import io.legado.app.model.webBook.BookContent;
import io.legado.app.model.webBook.BookInfo;
import io.legado.app.model.webBook.BookList;
import io.legado.app.model.webBook.WebBook;
import io.legado.app.model.webBook.WebBookKt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B1\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\tB/\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\fJ+\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010\"\u001a\u00020\u00032\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%J-\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\n\b\u0002\u0010+\u001a\u0004\u0018\u00010\u0003H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010,J#\u0010-\u001a\u00020(2\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010.\u001a\u00020\u0005H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010/J#\u0010-\u001a\u00020(2\u0006\u00100\u001a\u00020\u00032\b\b\u0002\u0010.\u001a\u00020\u0005H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00101J\u001f\u00102\u001a\b\u0012\u0004\u0012\u00020*0 2\u0006\u0010'\u001a\u00020(H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00103J2\u00104\u001a\b\u0012\u0004\u0012\u00020(052\u0006\u00106\u001a\u00020\u00032\u0006\u00107\u001a\u00020\u0003H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b8\u00109J+\u0010:\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010;\u001a\u00020\u00032\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0012R\u0011\u0010\u0017\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0019R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0019\"\u0004\b\u001d\u0010\u001e\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006<"}, d2={"Lio/legado/app/model/webBook/WebBook;", "", "bookSourceString", "", "debugLog", "", "debugLogger", "Lio/legado/app/model/DebugLog;", "userNameSpace", "(Ljava/lang/String;ZLio/legado/app/model/DebugLog;Ljava/lang/String;)V", "bookSource", "Lio/legado/app/data/entities/BookSource;", "(Lio/legado/app/data/entities/BookSource;ZLio/legado/app/model/DebugLog;Ljava/lang/String;)V", "getBookSource", "()Lio/legado/app/data/entities/BookSource;", "getDebugLog", "()Z", "getDebugLogger", "()Lio/legado/app/model/DebugLog;", "setDebugLogger", "(Lio/legado/app/model/DebugLog;)V", "debugger", "getDebugger", "sourceUrl", "getSourceUrl", "()Ljava/lang/String;", "userNS", "getUserNS", "getUserNameSpace", "setUserNameSpace", "(Ljava/lang/String;)V", "exploreBook", "", "Lio/legado/app/data/entities/SearchBook;", "url", "page", "", "(Ljava/lang/String;Ljava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBookContent", "book", "Lio/legado/app/data/entities/Book;", "bookChapter", "Lio/legado/app/data/entities/BookChapter;", "nextChapterUrl", "(Lio/legado/app/data/entities/Book;Lio/legado/app/data/entities/BookChapter;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBookInfo", "canReName", "(Lio/legado/app/data/entities/Book;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "bookUrl", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChapterList", "(Lio/legado/app/data/entities/Book;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "preciseSearch", "Lkotlin/Result;", "name", "author", "preciseSearch-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchBook", "key", "reader-pro"})
public final class WebBook {
    @NotNull
    private final BookSource bookSource;
    private final boolean debugLog;
    @Nullable
    private DebugLog debugLogger;
    @Nullable
    private String userNameSpace;

    public WebBook(@NotNull BookSource bookSource, boolean debugLog, @Nullable DebugLog debugLogger, @Nullable String userNameSpace) {
        Intrinsics.checkNotNullParameter((Object)bookSource, (String)"bookSource");
        this.bookSource = bookSource;
        this.debugLog = debugLog;
        this.debugLogger = debugLogger;
        this.userNameSpace = userNameSpace;
    }

    public /* synthetic */ WebBook(BookSource bookSource, boolean bl, DebugLog debugLog, String string, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = true;
        }
        if ((n & 4) != 0) {
            debugLog = null;
        }
        if ((n & 8) != 0) {
            string = null;
        }
        this(bookSource, bl, debugLog, string);
    }

    @NotNull
    public final BookSource getBookSource() {
        return this.bookSource;
    }

    public final boolean getDebugLog() {
        return this.debugLog;
    }

    @Nullable
    public final DebugLog getDebugLogger() {
        return this.debugLogger;
    }

    public final void setDebugLogger(@Nullable DebugLog debugLog) {
        this.debugLogger = debugLog;
    }

    @Nullable
    public final String getUserNameSpace() {
        return this.userNameSpace;
    }

    public final void setUserNameSpace(@Nullable String string) {
        this.userNameSpace = string;
    }

    public WebBook(@NotNull String bookSourceString, boolean debugLog, @Nullable DebugLog debugLogger, @Nullable String userNameSpace) {
        Intrinsics.checkNotNullParameter((Object)bookSourceString, (String)"bookSourceString");
        Object object = BookSource.Companion.fromJson-IoAF18A(bookSourceString);
        boolean bl = false;
        BookSource bookSource = (BookSource)(Result.isFailure-impl((Object)object) ? null : object);
        this(bookSource == null ? new BookSource(null, null, null, 0, null, 0, false, false, null, null, null, null, null, null, null, null, 0L, 0L, 0, null, null, null, null, null, null, null, 0x3FFFFFF, null) : bookSource, debugLog, debugLogger, userNameSpace);
    }

    public /* synthetic */ WebBook(String string, boolean bl, DebugLog debugLog, String string2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = true;
        }
        if ((n & 4) != 0) {
            debugLog = null;
        }
        if ((n & 8) != 0) {
            string2 = null;
        }
        this(string, bl, debugLog, string2);
    }

    @NotNull
    public final String getSourceUrl() {
        return this.bookSource.getBookSourceUrl();
    }

    @Nullable
    public final DebugLog getDebugger() {
        if (this.debugLogger != null) {
            return this.debugLogger;
        }
        if (this.debugLog) {
            return (DebugLog)Debug.INSTANCE;
        }
        return null;
    }

    @NotNull
    public final String getUserNS() {
        String string = this.userNameSpace;
        return string == null ? "unknow" : string;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object searchBook(@NotNull String var1_1, @Nullable Integer var2_2, @NotNull Continuation<? super List<SearchBook>> var3_3) {
        if (!(var3_3 instanceof searchBook.1)) ** GOTO lbl-1000
        var26_4 = var3_3;
        if ((var26_4.label & -2147483648) != 0) {
            var26_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var27_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                variableBook = new SearchBook(null, null, null, 0, null, null, null, null, null, null, null, null, 0L, null, 0, 32767, null);
                variableBook.setUserNameSpace(this.getUserNS());
                this.getBookSource().setUserNameSpace(this.getUserNS());
                this.getBookSource().setLogger(this.getDebugger());
                var6_8 = this.getBookSource().getSearchUrl();
                if (var6_8 != null) ** GOTO lbl20
                v0 = null;
                ** GOTO lbl97
lbl20:
                // 1 sources

                var7_10 = var6_8;
                var8_11 = false;
                var9_12 = false;
                searchUrl = var7_10;
                $i$a$-let-WebBook$searchBook$2 = false;
                analyzeUrl = new AnalyzeUrl(searchUrl, (String)key, (Integer)page, null, null, this.getBookSource().getBookSourceUrl(), (BaseSource)this.getBookSource(), (RuleDataInterface)variableBook, null, (Map)this.getBookSource().getHeaderMap(true), this.getDebugger(), 280, null);
                res = new Ref.ObjectRef();
                var14_17 = res;
                $continuation.L$0 = this;
                $continuation.L$1 = variableBook;
                $continuation.L$2 = analyzeUrl;
                $continuation.L$3 = res;
                $continuation.L$4 = var14_17;
                $continuation.label = 1;
                v1 = AnalyzeUrl.getStrResponseAwait$default((AnalyzeUrl)analyzeUrl, null, null, (boolean)false, (Continuation)$continuation, (int)7, null);
                if (v1 == var27_6) {
                    return var27_6;
                }
                ** GOTO lbl47
            }
            case 1: {
                $i$a$-let-WebBook$searchBook$2 = false;
                var14_17 = (Ref.ObjectRef)$continuation.L$4;
                res = (Ref.ObjectRef)$continuation.L$3;
                analyzeUrl = (AnalyzeUrl)$continuation.L$2;
                var4_7 = (SearchBook)$continuation.L$1;
                this = (WebBook)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl47:
                // 2 sources

                var14_17.element = var15_18 = v1;
                var16_19 = this.getBookSource().getLoginCheckJs();
                if (var16_19 != null) {
                    var17_20 = var16_19;
                    var18_22 = false;
                    var19_24 = false;
                    checkJs = var17_20;
                    $i$a$-let-WebBook$searchBook$2$1 = false;
                    var22_30 = checkJs;
                    var23_31 = false;
                    if (StringsKt.isBlank((CharSequence)var22_30) == false) {
                        var22_30 = analyzeUrl.evalJS(checkJs, res.element);
                        if (var22_30 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type io.legado.app.help.http.StrResponse");
                        }
                        res.element = (StrResponse)var22_30;
                    }
                }
                $continuation.L$0 = null;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.label = 2;
                v2 = BookList.INSTANCE.analyzeBookList(((StrResponse)res.element).getBody(), this.getBookSource(), analyzeUrl, ((StrResponse)res.element).getUrl(), var4_7, true, this.getDebugger(), (Continuation)$continuation);
                if (v2 == var27_6) {
                    return var27_6;
                }
                ** GOTO lbl78
            }
            case 2: {
                $i$a$-let-WebBook$searchBook$2 = false;
                ResultKt.throwOnFailure((Object)$result);
                v2 = $result;
lbl78:
                // 2 sources

                $this$map$iv = (Iterable)v2;
                $i$f$map = false;
                var18_23 = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    var23_32 = (SearchBook)item$iv$iv;
                    var14_17 = destination$iv$iv;
                    $i$a$-map-WebBook$searchBook$2$2 = false;
                    it.setTocHtml("");
                    it.setInfoHtml("");
                    var15_18 = it;
                    var14_17.add(var15_18);
                }
                v0 = (List)destination$iv$iv;
lbl97:
                // 2 sources

                var5_34 = v0;
                if (var5_34 == null) {
                    var6_9 = false;
                    v3 = new ArrayList<E>();
                } else {
                    v3 = var5_34;
                }
                return v3;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object searchBook$default(WebBook webBook, String string, Integer n, Continuation continuation, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 1;
        }
        return webBook.searchBook(string, n, continuation);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object exploreBook(@NotNull String var1_1, @Nullable Integer var2_2, @NotNull Continuation<? super List<SearchBook>> var3_3) {
        if (!(var3_3 instanceof exploreBook.1)) ** GOTO lbl-1000
        var18_4 = var3_3;
        if ((var18_4.label & -2147483648) != 0) {
            var18_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var19_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                variableBook = new SearchBook(null, null, null, 0, null, null, null, null, null, null, null, null, 0L, null, 0, 32767, null);
                variableBook.setUserNameSpace(this.getUserNS());
                this.getBookSource().setUserNameSpace(this.getUserNS());
                this.getBookSource().setLogger(this.getDebugger());
                analyzeUrl = new AnalyzeUrl((String)url, null, (Integer)page, null, null, this.getBookSource().getBookSourceUrl(), (BaseSource)this.getBookSource(), (RuleDataInterface)variableBook, null, (Map)this.getBookSource().getHeaderMap(true), this.getDebugger(), 282, null);
                var15_10 = res = new Ref.ObjectRef();
                $continuation.L$0 = this;
                $continuation.L$1 = variableBook;
                $continuation.L$2 = analyzeUrl;
                $continuation.L$3 = res;
                $continuation.L$4 = var15_10;
                $continuation.label = 1;
                v0 = AnalyzeUrl.getStrResponseAwait$default((AnalyzeUrl)analyzeUrl, null, null, (boolean)false, (Continuation)$continuation, (int)7, null);
                if (v0 == var19_6) {
                    return var19_6;
                }
                ** GOTO lbl36
            }
            case 1: {
                var15_10 = (Ref.ObjectRef)$continuation.L$4;
                res = (Ref.ObjectRef)$continuation.L$3;
                analyzeUrl = (AnalyzeUrl)$continuation.L$2;
                variableBook = (SearchBook)$continuation.L$1;
                this = (WebBook)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl36:
                // 2 sources

                var15_10.element = var16_11 = v0;
                var7_12 = this.getBookSource().getLoginCheckJs();
                if (var7_12 != null) {
                    var8_13 = var7_12;
                    var9_14 = false;
                    var10_15 = false;
                    checkJs = var8_13;
                    $i$a$-let-WebBook$exploreBook$2 = false;
                    var13_18 = checkJs;
                    var14_19 = false;
                    if (StringsKt.isBlank((CharSequence)var13_18) == false) {
                        var13_18 = analyzeUrl.evalJS(checkJs, res.element);
                        if (var13_18 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type io.legado.app.help.http.StrResponse");
                        }
                        res.element = (StrResponse)var13_18;
                    }
                }
                $continuation.L$0 = null;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.label = 2;
                v1 = BookList.INSTANCE.analyzeBookList(((StrResponse)res.element).getBody(), this.getBookSource(), analyzeUrl, ((StrResponse)res.element).getUrl(), variableBook, false, this.getDebugger(), (Continuation)$continuation);
                if (v1 == var19_6) {
                    return var19_6;
                }
                ** GOTO lbl66
            }
            case 2: {
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl66:
                // 2 sources

                return v1;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object exploreBook$default(WebBook webBook, String string, Integer n, Continuation continuation, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 1;
        }
        return webBook.exploreBook(string, n, continuation);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getBookInfo(@NotNull Book var1_1, boolean var2_2, @NotNull Continuation<? super Book> var3_3) {
        if (!(var3_3 instanceof getBookInfo.1)) ** GOTO lbl-1000
        var17_4 = var3_3;
        if ((var17_4.label & -2147483648) != 0) {
            var17_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var18_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                book.setType(this.getBookSource().getBookSourceType());
                book.setUserNameSpace(this.getUserNS());
                this.getBookSource().setUserNameSpace(this.getUserNS());
                this.getBookSource().setLogger(this.getDebugger());
                var4_7 = book.getInfoHtml();
                var5_8 = false;
                var6_10 = false;
                if (var4_7 == null || var4_7.length() == 0) break;
                $continuation.L$0 = book;
                $continuation.label = 1;
                v0 = BookInfo.analyzeBookInfo$default((BookInfo)BookInfo.INSTANCE, (Book)book, (String)book.getInfoHtml(), (BookSource)this.getBookSource(), (String)book.getBookUrl(), (String)book.getBookUrl(), (boolean)(canReName != false), null, (Continuation)$continuation, (int)64, null);
                if (v0 == var18_6) {
                    return var18_6;
                }
                ** GOTO lbl30
            }
            case 1: {
                var1_1 = (Book)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl30:
                // 2 sources

                return var1_1;
            }
        }
        analyzeUrl = new AnalyzeUrl(var1_1.getBookUrl(), null, null, null, null, this.getBookSource().getBookSourceUrl(), (BaseSource)this.getBookSource(), (RuleDataInterface)var1_1, null, (Map)this.getBookSource().getHeaderMap(true), this.getDebugger(), 286, null);
        var14_12 = res = new Ref.ObjectRef();
        $continuation.L$0 = this;
        $continuation.L$1 = var1_1;
        $continuation.L$2 = analyzeUrl;
        $continuation.L$3 = res;
        $continuation.L$4 = var14_12;
        $continuation.Z$0 = var2_2;
        $continuation.label = 2;
        v1 = AnalyzeUrl.getStrResponseAwait$default((AnalyzeUrl)analyzeUrl, null, null, (boolean)false, (Continuation)$continuation, (int)7, null);
        if (v1 == var18_6) {
            return var18_6;
        }
        ** GOTO lbl53
        {
            case 2: {
                var2_2 = $continuation.Z$0;
                var14_12 = (Ref.ObjectRef)$continuation.L$4;
                res = (Ref.ObjectRef)$continuation.L$3;
                analyzeUrl = (AnalyzeUrl)$continuation.L$2;
                var1_1 = (Book)$continuation.L$1;
                this = (WebBook)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl53:
                // 2 sources

                var14_12.element = var15_13 = v1;
                var6_11 = this.getBookSource().getLoginCheckJs();
                if (var6_11 != null) {
                    var7_14 = var6_11;
                    var8_15 = false;
                    var9_16 = false;
                    checkJs = var7_14;
                    $i$a$-let-WebBook$getBookInfo$2 = false;
                    var12_19 = checkJs;
                    var13_20 = false;
                    if (StringsKt.isBlank((CharSequence)var12_19) == false) {
                        var12_19 = analyzeUrl.evalJS(checkJs, res.element);
                        if (var12_19 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type io.legado.app.help.http.StrResponse");
                        }
                        res.element = (StrResponse)var12_19;
                    }
                }
                $continuation.L$0 = var1_1;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.label = 3;
                v2 = BookInfo.INSTANCE.analyzeBookInfo(var1_1, ((StrResponse)res.element).getBody(), this.getBookSource(), var1_1.getBookUrl(), ((StrResponse)res.element).getUrl(), var2_2 != false, this.getDebugger(), (Continuation)$continuation);
                if (v2 == var18_6) {
                    return var18_6;
                }
                ** GOTO lbl84
            }
            case 3: {
                var1_1 = (Book)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v2 = $result;
lbl84:
                // 2 sources

                var1_1.setTocHtml(null);
                return var1_1;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object getBookInfo$default(WebBook webBook, Book book, boolean bl, Continuation continuation, int n, Object object) {
        if ((n & 2) != 0) {
            bl = true;
        }
        return webBook.getBookInfo(book, bl, continuation);
    }

    @Nullable
    public final Object getBookInfo(@NotNull String bookUrl, boolean canReName, @NotNull Continuation<? super Book> $completion) {
        Book book = new Book(null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0L, null, 0L, 0L, 0, 0, null, 0, 0, 0L, null, false, 0, 0, false, null, null, false, null, -1, 1, null);
        book.setBookUrl(bookUrl);
        book.setOrigin(this.getBookSource().getBookSourceUrl());
        book.setOriginName(this.getBookSource().getBookSourceName());
        book.setOriginOrder(this.getBookSource().getCustomOrder());
        book.setType(this.getBookSource().getBookSourceType());
        book.setUserNameSpace(this.getUserNS());
        return this.getBookInfo(book, canReName, $completion);
    }

    public static /* synthetic */ Object getBookInfo$default(WebBook webBook, String string, boolean bl, Continuation continuation, int n, Object object) {
        if ((n & 2) != 0) {
            bl = true;
        }
        return webBook.getBookInfo(string, bl, continuation);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getChapterList(@NotNull Book var1_1, @NotNull Continuation<? super List<BookChapter>> var2_2) {
        if (!(var2_2 instanceof getChapterList.1)) ** GOTO lbl-1000
        var16_3 = var2_2;
        if ((var16_3.label & -2147483648) != 0) {
            var16_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var17_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                book.setType(this.getBookSource().getBookSourceType());
                book.setUserNameSpace(this.getUserNS());
                this.getBookSource().setUserNameSpace(this.getUserNS());
                this.getBookSource().setLogger(this.getDebugger());
                if (!Intrinsics.areEqual((Object)book.getBookUrl(), (Object)book.getTocUrl())) break;
                var3_6 = book.getTocHtml();
                var4_7 = false;
                var5_9 = false;
                if (var3_6 == null || var3_6.length() == 0) break;
                $continuation.label = 1;
                v0 = BookChapterList.analyzeChapterList$default((BookChapterList)BookChapterList.INSTANCE, (Book)book, (String)book.getTocHtml(), (BookSource)this.getBookSource(), (String)book.getTocUrl(), (String)book.getTocUrl(), null, (Continuation)$continuation, (int)32, null);
                if (v0 == var17_5) {
                    return var17_5;
                }
                ** GOTO lbl29
            }
            case 1: {
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl29:
                // 2 sources

                return v0;
            }
        }
        analyzeUrl = new AnalyzeUrl(var1_1.getTocUrl(), null, null, null, null, var1_1.getBookUrl(), (BaseSource)this.getBookSource(), (RuleDataInterface)var1_1, null, (Map)this.getBookSource().getHeaderMap(true), this.getDebugger(), 286, null);
        var13_11 = res = new Ref.ObjectRef();
        $continuation.L$0 = this;
        $continuation.L$1 = var1_1;
        $continuation.L$2 = analyzeUrl;
        $continuation.L$3 = res;
        $continuation.L$4 = var13_11;
        $continuation.label = 2;
        v1 = AnalyzeUrl.getStrResponseAwait$default((AnalyzeUrl)analyzeUrl, null, null, (boolean)false, (Continuation)$continuation, (int)7, null);
        if (v1 == var17_5) {
            return var17_5;
        }
        ** GOTO lbl50
        {
            case 2: {
                var13_11 = (Ref.ObjectRef)$continuation.L$4;
                res = (Ref.ObjectRef)$continuation.L$3;
                analyzeUrl = (AnalyzeUrl)$continuation.L$2;
                var1_1 = (Book)$continuation.L$1;
                this = (WebBook)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl50:
                // 2 sources

                var13_11.element = var14_12 = v1;
                var5_10 = this.getBookSource().getLoginCheckJs();
                if (var5_10 != null) {
                    var6_13 = var5_10;
                    var7_14 = false;
                    var8_15 = false;
                    checkJs = var6_13;
                    $i$a$-let-WebBook$getChapterList$2 = false;
                    var11_18 = checkJs;
                    var12_19 = false;
                    if (StringsKt.isBlank((CharSequence)var11_18) == false) {
                        var11_18 = analyzeUrl.evalJS(checkJs, res.element);
                        if (var11_18 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type io.legado.app.help.http.StrResponse");
                        }
                        res.element = (StrResponse)var11_18;
                    }
                }
                $continuation.L$0 = null;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.label = 3;
                v2 = BookChapterList.INSTANCE.analyzeChapterList(var1_1, ((StrResponse)res.element).getBody(), this.getBookSource(), var1_1.getTocUrl(), ((StrResponse)res.element).getUrl(), this.getDebugger(), (Continuation)$continuation);
                if (v2 == var17_5) {
                    return var17_5;
                }
                ** GOTO lbl80
            }
            case 3: {
                ResultKt.throwOnFailure((Object)$result);
                v2 = $result;
lbl80:
                // 2 sources

                return v2;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getBookContent(@NotNull Book var1_1, @NotNull BookChapter var2_2, @Nullable String var3_3, @NotNull Continuation<? super String> var4_4) {
        if (!(var4_4 instanceof getBookContent.1)) ** GOTO lbl-1000
        var9_5 = var4_4;
        if ((var9_5.label & -2147483648) != 0) {
            var9_5.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var10_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                book.setUserNameSpace(this.getUserNS());
                this.getBookSource().setUserNameSpace(this.getUserNS());
                this.getBookSource().setLogger(this.getDebugger());
                var5_8 = this.getBookSource().getContentRule().getContent();
                var6_9 = false;
                var7_11 = false;
                if (var5_8 == null || var5_8.length() == 0) {
                    var5_8 = this.getDebugger();
                    if (var5_8 != null) {
                        DebugLog.DefaultImpls.log$default((DebugLog)var5_8, (String)this.getBookSource().getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u21d2\u6b63\u6587\u89c4\u5219\u4e3a\u7a7a,\u4f7f\u7528\u7ae0\u8282\u94fe\u63a5: ", (Object)bookChapter.getUrl()), (boolean)false, (int)4, null);
                    }
                    return bookChapter.getUrl();
                }
                if (bookChapter.isVolume() && StringsKt.startsWith$default((String)bookChapter.getUrl(), (String)bookChapter.getTitle(), (boolean)false, (int)2, null)) {
                    var5_8 = this.getDebugger();
                    if (var5_8 != null) {
                        DebugLog.DefaultImpls.log$default((DebugLog)var5_8, (String)this.getBookSource().getBookSourceUrl(), (String)"\u21d2\u4e00\u7ea7\u76ee\u5f55\u6b63\u6587\u4e0d\u89e3\u6790\u89c4\u5219", (boolean)false, (int)4, null);
                    }
                    var5_8 = bookChapter.getTag();
                    return var5_8 == null ? "" : var5_8;
                }
                WebBookKt.access$getLogger$p().info("bookChapterUrl: {}", (Object)bookChapter.getUrl(), (Object)bookChapter.getAbsoluteURL());
                analyzeUrl = new AnalyzeUrl(bookChapter.getAbsoluteURL(), null, null, null, null, book.getTocUrl(), (BaseSource)this.getBookSource(), (RuleDataInterface)book, (BookChapter)bookChapter, (Map)this.getBookSource().getHeaderMap(true), this.getDebugger(), 30, null);
                $continuation.L$0 = this;
                $continuation.L$1 = book;
                $continuation.L$2 = bookChapter;
                $continuation.L$3 = nextChapterUrl;
                $continuation.label = 1;
                v0 = AnalyzeUrl.getStrResponseAwait$default((AnalyzeUrl)analyzeUrl, (String)this.getBookSource().getContentRule().getWebJs(), (String)this.getBookSource().getContentRule().getSourceRegex(), (boolean)false, (Continuation)$continuation, (int)4, null);
                if (v0 == var10_7) {
                    return var10_7;
                }
                ** GOTO lbl47
            }
            case 1: {
                var3_3 = (String)$continuation.L$3;
                var2_2 = (BookChapter)$continuation.L$2;
                var1_1 = (Book)$continuation.L$1;
                this = (WebBook)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl47:
                // 2 sources

                res = (StrResponse)v0;
                $continuation.L$0 = null;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.label = 2;
                v1 = BookContent.INSTANCE.analyzeContent(res.getBody(), var1_1, var2_2, this.getBookSource(), var2_2.getUrl(), res.getUrl(), var3_3, this.getDebugger(), (Continuation)$continuation);
                if (v1 == var10_7) {
                    return var10_7;
                }
                ** GOTO lbl60
            }
            case 2: {
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl60:
                // 2 sources

                return v1;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object getBookContent$default(WebBook webBook, Book book, BookChapter bookChapter, String string, Continuation continuation, int n, Object object) {
        if ((n & 4) != 0) {
            string = null;
        }
        return webBook.getBookContent(book, bookChapter, string, continuation);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    public final Object preciseSearch-0E7RQCE(@NotNull String var1_1, @NotNull String var2_2, @NotNull Continuation<? super Result<Book>> var3_3) {
        if (!(var3_3 instanceof preciseSearch.1)) ** GOTO lbl-1000
        var15_4 = var3_3;
        if ((var15_4.label & -2147483648) != 0) {
            var15_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var16_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                var4_7 = false;
                try {
                    var5_8 /* !! */  = Result.Companion;
                    $i$a$-runCatching-WebBook$preciseSearch$2 = false;
                    $continuation.L$0 = this;
                    $continuation.L$1 = name;
                    $continuation.L$2 = author;
                    $continuation.label = 1;
                    v0 = WebBook.searchBook$default((WebBook)this, (String)name, null, (Continuation)$continuation, (int)2, null);
                }
                catch (Throwable var6_11) {
                    var7_14 = Result.Companion;
                    var8_16 = false;
                    return Result.constructor-impl((Object)ResultKt.createFailure((Throwable)var6_11));
                }
                v1 = v0;
                if (v0 == var16_6) {
                    return var16_6;
                }
                ** GOTO lbl39
            }
            case 1: {
                $i$a$-runCatching-WebBook$preciseSearch$2 = false;
                author = (String)$continuation.L$2;
                name = (String)$continuation.L$1;
                this = (WebBook)$continuation.L$0;
                {
                    ResultKt.throwOnFailure((Object)$result);
                    v1 = $result;
lbl39:
                    // 2 sources

                    $this$firstOrNull$iv = (Iterable)v1;
                    $i$f$firstOrNull = false;
                    for (T element$iv : $this$firstOrNull$iv) {
                        it = (SearchBook)element$iv;
                        $i$a$-firstOrNull-WebBook$preciseSearch$2$1 = false;
                        if (!Boxing.boxBoolean((boolean)(Intrinsics.areEqual((Object)it.getName(), (Object)name) != false && Intrinsics.areEqual((Object)it.getAuthor(), (Object)author) != false)).booleanValue()) continue;
                        v2 = element$iv;
                        ** GOTO lbl48
                    }
                    v2 = null;
lbl48:
                    // 2 sources

                    var13_25 = v2;
                    if (var13_25 == null) {
                        throw new NoStackTraceException("\u672a\u641c\u7d22\u5230 " + name + '(' + author + ") \u4e66\u7c4d");
                    }
                    var7_12 = var13_25;
                    var8_15 = false;
                    var9_18 = false;
                    searchBook = var7_12;
                    $i$a$-let-WebBook$preciseSearch$2$2 = false;
                    book = searchBook.toBook();
                    if (!StringsKt.isBlank((CharSequence)book.getTocUrl())) ** GOTO lbl73
                    $continuation.L$0 = null;
                    $continuation.L$1 = null;
                    $continuation.L$2 = null;
                    $continuation.label = 2;
                    v3 = WebBook.getBookInfo$default((WebBook)this, (Book)book, (boolean)false, (Continuation)$continuation, (int)2, null);
                    ** if (v3 != var16_6) goto lbl65
                }
lbl64:
                // 1 sources

                return var16_6;
lbl65:
                // 1 sources

                ** GOTO lbl72
            }
            case 2: {
                $i$a$-runCatching-WebBook$preciseSearch$2 = false;
                $i$a$-let-WebBook$preciseSearch$2$2 = false;
                {
                    ResultKt.throwOnFailure((Object)$result);
                    v3 = $result;
lbl72:
                    // 2 sources

                    book = (Book)v3;
lbl73:
                    // 2 sources

                    var6_10 = book;
                    var7_13 = false;
                    return Result.constructor-impl((Object)var6_10);
                }
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}

