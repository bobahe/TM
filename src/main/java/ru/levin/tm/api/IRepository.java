package ru.levin.tm.api;

import java.util.Map;

public interface IRepository<E> {
    Map<String, E> findAll();
    E findOne(final String id);
    void persist(final E entity);
    void merge(final E entity);
    void remove(final E entity);
    void removeAll();
}