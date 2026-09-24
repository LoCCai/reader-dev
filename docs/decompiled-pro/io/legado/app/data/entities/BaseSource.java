/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.script.SimpleBindings
 *  io.legado.app.data.entities.BaseSource
 *  io.legado.app.help.JsExtensions
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.data.entities;

import com.script.SimpleBindings;
import io.legado.app.help.JsExtensions;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u000f\bf\u0018\u00002\u00020\u0001J-\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u00032\u0019\b\u0002\u0010\u001a\u001a\u0013\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d0\u001b\u00a2\u0006\u0002\b\u001eH\u0016J.\u0010\u001f\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030 j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003`!2\b\b\u0002\u0010\"\u001a\u00020\tH\u0016J\b\u0010#\u001a\u00020\u0003H&J\n\u0010$\u001a\u0004\u0018\u00010\u0003H\u0016J\u0016\u0010%\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010&H\u0016J\n\u0010'\u001a\u0004\u0018\u00010\u0003H\u0016J\u0016\u0010(\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010&H\u0016J\n\u0010)\u001a\u0004\u0018\u00010\u0003H\u0016J\n\u0010*\u001a\u0004\u0018\u00010\u0000H\u0016J\b\u0010+\u001a\u00020\u0003H&J\n\u0010,\u001a\u0004\u0018\u00010\u0003H\u0016J\b\u0010-\u001a\u00020\u001dH\u0016J\u0010\u0010.\u001a\u00020\u001d2\u0006\u0010\u000e\u001a\u00020\u0003H\u0016J\u0010\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020\u0003H\u0016J\b\u00101\u001a\u00020\u001dH\u0016J\b\u00102\u001a\u00020\u001dH\u0016J\u0012\u00103\u001a\u00020\u001d2\b\u00104\u001a\u0004\u0018\u00010\u0003H\u0016R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007R\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0005\"\u0004\b\u0013\u0010\u0007R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\u0007\u00a8\u00065"}, d2={"Lio/legado/app/data/entities/BaseSource;", "Lio/legado/app/help/JsExtensions;", "concurrentRate", "", "getConcurrentRate", "()Ljava/lang/String;", "setConcurrentRate", "(Ljava/lang/String;)V", "enabledCookieJar", "", "getEnabledCookieJar", "()Ljava/lang/Boolean;", "setEnabledCookieJar", "(Ljava/lang/Boolean;)V", "header", "getHeader", "setHeader", "loginUi", "getLoginUi", "setLoginUi", "loginUrl", "getLoginUrl", "setLoginUrl", "evalJS", "", "jsStr", "bindingsConfig", "Lkotlin/Function1;", "Lcom/script/SimpleBindings;", "", "Lkotlin/ExtensionFunctionType;", "getHeaderMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "hasLoginHeader", "getKey", "getLoginHeader", "getLoginHeaderMap", "", "getLoginInfo", "getLoginInfoMap", "getLoginJs", "getSource", "getTag", "getVariable", "login", "putLoginHeader", "putLoginInfo", "info", "removeLoginHeader", "removeLoginInfo", "setVariable", "variable", "reader-pro"})
public interface BaseSource
extends JsExtensions {
    @Nullable
    public String getConcurrentRate();

    public void setConcurrentRate(@Nullable String var1);

    @Nullable
    public String getLoginUrl();

    public void setLoginUrl(@Nullable String var1);

    @Nullable
    public String getLoginUi();

    public void setLoginUi(@Nullable String var1);

    @Nullable
    public String getHeader();

    public void setHeader(@Nullable String var1);

    @Nullable
    public Boolean getEnabledCookieJar();

    public void setEnabledCookieJar(@Nullable Boolean var1);

    @NotNull
    public String getTag();

    @NotNull
    public String getKey();

    @Nullable
    public BaseSource getSource();

    @Nullable
    public String getLoginJs();

    public void login();

    @NotNull
    public HashMap<String, String> getHeaderMap(boolean var1);

    @Nullable
    public String getLoginHeader();

    @Nullable
    public Map<String, String> getLoginHeaderMap();

    public void putLoginHeader(@NotNull String var1);

    public void removeLoginHeader();

    @Nullable
    public String getLoginInfo();

    @Nullable
    public Map<String, String> getLoginInfoMap();

    public boolean putLoginInfo(@NotNull String var1);

    public void removeLoginInfo();

    public void setVariable(@Nullable String var1);

    @Nullable
    public String getVariable();

    @Nullable
    public Object evalJS(@NotNull String var1, @NotNull Function1<? super SimpleBindings, Unit> var2) throws Exception;
}

