/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.kxml2.wap.WbxmlParser
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package org.kxml2.wap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class WbxmlParser
implements XmlPullParser {
    static final String HEX_DIGITS = "0123456789abcdef";
    public static final int WAP_EXTENSION = 64;
    private static final String UNEXPECTED_EOF = "Unexpected EOF";
    private static final String ILLEGAL_TYPE = "Wrong event type";
    private InputStream in;
    private int TAG_TABLE = 0;
    private int ATTR_START_TABLE = 1;
    private int ATTR_VALUE_TABLE = 2;
    private String[] attrStartTable;
    private String[] attrValueTable;
    private String[] tagTable;
    private byte[] stringTable;
    private Hashtable cacheStringTable = null;
    private boolean processNsp;
    private int depth;
    private String[] elementStack = new String[16];
    private String[] nspStack = new String[8];
    private int[] nspCounts = new int[4];
    private int attributeCount;
    private String[] attributes = new String[16];
    private int nextId = -2;
    private Vector tables = new Vector();
    private int version;
    private int publicIdentifierId;
    private String prefix;
    private String namespace;
    private String name;
    private String text;
    private Object wapExtensionData;
    private int wapCode;
    private int type;
    private boolean degenerated;
    private boolean isWhitespace;
    private String encoding;

    public boolean getFeature(String feature) {
        if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(feature)) {
            return this.processNsp;
        }
        return false;
    }

    public String getInputEncoding() {
        return this.encoding;
    }

    public void defineEntityReplacementText(String entity, String value) throws XmlPullParserException {
    }

    public Object getProperty(String property) {
        return null;
    }

    public int getNamespaceCount(int depth) {
        if (depth > this.depth) {
            throw new IndexOutOfBoundsException();
        }
        return this.nspCounts[depth];
    }

    public String getNamespacePrefix(int pos) {
        return this.nspStack[pos << 1];
    }

    public String getNamespaceUri(int pos) {
        return this.nspStack[(pos << 1) + 1];
    }

    public String getNamespace(String prefix) {
        if ("xml".equals(prefix)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if ("xmlns".equals(prefix)) {
            return "http://www.w3.org/2000/xmlns/";
        }
        for (int i = (this.getNamespaceCount(this.depth) << 1) - 2; i >= 0; i -= 2) {
            if (!(prefix == null ? this.nspStack[i] == null : prefix.equals(this.nspStack[i]))) continue;
            return this.nspStack[i + 1];
        }
        return null;
    }

    public int getDepth() {
        return this.depth;
    }

    public String getPositionDescription() {
        StringBuffer buf = new StringBuffer(this.type < TYPES.length ? TYPES[this.type] : "unknown");
        buf.append(' ');
        if (this.type == 2 || this.type == 3) {
            if (this.degenerated) {
                buf.append("(empty) ");
            }
            buf.append('<');
            if (this.type == 3) {
                buf.append('/');
            }
            if (this.prefix != null) {
                buf.append("{" + this.namespace + "}" + this.prefix + ":");
            }
            buf.append(this.name);
            int cnt = this.attributeCount << 2;
            for (int i = 0; i < cnt; i += 4) {
                buf.append(' ');
                if (this.attributes[i + 1] != null) {
                    buf.append("{" + this.attributes[i] + "}" + this.attributes[i + 1] + ":");
                }
                buf.append(this.attributes[i + 2] + "='" + this.attributes[i + 3] + "'");
            }
            buf.append('>');
        } else if (this.type != 7) {
            if (this.type != 4) {
                buf.append(this.getText());
            } else if (this.isWhitespace) {
                buf.append("(whitespace)");
            } else {
                String text = this.getText();
                if (text.length() > 16) {
                    text = text.substring(0, 16) + "...";
                }
                buf.append(text);
            }
        }
        return buf.toString();
    }

    public int getLineNumber() {
        return -1;
    }

    public int getColumnNumber() {
        return -1;
    }

    public boolean isWhitespace() throws XmlPullParserException {
        if (this.type != 4 && this.type != 7 && this.type != 5) {
            this.exception(ILLEGAL_TYPE);
        }
        return this.isWhitespace;
    }

    public String getText() {
        return this.text;
    }

    public char[] getTextCharacters(int[] poslen) {
        if (this.type >= 4) {
            poslen[0] = 0;
            poslen[1] = this.text.length();
            char[] buf = new char[this.text.length()];
            this.text.getChars(0, this.text.length(), buf, 0);
            return buf;
        }
        poslen[0] = -1;
        poslen[1] = -1;
        return null;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getName() {
        return this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public boolean isEmptyElementTag() throws XmlPullParserException {
        if (this.type != 2) {
            this.exception(ILLEGAL_TYPE);
        }
        return this.degenerated;
    }

    public int getAttributeCount() {
        return this.attributeCount;
    }

    public String getAttributeType(int index) {
        return "CDATA";
    }

    public boolean isAttributeDefault(int index) {
        return false;
    }

    public String getAttributeNamespace(int index) {
        if (index >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[index << 2];
    }

    public String getAttributeName(int index) {
        if (index >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[(index << 2) + 2];
    }

    public String getAttributePrefix(int index) {
        if (index >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[(index << 2) + 1];
    }

    public String getAttributeValue(int index) {
        if (index >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[(index << 2) + 3];
    }

    public String getAttributeValue(String namespace, String name) {
        for (int i = (this.attributeCount << 2) - 4; i >= 0; i -= 4) {
            if (!this.attributes[i + 2].equals(name) || namespace != null && !this.attributes[i].equals(namespace)) continue;
            return this.attributes[i + 3];
        }
        return null;
    }

    public int getEventType() throws XmlPullParserException {
        return this.type;
    }

    public int next() throws XmlPullParserException, IOException {
        this.isWhitespace = true;
        int minType = 9999;
        block3: while (true) {
            String save2 = this.text;
            this.nextImpl();
            if (this.type < minType) {
                minType = this.type;
            }
            if (minType > 5) continue;
            if (minType < 4) break;
            if (save2 != null) {
                this.text = this.text == null ? save2 : save2 + this.text;
            }
            switch (this.peekId()) {
                case 2: 
                case 3: 
                case 4: 
                case 68: 
                case 131: 
                case 132: 
                case 196: {
                    continue block3;
                }
            }
            break;
        }
        this.type = minType;
        if (this.type > 4) {
            this.type = 4;
        }
        return this.type;
    }

    public int nextToken() throws XmlPullParserException, IOException {
        this.isWhitespace = true;
        this.nextImpl();
        return this.type;
    }

    public int nextTag() throws XmlPullParserException, IOException {
        this.next();
        if (this.type == 4 && this.isWhitespace) {
            this.next();
        }
        if (this.type != 3 && this.type != 2) {
            this.exception("unexpected type");
        }
        return this.type;
    }

    public String nextText() throws XmlPullParserException, IOException {
        String result2;
        if (this.type != 2) {
            this.exception("precondition: START_TAG");
        }
        this.next();
        if (this.type == 4) {
            result2 = this.getText();
            this.next();
        } else {
            result2 = "";
        }
        if (this.type != 3) {
            this.exception("END_TAG expected");
        }
        return result2;
    }

    public void require(int type, String namespace, String name) throws XmlPullParserException, IOException {
        if (type != this.type || namespace != null && !namespace.equals(this.getNamespace()) || name != null && !name.equals(this.getName())) {
            this.exception("expected: " + (type == 64 ? "WAP Ext." : TYPES[type] + " {" + namespace + "}" + name));
        }
    }

    public void setInput(Reader reader) throws XmlPullParserException {
        this.exception("InputStream required");
    }

    public void setInput(InputStream in, String enc) throws XmlPullParserException {
        this.in = in;
        try {
            int cnt;
            block9: {
                block8: {
                    this.version = this.readByte();
                    this.publicIdentifierId = this.readInt();
                    if (this.publicIdentifierId == 0) {
                        this.readInt();
                    }
                    int charset = this.readInt();
                    if (null != enc) break block8;
                    switch (charset) {
                        case 4: {
                            this.encoding = "ISO-8859-1";
                            break block9;
                        }
                        case 106: {
                            this.encoding = "UTF-8";
                            break block9;
                        }
                        default: {
                            throw new UnsupportedEncodingException("" + charset);
                        }
                    }
                }
                this.encoding = enc;
            }
            int strTabSize = this.readInt();
            this.stringTable = new byte[strTabSize];
            for (int ok = 0; ok < strTabSize && (cnt = in.read(this.stringTable, ok, strTabSize - ok)) > 0; ok += cnt) {
            }
            this.selectPage(0, true);
            this.selectPage(0, false);
        }
        catch (IOException e) {
            this.exception("Illegal input format");
        }
    }

    public void setFeature(String feature, boolean value) throws XmlPullParserException {
        if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(feature)) {
            this.processNsp = value;
        } else {
            this.exception("unsupported feature: " + feature);
        }
    }

    public void setProperty(String property, Object value) throws XmlPullParserException {
        throw new XmlPullParserException("unsupported property: " + property);
    }

    private final boolean adjustNsp() throws XmlPullParserException {
        int cut;
        int cut2;
        String attrName;
        int i;
        boolean any = false;
        for (i = 0; i < this.attributeCount << 2; i += 4) {
            String prefix;
            attrName = this.attributes[i + 2];
            cut2 = attrName.indexOf(58);
            if (cut2 != -1) {
                prefix = attrName.substring(0, cut2);
                attrName = attrName.substring(cut2 + 1);
            } else {
                if (!attrName.equals("xmlns")) continue;
                prefix = attrName;
                attrName = null;
            }
            if (!prefix.equals("xmlns")) {
                any = true;
                continue;
            }
            int n = this.depth;
            int n2 = this.nspCounts[n];
            this.nspCounts[n] = n2 + 1;
            int j = n2 << 1;
            this.nspStack = this.ensureCapacity(this.nspStack, j + 2);
            this.nspStack[j] = attrName;
            this.nspStack[j + 1] = this.attributes[i + 3];
            if (attrName != null && this.attributes[i + 3].equals("")) {
                this.exception("illegal empty namespace");
            }
            System.arraycopy(this.attributes, i + 4, this.attributes, i, (--this.attributeCount << 2) - i);
            i -= 4;
        }
        if (any) {
            for (i = (this.attributeCount << 2) - 4; i >= 0; i -= 4) {
                attrName = this.attributes[i + 2];
                cut2 = attrName.indexOf(58);
                if (cut2 == 0) {
                    throw new RuntimeException("illegal attribute name: " + attrName + " at " + this);
                }
                if (cut2 == -1) continue;
                String attrPrefix = attrName.substring(0, cut2);
                attrName = attrName.substring(cut2 + 1);
                String attrNs = this.getNamespace(attrPrefix);
                if (attrNs == null) {
                    throw new RuntimeException("Undefined Prefix: " + attrPrefix + " in " + this);
                }
                this.attributes[i] = attrNs;
                this.attributes[i + 1] = attrPrefix;
                this.attributes[i + 2] = attrName;
                for (int j = (this.attributeCount << 2) - 4; j > i; j -= 4) {
                    if (!attrName.equals(this.attributes[j + 2]) || !attrNs.equals(this.attributes[j])) continue;
                    this.exception("Duplicate Attribute: {" + attrNs + "}" + attrName);
                }
            }
        }
        if ((cut = this.name.indexOf(58)) == 0) {
            this.exception("illegal tag name: " + this.name);
        } else if (cut != -1) {
            this.prefix = this.name.substring(0, cut);
            this.name = this.name.substring(cut + 1);
        }
        this.namespace = this.getNamespace(this.prefix);
        if (this.namespace == null) {
            if (this.prefix != null) {
                this.exception("undefined prefix: " + this.prefix);
            }
            this.namespace = "";
        }
        return any;
    }

    private final void setTable(int page, int type, String[] table) {
        if (this.stringTable != null) {
            throw new RuntimeException("setXxxTable must be called before setInput!");
        }
        while (this.tables.size() < 3 * page + 3) {
            this.tables.addElement(null);
        }
        this.tables.setElementAt(table, page * 3 + type);
    }

    private final void exception(String desc) throws XmlPullParserException {
        throw new XmlPullParserException(desc, (XmlPullParser)this, null);
    }

    private void selectPage(int nr, boolean tags) throws XmlPullParserException {
        if (this.tables.size() == 0 && nr == 0) {
            return;
        }
        if (nr * 3 > this.tables.size()) {
            this.exception("Code Page " + nr + " undefined!");
        }
        if (tags) {
            this.tagTable = (String[])this.tables.elementAt(nr * 3 + this.TAG_TABLE);
        } else {
            this.attrStartTable = (String[])this.tables.elementAt(nr * 3 + this.ATTR_START_TABLE);
            this.attrValueTable = (String[])this.tables.elementAt(nr * 3 + this.ATTR_VALUE_TABLE);
        }
    }

    private final void nextImpl() throws IOException, XmlPullParserException {
        if (this.type == 3) {
            --this.depth;
        }
        if (this.degenerated) {
            this.type = 3;
            this.degenerated = false;
            return;
        }
        this.text = null;
        this.prefix = null;
        this.name = null;
        int id = this.peekId();
        while (id == 0) {
            this.nextId = -2;
            this.selectPage(this.readByte(), true);
            id = this.peekId();
        }
        this.nextId = -2;
        switch (id) {
            case -1: {
                this.type = 1;
                break;
            }
            case 1: {
                int sp = this.depth - 1 << 2;
                this.type = 3;
                this.namespace = this.elementStack[sp];
                this.prefix = this.elementStack[sp + 1];
                this.name = this.elementStack[sp + 2];
                break;
            }
            case 2: {
                this.type = 6;
                char c = (char)this.readInt();
                this.text = "" + c;
                this.name = "#" + c;
                break;
            }
            case 3: {
                this.type = 4;
                this.text = this.readStrI();
                break;
            }
            case 64: 
            case 65: 
            case 66: 
            case 128: 
            case 129: 
            case 130: 
            case 192: 
            case 193: 
            case 194: 
            case 195: {
                this.type = 64;
                this.wapCode = id;
                this.wapExtensionData = this.parseWapExtension(id);
                break;
            }
            case 67: {
                throw new RuntimeException("PI curr. not supp.");
            }
            case 131: {
                this.type = 4;
                this.text = this.readStrT();
                break;
            }
            default: {
                this.parseElement(id);
            }
        }
    }

    public Object parseWapExtension(int id) throws IOException, XmlPullParserException {
        switch (id) {
            case 64: 
            case 65: 
            case 66: {
                return this.readStrI();
            }
            case 128: 
            case 129: 
            case 130: {
                return new Integer(this.readInt());
            }
            case 192: 
            case 193: 
            case 194: {
                return null;
            }
            case 195: {
                int count;
                byte[] buf = new byte[count];
                for (count = this.readInt(); count > 0; count -= this.in.read(buf, buf.length - count, count)) {
                }
                return buf;
            }
        }
        this.exception("illegal id: " + id);
        return null;
    }

    public void readAttr() throws IOException, XmlPullParserException {
        int id = this.readByte();
        int i = 0;
        while (id != 1) {
            StringBuffer value;
            while (id == 0) {
                this.selectPage(this.readByte(), false);
                id = this.readByte();
            }
            String name = this.resolveId(this.attrStartTable, id);
            int cut = name.indexOf(61);
            if (cut == -1) {
                value = new StringBuffer();
            } else {
                value = new StringBuffer(name.substring(cut + 1));
                name = name.substring(0, cut);
            }
            id = this.readByte();
            while (id > 128 || id == 0 || id == 2 || id == 3 || id == 131 || id >= 64 && id <= 66 || id >= 128 && id <= 130) {
                switch (id) {
                    case 0: {
                        this.selectPage(this.readByte(), false);
                        break;
                    }
                    case 2: {
                        value.append((char)this.readInt());
                        break;
                    }
                    case 3: {
                        value.append(this.readStrI());
                        break;
                    }
                    case 64: 
                    case 65: 
                    case 66: 
                    case 128: 
                    case 129: 
                    case 130: 
                    case 192: 
                    case 193: 
                    case 194: 
                    case 195: {
                        value.append(this.resolveWapExtension(id, this.parseWapExtension(id)));
                        break;
                    }
                    case 131: {
                        value.append(this.readStrT());
                        break;
                    }
                    default: {
                        value.append(this.resolveId(this.attrValueTable, id));
                    }
                }
                id = this.readByte();
            }
            this.attributes = this.ensureCapacity(this.attributes, i + 4);
            this.attributes[i++] = "";
            this.attributes[i++] = null;
            this.attributes[i++] = name;
            this.attributes[i++] = value.toString();
            ++this.attributeCount;
        }
    }

    private int peekId() throws IOException {
        if (this.nextId == -2) {
            this.nextId = this.in.read();
        }
        return this.nextId;
    }

    protected String resolveWapExtension(int id, Object data) {
        if (data instanceof byte[]) {
            StringBuffer sb = new StringBuffer();
            byte[] b = (byte[])data;
            for (int i = 0; i < b.length; ++i) {
                sb.append(HEX_DIGITS.charAt(b[i] >> 4 & 0xF));
                sb.append(HEX_DIGITS.charAt(b[i] & 0xF));
            }
            return sb.toString();
        }
        return "$(" + data + ")";
    }

    String resolveId(String[] tab, int id) throws IOException {
        int idx = (id & 0x7F) - 5;
        if (idx == -1) {
            this.wapCode = -1;
            return this.readStrT();
        }
        if (idx < 0 || tab == null || idx >= tab.length || tab[idx] == null) {
            throw new IOException("id " + id + " undef.");
        }
        this.wapCode = idx + 5;
        return tab[idx];
    }

    void parseElement(int id) throws IOException, XmlPullParserException {
        this.type = 2;
        this.name = this.resolveId(this.tagTable, id & 0x3F);
        this.attributeCount = 0;
        if ((id & 0x80) != 0) {
            this.readAttr();
        }
        this.degenerated = (id & 0x40) == 0;
        int sp = this.depth++ << 2;
        this.elementStack = this.ensureCapacity(this.elementStack, sp + 4);
        this.elementStack[sp + 3] = this.name;
        if (this.depth >= this.nspCounts.length) {
            int[] bigger = new int[this.depth + 4];
            System.arraycopy(this.nspCounts, 0, bigger, 0, this.nspCounts.length);
            this.nspCounts = bigger;
        }
        this.nspCounts[this.depth] = this.nspCounts[this.depth - 1];
        for (int i = this.attributeCount - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                if (!this.getAttributeName(i).equals(this.getAttributeName(j))) continue;
                this.exception("Duplicate Attribute: " + this.getAttributeName(i));
            }
        }
        if (this.processNsp) {
            this.adjustNsp();
        } else {
            this.namespace = "";
        }
        this.elementStack[sp] = this.namespace;
        this.elementStack[sp + 1] = this.prefix;
        this.elementStack[sp + 2] = this.name;
    }

    private final String[] ensureCapacity(String[] arr, int required) {
        if (arr.length >= required) {
            return arr;
        }
        String[] bigger = new String[required + 16];
        System.arraycopy(arr, 0, bigger, 0, arr.length);
        return bigger;
    }

    int readByte() throws IOException {
        int i = this.in.read();
        if (i == -1) {
            throw new IOException(UNEXPECTED_EOF);
        }
        return i;
    }

    int readInt() throws IOException {
        int i;
        int result2 = 0;
        do {
            i = this.readByte();
            result2 = result2 << 7 | i & 0x7F;
        } while ((i & 0x80) != 0);
        return result2;
    }

    String readStrI() throws IOException {
        int i;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        boolean wsp = true;
        while ((i = this.in.read()) != 0) {
            if (i == -1) {
                throw new IOException(UNEXPECTED_EOF);
            }
            if (i > 32) {
                wsp = false;
            }
            buf.write(i);
        }
        this.isWhitespace = wsp;
        String result2 = new String(buf.toByteArray(), this.encoding);
        buf.close();
        return result2;
    }

    String readStrT() throws IOException {
        String forReturn;
        int pos = this.readInt();
        if (this.cacheStringTable == null) {
            this.cacheStringTable = new Hashtable();
        }
        if ((forReturn = (String)this.cacheStringTable.get(new Integer(pos))) == null) {
            int end;
            for (end = pos; end < this.stringTable.length && this.stringTable[end] != 0; ++end) {
            }
            forReturn = new String(this.stringTable, pos, end - pos, this.encoding);
            this.cacheStringTable.put(new Integer(pos), forReturn);
        }
        return forReturn;
    }

    public void setTagTable(int page, String[] table) {
        this.setTable(page, this.TAG_TABLE, table);
    }

    public void setAttrStartTable(int page, String[] table) {
        this.setTable(page, this.ATTR_START_TABLE, table);
    }

    public void setAttrValueTable(int page, String[] table) {
        this.setTable(page, this.ATTR_VALUE_TABLE, table);
    }

    public int getWapCode() {
        return this.wapCode;
    }

    public Object getWapExtensionData() {
        return this.wapExtensionData;
    }
}

