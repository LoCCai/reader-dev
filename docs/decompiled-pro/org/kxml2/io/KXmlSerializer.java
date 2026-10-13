/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.kxml2.io.KXmlSerializer
 *  org.xmlpull.v1.XmlSerializer
 */
package org.kxml2.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.xmlpull.v1.XmlSerializer;

public class KXmlSerializer
implements XmlSerializer {
    private Writer writer;
    private boolean pending;
    private int auto;
    private int depth;
    private String[] elementStack = new String[12];
    private int[] nspCounts = new int[4];
    private String[] nspStack = new String[8];
    private boolean[] indent = new boolean[4];
    private boolean unicode;
    private String encoding;

    private final void check(boolean close) throws IOException {
        if (!this.pending) {
            return;
        }
        ++this.depth;
        this.pending = false;
        if (this.indent.length <= this.depth) {
            boolean[] hlp = new boolean[this.depth + 4];
            System.arraycopy(this.indent, 0, hlp, 0, this.depth);
            this.indent = hlp;
        }
        this.indent[this.depth] = this.indent[this.depth - 1];
        for (int i = this.nspCounts[this.depth - 1]; i < this.nspCounts[this.depth]; ++i) {
            this.writer.write(32);
            this.writer.write("xmlns");
            if (!"".equals(this.nspStack[i * 2])) {
                this.writer.write(58);
                this.writer.write(this.nspStack[i * 2]);
            } else if ("".equals(this.getNamespace()) && !"".equals(this.nspStack[i * 2 + 1])) {
                throw new IllegalStateException("Cannot set default namespace for elements in no namespace");
            }
            this.writer.write("=\"");
            this.writeEscaped(this.nspStack[i * 2 + 1], 34);
            this.writer.write(34);
        }
        if (this.nspCounts.length <= this.depth + 1) {
            int[] hlp = new int[this.depth + 8];
            System.arraycopy(this.nspCounts, 0, hlp, 0, this.depth + 1);
            this.nspCounts = hlp;
        }
        this.nspCounts[this.depth + 1] = this.nspCounts[this.depth];
        this.writer.write(close ? " />" : ">");
    }

    private final void writeEscaped(String s, int quot) throws IOException {
        block7: for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            switch (c) {
                case '\t': 
                case '\n': 
                case '\r': {
                    if (quot == -1) {
                        this.writer.write(c);
                        continue block7;
                    }
                    this.writer.write("&#" + c + ';');
                    continue block7;
                }
                case '&': {
                    this.writer.write("&amp;");
                    continue block7;
                }
                case '>': {
                    this.writer.write("&gt;");
                    continue block7;
                }
                case '<': {
                    this.writer.write("&lt;");
                    continue block7;
                }
                case '\"': 
                case '\'': {
                    if (c == quot) {
                        this.writer.write(c == '\"' ? "&quot;" : "&apos;");
                        continue block7;
                    }
                }
                default: {
                    if (i < s.length() - 1) {
                        char cLow = s.charAt(i + 1);
                        if (c >= '\ud800' && c <= '\udbff' && cLow >= '\udc00' && cLow <= '\udfff') {
                            int n = (c - 55296 << 10) + (cLow - 56320) + 65536;
                            this.writer.write("&#" + n + ";");
                            ++i;
                            continue block7;
                        }
                    }
                    if (c >= ' ' && c != '@' && (c < '\u007f' || this.unicode)) {
                        this.writer.write(c);
                        continue block7;
                    }
                    this.writer.write("&#" + c + ";");
                }
            }
        }
    }

    public void docdecl(String dd) throws IOException {
        this.writer.write("<!DOCTYPE");
        this.writer.write(dd);
        this.writer.write(">");
    }

    public void endDocument() throws IOException {
        while (this.depth > 0) {
            this.endTag(this.elementStack[this.depth * 3 - 3], this.elementStack[this.depth * 3 - 1]);
        }
        this.flush();
    }

    public void entityRef(String name) throws IOException {
        this.check(false);
        this.writer.write(38);
        this.writer.write(name);
        this.writer.write(59);
    }

    public boolean getFeature(String name) {
        return "http://xmlpull.org/v1/doc/features.html#indent-output".equals(name) ? this.indent[this.depth] : false;
    }

    public String getPrefix(String namespace, boolean create) {
        try {
            return this.getPrefix(namespace, false, create);
        }
        catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

    private final String getPrefix(String namespace, boolean includeDefault, boolean create) throws IOException {
        String prefix;
        for (int i = this.nspCounts[this.depth + 1] * 2 - 2; i >= 0; i -= 2) {
            if (!this.nspStack[i + 1].equals(namespace) || !includeDefault && this.nspStack[i].equals("")) continue;
            String cand = this.nspStack[i];
            for (int j = i + 2; j < this.nspCounts[this.depth + 1] * 2; ++j) {
                if (!this.nspStack[j].equals(cand)) continue;
                cand = null;
                break;
            }
            if (cand == null) continue;
            return cand;
        }
        if (!create) {
            return null;
        }
        if ("".equals(namespace)) {
            prefix = "";
        } else {
            block2: do {
                prefix = "n" + this.auto++;
                for (int i = this.nspCounts[this.depth + 1] * 2 - 2; i >= 0; i -= 2) {
                    if (!prefix.equals(this.nspStack[i])) continue;
                    prefix = null;
                    continue block2;
                }
            } while (prefix == null);
        }
        boolean p = this.pending;
        this.pending = false;
        this.setPrefix(prefix, namespace);
        this.pending = p;
        return prefix;
    }

    public Object getProperty(String name) {
        throw new RuntimeException("Unsupported property");
    }

    public void ignorableWhitespace(String s) throws IOException {
        this.text(s);
    }

    public void setFeature(String name, boolean value) {
        if (!"http://xmlpull.org/v1/doc/features.html#indent-output".equals(name)) {
            throw new RuntimeException("Unsupported Feature");
        }
        this.indent[this.depth] = value;
    }

    public void setProperty(String name, Object value) {
        throw new RuntimeException("Unsupported Property:" + value);
    }

    public void setPrefix(String prefix, String namespace) throws IOException {
        String defined;
        this.check(false);
        if (prefix == null) {
            prefix = "";
        }
        if (namespace == null) {
            namespace = "";
        }
        if (prefix.equals(defined = this.getPrefix(namespace, true, false))) {
            return;
        }
        int n = this.depth + 1;
        int n2 = this.nspCounts[n];
        this.nspCounts[n] = n2 + 1;
        int pos = n2 << 1;
        if (this.nspStack.length < pos + 1) {
            String[] hlp = new String[this.nspStack.length + 16];
            System.arraycopy(this.nspStack, 0, hlp, 0, pos);
            this.nspStack = hlp;
        }
        this.nspStack[pos++] = prefix;
        this.nspStack[pos] = namespace;
    }

    public void setOutput(Writer writer) {
        this.writer = writer;
        this.nspCounts[0] = 2;
        this.nspCounts[1] = 2;
        this.nspStack[0] = "";
        this.nspStack[1] = "";
        this.nspStack[2] = "xml";
        this.nspStack[3] = "http://www.w3.org/XML/1998/namespace";
        this.pending = false;
        this.auto = 0;
        this.depth = 0;
        this.unicode = false;
    }

    public void setOutput(OutputStream os, String encoding) throws IOException {
        if (os == null) {
            throw new IllegalArgumentException();
        }
        this.setOutput((Writer)(encoding == null ? new OutputStreamWriter(os) : new OutputStreamWriter(os, encoding)));
        this.encoding = encoding;
        if (encoding != null && encoding.toLowerCase().startsWith("utf")) {
            this.unicode = true;
        }
    }

    public void startDocument(String encoding, Boolean standalone) throws IOException {
        this.writer.write("<?xml version='1.0' ");
        if (encoding != null) {
            this.encoding = encoding;
            if (encoding.toLowerCase().startsWith("utf")) {
                this.unicode = true;
            }
        }
        if (this.encoding != null) {
            this.writer.write("encoding='");
            this.writer.write(this.encoding);
            this.writer.write("' ");
        }
        if (standalone != null) {
            this.writer.write("standalone='");
            this.writer.write(standalone != false ? "yes" : "no");
            this.writer.write("' ");
        }
        this.writer.write("?>");
    }

    public XmlSerializer startTag(String namespace, String name) throws IOException {
        String prefix;
        int esp;
        this.check(false);
        if (this.indent[this.depth]) {
            this.writer.write("\r\n");
            for (int i = 0; i < this.depth; ++i) {
                this.writer.write("  ");
            }
        }
        if (this.elementStack.length < (esp = this.depth * 3) + 3) {
            String[] hlp = new String[this.elementStack.length + 12];
            System.arraycopy(this.elementStack, 0, hlp, 0, esp);
            this.elementStack = hlp;
        }
        String string = prefix = namespace == null ? "" : this.getPrefix(namespace, true, true);
        if ("".equals(namespace)) {
            for (int i = this.nspCounts[this.depth]; i < this.nspCounts[this.depth + 1]; ++i) {
                if (!"".equals(this.nspStack[i * 2]) || "".equals(this.nspStack[i * 2 + 1])) continue;
                throw new IllegalStateException("Cannot set default namespace for elements in no namespace");
            }
        }
        this.elementStack[esp++] = namespace;
        this.elementStack[esp++] = prefix;
        this.elementStack[esp] = name;
        this.writer.write(60);
        if (!"".equals(prefix)) {
            this.writer.write(prefix);
            this.writer.write(58);
        }
        this.writer.write(name);
        this.pending = true;
        return this;
    }

    public XmlSerializer attribute(String namespace, String name, String value) throws IOException {
        if (!this.pending) {
            throw new IllegalStateException("illegal position for attribute");
        }
        if (namespace == null) {
            namespace = "";
        }
        String prefix = "".equals(namespace) ? "" : this.getPrefix(namespace, false, true);
        this.writer.write(32);
        if (!"".equals(prefix)) {
            this.writer.write(prefix);
            this.writer.write(58);
        }
        this.writer.write(name);
        this.writer.write(61);
        int q = value.indexOf(34) == -1 ? 34 : 39;
        this.writer.write(q);
        this.writeEscaped(value, q);
        this.writer.write(q);
        return this;
    }

    public void flush() throws IOException {
        this.check(false);
        this.writer.flush();
    }

    public XmlSerializer endTag(String namespace, String name) throws IOException {
        if (!this.pending) {
            --this.depth;
        }
        if (namespace == null && this.elementStack[this.depth * 3] != null || namespace != null && !namespace.equals(this.elementStack[this.depth * 3]) || !this.elementStack[this.depth * 3 + 2].equals(name)) {
            throw new IllegalArgumentException("</{" + namespace + "}" + name + "> does not match start");
        }
        if (this.pending) {
            this.check(true);
            --this.depth;
        } else {
            if (this.indent[this.depth + 1]) {
                this.writer.write("\r\n");
                for (int i = 0; i < this.depth; ++i) {
                    this.writer.write("  ");
                }
            }
            this.writer.write("</");
            String prefix = this.elementStack[this.depth * 3 + 1];
            if (!"".equals(prefix)) {
                this.writer.write(prefix);
                this.writer.write(58);
            }
            this.writer.write(name);
            this.writer.write(62);
        }
        this.nspCounts[this.depth + 1] = this.nspCounts[this.depth];
        return this;
    }

    public String getNamespace() {
        return this.getDepth() == 0 ? null : this.elementStack[this.getDepth() * 3 - 3];
    }

    public String getName() {
        return this.getDepth() == 0 ? null : this.elementStack[this.getDepth() * 3 - 1];
    }

    public int getDepth() {
        return this.pending ? this.depth + 1 : this.depth;
    }

    public XmlSerializer text(String text) throws IOException {
        this.check(false);
        this.indent[this.depth] = false;
        this.writeEscaped(text, -1);
        return this;
    }

    public XmlSerializer text(char[] text, int start2, int len) throws IOException {
        this.text(new String(text, start2, len));
        return this;
    }

    public void cdsect(String data) throws IOException {
        this.check(false);
        this.writer.write("<![CDATA[");
        this.writer.write(data);
        this.writer.write("]]>");
    }

    public void comment(String comment) throws IOException {
        this.check(false);
        this.writer.write("<!--");
        this.writer.write(comment);
        this.writer.write("-->");
    }

    public void processingInstruction(String pi) throws IOException {
        this.check(false);
        this.writer.write("<?");
        this.writer.write(pi);
        this.writer.write("?>");
    }
}

