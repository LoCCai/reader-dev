/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jayway.jsonpath.DocumentContext
 *  com.jayway.jsonpath.JsonPath
 *  com.jayway.jsonpath.Predicate
 *  com.jayway.jsonpath.ReadContext
 *  io.legado.app.data.entities.BookSource
 *  io.legado.app.data.entities.rule.BookInfoRule
 *  io.legado.app.data.entities.rule.ContentRule
 *  io.legado.app.data.entities.rule.ExploreRule
 *  io.legado.app.data.entities.rule.SearchRule
 *  io.legado.app.data.entities.rule.TocRule
 *  io.legado.app.exception.NoStackTraceException
 *  io.legado.app.help.SourceAnalyzer
 *  io.legado.app.help.SourceAnalyzer$BookSourceAny
 *  io.legado.app.help.SourceAnalyzer$jsonToBookSource-IoAF18A$$inlined$fromJsonObject$1
 *  io.legado.app.help.SourceAnalyzer$jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$1
 *  io.legado.app.help.SourceAnalyzer$jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$2
 *  io.legado.app.help.SourceAnalyzer$jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$4
 *  io.legado.app.help.SourceAnalyzer$jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$5
 *  io.legado.app.help.SourceAnalyzer$jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$6
 *  io.legado.app.help.SourceAnalyzer$jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$7
 *  io.legado.app.help.SourceAnalyzer$jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$8
 *  io.legado.app.model.Debug
 *  io.legado.app.model.DebugLog
 *  io.legado.app.model.DebugLog$DefaultImpls
 *  io.legado.app.utils.GsonExtensionsKt
 *  io.legado.app.utils.JsonExtensionsKt
 *  io.legado.app.utils.StringExtensionsKt
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Result
 *  kotlin.Result$Companion
 *  kotlin.ResultKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.help;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.ReadContext;
import io.legado.app.data.entities.BookSource;
import io.legado.app.data.entities.rule.BookInfoRule;
import io.legado.app.data.entities.rule.ContentRule;
import io.legado.app.data.entities.rule.ExploreRule;
import io.legado.app.data.entities.rule.SearchRule;
import io.legado.app.data.entities.rule.TocRule;
import io.legado.app.exception.NoStackTraceException;
import io.legado.app.help.SourceAnalyzer;
import io.legado.app.help.SourceAnalyzer$jsonToBookSource-IoAF18A$;
import io.legado.app.help.SourceAnalyzer$jsonToBookSource_IoAF18A$lambda-10$;
import io.legado.app.model.Debug;
import io.legado.app.model.DebugLog;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.JsonExtensionsKt;
import io.legado.app.utils.StringExtensionsKt;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u001cB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\f\u0010\rJ*\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u000f0\b2\u0006\u0010\u0010\u001a\u00020\u0011\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J*\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u000f0\b2\u0006\u0010\n\u001a\u00020\u000b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0012\u0010\rJ\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0015\u001a\u0004\u0018\u00010\u000bH\u0002J\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u000bH\u0002J\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u000bH\u0002J\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u000b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u000bH\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001d"}, d2={"Lio/legado/app/help/SourceAnalyzer;", "", "()V", "headerPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "jsPattern", "jsonToBookSource", "Lkotlin/Result;", "Lio/legado/app/data/entities/BookSource;", "json", "", "jsonToBookSource-IoAF18A", "(Ljava/lang/String;)Ljava/lang/Object;", "jsonToBookSources", "", "inputStream", "Ljava/io/InputStream;", "jsonToBookSources-IoAF18A", "(Ljava/io/InputStream;)Ljava/lang/Object;", "toNewRule", "oldRule", "toNewUrl", "oldUrl", "toNewUrls", "oldUrls", "uaToHeader", "ua", "BookSourceAny", "reader-pro"})
public final class SourceAnalyzer {
    @NotNull
    public static final SourceAnalyzer INSTANCE = new SourceAnalyzer();
    private static final Pattern headerPattern = Pattern.compile("@Header:\\{.+?\\}", 2);
    private static final Pattern jsPattern = Pattern.compile("\\{\\{.+?\\}\\}", 2);

    private SourceAnalyzer() {
    }

    @NotNull
    public final Object jsonToBookSources-IoAF18A(@NotNull String json) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        boolean bl = false;
        try {
            object = Result.Companion;
            boolean bl2 = false;
            boolean bl3 = false;
            List bookSources = new ArrayList();
            if (StringExtensionsKt.isJsonArray((String)json)) {
                Iterator iterator = JsonExtensionsKt.getJsonPath().parse(json).read("$", new Predicate[0]);
                Intrinsics.checkNotNullExpressionValue((Object)iterator, (String)"jsonPath.parse(json).read(\"$\")");
                List items = (List)((Object)iterator);
                for (Map item : items) {
                    DocumentContext jsonItem = JsonExtensionsKt.getJsonPath().parse((Object)item);
                    Object object2 = jsonItem.jsonString();
                    Intrinsics.checkNotNullExpressionValue((Object)object2, (String)"jsonItem.jsonString()");
                    object2 = INSTANCE.jsonToBookSource-IoAF18A((String)object2);
                    boolean bl4 = false;
                    ResultKt.throwOnFailure((Object)object2);
                    bl4 = false;
                    boolean bl5 = false;
                    BookSource it = (BookSource)object2;
                    boolean bl6 = false;
                    bookSources.add(it);
                }
            } else if (StringExtensionsKt.isJsonObject((String)json)) {
                Object object3 = INSTANCE.jsonToBookSource-IoAF18A(json);
                boolean bl7 = false;
                ResultKt.throwOnFailure((Object)object3);
                bl7 = false;
                boolean bl8 = false;
                BookSource it = (BookSource)object3;
                boolean bl9 = false;
                bookSources.add(it);
            } else {
                throw new NoStackTraceException("\u683c\u5f0f\u4e0d\u5bf9");
            }
            List list2 = bookSources;
            boolean bl10 = false;
            object = Result.constructor-impl((Object)list2);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl11 = false;
            object = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
        }
        return object;
    }

    @NotNull
    public final Object jsonToBookSources-IoAF18A(@NotNull InputStream inputStream) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)inputStream, (String)"inputStream");
        boolean bl = false;
        try {
            Object object2;
            object = Result.Companion;
            boolean bl2 = false;
            boolean bl3 = false;
            List bookSources = new ArrayList();
            bl3 = false;
            try {
                object2 = Result.Companion;
                boolean bl4 = false;
                Iterator iterator = JsonExtensionsKt.getJsonPath().parse(inputStream).read("$", new Predicate[0]);
                Intrinsics.checkNotNullExpressionValue((Object)iterator, (String)"jsonPath.parse(inputStream).read(\"$\")");
                List items = (List)((Object)iterator);
                for (Map item : items) {
                    DocumentContext jsonItem = JsonExtensionsKt.getJsonPath().parse((Object)item);
                    Object object3 = jsonItem.jsonString();
                    Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"jsonItem.jsonString()");
                    object3 = INSTANCE.jsonToBookSource-IoAF18A((String)object3);
                    boolean bl5 = false;
                    ResultKt.throwOnFailure((Object)object3);
                    bl5 = false;
                    boolean bl6 = false;
                    BookSource it = (BookSource)object3;
                    boolean bl7 = false;
                    bookSources.add(it);
                }
                Unit unit = Unit.INSTANCE;
                boolean bl8 = false;
                object2 = Result.constructor-impl((Object)unit);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl9 = false;
                object2 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
            }
            Object object4 = object2;
            boolean bl10 = false;
            boolean bl11 = false;
            Throwable throwable = Result.exceptionOrNull-impl((Object)object4);
            if (throwable != null) {
                Throwable throwable2 = throwable;
                boolean bl12 = false;
                boolean bl13 = false;
                Throwable throwable3 = throwable2;
                boolean bl14 = false;
                Throwable it = throwable3;
                boolean bl15 = false;
                Object object5 = JsonExtensionsKt.getJsonPath().parse(inputStream).read("$", new Predicate[0]);
                Intrinsics.checkNotNullExpressionValue((Object)object5, (String)"jsonPath.parse(inputStream).read(\"$\")");
                Map item = (Map)object5;
                DocumentContext jsonItem = JsonExtensionsKt.getJsonPath().parse((Object)item);
                Object object6 = jsonItem.jsonString();
                Intrinsics.checkNotNullExpressionValue((Object)object6, (String)"jsonItem.jsonString()");
                object6 = INSTANCE.jsonToBookSource-IoAF18A((String)object6);
                boolean bl16 = false;
                ResultKt.throwOnFailure((Object)object6);
                bl16 = false;
                boolean bl17 = false;
                BookSource it2 = (BookSource)object6;
                boolean bl18 = false;
                bookSources.add(it2);
            }
            List list2 = bookSources;
            boolean bl19 = false;
            object = Result.constructor-impl((Object)list2);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl20 = false;
            object = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
        }
        return object;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @NotNull
    public final Object jsonToBookSource-IoAF18A(@NotNull String json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        source = new BookSource(null, null, null, 0, null, 0, false, false, null, null, null, null, null, null, null, null, 0L, 0L, 0, null, null, null, null, null, null, null, 0x3FFFFFF, null);
        var4_3 = GsonExtensionsKt.getGSON();
        var5_5 = json;
        var6_8 = false;
        var5_5 = StringsKt.trim((CharSequence)var5_5).toString();
        $i$f$fromJsonObject = false;
        var7_13 = false;
        try {
            var8_17 /* !! */  = Result.Companion;
            $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
            $i$f$genericType = false;
            var11_48 = new jsonToBookSource-IoAF18A$$inlined$fromJsonObject$1().getType();
            Intrinsics.checkNotNullExpressionValue((Object)var11_48, (String)"object : TypeToken<T>() {}.type");
            v0 = $this$fromJsonObject$iv.fromJson((String)json$iv, (Type)var11_48);
            if (!(v0 instanceof BookSourceAny)) {
                v0 = null;
            }
            $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (BookSourceAny)v0;
            $i$f$genericType = false;
            var8_17 /* !! */  = Result.constructor-impl((Object)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
        }
        catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
            $i$f$genericType = Result.Companion;
            var11_49 = false;
            var8_17 /* !! */  = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
        }
        var4_3 = var8_17 /* !! */ ;
        var5_6 = false;
        $i$f$fromJsonObject = false;
        v1 = Result.exceptionOrNull-impl((Object)var4_3);
        if (v1 != null) {
            $i$f$fromJsonObject = v1;
            var7_13 = false;
            var8_18 = false;
            $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = $i$f$fromJsonObject;
            $i$f$genericType = false;
            it = $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv;
            $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = false;
            DebugLog.DefaultImpls.log$default((DebugLog)((DebugLog)Debug.INSTANCE), (String)"\u8f6c\u5316\u4e66\u6e90\u51fa\u9519", (String)it.getLocalizedMessage(), (boolean)false, (int)4, null);
        }
        var5_6 = false;
        sourceAny = (BookSourceAny)(Result.isFailure-impl((Object)var4_3) != false ? null : var4_3);
        var4_4 = false;
        try {
            block86: {
                block84: {
                    var5_7 /* !! */  = Result.Companion;
                    $i$a$-runCatching-SourceAnalyzer$jsonToBookSource$1 = false;
                    var7_14 = sourceAny;
                    if ((var7_14 == null ? null : var7_14.getRuleToc()) != null) break block84;
                    var7_14 = source;
                    var8_19 = false;
                    $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9 = var7_14;
                    $i$a$-apply-SourceAnalyzer$jsonToBookSource$1$1 = false;
                    $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = json;
                    var13_78 = false;
                    jsonItem = JsonExtensionsKt.getJsonPath().parse(StringsKt.trim((CharSequence)((CharSequence)$i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1)).toString());
                    Intrinsics.checkNotNullExpressionValue((Object)jsonItem, (String)"jsonItem");
                    $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"bookSourceUrl");
                    if ($i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 == null) {
                        throw new NoStackTraceException("\u683c\u5f0f\u4e0d\u5bf9");
                    }
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookSourceUrl((String)$i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1);
                    $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"bookSourceName");
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookSourceName((String)($i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 == null ? "" : $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookSourceGroup(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"bookSourceGroup"));
                    $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"bookSourceComment");
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookSourceComment((String)($i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 == null ? "" : $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookUrlPattern(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleBookUrlPattern"));
                    $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = JsonExtensionsKt.readInt((ReadContext)((ReadContext)jsonItem), (String)"serialNumber");
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setCustomOrder($i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 == null ? 0 : $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1.intValue());
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setHeader(SourceAnalyzer.INSTANCE.uaToHeader(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"httpUserAgent")));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setSearchUrl(SourceAnalyzer.INSTANCE.toNewUrl(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleSearchUrl")));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setExploreUrl(SourceAnalyzer.INSTANCE.toNewUrls(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleFindUrl")));
                    sourceType = JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"bookSourceType");
                    var13_79 = sourceType;
                    if (var13_79 == null) ** GOTO lbl-1000
                    tmp = -1;
                    switch (var13_79.hashCode()) {
                        case 49: {
                            if (var13_79.equals("1")) {
                                tmp = 1;
                            }
                            break;
                        }
                        case 100313435: {
                            if (var13_79.equals("image")) {
                                tmp = 2;
                            }
                            break;
                        }
                        case 50: {
                            if (var13_79.equals("2")) {
                                tmp = 3;
                            }
                            break;
                        }
                        case 69775675: {
                            if (var13_79.equals("IMAGE")) {
                                tmp = 4;
                            }
                            break;
                        }
                        case 3143036: {
                            if (var13_79.equals("file")) {
                                tmp = 5;
                            }
                            break;
                        }
                        case 51: {
                            if (var13_79.equals("3")) {
                                tmp = 6;
                            }
                            break;
                        }
                        case 93166550: {
                            if (var13_79.equals("audio")) {
                                tmp = 7;
                            }
                            break;
                        }
                        case 2157948: {
                            if (var13_79.equals("FILE")) {
                                tmp = 8;
                            }
                            break;
                        }
                        case 62628790: {
                            if (var13_79.equals("AUDIO")) {
                                tmp = 9;
                            }
                            break;
                        }
                    }
                    switch (tmp) {
                        case 9: {
                            v2 = 1;
                            break;
                        }
                        case 7: {
                            v2 = 1;
                            break;
                        }
                        case 1: {
                            v2 = 1;
                            break;
                        }
                        case 4: {
                            v2 = 2;
                            break;
                        }
                        case 2: {
                            v2 = 2;
                            break;
                        }
                        case 3: {
                            v2 = 2;
                            break;
                        }
                        case 8: {
                            v2 = 3;
                            break;
                        }
                        case 5: {
                            v2 = 3;
                            break;
                        }
                        case 6: {
                            v2 = 3;
                            break;
                        }
                        default: lbl-1000:
                        // 2 sources

                        {
                            v2 = 0;
                        }
                    }
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookSourceType(v2);
                    var13_79 = JsonExtensionsKt.readBool((ReadContext)((ReadContext)jsonItem), (String)"enable");
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setEnabled(var13_79 == null ? true : var13_79.booleanValue());
                    var13_79 = $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.getExploreUrl();
                    var15_121 = false;
                    var16_123 = false;
                    if (var13_79 == null || StringsKt.isBlank((CharSequence)var13_79) != false) {
                        $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setEnabledExplore(false);
                    }
                    var13_79 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleSearchList"));
                    var15_122 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleSearchName"));
                    var16_124 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleSearchAuthor"));
                    var17_127 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleSearchIntroduce"));
                    var18_129 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleSearchKind"));
                    var19_130 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleSearchNoteUrl"));
                    var20_131 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleSearchCoverUrl"));
                    var21_132 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleSearchLastChapter"));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setRuleSearch(new SearchRule((String)var13_79, var15_122, var16_124, var17_127, var18_129, var21_132, null, var19_130, var20_131, null, 576, null));
                    var13_79 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleFindList"));
                    var15_122 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleFindName"));
                    var16_124 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleFindAuthor"));
                    var17_127 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleFindIntroduce"));
                    var18_129 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleFindKind"));
                    var19_130 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleFindNoteUrl"));
                    var20_131 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleFindCoverUrl"));
                    var21_132 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleFindLastChapter"));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setRuleExplore(new ExploreRule((String)var13_79, var15_122, var16_124, var17_127, var18_129, var21_132, null, var19_130, var20_131, null, 576, null));
                    var13_79 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleBookInfoInit"));
                    var15_122 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleBookName"));
                    var16_124 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleBookAuthor"));
                    var17_127 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleIntroduce"));
                    var18_129 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleBookKind"));
                    var19_130 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleCoverUrl"));
                    var20_131 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleBookLastChapter"));
                    var21_132 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleChapterUrl"));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setRuleBookInfo(new BookInfoRule((String)var13_79, var15_122, var16_124, var17_127, var18_129, var20_131, null, var19_130, var21_132, null, null, 1600, null));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setRuleToc(new TocRule(null, SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleChapterList")), SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleChapterName")), SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleContentUrl")), null, null, null, SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleChapterUrlNext")), 113, null));
                    var15_122 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleBookContent"));
                    v3 = content = var15_122 == null ? "" : var15_122;
                    if (StringsKt.startsWith$default((String)content, (String)"$", (boolean)false, (int)2, null) && !StringsKt.startsWith$default((String)content, (String)"$.", (boolean)false, (int)2, null)) {
                        var15_122 = content;
                        var16_125 = 1;
                        var17_128 = false;
                        v4 = var15_122.substring(var16_125);
                        Intrinsics.checkNotNullExpressionValue((Object)v4, (String)"(this as java.lang.String).substring(startIndex)");
                        content = v4;
                    }
                    var15_122 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleBookContentReplace"));
                    var16_126 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString((ReadContext)((ReadContext)jsonItem), (String)"ruleContentUrlNext"));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setRuleContent(new ContentRule(content, var16_126, null, null, var15_122, null, 44, null));
                    break block86;
                }
                source.setBookSourceUrl(sourceAny.getBookSourceUrl());
                source.setBookSourceName(sourceAny.getBookSourceName());
                source.setBookSourceGroup(sourceAny.getBookSourceGroup());
                source.setBookSourceType(sourceAny.getBookSourceType());
                source.setBookUrlPattern(sourceAny.getBookUrlPattern());
                source.setCustomOrder(sourceAny.getCustomOrder());
                source.setEnabled(sourceAny.getEnabled());
                source.setEnabledExplore(sourceAny.getEnabledExplore());
                source.setEnabledCookieJar(Boolean.valueOf(sourceAny.getEnabledCookieJar()));
                source.setConcurrentRate(sourceAny.getConcurrentRate());
                source.setHeader(sourceAny.getHeader());
                var7_14 = sourceAny.getLoginUrl();
                if (var7_14 == null) {
                    v5 = null;
                } else if (var7_14 instanceof String) {
                    v5 = String.valueOf(sourceAny.getLoginUrl());
                } else {
                    var8_20 = JsonPath.parse((Object)sourceAny.getLoginUrl());
                    Intrinsics.checkNotNullExpressionValue((Object)var8_20, (String)"parse(sourceAny.loginUrl)");
                    v5 = JsonExtensionsKt.readString((ReadContext)((ReadContext)var8_20), (String)"url");
                }
                source.setLoginUrl(v5);
                source.setLoginCheckJs(sourceAny.getLoginCheckJs());
                source.setBookSourceComment(sourceAny.getBookSourceComment());
                source.setLastUpdateTime(sourceAny.getLastUpdateTime());
                source.setRespondTime(sourceAny.getRespondTime());
                source.setWeight(sourceAny.getWeight());
                source.setExploreUrl(sourceAny.getExploreUrl());
                v6 = source;
                if (sourceAny.getRuleExplore() instanceof String) {
                    var7_14 = GsonExtensionsKt.getGSON();
                    var8_21 = String.valueOf(sourceAny.getRuleExplore());
                    var22_133 = v6;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_101 = new jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$1().getType();
                        Intrinsics.checkNotNullExpressionValue((Object)var14_101, (String)"object : TypeToken<T>() {}.type");
                        v7 = $this$fromJsonObject$iv.fromJson((String)json$iv, var14_101);
                        if (!(v7 instanceof ExploreRule)) {
                            v7 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (ExploreRule)v7;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl((Object)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_102 = false;
                        var11_48 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v8 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v9 = (ExploreRule)(Result.isFailure-impl((Object)$this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                } else {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = GsonExtensionsKt.getGSON().toJson(sourceAny.getRuleExplore());
                    var22_133 = v6;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_103 = new jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$2().getType();
                        Intrinsics.checkNotNullExpressionValue((Object)var14_103, (String)"object : TypeToken<T>() {}.type");
                        v10 = $this$fromJsonObject$iv.fromJson(json$iv, var14_103);
                        if (!(v10 instanceof ExploreRule)) {
                            v10 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (ExploreRule)v10;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl((Object)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_104 = false;
                        var11_48 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v8 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v9 = (ExploreRule)(Result.isFailure-impl((Object)$this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                }
                v8.setRuleExplore(v9);
                source.setSearchUrl(sourceAny.getSearchUrl());
                v11 = source;
                if (sourceAny.getRuleSearch() instanceof String) {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = String.valueOf(sourceAny.getRuleSearch());
                    var22_133 = v11;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_105 = new /* Unavailable Anonymous Inner Class!! */.getType();
                        Intrinsics.checkNotNullExpressionValue((Object)var14_105, (String)"object : TypeToken<T>() {}.type");
                        v12 = $this$fromJsonObject$iv.fromJson(json$iv, var14_105);
                        if (!(v12 instanceof SearchRule)) {
                            v12 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (SearchRule)v12;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl((Object)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_106 = false;
                        var11_48 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v13 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v14 = (SearchRule)(Result.isFailure-impl((Object)$this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                } else {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = GsonExtensionsKt.getGSON().toJson(sourceAny.getRuleSearch());
                    var22_133 = v11;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_107 = new jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$4().getType();
                        Intrinsics.checkNotNullExpressionValue((Object)var14_107, (String)"object : TypeToken<T>() {}.type");
                        v15 = $this$fromJsonObject$iv.fromJson(json$iv, var14_107);
                        if (!(v15 instanceof SearchRule)) {
                            v15 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (SearchRule)v15;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl((Object)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_108 = false;
                        var11_48 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v13 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v14 = (SearchRule)(Result.isFailure-impl((Object)$this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                }
                v13.setRuleSearch(v14);
                v16 = source;
                if (sourceAny.getRuleBookInfo() instanceof String) {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = String.valueOf(sourceAny.getRuleBookInfo());
                    var22_133 = v16;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_109 = new jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$5().getType();
                        Intrinsics.checkNotNullExpressionValue((Object)var14_109, (String)"object : TypeToken<T>() {}.type");
                        v17 = $this$fromJsonObject$iv.fromJson(json$iv, var14_109);
                        if (!(v17 instanceof BookInfoRule)) {
                            v17 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (BookInfoRule)v17;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl((Object)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_110 = false;
                        var11_48 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v18 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v19 = (BookInfoRule)(Result.isFailure-impl((Object)$this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                } else {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = GsonExtensionsKt.getGSON().toJson(sourceAny.getRuleBookInfo());
                    var22_133 = v16;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_111 = new jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$6().getType();
                        Intrinsics.checkNotNullExpressionValue((Object)var14_111, (String)"object : TypeToken<T>() {}.type");
                        v20 = $this$fromJsonObject$iv.fromJson(json$iv, var14_111);
                        if (!(v20 instanceof BookInfoRule)) {
                            v20 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (BookInfoRule)v20;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl((Object)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_112 = false;
                        var11_48 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v18 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v19 = (BookInfoRule)(Result.isFailure-impl((Object)$this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                }
                v18.setRuleBookInfo(v19);
                v21 = source;
                if (sourceAny.getRuleToc() instanceof String) {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = String.valueOf(sourceAny.getRuleToc());
                    var22_133 = v21;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_113 = new jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$7().getType();
                        Intrinsics.checkNotNullExpressionValue((Object)var14_113, (String)"object : TypeToken<T>() {}.type");
                        v22 = $this$fromJsonObject$iv.fromJson(json$iv, var14_113);
                        if (!(v22 instanceof TocRule)) {
                            v22 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (TocRule)v22;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl((Object)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_114 = false;
                        var11_48 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v23 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v24 = (TocRule)(Result.isFailure-impl((Object)$this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                } else {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = GsonExtensionsKt.getGSON().toJson(sourceAny.getRuleToc());
                    var22_133 = v21;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_115 = new jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$8().getType();
                        Intrinsics.checkNotNullExpressionValue((Object)var14_115, (String)"object : TypeToken<T>() {}.type");
                        v25 = $this$fromJsonObject$iv.fromJson(json$iv, var14_115);
                        if (!(v25 instanceof TocRule)) {
                            v25 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (TocRule)v25;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl((Object)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_116 = false;
                        var11_48 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v23 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v24 = (TocRule)(Result.isFailure-impl((Object)$this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                }
                v23.setRuleToc(v24);
                v26 = source;
                if (sourceAny.getRuleContent() instanceof String) {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = String.valueOf(sourceAny.getRuleContent());
                    var22_133 = v26;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_117 = new /* Unavailable Anonymous Inner Class!! */.getType();
                        Intrinsics.checkNotNullExpressionValue((Object)var14_117, (String)"object : TypeToken<T>() {}.type");
                        v27 = $this$fromJsonObject$iv.fromJson(json$iv, var14_117);
                        if (!(v27 instanceof ContentRule)) {
                            v27 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (ContentRule)v27;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl((Object)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_118 = false;
                        var11_48 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v28 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v29 = (ContentRule)(Result.isFailure-impl((Object)$this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                } else {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = GsonExtensionsKt.getGSON().toJson(sourceAny.getRuleContent());
                    var22_133 = v26;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_119 = new /* Unavailable Anonymous Inner Class!! */.getType();
                        Intrinsics.checkNotNullExpressionValue((Object)var14_119, (String)"object : TypeToken<T>() {}.type");
                        v30 = $this$fromJsonObject$iv.fromJson(json$iv, var14_119);
                        if (!(v30 instanceof ContentRule)) {
                            v30 = null;
                        }
                        var12_75 = (ContentRule)v30;
                        var13_98 = false;
                        var11_48 = Result.constructor-impl((Object)var12_75);
                    }
                    catch (Throwable var12_76) {
                        var13_99 = Result.Companion;
                        var14_120 = false;
                        var11_48 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)var12_76));
                    }
                    var23_134 = var11_48;
                    v28 = var22_133;
                    var7_14 = var23_134;
                    var8_36 = false;
                    v29 = (ContentRule)(Result.isFailure-impl((Object)var7_14) != false ? null : var7_14);
                }
                v28.setRuleContent(v29);
            }
            var6_11 = source;
            var7_15 = false;
            var5_7 /* !! */  = Result.constructor-impl((Object)var6_11);
        }
        catch (Throwable var6_12) {
            var7_16 = Result.Companion;
            var8_37 = false;
            var5_7 /* !! */  = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)var6_12));
        }
        return var5_7 /* !! */ ;
    }

    private final String toNewRule(String oldRule) {
        int n;
        int n2;
        String string;
        CharSequence charSequence = oldRule;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || StringsKt.isBlank((CharSequence)charSequence)) {
            return null;
        }
        String newRule = oldRule;
        boolean reverse = false;
        boolean allinone = false;
        if (StringsKt.startsWith$default((String)oldRule, (String)"-", (boolean)false, (int)2, null)) {
            reverse = true;
            string = oldRule;
            n2 = 1;
            n = 0;
            String string2 = string;
            if (string2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string2.substring(n2);
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.String).substring(startIndex)");
            newRule = string3;
        }
        if (StringsKt.startsWith$default((String)newRule, (String)"+", (boolean)false, (int)2, null)) {
            allinone = true;
            string = newRule;
            n2 = 1;
            n = 0;
            String string4 = string;
            if (string4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string5 = string4.substring(n2);
            Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"(this as java.lang.String).substring(startIndex)");
            newRule = string5;
        }
        if (!(StringsKt.startsWith((String)newRule, (String)"@CSS:", (boolean)true) || StringsKt.startsWith((String)newRule, (String)"@XPath:", (boolean)true) || StringsKt.startsWith$default((String)newRule, (String)"//", (boolean)false, (int)2, null) || StringsKt.startsWith$default((String)newRule, (String)"##", (boolean)false, (int)2, null) || StringsKt.startsWith$default((String)newRule, (String)":", (boolean)false, (int)2, null) || StringsKt.contains((CharSequence)newRule, (CharSequence)"@js:", (boolean)true) || StringsKt.contains((CharSequence)newRule, (CharSequence)"<js>", (boolean)true))) {
            if (StringsKt.contains$default((CharSequence)newRule, (CharSequence)"#", (boolean)false, (int)2, null) && !StringsKt.contains$default((CharSequence)newRule, (CharSequence)"##", (boolean)false, (int)2, null)) {
                newRule = StringsKt.replace$default((String)oldRule, (String)"#", (String)"##", (boolean)false, (int)4, null);
            }
            if (StringsKt.contains$default((CharSequence)newRule, (CharSequence)"|", (boolean)false, (int)2, null) && !StringsKt.contains$default((CharSequence)newRule, (CharSequence)"||", (boolean)false, (int)2, null)) {
                if (StringsKt.contains$default((CharSequence)newRule, (CharSequence)"##", (boolean)false, (int)2, null)) {
                    String[] stringArray = new String[]{"##"};
                    List list2 = StringsKt.split$default((CharSequence)newRule, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
                    if (StringsKt.contains$default((CharSequence)((CharSequence)list2.get(0)), (CharSequence)"|", (boolean)false, (int)2, null)) {
                        newRule = StringsKt.replace$default((String)((String)list2.get(0)), (String)"|", (String)"||", (boolean)false, (int)4, null);
                        int n3 = 1;
                        n = list2.size();
                        if (n3 < n) {
                            do {
                                int i = n3++;
                                newRule = newRule + "##" + (String)list2.get(i);
                            } while (n3 < n);
                        }
                    }
                } else {
                    newRule = StringsKt.replace$default((String)newRule, (String)"|", (String)"||", (boolean)false, (int)4, null);
                }
            }
            if (StringsKt.contains$default((CharSequence)newRule, (CharSequence)"&", (boolean)false, (int)2, null) && !StringsKt.contains$default((CharSequence)newRule, (CharSequence)"&&", (boolean)false, (int)2, null) && !StringsKt.contains$default((CharSequence)newRule, (CharSequence)"http", (boolean)false, (int)2, null) && !StringsKt.startsWith$default((String)newRule, (String)"/", (boolean)false, (int)2, null)) {
                newRule = StringsKt.replace$default((String)newRule, (String)"&", (String)"&&", (boolean)false, (int)4, null);
            }
        }
        if (allinone) {
            newRule = Intrinsics.stringPlus((String)"+", (Object)newRule);
        }
        if (reverse) {
            newRule = Intrinsics.stringPlus((String)"-", (Object)newRule);
        }
        return newRule;
    }

    /*
     * WARNING - void declaration
     */
    private final String toNewUrls(String oldUrls) {
        void $this$mapTo$iv$iv;
        CharSequence charSequence = oldUrls;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || StringsKt.isBlank((CharSequence)charSequence)) {
            return null;
        }
        if (StringsKt.startsWith$default((String)oldUrls, (String)"@js:", (boolean)false, (int)2, null) || StringsKt.startsWith$default((String)oldUrls, (String)"<js>", (boolean)false, (int)2, null)) {
            return oldUrls;
        }
        if (!StringsKt.contains$default((CharSequence)oldUrls, (CharSequence)"\n", (boolean)false, (int)2, null) && !StringsKt.contains$default((CharSequence)oldUrls, (CharSequence)"&&", (boolean)false, (int)2, null)) {
            return this.toNewUrl(oldUrls);
        }
        CharSequence charSequence2 = oldUrls;
        String string = "(&&|\r?\n)+";
        int n = 0;
        string = new Regex(string);
        n = 0;
        boolean bl3 = false;
        List urls = string.split(charSequence2, n);
        Iterable $this$map$iv = urls;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            String string2;
            void it;
            String string3 = (String)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl4 = false;
            String string4 = INSTANCE.toNewUrl((String)it);
            if (string4 == null) {
                string2 = null;
            } else {
                CharSequence charSequence3 = string4;
                String string5 = "\n\\s*";
                boolean bl5 = false;
                string5 = new Regex(string5);
                String string6 = "";
                boolean bl6 = false;
                string2 = string5.replace(charSequence3, string6);
            }
            String string7 = string2;
            collection.add(string7);
        }
        return CollectionsKt.joinToString$default((Iterable)((List)destination$iv$iv), (CharSequence)"\n", null, null, (int)0, null, null, (int)62, null);
    }

    private final String toNewUrl(String oldUrl) {
        boolean bl;
        int n;
        Object object;
        String string;
        Object object2;
        CharSequence charSequence = oldUrl;
        boolean bl2 = false;
        boolean bl3 = false;
        if (charSequence == null || StringsKt.isBlank((CharSequence)charSequence)) {
            return null;
        }
        String url2 = oldUrl;
        if (StringsKt.startsWith((String)oldUrl, (String)"<js>", (boolean)true)) {
            url2 = StringsKt.replace$default((String)StringsKt.replace$default((String)url2, (String)"=searchKey", (String)"={{key}}", (boolean)false, (int)4, null), (String)"=searchPage", (String)"={{page}}", (boolean)false, (int)4, null);
            return url2;
        }
        HashMap map = new HashMap();
        Matcher mather = headerPattern.matcher(url2);
        if (mather.find()) {
            String[] header = mather.group();
            Intrinsics.checkNotNullExpressionValue((Object)header, (String)"header");
            url2 = StringsKt.replace$default((String)url2, (String)header, (String)"", (boolean)false, (int)4, null);
            object2 = map;
            string = "headers";
            object = header;
            n = 8;
            bl = false;
            String string2 = object.substring(n);
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"(this as java.lang.String).substring(startIndex)");
            object = string2;
            n = 0;
            object2.put(string, object);
        }
        object2 = new String[]{"|"};
        List urlList = StringsKt.split$default((CharSequence)url2, (String[])object2, (boolean)false, (int)0, (int)6, null);
        url2 = (String)urlList.get(0);
        if (urlList.size() > 1) {
            object2 = map;
            string = "charset";
            object = new String[]{"="};
            object = StringsKt.split$default((CharSequence)((CharSequence)urlList.get(1)), (String[])object, (boolean)false, (int)0, (int)6, null).get(1);
            n = 0;
            object2.put(string, object);
        }
        mather = jsPattern.matcher(url2);
        boolean bl4 = false;
        ArrayList<String> jsList = new ArrayList<String>();
        while (mather.find()) {
            jsList.add(mather.group());
            url2 = StringsKt.replace$default((String)url2, (String)((String)CollectionsKt.last((List)jsList)), (String)Intrinsics.stringPlus((String)"$", (Object)(jsList.size() - 1)), (boolean)false, (int)4, null);
        }
        url2 = StringsKt.replace$default((String)StringsKt.replace$default((String)url2, (String)"{", (String)"<", (boolean)false, (int)4, null), (String)"}", (String)">", (boolean)false, (int)4, null);
        url2 = StringsKt.replace$default((String)url2, (String)"searchKey", (String)"{{key}}", (boolean)false, (int)4, null);
        Object object3 = url2;
        object = "<searchPage([-+]1)>";
        n = 0;
        object = new Regex((String)object);
        String string3 = "{{page$1}}";
        bl = false;
        object3 = object.replace((CharSequence)object3, string3);
        object = "searchPage([-+]1)";
        boolean bl5 = false;
        object = new Regex((String)object);
        String string4 = "{{page$1}}";
        bl = false;
        url2 = StringsKt.replace$default((String)object.replace((CharSequence)object3, string4), (String)"searchPage", (String)"{{page}}", (boolean)false, (int)4, null);
        object3 = jsList.iterator();
        int n2 = 0;
        while (object3.hasNext()) {
            int index = n2++;
            String item = (String)object3.next();
            url2 = StringsKt.replace$default((String)url2, (String)Intrinsics.stringPlus((String)"$", (Object)index), (String)StringsKt.replace$default((String)StringsKt.replace$default((String)item, (String)"searchKey", (String)"key", (boolean)false, (int)4, null), (String)"searchPage", (String)"page", (boolean)false, (int)4, null), (boolean)false, (int)4, null);
        }
        object3 = new String[]{"@"};
        urlList = StringsKt.split$default((CharSequence)url2, (String[])object3, (boolean)false, (int)0, (int)6, null);
        url2 = (String)urlList.get(0);
        if (urlList.size() > 1) {
            object3 = map;
            String string5 = "method";
            String string6 = "POST";
            bl = false;
            object3.put(string5, string6);
            object3 = map;
            string5 = "body";
            string6 = urlList.get(1);
            bl = false;
            object3.put(string5, string6);
        }
        if (map.size() > 0) {
            url2 = url2 + ',' + GsonExtensionsKt.getGSON().toJson(map);
        }
        return url2;
    }

    private final String uaToHeader(String ua) {
        CharSequence charSequence = ua;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || charSequence.length() == 0) {
            return null;
        }
        Map map = MapsKt.mapOf((Pair)new Pair((Object)"User-Agent", (Object)ua));
        return GsonExtensionsKt.getGSON().toJson((Object)map);
    }
}

