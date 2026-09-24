/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.lib.tts.exceptions.TtsException
 */
package com.htmake.reader.lib.tts.exceptions;

public class TtsException
extends RuntimeException {
    private TtsException(String message) {
        super(message);
    }

    public static TtsException of(String message) {
        return new TtsException(message);
    }
}

