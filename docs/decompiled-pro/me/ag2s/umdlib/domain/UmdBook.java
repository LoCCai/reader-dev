/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.umdlib.domain.UmdBook
 *  me.ag2s.umdlib.domain.UmdChapters
 *  me.ag2s.umdlib.domain.UmdCover
 *  me.ag2s.umdlib.domain.UmdEnd
 *  me.ag2s.umdlib.domain.UmdHeader
 *  me.ag2s.umdlib.tool.WrapOutputStream
 */
package me.ag2s.umdlib.domain;

import java.io.IOException;
import java.io.OutputStream;
import me.ag2s.umdlib.domain.UmdChapters;
import me.ag2s.umdlib.domain.UmdCover;
import me.ag2s.umdlib.domain.UmdEnd;
import me.ag2s.umdlib.domain.UmdHeader;
import me.ag2s.umdlib.tool.WrapOutputStream;

public class UmdBook {
    private int num;
    private UmdHeader header = new UmdHeader();
    private UmdChapters chapters = new UmdChapters();
    private UmdCover cover = new UmdCover();
    private UmdEnd end = new UmdEnd();

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void buildUmd(OutputStream os) throws IOException {
        WrapOutputStream wos = new WrapOutputStream(os);
        this.header.buildHeader(wos);
        this.chapters.buildChapters(wos);
        this.cover.buildCover(wos);
        this.end.buildEnd(wos);
    }

    public UmdHeader getHeader() {
        return this.header;
    }

    public void setHeader(UmdHeader header) {
        this.header = header;
    }

    public UmdChapters getChapters() {
        return this.chapters;
    }

    public void setChapters(UmdChapters chapters) {
        this.chapters = chapters;
    }

    public UmdCover getCover() {
        return this.cover;
    }

    public void setCover(UmdCover cover) {
        this.cover = cover;
    }

    public UmdEnd getEnd() {
        return this.end;
    }

    public void setEnd(UmdEnd end) {
        this.end = end;
    }
}

