/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.help.http.RequestMethod
 *  kotlin.Metadata
 */
package io.legado.app.help.http;

import kotlin.Metadata;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lio/legado/app/help/http/RequestMethod;", "", "(Ljava/lang/String;I)V", "GET", "POST", "reader-pro"})
public final class RequestMethod
extends Enum<RequestMethod> {
    public static final /* enum */ RequestMethod GET = new RequestMethod("GET", 0);
    public static final /* enum */ RequestMethod POST = new RequestMethod("POST", 1);
    private static final /* synthetic */ RequestMethod[] $VALUES;

    public static RequestMethod[] values() {
        return (RequestMethod[])$VALUES.clone();
    }

    public static RequestMethod valueOf(String value) {
        return Enum.valueOf(RequestMethod.class, value);
    }

    private static final /* synthetic */ RequestMethod[] $values() {
        RequestMethod[] requestMethodArray = new RequestMethod[]{GET, POST};
        return requestMethodArray;
    }

    static {
        $VALUES = RequestMethod.$values();
    }
}

