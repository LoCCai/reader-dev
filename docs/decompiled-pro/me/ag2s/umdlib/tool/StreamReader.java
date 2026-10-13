/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.umdlib.tool.StreamReader
 *  me.ag2s.umdlib.tool.UmdUtils
 */
package me.ag2s.umdlib.tool;

import java.io.IOException;
import java.io.InputStream;
import me.ag2s.umdlib.tool.UmdUtils;

public class StreamReader {
    private InputStream is;
    private long offset;
    private long size;

    public long getOffset() {
        return this.offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    private void incCount(int value) {
        int temp = (int)(this.offset + (long)value);
        if (temp < 0) {
            temp = Integer.MAX_VALUE;
        }
        this.offset = temp;
    }

    public StreamReader(InputStream inputStream) throws IOException {
        this.is = inputStream;
    }

    public short readUint8() throws IOException {
        byte[] b = new byte[1];
        this.is.read(b);
        this.incCount(1);
        return (short)(b[0] & 0xFF);
    }

    public byte readByte() throws IOException {
        byte[] b = new byte[1];
        this.is.read(b);
        this.incCount(1);
        return b[0];
    }

    public byte[] readBytes(int len) throws IOException {
        if (len < 1) {
            System.out.println(len);
            throw new IllegalArgumentException("Length must > 0: " + len);
        }
        byte[] b = new byte[len];
        this.is.read(b);
        this.incCount(len);
        return b;
    }

    public String readHex(int len) throws IOException {
        if (len < 1) {
            System.out.println(len);
            throw new IllegalArgumentException("Length must > 0: " + len);
        }
        byte[] b = new byte[len];
        this.is.read(b);
        this.incCount(len);
        return UmdUtils.toHex((byte[])b);
    }

    public short readShort() throws IOException {
        byte[] b = new byte[2];
        this.is.read(b);
        this.incCount(2);
        short x = (short)((b[0] & 0xFF) << 8 | (b[1] & 0xFF) << 0);
        return x;
    }

    public short readShortLe() throws IOException {
        byte[] b = new byte[2];
        this.is.read(b);
        this.incCount(2);
        short x = (short)((b[1] & 0xFF) << 8 | (b[0] & 0xFF) << 0);
        return x;
    }

    public int readInt() throws IOException {
        byte[] b = new byte[4];
        this.is.read(b);
        this.incCount(4);
        int x = (b[0] & 0xFF) << 24 | (b[1] & 0xFF) << 16 | (b[2] & 0xFF) << 8 | (b[3] & 0xFF) << 0;
        return x;
    }

    public int readIntLe() throws IOException {
        byte[] b = new byte[4];
        this.is.read(b);
        this.incCount(4);
        int x = (b[3] & 0xFF) << 24 | (b[2] & 0xFF) << 16 | (b[1] & 0xFF) << 8 | (b[0] & 0xFF) << 0;
        return x;
    }

    public void skip(int len) throws IOException {
        this.readBytes(len);
    }

    public byte[] read(byte[] b) throws IOException {
        this.is.read(b);
        this.incCount(b.length);
        return b;
    }

    public byte[] read(byte[] b, int off, int len) throws IOException {
        this.is.read(b, off, len);
        this.incCount(len);
        return b;
    }
}

