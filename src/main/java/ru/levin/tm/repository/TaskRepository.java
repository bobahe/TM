package ru.levin.tm.repository;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.Task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskRepository implements IRepository<Task> {
    private final Map<String, Task> taskMap = new LinkedHashMap<>();

    @Override
    public Map<String, Task> findAll() {
        return taskMap;
    }

    @Override
    public Task findOne(final String id) {
        return taskMap.get(id);
    }

    @Override
    public void persist(final Task entity) {
        final String id = UUID.randomUUID().toString();
        entity.setId(id);
        taskMap.put(id, entity);
    }

    @Override
    public void merge(final Task entity) {
        taskMap.put(entity.getId(), entity);
    }

    @Override
    public void remove(final Task entity) {
        taskMap.remove(entity.getId());
    }

    @Override
    public void removeAll() {
        taskMap.clear();
    }

    public List<Task> findAllByProjectId(final String id) {
        return taskMap.values().stream().filter(task -> id.equals(task.getProjectId())).collect(Collectors.toList());
    }
}
