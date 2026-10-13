/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.help.http.SSLHelper
 *  io.legado.app.help.http.SSLHelper$SSLParams
 *  io.legado.app.help.http.SSLHelper$unsafeSSLSocketFactory$2
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.help.http;

import io.legado.app.help.http.SSLHelper;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001)B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0015\u001a\u00020\u00122\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0002\u00a2\u0006\u0002\u0010\u0019J \u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0012J1\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0012\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\u0017\"\u00020\u001b\u00a2\u0006\u0002\u0010 J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001e\u001a\u00020\u0012J!\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0012\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\u0017\"\u00020\u001b\u00a2\u0006\u0002\u0010!JA\u0010\"\u001a\u0004\u0018\u00010\u00042\b\u0010\u001e\u001a\u0004\u0018\u00010\u00122\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0012\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\u0017\"\u00020\u001bH\u0002\u00a2\u0006\u0002\u0010#J)\u0010$\u001a\n\u0012\u0004\u0012\u00020%\u0018\u00010\u00172\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0002\u00a2\u0006\u0002\u0010&J'\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0012\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\u0017\"\u00020\u001bH\u0002\u00a2\u0006\u0002\u0010(R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001b\u0010\u000b\u001a\u00020\f8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006*"}, d2={"Lio/legado/app/help/http/SSLHelper;", "", "()V", "sslSocketFactory", "Lio/legado/app/help/http/SSLHelper$SSLParams;", "getSslSocketFactory", "()Lio/legado/app/help/http/SSLHelper$SSLParams;", "unsafeHostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "getUnsafeHostnameVerifier", "()Ljavax/net/ssl/HostnameVerifier;", "unsafeSSLSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "getUnsafeSSLSocketFactory", "()Ljavax/net/ssl/SSLSocketFactory;", "unsafeSSLSocketFactory$delegate", "Lkotlin/Lazy;", "unsafeTrustManager", "Ljavax/net/ssl/X509TrustManager;", "getUnsafeTrustManager", "()Ljavax/net/ssl/X509TrustManager;", "chooseTrustManager", "trustManagers", "", "Ljavax/net/ssl/TrustManager;", "([Ljavax/net/ssl/TrustManager;)Ljavax/net/ssl/X509TrustManager;", "bksFile", "Ljava/io/InputStream;", "password", "", "trustManager", "certificates", "(Ljava/io/InputStream;Ljava/lang/String;[Ljava/io/InputStream;)Lio/legado/app/help/http/SSLHelper$SSLParams;", "([Ljava/io/InputStream;)Lio/legado/app/help/http/SSLHelper$SSLParams;", "getSslSocketFactoryBase", "(Ljavax/net/ssl/X509TrustManager;Ljava/io/InputStream;Ljava/lang/String;[Ljava/io/InputStream;)Lio/legado/app/help/http/SSLHelper$SSLParams;", "prepareKeyManager", "Ljavax/net/ssl/KeyManager;", "(Ljava/io/InputStream;Ljava/lang/String;)[Ljavax/net/ssl/KeyManager;", "prepareTrustManager", "([Ljava/io/InputStream;)[Ljavax/net/ssl/TrustManager;", "SSLParams", "reader-pro"})
public final class SSLHelper {
    @NotNull
    public static final SSLHelper INSTANCE = new SSLHelper();
    @NotNull
    private static final X509TrustManager unsafeTrustManager = (X509TrustManager)new /* Unavailable Anonymous Inner Class!! */;
    @NotNull
    private static final Lazy unsafeSSLSocketFactory$delegate = LazyKt.lazy((Function0)((Function0)unsafeSSLSocketFactory.2.INSTANCE));
    @NotNull
    private static final HostnameVerifier unsafeHostnameVerifier = SSLHelper::unsafeHostnameVerifier$lambda-0;

    private SSLHelper() {
    }

    @Nullable
    public final SSLParams getSslSocketFactory() {
        return this.getSslSocketFactoryBase(null, null, null, new InputStream[0]);
    }

    @NotNull
    public final X509TrustManager getUnsafeTrustManager() {
        return unsafeTrustManager;
    }

    @NotNull
    public final SSLSocketFactory getUnsafeSSLSocketFactory() {
        Lazy lazy = unsafeSSLSocketFactory$delegate;
        boolean bl = false;
        Object object = lazy.getValue();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"<get-unsafeSSLSocketFactory>(...)");
        return (SSLSocketFactory)object;
    }

    @NotNull
    public final HostnameVerifier getUnsafeHostnameVerifier() {
        return unsafeHostnameVerifier;
    }

    @Nullable
    public final SSLParams getSslSocketFactory(@NotNull X509TrustManager trustManager) {
        Intrinsics.checkNotNullParameter((Object)trustManager, (String)"trustManager");
        return this.getSslSocketFactoryBase(trustManager, null, null, new InputStream[0]);
    }

    @Nullable
    public final SSLParams getSslSocketFactory(InputStream ... certificates) {
        Intrinsics.checkNotNullParameter((Object)certificates, (String)"certificates");
        return this.getSslSocketFactoryBase(null, null, null, Arrays.copyOf(certificates, certificates.length));
    }

    @Nullable
    public final SSLParams getSslSocketFactory(@NotNull InputStream bksFile, @NotNull String password, InputStream ... certificates) {
        Intrinsics.checkNotNullParameter((Object)bksFile, (String)"bksFile");
        Intrinsics.checkNotNullParameter((Object)password, (String)"password");
        Intrinsics.checkNotNullParameter((Object)certificates, (String)"certificates");
        return this.getSslSocketFactoryBase(null, bksFile, password, Arrays.copyOf(certificates, certificates.length));
    }

    @Nullable
    public final SSLParams getSslSocketFactory(@NotNull InputStream bksFile, @NotNull String password, @NotNull X509TrustManager trustManager) {
        Intrinsics.checkNotNullParameter((Object)bksFile, (String)"bksFile");
        Intrinsics.checkNotNullParameter((Object)password, (String)"password");
        Intrinsics.checkNotNullParameter((Object)trustManager, (String)"trustManager");
        return this.getSslSocketFactoryBase(trustManager, bksFile, password, new InputStream[0]);
    }

    private final SSLParams getSslSocketFactoryBase(X509TrustManager trustManager, InputStream bksFile, String password, InputStream ... certificates) {
        SSLParams sslParams = new SSLParams();
        try {
            KeyManager[] keyManagers = this.prepareKeyManager(bksFile, password);
            TrustManager[] trustManagers = this.prepareTrustManager(Arrays.copyOf(certificates, certificates.length));
            X509TrustManager x509TrustManager = trustManager;
            X509TrustManager manager = x509TrustManager == null ? this.chooseTrustManager(trustManagers) : x509TrustManager;
            SSLContext sslContext = SSLContext.getInstance("TLS");
            Object object = new TrustManager[]{manager};
            sslContext.init(keyManagers, (TrustManager[])object, null);
            object = sslContext.getSocketFactory();
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"sslContext.socketFactory");
            sslParams.setSSLSocketFactory((SSLSocketFactory)object);
            sslParams.setTrustManager(manager);
            return sslParams;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
        try {
            if (bksFile == null || password == null) {
                return null;
            }
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            String string = password;
            boolean bl = false;
            char[] cArray = string.toCharArray();
            Intrinsics.checkNotNullExpressionValue((Object)cArray, (String)"(this as java.lang.String).toCharArray()");
            clientKeyStore.load(bksFile, cArray);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            String string2 = password;
            boolean bl2 = false;
            char[] cArray2 = string2.toCharArray();
            Intrinsics.checkNotNullExpressionValue((Object)cArray2, (String)"(this as java.lang.String).toCharArray()");
            kmf.init(clientKeyStore, cArray2);
            return kmf.getKeyManagers();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private final TrustManager[] prepareTrustManager(InputStream ... certificates) {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        InputStream[] inputStreamArray = certificates;
        int n = 0;
        int n2 = inputStreamArray.length;
        while (n < n2) {
            int index = n;
            InputStream certStream = inputStreamArray[n];
            ++n;
            String certificateAlias = Integer.toString(index);
            Certificate cert = certificateFactory.generateCertificate(certStream);
            keyStore.setCertificateEntry(certificateAlias, cert);
            try {
                certStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
        TrustManager[] trustManagerArray = tmf.getTrustManagers();
        Intrinsics.checkNotNullExpressionValue((Object)trustManagerArray, (String)"tmf.trustManagers");
        return trustManagerArray;
    }

    private final X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (!(trustManager instanceof X509TrustManager)) continue;
            return (X509TrustManager)trustManager;
        }
        throw new NullPointerException();
    }

    private static final boolean unsafeHostnameVerifier$lambda-0(String $noName_0, SSLSession $noName_1) {
        return true;
    }
}

