/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.kxml2.wap.WbxmlSerializer
 *  org.xmlpull.v1.XmlSerializer
 */
package org.kxml2.wap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Vector;
import org.xmlpull.v1.XmlSerializer;

/*
 * Exception performing whole class analysis ignored.
 */
public class WbxmlSerializer
implements XmlSerializer {
    Hashtable stringTable = new Hashtable();
    OutputStream out;
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    ByteArrayOutputStream stringTableBuf = new ByteArrayOutputStream();
    String pending;
    int depth;
    String name;
    String namespace;
    Vector attributes = new Vector();
    Hashtable attrStartTable = new Hashtable();
    Hashtable attrValueTable = new Hashtable();
    Hashtable tagTable = new Hashtable();
    private int attrPage;
    private int tagPage;
    private String encoding;
    private boolean headerSent = false;

    public XmlSerializer attribute(String namespace, String name, String value) {
        this.attributes.addElement(name);
        this.attributes.addElement(value);
        return this;
    }

    public void cdsect(String cdsect) throws IOException {
        this.text(cdsect);
    }

    public void comment(String comment) {
    }

    public void docdecl(String docdecl) {
        throw new RuntimeException("Cannot write docdecl for WBXML");
    }

    public void entityRef(String er) {
        throw new RuntimeException("EntityReference not supported for WBXML");
    }

    public int getDepth() {
        return this.depth;
    }

    public boolean getFeature(String name) {
        return false;
    }

    public String getNamespace() {
        return null;
    }

    public String getName() {
        return this.pending;
    }

    public String getPrefix(String nsp, boolean create) {
        throw new RuntimeException("NYI");
    }

    public Object getProperty(String name) {
        return null;
    }

    public void ignorableWhitespace(String sp) {
    }

    public void endDocument() throws IOException {
        this.flush();
    }

    public void flush() throws IOException {
        this.checkPending(false);
        if (!this.headerSent) {
            WbxmlSerializer.writeInt((OutputStream)this.out, (int)this.stringTableBuf.size());
            this.out.write(this.stringTableBuf.toByteArray());
            this.headerSent = true;
        }
        this.out.write(this.buf.toByteArray());
        this.buf.reset();
    }

    public void checkPending(boolean degenerated) throws IOException {
        if (this.pending == null) {
            return;
        }
        int len = this.attributes.size();
        int[] idx = (int[])this.tagTable.get(this.pending);
        if (idx == null) {
            this.buf.write(len == 0 ? (degenerated ? 4 : 68) : (degenerated ? 132 : 196));
            this.writeStrT(this.pending, false);
        } else {
            if (idx[0] != this.tagPage) {
                this.tagPage = idx[0];
                this.buf.write(0);
                this.buf.write(this.tagPage);
            }
            this.buf.write(len == 0 ? (degenerated ? idx[1] : idx[1] | 0x40) : (degenerated ? idx[1] | 0x80 : idx[1] | 0xC0));
        }
        for (int i = 0; i < len; ++i) {
            idx = (int[])this.attrStartTable.get(this.attributes.elementAt(i));
            if (idx == null) {
                this.buf.write(4);
                this.writeStrT((String)this.attributes.elementAt(i), false);
            } else {
                if (idx[0] != this.attrPage) {
                    this.attrPage = idx[0];
                    this.buf.write(0);
                    this.buf.write(this.attrPage);
                }
                this.buf.write(idx[1]);
            }
            idx = (int[])this.attrValueTable.get(this.attributes.elementAt(++i));
            if (idx == null) {
                this.writeStr((String)this.attributes.elementAt(i));
                continue;
            }
            if (idx[0] != this.attrPage) {
                this.attrPage = idx[0];
                this.buf.write(0);
                this.buf.write(this.attrPage);
            }
            this.buf.write(idx[1]);
        }
        if (len > 0) {
            this.buf.write(1);
        }
        this.pending = null;
        this.attributes.removeAllElements();
    }

    public void processingInstruction(String pi) {
        throw new RuntimeException("PI NYI");
    }

    public void setFeature(String name, boolean value) {
        throw new IllegalArgumentException("unknown feature " + name);
    }

    public void setOutput(Writer writer) {
        throw new RuntimeException("Wbxml requires an OutputStream!");
    }

    public void setOutput(OutputStream out, String encoding) throws IOException {
        this.encoding = encoding == null ? "UTF-8" : encoding;
        this.out = out;
        this.buf = new ByteArrayOutputStream();
        this.stringTableBuf = new ByteArrayOutputStream();
        this.headerSent = false;
    }

    public void setPrefix(String prefix, String nsp) {
        throw new RuntimeException("NYI");
    }

    public void setProperty(String property, Object value) {
        throw new IllegalArgumentException("unknown property " + property);
    }

    public void startDocument(String encoding, Boolean standalone) throws IOException {
        this.out.write(3);
        this.out.write(1);
        if (encoding != null) {
            this.encoding = encoding;
        }
        if (this.encoding.toUpperCase().equals("UTF-8")) {
            this.out.write(106);
        } else if (this.encoding.toUpperCase().equals("ISO-8859-1")) {
            this.out.write(4);
        } else {
            throw new UnsupportedEncodingException(encoding);
        }
    }

    public XmlSerializer startTag(String namespace, String name) throws IOException {
        if (namespace != null && !"".equals(namespace)) {
            throw new RuntimeException("NSP NYI");
        }
        this.checkPending(false);
        this.pending = name;
        ++this.depth;
        return this;
    }

    public XmlSerializer text(char[] chars, int start2, int len) throws IOException {
        this.checkPending(false);
        this.writeStr(new String(chars, start2, len));
        return this;
    }

    public XmlSerializer text(String text) throws IOException {
        this.checkPending(false);
        this.writeStr(text);
        return this;
    }

    private void writeStr(String text) throws IOException {
        int p0 = 0;
        int lastCut = 0;
        int len = text.length();
        if (this.headerSent) {
            this.writeStrI((OutputStream)this.buf, text);
            return;
        }
        while (p0 < len) {
            int p1;
            while (p0 < len && text.charAt(p0) < 'A') {
                ++p0;
            }
            for (p1 = p0; p1 < len && text.charAt(p1) >= 'A'; ++p1) {
            }
            if (p1 - p0 > 10) {
                if (p0 > lastCut && text.charAt(p0 - 1) == ' ' && this.stringTable.get(text.substring(p0, p1)) == null) {
                    this.buf.write(131);
                    this.writeStrT(text.substring(lastCut, p1), false);
                } else {
                    if (p0 > lastCut && text.charAt(p0 - 1) == ' ') {
                        --p0;
                    }
                    if (p0 > lastCut) {
                        this.buf.write(131);
                        this.writeStrT(text.substring(lastCut, p0), false);
                    }
                    this.buf.write(131);
                    this.writeStrT(text.substring(p0, p1), true);
                }
                lastCut = p1;
            }
            p0 = p1;
        }
        if (lastCut < len) {
            this.buf.write(131);
            this.writeStrT(text.substring(lastCut, len), false);
        }
    }

    public XmlSerializer endTag(String namespace, String name) throws IOException {
        if (this.pending != null) {
            this.checkPending(true);
        } else {
            this.buf.write(1);
        }
        --this.depth;
        return this;
    }

    public void writeWapExtension(int type, Object data) throws IOException {
        this.checkPending(false);
        this.buf.write(type);
        switch (type) {
            case 192: 
            case 193: 
            case 194: {
                break;
            }
            case 195: {
                byte[] bytes2 = (byte[])data;
                WbxmlSerializer.writeInt((OutputStream)this.buf, (int)bytes2.length);
                this.buf.write(bytes2);
                break;
            }
            case 64: 
            case 65: 
            case 66: {
                this.writeStrI((OutputStream)this.buf, (String)data);
                break;
            }
            case 128: 
            case 129: 
            case 130: {
                this.writeStrT((String)data, false);
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }

    static void writeInt(OutputStream out, int i) throws IOException {
        byte[] buf = new byte[5];
        int idx = 0;
        do {
            buf[idx++] = (byte)(i & 0x7F);
        } while ((i >>= 7) != 0);
        while (idx > 1) {
            out.write(buf[--idx] | 0x80);
        }
        out.write(buf[0]);
    }

    void writeStrI(OutputStream out, String s) throws IOException {
        byte[] data = s.getBytes(this.encoding);
        out.write(data);
        out.write(0);
    }

    private final void writeStrT(String s, boolean mayPrependSpace) throws IOException {
        Integer idx = (Integer)this.stringTable.get(s);
        WbxmlSerializer.writeInt((OutputStream)this.buf, (int)(idx == null ? this.addToStringTable(s, mayPrependSpace) : idx.intValue()));
    }

    public int addToStringTable(String s, boolean mayPrependSpace) throws IOException {
        int j;
        int i;
        if (this.headerSent) {
            throw new IOException("stringtable sent");
        }
        int offset = i = this.stringTableBuf.size();
        if (s.charAt(0) >= '0' && mayPrependSpace) {
            s = ' ' + s;
            ++offset;
        }
        this.stringTable.put(s, new Integer(i));
        if (s.charAt(0) == ' ') {
            this.stringTable.put(s.substring(1), new Integer(i + 1));
        }
        if ((j = s.lastIndexOf(32)) > 1) {
            String t = s.substring(j);
            int k = t.getBytes("utf-8").length;
            this.stringTable.put(t, new Integer(i + k));
            this.stringTable.put(s.substring(j + 1), new Integer(i + k + 1));
        }
        this.writeStrI((OutputStream)this.stringTableBuf, s);
        this.stringTableBuf.flush();
        return offset;
    }

    public void setTagTable(int page, String[] tagTable) {
        for (int i = 0; i < tagTable.length; ++i) {
            if (tagTable[i] == null) continue;
            int[] idx = new int[]{page, i + 5};
            this.tagTable.put(tagTable[i], idx);
        }
    }

    public void setAttrStartTable(int page, String[] attrStartTable) {
        for (int i = 0; i < attrStartTable.length; ++i) {
            if (attrStartTable[i] == null) continue;
            int[] idx = new int[]{page, i + 5};
            this.attrStartTable.put(attrStartTable[i], idx);
        }
    }

    public void setAttrValueTable(int page, String[] attrValueTable) {
        for (int i = 0; i < attrValueTable.length; ++i) {
            if (attrValueTable[i] == null) continue;
            int[] idx = new int[]{page, i + 133};
            this.attrValueTable.put(attrValueTable[i], idx);
        }
    }
}

