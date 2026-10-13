/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.ZipUtils
 *  io.legado.app.utils.ZipUtilsKt
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.CoroutineContext
 *  kotlin.io.ByteStreamsKt
 *  kotlin.io.CloseableKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  kotlinx.coroutines.BuildersKt
 *  kotlinx.coroutines.Dispatchers
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.utils;

import io.legado.app.utils.ZipUtilsKt;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0012\u0010\u0007\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0018\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006J\u0016\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\nJ\u0012\u0010\r\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\nH\u0002J\u0018\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006J\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\nJ\u0012\u0010\u0010\u001a\u00020\u00042\b\u0010\u0011\u001a\u0004\u0018\u00010\nH\u0002J6\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00062\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\nH\u0002J\u001e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0006J\u001e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\nJ,\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\b\u0010\u001e\u001a\u0004\u0018\u00010\nJ(\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\n2\b\u0010\u001e\u001a\u0004\u0018\u00010\nJ*\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\nH\u0002J(\u0010\u000b\u001a\u00020\u00042\b\u0010\u001f\u001a\u0004\u0018\u00010\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\nH\u0007J\u0016\u0010\u000b\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nJ\u001e\u0010\u000b\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010#\u001a\u00020\nJ.\u0010%\u001a\u00020\u00042\u000e\u0010&\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010'2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\nH\u0007J'\u0010%\u001a\u00020\u00042\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\n0'2\u0006\u0010\f\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J5\u0010%\u001a\u00020\u00042\u000e\u0010)\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010'2\b\u0010\f\u001a\u0004\u0018\u00010\n2\b\u0010#\u001a\u0004\u0018\u00010\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006+"}, d2={"Lio/legado/app/utils/ZipUtils;", "", "()V", "createOrExistsDir", "", "file", "Ljava/io/File;", "createOrExistsFile", "getComments", "", "", "zipFile", "zipFilePath", "getFileByPath", "filePath", "getFilesPath", "isSpace", "s", "unzipChildFile", "destDir", "files", "", "zip", "Ljava/util/zip/ZipFile;", "entry", "Ljava/util/zip/ZipEntry;", "name", "unzipFile", "destDirPath", "unzipFileByKeyword", "keyword", "srcFile", "rootPath", "zos", "Ljava/util/zip/ZipOutputStream;", "comment", "srcFilePath", "zipFiles", "srcFiles", "", "(Ljava/util/Collection;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "srcFilePaths", "(Ljava/util/Collection;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reader-pro"})
public final class ZipUtils {
    @NotNull
    public static final ZipUtils INSTANCE = new ZipUtils();

    private ZipUtils() {
    }

    @Nullable
    public final Object zipFiles(@NotNull Collection<String> srcFiles, @NotNull String zipFilePath, @NotNull Continuation<? super Boolean> $completion) {
        return this.zipFiles(srcFiles, zipFilePath, null, $completion);
    }

    @Nullable
    public final Object zipFiles(@Nullable Collection<String> srcFilePaths, @Nullable String zipFilePath, @Nullable String comment, @NotNull Continuation<? super Boolean> $completion) {
        return BuildersKt.withContext((CoroutineContext)((CoroutineContext)Dispatchers.getIO()), (Function2)((Function2)new /* Unavailable Anonymous Inner Class!! */), $completion);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @JvmOverloads
    public final boolean zipFiles(@Nullable Collection<? extends File> srcFiles, @Nullable File zipFile, @Nullable String comment) throws IOException {
        if (srcFiles == null || zipFile == null) {
            return false;
        }
        Closeable closeable = new ZipOutputStream(new FileOutputStream(zipFile));
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = null;
        try {
            ZipOutputStream it = (ZipOutputStream)closeable;
            boolean bl3 = false;
            for (File file : srcFiles) {
                if (INSTANCE.zipFile(file, "", it, comment)) continue;
                boolean bl4 = false;
                return bl4;
            }
            boolean bl5 = true;
            return bl5;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
    }

    public static /* synthetic */ boolean zipFiles$default(ZipUtils zipUtils, Collection collection, File file, String string, int n, Object object) throws IOException {
        if ((n & 4) != 0) {
            string = null;
        }
        return zipUtils.zipFiles(collection, file, string);
    }

    public final boolean zipFile(@NotNull String srcFilePath, @NotNull String zipFilePath) throws IOException {
        Intrinsics.checkNotNullParameter((Object)srcFilePath, (String)"srcFilePath");
        Intrinsics.checkNotNullParameter((Object)zipFilePath, (String)"zipFilePath");
        return this.zipFile(this.getFileByPath(srcFilePath), this.getFileByPath(zipFilePath), null);
    }

    public final boolean zipFile(@NotNull String srcFilePath, @NotNull String zipFilePath, @NotNull String comment) throws IOException {
        Intrinsics.checkNotNullParameter((Object)srcFilePath, (String)"srcFilePath");
        Intrinsics.checkNotNullParameter((Object)zipFilePath, (String)"zipFilePath");
        Intrinsics.checkNotNullParameter((Object)comment, (String)"comment");
        return this.zipFile(this.getFileByPath(srcFilePath), this.getFileByPath(zipFilePath), comment);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @JvmOverloads
    public final boolean zipFile(@Nullable File srcFile, @Nullable File zipFile, @Nullable String comment) throws IOException {
        if (srcFile == null || zipFile == null) {
            return false;
        }
        Closeable closeable = new ZipOutputStream(new FileOutputStream(zipFile));
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = null;
        try {
            ZipOutputStream zos = (ZipOutputStream)closeable;
            boolean bl3 = false;
            boolean bl4 = INSTANCE.zipFile(srcFile, "", zos, comment);
            return bl4;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
    }

    public static /* synthetic */ boolean zipFile$default(ZipUtils zipUtils, File file, File file2, String string, int n, Object object) throws IOException {
        if ((n & 4) != 0) {
            string = null;
        }
        return zipUtils.zipFile(file, file2, string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final boolean zipFile(File srcFile, String rootPath, ZipOutputStream zos, String comment) throws IOException {
        String rootPath1;
        block10: {
            File file;
            int n;
            File[] fileArray;
            File[] fileList;
            block12: {
                block11: {
                    rootPath1 = null;
                    rootPath1 = rootPath;
                    if (!srcFile.exists()) {
                        return true;
                    }
                    rootPath1 = rootPath1 + (this.isSpace(rootPath1) ? "" : File.separator) + srcFile.getName();
                    if (!srcFile.isDirectory()) break block10;
                    fileList = srcFile.listFiles();
                    if (fileList == null) break block11;
                    fileArray = fileList;
                    n = 0;
                    if (!(fileArray.length == 0)) break block12;
                }
                ZipEntry entry = new ZipEntry(Intrinsics.stringPlus((String)rootPath1, (Object)"/"));
                entry.setComment(comment);
                zos.putNextEntry(entry);
                zos.closeEntry();
                return true;
            }
            fileArray = fileList;
            n = 0;
            int n2 = fileArray.length;
            do {
                if (n >= n2) return true;
                file = fileArray[n];
                ++n;
                Intrinsics.checkNotNullExpressionValue((Object)file, (String)"file");
            } while (this.zipFile(file, rootPath1, zos, comment));
            return false;
        }
        Closeable closeable = new BufferedInputStream(new FileInputStream(srcFile));
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = null;
        try {
            BufferedInputStream is = (BufferedInputStream)closeable;
            boolean bl3 = false;
            ZipEntry entry = new ZipEntry(rootPath1);
            entry.setComment(comment);
            zos.putNextEntry(entry);
            zos.write(ByteStreamsKt.readBytes((InputStream)is));
            zos.closeEntry();
            Unit unit = Unit.INSTANCE;
            return true;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
    }

    @Nullable
    public final List<File> unzipFile(@NotNull String zipFilePath, @NotNull String destDirPath) throws IOException {
        Intrinsics.checkNotNullParameter((Object)zipFilePath, (String)"zipFilePath");
        Intrinsics.checkNotNullParameter((Object)destDirPath, (String)"destDirPath");
        return this.unzipFileByKeyword(zipFilePath, destDirPath, null);
    }

    @Nullable
    public final List<File> unzipFile(@NotNull File zipFile, @NotNull File destDir) throws IOException {
        Intrinsics.checkNotNullParameter((Object)zipFile, (String)"zipFile");
        Intrinsics.checkNotNullParameter((Object)destDir, (String)"destDir");
        return this.unzipFileByKeyword(zipFile, destDir, null);
    }

    @Nullable
    public final List<File> unzipFileByKeyword(@NotNull String zipFilePath, @NotNull String destDirPath, @Nullable String keyword) throws IOException {
        Intrinsics.checkNotNullParameter((Object)zipFilePath, (String)"zipFilePath");
        Intrinsics.checkNotNullParameter((Object)destDirPath, (String)"destDirPath");
        return this.unzipFileByKeyword(this.getFileByPath(zipFilePath), this.getFileByPath(destDirPath), keyword);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final List<File> unzipFileByKeyword(@Nullable File zipFile, @Nullable File destDir, @Nullable String keyword) throws IOException {
        if (zipFile == null || destDir == null) {
            return null;
        }
        ArrayList files = new ArrayList();
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        Closeable closeable = zip;
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = null;
        try {
            ZipFile it = (ZipFile)closeable;
            boolean bl3 = false;
            if (INSTANCE.isSpace(keyword)) {
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = entries.nextElement();
                    if (zipEntry == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
                    }
                    ZipEntry entry = zipEntry;
                    String entryName = entry.getName();
                    Intrinsics.checkNotNullExpressionValue((Object)entryName, (String)"entryName");
                    if (StringsKt.contains$default((CharSequence)entryName, (CharSequence)"../", (boolean)false, (int)2, null)) {
                        ZipUtilsKt.access$getLogger$p().error("ZipUtils entryName: " + entryName + " is dangerous!");
                        continue;
                    }
                    if (INSTANCE.unzipChildFile(destDir, (List)files, zip, entry, entryName)) continue;
                    List list2 = files;
                    return list2;
                }
            } else {
                while (entries.hasMoreElements()) {
                    Object entryName = entries.nextElement();
                    if (entryName == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
                    }
                    ZipEntry entry = entryName;
                    entryName = entry.getName();
                    Intrinsics.checkNotNullExpressionValue((Object)entryName, (String)"entryName");
                    if (StringsKt.contains$default((CharSequence)((CharSequence)entryName), (CharSequence)"../", (boolean)false, (int)2, null)) {
                        ZipUtilsKt.access$getLogger$p().error("ZipUtils entryName: " + entryName + " is dangerous!");
                        continue;
                    }
                    CharSequence charSequence = (CharSequence)entryName;
                    String string = keyword;
                    Intrinsics.checkNotNull((Object)string);
                    if (!StringsKt.contains$default((CharSequence)charSequence, (CharSequence)string, (boolean)false, (int)2, null) || INSTANCE.unzipChildFile(destDir, (List)files, zip, entry, (String)entryName)) continue;
                    List list3 = files;
                    return list3;
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
        return files;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean unzipChildFile(File destDir, List<File> files, ZipFile zip, ZipEntry entry, String name) throws IOException {
        File file = new File(destDir, name);
        files.add(file);
        if (entry.isDirectory()) {
            return this.createOrExistsDir(file);
        }
        if (!this.createOrExistsFile(file)) {
            return false;
        }
        Closeable closeable = new BufferedInputStream(zip.getInputStream(entry));
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = null;
        try {
            BufferedInputStream in = (BufferedInputStream)closeable;
            boolean bl3 = false;
            Closeable closeable2 = new BufferedOutputStream(new FileOutputStream(file));
            boolean bl4 = false;
            boolean bl5 = false;
            Throwable throwable2 = null;
            try {
                BufferedOutputStream out = (BufferedOutputStream)closeable2;
                boolean bl6 = false;
                out.write(ByteStreamsKt.readBytes((InputStream)in));
                Unit unit = Unit.INSTANCE;
            }
            catch (Throwable throwable3) {
                throwable2 = throwable3;
                throw throwable3;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable2, (Throwable)throwable2);
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable4) {
            throwable = throwable4;
            throw throwable4;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
        return true;
    }

    @Nullable
    public final List<String> getFilesPath(@NotNull String zipFilePath) throws IOException {
        Intrinsics.checkNotNullParameter((Object)zipFilePath, (String)"zipFilePath");
        return this.getFilesPath(this.getFileByPath(zipFilePath));
    }

    @Nullable
    public final List<String> getFilesPath(@Nullable File zipFile) throws IOException {
        if (zipFile == null) {
            return null;
        }
        ArrayList<String> paths = new ArrayList<String>();
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if (zipEntry == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
            }
            String entryName = zipEntry.getName();
            Intrinsics.checkNotNullExpressionValue((Object)entryName, (String)"entryName");
            if (StringsKt.contains$default((CharSequence)entryName, (CharSequence)"../", (boolean)false, (int)2, null)) {
                ZipUtilsKt.access$getLogger$p().error("ZipUtils entryName: " + entryName + " is dangerous!");
                paths.add(entryName);
                continue;
            }
            paths.add(entryName);
        }
        zip.close();
        return paths;
    }

    @Nullable
    public final List<String> getComments(@NotNull String zipFilePath) throws IOException {
        Intrinsics.checkNotNullParameter((Object)zipFilePath, (String)"zipFilePath");
        return this.getComments(this.getFileByPath(zipFilePath));
    }

    @Nullable
    public final List<String> getComments(@Nullable File zipFile) throws IOException {
        if (zipFile == null) {
            return null;
        }
        ArrayList<String> comments = new ArrayList<String>();
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if (zipEntry == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
            }
            ZipEntry entry = zipEntry;
            comments.add(entry.getComment());
        }
        zip.close();
        return comments;
    }

    private final boolean createOrExistsDir(File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private final boolean createOrExistsFile(File file) {
        boolean bl;
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!this.createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            bl = file.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }

    private final File getFileByPath(String filePath) {
        return this.isSpace(filePath) ? null : new File(filePath);
    }

    private final boolean isSpace(String s) {
        if (s == null) {
            return true;
        }
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            if (Character.isWhitespace(s.charAt(i))) continue;
            return false;
        }
        return true;
    }

    @JvmOverloads
    public final boolean zipFiles(@Nullable Collection<? extends File> srcFiles, @Nullable File zipFile) throws IOException {
        return ZipUtils.zipFiles$default((ZipUtils)this, srcFiles, (File)zipFile, null, (int)4, null);
    }

    @JvmOverloads
    public final boolean zipFile(@Nullable File srcFile, @Nullable File zipFile) throws IOException {
        return ZipUtils.zipFile$default((ZipUtils)this, (File)srcFile, (File)zipFile, null, (int)4, null);
    }

    public static final /* synthetic */ boolean access$zipFile(ZipUtils $this, File srcFile, String rootPath, ZipOutputStream zos, String comment) {
        return $this.zipFile(srcFile, rootPath, zos, comment);
    }

    public static final /* synthetic */ File access$getFileByPath(ZipUtils $this, String filePath) {
        return $this.getFileByPath(filePath);
    }
}

