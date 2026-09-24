/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.LazyResource
 *  me.ag2s.epublib.domain.LazyResourceProvider
 *  me.ag2s.epublib.domain.MediaTypes
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.util.IOUtil
 */
package me.ag2s.epublib.domain;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import me.ag2s.epublib.domain.LazyResourceProvider;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.util.IOUtil;

public class LazyResource
extends Resource {
    private static final long serialVersionUID = 5089400472352002866L;
    private final String TAG = this.getClass().getName();
    private final LazyResourceProvider resourceProvider;
    private final long cachedSize;

    public LazyResource(LazyResourceProvider resourceProvider, String href) {
        this(resourceProvider, -1L, href);
    }

    public LazyResource(LazyResourceProvider resourceProvider, String href, String originalHref) {
        this(resourceProvider, -1L, href, originalHref);
    }

    public LazyResource(LazyResourceProvider resourceProvider, long size, String href) {
        super(null, null, href, MediaTypes.determineMediaType((String)href));
        this.resourceProvider = resourceProvider;
        this.cachedSize = size;
    }

    public LazyResource(LazyResourceProvider resourceProvider, long size, String href, String originalHref) {
        super(null, null, href, originalHref, MediaTypes.determineMediaType((String)href));
        this.resourceProvider = resourceProvider;
        this.cachedSize = size;
    }

    public InputStream getInputStream() throws IOException {
        if (this.isInitialized()) {
            return new ByteArrayInputStream(this.getData());
        }
        return this.resourceProvider.getResourceStream(this.originalHref);
    }

    public void initialize() throws IOException {
        this.getData();
    }

    public byte[] getData() throws IOException {
        if (this.data == null) {
            InputStream in = this.resourceProvider.getResourceStream(this.originalHref);
            byte[] readData = IOUtil.toByteArray((InputStream)in, (int)((int)this.cachedSize));
            if (readData == null) {
                throw new IOException("Could not load the contents of resource: " + this.getHref());
            }
            this.data = readData;
            in.close();
        }
        return this.data;
    }

    public void close() {
        if (this.resourceProvider != null) {
            this.data = null;
        }
    }

    public boolean isInitialized() {
        return this.data != null;
    }

    public long getSize() {
        if (this.data != null) {
            return this.data.length;
        }
        return this.cachedSize;
    }
}

