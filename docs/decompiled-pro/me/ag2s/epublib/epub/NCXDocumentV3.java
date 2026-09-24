/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.Author
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.domain.Identifier
 *  me.ag2s.epublib.domain.MediaType
 *  me.ag2s.epublib.domain.MediaTypes
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.TOCReference
 *  me.ag2s.epublib.domain.TableOfContents
 *  me.ag2s.epublib.epub.DOMUtil
 *  me.ag2s.epublib.epub.EpubProcessorSupport
 *  me.ag2s.epublib.epub.EpubReader
 *  me.ag2s.epublib.epub.NCXDocumentV2
 *  me.ag2s.epublib.epub.NCXDocumentV3
 *  me.ag2s.epublib.util.ResourceUtil
 *  me.ag2s.epublib.util.StringUtil
 *  org.xmlpull.v1.XmlSerializer
 */
package me.ag2s.epublib.epub;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import me.ag2s.epublib.domain.Author;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Identifier;
import me.ag2s.epublib.domain.MediaType;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.TOCReference;
import me.ag2s.epublib.domain.TableOfContents;
import me.ag2s.epublib.epub.DOMUtil;
import me.ag2s.epublib.epub.EpubProcessorSupport;
import me.ag2s.epublib.epub.EpubReader;
import me.ag2s.epublib.epub.NCXDocumentV2;
import me.ag2s.epublib.util.ResourceUtil;
import me.ag2s.epublib.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

/*
 * Exception performing whole class analysis ignored.
 */
public class NCXDocumentV3 {
    public static final String NAMESPACE_XHTML = "http://www.w3.org/1999/xhtml";
    public static final String NAMESPACE_EPUB = "http://www.idpf.org/2007/ops";
    public static final String LANGUAGE = "en";
    public static final String PREFIX_XHTML = "html";
    public static final String NCX_ITEM_ID = "htmltoc";
    public static final String DEFAULT_NCX_HREF = "toc.xhtml";
    public static final String V3_NCX_PROPERTIES = "nav";
    public static final MediaType V3_NCX_MEDIATYPE = MediaTypes.XHTML;
    private static final String TAG = NCXDocumentV3.class.getName();

    public static Resource read(EpubBook book, EpubReader epubReader) {
        Resource ncxResource = null;
        if (book.getSpine().getTocResource() == null) {
            System.err.println(TAG + " Book does not contain a table of contents file");
            return null;
        }
        try {
            ncxResource = book.getSpine().getTocResource();
            if (ncxResource == null) {
                return null;
            }
            if (ncxResource.getHref().endsWith(".ncx")) {
                System.err.println(TAG + " \u8be5epub\u6587\u4ef6\u4e0d\u6807\u51c6\uff0c\u4f7f\u7528\u4e86epub2\u7684\u76ee\u5f55\u6587\u4ef6");
                return NCXDocumentV2.read((EpubBook)book, (EpubReader)epubReader);
            }
            System.out.println(TAG + " " + ncxResource.getHref());
            Document ncxDocument = ResourceUtil.getAsDocument((Resource)ncxResource);
            System.out.println(TAG + " " + ncxDocument.getNodeName());
            Element navMapElement = (Element)ncxDocument.getElementsByTagName("nav").item(0);
            if (navMapElement == null) {
                System.out.println(TAG + " epub3\u76ee\u5f55\u6587\u4ef6\u672a\u53d1\u73b0nav\u8282\u70b9\uff0c\u5c1d\u8bd5\u4f7f\u7528epub2\u7684\u89c4\u5219\u89e3\u6790");
                return NCXDocumentV2.read((EpubBook)book, (EpubReader)epubReader);
            }
            navMapElement = (Element)navMapElement.getElementsByTagName("ol").item(0);
            System.out.println(TAG + " " + navMapElement.getTagName());
            TableOfContents tableOfContents = new TableOfContents(NCXDocumentV3.readTOCReferences((NodeList)navMapElement.getChildNodes(), (EpubBook)book));
            System.out.println(TAG + " " + tableOfContents.toString());
            book.setTableOfContents(tableOfContents);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ncxResource;
    }

    private static List<TOCReference> doToc(Node n, EpubBook book) {
        ArrayList<TOCReference> result2 = new ArrayList<TOCReference>();
        if (n == null || n.getNodeType() != 1) {
            return result2;
        }
        Element el = (Element)n;
        NodeList nodeList = el.getElementsByTagName("li");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            result2.add(NCXDocumentV3.readTOCReference((Element)((Element)nodeList.item(i)), (EpubBook)book));
        }
        return result2;
    }

    static List<TOCReference> readTOCReferences(NodeList navpoints, EpubBook book) {
        if (navpoints == null) {
            return new ArrayList<TOCReference>();
        }
        ArrayList<TOCReference> result2 = new ArrayList<TOCReference>(navpoints.getLength());
        for (int i = 0; i < navpoints.getLength(); ++i) {
            Element el;
            Node node = navpoints.item(i);
            if (node == null || node.getNodeType() != 1 || !(el = (Element)node).getTagName().equals("li")) continue;
            result2.add(NCXDocumentV3.readTOCReference((Element)el, (EpubBook)book));
        }
        return result2;
    }

    static TOCReference readTOCReference(Element navpointElement, EpubBook book) {
        String label = NCXDocumentV3.readNavLabel((Element)navpointElement);
        String tocResourceRoot = StringUtil.substringBeforeLast((String)book.getSpine().getTocResource().getHref(), (char)'/');
        tocResourceRoot = tocResourceRoot.length() == book.getSpine().getTocResource().getHref().length() ? "" : tocResourceRoot + "/";
        String reference = StringUtil.collapsePathDots((String)(tocResourceRoot + NCXDocumentV3.readNavReference((Element)navpointElement)));
        String href = StringUtil.substringBefore((String)reference, (char)'#');
        String fragmentId = StringUtil.substringAfter((String)reference, (char)'#');
        Resource resource = book.getResources().getByHref(href);
        if (resource == null) {
            System.err.println(TAG + " Resource with href " + href + " in NCX document not found");
        }
        System.out.println(TAG + " label:" + label);
        System.out.println(TAG + " href:" + href);
        System.out.println(TAG + " fragmentId:" + fragmentId);
        TOCReference result2 = new TOCReference(label, resource, fragmentId);
        List childTOCReferences = NCXDocumentV3.doToc((Node)navpointElement, (EpubBook)book);
        result2.setChildren(childTOCReferences);
        return result2;
    }

    private static String readNavReference(Element navpointElement) {
        Element contentElement = DOMUtil.getFirstElementByTagNameNS((Element)navpointElement, (String)"", (String)"a");
        if (contentElement == null) {
            return null;
        }
        String result2 = DOMUtil.getAttribute((Element)contentElement, (String)"", (String)"href");
        try {
            result2 = URLDecoder.decode(result2, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result2;
    }

    private static String readNavLabel(Element navpointElement) {
        Element labelElement = DOMUtil.getFirstElementByTagNameNS((Element)navpointElement, (String)"", (String)"a");
        assert (labelElement != null);
        String label = labelElement.getTextContent();
        if (StringUtil.isNotBlank((String)label)) {
            return label;
        }
        labelElement = DOMUtil.getFirstElementByTagNameNS((Element)navpointElement, (String)"", (String)"span");
        assert (labelElement != null);
        label = labelElement.getTextContent();
        return label;
    }

    public static Resource createNCXResource(EpubBook book) throws IllegalArgumentException, IllegalStateException, IOException {
        return NCXDocumentV3.createNCXResource((List)book.getMetadata().getIdentifiers(), (String)book.getTitle(), (List)book.getMetadata().getAuthors(), (TableOfContents)book.getTableOfContents());
    }

    public static Resource createNCXResource(List<Identifier> identifiers, String title, List<Author> authors, TableOfContents tableOfContents) throws IllegalArgumentException, IllegalStateException, IOException {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        XmlSerializer out = EpubProcessorSupport.createXmlSerializer((OutputStream)data);
        NCXDocumentV3.write((XmlSerializer)out, identifiers, (String)title, authors, (TableOfContents)tableOfContents);
        Resource resource = new Resource("htmltoc", data.toByteArray(), "toc.xhtml", V3_NCX_MEDIATYPE);
        resource.setProperties("nav");
        return resource;
    }

    public static void write(XmlSerializer xmlSerializer, EpubBook book) throws IllegalArgumentException, IllegalStateException, IOException {
        NCXDocumentV3.write((XmlSerializer)xmlSerializer, (List)book.getMetadata().getIdentifiers(), (String)book.getTitle(), (List)book.getMetadata().getAuthors(), (TableOfContents)book.getTableOfContents());
    }

    public static void write(XmlSerializer serializer, List<Identifier> identifiers, String title, List<Author> authors, TableOfContents tableOfContents) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startDocument("UTF-8", Boolean.valueOf(false));
        serializer.setPrefix("", "http://www.w3.org/1999/xhtml");
        serializer.startTag("http://www.w3.org/1999/xhtml", "html");
        serializer.attribute("", "xmlns:epub", "http://www.idpf.org/2007/ops");
        serializer.attribute("", "xml:lang", "en");
        serializer.attribute("", "lang", "en");
        NCXDocumentV3.writeHead((String)title, (XmlSerializer)serializer);
        serializer.startTag("http://www.w3.org/1999/xhtml", "body");
        serializer.startTag("http://www.w3.org/1999/xhtml", "h1");
        serializer.text(title);
        serializer.endTag("http://www.w3.org/1999/xhtml", "h1");
        serializer.startTag("http://www.w3.org/1999/xhtml", "nav");
        serializer.attribute("", "epub:type", "toc");
        serializer.attribute("", "id", "toc");
        serializer.attribute("", "role", "doc-toc");
        serializer.startTag("http://www.w3.org/1999/xhtml", "h2");
        serializer.text("\u76ee\u5f55");
        serializer.endTag("http://www.w3.org/1999/xhtml", "h2");
        NCXDocumentV3.writeNavPoints((List)tableOfContents.getTocReferences(), (int)1, (XmlSerializer)serializer);
        serializer.endTag("http://www.w3.org/1999/xhtml", "nav");
        serializer.endTag("http://www.w3.org/1999/xhtml", "body");
        serializer.endTag("http://www.w3.org/1999/xhtml", "html");
        serializer.endDocument();
    }

    private static int writeNavPoints(List<TOCReference> tocReferences, int playOrder, XmlSerializer serializer) throws IOException {
        NCXDocumentV3.writeOlStart((XmlSerializer)serializer);
        for (TOCReference tocReference : tocReferences) {
            if (tocReference.getResource() == null) {
                playOrder = NCXDocumentV3.writeNavPoints((List)tocReference.getChildren(), (int)playOrder, (XmlSerializer)serializer);
                continue;
            }
            NCXDocumentV3.writeNavPointStart((TOCReference)tocReference, (XmlSerializer)serializer);
            ++playOrder;
            if (!tocReference.getChildren().isEmpty()) {
                playOrder = NCXDocumentV3.writeNavPoints((List)tocReference.getChildren(), (int)playOrder, (XmlSerializer)serializer);
            }
            NCXDocumentV3.writeNavPointEnd((TOCReference)tocReference, (XmlSerializer)serializer);
        }
        NCXDocumentV3.writeOlSEnd((XmlSerializer)serializer);
        return playOrder;
    }

    private static void writeNavPointStart(TOCReference tocReference, XmlSerializer serializer) throws IOException {
        NCXDocumentV3.writeLiStart((XmlSerializer)serializer);
        String title = tocReference.getTitle();
        String href = tocReference.getCompleteHref();
        if (StringUtil.isNotBlank((String)href)) {
            NCXDocumentV3.writeLabel((String)title, (String)href, (XmlSerializer)serializer);
        } else {
            NCXDocumentV3.writeLabel((String)title, (XmlSerializer)serializer);
        }
    }

    private static void writeNavPointEnd(TOCReference tocReference, XmlSerializer serializer) throws IOException {
        NCXDocumentV3.writeLiEnd((XmlSerializer)serializer);
    }

    protected static void writeLabel(String title, String href, XmlSerializer serializer) throws IOException {
        serializer.startTag("http://www.w3.org/1999/xhtml", "a");
        serializer.attribute("", "href", href);
        serializer.text(title);
        serializer.endTag("http://www.w3.org/1999/xhtml", "a");
    }

    protected static void writeLabel(String title, XmlSerializer serializer) throws IOException {
        serializer.startTag("http://www.w3.org/1999/xhtml", "span");
        serializer.text(title);
        serializer.endTag("http://www.w3.org/1999/xhtml", "span");
    }

    private static void writeLiStart(XmlSerializer serializer) throws IOException {
        serializer.startTag("http://www.w3.org/1999/xhtml", "li");
        System.out.println(TAG + " writeLiStart");
    }

    private static void writeLiEnd(XmlSerializer serializer) throws IOException {
        serializer.endTag("http://www.w3.org/1999/xhtml", "li");
        System.out.println(TAG + " writeLiEND");
    }

    private static void writeOlStart(XmlSerializer serializer) throws IOException {
        serializer.startTag("http://www.w3.org/1999/xhtml", "ol");
        System.out.println(TAG + " writeOlStart");
    }

    private static void writeOlSEnd(XmlSerializer serializer) throws IOException {
        serializer.endTag("http://www.w3.org/1999/xhtml", "ol");
        System.out.println(TAG + " writeOlEnd");
    }

    private static void writeHead(String title, XmlSerializer serializer) throws IOException {
        serializer.startTag("http://www.w3.org/1999/xhtml", "head");
        serializer.startTag("http://www.w3.org/1999/xhtml", "title");
        serializer.text(StringUtil.defaultIfNull((String)title));
        serializer.endTag("http://www.w3.org/1999/xhtml", "title");
        serializer.startTag("http://www.w3.org/1999/xhtml", "link");
        serializer.attribute("", "rel", "stylesheet");
        serializer.attribute("", "type", "text/css");
        serializer.attribute("", "href", "css/style.css");
        serializer.endTag("http://www.w3.org/1999/xhtml", "link");
        serializer.startTag("http://www.w3.org/1999/xhtml", "meta");
        serializer.attribute("", "http-equiv", "Content-Type");
        serializer.attribute("", "content", "text/html; charset=utf-8");
        serializer.endTag("http://www.w3.org/1999/xhtml", "meta");
        serializer.endTag("http://www.w3.org/1999/xhtml", "head");
    }
}

