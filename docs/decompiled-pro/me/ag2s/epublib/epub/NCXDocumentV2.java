/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.Author
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.domain.Identifier
 *  me.ag2s.epublib.domain.MediaTypes
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.TOCReference
 *  me.ag2s.epublib.domain.TableOfContents
 *  me.ag2s.epublib.epub.DOMUtil
 *  me.ag2s.epublib.epub.EpubProcessorSupport
 *  me.ag2s.epublib.epub.EpubReader
 *  me.ag2s.epublib.epub.EpubWriter
 *  me.ag2s.epublib.epub.NCXDocumentV2
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import me.ag2s.epublib.domain.Author;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Identifier;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.TOCReference;
import me.ag2s.epublib.domain.TableOfContents;
import me.ag2s.epublib.epub.DOMUtil;
import me.ag2s.epublib.epub.EpubProcessorSupport;
import me.ag2s.epublib.epub.EpubReader;
import me.ag2s.epublib.epub.EpubWriter;
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
public class NCXDocumentV2 {
    public static final String NAMESPACE_NCX = "http://www.daisy.org/z3986/2005/ncx/";
    public static final String PREFIX_NCX = "ncx";
    public static final String NCX_ITEM_ID = "ncx";
    public static final String DEFAULT_NCX_HREF = "toc.ncx";
    public static final String PREFIX_DTB = "dtb";
    private static final String TAG = NCXDocumentV2.class.getName();

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
            System.out.println(TAG + " ncxResource.getHref()" + ncxResource.getHref());
            Document ncxDocument = ResourceUtil.getAsDocument((Resource)ncxResource);
            Element navMapElement = DOMUtil.getFirstElementByTagNameNS((Element)ncxDocument.getDocumentElement(), (String)"http://www.daisy.org/z3986/2005/ncx/", (String)"navMap");
            if (navMapElement == null) {
                return null;
            }
            TableOfContents tableOfContents = new TableOfContents(NCXDocumentV2.readTOCReferences((NodeList)navMapElement.getChildNodes(), (EpubBook)book));
            book.setTableOfContents(tableOfContents);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ncxResource;
    }

    static List<TOCReference> readTOCReferences(NodeList navpoints, EpubBook book) {
        if (navpoints == null) {
            return new ArrayList<TOCReference>();
        }
        ArrayList<TOCReference> result2 = new ArrayList<TOCReference>(navpoints.getLength());
        for (int i = 0; i < navpoints.getLength(); ++i) {
            Node node = navpoints.item(i);
            if (node.getNodeType() != 1 || !node.getLocalName().equals("navPoint")) continue;
            TOCReference tocReference = NCXDocumentV2.readTOCReference((Element)((Element)node), (EpubBook)book);
            result2.add(tocReference);
        }
        return result2;
    }

    static TOCReference readTOCReference(Element navpointElement, EpubBook book) {
        String label = NCXDocumentV2.readNavLabel((Element)navpointElement);
        String tocResourceRoot = StringUtil.substringBeforeLast((String)book.getSpine().getTocResource().getHref(), (char)'/');
        tocResourceRoot = tocResourceRoot.length() == book.getSpine().getTocResource().getHref().length() ? "" : tocResourceRoot + "/";
        String reference = StringUtil.collapsePathDots((String)(tocResourceRoot + NCXDocumentV2.readNavReference((Element)navpointElement)));
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
        List childTOCReferences = NCXDocumentV2.readTOCReferences((NodeList)navpointElement.getChildNodes(), (EpubBook)book);
        result2.setChildren(childTOCReferences);
        return result2;
    }

    private static String readNavReference(Element navpointElement) {
        Element contentElement = DOMUtil.getFirstElementByTagNameNS((Element)navpointElement, (String)"http://www.daisy.org/z3986/2005/ncx/", (String)"content");
        if (contentElement == null) {
            return null;
        }
        String result2 = DOMUtil.getAttribute((Element)contentElement, (String)"http://www.daisy.org/z3986/2005/ncx/", (String)"src");
        try {
            result2 = URLDecoder.decode(result2, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result2;
    }

    private static String readNavLabel(Element navpointElement) {
        Element navLabel = DOMUtil.getFirstElementByTagNameNS((Element)navpointElement, (String)"http://www.daisy.org/z3986/2005/ncx/", (String)"navLabel");
        assert (navLabel != null);
        return DOMUtil.getTextChildrenContent((Element)DOMUtil.getFirstElementByTagNameNS((Element)navLabel, (String)"http://www.daisy.org/z3986/2005/ncx/", (String)"text"));
    }

    public static void write(EpubWriter epubWriter, EpubBook book, ZipOutputStream resultStream) throws IOException {
        resultStream.putNextEntry(new ZipEntry(book.getSpine().getTocResource().getHref()));
        XmlSerializer out = EpubProcessorSupport.createXmlSerializer((OutputStream)resultStream);
        NCXDocumentV2.write((XmlSerializer)out, (EpubBook)book);
        out.flush();
    }

    public static void write(XmlSerializer xmlSerializer, EpubBook book) throws IllegalArgumentException, IllegalStateException, IOException {
        NCXDocumentV2.write((XmlSerializer)xmlSerializer, (List)book.getMetadata().getIdentifiers(), (String)book.getTitle(), (List)book.getMetadata().getAuthors(), (TableOfContents)book.getTableOfContents());
    }

    public static Resource createNCXResource(EpubBook book) throws IllegalArgumentException, IllegalStateException, IOException {
        return NCXDocumentV2.createNCXResource((List)book.getMetadata().getIdentifiers(), (String)book.getTitle(), (List)book.getMetadata().getAuthors(), (TableOfContents)book.getTableOfContents());
    }

    public static Resource createNCXResource(List<Identifier> identifiers, String title, List<Author> authors, TableOfContents tableOfContents) throws IllegalArgumentException, IllegalStateException, IOException {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        XmlSerializer out = EpubProcessorSupport.createXmlSerializer((OutputStream)data);
        NCXDocumentV2.write((XmlSerializer)out, identifiers, (String)title, authors, (TableOfContents)tableOfContents);
        return new Resource("ncx", data.toByteArray(), "toc.ncx", MediaTypes.NCX);
    }

    public static void write(XmlSerializer serializer, List<Identifier> identifiers, String title, List<Author> authors, TableOfContents tableOfContents) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startDocument("UTF-8", Boolean.valueOf(false));
        serializer.setPrefix("", "http://www.daisy.org/z3986/2005/ncx/");
        serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "ncx");
        serializer.attribute("", "version", "2005-1");
        serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "head");
        for (Identifier identifier : identifiers) {
            NCXDocumentV2.writeMetaElement((String)identifier.getScheme(), (String)identifier.getValue(), (XmlSerializer)serializer);
        }
        NCXDocumentV2.writeMetaElement((String)"generator", (String)"Ag2S EpubLib", (XmlSerializer)serializer);
        NCXDocumentV2.writeMetaElement((String)"depth", (String)String.valueOf(tableOfContents.calculateDepth()), (XmlSerializer)serializer);
        NCXDocumentV2.writeMetaElement((String)"totalPageCount", (String)"0", (XmlSerializer)serializer);
        NCXDocumentV2.writeMetaElement((String)"maxPageNumber", (String)"0", (XmlSerializer)serializer);
        serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "head");
        serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "docTitle");
        serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "text");
        serializer.text(StringUtil.defaultIfNull((String)title));
        serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "text");
        serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "docTitle");
        for (Author author : authors) {
            serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "docAuthor");
            serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "text");
            serializer.text(author.getLastname() + ", " + author.getFirstname());
            serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "text");
            serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "docAuthor");
        }
        serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "navMap");
        NCXDocumentV2.writeNavPoints((List)tableOfContents.getTocReferences(), (int)1, (XmlSerializer)serializer);
        serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "navMap");
        serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "ncx");
        serializer.endDocument();
    }

    private static void writeMetaElement(String dtbName, String content, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "meta");
        serializer.attribute("", "name", "dtb:" + dtbName);
        serializer.attribute("", "content", content);
        serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "meta");
    }

    private static int writeNavPoints(List<TOCReference> tocReferences, int playOrder, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        for (TOCReference tocReference : tocReferences) {
            if (tocReference.getResource() == null) {
                playOrder = NCXDocumentV2.writeNavPoints((List)tocReference.getChildren(), (int)playOrder, (XmlSerializer)serializer);
                continue;
            }
            NCXDocumentV2.writeNavPointStart((TOCReference)tocReference, (int)playOrder, (XmlSerializer)serializer);
            ++playOrder;
            if (!tocReference.getChildren().isEmpty()) {
                playOrder = NCXDocumentV2.writeNavPoints((List)tocReference.getChildren(), (int)playOrder, (XmlSerializer)serializer);
            }
            NCXDocumentV2.writeNavPointEnd((TOCReference)tocReference, (XmlSerializer)serializer);
        }
        return playOrder;
    }

    private static void writeNavPointStart(TOCReference tocReference, int playOrder, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "navPoint");
        serializer.attribute("", "id", "navPoint-" + playOrder);
        serializer.attribute("", "playOrder", String.valueOf(playOrder));
        serializer.attribute("", "class", "chapter");
        serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "navLabel");
        serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "text");
        serializer.text(tocReference.getTitle());
        serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "text");
        serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "navLabel");
        serializer.startTag("http://www.daisy.org/z3986/2005/ncx/", "content");
        serializer.attribute("", "src", tocReference.getCompleteHref());
        serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "content");
    }

    private static void writeNavPointEnd(TOCReference tocReference, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.endTag("http://www.daisy.org/z3986/2005/ncx/", "navPoint");
    }
}

