/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.Spine
 *  me.ag2s.epublib.domain.SpineReference
 *  me.ag2s.epublib.domain.TableOfContents
 *  me.ag2s.epublib.util.StringUtil
 */
package me.ag2s.epublib.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.SpineReference;
import me.ag2s.epublib.domain.TableOfContents;
import me.ag2s.epublib.util.StringUtil;

/*
 * Exception performing whole class analysis ignored.
 */
public class Spine
implements Serializable {
    private static final long serialVersionUID = 3878483958947357246L;
    private Resource tocResource;
    private List<SpineReference> spineReferences;

    public Spine() {
        this(new ArrayList());
    }

    public Spine(TableOfContents tableOfContents) {
        this.spineReferences = Spine.createSpineReferences((Collection)tableOfContents.getAllUniqueResources());
    }

    public Spine(List<SpineReference> spineReferences) {
        this.spineReferences = spineReferences;
    }

    public static List<SpineReference> createSpineReferences(Collection<Resource> resources) {
        ArrayList<SpineReference> result2 = new ArrayList<SpineReference>(resources.size());
        for (Resource resource : resources) {
            result2.add(new SpineReference(resource));
        }
        return result2;
    }

    public List<SpineReference> getSpineReferences() {
        return this.spineReferences;
    }

    public void setSpineReferences(List<SpineReference> spineReferences) {
        this.spineReferences = spineReferences;
    }

    public Resource getResource(int index) {
        if (index < 0 || index >= this.spineReferences.size()) {
            return null;
        }
        return ((SpineReference)this.spineReferences.get(index)).getResource();
    }

    public int findFirstResourceById(String resourceId) {
        if (StringUtil.isBlank((String)resourceId)) {
            return -1;
        }
        for (int i = 0; i < this.spineReferences.size(); ++i) {
            SpineReference spineReference = (SpineReference)this.spineReferences.get(i);
            if (!resourceId.equals(spineReference.getResourceId())) continue;
            return i;
        }
        return -1;
    }

    public SpineReference addSpineReference(SpineReference spineReference) {
        if (this.spineReferences == null) {
            this.spineReferences = new ArrayList();
        }
        this.spineReferences.add(spineReference);
        return spineReference;
    }

    public SpineReference addResource(Resource resource) {
        return this.addSpineReference(new SpineReference(resource));
    }

    public int size() {
        return this.spineReferences.size();
    }

    public void setTocResource(Resource tocResource) {
        this.tocResource = tocResource;
    }

    public Resource getTocResource() {
        return this.tocResource;
    }

    public int getResourceIndex(Resource currentResource) {
        if (currentResource == null) {
            return -1;
        }
        return this.getResourceIndex(currentResource.getHref());
    }

    public int getResourceIndex(String resourceHref) {
        int result2 = -1;
        if (StringUtil.isBlank((String)resourceHref)) {
            return result2;
        }
        for (int i = 0; i < this.spineReferences.size(); ++i) {
            if (!resourceHref.equals(((SpineReference)this.spineReferences.get(i)).getResource().getHref())) continue;
            result2 = i;
            break;
        }
        return result2;
    }

    public boolean isEmpty() {
        return this.spineReferences.isEmpty();
    }
}

