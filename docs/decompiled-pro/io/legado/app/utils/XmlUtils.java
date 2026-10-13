/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.XmlUtils
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.utils;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0007J\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00042\u0006\u0010\t\u001a\u00020\u0001\u00a8\u0006\n"}, d2={"Lio/legado/app/utils/XmlUtils;", "", "()V", "parseNode", "", "", "list", "Lorg/w3c/dom/NodeList;", "xml2map", "source", "reader-pro"})
public final class XmlUtils {
    @NotNull
    public static final XmlUtils INSTANCE = new XmlUtils();

    private XmlUtils() {
    }

    @NotNull
    public final Map<String, Object> xml2map(@NotNull Object source) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        boolean bl = false;
        Map doc = new LinkedHashMap();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (source instanceof String) {
                Document document = builder.parse((String)source);
                NodeList nodeList = document.getChildNodes();
                Intrinsics.checkNotNullExpressionValue((Object)nodeList, (String)"document.getChildNodes()");
                return this.parseNode(nodeList);
            }
            if (source instanceof InputStream) {
                Document document = builder.parse((InputStream)source);
                NodeList nodeList = document.getChildNodes();
                Intrinsics.checkNotNullExpressionValue((Object)nodeList, (String)"document.getChildNodes()");
                return this.parseNode(nodeList);
            }
            if (source instanceof InputSource) {
                Document document = builder.parse((InputSource)source);
                NodeList nodeList = document.getChildNodes();
                Intrinsics.checkNotNullExpressionValue((Object)nodeList, (String)"document.getChildNodes()");
                return this.parseNode(nodeList);
            }
            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return doc;
        }
    }

    @NotNull
    public final Map<String, Object> parseNode(@NotNull NodeList list2) {
        Intrinsics.checkNotNullParameter((Object)list2, (String)"list");
        int n = 0;
        Map doc = new LinkedHashMap();
        n = 0;
        int n2 = list2.getLength();
        if (n < n2) {
            do {
                String string;
                int i;
                Node node;
                if ((node = list2.item(i = n++)).getNodeType() != 1) continue;
                NodeList childNodes = node.getChildNodes();
                if (childNodes.getLength() == 1 && node.getFirstChild().getNodeType() == 3) {
                    string = node.getNodeName();
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"node.getNodeName()");
                    String string2 = string;
                    string = node.getFirstChild().getNodeValue();
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"node.getFirstChild().getNodeValue()");
                    doc.put(string2, string);
                    continue;
                }
                if (childNodes.getLength() <= 1) continue;
                string = node.getNodeName();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"node.getNodeName()");
                Intrinsics.checkNotNullExpressionValue((Object)childNodes, (String)"childNodes");
                doc.put(string, this.parseNode(childNodes));
            } while (n < n2);
        }
        return doc;
    }
}

