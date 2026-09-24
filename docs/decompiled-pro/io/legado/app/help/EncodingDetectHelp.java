/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.help.BytesEncodingDetect
 *  io.legado.app.help.EncodingDetectHelp
 *  io.legado.app.utils.TextUtils
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package io.legado.app.help;

import io.legado.app.help.BytesEncodingDetect;
import io.legado.app.utils.TextUtils;
import java.io.File;
import java.nio.charset.StandardCharsets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * Exception performing whole class analysis ignored.
 */
public class EncodingDetectHelp {
    public static String getHtmlEncode(byte[] bytes2) {
        try {
            Document doc = Jsoup.parse((String)new String(bytes2, StandardCharsets.UTF_8));
            Elements metaTags = doc.getElementsByTag("meta");
            for (Element metaTag : metaTags) {
                String charsetStr = metaTag.attr("charset");
                if (!TextUtils.isEmpty((CharSequence)charsetStr)) {
                    return charsetStr;
                }
                String content = metaTag.attr("content");
                String http_equiv = metaTag.attr("http-equiv");
                if (!http_equiv.toLowerCase().equals("content-type") || TextUtils.isEmpty((CharSequence)(charsetStr = content.toLowerCase().contains("charset") ? content.substring(content.toLowerCase().indexOf("charset") + "charset=".length()) : content.substring(content.toLowerCase().indexOf(";") + 1)))) continue;
                return charsetStr;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return EncodingDetectHelp.getJavaEncode((byte[])bytes2);
    }

    public static String getJavaEncode(byte[] bytes2) {
        int len = bytes2.length > 2000 ? 2000 : bytes2.length;
        byte[] cBytes = new byte[len];
        System.arraycopy(bytes2, 0, cBytes, 0, len);
        BytesEncodingDetect bytesEncodingDetect = new BytesEncodingDetect();
        String code = BytesEncodingDetect.javaname[bytesEncodingDetect.detectEncoding(cBytes)];
        if ("Unicode".equals(code) && cBytes[0] == -1) {
            code = "UTF-16LE";
        }
        return code;
    }

    public static String getJavaEncode(String filePath) {
        byte[] tempByte;
        BytesEncodingDetect s = new BytesEncodingDetect();
        String fileCode = BytesEncodingDetect.javaname[s.detectEncoding(new File(filePath))];
        if ("Unicode".equals(fileCode) && (tempByte = BytesEncodingDetect.getFileBytes((File)new File(filePath)))[0] == -1) {
            fileCode = "UTF-16LE";
        }
        return fileCode;
    }

    public static String getJavaEncode(File file) {
        byte[] tempByte;
        BytesEncodingDetect s = new BytesEncodingDetect();
        String fileCode = BytesEncodingDetect.javaname[s.detectEncoding(file)];
        if ("Unicode".equals(fileCode) && (tempByte = BytesEncodingDetect.getFileBytes((File)file))[0] == -1) {
            fileCode = "UTF-16LE";
        }
        return fileCode;
    }
}

