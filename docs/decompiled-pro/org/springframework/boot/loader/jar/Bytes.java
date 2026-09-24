/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.loader.jar;

final class Bytes {
    private Bytes() {
    }

    public static long littleEndianValue(byte[] bytes2, int offset, int length) {
        long value = 0L;
        for (int i = length - 1; i >= 0; --i) {
            value = value << 8 | (long)(bytes2[offset + i] & 0xFF);
        }
        return value;
    }
}

