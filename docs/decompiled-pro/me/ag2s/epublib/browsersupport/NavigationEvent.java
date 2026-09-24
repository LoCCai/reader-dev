/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.browsersupport.NavigationEvent
 *  me.ag2s.epublib.browsersupport.Navigator
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.util.StringUtil
 */
package me.ag2s.epublib.browsersupport;

import java.util.EventObject;
import me.ag2s.epublib.browsersupport.Navigator;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.util.StringUtil;

public class NavigationEvent
extends EventObject {
    private static final long serialVersionUID = -6346750144308952762L;
    private Resource oldResource;
    private int oldSpinePos;
    private Navigator navigator;
    private EpubBook oldBook;
    private int oldSectionPos;
    private String oldFragmentId;

    public NavigationEvent(Object source) {
        super(source);
    }

    public NavigationEvent(Object source, Navigator navigator) {
        super(source);
        this.navigator = navigator;
        this.oldBook = navigator.getBook();
        this.oldFragmentId = navigator.getCurrentFragmentId();
        this.oldSectionPos = navigator.getCurrentSectionPos();
        this.oldResource = navigator.getCurrentResource();
        this.oldSpinePos = navigator.getCurrentSpinePos();
    }

    public int getOldSectionPos() {
        return this.oldSectionPos;
    }

    public Navigator getNavigator() {
        return this.navigator;
    }

    public String getOldFragmentId() {
        return this.oldFragmentId;
    }

    void setOldFragmentId(String oldFragmentId) {
        this.oldFragmentId = oldFragmentId;
    }

    public EpubBook getOldBook() {
        return this.oldBook;
    }

    void setOldPagePos(int oldPagePos) {
        this.oldSectionPos = oldPagePos;
    }

    public int getCurrentSectionPos() {
        return this.navigator.getCurrentSectionPos();
    }

    public int getOldSpinePos() {
        return this.oldSpinePos;
    }

    public int getCurrentSpinePos() {
        return this.navigator.getCurrentSpinePos();
    }

    public String getCurrentFragmentId() {
        return this.navigator.getCurrentFragmentId();
    }

    public boolean isBookChanged() {
        if (this.oldBook == null) {
            return true;
        }
        return this.oldBook != this.navigator.getBook();
    }

    public boolean isSpinePosChanged() {
        return this.getOldSpinePos() != this.getCurrentSpinePos();
    }

    public boolean isFragmentChanged() {
        return StringUtil.equals((String)this.getOldFragmentId(), (String)this.getCurrentFragmentId());
    }

    public Resource getOldResource() {
        return this.oldResource;
    }

    public Resource getCurrentResource() {
        return this.navigator.getCurrentResource();
    }

    public void setOldResource(Resource oldResource) {
        this.oldResource = oldResource;
    }

    public void setOldSpinePos(int oldSpinePos) {
        this.oldSpinePos = oldSpinePos;
    }

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    public void setOldBook(EpubBook oldBook) {
        this.oldBook = oldBook;
    }

    public EpubBook getCurrentBook() {
        return this.getNavigator().getBook();
    }

    public boolean isResourceChanged() {
        return this.oldResource != this.getCurrentResource();
    }

    @Override
    public String toString() {
        return StringUtil.toString((Object[])new Object[]{"oldSectionPos", this.oldSectionPos, "oldResource", this.oldResource, "oldBook", this.oldBook, "oldFragmentId", this.oldFragmentId, "oldSpinePos", this.oldSpinePos, "currentPagePos", this.getCurrentSectionPos(), "currentResource", this.getCurrentResource(), "currentBook", this.getCurrentBook(), "currentFragmentId", this.getCurrentFragmentId(), "currentSpinePos", this.getCurrentSpinePos()});
    }

    public boolean isSectionPosChanged() {
        return this.oldSectionPos != this.getCurrentSectionPos();
    }
}

