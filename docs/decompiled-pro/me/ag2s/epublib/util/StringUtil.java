/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.util.StringUtil
 */
package me.ag2s.epublib.util;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Exception performing whole class analysis ignored.
 */
public class StringUtil {
    public static String collapsePathDots(String path) {
        String[] stringParts = path.split("/");
        ArrayList<String> parts = new ArrayList<String>(Arrays.asList(stringParts));
        for (int i = 0; i < parts.size() - 1; ++i) {
            String currentDir = (String)parts.get(i);
            if (currentDir.length() == 0 || currentDir.equals(".")) {
                parts.remove(i);
                --i;
                continue;
            }
            if (!currentDir.equals("..")) continue;
            parts.remove(i - 1);
            parts.remove(i - 1);
            i -= 2;
        }
        StringBuilder result2 = new StringBuilder();
        if (path.startsWith("/")) {
            result2.append('/');
        }
        for (int i = 0; i < parts.size(); ++i) {
            result2.append((String)parts.get(i));
            if (i >= parts.size() - 1) continue;
            result2.append('/');
        }
        return result2.toString();
    }

    public static boolean isNotBlank(String text) {
        return !StringUtil.isBlank((String)text);
    }

    public static boolean isBlank(String text) {
        if (StringUtil.isEmpty((String)text)) {
            return true;
        }
        for (int i = 0; i < text.length(); ++i) {
            if (Character.isWhitespace(text.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }

    public static boolean endsWithIgnoreCase(String source, String suffix) {
        if (StringUtil.isEmpty((String)suffix)) {
            return true;
        }
        if (StringUtil.isEmpty((String)source)) {
            return false;
        }
        if (suffix.length() > source.length()) {
            return false;
        }
        return source.substring(source.length() - suffix.length()).toLowerCase().endsWith(suffix.toLowerCase());
    }

    public static String defaultIfNull(String text) {
        return StringUtil.defaultIfNull((String)text, (String)"");
    }

    public static String defaultIfNull(String text, String defaultValue) {
        if (text == null) {
            return defaultValue;
        }
        return text;
    }

    public static boolean equals(String text1, String text2) {
        if (text1 == null) {
            return text2 == null;
        }
        return text1.equals(text2);
    }

    public static String toString(Object ... keyValues) {
        StringBuilder result2 = new StringBuilder();
        result2.append('[');
        for (int i = 0; i < keyValues.length; i += 2) {
            if (i > 0) {
                result2.append(", ");
            }
            result2.append(keyValues[i]);
            result2.append(": ");
            Object value = null;
            if (i + 1 < keyValues.length) {
                value = keyValues[i + 1];
            }
            if (value == null) {
                result2.append("<null>");
                continue;
            }
            result2.append('\'');
            result2.append(value);
            result2.append('\'');
        }
        result2.append(']');
        return result2.toString();
    }

    public static int hashCode(String ... values) {
        int result2 = 31;
        for (String value : values) {
            result2 ^= String.valueOf(value).hashCode();
        }
        return result2;
    }

    public static String substringBefore(String text, char separator) {
        if (StringUtil.isEmpty((String)text)) {
            return text;
        }
        int sepPos = text.indexOf(separator);
        if (sepPos < 0) {
            return text;
        }
        return text.substring(0, sepPos);
    }

    public static String substringBeforeLast(String text, char separator) {
        if (StringUtil.isEmpty((String)text)) {
            return text;
        }
        int cPos = text.lastIndexOf(separator);
        if (cPos < 0) {
            return text;
        }
        return text.substring(0, cPos);
    }

    public static String substringAfterLast(String text, char separator) {
        if (StringUtil.isEmpty((String)text)) {
            return text;
        }
        int cPos = text.lastIndexOf(separator);
        if (cPos < 0) {
            return "";
        }
        return text.substring(cPos + 1);
    }

    public static String substringAfter(String text, char c) {
        if (StringUtil.isEmpty((String)text)) {
            return text;
        }
        int cPos = text.indexOf(c);
        if (cPos < 0) {
            return "";
        }
        return text.substring(cPos + 1);
    }

    public static String formatHtml(String text) {
        StringBuilder body = new StringBuilder();
        for (String s : text.split("\\r?\\n")) {
            if ((s = s.replaceAll("^\\s+|\\s+$", "")).length() <= 0) continue;
            if (s.matches("(?i)^<img\\s([^>]+)/?>$")) {
                body.append(s.replaceAll("(?i)^<img\\s([^>]+)/?>$", "<div class=\"duokan-image-single\"><img class=\"picture-80\" $1/></div>"));
                continue;
            }
            body.append("<p>").append(s).append("</p>");
        }
        return body.toString();
    }
}

