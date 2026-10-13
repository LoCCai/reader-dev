/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.model.analyzeRule.RuleDataInterface
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.model.analyzeRule;

import java.util.HashMap;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\b\u001a\u00020\u0004H&J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0016J\u001a\u0010\u000b\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\u00042\b\u0010\r\u001a\u0004\u0018\u00010\u0004H&R.\u0010\u0002\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`\u0005X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u000e"}, d2={"Lio/legado/app/model/analyzeRule/RuleDataInterface;", "", "variableMap", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "getVariableMap", "()Ljava/util/HashMap;", "getUserNameSpace", "getVariable", "key", "putVariable", "", "value", "reader-pro"})
public interface RuleDataInterface {
    @NotNull
    public HashMap<String, String> getVariableMap();

    public void putVariable(@NotNull String var1, @Nullable String var2);

    @Nullable
    public String getVariable(@NotNull String var1);

    @NotNull
    public String getUserNameSpace();
}

