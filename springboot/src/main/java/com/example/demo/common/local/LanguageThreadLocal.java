package com.example.demo.common.local;

public class LanguageThreadLocal {
    private static volatile ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void set(String language) {
        threadLocal.set(language);
    }

    public static String get() {
        return threadLocal.get();
    }

    public static void release() {
        threadLocal.remove();
    }
}
