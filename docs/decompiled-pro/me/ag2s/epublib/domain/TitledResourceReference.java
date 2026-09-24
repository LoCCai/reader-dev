/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.ResourceReference
 *  me.ag2s.epublib.domain.TitledResourceReference
 *  me.ag2s.epublib.util.StringUtil
 */
package me.ag2s.epublib.domain;

import java.io.Serializable;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.ResourceReference;
import me.ag2s.epublib.util.StringUtil;

public class TitledResourceReference
extends ResourceReference
implements Serializable {
    private static final long serialVersionUID = 3918155020095190080L;
    private String fragmentId;
    private String title;

    @Deprecated
    public TitledResourceReference(Resource resource) {
        this(resource, null);
    }

    public TitledResourceReference(Resource resource, String title) {
        this(resource, title, null);
    }

    public TitledResourceReference(Resource resource, String title, String fragmentId) {
        super(resource);
        this.title = title;
        this.fragmentId = fragmentId;
    }

    public String getFragmentId() {
        return this.fragmentId;
    }

    public void setFragmentId(String fragmentId) {
        this.fragmentId = fragmentId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompleteHref() {
        if (StringUtil.isBlank((String)this.fragmentId)) {
            return this.resource.getHref();
        }
        return this.resource.getHref() + '#' + this.fragmentId;
    }

    public Resource getResource() {
        if (this.resource != null && this.title != null) {
            this.resource.setTitle(this.title);
        }
        return this.resource;
    }

    public void setResource(Resource resource, String fragmentId) {
        super.setResource(resource);
        this.fragmentId = fragmentId;
    }

    public void setResource(Resource resource) {
        this.setResource(resource, null);
    }
}

