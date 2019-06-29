package ru.levin.tm.repository;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.AbstractEntity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractRepository<T extends AbstractEntity> implements IRepository<T> {
    protected final Map<String, T> storageMap = new LinkedHashMap<>();

    @Override
    public Map<String, T> findAll() {
        return storageMap;
    }

    @Override
    public T findOne(final String id) {
        return storageMap.get(id);
    }

    @Override
    public void persist(final T entity) {
        final String id = UUID.randomUUID().toString();
        entity.setId(id);
        storageMap.put(id, entity);
    }

    @Override
    public void merge(final T entity) {
        storageMap.put(entity.getId(), entity);
    }

    @Override
    public void remove(final T entity) {
        storageMap.remove(entity.getId());
    }

    @Override
    public void removeAll() {
        storageMap.clear();
    }
}
