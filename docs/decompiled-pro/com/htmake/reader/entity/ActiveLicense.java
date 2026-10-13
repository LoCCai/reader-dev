/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.entity.ActiveLicense
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.entity;

import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\bW\b\u0086\b\u0018\u00002\u00020\u0001B\u00bd\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\t\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0018J\t\u0010H\u001a\u00020\u0003H\u00c6\u0003J\t\u0010I\u001a\u00020\u0005H\u00c6\u0003J\t\u0010J\u001a\u00020\u0003H\u00c6\u0003J\t\u0010K\u001a\u00020\u0005H\u00c6\u0003J\t\u0010L\u001a\u00020\u0007H\u00c6\u0003J\t\u0010M\u001a\u00020\u0003H\u00c6\u0003J\t\u0010N\u001a\u00020\u0003H\u00c6\u0003J\t\u0010O\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010P\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u00106J\t\u0010Q\u001a\u00020\u0003H\u00c6\u0003J\t\u0010R\u001a\u00020\u0005H\u00c6\u0003J\t\u0010S\u001a\u00020\u0007H\u00c6\u0003J\t\u0010T\u001a\u00020\tH\u00c6\u0003J\t\u0010U\u001a\u00020\u0007H\u00c6\u0003J\t\u0010V\u001a\u00020\u0003H\u00c6\u0003J\t\u0010W\u001a\u00020\u0003H\u00c6\u0003J\t\u0010X\u001a\u00020\tH\u00c6\u0003J\u0010\u0010Y\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u00106J\u00c6\u0001\u0010Z\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\t2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u00072\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00032\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u0017\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010[J\u0013\u0010\\\u001a\u00020\t2\b\u0010]\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010^\u001a\u00020\u0005H\u00d6\u0001J\t\u0010_\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u0014\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u0013\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001a\"\u0004\b\u001e\u0010\u001cR\u001a\u0010\u0011\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010\u0012\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u001a\"\u0004\b(\u0010\u001cR\u001a\u0010\u0017\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u001a\"\u0004\b*\u0010\u001cR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010$\"\u0004\b,\u0010&R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u001a\"\u0004\b.\u0010\u001cR\u001a\u0010\u000b\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u001a\"\u0004\b0\u0010\u001cR\u001a\u0010\u000f\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010 \"\u0004\b2\u0010\"R\u001a\u0010\u0015\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u001a\"\u0004\b4\u0010\u001cR\u001e\u0010\u0016\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u00109\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u001a\u0010\n\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010$\"\u0004\b?\u0010&R\u001a\u0010\u0010\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b@\u0010\u001a\"\u0004\bA\u0010\u001cR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010 \"\u0004\bC\u0010\"R\u001a\u0010\r\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010;\"\u0004\bE\u0010=R\u001e\u0010\u000e\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u00109\u001a\u0004\bF\u00106\"\u0004\bG\u00108\u00a8\u0006`"}, d2={"Lcom/htmake/reader/entity/ActiveLicense;", "", "host", "", "userMaxLimit", "", "expiredAt", "", "openApi", "", "simpleWebExpiredAt", "id", "code", "verified", "verifyTime", "instances", "type", "activeOrder", "activeTime", "activeIp", "activeEmail", "lastOnlineIp", "lastOnlineTime", "errorMsg", "(Ljava/lang/String;IJZJLjava/lang/String;Ljava/lang/String;ZLjava/lang/Long;ILjava/lang/String;IJLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;)V", "getActiveEmail", "()Ljava/lang/String;", "setActiveEmail", "(Ljava/lang/String;)V", "getActiveIp", "setActiveIp", "getActiveOrder", "()I", "setActiveOrder", "(I)V", "getActiveTime", "()J", "setActiveTime", "(J)V", "getCode", "setCode", "getErrorMsg", "setErrorMsg", "getExpiredAt", "setExpiredAt", "getHost", "setHost", "getId", "setId", "getInstances", "setInstances", "getLastOnlineIp", "setLastOnlineIp", "getLastOnlineTime", "()Ljava/lang/Long;", "setLastOnlineTime", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "getOpenApi", "()Z", "setOpenApi", "(Z)V", "getSimpleWebExpiredAt", "setSimpleWebExpiredAt", "getType", "setType", "getUserMaxLimit", "setUserMaxLimit", "getVerified", "setVerified", "getVerifyTime", "setVerifyTime", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;IJZJLjava/lang/String;Ljava/lang/String;ZLjava/lang/Long;ILjava/lang/String;IJLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;)Lcom/htmake/reader/entity/ActiveLicense;", "equals", "other", "hashCode", "toString", "reader-pro"})
public final class ActiveLicense {
    @NotNull
    private String host;
    private int userMaxLimit;
    private long expiredAt;
    private boolean openApi;
    private long simpleWebExpiredAt;
    @NotNull
    private String id;
    @NotNull
    private String code;
    private boolean verified;
    @Nullable
    private Long verifyTime;
    private int instances;
    @NotNull
    private String type;
    private int activeOrder;
    private long activeTime;
    @NotNull
    private String activeIp;
    @NotNull
    private String activeEmail;
    @NotNull
    private String lastOnlineIp;
    @Nullable
    private Long lastOnlineTime;
    @NotNull
    private String errorMsg;

    public ActiveLicense(@NotNull String host, int userMaxLimit, long expiredAt, boolean openApi, long simpleWebExpiredAt, @NotNull String id, @NotNull String code, boolean verified, @Nullable Long verifyTime, int instances, @NotNull String type, int activeOrder, long activeTime, @NotNull String activeIp, @NotNull String activeEmail, @NotNull String lastOnlineIp, @Nullable Long lastOnlineTime, @NotNull String errorMsg) {
        Intrinsics.checkNotNullParameter((Object)host, (String)"host");
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)code, (String)"code");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)activeIp, (String)"activeIp");
        Intrinsics.checkNotNullParameter((Object)activeEmail, (String)"activeEmail");
        Intrinsics.checkNotNullParameter((Object)lastOnlineIp, (String)"lastOnlineIp");
        Intrinsics.checkNotNullParameter((Object)errorMsg, (String)"errorMsg");
        this.host = host;
        this.userMaxLimit = userMaxLimit;
        this.expiredAt = expiredAt;
        this.openApi = openApi;
        this.simpleWebExpiredAt = simpleWebExpiredAt;
        this.id = id;
        this.code = code;
        this.verified = verified;
        this.verifyTime = verifyTime;
        this.instances = instances;
        this.type = type;
        this.activeOrder = activeOrder;
        this.activeTime = activeTime;
        this.activeIp = activeIp;
        this.activeEmail = activeEmail;
        this.lastOnlineIp = lastOnlineIp;
        this.lastOnlineTime = lastOnlineTime;
        this.errorMsg = errorMsg;
    }

    public /* synthetic */ ActiveLicense(String string, int n, long l, boolean bl, long l2, String string2, String string3, boolean bl2, Long l3, int n2, String string4, int n3, long l4, String string5, String string6, String string7, Long l5, String string8, int n4, DefaultConstructorMarker defaultConstructorMarker) {
        String string9;
        if ((n4 & 1) != 0) {
            string = "*";
        }
        if ((n4 & 2) != 0) {
            n = 15;
        }
        if ((n4 & 4) != 0) {
            l = 0L;
        }
        if ((n4 & 8) != 0) {
            bl = false;
        }
        if ((n4 & 0x10) != 0) {
            l2 = 1682870399000L;
        }
        if ((n4 & 0x20) != 0) {
            string9 = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue((Object)string9, (String)"randomUUID().toString()");
            string2 = string9;
        }
        if ((n4 & 0x40) != 0) {
            string9 = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue((Object)string9, (String)"randomUUID().toString()");
            string3 = string9;
        }
        if ((n4 & 0x80) != 0) {
            bl2 = false;
        }
        if ((n4 & 0x100) != 0) {
            l3 = null;
        }
        if ((n4 & 0x200) != 0) {
            n2 = 1;
        }
        if ((n4 & 0x400) != 0) {
            string4 = "default";
        }
        if ((n4 & 0x800) != 0) {
            n3 = 1;
        }
        if ((n4 & 0x1000) != 0) {
            l4 = System.currentTimeMillis();
        }
        if ((n4 & 0x2000) != 0) {
            string5 = "";
        }
        if ((n4 & 0x4000) != 0) {
            string6 = "";
        }
        if ((n4 & 0x8000) != 0) {
            string7 = "";
        }
        if ((n4 & 0x10000) != 0) {
            l5 = null;
        }
        if ((n4 & 0x20000) != 0) {
            string8 = "";
        }
        this(string, n, l, bl, l2, string2, string3, bl2, l3, n2, string4, n3, l4, string5, string6, string7, l5, string8);
    }

    @NotNull
    public final String getHost() {
        return this.host;
    }

    public final void setHost(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.host = string;
    }

    public final int getUserMaxLimit() {
        return this.userMaxLimit;
    }

    public final void setUserMaxLimit(int n) {
        this.userMaxLimit = n;
    }

    public final long getExpiredAt() {
        return this.expiredAt;
    }

    public final void setExpiredAt(long l) {
        this.expiredAt = l;
    }

    public final boolean getOpenApi() {
        return this.openApi;
    }

    public final void setOpenApi(boolean bl) {
        this.openApi = bl;
    }

    public final long getSimpleWebExpiredAt() {
        return this.simpleWebExpiredAt;
    }

    public final void setSimpleWebExpiredAt(long l) {
        this.simpleWebExpiredAt = l;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    public final void setId(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.id = string;
    }

    @NotNull
    public final String getCode() {
        return this.code;
    }

    public final void setCode(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.code = string;
    }

    public final boolean getVerified() {
        return this.verified;
    }

    public final void setVerified(boolean bl) {
        this.verified = bl;
    }

    @Nullable
    public final Long getVerifyTime() {
        return this.verifyTime;
    }

    public final void setVerifyTime(@Nullable Long l) {
        this.verifyTime = l;
    }

    public final int getInstances() {
        return this.instances;
    }

    public final void setInstances(int n) {
        this.instances = n;
    }

    @NotNull
    public final String getType() {
        return this.type;
    }

    public final void setType(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.type = string;
    }

    public final int getActiveOrder() {
        return this.activeOrder;
    }

    public final void setActiveOrder(int n) {
        this.activeOrder = n;
    }

    public final long getActiveTime() {
        return this.activeTime;
    }

    public final void setActiveTime(long l) {
        this.activeTime = l;
    }

    @NotNull
    public final String getActiveIp() {
        return this.activeIp;
    }

    public final void setActiveIp(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.activeIp = string;
    }

    @NotNull
    public final String getActiveEmail() {
        return this.activeEmail;
    }

    public final void setActiveEmail(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.activeEmail = string;
    }

    @NotNull
    public final String getLastOnlineIp() {
        return this.lastOnlineIp;
    }

    public final void setLastOnlineIp(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.lastOnlineIp = string;
    }

    @Nullable
    public final Long getLastOnlineTime() {
        return this.lastOnlineTime;
    }

    public final void setLastOnlineTime(@Nullable Long l) {
        this.lastOnlineTime = l;
    }

    @NotNull
    public final String getErrorMsg() {
        return this.errorMsg;
    }

    public final void setErrorMsg(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.errorMsg = string;
    }

    @NotNull
    public final String component1() {
        return this.host;
    }

    public final int component2() {
        return this.userMaxLimit;
    }

    public final long component3() {
        return this.expiredAt;
    }

    public final boolean component4() {
        return this.openApi;
    }

    public final long component5() {
        return this.simpleWebExpiredAt;
    }

    @NotNull
    public final String component6() {
        return this.id;
    }

    @NotNull
    public final String component7() {
        return this.code;
    }

    public final boolean component8() {
        return this.verified;
    }

    @Nullable
    public final Long component9() {
        return this.verifyTime;
    }

    public final int component10() {
        return this.instances;
    }

    @NotNull
    public final String component11() {
        return this.type;
    }

    public final int component12() {
        return this.activeOrder;
    }

    public final long component13() {
        return this.activeTime;
    }

    @NotNull
    public final String component14() {
        return this.activeIp;
    }

    @NotNull
    public final String component15() {
        return this.activeEmail;
    }

    @NotNull
    public final String component16() {
        return this.lastOnlineIp;
    }

    @Nullable
    public final Long component17() {
        return this.lastOnlineTime;
    }

    @NotNull
    public final String component18() {
        return this.errorMsg;
    }

    @NotNull
    public final ActiveLicense copy(@NotNull String host, int userMaxLimit, long expiredAt, boolean openApi, long simpleWebExpiredAt, @NotNull String id, @NotNull String code, boolean verified, @Nullable Long verifyTime, int instances, @NotNull String type, int activeOrder, long activeTime, @NotNull String activeIp, @NotNull String activeEmail, @NotNull String lastOnlineIp, @Nullable Long lastOnlineTime, @NotNull String errorMsg) {
        Intrinsics.checkNotNullParameter((Object)host, (String)"host");
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)code, (String)"code");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)activeIp, (String)"activeIp");
        Intrinsics.checkNotNullParameter((Object)activeEmail, (String)"activeEmail");
        Intrinsics.checkNotNullParameter((Object)lastOnlineIp, (String)"lastOnlineIp");
        Intrinsics.checkNotNullParameter((Object)errorMsg, (String)"errorMsg");
        return new ActiveLicense(host, userMaxLimit, expiredAt, openApi, simpleWebExpiredAt, id, code, verified, verifyTime, instances, type, activeOrder, activeTime, activeIp, activeEmail, lastOnlineIp, lastOnlineTime, errorMsg);
    }

    public static /* synthetic */ ActiveLicense copy$default(ActiveLicense activeLicense, String string, int n, long l, boolean bl, long l2, String string2, String string3, boolean bl2, Long l3, int n2, String string4, int n3, long l4, String string5, String string6, String string7, Long l5, String string8, int n4, Object object) {
        if ((n4 & 1) != 0) {
            string = activeLicense.host;
        }
        if ((n4 & 2) != 0) {
            n = activeLicense.userMaxLimit;
        }
        if ((n4 & 4) != 0) {
            l = activeLicense.expiredAt;
        }
        if ((n4 & 8) != 0) {
            bl = activeLicense.openApi;
        }
        if ((n4 & 0x10) != 0) {
            l2 = activeLicense.simpleWebExpiredAt;
        }
        if ((n4 & 0x20) != 0) {
            string2 = activeLicense.id;
        }
        if ((n4 & 0x40) != 0) {
            string3 = activeLicense.code;
        }
        if ((n4 & 0x80) != 0) {
            bl2 = activeLicense.verified;
        }
        if ((n4 & 0x100) != 0) {
            l3 = activeLicense.verifyTime;
        }
        if ((n4 & 0x200) != 0) {
            n2 = activeLicense.instances;
        }
        if ((n4 & 0x400) != 0) {
            string4 = activeLicense.type;
        }
        if ((n4 & 0x800) != 0) {
            n3 = activeLicense.activeOrder;
        }
        if ((n4 & 0x1000) != 0) {
            l4 = activeLicense.activeTime;
        }
        if ((n4 & 0x2000) != 0) {
            string5 = activeLicense.activeIp;
        }
        if ((n4 & 0x4000) != 0) {
            string6 = activeLicense.activeEmail;
        }
        if ((n4 & 0x8000) != 0) {
            string7 = activeLicense.lastOnlineIp;
        }
        if ((n4 & 0x10000) != 0) {
            l5 = activeLicense.lastOnlineTime;
        }
        if ((n4 & 0x20000) != 0) {
            string8 = activeLicense.errorMsg;
        }
        return activeLicense.copy(string, n, l, bl, l2, string2, string3, bl2, l3, n2, string4, n3, l4, string5, string6, string7, l5, string8);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ActiveLicense(host=").append(this.host).append(", userMaxLimit=").append(this.userMaxLimit).append(", expiredAt=").append(this.expiredAt).append(", openApi=").append(this.openApi).append(", simpleWebExpiredAt=").append(this.simpleWebExpiredAt).append(", id=").append(this.id).append(", code=").append(this.code).append(", verified=").append(this.verified).append(", verifyTime=").append(this.verifyTime).append(", instances=").append(this.instances).append(", type=").append(this.type).append(", activeOrder=");
        stringBuilder.append(this.activeOrder).append(", activeTime=").append(this.activeTime).append(", activeIp=").append(this.activeIp).append(", activeEmail=").append(this.activeEmail).append(", lastOnlineIp=").append(this.lastOnlineIp).append(", lastOnlineTime=").append(this.lastOnlineTime).append(", errorMsg=").append(this.errorMsg).append(')');
        return stringBuilder.toString();
    }

    public int hashCode() {
        int result2 = this.host.hashCode();
        result2 = result2 * 31 + Integer.hashCode(this.userMaxLimit);
        result2 = result2 * 31 + Long.hashCode(this.expiredAt);
        int n = this.openApi ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result2 = result2 * 31 + n;
        result2 = result2 * 31 + Long.hashCode(this.simpleWebExpiredAt);
        result2 = result2 * 31 + this.id.hashCode();
        result2 = result2 * 31 + this.code.hashCode();
        int n2 = this.verified ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        result2 = result2 * 31 + n2;
        result2 = result2 * 31 + (this.verifyTime == null ? 0 : ((Object)this.verifyTime).hashCode());
        result2 = result2 * 31 + Integer.hashCode(this.instances);
        result2 = result2 * 31 + this.type.hashCode();
        result2 = result2 * 31 + Integer.hashCode(this.activeOrder);
        result2 = result2 * 31 + Long.hashCode(this.activeTime);
        result2 = result2 * 31 + this.activeIp.hashCode();
        result2 = result2 * 31 + this.activeEmail.hashCode();
        result2 = result2 * 31 + this.lastOnlineIp.hashCode();
        result2 = result2 * 31 + (this.lastOnlineTime == null ? 0 : ((Object)this.lastOnlineTime).hashCode());
        result2 = result2 * 31 + this.errorMsg.hashCode();
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ActiveLicense)) {
            return false;
        }
        ActiveLicense activeLicense = (ActiveLicense)other;
        if (!Intrinsics.areEqual((Object)this.host, (Object)activeLicense.host)) {
            return false;
        }
        if (this.userMaxLimit != activeLicense.userMaxLimit) {
            return false;
        }
        if (this.expiredAt != activeLicense.expiredAt) {
            return false;
        }
        if (this.openApi != activeLicense.openApi) {
            return false;
        }
        if (this.simpleWebExpiredAt != activeLicense.simpleWebExpiredAt) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.id, (Object)activeLicense.id)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.code, (Object)activeLicense.code)) {
            return false;
        }
        if (this.verified != activeLicense.verified) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.verifyTime, (Object)activeLicense.verifyTime)) {
            return false;
        }
        if (this.instances != activeLicense.instances) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.type, (Object)activeLicense.type)) {
            return false;
        }
        if (this.activeOrder != activeLicense.activeOrder) {
            return false;
        }
        if (this.activeTime != activeLicense.activeTime) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.activeIp, (Object)activeLicense.activeIp)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.activeEmail, (Object)activeLicense.activeEmail)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.lastOnlineIp, (Object)activeLicense.lastOnlineIp)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.lastOnlineTime, (Object)activeLicense.lastOnlineTime)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.errorMsg, (Object)activeLicense.errorMsg);
    }

    public ActiveLicense() {
        this(null, 0, 0L, false, 0L, null, null, false, null, 0, null, 0, 0L, null, null, null, null, null, 262143, null);
    }
}

