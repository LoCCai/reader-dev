/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookChapter
 *  io.legado.app.data.entities.BookSource
 *  io.legado.app.data.entities.SearchBook
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.DebugLog$DefaultImpls
 *  io.legado.app.model.Debugger
 *  io.legado.app.model.Debugger$contentDebug$1
 *  io.legado.app.model.Debugger$exploreDebug$1
 *  io.legado.app.model.Debugger$infoDebug$1
 *  io.legado.app.model.Debugger$searchDebug$1
 *  io.legado.app.model.Debugger$tocDebug$1
 *  io.legado.app.model.DebuggerKt
 *  io.legado.app.model.webBook.WebBook
 *  io.legado.app.utils.GsonExtensionsKt
 *  io.legado.app.utils.HtmlFormatter
 *  io.legado.app.utils.StringExtensionsKt
 *  kotlin.Metadata
 *  kotlin.Result
 *  kotlin.ResultKt
 *  kotlin.Unit
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.coroutines.jvm.internal.Boxing
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model;

import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.data.entities.BookSource;
import io.legado.app.data.entities.SearchBook;
import io.legado.app.model.DebugLog;
import io.legado.app.model.Debugger;
import io.legado.app.model.DebuggerKt;
import io.legado.app.model.webBook.WebBook;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.HtmlFormatter;
import io.legado.app.utils.StringExtensionsKt;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\u0002\u0010\u0006J3\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0004H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J!\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0004H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J!\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0004H\u0016J\u001a\u0010\u001b\u001a\u00020\u00052\b\u0010\u001d\u001a\u0004\u0018\u00010\u00042\b\u0010\u001e\u001a\u0004\u0018\u00010\u0004J$\u0010\u001b\u001a\u00020\u00052\b\u0010\u001d\u001a\u0004\u0018\u00010\u00042\b\u0010\u001e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001f\u001a\u00020 H\u0016J!\u0010!\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u0004H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J!\u0010#\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J!\u0010$\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006%"}, d2={"Lio/legado/app/model/Debugger;", "Lio/legado/app/model/DebugLog;", "logMsg", "Lkotlin/Function1;", "", "", "(Lkotlin/jvm/functions/Function1;)V", "debugTimeFormat", "Ljava/text/SimpleDateFormat;", "getLogMsg", "()Lkotlin/jvm/functions/Function1;", "startTime", "", "contentDebug", "webBook", "Lio/legado/app/model/webBook/WebBook;", "book", "Lio/legado/app/data/entities/Book;", "bookChapter", "Lio/legado/app/data/entities/BookChapter;", "nextChapterUrl", "(Lio/legado/app/model/webBook/WebBook;Lio/legado/app/data/entities/Book;Lio/legado/app/data/entities/BookChapter;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exploreDebug", "url", "(Lio/legado/app/model/webBook/WebBook;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "infoDebug", "(Lio/legado/app/model/webBook/WebBook;Lio/legado/app/data/entities/Book;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "log", "message", "sourceUrl", "msg", "isHtml", "", "searchDebug", "key", "startDebug", "tocDebug", "reader-pro"})
public final class Debugger
implements DebugLog {
    @NotNull
    private final Function1<String, Unit> logMsg;
    @NotNull
    private final SimpleDateFormat debugTimeFormat;
    private long startTime;

    public Debugger(@NotNull Function1<? super String, Unit> logMsg) {
        Intrinsics.checkNotNullParameter(logMsg, (String)"logMsg");
        this.logMsg = logMsg;
        this.debugTimeFormat = new SimpleDateFormat("[mm:ss.SSS]", Locale.getDefault());
        this.startTime = System.currentTimeMillis();
    }

    @NotNull
    public final Function1<String, Unit> getLogMsg() {
        return this.logMsg;
    }

    public final void log(@Nullable String sourceUrl, @Nullable String msg) {
        this.log(sourceUrl, msg, false);
    }

    public void log(@NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        String time = this.debugTimeFormat.format(new Date(System.currentTimeMillis() - this.startTime));
        this.logMsg.invoke((Object)(time + ' ' + message));
    }

    public void log(@Nullable String sourceUrl, @Nullable String msg, boolean isHtml) {
        if (sourceUrl == null || msg == null) {
            return;
        }
        DebuggerKt.access$getLogger$p().info("sourceUrl: {}, msg: {}", (Object)sourceUrl, (Object)msg);
        String printMsg = msg;
        if (isHtml) {
            printMsg = HtmlFormatter.format$default((HtmlFormatter)HtmlFormatter.INSTANCE, (String)msg, null, (int)2, null);
        }
        String time = this.debugTimeFormat.format(new Date(System.currentTimeMillis() - this.startTime));
        printMsg = time + ' ' + printMsg;
        this.logMsg.invoke((Object)printMsg);
    }

    @Nullable
    public final Object startDebug(@NotNull WebBook webBook, @NotNull String key, @NotNull Continuation<? super Unit> $completion) {
        BookSource bookSource = webBook.getBookSource();
        webBook.setDebugLogger((DebugLog)this);
        this.startTime = System.currentTimeMillis();
        if (StringExtensionsKt.isAbsUrl((String)key)) {
            Book book = new Book(null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0L, null, 0L, 0L, 0, 0, null, 0, 0, 0L, null, false, 0, 0, false, null, null, false, null, -1, 1, null);
            book.setOrigin(bookSource.getBookSourceUrl());
            book.setBookUrl(key);
            this.log(bookSource.getBookSourceUrl(), Intrinsics.stringPlus((String)"\u21d2\u5f00\u59cb\u8bbf\u95ee\u8be6\u60c5\u9875:", (Object)key));
            Object object = this.infoDebug(webBook, book, $completion);
            if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return object;
            }
            return Unit.INSTANCE;
        }
        if (StringsKt.contains$default((CharSequence)key, (CharSequence)"::", (boolean)false, (int)2, null)) {
            String url2 = StringsKt.substringAfter$default((String)key, (String)"::", null, (int)2, null);
            this.log(bookSource.getBookSourceUrl(), Intrinsics.stringPlus((String)"\u21d2\u5f00\u59cb\u8bbf\u95ee\u53d1\u73b0\u9875:", (Object)url2));
            Object object = this.exploreDebug(webBook, url2, $completion);
            if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return object;
            }
            return Unit.INSTANCE;
        }
        if (StringsKt.startsWith$default((String)key, (String)"++", (boolean)false, (int)2, null)) {
            String string = key;
            int n = 2;
            boolean bl = false;
            String string2 = string;
            if (string2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string2.substring(n);
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.String).substring(startIndex)");
            String url3 = string3;
            Book book = new Book(null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0L, null, 0L, 0L, 0, 0, null, 0, 0, 0L, null, false, 0, 0, false, null, null, false, null, -1, 1, null);
            book.setOrigin(bookSource.getBookSourceUrl());
            book.setTocUrl(url3);
            this.log(bookSource.getBookSourceUrl(), Intrinsics.stringPlus((String)"\u21d2\u5f00\u59cb\u8bbf\u76ee\u5f55\u9875:", (Object)url3));
            Object object = this.tocDebug(webBook, book, $completion);
            if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return object;
            }
            return Unit.INSTANCE;
        }
        if (StringsKt.startsWith$default((String)key, (String)"--", (boolean)false, (int)2, null)) {
            String book = key;
            int n = 2;
            boolean bl = false;
            String string = book;
            if (string == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string4 = string.substring(n);
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"(this as java.lang.String).substring(startIndex)");
            String url4 = string4;
            book = new Book(null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0L, null, 0L, 0L, 0, 0, null, 0, 0, 0L, null, false, 0, 0, false, null, null, false, null, -1, 1, null);
            book.setOrigin(bookSource.getBookSourceUrl());
            this.log(bookSource.getBookSourceUrl(), Intrinsics.stringPlus((String)"\u21d2\u5f00\u59cb\u8bbf\u6b63\u6587\u9875:", (Object)url4));
            BookChapter chapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
            chapter.setTitle("\u8c03\u8bd5");
            chapter.setUrl(url4);
            Object object = this.contentDebug(webBook, (Book)book, chapter, null, $completion);
            if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return object;
            }
            return Unit.INSTANCE;
        }
        this.log(bookSource.getBookSourceUrl(), Intrinsics.stringPlus((String)"\u21d2\u5f00\u59cb\u641c\u7d22\u5173\u952e\u5b57:", (Object)key));
        Object object = this.searchDebug(webBook, key, $completion);
        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return object;
        }
        return Unit.INSTANCE;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final Object exploreDebug(WebBook var1_1, String var2_2, Continuation<? super Unit> var3_3) {
        block14: {
            if (!(var3_3 instanceof exploreDebug.1)) ** GOTO lbl-1000
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
                    webBook.setDebugLogger((DebugLog)this);
                    this.log("\ufe3e\u5f00\u59cb\u89e3\u6790\u53d1\u73b0\u9875");
                    var4_7 = this;
                    var5_8 = false;
                    var6_9 /* !! */  = Result.Companion;
                    var7_12 = (Debugger)var4_7;
                    $i$a$-runCatching-Debugger$exploreDebug$2 = false;
                    $continuation.L$0 = this;
                    $continuation.L$1 = webBook;
                    $continuation.label = 1;
                    v0 = webBook.exploreBook((String)url, Boxing.boxInt((int)1), (Continuation)$continuation);
                    ** if (v0 != var18_6) goto lbl27
lbl26:
                    // 1 sources

                    return var18_6;
lbl27:
                    // 1 sources

                    ** GOTO lbl36
                }
                case 1: {
                    $i$a$-runCatching-Debugger$exploreDebug$2 = false;
                    webBook = (WebBook)$continuation.L$1;
                    this = (Debugger)$continuation.L$0;
                    try {
                        ResultKt.throwOnFailure((Object)$result);
                        v0 = $result;
lbl36:
                        // 2 sources

                        var7_12 = (List)v0;
                        $i$a$-runCatching-Debugger$exploreDebug$2 = false;
                        var6_9 /* !! */  = Result.constructor-impl((Object)var7_12);
                    }
                    catch (Throwable var7_13) {
                        $i$a$-runCatching-Debugger$exploreDebug$2 = Result.Companion;
                        var9_20 = false;
                        var6_9 /* !! */  = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)var7_13));
                    }
                    var4_7 = var6_9 /* !! */ ;
                    var5_8 = false;
                    var6_10 = false;
                    if (Result.isSuccess-impl((Object)var4_7)) {
                        exploreBooks = (List)var4_7;
                        $i$a$-onSuccess-Debugger$exploreDebug$3 = false;
                        var9_21 = exploreBooks;
                        var10_23 = false;
                        var11_24 = false;
                        it = var9_21;
                        $i$a$-let-Debugger$exploreDebug$3$1 = false;
                        var14_30 = exploreBooks;
                        var15_31 = false;
                        if (!(var14_30.isEmpty() == false)) break;
                        this.log("\u250c\u53d1\u73b0\u7ed3\u679c\u5217\u8868");
                        this.log(Intrinsics.stringPlus((String)"\u2514", (Object)GsonExtensionsKt.getGSON().toJson((Object)exploreBooks)));
                        this.log(webBook.getSourceUrl(), "\ufe3d\u53d1\u73b0\u9875\u89e3\u6790\u5b8c\u6210\n\n");
                        $continuation.L$0 = this;
                        $continuation.L$1 = webBook;
                        $continuation.L$2 = var4_7;
                        $continuation.label = 2;
                        v1 = this.infoDebug(webBook, ((SearchBook)exploreBooks.get(0)).toBook(), (Continuation)$continuation);
                        if (v1 == var18_6) {
                            return var18_6;
                        }
                    }
                    break block14;
                }
                case 2: {
                    $i$a$-onSuccess-Debugger$exploreDebug$3 = false;
                    $i$a$-let-Debugger$exploreDebug$3$1 = false;
                    var4_7 = $continuation.L$2;
                    var1_1 = (WebBook)$continuation.L$1;
                    this = (Debugger)$continuation.L$0;
                    ResultKt.throwOnFailure((Object)$result);
                    v1 = $result;
                    break block14;
                }
            }
            this.log(var1_1.getSourceUrl(), "\ufe3d\u672a\u83b7\u53d6\u5230\u4e66\u7c4d");
        }
        var5_8 = false;
        var6_10 = false;
        v2 = Result.exceptionOrNull-impl((Object)var4_7);
        if (v2 != null) {
            var6_11 = v2;
            var7_14 = false;
            var8_19 = false;
            var9_22 = var6_11;
            var10_23 = false;
            it = var9_22;
            $i$a$-onFailure-Debugger$exploreDebug$4 = false;
            this.log(var1_1.getSourceUrl(), Intrinsics.stringPlus((String)"Error: ", (Object)it.getLocalizedMessage()));
            throw it;
        }
        return Unit.INSTANCE;
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    private final Object searchDebug(WebBook var1_1, String var2_2, Continuation<? super Unit> var3_3) {
        block14: {
            if (!(var3_3 instanceof searchDebug.1)) ** GOTO lbl-1000
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
                    webBook.setDebugLogger((DebugLog)this);
                    DebugLog.DefaultImpls.log$default((DebugLog)((DebugLog)this), null, (String)"\ufe3e\u5f00\u59cb\u89e3\u6790\u641c\u7d22\u9875", (boolean)false, (int)5, null);
                    var4_7 = this;
                    var5_8 = false;
                    var6_9 = Result.Companion;
                    var7_12 = (Debugger)var4_7;
                    $i$a$-runCatching-Debugger$searchDebug$2 = false;
                    $continuation.L$0 = this;
                    $continuation.L$1 = webBook;
                    $continuation.label = 1;
                    v0 = webBook.searchBook((String)key, Boxing.boxInt((int)1), (Continuation)$continuation);
                    ** if (v0 != var18_6) goto lbl27
lbl26:
                    // 1 sources

                    return var18_6;
lbl27:
                    // 1 sources

                    ** GOTO lbl36
                }
                case 1: {
                    $i$a$-runCatching-Debugger$searchDebug$2 = false;
                    webBook = (WebBook)$continuation.L$1;
                    this = (Debugger)$continuation.L$0;
                    try {
                        ResultKt.throwOnFailure((Object)$result);
                        v0 = $result;
lbl36:
                        // 2 sources

                        var7_12 = (List)v0;
                        $i$a$-runCatching-Debugger$searchDebug$2 = false;
                        var6_9 = Result.constructor-impl((Object)var7_12);
                    }
                    catch (Throwable var7_13) {
                        $i$a$-runCatching-Debugger$searchDebug$2 = Result.Companion;
                        var9_20 = false;
                        var6_9 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)var7_13));
                    }
                    var4_7 = var6_9;
                    var5_8 = false;
                    var6_10 = false;
                    if (Result.isSuccess-impl((Object)var4_7)) {
                        searchBooks = (List)var4_7;
                        $i$a$-onSuccess-Debugger$searchDebug$3 = false;
                        var9_21 = searchBooks;
                        var10_23 = false;
                        var11_24 = false;
                        it = var9_21;
                        $i$a$-let-Debugger$searchDebug$3$1 = false;
                        var14_30 = searchBooks;
                        var15_31 = false;
                        if (!(var14_30.isEmpty() == false)) break;
                        this.log("\u250c\u641c\u7d22\u7ed3\u679c\u5217\u8868");
                        this.log(Intrinsics.stringPlus((String)"\u2514", (Object)GsonExtensionsKt.getGSON().toJson((Object)searchBooks)));
                        this.log(webBook.getSourceUrl(), "\ufe3d\u641c\u7d22\u9875\u89e3\u6790\u5b8c\u6210\n\n");
                        $continuation.L$0 = this;
                        $continuation.L$1 = webBook;
                        $continuation.L$2 = var4_7;
                        $continuation.label = 2;
                        v1 = this.infoDebug(webBook, ((SearchBook)searchBooks.get(0)).toBook(), (Continuation)$continuation);
                        if (v1 == var18_6) {
                            return var18_6;
                        }
                    }
                    break block14;
                }
                case 2: {
                    $i$a$-onSuccess-Debugger$searchDebug$3 = false;
                    $i$a$-let-Debugger$searchDebug$3$1 = false;
                    var4_7 = $continuation.L$2;
                    var1_1 = (WebBook)$continuation.L$1;
                    this = (Debugger)$continuation.L$0;
                    ResultKt.throwOnFailure((Object)$result);
                    v1 = $result;
                    break block14;
                }
            }
            this.log(var1_1.getSourceUrl(), "\ufe3d\u672a\u83b7\u53d6\u5230\u4e66\u7c4d");
        }
        var5_8 = false;
        var6_10 = false;
        v2 = Result.exceptionOrNull-impl((Object)var4_7);
        if (v2 != null) {
            var6_11 = v2;
            var7_14 = false;
            var8_19 = false;
            var9_22 = var6_11;
            var10_23 = false;
            it = var9_22;
            $i$a$-onFailure-Debugger$searchDebug$4 = false;
            this.log(var1_1.getSourceUrl(), Intrinsics.stringPlus((String)"Error: ", (Object)it.getLocalizedMessage()));
            throw it;
        }
        return Unit.INSTANCE;
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    private final Object infoDebug(WebBook var1_1, Book var2_2, Continuation<? super Unit> var3_3) {
        if (!(var3_3 instanceof infoDebug.1)) ** GOTO lbl-1000
        var14_4 = var3_3;
        if ((var14_4.label & -2147483648) != 0) {
            var14_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var15_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                webBook.setDebugLogger((DebugLog)this);
                DebugLog.DefaultImpls.log$default((DebugLog)((DebugLog)this), null, (String)"\ufe3e\u5f00\u59cb\u89e3\u6790\u8be6\u60c5\u9875", (boolean)false, (int)5, null);
                var4_7 = this;
                var5_8 = false;
                var6_9 = Result.Companion;
                var7_12 = (Debugger)var4_7;
                $i$a$-runCatching-Debugger$infoDebug$2 = false;
                $continuation.L$0 = this;
                $continuation.L$1 = webBook;
                $continuation.label = 1;
                v0 = WebBook.getBookInfo$default((WebBook)webBook, (Book)book, (boolean)false, (Continuation)$continuation, (int)2, null);
                ** if (v0 != var15_6) goto lbl27
lbl26:
                // 1 sources

                return var15_6;
lbl27:
                // 1 sources

                ** GOTO lbl36
            }
            case 1: {
                $i$a$-runCatching-Debugger$infoDebug$2 = false;
                webBook = (WebBook)$continuation.L$1;
                this = (Debugger)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure((Object)$result);
                    v0 = $result;
lbl36:
                    // 2 sources

                    var7_12 = (Book)v0;
                    $i$a$-runCatching-Debugger$infoDebug$2 = false;
                    var6_9 = Result.constructor-impl((Object)var7_12);
                }
                catch (Throwable var7_13) {
                    $i$a$-runCatching-Debugger$infoDebug$2 = Result.Companion;
                    var9_20 = false;
                    var6_9 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)var7_13));
                }
                var4_7 = var6_9;
                var5_8 = false;
                var6_10 = false;
                if (Result.isSuccess-impl((Object)var4_7)) {
                    it = (Book)var4_7;
                    $i$a$-onSuccess-Debugger$infoDebug$3 = false;
                    this.log("\u250c\u4e66\u7c4d\u8be6\u60c5");
                    this.log(Intrinsics.stringPlus((String)"\u2514", (Object)GsonExtensionsKt.getGSON().toJson((Object)it)));
                    this.log(webBook.getSourceUrl(), "\ufe3d\u8be6\u60c5\u9875\u89e3\u6790\u5b8c\u6210\n\n");
                    $continuation.L$0 = this;
                    $continuation.L$1 = webBook;
                    $continuation.L$2 = var4_7;
                    $continuation.label = 2;
                    v1 = this.tocDebug(webBook, it, (Continuation)$continuation);
                    if (v1 == var15_6) {
                        return var15_6;
                    }
                }
                ** GOTO lbl68
            }
            case 2: {
                $i$a$-onSuccess-Debugger$infoDebug$3 = false;
                var4_7 = $continuation.L$2;
                var1_1 = (WebBook)$continuation.L$1;
                this = (Debugger)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl68:
                // 2 sources

                var5_8 = false;
                var6_10 = false;
                v2 = Result.exceptionOrNull-impl((Object)var4_7);
                if (v2 != null) {
                    var6_11 = v2;
                    var7_14 = false;
                    var8_19 = false;
                    var9_21 = var6_11;
                    var10_22 = false;
                    it = var9_21;
                    $i$a$-onFailure-Debugger$infoDebug$4 = false;
                    this.log(var1_1.getSourceUrl(), Intrinsics.stringPlus((String)"Error: ", (Object)it.getLocalizedMessage()));
                    throw it;
                }
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final Object tocDebug(WebBook var1_1, Book var2_2, Continuation<? super Unit> var3_3) {
        block15: {
            if (!(var3_3 instanceof tocDebug.1)) ** GOTO lbl-1000
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
                    webBook.setDebugLogger((DebugLog)this);
                    DebugLog.DefaultImpls.log$default((DebugLog)((DebugLog)this), null, (String)"\ufe3e\u5f00\u59cb\u89e3\u6790\u76ee\u5f55\u9875", (boolean)false, (int)5, null);
                    var4_7 = this;
                    var5_8 = false;
                    var6_9 /* !! */  = Result.Companion;
                    var7_12 = (Debugger)var4_7;
                    $i$a$-runCatching-Debugger$tocDebug$2 = false;
                    $continuation.L$0 = this;
                    $continuation.L$1 = webBook;
                    $continuation.L$2 = book;
                    $continuation.label = 1;
                    v0 = webBook.getChapterList(book, (Continuation)$continuation);
                    ** if (v0 != var19_6) goto lbl28
lbl27:
                    // 1 sources

                    return var19_6;
lbl28:
                    // 1 sources

                    ** GOTO lbl38
                }
                case 1: {
                    $i$a$-runCatching-Debugger$tocDebug$2 = false;
                    book = (Book)$continuation.L$2;
                    webBook = (WebBook)$continuation.L$1;
                    this = (Debugger)$continuation.L$0;
                    try {
                        ResultKt.throwOnFailure((Object)$result);
                        v0 = $result;
lbl38:
                        // 2 sources

                        var7_12 = (List)v0;
                        $i$a$-runCatching-Debugger$tocDebug$2 = false;
                        var6_9 /* !! */  = Result.constructor-impl((Object)var7_12);
                    }
                    catch (Throwable var7_13) {
                        $i$a$-runCatching-Debugger$tocDebug$2 = Result.Companion;
                        var9_20 = false;
                        var6_9 /* !! */  = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)var7_13));
                    }
                    var4_7 = var6_9 /* !! */ ;
                    var5_8 = false;
                    var6_10 = false;
                    if (Result.isSuccess-impl((Object)var4_7)) {
                        chapterList = (List)var4_7;
                        $i$a$-onSuccess-Debugger$tocDebug$3 = false;
                        var9_21 = chapterList;
                        if (var9_21 != null) {
                            var10_23 = var9_21;
                            var11_25 = false;
                            var12_27 = false;
                            it = var10_23;
                            $i$a$-let-Debugger$tocDebug$3$1 = false;
                            var15_31 = it;
                            var16_32 = false;
                            if (!(var15_31.isEmpty() == false)) break;
                            this.log("\u250c\u76ee\u5f55\u5217\u8868");
                            this.log(Intrinsics.stringPlus((String)"\u2514", (Object)GsonExtensionsKt.getGSON().toJson((Object)it)));
                            this.log(webBook.getSourceUrl(), "\ufe3d\u76ee\u5f55\u9875\u89e3\u6790\u5b8c\u6210\n\n");
                            nextChapterUrl = it.size() > 1 ? ((BookChapter)it.get(1)).getUrl() : null;
                            $continuation.L$0 = this;
                            $continuation.L$1 = webBook;
                            $continuation.L$2 = var4_7;
                            $continuation.label = 2;
                            v1 = this.contentDebug(webBook, book, (BookChapter)it.get(0), nextChapterUrl, (Continuation)$continuation);
                            if (v1 == var19_6) {
                                return var19_6;
                            }
                        }
                    }
                    break block15;
                }
                case 2: {
                    $i$a$-onSuccess-Debugger$tocDebug$3 = false;
                    $i$a$-let-Debugger$tocDebug$3$1 = false;
                    var4_7 = $continuation.L$2;
                    var1_1 = (WebBook)$continuation.L$1;
                    this = (Debugger)$continuation.L$0;
                    ResultKt.throwOnFailure((Object)$result);
                    v1 = $result;
                    break block15;
                }
            }
            this.log(var1_1.getSourceUrl(), "\ufe3d\u76ee\u5f55\u5217\u8868\u4e3a\u7a7a");
        }
        var5_8 = false;
        var6_10 = false;
        v2 = Result.exceptionOrNull-impl((Object)var4_7);
        if (v2 != null) {
            var6_11 = v2;
            var7_14 = false;
            var8_19 = false;
            var9_22 = var6_11;
            var10_24 = false;
            it = var9_22;
            $i$a$-onFailure-Debugger$tocDebug$4 = false;
            this.log(var1_1.getSourceUrl(), Intrinsics.stringPlus((String)"Error: ", (Object)it.getLocalizedMessage()));
            throw it;
        }
        return Unit.INSTANCE;
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final Object contentDebug(WebBook var1_1, Book var2_2, BookChapter var3_3, String var4_4, Continuation<? super Unit> var5_5) {
        if (!(var5_5 instanceof contentDebug.1)) ** GOTO lbl-1000
        var16_6 = var5_5;
        if ((var16_6.label & -2147483648) != 0) {
            var16_6.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var17_8 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                webBook.setDebugLogger((DebugLog)this);
                this.log(webBook.getSourceUrl(), "\ufe3e\u5f00\u59cb\u89e3\u6790\u6b63\u6587\u9875");
                var6_9 = this;
                var7_10 = false;
                var8_11 /* !! */  = Result.Companion;
                var9_14 = var6_9;
                $i$a$-runCatching-Debugger$contentDebug$2 = false;
                $continuation.L$0 = this;
                $continuation.L$1 = webBook;
                $continuation.label = 1;
                v0 = webBook.getBookContent((Book)book, (BookChapter)bookChapter, (String)nextChapterUrl, (Continuation)$continuation);
                ** if (v0 != var17_8) goto lbl27
lbl26:
                // 1 sources

                return var17_8;
lbl27:
                // 1 sources

                ** GOTO lbl36
            }
            case 1: {
                $i$a$-runCatching-Debugger$contentDebug$2 = false;
                webBook = (WebBook)$continuation.L$1;
                this = (Debugger)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure((Object)$result);
                    v0 = $result;
lbl36:
                    // 2 sources

                    var9_14 = (String)v0;
                    $i$a$-runCatching-Debugger$contentDebug$2 = false;
                    var8_11 /* !! */  = Result.constructor-impl((Object)var9_14);
                }
                catch (Throwable var9_15) {
                    $i$a$-runCatching-Debugger$contentDebug$2 = Result.Companion;
                    var11_21 = false;
                    var8_11 /* !! */  = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)var9_15));
                }
                var6_9 = var8_11 /* !! */ ;
                var7_10 = false;
                var8_12 = false;
                if (Result.isSuccess-impl((Object)var6_9)) {
                    it = (String)var6_9;
                    $i$a$-onSuccess-Debugger$contentDebug$3 = false;
                    this.log("\u250c\u6b63\u6587\u5185\u5bb9");
                    this.log(Intrinsics.stringPlus((String)"\u2514", (Object)it));
                    this.log(webBook.getSourceUrl(), "\ufe3d\u6b63\u6587\u9875\u89e3\u6790\u5b8c\u6210");
                }
                var7_10 = false;
                var8_12 = false;
                v1 = Result.exceptionOrNull-impl((Object)var6_9);
                if (v1 != null) {
                    var8_13 = v1;
                    var9_16 = false;
                    var10_20 = false;
                    var11_22 = var8_13;
                    var12_23 = false;
                    it = var11_22;
                    $i$a$-onFailure-Debugger$contentDebug$4 = false;
                    this.log(webBook.getSourceUrl(), Intrinsics.stringPlus((String)"Error: ", (Object)it.getLocalizedMessage()));
                }
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static final /* synthetic */ Object access$exploreDebug(Debugger $this, WebBook webBook, String url2, Continuation $completion) {
        return $this.exploreDebug(webBook, url2, $completion);
    }

    public static final /* synthetic */ Object access$searchDebug(Debugger $this, WebBook webBook, String key, Continuation $completion) {
        return $this.searchDebug(webBook, key, $completion);
    }

    public static final /* synthetic */ Object access$infoDebug(Debugger $this, WebBook webBook, Book book, Continuation $completion) {
        return $this.infoDebug(webBook, book, $completion);
    }

    public static final /* synthetic */ Object access$tocDebug(Debugger $this, WebBook webBook, Book book, Continuation $completion) {
        return $this.tocDebug(webBook, book, $completion);
    }

    public static final /* synthetic */ Object access$contentDebug(Debugger $this, WebBook webBook, Book book, BookChapter bookChapter, String nextChapterUrl, Continuation $completion) {
        return $this.contentDebug(webBook, book, bookChapter, nextChapterUrl, $completion);
    }
}

