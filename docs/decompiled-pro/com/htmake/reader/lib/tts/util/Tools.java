/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.lib.tts.util.Tools
 *  okhttp3.OkHttpClient
 *  okhttp3.Request
 *  okhttp3.Request$Builder
 *  okhttp3.Response
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.htmake.reader.lib.tts.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tools {
    public static final Pattern NO_VOICE_PATTERN = Pattern.compile("[\\s\\p{C}\\p{P}\\p{Z}\\p{S}]");
    public static final String SDF = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z";
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    public static Logger log = LoggerFactory.getLogger(Tools.class);
    private static OkHttpClient client = new OkHttpClient();

    public static String httpGet(String url2) {
        try {
            Request request = new Request.Builder().url(url2).build();
            Response response2 = client.newCall(request).execute();
            log.info("response.toString():{}", (Object)response2.toString());
            log.info("response.isSuccessful():{}", (Object)response2.isSuccessful());
            if (response2.isSuccessful()) {
                String body = response2.body().string();
                return body;
            }
            throw new RuntimeException(String.format("request\uff1a%s fail, message:%s", url2, response2.code()));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isNoVoice(CharSequence charSequence) {
        return NO_VOICE_PATTERN.matcher(charSequence).replaceAll("").isEmpty();
    }

    public static void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    public static String date() {
        return new SimpleDateFormat(SDF).format(new Date());
    }

    public static String localDateTime() {
        return LocalDateTime.now().format(DTF);
    }

    public static String localeToEmoji(Locale locale) {
        String countryCode = locale.getCountry();
        if ("TW".equals(countryCode) && Locale.getDefault().getCountry().equals("CN")) {
            return "";
        }
        int firstLetter = Character.codePointAt(countryCode, 0) - 65 + 127462;
        int secondLetter = Character.codePointAt(countryCode, 1) - 65 + 127462;
        return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
    }

    public static String getRandomId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

