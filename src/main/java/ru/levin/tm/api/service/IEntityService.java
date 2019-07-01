package ru.levin.tm.api.service;

import ru.levin.tm.entity.AbstractEntity;

import java.util.List;

public interface IEntityService<T extends AbstractEntity> extends IService<T> {
    List<T> getAll();
    void save(T entity);
    void update(T entity);
    void remove(T entity);
    void removeAll();
}
