/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookChapter
 *  io.legado.app.model.localBook.PdfFile
 *  io.legado.app.model.localBook.PdfFile$Companion
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  okhttp3.internal.Util
 *  org.apache.pdfbox.pdmodel.PDDocument
 *  org.apache.pdfbox.pdmodel.PDPage
 *  org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline
 *  org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem
 *  org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model.localBook;

import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.model.localBook.PdfFile;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 '2\u00020\u0001:\u0001'B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u001b\u001a\u00020\u0017H\u0002J$\u0010\u001c\u001a\u001e\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\t0\u001dH\u0002J&\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\u001fH\u0002J\b\u0010&\u001a\u00020\u001fH\u0002R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR(\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006("}, d2={"Lio/legado/app/model/localBook/PdfFile;", "", "book", "Lio/legado/app/data/entities/Book;", "(Lio/legado/app/data/entities/Book;)V", "getBook", "()Lio/legado/app/data/entities/Book;", "setBook", "cover", "Ljava/io/InputStream;", "getCover", "()Ljava/io/InputStream;", "setCover", "(Ljava/io/InputStream;)V", "info", "", "", "getInfo", "()Ljava/util/Map;", "setInfo", "(Ljava/util/Map;)V", "getChapterList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/BookChapter;", "getChapterListByOutline", "getChapterListByPage", "getContent", "chapter", "parseBookInfo", "Lkotlin/Pair;", "processOutline", "", "document", "Lorg/apache/pdfbox/pdmodel/PDDocument;", "chapterList", "outline", "Lorg/apache/pdfbox/pdmodel/interactive/documentnavigation/outline/PDOutlineNode;", "upBookInfo", "updateCover", "Companion", "reader-pro"})
public final class PdfFile {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Book book;
    @Nullable
    private Map<String, Object> info;
    @Nullable
    private InputStream cover;
    @Nullable
    private static PdfFile cFile;

    public PdfFile(@NotNull Book book) {
        Intrinsics.checkNotNullParameter((Object)book, (String)"book");
        this.book = book;
    }

    @NotNull
    public final Book getBook() {
        return this.book;
    }

    public final void setBook(@NotNull Book book) {
        Intrinsics.checkNotNullParameter((Object)book, (String)"<set-?>");
        this.book = book;
    }

    @Nullable
    public final Map<String, Object> getInfo() {
        return this.info;
    }

    public final void setInfo(@Nullable Map<String, Object> map) {
        this.info = map;
    }

    @Nullable
    public final InputStream getCover() {
        return this.cover;
    }

    public final void setCover(@Nullable InputStream inputStream) {
        this.cover = inputStream;
    }

    private final Pair<Map<String, Object>, InputStream> parseBookInfo() {
        return new Pair((Object)this.info, (Object)this.cover);
    }

    private final void upBookInfo() {
        Pair result2 = this.parseBookInfo();
        if (result2.getFirst() != null) {
            Map info;
            Object object = result2.getFirst();
            if (object == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<kotlin.String, kotlin.Any>");
            }
            Map bookInfo = (Map)object;
            Map map = (Map)bookInfo.get("ComicInfo");
            Map map2 = info = map == null ? null : map;
            map = map2 == null ? null : map2.get("Title");
            this.book.setName((String)(map == null ? this.book.getName() : map));
            map2 = info;
            map = map2 == null ? null : map2.get("Writer");
            this.book.setAuthor((String)(map == null ? this.book.getAuthor() : map));
        }
        this.updateCover();
    }

    private final void updateCover() {
    }

    private final String getContent(BookChapter chapter) {
        return "";
    }

    private final ArrayList<BookChapter> getChapterList() {
        CharSequence charSequence = this.book.getTocUrl();
        boolean bl = false;
        if (charSequence.length() == 0) {
            this.book.setTocUrl("page");
        }
        if (Intrinsics.areEqual((Object)this.book.getTocUrl(), (Object)"page")) {
            return this.getChapterListByPage();
        }
        return this.getChapterListByOutline();
    }

    private final ArrayList<BookChapter> getChapterListByPage() {
        BookChapter bookChapter;
        ArrayList<BookChapter> chapterList = new ArrayList<BookChapter>();
        int n = 0;
        PDDocument document = PDDocument.load((File)this.book.getLocalFile());
        int n2 = document.getNumberOfPages();
        if (n < n2) {
            do {
                int pageIndex = n++;
                String name = "output-" + pageIndex + ".png";
                BookChapter chapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                chapter.setTitle(name);
                chapter.setIndex(pageIndex);
                chapter.setBookUrl(this.book.getBookUrl());
                chapter.setUrl(name);
                chapter.setStart(Long.valueOf(pageIndex));
                chapter.setEnd(Long.valueOf(pageIndex));
                chapterList.add(chapter);
            } while (n < n2);
        }
        this.book.setLatestChapterTitle((bookChapter = (BookChapter)CollectionsKt.lastOrNull((List)chapterList)) == null ? null : bookChapter.getTitle());
        this.book.setTotalChapterNum(chapterList.size());
        Intrinsics.checkNotNullExpressionValue((Object)document, (String)"document");
        Util.closeQuietly((Closeable)((Closeable)document));
        return chapterList;
    }

    private final ArrayList<BookChapter> getChapterListByOutline() {
        ArrayList<BookChapter> chapterList = new ArrayList<BookChapter>();
        PDDocument document = PDDocument.load((File)this.book.getLocalFile());
        PDDocumentOutline outline = document.getDocumentCatalog().getDocumentOutline();
        if (outline == null) {
            return chapterList;
        }
        Intrinsics.checkNotNullExpressionValue((Object)document, (String)"document");
        this.processOutline(document, chapterList, (PDOutlineNode)outline);
        if (chapterList.size() > 0) {
            chapterList.get(chapterList.size() - 1).setEnd(Long.valueOf(document.getNumberOfPages()));
        }
        Util.closeQuietly((Closeable)((Closeable)document));
        return chapterList;
    }

    private final void processOutline(PDDocument document, ArrayList<BookChapter> chapterList, PDOutlineNode outline) {
        PDOutlineItem current = outline.getFirstChild();
        while (current != null) {
            BookChapter chapter22;
            PDPage page = current.findDestinationPage(document);
            int pageIndex = document.getDocumentCatalog().getPages().indexOf(page);
            if (chapterList.size() == 0 && pageIndex >= 1) {
                chapter22 = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                chapter22.setTitle("\u9996\u7ae0");
                chapter22.setIndex(0);
                chapter22.setBookUrl(this.book.getBookUrl());
                chapter22.setUrl("chapter-0");
                chapter22.setStart(Long.valueOf(0L));
                chapter22.setEnd(Long.valueOf(pageIndex));
                chapterList.add(chapter22);
            }
            if (chapterList.size() > 0) {
                Long l = chapterList.get(chapterList.size() - 1).getStart();
                long chapter22 = pageIndex;
                if (l != null && l == chapter22) {
                    current = current.getNextSibling();
                    continue;
                }
                chapter22 = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                String string = current.getTitle();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"current.getTitle()");
                chapter22.setTitle(string);
                chapter22.setIndex(chapterList.size());
                chapter22.setBookUrl(this.book.getBookUrl());
                chapter22.setUrl(Intrinsics.stringPlus((String)"chapter-", (Object)chapterList.size()));
                chapter22.setStart(Long.valueOf(pageIndex));
                chapterList.get(chapterList.size() - 1).setEnd(Long.valueOf((long)pageIndex - 1L));
                chapterList.add(chapter22);
            }
            if (current.hasChildren()) {
                PDOutlineItem pDOutlineItem = current;
                this.processOutline(document, chapterList, (PDOutlineNode)pDOutlineItem);
            }
            current = current.getNextSibling();
        }
    }

    public static final /* synthetic */ PdfFile access$getCFile$cp() {
        return cFile;
    }

    public static final /* synthetic */ void access$setCFile$cp(PdfFile pdfFile) {
        cFile = pdfFile;
    }

    public static final /* synthetic */ ArrayList access$getChapterList(PdfFile $this) {
        return $this.getChapterList();
    }

    public static final /* synthetic */ String access$getContent(PdfFile $this, BookChapter chapter) {
        return $this.getContent(chapter);
    }

    public static final /* synthetic */ void access$updateCover(PdfFile $this) {
        $this.updateCover();
    }

    public static final /* synthetic */ void access$upBookInfo(PdfFile $this) {
        $this.upBookInfo();
    }
}

