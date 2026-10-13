/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  io.legado.app.constant.AppPattern
 *  io.legado.app.data.entities.BaseBook
 *  io.legado.app.data.entities.BaseBook$DefaultImpls
 *  io.legado.app.data.entities.Book
 *  io.legado.app.data.entities.Book$Companion
 *  io.legado.app.data.entities.Book$ReadConfig
 *  io.legado.app.data.entities.BookKt
 *  io.legado.app.data.entities.SearchBook
 *  io.legado.app.model.localBook.CbzFile
 *  io.legado.app.model.localBook.EpubFile
 *  io.legado.app.model.localBook.UmdFile
 *  io.legado.app.utils.FileUtils
 *  io.legado.app.utils.GsonExtensionsKt
 *  io.legado.app.utils.MD5Utils
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.io.FilesKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 */
package io.legado.app.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.legado.app.constant.AppPattern;
import io.legado.app.data.entities.BaseBook;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookKt;
import io.legado.app.data.entities.SearchBook;
import io.legado.app.model.localBook.CbzFile;
import io.legado.app.model.localBook.EpubFile;
import io.legado.app.model.localBook.UmdFile;
import io.legado.app.utils.FileUtils;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.MD5Utils;
import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@JsonIgnoreProperties(value={"variableMap", "infoHtml", "tocHtml", "config", "rootDir", "localBook", "epub", "epubRootDir", "onLineTxt", "localTxt", "umd", "realAuthor", "unreadChapterNum", "folderName", "pdfImageWidth", "localFile", "kindList", "_userNameSpace", "bookDir", "userNameSpace"})
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\bU\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b+\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\b\u0087\b\u0018\u0000 \u00d5\u00012\u00020\u0001:\u0006\u00d5\u0001\u00d6\u0001\u00d7\u0001B\u00e9\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0013\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u001f\u0012\b\b\u0002\u0010 \u001a\u00020\u0011\u0012\b\b\u0002\u0010!\u001a\u00020\u0011\u0012\b\b\u0002\u0010\"\u001a\u00020\u001f\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%\u0012\b\b\u0002\u0010&\u001a\u00020\u001f\u0012\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010(J\n\u0010\u0083\u0001\u001a\u00020\u0003H\u00c6\u0003J\f\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u0085\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u0086\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u0087\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u0088\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u0089\u0001\u001a\u00020\u0013H\u00c6\u0003J\f\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u008b\u0001\u001a\u00020\u0013H\u00c6\u0003J\n\u0010\u008c\u0001\u001a\u00020\u0013H\u00c6\u0003J\n\u0010\u008d\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u008e\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u008f\u0001\u001a\u00020\u0011H\u00c6\u0003J\f\u0010\u0090\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u0091\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u0092\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u0093\u0001\u001a\u00020\u0013H\u00c6\u0003J\f\u0010\u0094\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u0095\u0001\u001a\u00020\u001fH\u00c6\u0003J\n\u0010\u0096\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u0097\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u0098\u0001\u001a\u00020\u001fH\u00c6\u0003J\n\u0010\u0099\u0001\u001a\u00020\u0003H\u00c6\u0003J\f\u0010\u009a\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u009b\u0001\u001a\u0004\u0018\u00010%H\u00c6\u0003J\n\u0010\u009c\u0001\u001a\u00020\u001fH\u00c6\u0003J\f\u0010\u009d\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u009e\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u009f\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a0\u0001\u001a\u00020\u0003H\u00c6\u0003J\f\u0010\u00a1\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u00a2\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u00a3\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u00a4\u0001\u001a\u00020%H\u0002J\u00ee\u0002\u0010\u00a5\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u00112\b\b\u0002\u0010\u0018\u001a\u00020\u00112\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u00112\b\b\u0002\u0010\u001b\u001a\u00020\u00112\b\b\u0002\u0010\u001c\u001a\u00020\u00132\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010 \u001a\u00020\u00112\b\b\u0002\u0010!\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020\u001f2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%2\b\b\u0002\u0010&\u001a\u00020\u001f2\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0016\u0010\u00a6\u0001\u001a\u00020\u001f2\n\u0010\u00a7\u0001\u001a\u0005\u0018\u00010\u00a8\u0001H\u0096\u0002J\b\u0010\u00a9\u0001\u001a\u00030\u00aa\u0001J\u0007\u0010\u00ab\u0001\u001a\u00020\u0003J\u0010\u0010\u00ac\u0001\u001a\u00020\u001f2\u0007\u0010\u00ad\u0001\u001a\u00020\u0013J\t\u0010\u00ae\u0001\u001a\u0004\u0018\u00010\u0003J\t\u0010\u00af\u0001\u001a\u0004\u0018\u00010\u0003J\u0007\u0010\u00b0\u0001\u001a\u00020\u0003J\u0007\u0010\u00b1\u0001\u001a\u00020\u0003J\b\u0010\u00b2\u0001\u001a\u00030\u00b3\u0001J\b\u0010\u00b4\u0001\u001a\u00030\u00b5\u0001J\u0007\u0010\u00b6\u0001\u001a\u00020\u0003J\u0007\u0010\u00b7\u0001\u001a\u00020\u001fJ\u0007\u0010\u00b8\u0001\u001a\u00020\u0011J\t\u0010\u00b9\u0001\u001a\u00020\u0003H\u0016J\t\u0010\u00ba\u0001\u001a\u00020\u0011H\u0016J\u0007\u0010\u00bb\u0001\u001a\u00020\u001fJ\u0007\u0010\u00bc\u0001\u001a\u00020\u001fJ\u0007\u0010\u00bd\u0001\u001a\u00020\u001fJ\u0007\u0010\u00be\u0001\u001a\u00020\u001fJ\u0007\u0010\u00bf\u0001\u001a\u00020\u001fJ\u0007\u0010\u00c0\u0001\u001a\u00020\u001fJ\u0007\u0010\u00c1\u0001\u001a\u00020\u001fJ\u0007\u0010\u00c2\u0001\u001a\u00020\u001fJ\u0007\u0010\u00c3\u0001\u001a\u00020\u001fJ\u001e\u0010\u00c4\u0001\u001a\u00030\u00c5\u00012\u0007\u0010\u00c6\u0001\u001a\u00020\u00032\t\u0010\u00c7\u0001\u001a\u0004\u0018\u00010\u0003H\u0016J\u0011\u0010\u00c8\u0001\u001a\u00030\u00c5\u00012\u0007\u0010\u00ad\u0001\u001a\u00020\u0013J\u0012\u0010\u00c9\u0001\u001a\u00030\u00c5\u00012\b\u0010\u00ca\u0001\u001a\u00030\u00b5\u0001J\u0011\u0010\u00cb\u0001\u001a\u00030\u00c5\u00012\u0007\u0010\u00cc\u0001\u001a\u00020\u0003J\u0011\u0010\u00cd\u0001\u001a\u00030\u00c5\u00012\u0007\u0010\u00ce\u0001\u001a\u00020\u0003J\b\u0010\u00cf\u0001\u001a\u00030\u00d0\u0001J\n\u0010\u00d1\u0001\u001a\u00020\u0003H\u00d6\u0001J\u0013\u0010\u00d2\u0001\u001a\u00030\u00c5\u00012\t\b\u0002\u0010\u00d3\u0001\u001a\u00020\u001fJ\u0007\u0010\u00d4\u0001\u001a\u00020\u0003R\u000e\u0010)\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010+\"\u0004\b/\u0010-R\u001a\u0010\u001e\u001a\u00020\u001fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010+\"\u0004\b5\u0010-R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u0010+\"\u0004\b7\u0010-R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u0010+\"\u0004\b9\u0010-R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b:\u0010+\"\u0004\b;\u0010-R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u0010+\"\u0004\b=\u0010-R\u001a\u0010\u001a\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u001a\u0010\u001b\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010?\"\u0004\bC\u0010AR\u001a\u0010\u001c\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bH\u0010+\"\u0004\bI\u0010-R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010E\"\u0004\bK\u0010GR\u001c\u0010L\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bM\u0010+\"\u0004\bN\u0010-R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bO\u0010+\"\u0004\bP\u0010-R\u001c\u0010&\u001a\u00020\u001f8\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u00101\"\u0004\bQ\u00103R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bR\u0010+\"\u0004\bS\u0010-R\u001a\u0010\u0017\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bT\u0010?\"\u0004\bU\u0010AR\u001c\u0010'\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bV\u0010+\"\u0004\bW\u0010-R\u001a\u0010\u0016\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bX\u0010E\"\u0004\bY\u0010GR\u001a\u0010\u0015\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010E\"\u0004\b[\u0010GR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010+\"\u0004\b]\u0010-R\u001a\u0010\u0007\u001a\u00020\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b^\u0010+\"\u0004\b_\u0010-R\u001a\u0010 \u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b`\u0010?\"\u0004\ba\u0010AR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bb\u0010+\"\u0004\bc\u0010-R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bd\u0010+\"\u0004\be\u0010-R\u001a\u0010!\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bf\u0010?\"\u0004\bg\u0010AR\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR\u000e\u0010l\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010m\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bn\u0010+\"\u0004\bo\u0010-R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bp\u0010+\"\u0004\bq\u0010-R\u001a\u0010\u0018\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\br\u0010?\"\u0004\bs\u0010AR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bt\u0010?\"\u0004\bu\u0010AR\u001a\u0010\"\u001a\u00020\u001fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bv\u00101\"\u0004\bw\u00103R\u001c\u0010#\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bx\u0010+\"\u0004\by\u0010-R8\u0010z\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030{j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003`|8VX\u0096\u0084\u0002\u00a2\u0006\r\n\u0005\b\u007f\u0010\u0080\u0001\u001a\u0004\b}\u0010~R\u001e\u0010\u001d\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0081\u0001\u0010+\"\u0005\b\u0082\u0001\u0010-\u00a8\u0006\u00d8\u0001"}, d2={"Lio/legado/app/data/entities/Book;", "Lio/legado/app/data/entities/BaseBook;", "bookUrl", "", "tocUrl", "origin", "originName", "name", "author", "kind", "customTag", "coverUrl", "customCoverUrl", "intro", "customIntro", "charset", "type", "", "group", "", "latestChapterTitle", "latestChapterTime", "lastCheckTime", "lastCheckCount", "totalChapterNum", "durChapterTitle", "durChapterIndex", "durChapterPos", "durChapterTime", "wordCount", "canUpdate", "", "order", "originOrder", "useReplaceRule", "variable", "readConfig", "Lio/legado/app/data/entities/Book$ReadConfig;", "isInShelf", "lastCheckError", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IJLjava/lang/String;JJIILjava/lang/String;IIJLjava/lang/String;ZIIZLjava/lang/String;Lio/legado/app/data/entities/Book$ReadConfig;ZLjava/lang/String;)V", "_userNameSpace", "getAuthor", "()Ljava/lang/String;", "setAuthor", "(Ljava/lang/String;)V", "getBookUrl", "setBookUrl", "getCanUpdate", "()Z", "setCanUpdate", "(Z)V", "getCharset", "setCharset", "getCoverUrl", "setCoverUrl", "getCustomCoverUrl", "setCustomCoverUrl", "getCustomIntro", "setCustomIntro", "getCustomTag", "setCustomTag", "getDurChapterIndex", "()I", "setDurChapterIndex", "(I)V", "getDurChapterPos", "setDurChapterPos", "getDurChapterTime", "()J", "setDurChapterTime", "(J)V", "getDurChapterTitle", "setDurChapterTitle", "getGroup", "setGroup", "infoHtml", "getInfoHtml", "setInfoHtml", "getIntro", "setIntro", "setInShelf", "getKind", "setKind", "getLastCheckCount", "setLastCheckCount", "getLastCheckError", "setLastCheckError", "getLastCheckTime", "setLastCheckTime", "getLatestChapterTime", "setLatestChapterTime", "getLatestChapterTitle", "setLatestChapterTitle", "getName", "setName", "getOrder", "setOrder", "getOrigin", "setOrigin", "getOriginName", "setOriginName", "getOriginOrder", "setOriginOrder", "getReadConfig", "()Lio/legado/app/data/entities/Book$ReadConfig;", "setReadConfig", "(Lio/legado/app/data/entities/Book$ReadConfig;)V", "rootDir", "tocHtml", "getTocHtml", "setTocHtml", "getTocUrl", "setTocUrl", "getTotalChapterNum", "setTotalChapterNum", "getType", "setType", "getUseReplaceRule", "setUseReplaceRule", "getVariable", "setVariable", "variableMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getVariableMap", "()Ljava/util/HashMap;", "variableMap$delegate", "Lkotlin/Lazy;", "getWordCount", "setWordCount", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component4", "component5", "component6", "component7", "component8", "component9", "config", "copy", "equals", "other", "", "fileCharset", "Ljava/nio/charset/Charset;", "getBookDir", "getDelTag", "tag", "getDisplayCover", "getDisplayIntro", "getEpubRootDir", "getFolderName", "getLocalFile", "Ljava/io/File;", "getPdfImageWidth", "", "getRealAuthor", "getSplitLongChapter", "getUnreadChapterNum", "getUserNameSpace", "hashCode", "isCbz", "isEpub", "isLocalBook", "isLocalEpub", "isLocalPdf", "isLocalTxt", "isOnLineTxt", "isPdf", "isUmd", "putVariable", "", "key", "value", "setDelTag", "setPdfImageWidth", "pdfImageWidth", "setRootDir", "root", "setUserNameSpace", "nameSpace", "toSearchBook", "Lio/legado/app/data/entities/SearchBook;", "toString", "updateFromLocal", "onlyCover", "workRoot", "Companion", "Converters", "ReadConfig", "reader-pro"})
public final class Book
implements BaseBook {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String bookUrl;
    @NotNull
    private String tocUrl;
    @NotNull
    private String origin;
    @NotNull
    private String originName;
    @NotNull
    private String name;
    @NotNull
    private String author;
    @Nullable
    private String kind;
    @Nullable
    private String customTag;
    @Nullable
    private String coverUrl;
    @Nullable
    private String customCoverUrl;
    @Nullable
    private String intro;
    @Nullable
    private String customIntro;
    @Nullable
    private String charset;
    private int type;
    private long group;
    @Nullable
    private String latestChapterTitle;
    private long latestChapterTime;
    private long lastCheckTime;
    private int lastCheckCount;
    private int totalChapterNum;
    @Nullable
    private String durChapterTitle;
    private int durChapterIndex;
    private int durChapterPos;
    private long durChapterTime;
    @Nullable
    private String wordCount;
    private boolean canUpdate;
    private int order;
    private int originOrder;
    private boolean useReplaceRule;
    @Nullable
    private String variable;
    @Nullable
    private ReadConfig readConfig;
    private boolean isInShelf;
    @Nullable
    private String lastCheckError;
    @NotNull
    private final transient Lazy variableMap$delegate;
    @Nullable
    private String infoHtml;
    @Nullable
    private String tocHtml;
    @NotNull
    private transient String rootDir;
    @NotNull
    private transient String _userNameSpace;
    public static final long hTag = 2L;
    public static final long rubyTag = 4L;
    public static final long imgTag = 8L;
    @NotNull
    public static final String imgStyleDefault = "DEFAULT";
    @NotNull
    public static final String imgStyleFull = "FULL";
    @NotNull
    public static final String imgStyleText = "TEXT";

    public Book(@NotNull String bookUrl, @NotNull String tocUrl, @NotNull String origin, @NotNull String originName, @NotNull String name, @NotNull String author, @Nullable String kind, @Nullable String customTag, @Nullable String coverUrl, @Nullable String customCoverUrl, @Nullable String intro, @Nullable String customIntro, @Nullable String charset, int type, long group, @Nullable String latestChapterTitle, long latestChapterTime, long lastCheckTime, int lastCheckCount, int totalChapterNum, @Nullable String durChapterTitle, int durChapterIndex, int durChapterPos, long durChapterTime, @Nullable String wordCount, boolean canUpdate, int order, int originOrder, boolean useReplaceRule, @Nullable String variable, @Nullable ReadConfig readConfig, boolean isInShelf, @Nullable String lastCheckError) {
        Intrinsics.checkNotNullParameter((Object)bookUrl, (String)"bookUrl");
        Intrinsics.checkNotNullParameter((Object)tocUrl, (String)"tocUrl");
        Intrinsics.checkNotNullParameter((Object)origin, (String)"origin");
        Intrinsics.checkNotNullParameter((Object)originName, (String)"originName");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)author, (String)"author");
        this.bookUrl = bookUrl;
        this.tocUrl = tocUrl;
        this.origin = origin;
        this.originName = originName;
        this.name = name;
        this.author = author;
        this.kind = kind;
        this.customTag = customTag;
        this.coverUrl = coverUrl;
        this.customCoverUrl = customCoverUrl;
        this.intro = intro;
        this.customIntro = customIntro;
        this.charset = charset;
        this.type = type;
        this.group = group;
        this.latestChapterTitle = latestChapterTitle;
        this.latestChapterTime = latestChapterTime;
        this.lastCheckTime = lastCheckTime;
        this.lastCheckCount = lastCheckCount;
        this.totalChapterNum = totalChapterNum;
        this.durChapterTitle = durChapterTitle;
        this.durChapterIndex = durChapterIndex;
        this.durChapterPos = durChapterPos;
        this.durChapterTime = durChapterTime;
        this.wordCount = wordCount;
        this.canUpdate = canUpdate;
        this.order = order;
        this.originOrder = originOrder;
        this.useReplaceRule = useReplaceRule;
        this.variable = variable;
        this.readConfig = readConfig;
        this.isInShelf = isInShelf;
        this.lastCheckError = lastCheckError;
        this.variableMap$delegate = LazyKt.lazy((Function0)((Function0)new /* Unavailable Anonymous Inner Class!! */));
        this.rootDir = "";
        this._userNameSpace = "";
    }

    public /* synthetic */ Book(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, String string12, String string13, int n, long l, String string14, long l2, long l3, int n2, int n3, String string15, int n4, int n5, long l4, String string16, boolean bl, int n6, int n7, boolean bl2, String string17, ReadConfig readConfig, boolean bl3, String string18, int n8, int n9, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n8 & 1) != 0) {
            string = "";
        }
        if ((n8 & 2) != 0) {
            string2 = "";
        }
        if ((n8 & 4) != 0) {
            string3 = "loc_book";
        }
        if ((n8 & 8) != 0) {
            string4 = "";
        }
        if ((n8 & 0x10) != 0) {
            string5 = "";
        }
        if ((n8 & 0x20) != 0) {
            string6 = "";
        }
        if ((n8 & 0x40) != 0) {
            string7 = null;
        }
        if ((n8 & 0x80) != 0) {
            string8 = null;
        }
        if ((n8 & 0x100) != 0) {
            string9 = null;
        }
        if ((n8 & 0x200) != 0) {
            string10 = null;
        }
        if ((n8 & 0x400) != 0) {
            string11 = null;
        }
        if ((n8 & 0x800) != 0) {
            string12 = null;
        }
        if ((n8 & 0x1000) != 0) {
            string13 = null;
        }
        if ((n8 & 0x2000) != 0) {
            n = 0;
        }
        if ((n8 & 0x4000) != 0) {
            l = 0L;
        }
        if ((n8 & 0x8000) != 0) {
            string14 = null;
        }
        if ((n8 & 0x10000) != 0) {
            l2 = System.currentTimeMillis();
        }
        if ((n8 & 0x20000) != 0) {
            l3 = System.currentTimeMillis();
        }
        if ((n8 & 0x40000) != 0) {
            n2 = 0;
        }
        if ((n8 & 0x80000) != 0) {
            n3 = 0;
        }
        if ((n8 & 0x100000) != 0) {
            string15 = null;
        }
        if ((n8 & 0x200000) != 0) {
            n4 = 0;
        }
        if ((n8 & 0x400000) != 0) {
            n5 = 0;
        }
        if ((n8 & 0x800000) != 0) {
            l4 = System.currentTimeMillis();
        }
        if ((n8 & 0x1000000) != 0) {
            string16 = null;
        }
        if ((n8 & 0x2000000) != 0) {
            bl = true;
        }
        if ((n8 & 0x4000000) != 0) {
            n6 = 0;
        }
        if ((n8 & 0x8000000) != 0) {
            n7 = 0;
        }
        if ((n8 & 0x10000000) != 0) {
            bl2 = true;
        }
        if ((n8 & 0x20000000) != 0) {
            string17 = null;
        }
        if ((n8 & 0x40000000) != 0) {
            readConfig = null;
        }
        if ((n8 & Integer.MIN_VALUE) != 0) {
            bl3 = false;
        }
        if ((n9 & 1) != 0) {
            string18 = null;
        }
        this(string, string2, string3, string4, string5, string6, string7, string8, string9, string10, string11, string12, string13, n, l, string14, l2, l3, n2, n3, string15, n4, n5, l4, string16, bl, n6, n7, bl2, string17, readConfig, bl3, string18);
    }

    @NotNull
    public String getBookUrl() {
        return this.bookUrl;
    }

    public void setBookUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.bookUrl = string;
    }

    @NotNull
    public final String getTocUrl() {
        return this.tocUrl;
    }

    public final void setTocUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.tocUrl = string;
    }

    @NotNull
    public final String getOrigin() {
        return this.origin;
    }

    public final void setOrigin(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.origin = string;
    }

    @NotNull
    public final String getOriginName() {
        return this.originName;
    }

    public final void setOriginName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.originName = string;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    public void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.name = string;
    }

    @NotNull
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.author = string;
    }

    @Nullable
    public String getKind() {
        return this.kind;
    }

    public void setKind(@Nullable String string) {
        this.kind = string;
    }

    @Nullable
    public final String getCustomTag() {
        return this.customTag;
    }

    public final void setCustomTag(@Nullable String string) {
        this.customTag = string;
    }

    @Nullable
    public final String getCoverUrl() {
        return this.coverUrl;
    }

    public final void setCoverUrl(@Nullable String string) {
        this.coverUrl = string;
    }

    @Nullable
    public final String getCustomCoverUrl() {
        return this.customCoverUrl;
    }

    public final void setCustomCoverUrl(@Nullable String string) {
        this.customCoverUrl = string;
    }

    @Nullable
    public final String getIntro() {
        return this.intro;
    }

    public final void setIntro(@Nullable String string) {
        this.intro = string;
    }

    @Nullable
    public final String getCustomIntro() {
        return this.customIntro;
    }

    public final void setCustomIntro(@Nullable String string) {
        this.customIntro = string;
    }

    @Nullable
    public final String getCharset() {
        return this.charset;
    }

    public final void setCharset(@Nullable String string) {
        this.charset = string;
    }

    public final int getType() {
        return this.type;
    }

    public final void setType(int n) {
        this.type = n;
    }

    public final long getGroup() {
        return this.group;
    }

    public final void setGroup(long l) {
        this.group = l;
    }

    @Nullable
    public final String getLatestChapterTitle() {
        return this.latestChapterTitle;
    }

    public final void setLatestChapterTitle(@Nullable String string) {
        this.latestChapterTitle = string;
    }

    public final long getLatestChapterTime() {
        return this.latestChapterTime;
    }

    public final void setLatestChapterTime(long l) {
        this.latestChapterTime = l;
    }

    public final long getLastCheckTime() {
        return this.lastCheckTime;
    }

    public final void setLastCheckTime(long l) {
        this.lastCheckTime = l;
    }

    public final int getLastCheckCount() {
        return this.lastCheckCount;
    }

    public final void setLastCheckCount(int n) {
        this.lastCheckCount = n;
    }

    public final int getTotalChapterNum() {
        return this.totalChapterNum;
    }

    public final void setTotalChapterNum(int n) {
        this.totalChapterNum = n;
    }

    @Nullable
    public final String getDurChapterTitle() {
        return this.durChapterTitle;
    }

    public final void setDurChapterTitle(@Nullable String string) {
        this.durChapterTitle = string;
    }

    public final int getDurChapterIndex() {
        return this.durChapterIndex;
    }

    public final void setDurChapterIndex(int n) {
        this.durChapterIndex = n;
    }

    public final int getDurChapterPos() {
        return this.durChapterPos;
    }

    public final void setDurChapterPos(int n) {
        this.durChapterPos = n;
    }

    public final long getDurChapterTime() {
        return this.durChapterTime;
    }

    public final void setDurChapterTime(long l) {
        this.durChapterTime = l;
    }

    @Nullable
    public String getWordCount() {
        return this.wordCount;
    }

    public void setWordCount(@Nullable String string) {
        this.wordCount = string;
    }

    public final boolean getCanUpdate() {
        return this.canUpdate;
    }

    public final void setCanUpdate(boolean bl) {
        this.canUpdate = bl;
    }

    public final int getOrder() {
        return this.order;
    }

    public final void setOrder(int n) {
        this.order = n;
    }

    public final int getOriginOrder() {
        return this.originOrder;
    }

    public final void setOriginOrder(int n) {
        this.originOrder = n;
    }

    public final boolean getUseReplaceRule() {
        return this.useReplaceRule;
    }

    public final void setUseReplaceRule(boolean bl) {
        this.useReplaceRule = bl;
    }

    @Nullable
    public final String getVariable() {
        return this.variable;
    }

    public final void setVariable(@Nullable String string) {
        this.variable = string;
    }

    @Nullable
    public final ReadConfig getReadConfig() {
        return this.readConfig;
    }

    public final void setReadConfig(@Nullable ReadConfig readConfig) {
        this.readConfig = readConfig;
    }

    @JsonProperty(value="isInShelf")
    public final boolean isInShelf() {
        return this.isInShelf;
    }

    public final void setInShelf(boolean bl) {
        this.isInShelf = bl;
    }

    @Nullable
    public final String getLastCheckError() {
        return this.lastCheckError;
    }

    public final void setLastCheckError(@Nullable String string) {
        this.lastCheckError = string;
    }

    public final boolean isLocalBook() {
        return Intrinsics.areEqual((Object)this.origin, (Object)"loc_book");
    }

    public final boolean isLocalTxt() {
        return this.isLocalBook() && StringsKt.endsWith((String)this.originName, (String)".txt", (boolean)true);
    }

    public final boolean isLocalEpub() {
        return this.isLocalBook() && StringsKt.endsWith((String)this.originName, (String)".epub", (boolean)true);
    }

    public final boolean isLocalPdf() {
        return this.isLocalBook() && StringsKt.endsWith((String)this.originName, (String)".pdf", (boolean)true);
    }

    public final boolean isEpub() {
        return StringsKt.endsWith((String)this.originName, (String)".epub", (boolean)true);
    }

    public final boolean isCbz() {
        return StringsKt.endsWith((String)this.originName, (String)".cbz", (boolean)true);
    }

    public final boolean isPdf() {
        return StringsKt.endsWith((String)this.originName, (String)".pdf", (boolean)true);
    }

    public final boolean isUmd() {
        return StringsKt.endsWith((String)this.originName, (String)".umd", (boolean)true);
    }

    public final boolean isOnLineTxt() {
        return !this.isLocalBook() && this.type == 0;
    }

    public boolean equals(@Nullable Object other) {
        if (other instanceof Book) {
            return Intrinsics.areEqual((Object)((Book)other).getBookUrl(), (Object)this.getBookUrl());
        }
        return false;
    }

    public int hashCode() {
        return this.getBookUrl().hashCode();
    }

    @NotNull
    public HashMap<String, String> getVariableMap() {
        Lazy lazy = this.variableMap$delegate;
        boolean bl = false;
        return (HashMap)lazy.getValue();
    }

    public void putVariable(@NotNull String key, @Nullable String value) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        if (value != null) {
            Map map = this.getVariableMap();
            boolean bl = false;
            map.put(key, value);
        } else {
            this.getVariableMap().remove(key);
        }
        this.variable = GsonExtensionsKt.getGSON().toJson((Object)this.getVariableMap());
    }

    @Nullable
    public String getInfoHtml() {
        return this.infoHtml;
    }

    public void setInfoHtml(@Nullable String string) {
        this.infoHtml = string;
    }

    @Nullable
    public String getTocHtml() {
        return this.tocHtml;
    }

    public void setTocHtml(@Nullable String string) {
        this.tocHtml = string;
    }

    @NotNull
    public final String getRealAuthor() {
        CharSequence charSequence = this.getAuthor();
        Regex regex = AppPattern.INSTANCE.getAuthorRegex();
        String string = "";
        boolean bl = false;
        return regex.replace(charSequence, string);
    }

    public final int getUnreadChapterNum() {
        int n = this.totalChapterNum - this.durChapterIndex - 1;
        int n2 = 0;
        boolean bl = false;
        return Math.max(n, n2);
    }

    @Nullable
    public final String getDisplayCover() {
        CharSequence charSequence = this.customCoverUrl;
        boolean bl = false;
        boolean bl2 = false;
        return charSequence == null || charSequence.length() == 0 ? this.coverUrl : this.customCoverUrl;
    }

    @Nullable
    public final String getDisplayIntro() {
        CharSequence charSequence = this.customIntro;
        boolean bl = false;
        boolean bl2 = false;
        return charSequence == null || charSequence.length() == 0 ? this.intro : this.customIntro;
    }

    @NotNull
    public final Charset fileCharset() {
        String string = this.charset;
        string = string == null ? "UTF-8" : string;
        boolean bl = false;
        Charset charset = Charset.forName(string);
        Intrinsics.checkNotNullExpressionValue((Object)charset, (String)"Charset.forName(charsetName)");
        return charset;
    }

    private final ReadConfig config() {
        if (this.readConfig == null) {
            this.readConfig = new ReadConfig(false, 0, false, null, false, 0L, 0.0f, 127, null);
        }
        ReadConfig readConfig = this.readConfig;
        Intrinsics.checkNotNull((Object)readConfig);
        return readConfig;
    }

    public final void setDelTag(long tag) {
        this.config().setDelTag((this.config().getDelTag() & tag) == tag ? this.config().getDelTag() & (tag ^ 0xFFFFFFFFFFFFFFFFL) : this.config().getDelTag() | tag);
    }

    public final boolean getDelTag(long tag) {
        return (this.config().getDelTag() & tag) == tag;
    }

    public final float getPdfImageWidth() {
        return this.config().getPdfImageWidth();
    }

    public final void setPdfImageWidth(float pdfImageWidth) {
        this.config().setPdfImageWidth(pdfImageWidth);
    }

    @NotNull
    public final String getFolderName() {
        CharSequence charSequence = this.getName();
        Regex regex = AppPattern.INSTANCE.getFileNameRegex();
        String string = "";
        int n = 0;
        String folderName = regex.replace(charSequence, string);
        charSequence = folderName;
        int n2 = 0;
        int n3 = 9;
        n = folderName.length();
        boolean bl = false;
        n3 = Math.min(n3, n);
        n = 0;
        CharSequence charSequence2 = charSequence;
        if (charSequence2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String string2 = ((String)charSequence2).substring(n2, n3);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        folderName = string2;
        return Intrinsics.stringPlus((String)folderName, (Object)MD5Utils.INSTANCE.md5Encode16(this.getBookUrl()));
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setRootDir(@NotNull String root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        CharSequence charSequence = root;
        boolean bl = false;
        if (charSequence.length() > 0) {
            charSequence = File.separator;
            Intrinsics.checkNotNullExpressionValue((Object)charSequence, (String)"separator");
            if (!StringsKt.endsWith$default((String)root, (String)charSequence, (boolean)false, (int)2, null)) {
                this.rootDir = Intrinsics.stringPlus((String)root, (Object)File.separator);
                return;
            }
        }
        this.rootDir = root;
    }

    @NotNull
    public final File getLocalFile() {
        if (StringsKt.startsWith$default((String)this.originName, (String)this.rootDir, (boolean)false, (int)2, null)) {
            this.originName = StringsKt.replaceFirst$default((String)this.originName, (String)this.rootDir, (String)"", (boolean)false, (int)4, null);
        }
        BookKt.getLogger().info("getLocalFile rootDir: {} originName: {}", (Object)this.rootDir, (Object)this.originName);
        if (this.isEpub() && StringsKt.indexOf$default((CharSequence)this.originName, (String)"localStore", (int)0, (boolean)false, (int)6, null) < 0 && StringsKt.indexOf$default((CharSequence)this.originName, (String)"webdav", (int)0, (boolean)false, (int)6, null) < 0) {
            String[] stringArray = new String[]{"index.epub"};
            return FileUtils.INSTANCE.getFile(new File(Intrinsics.stringPlus((String)this.rootDir, (Object)this.originName)), stringArray);
        }
        if (this.isCbz() && StringsKt.indexOf$default((CharSequence)this.originName, (String)"localStore", (int)0, (boolean)false, (int)6, null) < 0 && StringsKt.indexOf$default((CharSequence)this.originName, (String)"webdav", (int)0, (boolean)false, (int)6, null) < 0) {
            String[] stringArray = new String[]{"index.cbz"};
            return FileUtils.INSTANCE.getFile(new File(Intrinsics.stringPlus((String)this.rootDir, (Object)this.originName)), stringArray);
        }
        if (this.isPdf() && StringsKt.indexOf$default((CharSequence)this.originName, (String)"localStore", (int)0, (boolean)false, (int)6, null) < 0 && StringsKt.indexOf$default((CharSequence)this.originName, (String)"webdav", (int)0, (boolean)false, (int)6, null) < 0) {
            String[] stringArray = new String[]{"index.pdf"};
            return FileUtils.INSTANCE.getFile(new File(Intrinsics.stringPlus((String)this.rootDir, (Object)this.originName)), stringArray);
        }
        return new File(Intrinsics.stringPlus((String)this.rootDir, (Object)this.originName));
    }

    public final void setUserNameSpace(@NotNull String nameSpace) {
        Intrinsics.checkNotNullParameter((Object)nameSpace, (String)"nameSpace");
        this._userNameSpace = nameSpace;
    }

    @NotNull
    public String getUserNameSpace() {
        return this._userNameSpace;
    }

    @NotNull
    public final String getBookDir() {
        String[] stringArray = new String[]{"storage", "data", this._userNameSpace, this.getName() + '_' + this.getAuthor()};
        return FileUtils.INSTANCE.getPath(new File(this.rootDir), stringArray);
    }

    public final boolean getSplitLongChapter() {
        return false;
    }

    @NotNull
    public final SearchBook toSearchBook() {
        String string = this.getName();
        String string2 = this.getAuthor();
        String string3 = this.getKind();
        String string4 = this.getBookUrl();
        String string5 = this.origin;
        String string6 = this.originName;
        int n = this.type;
        String string7 = this.getWordCount();
        String string8 = this.latestChapterTitle;
        String string9 = this.coverUrl;
        String string10 = this.intro;
        String string11 = this.tocUrl;
        String string12 = this.variable;
        string = new SearchBook(string4, string5, string6, n, string, string2, string3, string9, string10, string7, string8, string11, 0L, string12, 0, 20480, null);
        boolean bl = false;
        boolean bl2 = false;
        String $this$toSearchBook_u24lambda_u2d0 = string;
        boolean bl3 = false;
        $this$toSearchBook_u24lambda_u2d0.setInfoHtml(this.getInfoHtml());
        $this$toSearchBook_u24lambda_u2d0.setTocHtml(this.getTocHtml());
        $this$toSearchBook_u24lambda_u2d0.setUserNameSpace(this.getUserNameSpace());
        return string;
    }

    @NotNull
    public final String getEpubRootDir() {
        String defaultPath = "OEBPS";
        File containerRes = new File(this.getBookUrl() + File.separator + "index" + File.separator + "META-INF" + File.separator + "container.xml");
        if (containerRes.exists()) {
            try {
                Document document = Jsoup.parse((String)FilesKt.readText$default((File)containerRes, null, (int)1, null));
                Element rootFileElement = (Element)((Element)document.getElementsByTag("rootfiles").get(0)).getElementsByTag("rootfile").get(0);
                String result2 = rootFileElement.attr("full-path");
                System.out.println(Intrinsics.stringPlus((String)"result: ", (Object)result2));
                if (result2 != null) {
                    Object object = result2;
                    boolean bl = false;
                    if (object.length() > 0) {
                        String string;
                        object = new File(result2).getParentFile();
                        if (object == null) {
                            string = "";
                        } else {
                            Object object2 = object;
                            boolean bl2 = false;
                            boolean bl3 = false;
                            Object it = object2;
                            boolean bl4 = false;
                            String string2 = ((File)it).toString();
                            string = string2 == null ? "" : string2;
                        }
                        return string;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultPath;
    }

    public final void updateFromLocal(boolean onlyCover) {
        try {
            if (this.isEpub()) {
                EpubFile.Companion.upBookInfo(this, onlyCover);
            } else if (this.isUmd()) {
                UmdFile.Companion.upBookInfo(this, onlyCover);
            } else if (this.isCbz()) {
                CbzFile.Companion.upBookInfo(this, onlyCover);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static /* synthetic */ void updateFromLocal$default(Book book, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        book.updateFromLocal(bl);
    }

    @NotNull
    public final String workRoot() {
        return this.rootDir;
    }

    @NotNull
    public List<String> getKindList() {
        return BaseBook.DefaultImpls.getKindList((BaseBook)this);
    }

    @Nullable
    public String getVariable(@NotNull String key) {
        return BaseBook.DefaultImpls.getVariable((BaseBook)((BaseBook)this), (String)key);
    }

    @NotNull
    public final String component1() {
        return this.getBookUrl();
    }

    @NotNull
    public final String component2() {
        return this.tocUrl;
    }

    @NotNull
    public final String component3() {
        return this.origin;
    }

    @NotNull
    public final String component4() {
        return this.originName;
    }

    @NotNull
    public final String component5() {
        return this.getName();
    }

    @NotNull
    public final String component6() {
        return this.getAuthor();
    }

    @Nullable
    public final String component7() {
        return this.getKind();
    }

    @Nullable
    public final String component8() {
        return this.customTag;
    }

    @Nullable
    public final String component9() {
        return this.coverUrl;
    }

    @Nullable
    public final String component10() {
        return this.customCoverUrl;
    }

    @Nullable
    public final String component11() {
        return this.intro;
    }

    @Nullable
    public final String component12() {
        return this.customIntro;
    }

    @Nullable
    public final String component13() {
        return this.charset;
    }

    public final int component14() {
        return this.type;
    }

    public final long component15() {
        return this.group;
    }

    @Nullable
    public final String component16() {
        return this.latestChapterTitle;
    }

    public final long component17() {
        return this.latestChapterTime;
    }

    public final long component18() {
        return this.lastCheckTime;
    }

    public final int component19() {
        return this.lastCheckCount;
    }

    public final int component20() {
        return this.totalChapterNum;
    }

    @Nullable
    public final String component21() {
        return this.durChapterTitle;
    }

    public final int component22() {
        return this.durChapterIndex;
    }

    public final int component23() {
        return this.durChapterPos;
    }

    public final long component24() {
        return this.durChapterTime;
    }

    @Nullable
    public final String component25() {
        return this.getWordCount();
    }

    public final boolean component26() {
        return this.canUpdate;
    }

    public final int component27() {
        return this.order;
    }

    public final int component28() {
        return this.originOrder;
    }

    public final boolean component29() {
        return this.useReplaceRule;
    }

    @Nullable
    public final String component30() {
        return this.variable;
    }

    @Nullable
    public final ReadConfig component31() {
        return this.readConfig;
    }

    public final boolean component32() {
        return this.isInShelf;
    }

    @Nullable
    public final String component33() {
        return this.lastCheckError;
    }

    @NotNull
    public final Book copy(@NotNull String bookUrl, @NotNull String tocUrl, @NotNull String origin, @NotNull String originName, @NotNull String name, @NotNull String author, @Nullable String kind, @Nullable String customTag, @Nullable String coverUrl, @Nullable String customCoverUrl, @Nullable String intro, @Nullable String customIntro, @Nullable String charset, int type, long group, @Nullable String latestChapterTitle, long latestChapterTime, long lastCheckTime, int lastCheckCount, int totalChapterNum, @Nullable String durChapterTitle, int durChapterIndex, int durChapterPos, long durChapterTime, @Nullable String wordCount, boolean canUpdate, int order, int originOrder, boolean useReplaceRule, @Nullable String variable, @Nullable ReadConfig readConfig, boolean isInShelf, @Nullable String lastCheckError) {
        Intrinsics.checkNotNullParameter((Object)bookUrl, (String)"bookUrl");
        Intrinsics.checkNotNullParameter((Object)tocUrl, (String)"tocUrl");
        Intrinsics.checkNotNullParameter((Object)origin, (String)"origin");
        Intrinsics.checkNotNullParameter((Object)originName, (String)"originName");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)author, (String)"author");
        return new Book(bookUrl, tocUrl, origin, originName, name, author, kind, customTag, coverUrl, customCoverUrl, intro, customIntro, charset, type, group, latestChapterTitle, latestChapterTime, lastCheckTime, lastCheckCount, totalChapterNum, durChapterTitle, durChapterIndex, durChapterPos, durChapterTime, wordCount, canUpdate, order, originOrder, useReplaceRule, variable, readConfig, isInShelf, lastCheckError);
    }

    public static /* synthetic */ Book copy$default(Book book, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, String string12, String string13, int n, long l, String string14, long l2, long l3, int n2, int n3, String string15, int n4, int n5, long l4, String string16, boolean bl, int n6, int n7, boolean bl2, String string17, ReadConfig readConfig, boolean bl3, String string18, int n8, int n9, Object object) {
        if ((n8 & 1) != 0) {
            string = book.getBookUrl();
        }
        if ((n8 & 2) != 0) {
            string2 = book.tocUrl;
        }
        if ((n8 & 4) != 0) {
            string3 = book.origin;
        }
        if ((n8 & 8) != 0) {
            string4 = book.originName;
        }
        if ((n8 & 0x10) != 0) {
            string5 = book.getName();
        }
        if ((n8 & 0x20) != 0) {
            string6 = book.getAuthor();
        }
        if ((n8 & 0x40) != 0) {
            string7 = book.getKind();
        }
        if ((n8 & 0x80) != 0) {
            string8 = book.customTag;
        }
        if ((n8 & 0x100) != 0) {
            string9 = book.coverUrl;
        }
        if ((n8 & 0x200) != 0) {
            string10 = book.customCoverUrl;
        }
        if ((n8 & 0x400) != 0) {
            string11 = book.intro;
        }
        if ((n8 & 0x800) != 0) {
            string12 = book.customIntro;
        }
        if ((n8 & 0x1000) != 0) {
            string13 = book.charset;
        }
        if ((n8 & 0x2000) != 0) {
            n = book.type;
        }
        if ((n8 & 0x4000) != 0) {
            l = book.group;
        }
        if ((n8 & 0x8000) != 0) {
            string14 = book.latestChapterTitle;
        }
        if ((n8 & 0x10000) != 0) {
            l2 = book.latestChapterTime;
        }
        if ((n8 & 0x20000) != 0) {
            l3 = book.lastCheckTime;
        }
        if ((n8 & 0x40000) != 0) {
            n2 = book.lastCheckCount;
        }
        if ((n8 & 0x80000) != 0) {
            n3 = book.totalChapterNum;
        }
        if ((n8 & 0x100000) != 0) {
            string15 = book.durChapterTitle;
        }
        if ((n8 & 0x200000) != 0) {
            n4 = book.durChapterIndex;
        }
        if ((n8 & 0x400000) != 0) {
            n5 = book.durChapterPos;
        }
        if ((n8 & 0x800000) != 0) {
            l4 = book.durChapterTime;
        }
        if ((n8 & 0x1000000) != 0) {
            string16 = book.getWordCount();
        }
        if ((n8 & 0x2000000) != 0) {
            bl = book.canUpdate;
        }
        if ((n8 & 0x4000000) != 0) {
            n6 = book.order;
        }
        if ((n8 & 0x8000000) != 0) {
            n7 = book.originOrder;
        }
        if ((n8 & 0x10000000) != 0) {
            bl2 = book.useReplaceRule;
        }
        if ((n8 & 0x20000000) != 0) {
            string17 = book.variable;
        }
        if ((n8 & 0x40000000) != 0) {
            readConfig = book.readConfig;
        }
        if ((n8 & Integer.MIN_VALUE) != 0) {
            bl3 = book.isInShelf;
        }
        if ((n9 & 1) != 0) {
            string18 = book.lastCheckError;
        }
        return book.copy(string, string2, string3, string4, string5, string6, string7, string8, string9, string10, string11, string12, string13, n, l, string14, l2, l3, n2, n3, string15, n4, n5, l4, string16, bl, n6, n7, bl2, string17, readConfig, bl3, string18);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Book(bookUrl=").append(this.getBookUrl()).append(", tocUrl=").append(this.tocUrl).append(", origin=").append(this.origin).append(", originName=").append(this.originName).append(", name=").append(this.getName()).append(", author=").append(this.getAuthor()).append(", kind=").append((Object)this.getKind()).append(", customTag=").append((Object)this.customTag).append(", coverUrl=").append((Object)this.coverUrl).append(", customCoverUrl=").append((Object)this.customCoverUrl).append(", intro=").append((Object)this.intro).append(", customIntro=");
        stringBuilder.append((Object)this.customIntro).append(", charset=").append((Object)this.charset).append(", type=").append(this.type).append(", group=").append(this.group).append(", latestChapterTitle=").append((Object)this.latestChapterTitle).append(", latestChapterTime=").append(this.latestChapterTime).append(", lastCheckTime=").append(this.lastCheckTime).append(", lastCheckCount=").append(this.lastCheckCount).append(", totalChapterNum=").append(this.totalChapterNum).append(", durChapterTitle=").append((Object)this.durChapterTitle).append(", durChapterIndex=").append(this.durChapterIndex).append(", durChapterPos=").append(this.durChapterPos);
        stringBuilder.append(", durChapterTime=").append(this.durChapterTime).append(", wordCount=").append((Object)this.getWordCount()).append(", canUpdate=").append(this.canUpdate).append(", order=").append(this.order).append(", originOrder=").append(this.originOrder).append(", useReplaceRule=").append(this.useReplaceRule).append(", variable=").append((Object)this.variable).append(", readConfig=").append(this.readConfig).append(", isInShelf=").append(this.isInShelf).append(", lastCheckError=").append((Object)this.lastCheckError).append(')');
        return stringBuilder.toString();
    }

    public Book() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0L, null, 0L, 0L, 0, 0, null, 0, 0, 0L, null, false, 0, 0, false, null, null, false, null, -1, 1, null);
    }
}

