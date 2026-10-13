/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.MediaType
 *  me.ag2s.epublib.domain.MediaTypes
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.epub.EpubProcessorSupport
 *  me.ag2s.epublib.util.IOUtil
 *  me.ag2s.epublib.util.ResourceUtil
 *  me.ag2s.epublib.util.StringUtil
 */
package me.ag2s.epublib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import me.ag2s.epublib.domain.MediaType;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.epub.EpubProcessorSupport;
import me.ag2s.epublib.util.IOUtil;
import me.ag2s.epublib.util.StringUtil;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * Exception performing whole class analysis ignored.
 */
public class ResourceUtil {
    public static Resource createChapterResource(String title, String txt, String model, String href) {
        if (title.contains("\n")) {
            title = "<span class=\"chapter-sequence-number\">" + title.replaceFirst("\\s*\\n\\s*", "</span><br />");
        } else if ((title = title.replaceFirst("\\s+", "</span><br />")).contains("</span>")) {
            title = "<span class=\"chapter-sequence-number\">" + title;
        }
        String html = model.replace("{title}", title).replace("{content}", StringUtil.formatHtml((String)txt));
        return new Resource(html.getBytes(), href);
    }

    public static Resource createPublicResource(String name, String author, String intro, String kind, String wordCount, String model, String href) {
        String html = model.replace("{name}", name).replace("{author}", author).replace("{kind}", kind == null ? "" : kind).replace("{wordCount}", wordCount == null ? "" : wordCount).replace("{intro}", StringUtil.formatHtml((String)(intro == null ? "" : intro)));
        return new Resource(html.getBytes(), href);
    }

    public static Resource createResource(File file) throws IOException {
        if (file == null) {
            return null;
        }
        MediaType mediaType = MediaTypes.determineMediaType((String)file.getName());
        byte[] data = IOUtil.toByteArray((InputStream)new FileInputStream(file));
        return new Resource(data, mediaType);
    }

    public static Resource createResource(String title, String href) {
        String content = "<html><head><title>" + title + "</title></head><body><h1>" + title + "</h1></body></html>";
        return new Resource(null, content.getBytes(), href, MediaTypes.XHTML, "UTF-8");
    }

    public static Resource createResource(ZipEntry zipEntry, ZipInputStream zipInputStream) throws IOException {
        return new Resource((InputStream)zipInputStream, zipEntry.getName());
    }

    public static Resource createResource(ZipEntry zipEntry, InputStream zipInputStream) throws IOException {
        return new Resource(zipInputStream, zipEntry.getName());
    }

    public static byte[] recode(String inputEncoding, String outputEncoding, byte[] input) throws UnsupportedEncodingException {
        return new String(input, inputEncoding).getBytes(outputEncoding);
    }

    public static InputSource getInputSource(Resource resource) throws IOException {
        if (resource == null) {
            return null;
        }
        Reader reader = resource.getReader();
        if (reader == null) {
            return null;
        }
        return new InputSource(reader);
    }

    public static Document getAsDocument(Resource resource) throws SAXException, IOException {
        return ResourceUtil.getAsDocument((Resource)resource, (DocumentBuilder)EpubProcessorSupport.createDocumentBuilder());
    }

    public static Document getAsDocument(Resource resource, DocumentBuilder documentBuilder) throws UnsupportedEncodingException, SAXException, IOException {
        InputSource inputSource = ResourceUtil.getInputSource((Resource)resource);
        if (inputSource == null) {
            return null;
        }
        return documentBuilder.parse(inputSource);
    }
}

