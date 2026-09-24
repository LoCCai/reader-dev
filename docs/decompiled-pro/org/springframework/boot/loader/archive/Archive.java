/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.loader.archive;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.jar.Manifest;

public interface Archive
extends Iterable<Entry> {
    public URL getUrl() throws MalformedURLException;

    public Manifest getManifest() throws IOException;

    public List<Archive> getNestedArchives(EntryFilter var1) throws IOException;

    public static interface EntryFilter {
        public boolean matches(Entry var1);
    }

    public static interface Entry {
        public boolean isDirectory();

        public String getName();
    }
}

