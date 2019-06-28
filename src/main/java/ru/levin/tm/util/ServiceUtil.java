package ru.levin.tm.util;

public class ServiceUtil {
    public static void checkNull(Object object) {
        if (object == null) {
            throw new IllegalStateException("Can not save null.");
        }
    }

    public static void checkNullOrEmpty(Object string, String propertyName) {
        if (string == null || "".equals(string.toString())) {
            throw new IllegalStateException("Can not save project with empty or null " + propertyName + ".");
        }
    }
}
