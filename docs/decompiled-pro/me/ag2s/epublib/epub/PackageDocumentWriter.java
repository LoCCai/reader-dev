/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.domain.Guide
 *  me.ag2s.epublib.domain.GuideReference
 *  me.ag2s.epublib.domain.MediaTypes
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.Spine
 *  me.ag2s.epublib.domain.SpineReference
 *  me.ag2s.epublib.epub.EpubWriter
 *  me.ag2s.epublib.epub.NCXDocumentV3
 *  me.ag2s.epublib.epub.PackageDocumentBase
 *  me.ag2s.epublib.epub.PackageDocumentMetadataWriter
 *  me.ag2s.epublib.epub.PackageDocumentWriter
 *  me.ag2s.epublib.util.StringUtil
 *  org.xmlpull.v1.XmlSerializer
 */
package me.ag2s.epublib.epub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Guide;
import me.ag2s.epublib.domain.GuideReference;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.Spine;
import me.ag2s.epublib.domain.SpineReference;
import me.ag2s.epublib.epub.EpubWriter;
import me.ag2s.epublib.epub.NCXDocumentV3;
import me.ag2s.epublib.epub.PackageDocumentBase;
import me.ag2s.epublib.epub.PackageDocumentMetadataWriter;
import me.ag2s.epublib.util.StringUtil;
import org.xmlpull.v1.XmlSerializer;

/*
 * Exception performing whole class analysis ignored.
 */
public class PackageDocumentWriter
extends PackageDocumentBase {
    private static final String TAG = PackageDocumentWriter.class.getName();

    public static void write(EpubWriter epubWriter, XmlSerializer serializer, EpubBook book) {
        try {
            serializer.startDocument("UTF-8", Boolean.valueOf(false));
            serializer.setPrefix("", "http://www.idpf.org/2007/opf");
            serializer.setPrefix("dc", "http://purl.org/dc/elements/1.1/");
            serializer.startTag("http://www.idpf.org/2007/opf", "package");
            serializer.attribute("", "version", book.getVersion());
            serializer.attribute("", "unique-identifier", "duokan-book-id");
            PackageDocumentMetadataWriter.writeMetaData((EpubBook)book, (XmlSerializer)serializer);
            PackageDocumentWriter.writeManifest((EpubBook)book, (EpubWriter)epubWriter, (XmlSerializer)serializer);
            PackageDocumentWriter.writeSpine((EpubBook)book, (EpubWriter)epubWriter, (XmlSerializer)serializer);
            PackageDocumentWriter.writeGuide((EpubBook)book, (EpubWriter)epubWriter, (XmlSerializer)serializer);
            serializer.endTag("http://www.idpf.org/2007/opf", "package");
            serializer.endDocument();
            serializer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeSpine(EpubBook book, EpubWriter epubWriter, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startTag("http://www.idpf.org/2007/opf", "spine");
        Resource tocResource = book.getSpine().getTocResource();
        String tocResourceId = tocResource.getId();
        serializer.attribute("", "toc", tocResourceId);
        if (book.getCoverPage() != null && book.getSpine().findFirstResourceById(book.getCoverPage().getId()) < 0) {
            serializer.startTag("http://www.idpf.org/2007/opf", "itemref");
            serializer.attribute("", "idref", book.getCoverPage().getId());
            serializer.attribute("", "linear", "no");
            serializer.endTag("http://www.idpf.org/2007/opf", "itemref");
        }
        PackageDocumentWriter.writeSpineItems((Spine)book.getSpine(), (XmlSerializer)serializer);
        serializer.endTag("http://www.idpf.org/2007/opf", "spine");
    }

    private static void writeManifest(EpubBook book, EpubWriter epubWriter, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startTag("http://www.idpf.org/2007/opf", "manifest");
        serializer.startTag("http://www.idpf.org/2007/opf", "item");
        if (book.isEpub3()) {
            serializer.attribute("", "properties", "nav");
            serializer.attribute("", "id", "htmltoc");
            serializer.attribute("", "href", "toc.xhtml");
            serializer.attribute("", "media-type", NCXDocumentV3.V3_NCX_MEDIATYPE.getName());
        } else {
            serializer.attribute("", "id", epubWriter.getNcxId());
            serializer.attribute("", "href", epubWriter.getNcxHref());
            serializer.attribute("", "media-type", epubWriter.getNcxMediaType());
        }
        serializer.endTag("http://www.idpf.org/2007/opf", "item");
        for (Resource resource : PackageDocumentWriter.getAllResourcesSortById((EpubBook)book)) {
            PackageDocumentWriter.writeItem((EpubBook)book, (Resource)resource, (XmlSerializer)serializer);
        }
        serializer.endTag("http://www.idpf.org/2007/opf", "manifest");
    }

    private static List<Resource> getAllResourcesSortById(EpubBook book) {
        ArrayList<Resource> allResources = new ArrayList<Resource>(book.getResources().getAll());
        Collections.sort(allResources, (resource1, resource2) -> resource1.getId().compareToIgnoreCase(resource2.getId()));
        return allResources;
    }

    private static void writeItem(EpubBook book, Resource resource, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        if (resource == null || resource.getMediaType() == MediaTypes.NCX && book.getSpine().getTocResource() != null) {
            return;
        }
        if (StringUtil.isBlank((String)resource.getId())) {
            System.err.println(TAG + " resource id must not be empty (href: " + resource.getHref() + ", mediatype:" + resource.getMediaType() + ")");
            return;
        }
        if (StringUtil.isBlank((String)resource.getHref())) {
            System.err.println(TAG + " resource href must not be empty (id: " + resource.getId() + ", mediatype:" + resource.getMediaType() + ")");
            return;
        }
        if (resource.getMediaType() == null) {
            System.err.println(TAG + " resource mediatype must not be empty (id: " + resource.getId() + ", href:" + resource.getHref() + ")");
            return;
        }
        serializer.startTag("http://www.idpf.org/2007/opf", "item");
        serializer.attribute("", "id", resource.getId());
        serializer.attribute("", "href", resource.getHref());
        serializer.attribute("", "media-type", resource.getMediaType().getName());
        serializer.endTag("http://www.idpf.org/2007/opf", "item");
    }

    private static void writeSpineItems(Spine spine, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        for (SpineReference spineReference : spine.getSpineReferences()) {
            serializer.startTag("http://www.idpf.org/2007/opf", "itemref");
            serializer.attribute("", "idref", spineReference.getResourceId());
            if (!spineReference.isLinear()) {
                serializer.attribute("", "linear", "no");
            }
            serializer.endTag("http://www.idpf.org/2007/opf", "itemref");
        }
    }

    private static void writeGuide(EpubBook book, EpubWriter epubWriter, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startTag("http://www.idpf.org/2007/opf", "guide");
        PackageDocumentWriter.ensureCoverPageGuideReferenceWritten((Guide)book.getGuide(), (EpubWriter)epubWriter, (XmlSerializer)serializer);
        for (GuideReference reference : book.getGuide().getReferences()) {
            PackageDocumentWriter.writeGuideReference((GuideReference)reference, (XmlSerializer)serializer);
        }
        serializer.endTag("http://www.idpf.org/2007/opf", "guide");
    }

    private static void ensureCoverPageGuideReferenceWritten(Guide guide, EpubWriter epubWriter, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        if (!guide.getGuideReferencesByType("cover").isEmpty()) {
            return;
        }
        Resource coverPage = guide.getCoverPage();
        if (coverPage != null) {
            PackageDocumentWriter.writeGuideReference((GuideReference)new GuideReference(guide.getCoverPage(), "cover", "cover"), (XmlSerializer)serializer);
        }
    }

    private static void writeGuideReference(GuideReference reference, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        if (reference == null) {
            return;
        }
        serializer.startTag("http://www.idpf.org/2007/opf", "reference");
        serializer.attribute("", "type", reference.getType());
        serializer.attribute("", "href", reference.getCompleteHref());
        if (StringUtil.isNotBlank((String)reference.getTitle())) {
            serializer.attribute("", "title", reference.getTitle());
        }
        serializer.endTag("http://www.idpf.org/2007/opf", "reference");
    }
}

