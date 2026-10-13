/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.api.ReturnData
 *  com.htmake.reader.api.controller.BaseController
 *  com.htmake.reader.api.controller.BookSourceController
 *  com.htmake.reader.api.controller.BookSourceController$deleteAllBookSources$1
 *  com.htmake.reader.api.controller.BookSourceController$deleteBookSource$1
 *  com.htmake.reader.api.controller.BookSourceController$deleteBookSources$1
 *  com.htmake.reader.api.controller.BookSourceController$deleteBookSourcesFile$1
 *  com.htmake.reader.api.controller.BookSourceController$deleteUserBookSource$1
 *  com.htmake.reader.api.controller.BookSourceController$getBookSource$1
 *  com.htmake.reader.api.controller.BookSourceController$getBookSources$1
 *  com.htmake.reader.api.controller.BookSourceController$saveBookSource$1
 *  com.htmake.reader.api.controller.BookSourceController$saveBookSources$1
 *  com.htmake.reader.api.controller.BookSourceController$saveFromRemoteSource$1
 *  com.htmake.reader.api.controller.BookSourceController$setAsDefaultBookSources$1
 *  com.htmake.reader.api.controller.BookSourceController$updateRemoteSourceSub$1
 *  com.htmake.reader.entity.User
 *  com.htmake.reader.utils.ExtKt
 *  com.htmake.reader.utils.SpringContextUtils
 *  com.htmake.reader.utils.VertExtKt
 *  io.legado.app.data.entities.BookSource
 *  io.vertx.core.http.HttpMethod
 *  io.vertx.core.json.JsonArray
 *  io.vertx.core.json.JsonObject
 *  io.vertx.ext.web.FileUpload
 *  io.vertx.ext.web.RoutingContext
 *  io.vertx.ext.web.client.WebClient
 *  io.vertx.kotlin.coroutines.VertxCoroutineKt
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Result
 *  kotlin.ResultKt
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.collections.SetsKt
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.CoroutineContext
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.coroutines.jvm.internal.Boxing
 *  kotlin.io.FilesKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlin.jvm.internal.TypeIntrinsics
 *  kotlinx.coroutines.BuildersKt
 *  kotlinx.coroutines.CoroutineScope
 *  kotlinx.coroutines.Dispatchers
 *  kotlinx.coroutines.slf4j.MDCContext
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.BookSourceController;
import com.htmake.reader.entity.User;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.SpringContextUtils;
import com.htmake.reader.utils.VertExtKt;
import io.legado.app.data.entities.BookSource;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.kotlin.coroutines.VertxCoroutineKt;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.slf4j.MDCContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\u000e\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\u000f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\u0010\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\u0011\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ&\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u00132\u0006\u0010\u0016\u001a\u00020\u00142\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\u0019\u0010\u0019\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u00132\u0006\u0010\u0016\u001a\u00020\u0014J\u0019\u0010\u001b\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0016\u001a\u00020\u0014J4\u0010\u001d\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0016\u001a\u00020\u00142\u0010\b\u0002\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u001f2\u0010\b\u0002\u0010 \u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u001fJ\u0019\u0010!\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\"\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010#\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010#\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010$\u001a\u00020\u0018J\u0019\u0010%\u001a\u00020&2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ \u0010'\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00142\b\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010$\u001a\u00020\u0018J\u0019\u0010*\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ#\u0010+\u001a\u00020&2\u0006\u0010\u0016\u001a\u00020\u00142\b\u0010,\u001a\u0004\u0018\u00010)H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010-R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006."}, d2={"Lcom/htmake/reader/api/controller/BookSourceController;", "Lcom/htmake/reader/api/controller/BaseController;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "webClient", "Lio/vertx/ext/web/client/WebClient;", "canEditBookSource", "", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllBookSources", "Lcom/htmake/reader/api/ReturnData;", "deleteBookSource", "deleteBookSources", "deleteBookSourcesFile", "deleteUserBookSource", "generateBookSourceMap", "", "", "", "userNameSpace", "bookSourceList", "Lio/vertx/core/json/JsonArray;", "getBookSource", "getBookSourceMap", "getBookSources", "getUserBookSourceJson", "getUserBookSourceJsonOpt", "fields", "", "checkNotEmpty", "readSourceFile", "saveBookSource", "saveBookSources", "bookSourceJsonArray", "saveFromRemoteSource", "", "saveUserBookSources", "userInfo", "Lcom/htmake/reader/entity/User;", "setAsDefaultBookSources", "updateRemoteSourceSub", "user", "(Ljava/lang/String;Lcom/htmake/reader/entity/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reader-pro"})
public final class BookSourceController
extends BaseController {
    @NotNull
    private WebClient webClient;

    public BookSourceController(@NotNull CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter((Object)coroutineContext, (String)"coroutineContext");
        super(coroutineContext);
        Object object = SpringContextUtils.getBean((String)"webClient", WebClient.class);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"getBean(\"webClient\", WebClient::class.java)");
        this.webClient = (WebClient)object;
    }

    @Nullable
    public final JsonArray getUserBookSourceJsonOpt(@NotNull String userNameSpace, @Nullable Set<String> fields, @Nullable Set<String> checkNotEmpty) {
        Intrinsics.checkNotNullParameter((Object)userNameSpace, (String)"userNameSpace");
        String[] stringArray = new String[]{"data", userNameSpace, "bookSource"};
        File bookSourceFile = ExtKt.getStorageFile$default((String[])stringArray, null, (int)2, null);
        if (!bookSourceFile.exists()) {
            stringArray = new String[]{"data", "default", "bookSource"};
            bookSourceFile = ExtKt.getStorageFile$default((String[])stringArray, null, (int)2, null);
        }
        JsonArray bookSourceList2 = ExtKt.parseJsonStringList$default((File)bookSourceFile, fields, null, (int)0, (int)0, checkNotEmpty, null, (int)92, null);
        return bookSourceList2;
    }

    public static /* synthetic */ JsonArray getUserBookSourceJsonOpt$default(BookSourceController bookSourceController, String string, Set set, Set set2, int n, Object object) {
        if ((n & 2) != 0) {
            set = null;
        }
        if ((n & 4) != 0) {
            set2 = null;
        }
        return bookSourceController.getUserBookSourceJsonOpt(string, set, set2);
    }

    @Nullable
    public final JsonArray getUserBookSourceJson(@NotNull String userNameSpace) {
        String[] stringArray;
        JsonArray systemBookSourceList;
        Intrinsics.checkNotNullParameter((Object)userNameSpace, (String)"userNameSpace");
        String[] stringArray2 = new String[]{"bookSource"};
        JsonArray bookSourceList2 = ExtKt.asJsonArray((Object)this.getUserStorage((Object)userNameSpace, stringArray2));
        if (bookSourceList2 == null && !userNameSpace.equals("default") && (systemBookSourceList = ExtKt.asJsonArray((Object)this.getUserStorage((Object)"default", stringArray = new String[]{"bookSource"}))) != null) {
            bookSourceList2 = systemBookSourceList;
        }
        return bookSourceList2;
    }

    @Nullable
    public final Object canEditBookSource(@NotNull RoutingContext context, @NotNull Continuation<? super Boolean> $completion) {
        if (!this.getAppConfig().getSecure()) {
            return Boxing.boxBoolean((boolean)true);
        }
        User userInfo = (User)context.get("userInfo");
        if (userInfo == null) {
            return Boxing.boxBoolean((boolean)false);
        }
        return Boxing.boxBoolean((boolean)userInfo.getEnable_book_source());
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveBookSource(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveBookSource.1)) ** GOTO lbl-1000
        var13_3 = var2_2;
        if ((var13_3.label & -2147483648) != 0) {
            var13_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var14_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 2;
                v1 = this.canEditBookSource(context, (Continuation)$continuation);
                if (v1 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl43
            }
            case 2: {
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl43:
                // 2 sources

                if (!((Boolean)v1).booleanValue()) {
                    return var3_6.setErrorMsg("\u6743\u9650\u4e0d\u8db3");
                }
                var5_7 = var1_1.getBodyAsString();
                Intrinsics.checkNotNullExpressionValue((Object)var5_7, (String)"context.bodyAsString");
                var5_7 = BookSource.Companion.fromJson-IoAF18A((String)var5_7);
                var6_8 = false;
                bookSource = (BookSource)(Result.isFailure-impl((Object)var5_7) != false ? null : var5_7);
                if (bookSource == null) {
                    return var3_6.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                userNameSpace = this.getUserNameSpace(var1_1);
                bookSourceList = this.getUserBookSourceJson(userNameSpace);
                if (bookSourceList == null) {
                    bookSourceList = new JsonArray();
                }
                var8_11 = 0;
                urlMap = new LinkedHashMap<K, V>();
                var8_11 = 0;
                var9_13 = bookSourceList.size();
                if (var8_11 < var9_13) {
                    do {
                        i = var8_11++;
                        var11_17 = bookSourceList.getJsonObject(i).getString("bookSourceUrl");
                        Intrinsics.checkNotNullExpressionValue((Object)var11_17, (String)"bookSourceList.getJsonObject(i).getString(\"bookSourceUrl\")");
                        urlMap.put(var11_17, Boxing.boxInt((int)i));
                    } while (var8_11 < var9_13);
                }
                if ((existIndex = ((Number)urlMap.getOrDefault(bookSource.getBookSourceUrl(), Boxing.boxInt((int)-1))).intValue()) >= 0) {
                    sourceList = bookSourceList.getList();
                    sourceList.set(existIndex, JsonObject.mapFrom((Object)bookSource));
                    bookSourceList = new JsonArray(sourceList);
                } else {
                    userInfo = (User)var1_1.get("userInfo");
                    if (userInfo != null && bookSourceList.size() >= userInfo.getBook_source_limit()) {
                        return var3_6.setErrorMsg("\u4f60\u5df2\u8fbe\u5230\u4e66\u6e90\u6570\u4e0a\u9650\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
                    }
                    bookSourceList.add(JsonObject.mapFrom((Object)bookSource));
                }
                this.saveUserStorage((Object)userNameSpace, "bookSource", (Object)bookSourceList);
                this.generateBookSourceMap(userNameSpace, bookSourceList);
                return ReturnData.setData$default((ReturnData)var3_6, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveBookSources(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveBookSources.1)) ** GOTO lbl-1000
        var6_3 = var2_2;
        if ((var6_3.label & -2147483648) != 0) {
            var6_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var7_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var7_5) {
                    return var7_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 2;
                v1 = this.canEditBookSource(context, (Continuation)$continuation);
                if (v1 == var7_5) {
                    return var7_5;
                }
                ** GOTO lbl43
            }
            case 2: {
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl43:
                // 2 sources

                if (!((Boolean)v1).booleanValue()) {
                    return var3_6.setErrorMsg("\u6743\u9650\u4e0d\u8db3");
                }
                bookSourceJsonArray = var1_1.getBodyAsJsonArray();
                if (bookSourceJsonArray == null) {
                    return var3_6.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                return this.saveBookSources(var1_1, bookSourceJsonArray);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @NotNull
    public final ReturnData saveBookSources(@NotNull RoutingContext context, @NotNull JsonArray bookSourceJsonArray) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)bookSourceJsonArray, (String)"bookSourceJsonArray");
        return this.saveUserBookSources(this.getUserNameSpace(context), (User)context.get("userInfo"), bookSourceJsonArray);
    }

    @NotNull
    public final ReturnData saveUserBookSources(@NotNull String userNameSpace, @Nullable User userInfo, @NotNull JsonArray bookSourceJsonArray) {
        Intrinsics.checkNotNullParameter((Object)userNameSpace, (String)"userNameSpace");
        Intrinsics.checkNotNullParameter((Object)bookSourceJsonArray, (String)"bookSourceJsonArray");
        ReturnData returnData = new ReturnData();
        JsonArray bookSourceList2 = this.getUserBookSourceJson(userNameSpace);
        if (bookSourceList2 == null) {
            bookSourceList2 = new JsonArray();
        }
        int n = 0;
        Map urlMap = new LinkedHashMap();
        n = 0;
        int n2 = bookSourceList2.size();
        if (n < n2) {
            do {
                int i = n++;
                String string = bookSourceList2.getJsonObject(i).getString("bookSourceUrl");
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"bookSourceList.getJsonObject(i).getString(\"bookSourceUrl\")");
                urlMap.put(string, i);
            } while (n < n2);
        }
        boolean isOverLimit = false;
        int addCnt = 0;
        int maxIndex = bookSourceList2.size() - 1;
        int n3 = 0;
        Set updateIndex = new LinkedHashSet();
        n3 = 0;
        int n4 = bookSourceJsonArray.size();
        if (n3 < n4) {
            do {
                int n5;
                Object object;
                int k = n3++;
                try {
                    object = bookSourceJsonArray.getJsonObject(k).toString();
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"bookSourceJsonArray.getJsonObject(k).toString()");
                    object = BookSource.Companion.fromJson-IoAF18A((String)object);
                    n5 = 0;
                    object = Result.isFailure-impl((Object)object) ? null : object;
                }
                catch (Exception e) {
                    object = null;
                }
                BookSource bookSource = object;
                if (bookSource == null) continue;
                int existIndex = ((Number)urlMap.getOrDefault(bookSource.getBookSourceUrl(), -1)).intValue();
                if (existIndex >= 0) {
                    bookSourceList2.set(existIndex, JsonObject.mapFrom((Object)bookSource));
                    if (existIndex > maxIndex) continue;
                    updateIndex.add(existIndex);
                    continue;
                }
                if (userInfo != null && bookSourceList2.size() >= userInfo.getBook_source_limit()) {
                    isOverLimit = true;
                    break;
                }
                n5 = addCnt;
                addCnt = n5 + 1;
                bookSourceList2.add(JsonObject.mapFrom((Object)bookSource));
                urlMap.put(bookSource.getBookSourceUrl(), bookSourceList2.size() - 1);
            } while (n3 < n4);
        }
        this.saveUserStorage((Object)userNameSpace, "bookSource", (Object)bookSourceList2);
        this.generateBookSourceMap(userNameSpace, bookSourceList2);
        String tip = "\u65b0\u589e" + addCnt + "\u6761\u4e66\u6e90\uff0c\u66f4\u65b0" + updateIndex.size() + "\u6761\u4e66\u6e90";
        if (isOverLimit) {
            return returnData.setErrorMsg(Intrinsics.stringPlus((String)tip, (Object)"\u3002\u4f60\u5df2\u8fbe\u5230\u4e66\u6e90\u6570\u4e0a\u9650\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458"));
        }
        return returnData.setData((Object)"", tip);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getBookSource(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getBookSource.1)) ** GOTO lbl-1000
        var12_3 = var2_2;
        if ((var12_3.label & -2147483648) != 0) {
            var12_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var13_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var13_5) {
                    return var13_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                var4_7 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var5_8 = context.getBodyAsJson().getString("bookSourceUrl");
                    Intrinsics.checkNotNullExpressionValue((Object)var5_8, (String)"context.bodyAsJson.getString(\"bookSourceUrl\")");
                    var4_7 = var5_8;
                } else {
                    var6_9 = context.queryParam("bookSourceUrl");
                    Intrinsics.checkNotNullExpressionValue((Object)var6_9, (String)"context.queryParam(\"bookSourceUrl\")");
                    var5_8 = (String)CollectionsKt.firstOrNull((List)var6_9);
                    bookSourceUrl = var5_8 == null ? "" : var5_8;
                }
                var5_8 = bookSourceUrl;
                var6_10 = false;
                var7_12 = false;
                if (var5_8.length() == 0) {
                    return returnData.setErrorMsg("\u4e66\u6e90\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
                }
                userNameSpace = this.getUserNameSpace(context);
                urlMap = this.getBookSourceMap(userNameSpace);
                existIndex = ((Number)urlMap.getOrDefault(bookSourceUrl, Boxing.boxInt((int)-1))).intValue();
                if (existIndex < 0) {
                    return returnData.setErrorMsg("\u4e66\u6e90\u4fe1\u606f\u4e0d\u5b58\u5728");
                }
                var9_13 = new String[]{"data", userNameSpace, "bookSource"};
                bookSourceFile = ExtKt.getStorageFile$default((String[])var9_13, null, (int)2, null);
                if (!bookSourceFile.exists()) {
                    var9_13 = new String[]{"data", "default", "bookSource"};
                    bookSourceFile = ExtKt.getStorageFile$default((String[])var9_13, null, (int)2, null);
                }
                if ((bookSourceList = ExtKt.parseJsonStringList$default((File)bookSourceFile, null, null, (int)existIndex, (int)existIndex, null, null, (int)102, null)) == null) {
                    return returnData.setErrorMsg("\u4e66\u6e90\u4fe1\u606f\u4e0d\u5b58\u5728");
                }
                var10_15 = new JsonObject(bookSourceList.getString(0)).getMap();
                Intrinsics.checkNotNullExpressionValue((Object)var10_15, (String)"JsonObject(bookSourceList.getString(0)).map");
                return ReturnData.setData$default((ReturnData)returnData, (Object)var10_15, null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getBookSources(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getBookSources.1)) ** GOTO lbl-1000
        var20_3 = var2_2;
        if ((var20_3.label & -2147483648) != 0) {
            var20_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var21_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                var4_7 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var5_8 = context.getBodyAsJson().getInteger("simple", Boxing.boxInt((int)0));
                    Intrinsics.checkNotNullExpressionValue((Object)var5_8, (String)"context.bodyAsJson.getInteger(\"simple\", 0)");
                    var4_7 = ((Number)var5_8).intValue();
                } else {
                    var6_9 = context.queryParam("simple");
                    Intrinsics.checkNotNullExpressionValue((Object)var6_9, (String)"context.queryParam(\"simple\")");
                    var5_8 = (String)CollectionsKt.firstOrNull((List)var6_9);
                    if (var5_8 == null) {
                        v1 = 0;
                    } else {
                        var7_10 /* !! */  = var5_8;
                        var8_12 = false;
                        var6_9 = Boxing.boxInt((int)Integer.parseInt((String)var7_10 /* !! */ ));
                        v1 = var6_9 == null ? 0 : var6_9.intValue();
                    }
                    simple = v1;
                }
                userNameSpace = this.getUserNameSpace(context);
                if (simple > 0) {
                    var7_10 /* !! */  = new String[]{"bookSourceGroup", "bookSourceName", "bookSourceUrl"};
                    v2 = SetsKt.setOf((Object[])var7_10 /* !! */ );
                } else {
                    v2 = null;
                }
                bookSourceList = this.getUserBookSourceJsonOpt(userNameSpace, v2, simple > 0 ? SetsKt.setOf((Object)"exploreUrl") : null);
                if (bookSourceList != null) {
                    var7_10 /* !! */  = bookSourceList.getList();
                    Intrinsics.checkNotNullExpressionValue((Object)var7_10 /* !! */ , (String)"bookSourceList.list");
                    var7_10 /* !! */  = (Iterable)var7_10 /* !! */ ;
                    var16_13 = returnData;
                    $i$f$map = false;
                    var9_14 = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                    $i$f$mapTo = false;
                    var12_17 = $this$mapTo$iv$iv.iterator();
                    while (var12_17.hasNext()) {
                        var14_19 = item$iv$iv = var12_17.next();
                        var17_21 = destination$iv$iv;
                        $i$a$-map-BookSourceController$getBookSources$2 = false;
                        if (it == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                        }
                        var18_22 = new JsonObject((String)it).getMap();
                        var17_21.add(var18_22);
                    }
                    var17_21 = (List)destination$iv$iv;
                    return ReturnData.setData$default((ReturnData)var16_13, (Object)var17_21, null, (int)2, null);
                }
                var7_11 = false;
                return ReturnData.setData$default((ReturnData)returnData, new ArrayList<E>(), null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object deleteBookSource(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteBookSource.1)) ** GOTO lbl-1000
        var10_3 = var2_2;
        if ((var10_3.label & -2147483648) != 0) {
            var10_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var11_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var11_5) {
                    return var11_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 2;
                v1 = this.canEditBookSource(context, (Continuation)$continuation);
                if (v1 == var11_5) {
                    return var11_5;
                }
                ** GOTO lbl43
            }
            case 2: {
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl43:
                // 2 sources

                if (!((Boolean)v1).booleanValue()) {
                    return var3_6.setErrorMsg("\u6743\u9650\u4e0d\u8db3");
                }
                var5_7 = var1_1.getBodyAsString();
                Intrinsics.checkNotNullExpressionValue((Object)var5_7, (String)"context.bodyAsString");
                var5_7 = BookSource.Companion.fromJson-IoAF18A((String)var5_7);
                var6_8 = false;
                bookSource = (BookSource)(Result.isFailure-impl((Object)var5_7) != false ? null : var5_7);
                if (bookSource == null) {
                    return var3_6.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                userNameSpace = this.getUserNameSpace(var1_1);
                bookSourceList = this.getUserBookSourceJson(userNameSpace);
                if (bookSourceList == null) {
                    bookSourceList = new JsonArray();
                }
                if ((existIndex = ((Number)(urlMap = this.getBookSourceMap(userNameSpace)).getOrDefault(bookSource.getBookSourceUrl(), Boxing.boxInt((int)-1))).intValue()) >= 0) {
                    bookSourceList.remove(existIndex);
                }
                this.saveUserStorage((Object)userNameSpace, "bookSource", (Object)bookSourceList);
                this.generateBookSourceMap(userNameSpace, bookSourceList);
                return ReturnData.setData$default((ReturnData)var3_6, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object deleteBookSources(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteBookSources.1)) ** GOTO lbl-1000
        var17_3 = var2_2;
        if ((var17_3.label & -2147483648) != 0) {
            var17_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var18_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var18_5) {
                    return var18_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 2;
                v1 = this.canEditBookSource(context, (Continuation)$continuation);
                if (v1 == var18_5) {
                    return var18_5;
                }
                ** GOTO lbl43
            }
            case 2: {
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl43:
                // 2 sources

                if (!((Boolean)v1).booleanValue()) {
                    return var3_6.setErrorMsg("\u6743\u9650\u4e0d\u8db3");
                }
                bookSourceJsonArray = var1_1.getBodyAsJsonArray();
                userNameSpace = this.getUserNameSpace(var1_1);
                bookSourceList = this.getUserBookSourceJson(userNameSpace);
                if (bookSourceList == null) {
                    bookSourceList = new JsonArray();
                }
                if ((var7_10 = 0) < (var8_11 = bookSourceJsonArray.size())) {
                    do {
                        k = var7_10++;
                        bookSourceUrl = bookSourceJsonArray.getJsonObject(k).getString("bookSourceUrl");
                        var11_14 = bookSourceUrl;
                        var12_16 = 0;
                        var13_17 = 0;
                        if (var11_14 == null || var11_14.length() == 0) continue;
                        existIndex = -1;
                        var12_16 = 0;
                        var13_17 = bookSourceList.size();
                        if (var12_16 < var13_17) {
                            do {
                                if (!bookSourceUrl.equals(_bookSourceUrl = bookSourceList.getJsonObject(i = var12_16++).getString("bookSourceUrl"))) continue;
                                existIndex = i;
                                break;
                            } while (var12_16 < var13_17);
                        }
                        if (existIndex < 0) continue;
                        bookSourceList.remove(existIndex);
                    } while (var7_10 < var8_11);
                }
                this.saveUserStorage((Object)userNameSpace, "bookSource", (Object)bookSourceList);
                this.generateBookSourceMap(userNameSpace, bookSourceList);
                return ReturnData.setData$default((ReturnData)var3_6, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object deleteAllBookSources(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteAllBookSources.1)) ** GOTO lbl-1000
        var6_3 = var2_2;
        if ((var6_3.label & -2147483648) != 0) {
            var6_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var7_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var7_5) {
                    return var7_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 2;
                v1 = this.canEditBookSource(context, (Continuation)$continuation);
                if (v1 == var7_5) {
                    return var7_5;
                }
                ** GOTO lbl43
            }
            case 2: {
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl43:
                // 2 sources

                if (!((Boolean)v1).booleanValue()) {
                    return var3_6.setErrorMsg("\u6743\u9650\u4e0d\u8db3");
                }
                userNameSpace = this.getUserNameSpace(var1_1);
                this.saveUserStorage((Object)userNameSpace, "bookSource", (Object)new JsonArray());
                this.generateBookSourceMap(userNameSpace, new JsonArray());
                return ReturnData.setData$default((ReturnData)var3_6, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object setAsDefaultBookSources(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof setAsDefaultBookSources.1)) ** GOTO lbl-1000
        var8_3 = var2_2;
        if ((var8_3.label & -2147483648) != 0) {
            var8_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var9_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var9_5) {
                    return var9_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (!this.checkManagerAuth(context)) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_SECURE_KEY", null, (int)2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                }
                var6_8 /* !! */  = username = context.getBodyAsJson().getString("username");
                Intrinsics.checkNotNullExpressionValue((Object)var6_8 /* !! */ , (String)"username");
                v1 = var6_8 /* !! */ ;
                var6_8 /* !! */  = new String[]{"bookSource"};
                bookSourceList = ExtKt.asJsonArray((Object)this.getUserStorage((Object)v1, var6_8 /* !! */ ));
                if (bookSourceList == null) {
                    return returnData.setErrorMsg("\u7528\u6237\u4e66\u6e90\u4e0d\u5b58\u5728");
                }
                var6_8 /* !! */  = bookSourceList.getList();
                Intrinsics.checkNotNullExpressionValue((Object)var6_8 /* !! */ , (String)"bookSourceList.getList()");
                this.saveUserStorage((Object)"default", "bookSource", (Object)var6_8 /* !! */ );
                this.generateBookSourceMap("default", bookSourceList);
                return ReturnData.setData$default((ReturnData)returnData, (Object)"\u8bbe\u7f6e\u9ed8\u8ba4\u4e66\u6e90\u6210\u529f", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public final Object readSourceFile(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        ReturnData returnData = new ReturnData();
        if (context.fileUploads() == null || context.fileUploads().isEmpty()) {
            return returnData.setErrorMsg("\u8bf7\u4e0a\u4f20\u6587\u4ef6");
        }
        JsonArray sourceList = null;
        sourceList = new JsonArray();
        Collection collection = context.fileUploads();
        Intrinsics.checkNotNullExpressionValue((Object)collection, (String)"context.fileUploads()");
        Iterable $this$forEach$iv = collection;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            FileUpload it = (FileUpload)element$iv;
            boolean bl = false;
            File file = new File(it.uploadedFileName());
            if (!file.exists()) continue;
            sourceList.add(FilesKt.readText$default((File)file, null, (int)1, null));
            file.delete();
        }
        collection = sourceList.getList();
        Intrinsics.checkNotNullExpressionValue((Object)collection, (String)"sourceList.getList()");
        return ReturnData.setData$default((ReturnData)returnData, (Object)collection, null, (int)2, null);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveFromRemoteSource(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof saveFromRemoteSource.1)) ** GOTO lbl-1000
        var9_3 = var2_2;
        if ((var9_3.label & -2147483648) != 0) {
            var9_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var10_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var10_5) {
                    return var10_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    VertExtKt.success((RoutingContext)context, (Object)ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528"));
                    return Unit.INSTANCE;
                }
                url = new Ref.ObjectRef();
                if (context.request().method() == HttpMethod.POST) {
                    var5_8 = context.getBodyAsJson().getString("url");
                    url.element = var5_8 == null ? "" : var5_8;
                } else {
                    var6_9 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue((Object)var6_9, (String)"context.queryParam(\"url\")");
                    var5_8 = (String)CollectionsKt.firstOrNull((List)var6_9);
                    url.element = var5_8 == null ? "" : var5_8;
                }
                var5_8 = (CharSequence)url.element;
                var6_10 = false;
                var7_11 = false;
                if (var5_8 == null || var5_8.length() == 0) {
                    VertExtKt.success((RoutingContext)context, (Object)returnData.setErrorMsg("\u8bf7\u8f93\u5165\u8fdc\u7a0b\u4e66\u6e90\u94fe\u63a5"));
                    return Unit.INSTANCE;
                }
                BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object updateRemoteSourceSub(@NotNull String var1_1, @Nullable User var2_2, @NotNull Continuation<? super Unit> var3_3) {
        if (!(var3_3 instanceof updateRemoteSourceSub.1)) ** GOTO lbl-1000
        var14_4 = var3_3;
        if ((var14_4.label & -2147483648) != 0) {
            var14_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var15_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                remoteBookSourceList = new Ref.ObjectRef();
                var6_8 = new String[]{"remoteBookSourceSub"};
                var5_10 = ExtKt.asJsonArray((Object)this.getUserStorage((Object)userNameSpace, var6_8));
                if (var5_10 == null) {
                    return Unit.INSTANCE;
                }
                remoteBookSourceList.element = var5_10;
                var5_11 = 0;
                var6_9 = ((JsonArray)remoteBookSourceList.element).size();
                if (var5_11 < var6_9) {
                    while (true) {
                        i = var5_11++;
                        remoteBookSource = new Ref.ObjectRef();
                        remoteBookSource.element = ((JsonArray)remoteBookSourceList.element).getJsonObject(i);
                        url = new Ref.ObjectRef();
                        url.element = ((JsonObject)remoteBookSource.element).getString("link");
                        var10_15 = (CharSequence)url.element;
                        var11_16 = false;
                        var12_17 = false;
                        if (var10_15 == null || var10_15.length() == 0) continue;
                        $continuation.L$0 = this;
                        $continuation.L$1 = userNameSpace;
                        $continuation.L$2 = user;
                        $continuation.L$3 = remoteBookSourceList;
                        $continuation.I$0 = var5_11;
                        $continuation.I$1 = var6_9;
                        $continuation.label = 1;
                        v0 = VertxCoroutineKt.awaitResult((Function1)((Function1)new /* Unavailable Anonymous Inner Class!! */), (Continuation)$continuation);
                        if (v0 != var15_6) continue;
                        return var15_6;
                    }
                }
                ** GOTO lbl52
            }
            case 1: {
                var6_9 = $continuation.I$1;
                var5_11 = $continuation.I$0;
                var4_7 = (Ref.ObjectRef)$continuation.L$3;
                var2_2 = (User)$continuation.L$2;
                var1_1 = (String)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
                if (var5_11 < var6_9) ** continue;
lbl52:
                // 2 sources

                BookSourceController.generateBookSourceMap$default((BookSourceController)this, (String)var1_1, null, (int)2, null);
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object deleteUserBookSource(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteUserBookSource.1)) ** GOTO lbl-1000
        var13_3 = var2_2;
        if ((var13_3.label & -2147483648) != 0) {
            var13_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var14_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (!this.checkManagerAuth(context)) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_SECURE_KEY", null, (int)2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                }
                var5_8 = 0;
                userJsonArray = context.getBodyAsJsonArray();
                var6_9 = userJsonArray.size();
                if (var5_8 < var6_9) {
                    do {
                        i = var5_8++;
                        username = userJsonArray.getString(i);
                        var10_13 = new String[4];
                        var10_13[0] = "storage";
                        var10_13[1] = "data";
                        var11_14 = username;
                        Intrinsics.checkNotNullExpressionValue((Object)var11_14, (String)"username");
                        var10_13[2] = var11_14;
                        var10_13[3] = "bookSource.json";
                        userBookSourceFile = new File(ExtKt.getWorkDir((String[])var10_13));
                        if (!userBookSourceFile.exists()) continue;
                        ExtKt.deleteRecursively((File)userBookSourceFile);
                    } while (var5_8 < var6_9);
                }
                return ReturnData.setData$default((ReturnData)returnData, (Object)"\u5220\u9664\u4e66\u6e90\u6210\u529f", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object deleteBookSourcesFile(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteBookSourcesFile.1)) ** GOTO lbl-1000
        var8_3 = var2_2;
        if ((var8_3.label & -2147483648) != 0) {
            var8_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var9_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var9_5) {
                    return var9_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookSourceController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                userNameSpace = this.getUserNameSpace(context);
                var6_8 = new String[]{"storage", "data", userNameSpace, "bookSource.json"};
                userBookSourceFile = new File(ExtKt.getWorkDir((String[])var6_8));
                if (userBookSourceFile.exists()) {
                    ExtKt.deleteRecursively((File)userBookSourceFile);
                }
                return ReturnData.setData$default((ReturnData)returnData, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @NotNull
    public final Map<String, Integer> generateBookSourceMap(@NotNull String userNameSpace, @Nullable JsonArray bookSourceList2) {
        JsonArray bookSourceJsonArray;
        Intrinsics.checkNotNullParameter((Object)userNameSpace, (String)"userNameSpace");
        JsonArray jsonArray = bookSourceJsonArray = bookSourceList2 != null ? bookSourceList2 : this.getUserBookSourceJson(userNameSpace);
        if (bookSourceJsonArray == null) {
            bookSourceJsonArray = new JsonArray();
        }
        boolean bl = false;
        Map urlMap = new LinkedHashMap();
        int n = 0;
        List exploreList = new ArrayList();
        n = 0;
        int n2 = bookSourceJsonArray.size();
        if (n < n2) {
            do {
                int i = n++;
                CharSequence charSequence = bookSourceJsonArray.getJsonObject(i).getString("bookSourceUrl");
                Intrinsics.checkNotNullExpressionValue((Object)charSequence, (String)"bookSourceJsonArray.getJsonObject(i).getString(\"bookSourceUrl\")");
                urlMap.put(charSequence, i);
                charSequence = bookSourceJsonArray.getJsonObject(i).getString("exploreUrl");
                boolean bl2 = false;
                boolean bl3 = false;
                if (charSequence == null || charSequence.length() == 0) continue;
                charSequence = new Pair[]{TuplesKt.to((Object)"bookSourceUrl", (Object)bookSourceJsonArray.getJsonObject(i).getString("bookSourceUrl")), TuplesKt.to((Object)"bookSourceGroup", (Object)bookSourceJsonArray.getJsonObject(i).getString("bookSourceGroup")), TuplesKt.to((Object)"bookSourceName", (Object)bookSourceJsonArray.getJsonObject(i).getString("bookSourceName"))};
                exploreList.add(MapsKt.mutableMapOf((Pair[])charSequence));
            } while (n < n2);
        }
        this.saveUserStorage((Object)userNameSpace, "bookSourceMap", (Object)urlMap);
        this.saveUserStorage((Object)userNameSpace, "bookSourceExploreList", (Object)exploreList);
        return urlMap;
    }

    public static /* synthetic */ Map generateBookSourceMap$default(BookSourceController bookSourceController, String string, JsonArray jsonArray, int n, Object object) {
        if ((n & 2) != 0) {
            jsonArray = null;
        }
        return bookSourceController.generateBookSourceMap(string, jsonArray);
    }

    @NotNull
    public final Map<String, Integer> getBookSourceMap(@NotNull String userNameSpace) {
        String string;
        Intrinsics.checkNotNullParameter((Object)userNameSpace, (String)"userNameSpace");
        Object object = new String[]{"data", userNameSpace, "bookSource"};
        if (ExtKt.getStorageFile$default((String[])object, null, (int)2, null).exists()) {
            object = new String[]{"bookSourceMap"};
            string = this.getUserStorage((Object)userNameSpace, (String[])object);
        } else {
            object = new String[]{"bookSourceMap"};
            string = this.getUserStorage((Object)"default", (String[])object);
        }
        String content = string;
        object = content;
        boolean bl = false;
        boolean bl2 = false;
        if (!(object == null || object.length() == 0)) {
            JsonObject jsonObject = ExtKt.asJsonObject((Object)content);
            Object object2 = object = jsonObject == null ? null : jsonObject.getMap();
            if (object == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.Int>");
            }
            return TypeIntrinsics.asMutableMap((Object)object);
        }
        object = new String[]{"data", userNameSpace, "bookSource"};
        if (ExtKt.getStorageFile$default((String[])object, null, (int)2, null).exists()) {
            return BookSourceController.generateBookSourceMap$default((BookSourceController)this, (String)userNameSpace, null, (int)2, null);
        }
        return BookSourceController.generateBookSourceMap$default((BookSourceController)this, (String)"default", null, (int)2, null);
    }

    public static final /* synthetic */ WebClient access$getWebClient$p(BookSourceController $this) {
        return $this.webClient;
    }
}

