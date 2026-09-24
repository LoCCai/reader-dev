/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.help.coroutine.Coroutine
 *  io.legado.app.help.coroutine.Coroutine$Callback
 *  io.legado.app.help.coroutine.Coroutine$Companion
 *  io.legado.app.help.coroutine.Coroutine$Result
 *  io.legado.app.help.coroutine.Coroutine$VoidCallback
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.CoroutineContext
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.InlineMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlinx.coroutines.BuildersKt
 *  kotlinx.coroutines.CoroutineScope
 *  kotlinx.coroutines.CoroutineScopeKt
 *  kotlinx.coroutines.Dispatchers
 *  kotlinx.coroutines.DisposableHandle
 *  kotlinx.coroutines.Job
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.help.coroutine;

import io.legado.app.help.coroutine.Coroutine;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 E*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0004DEFGBC\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012'\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\f\u001a\u00020\"2\u0010\b\u0002\u0010#\u001a\n\u0018\u00010$j\u0004\u0018\u0001`%J?\u0010&\u001a\u00020\"\"\u0004\b\u0001\u0010'2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010(\u001a\u0002H'2\u0016\u0010)\u001a\u0012\u0012\u0004\u0012\u0002H'0\u000fR\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0082H\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*J+\u0010+\u001a\u00020\"2\u0006\u0010\u0003\u001a\u00020\u00042\u0010\u0010)\u001a\f0\rR\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0082H\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010,JT\u0010-\u001a\u00028\u00002\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020 2)\b\b\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\nH\u0082H\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.JA\u0010/\u001a\u00020\u001a2\u0006\u0010\u0005\u001a\u00020\u00062'\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\nH\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100J/\u00101\u001a\u0002022'\u00103\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0010\u00a2\u0006\f\b5\u0012\b\b6\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\"04j\u0002`7JI\u00108\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062'\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u00109JO\u0010:\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062-\u0010\u0007\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020;\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010<J\u001c\u0010=\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u000e\u0010(\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000>J\u001b\u0010=\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\u0010(\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010?JI\u0010@\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062'\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u00109JI\u0010A\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062'\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u00109JO\u0010B\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062-\u0010\u0007\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020;\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010<J\u001a\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0>J\u0014\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001f\u001a\u00020 R\u001a\u0010\f\u001a\u000e\u0018\u00010\rR\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fR\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0018\u00010\rR\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u0018\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u000e\u0018\u00010\rR\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000fR\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010!\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006H"}, d2={"Lio/legado/app/help/coroutine/Coroutine;", "T", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)V", "cancel", "Lio/legado/app/help/coroutine/Coroutine$VoidCallback;", "error", "Lio/legado/app/help/coroutine/Coroutine$Callback;", "", "errorReturn", "Lio/legado/app/help/coroutine/Coroutine$Result;", "finally", "isActive", "", "()Z", "isCancelled", "isCompleted", "job", "Lkotlinx/coroutines/Job;", "getScope", "()Lkotlinx/coroutines/CoroutineScope;", "start", "success", "timeMillis", "", "Ljava/lang/Long;", "", "cause", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "dispatchCallback", "R", "value", "callback", "(Lkotlinx/coroutines/CoroutineScope;Ljava/lang/Object;Lio/legado/app/help/coroutine/Coroutine$Callback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dispatchVoidCallback", "(Lkotlinx/coroutines/CoroutineScope;Lio/legado/app/help/coroutine/Coroutine$VoidCallback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "executeBlock", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;JLkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "executeInternal", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/Job;", "invokeOnCompletion", "Lkotlinx/coroutines/DisposableHandle;", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "onCancel", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lio/legado/app/help/coroutine/Coroutine;", "onError", "Lkotlin/Function3;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function3;)Lio/legado/app/help/coroutine/Coroutine;", "onErrorReturn", "Lkotlin/Function0;", "(Ljava/lang/Object;)Lio/legado/app/help/coroutine/Coroutine;", "onFinally", "onStart", "onSuccess", "timeout", "Callback", "Companion", "Result", "VoidCallback", "reader-pro"})
public final class Coroutine<T> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final CoroutineScope scope;
    @NotNull
    private final Job job;
    @Nullable
    private VoidCallback start;
    @Nullable
    private Callback<T> success;
    @Nullable
    private Callback<Throwable> error;
    @Nullable
    private VoidCallback finally;
    @Nullable
    private VoidCallback cancel;
    @Nullable
    private Long timeMillis;
    @Nullable
    private Result<? extends T> errorReturn;
    @NotNull
    private static final CoroutineScope DEFAULT = CoroutineScopeKt.MainScope();

    public Coroutine(@NotNull CoroutineScope scope, @NotNull CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter((Object)scope, (String)"scope");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter(block, (String)"block");
        this.scope = scope;
        this.job = this.executeInternal(context, block);
    }

    public /* synthetic */ Coroutine(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Function2 function2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            coroutineContext = (CoroutineContext)Dispatchers.getIO();
        }
        this(coroutineScope, coroutineContext, function2);
    }

    @NotNull
    public final CoroutineScope getScope() {
        return this.scope;
    }

    public final boolean isCancelled() {
        return this.job.isCancelled();
    }

    public final boolean isActive() {
        return this.job.isActive();
    }

    public final boolean isCompleted() {
        return this.job.isCompleted();
    }

    @NotNull
    public final Coroutine<T> timeout(@NotNull Function0<Long> timeMillis) {
        Intrinsics.checkNotNullParameter(timeMillis, (String)"timeMillis");
        this.timeMillis = (Long)timeMillis.invoke();
        return this;
    }

    @NotNull
    public final Coroutine<T> timeout(long timeMillis) {
        this.timeMillis = timeMillis;
        return this;
    }

    @NotNull
    public final Coroutine<T> onErrorReturn(@NotNull Function0<? extends T> value) {
        Intrinsics.checkNotNullParameter(value, (String)"value");
        this.errorReturn = new Result(value.invoke());
        return this;
    }

    @NotNull
    public final Coroutine<T> onErrorReturn(@Nullable T value) {
        this.errorReturn = new Result(value);
        return this;
    }

    @NotNull
    public final Coroutine<T> onStart(@Nullable CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, (String)"block");
        this.start = new VoidCallback(this, context, block);
        return this;
    }

    public static /* synthetic */ Coroutine onStart$default(Coroutine coroutine, CoroutineContext coroutineContext, Function2 function2, int n, Object object) {
        if ((n & 1) != 0) {
            coroutineContext = null;
        }
        return coroutine.onStart(coroutineContext, function2);
    }

    @NotNull
    public final Coroutine<T> onSuccess(@Nullable CoroutineContext context, @NotNull Function3<? super CoroutineScope, ? super T, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, (String)"block");
        this.success = new Callback(this, context, block);
        return this;
    }

    public static /* synthetic */ Coroutine onSuccess$default(Coroutine coroutine, CoroutineContext coroutineContext, Function3 function3, int n, Object object) {
        if ((n & 1) != 0) {
            coroutineContext = null;
        }
        return coroutine.onSuccess(coroutineContext, function3);
    }

    @NotNull
    public final Coroutine<T> onError(@Nullable CoroutineContext context, @NotNull Function3<? super CoroutineScope, ? super Throwable, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, (String)"block");
        this.error = new Callback(this, context, block);
        return this;
    }

    public static /* synthetic */ Coroutine onError$default(Coroutine coroutine, CoroutineContext coroutineContext, Function3 function3, int n, Object object) {
        if ((n & 1) != 0) {
            coroutineContext = null;
        }
        return coroutine.onError(coroutineContext, function3);
    }

    @NotNull
    public final Coroutine<T> onFinally(@Nullable CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, (String)"block");
        this.finally = new VoidCallback(this, context, block);
        return this;
    }

    public static /* synthetic */ Coroutine onFinally$default(Coroutine coroutine, CoroutineContext coroutineContext, Function2 function2, int n, Object object) {
        if ((n & 1) != 0) {
            coroutineContext = null;
        }
        return coroutine.onFinally(coroutineContext, function2);
    }

    @NotNull
    public final Coroutine<T> onCancel(@Nullable CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, (String)"block");
        this.cancel = new VoidCallback(this, context, block);
        return this;
    }

    public static /* synthetic */ Coroutine onCancel$default(Coroutine coroutine, CoroutineContext coroutineContext, Function2 function2, int n, Object object) {
        if ((n & 1) != 0) {
            coroutineContext = null;
        }
        return coroutine.onCancel(coroutineContext, function2);
    }

    public final void cancel(@Nullable CancellationException cause) {
        this.job.cancel(cause);
        VoidCallback voidCallback = this.cancel;
        if (voidCallback != null) {
            VoidCallback voidCallback2 = voidCallback;
            boolean bl = false;
            boolean bl2 = false;
            VoidCallback it = voidCallback2;
            boolean bl3 = false;
            BuildersKt.launch$default((CoroutineScope)CoroutineScopeKt.MainScope(), null, null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)3, null);
        }
    }

    public static /* synthetic */ void cancel$default(Coroutine coroutine, CancellationException cancellationException, int n, Object object) {
        if ((n & 1) != 0) {
            cancellationException = null;
        }
        coroutine.cancel(cancellationException);
    }

    @NotNull
    public final DisposableHandle invokeOnCompletion(@NotNull Function1<? super Throwable, Unit> handler2) {
        Intrinsics.checkNotNullParameter(handler2, (String)"handler");
        return this.job.invokeOnCompletion(handler2);
    }

    private final Job executeInternal(CoroutineContext context, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        return BuildersKt.launch$default((CoroutineScope)CoroutineScopeKt.plus((CoroutineScope)this.scope, (CoroutineContext)((CoroutineContext)Dispatchers.getIO())), null, null, (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), (int)3, null);
    }

    private final Object dispatchVoidCallback(CoroutineScope scope, VoidCallback callback, Continuation<? super Unit> $completion) {
        boolean $i$f$dispatchVoidCallback = false;
        if (callback.getContext() == null) {
            Function2 function2 = callback.getBlock();
            InlineMarker.mark((int)0);
            function2.invoke((Object)scope, $completion);
            InlineMarker.mark((int)1);
            return Unit.INSTANCE;
        }
        CoroutineContext coroutineContext = scope.getCoroutineContext().plus(callback.getContext());
        Function2 function2 = (Function2)new /* Unavailable Anonymous Inner Class!! */;
        InlineMarker.mark((int)0);
        BuildersKt.withContext((CoroutineContext)coroutineContext, (Function2)function2, $completion);
        InlineMarker.mark((int)1);
        return Unit.INSTANCE;
    }

    private final <R> Object dispatchCallback(CoroutineScope scope, R value, Callback<R> callback, Continuation<? super Unit> $completion) {
        boolean $i$f$dispatchCallback = false;
        if (!CoroutineScopeKt.isActive((CoroutineScope)scope)) {
            return Unit.INSTANCE;
        }
        if (callback.getContext() == null) {
            Function3 function3 = callback.getBlock();
            InlineMarker.mark((int)0);
            function3.invoke((Object)scope, value, $completion);
            InlineMarker.mark((int)1);
            return Unit.INSTANCE;
        }
        CoroutineContext coroutineContext = scope.getCoroutineContext().plus(callback.getContext());
        Function2 function2 = (Function2)new /* Unavailable Anonymous Inner Class!! */;
        InlineMarker.mark((int)0);
        BuildersKt.withContext((CoroutineContext)coroutineContext, (Function2)function2, $completion);
        InlineMarker.mark((int)1);
        return Unit.INSTANCE;
    }

    private final Object executeBlock(CoroutineScope scope, CoroutineContext context, long timeMillis, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        boolean $i$f$executeBlock = false;
        CoroutineContext coroutineContext = scope.getCoroutineContext().plus(context);
        Function2 function2 = (Function2)new /* Unavailable Anonymous Inner Class!! */;
        InlineMarker.mark((int)0);
        Object object = BuildersKt.withContext((CoroutineContext)coroutineContext, (Function2)function2, $completion);
        InlineMarker.mark((int)1);
        return object;
    }

    public static final /* synthetic */ CoroutineScope access$getDEFAULT$cp() {
        return DEFAULT;
    }

    public static final /* synthetic */ VoidCallback access$getStart$p(Coroutine $this) {
        return $this.start;
    }

    public static final /* synthetic */ Long access$getTimeMillis$p(Coroutine $this) {
        return $this.timeMillis;
    }

    public static final /* synthetic */ Callback access$getSuccess$p(Coroutine $this) {
        return $this.success;
    }

    public static final /* synthetic */ Result access$getErrorReturn$p(Coroutine $this) {
        return $this.errorReturn;
    }

    public static final /* synthetic */ Callback access$getError$p(Coroutine $this) {
        return $this.error;
    }

    public static final /* synthetic */ VoidCallback access$getFinally$p(Coroutine $this) {
        return $this.finally;
    }
}

