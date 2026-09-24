/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.kxml2.kdom.Element
 *  org.kxml2.kdom.Node
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package org.kxml2.kdom;

import java.io.IOException;
import java.util.Vector;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class Node {
    public static final int DOCUMENT = 0;
    public static final int ELEMENT = 2;
    public static final int TEXT = 4;
    public static final int CDSECT = 5;
    public static final int ENTITY_REF = 6;
    public static final int IGNORABLE_WHITESPACE = 7;
    public static final int PROCESSING_INSTRUCTION = 8;
    public static final int COMMENT = 9;
    public static final int DOCDECL = 10;
    protected Vector children;
    protected StringBuffer types;

    public void addChild(int index, int type, Object child) {
        if (child == null) {
            throw new NullPointerException();
        }
        if (this.children == null) {
            this.children = new Vector();
            this.types = new StringBuffer();
        }
        if (type == 2) {
            if (!(child instanceof Element)) {
                throw new RuntimeException("Element obj expected)");
            }
            ((Element)child).setParent(this);
        } else if (!(child instanceof String)) {
            throw new RuntimeException("String expected");
        }
        this.children.insertElementAt(child, index);
        this.types.insert(index, (char)type);
    }

    public void addChild(int type, Object child) {
        this.addChild(this.getChildCount(), type, child);
    }

    public Element createElement(String namespace, String name) {
        Element e = new Element();
        e.namespace = namespace == null ? "" : namespace;
        e.name = name;
        return e;
    }

    public Object getChild(int index) {
        return this.children.elementAt(index);
    }

    public int getChildCount() {
        return this.children == null ? 0 : this.children.size();
    }

    public Element getElement(int index) {
        Object child = this.getChild(index);
        return child instanceof Element ? (Element)child : null;
    }

    public Element getElement(String namespace, String name) {
        int i = this.indexOf(namespace, name, 0);
        int j = this.indexOf(namespace, name, i + 1);
        if (i == -1 || j != -1) {
            throw new RuntimeException("Element {" + namespace + "}" + name + (i == -1 ? " not found in " : " more than once in ") + this);
        }
        return this.getElement(i);
    }

    public String getText(int index) {
        return this.isText(index) ? (String)this.getChild(index) : null;
    }

    public int getType(int index) {
        return this.types.charAt(index);
    }

    public int indexOf(String namespace, String name, int startIndex) {
        int len = this.getChildCount();
        for (int i = startIndex; i < len; ++i) {
            Element child = this.getElement(i);
            if (child == null || !name.equals(child.getName()) || namespace != null && !namespace.equals(child.getNamespace())) continue;
            return i;
        }
        return -1;
    }

    public boolean isText(int i) {
        int t = this.getType(i);
        return t == 4 || t == 7 || t == 5;
    }

    public void parse(XmlPullParser parser) throws IOException, XmlPullParserException {
        boolean leave = false;
        do {
            int type = parser.getEventType();
            switch (type) {
                case 2: {
                    Element child = this.createElement(parser.getNamespace(), parser.getName());
                    this.addChild(2, (Object)child);
                    child.parse(parser);
                    break;
                }
                case 1: 
                case 3: {
                    leave = true;
                    break;
                }
                default: {
                    if (parser.getText() != null) {
                        this.addChild(type == 6 ? 4 : type, (Object)parser.getText());
                    } else if (type == 6 && parser.getName() != null) {
                        this.addChild(6, (Object)parser.getName());
                    }
                    parser.nextToken();
                }
            }
        } while (!leave);
    }

    public void removeChild(int idx) {
        this.children.removeElementAt(idx);
        int n = this.types.length() - 1;
        for (int i = idx; i < n; ++i) {
            this.types.setCharAt(i, this.types.charAt(i + 1));
        }
        this.types.setLength(n);
    }

    public void write(XmlSerializer writer) throws IOException {
        this.writeChildren(writer);
        writer.flush();
    }

    public void writeChildren(XmlSerializer writer) throws IOException {
        if (this.children == null) {
            return;
        }
        int len = this.children.size();
        block10: for (int i = 0; i < len; ++i) {
            int type = this.getType(i);
            Object child = this.children.elementAt(i);
            switch (type) {
                case 2: {
                    ((Element)child).write(writer);
                    continue block10;
                }
                case 4: {
                    writer.text((String)child);
                    continue block10;
                }
                case 7: {
                    writer.ignorableWhitespace((String)child);
                    continue block10;
                }
                case 5: {
                    writer.cdsect((String)child);
                    continue block10;
                }
                case 9: {
                    writer.comment((String)child);
                    continue block10;
                }
                case 6: {
                    writer.entityRef((String)child);
                    continue block10;
                }
                case 8: {
                    writer.processingInstruction((String)child);
                    continue block10;
                }
                case 10: {
                    writer.docdecl((String)child);
                    continue block10;
                }
                default: {
                    throw new RuntimeException("Illegal type: " + type);
                }
            }
        }
    }
}

