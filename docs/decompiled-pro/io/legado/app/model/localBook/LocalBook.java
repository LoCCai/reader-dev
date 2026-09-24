/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookChapter
 *  io.legado.app.exception.TocEmptyException
 *  io.legado.app.help.BookHelp
 *  io.legado.app.model.localBook.CbzFile
 *  io.legado.app.model.localBook.EpubFile
 *  io.legado.app.model.localBook.LocalBook
 *  io.legado.app.model.localBook.PdfFile
 *  io.legado.app.model.localBook.TextFile
 *  io.legado.app.model.localBook.UmdFile
 *  io.legado.app.utils.FileUtils
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Result
 *  kotlin.Result$Companion
 *  kotlin.ResultKt
 *  kotlin.Unit
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model.localBook;

import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.exception.TocEmptyException;
import io.legado.app.help.BookHelp;
import io.legado.app.model.localBook.CbzFile;
import io.legado.app.model.localBook.EpubFile;
import io.legado.app.model.localBook.PdfFile;
import io.legado.app.model.localBook.TextFile;
import io.legado.app.model.localBook.UmdFile;
import io.legado.app.utils.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\nJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fJ\u001e\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u00140\u0013j\b\u0012\u0004\u0012\u00020\u0014`\u00152\u0006\u0010\u000e\u001a\u00020\u000fJ\u0018\u0010\u0016\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0014R\u001e\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0007\u00a8\u0006\u0018"}, d2={"Lio/legado/app/model/localBook/LocalBook;", "", "()V", "nameAuthorPatterns", "", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "[Ljava/util/regex/Pattern;", "analyzeNameAuthor", "Lkotlin/Pair;", "", "fileName", "deleteBook", "", "book", "Lio/legado/app/data/entities/Book;", "getBookInputStream", "Ljava/io/InputStream;", "getChapterList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/BookChapter;", "Lkotlin/collections/ArrayList;", "getContent", "chapter", "reader-pro"})
public final class LocalBook {
    @NotNull
    public static final LocalBook INSTANCE = new LocalBook();
    @NotNull
    private static final Pattern[] nameAuthorPatterns;

    private LocalBook() {
    }

    @NotNull
    public final InputStream getBookInputStream(@NotNull Book book) throws FileNotFoundException, SecurityException {
        Intrinsics.checkNotNullParameter((Object)book, (String)"book");
        File file = book.getLocalFile();
        if (file.exists()) {
            return new FileInputStream(file);
        }
        throw new FileNotFoundException(Intrinsics.stringPlus((String)book.getName(), (Object)" \u6587\u4ef6\u4e0d\u5b58\u5728"));
    }

    @NotNull
    public final ArrayList<BookChapter> getChapterList(@NotNull Book book) throws Exception {
        ArrayList chapters;
        Intrinsics.checkNotNullParameter((Object)book, (String)"book");
        ArrayList arrayList = book.isEpub() ? EpubFile.Companion.getChapterList(book) : (book.isUmd() ? UmdFile.Companion.getChapterList(book) : (book.isCbz() ? CbzFile.Companion.getChapterList(book) : (chapters = book.isPdf() ? PdfFile.Companion.getChapterList(book) : TextFile.Companion.getChapterList(book))));
        if (chapters.isEmpty()) {
            throw new TocEmptyException(Intrinsics.stringPlus((String)"Chapterlist is empty  ", (Object)book.getLocalFile()));
        }
        return chapters;
    }

    @Nullable
    public final String getContent(@NotNull Book book, @NotNull BookChapter chapter) {
        Intrinsics.checkNotNullParameter((Object)book, (String)"book");
        Intrinsics.checkNotNullParameter((Object)chapter, (String)"chapter");
        return book.isEpub() ? EpubFile.Companion.getContent(book, chapter) : (book.isUmd() ? UmdFile.Companion.getContent(book, chapter) : (book.isCbz() ? CbzFile.Companion.getContent(book, chapter) : (book.isPdf() ? PdfFile.Companion.getContent(book, chapter) : TextFile.Companion.getContent(book, chapter))));
    }

    @NotNull
    public final Pair<String, String> analyzeNameAuthor(@NotNull String fileName) {
        Intrinsics.checkNotNullParameter((Object)fileName, (String)"fileName");
        String tempFileName = StringsKt.substringBeforeLast$default((String)fileName, (String)".", null, (int)2, null);
        String name = null;
        Object author = null;
        for (Pattern pattern : nameAuthorPatterns) {
            Matcher matcher;
            Matcher matcher2 = pattern.matcher(tempFileName);
            boolean bl = false;
            boolean bl2 = false;
            Matcher it = matcher2;
            boolean bl3 = false;
            Matcher matcher3 = matcher = it.find() ? matcher2 : null;
            if (matcher == null) continue;
            matcher2 = matcher;
            bl = false;
            bl2 = false;
            Matcher $this$analyzeNameAuthor_u24lambda_u2d1 = matcher2;
            boolean bl4 = false;
            String string = $this$analyzeNameAuthor_u24lambda_u2d1.group(2);
            Intrinsics.checkNotNull((Object)string);
            name = string;
            String string2 = $this$analyzeNameAuthor_u24lambda_u2d1.group(1);
            String group1 = string2 == null ? "" : string2;
            String string3 = $this$analyzeNameAuthor_u24lambda_u2d1.group(3);
            String group3 = string3 == null ? "" : string3;
            author = BookHelp.INSTANCE.formatBookAuthor(Intrinsics.stringPlus((String)group1, (Object)group3));
            return new Pair((Object)name, author);
        }
        name = BookHelp.INSTANCE.formatBookName(tempFileName);
        String string = BookHelp.INSTANCE.formatBookAuthor(StringsKt.replace$default((String)tempFileName, (String)name, (String)"", (boolean)false, (int)4, null));
        int n = 0;
        boolean bl = false;
        String it = string;
        boolean bl5 = false;
        boolean bl6 = it.length() != tempFileName.length();
        String string4 = bl6 ? string : null;
        author = string4 == null ? "" : string4;
        return new Pair((Object)name, author);
    }

    public final void deleteBook(@NotNull Book book) {
        Intrinsics.checkNotNullParameter((Object)book, (String)"book");
        boolean bl = false;
        try {
            Object object = Result.Companion;
            boolean bl2 = false;
            File bookFile = book.getLocalFile();
            if ((book.isLocalTxt() || book.isUmd()) && bookFile.exists()) {
                bookFile.delete();
            }
            if (book.isEpub()) {
                File file = bookFile.getParentFile();
                Intrinsics.checkNotNullExpressionValue((Object)file, (String)"bookFile.parentFile");
                bookFile = file;
                if (bookFile.exists()) {
                    FileUtils.INSTANCE.delete(bookFile, true);
                }
            }
            Unit unit = Unit.INSTANCE;
            boolean bl3 = false;
            object = Result.constructor-impl((Object)unit);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl4 = false;
            Object object = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
        }
    }

    static {
        Pattern[] patternArray = new Pattern[]{Pattern.compile("(.*?)\u300a([^\u300a\u300b]+)\u300b.*?\u4f5c\u8005\uff1a(.*)"), Pattern.compile("(.*?)\u300a([^\u300a\u300b]+)\u300b(.*)"), Pattern.compile("(^)(.+) \u4f5c\u8005\uff1a(.+)$"), Pattern.compile("(^)(.+) by (.+)$")};
        nameAuthorPatterns = patternArray;
    }
}

