/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.lib.icu4j.CharsetDetector
 *  io.legado.app.lib.icu4j.CharsetDetector$CSRecognizerInfo
 *  io.legado.app.lib.icu4j.CharsetMatch
 *  io.legado.app.lib.icu4j.CharsetRecog_2022$CharsetRecog_2022CN
 *  io.legado.app.lib.icu4j.CharsetRecog_2022$CharsetRecog_2022JP
 *  io.legado.app.lib.icu4j.CharsetRecog_2022$CharsetRecog_2022KR
 *  io.legado.app.lib.icu4j.CharsetRecog_UTF8
 *  io.legado.app.lib.icu4j.CharsetRecog_Unicode$CharsetRecog_UTF_16_BE
 *  io.legado.app.lib.icu4j.CharsetRecog_Unicode$CharsetRecog_UTF_16_LE
 *  io.legado.app.lib.icu4j.CharsetRecog_Unicode$CharsetRecog_UTF_32_BE
 *  io.legado.app.lib.icu4j.CharsetRecog_Unicode$CharsetRecog_UTF_32_LE
 *  io.legado.app.lib.icu4j.CharsetRecog_mbcs$CharsetRecog_big5
 *  io.legado.app.lib.icu4j.CharsetRecog_mbcs$CharsetRecog_euc$CharsetRecog_euc_jp
 *  io.legado.app.lib.icu4j.CharsetRecog_mbcs$CharsetRecog_euc$CharsetRecog_euc_kr
 *  io.legado.app.lib.icu4j.CharsetRecog_mbcs$CharsetRecog_gb_18030
 *  io.legado.app.lib.icu4j.CharsetRecog_mbcs$CharsetRecog_sjis
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_8859_1
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_8859_2
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_8859_5_ru
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_8859_6_ar
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_8859_7_el
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_8859_8_I_he
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_8859_8_he
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_8859_9_tr
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_IBM420_ar_ltr
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_IBM420_ar_rtl
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_IBM424_he_ltr
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_IBM424_he_rtl
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_KOI8_R
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_windows_1251
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$CharsetRecog_windows_1256
 *  io.legado.app.lib.icu4j.CharsetRecognizer
 */
package io.legado.app.lib.icu4j;

import io.legado.app.lib.icu4j.CharsetDetector;
import io.legado.app.lib.icu4j.CharsetMatch;
import io.legado.app.lib.icu4j.CharsetRecog_2022;
import io.legado.app.lib.icu4j.CharsetRecog_UTF8;
import io.legado.app.lib.icu4j.CharsetRecog_Unicode;
import io.legado.app.lib.icu4j.CharsetRecog_mbcs;
import io.legado.app.lib.icu4j.CharsetRecog_sbcs;
import io.legado.app.lib.icu4j.CharsetRecognizer;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CharsetDetector {
    private static final int kBufSize = 8000;
    byte[] fInputBytes = new byte[8000];
    int fInputLen;
    short[] fByteStats = new short[256];
    boolean fC1Bytes = false;
    String fDeclaredEncoding;
    byte[] fRawInput;
    int fRawLength;
    InputStream fInputStream;
    private boolean fStripTags = false;
    private boolean[] fEnabledRecognizers;
    private static final List<CSRecognizerInfo> ALL_CS_RECOGNIZERS;

    public CharsetDetector setDeclaredEncoding(String encoding) {
        this.fDeclaredEncoding = encoding;
        return this;
    }

    public CharsetDetector setText(byte[] in) {
        this.fRawInput = in;
        this.fRawLength = in.length;
        return this;
    }

    public CharsetDetector setText(InputStream in) throws IOException {
        int bytesRead;
        this.fInputStream = in;
        this.fInputStream.mark(8000);
        this.fRawInput = new byte[8000];
        this.fRawLength = 0;
        for (int remainingLength = 8000; remainingLength > 0 && (bytesRead = this.fInputStream.read(this.fRawInput, this.fRawLength, remainingLength)) > 0; remainingLength -= bytesRead) {
            this.fRawLength += bytesRead;
        }
        this.fInputStream.reset();
        return this;
    }

    public CharsetMatch detect() {
        CharsetMatch[] matches = this.detectAll();
        if (matches == null || matches.length == 0) {
            return null;
        }
        return matches[0];
    }

    public CharsetMatch[] detectAll() {
        ArrayList<CharsetMatch> matches = new ArrayList<CharsetMatch>();
        this.MungeInput();
        for (int i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
            CharsetMatch m;
            boolean active;
            CSRecognizerInfo rcinfo = (CSRecognizerInfo)ALL_CS_RECOGNIZERS.get(i);
            boolean bl = active = this.fEnabledRecognizers != null ? this.fEnabledRecognizers[i] : rcinfo.isDefaultEnabled;
            if (!active || (m = rcinfo.recognizer.match(this)) == null) continue;
            matches.add(m);
        }
        Collections.sort(matches);
        Collections.reverse(matches);
        CharsetMatch[] resultArray = new CharsetMatch[matches.size()];
        resultArray = matches.toArray(resultArray);
        return resultArray;
    }

    public Reader getReader(InputStream in, String declaredEncoding) {
        this.fDeclaredEncoding = declaredEncoding;
        try {
            this.setText(in);
            CharsetMatch match = this.detect();
            if (match == null) {
                return null;
            }
            return match.getReader();
        }
        catch (IOException e) {
            return null;
        }
    }

    public String getString(byte[] in, String declaredEncoding) {
        this.fDeclaredEncoding = declaredEncoding;
        try {
            this.setText(in);
            CharsetMatch match = this.detect();
            if (match == null) {
                return null;
            }
            return match.getString(-1);
        }
        catch (IOException e) {
            return null;
        }
    }

    public static String[] getAllDetectableCharsets() {
        String[] allCharsetNames = new String[ALL_CS_RECOGNIZERS.size()];
        for (int i = 0; i < allCharsetNames.length; ++i) {
            allCharsetNames[i] = ((CSRecognizerInfo)CharsetDetector.ALL_CS_RECOGNIZERS.get((int)i)).recognizer.getName();
        }
        return allCharsetNames;
    }

    public boolean inputFilterEnabled() {
        return this.fStripTags;
    }

    public boolean enableInputFilter(boolean filter) {
        boolean previous = this.fStripTags;
        this.fStripTags = filter;
        return previous;
    }

    private void MungeInput() {
        int srci;
        int dsti = 0;
        boolean inMarkup = false;
        int openTags = 0;
        int badTags = 0;
        if (this.fStripTags) {
            for (srci = 0; srci < this.fRawLength && dsti < this.fInputBytes.length; ++srci) {
                byte b = this.fRawInput[srci];
                if (b == 60) {
                    if (inMarkup) {
                        ++badTags;
                    }
                    inMarkup = true;
                    ++openTags;
                }
                if (!inMarkup) {
                    this.fInputBytes[dsti++] = b;
                }
                if (b != 62) continue;
                inMarkup = false;
            }
            this.fInputLen = dsti;
        }
        if (openTags < 5 || openTags / 5 < badTags || this.fInputLen < 100 && this.fRawLength > 600) {
            int limit = this.fRawLength;
            if (limit > 8000) {
                limit = 8000;
            }
            for (srci = 0; srci < limit; ++srci) {
                this.fInputBytes[srci] = this.fRawInput[srci];
            }
            this.fInputLen = srci;
        }
        Arrays.fill(this.fByteStats, (short)0);
        for (srci = 0; srci < this.fInputLen; ++srci) {
            int val;
            int n = val = this.fInputBytes[srci] & 0xFF;
            this.fByteStats[n] = (short)(this.fByteStats[n] + 1);
        }
        this.fC1Bytes = false;
        for (int i = 128; i <= 159; ++i) {
            if (this.fByteStats[i] == 0) continue;
            this.fC1Bytes = true;
            break;
        }
    }

    @Deprecated
    public String[] getDetectableCharsets() {
        ArrayList<String> csnames = new ArrayList<String>(ALL_CS_RECOGNIZERS.size());
        for (int i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
            boolean active;
            CSRecognizerInfo rcinfo = (CSRecognizerInfo)ALL_CS_RECOGNIZERS.get(i);
            boolean bl = active = this.fEnabledRecognizers == null ? rcinfo.isDefaultEnabled : this.fEnabledRecognizers[i];
            if (!active) continue;
            csnames.add(rcinfo.recognizer.getName());
        }
        return csnames.toArray(new String[0]);
    }

    @Deprecated
    public CharsetDetector setDetectableCharset(String encoding, boolean enabled) {
        int i;
        int modIdx = -1;
        boolean isDefaultVal = false;
        for (i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
            CSRecognizerInfo csrinfo = (CSRecognizerInfo)ALL_CS_RECOGNIZERS.get(i);
            if (!csrinfo.recognizer.getName().equals(encoding)) continue;
            modIdx = i;
            isDefaultVal = csrinfo.isDefaultEnabled == enabled;
            break;
        }
        if (modIdx < 0) {
            throw new IllegalArgumentException("Invalid encoding: \"" + encoding + "\"");
        }
        if (this.fEnabledRecognizers == null && !isDefaultVal) {
            this.fEnabledRecognizers = new boolean[ALL_CS_RECOGNIZERS.size()];
            for (i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
                this.fEnabledRecognizers[i] = ((CSRecognizerInfo)CharsetDetector.ALL_CS_RECOGNIZERS.get((int)i)).isDefaultEnabled;
            }
        }
        if (this.fEnabledRecognizers != null) {
            this.fEnabledRecognizers[modIdx] = enabled;
        }
        return this;
    }

    static {
        ArrayList<CSRecognizerInfo> list2 = new ArrayList<CSRecognizerInfo>();
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_UTF8(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_Unicode.CharsetRecog_UTF_16_BE(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_Unicode.CharsetRecog_UTF_16_LE(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_Unicode.CharsetRecog_UTF_32_BE(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_Unicode.CharsetRecog_UTF_32_LE(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_mbcs.CharsetRecog_sjis(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_2022.CharsetRecog_2022JP(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_2022.CharsetRecog_2022CN(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_2022.CharsetRecog_2022KR(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_mbcs.CharsetRecog_gb_18030(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_jp(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_kr(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_mbcs.CharsetRecog_big5(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_8859_1(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_8859_2(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_8859_5_ru(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_8859_6_ar(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_8859_7_el(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_8859_8_I_he(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_8859_8_he(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_windows_1251(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_windows_1256(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_KOI8_R(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_8859_9_tr(), true));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_IBM424_he_rtl(), false));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_IBM424_he_ltr(), false));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_rtl(), false));
        list2.add(new CSRecognizerInfo((CharsetRecognizer)new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_ltr(), false));
        ALL_CS_RECOGNIZERS = Collections.unmodifiableList(list2);
    }
}

