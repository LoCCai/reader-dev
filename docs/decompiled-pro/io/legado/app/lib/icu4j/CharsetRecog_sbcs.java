/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.lib.icu4j.CharsetDetector
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$NGramParser
 *  io.legado.app.lib.icu4j.CharsetRecog_sbcs$NGramParser_IBM420
 *  io.legado.app.lib.icu4j.CharsetRecognizer
 */
package io.legado.app.lib.icu4j;

import io.legado.app.lib.icu4j.CharsetDetector;
import io.legado.app.lib.icu4j.CharsetRecog_sbcs;
import io.legado.app.lib.icu4j.CharsetRecognizer;

abstract class CharsetRecog_sbcs
extends CharsetRecognizer {
    CharsetRecog_sbcs() {
    }

    abstract String getName();

    int match(CharsetDetector det, int[] ngrams, byte[] byteMap) {
        return this.match(det, ngrams, byteMap, (byte)32);
    }

    int match(CharsetDetector det, int[] ngrams, byte[] byteMap, byte spaceChar) {
        NGramParser parser = new NGramParser(ngrams, byteMap);
        return parser.parse(det, spaceChar);
    }

    int matchIBM420(CharsetDetector det, int[] ngrams, byte[] byteMap, byte spaceChar) {
        NGramParser_IBM420 parser = new NGramParser_IBM420(ngrams, byteMap);
        return parser.parse(det, spaceChar);
    }
}

