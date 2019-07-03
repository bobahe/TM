package ru.levin.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.repository.IRepository;
import ru.levin.tm.entity.AbstractEntity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractRepository<T extends AbstractEntity> implements IRepository<T> {

    @NotNull
    protected final Map<String, T> storageMap = new LinkedHashMap<>();

    @Override
    @NotNull
    public Map<String, T> findAll() {
        return storageMap;
    }

    @Override
    @Nullable
    public T findOne(@NotNull final String id) {
        return storageMap.get(id);
    }

    @Override
    public void persist(@NotNull final T entity) {
        if (entity.getId() == null) {
            @NotNull final String id = UUID.randomUUID().toString();
            entity.setId(id);
        }
        storageMap.put(entity.getId(), entity);
    }

    @Override
    public void merge(@NotNull final T entity) {
        storageMap.put(entity.getId(), entity);
    }

    @Override
    public void remove(@NotNull final T entity) {
        storageMap.remove(entity.getId());
    }

    @Override
    public void removeAll() {
        storageMap.clear();
    }

}
