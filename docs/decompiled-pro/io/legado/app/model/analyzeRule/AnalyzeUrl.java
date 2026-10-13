/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.script.Bindings
 *  com.script.SimpleBindings
 *  io.legado.app.adapters.ReaderAdapterHelper
 *  io.legado.app.adapters.ReaderAdapterInterface
 *  io.legado.app.adapters.ReaderAdapterInterface$DefaultImpls
 *  io.legado.app.constant.AppConst
 *  io.legado.app.constant.AppPattern
 *  io.legado.app.data.entities.BaseSource
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookChapter
 *  io.legado.app.exception.ConcurrentException
 *  io.legado.app.help.CacheManager
 *  io.legado.app.help.JsExtensions
 *  io.legado.app.help.JsExtensions$DefaultImpls
 *  io.legado.app.help.http.CookieStore
 *  io.legado.app.help.http.HttpHelperKt
 *  io.legado.app.help.http.OkHttpUtilsKt
 *  io.legado.app.help.http.RequestMethod
 *  io.legado.app.help.http.StrResponse
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.analyzeRule.AnalyzeUrl
 *  io.legado.app.model.analyzeRule.AnalyzeUrl$Companion
 *  io.legado.app.model.analyzeRule.AnalyzeUrl$ConcurrentRecord
 *  io.legado.app.model.analyzeRule.AnalyzeUrl$UrlOption
 *  io.legado.app.model.analyzeRule.AnalyzeUrl$WhenMappings
 *  io.legado.app.model.analyzeRule.AnalyzeUrl$analyzeUrl$$inlined$fromJsonObject$1
 *  io.legado.app.model.analyzeRule.AnalyzeUrl$getByteArrayAwait$1
 *  io.legado.app.model.analyzeRule.AnalyzeUrl$getResponseAwait$1
 *  io.legado.app.model.analyzeRule.AnalyzeUrl$getStrResponseAwait$1
 *  io.legado.app.model.analyzeRule.QueryTTF
 *  io.legado.app.model.analyzeRule.RuleAnalyzer
 *  io.legado.app.model.analyzeRule.RuleDataInterface
 *  io.legado.app.utils.Base64
 *  io.legado.app.utils.EncoderUtils
 *  io.legado.app.utils.GsonExtensionsKt
 *  io.legado.app.utils.NetworkUtils
 *  io.legado.app.utils.StringExtensionsKt
 *  io.legado.app.utils.StringUtils
 *  kotlin.Metadata
 *  kotlin.Result
 *  kotlin.Result$Companion
 *  kotlin.ResultKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  kotlinx.coroutines.BuildersKt
 *  okhttp3.OkHttpClient
 *  okhttp3.Response
 *  okhttp3.ResponseBody
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jsoup.Connection$Response
 */
package io.legado.app.model.analyzeRule;

import com.script.Bindings;
import com.script.SimpleBindings;
import io.legado.app.adapters.ReaderAdapterHelper;
import io.legado.app.adapters.ReaderAdapterInterface;
import io.legado.app.constant.AppConst;
import io.legado.app.constant.AppPattern;
import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.exception.ConcurrentException;
import io.legado.app.help.CacheManager;
import io.legado.app.help.JsExtensions;
import io.legado.app.help.http.CookieStore;
import io.legado.app.help.http.HttpHelperKt;
import io.legado.app.help.http.OkHttpUtilsKt;
import io.legado.app.help.http.RequestMethod;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.analyzeRule.AnalyzeUrl$analyzeUrl$;
import io.legado.app.model.analyzeRule.QueryTTF;
import io.legado.app.model.analyzeRule.RuleAnalyzer;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.utils.Base64;
import io.legado.app.utils.EncoderUtils;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.NetworkUtils;
import io.legado.app.utils.StringExtensionsKt;
import io.legado.app.utils.StringUtils;
import java.io.File;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0017\u0018\u0000 l2\u00020\u0001:\u0003lmnB\u008f\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u0016\b\u0002\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0002\u0010\u0014J\u0010\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u0003H\u0002J\b\u0010C\u001a\u00020AH\u0002J\b\u0010D\u001a\u00020AH\u0002J\u001c\u0010E\u001a\u0004\u0018\u00010F2\u0006\u0010G\u001a\u00020\u00032\n\b\u0002\u0010H\u001a\u0004\u0018\u00010FJ\u0012\u0010I\u001a\u00020A2\b\u0010J\u001a\u0004\u0018\u00010KH\u0002J\n\u0010L\u001a\u0004\u0018\u00010KH\u0002J\u000e\u0010M\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003J\u0006\u0010N\u001a\u00020OJ\u0011\u0010P\u001a\u00020OH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010QJ\n\u0010R\u001a\u0004\u0018\u00010\u0013H\u0016J\u0006\u0010S\u001a\u00020TJ\u0011\u0010U\u001a\u00020TH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010QJ\n\u0010V\u001a\u0004\u0018\u00010\u000bH\u0016J*\u0010W\u001a\u00020X2\n\b\u0002\u0010G\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010Y\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010>\u001a\u00020\"H\u0007J3\u0010Z\u001a\u00020X2\n\b\u0002\u0010G\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010Y\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010>\u001a\u00020\"H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010[J\u0006\u0010\\\u001a\u00020\u0003J\b\u0010]\u001a\u00020\u0003H\u0016J\u0006\u0010^\u001a\u00020AJ\u0006\u0010_\u001a\u00020\"J\u0016\u0010`\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010a\u001a\u00020\u0003J\b\u0010b\u001a\u00020AH\u0002J\u000e\u0010c\u001a\u00020A2\u0006\u0010d\u001a\u00020TJ\u0012\u0010e\u001a\u00020A2\b\u0010f\u001a\u0004\u0018\u00010\u0003H\u0002J)\u0010g\u001a\u00020X2\u0006\u0010h\u001a\u00020\u00032\u0006\u0010i\u001a\u00020F2\u0006\u0010j\u001a\u00020\u0003H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010kR\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\"\u0010\u001a\u001a\u0004\u0018\u00010\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010#\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030$j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003`%X\u0082\u0004\u00a2\u0006\u0002\n\u0000R-\u0010&\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030'j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003`(\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0016R\u000e\u0010-\u001a\u00020.X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u00101\u001a\u0004\b/\u00100R\u0010\u00102\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00103\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u00105\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0016R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u00101\u001a\u0004\b7\u00100R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0016R\"\u00109\u001a\u0004\u0018\u00010\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010\u0016R\u001e\u0010;\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010\u0016R\u000e\u0010=\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010?\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006o"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeUrl;", "Lio/legado/app/help/JsExtensions;", "mUrl", "", "key", "page", "", "speakText", "speakSpeed", "baseUrl", "source", "Lio/legado/app/data/entities/BaseSource;", "ruleData", "Lio/legado/app/model/analyzeRule/RuleDataInterface;", "chapter", "Lio/legado/app/data/entities/BookChapter;", "headerMapF", "", "debugLog", "Lio/legado/app/model/DebugLog;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Lio/legado/app/data/entities/BaseSource;Lio/legado/app/model/analyzeRule/RuleDataInterface;Lio/legado/app/data/entities/BookChapter;Ljava/util/Map;Lio/legado/app/model/DebugLog;)V", "getBaseUrl", "()Ljava/lang/String;", "setBaseUrl", "(Ljava/lang/String;)V", "<set-?>", "body", "getBody", "charset", "getDebugLog", "()Lio/legado/app/model/DebugLog;", "setDebugLog", "(Lio/legado/app/model/DebugLog;)V", "enabledCookieJar", "", "fieldMap", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "headerMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getHeaderMap", "()Ljava/util/HashMap;", "getKey", "getMUrl", "method", "Lio/legado/app/help/http/RequestMethod;", "getPage", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "proxy", "queryStr", "retry", "ruleUrl", "getRuleUrl", "getSpeakSpeed", "getSpeakText", "type", "getType", "url", "getUrl", "urlNoQuery", "useWebView", "webJs", "analyzeFields", "", "fieldsTxt", "analyzeJs", "analyzeUrl", "evalJS", "", "jsStr", "result", "fetchEnd", "concurrentRecord", "Lio/legado/app/model/analyzeRule/AnalyzeUrl$ConcurrentRecord;", "fetchStart", "get", "getByteArray", "", "getByteArrayAwait", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLogger", "getResponse", "Lokhttp3/Response;", "getResponseAwait", "getSource", "getStrResponse", "Lio/legado/app/help/http/StrResponse;", "sourceRegex", "getStrResponseAwait", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserAgent", "getUserNameSpace", "initUrl", "isPost", "put", "value", "replaceKeyPageJs", "saveCookieJar", "response", "setCookie", "tag", "upload", "fileName", "file", "contentType", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "ConcurrentRecord", "UrlOption", "reader-pro"})
public final class AnalyzeUrl
implements JsExtensions {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String mUrl;
    @Nullable
    private final String key;
    @Nullable
    private final Integer page;
    @Nullable
    private final String speakText;
    @Nullable
    private final Integer speakSpeed;
    @NotNull
    private String baseUrl;
    @Nullable
    private final BaseSource source;
    @Nullable
    private final RuleDataInterface ruleData;
    @Nullable
    private final BookChapter chapter;
    @Nullable
    private DebugLog debugLog;
    @NotNull
    private String ruleUrl;
    @NotNull
    private String url;
    @Nullable
    private String body;
    @Nullable
    private String type;
    @NotNull
    private final HashMap<String, String> headerMap;
    @NotNull
    private String urlNoQuery;
    @Nullable
    private String queryStr;
    @NotNull
    private final LinkedHashMap<String, String> fieldMap;
    @Nullable
    private String charset;
    @NotNull
    private RequestMethod method;
    @Nullable
    private String proxy;
    private int retry;
    private boolean useWebView;
    @Nullable
    private String webJs;
    private final boolean enabledCookieJar;
    @NotNull
    private static final Pattern paramPattern;
    private static final Pattern pagePattern;
    @NotNull
    private static final HashMap<String, ConcurrentRecord> concurrentRecordMap;

    public AnalyzeUrl(@NotNull String mUrl, @Nullable String key, @Nullable Integer page, @Nullable String speakText, @Nullable Integer speakSpeed, @NotNull String baseUrl, @Nullable BaseSource source, @Nullable RuleDataInterface ruleData, @Nullable BookChapter chapter, @Nullable Map<String, String> headerMapF, @Nullable DebugLog debugLog) {
        Map map;
        Intrinsics.checkNotNullParameter((Object)mUrl, (String)"mUrl");
        Intrinsics.checkNotNullParameter((Object)baseUrl, (String)"baseUrl");
        this.mUrl = mUrl;
        this.key = key;
        this.page = page;
        this.speakText = speakText;
        this.speakSpeed = speakSpeed;
        this.baseUrl = baseUrl;
        this.source = source;
        this.ruleData = ruleData;
        this.chapter = chapter;
        this.debugLog = debugLog;
        this.ruleUrl = "";
        this.url = "";
        this.headerMap = new HashMap();
        this.urlNoQuery = "";
        this.fieldMap = new LinkedHashMap();
        this.method = RequestMethod.GET;
        BaseSource baseSource = this.source;
        this.enabledCookieJar = baseSource == null ? false : ((map = baseSource.getEnabledCookieJar()) == null ? false : (Boolean)((Object)map));
        if (!StringExtensionsKt.isDataUrl((String)this.mUrl)) {
            BaseSource baseSource2;
            Map map2;
            boolean bl;
            Matcher urlMatcher = paramPattern.matcher(this.baseUrl);
            if (urlMatcher.find()) {
                map = this.baseUrl;
                int n = 0;
                int n2 = urlMatcher.start();
                bl = false;
                Map map3 = map;
                if (map3 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string = ((String)((Object)map3)).substring(n, n2);
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                this.baseUrl = string;
            }
            Map map4 = (map2 = headerMapF) == null ? (Map)((baseSource2 = this.source) == null ? null : baseSource2.getHeaderMap(true)) : (map = map2);
            if (map != null) {
                map2 = map;
                boolean bl2 = false;
                bl = false;
                Map it = map2;
                boolean bl3 = false;
                this.getHeaderMap().putAll(it);
                if (it.containsKey("proxy")) {
                    this.proxy = (String)it.get("proxy");
                    this.getHeaderMap().remove("proxy");
                }
            }
            this.initUrl();
        }
    }

    public /* synthetic */ AnalyzeUrl(String string, String string2, Integer n, String string3, Integer n2, String string4, BaseSource baseSource, RuleDataInterface ruleDataInterface, BookChapter bookChapter, Map map, DebugLog debugLog, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 2) != 0) {
            string2 = null;
        }
        if ((n3 & 4) != 0) {
            n = null;
        }
        if ((n3 & 8) != 0) {
            string3 = null;
        }
        if ((n3 & 0x10) != 0) {
            n2 = null;
        }
        if ((n3 & 0x20) != 0) {
            string4 = "";
        }
        if ((n3 & 0x40) != 0) {
            baseSource = null;
        }
        if ((n3 & 0x80) != 0) {
            ruleDataInterface = null;
        }
        if ((n3 & 0x100) != 0) {
            bookChapter = null;
        }
        if ((n3 & 0x200) != 0) {
            map = null;
        }
        if ((n3 & 0x400) != 0) {
            debugLog = null;
        }
        this(string, string2, n, string3, n2, string4, baseSource, ruleDataInterface, bookChapter, map, debugLog);
    }

    @NotNull
    public final String getMUrl() {
        return this.mUrl;
    }

    @Nullable
    public final String getKey() {
        return this.key;
    }

    @Nullable
    public final Integer getPage() {
        return this.page;
    }

    @Nullable
    public final String getSpeakText() {
        return this.speakText;
    }

    @Nullable
    public final Integer getSpeakSpeed() {
        return this.speakSpeed;
    }

    @NotNull
    public final String getBaseUrl() {
        return this.baseUrl;
    }

    public final void setBaseUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.baseUrl = string;
    }

    @Nullable
    public final DebugLog getDebugLog() {
        return this.debugLog;
    }

    public final void setDebugLog(@Nullable DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    @NotNull
    public final String getRuleUrl() {
        return this.ruleUrl;
    }

    @NotNull
    public final String getUrl() {
        return this.url;
    }

    @Nullable
    public final String getBody() {
        return this.body;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    @NotNull
    public final HashMap<String, String> getHeaderMap() {
        return this.headerMap;
    }

    @NotNull
    public String getUserNameSpace() {
        String string;
        RuleDataInterface ruleDataInterface = this.ruleData;
        return ruleDataInterface == null ? "unknow" : ((string = ruleDataInterface.getUserNameSpace()) == null ? "unknow" : string);
    }

    @Nullable
    public BaseSource getSource() {
        return this.source;
    }

    @Nullable
    public DebugLog getLogger() {
        return this.debugLog;
    }

    public final void initUrl() {
        this.ruleUrl = this.mUrl;
        this.analyzeJs();
        this.replaceKeyPageJs();
        this.analyzeUrl();
    }

    private final void analyzeJs() {
        boolean match$iv$iv;
        char it;
        int index$iv$iv;
        boolean startFound$iv$iv;
        int endIndex$iv$iv;
        int startIndex$iv$iv;
        boolean $i$f$trim;
        CharSequence $this$trim$iv$iv;
        Object $this$trim$iv;
        CharSequence charSequence;
        int start2 = 0;
        String tmp = null;
        Matcher jsMatcher = AppPattern.INSTANCE.getJS_PATTERN().matcher(this.ruleUrl);
        while (jsMatcher.find()) {
            if (jsMatcher.start() > start2) {
                charSequence = this.ruleUrl;
                int n = jsMatcher.start();
                boolean bl = false;
                String string = charSequence;
                if (string == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string2 = string.substring(start2, n);
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                charSequence = string2;
                boolean $i$f$trim2 = false;
                $this$trim$iv$iv = (CharSequence)$this$trim$iv;
                $i$f$trim = false;
                startIndex$iv$iv = 0;
                endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                startFound$iv$iv = false;
                while (startIndex$iv$iv <= endIndex$iv$iv) {
                    index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                    it = $this$trim$iv$iv.charAt(index$iv$iv);
                    boolean bl2 = false;
                    boolean bl3 = match$iv$iv = Intrinsics.compare((int)it, (int)32) <= 0;
                    if (!startFound$iv$iv) {
                        if (!match$iv$iv) {
                            startFound$iv$iv = true;
                            continue;
                        }
                        ++startIndex$iv$iv;
                        continue;
                    }
                    if (!match$iv$iv) break;
                    --endIndex$iv$iv;
                }
                tmp = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
                $this$trim$iv = tmp;
                $i$f$trim2 = false;
                if ($this$trim$iv.length() > 0) {
                    this.ruleUrl = StringsKt.replace$default((String)tmp, (String)"@result", (String)this.ruleUrl, (boolean)false, (int)4, null);
                }
            }
            String $i$f$trim2 = ($this$trim$iv$iv = jsMatcher.group(2)) == null ? jsMatcher.group(1) : $this$trim$iv$iv;
            Intrinsics.checkNotNullExpressionValue((Object)$i$f$trim2, (String)"jsMatcher.group(2) ?: jsMatcher.group(1)");
            $this$trim$iv = this.evalJS($i$f$trim2, (Object)this.ruleUrl);
            if ($this$trim$iv == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            }
            this.ruleUrl = (String)$this$trim$iv;
            start2 = jsMatcher.end();
        }
        if (this.ruleUrl.length() > start2) {
            $this$trim$iv = this.ruleUrl;
            boolean $i$f$trim2 = false;
            String string = $this$trim$iv;
            if (string == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string.substring(start2);
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.String).substring(startIndex)");
            $this$trim$iv = string3;
            $i$f$trim2 = false;
            $this$trim$iv$iv = (CharSequence)$this$trim$iv;
            $i$f$trim = false;
            startIndex$iv$iv = 0;
            endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
            startFound$iv$iv = false;
            while (startIndex$iv$iv <= endIndex$iv$iv) {
                index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                it = $this$trim$iv$iv.charAt(index$iv$iv);
                boolean bl = false;
                boolean bl4 = match$iv$iv = Intrinsics.compare((int)it, (int)32) <= 0;
                if (!startFound$iv$iv) {
                    if (!match$iv$iv) {
                        startFound$iv$iv = true;
                        continue;
                    }
                    ++startIndex$iv$iv;
                    continue;
                }
                if (!match$iv$iv) break;
                --endIndex$iv$iv;
            }
            tmp = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
            charSequence = tmp;
            boolean bl = false;
            if (charSequence.length() > 0) {
                this.ruleUrl = StringsKt.replace$default((String)tmp, (String)"@result", (String)this.ruleUrl, (boolean)false, (int)4, null);
            }
        }
    }

    private final void replaceKeyPageJs() {
        Integer n;
        boolean bl;
        if (StringsKt.contains$default((CharSequence)this.ruleUrl, (CharSequence)"{{", (boolean)false, (int)2, null) && StringsKt.contains$default((CharSequence)this.ruleUrl, (CharSequence)"}}", (boolean)false, (int)2, null)) {
            RuleAnalyzer analyze = new RuleAnalyzer(this.ruleUrl, false, 2, null);
            String url2 = analyze.innerRule("{{", "}}", (Function1)new /* Unavailable Anonymous Inner Class!! */);
            CharSequence charSequence = url2;
            bl = false;
            if (charSequence.length() > 0) {
                this.ruleUrl = url2;
            }
        }
        if ((n = this.page) != null) {
            Integer n2 = n;
            boolean bl2 = false;
            bl = false;
            int it = ((Number)n2).intValue();
            boolean bl3 = false;
            Matcher matcher = pagePattern.matcher(this.getRuleUrl());
            while (matcher.find()) {
                String string;
                AnalyzeUrl analyzeUrl;
                String string2;
                boolean match$iv$iv;
                char it2;
                int index$iv$iv;
                boolean startFound$iv$iv;
                int endIndex$iv$iv;
                int startIndex$iv$iv;
                boolean $i$f$trim;
                CharSequence $this$trim$iv$iv;
                Object $this$trim$iv;
                boolean $i$f$trim2;
                AnalyzeUrl analyzeUrl2;
                String string3;
                Object object;
                String string4 = matcher.group(1);
                Intrinsics.checkNotNull((Object)string4);
                Object object2 = new String[]{","};
                List pages = StringsKt.split$default((CharSequence)string4, (String[])object2, (boolean)false, (int)0, (int)6, null);
                if (this.getPage() < pages.size()) {
                    String string5 = this.getRuleUrl();
                    object2 = matcher.group();
                    Intrinsics.checkNotNullExpressionValue((Object)object2, (String)"matcher.group()");
                    Object object3 = object2;
                    object2 = (String)pages.get(this.getPage() - 1);
                    object = object3;
                    string3 = string5;
                    analyzeUrl2 = this;
                    $i$f$trim2 = false;
                    $this$trim$iv$iv = (CharSequence)$this$trim$iv;
                    $i$f$trim = false;
                    startIndex$iv$iv = 0;
                    endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                    startFound$iv$iv = false;
                    while (startIndex$iv$iv <= endIndex$iv$iv) {
                        index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                        it2 = $this$trim$iv$iv.charAt(index$iv$iv);
                        boolean bl4 = false;
                        boolean bl5 = match$iv$iv = Intrinsics.compare((int)it2, (int)32) <= 0;
                        if (!startFound$iv$iv) {
                            if (!match$iv$iv) {
                                startFound$iv$iv = true;
                                continue;
                            }
                            ++startIndex$iv$iv;
                            continue;
                        }
                        if (!match$iv$iv) break;
                        --endIndex$iv$iv;
                    }
                    string2 = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
                    analyzeUrl = analyzeUrl2;
                    string = StringsKt.replace$default((String)string3, (String)object, (String)string2, (boolean)false, (int)4, null);
                } else {
                    String string6 = this.getRuleUrl();
                    $this$trim$iv = matcher.group();
                    Intrinsics.checkNotNullExpressionValue((Object)$this$trim$iv, (String)"matcher.group()");
                    Object object4 = $this$trim$iv;
                    $this$trim$iv = (String)CollectionsKt.last((List)pages);
                    object = object4;
                    string3 = string6;
                    $i$f$trim2 = false;
                    $this$trim$iv$iv = (CharSequence)$this$trim$iv;
                    $i$f$trim = false;
                    startIndex$iv$iv = 0;
                    endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                    startFound$iv$iv = false;
                    while (startIndex$iv$iv <= endIndex$iv$iv) {
                        index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                        it2 = $this$trim$iv$iv.charAt(index$iv$iv);
                        boolean bl6 = false;
                        boolean bl7 = match$iv$iv = Intrinsics.compare((int)it2, (int)32) <= 0;
                        if (!startFound$iv$iv) {
                            if (!match$iv$iv) {
                                startFound$iv$iv = true;
                                continue;
                            }
                            ++startIndex$iv$iv;
                            continue;
                        }
                        if (!match$iv$iv) break;
                        --endIndex$iv$iv;
                    }
                    string2 = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
                    analyzeUrl = analyzeUrl2;
                    string = StringsKt.replace$default((String)string3, (String)object, (String)string2, (boolean)false, (int)4, null);
                }
                analyzeUrl.ruleUrl = string;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void analyzeUrl() {
        boolean bl;
        boolean json$iv2;
        Object $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22;
        Object object;
        String it2;
        String string;
        int n;
        int n2;
        String string2;
        Matcher urlMatcher = paramPattern.matcher(this.ruleUrl);
        if (urlMatcher.find()) {
            string2 = this.ruleUrl;
            int n3 = 0;
            n2 = urlMatcher.start();
            n = 0;
            String string3 = string2;
            if (string3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string4 = string3.substring(n3, n2);
            string = string4;
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        } else {
            string = this.ruleUrl;
        }
        String urlNoOption = string;
        this.url = NetworkUtils.INSTANCE.getAbsoluteURL(this.baseUrl, urlNoOption);
        string2 = NetworkUtils.INSTANCE.getBaseUrl(this.url);
        if (string2 != null) {
            String string5 = string2;
            n2 = 0;
            n = 0;
            it2 = string5;
            boolean bl2 = false;
            this.setBaseUrl(it2);
        }
        if (urlNoOption.length() != this.ruleUrl.length()) {
            Object bl2;
            Object object2 = GsonExtensionsKt.getGSON();
            String string6 = this.ruleUrl;
            n = urlMatcher.end();
            boolean it2 = false;
            String string7 = string6;
            if (string7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string8 = string7.substring(n);
            Intrinsics.checkNotNullExpressionValue((Object)string8, (String)"(this as java.lang.String).substring(startIndex)");
            string6 = string8;
            boolean $i$f$fromJsonObject = false;
            it2 = false;
            try {
                void json$iv2;
                void $this$fromJsonObject$iv;
                bl2 = Result.Companion;
                boolean $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = false;
                boolean $i$f$genericType = false;
                object = new analyzeUrl$$inlined$fromJsonObject$1().getType();
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"object : TypeToken<T>() {}.type");
                Object object3 = $this$fromJsonObject$iv.fromJson((String)json$iv2, (Type)object);
                if (!(object3 instanceof UrlOption)) {
                    object3 = null;
                }
                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = (UrlOption)object3;
                $i$f$genericType = false;
                bl2 = Result.constructor-impl((Object)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22);
            }
            catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22) {
                Result.Companion $i$f$genericType = Result.Companion;
                boolean bl3 = false;
                bl2 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22));
            }
            object2 = bl2;
            json$iv2 = false;
            string2 = (UrlOption)(Result.isFailure-impl((Object)object2) ? null : object2);
            if (string2 != null) {
                Object it32;
                object2 = string2;
                json$iv2 = false;
                n = 0;
                Object option = object2;
                boolean bl4 = false;
                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = option.getMethod();
                if ($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 != null) {
                    Object $i$f$genericType = $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22;
                    boolean bl5 = false;
                    bl = false;
                    it32 = $i$f$genericType;
                    boolean bl6 = false;
                    if (StringsKt.equals((String)it32, (String)"POST", (boolean)true)) {
                        this.method = RequestMethod.POST;
                    }
                }
                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = option.getHeaderMap();
                if ($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 != null) {
                    Object $this$forEach$iv = $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22;
                    boolean $i$f$forEach = false;
                    Object object4 = $this$forEach$iv;
                    boolean it32 = false;
                    Iterator bl6 = object4.entrySet().iterator();
                    while (bl6.hasNext()) {
                        Map.Entry element$iv;
                        Map.Entry entry = element$iv = bl6.next();
                        boolean bl7 = false;
                        Map map = this.getHeaderMap();
                        String string9 = String.valueOf(entry.getKey());
                        String string10 = String.valueOf(entry.getValue());
                        boolean bl8 = false;
                        map.put(string9, string10);
                    }
                }
                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = option.getBody();
                if ($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 != null) {
                    Object $this$forEach$iv = $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22;
                    boolean bl9 = false;
                    bl = false;
                    it32 = $this$forEach$iv;
                    boolean bl10 = false;
                    this.body = it32;
                }
                this.type = option.getType();
                this.charset = option.getCharset();
                this.retry = option.getRetry();
                this.useWebView = option.useWebView();
                this.webJs = option.getWebJs();
                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = option.getJs();
                if ($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 != null) {
                    String string11;
                    Object $this$forEach$iv = $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22;
                    boolean bl11 = false;
                    bl = false;
                    Object jsStr = $this$forEach$iv;
                    boolean bl12 = false;
                    Object object5 = this.evalJS((String)jsStr, (Object)this.getUrl());
                    if (object5 != null && (string11 = object5.toString()) != null) {
                        String string12 = string11;
                        boolean bl13 = false;
                        boolean bl14 = false;
                        String it4 = string12;
                        boolean bl15 = false;
                        this.url = it4;
                    }
                }
            }
        }
        if ((string2 = (String)this.headerMap.get("User-Agent")) == null) {
            AnalyzeUrl analyzeUrl = this;
            json$iv2 = false;
            n = 0;
            it2 = analyzeUrl;
            boolean bl16 = false;
            $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = this.getHeaderMap();
            String $this$forEach$iv = "User-Agent";
            object = AppConst.INSTANCE.getUserAgent();
            bl = false;
            $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22.put($this$forEach$iv, object);
        }
        this.urlNoQuery = this.url;
        string2 = this.method;
        int n4 = WhenMappings.$EnumSwitchMapping$0[string2.ordinal()];
        switch (n4) {
            case 1: {
                int pos = StringsKt.indexOf$default((CharSequence)this.url, (char)'?', (int)0, (boolean)false, (int)6, null);
                if (pos == -1) break;
                String string13 = this.url;
                int n5 = pos + 1;
                boolean bl17 = false;
                String string14 = string13;
                if (string14 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string15 = string14.substring(n5);
                Intrinsics.checkNotNullExpressionValue((Object)string15, (String)"(this as java.lang.String).substring(startIndex)");
                this.analyzeFields(string15);
                string13 = this.url;
                n5 = 0;
                bl17 = false;
                String string16 = string13;
                if (string16 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string17 = string16.substring(n5, pos);
                Intrinsics.checkNotNullExpressionValue((Object)string17, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                this.urlNoQuery = string17;
                break;
            }
            case 2: {
                String string18 = this.body;
                if (string18 == null) break;
                String string19 = string18;
                boolean bl18 = false;
                boolean bl17 = false;
                String it5 = string19;
                boolean bl19 = false;
                if (StringExtensionsKt.isJson((String)it5) || StringExtensionsKt.isXml((String)it5)) break;
                object = (CharSequence)this.getHeaderMap().get("Content-Type");
                bl = false;
                boolean bl20 = false;
                if (!(object == null || object.length() == 0)) break;
                this.analyzeFields(it5);
            }
        }
    }

    private final void analyzeFields(String fieldsTxt) {
        this.queryStr = fieldsTxt;
        String[] stringArray = new String[]{"&"};
        String[] queryS = StringExtensionsKt.splitNotBlank((String)fieldsTxt, (String[])stringArray);
        for (String query : queryS) {
            boolean bl;
            String string;
            String[] stringArray2 = new String[]{"="};
            String[] queryM = StringExtensionsKt.splitNotBlank((String)query, (String[])stringArray2);
            String value = queryM.length > 1 ? queryM[1] : "";
            Object object = this.charset;
            boolean bl2 = false;
            boolean bl3 = false;
            if (object == null || object.length() == 0) {
                if (NetworkUtils.INSTANCE.hasUrlEncoded(value)) {
                    object = this.fieldMap;
                    string = queryM[0];
                    bl3 = false;
                    object.put(string, value);
                    continue;
                }
                object = this.fieldMap;
                string = queryM[0];
                String string2 = URLEncoder.encode(value, "UTF-8");
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"encode(value, \"UTF-8\")");
                bl = false;
                object.put(string, string2);
                continue;
            }
            if (Intrinsics.areEqual((Object)this.charset, (Object)"escape")) {
                object = this.fieldMap;
                string = queryM[0];
                String string3 = EncoderUtils.INSTANCE.escape(value);
                bl = false;
                object.put(string, string3);
                continue;
            }
            object = this.fieldMap;
            string = queryM[0];
            String string4 = URLEncoder.encode(value, this.charset);
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"encode(value, charset)");
            bl = false;
            object.put(string, string4);
        }
    }

    @Nullable
    public final Object evalJS(@NotNull String jsStr, @Nullable Object result2) {
        Intrinsics.checkNotNullParameter((Object)jsStr, (String)"jsStr");
        SimpleBindings bindings = new SimpleBindings();
        Map map = (Map)bindings;
        String string = "java";
        boolean bl = false;
        map.put(string, this);
        map = (Map)bindings;
        string = "baseUrl";
        Object object = this.baseUrl;
        boolean bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "cookie";
        object = new CookieStore(this.getUserNameSpace());
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "cache";
        object = new CacheManager(this.getUserNameSpace());
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "page";
        object = this.page;
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "key";
        object = this.key;
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "speakText";
        object = this.speakText;
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "speakSpeed";
        object = this.speakSpeed;
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "book";
        object = this.ruleData;
        object = object instanceof Book ? (Book)object : null;
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "source";
        object = this.source;
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "result";
        boolean bl3 = false;
        map.put(string, result2);
        return AppConst.INSTANCE.getSCRIPT_ENGINE().eval(jsStr, (Bindings)bindings);
    }

    public static /* synthetic */ Object evalJS$default(AnalyzeUrl analyzeUrl, String string, Object object, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        return analyzeUrl.evalJS(string, object);
    }

    @NotNull
    public final String put(@NotNull String key, @NotNull String value) {
        block2: {
            Unit unit;
            Unit unit2;
            Intrinsics.checkNotNullParameter((Object)key, (String)"key");
            Intrinsics.checkNotNullParameter((Object)value, (String)"value");
            BookChapter bookChapter = this.chapter;
            if (bookChapter == null) {
                unit2 = null;
            } else {
                bookChapter.putVariable(key, value);
                unit2 = unit = Unit.INSTANCE;
            }
            if (unit != null || (bookChapter = this.ruleData) == null) break block2;
            bookChapter.putVariable(key, value);
        }
        return value;
    }

    @NotNull
    public final String get(@NotNull String key) {
        BookChapter bookChapter;
        Object object;
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        String string = key;
        if (Intrinsics.areEqual((Object)string, (Object)"bookName")) {
            object = this.ruleData;
            Book book = bookChapter = object instanceof Book ? (Book)object : null;
            if (bookChapter != null) {
                object = bookChapter;
                boolean bl = false;
                boolean bl2 = false;
                RuleDataInterface it = object;
                boolean bl3 = false;
                return it.getName();
            }
        } else if (Intrinsics.areEqual((Object)string, (Object)"title") && (bookChapter = this.chapter) != null) {
            BookChapter bookChapter2 = bookChapter;
            boolean bl = false;
            boolean bl4 = false;
            BookChapter it = bookChapter2;
            boolean bl5 = false;
            return it.getTitle();
        }
        bookChapter = this.chapter;
        String string2 = string = bookChapter == null ? null : bookChapter.getVariable(key);
        return string == null ? ((bookChapter = this.ruleData) == null ? "" : ((object = bookChapter.getVariable(key)) == null ? "" : object)) : string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final ConcurrentRecord fetchStart() {
        BaseSource baseSource = this.source;
        if (baseSource == null) {
            return null;
        }
        String concurrentRate = this.source.getConcurrentRate();
        CharSequence charSequence = concurrentRate;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || charSequence.length() == 0) {
            return null;
        }
        int rateIndex = StringsKt.indexOf$default((CharSequence)concurrentRate, (String)"/", (int)0, (boolean)false, (int)6, null);
        ConcurrentRecord fetchRecord = null;
        fetchRecord = (ConcurrentRecord)concurrentRecordMap.get(this.source.getKey());
        if (fetchRecord == null) {
            fetchRecord = new ConcurrentRecord(rateIndex > 0, System.currentTimeMillis(), 1);
            Map map = concurrentRecordMap;
            String string = this.source.getKey();
            ConcurrentRecord concurrentRecord = fetchRecord;
            boolean bl3 = false;
            map.put(string, concurrentRecord);
            return fetchRecord;
        }
        ConcurrentRecord concurrentRecord = fetchRecord;
        boolean bl4 = false;
        int n = 0;
        synchronized (concurrentRecord) {
            int n2;
            boolean bl5 = false;
            try {
                if (rateIndex == -1) {
                    if (fetchRecord.getFrequency() > 0) {
                        String string = concurrentRate;
                        boolean bl6 = false;
                        n2 = Integer.parseInt(string);
                    } else {
                        String string = concurrentRate;
                        boolean bl7 = false;
                        long nextTime = fetchRecord.getTime() + (long)Integer.parseInt(string);
                        if (System.currentTimeMillis() >= nextTime) {
                            fetchRecord.setTime(System.currentTimeMillis());
                            fetchRecord.setFrequency(1);
                            n2 = 0;
                        } else {
                            n2 = (int)(nextTime - System.currentTimeMillis());
                        }
                    }
                } else {
                    String sj;
                    String string = concurrentRate;
                    int n3 = rateIndex + 1;
                    boolean bl8 = false;
                    String string2 = string;
                    if (string2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string3 = string2.substring(n3);
                    Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.String).substring(startIndex)");
                    String string4 = sj = string3;
                    boolean bl9 = false;
                    long nextTime = fetchRecord.getTime() + (long)Integer.parseInt(string4);
                    if (System.currentTimeMillis() >= nextTime) {
                        fetchRecord.setTime(System.currentTimeMillis());
                        fetchRecord.setFrequency(1);
                        n2 = 0;
                    } else {
                        String cs;
                        String string5 = concurrentRate;
                        int n4 = 0;
                        boolean bl10 = false;
                        String string6 = string5;
                        if (string6 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string7 = string6.substring(n4, rateIndex);
                        Intrinsics.checkNotNullExpressionValue((Object)string7, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        string5 = cs = string7;
                        n4 = 0;
                        if (fetchRecord.getFrequency() > Integer.parseInt(string5)) {
                            n2 = (int)(nextTime - System.currentTimeMillis());
                        } else {
                            fetchRecord.setFrequency(fetchRecord.getFrequency() + 1);
                            n2 = 0;
                        }
                    }
                }
            }
            catch (Exception e) {
                n2 = 0;
            }
            n = n2;
        }
        int waitTime = n;
        if (waitTime > 0) {
            throw new ConcurrentException("\u6839\u636e\u5e76\u53d1\u7387\u8fd8\u9700\u7b49\u5f85" + waitTime + "\u6beb\u79d2\u624d\u53ef\u4ee5\u8bbf\u95ee", waitTime);
        }
        return fetchRecord;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void fetchEnd(ConcurrentRecord concurrentRecord) {
        if (concurrentRecord != null && !concurrentRecord.getConcurrent()) {
            boolean bl = false;
            boolean bl2 = false;
            synchronized (concurrentRecord) {
                boolean bl3 = false;
                concurrentRecord.setFrequency(concurrentRecord.getFrequency() - 1);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getStrResponseAwait(@Nullable String var1_1, @Nullable String var2_2, boolean var3_3, @NotNull Continuation<? super StrResponse> var4_4) {
        block13: {
            block14: {
                if (!(var4_4 instanceof getStrResponseAwait.1)) ** GOTO lbl-1000
                var22_5 = var4_4;
                if ((var22_5.label & -2147483648) != 0) {
                    var22_5.label -= -2147483648;
                } else lbl-1000:
                // 2 sources

                {
                    $continuation = new /* Unavailable Anonymous Inner Class!! */;
                }
                $result = $continuation.result;
                var25_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch ($continuation.label) {
                    case 0: {
                        ResultKt.throwOnFailure((Object)$result);
                        if (this.getType() == null) break;
                        var19_8 = StringUtils.INSTANCE;
                        var18_9 = this.getUrl();
                        $continuation.L$0 = var18_9;
                        $continuation.L$1 = var19_8;
                        $continuation.label = 1;
                        v0 = this.getByteArrayAwait((Continuation)$continuation);
                        if (v0 == var25_7) {
                            return var25_7;
                        }
                        ** GOTO lbl27
                    }
                    case 1: {
                        var19_8 = (StringUtils)$continuation.L$1;
                        var18_9 = (String)$continuation.L$0;
                        ResultKt.throwOnFailure((Object)$result);
                        v0 = $result;
lbl27:
                        // 2 sources

                        var20_10 = v0;
                        var23_11 = var19_8.byteToHexString((byte[])var20_10);
                        var24_12 = var18_9;
                        return new StrResponse(var24_12, var23_11);
                    }
                }
                concurrentRecord = this.fetchStart();
                var6_14 = this.source;
                this.setCookie(var6_14 == null ? null : var6_14.getKey());
                var6_14 = null;
                if (!this.useWebView || useWebView == false) break block13;
                var7_15 = this.method;
                var8_16 = WhenMappings.$EnumSwitchMapping$0[var7_15.ordinal()];
                if (var8_16 != 2) break block14;
                var9_17 = this.urlNoQuery;
                var11_18 /* !! */  = this.source;
                var10_19 = var11_18 /* !! */  == null ? null : var11_18 /* !! */ .getKey();
                var12_20 = this.webJs;
                var11_18 /* !! */  = var12_20 == null ? jsStr : var12_20;
                var12_20 = this.getHeaderMap();
                var13_21 = this.getBody();
                var14_22 = this.getUserNameSpace();
                var15_23 = this.getDebugLog();
                $continuation.L$0 = this;
                $continuation.L$1 = concurrentRecord;
                $continuation.label = 2;
                v1 = ReaderAdapterInterface.DefaultImpls.getStrResponseByRemoteWebview$default((ReaderAdapterInterface)ReaderAdapterHelper.INSTANCE.getAdapter(), (String)var9_17, null, null, (String)var10_19, (Map)((Map)var12_20), (String)sourceRegex, (String)var11_18 /* !! */ , null, (boolean)true, (String)var13_21, (String)var14_22, (DebugLog)var15_23, (Continuation)$continuation, (int)134, null);
                if (v1 == var25_7) {
                    return var25_7;
                }
                ** GOTO lbl60
                {
                    case 2: {
                        concurrentRecord = (ConcurrentRecord)$continuation.L$1;
                        this = (AnalyzeUrl)$continuation.L$0;
                        ResultKt.throwOnFailure((Object)$result);
                        v1 = $result;
lbl60:
                        // 2 sources

                        v2 = (StrResponse)v1;
                        ** GOTO lbl84
                    }
                }
            }
            var9_17 = this.getUrl();
            var11_18 /* !! */  = this.source;
            var10_19 = var11_18 /* !! */  == null ? null : var11_18 /* !! */ .getKey();
            var12_20 = this.webJs;
            var11_18 /* !! */  = var12_20 == null ? jsStr : var12_20;
            var12_20 = this.getHeaderMap();
            var13_21 = this.getUserNameSpace();
            var14_22 = this.getDebugLog();
            $continuation.L$0 = this;
            $continuation.L$1 = concurrentRecord;
            $continuation.label = 3;
            v3 = ReaderAdapterInterface.DefaultImpls.getStrResponseByRemoteWebview$default((ReaderAdapterInterface)ReaderAdapterHelper.INSTANCE.getAdapter(), (String)var9_17, null, null, (String)var10_19, (Map)((Map)var12_20), (String)sourceRegex, (String)var11_18 /* !! */ , null, (boolean)false, null, (String)var13_21, (DebugLog)var14_22, (Continuation)$continuation, (int)902, null);
            if (v3 == var25_7) {
                return var25_7;
            }
            ** GOTO lbl83
            {
                case 3: {
                    concurrentRecord = (ConcurrentRecord)$continuation.L$1;
                    this = (AnalyzeUrl)$continuation.L$0;
                    ResultKt.throwOnFailure((Object)$result);
                    v3 = $result;
lbl83:
                    // 2 sources

                    v2 = (StrResponse)v3;
lbl84:
                    // 2 sources

                    var6_14 = v2;
                    ** GOTO lbl101
                }
            }
        }
        $continuation.L$0 = this;
        $continuation.L$1 = concurrentRecord;
        $continuation.label = 4;
        v4 = OkHttpUtilsKt.newCallStrResponse((OkHttpClient)HttpHelperKt.getProxyClient((String)this.proxy, (DebugLog)this.getDebugLog()), (int)this.retry, (Function1)((Function1)new /* Unavailable Anonymous Inner Class!! */), (Continuation)$continuation);
        if (v4 == var25_7) {
            return var25_7;
        }
        ** GOTO lbl99
        {
            case 4: {
                concurrentRecord = (ConcurrentRecord)$continuation.L$1;
                this = (AnalyzeUrl)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v4 = $result;
lbl99:
                // 2 sources

                strResponse = (StrResponse)v4;
                this.saveCookieJar(strResponse.getRaw());
lbl101:
                // 2 sources

                this.fetchEnd(concurrentRecord);
                return strResponse;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object getStrResponseAwait$default(AnalyzeUrl analyzeUrl, String string, String string2, boolean bl, Continuation continuation, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            string2 = null;
        }
        if ((n & 4) != 0) {
            bl = true;
        }
        return analyzeUrl.getStrResponseAwait(string, string2, bl, continuation);
    }

    public final void saveCookieJar(@NotNull Response response2) {
        Intrinsics.checkNotNullParameter((Object)response2, (String)"response");
        List cookieList = response2.headers("Set-Cookie");
        if (cookieList.size() > 0) {
            CookieStore cookieStore = new CookieStore(this.getUserNameSpace());
            String domain = NetworkUtils.INSTANCE.getSubDomain(this.url);
            Iterable $this$forEach$iv = cookieList;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                String it = (String)element$iv;
                boolean bl = false;
                cookieStore.replaceCookie(Intrinsics.stringPlus((String)domain, (Object)"_cookieJar"), it);
            }
        }
    }

    @JvmOverloads
    @NotNull
    public final StrResponse getStrResponse(@Nullable String jsStr, @Nullable String sourceRegex, boolean useWebView) {
        return (StrResponse)BuildersKt.runBlocking$default(null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)1, null);
    }

    public static /* synthetic */ StrResponse getStrResponse$default(AnalyzeUrl analyzeUrl, String string, String string2, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            string2 = null;
        }
        if ((n & 4) != 0) {
            bl = true;
        }
        return analyzeUrl.getStrResponse(string, string2, bl);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getResponseAwait(@NotNull Continuation<? super Response> var1_1) {
        if (!(var1_1 instanceof getResponseAwait.1)) ** GOTO lbl-1000
        var5_2 = var1_1;
        if ((var5_2.label & -2147483648) != 0) {
            var5_2.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var6_4 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                concurrentRecord = this.fetchStart();
                var3_6 = this.source;
                this.setCookie(var3_6 == null ? null : var3_6.getKey());
                $continuation.L$0 = this;
                $continuation.L$1 = concurrentRecord;
                $continuation.label = 1;
                v0 = OkHttpUtilsKt.newCallResponse((OkHttpClient)HttpHelperKt.getProxyClient$default((String)this.proxy, null, (int)2, null), (int)this.retry, (Function1)((Function1)new /* Unavailable Anonymous Inner Class!! */), (Continuation)$continuation);
                if (v0 == var6_4) {
                    return var6_4;
                }
                ** GOTO lbl27
            }
            case 1: {
                concurrentRecord = (ConcurrentRecord)$continuation.L$1;
                this = (AnalyzeUrl)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                response = (Response)v0;
                this.fetchEnd(concurrentRecord);
                return response;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @NotNull
    public final Response getResponse() {
        return (Response)BuildersKt.runBlocking$default(null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)1, null);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getByteArrayAwait(@NotNull Continuation<? super byte[]> var1_1) {
        if (!(var1_1 instanceof getByteArrayAwait.1)) ** GOTO lbl-1000
        var7_2 = var1_1;
        if ((var7_2.label & -2147483648) != 0) {
            var7_2.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var8_4 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                concurrentRecord = this.fetchStart();
                dataUriFindResult = Regex.find$default((Regex)AppPattern.INSTANCE.getDataUriRegex(), (CharSequence)this.urlNoQuery, (int)0, (int)2, null);
                if (dataUriFindResult != null) {
                    dataUriBase64 = (String)dataUriFindResult.getGroupValues().get(1);
                    byteArray = Base64.decode((String)dataUriBase64, (int)0);
                    this.fetchEnd(concurrentRecord);
                    Intrinsics.checkNotNullExpressionValue((Object)byteArray, (String)"byteArray");
                    return byteArray;
                }
                dataUriBase64 = this.source;
                this.setCookie(dataUriBase64 == null ? null : dataUriBase64.getKey());
                $continuation.L$0 = this;
                $continuation.L$1 = concurrentRecord;
                $continuation.label = 1;
                v0 = OkHttpUtilsKt.newCallResponseBody((OkHttpClient)HttpHelperKt.getProxyClient$default((String)this.proxy, null, (int)2, null), (int)this.retry, (Function1)((Function1)new /* Unavailable Anonymous Inner Class!! */), (Continuation)$continuation);
                if (v0 == var8_4) {
                    return var8_4;
                }
                ** GOTO lbl34
            }
            case 1: {
                var2_5 = (ConcurrentRecord)$continuation.L$1;
                this = (AnalyzeUrl)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl34:
                // 2 sources

                byteArray = ((ResponseBody)v0).bytes();
                this.fetchEnd(var2_5);
                return byteArray;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @NotNull
    public final byte[] getByteArray() {
        return (byte[])BuildersKt.runBlocking$default(null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)1, null);
    }

    @Nullable
    public final Object upload(@NotNull String fileName, @NotNull Object file, @NotNull String contentType, @NotNull Continuation<? super StrResponse> $completion) {
        return OkHttpUtilsKt.newCallStrResponse((OkHttpClient)HttpHelperKt.getProxyClient$default((String)this.proxy, null, (int)2, null), (int)this.retry, (Function1)((Function1)new /* Unavailable Anonymous Inner Class!! */), $completion);
    }

    private final void setCookie(String tag) {
        String key;
        CharSequence charSequence;
        CharSequence charSequence2 = tag;
        String domain = NetworkUtils.INSTANCE.getSubDomain((String)(charSequence2 == null ? this.url : charSequence2));
        charSequence2 = domain;
        boolean bl = false;
        if (charSequence2.length() == 0) {
            return;
        }
        CookieStore cookieStore = new CookieStore(this.getUserNameSpace());
        if (this.enabledCookieJar && (charSequence = cookieStore.getCookie(key = Intrinsics.stringPlus((String)domain, (Object)"_cookieJar"))) != null) {
            CharSequence charSequence3 = charSequence;
            boolean bl2 = false;
            boolean bl3 = false;
            CharSequence it = charSequence3;
            boolean bl4 = false;
            cookieStore.replaceCookie(domain, (String)it);
        }
        String cookie = cookieStore.getCookie(domain);
        charSequence = cookie;
        boolean bl5 = false;
        if (charSequence.length() > 0) {
            String newCookie;
            Map cookieMap = cookieStore.cookieToMap(cookie);
            String string = (String)this.headerMap.get("Cookie");
            String string2 = string == null ? "" : string;
            Map customCookieMap = cookieStore.cookieToMap(string2);
            cookieMap.putAll(customCookieMap);
            string = newCookie = cookieStore.mapToCookie(cookieMap);
            if (string != null) {
                String string3 = string;
                boolean bl6 = false;
                boolean bl7 = false;
                String it = string3;
                boolean bl8 = false;
                this.getHeaderMap().put("Cookie", it);
            }
        }
    }

    @NotNull
    public final String getUserAgent() {
        String string = (String)this.headerMap.get("User-Agent");
        return string == null ? AppConst.INSTANCE.getUserAgent() : string;
    }

    public final boolean isPost() {
        return this.method == RequestMethod.POST;
    }

    @Nullable
    public byte[] aesBase64DecodeToByteArray(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesBase64DecodeToByteArray((JsExtensions)this, (String)str, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesBase64DecodeToString(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesBase64DecodeToString((JsExtensions)this, (String)str, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesDecodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesDecodeArgsBase64Str((JsExtensions)this, (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @Nullable
    public byte[] aesDecodeToByteArray(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesDecodeToByteArray((JsExtensions)this, (String)str, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesDecodeToString(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesDecodeToString((JsExtensions)this, (String)str, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesEncodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesEncodeArgsBase64Str((JsExtensions)this, (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @Nullable
    public byte[] aesEncodeToBase64ByteArray(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesEncodeToBase64ByteArray((JsExtensions)this, (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesEncodeToBase64String(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesEncodeToBase64String((JsExtensions)this, (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public byte[] aesEncodeToByteArray(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesEncodeToByteArray((JsExtensions)this, (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String aesEncodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesEncodeToString((JsExtensions)this, (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String ajax(@NotNull String urlStr) {
        return JsExtensions.DefaultImpls.ajax((JsExtensions)this, (String)urlStr);
    }

    @NotNull
    public StrResponse[] ajaxAll(@NotNull String[] urlList) {
        return JsExtensions.DefaultImpls.ajaxAll((JsExtensions)this, (String[])urlList);
    }

    @NotNull
    public String androidId() {
        return JsExtensions.DefaultImpls.androidId((JsExtensions)this);
    }

    @NotNull
    public String base64Decode(@NotNull String str) {
        return JsExtensions.DefaultImpls.base64Decode((JsExtensions)this, (String)str);
    }

    @NotNull
    public String base64Decode(@NotNull String str, int flags) {
        return JsExtensions.DefaultImpls.base64Decode((JsExtensions)this, (String)str, (int)flags);
    }

    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String str) {
        return JsExtensions.DefaultImpls.base64DecodeToByteArray((JsExtensions)this, (String)str);
    }

    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String str, int flags) {
        return JsExtensions.DefaultImpls.base64DecodeToByteArray((JsExtensions)this, (String)str, (int)flags);
    }

    @Nullable
    public String base64Encode(@NotNull String str) {
        return JsExtensions.DefaultImpls.base64Encode((JsExtensions)this, (String)str);
    }

    @Nullable
    public String base64Encode(@NotNull String str, int flags) {
        return JsExtensions.DefaultImpls.base64Encode((JsExtensions)this, (String)str, (int)flags);
    }

    @Nullable
    public String cacheFile(@NotNull String urlStr) {
        return JsExtensions.DefaultImpls.cacheFile((JsExtensions)this, (String)urlStr);
    }

    @Nullable
    public String cacheFile(@NotNull String urlStr, int saveTime) {
        return JsExtensions.DefaultImpls.cacheFile((JsExtensions)this, (String)urlStr, (int)saveTime);
    }

    @NotNull
    public StrResponse connect(@NotNull String urlStr) {
        return JsExtensions.DefaultImpls.connect((JsExtensions)this, (String)urlStr);
    }

    @NotNull
    public StrResponse connect(@NotNull String urlStr, @Nullable String header) {
        return JsExtensions.DefaultImpls.connect((JsExtensions)this, (String)urlStr, (String)header);
    }

    public void deleteFile(@NotNull String path) {
        JsExtensions.DefaultImpls.deleteFile((JsExtensions)this, (String)path);
    }

    @Nullable
    public String desBase64DecodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.desBase64DecodeToString((JsExtensions)this, (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String desDecodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.desDecodeToString((JsExtensions)this, (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String desEncodeToBase64String(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.desEncodeToBase64String((JsExtensions)this, (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String desEncodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.desEncodeToString((JsExtensions)this, (String)data, (String)key, (String)transformation, (String)iv);
    }

    @Nullable
    public String digestBase64Str(@NotNull String data, @NotNull String algorithm) {
        return JsExtensions.DefaultImpls.digestBase64Str((JsExtensions)this, (String)data, (String)algorithm);
    }

    @Nullable
    public String digestHex(@NotNull String data, @NotNull String algorithm) {
        return JsExtensions.DefaultImpls.digestHex((JsExtensions)this, (String)data, (String)algorithm);
    }

    @NotNull
    public String downloadFile(@NotNull String content, @NotNull String url2) {
        return JsExtensions.DefaultImpls.downloadFile((JsExtensions)this, (String)content, (String)url2);
    }

    @NotNull
    public String encodeURI(@NotNull String str) {
        return JsExtensions.DefaultImpls.encodeURI((JsExtensions)this, (String)str);
    }

    @NotNull
    public String encodeURI(@NotNull String str, @NotNull String enc) {
        return JsExtensions.DefaultImpls.encodeURI((JsExtensions)this, (String)str, (String)enc);
    }

    @NotNull
    public Connection.Response get(@NotNull String urlStr, @NotNull Map<String, String> headers) {
        return JsExtensions.DefaultImpls.get((JsExtensions)this, (String)urlStr, headers);
    }

    @NotNull
    public String getCookie(@NotNull String tag, @Nullable String key) {
        return JsExtensions.DefaultImpls.getCookie((JsExtensions)this, (String)tag, (String)key);
    }

    @NotNull
    public File getFile(@NotNull String path) {
        return JsExtensions.DefaultImpls.getFile((JsExtensions)this, (String)path);
    }

    @NotNull
    public String getTxtInFolder(@NotNull String unzipPath) {
        return JsExtensions.DefaultImpls.getTxtInFolder((JsExtensions)this, (String)unzipPath);
    }

    @Nullable
    public byte[] getZipByteArrayContent(@NotNull String url2, @NotNull String path) {
        return JsExtensions.DefaultImpls.getZipByteArrayContent((JsExtensions)this, (String)url2, (String)path);
    }

    @NotNull
    public String getZipStringContent(@NotNull String url2, @NotNull String path) {
        return JsExtensions.DefaultImpls.getZipStringContent((JsExtensions)this, (String)url2, (String)path);
    }

    @NotNull
    public String getZipStringContent(@NotNull String url2, @NotNull String path, @NotNull String charsetName) {
        return JsExtensions.DefaultImpls.getZipStringContent((JsExtensions)this, (String)url2, (String)path, (String)charsetName);
    }

    @NotNull
    public Connection.Response head(@NotNull String urlStr, @NotNull Map<String, String> headers) {
        return JsExtensions.DefaultImpls.head((JsExtensions)this, (String)urlStr, headers);
    }

    @NotNull
    public String htmlFormat(@NotNull String str) {
        return JsExtensions.DefaultImpls.htmlFormat((JsExtensions)this, (String)str);
    }

    @NotNull
    public String importScript(@NotNull String path) {
        return JsExtensions.DefaultImpls.importScript((JsExtensions)this, (String)path);
    }

    @NotNull
    public String log(@NotNull String msg) {
        return JsExtensions.DefaultImpls.log((JsExtensions)this, (String)msg);
    }

    public void logType(@Nullable Object any) {
        JsExtensions.DefaultImpls.logType((JsExtensions)this, (Object)any);
    }

    public void longToast(@Nullable Object msg) {
        JsExtensions.DefaultImpls.longToast((JsExtensions)this, (Object)msg);
    }

    @NotNull
    public String md5Encode(@NotNull String str) {
        return JsExtensions.DefaultImpls.md5Encode((JsExtensions)this, (String)str);
    }

    @NotNull
    public String md5Encode16(@NotNull String str) {
        return JsExtensions.DefaultImpls.md5Encode16((JsExtensions)this, (String)str);
    }

    @NotNull
    public Connection.Response post(@NotNull String urlStr, @NotNull String body, @NotNull Map<String, String> headers) {
        return JsExtensions.DefaultImpls.post((JsExtensions)this, (String)urlStr, (String)body, headers);
    }

    @Nullable
    public QueryTTF queryBase64TTF(@Nullable String base64) {
        return JsExtensions.DefaultImpls.queryBase64TTF((JsExtensions)this, (String)base64);
    }

    @Nullable
    public QueryTTF queryTTF(@Nullable String str) {
        return JsExtensions.DefaultImpls.queryTTF((JsExtensions)this, (String)str);
    }

    @NotNull
    public String randomUUID() {
        return JsExtensions.DefaultImpls.randomUUID((JsExtensions)this);
    }

    @Nullable
    public byte[] readFile(@NotNull String path) {
        return JsExtensions.DefaultImpls.readFile((JsExtensions)this, (String)path);
    }

    @NotNull
    public String readTxtFile(@NotNull String path) {
        return JsExtensions.DefaultImpls.readTxtFile((JsExtensions)this, (String)path);
    }

    @NotNull
    public String readTxtFile(@NotNull String path, @NotNull String charsetName) {
        return JsExtensions.DefaultImpls.readTxtFile((JsExtensions)this, (String)path, (String)charsetName);
    }

    @NotNull
    public String replaceFont(@NotNull String text, @Nullable QueryTTF font1, @Nullable QueryTTF font2) {
        return JsExtensions.DefaultImpls.replaceFont((JsExtensions)this, (String)text, (QueryTTF)font1, (QueryTTF)font2);
    }

    @NotNull
    public String timeFormat(long time) {
        return JsExtensions.DefaultImpls.timeFormat((JsExtensions)this, (long)time);
    }

    @Nullable
    public String timeFormatUTC(long time, @NotNull String format, int sh) {
        return JsExtensions.DefaultImpls.timeFormatUTC((JsExtensions)this, (long)time, (String)format, (int)sh);
    }

    public void toast(@Nullable Object msg) {
        JsExtensions.DefaultImpls.toast((JsExtensions)this, (Object)msg);
    }

    @Nullable
    public String tripleDESDecodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.tripleDESDecodeArgsBase64Str((JsExtensions)this, (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @Nullable
    public String tripleDESDecodeStr(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.tripleDESDecodeStr((JsExtensions)this, (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @Nullable
    public String tripleDESEncodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.tripleDESEncodeArgsBase64Str((JsExtensions)this, (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @Nullable
    public String tripleDESEncodeBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.tripleDESEncodeBase64Str((JsExtensions)this, (String)data, (String)key, (String)mode, (String)padding, (String)iv);
    }

    @NotNull
    public String unzipFile(@NotNull String zipPath) {
        return JsExtensions.DefaultImpls.unzipFile((JsExtensions)this, (String)zipPath);
    }

    @NotNull
    public String utf8ToGbk(@NotNull String str) {
        return JsExtensions.DefaultImpls.utf8ToGbk((JsExtensions)this, (String)str);
    }

    @Nullable
    public String webView(@Nullable String html, @Nullable String url2, @Nullable String js) {
        return JsExtensions.DefaultImpls.webView((JsExtensions)this, (String)html, (String)url2, (String)js);
    }

    @JvmOverloads
    @NotNull
    public final StrResponse getStrResponse(@Nullable String jsStr, @Nullable String sourceRegex) {
        return AnalyzeUrl.getStrResponse$default((AnalyzeUrl)this, (String)jsStr, (String)sourceRegex, (boolean)false, (int)4, null);
    }

    @JvmOverloads
    @NotNull
    public final StrResponse getStrResponse(@Nullable String jsStr) {
        return AnalyzeUrl.getStrResponse$default((AnalyzeUrl)this, (String)jsStr, null, (boolean)false, (int)6, null);
    }

    @JvmOverloads
    @NotNull
    public final StrResponse getStrResponse() {
        return AnalyzeUrl.getStrResponse$default((AnalyzeUrl)this, null, null, (boolean)false, (int)7, null);
    }

    public static final /* synthetic */ Pattern access$getParamPattern$cp() {
        return paramPattern;
    }

    public static final /* synthetic */ RequestMethod access$getMethod$p(AnalyzeUrl $this) {
        return $this.method;
    }

    public static final /* synthetic */ String access$getUrlNoQuery$p(AnalyzeUrl $this) {
        return $this.urlNoQuery;
    }

    public static final /* synthetic */ LinkedHashMap access$getFieldMap$p(AnalyzeUrl $this) {
        return $this.fieldMap;
    }

    static {
        Pattern pattern = Pattern.compile("\\s*,\\s*(?=\\{)");
        Intrinsics.checkNotNullExpressionValue((Object)pattern, (String)"compile(\"\\\\s*,\\\\s*(?=\\\\{)\")");
        paramPattern = pattern;
        pagePattern = Pattern.compile("<(.*?)>");
        boolean bl = false;
        concurrentRecordMap = new HashMap();
    }
}

