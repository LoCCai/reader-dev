/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.TxtTocRule
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.data.entities;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001e\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010 \u001a\u00020\u0005H\u00c6\u0003J\t\u0010!\u001a\u00020\bH\u00c6\u0003J\t\u0010\"\u001a\u00020\nH\u00c6\u0003J;\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010$\u001a\u00020\n2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\bH\u00d6\u0001J\t\u0010'\u001a\u00020\u0005H\u00d6\u0001R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0015\"\u0004\b\u0019\u0010\u0017R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d\u00a8\u0006("}, d2={"Lio/legado/app/data/entities/TxtTocRule;", "", "id", "", "name", "", "rule", "serialNumber", "", "enable", "", "(JLjava/lang/String;Ljava/lang/String;IZ)V", "getEnable", "()Z", "setEnable", "(Z)V", "getId", "()J", "setId", "(J)V", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "getRule", "setRule", "getSerialNumber", "()I", "setSerialNumber", "(I)V", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "toString", "reader-pro"})
public final class TxtTocRule {
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String rule;
    private int serialNumber;
    private boolean enable;

    public TxtTocRule(long id, @NotNull String name, @NotNull String rule, int serialNumber, boolean enable) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)rule, (String)"rule");
        this.id = id;
        this.name = name;
        this.rule = rule;
        this.serialNumber = serialNumber;
        this.enable = enable;
    }

    public /* synthetic */ TxtTocRule(long l, String string, String string2, int n, boolean bl, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            l = System.currentTimeMillis();
        }
        if ((n2 & 2) != 0) {
            string = "";
        }
        if ((n2 & 4) != 0) {
            string2 = "";
        }
        if ((n2 & 8) != 0) {
            n = -1;
        }
        if ((n2 & 0x10) != 0) {
            bl = true;
        }
        this(l, string, string2, n, bl);
    }

    public final long getId() {
        return this.id;
    }

    public final void setId(long l) {
        this.id = l;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.name = string;
    }

    @NotNull
    public final String getRule() {
        return this.rule;
    }

    public final void setRule(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.rule = string;
    }

    public final int getSerialNumber() {
        return this.serialNumber;
    }

    public final void setSerialNumber(int n) {
        this.serialNumber = n;
    }

    public final boolean getEnable() {
        return this.enable;
    }

    public final void setEnable(boolean bl) {
        this.enable = bl;
    }

    public final long component1() {
        return this.id;
    }

    @NotNull
    public final String component2() {
        return this.name;
    }

    @NotNull
    public final String component3() {
        return this.rule;
    }

    public final int component4() {
        return this.serialNumber;
    }

    public final boolean component5() {
        return this.enable;
    }

    @NotNull
    public final TxtTocRule copy(long id, @NotNull String name, @NotNull String rule, int serialNumber, boolean enable) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)rule, (String)"rule");
        return new TxtTocRule(id, name, rule, serialNumber, enable);
    }

    public static /* synthetic */ TxtTocRule copy$default(TxtTocRule txtTocRule, long l, String string, String string2, int n, boolean bl, int n2, Object object) {
        if ((n2 & 1) != 0) {
            l = txtTocRule.id;
        }
        if ((n2 & 2) != 0) {
            string = txtTocRule.name;
        }
        if ((n2 & 4) != 0) {
            string2 = txtTocRule.rule;
        }
        if ((n2 & 8) != 0) {
            n = txtTocRule.serialNumber;
        }
        if ((n2 & 0x10) != 0) {
            bl = txtTocRule.enable;
        }
        return txtTocRule.copy(l, string, string2, n, bl);
    }

    @NotNull
    public String toString() {
        return "TxtTocRule(id=" + this.id + ", name=" + this.name + ", rule=" + this.rule + ", serialNumber=" + this.serialNumber + ", enable=" + this.enable + ')';
    }

    public int hashCode() {
        int result2 = Long.hashCode(this.id);
        result2 = result2 * 31 + this.name.hashCode();
        result2 = result2 * 31 + this.rule.hashCode();
        result2 = result2 * 31 + Integer.hashCode(this.serialNumber);
        int n = this.enable ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result2 = result2 * 31 + n;
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TxtTocRule)) {
            return false;
        }
        TxtTocRule txtTocRule = (TxtTocRule)other;
        if (this.id != txtTocRule.id) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.name, (Object)txtTocRule.name)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.rule, (Object)txtTocRule.rule)) {
            return false;
        }
        if (this.serialNumber != txtTocRule.serialNumber) {
            return false;
        }
        return this.enable == txtTocRule.enable;
    }

    public TxtTocRule() {
        this(0L, null, null, 0, false, 31, null);
    }
}

