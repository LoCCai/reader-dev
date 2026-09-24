/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.lib.icu4j.CharsetDetector
 *  io.legado.app.lib.icu4j.CharsetMatch
 *  io.legado.app.lib.icu4j.CharsetRecog_Unicode
 *  io.legado.app.lib.icu4j.CharsetRecognizer
 */
package io.legado.app.lib.icu4j;

import io.legado.app.lib.icu4j.CharsetDetector;
import io.legado.app.lib.icu4j.CharsetMatch;
import io.legado.app.lib.icu4j.CharsetRecognizer;

abstract class CharsetRecog_Unicode
extends CharsetRecognizer {
    CharsetRecog_Unicode() {
    }

    abstract String getName();

    abstract CharsetMatch match(CharsetDetector var1);

    static int codeUnit16FromBytes(byte hi, byte lo) {
        return (hi & 0xFF) << 8 | lo & 0xFF;
    }

    static int adjustConfidence(int codeUnit, int confidence) {
        if (codeUnit == 0) {
            confidence -= 10;
        } else if (codeUnit >= 32 && codeUnit <= 255 || codeUnit == 10) {
            confidence += 10;
        }
        if (confidence < 0) {
            confidence = 0;
        } else if (confidence > 100) {
            confidence = 100;
        }
        return confidence;
    }
}

