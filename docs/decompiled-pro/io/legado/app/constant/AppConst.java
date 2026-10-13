/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.script.javascript.RhinoScriptEngine
 *  io.legado.app.constant.AppConst
 *  io.legado.app.constant.AppConst$SCRIPT_ENGINE$2
 *  io.legado.app.constant.AppConst$TIME_FORMAT$2
 *  io.legado.app.constant.AppConst$dateFormat$2
 *  io.legado.app.constant.AppConst$fileNameFormat$2
 *  io.legado.app.constant.AppConst$keyboardToolChars$2
 *  io.legado.app.constant.AppConst$timeFormat$2
 *  io.legado.app.constant.AppConst$userAgent$2
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.constant;

import com.script.javascript.RhinoScriptEngine;
import io.legado.app.constant.AppConst;
import java.text.SimpleDateFormat;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u000e\u001a\u00020\u000fX\u0086T\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0010\u001a\u00020\n8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0011\u0010\fR\u001b\u0010\u0013\u001a\u00020\n8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\b\u001a\u0004\b\u0014\u0010\fR!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00178FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001a\u0010\b\u001a\u0004\b\u0018\u0010\u0019R\u001b\u0010\u001b\u001a\u00020\n8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001d\u0010\b\u001a\u0004\b\u001c\u0010\fR\u001b\u0010\u001e\u001a\u00020\u000f8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b!\u0010\b\u001a\u0004\b\u001f\u0010 \u00a8\u0006\""}, d2={"Lio/legado/app/constant/AppConst;", "", "()V", "SCRIPT_ENGINE", "Lcom/script/javascript/RhinoScriptEngine;", "getSCRIPT_ENGINE", "()Lcom/script/javascript/RhinoScriptEngine;", "SCRIPT_ENGINE$delegate", "Lkotlin/Lazy;", "TIME_FORMAT", "Ljava/text/SimpleDateFormat;", "getTIME_FORMAT", "()Ljava/text/SimpleDateFormat;", "TIME_FORMAT$delegate", "UA_NAME", "", "dateFormat", "getDateFormat", "dateFormat$delegate", "fileNameFormat", "getFileNameFormat", "fileNameFormat$delegate", "keyboardToolChars", "", "getKeyboardToolChars", "()Ljava/util/List;", "keyboardToolChars$delegate", "timeFormat", "getTimeFormat", "timeFormat$delegate", "userAgent", "getUserAgent", "()Ljava/lang/String;", "userAgent$delegate", "reader-pro"})
public final class AppConst {
    @NotNull
    public static final AppConst INSTANCE = new AppConst();
    @NotNull
    public static final String UA_NAME = "User-Agent";
    @NotNull
    private static final Lazy userAgent$delegate = LazyKt.lazy((Function0)((Function0)userAgent.2.INSTANCE));
    @NotNull
    private static final Lazy SCRIPT_ENGINE$delegate = LazyKt.lazy((Function0)((Function0)SCRIPT_ENGINE.2.INSTANCE));
    @NotNull
    private static final Lazy TIME_FORMAT$delegate = LazyKt.lazy((Function0)((Function0)TIME_FORMAT.2.INSTANCE));
    @NotNull
    private static final Lazy timeFormat$delegate = LazyKt.lazy((Function0)((Function0)timeFormat.2.INSTANCE));
    @NotNull
    private static final Lazy dateFormat$delegate = LazyKt.lazy((Function0)((Function0)dateFormat.2.INSTANCE));
    @NotNull
    private static final Lazy fileNameFormat$delegate = LazyKt.lazy((Function0)((Function0)fileNameFormat.2.INSTANCE));
    @NotNull
    private static final Lazy keyboardToolChars$delegate = LazyKt.lazy((Function0)((Function0)keyboardToolChars.2.INSTANCE));

    private AppConst() {
    }

    @NotNull
    public final String getUserAgent() {
        Lazy lazy = userAgent$delegate;
        boolean bl = false;
        return (String)lazy.getValue();
    }

    @NotNull
    public final RhinoScriptEngine getSCRIPT_ENGINE() {
        Lazy lazy = SCRIPT_ENGINE$delegate;
        boolean bl = false;
        return (RhinoScriptEngine)lazy.getValue();
    }

    @NotNull
    public final SimpleDateFormat getTIME_FORMAT() {
        Lazy lazy = TIME_FORMAT$delegate;
        boolean bl = false;
        return (SimpleDateFormat)lazy.getValue();
    }

    @NotNull
    public final SimpleDateFormat getTimeFormat() {
        Lazy lazy = timeFormat$delegate;
        boolean bl = false;
        return (SimpleDateFormat)lazy.getValue();
    }

    @NotNull
    public final SimpleDateFormat getDateFormat() {
        Lazy lazy = dateFormat$delegate;
        boolean bl = false;
        return (SimpleDateFormat)lazy.getValue();
    }

    @NotNull
    public final SimpleDateFormat getFileNameFormat() {
        Lazy lazy = fileNameFormat$delegate;
        boolean bl = false;
        return (SimpleDateFormat)lazy.getValue();
    }

    @NotNull
    public final List<String> getKeyboardToolChars() {
        Lazy lazy = keyboardToolChars$delegate;
        boolean bl = false;
        return (List)lazy.getValue();
    }
}

