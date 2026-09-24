/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.data.entities.TxtTocRule
 *  io.legado.app.help.DefaultData
 *  io.legado.app.help.DefaultData$txtTocRules$2
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  org.jetbrains.annotations.NotNull
 */
package io.legado.app.help;

import io.legado.app.data.entities.TxtTocRule;
import io.legado.app.help.DefaultData;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lio/legado/app/help/DefaultData;", "", "()V", "txtTocRuleFileName", "", "txtTocRules", "", "Lio/legado/app/data/entities/TxtTocRule;", "getTxtTocRules", "()Ljava/util/List;", "txtTocRules$delegate", "Lkotlin/Lazy;", "reader-pro"})
public final class DefaultData {
    @NotNull
    public static final DefaultData INSTANCE = new DefaultData();
    @NotNull
    public static final String txtTocRuleFileName = "txtTocRule.json";
    @NotNull
    private static final Lazy txtTocRules$delegate = LazyKt.lazy((Function0)((Function0)txtTocRules.2.INSTANCE));

    private DefaultData() {
    }

    @NotNull
    public final List<TxtTocRule> getTxtTocRules() {
        Lazy lazy = txtTocRules$delegate;
        boolean bl = false;
        return (List)lazy.getValue();
    }
}

