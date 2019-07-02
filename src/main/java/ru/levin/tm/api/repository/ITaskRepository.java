package ru.levin.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.entity.Task;

import java.util.List;

public interface ITaskRepository extends IRepository<Task> {

    @NotNull List<Task> findAllByUserIdProjectId(@NotNull final String userId, @NotNull final String projectId);
    @NotNull List<Task> findAllByUserId(@NotNull final String userId);
    void removeByUserId(@NotNull final String userId);

}
