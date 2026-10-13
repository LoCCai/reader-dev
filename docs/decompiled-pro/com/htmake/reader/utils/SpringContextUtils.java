/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.utils.SpringContextUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.stereotype.Component
 */
package com.htmake.reader.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/*
 * Exception performing whole class analysis ignored.
 */
@Component
public class SpringContextUtils
implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        if (applicationContext != null) {
            return SpringContextUtils.getApplicationContext().getBean(name);
        }
        return null;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext != null) {
            return (T)SpringContextUtils.getApplicationContext().getBean(clazz);
        }
        return null;
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        if (applicationContext != null) {
            return (T)SpringContextUtils.getApplicationContext().getBean(name, clazz);
        }
        return null;
    }
}

