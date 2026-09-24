/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.FileResourceProvider
 *  me.ag2s.epublib.domain.LazyResourceProvider
 */
package me.ag2s.epublib.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import me.ag2s.epublib.domain.LazyResourceProvider;

public class FileResourceProvider
implements LazyResourceProvider {
    String dir;

    public FileResourceProvider(String parentDir) {
        this.dir = parentDir;
    }

    public FileResourceProvider(File parentFile) {
        this.dir = parentFile.getPath();
    }

    public InputStream getResourceStream(String href) throws IOException {
        return new FileInputStream(new File(this.dir, href));
    }
}

