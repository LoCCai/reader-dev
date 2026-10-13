/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.api.ReturnData
 *  com.htmake.reader.api.controller.BaseController
 *  com.htmake.reader.api.controller.BookController
 *  com.htmake.reader.api.controller.UserController
 *  com.htmake.reader.api.controller.UserController$addUser$1
 *  com.htmake.reader.api.controller.UserController$clearInactiveUsers$1
 *  com.htmake.reader.api.controller.UserController$deleteFile$1
 *  com.htmake.reader.api.controller.UserController$deleteUsers$1
 *  com.htmake.reader.api.controller.UserController$downloadBackupFile$1
 *  com.htmake.reader.api.controller.UserController$forEachUser$1
 *  com.htmake.reader.api.controller.UserController$forEachUser$lambda-7$lambda-6$$inlined$toDataClass$1
 *  com.htmake.reader.api.controller.UserController$getUserConfig$1
 *  com.htmake.reader.api.controller.UserController$getUserInfo$1
 *  com.htmake.reader.api.controller.UserController$getUserList$1
 *  com.htmake.reader.api.controller.UserController$login$$inlined$toDataClass$1
 *  com.htmake.reader.api.controller.UserController$login$1
 *  com.htmake.reader.api.controller.UserController$logout$1
 *  com.htmake.reader.api.controller.UserController$resetPassword$1
 *  com.htmake.reader.api.controller.UserController$saveUserConfig$1
 *  com.htmake.reader.api.controller.UserController$updateUser$1
 *  com.htmake.reader.api.controller.UserController$uploadFile$1
 *  com.htmake.reader.api.controller.UserControllerKt
 *  com.htmake.reader.entity.License
 *  com.htmake.reader.entity.User
 *  com.htmake.reader.utils.ExtKt
 *  com.htmake.reader.utils.VertExtKt
 *  io.vertx.core.http.HttpMethod
 *  io.vertx.core.json.Json
 *  io.vertx.core.json.JsonArray
 *  io.vertx.ext.web.FileUpload
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
 *  kotlin.io.FilesKt
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$BooleanRef
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlin.jvm.internal.TypeIntrinsics
 *  kotlin.text.Regex
 *  kotlin.text.RegexOption
 *  kotlin.text.StringsKt
 *  kotlinx.coroutines.CoroutineScope
 *  kotlinx.coroutines.sync.Mutex
 *  kotlinx.coroutines.sync.Mutex$DefaultImpls
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.BookController;
import com.htmake.reader.api.controller.UserController;
import com.htmake.reader.api.controller.UserController$forEachUser$lambda-7$lambda-6$;
import com.htmake.reader.api.controller.UserController$login$;
import com.htmake.reader.api.controller.UserControllerKt;
import com.htmake.reader.entity.License;
import com.htmake.reader.entity.User;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.VertExtKt;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
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
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.sync.Mutex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010\u0013\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJO\u0010\u0015\u001a\u00020\u000f2<\u0010\u0016\u001a8\b\u0001\u0012\u0004\u0012\u00020\u0018\u0012\u0013\u0012\u00110\u0019\u00a2\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001d\u0012\u0006\u0012\u0004\u0018\u00010\u001f0\u0017\u00a2\u0006\u0002\b H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\u0019\u0010\"\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010#\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0010\u0010$\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0019\u0010%\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010&\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010'\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010(\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010)\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010*\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010+\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rR\u0014\u0010\u0005\u001a\u00020\u0006X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006,"}, d2={"Lcom/htmake/reader/api/controller/UserController;", "Lcom/htmake/reader/api/controller/BaseController;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "userMaxCount", "", "getUserMaxCount", "()I", "addUser", "Lcom/htmake/reader/api/ReturnData;", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearInactiveUsers", "", "day", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteFile", "deleteUsers", "downloadBackupFile", "forEachUser", "handler", "Lkotlin/Function3;", "Lkotlinx/coroutines/CoroutineScope;", "Lcom/htmake/reader/entity/User;", "Lkotlin/ParameterName;", "name", "user", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserConfig", "getUserInfo", "getUserLimit", "getUserList", "login", "logout", "resetPassword", "saveUserConfig", "updateUser", "uploadFile", "reader-pro"})
public final class UserController
extends BaseController {
    private final int userMaxCount;

    public UserController(@NotNull CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter((Object)coroutineContext, (String)"coroutineContext");
        super(coroutineContext);
        this.userMaxCount = 15;
    }

    public final int getUserMaxCount() {
        return this.userMaxCount;
    }

    private final int getUserLimit(RoutingContext context) {
        License license = ExtKt.getInstalledLicense$default((boolean)false, (int)1, null);
        String string = context.request().host();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"context.request().host()");
        if (license.validHost(string)) {
            return Math.min(Math.max(this.getAppConfig().getUserLimit(), 1), license.getUserMaxLimit());
        }
        return Math.min(Math.max(this.getAppConfig().getUserLimit(), 1), this.userMaxCount);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object login(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof login.1)) ** GOTO lbl-1000
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
                var5_7 = context.getBodyAsJson().getString("username", "");
                username = var5_7 == null ? "" : var5_7;
                var6_9 = context.getBodyAsJson().getString("password", "");
                password = var6_9 == null ? "" : var6_9;
                var7_11 = context.getBodyAsJson().getBoolean("isLogin", Boxing.boxBoolean((boolean)false));
                isLogin = var7_11 == null ? false : var7_11.booleanValue();
                var7_11 = username;
                var8_12 = false;
                var9_14 = false;
                if (var7_11.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u7528\u6237\u540d");
                }
                var7_11 = password;
                var8_12 = false;
                var9_14 = false;
                if (var7_11.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u5bc6\u7801");
                }
                var8_12 = false;
                userMap = new LinkedHashMap<K, V>();
                var9_15 /* !! */  = new String[]{"data", "users"};
                userMapJson = ExtKt.asJsonObject((Object)ExtKt.getStorage$default((String[])var9_15 /* !! */ , null, (int)2, null));
                if (userMapJson != null) {
                    var9_15 /* !! */  = userMapJson.getMap();
                    if (var9_15 /* !! */  == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.Map<kotlin.String, kotlin.Any>>");
                    }
                    userMap = TypeIntrinsics.asMutableMap((Object)var9_15 /* !! */ );
                }
                var10_16 = userMap;
                var11_17 = null;
                var12_19 = false;
                v0 = var10_16;
                if (v0 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
                }
                existedUser = v0.getOrDefault(username, var11_17);
                if (existedUser != null) break;
                if (isLogin) {
                    return returnData.setErrorMsg("\u7528\u6237\u4e0d\u5b58\u5728");
                }
                if (username.length() < 5) {
                    return returnData.setErrorMsg("\u7528\u6237\u540d\u4e0d\u80fd\u4f4e\u4e8e5\u4f4d");
                }
                if (password.length() < this.getAppConfig().getMinUserPasswordLength()) {
                    return returnData.setErrorMsg("\u5bc6\u7801\u4e0d\u80fd\u4f4e\u4e8e" + this.getAppConfig().getMinUserPasswordLength() + '\u4f4d');
                }
                if (username.equals("default")) {
                    return returnData.setErrorMsg("\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u975e\u6cd5\u5b57\u7b26");
                }
                usernameReg = new Regex("[a-z0-9]+", RegexOption.IGNORE_CASE);
                if (!usernameReg.matches((CharSequence)username)) {
                    return returnData.setErrorMsg("\u7528\u6237\u540d\u53ea\u80fd\u7531\u5b57\u6bcd\u548c\u6570\u5b57\u7ec4\u6210");
                }
                var11_17 = this.getAppConfig().getInviteCode();
                var12_19 = false;
                if (var11_17.length() > 0) {
                    var12_20 = context.getBodyAsJson().getString("code");
                    code = var12_20 == null ? "" : var12_20;
                    var12_20 = code;
                    var13_23 = false;
                    var14_26 = false;
                    if (var12_20.length() == 0) {
                        return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u9080\u8bf7\u7801");
                    }
                    if (!this.getAppConfig().getInviteCode().equals(code)) {
                        return returnData.setErrorMsg("\u9080\u8bf7\u7801\u9519\u8bef");
                    }
                }
                userLimit = this.getUserLimit((RoutingContext)context);
                if (userMap.keySet().size() >= userLimit) {
                    return returnData.setErrorMsg("\u8d85\u8fc7\u7528\u6237\u6570\u4e0a\u9650");
                }
                salt = ExtKt.getRandomString((int)8);
                passwordEncrypted = ExtKt.genEncryptedPassword((String)password, (String)salt);
                newUser = new User(username, passwordEncrypted, salt, null, 0L, 0L, false, null, false, false, false, 0, 0, 8184, null);
                newUser.setEnable_webdav(this.getAppConfig().getDefaultUserEnableWebdav());
                newUser.setEnable_local_store(this.getAppConfig().getDefaultUserEnableLocalStore());
                newUser.setEnable_book_source(this.getAppConfig().getDefaultUserEnableBookSource());
                newUser.setEnable_rss_source(this.getAppConfig().getDefaultUserEnableRssSource());
                newUser.setBook_source_limit(this.getAppConfig().getDefaultUserBookSourceLimit());
                newUser.setBook_limit(this.getAppConfig().getDefaultUserBookLimit());
                $continuation.L$0 = returnData;
                $continuation.label = 1;
                v1 = BaseController.saveUserSession$default((BaseController)((BaseController)this), (RoutingContext)context, (User)newUser, (boolean)false, (Continuation)$continuation, (int)4, null);
                if (v1 == var18_5) {
                    return var18_5;
                }
                ** GOTO lbl91
            }
            case 1: {
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl91:
                // 2 sources

                loginData = (Map)v1;
                return ReturnData.setData$default((ReturnData)var3_6, (Object)loginData, null, (int)2, null);
            }
        }
        if (var6_10 == false) {
            return var3_6.setErrorMsg("\u7528\u6237\u540d\u5df2\u88ab\u5360\u7528");
        }
        $this$toDataClass$iv = existedUser;
        $i$f$toDataClass = false;
        $this$convert$iv$iv = $this$toDataClass$iv;
        $i$f$convert = false;
        json$iv$iv = $this$convert$iv$iv instanceof String != false ? (String)$this$convert$iv$iv : ExtKt.getGson().toJson((Object)$this$convert$iv$iv);
        userInfo = (User)ExtKt.getGson().fromJson(json$iv$iv, new login$$inlined$toDataClass$1().getType());
        if (userInfo == null) {
            return var3_6.setErrorMsg("\u7528\u6237\u4fe1\u606f\u9519\u8bef");
        }
        passwordEncrypted = ExtKt.genEncryptedPassword((String)var5_7, (String)userInfo.getSalt());
        if (!Intrinsics.areEqual((Object)passwordEncrypted, (Object)userInfo.getPassword())) {
            return var3_6.setErrorMsg("\u5bc6\u7801\u9519\u8bef");
        }
        $continuation.L$0 = var3_6;
        $continuation.label = 2;
        v2 = BaseController.saveUserSession$default((BaseController)((BaseController)this), (RoutingContext)var1_1, (User)userInfo, (boolean)false, (Continuation)$continuation, (int)4, null);
        if (v2 == var18_5) {
            return var18_5;
        }
        ** GOTO lbl117
        {
            case 2: {
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v2 = $result;
lbl117:
                // 2 sources

                loginData = (Map)v2;
                return ReturnData.setData$default((ReturnData)var3_6, (Object)loginData, null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object logout(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof logout.1)) ** GOTO lbl-1000
        var16_3 = var2_2;
        if ((var16_3.label & -2147483648) != 0) {
            var16_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var17_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var17_5) {
                    return var17_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (!this.getAppConfig().getSecure()) {
                    return returnData.setErrorMsg("\u4e0d\u652f\u6301\u7684\u64cd\u4f5c");
                }
                var5_7 = (String)context.session().get("username");
                username = var5_7 == null ? "" : var5_7;
                context.session().destroy();
                var7_9 = context.queryParam("accessToken");
                Intrinsics.checkNotNullExpressionValue((Object)var7_9, (String)"context.queryParam(\"accessToken\")");
                var6_12 = (String)CollectionsKt.firstOrNull((List)var7_9);
                accessToken = var6_12 == null ? "" : var6_12;
                var6_12 = accessToken;
                var7_10 = false;
                if (!(var6_12.length() > 0) || (tmp = StringsKt.split$default((CharSequence)accessToken, (String[])(var7_11 = new String[]{":"}), (boolean)false, (int)2, (int)2, null)).size() < 2) ** GOTO lbl107
                accessToken = (String)tmp.get(1);
                var8_13 = false;
                userMap = new LinkedHashMap<K, V>();
                $continuation.L$0 = this;
                $continuation.L$1 = returnData;
                $continuation.L$2 = username;
                $continuation.L$3 = accessToken;
                $continuation.L$4 = userMap;
                $continuation.label = 2;
                v1 = Mutex.DefaultImpls.lock$default((Mutex)this.getUserMutex(), null, (Continuation)$continuation, (int)1, null);
                ** if (v1 != var17_5) goto lbl55
lbl54:
                // 1 sources

                return var17_5;
lbl55:
                // 1 sources

                ** GOTO lbl66
            }
            case 2: {
                userMap = (Map)$continuation.L$4;
                var5_7 = (String)$continuation.L$3;
                var4_8 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl66:
                // 2 sources

                if ((userMapJson = ExtKt.asJsonObject((Object)ExtKt.getStorage$default((String[])(var9_16 /* !! */  = new String[]{"data", "users"}), null, (int)2, null))) != null) {
                    var9_16 /* !! */  = userMapJson.getMap();
                    if (var9_16 /* !! */  == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.MutableMap<kotlin.String, kotlin.Any>>");
                    }
                    userMap = TypeIntrinsics.asMutableMap((Object)var9_16 /* !! */ );
                }
                var10_17 = userMap;
                var11_18 /* !! */  = null;
                var12_19 = false;
                v2 = var10_17;
                if (v2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
                }
                currentUser = v2.getOrDefault(var4_8, var11_18 /* !! */ );
                if (currentUser == null) {
                    var10_17 = var3_6.setErrorMsg("\u7cfb\u7edf\u9519\u8bef");
                    return var10_17;
                }
                var11_18 /* !! */  = currentUser;
                var12_20 = "token_map";
                var13_21 = null;
                var14_22 = false;
                tokenMapVal = var11_18 /* !! */ .getOrDefault(var12_20, var13_21);
                if (tokenMapVal != null && (tokenMap = TypeIntrinsics.asMutableMap(tokenMapVal)) != null) {
                    tokenMap.remove(var5_7);
                    currentUser.put("token_map", tokenMap);
                }
                if (currentUser.getOrDefault("token", "").equals(var5_7)) {
                    currentUser.put("token", "");
                }
                userMap.put(var4_8, currentUser);
                var11_18 /* !! */  = new String[]{"data", "users"};
                v3 = var11_18 /* !! */ ;
                var11_18 /* !! */  = Json.encode((Object)userMap);
                Intrinsics.checkNotNullExpressionValue((Object)var11_18 /* !! */ , (String)"encode(userMap)");
                ExtKt.saveStorage$default((String[])v3, (Object)var11_18 /* !! */ , (boolean)false, null, (int)12, null);
                ** GOTO lbl107
            }
            catch (Throwable var8_15) {
                throw var8_15;
            }
            {
                finally {
                    Mutex.DefaultImpls.unlock$default((Mutex)this.getUserMutex(), null, (int)1, null);
                }
lbl107:
                // 2 sources

                return ReturnData.setData$default((ReturnData)var3_6.setErrorMsg("\u8bf7\u91cd\u65b0\u767b\u5f55"), (Object)"NEED_LOGIN", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getUserList(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getUserList.1)) ** GOTO lbl-1000
        var16_3 = var2_2;
        if ((var16_3.label & -2147483648) != 0) {
            var16_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var17_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var17_5) {
                    return var17_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (!this.getAppConfig().getSecure()) ** GOTO lbl33
                var4_7 = this.getAppConfig().getSecureKey();
                var5_8 = false;
                if (!(var4_7.length() == 0)) ** GOTO lbl34
lbl33:
                // 2 sources

                return returnData.setErrorMsg("\u4e0d\u652f\u6301\u7684\u64cd\u4f5c");
lbl34:
                // 1 sources

                if (!this.checkManagerAuth(context)) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_SECURE_KEY", null, (int)2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                }
                var5_8 = false;
                userMap = new LinkedHashMap<K, V>();
                var6_10 /* !! */  = new String[]{"data", "users"};
                userMapJson = ExtKt.asJsonObject((Object)ExtKt.getStorage$default((String[])var6_10 /* !! */ , null, (int)2, null));
                if (userMapJson != null) {
                    var6_10 /* !! */  = userMapJson.getMap();
                    if (var6_10 /* !! */  == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.MutableMap<kotlin.String, kotlin.Any>>");
                    }
                    userMap = TypeIntrinsics.asMutableMap((Object)var6_10 /* !! */ );
                }
                userList = null;
                var7_11 = false;
                userList = new ArrayList<Map>();
                $this$forEach$iv = userMap;
                $i$f$forEach = false;
                var9_14 = $this$forEach$iv;
                var10_15 = false;
                var11_16 = var9_14.entrySet().iterator();
                while (var11_16.hasNext()) {
                    it = element$iv = var11_16.next();
                    $i$a$-forEach-UserController$getUserList$2 = false;
                    userList.add(this.formatUser(it.getValue()));
                }
                return ReturnData.setData$default((ReturnData)returnData, userList, null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object addUser(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof addUser.1)) ** GOTO lbl-1000
        var30_3 = var2_2;
        if ((var30_3.label & -2147483648) != 0) {
            var30_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var31_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var31_5) {
                    return var31_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (!this.getAppConfig().getSecure()) ** GOTO lbl33
                var4_7 = this.getAppConfig().getSecureKey();
                var5_8 = false;
                if (!(var4_7.length() == 0)) ** GOTO lbl34
lbl33:
                // 2 sources

                return returnData.setErrorMsg("\u4e0d\u652f\u6301\u7684\u64cd\u4f5c");
lbl34:
                // 1 sources

                var5_9 = context.getBodyAsJson().getString("username");
                username = var5_9 == null ? "" : var5_9;
                var6_10 = context.getBodyAsJson().getString("password");
                password = var6_10 == null ? "" : var6_10;
                var6_10 = username;
                var7_11 = false;
                var8_13 = false;
                if (var6_10.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u7528\u6237\u540d");
                }
                var6_10 = password;
                var7_11 = false;
                var8_13 = false;
                if (var6_10.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u5bc6\u7801");
                }
                if (username.length() < 5) {
                    return returnData.setErrorMsg("\u7528\u6237\u540d\u4e0d\u80fd\u4f4e\u4e8e5\u4f4d");
                }
                if (password.length() < 8) {
                    return returnData.setErrorMsg("\u5bc6\u7801\u4e0d\u80fd\u4f4e\u4e8e8\u4f4d");
                }
                if (username.equals("default")) {
                    return returnData.setErrorMsg("\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u975e\u6cd5\u5b57\u7b26");
                }
                if (!this.checkManagerAuth(context)) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_SECURE_KEY", null, (int)2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                }
                usernameReg = new Regex("[a-z0-9]+", RegexOption.IGNORE_CASE);
                if (!usernameReg.matches((CharSequence)username)) {
                    return returnData.setErrorMsg("\u7528\u6237\u540d\u53ea\u80fd\u7531\u5b57\u6bcd\u548c\u6570\u5b57\u7ec4\u6210");
                }
                var8_13 = false;
                userMap = new LinkedHashMap<K, V>();
                var9_15 /* !! */  = new String[]{"data", "users"};
                userMapJson = ExtKt.asJsonObject((Object)ExtKt.getStorage$default((String[])var9_15 /* !! */ , null, (int)2, null));
                if (userMapJson != null) {
                    var9_15 /* !! */  = userMapJson.getMap();
                    if (var9_15 /* !! */  == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.Map<kotlin.String, kotlin.Any>>");
                    }
                    userMap = TypeIntrinsics.asMutableMap((Object)var9_15 /* !! */ );
                }
                var10_16 = userMap;
                var11_18 = null;
                var12_19 = false;
                v1 = var10_16;
                if (v1 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
                }
                existedUser = v1.getOrDefault(username, var11_18);
                if (existedUser != null) {
                    return returnData.setErrorMsg("\u7528\u6237\u5df2\u5b58\u5728");
                }
                userLimit = this.getUserLimit(context);
                if (userMap.keySet().size() >= userLimit) {
                    return returnData.setErrorMsg("\u8d85\u8fc7\u7528\u6237\u6570\u4e0a\u9650");
                }
                enableWebdav = context.getBodyAsJson().getBoolean("enableWebdav");
                enableLocalStore = context.getBodyAsJson().getBoolean("enableLocalStore");
                enableBookSource = context.getBodyAsJson().getBoolean("enableBookSource");
                enableRssSource = context.getBodyAsJson().getBoolean("enableRssSource");
                bookSourceLimit = context.getBodyAsJson().getInteger("bookSourceLimit");
                bookLimit = context.getBodyAsJson().getInteger("bookLimit");
                salt = ExtKt.getRandomString((int)8);
                passwordEncrypted = ExtKt.genEncryptedPassword((String)password, (String)salt);
                newUser = new User(username, passwordEncrypted, salt, null, 0L, 0L, false, null, false, false, false, 0, 0, 8184, null);
                var20_28 /* !! */  = enableWebdav;
                newUser.setEnable_webdav(var20_28 /* !! */  == null ? this.getAppConfig().getDefaultUserEnableWebdav() : var20_28 /* !! */ .booleanValue());
                var20_28 /* !! */  = enableLocalStore;
                newUser.setEnable_local_store(var20_28 /* !! */  == null ? this.getAppConfig().getDefaultUserEnableLocalStore() : var20_28 /* !! */ .booleanValue());
                var20_28 /* !! */  = enableBookSource;
                newUser.setEnable_book_source(var20_28 /* !! */  == null ? this.getAppConfig().getDefaultUserEnableBookSource() : var20_28 /* !! */ .booleanValue());
                var20_28 /* !! */  = enableRssSource;
                newUser.setEnable_rss_source(var20_28 /* !! */  == null ? this.getAppConfig().getDefaultUserEnableRssSource() : var20_28 /* !! */ .booleanValue());
                var20_28 /* !! */  = bookSourceLimit;
                newUser.setBook_source_limit(var20_28 /* !! */  == null ? this.getAppConfig().getDefaultUserBookSourceLimit() : var20_28 /* !! */ .intValue());
                var20_28 /* !! */  = bookLimit;
                newUser.setBook_limit(var20_28 /* !! */  == null ? this.getAppConfig().getDefaultUserBookLimit() : var20_28 /* !! */ .intValue());
                userMap.put(newUser.getUsername(), ExtKt.toMap((Object)newUser));
                var20_28 /* !! */  = new String[]{"data", "users"};
                v2 = var20_28 /* !! */ ;
                var20_28 /* !! */  = Json.encode((Object)userMap);
                Intrinsics.checkNotNullExpressionValue((Object)var20_28 /* !! */ , (String)"encode(userMap)");
                ExtKt.saveStorage$default(v2, (Object)var20_28 /* !! */ , (boolean)false, null, (int)12, null);
                userList = null;
                var21_29 = false;
                userList = new ArrayList<Map>();
                $this$forEach$iv = userMap;
                $i$f$forEach = false;
                var23_32 = $this$forEach$iv;
                var24_33 = false;
                var25_34 = var23_32.entrySet().iterator();
                while (var25_34.hasNext()) {
                    it = element$iv = var25_34.next();
                    $i$a$-forEach-UserController$addUser$2 = false;
                    userList.add(this.formatUser(it.getValue()));
                }
                return ReturnData.setData$default((ReturnData)returnData, userList, null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object resetPassword(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof resetPassword.1)) ** GOTO lbl-1000
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
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (!this.getAppConfig().getSecure()) ** GOTO lbl33
                var4_7 = this.getAppConfig().getSecureKey();
                var5_8 = false;
                if (!(var4_7.length() == 0)) ** GOTO lbl34
lbl33:
                // 2 sources

                return returnData.setErrorMsg("\u4e0d\u652f\u6301\u7684\u64cd\u4f5c");
lbl34:
                // 1 sources

                var5_9 = context.getBodyAsJson().getString("username");
                username = var5_9 == null ? "" : var5_9;
                var6_10 = context.getBodyAsJson().getString("password");
                password = var6_10 == null ? "" : var6_10;
                var6_10 = username;
                var7_11 = false;
                var8_13 = false;
                if (var6_10.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u7528\u6237\u540d");
                }
                var6_10 = password;
                var7_11 = false;
                var8_13 = false;
                if (var6_10.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u5bc6\u7801");
                }
                if (password.length() < this.getAppConfig().getMinUserPasswordLength()) {
                    return returnData.setErrorMsg("\u5bc6\u7801\u4e0d\u80fd\u4f4e\u4e8e" + this.getAppConfig().getMinUserPasswordLength() + '\u4f4d');
                }
                if (username.equals("default")) {
                    return returnData.setErrorMsg("\u7528\u6237\u4e0d\u5b58\u5728");
                }
                if (!this.checkManagerAuth(context)) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_SECURE_KEY", null, (int)2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                }
                var7_11 = false;
                userMap = new LinkedHashMap<K, V>();
                var8_14 /* !! */  = new String[]{"data", "users"};
                userMapJson = ExtKt.asJsonObject((Object)ExtKt.getStorage$default((String[])var8_14 /* !! */ , null, (int)2, null));
                if (userMapJson != null) {
                    var8_14 /* !! */  = userMapJson.getMap();
                    if (var8_14 /* !! */  == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.MutableMap<kotlin.String, kotlin.Any>>");
                    }
                    userMap = TypeIntrinsics.asMutableMap((Object)var8_14 /* !! */ );
                }
                var9_15 = userMap;
                var10_16 = null;
                var11_17 = false;
                v1 = var9_15;
                if (v1 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
                }
                existedUser = v1.getOrDefault(username, var10_16);
                if (existedUser == null) {
                    return returnData.setErrorMsg("\u7528\u6237\u4e0d\u5b58\u5728");
                }
                salt = ExtKt.getRandomString((int)8);
                passwordEncrypted = ExtKt.genEncryptedPassword((String)password, (String)salt);
                existedUser.put("salt", salt);
                existedUser.put("password", passwordEncrypted);
                userMap.put(username, existedUser);
                var11_18 /* !! */  = new String[]{"data", "users"};
                v2 = var11_18 /* !! */ ;
                var11_18 /* !! */  = Json.encode((Object)userMap);
                Intrinsics.checkNotNullExpressionValue((Object)var11_18 /* !! */ , (String)"encode(userMap as MutableMap<String, Map<String, Any>>)");
                ExtKt.saveStorage$default((String[])v2, (Object)var11_18 /* !! */ , (boolean)false, null, (int)12, null);
                return ReturnData.setData$default((ReturnData)returnData, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object deleteUsers(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteUsers.1)) ** GOTO lbl-1000
        var16_3 = var2_2;
        if ((var16_3.label & -2147483648) != 0) {
            var16_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var17_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var17_5) {
                    return var17_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (!this.getAppConfig().getSecure()) ** GOTO lbl33
                var4_7 = this.getAppConfig().getSecureKey();
                var5_8 = false;
                if (!(var4_7.length() == 0)) ** GOTO lbl34
lbl33:
                // 2 sources

                return returnData.setErrorMsg("\u4e0d\u652f\u6301\u7684\u64cd\u4f5c");
lbl34:
                // 1 sources

                if (!this.checkManagerAuth(context)) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_SECURE_KEY", null, (int)2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                }
                var5_8 = false;
                userMap = new LinkedHashMap<K, V>();
                var6_10 = new String[]{"data", "users"};
                userMapJson = ExtKt.asJsonObject((Object)ExtKt.getStorage$default((String[])var6_10, null, (int)2, null));
                if (userMapJson != null) {
                    var7_11 = 0;
                    userJsonArray = context.getBodyAsJsonArray();
                    var8_15 = userJsonArray.size();
                    if (var7_11 < var8_15) {
                        do {
                            if ((username = userJsonArray.getString(i = var7_11++)) == null || !userMapJson.containsKey(username)) continue;
                            userMapJson.remove(username);
                            var13_22 = username;
                            var12_21 = new String[]{"storage", "data", var13_22};
                            userHome = new File(ExtKt.getWorkDir((String[])var12_21));
                            UserControllerKt.access$getLogger$p().info("delete userHome: {}", (Object)userHome);
                            if (!userHome.exists()) continue;
                            ExtKt.deleteRecursively((File)userHome);
                        } while (var7_11 < var8_15);
                    }
                    if ((var7_12 /* !! */  = userMapJson.getMap()) == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.MutableMap<kotlin.String, kotlin.Any>>");
                    }
                    userMap = TypeIntrinsics.asMutableMap((Object)var7_12 /* !! */ );
                    var7_12 /* !! */  = new String[]{"data", "users"};
                    v1 = var7_12 /* !! */ ;
                    var7_12 /* !! */  = Json.encode((Object)userMap);
                    Intrinsics.checkNotNullExpressionValue((Object)var7_12 /* !! */ , (String)"encode(userMap)");
                    ExtKt.saveStorage$default((String[])v1, (Object)var7_12 /* !! */ , (boolean)false, null, (int)12, null);
                }
                userList = null;
                var7_13 = false;
                userList = new ArrayList<Map>();
                $this$forEach$iv = userMap;
                $i$f$forEach = false;
                var9_17 = $this$forEach$iv;
                var10_19 = false;
                var11_20 = var9_17.entrySet().iterator();
                while (var11_20.hasNext()) {
                    it = element$iv = var11_20.next();
                    $i$a$-forEach-UserController$deleteUsers$2 = false;
                    userList.add(this.formatUser(it.getValue()));
                }
                return ReturnData.setData$default((ReturnData)returnData, userList, null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object clearInactiveUsers(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof clearInactiveUsers.1)) ** GOTO lbl-1000
        var7_3 = var2_2;
        if ((var7_3.label & -2147483648) != 0) {
            var7_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var8_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var8_5) {
                    return var8_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (!this.getAppConfig().getSecure()) ** GOTO lbl33
                var4_7 = this.getAppConfig().getSecureKey();
                var5_8 = false;
                if (!(var4_7.length() == 0)) ** GOTO lbl34
lbl33:
                // 2 sources

                return returnData.setErrorMsg("\u4e0d\u652f\u6301\u7684\u64cd\u4f5c");
lbl34:
                // 1 sources

                if (!this.checkManagerAuth(context)) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_SECURE_KEY", null, (int)2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                }
                inactiveDay = context.getBodyAsJson().getInteger("inactiveDay", Boxing.boxInt((int)0));
                Intrinsics.checkNotNullExpressionValue((Object)inactiveDay, (String)"inactiveDay");
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = null;
                $continuation.label = 2;
                v1 = this.clearInactiveUsers(inactiveDay.intValue(), (Continuation)$continuation);
                if (v1 == var8_5) {
                    return var8_5;
                }
                ** GOTO lbl51
            }
            case 2: {
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl51:
                // 2 sources

                $continuation.L$0 = null;
                $continuation.L$1 = null;
                $continuation.label = 3;
                v2 = this.getUserList(var1_1, (Continuation)$continuation);
                if (v2 == var8_5) {
                    return var8_5;
                }
                ** GOTO lbl61
            }
            case 3: {
                ResultKt.throwOnFailure((Object)$result);
                v2 = $result;
lbl61:
                // 2 sources

                return v2;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public final Object clearInactiveUsers(int day, @NotNull Continuation<? super Unit> $completion) {
        long expireTime = System.currentTimeMillis() - (long)day * 86400L * 1000L;
        Object object = this.forEachUser((Function3)new /* Unavailable Anonymous Inner Class!! */, $completion);
        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return object;
        }
        return Unit.INSTANCE;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object updateUser(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof updateUser.1)) ** GOTO lbl-1000
        var23_3 = var2_2;
        if ((var23_3.label & -2147483648) != 0) {
            var23_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var24_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var24_5) {
                    return var24_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (!this.getAppConfig().getSecure()) ** GOTO lbl33
                var4_7 = this.getAppConfig().getSecureKey();
                var5_8 = false;
                if (!(var4_7.length() == 0)) ** GOTO lbl34
lbl33:
                // 2 sources

                return returnData.setErrorMsg("\u4e0d\u652f\u6301\u7684\u64cd\u4f5c");
lbl34:
                // 1 sources

                if (!this.checkManagerAuth(context)) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_SECURE_KEY", null, (int)2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                }
                var5_9 = context.getBodyAsJson().getString("username");
                username = var5_9 == null ? "" : var5_9;
                var5_9 = username;
                var6_10 = false;
                if (var5_9.length() == 0) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                enableWebdav = context.getBodyAsJson().getBoolean("enableWebdav");
                enableLocalStore = context.getBodyAsJson().getBoolean("enableLocalStore");
                enableBookSource = context.getBodyAsJson().getBoolean("enableBookSource");
                enableRssSource = context.getBodyAsJson().getBoolean("enableRssSource");
                bookSourceLimit = context.getBodyAsJson().getInteger("bookSourceLimit");
                bookLimit = context.getBodyAsJson().getInteger("bookLimit");
                var12_16 = false;
                userMap = (String[])new LinkedHashMap<K, V>();
                var13_19 /* !! */  = new String[]{"data", "users"};
                userMapJson = ExtKt.asJsonObject((Object)ExtKt.getStorage$default((String[])var13_19 /* !! */ , null, (int)2, null));
                if (userMapJson != null) {
                    var13_19 /* !! */  = userMapJson.getMap();
                    if (var13_19 /* !! */  == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.MutableMap<kotlin.String, kotlin.Any>>");
                    }
                    var14_20 /* !! */  = userMap = TypeIntrinsics.asMutableMap((Object)var13_19 /* !! */ );
                    var15_23 = null;
                    var16_25 = false;
                    if (var14_20 /* !! */  == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
                    }
                    existedUser = var14_20 /* !! */ .getOrDefault(username, var15_23);
                    if (existedUser == null) {
                        return returnData.setErrorMsg("\u7528\u6237\u4e0d\u5b58\u5728");
                    }
                    if (enableWebdav != null) {
                        existedUser.put("enable_webdav", enableWebdav);
                    }
                    if (enableLocalStore != null) {
                        existedUser.put("enable_local_store", enableLocalStore);
                    }
                    if (enableBookSource != null) {
                        existedUser.put("enable_book_source", enableBookSource);
                    }
                    if (enableRssSource != null) {
                        existedUser.put("enable_rss_source", enableRssSource);
                    }
                    if (bookSourceLimit != null) {
                        existedUser.put("book_source_limit", bookSourceLimit);
                    }
                    if (bookLimit != null) {
                        existedUser.put("book_limit", bookLimit);
                    }
                    userMap.put(username, existedUser);
                    var14_20 /* !! */  = new String[]{"data", "users"};
                    v1 = var14_20 /* !! */ ;
                    var14_20 /* !! */  = Json.encode((Object)userMap);
                    Intrinsics.checkNotNullExpressionValue((Object)var14_20 /* !! */ , (String)"encode(userMap)");
                    ExtKt.saveStorage$default((String[])v1, (Object)var14_20 /* !! */ , (boolean)false, null, (int)12, null);
                }
                userList = null;
                var14_21 = false;
                userList = new ArrayList<Map>();
                $this$forEach$iv = userMap;
                $i$f$forEach = false;
                var16_26 = $this$forEach$iv;
                var17_27 = false;
                var18_28 = var16_26.entrySet().iterator();
                while (var18_28.hasNext()) {
                    it = element$iv = var18_28.next();
                    $i$a$-forEach-UserController$updateUser$2 = false;
                    userList.add(this.formatUser(it.getValue()));
                }
                return ReturnData.setData$default((ReturnData)returnData, userList, null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getUserInfo(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getUserInfo.1)) ** GOTO lbl-1000
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
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                username = (String)context.session().get("username");
                secure = (Boolean)this.getEnv().getProperty("reader.app.secure", Boolean.TYPE);
                secureKey = this.getEnv().getProperty("reader.app.secureKey");
                userInfo = null;
                if (username != null && (user = this.getUserInfoClass(username)) != null) {
                    userInfo = this.formatUser((Object)user);
                }
                var9_12 = new String[]{"storage", "assets", "fonts"};
                fontsDir = ExtKt.getWorkDir((String[])var9_12);
                fontsList = null;
                var10_13 = false;
                fontsList = new ArrayList<Map>();
                $this$forEach$iv = ExtKt.listFilesRecursively((File)new File(fontsDir));
                $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    it = (File)element$iv;
                    $i$a$-forEach-UserController$getUserInfo$2 = false;
                    var16_22 = it.getName();
                    Intrinsics.checkNotNullExpressionValue((Object)var16_22, (String)"it.name");
                    if (StringsKt.startsWith$default((String)var16_22, (String)".", (boolean)false, (int)2, null) || !it.isFile()) continue;
                    fileName = it.getName();
                    v1 = (BaseController)this;
                    Intrinsics.checkNotNullExpressionValue((Object)fileName, (String)"fileName");
                    ext = BaseController.getFileExt$default((BaseController)v1, (String)fileName, null, (int)2, null);
                    if (!Intrinsics.areEqual((Object)ext, (Object)"ttf")) continue;
                    var18_24 = new Pair[]{TuplesKt.to((Object)"name", (Object)it.getName()), TuplesKt.to((Object)"size", (Object)Boxing.boxLong((long)it.length()))};
                    fontsList.add(MapsKt.mapOf((Pair[])var18_24));
                }
                var10_14 = new Pair[4];
                var10_14[0] = TuplesKt.to((Object)"userInfo", (Object)userInfo);
                var10_14[1] = TuplesKt.to((Object)"secure", (Object)secure);
                var11_16 = secureKey;
                if (var11_16 == null) {
                    v2 = null;
                } else {
                    var12_17 = var11_16;
                    var13_19 = false;
                    v2 = Boxing.boxBoolean((boolean)(var12_17.length() > 0));
                }
                var10_14[2] = TuplesKt.to((Object)"secureKey", v2);
                var10_14[3] = TuplesKt.to((Object)"fonts", fontsList);
                return ReturnData.setData$default((ReturnData)returnData, (Object)MapsKt.mapOf((Pair[])var10_14), null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveUserConfig(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveUserConfig.1)) ** GOTO lbl-1000
        var7_3 = var2_2;
        if ((var7_3.label & -2147483648) != 0) {
            var7_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var8_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var8_5) {
                    return var8_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                content = context.getBodyAsJson();
                if (content == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                content.put("@updateTime", Boxing.boxLong((long)System.currentTimeMillis()));
                userNameSpace = this.getUserNameSpace(context);
                this.saveUserStorage((Object)userNameSpace, "userConfig", (Object)content);
                return ReturnData.setData$default((ReturnData)returnData, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getUserConfig(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getUserConfig.1)) ** GOTO lbl-1000
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
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                userNameSpace = this.getUserNameSpace(context);
                userConfig = ExtKt.asJsonObject((Object)this.getUserStorage((Object)userNameSpace, var6_8 /* !! */  = new String[]{"userConfig"}));
                if (userConfig == null) {
                    return returnData.setErrorMsg("\u6ca1\u6709\u5907\u4efd\u6587\u4ef6");
                }
                var6_8 /* !! */  = userConfig.getMap();
                Intrinsics.checkNotNullExpressionValue((Object)var6_8 /* !! */ , (String)"userConfig.map");
                return ReturnData.setData$default((ReturnData)returnData, (Object)var6_8 /* !! */ , null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object uploadFile(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof uploadFile.1)) ** GOTO lbl-1000
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
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (context.fileUploads() == null || context.fileUploads().isEmpty()) {
                    return returnData.setErrorMsg("\u8bf7\u4e0a\u4f20\u6587\u4ef6");
                }
                userNameSpace = null;
                userNameSpace = this.getUserNameSpace(context);
                fileList = null;
                fileList = new JsonArray();
                type = null;
                type = context.request().getParam("type");
                var7_10 = type;
                var8_11 = false;
                var9_12 = false;
                if (var7_10 == null || var7_10.length() == 0) {
                    type = "images";
                }
                var7_10 = context.fileUploads();
                Intrinsics.checkNotNullExpressionValue((Object)var7_10, (String)"context.fileUploads()");
                $this$forEach$iv = (Iterable)var7_10;
                $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    it = (FileUpload)element$iv;
                    $i$a$-forEach-UserController$uploadFile$2 = false;
                    file = new File(it.uploadedFileName());
                    var14_18 = new Object[]{it.uploadedFileName(), it.fileName(), file};
                    UserControllerKt.access$getLogger$p().info("uploadFile: {} {} {}", var14_18);
                    if (!file.exists()) continue;
                    fileName = it.fileName();
                    var15_19 = new String[5];
                    var15_19[0] = "storage";
                    var15_19[1] = "assets";
                    var15_19[2] = userNameSpace;
                    var16_20 = type;
                    Intrinsics.checkNotNullExpressionValue((Object)var16_20, (String)"type");
                    var15_19[3] = var16_20;
                    var16_20 = fileName;
                    Intrinsics.checkNotNullExpressionValue((Object)var16_20, (String)"fileName");
                    var15_19[4] = var16_20;
                    newFile = new File(ExtKt.getWorkDir((String[])var15_19));
                    if (!newFile.getParentFile().exists()) {
                        newFile.getParentFile().mkdirs();
                    }
                    if (newFile.exists()) {
                        newFile.delete();
                    }
                    UserControllerKt.access$getLogger$p().info("moveTo: {}", (Object)newFile);
                    if (FilesKt.copyRecursively$default((File)file, (File)newFile, (boolean)false, null, (int)6, null)) {
                        fileList.add("/assets/" + userNameSpace + '/' + type + '/' + fileName);
                    }
                    ExtKt.deleteRecursively((File)file);
                }
                var7_10 = fileList.getList();
                Intrinsics.checkNotNullExpressionValue((Object)var7_10, (String)"fileList.getList()");
                return ReturnData.setData$default((ReturnData)returnData, (Object)var7_10, null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object deleteFile(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteFile.1)) ** GOTO lbl-1000
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
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var5_8 = context.getBodyAsJson().getString("url");
                    var4_7 = var5_8 == null ? "" : var5_8;
                } else {
                    var6_9 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue((Object)var6_9, (String)"context.queryParam(\"url\")");
                    var5_8 = (String)CollectionsKt.firstOrNull((List)var6_9);
                    url = var5_8 == null ? "" : var5_8;
                }
                var5_8 = url;
                var6_10 = false;
                var7_12 = false;
                if (var5_8.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u6587\u4ef6\u94fe\u63a5");
                }
                userNameSpace = this.getUserNameSpace(context);
                if (!StringsKt.startsWith$default((String)url, (String)("/assets/" + userNameSpace + '/'), (boolean)false, (int)2, null)) {
                    return returnData.setErrorMsg("\u6587\u4ef6\u94fe\u63a5\u9519\u8bef");
                }
                file = new File(ExtKt.getWorkDir((String)Intrinsics.stringPlus((String)"storage", (Object)url)));
                UserControllerKt.access$getLogger$p().info("delete file: {}", (Object)file);
                ExtKt.deleteRecursively((File)file);
                return ReturnData.setData$default((ReturnData)returnData, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object downloadBackupFile(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof downloadBackupFile.1)) ** GOTO lbl-1000
        var11_3 = var2_2;
        if ((var11_3.label & -2147483648) != 0) {
            var11_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var12_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var12_5) {
                    return var12_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (UserController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    VertExtKt.success((RoutingContext)context, (Object)ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528"));
                    return Unit.INSTANCE;
                }
                bookController = new BookController(this.getCoroutineContext());
                userNameSpace = this.getUserNameSpace(context);
                $continuation.L$0 = context;
                $continuation.L$1 = returnData;
                $continuation.L$2 = bookController;
                $continuation.L$3 = userNameSpace;
                $continuation.label = 2;
                v1 = bookController.getLastBackFileFromWebdav(userNameSpace, (Continuation)$continuation);
                if (v1 == var12_5) {
                    return var12_5;
                }
                ** GOTO lbl48
            }
            case 2: {
                userNameSpace = (String)$continuation.L$3;
                bookController = (BookController)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                var1_1 = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl48:
                // 2 sources

                latestZipFilePath = (String)v1;
                var8_10 = new String[]{"storage", "data", userNameSpace, "backup"};
                backupDir = ExtKt.getWorkDir((String[])var8_10);
                $continuation.L$0 = var1_1;
                $continuation.L$1 = var3_6;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.label = 3;
                v2 = bookController.createUserBackup(userNameSpace, backupDir, latestZipFilePath, (Continuation)$continuation);
                if (v2 == var12_5) {
                    return var12_5;
                }
                ** GOTO lbl65
            }
            case 3: {
                var3_6 = (ReturnData)$continuation.L$1;
                var1_1 = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v2 = $result;
lbl65:
                // 2 sources

                if ((backupFile = (File)v2) == null) {
                    VertExtKt.success((RoutingContext)var1_1, (Object)var3_6.setErrorMsg("\u5907\u4efd\u5931\u8d25"));
                    return Unit.INSTANCE;
                }
                response = var1_1.response().putHeader("Cache-Control", "86400");
                response.putHeader("Content-Disposition", Intrinsics.stringPlus((String)"attachment; filename=", (Object)URLEncoder.encode(backupFile.getName(), "UTF-8")));
                response.sendFile(backupFile.toString());
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object forEachUser(@NotNull Function3<? super CoroutineScope, ? super User, ? super Continuation<? super Boolean>, ? extends Object> var1_1, @NotNull Continuation<? super Unit> var2_2) {
        block14: {
            if (!(var2_2 instanceof forEachUser.1)) ** GOTO lbl-1000
            var29_3 = var2_2;
            if ((var29_3.label & -2147483648) != 0) {
                var29_3.label -= -2147483648;
            } else lbl-1000:
            // 2 sources

            {
                $continuation = new /* Unavailable Anonymous Inner Class!! */;
            }
            $result = $continuation.result;
            var30_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch ($continuation.label) {
                case 0: {
                    ResultKt.throwOnFailure((Object)$result);
                    if (!this.getAppConfig().getSecure()) break block14;
                    userMap = new Ref.ObjectRef();
                    var4_7 = false;
                    userMap.element = new LinkedHashMap<K, V>();
                    var5_9 /* !! */  = new String[]{"data", "users"};
                    userMapJson = ExtKt.asJsonObject((Object)ExtKt.getStorage$default((String[])var5_9 /* !! */ , null, (int)2, null));
                    if (userMapJson != null) {
                        var5_9 /* !! */  = userMapJson.getMap();
                        if (var5_9 /* !! */  == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.Map<kotlin.String, kotlin.Any>>");
                        }
                        userMap.element = TypeIntrinsics.asMutableMap((Object)var5_9 /* !! */ );
                    }
                    hasChanged = new Ref.BooleanRef();
                    var6_10 /* !! */  = (String[])userMap.element;
                    var7_11 = false;
                    var6_10 /* !! */  = var6_10 /* !! */ .entrySet().iterator();
                    var7_11 = false;
                    var8_12 = false;
                    $this$forEachUser_u24lambda_u2d7 = var6_10 /* !! */ ;
                    $i$a$-with-UserController$forEachUser$2 = false;
                    $this$forEach$iv = $this$forEachUser_u24lambda_u2d7;
                    $i$f$forEach = false;
                    var13_17 = $this$forEach$iv;
                    var14_18 = false;
                    var15_19 = var13_17;
lbl36:
                    // 6 sources

                    while (var15_19.hasNext()) {
                        element$iv = var15_19.next();
                        it = (Map.Entry)element$iv;
                        $i$a$-forEach-UserController$forEachUser$2$1 = false;
                        user = (Map)it.getValue();
                        if (user == null) continue;
                        var20_24 = user.getOrDefault("username", "");
                        username = var20_24 == null ? "" : var20_24;
                        var20_24 = username;
                        var22_26 = false;
                        if (!(var20_24.length() > 0)) continue;
                        var23_28 = (Map)userMap.element;
                        var24_29 = null;
                        var25_31 = false;
                        v0 = var23_28;
                        if (v0 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
                        }
                        var22_27 = v0.getOrDefault(username, var24_29);
                        if (var22_27 == null) {
                            v1 = null;
                        } else {
                            $this$toDataClass$iv = var22_27;
                            $i$f$toDataClass = false;
                            $this$convert$iv$iv = $this$toDataClass$iv;
                            $i$f$convert = false;
                            json$iv$iv = $this$convert$iv$iv instanceof String != false ? (String)$this$convert$iv$iv : ExtKt.getGson().toJson((Object)$this$convert$iv$iv);
                            v1 = (User)ExtKt.getGson().fromJson(json$iv$iv, new forEachUser$lambda-7$lambda-6$$inlined$toDataClass$1().getType());
                        }
                        if ((existedUser = v1) == null) continue;
                        $continuation.L$0 = this;
                        $continuation.L$1 = handler;
                        $continuation.L$2 = userMap;
                        $continuation.L$3 = hasChanged;
                        $continuation.L$4 = $this$forEachUser_u24lambda_u2d7;
                        $continuation.L$5 = var15_19;
                        $continuation.label = 1;
                        v2 = handler.invoke((Object)this, (Object)existedUser, (Object)$continuation);
                        if (v2 == var30_5) {
                            return var30_5;
                        }
                        ** GOTO lbl89
                    }
                    break;
                }
                case 1: {
                    $i$a$-with-UserController$forEachUser$2 = false;
                    $i$f$forEach = false;
                    $i$a$-forEach-UserController$forEachUser$2$1 = false;
                    var15_19 = (String[])$continuation.L$5;
                    var9_13 = (Iterator)$continuation.L$4;
                    var5_9 /* !! */  = (String[])$continuation.L$3;
                    var3_6 = (Ref.ObjectRef)$continuation.L$2;
                    var1_1 = (Function3)$continuation.L$1;
                    this = (UserController)$continuation.L$0;
                    ResultKt.throwOnFailure((Object)$result);
                    v2 = $result;
lbl89:
                    // 2 sources

                    if (!((Boolean)v2).booleanValue()) ** GOTO lbl36
                    var5_9 /* !! */ .element = true;
                    var9_13.remove();
                    ** GOTO lbl36
                }
            }
            if (var5_9 /* !! */ .element) {
                var6_10 /* !! */  = new String[]{"data", "users"};
                v3 = var6_10 /* !! */ ;
                var6_10 /* !! */  = Json.encode((Object)var3_6.element);
                Intrinsics.checkNotNullExpressionValue((Object)var6_10 /* !! */ , (String)"encode(userMap)");
                ExtKt.saveStorage$default((String[])v3, (Object)var6_10 /* !! */ , (boolean)false, null, (int)12, null);
            }
        }
        return Unit.INSTANCE;
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}

