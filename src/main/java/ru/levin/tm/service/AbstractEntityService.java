package ru.levin.tm.service;

import java.util.List;

public abstract class AbstractEntityService<T> extends AbstractService<T>{
    public abstract List<T> findAll();
    public abstract void save(final T entity);
    public abstract void update(final T entity);
    public abstract void remove(final T entity);
    public abstract void removeAll();
}
