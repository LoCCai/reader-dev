/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.BaseSource
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookSource
 *  io.legado.app.help.BookHelp
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.DebugLog$DefaultImpls
 *  io.legado.app.model.analyzeRule.AnalyzeRule
 *  io.legado.app.model.analyzeRule.RuleDataInterface
 *  io.legado.app.model.webBook.BookInfo
 *  io.legado.app.utils.NetworkUtils
 *  io.legado.app.utils.StringExtensionsKt
 *  io.legado.app.utils.StringUtils
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.CoroutineContext
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  kotlinx.coroutines.JobKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model.webBook;

import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookSource;
import io.legado.app.help.BookHelp;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeRule;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.utils.NetworkUtils;
import io.legado.app.utils.StringExtensionsKt;
import io.legado.app.utils.StringUtils;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.JobKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JO\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011JW\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0015"}, d2={"Lio/legado/app/model/webBook/BookInfo;", "", "()V", "analyzeBookInfo", "", "book", "Lio/legado/app/data/entities/Book;", "body", "", "bookSource", "Lio/legado/app/data/entities/BookSource;", "baseUrl", "redirectUrl", "canReName", "", "debugLog", "Lio/legado/app/model/DebugLog;", "(Lio/legado/app/data/entities/Book;Ljava/lang/String;Lio/legado/app/data/entities/BookSource;Ljava/lang/String;Ljava/lang/String;ZLio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "analyzeRule", "Lio/legado/app/model/analyzeRule/AnalyzeRule;", "(Lio/legado/app/data/entities/Book;Ljava/lang/String;Lio/legado/app/model/analyzeRule/AnalyzeRule;Lio/legado/app/data/entities/BookSource;Ljava/lang/String;Ljava/lang/String;ZLio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reader-pro"})
public final class BookInfo {
    @NotNull
    public static final BookInfo INSTANCE = new BookInfo();

    private BookInfo() {
    }

    @Nullable
    public final Object analyzeBookInfo(@NotNull Book book, @Nullable String body, @NotNull BookSource bookSource, @NotNull String baseUrl, @NotNull String redirectUrl, boolean canReName, @Nullable DebugLog debugLog, @NotNull Continuation<? super Unit> $completion) throws Exception {
        String string = body;
        if (string == null) {
            throw new Exception(Intrinsics.stringPlus((String)"error_get_web_content: ", (Object)baseUrl));
        }
        string = debugLog;
        if (string != null) {
            DebugLog.DefaultImpls.log$default((DebugLog)string, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2261\u83b7\u53d6\u6210\u529f:", (Object)baseUrl), (boolean)false, (int)4, null);
        }
        AnalyzeRule analyzeRule = new AnalyzeRule((RuleDataInterface)book, (BaseSource)bookSource, debugLog);
        AnalyzeRule.setContent$default((AnalyzeRule)analyzeRule, (Object)body, null, (int)2, null).setBaseUrl(baseUrl);
        analyzeRule.setRedirectUrl(redirectUrl);
        Object object = this.analyzeBookInfo(book, body, analyzeRule, bookSource, baseUrl, redirectUrl, canReName, debugLog, $completion);
        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return object;
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ Object analyzeBookInfo$default(BookInfo bookInfo, Book book, String string, BookSource bookSource, String string2, String string3, boolean bl, DebugLog debugLog, Continuation continuation, int n, Object object) throws Exception {
        if ((n & 0x40) != 0) {
            debugLog = null;
        }
        return bookInfo.analyzeBookInfo(book, string, bookSource, string2, string3, bl, debugLog, continuation);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object analyzeBookInfo(@NotNull Book book, @Nullable String body, @NotNull AnalyzeRule analyzeRule, @NotNull BookSource bookSource, @NotNull String baseUrl, @NotNull String redirectUrl, boolean canReName, @Nullable DebugLog debugLog, @NotNull Continuation<? super Unit> $completion) throws Exception {
        block77: {
            block78: {
                block75: {
                    block76: {
                        var10_10 = body;
                        if (var10_10 == null) {
                            throw new Exception(Intrinsics.stringPlus((String)"error_get_web_content: ", (Object)baseUrl));
                        }
                        infoRule = bookSource.getBookInfoRule();
                        var11_11 = infoRule.getInit();
                        if (var11_11 == null) {
                            v0 = null;
                        } else {
                            var12_13 = var11_11;
                            var13_19 = false;
                            var14_31 = false;
                            it = var12_13;
                            $i$a$-let-BookInfo$analyzeBookInfo$3 = false;
                            var17_44 = it;
                            var18_45 = false;
                            if (var17_44.length() > 0) {
                                JobKt.ensureActive((CoroutineContext)$completion.getContext());
                                var17_44 = debugLog;
                                if (var17_44 == null) {
                                    v1 = null;
                                } else {
                                    DebugLog.DefaultImpls.log$default((DebugLog)var17_44, (String)bookSource.getBookSourceUrl(), (String)"\u2261\u6267\u884c\u8be6\u60c5\u9875\u521d\u59cb\u5316\u89c4\u5219", (boolean)false, (int)4, null);
                                    v1 = Unit.INSTANCE;
                                }
                                AnalyzeRule.setContent$default((AnalyzeRule)analyzeRule, (Object)analyzeRule.getElement(it), null, (int)2, null);
                            }
                            v0 = Unit.INSTANCE;
                        }
                        if (!canReName) ** GOTO lbl-1000
                        var12_13 = infoRule.getCanReName();
                        var13_19 = false;
                        var14_31 = false;
                        if (!(var12_13 == null || StringsKt.isBlank((CharSequence)var12_13) != false)) {
                            v2 = true;
                        } else lbl-1000:
                        // 2 sources

                        {
                            v2 = false;
                        }
                        mCanReName = v2;
                        JobKt.ensureActive((CoroutineContext)$completion.getContext());
                        var12_13 = debugLog;
                        if (var12_13 == null) {
                            v3 = null;
                        } else {
                            DebugLog.DefaultImpls.log$default((DebugLog)var12_13, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u4e66\u540d", (boolean)false, (int)4, null);
                            v3 = Unit.INSTANCE;
                        }
                        var12_13 = BookHelp.INSTANCE.formatBookName(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (String)infoRule.getName(), null, (boolean)false, (int)6, null));
                        var13_19 = false;
                        var14_31 = false;
                        it = var12_13;
                        $i$a$-let-BookInfo$analyzeBookInfo$4 = false;
                        var17_44 = it;
                        var18_45 = false;
                        if (!(var17_44.length() > 0)) break block75;
                        if (mCanReName) break block76;
                        var17_44 = book.getName();
                        var18_45 = false;
                        if (!(var17_44.length() == 0)) break block75;
                    }
                    book.setName(it);
                }
                if ((var17_44 = debugLog) == null) {
                    v4 = null;
                } else {
                    DebugLog.DefaultImpls.log$default((DebugLog)var17_44, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)it), (boolean)false, (int)4, null);
                    v4 = Unit.INSTANCE;
                }
                JobKt.ensureActive((CoroutineContext)$completion.getContext());
                var12_13 = debugLog;
                if (var12_13 == null) {
                    v5 = null;
                } else {
                    DebugLog.DefaultImpls.log$default((DebugLog)var12_13, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u4f5c\u8005", (boolean)false, (int)4, null);
                    v5 = Unit.INSTANCE;
                }
                var12_13 = BookHelp.INSTANCE.formatBookAuthor(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (String)infoRule.getAuthor(), null, (boolean)false, (int)6, null));
                var13_19 = false;
                var14_31 = false;
                it = var12_13;
                $i$a$-let-BookInfo$analyzeBookInfo$5 = false;
                var17_44 = it;
                var18_45 = false;
                if (!(var17_44.length() > 0)) break block77;
                if (mCanReName) break block78;
                var17_44 = book.getAuthor();
                var18_45 = false;
                if (!(var17_44.length() == 0)) break block77;
            }
            book.setAuthor(it);
        }
        if ((var17_44 = debugLog) == null) {
            v6 = null;
        } else {
            DebugLog.DefaultImpls.log$default((DebugLog)var17_44, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)it), (boolean)false, (int)4, null);
            v6 = Unit.INSTANCE;
        }
        JobKt.ensureActive((CoroutineContext)$completion.getContext());
        var12_13 = debugLog;
        if (var12_13 == null) {
            v7 = null;
        } else {
            DebugLog.DefaultImpls.log$default((DebugLog)var12_13, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u5206\u7c7b", (boolean)false, (int)4, null);
            v7 = Unit.INSTANCE;
        }
        try {
            var12_13 = AnalyzeRule.getStringList$default((AnalyzeRule)analyzeRule, (String)infoRule.getKind(), null, (boolean)false, (int)6, null);
            if (var12_13 == null) {
                v8 = null;
            } else {
                var13_20 = CollectionsKt.joinToString$default((Iterable)((Iterable)var12_13), (CharSequence)",", null, null, (int)0, null, null, (int)62, null);
                if (var13_20 == null) {
                    v8 = null;
                } else {
                    var14_32 = var13_20;
                    it = false;
                    $i$a$-let-BookInfo$analyzeBookInfo$5 = false;
                    it = var14_32;
                    $i$a$-let-BookInfo$analyzeBookInfo$6 = false;
                    var19_46 = it;
                    var20_47 = false;
                    if (var19_46.length() > 0) {
                        book.setKind(it);
                    }
                    v8 = Unit.INSTANCE;
                }
            }
            var12_13 = debugLog;
            if (var12_13 == null) {
                v9 = null;
            } else {
                DebugLog.DefaultImpls.log$default((DebugLog)var12_13, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)book.getKind()), (boolean)false, (int)4, null);
                v9 = Unit.INSTANCE;
            }
        }
        catch (Exception e) {
            var13_21 = debugLog;
            if (var13_21 == null) {
                v10 = null;
            }
            DebugLog.DefaultImpls.log$default((DebugLog)var13_21, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)e.getLocalizedMessage()), (boolean)false, (int)4, null);
            v10 = Unit.INSTANCE;
        }
        JobKt.ensureActive((CoroutineContext)$completion.getContext());
        e /* !! */  = debugLog;
        if (e /* !! */  == null) {
            v11 = null;
        } else {
            DebugLog.DefaultImpls.log$default((DebugLog)e /* !! */ , (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u5b57\u6570", (boolean)false, (int)4, null);
            v11 = Unit.INSTANCE;
        }
        try {
            e /* !! */  = StringUtils.INSTANCE.wordCountFormat(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (String)infoRule.getWordCount(), null, (boolean)false, (int)6, null));
            var13_22 = false;
            var14_33 = false;
            it /* !! */  = e /* !! */ ;
            $i$a$-let-BookInfo$analyzeBookInfo$7 = false;
            var17_44 = (CharSequence)it /* !! */ ;
            var18_45 = false;
            if (var17_44.length() > 0) {
                book.setWordCount((String)it /* !! */ );
            }
            e /* !! */  = debugLog;
            if (e /* !! */  == null) {
                v12 = null;
            } else {
                DebugLog.DefaultImpls.log$default((DebugLog)e /* !! */ , (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)book.getWordCount()), (boolean)false, (int)4, null);
                v12 = Unit.INSTANCE;
            }
        }
        catch (Exception e) {
            var13_23 = debugLog;
            if (var13_23 == null) {
                v13 = null;
            }
            DebugLog.DefaultImpls.log$default((DebugLog)var13_23, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)e.getLocalizedMessage()), (boolean)false, (int)4, null);
            v13 = Unit.INSTANCE;
        }
        JobKt.ensureActive((CoroutineContext)$completion.getContext());
        e /* !! */  = debugLog;
        if (e /* !! */  == null) {
            v14 = null;
        } else {
            DebugLog.DefaultImpls.log$default((DebugLog)e /* !! */ , (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u6700\u65b0\u7ae0\u8282", (boolean)false, (int)4, null);
            v14 = Unit.INSTANCE;
        }
        try {
            e /* !! */  = AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (String)infoRule.getLastChapter(), null, (boolean)false, (int)6, null);
            var13_24 = false;
            var14_34 = false;
            it /* !! */  = e /* !! */ ;
            $i$a$-let-BookInfo$analyzeBookInfo$8 = false;
            var17_44 = (CharSequence)it /* !! */ ;
            var18_45 = false;
            if (var17_44.length() > 0) {
                book.setLatestChapterTitle((String)it /* !! */ );
            }
            e /* !! */  = debugLog;
            if (e /* !! */  == null) {
                v15 = null;
            } else {
                DebugLog.DefaultImpls.log$default((DebugLog)e /* !! */ , (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)book.getLatestChapterTitle()), (boolean)false, (int)4, null);
                v15 = Unit.INSTANCE;
            }
        }
        catch (Exception e) {
            var13_25 = debugLog;
            if (var13_25 == null) {
                v16 = null;
            }
            DebugLog.DefaultImpls.log$default((DebugLog)var13_25, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)e.getLocalizedMessage()), (boolean)false, (int)4, null);
            v16 = Unit.INSTANCE;
        }
        JobKt.ensureActive((CoroutineContext)$completion.getContext());
        e /* !! */  = debugLog;
        if (e /* !! */  == null) {
            v17 = null;
        } else {
            DebugLog.DefaultImpls.log$default((DebugLog)e /* !! */ , (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u7b80\u4ecb", (boolean)false, (int)4, null);
            v17 = Unit.INSTANCE;
        }
        try {
            e /* !! */  = AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (String)infoRule.getIntro(), null, (boolean)false, (int)6, null);
            var13_26 = false;
            var14_35 = false;
            it /* !! */  = e /* !! */ ;
            $i$a$-let-BookInfo$analyzeBookInfo$9 = false;
            var17_44 = (CharSequence)it /* !! */ ;
            var18_45 = false;
            if (var17_44.length() > 0) {
                book.setIntro(StringExtensionsKt.htmlFormat((String)it /* !! */ ));
            }
            e /* !! */  = debugLog;
            if (e /* !! */  == null) {
                v18 = null;
            } else {
                DebugLog.DefaultImpls.log$default((DebugLog)e /* !! */ , (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)book.getIntro()), (boolean)false, (int)4, null);
                v18 = Unit.INSTANCE;
            }
        }
        catch (Exception e) {
            var13_27 = debugLog;
            if (var13_27 == null) {
                v19 = null;
            }
            DebugLog.DefaultImpls.log$default((DebugLog)var13_27, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)e.getLocalizedMessage()), (boolean)false, (int)4, null);
            v19 = Unit.INSTANCE;
        }
        JobKt.ensureActive((CoroutineContext)$completion.getContext());
        e /* !! */  = debugLog;
        if (e /* !! */  == null) {
            v20 = null;
        } else {
            DebugLog.DefaultImpls.log$default((DebugLog)e /* !! */ , (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u5c01\u9762\u94fe\u63a5", (boolean)false, (int)4, null);
            v20 = Unit.INSTANCE;
        }
        try {
            e /* !! */  = AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (String)infoRule.getCoverUrl(), null, (boolean)false, (int)6, null);
            var13_28 = false;
            var14_36 = false;
            it /* !! */  = e /* !! */ ;
            $i$a$-let-BookInfo$analyzeBookInfo$10 = false;
            var17_44 = (CharSequence)it /* !! */ ;
            var18_45 = false;
            if (var17_44.length() > 0) {
                book.setCoverUrl(NetworkUtils.INSTANCE.getAbsoluteURL(redirectUrl, (String)it /* !! */ ));
            }
            e /* !! */  = debugLog;
            if (e /* !! */  == null) {
                v21 = null;
            } else {
                DebugLog.DefaultImpls.log$default((DebugLog)e /* !! */ , (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)book.getCoverUrl()), (boolean)false, (int)4, null);
                v21 = Unit.INSTANCE;
            }
        }
        catch (Exception e) {
            var13_29 = debugLog;
            if (var13_29 == null) {
                v22 = null;
            }
            DebugLog.DefaultImpls.log$default((DebugLog)var13_29, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)e.getLocalizedMessage()), (boolean)false, (int)4, null);
            v22 = Unit.INSTANCE;
        }
        JobKt.ensureActive((CoroutineContext)$completion.getContext());
        var12_13 = debugLog;
        if (var12_13 == null) {
            v23 = null;
        } else {
            DebugLog.DefaultImpls.log$default((DebugLog)var12_13, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u76ee\u5f55\u94fe\u63a5", (boolean)false, (int)4, null);
            v23 = Unit.INSTANCE;
        }
        book.setTocUrl(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (String)infoRule.getTocUrl(), null, (boolean)true, (int)2, null));
        var12_13 = book.getTocUrl();
        var13_30 = false;
        if (var12_13.length() == 0) {
            book.setTocUrl(baseUrl);
        }
        if (Intrinsics.areEqual((Object)book.getTocUrl(), (Object)baseUrl)) {
            book.setTocHtml(body);
        }
        if ((var12_13 = debugLog) == null) {
            v24 = null;
        } else {
            DebugLog.DefaultImpls.log$default((DebugLog)var12_13, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)book.getTocUrl()), (boolean)false, (int)4, null);
            v24 = Unit.INSTANCE;
        }
        if (v24 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return v24;
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ Object analyzeBookInfo$default(BookInfo bookInfo, Book book, String string, AnalyzeRule analyzeRule, BookSource bookSource, String string2, String string3, boolean bl, DebugLog debugLog, Continuation continuation, int n, Object object) throws Exception {
        if ((n & 0x80) != 0) {
            debugLog = null;
        }
        return bookInfo.analyzeBookInfo(book, string, analyzeRule, bookSource, string2, string3, bl, debugLog, continuation);
    }
}

