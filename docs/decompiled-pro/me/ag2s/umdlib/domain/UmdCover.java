/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.umdlib.domain.UmdCover
 *  me.ag2s.umdlib.tool.UmdUtils
 *  me.ag2s.umdlib.tool.WrapOutputStream
 */
package me.ag2s.umdlib.domain;

import java.io.File;
import java.io.IOException;
import me.ag2s.umdlib.tool.UmdUtils;
import me.ag2s.umdlib.tool.WrapOutputStream;

public class UmdCover {
    private static int DEFAULT_COVER_WIDTH = 120;
    private static int DEFAULT_COVER_HEIGHT = 160;
    private byte[] coverData;

    public UmdCover() {
    }

    public UmdCover(byte[] coverData) {
        this.coverData = coverData;
    }

    public void load(File f) throws IOException {
        this.coverData = UmdUtils.readFile((File)f);
    }

    public void load(String fileName) throws IOException {
        this.load(new File(fileName));
    }

    public void initDefaultCover(String title) throws IOException {
    }

    public void buildCover(WrapOutputStream wos) throws IOException {
        if (this.coverData == null || this.coverData.length == 0) {
            return;
        }
        wos.writeBytes(new int[]{35, 130, 0, 1, 10, 1});
        byte[] rb = UmdUtils.genRandomBytes((int)4);
        wos.writeBytes(rb);
        wos.write(36);
        wos.writeBytes(rb);
        wos.writeInt(this.coverData.length + 9);
        wos.write(this.coverData);
    }

    public byte[] getCoverData() {
        return this.coverData;
    }

    public void setCoverData(byte[] coverData) {
        this.coverData = coverData;
    }
}

