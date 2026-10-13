/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.lib.tts.constant.OutputFormat
 *  com.htmake.reader.lib.tts.constant.TtsStyleEnum
 *  com.htmake.reader.lib.tts.constant.VoiceEnum
 *  com.htmake.reader.lib.tts.model.SSML
 *  com.htmake.reader.lib.tts.model.SSML$SSMLBuilder
 *  com.htmake.reader.lib.tts.util.Tools
 */
package com.htmake.reader.lib.tts.model;

import com.htmake.reader.lib.tts.constant.OutputFormat;
import com.htmake.reader.lib.tts.constant.TtsStyleEnum;
import com.htmake.reader.lib.tts.constant.VoiceEnum;
import com.htmake.reader.lib.tts.model.SSML;
import com.htmake.reader.lib.tts.util.Tools;
import java.io.Serializable;
import java.util.Optional;

public class SSML
implements Serializable {
    public static String SSML_PATTERN = "X-RequestId:%s\r\nContent-Type:application/ssml+xml\r\nX-Timestamp:%sZ\r\nPath:ssml\r\n\r\n<speak version='1.0' xmlns='http://www.w3.org/2001/10/synthesis' xmlns:mstts='https://www.w3.org/2001/mstts' xml:lang='%s'>\r\n<voice name='%s'>\r\n%s<prosody pitch='%s' rate='%s' volume='%s'>%s</prosody>%s</voice></speak>";
    private String synthesisText;
    private VoiceEnum voice;
    private String rate;
    private String pitch;
    private String volume;
    private TtsStyleEnum style;
    private OutputFormat outputFormat;

    private SSML(String synthesisText, VoiceEnum voice, String rate, String pitch, String volume, TtsStyleEnum style, OutputFormat outputFormat) {
        this.synthesisText = synthesisText;
        this.voice = voice;
        this.rate = rate;
        this.pitch = pitch;
        this.volume = volume;
        this.style = style;
        this.outputFormat = outputFormat;
    }

    public static SSMLBuilder builder() {
        return new SSMLBuilder();
    }

    public String getSynthesisText() {
        return this.synthesisText;
    }

    public void setSynthesisText(String synthesisText) {
        this.synthesisText = synthesisText;
    }

    public VoiceEnum getVoice() {
        return this.voice;
    }

    public void setVoice(VoiceEnum voice) {
        this.voice = voice;
    }

    public String getRate() {
        return this.rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPitch() {
        return this.pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getVolume() {
        return this.volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public TtsStyleEnum getStyle() {
        return this.style;
    }

    public void setStyle(TtsStyleEnum style) {
        this.style = style;
    }

    public OutputFormat getOutputFormat() {
        return this.outputFormat;
    }

    public void setOutputFormat(OutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String toString() {
        return String.format(SSML_PATTERN, Tools.getRandomId(), Tools.date(), Optional.ofNullable(this.voice).orElse(VoiceEnum.zh_CN_XiaoxiaoNeural).getLocale(), Optional.ofNullable(this.voice).orElse(VoiceEnum.zh_CN_XiaoxiaoNeural).getShortName(), Optional.ofNullable(this.style).map(s -> String.format("<mstts:express-as style='%s'>\r\n", s.getValue())).orElse(""), Optional.ofNullable(this.pitch).orElse("+0Hz"), Optional.ofNullable(this.rate).orElse("+0%"), Optional.ofNullable(this.volume).orElse("+0%"), this.synthesisText, Optional.ofNullable(this.style).map(s -> "</mstts:express-as>").orElse(""));
    }
}

