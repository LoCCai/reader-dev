/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.epub.EpubProcessorSupport
 *  me.ag2s.epublib.epub.EpubProcessorSupport$EntityResolverImpl
 *  org.xmlpull.v1.XmlPullParserFactory
 *  org.xmlpull.v1.XmlSerializer
 */
package me.ag2s.epublib.epub;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import me.ag2s.epublib.epub.EpubProcessorSupport;
import org.xml.sax.EntityResolver;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/*
 * Exception performing whole class analysis ignored.
 */
public class EpubProcessorSupport {
    private static final String TAG = EpubProcessorSupport.class.getName();
    protected static DocumentBuilderFactory documentBuilderFactory;

    private static void init() {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setValidating(false);
    }

    public static XmlSerializer createXmlSerializer(OutputStream out) throws UnsupportedEncodingException {
        return EpubProcessorSupport.createXmlSerializer((Writer)new OutputStreamWriter(out, "UTF-8"));
    }

    public static XmlSerializer createXmlSerializer(Writer out) {
        XmlSerializer result2 = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setValidating(true);
            result2 = factory.newSerializer();
            result2.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            result2.setOutput(out);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result2;
    }

    public static EntityResolver getEntityResolver() {
        return new EntityResolverImpl();
    }

    public DocumentBuilderFactory getDocumentBuilderFactory() {
        return documentBuilderFactory;
    }

    public static DocumentBuilder createDocumentBuilder() {
        DocumentBuilder result2 = null;
        try {
            result2 = documentBuilderFactory.newDocumentBuilder();
            result2.setEntityResolver(EpubProcessorSupport.getEntityResolver());
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return result2;
    }

    static {
        EpubProcessorSupport.init();
    }
}

