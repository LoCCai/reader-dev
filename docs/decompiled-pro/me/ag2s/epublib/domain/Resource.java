/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.MediaType
 *  me.ag2s.epublib.domain.MediaTypes
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.util.IOUtil
 *  me.ag2s.epublib.util.StringUtil
 *  me.ag2s.epublib.util.commons.io.XmlStreamReader
 */
package me.ag2s.epublib.domain;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import me.ag2s.epublib.domain.MediaType;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.util.IOUtil;
import me.ag2s.epublib.util.StringUtil;
import me.ag2s.epublib.util.commons.io.XmlStreamReader;

public class Resource
implements Serializable {
    private static final long serialVersionUID = 1043946707835004037L;
    private String id;
    private String title;
    private String href;
    private String properties;
    protected final String originalHref;
    private MediaType mediaType;
    private String inputEncoding;
    protected byte[] data;

    public Resource(String href) {
        this(null, new byte[0], href, MediaTypes.determineMediaType((String)href));
    }

    public Resource(byte[] data, MediaType mediaType) {
        this(null, data, null, mediaType);
    }

    public Resource(byte[] data, String href) {
        this(null, data, href, MediaTypes.determineMediaType((String)href), "UTF-8");
    }

    public Resource(Reader in, String href) throws IOException {
        this(null, IOUtil.toByteArray((Reader)in, (String)"UTF-8"), href, MediaTypes.determineMediaType((String)href), "UTF-8");
    }

    public Resource(InputStream in, String href) throws IOException {
        this(null, IOUtil.toByteArray((InputStream)in), href, MediaTypes.determineMediaType((String)href));
    }

    public Resource(String id, byte[] data, String href, MediaType mediaType) {
        this(id, data, href, mediaType, "UTF-8");
    }

    public Resource(String id, byte[] data, String href, String originalHref, MediaType mediaType) {
        this(id, data, href, originalHref, mediaType, "UTF-8");
    }

    public Resource(String id, byte[] data, String href, MediaType mediaType, String inputEncoding) {
        this.id = id;
        this.href = href;
        this.originalHref = href;
        this.mediaType = mediaType;
        this.inputEncoding = inputEncoding;
        this.data = data;
    }

    public Resource(String id, byte[] data, String href, String originalHref, MediaType mediaType, String inputEncoding) {
        this.id = id;
        this.href = href;
        this.originalHref = originalHref;
        this.mediaType = mediaType;
        this.inputEncoding = inputEncoding;
        this.data = data;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.getData());
    }

    public byte[] getData() throws IOException {
        return this.data;
    }

    public void close() {
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public long getSize() {
        return this.data.length;
    }

    public String getTitle() {
        return this.title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getHref() {
        return this.href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getInputEncoding() {
        return this.inputEncoding;
    }

    public void setInputEncoding(String encoding) {
        this.inputEncoding = encoding;
    }

    public Reader getReader() throws IOException {
        return new XmlStreamReader((InputStream)new ByteArrayInputStream(this.getData()), this.getInputEncoding());
    }

    public int hashCode() {
        return this.href.hashCode();
    }

    public boolean equals(Object resourceObject) {
        if (!(resourceObject instanceof Resource)) {
            return false;
        }
        return this.href.equals(((Resource)resourceObject).getHref());
    }

    public MediaType getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProperties() {
        return this.properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String toString() {
        return StringUtil.toString((Object[])new Object[]{"id", this.id, "title", this.title, "encoding", this.inputEncoding, "mediaType", this.mediaType, "href", this.href, "size", this.data == null ? 0 : this.data.length});
    }
}

