/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.utils.LRUCache
 *  com.htmake.reader.utils.UserMutex
 *  com.htmake.reader.utils.UserMutex$getLocker$1
 *  kotlin.Metadata
 *  kotlin.ResultKt
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlinx.coroutines.sync.Mutex
 *  kotlinx.coroutines.sync.MutexKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.utils;

import com.htmake.reader.utils.LRUCache;
import com.htmake.reader.utils.UserMutex;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0019\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0005H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eR\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000f"}, d2={"Lcom/htmake/reader/utils/UserMutex;", "", "()V", "lockerMap", "Lcom/htmake/reader/utils/LRUCache;", "", "Lkotlinx/coroutines/sync/Mutex;", "getLockerMap", "()Lcom/htmake/reader/utils/LRUCache;", "mutex", "getMutex", "()Lkotlinx/coroutines/sync/Mutex;", "getLocker", "lockKey", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reader-pro"})
public final class UserMutex {
    @NotNull
    public static final UserMutex INSTANCE = new UserMutex();
    @NotNull
    private static final Mutex mutex = MutexKt.Mutex$default((boolean)false, (int)1, null);
    @NotNull
    private static final LRUCache<String, Mutex> lockerMap = new LRUCache(10);

    private UserMutex() {
    }

    @NotNull
    public final Mutex getMutex() {
        return mutex;
    }

    @NotNull
    public final LRUCache<String, Mutex> getLockerMap() {
        return lockerMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Nullable
    public final Object getLocker(@NotNull String var1_1, @NotNull Continuation<? super Mutex> var2_2) {
        if (!(var2_2 instanceof getLocker.1)) ** GOTO lbl-1000
        var9_3 = var2_2;
        if ((var9_3.label & -2147483648) != 0) {
            var9_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new /* Unavailable Anonymous Inner Class!! */;
        }
        $result = $continuation.result;
        var10_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                $this$withLock_u24default$iv = this.getMutex();
                owner$iv = null;
                $i$f$withLock = false;
                var6_10 = false;
                $continuation.L$0 = lockKey;
                $continuation.L$1 = $this$withLock_u24default$iv;
                $continuation.label = 1;
                v0 = $this$withLock_u24default$iv.lock(owner$iv, (Continuation)$continuation);
                if (v0 == var10_5) {
                    return var10_5;
                }
                ** GOTO lbl30
            }
            case 1: {
                $i$f$withLock = false;
                owner$iv = null;
                $this$withLock_u24default$iv = (Mutex)$continuation.L$1;
                lockKey = (String)$continuation.L$0;
                ResultKt.throwOnFailure((Object)$result);
                v0 = $result;
lbl30:
                // 2 sources

                try {
                    $i$a$-withLock$default-UserMutex$getLocker$2 = false;
                    if (UserMutex.INSTANCE.getLockerMap().get((Object)lockKey) == null) {
                        UserMutex.INSTANCE.getLockerMap().put((Object)lockKey, (Object)MutexKt.Mutex$default((boolean)false, (int)1, null));
                    }
                    v1 = UserMutex.INSTANCE.getLockerMap().get((Object)lockKey);
                    Intrinsics.checkNotNull((Object)v1);
                    var6_11 = (Mutex)v1;
                }
                finally {
                    $this$withLock_u24default$iv.unlock(owner$iv);
                }
                return var6_11;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}

