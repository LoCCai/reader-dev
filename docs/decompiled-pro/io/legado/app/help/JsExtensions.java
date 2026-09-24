/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.BaseSource
 *  io.legado.app.help.JsExtensions
 *  io.legado.app.help.http.StrResponse
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.analyzeRule.QueryTTF
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jsoup.Connection$Response
 */
package io.legado.app.help;

import io.legado.app.data.entities.BaseSource;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.QueryTTF;
import java.io.File;
import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\t\n\u0002\b\u000f\bf\u0018\u00002\u00020\u0001J*\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\t\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J2\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u000e\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J2\u0010\u0010\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u0011\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u0012\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u0013\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u0014\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u0005H\u0016J#\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u00182\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u0018H\u0016\u00a2\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u0005H\u0016J\u0010\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0014\u0010 \u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\u001c\u0010 \u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0012\u0010!\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u001a\u0010!\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0012\u0010\"\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u0005H\u0016J\u001c\u0010\"\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010#\u001a\u00020\u001fH\u0016J\u0010\u0010$\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0005H\u0016J\u001a\u0010$\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u0005H\u0016J*\u0010)\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010*\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010+\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010,\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u001a\u0010-\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005H\u0016J\u001a\u0010/\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005H\u0016J\u0018\u00100\u001a\u00020\u00052\u0006\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u0005H\u0016J\u0010\u00103\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u00103\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u00104\u001a\u00020\u0005H\u0016J$\u00105\u001a\u0002062\u0006\u0010\u0016\u001a\u00020\u00052\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000508H\u0016J\u001c\u00109\u001a\u00020\u00052\u0006\u0010:\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010;\u001a\u00020<2\u0006\u0010(\u001a\u00020\u0005H\u0016J\n\u0010=\u001a\u0004\u0018\u00010>H&J\n\u0010?\u001a\u0004\u0018\u00010@H&J\u0010\u0010A\u001a\u00020\u00052\u0006\u0010B\u001a\u00020\u0005H\u0016J\b\u0010C\u001a\u00020\u0005H&J\u001a\u0010D\u001a\u0004\u0018\u00010\u00032\u0006\u00102\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0016J\u0018\u0010E\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0016J \u0010E\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u00052\u0006\u0010F\u001a\u00020\u0005H\u0016J$\u0010G\u001a\u0002062\u0006\u0010\u0016\u001a\u00020\u00052\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000508H\u0016J\u0010\u0010H\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010I\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0016J\u0010\u0010J\u001a\u00020\u00052\u0006\u0010K\u001a\u00020\u0005H\u0016J\u0012\u0010L\u001a\u00020'2\b\u0010M\u001a\u0004\u0018\u00010\u0001H\u0016J\u0012\u0010N\u001a\u00020'2\b\u0010K\u001a\u0004\u0018\u00010\u0001H\u0016J\u0010\u0010O\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010P\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J,\u0010Q\u001a\u0002062\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010R\u001a\u00020\u00052\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000508H\u0016J\u0014\u0010S\u001a\u0004\u0018\u00010T2\b\u0010U\u001a\u0004\u0018\u00010\u0005H\u0016J\u0014\u0010V\u001a\u0004\u0018\u00010T2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\b\u0010W\u001a\u00020\u0005H\u0016J\u0012\u0010X\u001a\u0004\u0018\u00010\u00032\u0006\u0010(\u001a\u00020\u0005H\u0016J\u0010\u0010Y\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0016J\u0018\u0010Y\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u00052\u0006\u0010F\u001a\u00020\u0005H\u0016J$\u0010Z\u001a\u00020\u00052\u0006\u0010[\u001a\u00020\u00052\b\u0010\\\u001a\u0004\u0018\u00010T2\b\u0010]\u001a\u0004\u0018\u00010TH\u0016J\u0010\u0010^\u001a\u00020\u00052\u0006\u0010_\u001a\u00020`H\u0016J\"\u0010a\u001a\u0004\u0018\u00010\u00052\u0006\u0010_\u001a\u00020`2\u0006\u0010b\u001a\u00020\u00052\u0006\u0010c\u001a\u00020\u001fH\u0016J\u0012\u0010d\u001a\u00020'2\b\u0010K\u001a\u0004\u0018\u00010\u0001H\u0016J2\u0010e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J2\u0010f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J2\u0010g\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J2\u0010h\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u0010\u0010i\u001a\u00020\u00052\u0006\u0010j\u001a\u00020\u0005H\u0016J\u0010\u0010k\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J(\u0010l\u001a\u0004\u0018\u00010\u00052\b\u0010m\u001a\u0004\u0018\u00010\u00052\b\u00102\u001a\u0004\u0018\u00010\u00052\b\u0010n\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006o"}, d2={"Lio/legado/app/help/JsExtensions;", "", "aesBase64DecodeToByteArray", "", "str", "", "key", "transformation", "iv", "aesBase64DecodeToString", "aesDecodeArgsBase64Str", "data", "mode", "padding", "aesDecodeToByteArray", "aesDecodeToString", "aesEncodeArgsBase64Str", "aesEncodeToBase64ByteArray", "aesEncodeToBase64String", "aesEncodeToByteArray", "aesEncodeToString", "ajax", "urlStr", "ajaxAll", "", "Lio/legado/app/help/http/StrResponse;", "urlList", "([Ljava/lang/String;)[Lio/legado/app/help/http/StrResponse;", "androidId", "base64Decode", "flags", "", "base64DecodeToByteArray", "base64Encode", "cacheFile", "saveTime", "connect", "header", "deleteFile", "", "path", "desBase64DecodeToString", "desDecodeToString", "desEncodeToBase64String", "desEncodeToString", "digestBase64Str", "algorithm", "digestHex", "downloadFile", "content", "url", "encodeURI", "enc", "get", "Lorg/jsoup/Connection$Response;", "headers", "", "getCookie", "tag", "getFile", "Ljava/io/File;", "getLogger", "Lio/legado/app/model/DebugLog;", "getSource", "Lio/legado/app/data/entities/BaseSource;", "getTxtInFolder", "unzipPath", "getUserNameSpace", "getZipByteArrayContent", "getZipStringContent", "charsetName", "head", "htmlFormat", "importScript", "log", "msg", "logType", "any", "longToast", "md5Encode", "md5Encode16", "post", "body", "queryBase64TTF", "Lio/legado/app/model/analyzeRule/QueryTTF;", "base64", "queryTTF", "randomUUID", "readFile", "readTxtFile", "replaceFont", "text", "font1", "font2", "timeFormat", "time", "", "timeFormatUTC", "format", "sh", "toast", "tripleDESDecodeArgsBase64Str", "tripleDESDecodeStr", "tripleDESEncodeArgsBase64Str", "tripleDESEncodeBase64Str", "unzipFile", "zipPath", "utf8ToGbk", "webView", "html", "js", "reader-pro"})
public interface JsExtensions {
    @Nullable
    public BaseSource getSource();

    @NotNull
    public String getUserNameSpace();

    @Nullable
    public DebugLog getLogger();

    @Nullable
    public String ajax(@NotNull String var1);

    @NotNull
    public StrResponse[] ajaxAll(@NotNull String[] var1);

    @NotNull
    public StrResponse connect(@NotNull String var1);

    @NotNull
    public StrResponse connect(@NotNull String var1, @Nullable String var2);

    @Nullable
    public String webView(@Nullable String var1, @Nullable String var2, @Nullable String var3);

    @NotNull
    public String importScript(@NotNull String var1);

    @Nullable
    public String cacheFile(@NotNull String var1);

    @Nullable
    public String cacheFile(@NotNull String var1, int var2);

    @NotNull
    public String getCookie(@NotNull String var1, @Nullable String var2);

    @NotNull
    public String downloadFile(@NotNull String var1, @NotNull String var2);

    @NotNull
    public Connection.Response get(@NotNull String var1, @NotNull Map<String, String> var2);

    @NotNull
    public Connection.Response head(@NotNull String var1, @NotNull Map<String, String> var2);

    @NotNull
    public Connection.Response post(@NotNull String var1, @NotNull String var2, @NotNull Map<String, String> var3);

    @NotNull
    public String base64Decode(@NotNull String var1);

    @NotNull
    public String base64Decode(@NotNull String var1, int var2);

    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String var1);

    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String var1, int var2);

    @Nullable
    public String base64Encode(@NotNull String var1);

    @Nullable
    public String base64Encode(@NotNull String var1, int var2);

    @NotNull
    public String md5Encode(@NotNull String var1);

    @NotNull
    public String md5Encode16(@NotNull String var1);

    @Nullable
    public String timeFormatUTC(long var1, @NotNull String var3, int var4);

    @NotNull
    public String timeFormat(long var1);

    @NotNull
    public String utf8ToGbk(@NotNull String var1);

    @NotNull
    public String encodeURI(@NotNull String var1);

    @NotNull
    public String encodeURI(@NotNull String var1, @NotNull String var2);

    @NotNull
    public String htmlFormat(@NotNull String var1);

    @NotNull
    public File getFile(@NotNull String var1);

    @Nullable
    public byte[] readFile(@NotNull String var1);

    @NotNull
    public String readTxtFile(@NotNull String var1);

    @NotNull
    public String readTxtFile(@NotNull String var1, @NotNull String var2);

    public void deleteFile(@NotNull String var1);

    @NotNull
    public String unzipFile(@NotNull String var1);

    @NotNull
    public String getTxtInFolder(@NotNull String var1);

    @NotNull
    public String getZipStringContent(@NotNull String var1, @NotNull String var2);

    @NotNull
    public String getZipStringContent(@NotNull String var1, @NotNull String var2, @NotNull String var3);

    @Nullable
    public byte[] getZipByteArrayContent(@NotNull String var1, @NotNull String var2);

    @Nullable
    public QueryTTF queryBase64TTF(@Nullable String var1);

    @Nullable
    public QueryTTF queryTTF(@Nullable String var1);

    @NotNull
    public String replaceFont(@NotNull String var1, @Nullable QueryTTF var2, @Nullable QueryTTF var3);

    public void toast(@Nullable Object var1);

    public void longToast(@Nullable Object var1);

    @NotNull
    public String log(@NotNull String var1);

    public void logType(@Nullable Object var1);

    @NotNull
    public String randomUUID();

    @Nullable
    public byte[] aesDecodeToByteArray(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String aesDecodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public byte[] aesBase64DecodeToByteArray(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String aesBase64DecodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public byte[] aesEncodeToByteArray(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String aesEncodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public byte[] aesEncodeToBase64ByteArray(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String aesEncodeToBase64String(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @NotNull
    public String androidId();

    @Nullable
    public String aesDecodeArgsBase64Str(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String tripleDESDecodeStr(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String tripleDESDecodeArgsBase64Str(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String aesEncodeArgsBase64Str(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String desDecodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String desBase64DecodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String desEncodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String desEncodeToBase64String(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String tripleDESEncodeBase64Str(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String tripleDESEncodeArgsBase64Str(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String digestHex(@NotNull String var1, @NotNull String var2);

    @Nullable
    public String digestBase64Str(@NotNull String var1, @NotNull String var2);
}

