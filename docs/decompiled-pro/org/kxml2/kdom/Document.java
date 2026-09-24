/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.kxml2.kdom.Document
 *  org.kxml2.kdom.Element
 *  org.kxml2.kdom.Node
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package org.kxml2.kdom;

import java.io.IOException;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class Document
extends Node {
    protected int rootIndex = -1;
    String encoding;
    Boolean standalone;

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String enc) {
        this.encoding = enc;
    }

    public void setStandalone(Boolean standalone) {
        this.standalone = standalone;
    }

    public Boolean getStandalone() {
        return this.standalone;
    }

    public String getName() {
        return "#document";
    }

    public void addChild(int index, int type, Object child) {
        if (type == 2) {
            this.rootIndex = index;
        } else if (this.rootIndex >= index) {
            ++this.rootIndex;
        }
        super.addChild(index, type, child);
    }

    public void parse(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(0, null, null);
        parser.nextToken();
        this.encoding = parser.getInputEncoding();
        this.standalone = (Boolean)parser.getProperty("http://xmlpull.org/v1/doc/properties.html#xmldecl-standalone");
        super.parse(parser);
        if (parser.getEventType() != 1) {
            throw new RuntimeException("Document end expected!");
        }
    }

    public void removeChild(int index) {
        if (index == this.rootIndex) {
            this.rootIndex = -1;
        } else if (index < this.rootIndex) {
            --this.rootIndex;
        }
        super.removeChild(index);
    }

    public Element getRootElement() {
        if (this.rootIndex == -1) {
            throw new RuntimeException("Document has no root element!");
        }
        return (Element)this.getChild(this.rootIndex);
    }

    public void write(XmlSerializer writer) throws IOException {
        writer.startDocument(this.encoding, this.standalone);
        this.writeChildren(writer);
        writer.endDocument();
    }
}

