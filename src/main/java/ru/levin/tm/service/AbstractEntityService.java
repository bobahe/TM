package ru.levin.tm.service;

import ru.levin.tm.api.repository.IRepository;
import ru.levin.tm.api.service.IEntityService;
import ru.levin.tm.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntityService<T extends AbstractEntity> extends AbstractService<T>{
    protected static final String PROP_ID = "id";

    protected final E repository;

    public AbstractEntityService(E repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return new ArrayList<>(repository.findAll().values());
    }

    public abstract T save(final T entity);
    public abstract T update(final T entity);

    public boolean remove(final T entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getId() == null || "".equals(entity.getId())) {
            return false;
        }
        repository.remove(entity);
        return true;
    }

    public boolean removeAll() {
        repository.removeAll();
        return true;
    }
}
