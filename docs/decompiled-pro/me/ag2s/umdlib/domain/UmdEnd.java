/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.umdlib.domain.UmdEnd
 *  me.ag2s.umdlib.tool.WrapOutputStream
 */
package me.ag2s.umdlib.domain;

import java.io.IOException;
import me.ag2s.umdlib.tool.WrapOutputStream;

public class UmdEnd {
    public void buildEnd(WrapOutputStream wos) throws IOException {
        wos.writeBytes(new int[]{35, 12, 0, 1, 9});
        wos.writeInt(wos.getWritten() + 4);
    }
}

