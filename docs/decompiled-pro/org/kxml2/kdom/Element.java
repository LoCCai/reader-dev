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
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class Element
extends Node {
    protected String namespace;
    protected String name;
    protected Vector attributes;
    protected Node parent;
    protected Vector prefixes;

    public void init() {
    }

    public void clear() {
        this.attributes = null;
        this.children = null;
    }

    public Element createElement(String namespace, String name) {
        return this.parent == null ? super.createElement(namespace, name) : this.parent.createElement(namespace, name);
    }

    public int getAttributeCount() {
        return this.attributes == null ? 0 : this.attributes.size();
    }

    public String getAttributeNamespace(int index) {
        return ((String[])this.attributes.elementAt(index))[0];
    }

    public String getAttributeName(int index) {
        return ((String[])this.attributes.elementAt(index))[1];
    }

    public String getAttributeValue(int index) {
        return ((String[])this.attributes.elementAt(index))[2];
    }

    public String getAttributeValue(String namespace, String name) {
        for (int i = 0; i < this.getAttributeCount(); ++i) {
            if (!name.equals(this.getAttributeName(i)) || namespace != null && !namespace.equals(this.getAttributeNamespace(i))) continue;
            return this.getAttributeValue(i);
        }
        return null;
    }

    public Node getRoot() {
        Element current = this;
        while (current.parent != null) {
            if (!(current.parent instanceof Element)) {
                return current.parent;
            }
            current = (Element)current.parent;
        }
        return current;
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getNamespaceUri(String prefix) {
        int cnt = this.getNamespaceCount();
        for (int i = 0; i < cnt; ++i) {
            if (prefix != this.getNamespacePrefix(i) && (prefix == null || !prefix.equals(this.getNamespacePrefix(i)))) continue;
            return this.getNamespaceUri(i);
        }
        return this.parent instanceof Element ? ((Element)this.parent).getNamespaceUri(prefix) : null;
    }

    public int getNamespaceCount() {
        return this.prefixes == null ? 0 : this.prefixes.size();
    }

    public String getNamespacePrefix(int i) {
        return ((String[])this.prefixes.elementAt(i))[0];
    }

    public String getNamespaceUri(int i) {
        return ((String[])this.prefixes.elementAt(i))[1];
    }

    public Node getParent() {
        return this.parent;
    }

    public void parse(XmlPullParser parser) throws IOException, XmlPullParserException {
        int i;
        for (i = parser.getNamespaceCount(parser.getDepth() - 1); i < parser.getNamespaceCount(parser.getDepth()); ++i) {
            this.setPrefix(parser.getNamespacePrefix(i), parser.getNamespaceUri(i));
        }
        for (i = 0; i < parser.getAttributeCount(); ++i) {
            this.setAttribute(parser.getAttributeNamespace(i), parser.getAttributeName(i), parser.getAttributeValue(i));
        }
        this.init();
        if (parser.isEmptyElementTag()) {
            parser.nextToken();
        } else {
            parser.nextToken();
            super.parse(parser);
            if (this.getChildCount() == 0) {
                this.addChild(7, (Object)"");
            }
        }
        parser.require(3, this.getNamespace(), this.getName());
        parser.nextToken();
    }

    public void setAttribute(String namespace, String name, String value) {
        if (this.attributes == null) {
            this.attributes = new Vector();
        }
        if (namespace == null) {
            namespace = "";
        }
        for (int i = this.attributes.size() - 1; i >= 0; --i) {
            String[] attribut = (String[])this.attributes.elementAt(i);
            if (!attribut[0].equals(namespace) || !attribut[1].equals(name)) continue;
            if (value == null) {
                this.attributes.removeElementAt(i);
            } else {
                attribut[2] = value;
            }
            return;
        }
        this.attributes.addElement(new String[]{namespace, name, value});
    }

    public void setPrefix(String prefix, String namespace) {
        if (this.prefixes == null) {
            this.prefixes = new Vector();
        }
        this.prefixes.addElement(new String[]{prefix, namespace});
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNamespace(String namespace) {
        if (namespace == null) {
            throw new NullPointerException("Use \"\" for empty namespace");
        }
        this.namespace = namespace;
    }

    protected void setParent(Node parent) {
        this.parent = parent;
    }

    public void write(XmlSerializer writer) throws IOException {
        if (this.prefixes != null) {
            for (int i = 0; i < this.prefixes.size(); ++i) {
                writer.setPrefix(this.getNamespacePrefix(i), this.getNamespaceUri(i));
            }
        }
        writer.startTag(this.getNamespace(), this.getName());
        int len = this.getAttributeCount();
        for (int i = 0; i < len; ++i) {
            writer.attribute(this.getAttributeNamespace(i), this.getAttributeName(i), this.getAttributeValue(i));
        }
        this.writeChildren(writer);
        writer.endTag(this.getNamespace(), this.getName());
    }
}

