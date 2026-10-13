/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.BaseSource
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookSource
 *  io.legado.app.data.entities.SearchBook
 *  io.legado.app.data.entities.rule.BookListRule
 *  io.legado.app.help.BookHelp
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.DebugLog$DefaultImpls
 *  io.legado.app.model.analyzeRule.AnalyzeRule
 *  io.legado.app.model.analyzeRule.AnalyzeRule$SourceRule
 *  io.legado.app.model.analyzeRule.AnalyzeUrl
 *  io.legado.app.model.analyzeRule.RuleDataInterface
 *  io.legado.app.model.webBook.BookInfo
 *  io.legado.app.model.webBook.BookList
 *  io.legado.app.model.webBook.BookList$analyzeBookList$1
 *  io.legado.app.model.webBook.BookList$getInfoItem$1
 *  io.legado.app.utils.NetworkUtils
 *  io.legado.app.utils.StringExtensionsKt
 *  io.legado.app.utils.StringUtils
 *  kotlin.Metadata
 *  kotlin.ResultKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.CoroutineContext
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.coroutines.jvm.internal.Boxing
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  kotlinx.coroutines.JobKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model.webBook;

import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookSource;
import io.legado.app.data.entities.SearchBook;
import io.legado.app.data.entities.rule.BookListRule;
import io.legado.app.help.BookHelp;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeRule;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.model.webBook.BookInfo;
import io.legado.app.model.webBook.BookList;
import io.legado.app.utils.NetworkUtils;
import io.legado.app.utils.StringExtensionsKt;
import io.legado.app.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.JobKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002Ja\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013JQ\u0010\u0014\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u00e1\u0001\u0010\u0019\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\b2\u0006\u0010\u001b\u001a\u00020\u00102\u0010\u0010\u001c\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010\u001f\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010 \u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010!\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010\"\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010#\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010$\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010%\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006'"}, d2={"Lio/legado/app/model/webBook/BookList;", "", "()V", "analyzeBookList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/SearchBook;", "Lkotlin/collections/ArrayList;", "body", "", "bookSource", "Lio/legado/app/data/entities/BookSource;", "analyzeUrl", "Lio/legado/app/model/analyzeRule/AnalyzeUrl;", "baseUrl", "variableBook", "isSearch", "", "debugLog", "Lio/legado/app/model/DebugLog;", "(Ljava/lang/String;Lio/legado/app/data/entities/BookSource;Lio/legado/app/model/analyzeRule/AnalyzeUrl;Ljava/lang/String;Lio/legado/app/data/entities/SearchBook;ZLio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getInfoItem", "analyzeRule", "Lio/legado/app/model/analyzeRule/AnalyzeRule;", "variable", "(Ljava/lang/String;Lio/legado/app/model/analyzeRule/AnalyzeRule;Lio/legado/app/data/entities/BookSource;Lio/legado/app/model/analyzeRule/AnalyzeUrl;Ljava/lang/String;Ljava/lang/String;Lio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSearchItem", "item", "log", "ruleName", "", "Lio/legado/app/model/analyzeRule/AnalyzeRule$SourceRule;", "ruleBookUrl", "ruleAuthor", "ruleKind", "ruleCoverUrl", "ruleWordCount", "ruleIntro", "ruleLastChapter", "(Ljava/lang/Object;Lio/legado/app/model/analyzeRule/AnalyzeRule;Lio/legado/app/data/entities/BookSource;Ljava/lang/String;Ljava/lang/String;ZLjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reader-pro"})
public final class BookList {
    @NotNull
    public static final BookList INSTANCE = new BookList();

    private BookList() {
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object analyzeBookList(@Nullable String var1_1, @NotNull BookSource var2_2, @NotNull AnalyzeUrl var3_3, @NotNull String var4_4, @NotNull SearchBook var5_5, boolean var6_6, @Nullable DebugLog var7_7, @NotNull Continuation<? super ArrayList<SearchBook>> var8_8) throws Exception {
        block36: {
            block35: {
                block34: {
                    if (!(var8_8 instanceof analyzeBookList.1)) ** GOTO lbl-1000
                    var34_9 = var8_8;
                    if ((var34_9.label & -2147483648) != 0) {
                        var34_9.label -= -2147483648;
                    } else lbl-1000:
                    // 2 sources

                    {
                        $continuation = new /* Unavailable Anonymous Inner Class!! */;
                    }
                    $result = $continuation.result;
                    var35_11 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    switch ($continuation.label) {
                        case 0: {
                            ResultKt.throwOnFailure((Object)$result);
                            bookList = new ArrayList<E>();
                            var10_13 = body;
                            if (var10_13 == null) {
                                throw new Exception("error_get_web_content");
                            }
                            var10_13 = debugLog;
                            if (var10_13 == null) {
                                v0 = null;
                            } else {
                                DebugLog.DefaultImpls.log$default((DebugLog)var10_13, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2261\u83b7\u53d6\u6210\u529f:", (Object)analyzeUrl.getRuleUrl()), (boolean)false, (int)4, null);
                                v0 = Unit.INSTANCE;
                            }
                            analyzeRule = new AnalyzeRule((RuleDataInterface)variableBook, (BaseSource)bookSource, (DebugLog)debugLog);
                            AnalyzeRule.setContent$default((AnalyzeRule)analyzeRule, (Object)body, null, (int)2, null).setBaseUrl((String)baseUrl);
                            analyzeRule.setRedirectUrl((String)baseUrl);
                            var11_14 = bookSource.getBookUrlPattern();
                            if (var11_14 != null) ** GOTO lbl31
                            v1 = null;
                            break block34;
lbl31:
                            // 1 sources

                            var12_15 = var11_14;
                            var13_17 = false;
                            var14_19 = false;
                            it = var12_15;
                            $i$a$-let-BookList$analyzeBookList$2 = 0;
                            JobKt.ensureActive((CoroutineContext)$continuation.getContext());
                            var17_28 /* !! */  = (CharSequence)baseUrl;
                            var18_32 = it;
                            var19_34 = false;
                            var18_32 = new Regex((String)var18_32);
                            var19_34 = false;
                            if (!var18_32.matches((CharSequence)var17_28 /* !! */ )) break;
                            var17_28 /* !! */  = debugLog;
                            if (var17_28 /* !! */  == null) {
                                v2 = null;
                            } else {
                                DebugLog.DefaultImpls.log$default((DebugLog)var17_28 /* !! */ , (String)bookSource.getBookSourceUrl(), (String)"\u2261\u94fe\u63a5\u4e3a\u8be6\u60c5\u9875", (boolean)false, (int)4, null);
                                v2 = Unit.INSTANCE;
                            }
                            $continuation.L$0 = body;
                            $continuation.L$1 = bookList;
                            $continuation.label = 1;
                            v3 = BookList.INSTANCE.getInfoItem((String)body, analyzeRule, (BookSource)bookSource, (AnalyzeUrl)analyzeUrl, (String)baseUrl, variableBook.getVariable(), (DebugLog)debugLog, (Continuation)$continuation);
                            if (v3 == var35_11) {
                                return var35_11;
                            }
                            ** GOTO lbl62
                        }
                        case 1: {
                            $i$a$-let-BookList$analyzeBookList$2 = false;
                            var9_12 = (ArrayList)$continuation.L$1;
                            var1_1 = (String)$continuation.L$0;
                            ResultKt.throwOnFailure((Object)$result);
                            v3 = $result;
lbl62:
                            // 2 sources

                            if ((var17_28 /* !! */  = (SearchBook)v3) == null) {
                                v4 = null;
                            } else {
                                var18_32 = var17_28 /* !! */ ;
                                var19_34 = false;
                                var20_37 = false;
                                searchBook = var18_32;
                                $i$a$-let-BookList$analyzeBookList$2$1 = false;
                                searchBook.setInfoHtml(var1_1);
                                v4 = Boxing.boxBoolean((boolean)var9_12.add(searchBook));
                            }
                            return var9_12;
                        }
                    }
                    v1 = Unit.INSTANCE;
                }
                var11_14 = null;
                reverse = false;
                if (var6_6) {
                    v5 = (BookListRule)var2_2.getSearchRule();
                } else {
                    var14_20 = var2_2.getExploreRule().getBookList();
                    it = false;
                    $i$a$-let-BookList$analyzeBookList$2 = 0;
                    v5 = (var14_20 == null || StringsKt.isBlank((CharSequence)var14_20) != false) != false ? (BookListRule)var2_2.getSearchRule() : (BookListRule)var2_2.getExploreRule();
                }
                bookListRule = v5;
                it = bookListRule.getBookList();
                v6 = ruleList = it == null ? "" : it;
                if (StringsKt.startsWith$default((String)ruleList, (String)"-", (boolean)false, (int)2, null)) {
                    reverse = true;
                    it = ruleList;
                    $i$a$-let-BookList$analyzeBookList$2 = 1;
                    var17_29 = false;
                    v7 = it.substring($i$a$-let-BookList$analyzeBookList$2);
                    Intrinsics.checkNotNullExpressionValue((Object)v7, (String)"(this as java.lang.String).substring(startIndex)");
                    ruleList = v7;
                }
                if (StringsKt.startsWith$default((String)ruleList, (String)"+", (boolean)false, (int)2, null)) {
                    it = ruleList;
                    $i$a$-let-BookList$analyzeBookList$2 = 1;
                    var17_30 = false;
                    v8 = it;
                    if (v8 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    v9 = v8.substring($i$a$-let-BookList$analyzeBookList$2);
                    Intrinsics.checkNotNullExpressionValue((Object)v9, (String)"(this as java.lang.String).substring(startIndex)");
                    ruleList = v9;
                }
                if ((it = var7_7) == null) {
                    v10 = null;
                } else {
                    DebugLog.DefaultImpls.log$default((DebugLog)it, (String)var2_2.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u4e66\u7c4d\u5217\u8868", (boolean)false, (int)4, null);
                    v10 = Unit.INSTANCE;
                }
                collections = analyzeRule.getElements(ruleList);
                JobKt.ensureActive((CoroutineContext)$continuation.getContext());
                if (!collections.isEmpty()) break block35;
                it = var2_2.getBookUrlPattern();
                $i$a$-let-BookList$analyzeBookList$2 = 0;
                var17_31 = false;
                if (!(it == null || it.length() == 0)) break block35;
                it = var7_7;
                if (it == null) {
                    v11 = null;
                } else {
                    DebugLog.DefaultImpls.log$default((DebugLog)it, (String)var2_2.getBookSourceUrl(), (String)"\u2514\u5217\u8868\u4e3a\u7a7a,\u6309\u8be6\u60c5\u9875\u89e3\u6790", (boolean)false, (int)4, null);
                    v11 = Unit.INSTANCE;
                }
                $continuation.L$0 = var1_1;
                $continuation.L$1 = var9_12;
                $continuation.label = 2;
                v12 = this.getInfoItem(var1_1, analyzeRule, var2_2, var3_3, var4_4, var5_5.getVariable(), var7_7, (Continuation)$continuation);
                if (v12 == var35_11) {
                    return var35_11;
                }
                ** GOTO lbl136
                {
                    case 2: {
                        var9_12 = (ArrayList)$continuation.L$1;
                        var1_1 = (String)$continuation.L$0;
                        ResultKt.throwOnFailure((Object)$result);
                        v12 = $result;
lbl136:
                        // 2 sources

                        if ((it = (SearchBook)v12) == null) {
                            v13 = null;
                        } else {
                            $i$a$-let-BookList$analyzeBookList$2 = it;
                            var17_31 = false;
                            var18_33 = false;
                            searchBook = $i$a$-let-BookList$analyzeBookList$2;
                            $i$a$-let-BookList$analyzeBookList$3 = false;
                            searchBook.setInfoHtml(var1_1);
                            v13 = Boxing.boxBoolean((boolean)var9_12.add(searchBook));
                        }
                        break block36;
                    }
                }
            }
            ruleName = AnalyzeRule.splitSourceRule$default((AnalyzeRule)var10_13, (String)var13_18.getName(), (boolean)false, (int)2, null);
            ruleBookUrl = AnalyzeRule.splitSourceRule$default((AnalyzeRule)var10_13, (String)var13_18.getBookUrl(), (boolean)false, (int)2, null);
            ruleAuthor = AnalyzeRule.splitSourceRule$default((AnalyzeRule)var10_13, (String)var13_18.getAuthor(), (boolean)false, (int)2, null);
            ruleCoverUrl = AnalyzeRule.splitSourceRule$default((AnalyzeRule)var10_13, (String)var13_18.getCoverUrl(), (boolean)false, (int)2, null);
            ruleIntro = AnalyzeRule.splitSourceRule$default((AnalyzeRule)var10_13, (String)var13_18.getIntro(), (boolean)false, (int)2, null);
            ruleKind = AnalyzeRule.splitSourceRule$default((AnalyzeRule)var10_13, (String)var13_18.getKind(), (boolean)false, (int)2, null);
            ruleLastChapter = AnalyzeRule.splitSourceRule$default((AnalyzeRule)var10_13, (String)var13_18.getLastChapter(), (boolean)false, (int)2, null);
            ruleWordCount = AnalyzeRule.splitSourceRule$default((AnalyzeRule)var10_13, (String)var13_18.getWordCount(), (boolean)false, (int)2, null);
            var23_44 = var7_7;
            if (var23_44 == null) {
                v14 = null;
            } else {
                DebugLog.DefaultImpls.log$default(var23_44, (String)var2_2.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514\u5217\u8868\u5927\u5c0f:", (Object)Boxing.boxInt((int)var11_14.size())), (boolean)false, (int)4, null);
                v14 = Unit.INSTANCE;
            }
            var23_44 = var11_14.iterator();
            var24_45 = 0;
lbl164:
            // 3 sources

            while (var23_44.hasNext()) {
                index = var24_45++;
                item = var23_44.next();
                var28_49 /* !! */  = var5_5.getVariable();
                var29_50 = index == 0;
                $continuation.L$0 = this;
                $continuation.L$1 = var1_1;
                $continuation.L$2 = var2_2;
                $continuation.L$3 = var4_4;
                $continuation.L$4 = var5_5;
                $continuation.L$5 = var7_7;
                $continuation.L$6 = var9_12;
                $continuation.L$7 = var10_13;
                $continuation.L$8 = ruleName;
                $continuation.L$9 = ruleBookUrl;
                $continuation.L$10 = ruleAuthor;
                $continuation.L$11 = ruleCoverUrl;
                $continuation.L$12 = ruleIntro;
                $continuation.L$13 = ruleKind;
                $continuation.L$14 = ruleLastChapter;
                $continuation.L$15 = ruleWordCount;
                $continuation.L$16 = var23_44;
                $continuation.I$0 = var12_16;
                $continuation.I$1 = var24_45;
                $continuation.label = 3;
                v15 = this.getSearchItem(item, var10_13, var2_2, var4_4, (String)var28_49 /* !! */ , var29_50 != false, ruleName, ruleBookUrl, ruleAuthor, ruleKind, ruleCoverUrl, ruleWordCount, ruleIntro, ruleLastChapter, var7_7, (Continuation)$continuation);
                if (v15 == var35_11) {
                    return var35_11;
                }
                ** GOTO lbl216
            }
            {
                break;
                case 3: {
                    var24_45 = $continuation.I$1;
                    var12_16 = $continuation.I$0;
                    var23_44 = (DebugLog)$continuation.L$16;
                    ruleWordCount = (List)$continuation.L$15;
                    ruleLastChapter = (List)$continuation.L$14;
                    ruleKind = (List)$continuation.L$13;
                    ruleIntro = (List)$continuation.L$12;
                    ruleCoverUrl = (List)$continuation.L$11;
                    ruleAuthor = (List)$continuation.L$10;
                    ruleBookUrl = (List)$continuation.L$9;
                    ruleName = (List)$continuation.L$8;
                    var10_13 = (AnalyzeRule)$continuation.L$7;
                    var9_12 = (ArrayList)$continuation.L$6;
                    var7_7 = (DebugLog)$continuation.L$5;
                    var5_5 = (SearchBook)$continuation.L$4;
                    var4_4 = (String)$continuation.L$3;
                    var2_2 = (BookSource)$continuation.L$2;
                    var1_1 = (String)$continuation.L$1;
                    this = (BookList)$continuation.L$0;
                    ResultKt.throwOnFailure((Object)$result);
                    v15 = $result;
lbl216:
                    // 2 sources

                    if ((var27_48 = (SearchBook)v15) != null) ** GOTO lbl219
                    v16 = null;
                    ** GOTO lbl164
lbl219:
                    // 1 sources

                    var28_49 /* !! */  = var27_48;
                    var29_50 = false;
                    var30_51 = false;
                    searchBook = var28_49 /* !! */ ;
                    $i$a$-let-BookList$analyzeBookList$4 = false;
                    if (Intrinsics.areEqual((Object)var4_4, (Object)searchBook.getBookUrl())) {
                        searchBook.setInfoHtml(var1_1);
                    }
                    v16 = Boxing.boxBoolean((boolean)var9_12.add(searchBook));
                    ** GOTO lbl164
                }
            }
            if (var12_16 != 0) {
                CollectionsKt.reverse((List)var9_12);
            }
        }
        return var9_12;
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object analyzeBookList$default(BookList bookList, String string, BookSource bookSource, AnalyzeUrl analyzeUrl, String string2, SearchBook searchBook2, boolean bl, DebugLog debugLog, Continuation continuation, int n, Object object) throws Exception {
        if ((n & 0x20) != 0) {
            bl = true;
        }
        if ((n & 0x40) != 0) {
            debugLog = null;
        }
        return bookList.analyzeBookList(string, bookSource, analyzeUrl, string2, searchBook2, bl, debugLog, continuation);
    }

    /*
     * Unable to fully structure code
     */
    private final Object getInfoItem(String var1_1, AnalyzeRule var2_2, BookSource var3_3, AnalyzeUrl var4_4, String var5_5, String var6_6, DebugLog var7_7, Continuation<? super SearchBook> var8_8) {
        if (!(var8_8 instanceof getInfoItem.1)) ** GOTO lbl-1000
        var13_9 = var8_8;
        if ((var13_9.label & -2147483648) != 0) {
            var13_9.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var14_11 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                book = new Book(null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0L, null, 0L, 0L, 0, 0, null, 0, 0, 0L, null, false, 0, 0, false, (String)variable, null, false, null, -536870913, 1, null);
                book.setBookUrl(analyzeUrl.getRuleUrl());
                book.setOrigin(bookSource.getBookSourceUrl());
                book.setOriginName(bookSource.getBookSourceName());
                book.setOriginOrder(bookSource.getCustomOrder());
                book.setType(bookSource.getBookSourceType());
                book.setUserNameSpace(analyzeRule.getUserNameSpace());
                analyzeRule.setRuleData((RuleDataInterface)book);
                $continuation.L$0 = book;
                $continuation.label = 1;
                v0 = BookInfo.INSTANCE.analyzeBookInfo(book, (String)body, (AnalyzeRule)analyzeRule, (BookSource)bookSource, (String)baseUrl, (String)baseUrl, false, (DebugLog)debugLog, (Continuation)$continuation);
                if (v0 == var14_11) {
                    return var14_11;
                }
                ** GOTO lbl30
            }
            case 1: {
                book = (Book)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl30:
                // 2 sources

                var10_13 = book.getName();
                var11_14 = false;
                if (StringsKt.isBlank((CharSequence)var10_13) == false) {
                    return book.toSearchBook();
                }
                return null;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    static /* synthetic */ Object getInfoItem$default(BookList bookList, String string, AnalyzeRule analyzeRule, BookSource bookSource, AnalyzeUrl analyzeUrl, String string2, String string3, DebugLog debugLog, Continuation continuation, int n, Object object) {
        if ((n & 0x40) != 0) {
            debugLog = null;
        }
        return bookList.getInfoItem(string, analyzeRule, bookSource, analyzeUrl, string2, string3, debugLog, continuation);
    }

    private final Object getSearchItem(Object item, AnalyzeRule analyzeRule, BookSource bookSource, String baseUrl, String variable, boolean log, List<AnalyzeRule.SourceRule> ruleName, List<AnalyzeRule.SourceRule> ruleBookUrl, List<AnalyzeRule.SourceRule> ruleAuthor, List<AnalyzeRule.SourceRule> ruleKind, List<AnalyzeRule.SourceRule> ruleCoverUrl, List<AnalyzeRule.SourceRule> ruleWordCount, List<AnalyzeRule.SourceRule> ruleIntro, List<AnalyzeRule.SourceRule> ruleLastChapter, DebugLog debugLog, Continuation<? super SearchBook> $completion) {
        Object object;
        SearchBook searchBook2 = new SearchBook(null, null, null, 0, null, null, null, null, null, null, null, null, 0L, variable, 0, 24575, null);
        searchBook2.setOrigin(bookSource.getBookSourceUrl());
        searchBook2.setOriginName(bookSource.getBookSourceName());
        searchBook2.setType(bookSource.getBookSourceType());
        searchBook2.setOriginOrder(bookSource.getCustomOrder());
        searchBook2.setUserNameSpace(analyzeRule.getUserNameSpace());
        analyzeRule.setRuleData((RuleDataInterface)searchBook2);
        AnalyzeRule.setContent$default((AnalyzeRule)analyzeRule, (Object)item, null, (int)2, null);
        JobKt.ensureActive((CoroutineContext)$completion.getContext());
        if (log && (object = debugLog) != null) {
            DebugLog.DefaultImpls.log$default((DebugLog)object, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u4e66\u540d", (boolean)false, (int)4, null);
        }
        searchBook2.setName(BookHelp.INSTANCE.formatBookName(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, ruleName, null, (boolean)false, (int)6, null)));
        if (log && (object = debugLog) != null) {
            DebugLog.DefaultImpls.log$default((DebugLog)object, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)searchBook2.getName()), (boolean)false, (int)4, null);
        }
        object = searchBook2.getName();
        boolean bl = false;
        if (object.length() > 0) {
            block33: {
                block32: {
                    Object e2;
                    block31: {
                        block30: {
                            block29: {
                                JobKt.ensureActive((CoroutineContext)$completion.getContext());
                                if (log && (object = debugLog) != null) {
                                    DebugLog.DefaultImpls.log$default((DebugLog)object, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u4f5c\u8005", (boolean)false, (int)4, null);
                                }
                                searchBook2.setAuthor(BookHelp.INSTANCE.formatBookAuthor(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, ruleAuthor, null, (boolean)false, (int)6, null)));
                                if (log && (object = debugLog) != null) {
                                    DebugLog.DefaultImpls.log$default((DebugLog)object, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)searchBook2.getAuthor()), (boolean)false, (int)4, null);
                                }
                                JobKt.ensureActive((CoroutineContext)$completion.getContext());
                                if (log && (object = debugLog) != null) {
                                    DebugLog.DefaultImpls.log$default((DebugLog)object, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u5206\u7c7b", (boolean)false, (int)4, null);
                                }
                                try {
                                    object = AnalyzeRule.getStringList$default((AnalyzeRule)analyzeRule, ruleKind, null, (boolean)false, (int)6, null);
                                    searchBook2.setKind(object == null ? null : CollectionsKt.joinToString$default((Iterable)((Iterable)object), (CharSequence)",", null, null, (int)0, null, null, (int)62, null));
                                    if (log && (object = debugLog) != null) {
                                        DebugLog.DefaultImpls.log$default((DebugLog)object, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)searchBook2.getKind()), (boolean)false, (int)4, null);
                                    }
                                }
                                catch (Exception e2) {
                                    DebugLog debugLog2;
                                    if (!log || (debugLog2 = debugLog) == null) break block29;
                                    DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)e2.getLocalizedMessage()), (boolean)false, (int)4, null);
                                }
                            }
                            JobKt.ensureActive((CoroutineContext)$completion.getContext());
                            if (log && (e2 = debugLog) != null) {
                                DebugLog.DefaultImpls.log$default((DebugLog)e2, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u5b57\u6570", (boolean)false, (int)4, null);
                            }
                            try {
                                searchBook2.setWordCount(StringUtils.INSTANCE.wordCountFormat(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, ruleWordCount, null, (boolean)false, (int)6, null)));
                                if (log && (e2 = debugLog) != null) {
                                    DebugLog.DefaultImpls.log$default((DebugLog)e2, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)searchBook2.getWordCount()), (boolean)false, (int)4, null);
                                }
                            }
                            catch (Exception e2) {
                                DebugLog debugLog3;
                                if (!log || (debugLog3 = debugLog) == null) break block30;
                                DebugLog.DefaultImpls.log$default((DebugLog)debugLog3, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)e2.getLocalizedMessage()), (boolean)false, (int)4, null);
                            }
                        }
                        JobKt.ensureActive((CoroutineContext)$completion.getContext());
                        if (log && (e2 = debugLog) != null) {
                            DebugLog.DefaultImpls.log$default((DebugLog)e2, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u6700\u65b0\u7ae0\u8282", (boolean)false, (int)4, null);
                        }
                        try {
                            searchBook2.setLatestChapterTitle(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, ruleLastChapter, null, (boolean)false, (int)6, null));
                            if (log && (e2 = debugLog) != null) {
                                DebugLog.DefaultImpls.log$default((DebugLog)e2, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)searchBook2.getLatestChapterTitle()), (boolean)false, (int)4, null);
                            }
                        }
                        catch (Exception e3) {
                            DebugLog debugLog4;
                            if (!log || (debugLog4 = debugLog) == null) break block31;
                            DebugLog.DefaultImpls.log$default((DebugLog)debugLog4, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)e3.getLocalizedMessage()), (boolean)false, (int)4, null);
                        }
                    }
                    JobKt.ensureActive((CoroutineContext)$completion.getContext());
                    if (log && (e2 = debugLog) != null) {
                        DebugLog.DefaultImpls.log$default((DebugLog)e2, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u7b80\u4ecb", (boolean)false, (int)4, null);
                    }
                    try {
                        searchBook2.setIntro(StringExtensionsKt.htmlFormat((String)AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, ruleIntro, null, (boolean)false, (int)6, null)));
                        if (log && (e2 = debugLog) != null) {
                            DebugLog.DefaultImpls.log$default((DebugLog)e2, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)searchBook2.getIntro()), (boolean)false, (int)4, null);
                        }
                    }
                    catch (Exception e3) {
                        DebugLog debugLog5;
                        if (!log || (debugLog5 = debugLog) == null) break block32;
                        DebugLog.DefaultImpls.log$default((DebugLog)debugLog5, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)e3.getLocalizedMessage()), (boolean)false, (int)4, null);
                    }
                }
                JobKt.ensureActive((CoroutineContext)$completion.getContext());
                if (log && (e2 = debugLog) != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)e2, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u5c01\u9762\u94fe\u63a5", (boolean)false, (int)4, null);
                }
                try {
                    e2 = AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, ruleCoverUrl, null, (boolean)false, (int)6, null);
                    boolean bl2 = false;
                    boolean bl3 = false;
                    Object it = e2;
                    boolean bl4 = false;
                    CharSequence charSequence = (CharSequence)it;
                    boolean bl5 = false;
                    if (charSequence.length() > 0) {
                        searchBook2.setCoverUrl(NetworkUtils.INSTANCE.getAbsoluteURL(baseUrl, (String)it));
                    }
                    if (log && (e2 = debugLog) != null) {
                        DebugLog.DefaultImpls.log$default((DebugLog)e2, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)searchBook2.getCoverUrl()), (boolean)false, (int)4, null);
                    }
                }
                catch (Exception e) {
                    DebugLog debugLog6;
                    if (!log || (debugLog6 = debugLog) == null) break block33;
                    DebugLog.DefaultImpls.log$default((DebugLog)debugLog6, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)e.getLocalizedMessage()), (boolean)false, (int)4, null);
                }
            }
            JobKt.ensureActive((CoroutineContext)$completion.getContext());
            if (log && (object = debugLog) != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u8be6\u60c5\u9875\u94fe\u63a5", (boolean)false, (int)4, null);
            }
            searchBook2.setBookUrl(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, ruleBookUrl, null, (boolean)true, (int)2, null));
            object = searchBook2.getBookUrl();
            boolean bl6 = false;
            if (object.length() == 0) {
                searchBook2.setBookUrl(baseUrl);
            }
            if (log && (object = debugLog) != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)searchBook2.getBookUrl()), (boolean)false, (int)4, null);
            }
            return searchBook2;
        }
        return null;
    }

    static /* synthetic */ Object getSearchItem$default(BookList bookList, Object object, AnalyzeRule analyzeRule, BookSource bookSource, String string, String string2, boolean bl, List list2, List list3, List list4, List list5, List list6, List list7, List list8, List list9, DebugLog debugLog, Continuation continuation, int n, Object object2) {
        if ((n & 0x4000) != 0) {
            debugLog = null;
        }
        return bookList.getSearchItem(object, analyzeRule, bookSource, string, string2, bl, list2, list3, list4, list5, list6, list7, list8, list9, debugLog, continuation);
    }

    public static final /* synthetic */ Object access$getInfoItem(BookList $this, String body, AnalyzeRule analyzeRule, BookSource bookSource, AnalyzeUrl analyzeUrl, String baseUrl, String variable, DebugLog debugLog, Continuation $completion) {
        return $this.getInfoItem(body, analyzeRule, bookSource, analyzeUrl, baseUrl, variable, debugLog, $completion);
    }

    public static final /* synthetic */ Object access$getSearchItem(BookList $this, Object item, AnalyzeRule analyzeRule, BookSource bookSource, String baseUrl, String variable, boolean log, List ruleName, List ruleBookUrl, List ruleAuthor, List ruleKind, List ruleCoverUrl, List ruleWordCount, List ruleIntro, List ruleLastChapter, DebugLog debugLog, Continuation $completion) {
        return $this.getSearchItem(item, analyzeRule, bookSource, baseUrl, variable, log, ruleName, ruleBookUrl, ruleAuthor, ruleKind, ruleCoverUrl, ruleWordCount, ruleIntro, ruleLastChapter, debugLog, $completion);
    }
}

