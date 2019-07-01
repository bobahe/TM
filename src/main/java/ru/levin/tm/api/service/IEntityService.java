package ru.levin.tm.api.service;

import ru.levin.tm.entity.AbstractEntity;

import java.util.List;

public interface IEntityService<T extends AbstractEntity> extends IService<T> {
    List<T> getAll();
    T save(T entity);
    T update(T entity);
    boolean remove(T entity);
    boolean removeAll();
}
