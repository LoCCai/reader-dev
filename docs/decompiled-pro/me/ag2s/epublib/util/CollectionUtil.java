/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.util.CollectionUtil
 *  me.ag2s.epublib.util.CollectionUtil$IteratorEnumerationAdapter
 */
package me.ag2s.epublib.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import me.ag2s.epublib.util.CollectionUtil;

public class CollectionUtil {
    public static <T> Enumeration<T> createEnumerationFromIterator(Iterator<T> it) {
        return new IteratorEnumerationAdapter(it);
    }

    public static <T> T first(List<T> list2) {
        if (list2 == null || list2.isEmpty()) {
            return null;
        }
        return list2.get(0);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}

