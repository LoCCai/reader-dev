/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.help.http.ByteConverter
 *  kotlin.Metadata
 *  okhttp3.ResponseBody
 *  org.jetbrains.annotations.Nullable
 *  retrofit2.Converter
 *  retrofit2.Converter$Factory
 *  retrofit2.Retrofit
 */
package io.legado.app.help.http;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import kotlin.Metadata;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;
import retrofit2.Converter;
import retrofit2.Retrofit;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J?\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u000f"}, d2={"Lio/legado/app/help/http/ByteConverter;", "Lretrofit2/Converter$Factory;", "()V", "responseBodyConverter", "Lretrofit2/Converter;", "Lokhttp3/ResponseBody;", "", "type", "Ljava/lang/reflect/Type;", "annotations", "", "", "retrofit", "Lretrofit2/Retrofit;", "(Ljava/lang/reflect/Type;[Ljava/lang/annotation/Annotation;Lretrofit2/Retrofit;)Lretrofit2/Converter;", "reader-pro"})
public final class ByteConverter
extends Converter.Factory {
    @Nullable
    public Converter<ResponseBody, byte[]> responseBodyConverter(@Nullable Type type, @Nullable Annotation[] annotations, @Nullable Retrofit retrofit) {
        return ByteConverter::responseBodyConverter$lambda-0;
    }

    private static final byte[] responseBodyConverter$lambda-0(ResponseBody value) {
        return value.bytes();
    }
}

