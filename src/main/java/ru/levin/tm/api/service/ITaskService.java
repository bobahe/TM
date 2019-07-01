package ru.levin.tm.api.service;

import ru.levin.tm.entity.Task;

import java.util.List;

public interface ITaskService extends IEntityService<Task> {
    List<Task> findAllByUserIdAndProjectId(final String userId, final String projectId);
    List<Task> findAllByUserId(final String userId);
    void removeAllByUserIdAndProjectId(final String userId, final String projectId);
    void removeByUserId(final String userId);
    Task findOneByIndex(final String userId, final int index);
}
