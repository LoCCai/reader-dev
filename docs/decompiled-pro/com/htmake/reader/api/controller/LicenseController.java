/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.api.ReturnData
 *  com.htmake.reader.api.controller.BaseController
 *  com.htmake.reader.api.controller.LicenseController
 *  com.htmake.reader.api.controller.LicenseController$backupFileNames$2
 *  com.htmake.reader.api.controller.LicenseController$importLicense$1
 *  com.htmake.reader.api.controller.LicenseController$webClient$2
 *  com.htmake.reader.api.controller.LicenseControllerKt
 *  com.htmake.reader.entity.ActiveLicense
 *  com.htmake.reader.entity.License
 *  com.htmake.reader.utils.ExtKt
 *  com.htmake.reader.utils.VertExtKt
 *  io.legado.app.utils.ACache
 *  io.legado.app.utils.Base64
 *  io.legado.app.utils.EncoderUtils
 *  io.vertx.core.http.HttpMethod
 *  io.vertx.core.json.JsonArray
 *  io.vertx.core.json.JsonObject
 *  io.vertx.ext.web.RoutingContext
 *  io.vertx.ext.web.client.WebClient
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.ResultKt
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.CoroutineContext
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.coroutines.jvm.internal.Boxing
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlinx.coroutines.BuildersKt
 *  kotlinx.coroutines.CoroutineExceptionHandler
 *  kotlinx.coroutines.CoroutineExceptionHandler$Key
 *  kotlinx.coroutines.CoroutineScope
 *  kotlinx.coroutines.Dispatchers
 *  kotlinx.coroutines.Job
 *  kotlinx.coroutines.slf4j.MDCContext
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.LicenseController;
import com.htmake.reader.api.controller.LicenseControllerKt;
import com.htmake.reader.entity.ActiveLicense;
import com.htmake.reader.entity.License;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.VertExtKt;
import io.legado.app.utils.ACache;
import io.legado.app.utils.Base64;
import io.legado.app.utils.EncoderUtils;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.slf4j.MDCContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001dJ\u0019\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010 \u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010!\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010\"\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010#\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010$\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010%\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010&\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u0011\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006'"}, d2={"Lcom/htmake/reader/api/controller/LicenseController;", "Lcom/htmake/reader/api/controller/BaseController;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "backupFileNames", "", "", "getBackupFileNames", "()[Ljava/lang/String;", "backupFileNames$delegate", "Lkotlin/Lazy;", "privateKeyContent", "tryCodeCache", "Lio/legado/app/utils/ACache;", "webClient", "Lio/vertx/ext/web/client/WebClient;", "getWebClient", "()Lio/vertx/ext/web/client/WebClient;", "webClient$delegate", "activateLicense", "Lcom/htmake/reader/api/ReturnData;", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkLicense", "", "license", "Lcom/htmake/reader/entity/License;", "(Lcom/htmake/reader/entity/License;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "decryptLicense", "generateKeys", "generateLicense", "getLicense", "importLicense", "isHostValid", "isLicenseValid", "sendCodeToEmail", "supplyLicense", "reader-pro"})
public final class LicenseController
extends BaseController {
    @NotNull
    private final Lazy webClient$delegate;
    @NotNull
    private String privateKeyContent;
    @NotNull
    private ACache tryCodeCache;
    @NotNull
    private final Lazy backupFileNames$delegate;

    public LicenseController(@NotNull CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter((Object)coroutineContext, (String)"coroutineContext");
        super(coroutineContext);
        this.webClient$delegate = LazyKt.lazy((Function0)((Function0)webClient.2.INSTANCE));
        this.privateKeyContent = "";
        this.tryCodeCache = ACache.Companion.get("tryCodeCache", 2000000L, 10000);
        this.backupFileNames$delegate = LazyKt.lazy((Function0)((Function0)backupFileNames.2.INSTANCE));
    }

    private final WebClient getWebClient() {
        Lazy lazy = this.webClient$delegate;
        boolean bl = false;
        Object object = lazy.getValue();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"<get-webClient>(...)");
        return (WebClient)object;
    }

    @NotNull
    public final String[] getBackupFileNames() {
        Lazy lazy = this.backupFileNames$delegate;
        boolean bl = false;
        return (String[])lazy.getValue();
    }

    @Nullable
    public final Object getLicense(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        ReturnData returnData = new ReturnData();
        License license = ExtKt.getInstalledLicense$default((boolean)false, (int)1, null);
        return ReturnData.setData$default((ReturnData)returnData, (Object)MapsKt.mapOf((Pair)TuplesKt.to((Object)"license", (Object)license)), null, (int)2, null);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object importLicense(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof importLicense.1)) ** GOTO lbl-1000
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
                this = (LicenseController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    VertExtKt.success((RoutingContext)context, (Object)ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528"));
                    return Unit.INSTANCE;
                }
                if (!this.checkManagerAuth(context)) {
                    VertExtKt.success((RoutingContext)context, (Object)ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_SECURE_KEY", null, (int)2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801"));
                    return Unit.INSTANCE;
                }
                var5_7 = context.getBodyAsJson().getString("content");
                content = var5_7 == null ? "" : var5_7;
                var5_7 = content;
                var6_9 = false;
                var7_11 = false;
                if (var5_7.length() == 0) {
                    VertExtKt.success((RoutingContext)context, (Object)returnData.setErrorMsg("\u8bf7\u8f93\u5165\u5bc6\u94a5"));
                    return Unit.INSTANCE;
                }
                $i$f$CoroutineExceptionHandler = false;
                var7_12 = CoroutineExceptionHandler.Key;
                exceptionHandler = (CoroutineExceptionHandler)new /* Unavailable Anonymous Inner Class!! */;
                checkUrl = new Ref.ObjectRef();
                checkUrl.element = "https://r.htmake.com/reader3/activateLicense";
                BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()).plus((CoroutineContext)exceptionHandler), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public final Object generateKeys(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        ReturnData returnData = new ReturnData();
        KeyPair keyPair = EncoderUtils.INSTANCE.generateKeys();
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"publicKey", (Object)Base64.encodeToString((byte[])keyPair.getPublic().getEncoded(), (int)2)), TuplesKt.to((Object)"privateKey", (Object)Base64.encodeToString((byte[])keyPair.getPrivate().getEncoded(), (int)2))};
        return ReturnData.setData$default((ReturnData)returnData, (Object)MapsKt.mapOf((Pair[])pairArray), null, (int)2, null);
    }

    @Nullable
    public final Object generateLicense(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        boolean bl;
        Object object;
        ReturnData returnData = new ReturnData();
        Object host = null;
        long expiredAt = 0L;
        int userMaxLimit = 0;
        boolean openApi = false;
        long simpleWebExpiredAt = 0L;
        int instances = 0;
        Object type = null;
        Object key = null;
        Object code = null;
        if (context.request().method() == HttpMethod.POST) {
            object = context.getBodyAsJson().getString("host");
            host = object == null ? "" : object;
            object = context.getBodyAsJson().getLong("expiredAt");
            expiredAt = object == null ? 0L : (Long)object;
            object = context.getBodyAsJson().getInteger("userMaxLimit");
            userMaxLimit = object == null ? 15 : (Integer)object;
            object = context.getBodyAsJson().getBoolean("openApi");
            openApi = object == null ? false : (Boolean)object;
            object = context.getBodyAsJson().getLong("simpleWebExpiredAt");
            simpleWebExpiredAt = object == null ? 0L : (Long)object;
            object = context.getBodyAsJson().getInteger("instances");
            instances = object == null ? 1 : (Integer)object;
            object = context.getBodyAsJson().getString("type");
            type = object == null ? "" : object;
            object = context.getBodyAsJson().getString("key");
            key = object == null ? "" : object;
            object = context.getBodyAsJson().getString("code");
            code = object == null ? "" : object;
        } else {
            int n;
            long l;
            boolean bl2;
            int n2;
            Object object2;
            long l2;
            Object object3 = context.queryParam("host");
            Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"context.queryParam(\"host\")");
            object = (String)CollectionsKt.firstOrNull((List)object3);
            host = object == null ? "" : object;
            object3 = context.queryParam("expiredAt");
            Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"context.queryParam(\"expiredAt\")");
            object = (String)CollectionsKt.firstOrNull((List)object3);
            if (object == null) {
                l2 = 0L;
            } else {
                object2 = object;
                bl = false;
                object3 = Boxing.boxLong((long)Long.parseLong((String)object2));
                l2 = object3 == null ? 0L : (Long)object3;
            }
            expiredAt = l2;
            object3 = context.queryParam("userMaxLimit");
            Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"context.queryParam(\"userMaxLimit\")");
            object = (String)CollectionsKt.firstOrNull((List)object3);
            if (object == null) {
                n2 = 15;
            } else {
                object2 = object;
                bl = false;
                object3 = Boxing.boxInt((int)Integer.parseInt((String)object2));
                n2 = object3 == null ? 15 : (Integer)object3;
            }
            userMaxLimit = n2;
            object3 = context.queryParam("openApi");
            Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"context.queryParam(\"openApi\")");
            object = (String)CollectionsKt.firstOrNull((List)object3);
            if (object == null) {
                bl2 = false;
            } else {
                object2 = object;
                bl = false;
                object3 = Boxing.boxBoolean((boolean)Boolean.parseBoolean((String)object2));
                bl2 = object3 == null ? false : (Boolean)object3;
            }
            openApi = bl2;
            object3 = context.queryParam("simpleWebExpiredAt");
            Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"context.queryParam(\"simpleWebExpiredAt\")");
            object = (String)CollectionsKt.firstOrNull((List)object3);
            if (object == null) {
                l = 0L;
            } else {
                object2 = object;
                bl = false;
                object3 = Boxing.boxLong((long)Long.parseLong((String)object2));
                l = object3 == null ? 0L : (Long)object3;
            }
            simpleWebExpiredAt = l;
            object3 = context.queryParam("instances");
            Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"context.queryParam(\"instances\")");
            object = (String)CollectionsKt.firstOrNull((List)object3);
            if (object == null) {
                n = 1;
            } else {
                object2 = object;
                bl = false;
                object3 = Boxing.boxInt((int)Integer.parseInt((String)object2));
                n = object3 == null ? 1 : (Integer)object3;
            }
            instances = n;
            object3 = context.queryParam("type");
            Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"context.queryParam(\"type\")");
            object = (String)CollectionsKt.firstOrNull((List)object3);
            type = object == null ? "" : object;
            object3 = context.queryParam("key");
            Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"context.queryParam(\"key\")");
            object = (String)CollectionsKt.firstOrNull((List)object3);
            key = object == null ? "" : object;
            object3 = context.queryParam("code");
            Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"context.queryParam(\"code\")");
            object = (String)CollectionsKt.firstOrNull((List)object3);
            code = object == null ? "" : object;
        }
        object = (CharSequence)host;
        boolean bl3 = false;
        boolean bl4 = false;
        if (object.length() == 0) {
            return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u57df\u540d");
        }
        if (!"Pvkp7tMQJpi4kWBE".equals(key)) {
            return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
        }
        License license = new License((String)host, userMaxLimit, expiredAt, openApi, simpleWebExpiredAt, instances, (String)type, null, null, false, null, 1408, null);
        CharSequence charSequence = (CharSequence)code;
        bl4 = false;
        bl = false;
        if (!(charSequence.length() == 0)) {
            license.setCode((String)code);
        }
        String licenseContent = ExtKt.jsonEncode$default((Object)license, (boolean)false, (int)2, null);
        CharSequence charSequence2 = this.privateKeyContent;
        bl = false;
        if (charSequence2.length() == 0) {
            String[] stringArray = new String[]{"data", "privateKey"};
            charSequence2 = ExtKt.getStorage((String[])stringArray, (String)".key");
            this.privateKeyContent = charSequence2 == null ? "" : charSequence2;
        }
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode((String)this.privateKeyContent, (int)2)));
        Intrinsics.checkNotNullExpressionValue((Object)privateKey, (String)"privateKey");
        String licenseKey = EncoderUtils.encryptSegmentByPrivateKey$default((EncoderUtils)EncoderUtils.INSTANCE, (String)licenseContent, (PrivateKey)privateKey, (int)0, (int)4, null);
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"host", (Object)host), TuplesKt.to((Object)"key", (Object)licenseKey)};
        return ReturnData.setData$default((ReturnData)returnData, (Object)MapsKt.mapOf((Pair[])pairArray), null, (int)2, null);
    }

    @Nullable
    public final Object isHostValid(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        String string;
        ReturnData returnData = new ReturnData();
        String host = null;
        if (context.request().method() == HttpMethod.POST) {
            string = context.getBodyAsJson().getString("host");
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"context.bodyAsJson.getString(\"host\")");
            host = string;
        } else {
            List list2 = context.queryParam("host");
            Intrinsics.checkNotNullExpressionValue((Object)list2, (String)"context.queryParam(\"host\")");
            string = (String)CollectionsKt.firstOrNull((List)list2);
            host = string == null ? "" : string;
        }
        License license = ExtKt.getInstalledLicense$default((boolean)false, (int)1, null);
        return ReturnData.setData$default((ReturnData)returnData, (Object)MapsKt.mapOf((Pair)TuplesKt.to((Object)"isValid", (Object)Boxing.boxBoolean((boolean)license.validHost(host)))), null, (int)2, null);
    }

    @Nullable
    public final Object decryptLicense(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        ReturnData returnData = new ReturnData();
        CharSequence charSequence = context.getBodyAsJson().getString("content");
        String content = charSequence == null ? "" : charSequence;
        charSequence = content;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence.length() == 0) {
            return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u5bc6\u94a5");
        }
        License license = ExtKt.decryptToLicense((String)content);
        if (license == null) {
            return returnData.setErrorMsg("\u5bc6\u94a5\u9519\u8bef");
        }
        return ReturnData.setData$default((ReturnData)returnData, (Object)license, null, (int)2, null);
    }

    @Nullable
    public final Object activateLicense(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        CharSequence charSequence;
        String ip;
        int n;
        int activeTimes;
        JsonArray activeLicenseList;
        License license;
        ReturnData returnData;
        block12: {
            String i2;
            block11: {
                int i2;
                returnData = new ReturnData();
                CharSequence charSequence2 = context.getBodyAsJson().getString("content");
                String content = charSequence2 == null ? "" : charSequence2;
                charSequence2 = content;
                boolean bl = false;
                boolean bl2 = false;
                if (charSequence2.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u5bc6\u94a5");
                }
                license = ExtKt.decryptToLicense((String)content);
                if (license == null) {
                    return returnData.setErrorMsg("\u5bc6\u94a5\u9519\u8bef");
                }
                if (license.getVerified()) {
                    return returnData.setErrorMsg("\u5bc6\u94a5\u5df2\u88ab\u4f7f\u7528");
                }
                String[] stringArray = new String[]{"data", "activeLicense"};
                activeLicenseList = ExtKt.asJsonArray((Object)ExtKt.getStorage$default((String[])stringArray, null, (int)2, null));
                if (activeLicenseList == null) {
                    activeLicenseList = new JsonArray();
                }
                activeTimes = 0;
                int n2 = 0;
                int n3 = activeLicenseList.size();
                if (n2 < n3) {
                    do {
                        ActiveLicense _activeLicense22;
                        if (!(_activeLicense22 = (ActiveLicense)activeLicenseList.getJsonObject(i2 = n2++).mapTo(ActiveLicense.class)).getType().equals(license.getType()) || !_activeLicense22.getCode().equals(license.getCode())) continue;
                        n = activeTimes;
                        activeTimes = n + 1;
                    } while (n2 < n3);
                }
                if (activeTimes >= license.getInstances()) {
                    return returnData.setErrorMsg("\u5bc6\u94a5\u5df2\u8d85\u8fc7\u6700\u5927\u4f7f\u7528\u6b21\u6570");
                }
                ip = null;
                ip = context.request().getHeader("X-Real-IP");
                if (ip == null) break block11;
                charSequence = ip;
                charSequence = charSequence;
                i2 = 0;
                if (!(charSequence.length() == 0)) break block12;
            }
            ip = (charSequence = context.request().remoteAddress()) == null ? "" : ((i2 = charSequence.host()) == null ? "" : i2);
        }
        license.setVerified(true);
        license.setVerifyTime(Boxing.boxLong((long)System.currentTimeMillis()));
        charSequence = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue((Object)charSequence, (String)"randomUUID().toString()");
        license.setId((String)charSequence);
        ActiveLicense i2 = license.toActiveLicense();
        boolean _activeLicense22 = false;
        n = 0;
        ActiveLicense it222 = i2;
        boolean bl = false;
        it222.setActiveOrder(activeTimes + 1);
        it222.setActiveTime(System.currentTimeMillis());
        String string = ip;
        it222.setActiveIp(string);
        it222.setActiveEmail("");
        ActiveLicense activeLicense = it222;
        String[] _activeLicense22 = JsonObject.mapFrom((Object)activeLicense);
        Intrinsics.checkNotNullExpressionValue((Object)_activeLicense22, (String)"mapFrom(activeLicense)");
        String[] activeLicenseObject = _activeLicense22;
        activeLicenseList.add((JsonObject)activeLicenseObject);
        LicenseControllerKt.access$getLogger$p().info("activeLicenseList: {}", (Object)activeLicenseList);
        _activeLicense22 = new String[]{"data", "activeLicense"};
        ExtKt.saveStorage$default((String[])_activeLicense22, (Object)activeLicenseList, (boolean)false, null, (int)12, null);
        String licenseContent = ExtKt.jsonEncode$default((Object)license, (boolean)false, (int)2, null);
        CharSequence charSequence3 = this.privateKeyContent;
        boolean it222 = false;
        if (charSequence3.length() == 0) {
            String[] it222 = new String[]{"data", "privateKey"};
            charSequence3 = ExtKt.getStorage((String[])it222, (String)".key");
            this.privateKeyContent = charSequence3 == null ? "" : charSequence3;
        }
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode((String)this.privateKeyContent, (int)2)));
        Intrinsics.checkNotNullExpressionValue((Object)privateKey, (String)"privateKey");
        String licenseKey = EncoderUtils.encryptSegmentByPrivateKey$default((EncoderUtils)EncoderUtils.INSTANCE, (String)licenseContent, (PrivateKey)privateKey, (int)0, (int)4, null);
        return ReturnData.setData$default((ReturnData)returnData, (Object)MapsKt.mapOf((Pair)TuplesKt.to((Object)"result", (Object)licenseKey)), null, (int)2, null);
    }

    @Nullable
    public final Object isLicenseValid(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        CharSequence i2;
        String ip;
        Map result2;
        int index;
        ActiveLicense activeLicense;
        JsonArray activeLicenseList;
        ReturnData returnData;
        block14: {
            Object _activeLicense2;
            block13: {
                String[] stringArray;
                String string;
                returnData = new ReturnData();
                String id = null;
                if (context.request().method() == HttpMethod.POST) {
                    string = context.getBodyAsJson().getString("id");
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"context.bodyAsJson.getString(\"id\")");
                    id = string;
                } else {
                    stringArray = context.queryParam("id");
                    Intrinsics.checkNotNullExpressionValue((Object)stringArray, (String)"context.queryParam(\"id\")");
                    string = (String)CollectionsKt.firstOrNull((List)stringArray);
                    id = string == null ? "" : string;
                }
                stringArray = new String[]{"data", "activeLicense"};
                activeLicenseList = ExtKt.asJsonArray((Object)ExtKt.getStorage$default((String[])stringArray, null, (int)2, null));
                if (activeLicenseList == null) {
                    activeLicenseList = new JsonArray();
                }
                activeLicense = null;
                index = -1;
                int n = 0;
                int n2 = activeLicenseList.size();
                if (n < n2) {
                    do {
                        int i2;
                        if (!(_activeLicense2 = (ActiveLicense)activeLicenseList.getJsonObject(i2 = n++).mapTo(ActiveLicense.class)).getId().equals(id)) continue;
                        activeLicense = _activeLicense2;
                        index = i2;
                        break;
                    } while (n < n2);
                }
                n2 = 0;
                result2 = new LinkedHashMap();
                ip = context.request().getHeader("X-Real-IP");
                if (ip == null) break block13;
                i2 = ip;
                i2 = i2;
                boolean _activeLicense2 = false;
                if (!(i2.length() == 0)) break block14;
            }
            String string = (i2 = context.request().remoteAddress()) == null ? "" : (ip = (_activeLicense2 = i2.host()) == null ? "" : _activeLicense2);
        }
        if (activeLicense == null) {
            result2.put("isValid", Boxing.boxBoolean((boolean)false));
            result2.put("errorMsg", "\u5bc6\u94a5\u672a\u6fc0\u6d3b");
        } else {
            result2.put("isValid", Boxing.boxBoolean((boolean)activeLicense.getVerified()));
            result2.put("errorMsg", activeLicense.getErrorMsg());
            if (activeLicense.getLastOnlineTime() != null) {
                long l = System.currentTimeMillis();
                Long l2 = activeLicense.getLastOnlineTime();
                Intrinsics.checkNotNull((Object)l2);
                if (l < l2 + (long)600000 && !ip.equals(activeLicense.getLastOnlineIp())) {
                    i2 = new Pair[]{TuplesKt.to((Object)"lastOnlineTime", (Object)activeLicense.getLastOnlineTime()), TuplesKt.to((Object)"lastOnlineIp", (Object)activeLicense.getLastOnlineIp())};
                    result2.put("repeat", MapsKt.mapOf((Pair[])i2));
                }
            }
            activeLicense.setLastOnlineTime(Boxing.boxLong((long)System.currentTimeMillis()));
            i2 = ip;
            activeLicense.setLastOnlineIp((String)i2);
            activeLicenseList.set(index, JsonObject.mapFrom((Object)activeLicense));
            i2 = new String[]{"data", "activeLicense"};
            ExtKt.saveStorage$default((String[])i2, (Object)activeLicenseList, (boolean)false, null, (int)12, null);
        }
        String resultContent = ExtKt.jsonEncode$default((Object)result2, (boolean)false, (int)2, null);
        CharSequence _activeLicense2 = this.privateKeyContent;
        boolean bl = false;
        if (_activeLicense2.length() == 0) {
            String[] stringArray = new String[]{"data", "privateKey"};
            _activeLicense2 = ExtKt.getStorage((String[])stringArray, (String)".key");
            this.privateKeyContent = _activeLicense2 == null ? "" : _activeLicense2;
        }
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode((String)this.privateKeyContent, (int)2)));
        Intrinsics.checkNotNullExpressionValue((Object)privateKey, (String)"privateKey");
        String resultKey = EncoderUtils.encryptSegmentByPrivateKey$default((EncoderUtils)EncoderUtils.INSTANCE, (String)resultContent, (PrivateKey)privateKey, (int)0, (int)4, null);
        return ReturnData.setData$default((ReturnData)returnData, (Object)MapsKt.mapOf((Pair)TuplesKt.to((Object)"result", (Object)resultKey)), null, (int)2, null);
    }

    @Nullable
    public final Object checkLicense(@NotNull License license, @NotNull Continuation<? super Unit> $completion) {
        boolean $i$f$CoroutineExceptionHandler = false;
        CoroutineExceptionHandler.Key key = CoroutineExceptionHandler.Key;
        CoroutineExceptionHandler exceptionHandler = (CoroutineExceptionHandler)new /* Unavailable Anonymous Inner Class!! */;
        Ref.ObjectRef checkUrl = new Ref.ObjectRef();
        checkUrl.element = Intrinsics.stringPlus((String)"https://r.htmake.com/reader3/isLicenseValid?id=", (Object)license.getId());
        Job job = BuildersKt.launch$default((CoroutineScope)((CoroutineScope)this), (CoroutineContext)new MDCContext(null, 1, null).plus((CoroutineContext)Dispatchers.getIO()).plus((CoroutineContext)exceptionHandler), null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)2, null);
        if (job == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return job;
        }
        return Unit.INSTANCE;
    }

    @Nullable
    public final Object sendCodeToEmail(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        int n;
        int n2;
        CharSequence charSequence;
        ReturnData returnData = new ReturnData();
        String email = null;
        if (context.request().method() == HttpMethod.POST) {
            charSequence = context.getBodyAsJson().getString("email");
            email = charSequence == null ? "" : charSequence;
        } else {
            List list2 = context.queryParam("email");
            Intrinsics.checkNotNullExpressionValue((Object)list2, (String)"context.queryParam(\"email\")");
            charSequence = (String)CollectionsKt.firstOrNull((List)list2);
            email = charSequence == null ? "" : charSequence;
        }
        charSequence = email;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return returnData.setErrorMsg("\u90ae\u7bb1\u9519\u8bef");
        }
        if (!ExtKt.validateEmail((String)email)) {
            return returnData.setErrorMsg("\u4ec5\u652f\u6301 163|126|qq|yahoo|sina|sohu|yeah|139|189|21cn|outlook|gmail|icloud \u7b49\u90ae\u7bb1");
        }
        String[] stringArray = new String[]{"data", "activeLicense"};
        JsonArray activeLicenseList = ExtKt.asJsonArray((Object)ExtKt.getStorage$default((String[])stringArray, null, (int)2, null));
        if (activeLicenseList == null) {
            activeLicenseList = new JsonArray();
        }
        if ((n2 = 0) < (n = activeLicenseList.size())) {
            do {
                int i;
                if (!"trial".equals(activeLicenseList.getJsonObject(i = n2++).getString("type")) || !email.equals(activeLicenseList.getJsonObject(i).getString("code"))) continue;
                return returnData.setErrorMsg("\u8be5\u90ae\u7bb1\u5df2\u88ab\u4f7f\u7528");
            } while (n2 < n);
        }
        String verifyCode = this.tryCodeCache.getAsString(email);
        CharSequence charSequence2 = verifyCode;
        boolean bl2 = false;
        int n3 = 0;
        if (!(charSequence2 == null || charSequence2.length() == 0)) {
            return returnData.setData((Object)"", "\u60a8\u7684\u9a8c\u8bc1\u7801\u4ecd\u5728\u6709\u6548\u671f\u5185\uff0c\u8bf7\u52ff\u91cd\u590d\u83b7\u53d6");
        }
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"randomUUID().toString()");
        n3 = 0;
        int n4 = 6;
        boolean bl3 = false;
        String string2 = string.substring(n3, n4);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        String code = string2;
        this.tryCodeCache.put(email, code, 900);
        ExtKt.sendEmail((String)email, (String)"Reader Kindle\u7aef\u7684\u8bd5\u7528\u7533\u8bf7\u9a8c\u8bc1", (String)("\u60a8\u6b63\u5728\u7533\u8bf7Reader Kindle\u7aef\u7684\u8bd5\u7528\uff0c\u9a8c\u8bc1\u7801\u662f: " + code + "\uff0c15\u5206\u949f\u5185\u6709\u6548\uff0c\u8bf7\u52ff\u56de\u590d"));
        return returnData.setData((Object)"", "\u8bf7\u67e5\u6536\u90ae\u4ef6");
    }

    @Nullable
    public final Object supplyLicense(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        String code;
        String email;
        ReturnData returnData;
        block6: {
            block5: {
                returnData = new ReturnData();
                String string = context.getBodyAsJson().getString("email");
                email = string == null ? "" : string;
                CharSequence charSequence = context.getBodyAsJson().getString("code");
                code = charSequence == null ? "" : charSequence;
                charSequence = email;
                boolean bl = false;
                boolean bl2 = false;
                if (charSequence.length() == 0) break block5;
                charSequence = code;
                bl = false;
                bl2 = false;
                if (!(charSequence.length() == 0)) break block6;
            }
            return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
        }
        String verifyCode = this.tryCodeCache.getAsString(email);
        this.tryCodeCache.remove(email);
        if (!code.equals(verifyCode)) {
            return returnData.setErrorMsg("\u9a8c\u8bc1\u7801\u9519\u8bef");
        }
        long l = System.currentTimeMillis() + 604800000L;
        License license = new License("*", 15, 0L, false, l, 1, "trial", null, email, false, null, 1152, null);
        String licenseContent = ExtKt.jsonEncode$default((Object)license, (boolean)false, (int)2, null);
        CharSequence charSequence = this.privateKeyContent;
        boolean bl = false;
        if (charSequence.length() == 0) {
            String[] stringArray = new String[]{"data", "privateKey"};
            charSequence = ExtKt.getStorage((String[])stringArray, (String)".key");
            this.privateKeyContent = charSequence == null ? "" : charSequence;
        }
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode((String)this.privateKeyContent, (int)2)));
        Intrinsics.checkNotNullExpressionValue((Object)privateKey, (String)"privateKey");
        String licenseKey = EncoderUtils.encryptSegmentByPrivateKey$default((EncoderUtils)EncoderUtils.INSTANCE, (String)licenseContent, (PrivateKey)privateKey, (int)0, (int)4, null);
        return ReturnData.setData$default((ReturnData)returnData, (Object)MapsKt.mapOf((Pair)TuplesKt.to((Object)"key", (Object)licenseKey)), null, (int)2, null);
    }

    public static final /* synthetic */ WebClient access$getWebClient(LicenseController $this) {
        return $this.getWebClient();
    }
}

