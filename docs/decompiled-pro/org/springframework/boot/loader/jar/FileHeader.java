/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.loader.jar;

interface FileHeader {
    public boolean hasName(CharSequence var1, char var2);

    public long getLocalHeaderOffset();

    public long getCompressedSize();

    public long getSize();

    public int getMethod();
}

