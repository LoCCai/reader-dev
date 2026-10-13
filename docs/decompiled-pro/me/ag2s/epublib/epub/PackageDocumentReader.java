/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.domain.Guide
 *  me.ag2s.epublib.domain.GuideReference
 *  me.ag2s.epublib.domain.MediaType
 *  me.ag2s.epublib.domain.MediaTypes
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.Resources
 *  me.ag2s.epublib.domain.Spine
 *  me.ag2s.epublib.domain.SpineReference
 *  me.ag2s.epublib.epub.DOMUtil
 *  me.ag2s.epublib.epub.EpubReader
 *  me.ag2s.epublib.epub.PackageDocumentBase
 *  me.ag2s.epublib.epub.PackageDocumentMetadataReader
 *  me.ag2s.epublib.epub.PackageDocumentReader
 *  me.ag2s.epublib.util.ResourceUtil
 *  me.ag2s.epublib.util.StringUtil
 */
package me.ag2s.epublib.epub;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Guide;
import me.ag2s.epublib.domain.GuideReference;
import me.ag2s.epublib.domain.MediaType;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.Resources;
import me.ag2s.epublib.domain.Spine;
import me.ag2s.epublib.domain.SpineReference;
import me.ag2s.epublib.epub.DOMUtil;
import me.ag2s.epublib.epub.EpubReader;
import me.ag2s.epublib.epub.PackageDocumentBase;
import me.ag2s.epublib.epub.PackageDocumentMetadataReader;
import me.ag2s.epublib.util.ResourceUtil;
import me.ag2s.epublib.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * Exception performing whole class analysis ignored.
 */
public class PackageDocumentReader
extends PackageDocumentBase {
    private static final String TAG = PackageDocumentReader.class.getName();
    private static final String[] POSSIBLE_NCX_ITEM_IDS = new String[]{"toc", "ncx", "ncxtoc", "htmltoc"};

    public static void read(Resource packageResource, EpubReader epubReader, EpubBook book, Resources resources) throws SAXException, IOException {
        Document packageDocument = ResourceUtil.getAsDocument((Resource)packageResource);
        String packageHref = packageResource.getHref();
        resources = PackageDocumentReader.fixHrefs((String)packageHref, (Resources)resources);
        PackageDocumentReader.readGuide((Document)packageDocument, (EpubReader)epubReader, (EpubBook)book, (Resources)resources);
        HashMap idMapping = new HashMap();
        String version = DOMUtil.getAttribute((Element)packageDocument.getDocumentElement(), (String)"", (String)"version");
        resources = PackageDocumentReader.readManifest((Document)packageDocument, (String)packageHref, (EpubReader)epubReader, (Resources)resources, idMapping);
        book.setResources(resources);
        book.setVersion(version);
        PackageDocumentReader.readCover((Document)packageDocument, (EpubBook)book);
        book.setMetadata(PackageDocumentMetadataReader.readMetadata((Document)packageDocument));
        book.setSpine(PackageDocumentReader.readSpine((Document)packageDocument, (Resources)book.getResources(), idMapping));
        if (book.getCoverPage() == null && book.getSpine().size() > 0) {
            book.setCoverPage(book.getSpine().getResource(0));
        }
    }

    private static Resources readManifest(Document packageDocument, String packageHref, EpubReader epubReader, Resources resources, Map<String, String> idMapping) {
        Element manifestElement = DOMUtil.getFirstElementByTagNameNS((Element)packageDocument.getDocumentElement(), (String)"http://www.idpf.org/2007/opf", (String)"manifest");
        Resources result2 = new Resources();
        if (manifestElement == null) {
            System.err.println(TAG + " Package does not contain element " + "manifest");
            return result2;
        }
        NodeList itemElements = manifestElement.getElementsByTagNameNS("http://www.idpf.org/2007/opf", "item");
        for (int i = 0; i < itemElements.getLength(); ++i) {
            Element itemElement = (Element)itemElements.item(i);
            String id = DOMUtil.getAttribute((Element)itemElement, (String)"http://www.idpf.org/2007/opf", (String)"id");
            String href = DOMUtil.getAttribute((Element)itemElement, (String)"http://www.idpf.org/2007/opf", (String)"href");
            try {
                href = URLDecoder.decode(href, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String mediaTypeName = DOMUtil.getAttribute((Element)itemElement, (String)"http://www.idpf.org/2007/opf", (String)"media-type");
            Resource resource = resources.remove(href);
            if (resource == null) {
                System.err.println(TAG + " resource with href '" + href + "' not found");
                continue;
            }
            resource.setId(id);
            String properties = DOMUtil.getAttribute((Element)itemElement, (String)"http://www.idpf.org/2007/opf", (String)"properties");
            resource.setProperties(properties);
            MediaType mediaType = MediaTypes.getMediaTypeByName((String)mediaTypeName);
            if (mediaType != null) {
                resource.setMediaType(mediaType);
            }
            result2.add(resource);
            idMapping.put(id, resource.getId());
        }
        return result2;
    }

    private static void readGuide(Document packageDocument, EpubReader epubReader, EpubBook book, Resources resources) {
        Element guideElement = DOMUtil.getFirstElementByTagNameNS((Element)packageDocument.getDocumentElement(), (String)"http://www.idpf.org/2007/opf", (String)"guide");
        if (guideElement == null) {
            return;
        }
        Guide guide = book.getGuide();
        NodeList guideReferences = guideElement.getElementsByTagNameNS("http://www.idpf.org/2007/opf", "reference");
        for (int i = 0; i < guideReferences.getLength(); ++i) {
            Element referenceElement = (Element)guideReferences.item(i);
            String resourceHref = DOMUtil.getAttribute((Element)referenceElement, (String)"http://www.idpf.org/2007/opf", (String)"href");
            if (StringUtil.isBlank((String)resourceHref)) continue;
            Resource resource = resources.getByHref(StringUtil.substringBefore((String)resourceHref, (char)'#'));
            if (resource == null) {
                System.err.println(TAG + " Guide is referencing resource with href " + resourceHref + " which could not be found");
                continue;
            }
            String type = DOMUtil.getAttribute((Element)referenceElement, (String)"http://www.idpf.org/2007/opf", (String)"type");
            if (StringUtil.isBlank((String)type)) {
                System.err.println(TAG + " Guide is referencing resource with href " + resourceHref + " which is missing the 'type' attribute");
                continue;
            }
            String title = DOMUtil.getAttribute((Element)referenceElement, (String)"http://www.idpf.org/2007/opf", (String)"title");
            if ("cover".equalsIgnoreCase(type)) continue;
            GuideReference reference = new GuideReference(resource, type, title, StringUtil.substringAfter((String)resourceHref, (char)'#'));
            guide.addReference(reference);
        }
    }

    static Resources fixHrefs(String packageHref, Resources resourcesByHref) {
        int lastSlashPos = packageHref.lastIndexOf(47);
        if (lastSlashPos < 0) {
            return resourcesByHref;
        }
        Resources result2 = new Resources();
        for (Resource resource : resourcesByHref.getAll()) {
            if (StringUtil.isNotBlank((String)resource.getHref()) && resource.getHref().length() > lastSlashPos) {
                resource.setHref(resource.getHref().substring(lastSlashPos + 1));
            }
            result2.add(resource);
        }
        return result2;
    }

    private static Spine readSpine(Document packageDocument, Resources resources, Map<String, String> idMapping) {
        Element spineElement = DOMUtil.getFirstElementByTagNameNS((Element)packageDocument.getDocumentElement(), (String)"http://www.idpf.org/2007/opf", (String)"spine");
        if (spineElement == null) {
            System.err.println(TAG + " Element " + "spine" + " not found in package document, generating one automatically");
            return PackageDocumentReader.generateSpineFromResources((Resources)resources);
        }
        Spine result2 = new Spine();
        String tocResourceId = DOMUtil.getAttribute((Element)spineElement, (String)"http://www.idpf.org/2007/opf", (String)"toc");
        System.out.println(TAG + " " + tocResourceId);
        result2.setTocResource(PackageDocumentReader.findTableOfContentsResource((String)tocResourceId, (Resources)resources));
        NodeList spineNodes = DOMUtil.getElementsByTagNameNS((Document)packageDocument, (String)"http://www.idpf.org/2007/opf", (String)"itemref");
        if (spineNodes == null) {
            System.err.println(TAG + " spineNodes is null");
            return result2;
        }
        ArrayList<SpineReference> spineReferences = new ArrayList<SpineReference>(spineNodes.getLength());
        for (int i = 0; i < spineNodes.getLength(); ++i) {
            Resource resource;
            Element spineItem = (Element)spineNodes.item(i);
            String itemref = DOMUtil.getAttribute((Element)spineItem, (String)"http://www.idpf.org/2007/opf", (String)"idref");
            if (StringUtil.isBlank((String)itemref)) {
                System.err.println(TAG + " itemref with missing or empty idref");
                continue;
            }
            String id = idMapping.get(itemref);
            if (id == null) {
                id = itemref;
            }
            if ((resource = resources.getByIdOrHref(id)) == null) {
                System.err.println(TAG + " resource with id '" + id + "' not found");
                continue;
            }
            SpineReference spineReference = new SpineReference(resource);
            if ("no".equalsIgnoreCase(DOMUtil.getAttribute((Element)spineItem, (String)"http://www.idpf.org/2007/opf", (String)"linear"))) {
                spineReference.setLinear(false);
            }
            spineReferences.add(spineReference);
        }
        result2.setSpineReferences(spineReferences);
        return result2;
    }

    private static Spine generateSpineFromResources(Resources resources) {
        Spine result2 = new Spine();
        ArrayList resourceHrefs = new ArrayList(resources.getAllHrefs());
        Collections.sort(resourceHrefs, String.CASE_INSENSITIVE_ORDER);
        for (String resourceHref : resourceHrefs) {
            Resource resource = resources.getByHref(resourceHref);
            if (resource.getMediaType() == MediaTypes.NCX) {
                result2.setTocResource(resource);
                continue;
            }
            if (resource.getMediaType() != MediaTypes.XHTML) continue;
            result2.addSpineReference(new SpineReference(resource));
        }
        return result2;
    }

    static Resource findTableOfContentsResource(String tocResourceId, Resources resources) {
        Resource tocResource = resources.getByProperties("nav");
        if (tocResource != null) {
            return tocResource;
        }
        if (StringUtil.isNotBlank((String)tocResourceId)) {
            tocResource = resources.getByIdOrHref(tocResourceId);
        }
        if (tocResource != null) {
            return tocResource;
        }
        tocResource = resources.findFirstResourceByMediaType(MediaTypes.NCX);
        if (tocResource == null) {
            String possibleNcxItemId;
            String[] stringArray = POSSIBLE_NCX_ITEM_IDS;
            int n = stringArray.length;
            for (int i = 0; i < n && (tocResource = resources.getByIdOrHref(possibleNcxItemId = stringArray[i])) == null && (tocResource = resources.getByIdOrHref(possibleNcxItemId.toUpperCase())) == null; ++i) {
            }
        }
        if (tocResource == null) {
            System.err.println(TAG + " Could not find table of contents resource. Tried resource with id '" + tocResourceId + "', " + "toc" + ", " + "toc".toUpperCase() + " and any NCX resource.");
        }
        return tocResource;
    }

    static Set<String> findCoverHrefs(Document packageDocument) {
        String coverHref;
        HashSet<String> result2 = new HashSet<String>();
        String coverResourceId = DOMUtil.getFindAttributeValue((Document)packageDocument, (String)"http://www.idpf.org/2007/opf", (String)"meta", (String)"name", (String)"cover", (String)"content");
        if (StringUtil.isNotBlank((String)coverResourceId)) {
            coverHref = DOMUtil.getFindAttributeValue((Document)packageDocument, (String)"http://www.idpf.org/2007/opf", (String)"item", (String)"id", (String)coverResourceId, (String)"href");
            if (StringUtil.isNotBlank((String)coverHref)) {
                result2.add(coverHref);
            } else {
                result2.add(coverResourceId);
            }
        }
        if (StringUtil.isNotBlank((String)(coverHref = DOMUtil.getFindAttributeValue((Document)packageDocument, (String)"http://www.idpf.org/2007/opf", (String)"reference", (String)"type", (String)"cover", (String)"href")))) {
            result2.add(coverHref);
        }
        return result2;
    }

    private static void readCover(Document packageDocument, EpubBook book) {
        Set coverHrefs = PackageDocumentReader.findCoverHrefs((Document)packageDocument);
        for (String coverHref : coverHrefs) {
            Resource resource = book.getResources().getByHref(coverHref);
            if (resource == null) {
                System.err.println(TAG + " Cover resource " + coverHref + " not found");
                continue;
            }
            if (resource.getMediaType() == MediaTypes.XHTML) {
                book.setCoverPage(resource);
                continue;
            }
            if (!MediaTypes.isBitmapImage((MediaType)resource.getMediaType())) continue;
            book.setCoverImage(resource);
        }
    }
}

