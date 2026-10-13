/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.umdlib.domain.UmdChapters
 *  me.ag2s.umdlib.tool.UmdUtils
 *  me.ag2s.umdlib.tool.WrapOutputStream
 */
package me.ag2s.umdlib.domain;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import me.ag2s.umdlib.tool.UmdUtils;
import me.ag2s.umdlib.tool.WrapOutputStream;

public class UmdChapters {
    private static final int DEFAULT_CHUNK_INIT_SIZE = 32768;
    private int TotalContentLen;
    private List<byte[]> titles = new ArrayList();
    public List<Integer> contentLengths = new ArrayList();
    public ByteArrayOutputStream contents = new ByteArrayOutputStream();

    public List<byte[]> getTitles() {
        return this.titles;
    }

    public void addTitle(String s) {
        this.titles.add(UmdUtils.stringToUnicodeBytes((String)s));
    }

    public void addTitle(byte[] s) {
        this.titles.add(s);
    }

    public void addContentLength(Integer integer) {
        this.contentLengths.add(integer);
    }

    public int getContentLength(int index) {
        return (Integer)this.contentLengths.get(index);
    }

    public byte[] getContent(int index) {
        int st2 = (Integer)this.contentLengths.get(index);
        byte[] b = this.contents.toByteArray();
        int end = index + 1 < this.contentLengths.size() ? ((Integer)this.contentLengths.get(index + 1)).intValue() : this.getTotalContentLen();
        System.out.println("\u603b\u957f\u5ea6:" + this.contents.size());
        System.out.println("\u8d77\u59cb\u503c:" + st2);
        System.out.println("\u7ed3\u675f\u503c:" + end);
        byte[] bAr = new byte[end - st2];
        System.arraycopy(b, st2, bAr, 0, bAr.length);
        return bAr;
    }

    public String getContentString(int index) {
        return UmdUtils.unicodeBytesToString((byte[])this.getContent(index)).replace('\u2029', '\n');
    }

    public String getTitle(int index) {
        return UmdUtils.unicodeBytesToString((byte[])((byte[])this.titles.get(index)));
    }

    public void buildChapters(WrapOutputStream wos) throws IOException {
        this.writeChaptersHead(wos);
        this.writeChaptersContentOffset(wos);
        this.writeChaptersTitles(wos);
        this.writeChaptersChunks(wos);
    }

    private void writeChaptersHead(WrapOutputStream wos) throws IOException {
        wos.writeBytes(new int[]{35, 11, 0, 0, 9});
        wos.writeInt(this.contents.size());
    }

    private void writeChaptersContentOffset(WrapOutputStream wos) throws IOException {
        wos.writeBytes(new int[]{35, 131, 0, 0, 9});
        byte[] rb = UmdUtils.genRandomBytes((int)4);
        wos.writeBytes(rb);
        wos.write(36);
        wos.writeBytes(rb);
        wos.writeInt(this.contentLengths.size() * 4 + 9);
        int offset = 0;
        for (Integer n : this.contentLengths) {
            wos.writeInt(offset);
            offset += n.intValue();
        }
    }

    private void writeChaptersTitles(WrapOutputStream wos) throws IOException {
        wos.writeBytes(new int[]{35, 132, 0, 1, 9});
        byte[] rb = UmdUtils.genRandomBytes((int)4);
        wos.writeBytes(rb);
        wos.write(36);
        wos.writeBytes(rb);
        int totalTitlesLen = 0;
        for (byte[] t : this.titles) {
            totalTitlesLen += t.length;
        }
        wos.writeInt(totalTitlesLen + this.titles.size() + 9);
        for (byte[] t : this.titles) {
            wos.writeByte(t.length);
            wos.write(t);
        }
    }

    private void writeChaptersChunks(WrapOutputStream wos) throws IOException {
        byte[] allContents = this.contents.toByteArray();
        byte[] zero16 = new byte[16];
        Arrays.fill(zero16, 0, zero16.length, (byte)0);
        int startPos = 0;
        int len = 0;
        int left = 0;
        int chunkCnt = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(33024);
        ArrayList<byte[]> chunkRbList = new ArrayList<byte[]>();
        while (startPos < allContents.length) {
            left = allContents.length - startPos;
            len = 32768 < left ? 32768 : left;
            bos.reset();
            DeflaterOutputStream zos = new DeflaterOutputStream(bos);
            zos.write(allContents, startPos, len);
            zos.close();
            byte[] chunk = bos.toByteArray();
            byte[] rb = UmdUtils.genRandomBytes((int)4);
            wos.writeByte(36);
            wos.writeBytes(rb);
            chunkRbList.add(rb);
            wos.writeInt(chunk.length + 9);
            wos.write(chunk);
            wos.writeBytes(new int[]{35, 241, 0, 0, 21});
            wos.write(zero16);
            startPos += len;
            ++chunkCnt;
        }
        wos.writeBytes(new int[]{35, 129, 0, 1, 9});
        wos.writeBytes(new int[]{0, 0, 0, 0});
        wos.write(36);
        wos.writeBytes(new int[]{0, 0, 0, 0});
        wos.writeInt(chunkCnt * 4 + 9);
        for (int i = chunkCnt - 1; i >= 0; --i) {
            wos.writeBytes((byte[])chunkRbList.get(i));
        }
    }

    public void addChapter(String title, String content) {
        this.titles.add(UmdUtils.stringToUnicodeBytes((String)title));
        byte[] b = UmdUtils.stringToUnicodeBytes((String)content);
        this.contentLengths.add(b.length);
        try {
            this.contents.write(b);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFile(File f, String title) throws IOException {
        byte[] temp = UmdUtils.readFile((File)f);
        String s = new String(temp);
        this.addChapter(title, s);
    }

    public void addFile(File f) throws IOException {
        String s = f.getName();
        int idx = s.lastIndexOf(46);
        if (idx >= 0) {
            s = s.substring(0, idx);
        }
        this.addFile(f, s);
    }

    public void clearChapters() {
        this.titles.clear();
        this.contentLengths.clear();
        this.contents.reset();
    }

    public int getTotalContentLen() {
        return this.TotalContentLen;
    }

    public void setTotalContentLen(int totalContentLen) {
        this.TotalContentLen = totalContentLen;
    }
}

