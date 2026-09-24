/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.browsersupport.NavigationEvent
 *  me.ag2s.epublib.browsersupport.NavigationEventListener
 *  me.ag2s.epublib.browsersupport.Navigator
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.domain.Resource
 */
package me.ag2s.epublib.browsersupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import me.ag2s.epublib.browsersupport.NavigationEvent;
import me.ag2s.epublib.browsersupport.NavigationEventListener;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Resource;

public class Navigator
implements Serializable {
    private static final long serialVersionUID = 1076126986424925474L;
    private EpubBook book;
    private int currentSpinePos;
    private Resource currentResource;
    private int currentPagePos;
    private String currentFragmentId;
    private final List<NavigationEventListener> eventListeners = new ArrayList();

    public Navigator() {
        this(null);
    }

    public Navigator(EpubBook book) {
        this.book = book;
        this.currentSpinePos = 0;
        if (book != null) {
            this.currentResource = book.getCoverPage();
        }
        this.currentPagePos = 0;
    }

    private synchronized void handleEventListeners(NavigationEvent navigationEvent) {
        for (int i = 0; i < this.eventListeners.size(); ++i) {
            NavigationEventListener navigationEventListener = (NavigationEventListener)this.eventListeners.get(i);
            navigationEventListener.navigationPerformed(navigationEvent);
        }
    }

    public boolean addNavigationEventListener(NavigationEventListener navigationEventListener) {
        return this.eventListeners.add(navigationEventListener);
    }

    public boolean removeNavigationEventListener(NavigationEventListener navigationEventListener) {
        return this.eventListeners.remove(navigationEventListener);
    }

    public int gotoFirstSpineSection(Object source) {
        return this.gotoSpineSection(0, source);
    }

    public int gotoPreviousSpineSection(Object source) {
        return this.gotoPreviousSpineSection(0, source);
    }

    public int gotoPreviousSpineSection(int pagePos, Object source) {
        if (this.currentSpinePos < 0) {
            return this.gotoSpineSection(0, pagePos, source);
        }
        return this.gotoSpineSection(this.currentSpinePos - 1, pagePos, source);
    }

    public boolean hasNextSpineSection() {
        return this.currentSpinePos < this.book.getSpine().size() - 1;
    }

    public boolean hasPreviousSpineSection() {
        return this.currentSpinePos > 0;
    }

    public int gotoNextSpineSection(Object source) {
        if (this.currentSpinePos < 0) {
            return this.gotoSpineSection(0, source);
        }
        return this.gotoSpineSection(this.currentSpinePos + 1, source);
    }

    public int gotoResource(String resourceHref, Object source) {
        Resource resource = this.book.getResources().getByHref(resourceHref);
        return this.gotoResource(resource, source);
    }

    public int gotoResource(Resource resource, Object source) {
        return this.gotoResource(resource, 0, null, source);
    }

    public int gotoResource(Resource resource, String fragmentId, Object source) {
        return this.gotoResource(resource, 0, fragmentId, source);
    }

    public int gotoResource(Resource resource, int pagePos, Object source) {
        return this.gotoResource(resource, pagePos, null, source);
    }

    public int gotoResource(Resource resource, int pagePos, String fragmentId, Object source) {
        if (resource == null) {
            return -1;
        }
        NavigationEvent navigationEvent = new NavigationEvent(source, this);
        this.currentResource = resource;
        this.currentSpinePos = this.book.getSpine().getResourceIndex(this.currentResource);
        this.currentPagePos = pagePos;
        this.currentFragmentId = fragmentId;
        this.handleEventListeners(navigationEvent);
        return this.currentSpinePos;
    }

    public int gotoResourceId(String resourceId, Object source) {
        return this.gotoSpineSection(this.book.getSpine().findFirstResourceById(resourceId), source);
    }

    public int gotoSpineSection(int newSpinePos, Object source) {
        return this.gotoSpineSection(newSpinePos, 0, source);
    }

    public int gotoSpineSection(int newSpinePos, int newPagePos, Object source) {
        if (newSpinePos == this.currentSpinePos) {
            return this.currentSpinePos;
        }
        if (newSpinePos < 0 || newSpinePos >= this.book.getSpine().size()) {
            return this.currentSpinePos;
        }
        NavigationEvent navigationEvent = new NavigationEvent(source, this);
        this.currentSpinePos = newSpinePos;
        this.currentPagePos = newPagePos;
        this.currentResource = this.book.getSpine().getResource(this.currentSpinePos);
        this.handleEventListeners(navigationEvent);
        return this.currentSpinePos;
    }

    public int gotoLastSpineSection(Object source) {
        return this.gotoSpineSection(this.book.getSpine().size() - 1, source);
    }

    public void gotoBook(EpubBook book, Object source) {
        NavigationEvent navigationEvent = new NavigationEvent(source, this);
        this.book = book;
        this.currentFragmentId = null;
        this.currentPagePos = 0;
        this.currentResource = null;
        this.currentSpinePos = book.getSpine().getResourceIndex(this.currentResource);
        this.handleEventListeners(navigationEvent);
    }

    public int getCurrentSpinePos() {
        return this.currentSpinePos;
    }

    public Resource getCurrentResource() {
        return this.currentResource;
    }

    public void setCurrentSpinePos(int currentIndex) {
        this.currentSpinePos = currentIndex;
        this.currentResource = this.book.getSpine().getResource(currentIndex);
    }

    public EpubBook getBook() {
        return this.book;
    }

    public int setCurrentResource(Resource currentResource) {
        this.currentSpinePos = this.book.getSpine().getResourceIndex(currentResource);
        this.currentResource = currentResource;
        return this.currentSpinePos;
    }

    public String getCurrentFragmentId() {
        return this.currentFragmentId;
    }

    public int getCurrentSectionPos() {
        return this.currentPagePos;
    }
}

