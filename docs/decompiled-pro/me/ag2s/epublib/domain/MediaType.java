/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.MediaType
 */
package me.ag2s.epublib.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

public class MediaType
implements Serializable {
    private static final long serialVersionUID = -7256091153727506788L;
    private final String name;
    private final String defaultExtension;
    private final Collection<String> extensions;

    public MediaType(String name, String defaultExtension) {
        this(name, defaultExtension, new String[]{defaultExtension});
    }

    public MediaType(String name, String defaultExtension, String[] extensions) {
        this(name, defaultExtension, Arrays.asList(extensions));
    }

    public int hashCode() {
        if (this.name == null) {
            return 0;
        }
        return this.name.hashCode();
    }

    public MediaType(String name, String defaultExtension, Collection<String> mextensions) {
        this.name = name;
        this.defaultExtension = defaultExtension;
        this.extensions = mextensions;
    }

    public String getName() {
        return this.name;
    }

    public String getDefaultExtension() {
        return this.defaultExtension;
    }

    public Collection<String> getExtensions() {
        return this.extensions;
    }

    public boolean equals(Object otherMediaType) {
        if (!(otherMediaType instanceof MediaType)) {
            return false;
        }
        return this.name.equals(((MediaType)otherMediaType).getName());
    }

    public String toString() {
        return this.name;
    }
}

