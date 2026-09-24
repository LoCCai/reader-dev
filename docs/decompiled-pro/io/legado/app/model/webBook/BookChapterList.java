/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.BaseSource
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookChapter
 *  io.legado.app.data.entities.BookSource
 *  io.legado.app.data.entities.rule.TocRule
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.DebugLog$DefaultImpls
 *  io.legado.app.model.analyzeRule.AnalyzeRule
 *  io.legado.app.model.analyzeRule.RuleDataInterface
 *  io.legado.app.model.webBook.BookChapterList
 *  io.legado.app.utils.StringExtensionsKt
 *  io.legado.app.utils.TextUtils
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.CoroutineContext
 *  kotlin.coroutines.jvm.internal.Boxing
 *  kotlin.jvm.internal.Intrinsics
 *  kotlinx.coroutines.JobKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model.webBook;

import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.data.entities.BookSource;
import io.legado.app.data.entities.rule.TocRule;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeRule;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.utils.StringExtensionsKt;
import io.legado.app.utils.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.JobKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0081\u0001\u0010\u0003\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00050\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00132\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017JM\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\b\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2={"Lio/legado/app/model/webBook/BookChapterList;", "", "()V", "analyzeChapterList", "Lkotlin/Pair;", "", "Lio/legado/app/data/entities/BookChapter;", "", "book", "Lio/legado/app/data/entities/Book;", "baseUrl", "redirectUrl", "body", "tocRule", "Lio/legado/app/data/entities/rule/TocRule;", "listRule", "bookSource", "Lio/legado/app/data/entities/BookSource;", "getNextUrl", "", "log", "debugLog", "Lio/legado/app/model/DebugLog;", "(Lio/legado/app/data/entities/Book;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lio/legado/app/data/entities/rule/TocRule;Ljava/lang/String;Lio/legado/app/data/entities/BookSource;ZZLio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lio/legado/app/data/entities/Book;Ljava/lang/String;Lio/legado/app/data/entities/BookSource;Ljava/lang/String;Ljava/lang/String;Lio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reader-pro"})
public final class BookChapterList {
    @NotNull
    public static final BookChapterList INSTANCE = new BookChapterList();

    private BookChapterList() {
    }

    /*
     * Exception decompiling
     */
    @Nullable
    public final Object analyzeChapterList(@NotNull Book var1_1, @Nullable String var2_2, @NotNull BookSource var3_3, @NotNull String var4_4, @NotNull String var5_5, @Nullable DebugLog var6_6, @NotNull Continuation<? super List<BookChapter>> var7_7) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[CASE]], but top level block is 7[SWITCH]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static /* synthetic */ Object analyzeChapterList$default(BookChapterList bookChapterList, Book book, String string, BookSource bookSource, String string2, String string3, DebugLog debugLog, Continuation continuation, int n, Object object) {
        if ((n & 0x20) != 0) {
            debugLog = null;
        }
        return bookChapterList.analyzeChapterList(book, string, bookSource, string2, string3, debugLog, continuation);
    }

    /*
     * WARNING - void declaration
     */
    private final Object analyzeChapterList(Book book, String baseUrl, String redirectUrl, String body, TocRule tocRule, String listRule, BookSource bookSource, boolean getNextUrl, boolean log, DebugLog debugLog, Continuation<? super Pair<? extends List<BookChapter>, ? extends List<String>>> $completion) {
        boolean bl;
        Object object;
        DebugLog debugLog2;
        DebugLog debugLog3;
        AnalyzeRule analyzeRule = new AnalyzeRule((RuleDataInterface)book, (BaseSource)bookSource, debugLog);
        AnalyzeRule.setContent$default((AnalyzeRule)analyzeRule, (Object)body, null, (int)2, null).setBaseUrl(baseUrl);
        analyzeRule.setRedirectUrl(redirectUrl);
        boolean bl2 = false;
        ArrayList<BookChapter> chapterList = new ArrayList<BookChapter>();
        if (log && (debugLog3 = debugLog) != null) {
            DebugLog.DefaultImpls.log$default((DebugLog)debugLog3, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u76ee\u5f55\u5217\u8868", (boolean)false, (int)4, null);
        }
        List elements = analyzeRule.getElements(listRule);
        if (log && (debugLog2 = debugLog) != null) {
            DebugLog.DefaultImpls.log$default((DebugLog)debugLog2, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514\u5217\u8868\u5927\u5c0f:", (Object)Boxing.boxInt((int)elements.size())), (boolean)false, (int)4, null);
        }
        boolean bl3 = false;
        ArrayList<String> nextUrlList = new ArrayList<String>();
        String nextTocRule = tocRule.getNextTocUrl();
        if (getNextUrl) {
            object = nextTocRule;
            bl = false;
            boolean bl4 = false;
            if (!(object == null || object.length() == 0)) {
                if (log && (object = debugLog) != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)object, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u83b7\u53d6\u76ee\u5f55\u4e0b\u4e00\u9875\u5217\u8868", (boolean)false, (int)4, null);
                }
                if ((object = AnalyzeRule.getStringList$default((AnalyzeRule)analyzeRule, (String)nextTocRule, null, (boolean)true, (int)2, null)) != null) {
                    Object object2 = object;
                    bl4 = false;
                    boolean bl5 = false;
                    Object it = object2;
                    boolean bl6 = false;
                    Iterator iterator = it.iterator();
                    while (iterator.hasNext()) {
                        String item = (String)iterator.next();
                        if (Intrinsics.areEqual((Object)item, (Object)redirectUrl)) continue;
                        nextUrlList.add(item);
                    }
                }
                if (log && (object = debugLog) != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)object, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u2514", (Object)TextUtils.join((CharSequence)"\uff0c\n", (Iterable)nextUrlList)), (boolean)false, (int)4, null);
                }
            }
        }
        JobKt.ensureActive((CoroutineContext)$completion.getContext());
        object = elements;
        bl = false;
        if (!object.isEmpty()) {
            DebugLog debugLog4;
            if (log && (object = debugLog) != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object, (String)bookSource.getBookSourceUrl(), (String)"\u250c\u89e3\u6790\u76ee\u5f55\u5217\u8868", (boolean)false, (int)4, null);
            }
            List nameRule = AnalyzeRule.splitSourceRule$default((AnalyzeRule)analyzeRule, (String)tocRule.getChapterName(), (boolean)false, (int)2, null);
            List urlRule = AnalyzeRule.splitSourceRule$default((AnalyzeRule)analyzeRule, (String)tocRule.getChapterUrl(), (boolean)false, (int)2, null);
            List vipRule = AnalyzeRule.splitSourceRule$default((AnalyzeRule)analyzeRule, (String)tocRule.isVip(), (boolean)false, (int)2, null);
            List upTimeRule = AnalyzeRule.splitSourceRule$default((AnalyzeRule)analyzeRule, (String)tocRule.getUpdateTime(), (boolean)false, (int)2, null);
            List isVolumeRule = AnalyzeRule.splitSourceRule$default((AnalyzeRule)analyzeRule, (String)tocRule.isVolume(), (boolean)false, (int)2, null);
            Iterable $this$forEachIndexed$iv = elements;
            boolean $i$f$forEachIndexed = false;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                void item;
                int n = index$iv++;
                boolean bl7 = false;
                if (n < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Object t = item$iv;
                int index = ((Number)Boxing.boxInt((int)n)).intValue();
                boolean bl8 = false;
                JobKt.ensureActive((CoroutineContext)$completion.getContext());
                AnalyzeRule.setContent$default((AnalyzeRule)analyzeRule, (Object)item, null, (int)2, null);
                String string = book.getBookUrl();
                BookChapter bookChapter = new BookChapter(null, null, false, redirectUrl, string, 0, null, null, null, null, null, null, null, 8167, null);
                analyzeRule.setChapter(bookChapter);
                bookChapter.setTitle(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (List)nameRule, null, (boolean)false, (int)6, null));
                bookChapter.setUrl(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (List)urlRule, null, (boolean)false, (int)6, null));
                bookChapter.setTag(AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (List)upTimeRule, null, (boolean)false, (int)6, null));
                bookChapter.setUserNameSpace(book.getUserNameSpace());
                String isVolume = AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (List)isVolumeRule, null, (boolean)false, (int)6, null);
                bookChapter.setVolume(false);
                if (StringExtensionsKt.isTrue$default((String)isVolume, (boolean)false, (int)1, null)) {
                    bookChapter.setVolume(true);
                }
                CharSequence charSequence = bookChapter.getUrl();
                boolean bl9 = false;
                if (charSequence.length() == 0) {
                    if (bookChapter.isVolume()) {
                        bookChapter.setUrl(Intrinsics.stringPlus((String)bookChapter.getTitle(), (Object)Boxing.boxInt((int)index)));
                        if (log && (charSequence = debugLog) != null) {
                            DebugLog.DefaultImpls.log$default((DebugLog)charSequence, (String)bookSource.getBookSourceUrl(), (String)("\u21d2\u4e00\u7ea7\u76ee\u5f55" + index + "\u672a\u83b7\u53d6\u5230url,\u4f7f\u7528\u6807\u9898\u66ff\u4ee3"), (boolean)false, (int)4, null);
                        }
                    } else {
                        bookChapter.setUrl(baseUrl);
                        if (log && (charSequence = debugLog) != null) {
                            DebugLog.DefaultImpls.log$default((DebugLog)charSequence, (String)bookSource.getBookSourceUrl(), (String)("\u21d2\u76ee\u5f55" + index + "\u672a\u83b7\u53d6\u5230url,\u4f7f\u7528baseUrl\u66ff\u4ee3"), (boolean)false, (int)4, null);
                        }
                    }
                }
                charSequence = bookChapter.getTitle();
                bl9 = false;
                if (charSequence.length() > 0) {
                    String isVip = AnalyzeRule.getString$default((AnalyzeRule)analyzeRule, (List)vipRule, null, (boolean)false, (int)6, null);
                    if (StringExtensionsKt.isTrue$default((String)isVip, (boolean)false, (int)1, null)) {
                        bookChapter.setTitle(Intrinsics.stringPlus((String)"\ud83d\udd12", (Object)bookChapter.getTitle()));
                    }
                    chapterList.add(bookChapter);
                    continue;
                }
                if (!log || (charSequence = debugLog) == null) continue;
                DebugLog.DefaultImpls.log$default((DebugLog)charSequence, (String)bookSource.getBookSourceUrl(), (String)"\u7ae0\u8282\u540d\u4e3a\u7a7a", (boolean)false, (int)4, null);
            }
            if (log && (debugLog4 = debugLog) != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog4, (String)bookSource.getBookSourceUrl(), (String)"\u2514\u76ee\u5f55\u5217\u8868\u89e3\u6790\u5b8c\u6210", (boolean)false, (int)4, null);
            }
            if (chapterList.size() > 0) {
                if (log && (debugLog4 = debugLog) != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)debugLog4, (String)bookSource.getBookSourceUrl(), (String)"\u2261\u9996\u7ae0\u4fe1\u606f", (boolean)false, (int)4, null);
                }
                if (log && (debugLog4 = debugLog) != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)debugLog4, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u25c7\u7ae0\u8282\u540d\u79f0:", (Object)((BookChapter)chapterList.get(0)).getTitle()), (boolean)false, (int)4, null);
                }
                if (log && (debugLog4 = debugLog) != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)debugLog4, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u25c7\u7ae0\u8282\u94fe\u63a5:", (Object)((BookChapter)chapterList.get(0)).getUrl()), (boolean)false, (int)4, null);
                }
                if (log && (debugLog4 = debugLog) != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)debugLog4, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u25c7\u7ae0\u8282\u4fe1\u606f:", (Object)((BookChapter)chapterList.get(0)).getTag()), (boolean)false, (int)4, null);
                }
                if (log && (debugLog4 = debugLog) != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)debugLog4, (String)bookSource.getBookSourceUrl(), (String)Intrinsics.stringPlus((String)"\u25c7\u662f\u5426\u5377\u540d:", (Object)Boxing.boxBoolean((boolean)((BookChapter)chapterList.get(0)).isVolume())), (boolean)false, (int)4, null);
                }
            } else if (log && (debugLog4 = debugLog) != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)debugLog4, (String)bookSource.getBookSourceUrl(), (String)"\u7ae0\u8282\u5217\u8868\u4e3a\u7a7a", (boolean)false, (int)4, null);
            }
        }
        return new Pair(chapterList, nextUrlList);
    }

    static /* synthetic */ Object analyzeChapterList$default(BookChapterList bookChapterList, Book book, String string, String string2, String string3, TocRule tocRule, String string4, BookSource bookSource, boolean bl, boolean bl2, DebugLog debugLog, Continuation continuation, int n, Object object) {
        if ((n & 0x80) != 0) {
            bl = true;
        }
        if ((n & 0x100) != 0) {
            bl2 = false;
        }
        if ((n & 0x200) != 0) {
            debugLog = null;
        }
        return bookChapterList.analyzeChapterList(book, string, string2, string3, tocRule, string4, bookSource, bl, bl2, debugLog, continuation);
    }

    public static final /* synthetic */ Object access$analyzeChapterList(BookChapterList $this, Book book, String baseUrl, String redirectUrl, String body, TocRule tocRule, String listRule, BookSource bookSource, boolean getNextUrl, boolean log, DebugLog debugLog, Continuation $completion) {
        return $this.analyzeChapterList(book, baseUrl, redirectUrl, body, tocRule, listRule, bookSource, getNextUrl, log, debugLog, $completion);
    }
}

