/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.domain.MediaType
 *  me.ag2s.epublib.domain.MediaTypes
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.Resources
 *  me.ag2s.epublib.epub.BookProcessor
 *  me.ag2s.epublib.epub.EpubReader
 *  me.ag2s.epublib.epub.NCXDocumentV2
 *  me.ag2s.epublib.epub.NCXDocumentV3
 *  me.ag2s.epublib.epub.PackageDocumentReader
 *  me.ag2s.epublib.epub.ResourcesLoader
 *  me.ag2s.epublib.util.ResourceUtil
 *  me.ag2s.epublib.util.StringUtil
 */
package me.ag2s.epublib.epub;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.MediaType;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.Resources;
import me.ag2s.epublib.epub.BookProcessor;
import me.ag2s.epublib.epub.NCXDocumentV2;
import me.ag2s.epublib.epub.NCXDocumentV3;
import me.ag2s.epublib.epub.PackageDocumentReader;
import me.ag2s.epublib.epub.ResourcesLoader;
import me.ag2s.epublib.util.ResourceUtil;
import me.ag2s.epublib.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EpubReader {
    private static final String TAG = EpubReader.class.getName();
    private final BookProcessor bookProcessor = BookProcessor.IDENTITY_BOOKPROCESSOR;

    public EpubBook readEpub(InputStream in) throws IOException {
        return this.readEpub(in, "UTF-8");
    }

    public EpubBook readEpub(ZipInputStream in) throws IOException {
        return this.readEpub(in, "UTF-8");
    }

    public EpubBook readEpub(ZipFile zipfile) throws IOException {
        return this.readEpub(zipfile, "UTF-8");
    }

    public EpubBook readEpub(InputStream in, String encoding) throws IOException {
        return this.readEpub(new ZipInputStream(in), encoding);
    }

    public EpubBook readEpubLazy(ZipFile zipFile, String encoding) throws IOException {
        return this.readEpubLazy(zipFile, encoding, Arrays.asList(MediaTypes.mediaTypes));
    }

    public EpubBook readEpub(ZipInputStream in, String encoding) throws IOException {
        return this.readEpub(ResourcesLoader.loadResources((ZipInputStream)in, (String)encoding));
    }

    public EpubBook readEpub(ZipFile in, String encoding) throws IOException {
        return this.readEpub(ResourcesLoader.loadResources((ZipFile)in, (String)encoding));
    }

    public EpubBook readEpubLazy(ZipFile zipFile, String encoding, List<MediaType> lazyLoadedTypes) throws IOException {
        Resources resources = ResourcesLoader.loadResources((ZipFile)zipFile, (String)encoding, lazyLoadedTypes);
        return this.readEpub(resources);
    }

    public EpubBook readEpub(Resources resources) {
        return this.readEpub(resources, new EpubBook());
    }

    public EpubBook readEpub(Resources resources, EpubBook result2) {
        if (result2 == null) {
            result2 = new EpubBook();
        }
        this.handleMimeType(result2, resources);
        String packageResourceHref = this.getPackageResourceHref(resources);
        Resource packageResource = this.processPackageResource(packageResourceHref, result2, resources);
        result2.setOpfResource(packageResource);
        Resource ncxResource = this.processNcxResource(packageResource, result2);
        result2.setNcxResource(ncxResource);
        result2 = this.postProcessBook(result2);
        return result2;
    }

    private EpubBook postProcessBook(EpubBook book) {
        if (this.bookProcessor != null) {
            book = this.bookProcessor.processBook(book);
        }
        return book;
    }

    private Resource processNcxResource(Resource packageResource, EpubBook book) {
        System.out.println(TAG + " OPF:getHref()" + packageResource.getHref());
        if (book.isEpub3()) {
            return NCXDocumentV3.read((EpubBook)book, (EpubReader)this);
        }
        return NCXDocumentV2.read((EpubBook)book, (EpubReader)this);
    }

    private Resource processPackageResource(String packageResourceHref, EpubBook book, Resources resources) {
        Resource packageResource = resources.remove(packageResourceHref);
        try {
            PackageDocumentReader.read((Resource)packageResource, (EpubReader)this, (EpubBook)book, (Resources)resources);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return packageResource;
    }

    private String getPackageResourceHref(Resources resources) {
        String defaultResult;
        String result2 = defaultResult = "OEBPS/content.opf";
        Resource containerResource = resources.remove("META-INF/container.xml");
        if (containerResource == null) {
            return result2;
        }
        try {
            Document document = ResourceUtil.getAsDocument((Resource)containerResource);
            Element rootFileElement = (Element)((Element)document.getDocumentElement().getElementsByTagName("rootfiles").item(0)).getElementsByTagName("rootfile").item(0);
            result2 = rootFileElement.getAttribute("full-path");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.isBlank((String)result2)) {
            result2 = defaultResult;
        }
        return result2;
    }

    private void handleMimeType(EpubBook result2, Resources resources) {
        resources.remove("mimetype");
    }
}

