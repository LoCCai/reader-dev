/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.stream.JsonWriter
 *  io.legado.app.utils.GsonExtensionsKt
 *  io.legado.app.utils.GsonExtensionsKt$GSON$2
 *  io.legado.app.utils.GsonExtensionsKt$fromJsonObject$lambda-0$$inlined$genericType$1
 *  io.legado.app.utils.GsonExtensionsKt$fromJsonObject$lambda-2$$inlined$genericType$1
 *  io.legado.app.utils.ParameterizedTypeImpl
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.Result
 *  kotlin.Result$Companion
 *  kotlin.ResultKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.GsonExtensionsKt$fromJsonObject$lambda-0$;
import io.legado.app.utils.GsonExtensionsKt$fromJsonObject$lambda-2$;
import io.legado.app.utils.ParameterizedTypeImpl;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\u001a\u0011\u0010\u0006\u001a\u00020\u0007\"\u0006\b\u0000\u0010\b\u0018\u0001H\u0086\b\u001a5\u0010\t\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\b\u0018\u00010\u000b0\n\"\u0006\b\u0000\u0010\b\u0018\u0001*\u00020\u00012\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000e\u001a5\u0010\t\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\b\u0018\u00010\u000b0\n\"\u0006\b\u0000\u0010\b\u0018\u0001*\u00020\u00012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011\u001a/\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\b0\n\"\u0006\b\u0000\u0010\b\u0018\u0001*\u00020\u00012\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000e\u001a/\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\b0\n\"\u0006\b\u0000\u0010\b\u0018\u0001*\u00020\u00012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011\u001a\u001a\u0010\u0013\u001a\u00020\u0014*\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018\"\u001b\u0010\u0000\u001a\u00020\u00018FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2={"GSON", "Lcom/google/gson/Gson;", "getGSON", "()Lcom/google/gson/Gson;", "GSON$delegate", "Lkotlin/Lazy;", "genericType", "Ljava/lang/reflect/Type;", "T", "fromJsonArray", "Lkotlin/Result;", "", "inputStream", "Ljava/io/InputStream;", "(Lcom/google/gson/Gson;Ljava/io/InputStream;)Ljava/lang/Object;", "json", "", "(Lcom/google/gson/Gson;Ljava/lang/String;)Ljava/lang/Object;", "fromJsonObject", "writeToOutputStream", "", "out", "Ljava/io/OutputStream;", "any", "", "reader-pro"})
public final class GsonExtensionsKt {
    @NotNull
    private static final Lazy GSON$delegate = LazyKt.lazy((Function0)((Function0)GSON.2.INSTANCE));

    @NotNull
    public static final Gson getGSON() {
        Lazy lazy = GSON$delegate;
        Object var2_1 = null;
        boolean bl = false;
        Object object = lazy.getValue();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"<get-GSON>(...)");
        return (Gson)object;
    }

    public static final /* synthetic */ <T> Type genericType() {
        boolean $i$f$genericType = false;
        Intrinsics.needClassReification();
        Type type = new /* Unavailable Anonymous Inner Class!! */.getType();
        Intrinsics.checkNotNullExpressionValue((Object)type, (String)"object : TypeToken<T>() {}.type");
        return type;
    }

    public static final /* synthetic */ <T> Object fromJsonObject(Gson $this$fromJsonObject, String json) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)$this$fromJsonObject, (String)"<this>");
        boolean $i$f$fromJsonObject = false;
        boolean bl = false;
        try {
            object = Result.Companion;
            boolean bl2 = false;
            boolean $i$f$genericType = false;
            Intrinsics.needClassReification();
            Type type = new fromJsonObject$lambda-0$$inlined$genericType$1().getType();
            Intrinsics.checkNotNullExpressionValue((Object)type, (String)"object : TypeToken<T>() {}.type");
            Object object2 = $this$fromJsonObject.fromJson(json, type);
            Intrinsics.reifiedOperationMarker((int)2, (String)"T");
            Object object3 = object2;
            boolean bl3 = false;
            object = Result.constructor-impl((Object)object3);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl4 = false;
            object = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
        }
        return object;
    }

    public static final /* synthetic */ <T> Object fromJsonArray(Gson $this$fromJsonArray, String json) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)$this$fromJsonArray, (String)"<this>");
        boolean $i$f$fromJsonArray = false;
        boolean bl = false;
        try {
            object = Result.Companion;
            boolean bl2 = false;
            Intrinsics.reifiedOperationMarker((int)4, (String)"T");
            Object object2 = $this$fromJsonArray.fromJson(json, (Type)new ParameterizedTypeImpl(Object.class));
            List list2 = object2 instanceof List ? (List)object2 : null;
            boolean bl3 = false;
            object = Result.constructor-impl((Object)list2);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl4 = false;
            object = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
        }
        return object;
    }

    public static final /* synthetic */ <T> Object fromJsonObject(Gson $this$fromJsonObject, InputStream inputStream) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)$this$fromJsonObject, (String)"<this>");
        boolean $i$f$fromJsonObject = false;
        boolean bl = false;
        try {
            object = Result.Companion;
            boolean bl2 = false;
            InputStreamReader reader = new InputStreamReader(inputStream);
            Reader reader2 = reader;
            boolean $i$f$genericType = false;
            Intrinsics.needClassReification();
            Type type = new fromJsonObject$lambda-2$$inlined$genericType$1().getType();
            Intrinsics.checkNotNullExpressionValue((Object)type, (String)"object : TypeToken<T>() {}.type");
            Object object2 = $this$fromJsonObject.fromJson(reader2, type);
            Intrinsics.reifiedOperationMarker((int)2, (String)"T");
            Object object3 = object2;
            boolean bl3 = false;
            object = Result.constructor-impl((Object)object3);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl4 = false;
            object = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
        }
        return object;
    }

    public static final /* synthetic */ <T> Object fromJsonArray(Gson $this$fromJsonArray, InputStream inputStream) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)$this$fromJsonArray, (String)"<this>");
        boolean $i$f$fromJsonArray = false;
        boolean bl = false;
        try {
            object = Result.Companion;
            boolean bl2 = false;
            InputStreamReader reader = new InputStreamReader(inputStream);
            Reader reader2 = reader;
            Intrinsics.reifiedOperationMarker((int)4, (String)"T");
            Object object2 = $this$fromJsonArray.fromJson(reader2, (Type)new ParameterizedTypeImpl(Object.class));
            List list2 = object2 instanceof List ? (List)object2 : null;
            boolean bl3 = false;
            object = Result.constructor-impl((Object)list2);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl4 = false;
            object = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
        }
        return object;
    }

    public static final void writeToOutputStream(@NotNull Gson $this$writeToOutputStream, @NotNull OutputStream out, @NotNull Object any) {
        Intrinsics.checkNotNullParameter((Object)$this$writeToOutputStream, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)out, (String)"out");
        Intrinsics.checkNotNullParameter((Object)any, (String)"any");
        JsonWriter writer = new JsonWriter((Writer)new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        if (any instanceof List) {
            writer.beginArray();
            Iterable $this$forEach$iv = (Iterable)any;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv.iterator();
            while (iterator.hasNext()) {
                Object element$iv;
                Object it = element$iv = iterator.next();
                boolean bl = false;
                Object t = it;
                if (t == null) continue;
                Object t2 = t;
                boolean bl2 = false;
                boolean bl3 = false;
                Object it2 = t2;
                boolean bl4 = false;
                $this$writeToOutputStream.toJson(it2, (Type)it2.getClass(), writer);
            }
            writer.endArray();
        } else {
            $this$writeToOutputStream.toJson(any, (Type)any.getClass(), writer);
        }
        writer.close();
    }
}

