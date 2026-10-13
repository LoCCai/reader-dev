/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.help.http.CoroutinesCallAdapterFactory
 *  io.legado.app.help.http.CoroutinesCallAdapterFactory$BodyCallAdapter
 *  io.legado.app.help.http.CoroutinesCallAdapterFactory$Companion
 *  io.legado.app.help.http.CoroutinesCallAdapterFactory$ResponseCallAdapter
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlinx.coroutines.Deferred
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  retrofit2.CallAdapter
 *  retrofit2.CallAdapter$Factory
 *  retrofit2.Response
 *  retrofit2.Retrofit
 */
package io.legado.app.help.http;

import io.legado.app.help.http.CoroutinesCallAdapterFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Deferred;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u000e2\u00020\u0001:\u0003\r\u000e\u000fB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J8\u0010\u0003\u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0096\u0002\u00a2\u0006\u0002\u0010\f\u00a8\u0006\u0010"}, d2={"Lio/legado/app/help/http/CoroutinesCallAdapterFactory;", "Lretrofit2/CallAdapter$Factory;", "()V", "get", "Lretrofit2/CallAdapter;", "returnType", "Ljava/lang/reflect/Type;", "annotations", "", "", "retrofit", "Lretrofit2/Retrofit;", "(Ljava/lang/reflect/Type;[Ljava/lang/annotation/Annotation;Lretrofit2/Retrofit;)Lretrofit2/CallAdapter;", "BodyCallAdapter", "Companion", "ResponseCallAdapter", "reader-pro"})
public final class CoroutinesCallAdapterFactory
extends CallAdapter.Factory {
    @NotNull
    public static final Companion Companion = new Companion(null);

    private CoroutinesCallAdapterFactory() {
    }

    @Nullable
    public CallAdapter<?, ?> get(@NotNull Type returnType, @NotNull Annotation[] annotations, @NotNull Retrofit retrofit) {
        CallAdapter callAdapter;
        Intrinsics.checkNotNullParameter((Object)returnType, (String)"returnType");
        Intrinsics.checkNotNullParameter((Object)annotations, (String)"annotations");
        Intrinsics.checkNotNullParameter((Object)retrofit, (String)"retrofit");
        if (!Intrinsics.areEqual(Deferred.class, (Object)CallAdapter.Factory.getRawType((Type)returnType))) {
            return null;
        }
        boolean bl = returnType instanceof ParameterizedType;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            boolean bl4 = false;
            String string = "Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>";
            throw (Throwable)new IllegalStateException(string.toString());
        }
        Type responseType = CallAdapter.Factory.getParameterUpperBound((int)0, (ParameterizedType)((ParameterizedType)returnType));
        Class rawDeferredType = CallAdapter.Factory.getRawType((Type)responseType);
        if (Intrinsics.areEqual((Object)rawDeferredType, Response.class)) {
            bl3 = responseType instanceof ParameterizedType;
            boolean bl5 = false;
            boolean bl6 = false;
            if (!bl3) {
                boolean bl7 = false;
                String string = "Response must be parameterized as Response<Foo> or Response<out Foo>";
                throw (Throwable)new IllegalStateException(string.toString());
            }
            Type type = CallAdapter.Factory.getParameterUpperBound((int)0, (ParameterizedType)((ParameterizedType)responseType));
            Intrinsics.checkNotNullExpressionValue((Object)type, (String)"getParameterUpperBound(\n                    0,\n                    responseType\n                )");
            callAdapter = (CallAdapter)new ResponseCallAdapter(type);
        } else {
            Intrinsics.checkNotNullExpressionValue((Object)responseType, (String)"responseType");
            callAdapter = (CallAdapter)new BodyCallAdapter(responseType);
        }
        return callAdapter;
    }

    public /* synthetic */ CoroutinesCallAdapterFactory(DefaultConstructorMarker $constructor_marker) {
        this();
    }
}

