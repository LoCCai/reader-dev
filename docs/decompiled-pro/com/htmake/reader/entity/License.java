/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.entity.ActiveLicense
 *  com.htmake.reader.entity.License
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.entity;

import com.htmake.reader.entity.ActiveLicense;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b;\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001Bu\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\t\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\u0011J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\tH\u00c6\u0003J\u0010\u00105\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010/J\t\u00106\u001a\u00020\u0005H\u00c6\u0003J\t\u00107\u001a\u00020\u0007H\u00c6\u0003J\t\u00108\u001a\u00020\tH\u00c6\u0003J\t\u00109\u001a\u00020\u0007H\u00c6\u0003J\t\u0010:\u001a\u00020\u0005H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\t\u0010=\u001a\u00020\u0003H\u00c6\u0003J~\u0010>\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\t2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001\u00a2\u0006\u0002\u0010?J\u0013\u0010@\u001a\u00020\t2\b\u0010A\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010B\u001a\u00020\u0005H\u00d6\u0001J\u0006\u0010C\u001a\u00020\tJ\u0006\u0010D\u001a\u00020EJ\t\u0010F\u001a\u00020\u0003H\u00d6\u0001J\u000e\u0010G\u001a\u00020\t2\u0006\u0010H\u001a\u00020\u0003R\u001a\u0010\u000e\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0013\"\u0004\b\u001b\u0010\u0015R\u001a\u0010\r\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0013\"\u0004\b\u001d\u0010\u0015R\u001a\u0010\u000b\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010\n\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0017\"\u0004\b'\u0010\u0019R\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0013\"\u0004\b)\u0010\u0015R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u001f\"\u0004\b+\u0010!R\u001a\u0010\u000f\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010#\"\u0004\b-\u0010%R\u001e\u0010\u0010\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u00102\u001a\u0004\b.\u0010/\"\u0004\b0\u00101\u00a8\u0006I"}, d2={"Lcom/htmake/reader/entity/License;", "", "host", "", "userMaxLimit", "", "expiredAt", "", "openApi", "", "simpleWebExpiredAt", "instances", "type", "id", "code", "verified", "verifyTime", "(Ljava/lang/String;IJZJILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/Long;)V", "getCode", "()Ljava/lang/String;", "setCode", "(Ljava/lang/String;)V", "getExpiredAt", "()J", "setExpiredAt", "(J)V", "getHost", "setHost", "getId", "setId", "getInstances", "()I", "setInstances", "(I)V", "getOpenApi", "()Z", "setOpenApi", "(Z)V", "getSimpleWebExpiredAt", "setSimpleWebExpiredAt", "getType", "setType", "getUserMaxLimit", "setUserMaxLimit", "getVerified", "setVerified", "getVerifyTime", "()Ljava/lang/Long;", "setVerifyTime", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;IJZJILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/Long;)Lcom/htmake/reader/entity/License;", "equals", "other", "hashCode", "isValid", "toActiveLicense", "Lcom/htmake/reader/entity/ActiveLicense;", "toString", "validHost", "queryHost", "reader-pro"})
public final class License {
    @NotNull
    private String host;
    private int userMaxLimit;
    private long expiredAt;
    private boolean openApi;
    private long simpleWebExpiredAt;
    private int instances;
    @NotNull
    private String type;
    @NotNull
    private String id;
    @NotNull
    private String code;
    private boolean verified;
    @Nullable
    private Long verifyTime;

    public License(@NotNull String host, int userMaxLimit, long expiredAt, boolean openApi, long simpleWebExpiredAt, int instances, @NotNull String type, @NotNull String id, @NotNull String code, boolean verified, @Nullable Long verifyTime) {
        Intrinsics.checkNotNullParameter((Object)host, (String)"host");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)code, (String)"code");
        this.host = host;
        this.userMaxLimit = userMaxLimit;
        this.expiredAt = expiredAt;
        this.openApi = openApi;
        this.simpleWebExpiredAt = simpleWebExpiredAt;
        this.instances = instances;
        this.type = type;
        this.id = id;
        this.code = code;
        this.verified = verified;
        this.verifyTime = verifyTime;
    }

    public /* synthetic */ License(String string, int n, long l, boolean bl, long l2, int n2, String string2, String string3, String string4, boolean bl2, Long l3, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        String string5;
        if ((n3 & 1) != 0) {
            string = "*";
        }
        if ((n3 & 2) != 0) {
            n = 15;
        }
        if ((n3 & 4) != 0) {
            l = 0L;
        }
        if ((n3 & 8) != 0) {
            bl = false;
        }
        if ((n3 & 0x10) != 0) {
            l2 = 1688140799000L;
        }
        if ((n3 & 0x20) != 0) {
            n2 = 1;
        }
        if ((n3 & 0x40) != 0) {
            string2 = "default";
        }
        if ((n3 & 0x80) != 0) {
            string5 = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"randomUUID().toString()");
            string3 = string5;
        }
        if ((n3 & 0x100) != 0) {
            string5 = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"randomUUID().toString()");
            string4 = string5;
        }
        if ((n3 & 0x200) != 0) {
            bl2 = false;
        }
        if ((n3 & 0x400) != 0) {
            l3 = null;
        }
        this(string, n, l, bl, l2, n2, string2, string3, string4, bl2, l3);
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

    public final boolean isValid() {
        return this.expiredAt == 0L || this.expiredAt >= System.currentTimeMillis();
    }

    public final boolean validHost(@NotNull String queryHost) {
        Intrinsics.checkNotNullParameter((Object)queryHost, (String)"queryHost");
        if (!this.isValid()) {
            return false;
        }
        CharSequence charSequence = queryHost;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence.length() == 0) {
            return false;
        }
        if ("*".equals(this.host)) {
            return true;
        }
        String[] stringArray = new String[]{":"};
        List hostParts = StringsKt.split$default((CharSequence)queryHost, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
        String[] stringArray2 = new String[]{"."};
        List queryParts = StringsKt.split$default((CharSequence)((CharSequence)hostParts.get(0)), (String[])stringArray2, (boolean)false, (int)0, (int)6, null);
        String[] stringArray3 = new String[]{","};
        List hostList = StringsKt.split$default((CharSequence)this.host, (String[])stringArray3, (boolean)false, (int)0, (int)6, null);
        for (String hostname : hostList) {
            String[] stringArray4;
            List parts = StringsKt.split$default((CharSequence)hostname, (String[])(stringArray4 = new String[]{"."}), (boolean)false, (int)0, (int)6, null);
            if (parts.size() != queryParts.size()) continue;
            boolean isValid = true;
            int n = 0;
            int n2 = parts.size();
            if (n < n2) {
                do {
                    int i;
                    if ("*".equals(parts.get(i = n++)) || ((String)parts.get(i)).equals(queryParts.get(i))) continue;
                    isValid = false;
                } while (n < n2);
            }
            if (!isValid) continue;
            return true;
        }
        return false;
    }

    @NotNull
    public final ActiveLicense toActiveLicense() {
        return new ActiveLicense(this.host, this.userMaxLimit, this.expiredAt, this.openApi, this.simpleWebExpiredAt, this.id, this.code, this.verified, this.verifyTime, this.instances, this.type, 0, 0L, null, null, null, null, null, 260096, null);
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

    public final int component6() {
        return this.instances;
    }

    @NotNull
    public final String component7() {
        return this.type;
    }

    @NotNull
    public final String component8() {
        return this.id;
    }

    @NotNull
    public final String component9() {
        return this.code;
    }

    public final boolean component10() {
        return this.verified;
    }

    @Nullable
    public final Long component11() {
        return this.verifyTime;
    }

    @NotNull
    public final License copy(@NotNull String host, int userMaxLimit, long expiredAt, boolean openApi, long simpleWebExpiredAt, int instances, @NotNull String type, @NotNull String id, @NotNull String code, boolean verified, @Nullable Long verifyTime) {
        Intrinsics.checkNotNullParameter((Object)host, (String)"host");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)code, (String)"code");
        return new License(host, userMaxLimit, expiredAt, openApi, simpleWebExpiredAt, instances, type, id, code, verified, verifyTime);
    }

    public static /* synthetic */ License copy$default(License license, String string, int n, long l, boolean bl, long l2, int n2, String string2, String string3, String string4, boolean bl2, Long l3, int n3, Object object) {
        if ((n3 & 1) != 0) {
            string = license.host;
        }
        if ((n3 & 2) != 0) {
            n = license.userMaxLimit;
        }
        if ((n3 & 4) != 0) {
            l = license.expiredAt;
        }
        if ((n3 & 8) != 0) {
            bl = license.openApi;
        }
        if ((n3 & 0x10) != 0) {
            l2 = license.simpleWebExpiredAt;
        }
        if ((n3 & 0x20) != 0) {
            n2 = license.instances;
        }
        if ((n3 & 0x40) != 0) {
            string2 = license.type;
        }
        if ((n3 & 0x80) != 0) {
            string3 = license.id;
        }
        if ((n3 & 0x100) != 0) {
            string4 = license.code;
        }
        if ((n3 & 0x200) != 0) {
            bl2 = license.verified;
        }
        if ((n3 & 0x400) != 0) {
            l3 = license.verifyTime;
        }
        return license.copy(string, n, l, bl, l2, n2, string2, string3, string4, bl2, l3);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("License(host=").append(this.host).append(", userMaxLimit=").append(this.userMaxLimit).append(", expiredAt=").append(this.expiredAt).append(", openApi=").append(this.openApi).append(", simpleWebExpiredAt=").append(this.simpleWebExpiredAt).append(", instances=").append(this.instances).append(", type=").append(this.type).append(", id=").append(this.id).append(", code=").append(this.code).append(", verified=").append(this.verified).append(", verifyTime=").append(this.verifyTime).append(')');
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
        result2 = result2 * 31 + Integer.hashCode(this.instances);
        result2 = result2 * 31 + this.type.hashCode();
        result2 = result2 * 31 + this.id.hashCode();
        result2 = result2 * 31 + this.code.hashCode();
        int n2 = this.verified ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        result2 = result2 * 31 + n2;
        result2 = result2 * 31 + (this.verifyTime == null ? 0 : ((Object)this.verifyTime).hashCode());
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof License)) {
            return false;
        }
        License license = (License)other;
        if (!Intrinsics.areEqual((Object)this.host, (Object)license.host)) {
            return false;
        }
        if (this.userMaxLimit != license.userMaxLimit) {
            return false;
        }
        if (this.expiredAt != license.expiredAt) {
            return false;
        }
        if (this.openApi != license.openApi) {
            return false;
        }
        if (this.simpleWebExpiredAt != license.simpleWebExpiredAt) {
            return false;
        }
        if (this.instances != license.instances) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.type, (Object)license.type)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.id, (Object)license.id)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.code, (Object)license.code)) {
            return false;
        }
        if (this.verified != license.verified) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.verifyTime, (Object)license.verifyTime);
    }

    public License() {
        this(null, 0, 0L, false, 0L, 0, null, null, null, false, null, 2047, null);
    }
}

