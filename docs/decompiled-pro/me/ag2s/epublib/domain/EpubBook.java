/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.domain.Guide
 *  me.ag2s.epublib.domain.GuideReference
 *  me.ag2s.epublib.domain.Metadata
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.Resources
 *  me.ag2s.epublib.domain.Spine
 *  me.ag2s.epublib.domain.SpineReference
 *  me.ag2s.epublib.domain.TOCReference
 *  me.ag2s.epublib.domain.TableOfContents
 */
package me.ag2s.epublib.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import me.ag2s.epublib.domain.Guide;
import me.ag2s.epublib.domain.GuideReference;
import me.ag2s.epublib.domain.Metadata;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.Resources;
import me.ag2s.epublib.domain.Spine;
import me.ag2s.epublib.domain.SpineReference;
import me.ag2s.epublib.domain.TOCReference;
import me.ag2s.epublib.domain.TableOfContents;

/*
 * Exception performing whole class analysis ignored.
 */
public class EpubBook
implements Serializable {
    private static final long serialVersionUID = 2068355170895770100L;
    private Resources resources = new Resources();
    private Metadata metadata = new Metadata();
    private Spine spine = new Spine();
    private TableOfContents tableOfContents = new TableOfContents();
    private final Guide guide = new Guide();
    private Resource opfResource;
    private Resource ncxResource;
    private Resource coverImage;
    private String version = "2.0";

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isEpub3() {
        return this.version.startsWith("3.");
    }

    public TOCReference addSection(TOCReference parentSection, String sectionTitle, Resource resource) {
        return this.addSection(parentSection, sectionTitle, resource, null);
    }

    public TOCReference addSection(TOCReference parentSection, String sectionTitle, Resource resource, String fragmentId) {
        this.getResources().add(resource);
        if (this.spine.findFirstResourceById(resource.getId()) < 0) {
            this.spine.addSpineReference(new SpineReference(resource));
        }
        return parentSection.addChildSection(new TOCReference(sectionTitle, resource, fragmentId));
    }

    public TOCReference addSection(String title, Resource resource) {
        return this.addSection(title, resource, null);
    }

    public TOCReference addSection(String title, Resource resource, String fragmentId) {
        this.getResources().add(resource);
        TOCReference tocReference = this.tableOfContents.addTOCReference(new TOCReference(title, resource, fragmentId));
        if (this.spine.findFirstResourceById(resource.getId()) < 0) {
            this.spine.addSpineReference(new SpineReference(resource));
        }
        return tocReference;
    }

    public void generateSpineFromTableOfContents() {
        Spine spine = new Spine(this.tableOfContents);
        spine.setTocResource(this.spine.getTocResource());
        this.spine = spine;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public Resource addResource(Resource resource) {
        return this.resources.add(resource);
    }

    public Resources getResources() {
        return this.resources;
    }

    public Spine getSpine() {
        return this.spine;
    }

    public void setSpine(Spine spine) {
        this.spine = spine;
    }

    public TableOfContents getTableOfContents() {
        return this.tableOfContents;
    }

    public void setTableOfContents(TableOfContents tableOfContents) {
        this.tableOfContents = tableOfContents;
    }

    public Resource getCoverPage() {
        Resource coverPage = this.guide.getCoverPage();
        if (coverPage == null) {
            coverPage = this.spine.getResource(0);
        }
        return coverPage;
    }

    public void setCoverPage(Resource coverPage) {
        if (coverPage == null) {
            return;
        }
        if (this.resources.notContainsByHref(coverPage.getHref())) {
            this.resources.add(coverPage);
        }
        this.guide.setCoverPage(coverPage);
    }

    public String getTitle() {
        return this.getMetadata().getFirstTitle();
    }

    public Resource getCoverImage() {
        return this.coverImage;
    }

    public void setCoverImage(Resource coverImage) {
        if (coverImage == null) {
            return;
        }
        if (this.resources.notContainsByHref(coverImage.getHref())) {
            this.resources.add(coverImage);
        }
        this.coverImage = coverImage;
    }

    public Guide getGuide() {
        return this.guide;
    }

    public List<Resource> getContents() {
        LinkedHashMap result2 = new LinkedHashMap();
        EpubBook.addToContentsResult((Resource)this.getCoverPage(), result2);
        for (SpineReference spineReference : this.getSpine().getSpineReferences()) {
            EpubBook.addToContentsResult((Resource)spineReference.getResource(), result2);
        }
        for (Resource resource : this.getTableOfContents().getAllUniqueResources()) {
            EpubBook.addToContentsResult((Resource)resource, result2);
        }
        for (GuideReference guideReference : this.getGuide().getReferences()) {
            EpubBook.addToContentsResult((Resource)guideReference.getResource(), result2);
        }
        return new ArrayList<Resource>(result2.values());
    }

    private static void addToContentsResult(Resource resource, Map<String, Resource> allReachableResources) {
        if (resource != null && !allReachableResources.containsKey(resource.getHref())) {
            allReachableResources.put(resource.getHref(), resource);
        }
    }

    public Resource getOpfResource() {
        return this.opfResource;
    }

    public void setOpfResource(Resource opfResource) {
        this.opfResource = opfResource;
    }

    public void setNcxResource(Resource ncxResource) {
        this.ncxResource = ncxResource;
    }

    public Resource getNcxResource() {
        return this.ncxResource;
    }
}

