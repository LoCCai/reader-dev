/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.api.ReturnData
 *  com.htmake.reader.api.controller.BaseController
 *  com.htmake.reader.api.controller.BookController
 *  com.htmake.reader.api.controller.FileController
 *  com.htmake.reader.api.controller.FileController$checkAccess$1
 *  com.htmake.reader.api.controller.FileController$delete$1
 *  com.htmake.reader.api.controller.FileController$deleteMulti$1
 *  com.htmake.reader.api.controller.FileController$download$1
 *  com.htmake.reader.api.controller.FileController$get$1
 *  com.htmake.reader.api.controller.FileController$importPreview$1
 *  com.htmake.reader.api.controller.FileController$list$1
 *  com.htmake.reader.api.controller.FileController$mkdir$1
 *  com.htmake.reader.api.controller.FileController$parse$1
 *  com.htmake.reader.api.controller.FileController$restore$1
 *  com.htmake.reader.api.controller.FileController$save$1
 *  com.htmake.reader.api.controller.FileController$upload$1
 *  com.htmake.reader.api.controller.FileControllerKt
 *  com.htmake.reader.entity.User
 *  com.htmake.reader.utils.ExtKt
 *  com.htmake.reader.utils.VertExtKt
 *  io.legado.app.data.entities.Book
 *  io.legado.app.exception.TocEmptyException
 *  io.legado.app.model.localBook.LocalBook
 *  io.legado.app.utils.FileUtils
 *  io.vertx.core.http.HttpMethod
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
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.BookController;
import com.htmake.reader.api.controller.FileController;
import com.htmake.reader.api.controller.FileControllerKt;
import com.htmake.reader.entity.User;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.VertExtKt;
import io.legado.app.data.entities.Book;
import io.legado.app.exception.TocEmptyException;
import io.legado.app.model.localBook.LocalBook;
import io.legado.app.utils.FileUtils;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J/\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ\u0019\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001a"}, d2={"Lcom/htmake/reader/api/controller/FileController;", "Lcom/htmake/reader/api/controller/BaseController;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "checkAccess", "Lcom/htmake/reader/api/ReturnData;", "context", "Lio/vertx/ext/web/RoutingContext;", "isSave", "", "isDelete", "(Lio/vertx/ext/web/RoutingContext;ZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delete", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMulti", "download", "", "get", "importPreview", "list", "mkdir", "parse", "restore", "save", "upload", "reader-pro"})
public final class FileController
extends BaseController {
    public FileController(@NotNull CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter((Object)coroutineContext, (String)"coroutineContext");
        super(coroutineContext);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object checkAccess(@NotNull RoutingContext var1_1, boolean var2_2, boolean var3_3, @NotNull Continuation<? super ReturnData> var4_4) {
        if (!(var4_4 instanceof checkAccess.1)) ** GOTO lbl-1000
        var11_5 = var4_4;
        if ((var11_5.label & -2147483648) != 0) {
            var11_5.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var12_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.Z$0 = isSave;
                $continuation.Z$1 = isDelete;
                $continuation.label = 1;
                v0 = this.checkAuth(context, (Continuation)$continuation);
                if (v0 == var12_7) {
                    return var12_7;
                }
                ** GOTO lbl31
            }
            case 1: {
                isDelete = $continuation.Z$1;
                isSave = $continuation.Z$0;
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (FileController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl31:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                context.put("__FILE_HOME__", null);
                var6_9 = null;
                if (context.request().method() == HttpMethod.POST) {
                    if (context.fileUploads() == null || context.fileUploads().isEmpty()) {
                        var7_10 = context.getBodyAsJson().getString("home");
                        var6_9 = var7_10 == null ? "" : var7_10;
                    } else {
                        var7_10 = context.request().getParam("home");
                        home = var7_10 == null ? "" : var7_10;
                    }
                } else {
                    var8_11 = context.queryParam("home");
                    Intrinsics.checkNotNullExpressionValue((Object)var8_11, (String)"context.queryParam(\"home\")");
                    var7_10 = (String)CollectionsKt.firstOrNull((List)var8_11);
                    home = var7_10 == null ? "" : var7_10;
                }
                var7_10 = home;
                tmp = -1;
                switch (var7_10.hashCode()) {
                    case -1330162107: {
                        if (var7_10.equals("__WEBDAV__")) {
                            tmp = 1;
                        }
                        break;
                    }
                    case -1571867763: {
                        if (var7_10.equals("__LOCAL_STORE__")) {
                            tmp = 2;
                        }
                        break;
                    }
                    case -1386618657: {
                        if (var7_10.equals("__HOME__")) {
                            tmp = 3;
                        }
                        break;
                    }
                    case -220135525: {
                        if (var7_10.equals("__STORAGE__")) {
                            tmp = 4;
                        }
                        break;
                    }
                }
                switch (tmp) {
                    case 1: {
                        if (this.getAppConfig().getSecure()) {
                            userInfo = (User)context.get("userInfo");
                            if (userInfo == null) {
                                return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                            }
                            if (!userInfo.getEnable_webdav()) {
                                return returnData.setErrorMsg("\u672a\u5f00\u542fwebdav\u529f\u80fd");
                            }
                        }
                        context.put("__FILE_HOME__", (Object)ExtKt.toDir$default((String)this.getUserWebdavHome((Object)context), (boolean)false, (int)1, null));
                        break;
                    }
                    case 2: {
                        if (this.getAppConfig().getSecure()) {
                            userInfo = (User)context.get("userInfo");
                            if (userInfo == null) {
                                return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                            }
                            if (!userInfo.getEnable_local_store()) {
                                return returnData.setErrorMsg("\u672a\u5f00\u542f\u672c\u5730\u4e66\u4ed3\u529f\u80fd");
                            }
                        }
                        if ((isSave || isDelete) && !this.checkManagerAuth(context)) {
                            return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_SECURE_KEY", null, (int)2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                        }
                        userInfo = new String[]{"storage", "localStore"};
                        context.put("__FILE_HOME__", (Object)ExtKt.toDir$default((String)ExtKt.getWorkDir((String[])userInfo), (boolean)false, (int)1, null));
                        break;
                    }
                    case 3: {
                        userNameSpace = this.getUserNameSpace(context);
                        var9_12 = new String[]{"storage", "data", userNameSpace};
                        context.put("__FILE_HOME__", (Object)ExtKt.toDir$default((String)ExtKt.getWorkDir((String[])var9_12), (boolean)false, (int)1, null));
                        break;
                    }
                    case 4: {
                        if (!this.checkManagerAuth(context)) {
                            return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_SECURE_KEY", null, (int)2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                        }
                        context.put("__FILE_HOME__", (Object)ExtKt.toDir$default((String)ExtKt.getWorkDir((String)"storage"), (boolean)false, (int)1, null));
                        break;
                    }
                    default: {
                        return returnData.setErrorMsg("\u975e\u6cd5\u8bbf\u95ee");
                    }
                }
                FileControllerKt.access$getLogger$p().info("context.__FILE_HOME__ {}", context.get("__FILE_HOME__"));
                return null;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object checkAccess$default(FileController fileController, RoutingContext routingContext, boolean bl, boolean bl2, Continuation continuation, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        if ((n & 4) != 0) {
            bl2 = false;
        }
        return fileController.checkAccess(routingContext, bl, bl2, continuation);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object list(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof list.1)) ** GOTO lbl-1000
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
                $continuation.L$0 = context;
                $continuation.label = 1;
                v0 = FileController.checkAccess$default((FileController)this, (RoutingContext)context, (boolean)false, (boolean)false, (Continuation)$continuation, (int)6, null);
                if (v0 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl22
            }
            case 1: {
                context = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl22:
                // 2 sources

                if ((checkResult = (ReturnData)v0) != null) {
                    return checkResult;
                }
                returnData = new ReturnData();
                var5_8 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var6_9 = context.getBodyAsJson().getString("path");
                    var5_8 = var6_9 == null ? "" : var6_9;
                } else {
                    var7_10 = context.queryParam("path");
                    Intrinsics.checkNotNullExpressionValue((Object)var7_10, (String)"context.queryParam(\"path\")");
                    var6_9 = (String)CollectionsKt.firstOrNull((List)var7_10);
                    path = var6_9 == null ? "" : var6_9;
                }
                var6_9 = path;
                var7_11 = false;
                if (var6_9.length() == 0) {
                    path = "/";
                }
                home = null;
                home = context.get("__FILE_HOME__");
                if (home == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                path = ExtKt.toDir((String)path, (boolean)true);
                file = new File(Intrinsics.stringPlus((String)((String)home), (Object)path));
                FileControllerKt.access$getLogger$p().info("file: {} {}", (Object)path, (Object)file);
                if (!file.exists()) {
                    if (!Intrinsics.areEqual((Object)path, (Object)"/")) {
                        return returnData.setErrorMsg("\u8def\u5f84\u4e0d\u5b58\u5728");
                    }
                    file.mkdirs();
                }
                if (!file.isDirectory()) {
                    return returnData.setErrorMsg("\u8def\u5f84\u4e0d\u662f\u76ee\u5f55");
                }
                fileList = null;
                var9_14 = false;
                fileList = new ArrayList<Map>();
                var9_15 = file.listFiles();
                Intrinsics.checkNotNullExpressionValue((Object)var9_15, (String)"file.listFiles()");
                $this$forEach$iv = var9_15;
                $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    it = (File)element$iv;
                    $i$a$-forEach-FileController$list$2 = false;
                    var17_23 = it.getName();
                    Intrinsics.checkNotNullExpressionValue((Object)var17_23, (String)"it.name");
                    if (StringsKt.startsWith$default((String)var17_23, (String)".", (boolean)false, (int)2, null)) continue;
                    var17_23 = new Pair[5];
                    var17_23[0] = TuplesKt.to((Object)"name", (Object)it.getName());
                    var17_23[1] = TuplesKt.to((Object)"size", (Object)Boxing.boxLong((long)it.length()));
                    var18_24 = it.toString();
                    Intrinsics.checkNotNullExpressionValue((Object)var18_24, (String)"it.toString()");
                    var17_23[2] = TuplesKt.to((Object)"path", (Object)StringsKt.replace$default((String)var18_24, (String)((String)home), (String)"", (boolean)false, (int)4, null));
                    var17_23[3] = TuplesKt.to((Object)"lastModified", (Object)Boxing.boxLong((long)it.lastModified()));
                    var17_23[4] = TuplesKt.to((Object)"isDirectory", (Object)Boxing.boxBoolean((boolean)it.isDirectory()));
                    fileList.add(MapsKt.mapOf((Pair[])var17_23));
                }
                return ReturnData.setData$default((ReturnData)returnData, fileList, null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object upload(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof upload.1)) ** GOTO lbl-1000
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
                if (context.fileUploads() == null || context.fileUploads().isEmpty()) {
                    return returnData.setErrorMsg("\u8bf7\u4e0a\u4f20\u6587\u4ef6");
                }
                $continuation.L$0 = context;
                $continuation.L$1 = returnData;
                $continuation.label = 1;
                v0 = FileController.checkAccess$default((FileController)this, (RoutingContext)context, (boolean)true, (boolean)false, (Continuation)$continuation, (int)4, null);
                if (v0 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                var3_6 = (ReturnData)$continuation.L$1;
                var1_1 = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if ((checkResult = (ReturnData)v0) != null) {
                    return checkResult;
                }
                path = null;
                path = var1_1.request().getParam("path");
                var6_9 = path;
                var7_10 = false;
                var8_12 = false;
                if (var6_9 == null || var6_9.length() == 0) {
                    path = "/";
                }
                var6_9 = path;
                Intrinsics.checkNotNullExpressionValue((Object)var6_9, (String)"path");
                path = ExtKt.toDir((String)var6_9, (boolean)true);
                fileList = null;
                var7_10 = false;
                fileList = new ArrayList<Map>();
                home = null;
                home = var1_1.get("__FILE_HOME__");
                if (home == null) {
                    return var3_6.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                var8_13 = var1_1.fileUploads();
                Intrinsics.checkNotNullExpressionValue((Object)var8_13, (String)"context.fileUploads()");
                $this$forEach$iv = var8_13;
                $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    it = (FileUpload)element$iv;
                    $i$a$-forEach-FileController$upload$2 = false;
                    file = new File(it.uploadedFileName());
                    var15_20 = new Object[]{it.uploadedFileName(), it.fileName(), file};
                    FileControllerKt.access$getLogger$p().info("uploadFile: {} {} {}", var15_20);
                    if (!file.exists()) continue;
                    fileName = it.fileName();
                    newFile = new File(home + path + File.separator + fileName);
                    if (!newFile.getParentFile().exists()) {
                        newFile.getParentFile().mkdirs();
                    }
                    if (newFile.exists()) {
                        newFile.delete();
                    }
                    FileControllerKt.access$getLogger$p().info("moveTo: {}", (Object)newFile);
                    if (FilesKt.copyRecursively$default((File)file, (File)newFile, (boolean)false, null, (int)6, null)) {
                        var17_22 = new Pair[5];
                        var17_22[0] = TuplesKt.to((Object)"name", (Object)newFile.getName());
                        var17_22[1] = TuplesKt.to((Object)"size", (Object)Boxing.boxLong((long)newFile.length()));
                        var18_23 = newFile.toString();
                        Intrinsics.checkNotNullExpressionValue((Object)var18_23, (String)"newFile.toString()");
                        var17_22[2] = TuplesKt.to((Object)"path", (Object)StringsKt.replace$default((String)var18_23, (String)((String)home), (String)"", (boolean)false, (int)4, null));
                        var17_22[3] = TuplesKt.to((Object)"lastModified", (Object)Boxing.boxLong((long)newFile.lastModified()));
                        var17_22[4] = TuplesKt.to((Object)"isDirectory", (Object)Boxing.boxBoolean((boolean)newFile.isDirectory()));
                        fileList.add(MapsKt.mapOf((Pair[])var17_22));
                    }
                    FilesKt.deleteRecursively((File)file);
                }
                return ReturnData.setData$default((ReturnData)var3_6, fileList, null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object download(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof download.1)) ** GOTO lbl-1000
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
                $continuation.L$0 = context;
                $continuation.label = 1;
                v0 = FileController.checkAccess$default((FileController)this, (RoutingContext)context, (boolean)false, (boolean)false, (Continuation)$continuation, (int)6, null);
                if (v0 == var13_5) {
                    return var13_5;
                }
                ** GOTO lbl22
            }
            case 1: {
                context = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl22:
                // 2 sources

                if ((checkResult = (ReturnData)v0) != null) {
                    VertExtKt.success((RoutingContext)context, (Object)checkResult);
                    return Unit.INSTANCE;
                }
                returnData = new ReturnData();
                var5_8 = null;
                var6_9 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var7_10 = context.getBodyAsJson().getString("path");
                    path = var7_10 == null ? "" : var7_10;
                    var7_10 = context.getBodyAsJson().getInteger("stream", Boxing.boxInt((int)0));
                    Intrinsics.checkNotNullExpressionValue((Object)var7_10, (String)"context.bodyAsJson.getInteger(\"stream\", 0)");
                    var6_9 = ((Number)var7_10).intValue();
                } else {
                    var8_11 = context.queryParam("path");
                    Intrinsics.checkNotNullExpressionValue((Object)var8_11, (String)"context.queryParam(\"path\")");
                    var7_10 = (String)CollectionsKt.firstOrNull((List)var8_11);
                    path = var7_10 == null ? "" : var7_10;
                    var8_11 = context.queryParam("stream");
                    Intrinsics.checkNotNullExpressionValue((Object)var8_11, (String)"context.queryParam(\"stream\")");
                    var7_10 = (String)CollectionsKt.firstOrNull((List)var8_11);
                    if (var7_10 == null) {
                        v1 = 0;
                    } else {
                        var9_14 = var7_10;
                        var10_15 = false;
                        var8_11 = Boxing.boxInt((int)Integer.parseInt((String)var9_14));
                        v1 = var8_11 == null ? 0 : var8_11.intValue();
                    }
                    stream = v1;
                }
                var7_10 = path;
                var8_12 = false;
                if (var7_10.length() == 0) {
                    VertExtKt.success((RoutingContext)context, (Object)returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef"));
                    return Unit.INSTANCE;
                }
                home = (String)context.get("__FILE_HOME__");
                if (home == null) {
                    VertExtKt.success((RoutingContext)context, (Object)returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef"));
                    return Unit.INSTANCE;
                }
                path = ExtKt.toDir((String)path, (boolean)true);
                file = new File(Intrinsics.stringPlus((String)home, (Object)path));
                FileControllerKt.access$getLogger$p().info("file: {} {}", (Object)path, (Object)file);
                if (!file.exists()) {
                    VertExtKt.success((RoutingContext)context, (Object)returnData.setErrorMsg("\u8def\u5f84\u4e0d\u5b58\u5728"));
                    return Unit.INSTANCE;
                }
                response = context.response().putHeader("Cache-Control", "86400");
                if (stream <= 0) {
                    response.putHeader("Content-Disposition", Intrinsics.stringPlus((String)"attachment; filename=", (Object)URLEncoder.encode(file.getName(), "UTF-8")));
                }
                response.sendFile(file.toString());
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object get(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof get.1)) ** GOTO lbl-1000
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
                $continuation.L$0 = context;
                $continuation.label = 1;
                v0 = FileController.checkAccess$default((FileController)this, (RoutingContext)context, (boolean)false, (boolean)false, (Continuation)$continuation, (int)6, null);
                if (v0 == var10_5) {
                    return var10_5;
                }
                ** GOTO lbl22
            }
            case 1: {
                context = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl22:
                // 2 sources

                if ((checkResult = (ReturnData)v0) != null) {
                    return checkResult;
                }
                returnData = new ReturnData();
                var5_8 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var6_9 = context.getBodyAsJson().getString("path");
                    var5_8 = var6_9 == null ? "" : var6_9;
                } else {
                    var7_10 = context.queryParam("path");
                    Intrinsics.checkNotNullExpressionValue((Object)var7_10, (String)"context.queryParam(\"path\")");
                    var6_9 = (String)CollectionsKt.firstOrNull((List)var7_10);
                    path = var6_9 == null ? "" : var6_9;
                }
                var6_9 = path;
                var7_11 = false;
                if (var6_9.length() == 0) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                home = (String)context.get("__FILE_HOME__");
                if (home == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                path = ExtKt.toDir((String)path, (boolean)true);
                file = new File(Intrinsics.stringPlus((String)home, (Object)path));
                FileControllerKt.access$getLogger$p().info("file: {} {}", (Object)path, (Object)file);
                if (!file.exists()) {
                    return returnData.setErrorMsg("\u8def\u5f84\u4e0d\u5b58\u5728");
                }
                return ReturnData.setData$default((ReturnData)returnData, (Object)FilesKt.readText$default((File)file, null, (int)1, null), null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object save(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof save.1)) ** GOTO lbl-1000
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
                $continuation.L$0 = context;
                $continuation.label = 1;
                v0 = FileController.checkAccess$default((FileController)this, (RoutingContext)context, (boolean)true, (boolean)false, (Continuation)$continuation, (int)4, null);
                if (v0 == var11_5) {
                    return var11_5;
                }
                ** GOTO lbl22
            }
            case 1: {
                context = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl22:
                // 2 sources

                if ((checkResult = (ReturnData)v0) != null) {
                    return checkResult;
                }
                returnData = new ReturnData();
                var6_8 = context.getBodyAsJson().getString("path", "");
                path = var6_8 == null ? "" : var6_8;
                var7_10 = context.getBodyAsJson().getString("content", "");
                content = var7_10 == null ? "" : var7_10;
                var7_10 = path;
                var8_11 = false;
                if (var7_10.length() == 0) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                home = (String)context.get("__FILE_HOME__");
                if (home == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                path = ExtKt.toDir((String)path, (boolean)true);
                file = FileUtils.INSTANCE.createFileWithReplace(Intrinsics.stringPlus((String)home, (Object)path));
                FileControllerKt.access$getLogger$p().info("file: {} {}", (Object)path, (Object)file);
                FilesKt.writeText$default((File)file, (String)content, null, (int)2, null);
                return ReturnData.setData$default((ReturnData)returnData, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object mkdir(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof mkdir.1)) ** GOTO lbl-1000
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
                $continuation.L$0 = context;
                $continuation.label = 1;
                v0 = FileController.checkAccess$default((FileController)this, (RoutingContext)context, (boolean)true, (boolean)false, (Continuation)$continuation, (int)4, null);
                if (v0 == var11_5) {
                    return var11_5;
                }
                ** GOTO lbl22
            }
            case 1: {
                context = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl22:
                // 2 sources

                if ((checkResult = (ReturnData)v0) != null) {
                    return checkResult;
                }
                returnData = new ReturnData();
                var6_8 = context.getBodyAsJson().getString("path", "");
                path = var6_8 == null ? "" : var6_8;
                var6_8 = path;
                var7_10 = false;
                if (var6_8.length() == 0) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                var7_11 = context.getBodyAsJson().getString("name", "");
                name = var7_11 == null ? "" : var7_11;
                var7_11 = name;
                var8_12 = false;
                if (var7_11.length() == 0 || StringsKt.startsWith$default((String)name, (String)".", (boolean)false, (int)2, null)) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                home = (String)context.get("__FILE_HOME__");
                if (home == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                path = ExtKt.toDir((String)path, (boolean)true);
                file = new File(home + path + File.separator + name);
                FileControllerKt.access$getLogger$p().info("file: {} {}", (Object)path, (Object)file);
                if (file.exists()) {
                    return returnData.setErrorMsg("\u8def\u5f84\u5df2\u5b58\u5728");
                }
                file.mkdirs();
                return ReturnData.setData$default((ReturnData)returnData, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object delete(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof delete.1)) ** GOTO lbl-1000
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
                $continuation.L$0 = context;
                $continuation.label = 1;
                v0 = this.checkAccess(context, false, true, (Continuation)$continuation);
                if (v0 == var10_5) {
                    return var10_5;
                }
                ** GOTO lbl22
            }
            case 1: {
                context = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl22:
                // 2 sources

                if ((checkResult = (ReturnData)v0) != null) {
                    return checkResult;
                }
                returnData = new ReturnData();
                var5_8 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var6_9 = context.getBodyAsJson().getString("path");
                    var5_8 = var6_9 == null ? "" : var6_9;
                } else {
                    var7_10 = context.queryParam("path");
                    Intrinsics.checkNotNullExpressionValue((Object)var7_10, (String)"context.queryParam(\"path\")");
                    var6_9 = (String)CollectionsKt.firstOrNull((List)var7_10);
                    path = var6_9 == null ? "" : var6_9;
                }
                var6_9 = path;
                var7_11 = false;
                if (var6_9.length() == 0) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                home = (String)context.get("__FILE_HOME__");
                if (home == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                path = ExtKt.toDir((String)path, (boolean)true);
                file = new File(Intrinsics.stringPlus((String)home, (Object)path));
                FileControllerKt.access$getLogger$p().info("file: {} {}", (Object)path, (Object)file);
                if (!file.exists()) {
                    return returnData.setErrorMsg("\u8def\u5f84\u4e0d\u5b58\u5728");
                }
                FilesKt.deleteRecursively((File)file);
                return ReturnData.setData$default((ReturnData)returnData, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object deleteMulti(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteMulti.1)) ** GOTO lbl-1000
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
                $continuation.L$0 = context;
                $continuation.label = 1;
                v0 = this.checkAccess(context, false, true, (Continuation)$continuation);
                if (v0 == var18_5) {
                    return var18_5;
                }
                ** GOTO lbl22
            }
            case 1: {
                context = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl22:
                // 2 sources

                if ((checkResult = (ReturnData)v0) != null) {
                    return checkResult;
                }
                returnData = new ReturnData();
                path = context.getBodyAsJson().getJsonArray("path");
                if (path == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                home = null;
                home = context.get("__FILE_HOME__");
                if (home == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                var7_10 = path;
                $this$forEach$iv = (Iterable)var7_10;
                $i$f$forEach = false;
                var9_12 = $this$forEach$iv.iterator();
                while (var9_12.hasNext()) {
                    it = element$iv = var9_12.next();
                    $i$a$-forEach-FileController$deleteMulti$2 = false;
                    var13_16 = (String)it;
                    filePath = var13_16 == null ? "" : var13_16;
                    var13_16 = filePath;
                    var15_18 = false;
                    if (!(var13_16.length() > 0)) continue;
                    file = new File(Intrinsics.stringPlus((String)((String)home), (Object)ExtKt.toDir((String)filePath, (boolean)true)));
                    FilesKt.deleteRecursively((File)file);
                }
                return ReturnData.setData$default((ReturnData)returnData, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object importPreview(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof importPreview.1)) ** GOTO lbl-1000
        var27_3 = var2_2;
        if ((var27_3.label & -2147483648) != 0) {
            var27_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var28_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.label = 1;
                v0 = FileController.checkAccess$default((FileController)this, (RoutingContext)context, (boolean)false, (boolean)false, (Continuation)$continuation, (int)6, null);
                if (v0 == var28_5) {
                    return var28_5;
                }
                ** GOTO lbl24
            }
            case 1: {
                context = (RoutingContext)$continuation.L$1;
                this = (FileController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl24:
                // 2 sources

                if ((checkResult = (ReturnData)v0) != null) {
                    return checkResult;
                }
                returnData = new ReturnData();
                paths = context.getBodyAsJson().getJsonArray("path");
                if (paths == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                home = null;
                home = context.get("__FILE_HOME__");
                if (home == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                fileList = null;
                var8_11 = false;
                fileList = new ArrayList<Map>();
                userNameSpace = null;
                userNameSpace = this.getUserNameSpace(context);
                var9_13 = paths;
                $this$forEach$iv = (Iterable)var9_13;
                $i$f$forEach = false;
                var11_15 = $this$forEach$iv.iterator();
                while (var11_15.hasNext()) {
                    it = element$iv = var11_15.next();
                    $i$a$-forEach-FileController$importPreview$2 = false;
                    var15_19 = (String)it;
                    path = var15_19 == null ? "" : var15_19;
                    var15_19 = path;
                    var17_21 = false;
                    if (!(var15_19.length() > 0)) continue;
                    path = Intrinsics.stringPlus((String)((String)home), (Object)path);
                    file = new File(path);
                    FileControllerKt.access$getLogger$p().info("localFile: {} {}", (Object)path, (Object)file);
                    if (!file.exists() || file.isDirectory()) continue;
                    fileName = file.getName();
                    v1 = (BaseController)this;
                    Intrinsics.checkNotNullExpressionValue((Object)fileName, (String)"fileName");
                    ext = BaseController.getFileExt$default((BaseController)v1, (String)fileName, null, (int)2, null);
                    if (!(Intrinsics.areEqual((Object)ext, (Object)"txt") || Intrinsics.areEqual((Object)ext, (Object)"epub") || Intrinsics.areEqual((Object)ext, (Object)"umd") || Intrinsics.areEqual((Object)ext, (Object)"cbz") || Intrinsics.areEqual((Object)ext, (Object)"pdf"))) {
                        return returnData.setErrorMsg("\u4e0d\u652f\u6301\u5bfc\u5165" + ext + "\u683c\u5f0f\u7684\u4e66\u7c4d\u6587\u4ef6");
                    }
                    rootDir = ExtKt.getWorkDir$default(null, (int)1, null);
                    var20_25 = File.separator;
                    Intrinsics.checkNotNullExpressionValue((Object)var20_25, (String)"separator");
                    if (!StringsKt.endsWith$default((String)rootDir, (String)var20_25, (boolean)false, (int)2, null)) {
                        rootDir = Intrinsics.stringPlus((String)rootDir, (Object)File.separator);
                    }
                    relativePath = path;
                    FileControllerKt.access$getLogger$p().info("rootDir: {} path: {}", (Object)rootDir, (Object)path);
                    if (StringsKt.startsWith$default((String)relativePath, (String)rootDir, (boolean)false, (int)2, null)) {
                        relativePath = StringsKt.replaceFirst$default((String)relativePath, (String)rootDir, (String)"", (boolean)false, (int)4, null);
                    }
                    FileControllerKt.access$getLogger$p().info("relative path: {}", (Object)relativePath);
                    url = StringsKt.replace$default((String)relativePath, (String)"\\", (String)"/", (boolean)false, (int)4, null);
                    book = Book.Companion.initLocalBook(url, relativePath, rootDir);
                    book.setUserNameSpace(userNameSpace);
                    FileControllerKt.access$getLogger$p().info("book {}", (Object)book);
                    try {
                        chapters = LocalBook.INSTANCE.getChapterList(book);
                        var24_30 = new Pair[]{TuplesKt.to((Object)"book", (Object)book), TuplesKt.to((Object)"chapters", (Object)chapters)};
                        fileList.add(MapsKt.mapOf((Pair[])var24_30));
                    }
                    catch (TocEmptyException var23_29) {
                        var24_30 = new Pair[2];
                        var24_30[0] = TuplesKt.to((Object)"book", (Object)book);
                        var25_31 = false;
                        var24_30[1] = TuplesKt.to((Object)"chapters", new ArrayList<E>());
                        fileList.add(MapsKt.mapOf((Pair[])var24_30));
                    }
                }
                return ReturnData.setData$default((ReturnData)returnData, fileList, null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object restore(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof restore.1)) ** GOTO lbl-1000
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
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.label = 1;
                v0 = FileController.checkAccess$default((FileController)this, (RoutingContext)context, (boolean)false, (boolean)false, (Continuation)$continuation, (int)6, null);
                if (v0 == var13_5) {
                    return var13_5;
                }
                ** GOTO lbl24
            }
            case 1: {
                context = (RoutingContext)$continuation.L$1;
                this = (FileController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl24:
                // 2 sources

                if ((checkResult = (ReturnData)v0) != null) {
                    return checkResult;
                }
                returnData = new ReturnData();
                var5_8 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var6_9 = context.getBodyAsJson().getString("path");
                    var5_8 = var6_9 == null ? "" : var6_9;
                } else {
                    var7_10 = context.queryParam("path");
                    Intrinsics.checkNotNullExpressionValue((Object)var7_10, (String)"context.queryParam(\"path\")");
                    var6_9 = (String)CollectionsKt.firstOrNull((List)var7_10);
                    path = var6_9 == null ? "" : var6_9;
                }
                var6_9 = path;
                var7_11 = false;
                if (var6_9.length() == 0) {
                    path = "/";
                }
                if (!Intrinsics.areEqual((Object)(ext = BaseController.getFileExt$default((BaseController)((BaseController)this), (String)path, null, (int)2, null)), (Object)"zip")) {
                    return returnData.setErrorMsg("\u8def\u5f84\u4e0d\u662fzip\u5907\u4efd\u6587\u4ef6");
                }
                home = (String)context.get("__FILE_HOME__");
                if (home == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                file = new File(Intrinsics.stringPlus((String)home, (Object)path));
                FileControllerKt.access$getLogger$p().info("file: {} {}", (Object)path, (Object)file);
                if (!file.exists()) {
                    return returnData.setErrorMsg("\u8def\u5f84\u4e0d\u5b58\u5728");
                }
                bookController = new BookController(this.getCoroutineContext());
                var10_15 = file.toString();
                Intrinsics.checkNotNullExpressionValue((Object)var10_15, (String)"file.toString()");
                $continuation.L$0 = returnData;
                $continuation.L$1 = null;
                $continuation.label = 2;
                v1 = bookController.syncFromWebdav(var10_15, this.getUserNameSpace(context), (Continuation)$continuation);
                if (v1 == var13_5) {
                    return var13_5;
                }
                ** GOTO lbl63
            }
            case 2: {
                var4_7 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl63:
                // 2 sources

                if (!((Boolean)v1).booleanValue()) {
                    return var4_7.setErrorMsg("\u6062\u590d\u5931\u8d25");
                }
                return ReturnData.setData$default((ReturnData)var4_7, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object parse(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof parse.1)) ** GOTO lbl-1000
        var27_3 = var2_2;
        if ((var27_3.label & -2147483648) != 0) {
            var27_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var28_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.label = 1;
                v0 = FileController.checkAccess$default((FileController)this, (RoutingContext)context, (boolean)false, (boolean)false, (Continuation)$continuation, (int)6, null);
                if (v0 == var28_5) {
                    return var28_5;
                }
                ** GOTO lbl24
            }
            case 1: {
                context = (RoutingContext)$continuation.L$1;
                this = (FileController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl24:
                // 2 sources

                if ((checkResult = (ReturnData)v0) != null) {
                    return checkResult;
                }
                returnData = new ReturnData();
                home = null;
                home = context.get("__FILE_HOME__");
                if (home == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                path = null;
                import = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var8_11 = context.getBodyAsJson().getString("path");
                    path = var8_11 == null ? "" : var8_11;
                    var8_11 = context.getBodyAsJson().getInteger("import", Boxing.boxInt((int)0));
                    Intrinsics.checkNotNullExpressionValue((Object)var8_11, (String)"context.bodyAsJson.getInteger(\"import\", 0)");
                    import = ((Number)var8_11).intValue();
                } else {
                    var9_12 = context.queryParam("path");
                    Intrinsics.checkNotNullExpressionValue((Object)var9_12, (String)"context.queryParam(\"path\")");
                    var8_11 = (String)CollectionsKt.firstOrNull((List)var9_12);
                    path = var8_11 == null ? "" : var8_11;
                    var9_12 = context.queryParam("import");
                    Intrinsics.checkNotNullExpressionValue((Object)var9_12, (String)"context.queryParam(\"import\")");
                    var8_11 = (String)CollectionsKt.firstOrNull((List)var9_12);
                    if (var8_11 == null) {
                        v1 = 0;
                    } else {
                        var10_15 = var8_11;
                        var11_18 = false;
                        var9_12 = Boxing.boxInt((int)Integer.parseInt((String)var10_15));
                        v1 = var9_12 == null ? 0 : var9_12.intValue();
                    }
                    import = v1;
                }
                var8_11 = path;
                var9_13 = false;
                if (var8_11.length() == 0) {
                    path = "/";
                }
                file = new File(Intrinsics.stringPlus((String)((String)home), (Object)path));
                FileControllerKt.access$getLogger$p().info("file: {} {}", (Object)path, (Object)file);
                if (!file.exists()) {
                    return returnData.setErrorMsg("\u8def\u5f84\u4e0d\u5b58\u5728");
                }
                if (!file.isDirectory()) {
                    return returnData.setErrorMsg("\u8def\u5f84\u4e0d\u662f\u76ee\u5f55");
                }
                fileList = null;
                var10_16 = false;
                fileList = new ArrayList<Map>();
                userNameSpace = null;
                userNameSpace = this.getUserNameSpace(context);
                rootDir = null;
                rootDir = ExtKt.getWorkDir$default(null, (int)1, null);
                var12_20 = File.separator;
                Intrinsics.checkNotNullExpressionValue((Object)var12_20, (String)"separator");
                if (!StringsKt.endsWith$default((String)rootDir, (String)var12_20, (boolean)false, (int)2, null)) {
                    rootDir = Intrinsics.stringPlus((String)rootDir, (Object)File.separator);
                }
                bookController = new BookController(this.getCoroutineContext());
                $this$forEach$iv = ExtKt.listFilesRecursively((File)file);
                $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    it = (File)element$iv;
                    $i$a$-forEach-FileController$parse$2 = false;
                    var19_27 = it.getName();
                    Intrinsics.checkNotNullExpressionValue((Object)var19_27, (String)"it.name");
                    if (StringsKt.startsWith$default((String)var19_27, (String)".", (boolean)false, (int)2, null) || !it.isFile()) continue;
                    fileName = it.getName();
                    v2 = (BaseController)this;
                    Intrinsics.checkNotNullExpressionValue((Object)fileName, (String)"fileName");
                    ext = BaseController.getFileExt$default((BaseController)v2, (String)fileName, null, (int)2, null);
                    tmp = -1;
                    switch (ext.hashCode()) {
                        case 115312: {
                            if (ext.equals("txt")) {
                                tmp = 1;
                            }
                            break;
                        }
                        case 110834: {
                            if (ext.equals("pdf")) {
                                tmp = 1;
                            }
                            break;
                        }
                        case 3120248: {
                            if (ext.equals("epub")) {
                                tmp = 1;
                            }
                            break;
                        }
                        case 98299: {
                            if (ext.equals("cbz")) {
                                tmp = 1;
                            }
                            break;
                        }
                        case 115916: {
                            if (ext.equals("umd")) {
                                tmp = 1;
                            }
                            break;
                        }
                    }
                    switch (tmp) {
                        case 1: {
                            relativePath = it.getPath();
                            FileControllerKt.access$getLogger$p().debug("rootDir: {} path: {}", (Object)rootDir, (Object)path);
                            var22_30 = relativePath;
                            Intrinsics.checkNotNullExpressionValue((Object)var22_30, (String)"relativePath");
                            if (StringsKt.startsWith$default((String)var22_30, (String)rootDir, (boolean)false, (int)2, null)) {
                                var22_30 = relativePath;
                                Intrinsics.checkNotNullExpressionValue((Object)var22_30, (String)"relativePath");
                                relativePath = StringsKt.replaceFirst$default((String)var22_30, (String)rootDir, (String)"", (boolean)false, (int)4, null);
                            }
                            FileControllerKt.access$getLogger$p().debug("relative path: {}", (Object)relativePath);
                            var23_31 = relativePath;
                            Intrinsics.checkNotNullExpressionValue((Object)var23_31, (String)"relativePath");
                            url = StringsKt.replace$default((String)var23_31, (String)"\\", (String)"/", (boolean)false, (int)4, null);
                            var24_32 = relativePath;
                            Intrinsics.checkNotNullExpressionValue((Object)var24_32, (String)"relativePath");
                            book = Book.Companion.initLocalBook(url, (String)var24_32, rootDir);
                            book.setUserNameSpace(userNameSpace);
                            FileControllerKt.access$getLogger$p().debug("book {}", (Object)book);
                            if (import > 0) {
                                result = bookController.saveBookToShelf(book, userNameSpace, context);
                                if (result.getSecond() != null || !((Book)result.getFirst()).isInShelf()) break;
                                fileList.add(MapsKt.mapOf((Pair)TuplesKt.to((Object)"name", (Object)it.getName())));
                                break;
                            }
                            var24_32 = new Pair[5];
                            var24_32[0] = TuplesKt.to((Object)"name", (Object)it.getName());
                            var24_32[1] = TuplesKt.to((Object)"size", (Object)Boxing.boxLong((long)it.length()));
                            var25_33 = it.toString();
                            Intrinsics.checkNotNullExpressionValue((Object)var25_33, (String)"it.toString()");
                            var24_32[2] = TuplesKt.to((Object)"path", (Object)StringsKt.replaceFirst$default((String)var25_33, (String)((String)home), (String)"", (boolean)false, (int)4, null));
                            var24_32[3] = TuplesKt.to((Object)"lastModified", (Object)Boxing.boxLong((long)it.lastModified()));
                            var24_32[4] = TuplesKt.to((Object)"book", (Object)book);
                            fileList.add(MapsKt.mapOf((Pair[])var24_32));
                        }
                    }
                }
                return ReturnData.setData$default((ReturnData)returnData, fileList, null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}

