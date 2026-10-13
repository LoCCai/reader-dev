/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.ResourceReference
 *  me.ag2s.epublib.domain.SpineReference
 */
package me.ag2s.epublib.domain;

import java.io.Serializable;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.ResourceReference;

public class SpineReference
extends ResourceReference
implements Serializable {
    private static final long serialVersionUID = -7921609197351510248L;
    private boolean linear;

    public SpineReference(Resource resource) {
        this(resource, true);
    }

    public SpineReference(Resource resource, boolean linear) {
        super(resource);
        this.linear = linear;
    }

    public boolean isLinear() {
        return this.linear;
    }

    public void setLinear(boolean linear) {
        this.linear = linear;
    }
}

