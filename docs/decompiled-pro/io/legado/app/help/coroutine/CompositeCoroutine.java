/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.help.coroutine.CompositeCoroutine
 *  io.legado.app.help.coroutine.Coroutine
 *  io.legado.app.help.coroutine.CoroutineContainer
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.help.coroutine;

import io.legado.app.help.coroutine.Coroutine;
import io.legado.app.help.coroutine.CoroutineContainer;
import java.util.Arrays;
import java.util.HashSet;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0002B#\b\u0016\u0012\u001a\u0010\u0003\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00050\u0004\"\u0006\u0012\u0002\b\u00030\u0005\u00a2\u0006\u0002\u0010\u0006B\u0019\b\u0016\u0012\u0010\u0010\u0003\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u0007\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\u0013\u001a\u00020\n2\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0016J)\u0010\u0015\u001a\u00020\n2\u001a\u0010\u0003\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00050\u0004\"\u0006\u0012\u0002\b\u00030\u0005H\u0016\u00a2\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0014\u0010\u0019\u001a\u00020\n2\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0016J\u0014\u0010\u001a\u001a\u00020\n2\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0016R\u0011\u0010\t\u001a\u00020\n8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\u000bR*\u0010\f\u001a\u001e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0018\u00010\rj\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0018\u0001`\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001b"}, d2={"Lio/legado/app/help/coroutine/CompositeCoroutine;", "Lio/legado/app/help/coroutine/CoroutineContainer;", "()V", "coroutines", "", "Lio/legado/app/help/coroutine/Coroutine;", "([Lio/legado/app/help/coroutine/Coroutine;)V", "", "(Ljava/lang/Iterable;)V", "isEmpty", "", "()Z", "resources", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "size", "", "getSize", "()I", "add", "coroutine", "addAll", "([Lio/legado/app/help/coroutine/Coroutine;)Z", "clear", "", "delete", "remove", "reader-pro"})
public final class CompositeCoroutine
implements CoroutineContainer {
    @Nullable
    private HashSet<Coroutine<?>> resources;

    public final int getSize() {
        int n;
        HashSet hashSet = this.resources;
        return hashSet == null ? 0 : (n = hashSet.size());
    }

    public final boolean isEmpty() {
        return this.getSize() == 0;
    }

    public CompositeCoroutine() {
    }

    public CompositeCoroutine(Coroutine<?> ... coroutines) {
        Intrinsics.checkNotNullParameter(coroutines, (String)"coroutines");
        this.resources = SetsKt.hashSetOf((Object[])Arrays.copyOf(coroutines, coroutines.length));
    }

    public CompositeCoroutine(@NotNull Iterable<? extends Coroutine<?>> coroutines) {
        Intrinsics.checkNotNullParameter(coroutines, (String)"coroutines");
        boolean bl = false;
        this.resources = new HashSet();
        for (Coroutine<?> d : coroutines) {
            HashSet hashSet = this.resources;
            if (hashSet == null) continue;
            hashSet.add(d);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean add(@NotNull Coroutine<?> coroutine) {
        Intrinsics.checkNotNullParameter(coroutine, (String)"coroutine");
        boolean bl = false;
        boolean bl2 = false;
        synchronized (this) {
            boolean bl3 = false;
            HashSet set = this.resources;
            if (this.resources == null) {
                boolean bl4 = false;
                this.resources = set = new HashSet();
            }
            HashSet hashSet = set;
            Intrinsics.checkNotNull((Object)hashSet);
            boolean bl5 = hashSet.add(coroutine);
            return bl5;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean addAll(Coroutine<?> ... coroutines) {
        Intrinsics.checkNotNullParameter(coroutines, (String)"coroutines");
        boolean bl = false;
        boolean bl2 = false;
        synchronized (this) {
            boolean bl3 = false;
            HashSet set = this.resources;
            if (this.resources == null) {
                boolean bl4 = false;
                this.resources = set = new HashSet();
            }
            for (Coroutine<?> coroutine : coroutines) {
                HashSet hashSet = set;
                Intrinsics.checkNotNull(hashSet);
                boolean add = hashSet.add(coroutine);
                if (add) continue;
                boolean bl5 = false;
                return bl5;
            }
            Unit unit = Unit.INSTANCE;
        }
        return true;
    }

    public boolean remove(@NotNull Coroutine<?> coroutine) {
        Intrinsics.checkNotNullParameter(coroutine, (String)"coroutine");
        if (this.delete(coroutine)) {
            Coroutine.cancel$default(coroutine, null, (int)1, null);
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean delete(@NotNull Coroutine<?> coroutine) {
        Intrinsics.checkNotNullParameter(coroutine, (String)"coroutine");
        boolean bl = false;
        boolean bl2 = false;
        synchronized (this) {
            block4: {
                boolean bl3 = false;
                HashSet set = this.resources;
                if (set != null && set.remove(coroutine)) break block4;
                boolean bl4 = false;
                return bl4;
            }
            Unit unit = Unit.INSTANCE;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public void clear() {
        HashSet set = null;
        boolean bl = false;
        boolean bl2 = false;
        synchronized (this) {
            boolean bl3 = false;
            set = this.resources;
            this.resources = null;
            Unit unit = Unit.INSTANCE;
        }
        HashSet hashSet = set;
        if (hashSet != null) {
            Iterable $this$forEachIndexed$iv = hashSet;
            boolean $i$f$forEachIndexed = false;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                void coroutine;
                int n = index$iv++;
                boolean bl4 = false;
                if (n < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Coroutine coroutine2 = (Coroutine)item$iv;
                int $noName_0 = n;
                boolean bl5 = false;
                Coroutine.cancel$default((Coroutine)coroutine, null, (int)1, null);
            }
        }
    }
}

