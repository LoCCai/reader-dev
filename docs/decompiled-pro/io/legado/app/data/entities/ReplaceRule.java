/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  io.legado.app.data.entities.ReplaceRule
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.data.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b1\b\u0086\b\u0018\u00002\u00020\u0001B\u0081\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0012J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u000bH\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0011H\u00c6\u0003J\t\u00105\u001a\u00020\u0005H\u00c6\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u00107\u001a\u00020\u0005H\u00c6\u0003J\t\u00108\u001a\u00020\u0005H\u00c6\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010:\u001a\u00020\u000bH\u00c6\u0003J\t\u0010;\u001a\u00020\u000bH\u00c6\u0003J\t\u0010<\u001a\u00020\u000bH\u00c6\u0003J\u0085\u0001\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u00c6\u0001J\u0013\u0010>\u001a\u00020\u000b2\b\u0010?\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010@\u001a\u00020\u0011H\u00d6\u0001J\t\u0010A\u001a\u00020\u0005H\u00d6\u0001R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\r\u001a\u00020\u000b8\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u000e\u001a\u00020\u000b8\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u001b\"\u0004\b\u001e\u0010\u001dR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0014\"\u0004\b \u0010\u0016R\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0014\"\u0004\b&\u0010\u0016R\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0014\"\u0004\b(\u0010\u0016R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0014\"\u0004\b*\u0010\u0016R\u001a\u0010\f\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u001b\"\u0004\b,\u0010\u001dR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u001b\"\u0004\b.\u0010\u001dR\u001a\u0010\u000f\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0018\"\u0004\b0\u0010\u001a\u00a8\u0006B"}, d2={"Lio/legado/app/data/entities/ReplaceRule;", "", "id", "", "name", "", "group", "pattern", "replacement", "scope", "scopeTitle", "", "scopeContent", "isEnabled", "isRegex", "timeoutMillisecond", "order", "", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZZJI)V", "getGroup", "()Ljava/lang/String;", "setGroup", "(Ljava/lang/String;)V", "getId", "()J", "setId", "(J)V", "()Z", "setEnabled", "(Z)V", "setRegex", "getName", "setName", "getOrder", "()I", "setOrder", "(I)V", "getPattern", "setPattern", "getReplacement", "setReplacement", "getScope", "setScope", "getScopeContent", "setScopeContent", "getScopeTitle", "setScopeTitle", "getTimeoutMillisecond", "setTimeoutMillisecond", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "reader-pro"})
public final class ReplaceRule {
    private long id;
    @NotNull
    private String name;
    @Nullable
    private String group;
    @NotNull
    private String pattern;
    @NotNull
    private String replacement;
    @Nullable
    private String scope;
    private boolean scopeTitle;
    private boolean scopeContent;
    private boolean isEnabled;
    private boolean isRegex;
    private long timeoutMillisecond;
    private int order;

    public ReplaceRule(long id, @NotNull String name, @Nullable String group, @NotNull String pattern, @NotNull String replacement, @Nullable String scope, boolean scopeTitle, boolean scopeContent, boolean isEnabled, boolean isRegex, long timeoutMillisecond, int order) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)pattern, (String)"pattern");
        Intrinsics.checkNotNullParameter((Object)replacement, (String)"replacement");
        this.id = id;
        this.name = name;
        this.group = group;
        this.pattern = pattern;
        this.replacement = replacement;
        this.scope = scope;
        this.scopeTitle = scopeTitle;
        this.scopeContent = scopeContent;
        this.isEnabled = isEnabled;
        this.isRegex = isRegex;
        this.timeoutMillisecond = timeoutMillisecond;
        this.order = order;
    }

    public /* synthetic */ ReplaceRule(long l, String string, String string2, String string3, String string4, String string5, boolean bl, boolean bl2, boolean bl3, boolean bl4, long l2, int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            l = System.currentTimeMillis();
        }
        if ((n2 & 2) != 0) {
            string = "";
        }
        if ((n2 & 4) != 0) {
            string2 = null;
        }
        if ((n2 & 8) != 0) {
            string3 = "";
        }
        if ((n2 & 0x10) != 0) {
            string4 = "";
        }
        if ((n2 & 0x20) != 0) {
            string5 = null;
        }
        if ((n2 & 0x40) != 0) {
            bl = false;
        }
        if ((n2 & 0x80) != 0) {
            bl2 = true;
        }
        if ((n2 & 0x100) != 0) {
            bl3 = true;
        }
        if ((n2 & 0x200) != 0) {
            bl4 = false;
        }
        if ((n2 & 0x400) != 0) {
            l2 = 3000L;
        }
        if ((n2 & 0x800) != 0) {
            n = 0;
        }
        this(l, string, string2, string3, string4, string5, bl, bl2, bl3, bl4, l2, n);
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

    @Nullable
    public final String getGroup() {
        return this.group;
    }

    public final void setGroup(@Nullable String string) {
        this.group = string;
    }

    @NotNull
    public final String getPattern() {
        return this.pattern;
    }

    public final void setPattern(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.pattern = string;
    }

    @NotNull
    public final String getReplacement() {
        return this.replacement;
    }

    public final void setReplacement(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.replacement = string;
    }

    @Nullable
    public final String getScope() {
        return this.scope;
    }

    public final void setScope(@Nullable String string) {
        this.scope = string;
    }

    public final boolean getScopeTitle() {
        return this.scopeTitle;
    }

    public final void setScopeTitle(boolean bl) {
        this.scopeTitle = bl;
    }

    public final boolean getScopeContent() {
        return this.scopeContent;
    }

    public final void setScopeContent(boolean bl) {
        this.scopeContent = bl;
    }

    @JsonProperty(value="isEnabled")
    public final boolean isEnabled() {
        return this.isEnabled;
    }

    public final void setEnabled(boolean bl) {
        this.isEnabled = bl;
    }

    @JsonProperty(value="isRegex")
    public final boolean isRegex() {
        return this.isRegex;
    }

    public final void setRegex(boolean bl) {
        this.isRegex = bl;
    }

    public final long getTimeoutMillisecond() {
        return this.timeoutMillisecond;
    }

    public final void setTimeoutMillisecond(long l) {
        this.timeoutMillisecond = l;
    }

    public final int getOrder() {
        return this.order;
    }

    public final void setOrder(int n) {
        this.order = n;
    }

    public final long component1() {
        return this.id;
    }

    @NotNull
    public final String component2() {
        return this.name;
    }

    @Nullable
    public final String component3() {
        return this.group;
    }

    @NotNull
    public final String component4() {
        return this.pattern;
    }

    @NotNull
    public final String component5() {
        return this.replacement;
    }

    @Nullable
    public final String component6() {
        return this.scope;
    }

    public final boolean component7() {
        return this.scopeTitle;
    }

    public final boolean component8() {
        return this.scopeContent;
    }

    public final boolean component9() {
        return this.isEnabled;
    }

    public final boolean component10() {
        return this.isRegex;
    }

    public final long component11() {
        return this.timeoutMillisecond;
    }

    public final int component12() {
        return this.order;
    }

    @NotNull
    public final ReplaceRule copy(long id, @NotNull String name, @Nullable String group, @NotNull String pattern, @NotNull String replacement, @Nullable String scope, boolean scopeTitle, boolean scopeContent, boolean isEnabled, boolean isRegex, long timeoutMillisecond, int order) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)pattern, (String)"pattern");
        Intrinsics.checkNotNullParameter((Object)replacement, (String)"replacement");
        return new ReplaceRule(id, name, group, pattern, replacement, scope, scopeTitle, scopeContent, isEnabled, isRegex, timeoutMillisecond, order);
    }

    public static /* synthetic */ ReplaceRule copy$default(ReplaceRule replaceRule, long l, String string, String string2, String string3, String string4, String string5, boolean bl, boolean bl2, boolean bl3, boolean bl4, long l2, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            l = replaceRule.id;
        }
        if ((n2 & 2) != 0) {
            string = replaceRule.name;
        }
        if ((n2 & 4) != 0) {
            string2 = replaceRule.group;
        }
        if ((n2 & 8) != 0) {
            string3 = replaceRule.pattern;
        }
        if ((n2 & 0x10) != 0) {
            string4 = replaceRule.replacement;
        }
        if ((n2 & 0x20) != 0) {
            string5 = replaceRule.scope;
        }
        if ((n2 & 0x40) != 0) {
            bl = replaceRule.scopeTitle;
        }
        if ((n2 & 0x80) != 0) {
            bl2 = replaceRule.scopeContent;
        }
        if ((n2 & 0x100) != 0) {
            bl3 = replaceRule.isEnabled;
        }
        if ((n2 & 0x200) != 0) {
            bl4 = replaceRule.isRegex;
        }
        if ((n2 & 0x400) != 0) {
            l2 = replaceRule.timeoutMillisecond;
        }
        if ((n2 & 0x800) != 0) {
            n = replaceRule.order;
        }
        return replaceRule.copy(l, string, string2, string3, string4, string5, bl, bl2, bl3, bl4, l2, n);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ReplaceRule(id=").append(this.id).append(", name=").append(this.name).append(", group=").append((Object)this.group).append(", pattern=").append(this.pattern).append(", replacement=").append(this.replacement).append(", scope=").append((Object)this.scope).append(", scopeTitle=").append(this.scopeTitle).append(", scopeContent=").append(this.scopeContent).append(", isEnabled=").append(this.isEnabled).append(", isRegex=").append(this.isRegex).append(", timeoutMillisecond=").append(this.timeoutMillisecond).append(", order=");
        stringBuilder.append(this.order).append(')');
        return stringBuilder.toString();
    }

    public int hashCode() {
        int result2 = Long.hashCode(this.id);
        result2 = result2 * 31 + this.name.hashCode();
        result2 = result2 * 31 + (this.group == null ? 0 : this.group.hashCode());
        result2 = result2 * 31 + this.pattern.hashCode();
        result2 = result2 * 31 + this.replacement.hashCode();
        result2 = result2 * 31 + (this.scope == null ? 0 : this.scope.hashCode());
        int n = this.scopeTitle ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result2 = result2 * 31 + n;
        int n2 = this.scopeContent ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        result2 = result2 * 31 + n2;
        int n3 = this.isEnabled ? 1 : 0;
        if (n3 != 0) {
            n3 = 1;
        }
        result2 = result2 * 31 + n3;
        int n4 = this.isRegex ? 1 : 0;
        if (n4 != 0) {
            n4 = 1;
        }
        result2 = result2 * 31 + n4;
        result2 = result2 * 31 + Long.hashCode(this.timeoutMillisecond);
        result2 = result2 * 31 + Integer.hashCode(this.order);
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReplaceRule)) {
            return false;
        }
        ReplaceRule replaceRule = (ReplaceRule)other;
        if (this.id != replaceRule.id) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.name, (Object)replaceRule.name)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.group, (Object)replaceRule.group)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.pattern, (Object)replaceRule.pattern)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.replacement, (Object)replaceRule.replacement)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.scope, (Object)replaceRule.scope)) {
            return false;
        }
        if (this.scopeTitle != replaceRule.scopeTitle) {
            return false;
        }
        if (this.scopeContent != replaceRule.scopeContent) {
            return false;
        }
        if (this.isEnabled != replaceRule.isEnabled) {
            return false;
        }
        if (this.isRegex != replaceRule.isRegex) {
            return false;
        }
        if (this.timeoutMillisecond != replaceRule.timeoutMillisecond) {
            return false;
        }
        return this.order == replaceRule.order;
    }

    public ReplaceRule() {
        this(0L, null, null, null, null, null, false, false, false, false, 0L, 0, 4095, null);
    }
}

