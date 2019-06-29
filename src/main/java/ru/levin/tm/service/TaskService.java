package ru.levin.tm.service;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.Task;
import ru.levin.tm.repository.TaskRepository;
import ru.levin.tm.util.ServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService extends AbstractEntityService<Task> {
    private static final String PROP_NAME = "name";
    private static final String PROP_ID = "id";

    private final IRepository<Task> repository;

    public TaskService(final IRepository<Task> repository) {
        this.repository = repository;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(repository.findAll().values());
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

    @Override
    public void remove(final Task entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getId(), PROP_ID);
        repository.remove(entity);
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }

    public void removeByUserId(final String id) {
        final List<Task> userTasks = repository.findAll().values().stream()
                .filter(task -> task.getUserId().equals(id))
                .collect(Collectors.toList());

        userTasks.forEach(repository::remove);
    }
}
