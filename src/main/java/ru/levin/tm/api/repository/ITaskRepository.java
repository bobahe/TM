package ru.levin.tm.api.repository;

import ru.levin.tm.entity.Task;

import java.util.List;

public interface ITaskRepository extends IRepository<Task> {
    List<Task> findAllByUserIdProjectId(final String userId, final String projectId);
    List<Task> findAllByUserId(final String userId);
    void removeByUserId(final String userId);
}
