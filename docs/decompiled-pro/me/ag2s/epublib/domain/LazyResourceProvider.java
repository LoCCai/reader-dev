/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.LazyResourceProvider
 */
package me.ag2s.epublib.domain;

import java.io.IOException;
import java.io.InputStream;

public interface LazyResourceProvider {
    public InputStream getResourceStream(String var1) throws IOException;
}

