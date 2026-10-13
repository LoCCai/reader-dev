/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.api.controller.BaseController
 *  com.htmake.reader.api.controller.BaseController$checkAuth$1
 *  com.htmake.reader.api.controller.BaseController$getUserInfoClass$$inlined$toDataClass$1
 *  com.htmake.reader.api.controller.BaseController$limitConcurrent$2
 *  com.htmake.reader.api.controller.BaseController$limitConcurrent$3
 *  com.htmake.reader.api.controller.BaseController$saveUserSession$1
 *  com.htmake.reader.api.controller.BaseControllerKt
 *  com.htmake.reader.config.AppConfig
 *  com.htmake.reader.entity.User
 *  com.htmake.reader.utils.ExtKt
 *  com.htmake.reader.utils.SpringContextUtils
 *  io.legado.app.utils.FileUtils
 *  io.vertx.core.json.Json
 *  io.vertx.core.json.JsonObject
 *  io.vertx.ext.web.RoutingContext
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
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SpreadBuilder
 *  kotlin.jvm.internal.TypeIntrinsics
 *  kotlin.text.StringsKt
 *  kotlinx.coroutines.BuildersKt
 *  kotlinx.coroutines.CoroutineScope
 *  kotlinx.coroutines.Deferred
 *  kotlinx.coroutines.DelayKt
 *  kotlinx.coroutines.sync.Mutex
 *  kotlinx.coroutines.sync.Mutex$DefaultImpls
 *  kotlinx.coroutines.sync.MutexKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.core.env.Environment
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.BaseController$getUserInfoClass$;
import com.htmake.reader.api.controller.BaseControllerKt;
import com.htmake.reader.config.AppConfig;
import com.htmake.reader.entity.User;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.SpringContextUtils;
import io.legado.app.utils.FileUtils;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.env.Environment;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0\u001c2\u0006\u0010\u001f\u001a\u00020\u001eJ\u0018\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u001d2\b\b\u0002\u0010\"\u001a\u00020\u001dJ\u0010\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020\u001dJ\u001c\u0010&\u001a\u0010\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e\u0018\u00010'2\u0006\u0010%\u001a\u00020\u001dJ\u000e\u0010(\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u0018J)\u0010)\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u0017\u001a\u00020\u001e2\u0012\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0+\"\u00020\u001d\u00a2\u0006\u0002\u0010,J\u000e\u0010-\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u001eJX\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00102\u0006\u00101\u001a\u00020\u00102\u0006\u00102\u001a\u00020\u00102-\u00103\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e05\u0012\u0006\u0012\u0004\u0018\u00010\u001e04\u00a2\u0006\u0002\b6H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00107J\u0082\u0001\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00102\u0006\u00101\u001a\u00020\u00102\u0006\u00102\u001a\u00020\u00102-\u00103\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e05\u0012\u0006\u0012\u0004\u0018\u00010\u001e04\u00a2\u0006\u0002\b62(\u00108\u001a$\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u001e0:j\b\u0012\u0004\u0012\u00020\u001e`;\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u001609H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010<J7\u0010=\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0'2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010>\u001a\u00020$2\b\b\u0002\u0010?\u001a\u00020\u0016H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010@J\u001e\u0010A\u001a\u00020/2\u0006\u0010\u0017\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020\u001d2\u0006\u0010B\u001a\u00020\u001eR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006C"}, d2={"Lcom/htmake/reader/api/controller/BaseController;", "Lkotlinx/coroutines/CoroutineScope;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "appConfig", "Lcom/htmake/reader/config/AppConfig;", "getAppConfig", "()Lcom/htmake/reader/config/AppConfig;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "env", "Lorg/springframework/core/env/Environment;", "getEnv", "()Lorg/springframework/core/env/Environment;", "loginExpireDays", "", "userMutex", "Lkotlinx/coroutines/sync/Mutex;", "getUserMutex", "()Lkotlinx/coroutines/sync/Mutex;", "checkAuth", "", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkManagerAuth", "formatUser", "", "", "", "userInfo", "getFileExt", "url", "defaultExt", "getUserInfoClass", "Lcom/htmake/reader/entity/User;", "username", "getUserInfoMap", "", "getUserNameSpace", "getUserStorage", "path", "", "(Ljava/lang/Object;[Ljava/lang/String;)Ljava/lang/String;", "getUserWebdavHome", "limitConcurrent", "", "concurrentCount", "startIndex", "endIndex", "handler", "Lkotlin/Function3;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(IIILkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "needContinue", "Lkotlin/Function2;", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "(IIILkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserSession", "user", "regenerateToken", "(Lio/vertx/ext/web/RoutingContext;Lcom/htmake/reader/entity/User;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserStorage", "value", "reader-pro"})
public class BaseController
implements CoroutineScope {
    @NotNull
    private final CoroutineContext coroutineContext;
    private int loginExpireDays;
    @NotNull
    private final AppConfig appConfig;
    @NotNull
    private final Environment env;
    @NotNull
    private final Mutex userMutex;

    public BaseController(@NotNull CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter((Object)coroutineContext, (String)"coroutineContext");
        this.coroutineContext = coroutineContext;
        this.loginExpireDays = 7;
        Object object = SpringContextUtils.getBean((String)"appConfig", AppConfig.class);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"getBean(\"appConfig\", AppConfig::class.java)");
        this.appConfig = (AppConfig)object;
        object = SpringContextUtils.getBean(Environment.class);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"getBean(Environment::class.java)");
        this.env = (Environment)object;
        this.userMutex = MutexKt.Mutex$default((boolean)false, (int)1, null);
    }

    @NotNull
    public CoroutineContext getCoroutineContext() {
        return this.coroutineContext;
    }

    @NotNull
    public final AppConfig getAppConfig() {
        return this.appConfig;
    }

    @NotNull
    public final Environment getEnv() {
        return this.env;
    }

    @NotNull
    public final Mutex getUserMutex() {
        return this.userMutex;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object saveUserSession(@NotNull RoutingContext var1_1, @NotNull User var2_2, boolean var3_3, @NotNull Continuation<? super Map<String, ? extends Object>> var4_4) {
        if (!(var4_4 instanceof saveUserSession.1)) ** GOTO lbl-1000
        var13_5 = var4_4;
        if ((var13_5.label & -2147483648) != 0) {
            var13_5.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var14_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = user;
                $continuation.Z$0 = regenerateToken;
                $continuation.label = 1;
                v0 = Mutex.DefaultImpls.lock$default((Mutex)this.getUserMutex(), null, (Continuation)$continuation, (int)1, null);
                ** if (v0 != var14_7) goto lbl22
lbl21:
                // 1 sources

                return var14_7;
lbl22:
                // 1 sources

                ** GOTO lbl32
            }
            case 1: {
                regenerateToken = $continuation.Z$0;
                user = (User)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BaseController)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure((Object)$result);
                    v0 = $result;
lbl32:
                    // 2 sources

                    user.setLast_login_at(System.currentTimeMillis());
                    if (regenerateToken) {
                        user.setToken(ExtKt.genEncryptedPassword((String)user.getUsername(), (String)String.valueOf(System.currentTimeMillis())));
                        tokenMap = null;
                        expire = System.currentTimeMillis() + (long)(this.loginExpireDays * 86400 * 1000);
                        if (user.getToken_map() != null) {
                            var8_13 = user.getToken_map();
                            v1 /* !! */  = tokenMap = TypeIntrinsics.isMutableMap((Object)var8_13) != false ? var8_13 : null;
                        }
                        if (tokenMap == null) {
                            var8_13 = new Pair[]{TuplesKt.to((Object)user.getToken(), (Object)Boxing.boxLong((long)expire))};
                            tokenMap = MapsKt.mutableMapOf((Pair[])var8_13);
                        } else {
                            var8_13 = tokenMap;
                            var9_14 = user.getToken();
                            var10_15 = Boxing.boxLong((long)expire);
                            var11_17 = false;
                            var8_13.put(var9_14, var10_15);
                        }
                        CollectionsKt.removeAll((Iterable)tokenMap.values(), (Function1)((Function1)new /* Unavailable Anonymous Inner Class!! */));
                        user.setToken_map((Map)tokenMap);
                    }
                    expire = false;
                    userMap = new LinkedHashMap<K, V>();
                    var7_18 = new String[]{"data", "users"};
                    userMapJson = ExtKt.asJsonObject((Object)ExtKt.getStorage$default((String[])var7_18, null, (int)2, null));
                    if (userMapJson != null) {
                        var7_18 = userMapJson.getMap();
                        if (var7_18 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.Map<kotlin.String, kotlin.Any>>");
                        }
                        userMap = TypeIntrinsics.asMutableMap((Object)var7_18);
                    }
                    var7_18 = userMap;
                    var8_13 = user.getUsername();
                    var9_14 = ExtKt.toMap((Object)user);
                    var10_16 = false;
                    var7_18.put(var8_13, var9_14);
                    var7_18 = new String[]{"data", "users"};
                    v2 = var7_18;
                    var7_18 = Json.encode((Object)userMap);
                    Intrinsics.checkNotNullExpressionValue((Object)var7_18, (String)"encode(userMap)");
                    ExtKt.saveStorage$default((String[])v2, (Object)var7_18, (boolean)false, null, (int)12, null);
                    loginData = this.formatUser((Object)user);
                    context.session().put("username", (Object)user.getUsername());
                    context.put("username", (Object)user.getUsername());
                    var8_13 = loginData;
                    return var8_13;
                }
                catch (Throwable var5_9) {
                    throw var5_9;
                }
                finally {
                    Mutex.DefaultImpls.unlock$default((Mutex)this.getUserMutex(), null, (int)1, null);
                }
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object saveUserSession$default(BaseController baseController, RoutingContext routingContext, User user, boolean bl, Continuation continuation, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: saveUserSession");
        }
        if ((n & 4) != 0) {
            bl = true;
        }
        return baseController.saveUserSession(routingContext, user, bl, continuation);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object checkAuth(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Boolean> var2_2) {
        if (!(var2_2 instanceof checkAuth.1)) ** GOTO lbl-1000
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
                if (!this.getAppConfig().getSecure()) {
                    return Boxing.boxBoolean((boolean)true);
                }
                var4_6 = (String)context.session().get("username");
                username = var4_6 == null ? "" : var4_6;
                userInfo = this.getUserInfoClass(username);
                if (userInfo != null) {
                    context.put("username", (Object)userInfo.getUsername());
                    context.put("userInfo", (Object)userInfo);
                    return Boxing.boxBoolean((boolean)true);
                }
                var7_8 = context.queryParam("accessToken");
                Intrinsics.checkNotNullExpressionValue((Object)var7_8, (String)"context.queryParam(\"accessToken\")");
                var6_11 = (String)CollectionsKt.firstOrNull((List)var7_8);
                accessToken = var6_11 == null ? "" : var6_11;
                var6_11 = accessToken;
                var7_9 = false;
                if (!(var6_11.length() > 0)) break;
                var7_9 = false;
                userMap = new LinkedHashMap<K, V>();
                var8_13 = new String[]{"data", "users"};
                userMapJson = ExtKt.asJsonObject((Object)ExtKt.getStorage$default((String[])var8_13, null, (int)2, null));
                if (userMapJson != null) {
                    var9_14 = userMapJson.getMap();
                    v0 = var8_13 = TypeIntrinsics.isMutableMap((Object)var9_14) != false ? var9_14 : null;
                    if (var8_13 == null) {
                        var9_15 = false;
                        v1 /* !! */  = new LinkedHashMap<K, V>();
                    } else {
                        v1 /* !! */  = userMap = var8_13;
                    }
                }
                if ((tmp = StringsKt.split$default((CharSequence)accessToken, (String[])(var9_16 = new String[]{":"}), (boolean)false, (int)2, (int)2, null)).size() < 2) break;
                _username = (String)tmp.get(0);
                token = (String)tmp.get(1);
                var13_18 = userMap;
                var14_21 = null;
                var15_25 = false;
                var12_28 = var13_18.getOrDefault(_username, var14_21);
                if (var12_28 == null) {
                    v2 = null;
                } else {
                    $this$toDataClass$iv = var12_28;
                    $i$f$toDataClass = false;
                    $this$convert$iv$iv = $this$toDataClass$iv;
                    $i$f$convert = false;
                    json$iv$iv = $this$convert$iv$iv instanceof String != false ? (String)$this$convert$iv$iv : ExtKt.getGson().toJson((Object)$this$convert$iv$iv);
                    v2 = existedUser = (User)ExtKt.getGson().fromJson(json$iv$iv, new /* Unavailable Anonymous Inner Class!! */.getType());
                }
                if (existedUser == null) break;
                var12_28 = token;
                $this$toDataClass$iv = false;
                if (!(var12_28.length() > 0)) break;
                isLogin = 0;
                $this$toDataClass$iv = existedUser.getToken();
                var14_23 = false;
                if ($this$toDataClass$iv.length() > 0 && Intrinsics.areEqual((Object)existedUser.getToken(), (Object)token)) {
                    isLogin = 1;
                }
                if (isLogin == 0 && existedUser.getToken_map() != null) {
                    var14_24 = existedUser.getToken_map();
                    v3 = tokenMap = TypeIntrinsics.isMutableMap((Object)var14_24) != false ? var14_24 : null;
                    if (tokenMap != null && tokenMap.containsKey(token)) {
                        if (((Number)tokenMap.getOrDefault(token, Boxing.boxLong((long)0L))).longValue() > System.currentTimeMillis()) {
                            isLogin = 1;
                            var14_24 = tokenMap;
                            var15_27 = Boxing.boxLong((long)(System.currentTimeMillis() + (long)(this.loginExpireDays * 86400 * 1000)));
                            var16_30 = false;
                            var14_24.put(token, var15_27);
                        } else {
                            tokenMap.remove(token);
                        }
                        existedUser.setToken_map((Map)tokenMap);
                    }
                }
                if (isLogin == 0) ** GOTO lbl102
                $continuation.L$0 = context;
                $continuation.L$1 = existedUser;
                $continuation.I$0 = isLogin;
                $continuation.label = 1;
                v4 = this.saveUserSession((RoutingContext)context, existedUser, false, (Continuation)$continuation);
                if (v4 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl98
            }
            case 1: {
                var12_29 = $continuation.I$0;
                var11_32 = (User)$continuation.L$1;
                var1_1 = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v4 = $result;
lbl98:
                // 2 sources

                var1_1.put("username", (Object)var11_32.getUsername());
                var1_1.put("userInfo", (Object)var11_32);
lbl102:
                // 2 sources

                return Boxing.boxBoolean((boolean)(var12_29 != 0));
            }
        }
        return Boxing.boxBoolean((boolean)false);
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public final boolean checkManagerAuth(@NotNull RoutingContext context) {
        String secureKey;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (!this.appConfig.getSecure()) {
            return true;
        }
        CharSequence charSequence = this.appConfig.getSecureKey();
        boolean bl = false;
        if (charSequence.length() == 0) {
            return true;
        }
        Object object = context.queryParam("secureKey");
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.queryParam(\"secureKey\")");
        String string = (String)CollectionsKt.firstOrNull((List)object);
        String string2 = secureKey = string == null ? "" : string;
        if (Intrinsics.areEqual((Object)secureKey, (Object)this.appConfig.getSecureKey())) {
            object = context.queryParam("userNS");
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.queryParam(\"userNS\")");
            String userNS = (String)CollectionsKt.firstOrNull((List)object);
            object = userNS;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!(object == null || object.length() == 0)) {
                context.put("userNameSpace", (Object)userNS);
            } else {
                context.remove("userNameSpace");
            }
            return true;
        }
        return false;
    }

    @NotNull
    public final String getUserNameSpace(@NotNull RoutingContext context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (!this.appConfig.getSecure()) {
            return "default";
        }
        this.checkManagerAuth(context);
        String userNS = (String)context.get("userNameSpace");
        CharSequence charSequence = userNS;
        boolean bl = false;
        boolean bl2 = false;
        if (!(charSequence == null || charSequence.length() == 0)) {
            return userNS;
        }
        String username = (String)context.get("username");
        if (username != null) {
            return username;
        }
        return "default";
    }

    @Nullable
    public final String getUserStorage(@NotNull Object context, String ... path) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        String userNameSpace = "";
        Object object = context;
        if (object instanceof RoutingContext) {
            userNameSpace = this.getUserNameSpace((RoutingContext)context);
        } else if (object instanceof String) {
            userNameSpace = (String)context;
        }
        object = userNameSpace;
        boolean bl = false;
        if (object.length() == 0) {
            object = new SpreadBuilder(2);
            object.add((Object)"data");
            object.addSpread((Object)path);
            return ExtKt.getStorage$default((String[])((String[])object.toArray((Object[])new String[object.size()])), null, (int)2, null);
        }
        object = new SpreadBuilder(3);
        object.add((Object)"data");
        object.add((Object)userNameSpace);
        object.addSpread((Object)path);
        return ExtKt.getStorage$default((String[])((String[])object.toArray((Object[])new String[object.size()])), null, (int)2, null);
    }

    public final void saveUserStorage(@NotNull Object context, @NotNull String path, @NotNull Object value) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        String userNameSpace = "";
        String[] stringArray = context;
        if (stringArray instanceof RoutingContext) {
            userNameSpace = this.getUserNameSpace((RoutingContext)context);
        } else if (stringArray instanceof String) {
            userNameSpace = (String)context;
        }
        stringArray = userNameSpace;
        boolean bl = false;
        if (stringArray.length() == 0) {
            stringArray = new String[]{"data", path};
            ExtKt.saveStorage$default((String[])stringArray, (Object)value, (boolean)false, null, (int)12, null);
            return;
        }
        stringArray = new String[]{"data", userNameSpace, path};
        ExtKt.saveStorage$default((String[])stringArray, (Object)value, (boolean)false, null, (int)12, null);
    }

    @Nullable
    public final User getUserInfoClass(@NotNull String username) {
        User user;
        Intrinsics.checkNotNullParameter((Object)username, (String)"username");
        Map map = this.getUserInfoMap(username);
        if (map == null) {
            user = null;
        } else {
            Map $this$toDataClass$iv = map;
            boolean $i$f$toDataClass = false;
            Map $this$convert$iv$iv = $this$toDataClass$iv;
            boolean $i$f$convert = false;
            String json$iv$iv = $this$convert$iv$iv instanceof String ? (String)((Object)$this$convert$iv$iv) : ExtKt.getGson().toJson((Object)$this$convert$iv$iv);
            user = (User)ExtKt.getGson().fromJson(json$iv$iv, new getUserInfoClass$$inlined$toDataClass$1().getType());
        }
        User user2 = user;
        return user2;
    }

    @Nullable
    public final Map<String, Object> getUserInfoMap(@NotNull String username) {
        Intrinsics.checkNotNullParameter((Object)username, (String)"username");
        CharSequence charSequence = username;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        bl = false;
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
        object = userMap;
        Object v = null;
        boolean bl2 = false;
        Object object2 = object;
        if (object2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
        }
        return object2.getOrDefault(username, v);
    }

    @NotNull
    public final Map<String, Object> formatUser(@NotNull Object userInfo) {
        Intrinsics.checkNotNullParameter((Object)userInfo, (String)"userInfo");
        User user = null;
        if (!(userInfo instanceof User)) {
            Map userMap;
            Map map = userMap = userInfo instanceof Map ? (Map)userInfo : null;
            if (userMap != null) {
                Map $this$toDataClass$iv = userMap;
                boolean $i$f$toDataClass = false;
                Map $this$convert$iv$iv = $this$toDataClass$iv;
                boolean $i$f$convert = false;
                String json$iv$iv = $this$convert$iv$iv instanceof String ? (String)((Object)$this$convert$iv$iv) : ExtKt.getGson().toJson((Object)$this$convert$iv$iv);
                user = (User)ExtKt.getGson().fromJson(json$iv$iv, new /* Unavailable Anonymous Inner Class!! */.getType());
            }
        } else {
            user = (User)userInfo;
        }
        if (user == null) {
            boolean bl = false;
            return new LinkedHashMap();
        }
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"username", (Object)user.getUsername()), TuplesKt.to((Object)"lastLoginAt", (Object)user.getLast_login_at()), TuplesKt.to((Object)"accessToken", (Object)(user.getUsername() + ':' + user.getToken())), TuplesKt.to((Object)"enableWebdav", (Object)user.getEnable_webdav()), TuplesKt.to((Object)"enableLocalStore", (Object)user.getEnable_local_store()), TuplesKt.to((Object)"enableBookSource", (Object)user.getEnable_book_source()), TuplesKt.to((Object)"enableRssSource", (Object)user.getEnable_rss_source()), TuplesKt.to((Object)"bookSourceLimit", (Object)user.getBook_source_limit()), TuplesKt.to((Object)"bookLimit", (Object)user.getBook_limit()), TuplesKt.to((Object)"createdAt", (Object)user.getCreated_at())};
        return MapsKt.mutableMapOf((Pair[])pairArray);
    }

    @NotNull
    public final String getUserWebdavHome(@NotNull Object context) {
        File file;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        String[] stringArray = new String[]{"storage", "data"};
        String prefix = ExtKt.getWorkDir((String[])stringArray);
        String userNameSpace = "";
        Object object = context;
        if (object instanceof RoutingContext) {
            userNameSpace = this.getUserNameSpace((RoutingContext)context);
        } else if (object instanceof String) {
            userNameSpace = (String)context;
        }
        object = userNameSpace;
        boolean bl = false;
        if (object.length() > 0) {
            prefix = prefix + File.separator + userNameSpace;
        }
        if (!(file = new File(prefix = prefix + File.separator + "webdav")).exists()) {
            file.mkdirs();
        }
        return prefix;
    }

    @NotNull
    public final String getFileExt(@NotNull String url2, @NotNull String defaultExt) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        Intrinsics.checkNotNullParameter((Object)defaultExt, (String)"defaultExt");
        return FileUtils.INSTANCE.getFileExtetion(url2, defaultExt);
    }

    public static /* synthetic */ String getFileExt$default(BaseController baseController, String string, String string2, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getFileExt");
        }
        if ((n & 2) != 0) {
            string2 = "";
        }
        return baseController.getFileExt(string, string2);
    }

    @Nullable
    public final Object limitConcurrent(int concurrentCount, int startIndex, int endIndex, @NotNull Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object> handler2, @NotNull Continuation<? super Unit> $completion) {
        Object object = this.limitConcurrent(concurrentCount, startIndex, endIndex, handler2, (Function2)limitConcurrent.2.INSTANCE, $completion);
        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return object;
        }
        return Unit.INSTANCE;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object limitConcurrent(int var1_1, int var2_2, int var3_3, @NotNull Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object> var4_4, @NotNull Function2<? super ArrayList<Object>, ? super Integer, Boolean> var5_5, @NotNull Continuation<? super Unit> var6_6) {
        block26: {
            block25: {
                if (!(var6_6 instanceof limitConcurrent.3)) ** GOTO lbl-1000
                var26_7 = var6_6;
                if ((var26_7.label & -2147483648) != 0) {
                    var26_7.label -= -2147483648;
                } else lbl-1000:
                // 2 sources

                {
                    $continuation = new /* Unavailable Anonymous Inner Class!! */;
                }
                $result = $continuation.result;
                var27_9 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                block3 : switch ($continuation.label) {
                    case 0: {
                        ResultKt.throwOnFailure((Object)$result);
                        lastIndex = startIndex;
                        loopCount = 0;
                        resultCount = 0;
                        loopStart = System.currentTimeMillis();
                        var12_14 = 0L;
                        var15_15 = 0;
                        deferredList = new ArrayList<Deferred>();
lbl20:
                        // 2 sources

                        while (true) {
                            coroutineCount = deferredList.size();
                            if (coroutineCount < concurrentCount && (var16_18 = lastIndex) < endIndex) {
                                do {
                                    i = var16_18++;
                                    deferredList.add(BuildersKt.async$default((CoroutineScope)((CoroutineScope)this), null, null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)3, null));
                                    lastIndex = i;
                                } while (++coroutineCount < concurrentCount && var16_18 < endIndex);
                            }
                            i = 0;
                            resultList = new ArrayList<Object>();
lbl31:
                            // 2 sources

                            while (resultList.size() <= 0) {
                                $continuation.L$0 = this;
                                $continuation.L$1 = handler;
                                $continuation.L$2 = needContinue;
                                $continuation.L$3 = deferredList;
                                $continuation.L$4 = resultList;
                                $continuation.I$0 = concurrentCount;
                                $continuation.I$1 = endIndex;
                                $continuation.I$2 = lastIndex;
                                $continuation.I$3 = loopCount;
                                $continuation.I$4 = resultCount;
                                $continuation.J$0 = loopStart;
                                $continuation.I$5 = coroutineCount;
                                $continuation.label = 1;
                                v0 = DelayKt.delay((long)10L, (Continuation)$continuation);
                                if (v0 == var27_9) {
                                    return var27_9;
                                }
                                ** GOTO lbl65
                            }
                            break block3;
                            break;
                        }
                    }
                    case 1: {
                        var15_15 = $continuation.I$5;
                        var10_13 = $continuation.J$0;
                        var9_12 = $continuation.I$4;
                        var8_11 = $continuation.I$3;
                        var7_10 = $continuation.I$2;
                        var3_3 = $continuation.I$1;
                        var1_1 = $continuation.I$0;
                        resultList = (ArrayList<Object>)$continuation.L$4;
                        var14_16 = (ArrayList)$continuation.L$3;
                        var5_5 = (Function2)$continuation.L$2;
                        var4_4 = (Function3)$continuation.L$1;
                        this = (BaseController)$continuation.L$0;
                        ResultKt.throwOnFailure((Object)$result);
                        v0 = $result;
lbl65:
                        // 2 sources

                        var18_23 = 0;
                        stillDeferredList = new ArrayList<Deferred>();
                        var18_23 = 0;
                        var19_24 = var14_16.size();
                        if (var18_23 < var19_24) {
                            do {
                                i = var18_23++;
                                try {
                                    var22_29 = var14_16.get(i);
                                    Intrinsics.checkNotNullExpressionValue(var22_29, (String)"deferredList[i]");
                                    deferred = (Deferred)var22_29;
                                    if (deferred.isCompleted()) {
                                        var22_30 = var9_12;
                                        var9_12 = var22_30 + 1;
                                        resultList.add(deferred.getCompleted());
                                        continue;
                                    }
                                    if (!deferred.isCancelled()) {
                                        stillDeferredList.add(deferred);
                                        continue;
                                    }
                                    var22_31 = var9_12;
                                    var9_12 = var22_31 + 1;
                                }
                                catch (Exception var21_28) {
                                    // empty catch block
                                }
                            } while (var18_23 < var19_24);
                        }
                        var14_16.clear();
                        var14_16.addAll((Collection)stillDeferredList);
                        ** GOTO lbl31
                    }
                }
                if (var9_12 / var1_1 > var8_11) {
                    var8_11 = var9_12 / var1_1;
                    costTime = System.currentTimeMillis() - var10_13;
                    var17_21 = new Object[]{Boxing.boxInt((int)var8_11), Boxing.boxInt((int)var15_15), Boxing.boxInt((int)var7_10), Boxing.boxInt((int)var3_3), Boxing.boxLong((long)costTime), Boxing.boxInt((int)var14_16.size())};
                    BaseControllerKt.access$getLogger$p().info("Loop: {} concurrentCount: {} lastIndex: {} endIndex: {} costTime: {} ms deferredList size: {}", var17_21);
                }
                if (var7_10 < var3_3 - 1) break block25;
                var17_22 = 0;
                var18_23 = var14_16.size();
                if (var17_22 >= var18_23) ** GOTO lbl143
                while (true) {
                    i = var17_22++;
                    var23_32 = resultList;
                    $continuation.L$0 = var5_5;
                    $continuation.L$1 = var14_16;
                    $continuation.L$2 = resultList;
                    $continuation.L$3 = var23_32;
                    $continuation.L$4 = null;
                    $continuation.I$0 = var8_11;
                    $continuation.I$1 = var17_22;
                    $continuation.I$2 = var18_23;
                    $continuation.label = 2;
                    v1 = ((Deferred)var14_16.get(i)).await((Continuation)$continuation);
                    ** if (v1 != var27_9) goto lbl123
lbl122:
                    // 1 sources

                    return var27_9;
lbl123:
                    // 1 sources

                    ** GOTO lbl136
                    break;
                }
                {
                    case 2: {
                        var18_23 = $continuation.I$2;
                        var17_22 = $continuation.I$1;
                        var8_11 = $continuation.I$0;
                        var23_32 = (ArrayList<Object>)$continuation.L$3;
                        var16_17 = (ArrayList)$continuation.L$2;
                        var14_16 = (ArrayList)$continuation.L$1;
                        var5_5 = (Function2)$continuation.L$0;
                        try {
                            ResultKt.throwOnFailure((Object)$result);
                            v1 = $result;
lbl136:
                            // 2 sources

                            var24_33 = v1;
                            var23_32.add(var24_33);
                        }
                        catch (Exception var20_26) {
                            // empty catch block
                        }
                        if (var17_22 < var18_23) ** continue;
lbl143:
                        // 2 sources

                        var14_16.clear();
                        var5_5.invoke((Object)var16_17, (Object)Boxing.boxInt((int)var8_11));
                        break block26;
                    }
                }
            }
            if (var16_17.size() <= 0 || ((Boolean)var5_5.invoke((Object)var16_17, (Object)Boxing.boxInt((int)var8_11))).booleanValue()) {
                ++var7_10;
                ** continue;
            }
        }
        return Unit.INSTANCE;
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}

