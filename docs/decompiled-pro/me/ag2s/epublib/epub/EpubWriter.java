/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.domain.MediaTypes
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.epub.BookProcessor
 *  me.ag2s.epublib.epub.EpubProcessorSupport
 *  me.ag2s.epublib.epub.EpubWriter
 *  me.ag2s.epublib.epub.NCXDocumentV2
 *  me.ag2s.epublib.epub.NCXDocumentV3
 *  me.ag2s.epublib.epub.PackageDocumentWriter
 *  me.ag2s.epublib.util.IOUtil
 *  org.xmlpull.v1.XmlSerializer
 */
package me.ag2s.epublib.epub;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.epub.BookProcessor;
import me.ag2s.epublib.epub.EpubProcessorSupport;
import me.ag2s.epublib.epub.NCXDocumentV2;
import me.ag2s.epublib.epub.NCXDocumentV3;
import me.ag2s.epublib.epub.PackageDocumentWriter;
import me.ag2s.epublib.util.IOUtil;
import org.xmlpull.v1.XmlSerializer;

public class EpubWriter {
    private static final String TAG = EpubWriter.class.getName();
    static final String EMPTY_NAMESPACE_PREFIX = "";
    private BookProcessor bookProcessor;

    public EpubWriter() {
        this(BookProcessor.IDENTITY_BOOKPROCESSOR);
    }

    public EpubWriter(BookProcessor bookProcessor) {
        this.bookProcessor = bookProcessor;
    }

    public void write(EpubBook book, OutputStream out) throws IOException {
        book = this.processBook(book);
        ZipOutputStream resultStream = new ZipOutputStream(out);
        this.writeMimeType(resultStream);
        this.writeContainer(resultStream);
        this.initTOCResource(book);
        this.writeResources(book, resultStream);
        this.writePackageDocument(book, resultStream);
        resultStream.close();
    }

    private EpubBook processBook(EpubBook book) {
        if (this.bookProcessor != null) {
            book = this.bookProcessor.processBook(book);
        }
        return book;
    }

    private void initTOCResource(EpubBook book) {
        try {
            Resource tocResource = book.isEpub3() ? NCXDocumentV3.createNCXResource((EpubBook)book) : NCXDocumentV2.createNCXResource((EpubBook)book);
            Resource currentTocResource = book.getSpine().getTocResource();
            if (currentTocResource != null) {
                book.getResources().remove(currentTocResource.getHref());
            }
            book.getSpine().setTocResource(tocResource);
            book.getResources().add(tocResource);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeResources(EpubBook book, ZipOutputStream resultStream) {
        for (Resource resource : book.getResources().getAll()) {
            this.writeResource(resource, resultStream);
        }
    }

    private void writeResource(Resource resource, ZipOutputStream resultStream) {
        if (resource == null) {
            return;
        }
        try {
            resultStream.putNextEntry(new ZipEntry("OEBPS/" + resource.getHref()));
            InputStream inputStream = resource.getInputStream();
            IOUtil.copy((InputStream)inputStream, (OutputStream)resultStream);
            inputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writePackageDocument(EpubBook book, ZipOutputStream resultStream) throws IOException {
        resultStream.putNextEntry(new ZipEntry("OEBPS/content.opf"));
        XmlSerializer xmlSerializer = EpubProcessorSupport.createXmlSerializer((OutputStream)resultStream);
        PackageDocumentWriter.write((EpubWriter)this, (XmlSerializer)xmlSerializer, (EpubBook)book);
        xmlSerializer.flush();
    }

    private void writeContainer(ZipOutputStream resultStream) throws IOException {
        resultStream.putNextEntry(new ZipEntry("META-INF/container.xml"));
        OutputStreamWriter out = new OutputStreamWriter(resultStream);
        out.write("<?xml version=\"1.0\"?>\n");
        out.write("<container version=\"1.0\" xmlns=\"urn:oasis:names:tc:opendocument:xmlns:container\">\n");
        out.write("\t<rootfiles>\n");
        out.write("\t\t<rootfile full-path=\"OEBPS/content.opf\" media-type=\"application/oebps-package+xml\"/>\n");
        out.write("\t</rootfiles>\n");
        out.write("</container>");
        ((Writer)out).flush();
    }

    private void writeMimeType(ZipOutputStream resultStream) throws IOException {
        ZipEntry mimetypeZipEntry = new ZipEntry("mimetype");
        mimetypeZipEntry.setMethod(0);
        byte[] mimetypeBytes = MediaTypes.EPUB.getName().getBytes();
        mimetypeZipEntry.setSize(mimetypeBytes.length);
        mimetypeZipEntry.setCrc(this.calculateCrc(mimetypeBytes));
        resultStream.putNextEntry(mimetypeZipEntry);
        resultStream.write(mimetypeBytes);
    }

    private long calculateCrc(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data);
        return crc.getValue();
    }

    String getNcxId() {
        return "ncx";
    }

    String getNcxHref() {
        return "toc.ncx";
    }

    String getNcxMediaType() {
        return MediaTypes.NCX.getName();
    }

    public BookProcessor getBookProcessor() {
        return this.bookProcessor;
    }

    public void setBookProcessor(BookProcessor bookProcessor) {
        this.bookProcessor = bookProcessor;
    }
}

