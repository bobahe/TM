package ru.levin.tm.repository;

import ru.levin.tm.entity.Task;

import java.util.List;
import java.util.stream.Collectors;

public final class TaskRepository extends AbstractRepository<Task> {
    public List<Task> findAllByProjectId(final String id) {
        return storageMap.values().stream().filter(task -> id.equals(task.getProjectId())).collect(Collectors.toList());
    }
}
