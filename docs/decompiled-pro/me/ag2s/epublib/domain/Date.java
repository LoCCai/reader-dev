/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.Date
 *  me.ag2s.epublib.domain.Date$Event
 */
package me.ag2s.epublib.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;
import me.ag2s.epublib.domain.Date;

/*
 * Exception performing whole class analysis ignored.
 */
public class Date
implements Serializable {
    private static final long serialVersionUID = 7533866830395120136L;
    private Event event;
    private String dateString;

    public Date() {
        this(new java.util.Date(), Event.CREATION);
    }

    public Date(java.util.Date date) {
        this(date, (Event)null);
    }

    public Date(String dateString) {
        this(dateString, (Event)null);
    }

    public Date(java.util.Date date, Event event) {
        this(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date), event);
    }

    public Date(String dateString, Event event) {
        this.dateString = dateString;
        this.event = event;
    }

    public Date(java.util.Date date, String event) {
        this(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date), event);
    }

    public Date(String dateString, String event) {
        this(Date.checkDate((String)dateString), Event.fromValue((String)event));
        this.dateString = dateString;
    }

    private static String checkDate(String dateString) {
        if (dateString == null) {
            throw new IllegalArgumentException("Cannot create a date from a blank string");
        }
        return dateString;
    }

    public String getValue() {
        return this.dateString;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String toString() {
        if (this.event == null) {
            return this.dateString;
        }
        return "" + this.event + ":" + this.dateString;
    }
}

