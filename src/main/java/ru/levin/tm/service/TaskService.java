package ru.levin.tm.service;

import ru.levin.tm.api.repository.ITaskRepository;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.entity.Task;

import java.util.List;

public final class TaskService extends AbstractEntityService<Task, ITaskRepository> implements ITaskService {
    private final ITaskRepository repository;

    public TaskService(final ITaskRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Task> findAllByUserIdAndProjectId(final String userId, final String projectId) {
        return repository.findAllByUserIdProjectId(userId, projectId);
    }

    @Override
    public List<Task> findAllByUserId(final String userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public void removeAllByUserIdAndProjectId(final String userId, final String projectId) {
        if (userId == null || userId.isEmpty()) return;
        if (projectId == null || projectId.isEmpty()) return;

        findAllByUserIdAndProjectId(userId, projectId).forEach(repository::remove);
    }

    @Override
    public void removeByUserId(String userId) {
        repository.findAllByUserId(userId);
    }

    @Override
    public Task findOneByIndex(final String userId, final int index) {
        return repository.findAllByUserId(userId).get(index - 1);
    }

    @Override
    public Task save(final Task entity) {
        if (entity == null) return null;
        if (entity.getName() == null || entity.getName().isEmpty()) return null;

        repository.persist(entity);
        return entity;
    }

    @Override
    public Task update(final Task entity) {
        if (entity == null) return null;
        if (entity.getId() == null || entity.getId().isEmpty()) return null;
        if (entity.getName() == null || entity.getName().isEmpty()) return null;

        if (repository.findOne(entity.getId()) == null) {
            throw new IllegalStateException("Can not update task. There is no such task in storage.");
        }

        repository.merge(entity);
        return entity;
    }
}
