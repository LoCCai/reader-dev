/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.kxml2.io.KXmlParser
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package org.kxml2.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Hashtable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class KXmlParser
implements XmlPullParser {
    private Object location;
    private static final String UNEXPECTED_EOF = "Unexpected EOF";
    private static final String ILLEGAL_TYPE = "Wrong event type";
    private static final int LEGACY = 999;
    private static final int XML_DECL = 998;
    private String version;
    private Boolean standalone;
    private boolean processNsp;
    private boolean relaxed;
    private Hashtable entityMap;
    private int depth;
    private String[] elementStack = new String[16];
    private String[] nspStack = new String[8];
    private int[] nspCounts = new int[4];
    private Reader reader;
    private String encoding;
    private char[] srcBuf;
    private int srcPos;
    private int srcCount;
    private int line;
    private int column;
    private char[] txtBuf = new char[128];
    private int txtPos;
    private int type;
    private boolean isWhitespace;
    private String namespace;
    private String prefix;
    private String name;
    private boolean degenerated;
    private int attributeCount;
    private String[] attributes = new String[16];
    private String error;
    private int[] peek = new int[2];
    private int peekCount;
    private boolean wasCR;
    private boolean unresolved;
    private boolean token;

    public KXmlParser() {
        this.srcBuf = new char[Runtime.getRuntime().freeMemory() >= 0x100000L ? 8192 : 128];
    }

    private final boolean isProp(String n1, boolean prop, String n2) {
        if (!n1.startsWith("http://xmlpull.org/v1/doc/")) {
            return false;
        }
        if (prop) {
            return n1.substring(42).equals(n2);
        }
        return n1.substring(40).equals(n2);
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
                this.error("illegal empty namespace");
            }
            System.arraycopy(this.attributes, i + 4, this.attributes, i, (--this.attributeCount << 2) - i);
            i -= 4;
        }
        if (any) {
            for (i = (this.attributeCount << 2) - 4; i >= 0; i -= 4) {
                attrName = this.attributes[i + 2];
                cut2 = attrName.indexOf(58);
                if (cut2 == 0 && !this.relaxed) {
                    throw new RuntimeException("illegal attribute name: " + attrName + " at " + this);
                }
                if (cut2 == -1) continue;
                String attrPrefix = attrName.substring(0, cut2);
                attrName = attrName.substring(cut2 + 1);
                String attrNs = this.getNamespace(attrPrefix);
                if (attrNs == null && !this.relaxed) {
                    throw new RuntimeException("Undefined Prefix: " + attrPrefix + " in " + this);
                }
                this.attributes[i] = attrNs;
                this.attributes[i + 1] = attrPrefix;
                this.attributes[i + 2] = attrName;
            }
        }
        if ((cut = this.name.indexOf(58)) == 0) {
            this.error("illegal tag name: " + this.name);
        }
        if (cut != -1) {
            this.prefix = this.name.substring(0, cut);
            this.name = this.name.substring(cut + 1);
        }
        this.namespace = this.getNamespace(this.prefix);
        if (this.namespace == null) {
            if (this.prefix != null) {
                this.error("undefined prefix: " + this.prefix);
            }
            this.namespace = "";
        }
        return any;
    }

    private final String[] ensureCapacity(String[] arr, int required) {
        if (arr.length >= required) {
            return arr;
        }
        String[] bigger = new String[required + 16];
        System.arraycopy(arr, 0, bigger, 0, arr.length);
        return bigger;
    }

    private final void error(String desc) throws XmlPullParserException {
        if (this.relaxed) {
            if (this.error == null) {
                this.error = "ERR: " + desc;
            }
        } else {
            this.exception(desc);
        }
    }

    private final void exception(String desc) throws XmlPullParserException {
        throw new XmlPullParserException(desc.length() < 100 ? desc : desc.substring(0, 100) + "\n", (XmlPullParser)this, null);
    }

    private final void nextImpl() throws IOException, XmlPullParserException {
        if (this.reader == null) {
            this.exception("No Input specified");
        }
        if (this.type == 3) {
            --this.depth;
        }
        do {
            this.attributeCount = -1;
            if (this.degenerated) {
                this.degenerated = false;
                this.type = 3;
                return;
            }
            if (this.error != null) {
                for (int i = 0; i < this.error.length(); ++i) {
                    this.push((int)this.error.charAt(i));
                }
                this.error = null;
                this.type = 9;
                return;
            }
            this.prefix = null;
            this.name = null;
            this.namespace = null;
            this.type = this.peekType();
            switch (this.type) {
                case 6: {
                    this.pushEntity();
                    return;
                }
                case 2: {
                    this.parseStartTag(false);
                    return;
                }
                case 3: {
                    this.parseEndTag();
                    return;
                }
                case 1: {
                    return;
                }
                case 4: {
                    this.pushText(60, !this.token);
                    if (this.depth == 0 && this.isWhitespace) {
                        this.type = 7;
                    }
                    return;
                }
            }
            this.type = this.parseLegacy(this.token);
        } while (this.type == 998);
    }

    private final int parseLegacy(boolean push) throws IOException, XmlPullParserException {
        int result2;
        int term;
        String req2 = "";
        int prev = 0;
        this.read();
        int c = this.read();
        if (c == 63) {
            if (!(this.peek(0) != 120 && this.peek(0) != 88 || this.peek(1) != 109 && this.peek(1) != 77)) {
                if (push) {
                    this.push(this.peek(0));
                    this.push(this.peek(1));
                }
                this.read();
                this.read();
                if ((this.peek(0) == 108 || this.peek(0) == 76) && this.peek(1) <= 32) {
                    if (this.line != 1 || this.column > 4) {
                        this.error("PI must not start with xml");
                    }
                    this.parseStartTag(true);
                    if (this.attributeCount < 1 || !"version".equals(this.attributes[2])) {
                        this.error("version expected");
                    }
                    this.version = this.attributes[3];
                    int pos = 1;
                    if (pos < this.attributeCount && "encoding".equals(this.attributes[6])) {
                        this.encoding = this.attributes[7];
                        ++pos;
                    }
                    if (pos < this.attributeCount && "standalone".equals(this.attributes[4 * pos + 2])) {
                        String st2 = this.attributes[3 + 4 * pos];
                        if ("yes".equals(st2)) {
                            this.standalone = new Boolean(true);
                        } else if ("no".equals(st2)) {
                            this.standalone = new Boolean(false);
                        } else {
                            this.error("illegal standalone value: " + st2);
                        }
                        ++pos;
                    }
                    if (pos != this.attributeCount) {
                        this.error("illegal xmldecl");
                    }
                    this.isWhitespace = true;
                    this.txtPos = 0;
                    return 998;
                }
            }
            term = 63;
            result2 = 8;
        } else if (c == 33) {
            if (this.peek(0) == 45) {
                result2 = 9;
                req2 = "--";
                term = 45;
            } else if (this.peek(0) == 91) {
                result2 = 5;
                req2 = "[CDATA[";
                term = 93;
                push = true;
            } else {
                result2 = 10;
                req2 = "DOCTYPE";
                term = -1;
            }
        } else {
            this.error("illegal: <" + c);
            return 9;
        }
        for (int i = 0; i < req2.length(); ++i) {
            this.read(req2.charAt(i));
        }
        if (result2 == 10) {
            this.parseDoctype(push);
        } else {
            while (true) {
                if ((c = this.read()) == -1) {
                    this.error(UNEXPECTED_EOF);
                    return 9;
                }
                if (push) {
                    this.push(c);
                }
                if ((term == 63 || c == term) && this.peek(0) == term && this.peek(1) == 62) break;
                prev = c;
            }
            if (term == 45 && prev == 45 && !this.relaxed) {
                this.error("illegal comment delimiter: --->");
            }
            this.read();
            this.read();
            if (push && term != 63) {
                --this.txtPos;
            }
        }
        return result2;
    }

    private final void parseDoctype(boolean push) throws IOException, XmlPullParserException {
        int nesting = 1;
        boolean quoted = false;
        while (true) {
            int i = this.read();
            switch (i) {
                case -1: {
                    this.error(UNEXPECTED_EOF);
                    return;
                }
                case 39: {
                    quoted = !quoted;
                    break;
                }
                case 60: {
                    if (quoted) break;
                    ++nesting;
                    break;
                }
                case 62: {
                    if (quoted || --nesting != 0) break;
                    return;
                }
            }
            if (!push) continue;
            this.push(i);
        }
    }

    private final void parseEndTag() throws IOException, XmlPullParserException {
        this.read();
        this.read();
        this.name = this.readName();
        this.skip();
        this.read('>');
        int sp = this.depth - 1 << 2;
        if (this.depth == 0) {
            this.error("element stack empty");
            this.type = 9;
            return;
        }
        if (!this.relaxed) {
            if (!this.name.equals(this.elementStack[sp + 3])) {
                this.error("expected: /" + this.elementStack[sp + 3] + " read: " + this.name);
            }
            this.namespace = this.elementStack[sp];
            this.prefix = this.elementStack[sp + 1];
            this.name = this.elementStack[sp + 2];
        }
    }

    private final int peekType() throws IOException {
        switch (this.peek(0)) {
            case -1: {
                return 1;
            }
            case 38: {
                return 6;
            }
            case 60: {
                switch (this.peek(1)) {
                    case 47: {
                        return 3;
                    }
                    case 33: 
                    case 63: {
                        return 999;
                    }
                }
                return 2;
            }
        }
        return 4;
    }

    private final String get(int pos) {
        return new String(this.txtBuf, pos, this.txtPos - pos);
    }

    private final void push(int c) {
        this.isWhitespace &= c <= 32;
        if (this.txtPos + 1 >= this.txtBuf.length) {
            char[] bigger = new char[this.txtPos * 4 / 3 + 4];
            System.arraycopy(this.txtBuf, 0, bigger, 0, this.txtPos);
            this.txtBuf = bigger;
        }
        if (c > 65535) {
            int offset = c - 65536;
            this.txtBuf[this.txtPos++] = (char)((offset >>> 10) + 55296);
            this.txtBuf[this.txtPos++] = (char)((offset & 0x3FF) + 56320);
        } else {
            this.txtBuf[this.txtPos++] = (char)c;
        }
    }

    private final void parseStartTag(boolean xmldecl) throws IOException, XmlPullParserException {
        if (!xmldecl) {
            this.read();
        }
        this.name = this.readName();
        this.attributeCount = 0;
        while (true) {
            this.skip();
            int c = this.peek(0);
            if (xmldecl) {
                if (c == 63) {
                    this.read();
                    this.read('>');
                    return;
                }
            } else {
                if (c == 47) {
                    this.degenerated = true;
                    this.read();
                    this.skip();
                    this.read('>');
                    break;
                }
                if (c == 62 && !xmldecl) {
                    this.read();
                    break;
                }
            }
            if (c == -1) {
                this.error(UNEXPECTED_EOF);
                return;
            }
            String attrName = this.readName();
            if (attrName.length() == 0) {
                this.error("attr name expected");
                break;
            }
            int i = this.attributeCount++ << 2;
            this.attributes = this.ensureCapacity(this.attributes, i + 4);
            this.attributes[i++] = "";
            this.attributes[i++] = null;
            this.attributes[i++] = attrName;
            this.skip();
            if (this.peek(0) != 61) {
                if (!this.relaxed) {
                    this.error("Attr.value missing f. " + attrName);
                }
                this.attributes[i] = attrName;
                continue;
            }
            this.read('=');
            this.skip();
            int delimiter = this.peek(0);
            if (delimiter != 39 && delimiter != 34) {
                if (!this.relaxed) {
                    this.error("attr value delimiter missing!");
                }
                delimiter = 32;
            } else {
                this.read();
            }
            int p = this.txtPos;
            this.pushText(delimiter, true);
            this.attributes[i] = this.get(p);
            this.txtPos = p;
            if (delimiter == 32) continue;
            this.read();
        }
        int sp = this.depth++ << 2;
        this.elementStack = this.ensureCapacity(this.elementStack, sp + 4);
        this.elementStack[sp + 3] = this.name;
        if (this.depth >= this.nspCounts.length) {
            int[] bigger = new int[this.depth + 4];
            System.arraycopy(this.nspCounts, 0, bigger, 0, this.nspCounts.length);
            this.nspCounts = bigger;
        }
        this.nspCounts[this.depth] = this.nspCounts[this.depth - 1];
        if (this.processNsp) {
            this.adjustNsp();
        } else {
            this.namespace = "";
        }
        this.elementStack[sp] = this.namespace;
        this.elementStack[sp + 1] = this.prefix;
        this.elementStack[sp + 2] = this.name;
    }

    private final void pushEntity() throws IOException, XmlPullParserException {
        this.push(this.read());
        int pos = this.txtPos;
        while (true) {
            int c;
            if ((c = this.peek(0)) == 59) break;
            if (!(c >= 128 || c >= 48 && c <= 57 || c >= 97 && c <= 122 || c >= 65 && c <= 90 || c == 95 || c == 45 || c == 35)) {
                if (!this.relaxed) {
                    this.error("unterminated entity ref");
                }
                System.out.println("broken entitiy: " + this.get(pos - 1));
                return;
            }
            this.push(this.read());
        }
        this.read();
        String code = this.get(pos);
        this.txtPos = pos - 1;
        if (this.token && this.type == 6) {
            this.name = code;
        }
        if (code.charAt(0) == '#') {
            int c = code.charAt(1) == 'x' ? Integer.parseInt(code.substring(2), 16) : Integer.parseInt(code.substring(1));
            this.push(c);
            return;
        }
        String result2 = (String)this.entityMap.get(code);
        boolean bl = this.unresolved = result2 == null;
        if (this.unresolved) {
            if (!this.token) {
                this.error("unresolved: &" + code + ";");
            }
        } else {
            for (int i = 0; i < result2.length(); ++i) {
                this.push((int)result2.charAt(i));
            }
        }
    }

    private final void pushText(int delimiter, boolean resolveEntities) throws IOException, XmlPullParserException {
        int next = this.peek(0);
        int cbrCount = 0;
        while (next != -1 && next != delimiter && (delimiter != 32 || next > 32 && next != 62)) {
            if (next == 38) {
                if (!resolveEntities) break;
                this.pushEntity();
            } else if (next == 10 && this.type == 2) {
                this.read();
                this.push(32);
            } else {
                this.push(this.read());
            }
            if (next == 62 && cbrCount >= 2 && delimiter != 93) {
                this.error("Illegal: ]]>");
            }
            cbrCount = next == 93 ? ++cbrCount : 0;
            next = this.peek(0);
        }
    }

    private final void read(char c) throws IOException, XmlPullParserException {
        int a = this.read();
        if (a != c) {
            this.error("expected: '" + c + "' actual: '" + (char)a + "'");
        }
    }

    private final int read() throws IOException {
        int result2;
        if (this.peekCount == 0) {
            result2 = this.peek(0);
        } else {
            result2 = this.peek[0];
            this.peek[0] = this.peek[1];
        }
        --this.peekCount;
        ++this.column;
        if (result2 == 10) {
            ++this.line;
            this.column = 1;
        }
        return result2;
    }

    private final int peek(int pos) throws IOException {
        while (pos >= this.peekCount) {
            int nw;
            if (this.srcBuf.length <= 1) {
                nw = this.reader.read();
            } else if (this.srcPos < this.srcCount) {
                nw = this.srcBuf[this.srcPos++];
            } else {
                this.srcCount = this.reader.read(this.srcBuf, 0, this.srcBuf.length);
                nw = this.srcCount <= 0 ? -1 : this.srcBuf[0];
                this.srcPos = 1;
            }
            if (nw == 13) {
                this.wasCR = true;
                this.peek[this.peekCount++] = 10;
                continue;
            }
            if (nw == 10) {
                if (!this.wasCR) {
                    this.peek[this.peekCount++] = 10;
                }
            } else {
                this.peek[this.peekCount++] = nw;
            }
            this.wasCR = false;
        }
        return this.peek[pos];
    }

    private final String readName() throws IOException, XmlPullParserException {
        int pos = this.txtPos;
        int c = this.peek(0);
        if (!(c >= 97 && c <= 122 || c >= 65 && c <= 90 || c == 95 || c == 58 || c >= 192 || this.relaxed)) {
            this.error("name expected");
        }
        do {
            this.push(this.read());
        } while ((c = this.peek(0)) >= 97 && c <= 122 || c >= 65 && c <= 90 || c >= 48 && c <= 57 || c == 95 || c == 45 || c == 58 || c == 46 || c >= 183);
        String result2 = this.get(pos);
        this.txtPos = pos;
        return result2;
    }

    private final void skip() throws IOException {
        int c;
        while ((c = this.peek(0)) <= 32 && c != -1) {
            this.read();
        }
    }

    public void setInput(Reader reader) throws XmlPullParserException {
        this.reader = reader;
        this.line = 1;
        this.column = 0;
        this.type = 0;
        this.name = null;
        this.namespace = null;
        this.degenerated = false;
        this.attributeCount = -1;
        this.encoding = null;
        this.version = null;
        this.standalone = null;
        if (reader == null) {
            return;
        }
        this.srcPos = 0;
        this.srcCount = 0;
        this.peekCount = 0;
        this.depth = 0;
        this.entityMap = new Hashtable();
        this.entityMap.put("amp", "&");
        this.entityMap.put("apos", "'");
        this.entityMap.put("gt", ">");
        this.entityMap.put("lt", "<");
        this.entityMap.put("quot", "\"");
    }

    public void setInput(InputStream is, String _enc) throws XmlPullParserException {
        this.srcPos = 0;
        this.srcCount = 0;
        String enc = _enc;
        if (is == null) {
            throw new IllegalArgumentException();
        }
        try {
            if (enc == null) {
                int i;
                int chk = 0;
                while (this.srcCount < 4 && (i = is.read()) != -1) {
                    chk = chk << 8 | i;
                    this.srcBuf[this.srcCount++] = (char)i;
                }
                if (this.srcCount == 4) {
                    switch (chk) {
                        case 65279: {
                            enc = "UTF-32BE";
                            this.srcCount = 0;
                            break;
                        }
                        case -131072: {
                            enc = "UTF-32LE";
                            this.srcCount = 0;
                            break;
                        }
                        case 60: {
                            enc = "UTF-32BE";
                            this.srcBuf[0] = 60;
                            this.srcCount = 1;
                            break;
                        }
                        case 0x3C000000: {
                            enc = "UTF-32LE";
                            this.srcBuf[0] = 60;
                            this.srcCount = 1;
                            break;
                        }
                        case 3932223: {
                            enc = "UTF-16BE";
                            this.srcBuf[0] = 60;
                            this.srcBuf[1] = 63;
                            this.srcCount = 2;
                            break;
                        }
                        case 1006649088: {
                            enc = "UTF-16LE";
                            this.srcBuf[0] = 60;
                            this.srcBuf[1] = 63;
                            this.srcCount = 2;
                            break;
                        }
                        case 1010792557: {
                            while ((i = is.read()) != -1) {
                                this.srcBuf[this.srcCount++] = (char)i;
                                if (i != 62) continue;
                                String s = new String(this.srcBuf, 0, this.srcCount);
                                int i0 = s.indexOf("encoding");
                                if (i0 == -1) break;
                                while (s.charAt(i0) != '\"' && s.charAt(i0) != '\'') {
                                    ++i0;
                                }
                                char deli = s.charAt(i0++);
                                int i1 = s.indexOf(deli, i0);
                                enc = s.substring(i0, i1);
                                break;
                            }
                        }
                        default: {
                            if ((chk & 0xFFFF0000) == -16842752) {
                                enc = "UTF-16BE";
                                this.srcBuf[0] = (char)(this.srcBuf[2] << 8 | this.srcBuf[3]);
                                this.srcCount = 1;
                                break;
                            }
                            if ((chk & 0xFFFF0000) == -131072) {
                                enc = "UTF-16LE";
                                this.srcBuf[0] = (char)(this.srcBuf[3] << 8 | this.srcBuf[2]);
                                this.srcCount = 1;
                                break;
                            }
                            if ((chk & 0xFFFFFF00) != -272908544) break;
                            enc = "UTF-8";
                            this.srcBuf[0] = this.srcBuf[3];
                            this.srcCount = 1;
                        }
                    }
                }
            }
            if (enc == null) {
                enc = "UTF-8";
            }
            int sc = this.srcCount;
            this.setInput((Reader)new InputStreamReader(is, enc));
            this.encoding = _enc;
            this.srcCount = sc;
        }
        catch (Exception e) {
            throw new XmlPullParserException("Invalid stream or encoding: " + e.toString(), (XmlPullParser)this, (Throwable)e);
        }
    }

    public boolean getFeature(String feature) {
        if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(feature)) {
            return this.processNsp;
        }
        if (this.isProp(feature, false, "relaxed")) {
            return this.relaxed;
        }
        return false;
    }

    public String getInputEncoding() {
        return this.encoding;
    }

    public void defineEntityReplacementText(String entity, String value) throws XmlPullParserException {
        if (this.entityMap == null) {
            throw new RuntimeException("entity replacement text must be defined after setInput!");
        }
        this.entityMap.put(entity, value);
    }

    public Object getProperty(String property) {
        if (this.isProp(property, true, "xmldecl-version")) {
            return this.version;
        }
        if (this.isProp(property, true, "xmldecl-standalone")) {
            return this.standalone;
        }
        if (this.isProp(property, true, "location")) {
            return this.location != null ? this.location : this.reader.toString();
        }
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
        buf.append("@" + this.line + ":" + this.column);
        if (this.location != null) {
            buf.append(" in ");
            buf.append(this.location);
        } else if (this.reader != null) {
            buf.append(" in ");
            buf.append(this.reader.toString());
        }
        return buf.toString();
    }

    public int getLineNumber() {
        return this.line;
    }

    public int getColumnNumber() {
        return this.column;
    }

    public boolean isWhitespace() throws XmlPullParserException {
        if (this.type != 4 && this.type != 7 && this.type != 5) {
            this.exception(ILLEGAL_TYPE);
        }
        return this.isWhitespace;
    }

    public String getText() {
        return this.type < 4 || this.type == 6 && this.unresolved ? null : this.get(0);
    }

    public char[] getTextCharacters(int[] poslen) {
        if (this.type >= 4) {
            if (this.type == 6) {
                poslen[0] = 0;
                poslen[1] = this.name.length();
                return this.name.toCharArray();
            }
            poslen[0] = 0;
            poslen[1] = this.txtPos;
            return this.txtBuf;
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
        this.txtPos = 0;
        this.isWhitespace = true;
        int minType = 9999;
        this.token = false;
        do {
            this.nextImpl();
            if (this.type >= minType) continue;
            minType = this.type;
        } while (minType > 6 || minType >= 4 && this.peekType() >= 4);
        this.type = minType;
        if (this.type > 4) {
            this.type = 4;
        }
        return this.type;
    }

    public int nextToken() throws XmlPullParserException, IOException {
        this.isWhitespace = true;
        this.txtPos = 0;
        this.token = true;
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

    public void require(int type, String namespace, String name) throws XmlPullParserException, IOException {
        if (type != this.type || namespace != null && !namespace.equals(this.getNamespace()) || name != null && !name.equals(this.getName())) {
            this.exception("expected: " + TYPES[type] + " {" + namespace + "}" + name);
        }
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

    public void setFeature(String feature, boolean value) throws XmlPullParserException {
        if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(feature)) {
            this.processNsp = value;
        } else if (this.isProp(feature, false, "relaxed")) {
            this.relaxed = value;
        } else {
            this.exception("unsupported feature: " + feature);
        }
    }

    public void setProperty(String property, Object value) throws XmlPullParserException {
        if (!this.isProp(property, true, "location")) {
            throw new XmlPullParserException("unsupported property: " + property);
        }
        this.location = value;
    }

    public void skipSubTree() throws XmlPullParserException, IOException {
        this.require(2, null, null);
        int level = 1;
        while (level > 0) {
            int eventType = this.next();
            if (eventType == 3) {
                --level;
                continue;
            }
            if (eventType != 2) continue;
            ++level;
        }
    }
}

