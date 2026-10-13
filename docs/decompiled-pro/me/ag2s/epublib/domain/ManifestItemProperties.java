/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.ManifestItemProperties
 *  me.ag2s.epublib.domain.ManifestProperties
 */
package me.ag2s.epublib.domain;

import me.ag2s.epublib.domain.ManifestProperties;

public enum ManifestItemProperties implements ManifestProperties
{
    COVER_IMAGE("cover-image"),
    MATHML("mathml"),
    NAV("nav"),
    REMOTE_RESOURCES("remote-resources"),
    SCRIPTED("scripted"),
    SVG("svg"),
    SWITCH("switch");

    private final String name;

    private ManifestItemProperties(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

