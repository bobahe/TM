package ru.levin.tm.service;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.AbstractHasOwnerEntity;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractEntityWithOwnerService<T extends AbstractHasOwnerEntity> extends AbstractEntityService<T> {
    public AbstractEntityWithOwnerService(IRepository<T> repository) {
        super(repository);
    }

    public void removeByUserId(final String id) {
        final List<T> userProjects = repository.findAll().values().stream()
                .filter(entity -> entity.getUserId().equals(id))
                .collect(Collectors.toList());

        userProjects.forEach(repository::remove);
    }
}
