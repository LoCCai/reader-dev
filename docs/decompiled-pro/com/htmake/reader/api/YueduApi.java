/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.SpringEvent
 *  com.htmake.reader.api.ReturnData
 *  com.htmake.reader.api.YueduApi
 *  com.htmake.reader.api.YueduApi$getSystemInfo$1
 *  com.htmake.reader.api.YueduApi$initRouter$1
 *  com.htmake.reader.api.YueduApiKt
 *  com.htmake.reader.api.controller.BookController
 *  com.htmake.reader.api.controller.BookGroupController
 *  com.htmake.reader.api.controller.BookSourceController
 *  com.htmake.reader.api.controller.BookmarkController
 *  com.htmake.reader.api.controller.FileController
 *  com.htmake.reader.api.controller.HttpTTSController
 *  com.htmake.reader.api.controller.LicenseController
 *  com.htmake.reader.api.controller.ReplaceRuleController
 *  com.htmake.reader.api.controller.RssSourceController
 *  com.htmake.reader.api.controller.UserController
 *  com.htmake.reader.api.controller.WebdavController
 *  com.htmake.reader.config.AppConfig
 *  com.htmake.reader.config.BookConfig
 *  com.htmake.reader.entity.License
 *  com.htmake.reader.init.ReaderAdapter
 *  com.htmake.reader.utils.ExtKt
 *  com.htmake.reader.utils.MongoManager
 *  com.htmake.reader.utils.RemoteWebview
 *  com.htmake.reader.utils.SpringContextUtils
 *  com.htmake.reader.utils.VertExtKt
 *  com.htmake.reader.verticle.RestVerticle
 *  io.legado.app.adapters.ReaderAdapterHelper
 *  io.legado.app.adapters.ReaderAdapterInterface
 *  io.vertx.core.Handler
 *  io.vertx.core.http.HttpServerResponse
 *  io.vertx.core.net.impl.URIDecoder
 *  io.vertx.ext.web.Route
 *  io.vertx.ext.web.Router
 *  io.vertx.ext.web.RoutingContext
 *  io.vertx.ext.web.handler.StaticHandler
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.ResultKt
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.MapsKt
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.CoroutineContext
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.coroutines.jvm.internal.Boxing
 *  kotlin.io.FilesKt
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$IntRef
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlin.text.StringsKt
 *  kotlinx.coroutines.BuildersKt
 *  kotlinx.coroutines.CoroutineScope
 *  kotlinx.coroutines.Dispatchers
 *  kotlinx.coroutines.slf4j.MDCContext
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.slf4j.MDC
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.core.env.Environment
 *  org.springframework.scheduling.annotation.Scheduled
 *  org.springframework.stereotype.Component
 */
package com.htmake.reader.api;

import com.htmake.reader.SpringEvent;
import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.YueduApi;
import com.htmake.reader.api.YueduApiKt;
import com.htmake.reader.api.controller.BookController;
import com.htmake.reader.api.controller.BookGroupController;
import com.htmake.reader.api.controller.BookSourceController;
import com.htmake.reader.api.controller.BookmarkController;
import com.htmake.reader.api.controller.FileController;
import com.htmake.reader.api.controller.HttpTTSController;
import com.htmake.reader.api.controller.LicenseController;
import com.htmake.reader.api.controller.ReplaceRuleController;
import com.htmake.reader.api.controller.RssSourceController;
import com.htmake.reader.api.controller.UserController;
import com.htmake.reader.api.controller.WebdavController;
import com.htmake.reader.config.AppConfig;
import com.htmake.reader.config.BookConfig;
import com.htmake.reader.entity.License;
import com.htmake.reader.init.ReaderAdapter;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.MongoManager;
import com.htmake.reader.utils.RemoteWebview;
import com.htmake.reader.utils.SpringContextUtils;
import com.htmake.reader.utils.VertExtKt;
import com.htmake.reader.verticle.RestVerticle;
import io.legado.app.adapters.ReaderAdapterHelper;
import io.legado.app.adapters.ReaderAdapterInterface;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.net.impl.URIDecoder;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import java.io.File;
import java.lang.invoke.LambdaMetafactory;
import java.net.URLDecoder;
import java.util.Calendar;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.slf4j.MDCContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 * Exception performing whole class analysis ignored.
 */
@Component
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0017\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0017J\b\u0010\t\u001a\u00020\bH\u0017J\b\u0010\n\u001a\u00020\bH\u0017J\b\u0010\u000b\u001a\u00020\bH\u0017J\b\u0010\f\u001a\u00020\rH\u0016J\u0019\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u0019\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0011\u0010\u0017\u001a\u00020\bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u00112\n\u0010\u001b\u001a\u00060\u001cj\u0002`\u001dH\u0016J\b\u0010\u001e\u001a\u00020\bH\u0016J\b\u0010\u001f\u001a\u00020\bH\u0017J\u0011\u0010 \u001a\u00020\bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\b\u0010!\u001a\u00020\bH\u0017J\b\u0010\"\u001a\u00020\bH\u0016R\u0012\u0010\u0003\u001a\u00020\u00048\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006#"}, d2={"Lcom/htmake/reader/api/YueduApi;", "Lcom/htmake/reader/verticle/RestVerticle;", "()V", "appConfig", "Lcom/htmake/reader/config/AppConfig;", "env", "Lorg/springframework/core/env/Environment;", "autoBackup", "", "autoGC", "checkLicense", "clearUser", "getContextPath", "", "getSystemInfo", "Lcom/htmake/reader/api/ReturnData;", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "initRouter", "router", "Lio/vertx/ext/web/Router;", "(Lio/vertx/ext/web/Router;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "migration", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onHandlerError", "ctx", "error", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onStartError", "remoteBookSourceSubUpdateJob", "setupPort", "shelfUpdateJob", "started", "reader-pro"})
public class YueduApi
extends RestVerticle {
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private Environment env;

    @Nullable
    public Object initRouter(@NotNull Router router, @NotNull Continuation<? super Unit> $completion) {
        return YueduApi.initRouter$suspendImpl((YueduApi)this, (Router)router, $completion);
    }

    /*
     * Unable to fully structure code
     */
    static /* synthetic */ Object initRouter$suspendImpl(YueduApi var0, Router var1_1, Continuation var2_2) {
        if (!(var2_2 instanceof initRouter.1)) ** GOTO lbl-1000
        var21_3 = var2_2;
        if ((var21_3.label & -2147483648) != 0) {
            var21_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var22_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                $continuation.L$0 = this;
                $continuation.L$1 = router;
                $continuation.label = 1;
                v0 = this.setupPort((Continuation)$continuation);
                if (v0 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl24
            }
            case 1: {
                router = (Router)$continuation.L$1;
                this = (YueduApi)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl24:
                // 2 sources

                if ((var3_6 = this.appConfig) == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"appConfig");
                    throw null;
                }
                var3_6 = var3_6.getMongoUri();
                var4_7 = false;
                if (var3_6.length() > 0) {
                    var3_6 = this.appConfig;
                    if (var3_6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException((String)"appConfig");
                        throw null;
                    }
                    MongoManager.INSTANCE.connect(var3_6.getMongoUri());
                }
                if ((var3_6 = this.appConfig) == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"appConfig");
                    throw null;
                }
                var3_6 = var3_6.getRemoteWebviewApi();
                var4_7 = false;
                if (var3_6.length() > 0) {
                    var3_6 = this.appConfig;
                    if (var3_6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException((String)"appConfig");
                        throw null;
                    }
                    RemoteWebview.INSTANCE.setRemoteApi(var3_6.getRemoteWebviewApi());
                }
                ReaderAdapterHelper.INSTANCE.setAdapter((ReaderAdapterInterface)ReaderAdapter.INSTANCE);
                $continuation.L$0 = this;
                $continuation.L$1 = router;
                $continuation.label = 2;
                v1 = this.migration((Continuation)$continuation);
                if (v1 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl59
            }
            case 2: {
                var1_1 = (Router)$continuation.L$1;
                var0 = (YueduApi)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl59:
                // 2 sources

                var1_1.route("/*").handler((Handler)StaticHandler.create((String)"web").setDefaultContentEncoding("UTF-8"));
                var4_8 = new String[]{"storage", "assets"};
                assetsDir = ExtKt.getWorkDir((String[])var4_8);
                assetsDirFile = new File(assetsDir);
                if (!assetsDirFile.exists()) {
                    assetsDirFile.mkdirs();
                }
                if (!(assetsCssFile = new File(assetsCss = ExtKt.getWorkDir((String[])(var6_9 = new String[]{"storage", "assets", "reader.css"})))).exists()) {
                    FilesKt.writeText$default((File)assetsCssFile, (String)"/* \u5728\u6b64\u5904\u53ef\u4ee5\u7f16\u5199CSS\u6837\u5f0f\u6765\u81ea\u5b9a\u4e49\u9875\u9762 */", null, (int)2, null);
                }
                var1_1.route("/assets/*").handler((Handler)StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot(assetsDir).setDefaultContentEncoding("UTF-8"));
                dataDir = new Ref.ObjectRef();
                var8_12 = new String[]{"storage", "data"};
                dataDir.element = ExtKt.getWorkDir((String[])var8_12);
                var1_1.route("/book-assets/*").handler((Handler)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, initRouter$lambda-0(kotlin.jvm.internal.Ref$ObjectRef io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)((Ref.ObjectRef)dataDir));
                var1_1.route("/book-assets/*").handler((Handler)StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot((String)dataDir.element).setDefaultContentEncoding("UTF-8"));
                var1_1.route("/epub/*").handler((Handler)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, initRouter$lambda-1(kotlin.jvm.internal.Ref$ObjectRef io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)((Ref.ObjectRef)dataDir));
                var1_1.route("/epub/*").handler((Handler)StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot((String)dataDir.element).setDefaultContentEncoding("UTF-8"));
                var1_1.route("/simple-web").handler((Handler)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, initRouter$lambda-2(io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)());
                var1_1.route("/simple-web/*").handler((Handler)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, initRouter$lambda-3(io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)());
                var1_1.route("/simple-web/*").handler((Handler)StaticHandler.create((String)"simple-web").setDefaultContentEncoding("UTF-8"));
                var8_12 = var1_1.get("/reader3/getSystemInfo");
                Intrinsics.checkNotNullExpressionValue((Object)var8_12, (String)"router.get(\"/reader3/getSystemInfo\")");
                var0.coroutineHandler((Route)var8_12, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                bookController = new BookController(var0.getCoroutineContext());
                bookGroupController = new BookGroupController(var0.getCoroutineContext());
                bookSourceController = new BookSourceController(var0.getCoroutineContext());
                rssSourceController = new RssSourceController(var0.getCoroutineContext());
                userController = new UserController(var0.getCoroutineContext());
                webdavController = new WebdavController(var0.getCoroutineContext(), var1_1, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                replaceRuleController = new ReplaceRuleController(var0.getCoroutineContext());
                bookmarkController = new BookmarkController(var0.getCoroutineContext());
                fileController = new FileController(var0.getCoroutineContext());
                licenseController = new LicenseController(var0.getCoroutineContext());
                httpTTSController = new HttpTTSController(var0.getCoroutineContext());
                var19_23 = var1_1.post("/reader3/saveBookSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveBookSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveBookSources");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveBookSources\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getBookSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getBookSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/getBookSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/getBookSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getBookSources");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getBookSources\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/getBookSources");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/getBookSources\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteAllBookSources");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteAllBookSources\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteBookSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteBookSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteBookSources");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteBookSources\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/readSourceFile");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/readSourceFile\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveFromRemoteSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveFromRemoteSource\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/setAsDefaultBookSources");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/setAsDefaultBookSources\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteUserBookSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteUserBookSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteBookSourcesFile");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteBookSourcesFile\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getBookshelf");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getBookshelf\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getShelfBook");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getShelfBook\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveBook");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveBook\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteBook");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteBook\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteBooks");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteBooks\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/getInvalidBookSources");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/getInvalidBookSources\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/exploreBook");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/exploreBook\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/exploreBook");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/exploreBook\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/searchBook");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/searchBook\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/searchBook");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/searchBook\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/searchBookMulti");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/searchBookMulti\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/searchBookMulti");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/searchBookMulti\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/searchBookMultiSSE");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/searchBookMultiSSE\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getBookInfo");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getBookInfo\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/getBookInfo");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/getBookInfo\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getChapterList");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getChapterList\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/getChapterList");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/getChapterList\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getBookContent");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getBookContent\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/getBookContent");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/getBookContent\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveBookContent");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveBookContent\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveBookProgress");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveBookProgress\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/cover");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/cover\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/searchBookSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/searchBookSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/searchBookSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/searchBookSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getAvailableBookSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getAvailableBookSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/getAvailableBookSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/getAvailableBookSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/searchBookSourceSSE");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/searchBookSourceSSE\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/setBookSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/setBookSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/setBookSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/setBookSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveBookGroupId");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveBookGroupId\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/addBookGroupMulti");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/addBookGroupMulti\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/removeBookGroupMulti");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/removeBookGroupMulti\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/importBookPreview");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/importBookPreview\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/refreshLocalBook");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/refreshLocalBook\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getTxtTocRules");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getTxtTocRules\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/getChapterListByRule");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/getChapterListByRule\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getBookGroups");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getBookGroups\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveBookGroup");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveBookGroup\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteBookGroup");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteBookGroup\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveBookGroupOrder");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveBookGroupOrder\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/bookSourceDebugSSE");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/bookSourceDebugSSE\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/cacheBookSSE");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/cacheBookSSE\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/cacheBookOnServer");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/cacheBookOnServer\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getShelfBookWithCacheInfo");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getShelfBookWithCacheInfo\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteBookCache");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteBookCache\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/exportBook");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/exportBook\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/exportBook");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/exportBook\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/searchBookContent");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/searchBookContent\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/searchBookContent");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/searchBookContent\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/backupToMongodb");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/backupToMongodb\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/restoreFromMongodb");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/restoreFromMongodb\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/book/saveBookConfig");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/book/saveBookConfig\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/book/tts");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/book/tts\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/book/tts");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/book/tts\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/uploadFile");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/uploadFile\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteFile");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteFile\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/login");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/login\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/logout");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/logout\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getUserInfo");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getUserInfo\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveUserConfig");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveUserConfig\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getUserConfig");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getUserConfig\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getUserList");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getUserList\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteUsers");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteUsers\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/clearInactiveUsers");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/clearInactiveUsers\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/addUser");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/addUser\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/resetPassword");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/resetPassword\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/updateUser");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/updateUser\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/user/downloadBackupFile");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/user/downloadBackupFile\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getLicense");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getLicense\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/importLicense");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/importLicense\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/generateKeys");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/generateKeys\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/generateKeys");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/generateKeys\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/generateLicense");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/generateLicense\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/generateLicense");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/generateLicense\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/isHostValid");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/isHostValid\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/isHostValid");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/isHostValid\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/activateLicense");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/activateLicense\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/isLicenseValid");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/isLicenseValid\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/isLicenseValid");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/isLicenseValid\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/decryptLicense");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/decryptLicense\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/sendCodeToEmail");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/sendCodeToEmail\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/supplyLicense");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/supplyLicense\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/backupToWebdav");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/backupToWebdav\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getRssSources");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getRssSources\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveRssSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveRssSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveRssSources");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveRssSources\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteRssSource");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteRssSource\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getRssArticles");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getRssArticles\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/getRssArticles");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/getRssArticles\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getRssContent");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getRssContent\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/getRssContent");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/getRssContent\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getReplaceRules");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getReplaceRules\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveReplaceRule");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveReplaceRule\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveReplaceRules");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveReplaceRules\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteReplaceRule");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteReplaceRule\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteReplaceRules");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteReplaceRules\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/getBookmarks");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/getBookmarks\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveBookmark");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveBookmark\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/saveBookmarks");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/saveBookmarks\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteBookmark");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteBookmark\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/deleteBookmarks");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/deleteBookmarks\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/file/list");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/file/list\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/file/get");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/file/get\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/file/save");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/file/save\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/file/mkdir");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/file/mkdir\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/file/download");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/file/download\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/file/upload");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/file/upload\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/file/delete");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/file/delete\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/file/deleteMulti");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/file/deleteMulti\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/file/importPreview");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/file/importPreview\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/file/restore");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/file/restore\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/file/parse");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/file/parse\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/file/parse");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/file/parse\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.get("/reader3/httpTTS/list");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.get(\"/reader3/httpTTS/list\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/httpTTS/save");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/httpTTS/save\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/httpTTS/saveMulti");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/httpTTS/saveMulti\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/httpTTS/delete");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/httpTTS/delete\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                var19_23 = var1_1.post("/reader3/httpTTS/deleteMulti");
                Intrinsics.checkNotNullExpressionValue((Object)var19_23, (String)"router.post(\"/reader3/httpTTS/deleteMulti\")");
                var0.coroutineHandler(var19_23, (Function2)new /* Unavailable Anonymous Inner Class!! */);
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public Object setupPort(@NotNull Continuation<? super Unit> $completion) {
        return YueduApi.setupPort$suspendImpl((YueduApi)this, $completion);
    }

    static /* synthetic */ Object setupPort$suspendImpl(YueduApi this_, Continuation $completion) {
        YueduApiKt.access$getLogger$p().info("port: {}", (Object)Boxing.boxInt((int)this_.getPort()));
        Object object = this_.env;
        if (object == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"env");
            throw null;
        }
        Integer serverPort = (Integer)object.getProperty("reader.server.port", Integer.TYPE);
        YueduApiKt.access$getLogger$p().info("serverPort: {}", (Object)serverPort);
        if (serverPort != null && ((Number)(object = serverPort)).intValue() > 0) {
            object = serverPort;
            this_.setPort(((Number)object).intValue());
        }
        return Unit.INSTANCE;
    }

    @Nullable
    public Object migration(@NotNull Continuation<? super Unit> $completion) {
        return YueduApi.migration$suspendImpl((YueduApi)this, $completion);
    }

    static /* synthetic */ Object migration$suspendImpl(YueduApi this_, Continuation $completion) {
        try {
            File storageDir = new File(ExtKt.getWorkDir((String)"storage"));
            String[] stringArray = new String[]{"storage", "data", "default"};
            File dataDir = new File(ExtKt.getWorkDir((String[])stringArray));
            if (!storageDir.exists()) {
                dataDir.mkdirs();
            } else if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Unit.INSTANCE;
    }

    @NotNull
    public String getContextPath() {
        Object object = this.env;
        if (object == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"env");
            throw null;
        }
        String contextPath = (String)object.getProperty("reader.server.contextPath", String.class);
        object = contextPath;
        boolean bl = false;
        boolean bl2 = false;
        if (!(object == null || object.length() == 0)) {
            object = contextPath;
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"contextPath");
            return object;
        }
        return "";
    }

    public void started() {
        SpringContextUtils.getApplicationContext().publishEvent((ApplicationEvent)new SpringEvent((Object)this, "READY", ""));
    }

    public void onStartError() {
        YueduApiKt.access$getLogger$p().error("\u5e94\u7528\u542f\u52a8\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5" + this.getPort() + "\u7aef\u53e3\u662f\u5426\u88ab\u5360\u7528");
        SpringContextUtils.getApplicationContext().publishEvent((ApplicationEvent)new SpringEvent((Object)this, "START_ERROR", "\u5e94\u7528\u542f\u52a8\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5" + this.getPort() + "\u7aef\u53e3\u662f\u5426\u88ab\u5360\u7528"));
    }

    public void onHandlerError(@NotNull RoutingContext ctx, @NotNull Exception error2) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        Intrinsics.checkNotNullParameter((Object)error2, (String)"error");
        ReturnData returnData = new ReturnData();
        YueduApiKt.access$getLogger$p().error("onHandlerError: ", (Throwable)error2);
        if (!ctx.response().headWritten()) {
            VertExtKt.success((RoutingContext)ctx, (Object)returnData.setErrorMsg(error2.toString()));
        } else {
            ctx.response().end(error2.toString());
        }
    }

    /*
     * Unable to fully structure code
     */
    private final Object getSystemInfo(RoutingContext var1_1, Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getSystemInfo.1)) ** GOTO lbl-1000
        var19_3 = var2_2;
        if ((var19_3.label & -2147483648) != 0) {
            var19_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var20_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                systemFont = System.getProperty("reader.system.fonts");
                freeMemory = "" + Runtime.getRuntime().freeMemory() / (long)1024 / (long)1024 + 'M';
                totalMemory = "" + Runtime.getRuntime().totalMemory() / (long)1024 / (long)1024 + 'M';
                maxMemory = "" + Runtime.getRuntime().maxMemory() / (long)1024 / (long)1024 + 'M';
                userController = new UserController(this.getCoroutineContext());
                dayLoginUser = new Ref.IntRef();
                sevenDayLoginUser = new Ref.IntRef();
                monthLoginUser = new Ref.IntRef();
                keepUser = new Ref.IntRef();
                dayRegisterUser = new Ref.IntRef();
                sevenDayRegisterUser = new Ref.IntRef();
                monthRegisterUser = new Ref.IntRef();
                calendar = Calendar.getInstance();
                calendar.set(5, 1);
                calendar.set(11, 0);
                calendar.set(12, 0);
                calendar.set(13, 0);
                calendar.set(14, 0);
                calendar.getTimeInMillis();
                $continuation.L$0 = returnData;
                $continuation.L$1 = systemFont;
                $continuation.L$2 = freeMemory;
                $continuation.L$3 = totalMemory;
                $continuation.L$4 = maxMemory;
                $continuation.L$5 = dayLoginUser;
                $continuation.L$6 = sevenDayLoginUser;
                $continuation.L$7 = monthLoginUser;
                $continuation.L$8 = keepUser;
                $continuation.L$9 = dayRegisterUser;
                $continuation.L$10 = sevenDayRegisterUser;
                $continuation.L$11 = monthRegisterUser;
                $continuation.label = 1;
                v0 = userController.forEachUser((Function3)new /* Unavailable Anonymous Inner Class!! */, (Continuation)$continuation);
                if (v0 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl65
            }
            case 1: {
                monthRegisterUser = (Ref.IntRef)$continuation.L$11;
                sevenDayRegisterUser = (Ref.IntRef)$continuation.L$10;
                dayRegisterUser = (Ref.IntRef)$continuation.L$9;
                keepUser = (Ref.IntRef)$continuation.L$8;
                monthLoginUser = (Ref.IntRef)$continuation.L$7;
                sevenDayLoginUser = (Ref.IntRef)$continuation.L$6;
                dayLoginUser = (Ref.IntRef)$continuation.L$5;
                maxMemory = (String)$continuation.L$4;
                totalMemory = (String)$continuation.L$3;
                freeMemory = (String)$continuation.L$2;
                systemFont = (String)$continuation.L$1;
                returnData = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl65:
                // 2 sources

                var17_20 = new Pair[]{TuplesKt.to((Object)"fonts", (Object)systemFont), TuplesKt.to((Object)"freeMemory", (Object)freeMemory), TuplesKt.to((Object)"totalMemory", (Object)totalMemory), TuplesKt.to((Object)"maxMemory", (Object)maxMemory), TuplesKt.to((Object)"dayRegisterUser", (Object)Boxing.boxInt((int)dayRegisterUser.element)), TuplesKt.to((Object)"dayLoginUser", (Object)Boxing.boxInt((int)dayLoginUser.element)), TuplesKt.to((Object)"sevenDayRegisterUser", (Object)Boxing.boxInt((int)sevenDayRegisterUser.element)), TuplesKt.to((Object)"sevenDayLoginUser", (Object)Boxing.boxInt((int)sevenDayLoginUser.element)), TuplesKt.to((Object)"monthRegisterUser", (Object)Boxing.boxInt((int)monthRegisterUser.element)), TuplesKt.to((Object)"monthLoginUser", (Object)Boxing.boxInt((int)monthLoginUser.element)), TuplesKt.to((Object)"keepUser", (Object)Boxing.boxInt((int)keepUser.element))};
                return ReturnData.setData$default((ReturnData)returnData, (Object)MapsKt.mapOf((Pair[])var17_20), null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Scheduled(cron="0 0/10 * * * ?")
    public void shelfUpdateJob() {
        AppConfig appConfig = this.appConfig;
        if (appConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"appConfig");
            throw null;
        }
        if (appConfig.getShelfUpdateInteval() <= 0) {
            return;
        }
        Calendar now = Calendar.getInstance();
        int hour = now.get(11);
        int munite = now.get(12);
        int muniteFromToday = hour * 60 + munite;
        AppConfig appConfig2 = this.appConfig;
        if (appConfig2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"appConfig");
            throw null;
        }
        if (muniteFromToday % appConfig2.getShelfUpdateInteval() != 0) {
            return;
        }
        MDC.put((String)"traceId", (String)ExtKt.getTraceId());
        BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
    }

    @Scheduled(cron="0 0/10 * * * ?")
    public void remoteBookSourceSubUpdateJob() {
        AppConfig appConfig = this.appConfig;
        if (appConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"appConfig");
            throw null;
        }
        if (appConfig.getRemoteBookSourceUpdateInterval() <= 0) {
            return;
        }
        Calendar now = Calendar.getInstance();
        int hour = now.get(11);
        int munite = now.get(12);
        int muniteFromToday = hour * 60 + munite;
        AppConfig appConfig2 = this.appConfig;
        if (appConfig2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"appConfig");
            throw null;
        }
        if (muniteFromToday % appConfig2.getRemoteBookSourceUpdateInterval() != 0) {
            return;
        }
        MDC.put((String)"traceId", (String)ExtKt.getTraceId());
        BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
    }

    @Scheduled(cron="0 59 23 * * ?")
    public void clearUser() {
        block6: {
            block5: {
                AppConfig appConfig = this.appConfig;
                if (appConfig == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"appConfig");
                    throw null;
                }
                if (appConfig.getAutoClearInactiveUser() <= 0) break block5;
                appConfig = this.appConfig;
                if (appConfig == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"appConfig");
                    throw null;
                }
                if (appConfig.getSecure()) break block6;
            }
            return;
        }
        MDC.put((String)"traceId", (String)ExtKt.getTraceId());
        BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
    }

    @Scheduled(cron="0 50 23 * * ?")
    public void autoBackup() {
        AppConfig appConfig = this.appConfig;
        if (appConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"appConfig");
            throw null;
        }
        if (!appConfig.getAutoBackupUserData()) {
            return;
        }
        MDC.put((String)"traceId", (String)ExtKt.getTraceId());
        BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
    }

    @Scheduled(cron="0 0 2 * * ?")
    public void autoGC() {
        System.gc();
    }

    @Scheduled(cron="0 4/15 7-23 * * ?")
    public void checkLicense() {
        License license = ExtKt.getInstalledLicense((boolean)true);
        if ("default".equals(license.getType())) {
            return;
        }
        MDC.put((String)"traceId", (String)ExtKt.getTraceId());
        BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
    }

    private static final void initRouter$lambda-0(Ref.ObjectRef $dataDir, RoutingContext it) {
        File filePath;
        Intrinsics.checkNotNullParameter((Object)$dataDir, (String)"$dataDir");
        String string = it.request().path();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.request().path()");
        String path = StringsKt.replace((String)string, (String)"/book-assets/", (String)"/", (boolean)true);
        string = URIDecoder.decodeURIComponent((String)path, (boolean)false);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"decodeURIComponent(path, false)");
        path = string;
        if ((StringsKt.endsWith((String)path, (String)"html", (boolean)true) || StringsKt.endsWith((String)path, (String)"htm", (boolean)true)) && (filePath = new File(Intrinsics.stringPlus((String)((String)$dataDir.element), (Object)path))).exists()) {
            String string2 = filePath.toString();
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"filePath.toString()");
            BookConfig.INSTANCE.injectJavascriptToEpubChapter(string2);
        }
        it.next();
    }

    private static final void initRouter$lambda-1(Ref.ObjectRef $dataDir, RoutingContext it) {
        File filePath;
        Intrinsics.checkNotNullParameter((Object)$dataDir, (String)"$dataDir");
        String string = it.request().path();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.request().path()");
        String path = StringsKt.replace((String)string, (String)"/epub/", (String)"/", (boolean)true);
        string = URLDecoder.decode(path, "UTF-8");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"decode(path, \"UTF-8\")");
        path = string;
        if (StringsKt.endsWith((String)path, (String)"html", (boolean)true) && (filePath = new File(Intrinsics.stringPlus((String)((String)$dataDir.element), (Object)path))).exists()) {
            String string2 = filePath.toString();
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"filePath.toString()");
            BookConfig.INSTANCE.injectJavascriptToEpubChapter(string2);
        }
        it.next();
    }

    private static final void initRouter$lambda-2(RoutingContext it) {
        String string = it.request().path();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.request().path()");
        if (StringsKt.endsWith$default((String)string, (String)"/simple-web", (boolean)false, (int)2, null)) {
            HttpServerResponse httpServerResponse = it.response();
            string = URLDecoder.decode(it.request().absoluteURI(), "UTF-8");
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"decode(it.request().absoluteURI(), \"UTF-8\")");
            httpServerResponse.putHeader("Location", StringsKt.replace$default((String)string, (String)"/simple-web", (String)"/simple-web/", (boolean)false, (int)4, null)).setStatusCode(302).end();
        } else {
            it.next();
        }
    }

    private static final void initRouter$lambda-3(RoutingContext it) {
        License license = ExtKt.getInstalledLicense$default((boolean)false, (int)1, null);
        long simpleWebExpiredAt = 0L;
        String string = it.request().host();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.request().host()");
        if (license.validHost(string)) {
            simpleWebExpiredAt = license.getSimpleWebExpiredAt();
        }
        if (simpleWebExpiredAt != 0L && simpleWebExpiredAt < System.currentTimeMillis()) {
            it.response().putHeader("content-type", "text/html; charset=UTF-8").setStatusCode(403).end("<html><head><title>\u672a\u6fc0\u6d3b\u8be5\u529f\u80fd</title></head><body><div style='text-align: center;padding: 30px 0;'>\u672a\u6fc0\u6d3b\u8be5\u529f\u80fd\uff0c\u8bf7\u52a0<a href='https://t.me/+pQ8HDlANPZ84ZWNl'>TG\u7fa4</a>\u6fc0\u6d3b</div></body></html>");
        } else {
            it.next();
        }
    }

    public static final /* synthetic */ Object access$getSystemInfo(YueduApi $this, RoutingContext context, Continuation $completion) {
        return $this.getSystemInfo(context, $completion);
    }

    public static final /* synthetic */ AppConfig access$getAppConfig$p(YueduApi $this) {
        return $this.appConfig;
    }
}

