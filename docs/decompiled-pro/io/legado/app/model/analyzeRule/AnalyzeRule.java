/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.script.Bindings
 *  com.script.SimpleBindings
 *  io.legado.app.constant.AppConst
 *  io.legado.app.constant.AppPattern
 *  io.legado.app.data.entities.BaseBook
 *  io.legado.app.data.entities.BaseSource
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.BookChapter
 *  io.legado.app.data.entities.BookSource
 *  io.legado.app.help.CacheManager
 *  io.legado.app.help.JsExtensions
 *  io.legado.app.help.JsExtensions$DefaultImpls
 *  io.legado.app.help.http.CookieStore
 *  io.legado.app.help.http.StrResponse
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.analyzeRule.AnalyzeByJSonPath
 *  io.legado.app.model.analyzeRule.AnalyzeByJSoup
 *  io.legado.app.model.analyzeRule.AnalyzeByRegex
 *  io.legado.app.model.analyzeRule.AnalyzeByXPath
 *  io.legado.app.model.analyzeRule.AnalyzeRule
 *  io.legado.app.model.analyzeRule.AnalyzeRule$Companion
 *  io.legado.app.model.analyzeRule.AnalyzeRule$Mode
 *  io.legado.app.model.analyzeRule.AnalyzeRule$SourceRule
 *  io.legado.app.model.analyzeRule.AnalyzeRule$WhenMappings
 *  io.legado.app.model.analyzeRule.AnalyzeRule$splitPutRule$$inlined$fromJsonObject$1
 *  io.legado.app.model.analyzeRule.QueryTTF
 *  io.legado.app.model.analyzeRule.RuleDataInterface
 *  io.legado.app.utils.GsonExtensionsKt
 *  io.legado.app.utils.NetworkUtils
 *  io.legado.app.utils.StringExtensionsKt
 *  io.legado.app.utils.StringUtils
 *  io.legado.app.utils.TextUtils
 *  kotlin.Metadata
 *  kotlin.Result
 *  kotlin.Result$Companion
 *  kotlin.ResultKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  kotlinx.coroutines.BuildersKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jsoup.Connection$Response
 *  org.jsoup.nodes.Entities
 *  org.mozilla.javascript.NativeObject
 */
package io.legado.app.model.analyzeRule;

import com.script.Bindings;
import com.script.SimpleBindings;
import io.legado.app.constant.AppConst;
import io.legado.app.constant.AppPattern;
import io.legado.app.data.entities.BaseBook;
import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.data.entities.BookSource;
import io.legado.app.help.CacheManager;
import io.legado.app.help.JsExtensions;
import io.legado.app.help.http.CookieStore;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeByJSonPath;
import io.legado.app.model.analyzeRule.AnalyzeByJSoup;
import io.legado.app.model.analyzeRule.AnalyzeByRegex;
import io.legado.app.model.analyzeRule.AnalyzeByXPath;
import io.legado.app.model.analyzeRule.AnalyzeRule;
import io.legado.app.model.analyzeRule.AnalyzeRule$splitPutRule$;
import io.legado.app.model.analyzeRule.QueryTTF;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.NetworkUtils;
import io.legado.app.utils.StringExtensionsKt;
import io.legado.app.utils.StringUtils;
import io.legado.app.utils.TextUtils;
import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;
import org.jsoup.nodes.Entities;
import org.mozilla.javascript.NativeObject;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 g2\u00020\u0001:\u0003ghiB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\u0012\u00108\u001a\u0004\u0018\u00010\u00102\u0006\u00109\u001a\u00020\u0010H\u0016J\u001c\u0010:\u001a\u0004\u0018\u00010\u001e2\u0006\u0010;\u001a\u00020\u00102\n\b\u0002\u0010<\u001a\u0004\u0018\u00010\u001eJ\u000e\u0010=\u001a\u00020\u00102\u0006\u0010>\u001a\u00020\u0010J\u0010\u0010?\u001a\u00020\n2\u0006\u0010@\u001a\u00020\u001eH\u0002J\u0010\u0010A\u001a\u00020\f2\u0006\u0010@\u001a\u00020\u001eH\u0002J\u0010\u0010B\u001a\u00020\u000e2\u0006\u0010@\u001a\u00020\u001eH\u0002J\u0010\u0010C\u001a\u0004\u0018\u00010\u001e2\u0006\u0010D\u001a\u00020\u0010J\u0014\u0010E\u001a\b\u0012\u0004\u0012\u00020\u001e0F2\u0006\u0010D\u001a\u00020\u0010J\n\u0010G\u001a\u0004\u0018\u00010\u0007H\u0016J\n\u0010H\u001a\u0004\u0018\u00010\u0005H\u0016J(\u0010I\u001a\u00020\u00102\b\u0010D\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010J\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010K\u001a\u00020'H\u0007J0\u0010I\u001a\u00020\u00102\u0010\u0010L\u001a\f\u0012\b\u0012\u00060MR\u00020\u00000F2\n\b\u0002\u0010J\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010K\u001a\u00020'H\u0007J0\u0010N\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010F2\b\u0010O\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010J\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010K\u001a\u00020'H\u0007J8\u0010N\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010F2\u0010\u0010L\u001a\f\u0012\b\u0012\u00060MR\u00020\u00000F2\n\b\u0002\u0010J\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010K\u001a\u00020'H\u0007J\b\u0010P\u001a\u00020\u0010H\u0016J\u0016\u0010Q\u001a\u00020\u00102\u0006\u0010>\u001a\u00020\u00102\u0006\u0010R\u001a\u00020\u0010J\u001c\u0010S\u001a\u00020T2\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100VH\u0002J\u0006\u0010W\u001a\u00020TJ\u0006\u0010X\u001a\u00020TJ\u0006\u0010Y\u001a\u00020TJ\u001c\u0010Z\u001a\u00020\u00102\u0006\u0010<\u001a\u00020\u00102\n\u0010O\u001a\u00060MR\u00020\u0000H\u0002J\u0010\u0010[\u001a\u00020\u00002\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010J\u001e\u0010\\\u001a\u00020\u00002\b\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0007J\u0010\u0010]\u001a\u0004\u0018\u0001002\u0006\u0010^\u001a\u00020\u0010J4\u0010_\u001a\u00020\u00102\u0006\u0010D\u001a\u00020\u00102\"\u0010`\u001a\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100aj\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0010`bH\u0002J$\u0010c\u001a\f\u0012\b\u0012\u00060MR\u00020\u00000F2\b\u0010D\u001a\u0004\u0018\u00010\u00102\b\b\u0002\u0010d\u001a\u00020'J\u0012\u0010e\u001a\u0004\u0018\u00010\u00102\b\u0010f\u001a\u0004\u0018\u00010\u0010R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\u0011\u001a\u0004\u0018\u00010\u00102\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u001e@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010)\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0013\"\u0004\b+\u0010,R\u000e\u0010-\u001a\u00020'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u00101\u001a\u0004\u0018\u0001002\b\u0010\u000f\u001a\u0004\u0018\u000100@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006j"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeRule;", "Lio/legado/app/help/JsExtensions;", "ruleData", "Lio/legado/app/model/analyzeRule/RuleDataInterface;", "source", "Lio/legado/app/data/entities/BaseSource;", "debugLog", "Lio/legado/app/model/DebugLog;", "(Lio/legado/app/model/analyzeRule/RuleDataInterface;Lio/legado/app/data/entities/BaseSource;Lio/legado/app/model/DebugLog;)V", "analyzeByJSonPath", "Lio/legado/app/model/analyzeRule/AnalyzeByJSonPath;", "analyzeByJSoup", "Lio/legado/app/model/analyzeRule/AnalyzeByJSoup;", "analyzeByXPath", "Lio/legado/app/model/analyzeRule/AnalyzeByXPath;", "<set-?>", "", "baseUrl", "getBaseUrl", "()Ljava/lang/String;", "book", "Lio/legado/app/data/entities/BaseBook;", "getBook", "()Lio/legado/app/data/entities/BaseBook;", "chapter", "Lio/legado/app/data/entities/BookChapter;", "getChapter", "()Lio/legado/app/data/entities/BookChapter;", "setChapter", "(Lio/legado/app/data/entities/BookChapter;)V", "", "content", "getContent", "()Ljava/lang/Object;", "getDebugLog", "()Lio/legado/app/model/DebugLog;", "setDebugLog", "(Lio/legado/app/model/DebugLog;)V", "isJSON", "", "isRegex", "nextChapterUrl", "getNextChapterUrl", "setNextChapterUrl", "(Ljava/lang/String;)V", "objectChangedJP", "objectChangedJS", "objectChangedXP", "Ljava/net/URL;", "redirectUrl", "getRedirectUrl", "()Ljava/net/URL;", "getRuleData", "()Lio/legado/app/model/analyzeRule/RuleDataInterface;", "setRuleData", "(Lio/legado/app/model/analyzeRule/RuleDataInterface;)V", "ajax", "urlStr", "evalJS", "jsStr", "result", "get", "key", "getAnalyzeByJSonPath", "o", "getAnalyzeByJSoup", "getAnalyzeByXPath", "getElement", "ruleStr", "getElements", "", "getLogger", "getSource", "getString", "mContent", "isUrl", "ruleList", "Lio/legado/app/model/analyzeRule/AnalyzeRule$SourceRule;", "getStringList", "rule", "getUserNameSpace", "put", "value", "putRule", "", "map", "", "reGetBook", "refreshBookUrl", "refreshTocUrl", "replaceRegex", "setBaseUrl", "setContent", "setRedirectUrl", "url", "splitPutRule", "putMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "splitSourceRule", "allInOne", "toNumChapter", "s", "Companion", "Mode", "SourceRule", "reader-pro"})
public final class AnalyzeRule
implements JsExtensions {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private RuleDataInterface ruleData;
    @Nullable
    private final BaseSource source;
    @Nullable
    private DebugLog debugLog;
    @Nullable
    private BookChapter chapter;
    @Nullable
    private String nextChapterUrl;
    @Nullable
    private Object content;
    @Nullable
    private String baseUrl;
    @Nullable
    private URL redirectUrl;
    private boolean isJSON;
    private boolean isRegex;
    @Nullable
    private AnalyzeByXPath analyzeByXPath;
    @Nullable
    private AnalyzeByJSoup analyzeByJSoup;
    @Nullable
    private AnalyzeByJSonPath analyzeByJSonPath;
    private boolean objectChangedXP;
    private boolean objectChangedJS;
    private boolean objectChangedJP;
    private static final Pattern putPattern = Pattern.compile("@put:(\\{[^}]+?\\})", 2);
    private static final Pattern evalPattern = Pattern.compile("@get:\\{[^}]+?\\}|\\{\\{[\\w\\W]*?\\}\\}", 2);
    private static final Pattern regexPattern = Pattern.compile("\\$\\d{1,2}");
    private static final Pattern titleNumPattern = Pattern.compile("(\u7b2c)(.+?)(\u7ae0)");

    public AnalyzeRule(@NotNull RuleDataInterface ruleData, @Nullable BaseSource source, @Nullable DebugLog debugLog) {
        Intrinsics.checkNotNullParameter((Object)ruleData, (String)"ruleData");
        this.ruleData = ruleData;
        this.source = source;
        this.debugLog = debugLog;
    }

    public /* synthetic */ AnalyzeRule(RuleDataInterface ruleDataInterface, BaseSource baseSource, DebugLog debugLog, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            baseSource = null;
        }
        if ((n & 4) != 0) {
            debugLog = null;
        }
        this(ruleDataInterface, baseSource, debugLog);
    }

    @NotNull
    public final RuleDataInterface getRuleData() {
        return this.ruleData;
    }

    public final void setRuleData(@NotNull RuleDataInterface ruleDataInterface) {
        Intrinsics.checkNotNullParameter((Object)ruleDataInterface, (String)"<set-?>");
        this.ruleData = ruleDataInterface;
    }

    @Nullable
    public final DebugLog getDebugLog() {
        return this.debugLog;
    }

    public final void setDebugLog(@Nullable DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    @Nullable
    public final BaseBook getBook() {
        RuleDataInterface ruleDataInterface = this.ruleData;
        return ruleDataInterface instanceof BaseBook ? (BaseBook)ruleDataInterface : null;
    }

    @Nullable
    public final BookChapter getChapter() {
        return this.chapter;
    }

    public final void setChapter(@Nullable BookChapter bookChapter) {
        this.chapter = bookChapter;
    }

    @Nullable
    public final String getNextChapterUrl() {
        return this.nextChapterUrl;
    }

    public final void setNextChapterUrl(@Nullable String string) {
        this.nextChapterUrl = string;
    }

    @Nullable
    public final Object getContent() {
        return this.content;
    }

    @Nullable
    public final String getBaseUrl() {
        return this.baseUrl;
    }

    @Nullable
    public final URL getRedirectUrl() {
        return this.redirectUrl;
    }

    @NotNull
    public String getUserNameSpace() {
        return this.ruleData.getUserNameSpace();
    }

    @Nullable
    public BaseSource getSource() {
        return this.source;
    }

    @Nullable
    public DebugLog getLogger() {
        return this.debugLog;
    }

    @JvmOverloads
    @NotNull
    public final AnalyzeRule setContent(@Nullable Object content, @Nullable String baseUrl) {
        if (content == null) {
            throw new AssertionError((Object)"\u5185\u5bb9\u4e0d\u53ef\u7a7a\uff08Content cannot be null\uff09");
        }
        this.content = content;
        this.isJSON = StringExtensionsKt.isJson((String)content.toString());
        this.setBaseUrl(baseUrl);
        this.objectChangedXP = true;
        this.objectChangedJS = true;
        this.objectChangedJP = true;
        return this;
    }

    public static /* synthetic */ AnalyzeRule setContent$default(AnalyzeRule analyzeRule, Object object, String string, int n, Object object2) {
        if ((n & 2) != 0) {
            string = null;
        }
        return analyzeRule.setContent(object, string);
    }

    @NotNull
    public final AnalyzeRule setBaseUrl(@Nullable String baseUrl) {
        String string = baseUrl;
        if (string != null) {
            String string2 = string;
            boolean bl = false;
            boolean bl2 = false;
            String it = string2;
            boolean bl3 = false;
            this.baseUrl = baseUrl;
        }
        return this;
    }

    @Nullable
    public final URL setRedirectUrl(@NotNull String url2) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        try {
            this.redirectUrl = new URL(url2);
        }
        catch (Exception e) {
            this.log("URL(" + url2 + ") error\n" + e.getLocalizedMessage());
        }
        return this.redirectUrl;
    }

    private final AnalyzeByXPath getAnalyzeByXPath(Object o) {
        AnalyzeByXPath analyzeByXPath;
        if (!Intrinsics.areEqual((Object)o, (Object)this.content)) {
            analyzeByXPath = new AnalyzeByXPath(o);
        } else {
            if (this.analyzeByXPath == null || this.objectChangedXP) {
                Object object = this.content;
                Intrinsics.checkNotNull((Object)object);
                this.analyzeByXPath = new AnalyzeByXPath(object);
                this.objectChangedXP = false;
            }
            AnalyzeByXPath analyzeByXPath2 = this.analyzeByXPath;
            analyzeByXPath = analyzeByXPath2;
            Intrinsics.checkNotNull((Object)analyzeByXPath2);
        }
        return analyzeByXPath;
    }

    private final AnalyzeByJSoup getAnalyzeByJSoup(Object o) {
        AnalyzeByJSoup analyzeByJSoup;
        if (!Intrinsics.areEqual((Object)o, (Object)this.content)) {
            analyzeByJSoup = new AnalyzeByJSoup(o);
        } else {
            if (this.analyzeByJSoup == null || this.objectChangedJS) {
                Object object = this.content;
                Intrinsics.checkNotNull((Object)object);
                this.analyzeByJSoup = new AnalyzeByJSoup(object);
                this.objectChangedJS = false;
            }
            AnalyzeByJSoup analyzeByJSoup2 = this.analyzeByJSoup;
            analyzeByJSoup = analyzeByJSoup2;
            Intrinsics.checkNotNull((Object)analyzeByJSoup2);
        }
        return analyzeByJSoup;
    }

    private final AnalyzeByJSonPath getAnalyzeByJSonPath(Object o) {
        AnalyzeByJSonPath analyzeByJSonPath;
        if (!Intrinsics.areEqual((Object)o, (Object)this.content)) {
            analyzeByJSonPath = new AnalyzeByJSonPath(o);
        } else {
            if (this.analyzeByJSonPath == null || this.objectChangedJP) {
                Object object = this.content;
                Intrinsics.checkNotNull((Object)object);
                this.analyzeByJSonPath = new AnalyzeByJSonPath(object);
                this.objectChangedJP = false;
            }
            AnalyzeByJSonPath analyzeByJSonPath2 = this.analyzeByJSonPath;
            analyzeByJSonPath = analyzeByJSonPath2;
            Intrinsics.checkNotNull((Object)analyzeByJSonPath2);
        }
        return analyzeByJSonPath;
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@Nullable String rule, @Nullable Object mContent, boolean isUrl) {
        CharSequence charSequence = rule;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || charSequence.length() == 0) {
            return null;
        }
        List ruleList = this.splitSourceRule(rule, false);
        return this.getStringList(ruleList, mContent, isUrl);
    }

    public static /* synthetic */ List getStringList$default(AnalyzeRule analyzeRule, String string, Object object, boolean bl, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        return analyzeRule.getStringList(string, object, bl);
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@NotNull List<SourceRule> ruleList, @Nullable Object mContent, boolean isUrl) {
        boolean bl;
        String[] stringArray;
        String[] content;
        Intrinsics.checkNotNullParameter(ruleList, (String)"ruleList");
        Object result2 = null;
        Object object = mContent;
        Object object2 = content = object == null ? this.content : object;
        if (content != null) {
            object = ruleList;
            boolean bl2 = false;
            if (!object.isEmpty()) {
                result2 = content;
                if (content instanceof NativeObject) {
                    object = ((NativeObject)content).get((Object)ruleList.get(0).getRule$reader_pro());
                    result2 = object == null ? null : object.toString();
                } else {
                    for (SourceRule sourceRule : ruleList) {
                        this.putRule((Map)sourceRule.getPutMap$reader_pro());
                        sourceRule.makeUpRule(result2);
                        stringArray = result2;
                        if (stringArray == null) continue;
                        String[] stringArray2 = stringArray;
                        boolean bl3 = false;
                        bl = false;
                        String[] it = stringArray2;
                        boolean bl4 = false;
                        CharSequence charSequence = sourceRule.getRule$reader_pro();
                        int n = 0;
                        if (charSequence.length() > 0) {
                            Object object3;
                            charSequence = sourceRule.getMode$reader_pro();
                            n = WhenMappings.$EnumSwitchMapping$0[charSequence.ordinal()];
                            switch (n) {
                                case 1: {
                                    object3 = this.evalJS(sourceRule.getRule$reader_pro(), result2);
                                    break;
                                }
                                case 2: {
                                    object3 = this.getAnalyzeByJSonPath((Object)it).getStringList$reader_pro(sourceRule.getRule$reader_pro());
                                    break;
                                }
                                case 3: {
                                    object3 = this.getAnalyzeByXPath((Object)it).getStringList$reader_pro(sourceRule.getRule$reader_pro());
                                    break;
                                }
                                case 4: {
                                    object3 = this.getAnalyzeByJSoup((Object)it).getStringList$reader_pro(sourceRule.getRule$reader_pro());
                                    break;
                                }
                                default: {
                                    object3 = sourceRule.getRule$reader_pro();
                                }
                            }
                            result2 = object3;
                        }
                        charSequence = sourceRule.getReplaceRegex$reader_pro();
                        n = 0;
                        if (charSequence.length() > 0 && result2 instanceof List) {
                            ArrayList<String> newList = new ArrayList<String>();
                            Object object4 = result2;
                            for (Object item : (List)object4) {
                                newList.add(this.replaceRegex(String.valueOf(item), sourceRule));
                            }
                            result2 = newList;
                            continue;
                        }
                        charSequence = sourceRule.getReplaceRegex$reader_pro();
                        n = 0;
                        if (!(charSequence.length() > 0)) continue;
                        result2 = this.replaceRegex(String.valueOf(result2), sourceRule);
                    }
                }
            }
        }
        if (result2 == null) {
            return null;
        }
        if (result2 instanceof String) {
            object = result2;
            CharSequence charSequence = (String)object;
            object = new String[]{"\n"};
            result2 = StringsKt.split$default((CharSequence)charSequence, (String[])object, (boolean)false, (int)0, (int)6, null);
        }
        if (isUrl) {
            ArrayList<String> urlList = new ArrayList<String>();
            if (result2 instanceof List) {
                stringArray = result2;
                for (Object url2 : (List)stringArray) {
                    String absoluteURL = NetworkUtils.INSTANCE.getAbsoluteURL(this.redirectUrl, String.valueOf(url2));
                    CharSequence charSequence = absoluteURL;
                    bl = false;
                    if (!(charSequence.length() > 0) || urlList.contains(absoluteURL)) continue;
                    urlList.add(absoluteURL);
                }
            }
            return urlList;
        }
        object = result2;
        return object instanceof List ? (List)object : null;
    }

    public static /* synthetic */ List getStringList$default(AnalyzeRule analyzeRule, List list2, Object object, boolean bl, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        return analyzeRule.getStringList(list2, object, bl);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@Nullable String ruleStr, @Nullable Object mContent, boolean isUrl) {
        if (TextUtils.isEmpty((CharSequence)ruleStr)) {
            return "";
        }
        List ruleList = AnalyzeRule.splitSourceRule$default((AnalyzeRule)this, (String)ruleStr, (boolean)false, (int)2, null);
        return this.getString(ruleList, mContent, isUrl);
    }

    public static /* synthetic */ String getString$default(AnalyzeRule analyzeRule, String string, Object object, boolean bl, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        return analyzeRule.getString(string, object, bl);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@NotNull List<SourceRule> ruleList, @Nullable Object mContent, boolean isUrl) {
        Object object;
        boolean bl;
        boolean bl2;
        Object object2;
        boolean bl3;
        Iterator<SourceRule> result2;
        block18: {
            block19: {
                Iterator<SourceRule> content;
                Intrinsics.checkNotNullParameter(ruleList, (String)"ruleList");
                result2 = null;
                Object object3 = mContent;
                Iterator<SourceRule> iterator = content = object3 == null ? this.content : object3;
                if (content == null) break block18;
                object3 = ruleList;
                bl3 = false;
                if (!(!object3.isEmpty())) break block18;
                result2 = content;
                if (!(result2 instanceof NativeObject)) break block19;
                object3 = ((NativeObject)result2).get((Object)ruleList.get(0).getRule$reader_pro());
                result2 = object3 == null ? null : object3.toString();
                break block18;
            }
            for (SourceRule sourceRule : ruleList) {
                int n;
                CharSequence charSequence;
                block21: {
                    Object it;
                    block20: {
                        this.putRule((Map)sourceRule.getPutMap$reader_pro());
                        sourceRule.makeUpRule((Object)result2);
                        object2 = result2;
                        if (object2 == null) continue;
                        Object object4 = object2;
                        bl2 = false;
                        bl = false;
                        it = object4;
                        boolean bl4 = false;
                        charSequence = sourceRule.getRule$reader_pro();
                        n = 0;
                        if (!StringsKt.isBlank((CharSequence)charSequence)) break block20;
                        charSequence = sourceRule.getReplaceRegex$reader_pro();
                        n = 0;
                        if (!(charSequence.length() == 0)) break block21;
                    }
                    charSequence = sourceRule.getMode$reader_pro();
                    n = WhenMappings.$EnumSwitchMapping$0[charSequence.ordinal()];
                    switch (n) {
                        case 1: {
                            Object object5 = this.evalJS(sourceRule.getRule$reader_pro(), it);
                            break;
                        }
                        case 2: {
                            Object object5 = this.getAnalyzeByJSonPath(it).getString(sourceRule.getRule$reader_pro());
                            break;
                        }
                        case 3: {
                            Object object5 = this.getAnalyzeByXPath(it).getString(sourceRule.getRule$reader_pro());
                            break;
                        }
                        case 4: {
                            Object object5;
                            if (isUrl) {
                                object5 = this.getAnalyzeByJSoup(it).getString0$reader_pro(sourceRule.getRule$reader_pro());
                                break;
                            }
                            object5 = this.getAnalyzeByJSoup(it).getString$reader_pro(sourceRule.getRule$reader_pro());
                            break;
                        }
                        default: {
                            Object object5 = result2 = sourceRule.getRule$reader_pro();
                        }
                    }
                }
                if (result2 == null) continue;
                charSequence = sourceRule.getReplaceRegex$reader_pro();
                n = 0;
                if (!(charSequence.length() > 0)) continue;
                result2 = this.replaceRegex(String.valueOf(result2), sourceRule);
            }
        }
        if (result2 == null) {
            result2 = "";
        }
        bl3 = false;
        try {
            object2 = Result.Companion;
            boolean bl5 = false;
            String string = Entities.unescape((String)String.valueOf(result2));
            bl2 = false;
            object2 = Result.constructor-impl((Object)string);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            bl = false;
            object2 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
        }
        Object object6 = object2;
        boolean bl6 = false;
        boolean bl7 = false;
        Throwable throwable = Result.exceptionOrNull-impl((Object)object6);
        if (throwable != null) {
            Throwable throwable2 = throwable;
            boolean bl8 = false;
            bl = false;
            Throwable throwable3 = throwable2;
            boolean bl9 = false;
            Throwable it = throwable3;
            boolean bl10 = false;
            this.log(Intrinsics.stringPlus((String)"Entities.unescape() error\n", (Object)it.getLocalizedMessage()));
        }
        bl6 = false;
        boolean bl11 = false;
        Throwable throwable4 = Result.exceptionOrNull-impl((Object)object6);
        if (throwable4 == null) {
            object = object6;
        } else {
            Throwable it = throwable4;
            boolean bl12 = false;
            object = String.valueOf(result2);
        }
        String str = (String)object;
        if (isUrl) {
            Intrinsics.checkNotNullExpressionValue((Object)str, (String)"str");
            return StringsKt.isBlank((CharSequence)str) ? ((object6 = this.baseUrl) == null ? "" : object6) : NetworkUtils.INSTANCE.getAbsoluteURL(this.redirectUrl, str);
        }
        Intrinsics.checkNotNullExpressionValue((Object)str, (String)"str");
        return str;
    }

    public static /* synthetic */ String getString$default(AnalyzeRule analyzeRule, List list2, Object object, boolean bl, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        return analyzeRule.getString(list2, object, bl);
    }

    @Nullable
    public final Object getElement(@NotNull String ruleStr) {
        Intrinsics.checkNotNullParameter((Object)ruleStr, (String)"ruleStr");
        if (TextUtils.isEmpty((CharSequence)ruleStr)) {
            return null;
        }
        Object result2 = null;
        Object content = this.content;
        List ruleList = this.splitSourceRule(ruleStr, true);
        if (content != null) {
            Collection collection = ruleList;
            boolean bl = false;
            if (!collection.isEmpty()) {
                result2 = content;
                for (SourceRule sourceRule : ruleList) {
                    Object object;
                    this.putRule((Map)sourceRule.getPutMap$reader_pro());
                    sourceRule.makeUpRule(result2);
                    Object object2 = result2;
                    if (object2 == null) continue;
                    Object object3 = object2;
                    boolean bl2 = false;
                    boolean bl3 = false;
                    Object it = object3;
                    boolean bl4 = false;
                    Object object4 = sourceRule.getMode$reader_pro();
                    int n = WhenMappings.$EnumSwitchMapping$0[object4.ordinal()];
                    switch (n) {
                        case 5: {
                            String[] stringArray = new String[]{"&&"};
                            object = AnalyzeByRegex.getElement$default((AnalyzeByRegex)AnalyzeByRegex.INSTANCE, (String)String.valueOf(result2), (String[])StringExtensionsKt.splitNotBlank((String)sourceRule.getRule$reader_pro(), (String[])stringArray), (int)0, (int)4, null);
                            break;
                        }
                        case 1: {
                            object = this.evalJS(sourceRule.getRule$reader_pro(), it);
                            break;
                        }
                        case 2: {
                            object = this.getAnalyzeByJSonPath(it).getObject$reader_pro(sourceRule.getRule$reader_pro());
                            break;
                        }
                        case 3: {
                            object = this.getAnalyzeByXPath(it).getElements$reader_pro(sourceRule.getRule$reader_pro());
                            break;
                        }
                        default: {
                            object = this.getAnalyzeByJSoup(it).getElements$reader_pro(sourceRule.getRule$reader_pro());
                        }
                    }
                    result2 = object;
                    object4 = sourceRule.getReplaceRegex$reader_pro();
                    n = 0;
                    if (!(object4.length() > 0)) continue;
                    result2 = this.replaceRegex(String.valueOf(result2), sourceRule);
                }
            }
        }
        return result2;
    }

    @NotNull
    public final List<Object> getElements(@NotNull String ruleStr) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)ruleStr, (String)"ruleStr");
        Object result2 = null;
        Object content = this.content;
        List ruleList = this.splitSourceRule(ruleStr, true);
        if (content != null) {
            object = ruleList;
            boolean bl = false;
            if (!object.isEmpty()) {
                result2 = content;
                object = ruleList.iterator();
                while (object.hasNext()) {
                    Object object2;
                    SourceRule sourceRule = (SourceRule)object.next();
                    this.putRule((Map)sourceRule.getPutMap$reader_pro());
                    Object object3 = result2;
                    if (object3 == null) continue;
                    Object object4 = object3;
                    boolean bl2 = false;
                    boolean bl3 = false;
                    Object it = object4;
                    boolean bl4 = false;
                    Object object5 = sourceRule.getMode$reader_pro();
                    int n = WhenMappings.$EnumSwitchMapping$0[object5.ordinal()];
                    switch (n) {
                        case 5: {
                            String[] stringArray = new String[]{"&&"};
                            object2 = AnalyzeByRegex.getElements$default((AnalyzeByRegex)AnalyzeByRegex.INSTANCE, (String)String.valueOf(result2), (String[])StringExtensionsKt.splitNotBlank((String)sourceRule.getRule$reader_pro(), (String[])stringArray), (int)0, (int)4, null);
                            break;
                        }
                        case 1: {
                            object2 = this.evalJS(sourceRule.getRule$reader_pro(), result2);
                            break;
                        }
                        case 2: {
                            object2 = this.getAnalyzeByJSonPath(it).getList$reader_pro(sourceRule.getRule$reader_pro());
                            break;
                        }
                        case 3: {
                            object2 = this.getAnalyzeByXPath(it).getElements$reader_pro(sourceRule.getRule$reader_pro());
                            break;
                        }
                        default: {
                            object2 = this.getAnalyzeByJSoup(it).getElements$reader_pro(sourceRule.getRule$reader_pro());
                        }
                    }
                    result2 = object2;
                    object5 = sourceRule.getReplaceRegex$reader_pro();
                    n = 0;
                    if (!(object5.length() > 0)) continue;
                    result2 = this.replaceRegex(String.valueOf(result2), sourceRule);
                }
            }
        }
        if ((object = result2) != null) {
            Collection collection = object;
            boolean bl = false;
            boolean bl5 = false;
            Collection it = collection;
            boolean bl6 = false;
            return (List)it;
        }
        return new ArrayList();
    }

    private final void putRule(Map<String, String> map) {
        Object object = map;
        boolean bl = false;
        Iterator<Map.Entry<String, String>> iterator = object.entrySet().iterator();
        while (iterator.hasNext()) {
            Object object2 = object = iterator.next();
            boolean bl2 = false;
            String key = (String)object2.getKey();
            Object object3 = object;
            boolean bl3 = false;
            String value = (String)object3.getValue();
            this.put(key, AnalyzeRule.getString$default((AnalyzeRule)this, (String)value, null, (boolean)false, (int)6, null));
        }
    }

    /*
     * WARNING - void declaration
     */
    private final String splitPutRule(String ruleStr, HashMap<String, String> putMap) {
        String vRuleStr = ruleStr;
        Matcher putMatcher = putPattern.matcher(vRuleStr);
        while (putMatcher.find()) {
            Object object;
            Object object2 = putMatcher.group();
            Intrinsics.checkNotNullExpressionValue((Object)object2, (String)"putMatcher.group()");
            vRuleStr = StringsKt.replace$default((String)vRuleStr, (String)object2, (String)"", (boolean)false, (int)4, null);
            Object object3 = GsonExtensionsKt.getGSON();
            String json$iv = putMatcher.group(1);
            boolean $i$f$fromJsonObject = false;
            boolean bl = false;
            try {
                void $this$fromJsonObject$iv;
                object = Result.Companion;
                boolean bl2 = false;
                boolean $i$f$genericType = false;
                Type type = new splitPutRule$$inlined$fromJsonObject$1().getType();
                Intrinsics.checkNotNullExpressionValue((Object)type, (String)"object : TypeToken<T>() {}.type");
                Object object4 = $this$fromJsonObject$iv.fromJson(json$iv, type);
                if (!(object4 instanceof Map)) {
                    object4 = null;
                }
                Map map = (Map)object4;
                boolean bl3 = false;
                object = Result.constructor-impl((Object)map);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl4 = false;
                object = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
            }
            object3 = object;
            boolean bl5 = false;
            object2 = (Map)(Result.isFailure-impl((Object)object3) ? null : object3);
            if (object2 == null) continue;
            object3 = object2;
            bl5 = false;
            boolean bl6 = false;
            Object it = object3;
            boolean bl7 = false;
            putMap.putAll((Map<String, String>)it);
        }
        return vRuleStr;
    }

    private final String replaceRegex(String result2, SourceRule rule) {
        String string;
        boolean bl;
        Object object;
        Object object2;
        CharSequence charSequence;
        CharSequence charSequence2 = rule.getReplaceRegex$reader_pro();
        boolean bl2 = false;
        if (charSequence2.length() == 0) {
            return result2;
        }
        String vResult = null;
        vResult = result2;
        if (rule.getReplaceFirst$reader_pro()) {
            Object object3;
            Object object4;
            bl2 = false;
            try {
                String string2;
                object4 = Result.Companion;
                boolean $i$a$-runCatching-AnalyzeRule$replaceRegex$422 = false;
                Pattern pattern22 = Pattern.compile(rule.getReplaceRegex$reader_pro());
                Matcher matcher = pattern22.matcher(vResult);
                if (matcher.find()) {
                    String string3 = matcher.group(0);
                    Intrinsics.checkNotNull((Object)string3);
                    charSequence = string3;
                    String string4 = rule.getReplaceRegex$reader_pro();
                    boolean bl3 = false;
                    string4 = new Regex(string4);
                    String string5 = rule.getReplacement$reader_pro();
                    boolean bl4 = false;
                    string2 = string4.replaceFirst(charSequence, string5);
                } else {
                    string2 = "";
                }
                String $i$a$-runCatching-AnalyzeRule$replaceRegex$422 = string2;
                boolean pattern22 = false;
                object4 = Result.constructor-impl((Object)$i$a$-runCatching-AnalyzeRule$replaceRegex$422);
            }
            catch (Throwable $i$a$-runCatching-AnalyzeRule$replaceRegex$422) {
                Result.Companion pattern22 = Result.Companion;
                boolean matcher = false;
                object4 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-AnalyzeRule$replaceRegex$422));
            }
            object = object2 = object4;
            bl = false;
            boolean $i$a$-runCatching-AnalyzeRule$replaceRegex$422 = false;
            Throwable $i$a$-runCatching-AnalyzeRule$replaceRegex$422 = Result.exceptionOrNull-impl((Object)object);
            if ($i$a$-runCatching-AnalyzeRule$replaceRegex$422 == null) {
                object3 = object;
            } else {
                Throwable it22 = $i$a$-runCatching-AnalyzeRule$replaceRegex$422;
                boolean bl5 = false;
                object3 = object2 = StringsKt.replaceFirst$default((String)vResult, (String)rule.getReplaceRegex$reader_pro(), (String)rule.getReplacement$reader_pro(), (boolean)false, (int)4, null);
            }
            string = (String)object3;
        } else {
            Object object5;
            Object object6;
            bl2 = false;
            try {
                object6 = Result.Companion;
                boolean bl6 = false;
                CharSequence it22 = vResult;
                String bl5 = rule.getReplaceRegex$reader_pro();
                boolean bl7 = false;
                bl5 = new Regex(bl5);
                charSequence = rule.getReplacement$reader_pro();
                boolean bl8 = false;
                String string6 = bl5.replace(it22, charSequence);
                boolean it22 = false;
                object6 = Result.constructor-impl((Object)string6);
            }
            catch (Throwable throwable) {
                Result.Companion it22 = Result.Companion;
                boolean bl5 = false;
                object6 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
            }
            object = object2 = object6;
            bl = false;
            boolean bl9 = false;
            Throwable throwable = Result.exceptionOrNull-impl((Object)object);
            if (throwable == null) {
                object5 = object;
            } else {
                Throwable it = throwable;
                boolean bl10 = false;
                object5 = object2 = StringsKt.replace$default((String)vResult, (String)rule.getReplaceRegex$reader_pro(), (String)rule.getReplacement$reader_pro(), (boolean)false, (int)4, null);
            }
            string = (String)object5;
        }
        vResult = string;
        return vResult;
    }

    @NotNull
    public final List<SourceRule> splitSourceRule(@Nullable String ruleStr, boolean allInOne) {
        boolean match$iv$iv;
        char it;
        int index$iv$iv;
        boolean startFound$iv$iv;
        int endIndex$iv$iv;
        int startIndex$iv$iv;
        boolean $i$f$trim;
        CharSequence $this$trim$iv$iv;
        CharSequence $this$trim$iv;
        CharSequence charSequence;
        CharSequence charSequence2 = ruleStr;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence2 == null || charSequence2.length() == 0) {
            return CollectionsKt.emptyList();
        }
        ArrayList<SourceRule> ruleList = new ArrayList<SourceRule>();
        Mode mMode = Mode.Default;
        int start2 = 0;
        if (allInOne && StringsKt.startsWith$default((String)ruleStr, (String)":", (boolean)false, (int)2, null)) {
            mMode = Mode.Regex;
            this.isRegex = true;
            start2 = 1;
        } else if (this.isRegex) {
            mMode = Mode.Regex;
        }
        String tmp = null;
        Matcher jsMatcher = AppPattern.INSTANCE.getJS_PATTERN().matcher(ruleStr);
        while (jsMatcher.find()) {
            String $i$f$trim22;
            if (jsMatcher.start() > start2) {
                charSequence = ruleStr;
                int n = jsMatcher.start();
                boolean bl3 = false;
                String string = charSequence;
                if (string == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string2 = string.substring(start2, n);
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                charSequence = string2;
                boolean $i$f$trim22 = false;
                $this$trim$iv$iv = $this$trim$iv;
                $i$f$trim = false;
                startIndex$iv$iv = 0;
                endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                startFound$iv$iv = false;
                while (startIndex$iv$iv <= endIndex$iv$iv) {
                    index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                    it = $this$trim$iv$iv.charAt(index$iv$iv);
                    boolean bl4 = false;
                    boolean bl5 = match$iv$iv = Intrinsics.compare((int)it, (int)32) <= 0;
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
                $i$f$trim22 = false;
                if ($this$trim$iv.length() > 0) {
                    ruleList.add(new SourceRule(this, tmp, mMode));
                }
            }
            $this$trim$iv = ($i$f$trim22 = jsMatcher.group(2)) == null ? jsMatcher.group(1) : $i$f$trim22;
            Intrinsics.checkNotNullExpressionValue((Object)$this$trim$iv, (String)"jsMatcher.group(2) ?: jsMatcher.group(1)");
            ruleList.add(new SourceRule(this, (String)$this$trim$iv, Mode.Js));
            start2 = jsMatcher.end();
        }
        if (ruleStr.length() > start2) {
            $this$trim$iv = ruleStr;
            boolean $i$f$trim22 = false;
            String string = $this$trim$iv;
            if (string == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string.substring(start2);
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.String).substring(startIndex)");
            $this$trim$iv = string3;
            $i$f$trim22 = false;
            $this$trim$iv$iv = $this$trim$iv;
            $i$f$trim = false;
            startIndex$iv$iv = 0;
            endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
            startFound$iv$iv = false;
            while (startIndex$iv$iv <= endIndex$iv$iv) {
                index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                it = $this$trim$iv$iv.charAt(index$iv$iv);
                boolean bl6 = false;
                boolean bl7 = match$iv$iv = Intrinsics.compare((int)it, (int)32) <= 0;
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
            boolean bl8 = false;
            if (charSequence.length() > 0) {
                ruleList.add(new SourceRule(this, tmp, mMode));
            }
        }
        return ruleList;
    }

    public static /* synthetic */ List splitSourceRule$default(AnalyzeRule analyzeRule, String string, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return analyzeRule.splitSourceRule(string, bl);
    }

    @NotNull
    public final String put(@NotNull String key, @NotNull String value) {
        block6: {
            Unit unit;
            Unit unit2;
            Unit unit3;
            Unit unit4;
            Intrinsics.checkNotNullParameter((Object)key, (String)"key");
            Intrinsics.checkNotNullParameter((Object)value, (String)"value");
            BookChapter bookChapter = this.chapter;
            if (bookChapter == null) {
                unit4 = null;
            } else {
                bookChapter.putVariable(key, value);
                unit4 = unit3 = Unit.INSTANCE;
            }
            if (unit3 == null) {
                bookChapter = this.getBook();
                if (bookChapter == null) {
                    unit2 = null;
                } else {
                    bookChapter.putVariable(key, value);
                    unit2 = Unit.INSTANCE;
                }
            } else {
                unit2 = unit = unit3;
            }
            if (unit != null) break block6;
            this.ruleData.putVariable(key, value);
        }
        return value;
    }

    @NotNull
    public final String get(@NotNull String key) {
        String string;
        BaseBook baseBook;
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        String string2 = key;
        if (Intrinsics.areEqual((Object)string2, (Object)"bookName")) {
            baseBook = this.getBook();
            if (baseBook != null) {
                BaseBook baseBook2 = baseBook;
                boolean bl = false;
                boolean bl2 = false;
                BaseBook it = baseBook2;
                boolean bl3 = false;
                return it.getName();
            }
        } else if (Intrinsics.areEqual((Object)string2, (Object)"title") && (baseBook = this.chapter) != null) {
            BaseBook baseBook3 = baseBook;
            boolean bl = false;
            boolean bl4 = false;
            BaseBook it = baseBook3;
            boolean bl5 = false;
            return it.getTitle();
        }
        baseBook = this.chapter;
        String string3 = string2 = baseBook == null ? null : baseBook.getVariable(key);
        if (string2 == null) {
            String string4;
            BaseBook baseBook4 = this.getBook();
            String string5 = baseBook = baseBook4 == null ? null : baseBook4.getVariable(key);
            string = baseBook == null ? ((baseBook4 = this.ruleData) == null ? "" : ((string4 = baseBook4.getVariable(key)) == null ? "" : string4)) : baseBook;
        } else {
            string = string2;
        }
        return string;
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
        string = "cookie";
        CookieStore cookieStore = new CookieStore(this.getUserNameSpace());
        boolean bl2 = false;
        map.put(string, cookieStore);
        map = (Map)bindings;
        string = "cache";
        cookieStore = new CacheManager(this.getUserNameSpace());
        bl2 = false;
        map.put(string, cookieStore);
        map = (Map)bindings;
        string = "source";
        cookieStore = this.source;
        bl2 = false;
        map.put(string, cookieStore);
        map = (Map)bindings;
        string = "book";
        cookieStore = this.getBook();
        bl2 = false;
        map.put(string, cookieStore);
        map = (Map)bindings;
        string = "result";
        boolean bl3 = false;
        map.put(string, result2);
        map = (Map)bindings;
        string = "baseUrl";
        Object object = this.baseUrl;
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "chapter";
        object = this.chapter;
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "title";
        object = this.chapter;
        object = object == null ? null : object.getTitle();
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "src";
        object = this.content;
        bl2 = false;
        map.put(string, object);
        map = (Map)bindings;
        string = "nextChapterUrl";
        object = this.nextChapterUrl;
        bl2 = false;
        map.put(string, object);
        return AppConst.INSTANCE.getSCRIPT_ENGINE().eval(jsStr, (Bindings)bindings);
    }

    public static /* synthetic */ Object evalJS$default(AnalyzeRule analyzeRule, String string, Object object, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        return analyzeRule.evalJS(string, object);
    }

    @Nullable
    public String ajax(@NotNull String urlStr) {
        Intrinsics.checkNotNullParameter((Object)urlStr, (String)"urlStr");
        return (String)BuildersKt.runBlocking$default(null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)1, null);
    }

    @Nullable
    public final String toNumChapter(@Nullable String s) {
        String string = s;
        if (string == null) {
            return null;
        }
        Matcher matcher = titleNumPattern.matcher(s);
        if (matcher.find()) {
            return matcher.group(1) + StringUtils.INSTANCE.stringToInt(matcher.group(2)) + matcher.group(3);
        }
        return s;
    }

    public final void reGetBook() {
        Book book;
        BaseSource baseSource = this.source;
        BookSource bookSource = baseSource instanceof BookSource ? (BookSource)baseSource : null;
        BaseBook baseBook = this.getBook();
        Book book2 = book = baseBook instanceof Book ? (Book)baseBook : null;
        if (bookSource == null || book == null) {
            return;
        }
        BuildersKt.runBlocking$default(null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)1, null);
    }

    public final void refreshBookUrl() {
        BuildersKt.runBlocking$default(null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)1, null);
    }

    public final void refreshTocUrl() {
        BuildersKt.runBlocking$default(null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)1, null);
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
    public final AnalyzeRule setContent(@Nullable Object content) {
        return AnalyzeRule.setContent$default((AnalyzeRule)this, (Object)content, null, (int)2, null);
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@Nullable String rule, @Nullable Object mContent) {
        return AnalyzeRule.getStringList$default((AnalyzeRule)this, (String)rule, (Object)mContent, (boolean)false, (int)4, null);
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@Nullable String rule) {
        return AnalyzeRule.getStringList$default((AnalyzeRule)this, (String)rule, null, (boolean)false, (int)6, null);
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@NotNull List<SourceRule> ruleList, @Nullable Object mContent) {
        Intrinsics.checkNotNullParameter(ruleList, (String)"ruleList");
        return AnalyzeRule.getStringList$default((AnalyzeRule)this, ruleList, (Object)mContent, (boolean)false, (int)4, null);
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@NotNull List<SourceRule> ruleList) {
        Intrinsics.checkNotNullParameter(ruleList, (String)"ruleList");
        return AnalyzeRule.getStringList$default((AnalyzeRule)this, ruleList, null, (boolean)false, (int)6, null);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@Nullable String ruleStr, @Nullable Object mContent) {
        return AnalyzeRule.getString$default((AnalyzeRule)this, (String)ruleStr, (Object)mContent, (boolean)false, (int)4, null);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@Nullable String ruleStr) {
        return AnalyzeRule.getString$default((AnalyzeRule)this, (String)ruleStr, null, (boolean)false, (int)6, null);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@NotNull List<SourceRule> ruleList, @Nullable Object mContent) {
        Intrinsics.checkNotNullParameter(ruleList, (String)"ruleList");
        return AnalyzeRule.getString$default((AnalyzeRule)this, ruleList, (Object)mContent, (boolean)false, (int)4, null);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@NotNull List<SourceRule> ruleList) {
        Intrinsics.checkNotNullParameter(ruleList, (String)"ruleList");
        return AnalyzeRule.getString$default((AnalyzeRule)this, ruleList, null, (boolean)false, (int)6, null);
    }

    public static final /* synthetic */ boolean access$isJSON$p(AnalyzeRule $this) {
        return $this.isJSON;
    }

    public static final /* synthetic */ String access$splitPutRule(AnalyzeRule $this, String ruleStr, HashMap putMap) {
        return $this.splitPutRule(ruleStr, putMap);
    }

    public static final /* synthetic */ Pattern access$getEvalPattern$cp() {
        return evalPattern;
    }

    public static final /* synthetic */ Pattern access$getRegexPattern$cp() {
        return regexPattern;
    }

    public static final /* synthetic */ BaseSource access$getSource$p(AnalyzeRule $this) {
        return $this.source;
    }
}

