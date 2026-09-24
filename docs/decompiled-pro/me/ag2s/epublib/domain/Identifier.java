/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.Identifier
 *  me.ag2s.epublib.util.StringUtil
 */
package me.ag2s.epublib.domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import me.ag2s.epublib.util.StringUtil;

public class Identifier
implements Serializable {
    private static final long serialVersionUID = 955949951416391810L;
    private boolean bookId = false;
    private String scheme;
    private String value;

    public Identifier() {
        this("UUID", UUID.randomUUID().toString());
    }

    public Identifier(String scheme, String value) {
        this.scheme = scheme;
        this.value = value;
    }

    public static Identifier getBookIdIdentifier(List<Identifier> identifiers) {
        if (identifiers == null || identifiers.isEmpty()) {
            return null;
        }
        Identifier result2 = null;
        for (Identifier identifier : identifiers) {
            if (!identifier.isBookId()) continue;
            result2 = identifier;
            break;
        }
        if (result2 == null) {
            result2 = identifiers.get(0);
        }
        return result2;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setBookId(boolean bookId) {
        this.bookId = bookId;
    }

    public boolean isBookId() {
        return this.bookId;
    }

    public int hashCode() {
        return StringUtil.defaultIfNull((String)this.scheme).hashCode() ^ StringUtil.defaultIfNull((String)this.value).hashCode();
    }

    public boolean equals(Object otherIdentifier) {
        if (!(otherIdentifier instanceof Identifier)) {
            return false;
        }
        return StringUtil.equals((String)this.scheme, (String)((Identifier)otherIdentifier).scheme) && StringUtil.equals((String)this.value, (String)((Identifier)otherIdentifier).value);
    }

    public String toString() {
        if (StringUtil.isBlank((String)this.scheme)) {
            return "" + this.value;
        }
        return "" + this.scheme + ":" + this.value;
    }
}

