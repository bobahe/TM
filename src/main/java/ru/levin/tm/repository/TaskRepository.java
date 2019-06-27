package ru.levin.tm.repository;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.Task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskRepository implements IRepository<Task> {
    Map<String, Task> taskMap = new LinkedHashMap<>();

    @Override
    public Map<String, Task> findAll() {
        return taskMap;
    }

    @Override
    public Task findOne(String id) {
        return taskMap.get(id);
    }

    @Override
    public void persist(Task entity) {
        String id = UUID.randomUUID().toString();
        entity.setId(id);
        taskMap.put(id, entity);
    }

    @Override
    public void merge(Task entity) {
        taskMap.put(entity.getId(), entity);
    }

    @Override
    public void remove(Task entity) {
        taskMap.remove(entity.getId());
    }

    @Override
    public void removeAll() {
        taskMap.clear();
    }

    public List<Task> findAllByProjectId(String id) {
        return taskMap.values().stream().filter(task -> id.equals(task.getProjectId())).collect(Collectors.toList());
    }
}
