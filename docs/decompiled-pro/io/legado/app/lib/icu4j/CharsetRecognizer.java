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
import io.legado.app.lib.icu4j.CharsetMatch;

abstract class CharsetRecognizer {
    CharsetRecognizer() {
    }

    abstract String getName();

    public String getLanguage() {
        return null;
    }

    abstract CharsetMatch match(CharsetDetector var1);
}

