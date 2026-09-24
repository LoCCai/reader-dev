/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.EpubBook
 *  me.ag2s.epublib.epub.BookProcessor
 */
package me.ag2s.epublib.epub;

import me.ag2s.epublib.domain.EpubBook;

public interface BookProcessor {
    public static final BookProcessor IDENTITY_BOOKPROCESSOR = book -> book;

    public EpubBook processBook(EpubBook var1);
}

