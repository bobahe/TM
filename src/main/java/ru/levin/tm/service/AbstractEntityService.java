package ru.levin.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.repository.IRepository;
import ru.levin.tm.api.service.IEntityService;
import ru.levin.tm.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntityService<T extends AbstractEntity, E extends IRepository<T>>
        extends AbstractService<T>
        implements IEntityService<T> {

    @NotNull
    protected final E repository;

    public AbstractEntityService(@NotNull E repository) {
        this.repository = repository;
    }

    @NotNull
    public List<T> getAll() {
        return new ArrayList<>(repository.findAll().values());
    }

    @Nullable
    public abstract T save(@Nullable final T entity);

    @Nullable
    public abstract T update(@Nullable final T entity);

    public boolean remove(@Nullable final T entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getId() == null || entity.getId().isEmpty()) {
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
