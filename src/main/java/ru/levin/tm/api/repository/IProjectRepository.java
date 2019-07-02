package ru.levin.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.entity.Project;

import java.util.List;

public interface IProjectRepository extends IRepository<Project> {

    void removeByUserId(@NotNull final String userId);
    @NotNull List<Project> findAllByUserId(@NotNull final String userId);

}
