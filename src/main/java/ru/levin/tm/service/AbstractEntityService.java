package ru.levin.tm.service;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.AbstractEntity;
import ru.levin.tm.util.ServiceUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntityService<T extends AbstractEntity> extends AbstractService<T>{
    protected static final String PROP_ID = "id";

    protected final IRepository<T> repository;

    public AbstractEntityService(IRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return new ArrayList<>(repository.findAll().values());
    }

    public abstract void save(final T entity);
    public abstract void update(final T entity);

    public void remove(final T entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getId(), PROP_ID);
        repository.remove(entity);
    }

    public void removeAll() {
        repository.removeAll();
    }
}
