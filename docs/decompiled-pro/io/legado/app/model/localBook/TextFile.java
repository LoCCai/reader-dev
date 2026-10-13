/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookChapter
 *  io.legado.app.data.entities.TxtTocRule
 *  io.legado.app.help.DefaultData
 *  io.legado.app.model.localBook.LocalBook
 *  io.legado.app.model.localBook.TextFile
 *  io.legado.app.model.localBook.TextFile$Companion
 *  io.legado.app.utils.EncodingDetect
 *  io.legado.app.utils.MD5Utils
 *  io.legado.app.utils.StringUtils
 *  io.legado.app.utils.Utf8BomUtils
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.io.CloseableKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.model.localBook;

import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.data.entities.TxtTocRule;
import io.legado.app.help.DefaultData;
import io.legado.app.model.localBook.LocalBook;
import io.legado.app.model.localBook.TextFile;
import io.legado.app.utils.EncodingDetect;
import io.legado.app.utils.MD5Utils;
import io.legado.app.utils.StringUtils;
import io.legado.app.utils.Utf8BomUtils;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J,\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u00102\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u0014H\u0002J\u0016\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u0010J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"Lio/legado/app/model/localBook/TextFile;", "", "book", "Lio/legado/app/data/entities/Book;", "(Lio/legado/app/data/entities/Book;)V", "blank", "", "bufferSize", "", "charset", "Ljava/nio/charset/Charset;", "maxLengthWithNoToc", "maxLengthWithToc", "analyze", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/BookChapter;", "Lkotlin/collections/ArrayList;", "pattern", "Ljava/util/regex/Pattern;", "fileStart", "", "fileEnd", "getChapterList", "getTocRule", "content", "", "getTocRules", "", "Lio/legado/app/data/entities/TxtTocRule;", "Companion", "reader-pro"})
public final class TextFile {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Book book;
    private final byte blank;
    private final int bufferSize;
    private final int maxLengthWithNoToc;
    private final int maxLengthWithToc;
    @NotNull
    private Charset charset;

    public TextFile(@NotNull Book book) {
        Intrinsics.checkNotNullParameter((Object)book, (String)"book");
        this.book = book;
        this.blank = (byte)10;
        this.bufferSize = 512000;
        this.maxLengthWithNoToc = 10240;
        this.maxLengthWithToc = 102400;
        this.charset = this.book.fileCharset();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    @NotNull
    public final ArrayList<BookChapter> getChapterList() throws FileNotFoundException {
        if (this.book.getCharset() == null || StringsKt.isBlank((CharSequence)this.book.getTocUrl())) {
            Closeable closeable = LocalBook.INSTANCE.getBookInputStream(this.book);
            boolean bl = false;
            boolean bl2 = false;
            Throwable throwable = null;
            try {
                InputStream bis = (InputStream)closeable;
                boolean bl3 = false;
                byte[] buffer = new byte[this.bufferSize];
                int length = bis.read(buffer);
                Object object = this.book.getCharset();
                int n = 0;
                boolean bl4 = false;
                if (object == null || StringsKt.isBlank((CharSequence)object)) {
                    object = buffer;
                    n = 0;
                    byte[] byArray = Arrays.copyOf((byte[])object, length);
                    Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"java.util.Arrays.copyOf(this, newSize)");
                    this.book.setCharset(EncodingDetect.INSTANCE.getEncode(byArray));
                }
                this.charset = this.book.fileCharset();
                if (StringsKt.isBlank((CharSequence)this.book.getTocUrl())) {
                    n = 0;
                    Object object2 = this.charset;
                    boolean bl5 = false;
                    String blockContent = new String(buffer, n, length, (Charset)object2);
                    Pattern pattern = this.getTocRule(blockContent);
                    this.book.setTocUrl((String)(pattern == null ? "" : ((object2 = pattern.pattern()) == null ? "" : object2)));
                }
                bis = Unit.INSTANCE;
            }
            catch (Throwable bis) {
                throwable = bis;
                throw bis;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
            }
        }
        String string = this.book.getTocUrl();
        int n = 8;
        boolean bis = false;
        Pattern pattern = Pattern.compile(string, n);
        Intrinsics.checkNotNullExpressionValue((Object)pattern, (String)"java.util.regex.Pattern.compile(this, flags)");
        ArrayList toc = this.analyze(pattern);
        Iterable $this$forEachIndexed$iv = toc;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void bookChapter;
            int n2 = index$iv++;
            boolean bl = false;
            if (n2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            BookChapter bookChapter2 = (BookChapter)item$iv;
            int index = n2;
            boolean bl6 = false;
            bookChapter.setIndex(index);
            bookChapter.setBookUrl(this.book.getBookUrl());
            bookChapter.setUrl(MD5Utils.INSTANCE.md5Encode16(this.book.getOriginName() + index + bookChapter.getTitle()));
        }
        this.book.setLatestChapterTitle(((BookChapter)CollectionsKt.last((List)toc)).getTitle());
        this.book.setTotalChapterNum(toc.size());
        return toc;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    private final ArrayList<BookChapter> analyze(Pattern pattern) {
        Object object = pattern;
        object = object == null ? null : ((Pattern)object).pattern();
        boolean bl = false;
        boolean bl2 = false;
        if (object == null || object.length() == 0) {
            return TextFile.analyze$default((TextFile)this, (long)0L, (long)0L, (int)3, null);
        }
        object = pattern;
        if (object == null) {
            return TextFile.analyze$default((TextFile)this, (long)0L, (long)0L, (int)3, null);
        }
        bl = false;
        ArrayList<BookChapter> toc = new ArrayList<BookChapter>();
        Closeable closeable = LocalBook.INSTANCE.getBookInputStream(this.book);
        bl2 = false;
        boolean bl3 = false;
        Throwable throwable = null;
        try {
            InputStream bis = (InputStream)closeable;
            boolean bl4 = false;
            String blockContent = null;
            long curOffset = 0L;
            int length = 0;
            byte[] buffer = new byte[this.bufferSize];
            int bufferStart = 3;
            bis.read(buffer, 0, 3);
            if (Utf8BomUtils.INSTANCE.hasBom(buffer)) {
                bufferStart = 0;
                curOffset = 3L;
            }
            while (true) {
                int n = bis.read(buffer, bufferStart, this.bufferSize - bufferStart);
                int n2 = 0;
                boolean bl5 = false;
                int it22 = n;
                boolean bl6 = false;
                length = it22;
                if (n <= 0) break;
                int end = bufferStart + length;
                if (end == this.bufferSize && 0 <= (n2 = bufferStart + length - 1)) {
                    do {
                        int i;
                        if (buffer[i = n2--] != this.blank) continue;
                        end = i;
                        break;
                    } while (0 <= n2);
                }
                n2 = 0;
                Charset i = this.charset;
                it22 = 0;
                blockContent = new String(buffer, n2, end, i);
                ArraysKt.copyInto((byte[])buffer, (byte[])buffer, (int)0, (int)end, (int)(bufferStart + length));
                bufferStart = bufferStart + length - end;
                length = end;
                int seekPos = 0;
                Matcher it22 = pattern.matcher(blockContent);
                Intrinsics.checkNotNullExpressionValue((Object)it22, (String)"pattern.matcher(blockContent)");
                Matcher matcher = it22;
                while (matcher.find()) {
                    Collection lastChapter;
                    BookChapter curChapter;
                    Object object2;
                    CharSequence lastTitle;
                    Long l;
                    long lastStart;
                    String chapterContent;
                    int chapterStart = matcher.start();
                    String string = blockContent;
                    boolean bl7 = false;
                    Intrinsics.checkNotNullExpressionValue((Object)string.substring(seekPos, chapterStart), (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    String string2 = chapterContent;
                    Charset charset = this.charset;
                    boolean bl8 = false;
                    String string3 = string2;
                    if (string3 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    byte[] byArray = string3.getBytes(charset);
                    Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"(this as java.lang.String).getBytes(charset)");
                    int chapterLength = byArray.length;
                    BookChapter bookChapter = (BookChapter)CollectionsKt.lastOrNull((List)toc);
                    long l2 = bookChapter == null ? curOffset : (lastStart = (l = bookChapter.getStart()) == null ? curOffset : l);
                    if (this.book.getSplitLongChapter() && curOffset + (long)chapterLength - lastStart > (long)this.maxLengthWithToc) {
                        Unit unit;
                        boolean bl9;
                        CharSequence charSequence;
                        int n3;
                        Unit unit2;
                        bookChapter = (BookChapter)CollectionsKt.lastOrNull((List)toc);
                        if (bookChapter == null) {
                            unit2 = null;
                        } else {
                            l = bookChapter;
                            boolean bl10 = false;
                            boolean bl11 = false;
                            Long it = l;
                            boolean bl12 = false;
                            it.setEnd(it.getStart());
                            unit2 = Unit.INSTANCE;
                        }
                        l = (BookChapter)CollectionsKt.lastOrNull((List)toc);
                        lastTitle = l == null ? null : l.getTitle();
                        object2 = lastTitle;
                        if (object2 == null) {
                            n3 = 0;
                        } else {
                            int n4;
                            byte[] byArray2;
                            charSequence = object2;
                            Charset charset2 = this.charset;
                            bl9 = false;
                            Intrinsics.checkNotNullExpressionValue((Object)((String)charSequence).getBytes(charset2), (String)"(this as java.lang.String).getBytes(charset)");
                            n3 = byArray2 == null ? 0 : (n4 = byArray2.length);
                        }
                        int lastTitleLength22 = n3;
                        ArrayList chapters = this.analyze(lastStart + (long)lastTitleLength22, curOffset + (long)chapterLength);
                        CharSequence charSequence2 = lastTitle;
                        if (charSequence2 == null) {
                            unit = null;
                        } else {
                            CharSequence charSequence3 = charSequence2;
                            boolean bl13 = false;
                            bl9 = false;
                            CharSequence it = charSequence3;
                            boolean bl14 = false;
                            Iterable $this$forEachIndexed$iv = chapters;
                            boolean $i$f$forEachIndexed = false;
                            int index$iv = 0;
                            for (Object item$iv : $this$forEachIndexed$iv) {
                                void bookChapter2;
                                int n5 = index$iv++;
                                boolean bl15 = false;
                                if (n5 < 0) {
                                    CollectionsKt.throwIndexOverflow();
                                }
                                BookChapter bookChapter3 = (BookChapter)item$iv;
                                int index = n5;
                                boolean bl16 = false;
                                bookChapter2.setTitle("" + lastTitle + '(' + (index + 1) + ')');
                            }
                            unit = Unit.INSTANCE;
                        }
                        toc.addAll(chapters);
                        BookChapter curChapter2 = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                        charSequence = matcher.group();
                        Intrinsics.checkNotNullExpressionValue((Object)charSequence, (String)"matcher.group()");
                        curChapter2.setTitle((String)charSequence);
                        curChapter2.setStart(Long.valueOf(curOffset + (long)chapterLength));
                        toc.add(curChapter2);
                    } else if (seekPos == 0 && chapterStart != 0) {
                        if (toc.isEmpty()) {
                            lastTitle = StringUtils.INSTANCE.trim(chapterContent);
                            boolean lastTitleLength22 = false;
                            if (lastTitle.length() > 0) {
                                BookChapter qyChapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                                qyChapter.setTitle("\u524d\u8a00");
                                qyChapter.setStart(Long.valueOf(curOffset));
                                qyChapter.setEnd(Long.valueOf(chapterLength));
                                toc.add(qyChapter);
                            }
                            curChapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                            String lastTitleLength22 = matcher.group();
                            Intrinsics.checkNotNullExpressionValue((Object)lastTitleLength22, (String)"matcher.group()");
                            curChapter.setTitle(lastTitleLength22);
                            curChapter.setStart(Long.valueOf(chapterLength));
                            toc.add(curChapter);
                        } else {
                            lastChapter = (BookChapter)CollectionsKt.last((List)toc);
                            lastChapter.setVolume(StringsKt.isBlank((CharSequence)StringsKt.substringAfter$default((String)chapterContent, (String)lastChapter.getTitle(), null, (int)2, null)));
                            Long l3 = lastChapter.getEnd();
                            Intrinsics.checkNotNull((Object)l3);
                            lastChapter.setEnd(l3 + (long)chapterLength);
                            BookChapter curChapter32 = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                            object2 = matcher.group();
                            Intrinsics.checkNotNullExpressionValue((Object)object2, (String)"matcher.group()");
                            curChapter32.setTitle((String)object2);
                            curChapter32.setStart(lastChapter.getEnd());
                            toc.add(curChapter32);
                        }
                    } else {
                        lastChapter = toc;
                        boolean curChapter32 = false;
                        if (!lastChapter.isEmpty()) {
                            lastChapter = (BookChapter)CollectionsKt.last((List)toc);
                            lastChapter.setVolume(StringsKt.isBlank((CharSequence)StringsKt.substringAfter$default((String)chapterContent, (String)lastChapter.getTitle(), null, (int)2, null)));
                            Long l4 = lastChapter.getStart();
                            Intrinsics.checkNotNull((Object)l4);
                            long l5 = l4;
                            String curChapter32 = chapterContent;
                            object2 = this.charset;
                            boolean bl17 = false;
                            String string4 = curChapter32;
                            if (string4 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                            }
                            byte[] byArray3 = string4.getBytes((Charset)object2);
                            Intrinsics.checkNotNullExpressionValue((Object)byArray3, (String)"(this as java.lang.String).getBytes(charset)");
                            lastChapter.setEnd(l5 + (long)byArray3.length);
                            curChapter32 = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                            object2 = matcher.group();
                            Intrinsics.checkNotNullExpressionValue((Object)object2, (String)"matcher.group()");
                            curChapter32.setTitle((String)object2);
                            curChapter32.setStart(lastChapter.getEnd());
                            toc.add((BookChapter)curChapter32);
                        } else {
                            curChapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                            String string5 = matcher.group();
                            Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"matcher.group()");
                            curChapter.setTitle(string5);
                            curChapter.setStart(Long.valueOf(curOffset));
                            curChapter.setEnd(Long.valueOf(curOffset));
                            toc.add(curChapter);
                        }
                    }
                    seekPos += chapterContent.length();
                }
                curOffset += (long)length;
                BookChapter bookChapter = (BookChapter)CollectionsKt.lastOrNull((List)toc);
                if (bookChapter == null) continue;
                bookChapter.setEnd(Long.valueOf(curOffset));
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
        System.gc();
        System.runFinalization();
        return toc;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final ArrayList<BookChapter> analyze(long fileStart, long fileEnd) {
        boolean bl = false;
        ArrayList<BookChapter> toc = new ArrayList<BookChapter>();
        Closeable closeable = LocalBook.INSTANCE.getBookInputStream(this.book);
        boolean bl2 = false;
        boolean bl3 = false;
        Throwable throwable = null;
        try {
            Object object;
            int n;
            InputStream bis = (InputStream)closeable;
            boolean bl4 = false;
            int blockPos = 0;
            long curOffset = 0L;
            int chapterPos = 0;
            int length = 0;
            byte[] buffer = new byte[this.bufferSize];
            int bufferStart = 0;
            bufferStart = 3;
            if (fileStart == 0L) {
                bis.read(buffer, 0, 3);
                if (Utf8BomUtils.INSTANCE.hasBom(buffer)) {
                    bufferStart = 0;
                    curOffset = 3L;
                }
            } else {
                bis.skip(fileStart);
                curOffset = fileStart;
                bufferStart = 0;
            }
            while (fileEnd - curOffset - (long)bufferStart > 0L) {
                long l = this.bufferSize - bufferStart;
                long l2 = fileEnd - curOffset - (long)bufferStart;
                boolean bl5 = false;
                int n2 = bis.read(buffer, bufferStart, (int)Math.min(l, l2));
                boolean bl6 = false;
                n = 0;
                int it = n2;
                boolean bl7 = false;
                length = it;
                if (n2 <= 0) break;
                n2 = blockPos;
                blockPos = n2 + 1;
                int chapterOffset = 0;
                int strLength = length += bufferStart;
                chapterPos = 0;
                while (strLength > 0) {
                    n = chapterPos;
                    chapterPos = n + 1;
                    if (strLength > this.maxLengthWithNoToc) {
                        Long l3;
                        int end = length;
                        it = chapterOffset + this.maxLengthWithNoToc;
                        if (it < length) {
                            do {
                                int i;
                                if (buffer[i = it++] != this.blank) continue;
                                end = i;
                                break;
                            } while (it < length);
                        }
                        BookChapter chapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                        chapter.setTitle("" + '\u7b2c' + blockPos + "\u7ae0(" + chapterPos + ')');
                        BookChapter i = (BookChapter)CollectionsKt.lastOrNull((List)toc);
                        chapter.setStart(i == null ? Long.valueOf(curOffset) : ((l3 = i.getEnd()) == null ? Long.valueOf(curOffset) : l3));
                        Long l4 = chapter.getStart();
                        Intrinsics.checkNotNull((Object)l4);
                        chapter.setEnd(Long.valueOf(l4 + (long)end - (long)chapterOffset));
                        toc.add(chapter);
                        strLength -= end - chapterOffset;
                        chapterOffset = end;
                        continue;
                    }
                    ArraysKt.copyInto((byte[])buffer, (byte[])buffer, (int)0, (int)(length - strLength), (int)length);
                    length -= strLength;
                    bufferStart = strLength;
                    strLength = 0;
                }
                curOffset += (long)length;
            }
            if (bufferStart > 100 || toc.isEmpty()) {
                Long l;
                BookChapter chapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                chapter.setTitle("" + '\u7b2c' + blockPos + "\u7ae0(" + chapterPos + ')');
                BookChapter bookChapter = (BookChapter)CollectionsKt.lastOrNull((List)toc);
                chapter.setStart(bookChapter == null ? Long.valueOf(curOffset) : ((l = bookChapter.getEnd()) == null ? Long.valueOf(curOffset) : l));
                Long l5 = chapter.getStart();
                Intrinsics.checkNotNull((Object)l5);
                chapter.setEnd(Long.valueOf(l5 + (long)bufferStart));
                object = toc.add(chapter);
            } else {
                BookChapter bookChapter = (BookChapter)CollectionsKt.lastOrNull((List)toc);
                if (bookChapter == null) {
                    object = null;
                } else {
                    BookChapter bookChapter2 = bookChapter;
                    n = 0;
                    boolean bl8 = false;
                    BookChapter it = bookChapter2;
                    boolean bl9 = false;
                    Long l = it.getEnd();
                    Intrinsics.checkNotNull((Object)l);
                    it.setEnd(Long.valueOf(l + (long)bufferStart));
                    object = Unit.INSTANCE;
                }
            }
            Unit unit = object;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
        return toc;
    }

    static /* synthetic */ ArrayList analyze$default(TextFile textFile, long l, long l2, int n, Object object) {
        if ((n & 1) != 0) {
            l = 0L;
        }
        if ((n & 2) != 0) {
            l2 = Long.MAX_VALUE;
        }
        return textFile.analyze(l, l2);
    }

    private final Pattern getTocRule(String content) {
        List rules = CollectionsKt.reversed((Iterable)this.getTocRules());
        int maxCs = 1;
        Pattern tocPattern = null;
        for (TxtTocRule tocRule : rules) {
            Pattern pattern;
            String string = tocRule.getRule();
            int n = 8;
            int n2 = 0;
            Intrinsics.checkNotNullExpressionValue((Object)Pattern.compile(string, n), (String)"java.util.regex.Pattern.compile(this, flags)");
            Matcher matcher = pattern.matcher(content);
            int cs = 0;
            while (matcher.find()) {
                n2 = cs;
                cs = n2 + 1;
            }
            if (cs < maxCs) continue;
            maxCs = cs;
            tocPattern = pattern;
        }
        return tocPattern;
    }

    /*
     * WARNING - void declaration
     */
    private final List<TxtTocRule> getTocRules() {
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv = DefaultData.INSTANCE.getTxtTocRules();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            TxtTocRule it = (TxtTocRule)element$iv$iv;
            boolean bl = false;
            if (!it.getEnable()) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }
}

