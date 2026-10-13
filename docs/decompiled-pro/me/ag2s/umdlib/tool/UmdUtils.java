/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.umdlib.tool.UmdUtils
 */
package me.ag2s.umdlib.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.InflaterInputStream;

public class UmdUtils {
    private static final int EOF = -1;
    private static final int BUFFER_SIZE = 8192;
    private static Random random = new Random();

    public static byte[] stringToUnicodeBytes(String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        int len = s.length();
        byte[] ret = new byte[len * 2];
        for (int i = 0; i < len; ++i) {
            char c = s.charAt(i);
            int a = c >> 8;
            int b = c & 0xFF;
            if (a < 0) {
                a += 255;
            }
            if (b < 0) {
                b += 255;
            }
            ret[i * 2] = (byte)b;
            ret[i * 2 + 1] = (byte)a;
        }
        return ret;
    }

    public static String unicodeBytesToString(byte[] bytes2) {
        char[] s = new char[bytes2.length / 2];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length; ++i) {
            byte a = bytes2[i * 2 + 1];
            byte b = bytes2[i * 2];
            int c = (a & 0xFF) << 8 | b & 0xFF;
            if (c < 0) {
                c += 65535;
            }
            char[] c1 = Character.toChars(c);
            sb.append(c1);
        }
        return sb.toString();
    }

    public static String toHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length);
        for (int i = 0; i < bArr.length; ++i) {
            String sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTmp.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] decompress(byte[] compress) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(compress);
        InflaterInputStream iis = new InflaterInputStream(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int c = 0;
        byte[] buf = new byte[8192];
        while ((c = iis.read(buf)) != -1) {
            baos.write(buf, 0, c);
        }
        baos.flush();
        return baos.toByteArray();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void saveFile(File f, byte[] content) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(f);){
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(content);
            bos.flush();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] readFile(File f) throws IOException {
        try (FileInputStream fis = new FileInputStream(f);){
            int ch;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedInputStream bis = new BufferedInputStream(fis);
            while ((ch = bis.read()) >= 0) {
                baos.write(ch);
            }
            baos.flush();
            byte[] byArray = baos.toByteArray();
            return byArray;
        }
    }

    public static byte[] genRandomBytes(int len) {
        if (len <= 0) {
            throw new IllegalArgumentException("Length must > 0: " + len);
        }
        byte[] ret = new byte[len];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = (byte)random.nextInt(256);
        }
        return ret;
    }
}

