/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookChapter
 *  io.legado.app.model.localBook.EpubFile
 *  io.legado.app.model.localBook.EpubFile$Companion
 *  io.legado.app.utils.FileUtils
 *  io.legado.app.utils.HtmlFormatter
 *  io.legado.app.utils.MD5Utils
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  me.ag2s.epublib.domain.Author
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.domain.Metadata
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.Resources
 *  me.ag2s.epublib.domain.SpineReference
 *  me.ag2s.epublib.epub.EpubReader
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package io.legado.app.model.localBook;

import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.model.localBook.EpubFile;
import io.legado.app.utils.FileUtils;
import io.legado.app.utils.HtmlFormatter;
import io.legado.app.utils.MD5Utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import me.ag2s.epublib.domain.Author;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.Resources;
import me.ag2s.epublib.domain.SpineReference;
import me.ag2s.epublib.epub.EpubReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 '2\u00020\u0001:\u0001'B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0013H\u0002J\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\b\b\u0002\u0010\u0019\u001a\u00020\u001aJ\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\b\b\u0002\u0010\u001d\u001a\u00020\u001aJ\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u001f\u001a\u00020\u0017H\u0002J\u0012\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020\u0013H\u0002J\n\u0010#\u001a\u0004\u0018\u00010\tH\u0002J\b\u0010$\u001a\u00020%H\u0002J\u0006\u0010&\u001a\u00020%R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u0018\u0010\b\u001a\u0004\u0018\u00010\t8BX\u0082\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2={"Lio/legado/app/model/localBook/EpubFile;", "", "book", "Lio/legado/app/data/entities/Book;", "(Lio/legado/app/data/entities/Book;)V", "getBook", "()Lio/legado/app/data/entities/Book;", "setBook", "epubBook", "Lme/ag2s/epublib/domain/EpubBook;", "getEpubBook", "()Lme/ag2s/epublib/domain/EpubBook;", "mCharset", "Ljava/nio/charset/Charset;", "getBody", "Lorg/jsoup/nodes/Element;", "res", "Lme/ag2s/epublib/domain/Resource;", "startFragmentId", "", "endFragmentId", "getChapterList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/BookChapter;", "getChapterListBySpinAndToc", "useTocTitle", "", "getChapterListBySpine", "getChapterListByTocAndSpin", "useSpinTitle", "getContent", "chapter", "getImage", "Ljava/io/InputStream;", "href", "readEpub", "upBookInfo", "", "updateCover", "Companion", "reader-pro"})
public final class EpubFile {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Book book;
    @NotNull
    private Charset mCharset;
    @Nullable
    private EpubBook epubBook;
    @Nullable
    private static EpubFile eFile;

    public EpubFile(@NotNull Book book) {
        Intrinsics.checkNotNullParameter((Object)book, (String)"book");
        this.book = book;
        Charset charset = Charset.defaultCharset();
        Intrinsics.checkNotNullExpressionValue((Object)charset, (String)"defaultCharset()");
        this.mCharset = charset;
        try {
            charset = this.getEpubBook();
            if (charset != null) {
                Charset charset2 = charset;
                boolean bl = false;
                boolean bl2 = false;
                Charset it = charset2;
                boolean bl3 = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public final Book getBook() {
        return this.book;
    }

    public final void setBook(@NotNull Book book) {
        Intrinsics.checkNotNullParameter((Object)book, (String)"<set-?>");
        this.book = book;
    }

    private final EpubBook getEpubBook() {
        if (this.epubBook != null) {
            return this.epubBook;
        }
        this.epubBook = this.readEpub();
        return this.epubBook;
    }

    private final EpubBook readEpub() {
        try {
            File file = this.book.getLocalFile();
            return new EpubReader().readEpubLazy(new ZipFile(file), "utf-8");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private final String getContent(BookChapter chapter) {
        if (StringsKt.contains$default((CharSequence)chapter.getUrl(), (CharSequence)"titlepage.xhtml", (boolean)false, (int)2, null)) {
            return "<img src=\"cover.jpeg\" />";
        }
        EpubBook epubBook = this.getEpubBook();
        if (epubBook != null) {
            EpubBook epubBook2 = epubBook;
            boolean bl = false;
            boolean bl2 = false;
            EpubBook epubBook3 = epubBook2;
            boolean bl3 = false;
            String nextUrl = chapter.getVariable("nextUrl");
            String startFragmentId = chapter.getStartFragmentId();
            String endFragmentId = chapter.getEndFragmentId();
            Elements elements = new Elements();
            boolean isChapter = false;
            for (Resource res : epubBook3.getContents()) {
                String string;
                if (Intrinsics.areEqual((Object)StringsKt.substringBeforeLast$default((String)chapter.getUrl(), (String)"#", null, (int)2, null), (Object)res.getHref())) {
                    Intrinsics.checkNotNullExpressionValue((Object)res, (String)"res");
                    elements.add((Object)this.getBody(res, startFragmentId, endFragmentId));
                    isChapter = true;
                    if (nextUrl == null) break;
                    string = nextUrl;
                    if (!Intrinsics.areEqual((Object)res.getHref(), (Object)StringsKt.substringBeforeLast$default((String)string, (String)"#", null, (int)2, null))) continue;
                    break;
                }
                if (!isChapter) continue;
                if (Intrinsics.areEqual((Object)res.getHref(), (string = nextUrl) == null ? null : StringsKt.substringBeforeLast$default((String)string, (String)"#", null, (int)2, null))) break;
                Intrinsics.checkNotNullExpressionValue((Object)res, (String)"res");
                elements.add((Object)this.getBody(res, startFragmentId, endFragmentId));
            }
            String html = elements.outerHtml();
            long tag = 4L;
            if (this.getBook().getDelTag(tag)) {
                CharSequence charSequence = html;
                Intrinsics.checkNotNullExpressionValue((Object)charSequence, (String)"html");
                charSequence = charSequence;
                String string = "<ruby>\\s?([\\u4e00-\\u9fa5])\\s?.*?</ruby>";
                boolean bl4 = false;
                string = new Regex(string);
                String string2 = "$1";
                boolean bl5 = false;
                html = string.replace(charSequence, string2);
            }
            return HtmlFormatter.formatKeepImg$default((HtmlFormatter)HtmlFormatter.INSTANCE, (String)html, null, (int)2, null);
        }
        return null;
    }

    private final Element getBody(Resource res, String startFragmentId, String endFragmentId) {
        long tag;
        Elements elements;
        Object object = res.getData();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"res.data");
        Charset charset = this.mCharset;
        boolean bl = false;
        Element body = Jsoup.parse((String)new String((byte[])object, charset)).body();
        object = startFragmentId;
        boolean bl2 = false;
        bl = false;
        if (!(object == null || StringsKt.isBlank((CharSequence)object)) && (object = (Object)body.getElementById(startFragmentId)) != null && (elements = object.previousElementSiblings()) != null) {
            elements.remove();
        }
        object = endFragmentId;
        boolean bl3 = false;
        bl = false;
        if (!(object == null || StringsKt.isBlank((CharSequence)object)) && !Intrinsics.areEqual((Object)endFragmentId, (Object)startFragmentId) && (object = (Object)body.getElementById(endFragmentId)) != null) {
            Object object2 = object;
            bl = false;
            boolean bl4 = false;
            Object $this$getBody_u24lambda_u2d2 = object2;
            boolean bl5 = false;
            $this$getBody_u24lambda_u2d2.nextElementSiblings().remove();
            $this$getBody_u24lambda_u2d2.remove();
        }
        if (this.book.getDelTag(tag = 2L)) {
            body.getElementsByTag("h1").remove();
            body.getElementsByTag("h2").remove();
            body.getElementsByTag("h3").remove();
            body.getElementsByTag("h4").remove();
            body.getElementsByTag("h5").remove();
            body.getElementsByTag("h6").remove();
        }
        Elements children = body.children();
        children.select("script").remove();
        children.select("style").remove();
        Intrinsics.checkNotNullExpressionValue((Object)body, (String)"body");
        return body;
    }

    private final InputStream getImage(String href) {
        Resource resource;
        Resources resources;
        String abHref = StringsKt.replace$default((String)href, (String)"../", (String)"", (boolean)false, (int)4, null);
        EpubBook epubBook = this.getEpubBook();
        return epubBook == null ? null : ((resources = epubBook.getResources()) == null ? null : ((resource = resources.getByHref(abHref)) == null ? null : resource.getInputStream()));
    }

    private final void upBookInfo() {
        if (this.getEpubBook() == null) {
            eFile = null;
            this.book.setIntro("\u4e66\u7c4d\u5bfc\u5165\u5f02\u5e38");
        } else {
            EpubBook epubBook = this.getEpubBook();
            Intrinsics.checkNotNull((Object)epubBook);
            me.ag2s.epublib.domain.Metadata metadata = epubBook.getMetadata();
            CharSequence charSequence = metadata.getFirstTitle();
            Intrinsics.checkNotNullExpressionValue((Object)charSequence, (String)"metadata.firstTitle");
            this.book.setName((String)charSequence);
            charSequence = this.book.getName();
            boolean bl = false;
            if (charSequence.length() == 0) {
                this.book.setName(StringsKt.replace$default((String)this.book.getOriginName(), (String)".epub", (String)"", (boolean)false, (int)4, null));
            }
            if (metadata.getAuthors().size() > 0) {
                CharSequence charSequence2 = ((Author)metadata.getAuthors().get(0)).toString();
                Intrinsics.checkNotNullExpressionValue((Object)charSequence2, (String)"metadata.authors[0].toString()");
                charSequence2 = charSequence2;
                String string = "^, |, $";
                boolean bl2 = false;
                string = new Regex(string);
                String string2 = "";
                boolean bl3 = false;
                String author = string.replace(charSequence2, string2);
                this.book.setAuthor(author);
            }
            if (metadata.getDescriptions().size() > 0) {
                this.book.setIntro(Jsoup.parse((String)((String)metadata.getDescriptions().get(0))).text());
            }
            this.updateCover();
        }
    }

    public final void updateCover() {
        byte[] byArray;
        Resource resource;
        String coverFile = Intrinsics.stringPlus((String)MD5Utils.INSTANCE.md5Encode16(this.book.getBookUrl()), (Object)".jpg");
        String[] stringArray = new String[]{this.book.getUserNameSpace(), "covers", coverFile};
        String relativeCoverUrl = ((Object)Paths.get("assets", stringArray)).toString();
        this.book.setCoverUrl(Intrinsics.stringPlus((String)"/", (Object)StringsKt.replace$default((String)relativeCoverUrl, (String)"\\", (String)"/", (boolean)false, (int)4, null)));
        String[] stringArray2 = new String[]{"storage", relativeCoverUrl};
        String coverUrl = ((Object)Paths.get(this.book.workRoot(), stringArray2)).toString();
        if (!new File(coverUrl).exists() && (stringArray2 = this.getEpubBook()) != null && (resource = stringArray2.getCoverImage()) != null && (byArray = resource.getData()) != null) {
            byte[] byArray2 = byArray;
            boolean bl = false;
            boolean bl2 = false;
            byte[] it = byArray2;
            boolean bl3 = false;
            FileUtils.INSTANCE.writeBytes(coverUrl, it);
        }
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    public final ArrayList<BookChapter> getChapterListBySpine() {
        block8: {
            chapterList = new ArrayList<BookChapter>();
            var2_2 = this.getEpubBook();
            if (var2_2 == null || (var3_3 = var2_2.getSpine()) == null || (var4_4 = var3_3.getSpineReferences()) == null) break block8;
            $this$forEachIndexed$iv = var4_4;
            $i$f$forEachIndexed = false;
            index$iv = 0;
            for (T item$iv : $this$forEachIndexed$iv) {
                var10_10 = index$iv++;
                var11_11 = false;
                if (var10_10 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                var12_12 = (SpineReference)item$iv;
                index = var10_10;
                $i$a$-forEachIndexed-EpubFile$getChapterListBySpine$1 = false;
                resource = spinResource.getResource();
                title = resource.getTitle();
                var17_17 = title;
                var18_20 = false;
                var19_22 = false;
                if (var17_17 == null || var17_17.length() == 0) {
                    try {
                        var18_21 = resource.getData();
                        Intrinsics.checkNotNullExpressionValue((Object)var18_21, (String)"resource.data");
                        var19_23 = this.mCharset;
                        var20_24 = false;
                        doc = Jsoup.parse((String)new String(var18_21, var19_23));
                        elements = doc.getElementsByTag("title");
                        if (elements.size() > 0) {
                            title = ((Element)elements.get(0)).text();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                chapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                chapter.setIndex(index);
                chapter.setBookUrl(this.getBook().getBookUrl());
                var18_19 = resource.getHref();
                Intrinsics.checkNotNullExpressionValue((Object)var18_19, (String)"resource.href");
                chapter.setUrl((String)var18_19);
                if (index != 0) ** GOTO lbl-1000
                var18_19 = title;
                Intrinsics.checkNotNullExpressionValue((Object)var18_19, (String)"title");
                var18_19 = var18_19;
                var19_22 = false;
                if (var18_19.length() == 0) {
                    chapter.setTitle("\u5c01\u9762");
                } else lbl-1000:
                // 2 sources

                {
                    chapter.setTitle((String)((var18_19 = title) == null ? "" : var18_19));
                }
                chapterList.add(chapter);
            }
        }
        this.book.setLatestChapterTitle((var2_2 = (BookChapter)CollectionsKt.lastOrNull((List)chapterList)) == null ? null : var2_2.getTitle());
        this.book.setTotalChapterNum(chapterList.size());
        return chapterList;
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    public final ArrayList<BookChapter> getChapterList() {
        block8: {
            chapterList = new ArrayList<BookChapter>();
            var2_2 = this.getEpubBook();
            if (var2_2 == null || (var3_3 = var2_2.getTableOfContents()) == null || (var4_4 = var3_3.getAllUniqueResources()) == null) break block8;
            $this$forEachIndexed$iv = var4_4;
            $i$f$forEachIndexed = false;
            index$iv = 0;
            for (T item$iv : $this$forEachIndexed$iv) {
                var10_10 = index$iv++;
                var11_11 = false;
                if (var10_10 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                var12_12 = (Resource)item$iv;
                index = var10_10;
                $i$a$-forEachIndexed-EpubFile$getChapterList$1 = false;
                title = resource.getTitle();
                var16_16 = title;
                var17_19 = false;
                var18_21 = false;
                if (var16_16 == null || var16_16.length() == 0) {
                    try {
                        var17_20 = resource.getData();
                        Intrinsics.checkNotNullExpressionValue((Object)var17_20, (String)"resource.data");
                        var18_22 = this.mCharset;
                        var19_23 = false;
                        doc = Jsoup.parse((String)new String(var17_20, var18_22));
                        elements = doc.getElementsByTag("title");
                        if (elements.size() > 0) {
                            title = ((Element)elements.get(0)).text();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                chapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                chapter.setIndex(index);
                chapter.setBookUrl(this.getBook().getBookUrl());
                var17_18 = resource.getHref();
                Intrinsics.checkNotNullExpressionValue((Object)var17_18, (String)"resource.href");
                chapter.setUrl((String)var17_18);
                if (index != 0) ** GOTO lbl-1000
                var17_18 = title;
                Intrinsics.checkNotNullExpressionValue((Object)var17_18, (String)"title");
                var17_18 = var17_18;
                var18_21 = false;
                if (var17_18.length() == 0) {
                    chapter.setTitle("\u5c01\u9762");
                } else lbl-1000:
                // 2 sources

                {
                    chapter.setTitle((String)((var17_18 = title) == null ? "" : var17_18));
                }
                chapterList.add(chapter);
            }
        }
        this.book.setLatestChapterTitle((var2_2 = (BookChapter)CollectionsKt.lastOrNull((List)chapterList)) == null ? null : var2_2.getTitle());
        this.book.setTotalChapterNum(chapterList.size());
        return chapterList;
    }

    @NotNull
    public final ArrayList<BookChapter> getChapterListBySpinAndToc(boolean useTocTitle) {
        BookChapter bookChapter;
        int i;
        ArrayList tocChapterList = this.getChapterList();
        ArrayList spinChapterList = this.getChapterListBySpine();
        if (spinChapterList.size() == 0) {
            return tocChapterList;
        }
        if (tocChapterList.size() == 0) {
            return spinChapterList;
        }
        int n = 0;
        Map titleMap = new LinkedHashMap();
        n = 0;
        int n2 = tocChapterList.size();
        if (n < n2) {
            do {
                i = n++;
                String string = ((BookChapter)tocChapterList.get(i)).getUrl();
                Object e = tocChapterList.get(i);
                Intrinsics.checkNotNullExpressionValue(e, (String)"tocChapterList.get(i)");
                titleMap.put(string, e);
            } while (n < n2);
        }
        if ((n = 0) < (n2 = spinChapterList.size())) {
            do {
                i = n++;
                Object e = spinChapterList.get(i);
                Intrinsics.checkNotNullExpressionValue(e, (String)"spinChapterList.get(i)");
                BookChapter chapter = (BookChapter)e;
                BookChapter tocChapter = (BookChapter)titleMap.get(chapter.getUrl());
                if (tocChapter == null) continue;
                CharSequence charSequence = tocChapter.getTitle();
                boolean bl = false;
                if (!(charSequence.length() > 0)) continue;
                if (!useTocTitle) {
                    charSequence = chapter.getTitle();
                    bl = false;
                    if (!(charSequence.length() == 0)) continue;
                }
                chapter.setTitle(tocChapter.getTitle());
            } while (n < n2);
        }
        this.book.setLatestChapterTitle((bookChapter = (BookChapter)CollectionsKt.lastOrNull((List)spinChapterList)) == null ? null : bookChapter.getTitle());
        this.book.setTotalChapterNum(spinChapterList.size());
        return spinChapterList;
    }

    public static /* synthetic */ ArrayList getChapterListBySpinAndToc$default(EpubFile epubFile, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        return epubFile.getChapterListBySpinAndToc(bl);
    }

    @NotNull
    public final ArrayList<BookChapter> getChapterListByTocAndSpin(boolean useSpinTitle) {
        BookChapter bookChapter;
        int i;
        ArrayList tocChapterList = this.getChapterList();
        ArrayList spinChapterList = this.getChapterListBySpine();
        if (tocChapterList.size() == 0) {
            return spinChapterList;
        }
        if (spinChapterList.size() == 0) {
            return tocChapterList;
        }
        int n = 0;
        Map titleMap = new LinkedHashMap();
        n = 0;
        int n2 = spinChapterList.size();
        if (n < n2) {
            do {
                i = n++;
                String string = ((BookChapter)spinChapterList.get(i)).getUrl();
                Object e = spinChapterList.get(i);
                Intrinsics.checkNotNullExpressionValue(e, (String)"spinChapterList.get(i)");
                titleMap.put(string, e);
            } while (n < n2);
        }
        if ((n = 0) < (n2 = tocChapterList.size())) {
            do {
                i = n++;
                Object e = tocChapterList.get(i);
                Intrinsics.checkNotNullExpressionValue(e, (String)"tocChapterList.get(i)");
                BookChapter chapter = (BookChapter)e;
                BookChapter tocChapter = (BookChapter)titleMap.get(chapter.getUrl());
                if (tocChapter == null) continue;
                CharSequence charSequence = tocChapter.getTitle();
                boolean bl = false;
                if (!(charSequence.length() > 0)) continue;
                if (!useSpinTitle) {
                    charSequence = chapter.getTitle();
                    bl = false;
                    if (!(charSequence.length() == 0)) continue;
                }
                chapter.setTitle(tocChapter.getTitle());
            } while (n < n2);
        }
        this.book.setLatestChapterTitle((bookChapter = (BookChapter)CollectionsKt.lastOrNull((List)tocChapterList)) == null ? null : bookChapter.getTitle());
        this.book.setTotalChapterNum(tocChapterList.size());
        return tocChapterList;
    }

    public static /* synthetic */ ArrayList getChapterListByTocAndSpin$default(EpubFile epubFile, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        return epubFile.getChapterListByTocAndSpin(bl);
    }

    public static final /* synthetic */ EpubFile access$getEFile$cp() {
        return eFile;
    }

    public static final /* synthetic */ void access$setEFile$cp(EpubFile epubFile) {
        eFile = epubFile;
    }

    public static final /* synthetic */ String access$getContent(EpubFile $this, BookChapter chapter) {
        return $this.getContent(chapter);
    }

    public static final /* synthetic */ InputStream access$getImage(EpubFile $this, String href) {
        return $this.getImage(href);
    }

    public static final /* synthetic */ void access$upBookInfo(EpubFile $this) {
        $this.upBookInfo();
    }
}

