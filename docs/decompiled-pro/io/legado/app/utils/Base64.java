/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.Base64
 *  io.legado.app.utils.Base64$Decoder
 *  io.legado.app.utils.Base64$Encoder
 */
package io.legado.app.utils;

import io.legado.app.utils.Base64;
import java.io.UnsupportedEncodingException;

/*
 * Exception performing whole class analysis ignored.
 */
public class Base64 {
    public static final int DEFAULT = 0;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int CRLF = 4;
    public static final int URL_SAFE = 8;
    public static final int NO_CLOSE = 16;

    public static byte[] decode(String str, int flags) {
        return Base64.decode((byte[])str.getBytes(), (int)flags);
    }

    public static byte[] decode(byte[] input, int flags) {
        return Base64.decode((byte[])input, (int)0, (int)input.length, (int)flags);
    }

    public static byte[] decode(byte[] input, int offset, int len, int flags) {
        Decoder decoder = new Decoder(flags, new byte[len * 3 / 4]);
        if (!decoder.process(input, offset, len, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (decoder.op == decoder.output.length) {
            return decoder.output;
        }
        byte[] temp = new byte[decoder.op];
        System.arraycopy(decoder.output, 0, temp, 0, decoder.op);
        return temp;
    }

    public static String encodeToString(byte[] input, int flags) {
        try {
            return new String(Base64.encode((byte[])input, (int)flags), "US-ASCII");
        }
        catch (UnsupportedEncodingException e) {
            throw new AssertionError((Object)e);
        }
    }

    public static String encodeToString(byte[] input, int offset, int len, int flags) {
        try {
            return new String(Base64.encode((byte[])input, (int)offset, (int)len, (int)flags), "US-ASCII");
        }
        catch (UnsupportedEncodingException e) {
            throw new AssertionError((Object)e);
        }
    }

    public static byte[] encode(byte[] input, int flags) {
        return Base64.encode((byte[])input, (int)0, (int)input.length, (int)flags);
    }

    public static byte[] encode(byte[] input, int offset, int len, int flags) {
        Encoder encoder = new Encoder(flags, null);
        int output_len = len / 3 * 4;
        if (encoder.do_padding) {
            if (len % 3 > 0) {
                output_len += 4;
            }
        } else {
            switch (len % 3) {
                case 0: {
                    break;
                }
                case 1: {
                    output_len += 2;
                    break;
                }
                case 2: {
                    output_len += 3;
                }
            }
        }
        if (encoder.do_newline && len > 0) {
            output_len += ((len - 1) / 57 + 1) * (encoder.do_cr ? 2 : 1);
        }
        encoder.output = new byte[output_len];
        encoder.process(input, offset, len, true);
        assert (encoder.op == output_len);
        return encoder.output;
    }

    private Base64() {
    }
}

