/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.ResourceInputStream
 */
package me.ag2s.epublib.domain;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

public class ResourceInputStream
extends FilterInputStream {
    private final ZipFile zipFile;

    public ResourceInputStream(InputStream in, ZipFile zipFile) {
        super(in);
        this.zipFile = zipFile;
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.zipFile.close();
    }
}

