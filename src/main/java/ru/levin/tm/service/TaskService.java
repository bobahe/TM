package ru.levin.tm.service;

import ru.levin.tm.api.repository.ITaskRepository;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.entity.Task;

import java.util.List;

public final class TaskService extends AbstractEntityWithOwnerService<Task> {
    private static final String PROP_NAME = "name";

    public TaskService(final IRepository<Task> repository) {
        super(repository);
    }

    @Override
    public void removeAllByUserIdAndProjectId(final String userId, final String projectId) {
        if (userId == null || "".equals(userId)) {
            return;
        }
        if (projectId == null || "".equals(projectId)) {
            return;
        }

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
        if (entity == null) {
            return null;
        }
        if (entity.getName() == null || "".equals(entity.getName())) {
            System.out.println("Can not save task without name.");
            return null;
        }

        repository.persist(entity);
        return entity;
    }

    @Override
    public Task update(final Task entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getId() == null || "".equals(entity.getId())) {
            System.out.println("Can not update task without id.");
            return null;
        }
        if (entity.getName() == null || "".equals(entity.getName())) {
            System.out.println("Can not update task without name.");
            return null;
        }

        if (repository.findOne(entity.getId()) == null) {
            throw new IllegalStateException("Can not update task. There is no such task in storage.");
        }

        repository.merge(entity);
        return entity;
    }
}
