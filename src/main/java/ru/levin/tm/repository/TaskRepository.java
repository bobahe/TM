package ru.levin.tm.repository;

import ru.levin.tm.api.repository.ITaskRepository;
import ru.levin.tm.entity.Task;

import java.util.List;
import java.util.stream.Collectors;

public final class TaskRepository extends AbstractRepository<Task> implements ITaskRepository {
    public List<Task> findAllByUserIdProjectId(final String userId, final String projectId) {
        return storageMap.values().stream()
                .filter(task -> userId.equals(task.getUserId()) && projectId.equals(task.getProjectId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAllByUserId(String userId) {
        return storageMap.values().stream()
                .filter(task -> userId.equals(task.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeByUserId(final String userId) {
        storageMap.values().forEach(task -> {
            if (userId.equals(task.getUserId())) {
                remove(task);
            }
        });
    }
}
