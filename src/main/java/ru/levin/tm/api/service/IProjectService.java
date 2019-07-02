package ru.levin.tm.api.service;

import org.jetbrains.annotations.Nullable;
import ru.levin.tm.entity.Project;

import java.util.List;

public interface IProjectService extends IEntityService<Project> {
    void removeByUserId(@Nullable final String userId);
    Project findOneByIndex(@Nullable final String userId, final int index);
    List<Project> findAllByUserId(@Nullable final String userId);
}
