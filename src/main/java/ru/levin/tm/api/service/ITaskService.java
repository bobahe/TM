package ru.levin.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.entity.Task;

import java.util.List;

public interface ITaskService extends IEntityService<Task> {

    @NotNull List<Task> findAllByUserIdAndProjectId(@Nullable final String userId, @Nullable final String projectId);
    @NotNull List<Task> findAllByUserId(@Nullable final String userId);
    void removeAllByUserIdAndProjectId(@Nullable final String userId, @Nullable final String projectId);
    void removeByUserId(@Nullable final String userId);
    @Nullable Task findOneByIndex(@Nullable final String userId, final int index);
    @NotNull List<Task> findAllByPartOfNameOrDescription(@Nullable final String partOfName);

}
