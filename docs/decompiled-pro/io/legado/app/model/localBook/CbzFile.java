/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookChapter
 *  io.legado.app.model.localBook.CbzFile
 *  io.legado.app.model.localBook.CbzFile$Companion
 *  io.legado.app.utils.FileUtils
 *  io.legado.app.utils.MD5Utils
 *  io.legado.app.utils.XmlUtils
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model.localBook;

import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.model.localBook.CbzFile;
import io.legado.app.utils.FileUtils;
import io.legado.app.utils.MD5Utils;
import io.legado.app.utils.XmlUtils;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0019\u001a\u00020\u0017H\u0002J$\u0010\u001a\u001a\u001e\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\t0\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0002R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR(\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006 "}, d2={"Lio/legado/app/model/localBook/CbzFile;", "", "book", "Lio/legado/app/data/entities/Book;", "(Lio/legado/app/data/entities/Book;)V", "getBook", "()Lio/legado/app/data/entities/Book;", "setBook", "cover", "Ljava/io/InputStream;", "getCover", "()Ljava/io/InputStream;", "setCover", "(Ljava/io/InputStream;)V", "info", "", "", "getInfo", "()Ljava/util/Map;", "setInfo", "(Ljava/util/Map;)V", "getChapterList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/BookChapter;", "getContent", "chapter", "parseBookInfo", "Lkotlin/Pair;", "upBookInfo", "", "updateCover", "Companion", "reader-pro"})
public final class CbzFile {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Book book;
    @Nullable
    private Map<String, Object> info;
    @Nullable
    private InputStream cover;
    @Nullable
    private static CbzFile cFile;

    public CbzFile(@NotNull Book book) {
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
        if (this.cover != null || this.info != null) {
            return new Pair((Object)this.info, (Object)this.cover);
        }
        ZipFile zf = new ZipFile(this.book.getLocalFile());
        Enumeration<? extends ZipEntry> entries = zf.entries();
        Object[] objectArray = new String[]{"jpg", "jpeg", "gif", "png", "bmp", "webp", "svg"};
        List imageExt = CollectionsKt.listOf((Object[])objectArray);
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if (zipEntry == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
            }
            ZipEntry zipEntry2 = zipEntry;
            if (!zipEntry2.isDirectory()) {
                Object object;
                String name = zipEntry2.getName();
                if (name.equals("ComicInfo.xml")) {
                    InputStream inputStream = zf.getInputStream(zipEntry2);
                    object = inputStream;
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"inputStream");
                    this.info = XmlUtils.INSTANCE.xml2map(object);
                } else if (this.cover == null) {
                    String ext;
                    Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
                    object = FileUtils.getFileExtetion$default((FileUtils)FileUtils.INSTANCE, (String)name, null, (int)2, null);
                    boolean bl = false;
                    Object object2 = object;
                    if (object2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    Intrinsics.checkNotNullExpressionValue((Object)((String)object2).toLowerCase(Locale.ROOT), (String)"(this as java.lang.Strin\u2026.toLowerCase(Locale.ROOT)");
                    if (imageExt.contains(ext)) {
                        this.cover = zf.getInputStream(zipEntry2);
                    }
                }
            }
            if (this.cover == null || this.info == null) continue;
        }
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
        Pair result2;
        String coverFile = Intrinsics.stringPlus((String)MD5Utils.INSTANCE.md5Encode16(this.book.getBookUrl()), (Object)".jpg");
        String[] stringArray = new String[]{this.book.getUserNameSpace(), "covers", coverFile};
        String relativeCoverUrl = ((Object)Paths.get("assets", stringArray)).toString();
        this.book.setCoverUrl(Intrinsics.stringPlus((String)"/", (Object)StringsKt.replace$default((String)relativeCoverUrl, (String)"\\", (String)"/", (boolean)false, (int)4, null)));
        String[] stringArray2 = new String[]{"storage", relativeCoverUrl};
        String coverUrl = ((Object)Paths.get(this.book.workRoot(), stringArray2)).toString();
        if (!new File(coverUrl).exists() && (result2 = this.parseBookInfo()).getSecond() != null) {
            Object object = result2.getSecond();
            if (object == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.io.InputStream");
            }
            InputStream coverStream = (InputStream)object;
            FileUtils.INSTANCE.writeInputStream(coverUrl, coverStream);
        }
    }

    private final String getContent(BookChapter chapter) {
        return "";
    }

    private final ArrayList<BookChapter> getChapterList() {
        BookChapter bookChapter;
        ArrayList<BookChapter> chapterList = new ArrayList<BookChapter>();
        ZipFile zf = new ZipFile(this.book.getLocalFile());
        Enumeration<? extends ZipEntry> entries = zf.entries();
        int n = 0;
        ArrayList<String> imageFileList = new ArrayList<String>();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if (zipEntry == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
            }
            ZipEntry zipEntry2 = zipEntry;
            if (zipEntry2.isDirectory()) continue;
            String name = zipEntry2.getName();
            Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
            if (StringsKt.endsWith$default((String)name, (String)".xml", (boolean)false, (int)2, null)) continue;
            imageFileList.add(name);
        }
        CollectionsKt.sort((List)imageFileList);
        n = 0;
        int n2 = imageFileList.size();
        if (n < n2) {
            do {
                int i = n++;
                Object e = imageFileList.get(i);
                Intrinsics.checkNotNullExpressionValue(e, (String)"imageFileList.get(i)");
                String name = (String)e;
                BookChapter chapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                chapter.setTitle(name);
                chapter.setIndex(i);
                chapter.setBookUrl(this.book.getBookUrl());
                chapter.setUrl(name);
                chapterList.add(chapter);
            } while (n < n2);
        }
        this.book.setLatestChapterTitle((bookChapter = (BookChapter)CollectionsKt.lastOrNull((List)chapterList)) == null ? null : bookChapter.getTitle());
        this.book.setTotalChapterNum(chapterList.size());
        return chapterList;
    }

    public static final /* synthetic */ CbzFile access$getCFile$cp() {
        return cFile;
    }

    public static final /* synthetic */ void access$setCFile$cp(CbzFile cbzFile) {
        cFile = cbzFile;
    }

    public static final /* synthetic */ ArrayList access$getChapterList(CbzFile $this) {
        return $this.getChapterList();
    }

    public static final /* synthetic */ String access$getContent(CbzFile $this, BookChapter chapter) {
        return $this.getContent(chapter);
    }

    public static final /* synthetic */ void access$updateCover(CbzFile $this) {
        $this.updateCover();
    }

    public static final /* synthetic */ void access$upBookInfo(CbzFile $this) {
        $this.upBookInfo();
    }
}

