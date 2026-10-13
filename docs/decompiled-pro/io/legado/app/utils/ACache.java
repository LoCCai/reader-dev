/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.ACache
 *  io.legado.app.utils.ACache$ACacheManager
 *  io.legado.app.utils.ACache$Companion
 *  io.legado.app.utils.ACache$Utils
 *  io.legado.app.utils.ACacheKt
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.io.CloseableKt
 *  kotlin.io.FilesKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.utils;

import io.legado.app.utils.ACache;
import io.legado.app.utils.ACacheKt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 \u001d2\u00020\u0001:\u0003\u001c\u001d\u001eB\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0015\u001a\u00020\u000fJ\"\u0010\u0016\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u0007H\u0007J\u0016\u0010\u0016\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0011J\u001e\u0010\u0016\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0007J\u0016\u0010\u0016\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fJ\u001e\u0010\u0016\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u0007J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000e\u001a\u00020\u000fR\u0014\u0010\t\u001a\b\u0018\u00010\nR\u00020\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2={"Lio/legado/app/utils/ACache;", "", "cacheDir", "Ljava/io/File;", "max_size", "", "max_count", "", "(Ljava/io/File;JI)V", "mCache", "Lio/legado/app/utils/ACache$ACacheManager;", "clear", "", "file", "key", "", "getAsBinary", "", "getAsObject", "getAsString", "getByHashCode", "hashCode", "put", "value", "Ljava/io/Serializable;", "saveTime", "remove", "", "ACacheManager", "Companion", "Utils", "reader-pro"})
public final class ACache {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private ACacheManager mCache;
    public static final int TIME_HOUR = 3600;
    public static final int TIME_DAY = 86400;
    private static final int MAX_SIZE = 50000000;
    private static final int MAX_COUNT = Integer.MAX_VALUE;
    @NotNull
    private static final HashMap<String, ACache> mInstanceMap = new HashMap();

    private ACache(File cacheDir2, long max_size, int max_count) {
        try {
            if (!cacheDir2.exists() && !cacheDir2.mkdirs()) {
                ACacheKt.access$getLogger$p().info(Intrinsics.stringPlus((String)"ACache can't make dirs in %s", (Object)cacheDir2.getAbsolutePath()));
            }
            this.mCache = new ACacheManager(this, cacheDir2, max_size, max_count);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void put(@NotNull String key, @NotNull String value) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            ACacheManager aCacheManager2 = aCacheManager;
            boolean bl = false;
            boolean bl2 = false;
            ACacheManager mCache = aCacheManager2;
            boolean bl3 = false;
            try {
                File file = mCache.newFile(key);
                FilesKt.writeText$default((File)file, (String)value, null, (int)2, null);
                mCache.put(file);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void put(@NotNull String key, @NotNull String value, int saveTime) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        if (saveTime <= 0) {
            this.put(key, value);
            return;
        }
        this.put(key, Utils.INSTANCE.newStringWithDateInfo(saveTime, value));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final String getAsString(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            boolean removeFile;
            block7: {
                ACacheManager aCacheManager2 = aCacheManager;
                boolean bl = false;
                boolean bl2 = false;
                ACacheManager mCache = aCacheManager2;
                boolean bl3 = false;
                File file = mCache.get(key);
                if (!file.exists()) {
                    return null;
                }
                removeFile = false;
                String text = FilesKt.readText$default((File)file, null, (int)1, null);
                if (Utils.INSTANCE.isDue(text)) break block7;
                String string = Utils.INSTANCE.clearDateInfo(text);
                return string;
            }
            try {
                removeFile = true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            this.remove(key);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final String getByHashCode(@NotNull String hashCode) {
        Intrinsics.checkNotNullParameter((Object)hashCode, (String)"hashCode");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            boolean removeFile;
            File file;
            block7: {
                ACacheManager aCacheManager2 = aCacheManager;
                boolean bl = false;
                boolean bl2 = false;
                ACacheManager mCache = aCacheManager2;
                boolean bl3 = false;
                file = mCache.newFileFromHashCode(hashCode);
                if (!file.exists()) {
                    return null;
                }
                removeFile = false;
                String text = FilesKt.readText$default((File)file, null, (int)1, null);
                if (Utils.INSTANCE.isDue(text)) break block7;
                String string = Utils.INSTANCE.clearDateInfo(text);
                return string;
            }
            try {
                removeFile = true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            file.delete();
        }
        return null;
    }

    public final void put(@NotNull String key, @NotNull byte[] value) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            ACacheManager aCacheManager2 = aCacheManager;
            boolean bl = false;
            boolean bl2 = false;
            ACacheManager mCache = aCacheManager2;
            boolean bl3 = false;
            File file = mCache.newFile(key);
            FilesKt.writeBytes((File)file, (byte[])value);
            mCache.put(file);
        }
    }

    public final void put(@NotNull String key, @NotNull byte[] value, int saveTime) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        if (saveTime <= 0) {
            this.put(key, value);
            return;
        }
        this.put(key, Utils.INSTANCE.newByteArrayWithDateInfo(saveTime, value));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final byte[] getAsBinary(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            ACacheManager aCacheManager2 = aCacheManager;
            boolean bl = false;
            boolean bl2 = false;
            ACacheManager mCache = aCacheManager2;
            boolean bl3 = false;
            boolean removeFile = false;
            try {
                byte[] byArray;
                File file = mCache.get(key);
                if (!file.exists()) {
                    byte[] byArray2 = null;
                    return byArray2;
                }
                byte[] byteArray2 = FilesKt.readBytes((File)file);
                if (!Utils.INSTANCE.isDue(byteArray2)) {
                    byArray = Utils.INSTANCE.clearDateInfo(byteArray2);
                } else {
                    removeFile = true;
                    byArray = null;
                }
                byte[] byArray3 = byArray;
                return byArray3;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (removeFile) {
                    this.remove(key);
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @JvmOverloads
    public final void put(@NotNull String key, @NotNull Serializable value, int saveTime) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Closeable closeable = new ObjectOutputStream(byteArrayOutputStream);
            boolean bl = false;
            boolean bl2 = false;
            Throwable throwable = null;
            try {
                ObjectOutputStream oos = (ObjectOutputStream)closeable;
                boolean bl3 = false;
                oos.writeObject(value);
                byte[] data = byteArrayOutputStream.toByteArray();
                if (saveTime != -1) {
                    Intrinsics.checkNotNullExpressionValue((Object)data, (String)"data");
                    this.put(key, data, saveTime);
                } else {
                    Intrinsics.checkNotNullExpressionValue((Object)data, (String)"data");
                    this.put(key, data);
                }
                Unit unit = Unit.INSTANCE;
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static /* synthetic */ void put$default(ACache aCache, String string, Serializable serializable, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = -1;
        }
        aCache.put(string, serializable, n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Nullable
    public final Object getAsObject(@NotNull String key) {
        block20: {
            Intrinsics.checkNotNullParameter((Object)key, (String)"key");
            data = this.getAsBinary(key);
            if (data != null) {
                bis = null;
                ois = null;
                bis = new ByteArrayInputStream(data);
                ois = new ObjectInputStream(bis);
                var5_5 = ois.readObject();
                ** try [egrp 1[TRYBLOCK] [0 : 51->63)] { 
lbl-1000:
                // 1 sources

                {
                    var6_11 = bis;
                    var6_11.close();
                }
lbl16:
                // 1 sources

                catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    e = ois;
                    e.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return var5_5;
                catch (Exception e) {
                    e.printStackTrace();
                    break block20;
                }
                finally {
                    try {
                        e = bis;
                        if (e != null) {
                            e.close();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        e = ois;
                        if (e != null) {
                            e.close();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    public final File file(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            ACacheManager aCacheManager2 = aCacheManager;
            boolean bl = false;
            boolean bl2 = false;
            ACacheManager mCache = aCacheManager2;
            boolean bl3 = false;
            try {
                File f = mCache.newFile(key);
                if (f.exists()) {
                    return f;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public final boolean remove(@NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        ACacheManager aCacheManager = this.mCache;
        return aCacheManager == null ? false : aCacheManager.remove(key);
    }

    public final void clear() {
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            aCacheManager.clear();
        }
    }

    @JvmOverloads
    public final void put(@NotNull String key, @NotNull Serializable value) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        ACache.put$default((ACache)this, (String)key, (Serializable)value, (int)0, (int)4, null);
    }

    public static final /* synthetic */ HashMap access$getMInstanceMap$cp() {
        return mInstanceMap;
    }

    public /* synthetic */ ACache(File cacheDir2, long max_size, int max_count, DefaultConstructorMarker $constructor_marker) {
        this(cacheDir2, max_size, max_count);
    }
}

