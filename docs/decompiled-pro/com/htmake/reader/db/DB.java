/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.db.DB
 *  com.htmake.reader.db.DB$Companion
 *  io.vertx.core.json.JsonArray
 *  io.vertx.core.json.JsonObject
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.db;

import com.htmake.reader.db.DB;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\b\u0016\u0018\u0000 &*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001&B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013H\u0016J7\u0010\u0016\u001a\u0004\u0018\u00018\u0000\"\b\b\u0001\u0010\u0017*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u0002H\u00172\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u001bH\u0016\u00a2\u0006\u0002\u0010\u001cJ\b\u0010\u001d\u001a\u00020\bH\u0016J\b\u0010\u001e\u001a\u00020\u0011H\u0016JQ\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00028\u00002 \u0010\u001f\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0011\u0018\u00010 2\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00150!H\u0016\u00a2\u0006\u0002\u0010\"JW\u0010#\u001a\u00020\u00112\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000$2 \u0010\u001f\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0011\u0018\u00010 2\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00150!H\u0016\u00a2\u0006\u0002\u0010%R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e\u00a8\u0006'"}, d2={"Lcom/htmake/reader/db/DB;", "T", "", "userNameSpace", "", "name", "(Ljava/lang/String;Ljava/lang/String;)V", "cachedValue", "Lio/vertx/core/json/JsonArray;", "getCachedValue", "()Lio/vertx/core/json/JsonArray;", "setCachedValue", "(Lio/vertx/core/json/JsonArray;)V", "getName", "()Ljava/lang/String;", "getUserNameSpace", "delete", "", "checker", "Lkotlin/Function1;", "Lio/vertx/core/json/JsonObject;", "", "findBy", "P", "field", "value", "clazz", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;", "readAll", "save", "onCheckEnd", "Lkotlin/Function3;", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function2;)V", "saveMulti", "", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function2;)V", "Companion", "reader-pro"})
public class DB<T> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String userNameSpace;
    @NotNull
    private final String name;
    @NotNull
    private JsonArray cachedValue;

    public DB(@NotNull String userNameSpace, @NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)userNameSpace, (String)"userNameSpace");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        this.userNameSpace = userNameSpace;
        this.name = name;
        this.cachedValue = new JsonArray();
    }

    @NotNull
    public final String getUserNameSpace() {
        return this.userNameSpace;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final JsonArray getCachedValue() {
        return this.cachedValue;
    }

    public final void setCachedValue(@NotNull JsonArray jsonArray) {
        Intrinsics.checkNotNullParameter((Object)jsonArray, (String)"<set-?>");
        this.cachedValue = jsonArray;
    }

    @NotNull
    public JsonArray readAll() {
        return new JsonArray();
    }

    @Nullable
    public <P> T findBy(@NotNull String field, @NotNull P value, @NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter((Object)field, (String)"field");
        Intrinsics.checkNotNullParameter(value, (String)"value");
        Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
        return null;
    }

    public void save(T value, @Nullable Function3<? super T, ? super Boolean, ? super JsonArray, Unit> onCheckEnd, @NotNull Function2<? super JsonObject, ? super T, Boolean> checker) {
        Intrinsics.checkNotNullParameter(checker, (String)"checker");
    }

    public void saveMulti(@NotNull T[] value, @Nullable Function3<? super T, ? super Boolean, ? super JsonArray, Unit> onCheckEnd, @NotNull Function2<? super JsonObject, ? super T, Boolean> checker) {
        Intrinsics.checkNotNullParameter(value, (String)"value");
        Intrinsics.checkNotNullParameter(checker, (String)"checker");
    }

    public void delete(@NotNull Function1<? super JsonObject, Boolean> checker) {
        Intrinsics.checkNotNullParameter(checker, (String)"checker");
    }

    public void save() {
    }
}

