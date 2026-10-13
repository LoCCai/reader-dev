/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.browsersupport.NavigationEvent
 *  me.ag2s.epublib.browsersupport.NavigationEventListener
 *  me.ag2s.epublib.browsersupport.NavigationHistory
 *  me.ag2s.epublib.browsersupport.NavigationHistory$Location
 *  me.ag2s.epublib.browsersupport.Navigator
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.domain.Resource
 */
package me.ag2s.epublib.browsersupport;

import java.util.ArrayList;
import java.util.List;
import me.ag2s.epublib.browsersupport.NavigationEvent;
import me.ag2s.epublib.browsersupport.NavigationEventListener;
import me.ag2s.epublib.browsersupport.NavigationHistory;
import me.ag2s.epublib.browsersupport.Navigator;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Resource;

public class NavigationHistory
implements NavigationEventListener {
    public static final int DEFAULT_MAX_HISTORY_SIZE = 1000;
    private static final long DEFAULT_HISTORY_WAIT_TIME = 1000L;
    private long lastUpdateTime = 0L;
    private List<Location> locations = new ArrayList();
    private final Navigator navigator;
    private int currentPos = -1;
    private int currentSize = 0;
    private int maxHistorySize = 1000;
    private long historyWaitTime = 1000L;

    public NavigationHistory(Navigator navigator) {
        this.navigator = navigator;
        navigator.addNavigationEventListener((NavigationEventListener)this);
        this.initBook(navigator.getBook());
    }

    public int getCurrentPos() {
        return this.currentPos;
    }

    public int getCurrentSize() {
        return this.currentSize;
    }

    public void initBook(EpubBook book) {
        if (book == null) {
            return;
        }
        this.locations = new ArrayList();
        this.currentPos = -1;
        this.currentSize = 0;
        if (this.navigator.getCurrentResource() != null) {
            this.addLocation(this.navigator.getCurrentResource().getHref());
        }
    }

    public long getHistoryWaitTime() {
        return this.historyWaitTime;
    }

    public void setHistoryWaitTime(long historyWaitTime) {
        this.historyWaitTime = historyWaitTime;
    }

    public void addLocation(Resource resource) {
        if (resource == null) {
            return;
        }
        this.addLocation(resource.getHref());
    }

    public void addLocation(Location location) {
        if (!this.locations.isEmpty() && location.getHref().equals(((Location)this.locations.get(this.currentPos)).getHref())) {
            return;
        }
        ++this.currentPos;
        if (this.currentPos != this.currentSize) {
            this.locations.set(this.currentPos, location);
        } else {
            this.locations.add(location);
            this.checkHistorySize();
        }
        this.currentSize = this.currentPos + 1;
    }

    private void checkHistorySize() {
        while (this.locations.size() > this.maxHistorySize) {
            this.locations.remove(0);
            --this.currentSize;
            --this.currentPos;
        }
    }

    public void addLocation(String href) {
        this.addLocation(new Location(href));
    }

    private String getLocationHref(int pos) {
        if (pos < 0 || pos >= this.locations.size()) {
            return null;
        }
        return ((Location)this.locations.get(this.currentPos)).getHref();
    }

    public boolean move(int delta) {
        if (this.currentPos + delta < 0 || this.currentPos + delta >= this.currentSize) {
            return false;
        }
        this.currentPos += delta;
        this.navigator.gotoResource(this.getLocationHref(this.currentPos), (Object)this);
        return true;
    }

    public void navigationPerformed(NavigationEvent navigationEvent) {
        if (this == navigationEvent.getSource()) {
            return;
        }
        if (navigationEvent.getCurrentResource() == null) {
            return;
        }
        if (System.currentTimeMillis() - this.lastUpdateTime > this.historyWaitTime) {
            this.addLocation(navigationEvent.getOldResource());
            this.addLocation(navigationEvent.getCurrentResource().getHref());
        }
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public String getCurrentHref() {
        if (this.currentPos < 0 || this.currentPos >= this.locations.size()) {
            return null;
        }
        return ((Location)this.locations.get(this.currentPos)).getHref();
    }

    public void setMaxHistorySize(int maxHistorySize) {
        this.maxHistorySize = maxHistorySize;
    }

    public int getMaxHistorySize() {
        return this.maxHistorySize;
    }
}

