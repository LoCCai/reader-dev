/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.loader.jar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.URLStreamHandler;
import java.security.Permission;
import org.springframework.boot.loader.jar.AsciiBytes;
import org.springframework.boot.loader.jar.JarEntry;
import org.springframework.boot.loader.jar.JarFile;
import org.springframework.boot.loader.jar.StringSequence;

final class JarURLConnection
extends java.net.JarURLConnection {
    private static ThreadLocal<Boolean> useFastExceptions = new ThreadLocal();
    private static final FileNotFoundException FILE_NOT_FOUND_EXCEPTION = new FileNotFoundException("Jar file or entry not found");
    private static final IllegalStateException NOT_FOUND_CONNECTION_EXCEPTION = new IllegalStateException(FILE_NOT_FOUND_EXCEPTION);
    private static final String SEPARATOR = "!/";
    private static final URL EMPTY_JAR_URL;
    private static final JarEntryName EMPTY_JAR_ENTRY_NAME;
    private static final String READ_ACTION = "read";
    private static final JarURLConnection NOT_FOUND_CONNECTION;
    private final JarFile jarFile;
    private Permission permission;
    private URL jarFileUrl;
    private final JarEntryName jarEntryName;
    private JarEntry jarEntry;

    private JarURLConnection(URL url2, JarFile jarFile, JarEntryName jarEntryName) throws IOException {
        super(EMPTY_JAR_URL);
        this.url = url2;
        this.jarFile = jarFile;
        this.jarEntryName = jarEntryName;
    }

    @Override
    public void connect() throws IOException {
        if (this.jarFile == null) {
            throw FILE_NOT_FOUND_EXCEPTION;
        }
        if (!this.jarEntryName.isEmpty() && this.jarEntry == null) {
            this.jarEntry = this.jarFile.getJarEntry(this.getEntryName());
            if (this.jarEntry == null) {
                this.throwFileNotFound(this.jarEntryName, this.jarFile);
            }
        }
        this.connected = true;
    }

    @Override
    public JarFile getJarFile() throws IOException {
        this.connect();
        return this.jarFile;
    }

    @Override
    public URL getJarFileURL() {
        if (this.jarFile == null) {
            throw NOT_FOUND_CONNECTION_EXCEPTION;
        }
        if (this.jarFileUrl == null) {
            this.jarFileUrl = this.buildJarFileUrl();
        }
        return this.jarFileUrl;
    }

    private URL buildJarFileUrl() {
        try {
            String spec = this.jarFile.getUrl().getFile();
            if (spec.endsWith(SEPARATOR)) {
                spec = spec.substring(0, spec.length() - SEPARATOR.length());
            }
            if (spec.indexOf(SEPARATOR) == -1) {
                return new URL(spec);
            }
            return new URL("jar:" + spec);
        }
        catch (MalformedURLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public JarEntry getJarEntry() throws IOException {
        if (this.jarEntryName == null || this.jarEntryName.isEmpty()) {
            return null;
        }
        this.connect();
        return this.jarEntry;
    }

    @Override
    public String getEntryName() {
        if (this.jarFile == null) {
            throw NOT_FOUND_CONNECTION_EXCEPTION;
        }
        return this.jarEntryName.toString();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream;
        if (this.jarFile == null) {
            throw FILE_NOT_FOUND_EXCEPTION;
        }
        if (this.jarEntryName.isEmpty() && this.jarFile.getType() == JarFile.JarFileType.DIRECT) {
            throw new IOException("no entry name specified");
        }
        this.connect();
        InputStream inputStream2 = inputStream = this.jarEntryName.isEmpty() ? this.jarFile.getData().getInputStream() : this.jarFile.getInputStream(this.jarEntry);
        if (inputStream == null) {
            this.throwFileNotFound(this.jarEntryName, this.jarFile);
        }
        return inputStream;
    }

    private void throwFileNotFound(Object entry, JarFile jarFile) throws FileNotFoundException {
        if (Boolean.TRUE.equals(useFastExceptions.get())) {
            throw FILE_NOT_FOUND_EXCEPTION;
        }
        throw new FileNotFoundException("JAR entry " + entry + " not found in " + jarFile.getName());
    }

    @Override
    public int getContentLength() {
        long length = this.getContentLengthLong();
        if (length > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)length;
    }

    @Override
    public long getContentLengthLong() {
        if (this.jarFile == null) {
            return -1L;
        }
        try {
            if (this.jarEntryName.isEmpty()) {
                return this.jarFile.size();
            }
            JarEntry entry = this.getJarEntry();
            return entry != null ? (long)((int)entry.getSize()) : -1L;
        }
        catch (IOException ex) {
            return -1L;
        }
    }

    @Override
    public Object getContent() throws IOException {
        this.connect();
        return this.jarEntryName.isEmpty() ? this.jarFile : super.getContent();
    }

    @Override
    public String getContentType() {
        return this.jarEntryName != null ? this.jarEntryName.getContentType() : null;
    }

    @Override
    public Permission getPermission() throws IOException {
        if (this.jarFile == null) {
            throw FILE_NOT_FOUND_EXCEPTION;
        }
        if (this.permission == null) {
            this.permission = new FilePermission(this.jarFile.getRootJarFile().getFile().getPath(), READ_ACTION);
        }
        return this.permission;
    }

    @Override
    public long getLastModified() {
        if (this.jarFile == null || this.jarEntryName.isEmpty()) {
            return 0L;
        }
        try {
            JarEntry entry = this.getJarEntry();
            return entry != null ? entry.getTime() : 0L;
        }
        catch (IOException ex) {
            return 0L;
        }
    }

    static void setUseFastExceptions(boolean useFastExceptions) {
        JarURLConnection.useFastExceptions.set(useFastExceptions);
    }

    static JarURLConnection get(URL url2, JarFile jarFile) throws IOException {
        int separator;
        StringSequence spec = new StringSequence(url2.getFile());
        int index = JarURLConnection.indexOfRootSpec(spec, jarFile.getPathFromRoot());
        if (index == -1) {
            return Boolean.TRUE.equals(useFastExceptions.get()) ? NOT_FOUND_CONNECTION : new JarURLConnection(url2, null, EMPTY_JAR_ENTRY_NAME);
        }
        while ((separator = spec.indexOf(SEPARATOR, index)) > 0) {
            JarEntryName entryName = JarEntryName.get(spec.subSequence(index, separator));
            JarEntry jarEntry = jarFile.getJarEntry(entryName.toCharSequence());
            if (jarEntry == null) {
                return JarURLConnection.notFound(jarFile, entryName);
            }
            jarFile = jarFile.getNestedJarFile(jarEntry);
            index = separator + SEPARATOR.length();
        }
        JarEntryName jarEntryName = JarEntryName.get(spec, index);
        if (Boolean.TRUE.equals(useFastExceptions.get()) && !jarEntryName.isEmpty() && !jarFile.containsEntry(jarEntryName.toString())) {
            return NOT_FOUND_CONNECTION;
        }
        return new JarURLConnection(url2, jarFile, jarEntryName);
    }

    private static int indexOfRootSpec(StringSequence file, String pathFromRoot) {
        int separatorIndex = file.indexOf(SEPARATOR);
        if (separatorIndex < 0 || !file.startsWith(pathFromRoot, separatorIndex)) {
            return -1;
        }
        return separatorIndex + SEPARATOR.length() + pathFromRoot.length();
    }

    private static JarURLConnection notFound() {
        try {
            return JarURLConnection.notFound(null, null);
        }
        catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static JarURLConnection notFound(JarFile jarFile, JarEntryName jarEntryName) throws IOException {
        if (Boolean.TRUE.equals(useFastExceptions.get())) {
            return NOT_FOUND_CONNECTION;
        }
        return new JarURLConnection(null, jarFile, jarEntryName);
    }

    static {
        try {
            EMPTY_JAR_URL = new URL("jar:", null, 0, "file:!/", new URLStreamHandler(){

                @Override
                protected URLConnection openConnection(URL u) throws IOException {
                    return null;
                }
            });
        }
        catch (MalformedURLException ex) {
            throw new IllegalStateException(ex);
        }
        EMPTY_JAR_ENTRY_NAME = new JarEntryName(new StringSequence(""));
        NOT_FOUND_CONNECTION = JarURLConnection.notFound();
    }

    static class JarEntryName {
        private final StringSequence name;
        private String contentType;

        JarEntryName(StringSequence spec) {
            this.name = this.decode(spec);
        }

        private StringSequence decode(StringSequence source) {
            if (source.isEmpty() || source.indexOf('%') < 0) {
                return source;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream(source.length());
            this.write(source.toString(), bos);
            return new StringSequence(AsciiBytes.toString(bos.toByteArray()));
        }

        private void write(String source, ByteArrayOutputStream outputStream) {
            int length = source.length();
            for (int i = 0; i < length; ++i) {
                char c = source.charAt(i);
                if (c > '\u007f') {
                    try {
                        String encoded = URLEncoder.encode(String.valueOf(c), "UTF-8");
                        this.write(encoded, outputStream);
                        continue;
                    }
                    catch (UnsupportedEncodingException ex) {
                        throw new IllegalStateException(ex);
                    }
                }
                if (c == '%') {
                    if (i + 2 >= length) {
                        throw new IllegalArgumentException("Invalid encoded sequence \"" + source.substring(i) + "\"");
                    }
                    c = this.decodeEscapeSequence(source, i);
                    i += 2;
                }
                outputStream.write(c);
            }
        }

        private char decodeEscapeSequence(String source, int i) {
            int hi = Character.digit(source.charAt(i + 1), 16);
            int lo = Character.digit(source.charAt(i + 2), 16);
            if (hi == -1 || lo == -1) {
                throw new IllegalArgumentException("Invalid encoded sequence \"" + source.substring(i) + "\"");
            }
            return (char)((hi << 4) + lo);
        }

        public CharSequence toCharSequence() {
            return this.name;
        }

        public String toString() {
            return this.name.toString();
        }

        public boolean isEmpty() {
            return this.name.isEmpty();
        }

        public String getContentType() {
            if (this.contentType == null) {
                this.contentType = this.deduceContentType();
            }
            return this.contentType;
        }

        private String deduceContentType() {
            String type = this.isEmpty() ? "x-java/jar" : null;
            type = type != null ? type : URLConnection.guessContentTypeFromName(this.toString());
            type = type != null ? type : "content/unknown";
            return type;
        }

        public static JarEntryName get(StringSequence spec) {
            return JarEntryName.get(spec, 0);
        }

        public static JarEntryName get(StringSequence spec, int beginIndex) {
            if (spec.length() <= beginIndex) {
                return EMPTY_JAR_ENTRY_NAME;
            }
            return new JarEntryName(spec.subSequence(beginIndex));
        }
    }
}

