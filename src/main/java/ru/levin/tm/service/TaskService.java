package ru.levin.tm.service;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.Task;
import ru.levin.tm.repository.TaskRepository;
import ru.levin.tm.util.ServiceUtil;

import java.util.List;

public final class TaskService extends AbstractEntityWithOwnerService<Task> {
    private static final String PROP_NAME = "name";

    public TaskService(final IRepository<Task> repository) {
        super(repository);
    }

    public List<Task> findByProjectId(final String id) {
        return ((TaskRepository) repository).findAllByProjectId(id);
    }

    @Override
    public void save(final Task entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getName(), PROP_NAME);

        repository.persist(entity);
    }

    @Override
    public void update(final Task entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getId(), PROP_ID);
        ServiceUtil.checkNullOrEmpty(entity.getName(), PROP_NAME);

        if (repository.findOne(entity.getId()) == null) {
            throw new IllegalStateException("Can not update task. There is no such task in storage.");
        }

        repository.merge(entity);
    }
}
