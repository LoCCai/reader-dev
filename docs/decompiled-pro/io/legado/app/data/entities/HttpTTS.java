/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.script.SimpleBindings
 *  io.legado.app.data.entities.BaseSource
 *  io.legado.app.data.entities.BaseSource$DefaultImpls
 *  io.legado.app.data.entities.HttpTTS
 *  io.legado.app.data.entities.HttpTTS$Companion
 *  io.legado.app.help.http.StrResponse
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.analyzeRule.QueryTTF
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jsoup.Connection$Response
 */
package io.legado.app.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.script.SimpleBindings;
import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.HttpTTS;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.QueryTTF;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;

@JsonIgnoreProperties(value={"headerMap", "source", "_userNameSpace", "userNameSpace"})
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b(\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0087\b\u0018\u0000 P2\u00020\u0001:\u0001PB\u008d\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0011J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00104\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u000b\u00105\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0005H\u00c6\u0003J\t\u00108\u001a\u00020\u0005H\u00c6\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010<\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010=\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0096\u0001\u0010?\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010@J\u0013\u0010A\u001a\u00020\u000e2\b\u0010B\u001a\u0004\u0018\u00010CH\u00d6\u0003J\b\u0010D\u001a\u00020\u0005H\u0016J\n\u0010E\u001a\u0004\u0018\u00010\u001aH\u0016J\b\u0010F\u001a\u00020\u0005H\u0016J\b\u0010G\u001a\u00020\u0005H\u0016J\t\u0010H\u001a\u00020IH\u00d6\u0001J\u0010\u0010J\u001a\u00020K2\b\u0010L\u001a\u0004\u0018\u00010\u001aJ\u000e\u0010M\u001a\u00020K2\u0006\u0010N\u001a\u00020\u0005J\t\u0010O\u001a\u00020\u0005H\u00d6\u0001R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0014\"\u0004\b\u0018\u0010\u0016R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0096\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001f\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0014\"\u0004\b!\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0014\"\u0004\b%\u0010\u0016R\u001a\u0010\u0010\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010#\"\u0004\b'\u0010(R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0014\"\u0004\b*\u0010\u0016R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0014\"\u0004\b,\u0010\u0016R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u0014\"\u0004\b.\u0010\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0014\"\u0004\b0\u0010\u0016R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0014\"\u0004\b2\u0010\u0016\u00a8\u0006Q"}, d2={"Lio/legado/app/data/entities/HttpTTS;", "Lio/legado/app/data/entities/BaseSource;", "id", "", "name", "", "url", "contentType", "concurrentRate", "loginUrl", "loginUi", "header", "jsLib", "enabledCookieJar", "", "loginCheckJs", "lastUpdateTime", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;J)V", "_userNameSpace", "getConcurrentRate", "()Ljava/lang/String;", "setConcurrentRate", "(Ljava/lang/String;)V", "getContentType", "setContentType", "debugLog", "Lio/legado/app/model/DebugLog;", "getEnabledCookieJar", "()Ljava/lang/Boolean;", "setEnabledCookieJar", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "getHeader", "setHeader", "getId", "()J", "getJsLib", "setJsLib", "getLastUpdateTime", "setLastUpdateTime", "(J)V", "getLoginCheckJs", "setLoginCheckJs", "getLoginUi", "setLoginUi", "getLoginUrl", "setLoginUrl", "getName", "setName", "getUrl", "setUrl", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;J)Lio/legado/app/data/entities/HttpTTS;", "equals", "other", "", "getKey", "getLogger", "getTag", "getUserNameSpace", "hashCode", "", "setLogger", "", "logger", "setUserNameSpace", "nameSpace", "toString", "Companion", "reader-pro"})
public final class HttpTTS
implements BaseSource {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final long id;
    @NotNull
    private String name;
    @NotNull
    private String url;
    @Nullable
    private String contentType;
    @Nullable
    private String concurrentRate;
    @Nullable
    private String loginUrl;
    @Nullable
    private String loginUi;
    @Nullable
    private String header;
    @Nullable
    private String jsLib;
    @Nullable
    private Boolean enabledCookieJar;
    @Nullable
    private String loginCheckJs;
    private long lastUpdateTime;
    @NotNull
    private transient String _userNameSpace;
    @Nullable
    private transient DebugLog debugLog;

    public HttpTTS(long id, @NotNull String name, @NotNull String url2, @Nullable String contentType, @Nullable String concurrentRate, @Nullable String loginUrl, @Nullable String loginUi, @Nullable String header, @Nullable String jsLib, @Nullable Boolean enabledCookieJar, @Nullable String loginCheckJs, long lastUpdateTime) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        this.id = id;
        this.name = name;
        this.url = url2;
        this.contentType = contentType;
        this.concurrentRate = concurrentRate;
        this.loginUrl = loginUrl;
        this.loginUi = loginUi;
        this.header = header;
        this.jsLib = jsLib;
        this.enabledCookieJar = enabledCookieJar;
        this.loginCheckJs = loginCheckJs;
        this.lastUpdateTime = lastUpdateTime;
        this._userNameSpace = "";
    }

    public /* synthetic */ HttpTTS(long l, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, Boolean bl, String string9, long l2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            l = System.currentTimeMillis();
        }
        if ((n & 2) != 0) {
            string = "";
        }
        if ((n & 4) != 0) {
            string2 = "";
        }
        if ((n & 8) != 0) {
            string3 = null;
        }
        if ((n & 0x10) != 0) {
            string4 = "0";
        }
        if ((n & 0x20) != 0) {
            string5 = null;
        }
        if ((n & 0x40) != 0) {
            string6 = null;
        }
        if ((n & 0x80) != 0) {
            string7 = null;
        }
        if ((n & 0x100) != 0) {
            string8 = null;
        }
        if ((n & 0x200) != 0) {
            bl = false;
        }
        if ((n & 0x400) != 0) {
            string9 = null;
        }
        if ((n & 0x800) != 0) {
            l2 = System.currentTimeMillis();
        }
        this(l, string, string2, string3, string4, string5, string6, string7, string8, bl, string9, l2);
    }

    public final long getId() {
        return this.id;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.name = string;
    }

    @NotNull
    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.url = string;
    }

    @Nullable
    public final String getContentType() {
        return this.contentType;
    }

    public final void setContentType(@Nullable String string) {
        this.contentType = string;
    }

    @Nullable
    public String getConcurrentRate() {
        return this.concurrentRate;
    }

    public void setConcurrentRate(@Nullable String string) {
        this.concurrentRate = string;
    }

    @Nullable
    public String getLoginUrl() {
        return this.loginUrl;
    }

    public void setLoginUrl(@Nullable String string) {
        this.loginUrl = string;
    }

    @Nullable
    public String getLoginUi() {
        return this.loginUi;
    }

    public void setLoginUi(@Nullable String string) {
        this.loginUi = string;
    }

    @Nullable
    public String getHeader() {
        return this.header;
    }

    public void setHeader(@Nullable String string) {
        this.header = string;
    }

    @Nullable
    public final String getJsLib() {
        return this.jsLib;
    }

    public final void setJsLib(@Nullable String string) {
        this.jsLib = string;
    }

    @Nullable
    public Boolean getEnabledCookieJar() {
        return this.enabledCookieJar;
    }

    public void setEnabledCookieJar(@Nullable Boolean bl) {
        this.enabledCookieJar = bl;
    }

    @Nullable
    public final String getLoginCheckJs() {
        return this.loginCheckJs;
    }

    public final void setLoginCheckJs(@Nullable String string) {
        this.loginCheckJs = string;
    }

    public final long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public final void setLastUpdateTime(long l) {
        this.lastUpdateTime = l;
    }

    public final void setUserNameSpace(@NotNull String nameSpace) {
        Intrinsics.checkNotNullParameter((Object)nameSpace, (String)"nameSpace");
        this._userNameSpace = nameSpace;
    }

    @NotNull
    public String getUserNameSpace() {
        return this._userNameSpace;
    }

    public final void setLogger(@Nullable DebugLog logger2) {
        this.debugLog = logger2;
    }

    @Nullable
    public DebugLog getLogger() {
        return this.debugLog;
    }

    @NotNull
    public String getTag() {
        return this.name;
    }

    @NotNull
    public String getKey() {
        return Intrinsics.stringPlus((String)"httpTts:", (Object)this.id);
    }

    @Nullable
    public Object evalJS(@NotNull String jsStr, @NotNull Function1<? super SimpleBindings, Unit> bindingsConfig) throws Exception {
        return BaseSource.DefaultImpls.evalJS((BaseSource)this, (String)jsStr, bindingsConfig);
    }

    @Nullable
    public byte[] aesBase64DecodeToByteArray(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesBase64DecodeToByteArray((BaseSource)((BaseSource)this), (String)str, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesBase64DecodeToString(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesBase64DecodeToString((BaseSource)((BaseSource)this), (String)str, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesDecodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesDecodeArgsBase64Str((BaseSource)((BaseSource)this), (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @Nullable
    public byte[] aesDecodeToByteArray(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesDecodeToByteArray((BaseSource)((BaseSource)this), (String)str, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesDecodeToString(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesDecodeToString((BaseSource)((BaseSource)this), (String)str, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesEncodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesEncodeArgsBase64Str((BaseSource)((BaseSource)this), (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @Nullable
    public byte[] aesEncodeToBase64ByteArray(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesEncodeToBase64ByteArray((BaseSource)((BaseSource)this), (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesEncodeToBase64String(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesEncodeToBase64String((BaseSource)((BaseSource)this), (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public byte[] aesEncodeToByteArray(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesEncodeToByteArray((BaseSource)((BaseSource)this), (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesEncodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesEncodeToString((BaseSource)((BaseSource)this), (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String ajax(@NotNull String urlStr) {
        return BaseSource.DefaultImpls.ajax((BaseSource)((BaseSource)this), (String)urlStr);
    }

    @NotNull
    public StrResponse[] ajaxAll(@NotNull String[] urlList) {
        return BaseSource.DefaultImpls.ajaxAll((BaseSource)((BaseSource)this), (String[])urlList);
    }

    @NotNull
    public String androidId() {
        return BaseSource.DefaultImpls.androidId((BaseSource)((BaseSource)this));
    }

    @NotNull
    public String base64Decode(@NotNull String str) {
        return BaseSource.DefaultImpls.base64Decode((BaseSource)((BaseSource)this), (String)str);
    }

    @NotNull
    public String base64Decode(@NotNull String str, int flags) {
        return BaseSource.DefaultImpls.base64Decode((BaseSource)((BaseSource)this), (String)str, (int)flags);
    }

    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String str) {
        return BaseSource.DefaultImpls.base64DecodeToByteArray((BaseSource)((BaseSource)this), (String)str);
    }

    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String str, int flags) {
        return BaseSource.DefaultImpls.base64DecodeToByteArray((BaseSource)((BaseSource)this), (String)str, (int)flags);
    }

    @Nullable
    public String base64Encode(@NotNull String str) {
        return BaseSource.DefaultImpls.base64Encode((BaseSource)((BaseSource)this), (String)str);
    }

    @Nullable
    public String base64Encode(@NotNull String str, int flags) {
        return BaseSource.DefaultImpls.base64Encode((BaseSource)((BaseSource)this), (String)str, (int)flags);
    }

    @Nullable
    public String cacheFile(@NotNull String urlStr) {
        return BaseSource.DefaultImpls.cacheFile((BaseSource)((BaseSource)this), (String)urlStr);
    }

    @Nullable
    public String cacheFile(@NotNull String urlStr, int saveTime) {
        return BaseSource.DefaultImpls.cacheFile((BaseSource)((BaseSource)this), (String)urlStr, (int)saveTime);
    }

    @NotNull
    public StrResponse connect(@NotNull String urlStr) {
        return BaseSource.DefaultImpls.connect((BaseSource)((BaseSource)this), (String)urlStr);
    }

    @NotNull
    public StrResponse connect(@NotNull String urlStr, @Nullable String header) {
        return BaseSource.DefaultImpls.connect((BaseSource)((BaseSource)this), (String)urlStr, (String)header);
    }

    public void deleteFile(@NotNull String path) {
        BaseSource.DefaultImpls.deleteFile((BaseSource)((BaseSource)this), (String)path);
    }

    @Nullable
    public String desBase64DecodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.desBase64DecodeToString((BaseSource)((BaseSource)this), (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String desDecodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.desDecodeToString((BaseSource)((BaseSource)this), (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String desEncodeToBase64String(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.desEncodeToBase64String((BaseSource)((BaseSource)this), (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String desEncodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.desEncodeToString((BaseSource)((BaseSource)this), (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String digestBase64Str(@NotNull String data, @NotNull String algorithm) {
        return BaseSource.DefaultImpls.digestBase64Str((BaseSource)((BaseSource)this), (String)data, (String)algorithm);
    }

    @Nullable
    public String digestHex(@NotNull String data, @NotNull String algorithm) {
        return BaseSource.DefaultImpls.digestHex((BaseSource)((BaseSource)this), (String)data, (String)algorithm);
    }

    @NotNull
    public String downloadFile(@NotNull String content, @NotNull String url2) {
        return BaseSource.DefaultImpls.downloadFile((BaseSource)((BaseSource)this), (String)content, (String)url2);
    }

    @NotNull
    public String encodeURI(@NotNull String str) {
        return BaseSource.DefaultImpls.encodeURI((BaseSource)((BaseSource)this), (String)str);
    }

    @NotNull
    public String encodeURI(@NotNull String str, @NotNull String enc) {
        return BaseSource.DefaultImpls.encodeURI((BaseSource)((BaseSource)this), (String)str, (String)enc);
    }

    @NotNull
    public Connection.Response get(@NotNull String urlStr, @NotNull Map<String, String> headers) {
        return BaseSource.DefaultImpls.get((BaseSource)((BaseSource)this), (String)urlStr, headers);
    }

    @NotNull
    public String getCookie(@NotNull String tag, @Nullable String key) {
        return BaseSource.DefaultImpls.getCookie((BaseSource)((BaseSource)this), (String)tag, (String)key);
    }

    @NotNull
    public File getFile(@NotNull String path) {
        return BaseSource.DefaultImpls.getFile((BaseSource)((BaseSource)this), (String)path);
    }

    @NotNull
    public HashMap<String, String> getHeaderMap(boolean hasLoginHeader) {
        return BaseSource.DefaultImpls.getHeaderMap((BaseSource)this, (boolean)hasLoginHeader);
    }

    @Nullable
    public String getLoginHeader() {
        return BaseSource.DefaultImpls.getLoginHeader((BaseSource)this);
    }

    @Nullable
    public Map<String, String> getLoginHeaderMap() {
        return BaseSource.DefaultImpls.getLoginHeaderMap((BaseSource)this);
    }

    @Nullable
    public String getLoginInfo() {
        return BaseSource.DefaultImpls.getLoginInfo((BaseSource)this);
    }

    @Nullable
    public Map<String, String> getLoginInfoMap() {
        return BaseSource.DefaultImpls.getLoginInfoMap((BaseSource)this);
    }

    @Nullable
    public String getLoginJs() {
        return BaseSource.DefaultImpls.getLoginJs((BaseSource)this);
    }

    @Nullable
    public BaseSource getSource() {
        return BaseSource.DefaultImpls.getSource((BaseSource)this);
    }

    @NotNull
    public String getTxtInFolder(@NotNull String unzipPath) {
        return BaseSource.DefaultImpls.getTxtInFolder((BaseSource)((BaseSource)this), (String)unzipPath);
    }

    @Nullable
    public String getVariable() {
        return BaseSource.DefaultImpls.getVariable((BaseSource)this);
    }

    @Nullable
    public byte[] getZipByteArrayContent(@NotNull String url2, @NotNull String path) {
        return BaseSource.DefaultImpls.getZipByteArrayContent((BaseSource)((BaseSource)this), (String)url2, (String)path);
    }

    @NotNull
    public String getZipStringContent(@NotNull String url2, @NotNull String path) {
        return BaseSource.DefaultImpls.getZipStringContent((BaseSource)((BaseSource)this), (String)url2, (String)path);
    }

    @NotNull
    public String getZipStringContent(@NotNull String url2, @NotNull String path, @NotNull String charsetName) {
        return BaseSource.DefaultImpls.getZipStringContent((BaseSource)((BaseSource)this), (String)url2, (String)path, (String)charsetName);
    }

    @NotNull
    public Connection.Response head(@NotNull String urlStr, @NotNull Map<String, String> headers) {
        return BaseSource.DefaultImpls.head((BaseSource)((BaseSource)this), (String)urlStr, headers);
    }

    @NotNull
    public String htmlFormat(@NotNull String str) {
        return BaseSource.DefaultImpls.htmlFormat((BaseSource)((BaseSource)this), (String)str);
    }

    @NotNull
    public String importScript(@NotNull String path) {
        return BaseSource.DefaultImpls.importScript((BaseSource)((BaseSource)this), (String)path);
    }

    @NotNull
    public String log(@NotNull String msg) {
        return BaseSource.DefaultImpls.log((BaseSource)((BaseSource)this), (String)msg);
    }

    public void logType(@Nullable Object any) {
        BaseSource.DefaultImpls.logType((BaseSource)((BaseSource)this), (Object)any);
    }

    public void login() {
        BaseSource.DefaultImpls.login((BaseSource)this);
    }

    public void longToast(@Nullable Object msg) {
        BaseSource.DefaultImpls.longToast((BaseSource)((BaseSource)this), (Object)msg);
    }

    @NotNull
    public String md5Encode(@NotNull String str) {
        return BaseSource.DefaultImpls.md5Encode((BaseSource)((BaseSource)this), (String)str);
    }

    @NotNull
    public String md5Encode16(@NotNull String str) {
        return BaseSource.DefaultImpls.md5Encode16((BaseSource)((BaseSource)this), (String)str);
    }

    @NotNull
    public Connection.Response post(@NotNull String urlStr, @NotNull String body, @NotNull Map<String, String> headers) {
        return BaseSource.DefaultImpls.post((BaseSource)((BaseSource)this), (String)urlStr, (String)body, headers);
    }

    public void putLoginHeader(@NotNull String header) {
        BaseSource.DefaultImpls.putLoginHeader((BaseSource)this, (String)header);
    }

    public boolean putLoginInfo(@NotNull String info) {
        return BaseSource.DefaultImpls.putLoginInfo((BaseSource)this, (String)info);
    }

    @Nullable
    public QueryTTF queryBase64TTF(@Nullable String base64) {
        return BaseSource.DefaultImpls.queryBase64TTF((BaseSource)((BaseSource)this), (String)base64);
    }

    @Nullable
    public QueryTTF queryTTF(@Nullable String str) {
        return BaseSource.DefaultImpls.queryTTF((BaseSource)((BaseSource)this), (String)str);
    }

    @NotNull
    public String randomUUID() {
        return BaseSource.DefaultImpls.randomUUID((BaseSource)((BaseSource)this));
    }

    @Nullable
    public byte[] readFile(@NotNull String path) {
        return BaseSource.DefaultImpls.readFile((BaseSource)((BaseSource)this), (String)path);
    }

    @NotNull
    public String readTxtFile(@NotNull String path) {
        return BaseSource.DefaultImpls.readTxtFile((BaseSource)((BaseSource)this), (String)path);
    }

    @NotNull
    public String readTxtFile(@NotNull String path, @NotNull String charsetName) {
        return BaseSource.DefaultImpls.readTxtFile((BaseSource)((BaseSource)this), (String)path, (String)charsetName);
    }

    public void removeLoginHeader() {
        BaseSource.DefaultImpls.removeLoginHeader((BaseSource)this);
    }

    public void removeLoginInfo() {
        BaseSource.DefaultImpls.removeLoginInfo((BaseSource)this);
    }

    @NotNull
    public String replaceFont(@NotNull String text, @Nullable QueryTTF font1, @Nullable QueryTTF font2) {
        return BaseSource.DefaultImpls.replaceFont((BaseSource)((BaseSource)this), (String)text, (QueryTTF)font1, (QueryTTF)font2);
    }

    public void setVariable(@Nullable String variable) {
        BaseSource.DefaultImpls.setVariable((BaseSource)this, (String)variable);
    }

    @NotNull
    public String timeFormat(long time) {
        return BaseSource.DefaultImpls.timeFormat((BaseSource)((BaseSource)this), (long)time);
    }

    @Nullable
    public String timeFormatUTC(long time, @NotNull String format, int sh) {
        return BaseSource.DefaultImpls.timeFormatUTC((BaseSource)((BaseSource)this), (long)time, (String)format, (int)sh);
    }

    public void toast(@Nullable Object msg) {
        BaseSource.DefaultImpls.toast((BaseSource)((BaseSource)this), (Object)msg);
    }

    @Nullable
    public String tripleDESDecodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.tripleDESDecodeArgsBase64Str((BaseSource)((BaseSource)this), (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @Nullable
    public String tripleDESDecodeStr(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.tripleDESDecodeStr((BaseSource)((BaseSource)this), (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @Nullable
    public String tripleDESEncodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.tripleDESEncodeArgsBase64Str((BaseSource)((BaseSource)this), (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @Nullable
    public String tripleDESEncodeBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.tripleDESEncodeBase64Str((BaseSource)((BaseSource)this), (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @NotNull
    public String unzipFile(@NotNull String zipPath) {
        return BaseSource.DefaultImpls.unzipFile((BaseSource)((BaseSource)this), (String)zipPath);
    }

    @NotNull
    public String utf8ToGbk(@NotNull String str) {
        return BaseSource.DefaultImpls.utf8ToGbk((BaseSource)((BaseSource)this), (String)str);
    }

    @Nullable
    public String webView(@Nullable String html, @Nullable String url2, @Nullable String js) {
        return BaseSource.DefaultImpls.webView((BaseSource)((BaseSource)this), (String)html, (String)url2, (String)js);
    }

    public final long component1() {
        return this.id;
    }

    @NotNull
    public final String component2() {
        return this.name;
    }

    @NotNull
    public final String component3() {
        return this.url;
    }

    @Nullable
    public final String component4() {
        return this.contentType;
    }

    @Nullable
    public final String component5() {
        return this.getConcurrentRate();
    }

    @Nullable
    public final String component6() {
        return this.getLoginUrl();
    }

    @Nullable
    public final String component7() {
        return this.getLoginUi();
    }

    @Nullable
    public final String component8() {
        return this.getHeader();
    }

    @Nullable
    public final String component9() {
        return this.jsLib;
    }

    @Nullable
    public final Boolean component10() {
        return this.getEnabledCookieJar();
    }

    @Nullable
    public final String component11() {
        return this.loginCheckJs;
    }

    public final long component12() {
        return this.lastUpdateTime;
    }

    @NotNull
    public final HttpTTS copy(long id, @NotNull String name, @NotNull String url2, @Nullable String contentType, @Nullable String concurrentRate, @Nullable String loginUrl, @Nullable String loginUi, @Nullable String header, @Nullable String jsLib, @Nullable Boolean enabledCookieJar, @Nullable String loginCheckJs, long lastUpdateTime) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        return new HttpTTS(id, name, url2, contentType, concurrentRate, loginUrl, loginUi, header, jsLib, enabledCookieJar, loginCheckJs, lastUpdateTime);
    }

    public static /* synthetic */ HttpTTS copy$default(HttpTTS httpTTS, long l, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, Boolean bl, String string9, long l2, int n, Object object) {
        if ((n & 1) != 0) {
            l = httpTTS.id;
        }
        if ((n & 2) != 0) {
            string = httpTTS.name;
        }
        if ((n & 4) != 0) {
            string2 = httpTTS.url;
        }
        if ((n & 8) != 0) {
            string3 = httpTTS.contentType;
        }
        if ((n & 0x10) != 0) {
            string4 = httpTTS.getConcurrentRate();
        }
        if ((n & 0x20) != 0) {
            string5 = httpTTS.getLoginUrl();
        }
        if ((n & 0x40) != 0) {
            string6 = httpTTS.getLoginUi();
        }
        if ((n & 0x80) != 0) {
            string7 = httpTTS.getHeader();
        }
        if ((n & 0x100) != 0) {
            string8 = httpTTS.jsLib;
        }
        if ((n & 0x200) != 0) {
            bl = httpTTS.getEnabledCookieJar();
        }
        if ((n & 0x400) != 0) {
            string9 = httpTTS.loginCheckJs;
        }
        if ((n & 0x800) != 0) {
            l2 = httpTTS.lastUpdateTime;
        }
        return httpTTS.copy(l, string, string2, string3, string4, string5, string6, string7, string8, bl, string9, l2);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HttpTTS(id=").append(this.id).append(", name=").append(this.name).append(", url=").append(this.url).append(", contentType=").append((Object)this.contentType).append(", concurrentRate=").append((Object)this.getConcurrentRate()).append(", loginUrl=").append((Object)this.getLoginUrl()).append(", loginUi=").append((Object)this.getLoginUi()).append(", header=").append((Object)this.getHeader()).append(", jsLib=").append((Object)this.jsLib).append(", enabledCookieJar=").append(this.getEnabledCookieJar()).append(", loginCheckJs=").append((Object)this.loginCheckJs).append(", lastUpdateTime=");
        stringBuilder.append(this.lastUpdateTime).append(')');
        return stringBuilder.toString();
    }

    public int hashCode() {
        int result2 = Long.hashCode(this.id);
        result2 = result2 * 31 + this.name.hashCode();
        result2 = result2 * 31 + this.url.hashCode();
        result2 = result2 * 31 + (this.contentType == null ? 0 : this.contentType.hashCode());
        result2 = result2 * 31 + (this.getConcurrentRate() == null ? 0 : this.getConcurrentRate().hashCode());
        result2 = result2 * 31 + (this.getLoginUrl() == null ? 0 : this.getLoginUrl().hashCode());
        result2 = result2 * 31 + (this.getLoginUi() == null ? 0 : this.getLoginUi().hashCode());
        result2 = result2 * 31 + (this.getHeader() == null ? 0 : this.getHeader().hashCode());
        result2 = result2 * 31 + (this.jsLib == null ? 0 : this.jsLib.hashCode());
        result2 = result2 * 31 + (this.getEnabledCookieJar() == null ? 0 : ((Object)this.getEnabledCookieJar()).hashCode());
        result2 = result2 * 31 + (this.loginCheckJs == null ? 0 : this.loginCheckJs.hashCode());
        result2 = result2 * 31 + Long.hashCode(this.lastUpdateTime);
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HttpTTS)) {
            return false;
        }
        HttpTTS httpTTS = (HttpTTS)other;
        if (this.id != httpTTS.id) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.name, (Object)httpTTS.name)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.url, (Object)httpTTS.url)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.contentType, (Object)httpTTS.contentType)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.getConcurrentRate(), (Object)httpTTS.getConcurrentRate())) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.getLoginUrl(), (Object)httpTTS.getLoginUrl())) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.getLoginUi(), (Object)httpTTS.getLoginUi())) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.getHeader(), (Object)httpTTS.getHeader())) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.jsLib, (Object)httpTTS.jsLib)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.getEnabledCookieJar(), (Object)httpTTS.getEnabledCookieJar())) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.loginCheckJs, (Object)httpTTS.loginCheckJs)) {
            return false;
        }
        return this.lastUpdateTime == httpTTS.lastUpdateTime;
    }

    public HttpTTS() {
        this(0L, null, null, null, null, null, null, null, null, null, null, 0L, 4095, null);
    }
}

