/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.util.IOUtil
 *  me.ag2s.epublib.util.commons.io.IOConsumer
 */
package me.ag2s.epublib.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import me.ag2s.epublib.util.commons.io.IOConsumer;

/*
 * Exception performing whole class analysis ignored.
 */
public class IOUtil {
    private static final String TAG = IOUtil.class.getName();
    public static final int EOF = -1;
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final byte[] SKIP_BYTE_BUFFER = new byte[8192];
    private static char[] SKIP_CHAR_BUFFER;

    public static byte[] toByteArray(Reader in, String encoding) throws IOException {
        StringWriter out = new StringWriter();
        IOUtil.copy((Reader)in, (Writer)out);
        out.flush();
        return out.toString().getBytes(encoding);
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream result2 = new ByteArrayOutputStream();
        IOUtil.copy((InputStream)in, (OutputStream)result2);
        result2.flush();
        return result2.toByteArray();
    }

    public static byte[] toByteArray(InputStream in, int size) throws IOException {
        try {
            ByteArrayOutputStream result2 = size > 0 ? new ByteArrayOutputStream(size) : new ByteArrayOutputStream();
            IOUtil.copy((InputStream)in, (OutputStream)result2);
            result2.flush();
            return result2.toByteArray();
        }
        catch (OutOfMemoryError error2) {
            return null;
        }
    }

    protected static int calcNewNrReadSize(int nrRead, int totalNrNread) {
        if (totalNrNread < 0) {
            return totalNrNread;
        }
        if (totalNrNread > Integer.MAX_VALUE - nrRead) {
            return -1;
        }
        return totalNrNread + nrRead;
    }

    public static void copy(InputStream in, OutputStream result2) throws IOException {
        IOUtil.copy((InputStream)in, (OutputStream)result2, (int)8192);
    }

    public static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        return IOUtil.copyLarge((InputStream)input, (OutputStream)output, (byte[])new byte[bufferSize]);
    }

    @Deprecated
    public static void copy(InputStream input, Writer output) throws IOException {
        IOUtil.copy((InputStream)input, (Writer)output, (Charset)Charset.defaultCharset());
    }

    public static void copy(InputStream input, Writer output, Charset inputCharset) throws IOException {
        InputStreamReader in = new InputStreamReader(input, inputCharset.name());
        IOUtil.copy((Reader)in, (Writer)output);
    }

    public static void copy(InputStream input, Writer output, String inputCharsetName) throws IOException {
        IOUtil.copy((InputStream)input, (Writer)output, (Charset)Charset.forName(inputCharsetName));
    }

    public static long copy(Reader input, Appendable output) throws IOException {
        return IOUtil.copy((Reader)input, (Appendable)output, (CharBuffer)CharBuffer.allocate(8192));
    }

    public static long copy(Reader input, Appendable output, CharBuffer buffer) throws IOException {
        int n;
        long count = 0L;
        while (-1 != (n = input.read(buffer))) {
            buffer.flip();
            output.append(buffer, 0, n);
            count += (long)n;
        }
        return count;
    }

    @Deprecated
    public static void copy(Reader input, OutputStream output) throws IOException {
        IOUtil.copy((Reader)input, (OutputStream)output, (Charset)Charset.defaultCharset());
    }

    public static void copy(Reader input, OutputStream output, Charset outputCharset) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(output, outputCharset.name());
        IOUtil.copy((Reader)input, (Writer)out);
        out.flush();
    }

    public static void copy(Reader input, OutputStream output, String outputCharsetName) throws IOException {
        IOUtil.copy((Reader)input, (OutputStream)output, (Charset)Charset.forName(outputCharsetName));
    }

    public static int copy(Reader input, Writer output) throws IOException {
        long count = IOUtil.copyLarge((Reader)input, (Writer)output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)count;
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return IOUtil.copy((InputStream)input, (OutputStream)output, (int)8192);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0L;
        if (input != null) {
            int n;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
                count += (long)n;
            }
        }
        return count;
    }

    public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length) throws IOException {
        return IOUtil.copyLarge((InputStream)input, (OutputStream)output, (long)inputOffset, (long)length, (byte[])new byte[8192]);
    }

    public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length, byte[] buffer) throws IOException {
        int read;
        int bufferLength;
        if (inputOffset > 0L) {
            IOUtil.skipFully((InputStream)input, (long)inputOffset);
        }
        if (length == 0L) {
            return 0L;
        }
        int bytesToRead = bufferLength = buffer.length;
        if (length > 0L && length < (long)bufferLength) {
            bytesToRead = (int)length;
        }
        long totalRead = 0L;
        while (bytesToRead > 0 && -1 != (read = input.read(buffer, 0, bytesToRead))) {
            output.write(buffer, 0, read);
            totalRead += (long)read;
            if (length <= 0L) continue;
            bytesToRead = (int)Math.min(length - totalRead, (long)bufferLength);
        }
        return totalRead;
    }

    public static long copyLarge(Reader input, Writer output) throws IOException {
        return IOUtil.copyLarge((Reader)input, (Writer)output, (char[])new char[8192]);
    }

    public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
        int n;
        long count = 0L;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += (long)n;
        }
        return count;
    }

    public static long copyLarge(Reader input, Writer output, long inputOffset, long length) throws IOException {
        return IOUtil.copyLarge((Reader)input, (Writer)output, (long)inputOffset, (long)length, (char[])new char[8192]);
    }

    public static long copyLarge(Reader input, Writer output, long inputOffset, long length, char[] buffer) throws IOException {
        int read;
        if (inputOffset > 0L) {
            IOUtil.skipFully((Reader)input, (long)inputOffset);
        }
        if (length == 0L) {
            return 0L;
        }
        int bytesToRead = buffer.length;
        if (length > 0L && length < (long)buffer.length) {
            bytesToRead = (int)length;
        }
        long totalRead = 0L;
        while (bytesToRead > 0 && -1 != (read = input.read(buffer, 0, bytesToRead))) {
            output.write(buffer, 0, read);
            totalRead += (long)read;
            if (length <= 0L) continue;
            bytesToRead = (int)Math.min(length - totalRead, (long)buffer.length);
        }
        return totalRead;
    }

    public static long skip(InputStream input, long toSkip) throws IOException {
        long remain;
        long n;
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        }
        for (remain = toSkip; remain > 0L && (n = (long)input.read(SKIP_BYTE_BUFFER, 0, (int)Math.min(remain, (long)SKIP_BYTE_BUFFER.length))) >= 0L; remain -= n) {
        }
        return toSkip - remain;
    }

    public static long skip(ReadableByteChannel input, long toSkip) throws IOException {
        long remain;
        int n;
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        }
        ByteBuffer skipByteBuffer = ByteBuffer.allocate((int)Math.min(toSkip, (long)SKIP_BYTE_BUFFER.length));
        for (remain = toSkip; remain > 0L; remain -= (long)n) {
            skipByteBuffer.position(0);
            skipByteBuffer.limit((int)Math.min(remain, (long)SKIP_BYTE_BUFFER.length));
            n = input.read(skipByteBuffer);
            if (n == -1) break;
        }
        return toSkip - remain;
    }

    public static long skip(Reader input, long toSkip) throws IOException {
        long remain;
        long n;
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        }
        if (SKIP_CHAR_BUFFER == null) {
            SKIP_CHAR_BUFFER = new char[SKIP_BYTE_BUFFER.length];
        }
        for (remain = toSkip; remain > 0L && (n = (long)input.read(SKIP_CHAR_BUFFER, 0, (int)Math.min(remain, (long)SKIP_BYTE_BUFFER.length))) >= 0L; remain -= n) {
        }
        return toSkip - remain;
    }

    public static void skipFully(InputStream input, long toSkip) throws IOException {
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
        }
        long skipped = IOUtil.skip((InputStream)input, (long)toSkip);
        if (skipped != toSkip) {
            throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
        }
    }

    public static void skipFully(ReadableByteChannel input, long toSkip) throws IOException {
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
        }
        long skipped = IOUtil.skip((ReadableByteChannel)input, (long)toSkip);
        if (skipped != toSkip) {
            throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
        }
    }

    public static void skipFully(Reader input, long toSkip) throws IOException {
        long skipped = IOUtil.skip((Reader)input, (long)toSkip);
        if (skipped != toSkip) {
            throw new EOFException("Chars to skip: " + toSkip + " actual: " + skipped);
        }
    }

    public static int length(byte[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(char[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(CharSequence csq) {
        return csq == null ? 0 : csq.length();
    }

    public static int length(Object[] array) {
        return array == null ? 0 : array.length;
    }

    public static void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }

    public static void close(Closeable ... closeables) throws IOException {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                IOUtil.close((Closeable)closeable);
            }
        }
    }

    public static void close(Closeable closeable, IOConsumer<IOException> consumer) throws IOException {
        block3: {
            if (closeable != null) {
                try {
                    closeable.close();
                }
                catch (IOException e) {
                    if (consumer == null) break block3;
                    consumer.accept((Object)e);
                }
            }
        }
    }

    public static void close(URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection)conn).disconnect();
        }
    }

    public static String Stream2String(InputStream inputStream) {
        ByteArrayOutputStream result2 = new ByteArrayOutputStream();
        try {
            int length;
            byte[] buffer = new byte[8192];
            while ((length = inputStream.read(buffer)) != -1) {
                result2.write(buffer, 0, length);
            }
            return result2.toString();
        }
        catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }
}

