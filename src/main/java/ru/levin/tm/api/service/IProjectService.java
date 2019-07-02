package ru.levin.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.entity.Project;

import java.util.List;

public interface IProjectService extends IEntityService<Project> {

    void removeByUserId(@Nullable final String userId);
    @Nullable Project findOneByIndex(@Nullable final String userId, final int index);
    @NotNull  List<Project> findAllByUserId(@Nullable final String userId);
    @NotNull List<Project> findAllByPartOfNameOrDescription(@Nullable final String partOfName);

}
