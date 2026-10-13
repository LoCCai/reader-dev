/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.lib.icu4j.CharsetRecog_2022
 *  io.legado.app.lib.icu4j.CharsetRecognizer
 */
package io.legado.app.lib.icu4j;

import io.legado.app.lib.icu4j.CharsetRecognizer;

abstract class CharsetRecog_2022
extends CharsetRecognizer {
    CharsetRecog_2022() {
    }

    int match(byte[] text, int textLen, byte[][] escapeSequences) {
        int hits = 0;
        int misses = 0;
        int shifts = 0;
        block0: for (int i = 0; i < textLen; ++i) {
            if (text[i] == 27) {
                block1: for (int escN = 0; escN < escapeSequences.length; ++escN) {
                    byte[] seq = escapeSequences[escN];
                    if (textLen - i < seq.length) continue;
                    for (int j = 1; j < seq.length; ++j) {
                        if (seq[j] != text[i + j]) continue block1;
                    }
                    ++hits;
                    i += seq.length - 1;
                    continue block0;
                }
                ++misses;
            }
            if (text[i] != 14 && text[i] != 15) continue;
            ++shifts;
        }
        if (hits == 0) {
            return 0;
        }
        int quality = (100 * hits - 100 * misses) / (hits + misses);
        if (hits + shifts < 5) {
            quality -= (5 - (hits + shifts)) * 10;
        }
        if (quality < 0) {
            quality = 0;
        }
        return quality;
    }
}

