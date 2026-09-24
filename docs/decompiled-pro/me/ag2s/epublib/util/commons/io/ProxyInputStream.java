/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.util.IOUtil
 *  me.ag2s.epublib.util.commons.io.ProxyInputStream
 */
package me.ag2s.epublib.util.commons.io;

import java.io.Closeable;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import me.ag2s.epublib.util.IOUtil;

public abstract class ProxyInputStream
extends FilterInputStream {
    public ProxyInputStream(InputStream proxy) {
        super(proxy);
    }

    @Override
    public int read() throws IOException {
        try {
            this.beforeRead(1);
            int b = this.in.read();
            this.afterRead(b != -1 ? 1 : -1);
            return b;
        }
        catch (IOException e) {
            this.handleIOException(e);
            return -1;
        }
    }

    @Override
    public int read(byte[] bts) throws IOException {
        try {
            this.beforeRead(IOUtil.length((byte[])bts));
            int n = this.in.read(bts);
            this.afterRead(n);
            return n;
        }
        catch (IOException e) {
            this.handleIOException(e);
            return -1;
        }
    }

    @Override
    public int read(byte[] bts, int off, int len) throws IOException {
        try {
            this.beforeRead(len);
            int n = this.in.read(bts, off, len);
            this.afterRead(n);
            return n;
        }
        catch (IOException e) {
            this.handleIOException(e);
            return -1;
        }
    }

    @Override
    public long skip(long ln) throws IOException {
        try {
            return this.in.skip(ln);
        }
        catch (IOException e) {
            this.handleIOException(e);
            return 0L;
        }
    }

    @Override
    public int available() throws IOException {
        try {
            return super.available();
        }
        catch (IOException e) {
            this.handleIOException(e);
            return 0;
        }
    }

    @Override
    public void close() throws IOException {
        IOUtil.close((Closeable)this.in, arg_0 -> this.handleIOException(arg_0));
    }

    @Override
    public synchronized void mark(int readlimit) {
        this.in.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        try {
            this.in.reset();
        }
        catch (IOException e) {
            this.handleIOException(e);
        }
    }

    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }

    protected void beforeRead(int n) {
    }

    protected void afterRead(int n) {
    }

    protected void handleIOException(IOException e) throws IOException {
        throw e;
    }
}

