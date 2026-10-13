/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.lib.icu4j.CharsetDetector
 *  io.legado.app.lib.icu4j.CharsetMatch
 *  io.legado.app.lib.icu4j.CharsetRecognizer
 */
package io.legado.app.lib.icu4j;

import io.legado.app.lib.icu4j.CharsetDetector;
import io.legado.app.lib.icu4j.CharsetRecognizer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class CharsetMatch
implements Comparable<CharsetMatch> {
    private final int fConfidence;
    private byte[] fRawInput = null;
    private int fRawLength;
    private final InputStream fInputStream;
    private final String fCharsetName;
    private final String fLang;

    public Reader getReader() {
        InputStream inputStream = this.fInputStream;
        if (inputStream == null) {
            inputStream = new ByteArrayInputStream(this.fRawInput, 0, this.fRawLength);
        }
        try {
            inputStream.reset();
            return new InputStreamReader(inputStream, this.getName());
        }
        catch (IOException e) {
            return null;
        }
    }

    public String getString() throws IOException {
        return this.getString(-1);
    }

    public String getString(int maxLength) throws IOException {
        int startSuffix;
        if (this.fInputStream != null) {
            int bytesRead;
            int max;
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024];
            Reader reader = this.getReader();
            int n = max = maxLength < 0 ? Integer.MAX_VALUE : maxLength;
            while ((bytesRead = reader.read(buffer, 0, Math.min(max, 1024))) >= 0) {
                sb.append(buffer, 0, bytesRead);
                max -= bytesRead;
            }
            reader.close();
            return sb.toString();
        }
        String name = this.getName();
        int n = startSuffix = !name.contains("_rtl") ? name.indexOf("_ltr") : name.indexOf("_rtl");
        if (startSuffix > 0) {
            name = name.substring(0, startSuffix);
        }
        String result2 = new String(this.fRawInput, name);
        return result2;
    }

    public int getConfidence() {
        return this.fConfidence;
    }

    public String getName() {
        return this.fCharsetName;
    }

    public String getLanguage() {
        return this.fLang;
    }

    @Override
    public int compareTo(CharsetMatch other) {
        int compareResult = 0;
        if (this.fConfidence > other.fConfidence) {
            compareResult = 1;
        } else if (this.fConfidence < other.fConfidence) {
            compareResult = -1;
        }
        return compareResult;
    }

    CharsetMatch(CharsetDetector det, CharsetRecognizer rec, int conf) {
        this.fConfidence = conf;
        if (det.fInputStream == null) {
            this.fRawInput = det.fRawInput;
            this.fRawLength = det.fRawLength;
        }
        this.fInputStream = det.fInputStream;
        this.fCharsetName = rec.getName();
        this.fLang = rec.getLanguage();
    }

    CharsetMatch(CharsetDetector det, CharsetRecognizer rec, int conf, String csName, String lang) {
        this.fConfidence = conf;
        if (det.fInputStream == null) {
            this.fRawInput = det.fRawInput;
            this.fRawLength = det.fRawLength;
        }
        this.fInputStream = det.fInputStream;
        this.fCharsetName = csName;
        this.fLang = lang;
    }
}

