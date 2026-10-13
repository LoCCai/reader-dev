/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.help.http.EncodeConverter
 *  io.legado.app.utils.EncodingDetect
 *  io.legado.app.utils.UTF8BOMFighter
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  okhttp3.MediaType
 *  okhttp3.ResponseBody
 *  org.jetbrains.annotations.Nullable
 *  retrofit2.Converter
 *  retrofit2.Converter$Factory
 *  retrofit2.Retrofit
 */
package io.legado.app.help.http;

import io.legado.app.utils.EncodingDetect;
import io.legado.app.utils.UTF8BOMFighter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;
import retrofit2.Converter;
import retrofit2.Retrofit;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J?\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00062\b\u0010\b\u001a\u0004\u0018\u00010\t2\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016\u00a2\u0006\u0002\u0010\u000fR\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lio/legado/app/help/http/EncodeConverter;", "Lretrofit2/Converter$Factory;", "encode", "", "(Ljava/lang/String;)V", "responseBodyConverter", "Lretrofit2/Converter;", "Lokhttp3/ResponseBody;", "type", "Ljava/lang/reflect/Type;", "annotations", "", "", "retrofit", "Lretrofit2/Retrofit;", "(Ljava/lang/reflect/Type;[Ljava/lang/annotation/Annotation;Lretrofit2/Retrofit;)Lretrofit2/Converter;", "reader-pro"})
public final class EncodeConverter
extends Converter.Factory {
    @Nullable
    private final String encode;

    public EncodeConverter(@Nullable String encode) {
        this.encode = encode;
    }

    public /* synthetic */ EncodeConverter(String string, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = null;
        }
        this(string);
    }

    @Nullable
    public Converter<ResponseBody, String> responseBodyConverter(@Nullable Type type, @Nullable Annotation[] annotations, @Nullable Retrofit retrofit) {
        return arg_0 -> EncodeConverter.responseBodyConverter$lambda-1(this, arg_0);
    }

    private static final String responseBodyConverter$lambda-1(EncodeConverter this$0, ResponseBody value) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        byte[] responseBytes = UTF8BOMFighter.INSTANCE.removeUTF8BOM(value.bytes());
        String string = this$0.encode;
        if (string != null) {
            String string2 = string;
            boolean bl = false;
            boolean bl2 = false;
            String it = string2;
            boolean bl3 = false;
            Charset charset = Charset.forName(this$0.encode);
            Intrinsics.checkNotNullExpressionValue((Object)charset, (String)"forName(encode)");
            boolean bl4 = false;
            return new String(responseBytes, charset);
        }
        String charsetName = null;
        MediaType mediaType = value.contentType();
        if (mediaType != null) {
            Charset charset = MediaType.charset$default((MediaType)mediaType, null, (int)1, null);
            Charset charset2 = charset;
            String string3 = charsetName = charset2 == null ? null : charset2.displayName();
        }
        if (charsetName == null) {
            charsetName = EncodingDetect.INSTANCE.getHtmlEncode(responseBytes);
        }
        Charset charset = Charset.forName(charsetName);
        Intrinsics.checkNotNullExpressionValue((Object)charset, (String)"forName(charsetName)");
        boolean bl = false;
        return new String(responseBytes, charset);
    }

    public EncodeConverter() {
        this(null, 1, null);
    }
}

