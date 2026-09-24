/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.TOCReference
 *  me.ag2s.epublib.domain.TableOfContents
 */
package me.ag2s.epublib.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.TOCReference;

/*
 * Exception performing whole class analysis ignored.
 */
public class TableOfContents
implements Serializable {
    private static final long serialVersionUID = -3147391239966275152L;
    public static final String DEFAULT_PATH_SEPARATOR = "/";
    private List<TOCReference> tocReferences;

    public TableOfContents() {
        this(new ArrayList());
    }

    public TableOfContents(List<TOCReference> tocReferences) {
        this.tocReferences = tocReferences;
    }

    public List<TOCReference> getTocReferences() {
        return this.tocReferences;
    }

    public void setTocReferences(List<TOCReference> tocReferences) {
        this.tocReferences = tocReferences;
    }

    public TOCReference addSection(Resource resource, String path) {
        return this.addSection(resource, path, "/");
    }

    public TOCReference addSection(Resource resource, String path, String pathSeparator) {
        String[] pathElements = path.split(pathSeparator);
        return this.addSection(resource, pathElements);
    }

    private static TOCReference findTocReferenceByTitle(String title, List<TOCReference> tocReferences) {
        for (TOCReference tocReference : tocReferences) {
            if (!title.equals(tocReference.getTitle())) continue;
            return tocReference;
        }
        return null;
    }

    public TOCReference addSection(Resource resource, String[] pathElements) {
        if (pathElements == null || pathElements.length == 0) {
            return null;
        }
        TOCReference result2 = null;
        List currentTocReferences = this.tocReferences;
        for (String currentTitle : pathElements) {
            result2 = TableOfContents.findTocReferenceByTitle((String)currentTitle, (List)currentTocReferences);
            if (result2 == null) {
                result2 = new TOCReference(currentTitle, null);
                currentTocReferences.add(result2);
            }
            currentTocReferences = result2.getChildren();
        }
        result2.setResource(resource);
        return result2;
    }

    public TOCReference addSection(Resource resource, int[] pathElements, String sectionTitlePrefix, String sectionNumberSeparator) {
        if (pathElements == null || pathElements.length == 0) {
            return null;
        }
        TOCReference result2 = null;
        List currentTocReferences = this.tocReferences;
        for (int i = 0; i < pathElements.length; ++i) {
            int currentIndex = pathElements[i];
            result2 = currentIndex > 0 && currentIndex < currentTocReferences.size() - 1 ? (TOCReference)currentTocReferences.get(currentIndex) : null;
            if (result2 == null) {
                this.paddTOCReferences(currentTocReferences, pathElements, i, sectionTitlePrefix, sectionNumberSeparator);
                result2 = (TOCReference)currentTocReferences.get(currentIndex);
            }
            currentTocReferences = result2.getChildren();
        }
        result2.setResource(resource);
        return result2;
    }

    private void paddTOCReferences(List<TOCReference> currentTocReferences, int[] pathElements, int pathPos, String sectionPrefix, String sectionNumberSeparator) {
        for (int i = currentTocReferences.size(); i <= pathElements[pathPos]; ++i) {
            String sectionTitle = this.createSectionTitle(pathElements, pathPos, i, sectionPrefix, sectionNumberSeparator);
            currentTocReferences.add(new TOCReference(sectionTitle, null));
        }
    }

    private String createSectionTitle(int[] pathElements, int pathPos, int lastPos, String sectionPrefix, String sectionNumberSeparator) {
        StringBuilder title = new StringBuilder(sectionPrefix);
        for (int i = 0; i < pathPos; ++i) {
            if (i > 0) {
                title.append(sectionNumberSeparator);
            }
            title.append(pathElements[i] + 1);
        }
        if (pathPos > 0) {
            title.append(sectionNumberSeparator);
        }
        title.append(lastPos + 1);
        return title.toString();
    }

    public TOCReference addTOCReference(TOCReference tocReference) {
        if (this.tocReferences == null) {
            this.tocReferences = new ArrayList();
        }
        this.tocReferences.add(tocReference);
        return tocReference;
    }

    public List<Resource> getAllUniqueResources() {
        HashSet uniqueHrefs = new HashSet();
        ArrayList<Resource> result2 = new ArrayList<Resource>();
        TableOfContents.getAllUniqueResources(uniqueHrefs, result2, (List)this.tocReferences);
        return result2;
    }

    private static void getAllUniqueResources(Set<String> uniqueHrefs, List<Resource> result2, List<TOCReference> tocReferences) {
        for (TOCReference tocReference : tocReferences) {
            Resource resource = tocReference.getResource();
            if (resource != null && !uniqueHrefs.contains(resource.getHref())) {
                uniqueHrefs.add(resource.getHref());
                result2.add(resource);
            }
            TableOfContents.getAllUniqueResources(uniqueHrefs, result2, (List)tocReference.getChildren());
        }
    }

    public int size() {
        return TableOfContents.getTotalSize((Collection)this.tocReferences);
    }

    private static int getTotalSize(Collection<TOCReference> tocReferences) {
        int result2 = tocReferences.size();
        for (TOCReference tocReference : tocReferences) {
            result2 += TableOfContents.getTotalSize((Collection)tocReference.getChildren());
        }
        return result2;
    }

    public int calculateDepth() {
        return this.calculateDepth(this.tocReferences, 0);
    }

    private int calculateDepth(List<TOCReference> tocReferences, int currentDepth) {
        int maxChildDepth = 0;
        for (TOCReference tocReference : tocReferences) {
            int childDepth = this.calculateDepth(tocReference.getChildren(), 1);
            if (childDepth <= maxChildDepth) continue;
            maxChildDepth = childDepth;
        }
        return currentDepth + maxChildDepth;
    }
}

