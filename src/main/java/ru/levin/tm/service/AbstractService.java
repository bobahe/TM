package ru.levin.tm.service;

import java.util.List;

public abstract class AbstractService<T> {
    public abstract List<T> findAll();
    public abstract void save(T entity);
    public abstract void update(T entity);
    public abstract void remove(T entity);
    public abstract void removeAll();

    protected void checkNull(Object object) {
        if (object == null) {
            throw new IllegalStateException("Can not save null.");
        }
    }

    protected void checkNullOrEmpty(Object string, String propertyName) {
        if (string == null || "".equals(string.toString())) {
            throw new IllegalStateException("Can not save project with empty or null " + propertyName + ".");
        }
    }
}
