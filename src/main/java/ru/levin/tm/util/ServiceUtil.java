package ru.levin.tm.util;

public final class ServiceUtil {
    public static void checkNull(final Object object) {
        if (object == null) {
            throw new IllegalStateException("Can not save null.");
        }
    }

    public static void checkNullOrEmpty(final Object string, final String propertyName) {
        if (string == null || "".equals(string.toString())) {
            throw new IllegalStateException("Can not save entity with empty or null " + propertyName + ".");
        }
    }
}
