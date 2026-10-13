/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.Author
 *  me.ag2s.epublib.domain.Date
 *  me.ag2s.epublib.domain.Identifier
 *  me.ag2s.epublib.domain.Metadata
 *  me.ag2s.epublib.epub.DOMUtil
 *  me.ag2s.epublib.epub.PackageDocumentBase
 *  me.ag2s.epublib.epub.PackageDocumentMetadataReader
 *  me.ag2s.epublib.util.StringUtil
 */
package me.ag2s.epublib.epub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import me.ag2s.epublib.domain.Author;
import me.ag2s.epublib.domain.Date;
import me.ag2s.epublib.domain.Identifier;
import me.ag2s.epublib.domain.Metadata;
import me.ag2s.epublib.epub.DOMUtil;
import me.ag2s.epublib.epub.PackageDocumentBase;
import me.ag2s.epublib.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Exception performing whole class analysis ignored.
 */
class PackageDocumentMetadataReader
extends PackageDocumentBase {
    private static final String TAG = PackageDocumentMetadataReader.class.getName();

    PackageDocumentMetadataReader() {
    }

    public static Metadata readMetadata(Document packageDocument) {
        Metadata result2 = new Metadata();
        Element metadataElement = DOMUtil.getFirstElementByTagNameNS((Element)packageDocument.getDocumentElement(), (String)"http://www.idpf.org/2007/opf", (String)"metadata");
        if (metadataElement == null) {
            System.err.println(TAG + " Package does not contain element " + "metadata");
            return result2;
        }
        result2.setTitles(DOMUtil.getElementsTextChild((Element)metadataElement, (String)"http://purl.org/dc/elements/1.1/", (String)"title"));
        result2.setPublishers(DOMUtil.getElementsTextChild((Element)metadataElement, (String)"http://purl.org/dc/elements/1.1/", (String)"publisher"));
        result2.setDescriptions(DOMUtil.getElementsTextChild((Element)metadataElement, (String)"http://purl.org/dc/elements/1.1/", (String)"description"));
        result2.setRights(DOMUtil.getElementsTextChild((Element)metadataElement, (String)"http://purl.org/dc/elements/1.1/", (String)"rights"));
        result2.setTypes(DOMUtil.getElementsTextChild((Element)metadataElement, (String)"http://purl.org/dc/elements/1.1/", (String)"type"));
        result2.setSubjects(DOMUtil.getElementsTextChild((Element)metadataElement, (String)"http://purl.org/dc/elements/1.1/", (String)"subject"));
        result2.setIdentifiers(PackageDocumentMetadataReader.readIdentifiers((Element)metadataElement));
        result2.setAuthors(PackageDocumentMetadataReader.readCreators((Element)metadataElement));
        result2.setContributors(PackageDocumentMetadataReader.readContributors((Element)metadataElement));
        result2.setDates(PackageDocumentMetadataReader.readDates((Element)metadataElement));
        result2.setOtherProperties(PackageDocumentMetadataReader.readOtherProperties((Element)metadataElement));
        result2.setMetaAttributes(PackageDocumentMetadataReader.readMetaProperties((Element)metadataElement));
        Element languageTag = DOMUtil.getFirstElementByTagNameNS((Element)metadataElement, (String)"http://purl.org/dc/elements/1.1/", (String)"language");
        if (languageTag != null) {
            result2.setLanguage(DOMUtil.getTextChildrenContent((Element)languageTag));
        }
        return result2;
    }

    private static Map<QName, String> readOtherProperties(Element metadataElement) {
        HashMap<QName, String> result2 = new HashMap<QName, String>();
        NodeList metaTags = metadataElement.getElementsByTagName("meta");
        for (int i = 0; i < metaTags.getLength(); ++i) {
            Node metaNode = metaTags.item(i);
            Node property = metaNode.getAttributes().getNamedItem("property");
            if (property == null) continue;
            String name = property.getNodeValue();
            String value = metaNode.getTextContent();
            result2.put(new QName(name), value);
        }
        return result2;
    }

    private static Map<String, String> readMetaProperties(Element metadataElement) {
        HashMap<String, String> result2 = new HashMap<String, String>();
        NodeList metaTags = metadataElement.getElementsByTagName("meta");
        for (int i = 0; i < metaTags.getLength(); ++i) {
            Element metaElement = (Element)metaTags.item(i);
            String name = metaElement.getAttribute("name");
            String value = metaElement.getAttribute("content");
            result2.put(name, value);
        }
        return result2;
    }

    private static String getBookIdId(Document document) {
        Element packageElement = DOMUtil.getFirstElementByTagNameNS((Element)document.getDocumentElement(), (String)"http://www.idpf.org/2007/opf", (String)"package");
        if (packageElement == null) {
            return null;
        }
        return DOMUtil.getAttribute((Element)packageElement, (String)"http://www.idpf.org/2007/opf", (String)"unique-identifier");
    }

    private static List<Author> readCreators(Element metadataElement) {
        return PackageDocumentMetadataReader.readAuthors((String)"creator", (Element)metadataElement);
    }

    private static List<Author> readContributors(Element metadataElement) {
        return PackageDocumentMetadataReader.readAuthors((String)"contributor", (Element)metadataElement);
    }

    private static List<Author> readAuthors(String authorTag, Element metadataElement) {
        NodeList elements = metadataElement.getElementsByTagNameNS("http://purl.org/dc/elements/1.1/", authorTag);
        ArrayList<Author> result2 = new ArrayList<Author>(elements.getLength());
        for (int i = 0; i < elements.getLength(); ++i) {
            Element authorElement = (Element)elements.item(i);
            Author author = PackageDocumentMetadataReader.createAuthor((Element)authorElement);
            if (author == null) continue;
            result2.add(author);
        }
        return result2;
    }

    private static List<Date> readDates(Element metadataElement) {
        NodeList elements = metadataElement.getElementsByTagNameNS("http://purl.org/dc/elements/1.1/", "date");
        ArrayList<Date> result2 = new ArrayList<Date>(elements.getLength());
        for (int i = 0; i < elements.getLength(); ++i) {
            Element dateElement = (Element)elements.item(i);
            try {
                Date date = new Date(DOMUtil.getTextChildrenContent((Element)dateElement), DOMUtil.getAttribute((Element)dateElement, (String)"http://www.idpf.org/2007/opf", (String)"event"));
                result2.add(date);
                continue;
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return result2;
    }

    private static Author createAuthor(Element authorElement) {
        String authorString = DOMUtil.getTextChildrenContent((Element)authorElement);
        if (StringUtil.isBlank((String)authorString)) {
            return null;
        }
        int spacePos = authorString.lastIndexOf(32);
        Author result2 = spacePos < 0 ? new Author(authorString) : new Author(authorString.substring(0, spacePos), authorString.substring(spacePos + 1));
        result2.setRole(DOMUtil.getAttribute((Element)authorElement, (String)"http://www.idpf.org/2007/opf", (String)"role"));
        return result2;
    }

    private static List<Identifier> readIdentifiers(Element metadataElement) {
        NodeList identifierElements = metadataElement.getElementsByTagNameNS("http://purl.org/dc/elements/1.1/", "identifier");
        if (identifierElements.getLength() == 0) {
            System.err.println(TAG + " Package does not contain element " + "identifier");
            return new ArrayList<Identifier>();
        }
        String bookIdId = PackageDocumentMetadataReader.getBookIdId((Document)metadataElement.getOwnerDocument());
        ArrayList<Identifier> result2 = new ArrayList<Identifier>(identifierElements.getLength());
        for (int i = 0; i < identifierElements.getLength(); ++i) {
            Element identifierElement = (Element)identifierElements.item(i);
            String schemeName = DOMUtil.getAttribute((Element)identifierElement, (String)"http://www.idpf.org/2007/opf", (String)"scheme");
            String identifierValue = DOMUtil.getTextChildrenContent((Element)identifierElement);
            if (StringUtil.isBlank((String)identifierValue)) continue;
            Identifier identifier = new Identifier(schemeName, identifierValue);
            if (identifierElement.getAttribute("id").equals(bookIdId)) {
                identifier.setBookId(true);
            }
            result2.add(identifier);
        }
        return result2;
    }
}

