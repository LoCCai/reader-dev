/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.ManifestItemRefProperties
 *  me.ag2s.epublib.domain.ManifestProperties
 */
package me.ag2s.epublib.domain;

import me.ag2s.epublib.domain.ManifestProperties;

public enum ManifestItemRefProperties implements ManifestProperties
{
    PAGE_SPREAD_LEFT("page-spread-left"),
    PAGE_SPREAD_RIGHT("page-spread-right");

    private final String name;

    private ManifestItemRefProperties(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

