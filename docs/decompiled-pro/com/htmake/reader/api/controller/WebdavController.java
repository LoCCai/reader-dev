/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.api.ReturnData
 *  com.htmake.reader.api.controller.BaseController
 *  com.htmake.reader.api.controller.BookController
 *  com.htmake.reader.api.controller.WebdavController
 *  com.htmake.reader.api.controller.WebdavController$backupToWebdav$1
 *  com.htmake.reader.api.controller.WebdavController$webdavList$formatter$1
 *  com.htmake.reader.api.controller.WebdavController$webdavUpload$1
 *  com.htmake.reader.api.controller.WebdavControllerKt
 *  com.htmake.reader.entity.User
 *  com.htmake.reader.utils.ExtKt
 *  com.htmake.reader.utils.VertExtKt
 *  io.legado.app.utils.EncoderUtils
 *  io.vertx.core.http.HttpServerResponse
 *  io.vertx.core.json.JsonObject
 *  io.vertx.ext.web.Route
 *  io.vertx.ext.web.Router
 *  io.vertx.ext.web.RoutingContext
 *  kotlin.Metadata
 *  kotlin.ResultKt
 *  kotlin.Unit
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.CoroutineContext
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.coroutines.jvm.internal.Boxing
 *  kotlin.io.FilesKt
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlin.jvm.internal.StringCompanionObject
 *  kotlin.jvm.internal.TypeIntrinsics
 *  kotlin.text.StringsKt
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
import com.htmake.reader.api.controller.BookController;
import com.htmake.reader.api.controller.WebdavController;
import com.htmake.reader.api.controller.WebdavControllerKt;
import com.htmake.reader.entity.User;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.VertExtKt;
import io.legado.app.utils.EncoderUtils;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.slf4j.MDCContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u001c\u0010\u0006\u001a\u0018\u0012\u0004\u0012\u00020\b\u0012\b\u0012\u00060\tj\u0002`\n\u0012\u0004\u0012\u00020\u000b0\u0007\u00a2\u0006\u0002\u0010\fJ\u0019\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\bJ\u0019\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2={"Lcom/htmake/reader/api/controller/WebdavController;", "Lcom/htmake/reader/api/controller/BaseController;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "router", "Lio/vertx/ext/web/Router;", "onHandlerError", "Lkotlin/Function2;", "Lio/vertx/ext/web/RoutingContext;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "", "(Lkotlin/coroutines/CoroutineContext;Lio/vertx/ext/web/Router;Lkotlin/jvm/functions/Function2;)V", "backupToWebdav", "Lcom/htmake/reader/api/ReturnData;", "context", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkAuthorization", "", "webdavCopy", "webdavDelete", "webdavDownload", "webdavList", "webdavLock", "webdavMkdir", "webdavMove", "webdavUnLock", "webdavUpload", "reader-pro"})
public final class WebdavController
extends BaseController {
    public WebdavController(@NotNull CoroutineContext coroutineContext, @NotNull Router router, @NotNull Function2<? super RoutingContext, ? super Exception, Unit> onHandlerError) {
        Intrinsics.checkNotNullParameter((Object)coroutineContext, (String)"coroutineContext");
        Intrinsics.checkNotNullParameter((Object)router, (String)"router");
        Intrinsics.checkNotNullParameter(onHandlerError, (String)"onHandlerError");
        super(coroutineContext);
        Route route = router.route("/reader3/webdav*");
        Intrinsics.checkNotNullExpressionValue((Object)route, (String)"router.route(\"/reader3/webdav*\")");
        VertExtKt.globalHandler((Route)route, arg_0 -> WebdavController._init_$lambda-1(this, onHandlerError, arg_0));
    }

    public final boolean checkAuthorization(@NotNull RoutingContext context) {
        String[] authorization;
        block13: {
            block12: {
                Intrinsics.checkNotNullParameter((Object)context, (String)"context");
                if (!this.getAppConfig().getSecure()) {
                    return true;
                }
                authorization = context.request().getHeader("Authorization");
                WebdavControllerKt.access$getLogger$p().info("authorization: {}", (Object)authorization);
                if (authorization == null) break block12;
                CharSequence charSequence = authorization;
                charSequence = charSequence;
                boolean bl = false;
                if (!(charSequence.length() == 0)) break block13;
            }
            return false;
        }
        String[] stringArray = authorization;
        List auth = StringsKt.split$default((CharSequence)EncoderUtils.base64Decode$default((EncoderUtils)EncoderUtils.INSTANCE, (String)StringsKt.replace((String)stringArray, (String)"Basic ", (String)"", (boolean)true), (int)0, (int)2, null), (String[])(stringArray = new String[]{":"}), (boolean)false, (int)2, (int)2, null);
        if (auth.size() < 2) {
            return false;
        }
        String username = (String)auth.get(0);
        String password = (String)auth.get(1);
        boolean bl = false;
        Map userMap = new LinkedHashMap();
        Object object = new String[]{"data", "users"};
        JsonObject userMapJson = ExtKt.asJsonObject((Object)ExtKt.getStorage$default((String[])object, null, (int)2, null));
        if (userMapJson != null) {
            object = userMapJson.getMap();
            if (object == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.Map<kotlin.String, kotlin.Any>>");
            }
            userMap = TypeIntrinsics.asMutableMap((Object)object);
        }
        Map map = userMap;
        Object v = null;
        boolean bl2 = false;
        Map map2 = map;
        if (map2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
        }
        Map existedUser = map2.getOrDefault(username, v);
        if (existedUser == null) {
            return false;
        }
        Map $this$toDataClass$iv = existedUser;
        boolean $i$f$toDataClass = false;
        Map $this$convert$iv$iv = $this$toDataClass$iv;
        boolean $i$f$convert = false;
        String json$iv$iv = $this$convert$iv$iv instanceof String ? (String)((Object)$this$convert$iv$iv) : ExtKt.getGson().toJson((Object)$this$convert$iv$iv);
        User userInfo = (User)ExtKt.getGson().fromJson(json$iv$iv, new /* Unavailable Anonymous Inner Class!! */.getType());
        if (userInfo == null) {
            return false;
        }
        String passwordEncrypted = ExtKt.genEncryptedPassword((String)password, (String)userInfo.getSalt());
        if (!Intrinsics.areEqual((Object)passwordEncrypted, (Object)userInfo.getPassword())) {
            WebdavControllerKt.access$getLogger$p().info("user: {} password error", (Object)userInfo.getUsername());
            return false;
        }
        if (!userInfo.getEnable_webdav()) {
            WebdavControllerKt.access$getLogger$p().info("user: {} enable_webdav: false", (Object)userInfo.getUsername());
            return false;
        }
        context.put("username", (Object)userInfo.getUsername());
        return true;
    }

    @Nullable
    public final Object webdavList(@NotNull RoutingContext context, @NotNull Continuation<? super Unit> $completion) {
        String home = this.getUserWebdavHome((Object)context);
        String string = context.request().path();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"context.request().path()");
        String path = StringsKt.replace((String)string, (String)"/reader3/webdav/", (String)"/", (boolean)true);
        string = URLDecoder.decode(path, "UTF-8");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"decode(path, \"UTF-8\")");
        path = string;
        File file = new File(Intrinsics.stringPlus((String)home, (Object)path));
        if (!file.exists()) {
            context.response().setStatusCode(404).end();
            return Unit.INSTANCE;
        }
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n            <D:multistatus xmlns:D=\"DAV:\">\n                %s\n            </D:multistatus>\n        ";
        Ref.ObjectRef dirResponse = new Ref.ObjectRef();
        dirResponse.element = "<D:response>\n                <D:href>%s</D:href>\n                <D:propstat>\n                    <D:status>HTTP/1.1 200 OK</D:status>\n                    <D:prop>\n                        <D:getlastmodified>%s</D:getlastmodified>\n                        <D:creationdate>%s</D:creationdate>\n                        <D:resourcetype>\n                            <D:collection />\n                        </D:resourcetype>\n                        <D:displayname>%s</D:displayname>\n                    </D:prop>\n                </D:propstat>\n            </D:response>\n        ";
        Ref.ObjectRef fileResponse = new Ref.ObjectRef();
        fileResponse.element = "<D:response>\n                <D:href>%s</D:href>\n                <D:propstat>\n                    <D:status>HTTP/1.1 200 OK</D:status>\n                    <D:prop>\n                        <D:getlastmodified>%s</D:getlastmodified>\n                        <D:creationdate>%s</D:creationdate>\n                        <D:resourcetype />\n                        <D:displayname>%s</D:displayname>\n                        <D:getcontentlength>%s</D:getcontentlength>\n                        <D:getcontenttype>%s</D:getcontenttype>\n                    </D:prop>\n                </D:propstat>\n            </D:response>\n        ";
        StringCompanionObject fileUrl = null;
        fileUrl = context.request().absoluteURI();
        webdavList.formatter.1 formatter2 = null;
        formatter2 = new /* Unavailable Anonymous Inner Class!! */;
        Object response2 = null;
        response2 = "";
        if (file.isFile()) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Object[] objectArray = new Object[1];
            Function3 function3 = (Function3)formatter2;
            StringCompanionObject stringCompanionObject2 = fileUrl;
            Intrinsics.checkNotNullExpressionValue((Object)stringCompanionObject2, (String)"fileUrl");
            objectArray[0] = function3.invoke((Object)file, (Object)stringCompanionObject2, (Object)Boxing.boxBoolean((boolean)true));
            boolean bl = false;
            String string2 = String.format(xml, Arrays.copyOf(objectArray, objectArray.length));
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"java.lang.String.format(format, *args)");
            response2 = string2;
            context.response().setStatusCode(207).end((String)response2);
            return Unit.INSTANCE;
        }
        if (file.isDirectory()) {
            StringCompanionObject stringCompanionObject = fileUrl;
            Intrinsics.checkNotNullExpressionValue((Object)stringCompanionObject, (String)"fileUrl");
            fileUrl = StringsKt.endsWith$default((String)stringCompanionObject, (String)"/", (boolean)false, (int)2, null) ? fileUrl : Intrinsics.stringPlus((String)fileUrl, (Object)"/");
            Function3 function3 = (Function3)formatter2;
            stringCompanionObject = fileUrl;
            Intrinsics.checkNotNullExpressionValue((Object)stringCompanionObject, (String)"fileUrl");
            response2 = function3.invoke((Object)file, (Object)stringCompanionObject, (Object)Boxing.boxBoolean((boolean)false));
            stringCompanionObject = file.listFiles();
            Intrinsics.checkNotNullExpressionValue((Object)stringCompanionObject, (String)"file.listFiles()");
            Object[] $this$forEach$iv = (Object[])stringCompanionObject;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                File it = (File)element$iv;
                boolean bl = false;
                String fileName = URLEncoder.encode(it.getName(), "UTF-8");
                String string3 = (String)response2;
                Function3 function32 = (Function3)formatter2;
                Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                response2 = Intrinsics.stringPlus((String)string3, (Object)function32.invoke((Object)it, (Object)Intrinsics.stringPlus((String)fileUrl, (Object)fileName), (Object)Boxing.boxBoolean((boolean)true)));
            }
            stringCompanionObject = StringCompanionObject.INSTANCE;
            Object[] objectArray = new Object[]{response2};
            boolean bl = false;
            String string4 = String.format(xml, Arrays.copyOf(objectArray, objectArray.length));
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"java.lang.String.format(format, *args)");
            response2 = string4;
            context.response().setStatusCode(207).end((String)response2);
            return Unit.INSTANCE;
        }
        context.response().setStatusCode(404).end();
        return Unit.INSTANCE;
    }

    @Nullable
    public final Object webdavMkdir(@NotNull RoutingContext context, @NotNull Continuation<? super Unit> $completion) {
        String home = this.getUserWebdavHome((Object)context);
        String string = context.request().path();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"context.request().path()");
        String path = StringsKt.replace((String)string, (String)"/reader3/webdav/", (String)"/", (boolean)true);
        string = URLDecoder.decode(path, "UTF-8");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"decode(path, \"UTF-8\")");
        path = string;
        File file = new File(Intrinsics.stringPlus((String)home, (Object)path));
        if (file.exists()) {
            context.response().setStatusCode(201).end();
            return Unit.INSTANCE;
        }
        try {
            file.mkdirs();
            context.response().setStatusCode(201).end();
        }
        catch (Exception e) {
            context.response().setStatusCode(500).end();
        }
        return Unit.INSTANCE;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object webdavUpload(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof webdavUpload.1)) ** GOTO lbl-1000
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
                home = this.getUserWebdavHome((Object)context);
                var5_7 = context.request().path();
                Intrinsics.checkNotNullExpressionValue((Object)var5_7, (String)"context.request().path()");
                path = StringsKt.replace((String)var5_7, (String)"/reader3/webdav/", (String)"/", (boolean)true);
                var5_7 = URLDecoder.decode(path, "UTF-8");
                Intrinsics.checkNotNullExpressionValue((Object)var5_7, (String)"decode(path, \"UTF-8\")");
                path = var5_7;
                file = new File(Intrinsics.stringPlus((String)home, (Object)path));
                if (!file.getParentFile().exists()) {
                    context.response().setStatusCode(409).end();
                    return Unit.INSTANCE;
                }
                if (file.isDirectory()) {
                    context.response().setStatusCode(405).end();
                    return Unit.INSTANCE;
                }
                if (file.exists()) {
                    file.delete();
                }
                var6_9 /* !! */  = context.getBody().getBytes();
                Intrinsics.checkNotNullExpressionValue((Object)var6_9 /* !! */ , (String)"context.getBody().getBytes()");
                FilesKt.writeBytes((File)file, (byte[])var6_9 /* !! */ );
                var6_9 /* !! */  = (byte[])file.toString();
                Intrinsics.checkNotNullExpressionValue((Object)var6_9 /* !! */ , (String)"file.toString()");
                if (StringsKt.indexOf$default((CharSequence)((CharSequence)var6_9 /* !! */ ), (String)"bookProgress", (int)0, (boolean)false, (int)6, null) <= 0) ** GOTO lbl53
                var6_9 /* !! */  = (byte[])file.toString();
                Intrinsics.checkNotNullExpressionValue((Object)var6_9 /* !! */ , (String)"file.toString()");
                if (StringsKt.indexOf$default((CharSequence)((CharSequence)var6_9 /* !! */ ), (String)".json", (int)0, (boolean)false, (int)6, null) <= 0) ** GOTO lbl53
                userNameSpace = this.getUserNameSpace((RoutingContext)context);
                $continuation.L$0 = context;
                $continuation.label = 1;
                v0 = new BookController(this.getCoroutineContext()).syncBookProgressFromWebdav((Object)file, userNameSpace, (Continuation)$continuation);
                ** if (v0 != var9_5) goto lbl46
lbl45:
                // 1 sources

                return var9_5;
lbl46:
                // 1 sources

                ** GOTO lbl53
            }
            case 1: {
                var1_1 = (RoutingContext)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure((Object)$result);
                    v0 = $result;
lbl53:
                    // 4 sources

                    var1_1.response().setStatusCode(201).end();
                }
                catch (Exception var6_10) {
                    var1_1.response().setStatusCode(500).end();
                }
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public final Object webdavDownload(@NotNull RoutingContext context, @NotNull Continuation<? super Unit> $completion) {
        String home = this.getUserWebdavHome((Object)context);
        String string = context.request().path();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"context.request().path()");
        String path = StringsKt.replace((String)string, (String)"/reader3/webdav/", (String)"/", (boolean)true);
        string = URLDecoder.decode(path, "UTF-8");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"decode(path, \"UTF-8\")");
        path = string;
        File file = new File(Intrinsics.stringPlus((String)home, (Object)path));
        if (!file.exists()) {
            context.response().setStatusCode(404).end();
            return Unit.INSTANCE;
        }
        if (file.isDirectory()) {
            context.response().setStatusCode(405).end();
            return Unit.INSTANCE;
        }
        HttpServerResponse httpServerResponse = context.response().putHeader("Cache-Control", "86400").putHeader("Content-Disposition", Intrinsics.stringPlus((String)"attachment; filename=", (Object)URLEncoder.encode(file.getName(), "UTF-8"))).sendFile(file.toString());
        if (httpServerResponse == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return httpServerResponse;
        }
        return Unit.INSTANCE;
    }

    @Nullable
    public final Object webdavDelete(@NotNull RoutingContext context, @NotNull Continuation<? super Unit> $completion) {
        String home = this.getUserWebdavHome((Object)context);
        String string = context.request().path();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"context.request().path()");
        String path = StringsKt.replace((String)string, (String)"/reader3/webdav/", (String)"/", (boolean)true);
        string = URLDecoder.decode(path, "UTF-8");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"decode(path, \"UTF-8\")");
        path = string;
        File file = new File(Intrinsics.stringPlus((String)home, (Object)path));
        if (!file.exists()) {
            context.response().setStatusCode(404).end();
            return Unit.INSTANCE;
        }
        ExtKt.deleteRecursively((File)file);
        context.response().setStatusCode(200).end();
        return Unit.INSTANCE;
    }

    @Nullable
    public final Object webdavMove(@NotNull RoutingContext context, @NotNull Continuation<? super Unit> $completion) {
        File destinationFile;
        File file;
        block6: {
            block8: {
                block7: {
                    String home = this.getUserWebdavHome((Object)context);
                    String string = context.request().path();
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"context.request().path()");
                    String path = StringsKt.replace((String)string, (String)"/reader3/webdav/", (String)"/", (boolean)true);
                    string = URLDecoder.decode(path, "UTF-8");
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"decode(path, \"UTF-8\")");
                    path = string;
                    file = new File(Intrinsics.stringPlus((String)home, (Object)path));
                    if (!file.exists()) {
                        context.response().setStatusCode(412).end();
                        return Unit.INSTANCE;
                    }
                    String destination = context.request().getHeader("Destination");
                    if (destination == null) {
                        context.response().setStatusCode(400).end();
                        return Unit.INSTANCE;
                    }
                    URL destinationUrl = new URL(destination);
                    String string2 = destinationUrl.getPath();
                    String string3 = destination = string2 == null ? null : StringsKt.replace((String)string2, (String)"/reader3/webdav/", (String)"/", (boolean)true);
                    if (destination == null) {
                        context.response().setStatusCode(400).end();
                        return Unit.INSTANCE;
                    }
                    String overwrite = context.request().getHeader("Overwrite");
                    destinationFile = new File(Intrinsics.stringPlus((String)home, (Object)URLDecoder.decode(destination, "UTF-8")));
                    if (!destinationFile.exists()) break block6;
                    if (overwrite == null) break block7;
                    CharSequence charSequence = overwrite;
                    charSequence = charSequence;
                    boolean bl = false;
                    if (!(charSequence.length() == 0)) break block8;
                }
                context.response().setStatusCode(412).end();
                return Unit.INSTANCE;
            }
            ExtKt.deleteRecursively((File)destinationFile);
        }
        file.renameTo(destinationFile);
        context.response().setStatusCode(201).end();
        return Unit.INSTANCE;
    }

    @Nullable
    public final Object webdavCopy(@NotNull RoutingContext context, @NotNull Continuation<? super Unit> $completion) {
        File destinationFile;
        File file;
        block6: {
            block8: {
                block7: {
                    String home = this.getUserWebdavHome((Object)context);
                    String string = context.request().path();
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"context.request().path()");
                    String path = StringsKt.replace((String)string, (String)"/reader3/webdav/", (String)"/", (boolean)true);
                    string = URLDecoder.decode(path, "UTF-8");
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"decode(path, \"UTF-8\")");
                    path = string;
                    file = new File(Intrinsics.stringPlus((String)home, (Object)path));
                    if (!file.exists()) {
                        context.response().setStatusCode(412).end();
                        return Unit.INSTANCE;
                    }
                    String destination = context.request().getHeader("Destination");
                    if (destination == null) {
                        context.response().setStatusCode(400).end();
                        return Unit.INSTANCE;
                    }
                    URL destinationUrl = new URL(destination);
                    String string2 = destinationUrl.getPath();
                    String string3 = destination = string2 == null ? null : StringsKt.replace((String)string2, (String)"/reader3/webdav/", (String)"/", (boolean)true);
                    if (destination == null) {
                        context.response().setStatusCode(400).end();
                        return Unit.INSTANCE;
                    }
                    String overwrite = context.request().getHeader("Overwrite");
                    destinationFile = new File(Intrinsics.stringPlus((String)home, (Object)URLDecoder.decode(destination, "UTF-8")));
                    if (!destinationFile.exists()) break block6;
                    if (overwrite == null) break block7;
                    CharSequence charSequence = overwrite;
                    charSequence = charSequence;
                    boolean bl = false;
                    if (!(charSequence.length() == 0)) break block8;
                }
                context.response().setStatusCode(412).end();
                return Unit.INSTANCE;
            }
            ExtKt.deleteRecursively((File)destinationFile);
        }
        FilesKt.copyRecursively$default((File)file, (File)destinationFile, (boolean)false, null, (int)6, null);
        context.response().setStatusCode(201).end();
        return Unit.INSTANCE;
    }

    @Nullable
    public final Object webdavLock(@NotNull RoutingContext context, @NotNull Continuation<? super Unit> $completion) {
        String response2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n        <D:prop xmlns:D=\"DAV:\">\n            <D:lockdiscovery>\n                <D:activelock>\n                    <D:locktype>\n                        <write />\n                    </D:locktype>\n                    <D:lockscope>\n                        <exclusive />\n                    </D:lockscope>\n                    <D:locktoken>\n                        <D:href>%s</D:href>\n                    </D:locktoken>\n                    <D:lockroot>\n                        <D:href>%s</D:href>\n                    </D:lockroot>\n                    <D:depth>infinity</D:depth>\n                    <D:owner>\n                        <a:href xmlns:a=\"DAV:\">http://www.apple.com/webdav_fs/</a:href>\n                    </D:owner>\n                    <D:timeout>%s</D:timeout>\n                </D:activelock>\n            </D:lockdiscovery>\n        </D:prop>\n        ";
        UUID uUID = UUID.randomUUID();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"randomUUID()");
        String lockToken = Intrinsics.stringPlus((String)"urn:uuid:", (Object)uUID);
        String timeout = context.request().getHeader("Timeout");
        if (timeout == null) {
            timeout = "Second-3600";
        }
        String fileUrl = context.request().absoluteURI();
        HttpServerResponse httpServerResponse = context.response().putHeader("Lock-Token", lockToken).setStatusCode(200);
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objectArray = new Object[]{lockToken, fileUrl, timeout};
        boolean bl = false;
        String string = String.format(response2, Arrays.copyOf(objectArray, objectArray.length));
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"java.lang.String.format(format, *args)");
        httpServerResponse.end(string);
        return Unit.INSTANCE;
    }

    @Nullable
    public final Object webdavUnLock(@NotNull RoutingContext context, @NotNull Continuation<? super Unit> $completion) {
        String lockToken = context.request().getHeader("Lock-Token");
        if (lockToken == null) {
            context.response().setStatusCode(400).end();
            return Unit.INSTANCE;
        }
        context.response().putHeader("Lock-Token", lockToken).setStatusCode(204).end();
        return Unit.INSTANCE;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object backupToWebdav(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof backupToWebdav.1)) ** GOTO lbl-1000
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
                this = (WebdavController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (this.getAppConfig().getSecure()) {
                    userInfo = (User)context.get("userInfo");
                    if (userInfo == null) {
                        return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                    }
                    if (!userInfo.getEnable_webdav()) {
                        return returnData.setErrorMsg("\u672a\u5f00\u542fwebdav\u529f\u80fd");
                    }
                }
                bookController = new BookController(this.getCoroutineContext());
                userNameSpace = this.getUserNameSpace(context);
                $continuation.L$0 = returnData;
                $continuation.L$1 = bookController;
                $continuation.L$2 = userNameSpace;
                $continuation.label = 2;
                v1 = bookController.getLastBackFileFromWebdav(userNameSpace, (Continuation)$continuation);
                if (v1 == var9_5) {
                    return var9_5;
                }
                ** GOTO lbl51
            }
            case 2: {
                userNameSpace = (String)$continuation.L$2;
                bookController = (BookController)$continuation.L$1;
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl51:
                // 2 sources

                latestZipFilePath = (String)v1;
                $continuation.L$0 = var3_6;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.label = 3;
                v2 = bookController.saveToWebdav(userNameSpace, latestZipFilePath, (Continuation)$continuation);
                if (v2 == var9_5) {
                    return var9_5;
                }
                ** GOTO lbl64
            }
            case 3: {
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v2 = $result;
lbl64:
                // 2 sources

                if (!((Boolean)v2).booleanValue()) {
                    return var3_6.setErrorMsg("\u5907\u4efd\u5931\u8d25");
                }
                return ReturnData.setData$default((ReturnData)var3_6, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    private static final void lambda-1$lambda-0(RoutingContext $it, WebdavController this$0, Void $noName_0) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        HttpServerResponse res = $it.response();
        res.putHeader("DAV", "1,2");
        res.putHeader("Access-Control-Allow-Origin", "*");
        res.putHeader("Access-Control-Allow-Credentials", "true");
        res.putHeader("Access-Control-Expose-Headers", "DAV, content-length, Allow");
        res.putHeader("MS-Author-Via", "DAV");
        res.putHeader("Allow", "OPTIONS,DELETE,GET,PUT,PROPFIND,MKCOL,MOVE,COPY,LOCK,UNLOCK");
        if (this$0.getAppConfig().getSecure()) {
            res.putHeader("WWW-Authenticate", "Basic realm=\"Default realm\"");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final void _init_$lambda-1(WebdavController this$0, Function2 $onHandlerError, RoutingContext it) {
        String string;
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)$onHandlerError, (String)"$onHandlerError");
        it.addHeadersEndHandler(arg_0 -> WebdavController.lambda-1$lambda-0(it, this$0, arg_0));
        String rawMethod = it.request().rawMethod();
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        if (!this$0.checkAuthorization(it)) {
            String authorization;
            if (rawMethod.equals("PROPFIND") || rawMethod.equals("MKCOL") || rawMethod.equals("PUT") || rawMethod.equals("GET") || rawMethod.equals("DELETE") || rawMethod.equals("MOVE") || rawMethod.equals("COPY") || rawMethod.equals("LOCK") || rawMethod.equals("UNLOCK")) {
                it.response().setStatusCode(401).end();
                return;
            }
            if (rawMethod.equals("OPTIONS") && (authorization = it.request().getHeader("Authorization")) != null) {
                it.response().setStatusCode(401).end();
                return;
            }
        }
        if ((string = rawMethod) != null) {
            int n = -1;
            switch (string.hashCode()) {
                case 73412354: {
                    if (!string.equals("MKCOL")) break;
                    n = 1;
                    break;
                }
                case 2012838315: {
                    if (!string.equals("DELETE")) break;
                    n = 2;
                    break;
                }
                case 2372561: {
                    if (!string.equals("MOVE")) break;
                    n = 3;
                    break;
                }
                case 70454: {
                    if (!string.equals("GET")) break;
                    n = 4;
                    break;
                }
                case -210493540: {
                    if (!string.equals("PROPFIND")) break;
                    n = 5;
                    break;
                }
                case 2342187: {
                    if (!string.equals("LOCK")) break;
                    n = 6;
                    break;
                }
                case 2074485: {
                    if (!string.equals("COPY")) break;
                    n = 7;
                    break;
                }
                case -531492226: {
                    if (!string.equals("OPTIONS")) break;
                    n = 8;
                    break;
                }
                case 79599: {
                    if (!string.equals("PUT")) break;
                    n = 9;
                    break;
                }
                case -1787112636: {
                    if (!string.equals("UNLOCK")) break;
                    n = 10;
                    break;
                }
            }
            switch (n) {
                case 5: {
                    BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this$0), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
                    return;
                }
                case 1: {
                    BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this$0), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
                    return;
                }
                case 9: {
                    BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this$0), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
                    return;
                }
                case 4: {
                    BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this$0), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
                    return;
                }
                case 2: {
                    BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this$0), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
                    return;
                }
                case 3: {
                    BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this$0), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
                    return;
                }
                case 7: {
                    BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this$0), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
                    return;
                }
                case 6: {
                    BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this$0), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
                    return;
                }
                case 10: {
                    BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this$0), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
                    return;
                }
                case 8: {
                    it.response().setStatusCode(200).end();
                    return;
                }
            }
        }
        it.response().setStatusCode(405).end();
    }
}

