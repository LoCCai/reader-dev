/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.utils.FileUtils
 *  io.legado.app.utils.FileUtils$SortByExtension
 *  io.legado.app.utils.FileUtils$SortByName
 *  io.legado.app.utils.FileUtils$SortBySize
 *  io.legado.app.utils.FileUtils$SortByTime
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package io.legado.app.utils;

import io.legado.app.utils.FileUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 * Exception performing whole class analysis ignored.
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b'\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0005abcdeB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013J\u0010\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\u0016\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u0013J\u0016\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eJ\u0016\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u0013J'\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u001e2\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130#\"\u00020\u0013\u00a2\u0006\u0002\u0010$J\u000e\u0010 \u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u0013J\u000e\u0010&\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u0013J'\u0010'\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u001e2\u0012\u0010(\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130#\"\u00020\u0013\u00a2\u0006\u0002\u0010$J\u000e\u0010'\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u0013J\u001a\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u001e2\b\b\u0002\u0010+\u001a\u00020\u0011H\u0007J\u001a\u0010)\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010+\u001a\u00020\u0011H\u0007J\u000e\u0010,\u001a\u00020\u00162\u0006\u0010%\u001a\u00020\u0013J\u0010\u0010-\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u001eH\u0002J\u000e\u0010.\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J'\u0010/\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u001e2\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130#\"\u00020\u0013\u00a2\u0006\u0002\u00100J\u0006\u00101\u001a\u00020\u0013J\u0016\u00102\u001a\u00020\u00132\u0006\u0010*\u001a\u00020\u001e2\u0006\u00103\u001a\u00020\u0013J\u001a\u00102\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u00103\u001a\u00020\u0013H\u0007J\u000e\u00104\u001a\u00020\u00132\u0006\u00105\u001a\u00020\u0013J'\u00106\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u001e2\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130#\"\u00020\u0013\u00a2\u0006\u0002\u0010$J\u0018\u00107\u001a\u00020\u00132\u0006\u00108\u001a\u00020\u00132\b\b\u0002\u00109\u001a\u00020\u0013J\u000e\u0010:\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010;\u001a\u00020\u00132\u0006\u00105\u001a\u00020\u0013J\u0010\u0010<\u001a\u00020\u00132\b\u00105\u001a\u0004\u0018\u00010\u0013J\u000e\u0010=\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0013J'\u0010>\u001a\u00020\u00132\u0006\u0010!\u001a\u00020\u001e2\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130#\"\u00020\u0013\u00a2\u0006\u0002\u0010?J\u000e\u0010@\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0013J7\u0010A\u001a\b\u0012\u0004\u0012\u00020\u001e0#2\u0006\u0010B\u001a\u00020\u00132\u0010\b\u0002\u0010C\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010#2\b\b\u0002\u0010D\u001a\u00020\u0004H\u0007\u00a2\u0006\u0002\u0010EJ/\u0010F\u001a\n\u0012\u0004\u0012\u00020\u001e\u0018\u00010#2\u0006\u0010B\u001a\u00020\u00132\u0010\b\u0002\u0010G\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010#H\u0007\u00a2\u0006\u0002\u0010HJ1\u0010I\u001a\b\u0012\u0004\u0012\u00020\u001e0#2\u0006\u0010B\u001a\u00020\u00132\n\b\u0002\u0010J\u001a\u0004\u0018\u00010K2\b\b\u0002\u0010D\u001a\u00020\u0004H\u0007\u00a2\u0006\u0002\u0010LJ+\u0010I\u001a\n\u0012\u0004\u0012\u00020\u001e\u0018\u00010#2\u0006\u0010B\u001a\u00020\u00132\u000e\u0010G\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010#\u00a2\u0006\u0002\u0010HJ%\u0010I\u001a\n\u0012\u0004\u0012\u00020\u001e\u0018\u00010#2\u0006\u0010B\u001a\u00020\u00132\b\u0010M\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0002\u0010NJ\u000e\u0010O\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u001eJ\u000e\u0010O\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010P\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eJ\u0016\u0010P\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u0013J\u0010\u0010Q\u001a\u0004\u0018\u00010R2\u0006\u0010S\u001a\u00020\u0013J\u001a\u0010T\u001a\u00020\u00132\u0006\u0010S\u001a\u00020\u00132\b\b\u0002\u0010U\u001a\u00020\u0013H\u0007J\u0016\u0010V\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eJ\u0016\u0010V\u001a\u00020\u00112\u0006\u0010W\u001a\u00020\u00132\u0006\u0010X\u001a\u00020\u0013J\u000e\u0010Y\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010Z\u001a\u00020\u00132\u0006\u0010[\u001a\u00020\rJ\u0016\u0010\\\u001a\u00020\u00112\u0006\u0010S\u001a\u00020\u00132\u0006\u0010]\u001a\u00020RJ\u0016\u0010^\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u001e2\u0006\u0010]\u001a\u00020_J\u0016\u0010^\u001a\u00020\u00112\u0006\u0010S\u001a\u00020\u00132\u0006\u0010]\u001a\u00020_J\"\u0010`\u001a\u00020\u00112\u0006\u0010S\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\b\b\u0002\u0010U\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006f"}, d2={"Lio/legado/app/utils/FileUtils;", "", "()V", "BY_EXTENSION_ASC", "", "BY_EXTENSION_DESC", "BY_NAME_ASC", "BY_NAME_DESC", "BY_SIZE_ASC", "BY_SIZE_DESC", "BY_TIME_ASC", "BY_TIME_DESC", "GB", "", "KB", "MB", "appendText", "", "path", "", "content", "closeSilently", "", "c", "Ljava/io/Closeable;", "compareLastModified", "path1", "path2", "copy", "src", "Ljava/io/File;", "tar", "createFileIfNotExist", "root", "subDirFiles", "", "(Ljava/io/File;[Ljava/lang/String;)Ljava/io/File;", "filePath", "createFileWithReplace", "createFolderIfNotExist", "subDirs", "delete", "file", "deleteRootDir", "deleteFile", "deleteResolveEBUSY", "exist", "exists", "(Ljava/io/File;[Ljava/lang/String;)Z", "getCachePath", "getDateTime", "format", "getExtension", "pathOrUrl", "getFile", "getFileExtetion", "url", "defaultExt", "getLength", "getMimeType", "getName", "getNameExcludeExtension", "getPath", "(Ljava/io/File;[Ljava/lang/String;)Ljava/lang/String;", "getSize", "listDirs", "startDirPath", "excludeDirs", "sortType", "(Ljava/lang/String;[Ljava/lang/String;I)[Ljava/io/File;", "listDirsAndFiles", "allowExtensions", "(Ljava/lang/String;[Ljava/lang/String;)[Ljava/io/File;", "listFiles", "filterPattern", "Ljava/util/regex/Pattern;", "(Ljava/lang/String;Ljava/util/regex/Pattern;I)[Ljava/io/File;", "allowExtension", "(Ljava/lang/String;Ljava/lang/String;)[Ljava/io/File;", "makeDirs", "move", "readBytes", "", "filepath", "readText", "charset", "rename", "oldPath", "newPath", "separator", "toFileSizeString", "fileSize", "writeBytes", "data", "writeInputStream", "Ljava/io/InputStream;", "writeText", "SortByExtension", "SortByName", "SortBySize", "SortByTime", "SortType", "reader-pro"})
public final class FileUtils {
    @NotNull
    public static final FileUtils INSTANCE = new FileUtils();
    public static final long GB = 0x40000000L;
    public static final long MB = 0x100000L;
    public static final long KB = 1024L;
    public static final int BY_NAME_ASC = 0;
    public static final int BY_NAME_DESC = 1;
    public static final int BY_TIME_ASC = 2;
    public static final int BY_TIME_DESC = 3;
    public static final int BY_SIZE_ASC = 4;
    public static final int BY_SIZE_DESC = 5;
    public static final int BY_EXTENSION_ASC = 6;
    public static final int BY_EXTENSION_DESC = 7;

    private FileUtils() {
    }

    public final boolean exists(@NotNull File root, String ... subDirFiles) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        Intrinsics.checkNotNullParameter((Object)subDirFiles, (String)"subDirFiles");
        return this.getFile(root, Arrays.copyOf(subDirFiles, subDirFiles.length)).exists();
    }

    @NotNull
    public final File createFileIfNotExist(@NotNull File root, String ... subDirFiles) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        Intrinsics.checkNotNullParameter((Object)subDirFiles, (String)"subDirFiles");
        String filePath = this.getPath(root, Arrays.copyOf(subDirFiles, subDirFiles.length));
        return this.createFileIfNotExist(filePath);
    }

    @NotNull
    public final File createFolderIfNotExist(@NotNull File root, String ... subDirs) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        Intrinsics.checkNotNullParameter((Object)subDirs, (String)"subDirs");
        String filePath = this.getPath(root, Arrays.copyOf(subDirs, subDirs.length));
        return this.createFolderIfNotExist(filePath);
    }

    @NotNull
    public final File createFolderIfNotExist(@NotNull String filePath) {
        Intrinsics.checkNotNullParameter((Object)filePath, (String)"filePath");
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    @NotNull
    public final synchronized File createFileIfNotExist(@NotNull String filePath) {
        Intrinsics.checkNotNullParameter((Object)filePath, (String)"filePath");
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                String string = file.getParent();
                if (string != null) {
                    String string2 = string;
                    boolean bl = false;
                    boolean bl2 = false;
                    String it = string2;
                    boolean bl3 = false;
                    INSTANCE.createFolderIfNotExist(it);
                }
                file.createNewFile();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @NotNull
    public final File createFileWithReplace(@NotNull String filePath) {
        Intrinsics.checkNotNullParameter((Object)filePath, (String)"filePath");
        File file = new File(filePath);
        if (!file.exists()) {
            String string = file.getParent();
            if (string != null) {
                String string2 = string;
                boolean bl = false;
                boolean bl2 = false;
                String it = string2;
                boolean bl3 = false;
                INSTANCE.createFolderIfNotExist(it);
            }
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
        return file;
    }

    @NotNull
    public final File getFile(@NotNull File root, String ... subDirFiles) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        Intrinsics.checkNotNullParameter((Object)subDirFiles, (String)"subDirFiles");
        String filePath = this.getPath(root, Arrays.copyOf(subDirFiles, subDirFiles.length));
        return new File(filePath);
    }

    @NotNull
    public final String getPath(@NotNull File root, String ... subDirFiles) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        Intrinsics.checkNotNullParameter((Object)subDirFiles, (String)"subDirFiles");
        StringBuilder path = new StringBuilder(root.getAbsolutePath());
        String[] $this$forEach$iv = subDirFiles;
        boolean $i$f$forEach = false;
        String[] stringArray = $this$forEach$iv;
        int n = stringArray.length;
        for (int i = 0; i < n; ++i) {
            String element$iv;
            String it = element$iv = stringArray[i];
            boolean bl = false;
            CharSequence charSequence = it;
            boolean bl2 = false;
            if (!(charSequence.length() > 0)) continue;
            path.append(File.separator).append(it);
        }
        String string = path.toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"path.toString()");
        return string;
    }

    public final synchronized void deleteFile(@NotNull String filePath) {
        File[] files;
        File[] fileArray;
        Intrinsics.checkNotNullParameter((Object)filePath, (String)"filePath");
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory() && (fileArray = (files = file.listFiles())) != null) {
            File[] $this$forEach$iv = fileArray;
            boolean $i$f$forEach = false;
            File[] fileArray2 = $this$forEach$iv;
            int n = fileArray2.length;
            for (int i = 0; i < n; ++i) {
                File element$iv;
                File subFile = element$iv = fileArray2[i];
                boolean bl = false;
                String path = subFile.getPath();
                Intrinsics.checkNotNullExpressionValue((Object)path, (String)"path");
                INSTANCE.deleteFile(path);
            }
        }
        file.delete();
    }

    @NotNull
    public final String getCachePath() {
        throw new Exception("Not implemented");
    }

    @NotNull
    public final String separator(@NotNull String path) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        String path1 = path;
        String separator = File.separator;
        Intrinsics.checkNotNullExpressionValue((Object)separator, (String)"separator");
        path1 = StringsKt.replace$default((String)path1, (String)"\\", (String)separator, (boolean)false, (int)4, null);
        if (!StringsKt.endsWith$default((String)path1, (String)separator, (boolean)false, (int)2, null)) {
            path1 = Intrinsics.stringPlus((String)path1, (Object)separator);
        }
        return path1;
    }

    public final void closeSilently(@Nullable Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @JvmOverloads
    @NotNull
    public final File[] listDirs(@NotNull String startDirPath, @Nullable String[] excludeDirs, int sortType) {
        Intrinsics.checkNotNullParameter((Object)startDirPath, (String)"startDirPath");
        Object[] excludeDirs1 = excludeDirs;
        ArrayList<File> dirList = new ArrayList<File>();
        File startDir = new File(startDirPath);
        if (!startDir.isDirectory()) {
            return new File[0];
        }
        File[] fileArray = startDir.listFiles(FileUtils::listDirs$lambda-4);
        if (fileArray == null) {
            return new File[0];
        }
        File[] dirs = fileArray;
        if (excludeDirs1 == null) {
            excludeDirs1 = new String[]{};
        }
        for (File dir : dirs) {
            File file = dir.getAbsoluteFile();
            Object object = excludeDirs1;
            boolean bl = false;
            CharSequence charSequence = ArraysKt.contentDeepToString((Object[])object);
            object = file.getName();
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"file.name");
            if (StringsKt.contains$default((CharSequence)charSequence, (CharSequence)((CharSequence)object), (boolean)false, (int)2, null)) continue;
            dirList.add(file);
        }
        int n = sortType;
        switch (n) {
            case 0: {
                Collections.sort((List)dirList, (Comparator)new SortByName());
                break;
            }
            case 1: {
                Collections.sort((List)dirList, (Comparator)new SortByName());
                CollectionsKt.reverse((List)dirList);
                break;
            }
            case 2: {
                Collections.sort((List)dirList, (Comparator)new SortByTime());
                break;
            }
            case 3: {
                Collections.sort((List)dirList, (Comparator)new SortByTime());
                CollectionsKt.reverse((List)dirList);
                break;
            }
            case 4: {
                Collections.sort((List)dirList, (Comparator)new SortBySize());
                break;
            }
            case 5: {
                Collections.sort((List)dirList, (Comparator)new SortBySize());
                CollectionsKt.reverse((List)dirList);
                break;
            }
            case 6: {
                Collections.sort((List)dirList, (Comparator)new SortByExtension());
                break;
            }
            case 7: {
                Collections.sort((List)dirList, (Comparator)new SortByExtension());
                CollectionsKt.reverse((List)dirList);
            }
        }
        Collection $this$toTypedArray$iv = dirList;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        File[] fileArray2 = thisCollection$iv.toArray(new File[0]);
        if (fileArray2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return fileArray2;
    }

    public static /* synthetic */ File[] listDirs$default(FileUtils fileUtils, String string, String[] stringArray, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            stringArray = null;
        }
        if ((n2 & 4) != 0) {
            n = 0;
        }
        return fileUtils.listDirs(string, stringArray, n);
    }

    @JvmOverloads
    @Nullable
    public final File[] listDirsAndFiles(@NotNull String startDirPath, @Nullable String[] allowExtensions) {
        Intrinsics.checkNotNullParameter((Object)startDirPath, (String)"startDirPath");
        Object[] dirs = null;
        Object[] files = allowExtensions == null ? FileUtils.listFiles$default((FileUtils)this, (String)startDirPath, null, (int)0, (int)6, null) : this.listFiles(startDirPath, allowExtensions);
        dirs = FileUtils.listDirs$default((FileUtils)this, (String)startDirPath, null, (int)0, (int)6, null);
        if (files == null) {
            return null;
        }
        return (File[])ArraysKt.plus((Object[])dirs, (Object[])files);
    }

    public static /* synthetic */ File[] listDirsAndFiles$default(FileUtils fileUtils, String string, String[] stringArray, int n, Object object) {
        if ((n & 2) != 0) {
            stringArray = null;
        }
        return fileUtils.listDirsAndFiles(string, stringArray);
    }

    @JvmOverloads
    @NotNull
    public final File[] listFiles(@NotNull String startDirPath, @Nullable Pattern filterPattern, int sortType) {
        File[] files;
        Intrinsics.checkNotNullParameter((Object)startDirPath, (String)"startDirPath");
        ArrayList<File> fileList = new ArrayList<File>();
        File f = new File(startDirPath);
        if (!f.isDirectory()) {
            return new File[0];
        }
        File[] fileArray = f.listFiles(arg_0 -> FileUtils.listFiles$lambda-5(filterPattern, arg_0));
        if (fileArray == null) {
            return new File[0];
        }
        for (File file : files = fileArray) {
            fileList.add(file.getAbsoluteFile());
        }
        int n = sortType;
        switch (n) {
            case 0: {
                Collections.sort((List)fileList, (Comparator)new SortByName());
                break;
            }
            case 1: {
                Collections.sort((List)fileList, (Comparator)new SortByName());
                CollectionsKt.reverse((List)fileList);
                break;
            }
            case 2: {
                Collections.sort((List)fileList, (Comparator)new SortByTime());
                break;
            }
            case 3: {
                Collections.sort((List)fileList, (Comparator)new SortByTime());
                CollectionsKt.reverse((List)fileList);
                break;
            }
            case 4: {
                Collections.sort((List)fileList, (Comparator)new SortBySize());
                break;
            }
            case 5: {
                Collections.sort((List)fileList, (Comparator)new SortBySize());
                CollectionsKt.reverse((List)fileList);
                break;
            }
            case 6: {
                Collections.sort((List)fileList, (Comparator)new SortByExtension());
                break;
            }
            case 7: {
                Collections.sort((List)fileList, (Comparator)new SortByExtension());
                CollectionsKt.reverse((List)fileList);
            }
        }
        Collection $this$toTypedArray$iv = fileList;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        File[] fileArray2 = thisCollection$iv.toArray(new File[0]);
        if (fileArray2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return fileArray2;
    }

    public static /* synthetic */ File[] listFiles$default(FileUtils fileUtils, String string, Pattern pattern, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            pattern = null;
        }
        if ((n2 & 4) != 0) {
            n = 0;
        }
        return fileUtils.listFiles(string, pattern, n);
    }

    @Nullable
    public final File[] listFiles(@NotNull String startDirPath, @Nullable String[] allowExtensions) {
        Intrinsics.checkNotNullParameter((Object)startDirPath, (String)"startDirPath");
        File file = new File(startDirPath);
        return file.listFiles((arg_0, arg_1) -> FileUtils.listFiles$lambda-6(allowExtensions, arg_0, arg_1));
    }

    @Nullable
    public final File[] listFiles(@NotNull String startDirPath, @Nullable String allowExtension) {
        File[] fileArray;
        Intrinsics.checkNotNullParameter((Object)startDirPath, (String)"startDirPath");
        if (allowExtension == null) {
            fileArray = this.listFiles(startDirPath, null);
        } else {
            String[] stringArray = new String[]{allowExtension};
            fileArray = this.listFiles(startDirPath, stringArray);
        }
        return fileArray;
    }

    public final boolean exist(@NotNull String path) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        File file = new File(path);
        return file.exists();
    }

    @JvmOverloads
    public final boolean delete(@NotNull File file, boolean deleteRootDir) {
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        boolean result2 = false;
        if (file.isFile()) {
            result2 = this.deleteResolveEBUSY(file);
        } else {
            File[] files;
            File[] fileArray = file.listFiles();
            if (fileArray == null) {
                return false;
            }
            fileArray = files = fileArray;
            int n = 0;
            if (fileArray.length == 0) {
                result2 = deleteRootDir && this.deleteResolveEBUSY(file);
            } else {
                for (File f : files) {
                    Intrinsics.checkNotNullExpressionValue((Object)f, (String)"f");
                    this.delete(f, deleteRootDir);
                    result2 = this.deleteResolveEBUSY(f);
                }
            }
            if (deleteRootDir) {
                result2 = this.deleteResolveEBUSY(file);
            }
        }
        return result2;
    }

    public static /* synthetic */ boolean delete$default(FileUtils fileUtils, File file, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return fileUtils.delete(file, bl);
    }

    private final boolean deleteResolveEBUSY(File file) {
        File to = new File(Intrinsics.stringPlus((String)file.getAbsolutePath(), (Object)System.currentTimeMillis()));
        file.renameTo(to);
        return to.delete();
    }

    @JvmOverloads
    public final boolean delete(@NotNull String path, boolean deleteRootDir) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        File file = new File(path);
        return file.exists() ? this.delete(file, deleteRootDir) : false;
    }

    public static /* synthetic */ boolean delete$default(FileUtils fileUtils, String string, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return fileUtils.delete(string, bl);
    }

    public final boolean copy(@NotNull String src, @NotNull String tar) {
        Intrinsics.checkNotNullParameter((Object)src, (String)"src");
        Intrinsics.checkNotNullParameter((Object)tar, (String)"tar");
        File srcFile = new File(src);
        return srcFile.exists() && this.copy(srcFile, new File(tar));
    }

    public final boolean copy(@NotNull File src, @NotNull File tar) {
        Intrinsics.checkNotNullParameter((Object)src, (String)"src");
        Intrinsics.checkNotNullParameter((Object)tar, (String)"tar");
        try {
            if (src.isFile()) {
                int len;
                FileInputStream is = new FileInputStream(src);
                FileOutputStream op = new FileOutputStream(tar);
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(op);
                byte[] bt = new byte[8192];
                while ((len = bis.read(bt)) != -1) {
                    bos.write(bt, 0, len);
                }
                bis.close();
                bos.close();
            } else if (src.isDirectory()) {
                tar.mkdirs();
                File[] is = src.listFiles();
                if (is != null) {
                    File[] $this$forEach$iv = is;
                    boolean $i$f$forEach = false;
                    File[] fileArray = $this$forEach$iv;
                    int n = fileArray.length;
                    for (int i = 0; i < n; ++i) {
                        File element$iv;
                        File file = element$iv = fileArray[i];
                        boolean bl = false;
                        File file2 = file.getAbsoluteFile();
                        Intrinsics.checkNotNullExpressionValue((Object)file2, (String)"file.absoluteFile");
                        INSTANCE.copy(file2, new File(tar.getAbsoluteFile(), file.getName()));
                    }
                }
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public final boolean move(@NotNull String src, @NotNull String tar) {
        Intrinsics.checkNotNullParameter((Object)src, (String)"src");
        Intrinsics.checkNotNullParameter((Object)tar, (String)"tar");
        return this.move(new File(src), new File(tar));
    }

    public final boolean move(@NotNull File src, @NotNull File tar) {
        Intrinsics.checkNotNullParameter((Object)src, (String)"src");
        Intrinsics.checkNotNullParameter((Object)tar, (String)"tar");
        return this.rename(src, tar);
    }

    public final boolean rename(@NotNull String oldPath, @NotNull String newPath) {
        Intrinsics.checkNotNullParameter((Object)oldPath, (String)"oldPath");
        Intrinsics.checkNotNullParameter((Object)newPath, (String)"newPath");
        return this.rename(new File(oldPath), new File(newPath));
    }

    public final boolean rename(@NotNull File src, @NotNull File tar) {
        Intrinsics.checkNotNullParameter((Object)src, (String)"src");
        Intrinsics.checkNotNullParameter((Object)tar, (String)"tar");
        return src.renameTo(tar);
    }

    /*
     * WARNING - void declaration
     */
    @JvmOverloads
    @NotNull
    public final String readText(@NotNull String filepath, @NotNull String charset) {
        Intrinsics.checkNotNullParameter((Object)filepath, (String)"filepath");
        Intrinsics.checkNotNullParameter((Object)charset, (String)"charset");
        try {
            byte[] data = this.readBytes(filepath);
            if (data != null) {
                void $this$trim$iv;
                Object object = Charset.forName(charset);
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"forName(charset)");
                boolean bl = false;
                object = new String(data, (Charset)object);
                boolean $i$f$trim = false;
                CharSequence $this$trim$iv$iv = (CharSequence)$this$trim$iv;
                boolean $i$f$trim2 = false;
                int startIndex$iv$iv = 0;
                int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                boolean startFound$iv$iv = false;
                while (startIndex$iv$iv <= endIndex$iv$iv) {
                    boolean match$iv$iv;
                    int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                    char it = $this$trim$iv$iv.charAt(index$iv$iv);
                    boolean bl2 = false;
                    boolean bl3 = match$iv$iv = Intrinsics.compare((int)it, (int)32) <= 0;
                    if (!startFound$iv$iv) {
                        if (!match$iv$iv) {
                            startFound$iv$iv = true;
                            continue;
                        }
                        ++startIndex$iv$iv;
                        continue;
                    }
                    if (!match$iv$iv) break;
                    --endIndex$iv$iv;
                }
                return ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
            }
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        return "";
    }

    public static /* synthetic */ String readText$default(FileUtils fileUtils, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = "utf-8";
        }
        return fileUtils.readText(string, string2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final byte[] readBytes(@NotNull String filepath) {
        Intrinsics.checkNotNullParameter((Object)filepath, (String)"filepath");
        FileInputStream fis = null;
        try {
            int len;
            fis = new FileInputStream(filepath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] data = baos.toByteArray();
            baos.close();
            byte[] byArray = data;
            return byArray;
        }
        catch (IOException e) {
            byte[] byArray = null;
            return byArray;
        }
        finally {
            this.closeSilently((Closeable)fis);
        }
    }

    @JvmOverloads
    public final boolean writeText(@NotNull String filepath, @NotNull String content, @NotNull String charset) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)filepath, (String)"filepath");
        Intrinsics.checkNotNullParameter((Object)content, (String)"content");
        Intrinsics.checkNotNullParameter((Object)charset, (String)"charset");
        try {
            String string = content;
            boolean bl2 = false;
            Charset charset2 = Charset.forName(charset);
            Intrinsics.checkNotNullExpressionValue((Object)charset2, (String)"Charset.forName(charsetName)");
            Charset charset3 = charset2;
            boolean bl3 = false;
            byte[] byArray = string.getBytes(charset3);
            Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"(this as java.lang.String).getBytes(charset)");
            bl = this.writeBytes(filepath, byArray);
        }
        catch (UnsupportedEncodingException e) {
            bl = false;
        }
        return bl;
    }

    public static /* synthetic */ boolean writeText$default(FileUtils fileUtils, String string, String string2, String string3, int n, Object object) {
        if ((n & 4) != 0) {
            string3 = "utf-8";
        }
        return fileUtils.writeText(string, string2, string3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final boolean writeBytes(@NotNull String filepath, @NotNull byte[] data) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)filepath, (String)"filepath");
        Intrinsics.checkNotNullParameter((Object)data, (String)"data");
        File file = new File(filepath);
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                File file2 = file.getParentFile();
                if (file2 != null) {
                    file2.mkdirs();
                }
                file.createNewFile();
            }
            fos = new FileOutputStream(filepath);
            fos.write(data);
            bl = true;
        }
        catch (IOException e) {
            bl = false;
        }
        finally {
            this.closeSilently((Closeable)fos);
        }
        return bl;
    }

    public final boolean writeInputStream(@NotNull String filepath, @NotNull InputStream data) {
        Intrinsics.checkNotNullParameter((Object)filepath, (String)"filepath");
        Intrinsics.checkNotNullParameter((Object)data, (String)"data");
        File file = new File(filepath);
        return this.writeInputStream(file, data);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final boolean writeInputStream(@NotNull File file, @NotNull InputStream data) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        Intrinsics.checkNotNullParameter((Object)data, (String)"data");
        FileOutputStream fos = null;
        try {
            int len;
            if (!file.exists()) {
                File file2 = file.getParentFile();
                if (file2 != null) {
                    file2.mkdirs();
                }
                file.createNewFile();
            }
            byte[] buffer = new byte[4096];
            fos = new FileOutputStream(file);
            while ((len = data.read(buffer, 0, buffer.length)) != -1) {
                fos.write(buffer, 0, len);
            }
            data.close();
            fos.flush();
            bl = true;
        }
        catch (IOException e) {
            bl = false;
        }
        finally {
            this.closeSilently((Closeable)fos);
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final boolean appendText(@NotNull String path, @NotNull String content) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        Intrinsics.checkNotNullParameter((Object)content, (String)"content");
        File file = new File(path);
        FileWriter writer = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            writer.write(content);
            bl = true;
        }
        catch (IOException e) {
            bl = false;
        }
        finally {
            this.closeSilently((Closeable)writer);
        }
        return bl;
    }

    public final long getLength(@NotNull String path) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        File file = new File(path);
        return !file.isFile() || !file.exists() ? 0L : file.length();
    }

    @NotNull
    public final String getName(@Nullable String pathOrUrl) {
        String string;
        if (pathOrUrl == null) {
            return "";
        }
        int pos = StringsKt.lastIndexOf$default((CharSequence)pathOrUrl, (char)'/', (int)0, (boolean)false, (int)6, null);
        if (0 <= pos) {
            String string2 = pathOrUrl;
            int n = pos + 1;
            boolean bl = false;
            String string3 = string2.substring(n);
            string = string3;
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.String).substring(startIndex)");
        } else {
            string = "" + System.currentTimeMillis() + '.' + this.getExtension(pathOrUrl);
        }
        return string;
    }

    @NotNull
    public final String getNameExcludeExtension(@NotNull String path) {
        String string;
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        try {
            String fileName;
            String string2 = fileName = new File(path).getName();
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"fileName");
            int lastIndexOf = StringsKt.lastIndexOf$default((CharSequence)string2, (String)".", (int)0, (boolean)false, (int)6, null);
            if (lastIndexOf != -1) {
                string2 = fileName;
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"fileName");
                int n = 0;
                boolean bl = false;
                String string3 = string2.substring(n, lastIndexOf);
                Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                fileName = string3;
            }
            string = fileName;
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"{\n            var fileName = File(path).name\n            val lastIndexOf = fileName.lastIndexOf(\".\")\n            if (lastIndexOf != -1) {\n                fileName = fileName.substring(0, lastIndexOf)\n            }\n            fileName\n        }");
        }
        catch (Exception e) {
            string = "";
        }
        return string;
    }

    @NotNull
    public final String getSize(@NotNull String path) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        long fileSize = this.getLength(path);
        return this.toFileSizeString(fileSize);
    }

    @NotNull
    public final String toFileSizeString(long fileSize) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = null;
        fileSizeString = fileSize < 1024L ? "" + fileSize + 'B' : (fileSize < 0x100000L ? Intrinsics.stringPlus((String)df.format((double)fileSize / (double)1024L), (Object)"K") : (fileSize < 0x40000000L ? Intrinsics.stringPlus((String)df.format((double)fileSize / (double)0x100000L), (Object)"M") : Intrinsics.stringPlus((String)df.format((double)fileSize / (double)0x40000000L), (Object)"G")));
        return fileSizeString;
    }

    @NotNull
    public final String getExtension(@NotNull String pathOrUrl) {
        String string;
        Intrinsics.checkNotNullParameter((Object)pathOrUrl, (String)"pathOrUrl");
        int dotPos = StringsKt.lastIndexOf$default((CharSequence)pathOrUrl, (char)'.', (int)0, (boolean)false, (int)6, null);
        if (0 <= dotPos) {
            String string2 = pathOrUrl;
            int n = dotPos + 1;
            boolean bl = false;
            String string3 = string2.substring(n);
            string = string3;
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"(this as java.lang.String).substring(startIndex)");
        } else {
            string = "ext";
        }
        return string;
    }

    @NotNull
    public final String getFileExtetion(@NotNull String url2, @NotNull String defaultExt) {
        Intrinsics.checkNotNullParameter((Object)url2, (String)"url");
        Intrinsics.checkNotNullParameter((Object)defaultExt, (String)"defaultExt");
        try {
            String string;
            String[] stringArray = new String[]{"?"};
            List seqs = StringsKt.split((CharSequence)url2, (String[])stringArray, (boolean)true, (int)2);
            String[] stringArray2 = new String[]{"/"};
            String file = (String)CollectionsKt.last((List)StringsKt.split$default((CharSequence)((CharSequence)seqs.get(0)), (String[])stringArray2, (boolean)false, (int)0, (int)6, null));
            int dotPos = StringsKt.lastIndexOf$default((CharSequence)file, (char)'.', (int)0, (boolean)false, (int)6, null);
            if (0 <= dotPos) {
                String string2 = file;
                int n = dotPos + 1;
                boolean bl = false;
                String string3 = string2;
                if (string3 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string4 = string3.substring(n);
                string = string4;
                Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"(this as java.lang.String).substring(startIndex)");
            } else {
                string = defaultExt;
            }
            return string;
        }
        catch (Exception e) {
            return defaultExt;
        }
    }

    public static /* synthetic */ String getFileExtetion$default(FileUtils fileUtils, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = "";
        }
        return fileUtils.getFileExtetion(string, string2);
    }

    @NotNull
    public final String getMimeType(@NotNull String pathOrUrl) {
        Intrinsics.checkNotNullParameter((Object)pathOrUrl, (String)"pathOrUrl");
        throw new Exception("Not implemented");
    }

    @JvmOverloads
    @NotNull
    public final String getDateTime(@NotNull String path, @NotNull String format) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        Intrinsics.checkNotNullParameter((Object)format, (String)"format");
        File file = new File(path);
        return this.getDateTime(file, format);
    }

    public static /* synthetic */ String getDateTime$default(FileUtils fileUtils, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = "yyyy\u5e74MM\u6708dd\u65e5HH:mm";
        }
        return fileUtils.getDateTime(string, string2);
    }

    @NotNull
    public final String getDateTime(@NotNull File file, @NotNull String format) {
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        Intrinsics.checkNotNullParameter((Object)format, (String)"format");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(file.lastModified());
        String string = new SimpleDateFormat(format, Locale.PRC).format(cal.getTime());
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"SimpleDateFormat(format, Locale.PRC).format(cal.time)");
        return string;
    }

    public final int compareLastModified(@NotNull String path1, @NotNull String path2) {
        Intrinsics.checkNotNullParameter((Object)path1, (String)"path1");
        Intrinsics.checkNotNullParameter((Object)path2, (String)"path2");
        long stamp1 = new File(path1).lastModified();
        long stamp2 = new File(path2).lastModified();
        return stamp1 > stamp2 ? 1 : (stamp1 < stamp2 ? -1 : 0);
    }

    public final boolean makeDirs(@NotNull String path) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        return this.makeDirs(new File(path));
    }

    public final boolean makeDirs(@NotNull File file) {
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        return file.mkdirs();
    }

    @JvmOverloads
    @NotNull
    public final File[] listDirs(@NotNull String startDirPath, @Nullable String[] excludeDirs) {
        Intrinsics.checkNotNullParameter((Object)startDirPath, (String)"startDirPath");
        return FileUtils.listDirs$default((FileUtils)this, (String)startDirPath, (String[])excludeDirs, (int)0, (int)4, null);
    }

    @JvmOverloads
    @NotNull
    public final File[] listDirs(@NotNull String startDirPath) {
        Intrinsics.checkNotNullParameter((Object)startDirPath, (String)"startDirPath");
        return FileUtils.listDirs$default((FileUtils)this, (String)startDirPath, null, (int)0, (int)6, null);
    }

    @JvmOverloads
    @Nullable
    public final File[] listDirsAndFiles(@NotNull String startDirPath) {
        Intrinsics.checkNotNullParameter((Object)startDirPath, (String)"startDirPath");
        return FileUtils.listDirsAndFiles$default((FileUtils)this, (String)startDirPath, null, (int)2, null);
    }

    @JvmOverloads
    @NotNull
    public final File[] listFiles(@NotNull String startDirPath, @Nullable Pattern filterPattern) {
        Intrinsics.checkNotNullParameter((Object)startDirPath, (String)"startDirPath");
        return FileUtils.listFiles$default((FileUtils)this, (String)startDirPath, (Pattern)filterPattern, (int)0, (int)4, null);
    }

    @JvmOverloads
    @NotNull
    public final File[] listFiles(@NotNull String startDirPath) {
        Intrinsics.checkNotNullParameter((Object)startDirPath, (String)"startDirPath");
        return FileUtils.listFiles$default((FileUtils)this, (String)startDirPath, null, (int)0, (int)6, null);
    }

    @JvmOverloads
    public final boolean delete(@NotNull File file) {
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        return FileUtils.delete$default((FileUtils)this, (File)file, (boolean)false, (int)2, null);
    }

    @JvmOverloads
    public final boolean delete(@NotNull String path) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        return FileUtils.delete$default((FileUtils)this, (String)path, (boolean)false, (int)2, null);
    }

    @JvmOverloads
    @NotNull
    public final String readText(@NotNull String filepath) {
        Intrinsics.checkNotNullParameter((Object)filepath, (String)"filepath");
        return FileUtils.readText$default((FileUtils)this, (String)filepath, null, (int)2, null);
    }

    @JvmOverloads
    public final boolean writeText(@NotNull String filepath, @NotNull String content) {
        Intrinsics.checkNotNullParameter((Object)filepath, (String)"filepath");
        Intrinsics.checkNotNullParameter((Object)content, (String)"content");
        return FileUtils.writeText$default((FileUtils)this, (String)filepath, (String)content, null, (int)4, null);
    }

    @JvmOverloads
    @NotNull
    public final String getDateTime(@NotNull String path) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        return FileUtils.getDateTime$default((FileUtils)this, (String)path, null, (int)2, null);
    }

    private static final boolean listDirs$lambda-4(File f) {
        if (f == null) {
            return false;
        }
        return f.isDirectory();
    }

    private static final boolean listFiles$lambda-5(Pattern $filterPattern, File file) {
        boolean bl;
        Matcher matcher;
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            return false;
        }
        Pattern pattern = $filterPattern;
        return pattern == null ? true : ((matcher = pattern.matcher(file.getName())) == null ? true : (bl = matcher.find()));
    }

    private static final boolean listFiles$lambda-6(String[] $allowExtensions, File $noName_0, String name) {
        boolean bl;
        Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
        String extension = INSTANCE.getExtension(name);
        Object[] objectArray = $allowExtensions;
        if (objectArray == null) {
            bl = false;
        } else {
            Object[] objectArray2 = objectArray;
            boolean bl2 = false;
            String string = ArraysKt.contentDeepToString((Object[])objectArray2);
            bl = string == null ? false : StringsKt.contains$default((CharSequence)string, (CharSequence)extension, (boolean)false, (int)2, null);
        }
        return bl || $allowExtensions == null;
    }
}

