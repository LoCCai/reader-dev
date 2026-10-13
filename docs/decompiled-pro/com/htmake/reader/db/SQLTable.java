/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.db.DB
 *  com.htmake.reader.db.SQLTable
 *  com.htmake.reader.utils.ExtKt
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
import com.htmake.reader.utils.ExtKt;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nH\u0016J7\u0010\r\u001a\u0004\u0018\u00018\u0000\"\b\b\u0001\u0010\u000e*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u0002H\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013H\u0016\u00a2\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\bH\u0016JQ\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00028\u00002 \u0010\u0018\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\b\u0018\u00010\u00192\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u001aH\u0016\u00a2\u0006\u0002\u0010\u001bJW\u0010\u001c\u001a\u00020\b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u001d2 \u0010\u0018\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\b\u0018\u00010\u00192\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u001aH\u0016\u00a2\u0006\u0002\u0010\u001e\u00a8\u0006\u001f"}, d2={"Lcom/htmake/reader/db/SQLTable;", "T", "Lcom/htmake/reader/db/DB;", "userNameSpace", "", "name", "(Ljava/lang/String;Ljava/lang/String;)V", "delete", "", "checker", "Lkotlin/Function1;", "Lio/vertx/core/json/JsonObject;", "", "findBy", "P", "", "field", "value", "clazz", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;", "readAll", "Lio/vertx/core/json/JsonArray;", "save", "onCheckEnd", "Lkotlin/Function3;", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function2;)V", "saveMulti", "", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function2;)V", "reader-pro"})
public final class SQLTable<T>
extends DB<T> {
    public SQLTable(@NotNull String userNameSpace, @NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)userNameSpace, (String)"userNameSpace");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        super(userNameSpace, name);
    }

    @NotNull
    public JsonArray readAll() {
        String[] stringArray = new String[]{"data", this.getUserNameSpace(), this.getName()};
        JsonArray dataList = ExtKt.asJsonArray((Object)ExtKt.getStorage$default((String[])stringArray, null, (int)2, null));
        if (dataList == null) {
            dataList = new JsonArray();
        }
        this.setCachedValue(dataList);
        return dataList;
    }

    @Nullable
    public <P> T findBy(@NotNull String field, @NotNull P value, @NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter((Object)field, (String)"field");
        Intrinsics.checkNotNullParameter(value, (String)"value");
        Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
        JsonArray dataList = this.readAll();
        int n = 0;
        int n2 = dataList.size();
        if (n < n2) {
            do {
                int i;
                Object objValue;
                if (!value.equals(objValue = dataList.getJsonObject(i = n++).getValue(field))) continue;
                return (T)dataList.getJsonObject(i).mapTo(clazz);
            } while (n < n2);
        }
        return null;
    }

    public void save(T value, @Nullable Function3<? super T, ? super Boolean, ? super JsonArray, Unit> onCheckEnd, @NotNull Function2<? super JsonObject, ? super T, Boolean> checker) {
        Intrinsics.checkNotNullParameter(checker, (String)"checker");
        JsonArray dataList = this.readAll();
        int existIndex = -1;
        int n = 0;
        int n2 = dataList.size();
        if (n < n2) {
            do {
                int i = n++;
                JsonObject jsonObject = dataList.getJsonObject(i);
                Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"dataList.getJsonObject(i)");
                if (!((Boolean)checker.invoke((Object)jsonObject, value)).booleanValue()) continue;
                existIndex = i;
                break;
            } while (n < n2);
        }
        if (onCheckEnd != null) {
            onCheckEnd.invoke(value, (Object)(existIndex >= 0 ? 1 : 0), (Object)dataList);
        }
        if (existIndex >= 0) {
            List list2 = dataList.getList();
            list2.set(existIndex, JsonObject.mapFrom(value));
            dataList = new JsonArray(list2);
        } else {
            dataList.add(JsonObject.mapFrom(value));
        }
        this.setCachedValue(dataList);
        this.save();
    }

    public void saveMulti(@NotNull T[] value, @Nullable Function3<? super T, ? super Boolean, ? super JsonArray, Unit> onCheckEnd, @NotNull Function2<? super JsonObject, ? super T, Boolean> checker) {
        Intrinsics.checkNotNullParameter(value, (String)"value");
        Intrinsics.checkNotNullParameter(checker, (String)"checker");
        JsonArray dataList = this.readAll();
        int existIndex = -1;
        T[] TArray = value;
        int n = 0;
        int n2 = TArray.length;
        while (n < n2) {
            T j = TArray[n];
            ++n;
            int n3 = 0;
            int n4 = dataList.size();
            if (n3 < n4) {
                do {
                    int i = n3++;
                    JsonObject jsonObject = dataList.getJsonObject(i);
                    Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"dataList.getJsonObject(i)");
                    if (!((Boolean)checker.invoke((Object)jsonObject, j)).booleanValue()) continue;
                    existIndex = i;
                    break;
                } while (n3 < n4);
            }
            if (onCheckEnd != null) {
                onCheckEnd.invoke(j, (Object)(existIndex >= 0 ? 1 : 0), (Object)dataList);
            }
            if (existIndex >= 0) {
                List list2 = dataList.getList();
                list2.set(existIndex, JsonObject.mapFrom(j));
                dataList = new JsonArray(list2);
                continue;
            }
            dataList.add(JsonObject.mapFrom(j));
        }
        this.setCachedValue(dataList);
        this.save();
    }

    public void delete(@NotNull Function1<? super JsonObject, Boolean> checker) {
        Intrinsics.checkNotNullParameter(checker, (String)"checker");
        JsonArray dataList = null;
        dataList = this.readAll();
        int n = 0;
        List removeIndexList = new ArrayList();
        n = 0;
        int n2 = dataList.size();
        if (n < n2) {
            do {
                int i = n++;
                JsonObject jsonObject = dataList.getJsonObject(i);
                Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"dataList.getJsonObject(i)");
                if (!((Boolean)checker.invoke((Object)jsonObject)).booleanValue()) continue;
                removeIndexList.add(i);
            } while (n < n2);
        }
        if (removeIndexList.size() > 0) {
            Iterable $this$forEach$iv = removeIndexList;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                int it = ((Number)element$iv).intValue();
                boolean bl = false;
                dataList.remove(it);
            }
        }
        this.setCachedValue(dataList);
        this.save();
    }

    public void save() {
        String[] stringArray = new String[]{"data", this.getUserNameSpace(), this.getName()};
        ExtKt.saveStorage$default((String[])stringArray, (Object)this.getCachedValue(), (boolean)false, null, (int)12, null);
    }
}

