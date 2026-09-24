/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.umdlib.domain.UmdHeader
 *  me.ag2s.umdlib.tool.UmdUtils
 *  me.ag2s.umdlib.tool.WrapOutputStream
 */
package me.ag2s.umdlib.domain;

import java.io.IOException;
import me.ag2s.umdlib.tool.UmdUtils;
import me.ag2s.umdlib.tool.WrapOutputStream;

public class UmdHeader {
    private byte umdType;
    private String title;
    private String author;
    private String year;
    private String month;
    private String day;
    private String bookType;
    private String bookMan;
    private String shopKeeper;
    private static final byte B_type_umd = 1;
    private static final byte B_type_title = 2;
    private static final byte B_type_author = 3;
    private static final byte B_type_year = 4;
    private static final byte B_type_month = 5;
    private static final byte B_type_day = 6;
    private static final byte B_type_bookType = 7;
    private static final byte B_type_bookMan = 8;
    private static final byte B_type_shopKeeper = 9;

    public byte getUmdType() {
        return this.umdType;
    }

    public void setUmdType(byte umdType) {
        this.umdType = umdType;
    }

    public void buildHeader(WrapOutputStream wos) throws IOException {
        wos.writeBytes(new int[]{137, 155, 154, 222});
        wos.writeByte(35);
        wos.writeBytes(new int[]{1, 0, 0, 8});
        wos.writeByte(1);
        wos.writeBytes(UmdUtils.genRandomBytes((int)2));
        this.buildType(wos, (byte)2, this.getTitle());
        this.buildType(wos, (byte)3, this.getAuthor());
        this.buildType(wos, (byte)4, this.getYear());
        this.buildType(wos, (byte)5, this.getMonth());
        this.buildType(wos, (byte)6, this.getDay());
        this.buildType(wos, (byte)7, this.getBookType());
        this.buildType(wos, (byte)8, this.getBookMan());
        this.buildType(wos, (byte)9, this.getShopKeeper());
    }

    public void buildType(WrapOutputStream wos, byte type, String content) throws IOException {
        if (content == null || content.length() == 0) {
            return;
        }
        wos.writeBytes(new int[]{35, type, 0, 0});
        byte[] temp = UmdUtils.stringToUnicodeBytes((String)content);
        wos.writeByte(temp.length + 5);
        wos.write(temp);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookMan() {
        return this.bookMan;
    }

    public void setBookMan(String bookMan) {
        this.bookMan = bookMan;
    }

    public String getShopKeeper() {
        return this.shopKeeper;
    }

    public void setShopKeeper(String shopKeeper) {
        this.shopKeeper = shopKeeper;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getBookType() {
        return this.bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String toString() {
        return "UmdHeader{umdType=" + this.umdType + ", title='" + this.title + '\'' + ", author='" + this.author + '\'' + ", year='" + this.year + '\'' + ", month='" + this.month + '\'' + ", day='" + this.day + '\'' + ", bookType='" + this.bookType + '\'' + ", bookMan='" + this.bookMan + '\'' + ", shopKeeper='" + this.shopKeeper + '\'' + '}';
    }
}

