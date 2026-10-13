/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.api.ReturnData
 *  com.htmake.reader.api.controller.CURD
 *  com.htmake.reader.db.DB
 *  io.vertx.core.json.JsonArray
 *  io.vertx.core.json.JsonObject
 *  io.vertx.ext.web.RoutingContext
 *  kotlin.Metadata
 *  kotlin.coroutines.Continuation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.db.DB;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J%\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00028\u00002\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0016\u00a2\u0006\u0002\u0010\bJ%\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00028\u00002\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0016\u00a2\u0006\u0002\u0010\bJ%\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00028\u00002\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0016\u00a2\u0006\u0002\u0010\bJ\u0019\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u001d\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00028\u0000H&\u00a2\u0006\u0002\u0010\u0014J\u0015\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0011\u001a\u00020\u0012H\u0016\u00a2\u0006\u0002\u0010\u0016J\u001b\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00182\u0006\u0010\u0011\u001a\u00020\u0019H\u0016\u00a2\u0006\u0002\u0010\u001aJ\u0019\u0010\u001b\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u0019\u0010\u001c\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH&J\b\u0010\u001f\u001a\u00020\u0019H&J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u000eH&J\u0019\u0010!\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ%\u0010\"\u001a\u00020#2\u0006\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010$\u001a\u00020%H\u0016\u00a2\u0006\u0002\u0010&J\u0018\u0010'\u001a\u00020%2\u0006\u0010\u0011\u001a\u00020%2\u0006\u0010(\u001a\u00020\u0019H\u0016J\u0019\u0010)\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u0019\u0010*\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006+"}, d2={"Lcom/htmake/reader/api/controller/CURD;", "T", "", "beforeAdd", "Lcom/htmake/reader/api/ReturnData;", "val1", "db", "Lcom/htmake/reader/db/DB;", "(Ljava/lang/Object;Lcom/htmake/reader/db/DB;)Lcom/htmake/reader/api/ReturnData;", "beforeDelete", "beforeSave", "checkUserAuth", "", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checker", "var1", "Lio/vertx/core/json/JsonObject;", "var2", "(Lio/vertx/core/json/JsonObject;Ljava/lang/Object;)Z", "convertToEntity", "(Lio/vertx/core/json/JsonObject;)Ljava/lang/Object;", "convertToEntityList", "", "", "(Ljava/lang/String;)[Ljava/lang/Object;", "delete", "deleteMulti", "getEntityClass", "Ljava/lang/Class;", "getTableName", "getUserNS", "list", "onCheckEnd", "", "var3", "Lio/vertx/core/json/JsonArray;", "(Ljava/lang/Object;ZLio/vertx/core/json/JsonArray;)V", "onList", "userNameSpace", "save", "saveMulti", "reader-pro"})
public interface CURD<T> {
    @NotNull
    public String getTableName();

    public T convertToEntity(@NotNull JsonObject var1);

    @NotNull
    public T[] convertToEntityList(@NotNull String var1);

    @NotNull
    public JsonArray onList(@NotNull JsonArray var1, @NotNull String var2);

    public boolean checker(@NotNull JsonObject var1, T var2);

    public void onCheckEnd(T var1, boolean var2, @NotNull JsonArray var3);

    @Nullable
    public ReturnData beforeSave(T var1, @NotNull DB<T> var2);

    @Nullable
    public ReturnData beforeAdd(T var1, @NotNull DB<T> var2);

    @Nullable
    public ReturnData beforeDelete(T var1, @NotNull DB<T> var2);

    @Nullable
    public Object checkUserAuth(@NotNull RoutingContext var1, @NotNull Continuation<? super Boolean> var2);

    @NotNull
    public String getUserNS(@NotNull RoutingContext var1);

    @NotNull
    public Class<T> getEntityClass();

    @Nullable
    public Object list(@NotNull RoutingContext var1, @NotNull Continuation<? super ReturnData> var2);

    @Nullable
    public Object save(@NotNull RoutingContext var1, @NotNull Continuation<? super ReturnData> var2);

    @Nullable
    public Object saveMulti(@NotNull RoutingContext var1, @NotNull Continuation<? super ReturnData> var2);

    @Nullable
    public Object delete(@NotNull RoutingContext var1, @NotNull Continuation<? super ReturnData> var2);

    @Nullable
    public Object deleteMulti(@NotNull RoutingContext var1, @NotNull Continuation<? super ReturnData> var2);
}

