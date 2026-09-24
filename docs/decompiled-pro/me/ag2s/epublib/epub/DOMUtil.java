/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.epub.DOMUtil
 *  me.ag2s.epublib.util.StringUtil
 */
package me.ag2s.epublib.epub;

import java.util.ArrayList;
import java.util.List;
import me.ag2s.epublib.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/*
 * Exception performing whole class analysis ignored.
 */
class DOMUtil {
    DOMUtil() {
    }

    public static String getAttribute(Element element, String namespace, String attribute) {
        String result2 = element.getAttributeNS(namespace, attribute);
        if (StringUtil.isEmpty((String)result2)) {
            result2 = element.getAttribute(attribute);
        }
        return result2;
    }

    public static List<String> getElementsTextChild(Element parentElement, String namespace, String tagName) {
        NodeList elements = parentElement.getElementsByTagNameNS(namespace, tagName);
        ArrayList<String> result2 = new ArrayList<String>(elements.getLength());
        for (int i = 0; i < elements.getLength(); ++i) {
            result2.add(DOMUtil.getTextChildrenContent((Element)((Element)elements.item(i))));
        }
        return result2;
    }

    public static String getFindAttributeValue(Document document, String namespace, String elementName, String findAttributeName, String findAttributeValue, String resultAttributeName) {
        NodeList metaTags = document.getElementsByTagNameNS(namespace, elementName);
        for (int i = 0; i < metaTags.getLength(); ++i) {
            Element metaElement = (Element)metaTags.item(i);
            if (!findAttributeValue.equalsIgnoreCase(metaElement.getAttribute(findAttributeName)) || !StringUtil.isNotBlank((String)metaElement.getAttribute(resultAttributeName))) continue;
            return metaElement.getAttribute(resultAttributeName);
        }
        return null;
    }

    public static NodeList getElementsByTagNameNS(Element parentElement, String namespace, String tagName) {
        NodeList nodes = parentElement.getElementsByTagNameNS(namespace, tagName);
        if (nodes.getLength() != 0) {
            return nodes;
        }
        nodes = parentElement.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            return null;
        }
        return nodes;
    }

    public static NodeList getElementsByTagNameNS(Document parentElement, String namespace, String tagName) {
        NodeList nodes = parentElement.getElementsByTagNameNS(namespace, tagName);
        if (nodes.getLength() != 0) {
            return nodes;
        }
        nodes = parentElement.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            return null;
        }
        return nodes;
    }

    public static Element getFirstElementByTagNameNS(Element parentElement, String namespace, String tagName) {
        NodeList nodes = parentElement.getElementsByTagNameNS(namespace, tagName);
        if (nodes.getLength() != 0) {
            return (Element)nodes.item(0);
        }
        nodes = parentElement.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            return null;
        }
        return (Element)nodes.item(0);
    }

    public static String getTextChildrenContent(Element parentElement) {
        if (parentElement == null) {
            return null;
        }
        StringBuilder result2 = new StringBuilder();
        NodeList childNodes = parentElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node node = childNodes.item(i);
            if (node == null || node.getNodeType() != 3) continue;
            result2.append(((Text)node).getData());
        }
        return result2.toString().trim();
    }
}

