/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.util.NoCloseWriter
 */
package me.ag2s.epublib.util;

import java.io.IOException;
import java.io.Writer;

public class NoCloseWriter
extends Writer {
    private final Writer writer;

    public NoCloseWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() throws IOException {
        this.writer.flush();
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        this.writer.write(cbuf, off, len);
    }
}

