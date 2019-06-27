package ru.levin.tm.service;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.Task;
import ru.levin.tm.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class TaskService extends AbstractService<Task> {
    private static final String PROP_NAME = "name";
    private static final String PROP_ID = "id";

    private final IRepository<Task> repository;

    public TaskService(IRepository<Task> repository) {
        this.repository = repository;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(repository.findAll().values());
    }

    public List<Task> findByProjectId(String id) {
        return ((TaskRepository) repository).findAllByProjectId(id);
    }

    @Override
    public void save(Task entity) {
        checkNull(entity);
        checkNullOrEmpty(entity.getName(), PROP_NAME);

        repository.persist(entity);
    }

    @Override
    public void update(Task entity) {
        checkNull(entity);
        checkNullOrEmpty(entity.getId(), PROP_ID);
        checkNullOrEmpty(entity.getName(), PROP_NAME);

        if (repository.findOne(entity.getId()) == null) {
            throw new IllegalStateException("Can not update task. There is no such task in storage.");
        }

        repository.merge(entity);
    }

    @Override
    public void remove(Task entity) {
        checkNull(entity);
        checkNullOrEmpty(entity.getId(), PROP_ID);
        repository.remove(entity);
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }
}
