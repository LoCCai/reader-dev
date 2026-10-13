/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.ParameterizedTypeImpl
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ\n\u0010\t\u001a\u0004\u0018\u00010\u0007H\u0016J\b\u0010\n\u001a\u00020\u0007H\u0016R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lio/legado/app/utils/ParameterizedTypeImpl;", "Ljava/lang/reflect/ParameterizedType;", "clazz", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "getActualTypeArguments", "", "Ljava/lang/reflect/Type;", "()[Ljava/lang/reflect/Type;", "getOwnerType", "getRawType", "reader-pro"})
public final class ParameterizedTypeImpl
implements ParameterizedType {
    @NotNull
    private final Class<?> clazz;

    public ParameterizedTypeImpl(@NotNull Class<?> clazz) {
        Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
        this.clazz = clazz;
    }

    @Override
    @NotNull
    public Type getRawType() {
        return (Type)((Object)List.class);
    }

    @Override
    @Nullable
    public Type getOwnerType() {
        return null;
    }

    @Override
    @NotNull
    public Type[] getActualTypeArguments() {
        Type[] typeArray = new Type[]{this.clazz};
        return typeArray;
    }
}

