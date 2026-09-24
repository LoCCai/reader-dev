/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.api.ReturnData
 *  com.htmake.reader.api.controller.BaseController
 *  com.htmake.reader.api.controller.BookGroupController
 *  com.htmake.reader.api.controller.BookGroupController$saveBookGroupOrder$1
 *  com.htmake.reader.api.controller.CURD
 *  com.htmake.reader.api.controller.CURD$DefaultImpls
 *  com.htmake.reader.db.DB
 *  com.htmake.reader.utils.ExtKt
 *  io.legado.app.data.entities.BookGroup
 *  io.vertx.core.json.JsonArray
 *  io.vertx.core.json.JsonObject
 *  io.vertx.ext.web.RoutingContext
 *  kotlin.Metadata
 *  kotlin.ResultKt
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.CoroutineContext
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.coroutines.jvm.internal.Boxing
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.BookGroupController;
import com.htmake.reader.api.controller.CURD;
import com.htmake.reader.db.DB;
import com.htmake.reader.utils.ExtKt;
import io.legado.app.data.entities.BookGroup;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.util.Iterator;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u0016J\u0019\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0003H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J \u0010\u0019\u001a\u00020\u001a2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\t\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u0017H\u0016J\u0019\u0010\u001f\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006 "}, d2={"Lcom/htmake/reader/api/controller/BookGroupController;", "Lcom/htmake/reader/api/controller/BaseController;", "Lcom/htmake/reader/api/controller/CURD;", "Lio/legado/app/data/entities/BookGroup;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "beforeSave", "Lcom/htmake/reader/api/ReturnData;", "var1", "db", "Lcom/htmake/reader/db/DB;", "checkUserAuth", "", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checker", "Lio/vertx/core/json/JsonObject;", "var2", "getEntityClass", "Ljava/lang/Class;", "getTableName", "", "getUserNS", "onCheckEnd", "", "bookGroupList", "Lio/vertx/core/json/JsonArray;", "onList", "userNameSpace", "saveBookGroupOrder", "reader-pro"})
public final class BookGroupController
extends BaseController
implements CURD<BookGroup> {
    public BookGroupController(@NotNull CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter((Object)coroutineContext, (String)"coroutineContext");
        super(coroutineContext);
    }

    @NotNull
    public String getTableName() {
        return "bookGroup";
    }

    @NotNull
    public JsonArray onList(@NotNull JsonArray var1, @NotNull String userNameSpace) {
        JsonArray var2;
        Intrinsics.checkNotNullParameter((Object)var1, (String)"var1");
        Intrinsics.checkNotNullParameter((Object)userNameSpace, (String)"userNameSpace");
        if (var1.size() == 0 && (var2 = ExtKt.asJsonArray((Object)"\n            [{\"groupId\":-1,\"groupName\":\"\u5168\u90e8\",\"order\":-10,\"show\":true},{\"groupId\":-2,\"groupName\":\"\u672c\u5730\",\"order\":-9,\"show\":true},{\"groupId\":-3,\"groupName\":\"\u97f3\u9891\",\"order\":-8,\"show\":true},{\"groupId\":-4,\"groupName\":\"\u672a\u5206\u7ec4\",\"order\":-7,\"show\":true},{\"groupId\":-5,\"groupName\":\"\u66f4\u65b0\u9519\u8bef\",\"order\":-6,\"show\":true}]\n            ")) != null) {
            this.saveUserStorage((Object)userNameSpace, "bookGroup", (Object)var2);
            return var2;
        }
        return var1;
    }

    public boolean checker(@NotNull JsonObject var1, @NotNull BookGroup var2) {
        Intrinsics.checkNotNullParameter((Object)var1, (String)"var1");
        Intrinsics.checkNotNullParameter((Object)var2, (String)"var2");
        return ((Object)var2.getGroupId()).equals(var1.getLong("groupId"));
    }

    /*
     * WARNING - void declaration
     */
    public void onCheckEnd(@NotNull BookGroup var1, boolean var2, @NotNull JsonArray bookGroupList) {
        Intrinsics.checkNotNullParameter((Object)var1, (String)"var1");
        Intrinsics.checkNotNullParameter((Object)bookGroupList, (String)"bookGroupList");
        if (!var2) {
            int maxOrder = 0;
            Iterable iterable = (Iterable)bookGroupList;
            boolean bl = false;
            long l = 0L;
            Iterator iterator = iterable.iterator();
            while (iterator.hasNext()) {
                Integer n;
                Object object;
                void it;
                Object t;
                Object t2 = t = iterator.next();
                long l2 = l;
                boolean bl2 = false;
                JsonObject jsonObject = ExtKt.asJsonObject((Object)it);
                long id = jsonObject == null ? 0L : ((object = jsonObject.getLong("groupId", Long.valueOf(0L))) == null ? 0L : (Long)object);
                object = ExtKt.asJsonObject((Object)it);
                int order = object == null ? 0 : ((n = object.getInteger("order", Integer.valueOf(0))) == null ? 0 : n);
                maxOrder = order > maxOrder ? order : maxOrder;
                long l3 = id > 0L ? id : 0L;
                l = l2 + l3;
            }
            long idsSum = l;
            long id = 1L;
            while ((id & idsSum) != 0L) {
                id <<= 1;
            }
            var1.setGroupId(id);
            var1.setOrder(maxOrder + 1);
        }
    }

    @Nullable
    public ReturnData beforeSave(@NotNull BookGroup var1, @NotNull DB<BookGroup> db) {
        Intrinsics.checkNotNullParameter((Object)var1, (String)"var1");
        Intrinsics.checkNotNullParameter(db, (String)"db");
        ReturnData returnData = new ReturnData();
        CharSequence charSequence = var1.getGroupName();
        boolean bl = false;
        if (charSequence.length() == 0) {
            return returnData.setErrorMsg("\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return null;
    }

    @Nullable
    public Object checkUserAuth(@NotNull RoutingContext context, @NotNull Continuation<? super Boolean> $completion) {
        return this.checkAuth(context, $completion);
    }

    @NotNull
    public String getUserNS(@NotNull RoutingContext context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        return this.getUserNameSpace(context);
    }

    @NotNull
    public Class<BookGroup> getEntityClass() {
        return BookGroup.class;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object saveBookGroupOrder(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveBookGroupOrder.1)) ** GOTO lbl-1000
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
                this = (BookGroupController)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default((ReturnData)returnData, (Object)"NEED_LOGIN", null, (int)2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                bookGroupOrder = context.getBodyAsJson().getJsonArray("order", null);
                if (bookGroupOrder == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                userNameSpace = this.getUserNameSpace(context);
                bookGroupList = ExtKt.asJsonArray((Object)this.getUserStorage((Object)userNameSpace, var7_9 = new String[]{"bookGroup"}));
                if (bookGroupList == null) {
                    bookGroupList = new JsonArray();
                }
                var8_11 = 0;
                orderMap = new LinkedHashMap<K, V>();
                var8_11 = 0;
                var9_13 = bookGroupOrder.size();
                if (var8_11 < var9_13) {
                    do {
                        i = var8_11++;
                        var11_15 /* !! */  = bookGroupOrder.getJsonObject(i).getLong("groupId");
                        Intrinsics.checkNotNullExpressionValue((Object)var11_15 /* !! */ , (String)"bookGroupOrder.getJsonObject(i).getLong(\"groupId\")");
                        v1 = var11_15 /* !! */ ;
                        var11_15 /* !! */  = bookGroupOrder.getJsonObject(i).getInteger("order");
                        Intrinsics.checkNotNullExpressionValue((Object)var11_15 /* !! */ , (String)"bookGroupOrder.getJsonObject(i).getInteger(\"order\")");
                        orderMap.put(v1, var11_15 /* !! */ );
                    } while (var8_11 < var9_13);
                }
                groupList = bookGroupList.getList();
                var9_13 = 0;
                var10_14 = bookGroupList.size();
                if (var9_13 < var10_14) {
                    do {
                        if (!orderMap.containsKey(Boxing.boxLong((long)(bookGroup = (BookGroup)bookGroupList.getJsonObject(i = var9_13++).mapTo(BookGroup.class)).getGroupId()))) continue;
                        var14_20 = orderMap.get(Boxing.boxLong((long)bookGroup.getGroupId()));
                        var13_19 = var14_20 instanceof Integer != false ? (Integer)var14_20 : null;
                        bookGroup.setOrder(var13_19 == null ? bookGroup.getOrder() : var13_19.intValue());
                        groupList.set(i, JsonObject.mapFrom((Object)bookGroup));
                    } while (var9_13 < var10_14);
                }
                bookGroupList = new JsonArray(groupList);
                this.saveUserStorage((Object)userNameSpace, "bookGroup", (Object)bookGroupList);
                return ReturnData.setData$default((ReturnData)returnData, (Object)"", null, (int)2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public ReturnData beforeAdd(@NotNull BookGroup val1, @NotNull DB<BookGroup> db) {
        return CURD.DefaultImpls.beforeAdd((CURD)this, (Object)val1, db);
    }

    @Nullable
    public ReturnData beforeDelete(@NotNull BookGroup val1, @NotNull DB<BookGroup> db) {
        return CURD.DefaultImpls.beforeDelete((CURD)this, (Object)val1, db);
    }

    @NotNull
    public BookGroup convertToEntity(@NotNull JsonObject var1) {
        return (BookGroup)CURD.DefaultImpls.convertToEntity((CURD)this, (JsonObject)var1);
    }

    @NotNull
    public BookGroup[] convertToEntityList(@NotNull String var1) {
        return (BookGroup[])CURD.DefaultImpls.convertToEntityList((CURD)this, (String)var1);
    }

    @Nullable
    public Object delete(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        return CURD.DefaultImpls.delete((CURD)this, (RoutingContext)context, $completion);
    }

    @Nullable
    public Object deleteMulti(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        return CURD.DefaultImpls.deleteMulti((CURD)this, (RoutingContext)context, $completion);
    }

    @Nullable
    public Object list(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        return CURD.DefaultImpls.list((CURD)this, (RoutingContext)context, $completion);
    }

    @Nullable
    public Object save(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        return CURD.DefaultImpls.save((CURD)this, (RoutingContext)context, $completion);
    }

    @Nullable
    public Object saveMulti(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        return CURD.DefaultImpls.saveMulti((CURD)this, (RoutingContext)context, $completion);
    }
}

