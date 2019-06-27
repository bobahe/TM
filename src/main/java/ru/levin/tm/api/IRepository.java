package ru.levin.tm.api;

import java.util.Map;

public interface IRepository<E> {
    Map<String, E> findAll();
    E findOne(String id);
    void persist(E entity);
    void merge(E entity);
    void remove(E entity);
    void removeAll();
}