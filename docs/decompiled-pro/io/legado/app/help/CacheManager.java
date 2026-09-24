/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.adapters.ReaderAdapterHelper
 *  io.legado.app.help.CacheManager
 *  io.legado.app.model.analyzeRule.QueryTTF
 *  io.legado.app.utils.ACache
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.help;

import io.legado.app.adapters.ReaderAdapterHelper;
import io.legado.app.model.analyzeRule.QueryTTF;
import io.legado.app.utils.ACache;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0003J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0013\u001a\u00020\u0003J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0013\u001a\u00020\u0003J\u0015\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0013\u001a\u00020\u0003J\u0015\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u001dJ\u0015\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\u0002\u0010 J\u0015\u0010!\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\"J\u0010\u0010#\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0013\u001a\u00020\u0003J\"\u0010$\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\u00012\b\b\u0002\u0010&\u001a\u00020\u001fH\u0007J \u0010'\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\u00032\b\b\u0002\u0010&\u001a\u00020\u001fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bRB\u0010\t\u001a6\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b0\nj\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b`\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006("}, d2={"Lio/legado/app/help/CacheManager;", "", "userNameSpace", "", "(Ljava/lang/String;)V", "cacheInstance", "Lio/legado/app/utils/ACache;", "getCacheInstance", "()Lio/legado/app/utils/ACache;", "queryTTFMap", "Ljava/util/HashMap;", "Lkotlin/Pair;", "", "Lio/legado/app/model/analyzeRule/QueryTTF;", "Lkotlin/collections/HashMap;", "getUserNameSpace", "()Ljava/lang/String;", "delete", "", "key", "get", "getByteArray", "", "getDouble", "", "(Ljava/lang/String;)Ljava/lang/Double;", "getFile", "getFloat", "", "(Ljava/lang/String;)Ljava/lang/Float;", "getInt", "", "(Ljava/lang/String;)Ljava/lang/Integer;", "getLong", "(Ljava/lang/String;)Ljava/lang/Long;", "getQueryTTF", "put", "value", "saveTime", "putFile", "reader-pro"})
public final class CacheManager {
    @NotNull
    private final String userNameSpace;
    @NotNull
    private final HashMap<String, Pair<Long, QueryTTF>> queryTTFMap;
    @NotNull
    private final ACache cacheInstance;

    public CacheManager(@NotNull String userNameSpace) {
        Intrinsics.checkNotNullParameter((Object)userNameSpace, (String)"userNameSpace");
        this.userNameSpace = userNameSpace;
        boolean bl = false;
        this.queryTTFMap = new HashMap();
        String[] stringArray = new String[]{"storage", "cache", "runtimeCache", this.userNameSpace};
        File cacheDir2 = new File(ReaderAdapterHelper.INSTANCE.getAdapter().getWorkDir(stringArray));
        this.cacheInstance = ACache.Companion.get(cacheDir2, 50000000L, 1000000);
    }

    @NotNull
    public final String getUserNameSpace() {
        return this.userNameSpace;
    }

    @NotNull
    public final ACache getCacheInstance() {
        return this.cacheInstance;
    }

    @JvmOverloads
    public final void put(@NotNull String key, @NotNull Object value, int saveTime) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        CharSequence charSequence = key;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return;
        }
        long deadline = saveTime == 0 ? 0L : System.currentTimeMillis() + (long)(saveTime * 1000);
        Object object = value;
        if (object instanceof QueryTTF) {
            Map map = this.queryTTFMap;
            Pair pair = new Pair((Object)deadline, value);
            boolean bl2 = false;
            map.put(key, pair);
        } else if (object instanceof byte[]) {
            this.cacheInstance.put(key, (byte[])value, saveTime);
        } else {
            this.cacheInstance.put(key, value.toString(), saveTime);
        }
    }

    public static /* synthetic */ void put$default(CacheManager cacheManager, String string, Object object, int n, int n2, Object object2) {
        if ((n2 & 4) != 0) {
            n = 0;
        }
        cacheManager.put(string, object, n);
    }

    @Nullable
    public final String get(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        CharSequence charSequence = key;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        return this.cacheInstance.getAsString(key);
    }

    @Nullable
    public final Integer getInt(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        String string = this.get(key);
        return string == null ? null : StringsKt.toIntOrNull((String)string);
    }

    @Nullable
    public final Long getLong(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        String string = this.get(key);
        return string == null ? null : StringsKt.toLongOrNull((String)string);
    }

    @Nullable
    public final Double getDouble(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        String string = this.get(key);
        return string == null ? null : StringsKt.toDoubleOrNull((String)string);
    }

    @Nullable
    public final Float getFloat(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        String string = this.get(key);
        return string == null ? null : StringsKt.toFloatOrNull((String)string);
    }

    @Nullable
    public final byte[] getByteArray(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        CharSequence charSequence = key;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        return this.cacheInstance.getAsBinary(key);
    }

    @Nullable
    public final QueryTTF getQueryTTF(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Pair pair = (Pair)this.queryTTFMap.get(key);
        if (pair == null) {
            return null;
        }
        Pair pair2 = pair;
        Pair cache = pair2;
        if (((Number)cache.getFirst()).longValue() == 0L || ((Number)cache.getFirst()).longValue() > System.currentTimeMillis()) {
            return (QueryTTF)cache.getSecond();
        }
        return null;
    }

    public final void putFile(@NotNull String key, @NotNull String value, int saveTime) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        CharSequence charSequence = key;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return;
        }
        this.cacheInstance.put(key, value, saveTime);
    }

    public static /* synthetic */ void putFile$default(CacheManager cacheManager, String string, String string2, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 0;
        }
        cacheManager.putFile(string, string2, n);
    }

    @Nullable
    public final String getFile(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        CharSequence charSequence = key;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        return this.cacheInstance.getAsString(key);
    }

    public final void delete(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        CharSequence charSequence = key;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return;
        }
        this.cacheInstance.remove(key);
    }

    @JvmOverloads
    public final void put(@NotNull String key, @NotNull Object value) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        CacheManager.put$default((CacheManager)this, (String)key, (Object)value, (int)0, (int)4, null);
    }
}

