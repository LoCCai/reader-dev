/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.BookGroup
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

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001e\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010!\u001a\u00020\bH\u00c6\u0003J\t\u0010\"\u001a\u00020\nH\u00c6\u0003J=\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010$\u001a\u00020\n2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\bH\u00d6\u0001J\t\u0010'\u001a\u00020\u0005H\u00d6\u0001R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u000fR\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d\u00a8\u0006("}, d2={"Lio/legado/app/data/entities/BookGroup;", "", "groupId", "", "groupName", "", "cover", "order", "", "show", "", "(JLjava/lang/String;Ljava/lang/String;IZ)V", "getCover", "()Ljava/lang/String;", "setCover", "(Ljava/lang/String;)V", "getGroupId", "()J", "setGroupId", "(J)V", "getGroupName", "setGroupName", "getOrder", "()I", "setOrder", "(I)V", "getShow", "()Z", "setShow", "(Z)V", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "toString", "reader-pro"})
public final class BookGroup {
    private long groupId;
    @NotNull
    private String groupName;
    @Nullable
    private String cover;
    private int order;
    private boolean show;

    public BookGroup(long groupId, @NotNull String groupName, @Nullable String cover, int order, boolean show) {
        Intrinsics.checkNotNullParameter((Object)groupName, (String)"groupName");
        this.groupId = groupId;
        this.groupName = groupName;
        this.cover = cover;
        this.order = order;
        this.show = show;
    }

    public /* synthetic */ BookGroup(long l, String string, String string2, int n, boolean bl, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            l = 0L;
        }
        if ((n2 & 2) != 0) {
            string = "";
        }
        if ((n2 & 4) != 0) {
            string2 = null;
        }
        if ((n2 & 8) != 0) {
            n = 0;
        }
        if ((n2 & 0x10) != 0) {
            bl = true;
        }
        this(l, string, string2, n, bl);
    }

    public final long getGroupId() {
        return this.groupId;
    }

    public final void setGroupId(long l) {
        this.groupId = l;
    }

    @NotNull
    public final String getGroupName() {
        return this.groupName;
    }

    public final void setGroupName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.groupName = string;
    }

    @Nullable
    public final String getCover() {
        return this.cover;
    }

    public final void setCover(@Nullable String string) {
        this.cover = string;
    }

    public final int getOrder() {
        return this.order;
    }

    public final void setOrder(int n) {
        this.order = n;
    }

    public final boolean getShow() {
        return this.show;
    }

    public final void setShow(boolean bl) {
        this.show = bl;
    }

    public final long component1() {
        return this.groupId;
    }

    @NotNull
    public final String component2() {
        return this.groupName;
    }

    @Nullable
    public final String component3() {
        return this.cover;
    }

    public final int component4() {
        return this.order;
    }

    public final boolean component5() {
        return this.show;
    }

    @NotNull
    public final BookGroup copy(long groupId, @NotNull String groupName, @Nullable String cover, int order, boolean show) {
        Intrinsics.checkNotNullParameter((Object)groupName, (String)"groupName");
        return new BookGroup(groupId, groupName, cover, order, show);
    }

    public static /* synthetic */ BookGroup copy$default(BookGroup bookGroup, long l, String string, String string2, int n, boolean bl, int n2, Object object) {
        if ((n2 & 1) != 0) {
            l = bookGroup.groupId;
        }
        if ((n2 & 2) != 0) {
            string = bookGroup.groupName;
        }
        if ((n2 & 4) != 0) {
            string2 = bookGroup.cover;
        }
        if ((n2 & 8) != 0) {
            n = bookGroup.order;
        }
        if ((n2 & 0x10) != 0) {
            bl = bookGroup.show;
        }
        return bookGroup.copy(l, string, string2, n, bl);
    }

    @NotNull
    public String toString() {
        return "BookGroup(groupId=" + this.groupId + ", groupName=" + this.groupName + ", cover=" + this.cover + ", order=" + this.order + ", show=" + this.show + ')';
    }

    public int hashCode() {
        int result2 = Long.hashCode(this.groupId);
        result2 = result2 * 31 + this.groupName.hashCode();
        result2 = result2 * 31 + (this.cover == null ? 0 : this.cover.hashCode());
        result2 = result2 * 31 + Integer.hashCode(this.order);
        int n = this.show ? 1 : 0;
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
        if (!(other instanceof BookGroup)) {
            return false;
        }
        BookGroup bookGroup = (BookGroup)other;
        if (this.groupId != bookGroup.groupId) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.groupName, (Object)bookGroup.groupName)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.cover, (Object)bookGroup.cover)) {
            return false;
        }
        if (this.order != bookGroup.order) {
            return false;
        }
        return this.show == bookGroup.show;
    }

    public BookGroup() {
        this(0L, null, null, 0, false, 31, null);
    }
}

