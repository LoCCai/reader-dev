/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.lib.icu4j.CharsetDetector
 *  io.legado.app.lib.icu4j.CharsetRecog_mbcs
 *  io.legado.app.lib.icu4j.CharsetRecog_mbcs$iteratedChar
 *  io.legado.app.lib.icu4j.CharsetRecognizer
 */
package io.legado.app.lib.icu4j;

import io.legado.app.lib.icu4j.CharsetDetector;
import io.legado.app.lib.icu4j.CharsetRecog_mbcs;
import io.legado.app.lib.icu4j.CharsetRecognizer;
import java.util.Arrays;

abstract class CharsetRecog_mbcs
extends CharsetRecognizer {
    CharsetRecog_mbcs() {
    }

    abstract String getName();

    int match(CharsetDetector det, int[] commonChars) {
        int confidence;
        block13: {
            int singleByteCharCount = 0;
            int doubleByteCharCount = 0;
            int commonCharCount = 0;
            int badCharCount = 0;
            int totalCharCount = 0;
            confidence = 0;
            iteratedChar iter = new iteratedChar();
            iter.reset();
            while (this.nextChar(iter, det)) {
                ++totalCharCount;
                if (iter.error) {
                    ++badCharCount;
                } else {
                    long cv = (long)iter.charValue & 0xFFFFFFFFL;
                    if (cv <= 255L) {
                        ++singleByteCharCount;
                    } else {
                        ++doubleByteCharCount;
                        if (commonChars != null && Arrays.binarySearch(commonChars, (int)cv) >= 0) {
                            ++commonCharCount;
                        }
                    }
                }
                if (badCharCount < 2 || badCharCount * 5 < doubleByteCharCount) continue;
                break block13;
            }
            if (doubleByteCharCount <= 10 && badCharCount == 0) {
                confidence = doubleByteCharCount == 0 && totalCharCount < 10 ? 0 : 10;
            } else if (doubleByteCharCount < 20 * badCharCount) {
                confidence = 0;
            } else if (commonChars == null) {
                confidence = 30 + doubleByteCharCount - 20 * badCharCount;
                if (confidence > 100) {
                    confidence = 100;
                }
            } else {
                double maxVal = Math.log((float)doubleByteCharCount / 4.0f);
                double scaleFactor = 90.0 / maxVal;
                confidence = (int)(Math.log(commonCharCount + 1) * scaleFactor + 10.0);
                confidence = Math.min(confidence, 100);
            }
        }
        return confidence;
    }

    abstract boolean nextChar(iteratedChar var1, CharsetDetector var2);
}

