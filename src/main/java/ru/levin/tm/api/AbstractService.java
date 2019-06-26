package ru.levin.tm.api;

import java.util.List;
import java.util.Optional;

public interface AbstractService<T> {
    Optional<T> get(long id);
    List<T> getAll();
    boolean save(T t);
    boolean update(T t);
    boolean delete(T t);
    void deleteAll();
}
