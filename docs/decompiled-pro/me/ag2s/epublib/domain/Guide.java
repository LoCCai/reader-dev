/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.Guide
 *  me.ag2s.epublib.domain.GuideReference
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.ResourceReference
 */
package me.ag2s.epublib.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import me.ag2s.epublib.domain.GuideReference;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.ResourceReference;

public class Guide
implements Serializable {
    private static final long serialVersionUID = -6256645339915751189L;
    public static final String DEFAULT_COVER_TITLE = "cover";
    private List<GuideReference> references = new ArrayList();
    private static final int COVERPAGE_NOT_FOUND = -1;
    private static final int COVERPAGE_UNITIALIZED = -2;
    private int coverPageIndex = -1;

    public List<GuideReference> getReferences() {
        return this.references;
    }

    public void setReferences(List<GuideReference> references) {
        this.references = references;
        this.uncheckCoverPage();
    }

    private void uncheckCoverPage() {
        this.coverPageIndex = -2;
    }

    public GuideReference getCoverReference() {
        this.checkCoverPage();
        if (this.coverPageIndex >= 0) {
            return (GuideReference)this.references.get(this.coverPageIndex);
        }
        return null;
    }

    public int setCoverReference(GuideReference guideReference) {
        if (this.coverPageIndex >= 0) {
            this.references.set(this.coverPageIndex, guideReference);
        } else {
            this.references.add(0, guideReference);
            this.coverPageIndex = 0;
        }
        return this.coverPageIndex;
    }

    private void checkCoverPage() {
        if (this.coverPageIndex == -2) {
            this.initCoverPage();
        }
    }

    private void initCoverPage() {
        int result2 = -1;
        for (int i = 0; i < this.references.size(); ++i) {
            GuideReference guideReference = (GuideReference)this.references.get(i);
            if (!guideReference.getType().equals(DEFAULT_COVER_TITLE)) continue;
            result2 = i;
            break;
        }
        this.coverPageIndex = result2;
    }

    public Resource getCoverPage() {
        GuideReference guideReference = this.getCoverReference();
        if (guideReference == null) {
            return null;
        }
        return guideReference.getResource();
    }

    public void setCoverPage(Resource coverPage) {
        GuideReference coverpageGuideReference = new GuideReference(coverPage, DEFAULT_COVER_TITLE, DEFAULT_COVER_TITLE);
        this.setCoverReference(coverpageGuideReference);
    }

    public ResourceReference addReference(GuideReference reference) {
        this.references.add(reference);
        this.uncheckCoverPage();
        return reference;
    }

    public List<GuideReference> getGuideReferencesByType(String referenceTypeName) {
        ArrayList<GuideReference> result2 = new ArrayList<GuideReference>();
        for (GuideReference guideReference : this.references) {
            if (!referenceTypeName.equalsIgnoreCase(guideReference.getType())) continue;
            result2.add(guideReference);
        }
        return result2;
    }
}

