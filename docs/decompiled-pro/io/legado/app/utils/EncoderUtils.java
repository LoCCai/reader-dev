/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.Base64
 *  io.legado.app.utils.EncoderUtils
 *  kotlin.Metadata
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.CharsKt
 *  kotlin.text.Charsets
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.utils;

import io.legado.app.utils.Base64;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007J\u001c\u0010\b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007J2\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\r\u001a\u00020\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ2\u0010\u000f\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\r\u001a\u00020\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ2\u0010\u0010\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\r\u001a\u00020\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ2\u0010\u0011\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\r\u001a\u00020\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ\u0016\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0018J2\u0010\u0019\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\r\u001a\u00020\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ2\u0010\u001a\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\r\u001a\u00020\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ\"\u0010\u001b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u0007J\"\u0010\u001d\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u001c\u001a\u00020\u0007J4\u0010\u001e\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ4\u0010\u001f\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ\u0016\u0010 \u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010!\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0018J4\u0010\"\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ4\u0010#\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ4\u0010$\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ4\u0010%\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\nJ \u0010&\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u0007J \u0010'\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u001c\u001a\u00020\u0007J\u000e\u0010(\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u0004J\u0006\u0010*\u001a\u00020+J@\u0010,\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010-\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\n2\u0006\u0010.\u001a\u00020/H\u0002\u00a8\u00060"}, d2={"Lio/legado/app/utils/EncoderUtils;", "", "()V", "base64Decode", "", "str", "flags", "", "base64Encode", "decryptAES", "", "data", "key", "transformation", "iv", "decryptBase64AES", "decryptBase64DES", "decryptBase64DESede", "decryptByPrivateKey", "input", "privateKey", "Ljava/security/PrivateKey;", "decryptByPublicKey", "publicKey", "Ljava/security/PublicKey;", "decryptDES", "decryptDESede", "decryptSegmentByPrivateKey", "keySize", "decryptSegmentByPublicKey", "encryptAES", "encryptAES2Base64", "encryptByPrivateKey", "encryptByPublicKey", "encryptDES", "encryptDES2Base64", "encryptDESede", "encryptDESede2Base64", "encryptSegmentByPrivateKey", "encryptSegmentByPublicKey", "escape", "src", "generateKeys", "Ljava/security/KeyPair;", "symmetricTemplate", "algorithm", "isEncrypt", "", "reader-pro"})
public final class EncoderUtils {
    @NotNull
    public static final EncoderUtils INSTANCE = new EncoderUtils();

    private EncoderUtils() {
    }

    @NotNull
    public final String escape(@NotNull String src) {
        Intrinsics.checkNotNullParameter((Object)src, (String)"src");
        StringBuilder tmp = new StringBuilder();
        String string = src;
        int n = 0;
        int n2 = string.length();
        while (n < n2) {
            char charCode;
            char c;
            block5: {
                char c2;
                block4: {
                    c2 = string.charAt(n);
                    ++n;
                    char c3 = c2;
                    c = '\u0000';
                    charCode = c3;
                    if ('0' <= charCode ? charCode <= '9' : false) break block4;
                    if ('A' <= charCode ? charCode <= 'Z' : false) break block4;
                    boolean bl = 'a' <= charCode ? charCode <= 'z' : false;
                    if (!bl) break block5;
                }
                tmp.append(c2);
                continue;
            }
            String prefix = charCode < '\u0010' ? "%0" : (charCode < '\u0100' ? "%" : "%u");
            StringBuilder stringBuilder = tmp.append(prefix);
            c = charCode;
            int n3 = 16;
            boolean bl = false;
            String string2 = Integer.toString(c, CharsKt.checkRadix((int)n3));
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"java.lang.Integer.toStri\u2026(this, checkRadix(radix))");
            stringBuilder.append(string2);
        }
        string = tmp.toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"tmp.toString()");
        return string;
    }

    @JvmOverloads
    @NotNull
    public final String base64Decode(@NotNull String str, int flags) {
        Intrinsics.checkNotNullParameter((Object)str, (String)"str");
        byte[] bytes2 = Base64.decode((String)str, (int)flags);
        Intrinsics.checkNotNullExpressionValue((Object)bytes2, (String)"bytes");
        byte[] byArray = bytes2;
        boolean bl = false;
        return new String(byArray, Charsets.UTF_8);
    }

    public static /* synthetic */ String base64Decode$default(EncoderUtils encoderUtils, String string, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        return encoderUtils.base64Decode(string, n);
    }

    @JvmOverloads
    @Nullable
    public final String base64Encode(@NotNull String str, int flags) {
        Intrinsics.checkNotNullParameter((Object)str, (String)"str");
        String string = str;
        Charset charset = Charsets.UTF_8;
        boolean bl = false;
        byte[] byArray = string.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"(this as java.lang.String).getBytes(charset)");
        return Base64.encodeToString((byte[])byArray, (int)flags);
    }

    public static /* synthetic */ String base64Encode$default(EncoderUtils encoderUtils, String string, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 2;
        }
        return encoderUtils.base64Encode(string, n);
    }

    @Nullable
    public final byte[] encryptAES2Base64(@Nullable byte[] data, @Nullable byte[] key, @Nullable String transformation, @Nullable byte[] iv) throws Exception {
        return Base64.encode((byte[])this.encryptAES(data, key, transformation, iv), (int)2);
    }

    public static /* synthetic */ byte[] encryptAES2Base64$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DES/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.encryptAES2Base64(byArray, byArray2, string, byArray3);
    }

    @Nullable
    public final byte[] encryptAES(@Nullable byte[] data, @Nullable byte[] key, @Nullable String transformation, @Nullable byte[] iv) throws Exception {
        String string = transformation;
        Intrinsics.checkNotNull((Object)string);
        return this.symmetricTemplate(data, key, "AES", string, iv, true);
    }

    public static /* synthetic */ byte[] encryptAES$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DES/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.encryptAES(byArray, byArray2, string, byArray3);
    }

    @Nullable
    public final byte[] decryptBase64AES(@Nullable byte[] data, @Nullable byte[] key, @NotNull String transformation, @Nullable byte[] iv) throws Exception {
        Intrinsics.checkNotNullParameter((Object)transformation, (String)"transformation");
        return this.decryptAES(Base64.decode((byte[])data, (int)2), key, transformation, iv);
    }

    public static /* synthetic */ byte[] decryptBase64AES$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DES/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.decryptBase64AES(byArray, byArray2, string, byArray3);
    }

    @Nullable
    public final byte[] decryptAES(@Nullable byte[] data, @Nullable byte[] key, @NotNull String transformation, @Nullable byte[] iv) throws Exception {
        Intrinsics.checkNotNullParameter((Object)transformation, (String)"transformation");
        return this.symmetricTemplate(data, key, "AES", transformation, iv, false);
    }

    public static /* synthetic */ byte[] decryptAES$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DES/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.decryptAES(byArray, byArray2, string, byArray3);
    }

    /*
     * Unable to fully structure code
     */
    private final byte[] symmetricTemplate(byte[] data, byte[] key, String algorithm, String transformation, byte[] iv, boolean isEncrypt) throws Exception {
        block4: {
            block3: {
                block2: {
                    if (data == null) break block2;
                    var7_7 = data;
                    var8_8 = false;
                    if (var7_7.length == 0 || key == null) break block2;
                    var7_7 = key;
                    var8_8 = false;
                    if (!(var7_7.length == 0)) break block3;
                }
                v0 = null;
                break block4;
            }
            keySpec = new SecretKeySpec(key, algorithm);
            cipher = Cipher.getInstance(transformation);
            v1 = mode = isEncrypt != false ? 1 : 2;
            if (iv == null) ** GOTO lbl-1000
            var10_11 = iv;
            var11_12 = false;
            if (var10_11.length == 0) lbl-1000:
            // 2 sources

            {
                cipher.init(mode, keySpec);
            } else {
                params = new IvParameterSpec(iv);
                cipher.init(mode, (Key)keySpec, params);
            }
            v0 = cipher.doFinal(data);
        }
        return v0;
    }

    @Nullable
    public final byte[] encryptDES2Base64(@Nullable byte[] data, @Nullable byte[] key, @Nullable String transformation, @Nullable byte[] iv) throws Exception {
        return Base64.encode((byte[])this.encryptDES(data, key, transformation, iv), (int)2);
    }

    public static /* synthetic */ byte[] encryptDES2Base64$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DES/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.encryptDES2Base64(byArray, byArray2, string, byArray3);
    }

    @Nullable
    public final byte[] encryptDES(@Nullable byte[] data, @Nullable byte[] key, @Nullable String transformation, @Nullable byte[] iv) throws Exception {
        String string = transformation;
        Intrinsics.checkNotNull((Object)string);
        return this.symmetricTemplate(data, key, "DES", string, iv, true);
    }

    public static /* synthetic */ byte[] encryptDES$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DES/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.encryptDES(byArray, byArray2, string, byArray3);
    }

    @Nullable
    public final byte[] decryptBase64DES(@Nullable byte[] data, @Nullable byte[] key, @NotNull String transformation, @Nullable byte[] iv) throws Exception {
        Intrinsics.checkNotNullParameter((Object)transformation, (String)"transformation");
        return this.decryptDES(Base64.decode((byte[])data, (int)2), key, transformation, iv);
    }

    public static /* synthetic */ byte[] decryptBase64DES$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DES/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.decryptBase64DES(byArray, byArray2, string, byArray3);
    }

    @Nullable
    public final byte[] decryptDES(@Nullable byte[] data, @Nullable byte[] key, @NotNull String transformation, @Nullable byte[] iv) throws Exception {
        Intrinsics.checkNotNullParameter((Object)transformation, (String)"transformation");
        return this.symmetricTemplate(data, key, "DES", transformation, iv, false);
    }

    public static /* synthetic */ byte[] decryptDES$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DES/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.decryptDES(byArray, byArray2, string, byArray3);
    }

    @Nullable
    public final byte[] encryptDESede2Base64(@Nullable byte[] data, @Nullable byte[] key, @Nullable String transformation, @Nullable byte[] iv) throws Exception {
        return Base64.encode((byte[])this.encryptDESede(data, key, transformation, iv), (int)2);
    }

    public static /* synthetic */ byte[] encryptDESede2Base64$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DESede/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.encryptDESede2Base64(byArray, byArray2, string, byArray3);
    }

    @Nullable
    public final byte[] encryptDESede(@Nullable byte[] data, @Nullable byte[] key, @Nullable String transformation, @Nullable byte[] iv) throws Exception {
        String string = transformation;
        Intrinsics.checkNotNull((Object)string);
        return this.symmetricTemplate(data, key, "DESede", string, iv, true);
    }

    public static /* synthetic */ byte[] encryptDESede$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DESede/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.encryptDESede(byArray, byArray2, string, byArray3);
    }

    @Nullable
    public final byte[] decryptBase64DESede(@Nullable byte[] data, @Nullable byte[] key, @NotNull String transformation, @Nullable byte[] iv) throws Exception {
        Intrinsics.checkNotNullParameter((Object)transformation, (String)"transformation");
        return this.decryptDESede(Base64.decode((byte[])data, (int)2), key, transformation, iv);
    }

    public static /* synthetic */ byte[] decryptBase64DESede$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DESede/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.decryptBase64DESede(byArray, byArray2, string, byArray3);
    }

    @Nullable
    public final byte[] decryptDESede(@Nullable byte[] data, @Nullable byte[] key, @NotNull String transformation, @Nullable byte[] iv) throws Exception {
        Intrinsics.checkNotNullParameter((Object)transformation, (String)"transformation");
        return this.symmetricTemplate(data, key, "DESede", transformation, iv, false);
    }

    public static /* synthetic */ byte[] decryptDESede$default(EncoderUtils encoderUtils, byte[] byArray, byte[] byArray2, String string, byte[] byArray3, int n, Object object) throws Exception {
        if ((n & 4) != 0) {
            string = "DESede/ECB/PKCS5Padding";
        }
        if ((n & 8) != 0) {
            byArray3 = null;
        }
        return encoderUtils.decryptDESede(byArray, byArray2, string, byArray3);
    }

    @NotNull
    public final String encryptByPrivateKey(@NotNull String input, @NotNull PrivateKey privateKey) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter((Object)privateKey, (String)"privateKey");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, privateKey);
        String string = input;
        Charset charset = Charsets.UTF_8;
        boolean bl = false;
        byte[] byArray = string.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"(this as java.lang.String).getBytes(charset)");
        byte[] encrypt = cipher.doFinal(byArray);
        string = Base64.encodeToString((byte[])encrypt, (int)2);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"encodeToString(encrypt, Base64.NO_WRAP)");
        return string;
    }

    @NotNull
    public final String decryptByPublicKey(@NotNull String input, @NotNull PublicKey publicKey) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter((Object)publicKey, (String)"publicKey");
        byte[] decode = Base64.decode((String)input, (int)2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, publicKey);
        byte[] encrypt = cipher.doFinal(decode);
        Intrinsics.checkNotNullExpressionValue((Object)encrypt, (String)"encrypt");
        byte[] byArray = encrypt;
        boolean bl = false;
        return new String(byArray, Charsets.UTF_8);
    }

    @NotNull
    public final String encryptByPublicKey(@NotNull String input, @NotNull PublicKey publicKey) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter((Object)publicKey, (String)"publicKey");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, publicKey);
        String string = input;
        Charset charset = Charsets.UTF_8;
        boolean bl = false;
        byte[] byArray = string.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"(this as java.lang.String).getBytes(charset)");
        byte[] encrypt = cipher.doFinal(byArray);
        string = Base64.encodeToString((byte[])encrypt, (int)2);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"encodeToString(encrypt, Base64.NO_WRAP)");
        return string;
    }

    @NotNull
    public final String decryptByPrivateKey(@NotNull String input, @NotNull PrivateKey privateKey) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter((Object)privateKey, (String)"privateKey");
        byte[] decode = Base64.decode((String)input, (int)2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, privateKey);
        byte[] encrypt = cipher.doFinal(decode);
        Intrinsics.checkNotNullExpressionValue((Object)encrypt, (String)"encrypt");
        byte[] byArray = encrypt;
        boolean bl = false;
        return new String(byArray, Charsets.UTF_8);
    }

    @NotNull
    public final String encryptSegmentByPrivateKey(@NotNull String input, @NotNull PrivateKey privateKey, int keySize) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter((Object)privateKey, (String)"privateKey");
        String string = input;
        Charset charset = Charsets.UTF_8;
        boolean bl = false;
        byte[] byArray = string.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"(this as java.lang.String).getBytes(charset)");
        byte[] byteArray2 = byArray;
        Object temp = null;
        int offset = 0;
        int MAX_ENCRYPT_BLOCK = keySize / 8 - 11;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, privateKey);
        while (byteArray2.length - offset > 0) {
            if (byteArray2.length - offset >= MAX_ENCRYPT_BLOCK) {
                object = cipher.doFinal(byteArray2, offset, MAX_ENCRYPT_BLOCK);
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"cipher.doFinal(byteArray, offset, MAX_ENCRYPT_BLOCK)");
                temp = object;
                offset += MAX_ENCRYPT_BLOCK;
            } else {
                object = cipher.doFinal(byteArray2, offset, byteArray2.length - offset);
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"cipher.doFinal(byteArray, offset, byteArray.size - offset)");
                temp = object;
                offset = byteArray2.length;
            }
            bos.write((byte[])temp);
        }
        bos.close();
        object = Base64.encodeToString((byte[])bos.toByteArray(), (int)2);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"encodeToString(bos.toByteArray(), Base64.NO_WRAP)");
        return object;
    }

    public static /* synthetic */ String encryptSegmentByPrivateKey$default(EncoderUtils encoderUtils, String string, PrivateKey privateKey, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 2048;
        }
        return encoderUtils.encryptSegmentByPrivateKey(string, privateKey, n);
    }

    @Nullable
    public final String decryptSegmentByPublicKey(@NotNull String input, @NotNull PublicKey publicKey, int keySize) {
        byte[] byArray;
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter((Object)publicKey, (String)"publicKey");
        byte[] byteArray2 = Base64.decode((String)input, (int)2);
        byte[] temp = null;
        int offset = 0;
        int MAX_DECRYPT_BLOCK = keySize / 8;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, publicKey);
        while (byteArray2.length - offset > 0) {
            if (byteArray2.length - offset >= MAX_DECRYPT_BLOCK) {
                byArray = cipher.doFinal(byteArray2, offset, MAX_DECRYPT_BLOCK);
                Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"cipher.doFinal(byteArray, offset, MAX_DECRYPT_BLOCK)");
                temp = byArray;
                offset += MAX_DECRYPT_BLOCK;
            } else {
                byArray = cipher.doFinal(byteArray2, offset, byteArray2.length - offset);
                Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"cipher.doFinal(byteArray, offset, byteArray.size - offset)");
                temp = byArray;
                offset = byteArray2.length;
            }
            bos.write(temp);
        }
        bos.close();
        byArray = bos.toByteArray();
        Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"bos.toByteArray()");
        boolean bl = false;
        return new String(byArray, Charsets.UTF_8);
    }

    public static /* synthetic */ String decryptSegmentByPublicKey$default(EncoderUtils encoderUtils, String string, PublicKey publicKey, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 2048;
        }
        return encoderUtils.decryptSegmentByPublicKey(string, publicKey, n);
    }

    @NotNull
    public final String encryptSegmentByPublicKey(@NotNull String input, @NotNull PublicKey publicKey, int keySize) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter((Object)publicKey, (String)"publicKey");
        String string = input;
        Charset charset = Charsets.UTF_8;
        boolean bl = false;
        byte[] byArray = string.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"(this as java.lang.String).getBytes(charset)");
        byte[] byteArray2 = byArray;
        Object temp = null;
        int offset = 0;
        int MAX_ENCRYPT_BLOCK = keySize / 8 - 11;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, publicKey);
        while (byteArray2.length - offset > 0) {
            if (byteArray2.length - offset >= MAX_ENCRYPT_BLOCK) {
                object = cipher.doFinal(byteArray2, offset, MAX_ENCRYPT_BLOCK);
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"cipher.doFinal(byteArray, offset, MAX_ENCRYPT_BLOCK)");
                temp = object;
                offset += MAX_ENCRYPT_BLOCK;
            } else {
                object = cipher.doFinal(byteArray2, offset, byteArray2.length - offset);
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"cipher.doFinal(byteArray, offset, byteArray.size - offset)");
                temp = object;
                offset = byteArray2.length;
            }
            bos.write((byte[])temp);
        }
        bos.close();
        object = Base64.encodeToString((byte[])bos.toByteArray(), (int)2);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"encodeToString(bos.toByteArray(), Base64.NO_WRAP)");
        return object;
    }

    public static /* synthetic */ String encryptSegmentByPublicKey$default(EncoderUtils encoderUtils, String string, PublicKey publicKey, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 2048;
        }
        return encoderUtils.encryptSegmentByPublicKey(string, publicKey, n);
    }

    @Nullable
    public final String decryptSegmentByPrivateKey(@NotNull String input, @NotNull PrivateKey privateKey, int keySize) {
        byte[] byArray;
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter((Object)privateKey, (String)"privateKey");
        byte[] byteArray2 = Base64.decode((String)input, (int)2);
        byte[] temp = null;
        int offset = 0;
        int MAX_DECRYPT_BLOCK = keySize / 8;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, privateKey);
        while (byteArray2.length - offset > 0) {
            if (byteArray2.length - offset >= MAX_DECRYPT_BLOCK) {
                byArray = cipher.doFinal(byteArray2, offset, MAX_DECRYPT_BLOCK);
                Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"cipher.doFinal(byteArray, offset, MAX_DECRYPT_BLOCK)");
                temp = byArray;
                offset += MAX_DECRYPT_BLOCK;
            } else {
                byArray = cipher.doFinal(byteArray2, offset, byteArray2.length - offset);
                Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"cipher.doFinal(byteArray, offset, byteArray.size - offset)");
                temp = byArray;
                offset = byteArray2.length;
            }
            bos.write(temp);
        }
        bos.close();
        byArray = bos.toByteArray();
        Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"bos.toByteArray()");
        boolean bl = false;
        return new String(byArray, Charsets.UTF_8);
    }

    public static /* synthetic */ String decryptSegmentByPrivateKey$default(EncoderUtils encoderUtils, String string, PrivateKey privateKey, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 2048;
        }
        return encoderUtils.decryptSegmentByPrivateKey(string, privateKey, n);
    }

    @NotNull
    public final KeyPair generateKeys() {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = generator.genKeyPair();
        Intrinsics.checkNotNullExpressionValue((Object)keyPair, (String)"generator.genKeyPair()");
        return keyPair;
    }

    @JvmOverloads
    @NotNull
    public final String base64Decode(@NotNull String str) {
        Intrinsics.checkNotNullParameter((Object)str, (String)"str");
        return EncoderUtils.base64Decode$default((EncoderUtils)this, (String)str, (int)0, (int)2, null);
    }

    @JvmOverloads
    @Nullable
    public final String base64Encode(@NotNull String str) {
        Intrinsics.checkNotNullParameter((Object)str, (String)"str");
        return EncoderUtils.base64Encode$default((EncoderUtils)this, (String)str, (int)0, (int)2, null);
    }
}

