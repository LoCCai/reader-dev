/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.internal.LinkedTreeMap
 *  io.legado.app.utils.MapDeserializerDoubleAsIntFix
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0005J0\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000e\u001a\u00020\b\u00a8\u0006\u000f"}, d2={"Lio/legado/app/utils/MapDeserializerDoubleAsIntFix;", "Lcom/google/gson/JsonDeserializer;", "", "", "", "()V", "deserialize", "jsonElement", "Lcom/google/gson/JsonElement;", "type", "Ljava/lang/reflect/Type;", "jsonDeserializationContext", "Lcom/google/gson/JsonDeserializationContext;", "read", "json", "reader-pro"})
public final class MapDeserializerDoubleAsIntFix
implements JsonDeserializer<Map<String, ? extends Object>> {
    @Nullable
    public Map<String, Object> deserialize(@NotNull JsonElement jsonElement, @NotNull Type type, @NotNull JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Intrinsics.checkNotNullParameter((Object)jsonElement, (String)"jsonElement");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)jsonDeserializationContext, (String)"jsonDeserializationContext");
        Object object = this.read(jsonElement);
        return object instanceof Map ? (Map)object : null;
    }

    @Nullable
    public final Object read(@NotNull JsonElement json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        if (json.isJsonArray()) {
            List list2 = new ArrayList();
            JsonArray arr = json.getAsJsonArray();
            for (JsonElement anArr : arr) {
                Intrinsics.checkNotNullExpressionValue((Object)anArr, (String)"anArr");
                list2.add(this.read(anArr));
            }
            return list2;
        }
        if (json.isJsonObject()) {
            Map map = (Map)new LinkedTreeMap();
            JsonObject obj = json.getAsJsonObject();
            Set entitySet = obj.entrySet();
            for (Map.Entry entry : entitySet) {
                Intrinsics.checkNotNullExpressionValue((Object)entry, (String)"entitySet");
                Map.Entry entry2 = entry;
                boolean bl = false;
                String key = (String)entry2.getKey();
                Object object = entry;
                boolean bl2 = false;
                JsonElement value = (JsonElement)object.getValue();
                object = map;
                Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
                String string = key;
                Intrinsics.checkNotNullExpressionValue((Object)value, (String)"value");
                Object object2 = this.read(value);
                boolean bl3 = false;
                object.put(string, object2);
            }
            return map;
        }
        if (json.isJsonPrimitive()) {
            JsonPrimitive prim = json.getAsJsonPrimitive();
            if (prim.isBoolean()) {
                return prim.getAsBoolean();
            }
            if (prim.isString()) {
                return prim.getAsString();
            }
            if (prim.isNumber()) {
                Number number = prim.getAsNumber();
                Intrinsics.checkNotNullExpressionValue((Object)number, (String)"prim.asNumber");
                Number num = number;
                double d = num.doubleValue();
                boolean bl = false;
                return Math.ceil(d) == (double)num.longValue() ? (Number)num.longValue() : (Number)num.doubleValue();
            }
        }
        return null;
    }
}

