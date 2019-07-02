package ru.levin.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface IRepository<E> {

    @NotNull Map<String, E> findAll();
    @Nullable E findOne(@NotNull final String id);
    void persist(@NotNull final E entity);
    void merge(@NotNull final E entity);
    void remove(@NotNull final E entity);
    void removeAll();

}