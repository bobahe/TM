package ru.levin.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.repository.IProjectRepository;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.entity.Project;

import java.util.ArrayList;
import java.util.List;

public final class ProjectService extends AbstractEntityService<Project, IProjectRepository> implements IProjectService {
    public ProjectService(@NotNull final IProjectRepository repository) {
        super(repository);
    }

    @Override
    @Nullable
    public Project save(@Nullable final Project entity) {
        if (entity == null) return null;
        if (entity.getName() == null || entity.getName().isEmpty()) return null;

        repository.persist(entity);
        return entity;
    }

    @Override
    @Nullable
    public Project update(@Nullable final Project entity) {
        if (entity == null) return null;
        if (entity.getId() == null || entity.getId().isEmpty()) return null;
        if (entity.getName() == null || entity.getName().isEmpty()) return null;

        if (repository.findOne(entity.getId()) == null) {
            throw new IllegalStateException("Can not update project. There is no such project in storage.");
        }

        repository.merge(entity);
        return entity;
    }

    @Override
    public void removeByUserId(@Nullable final String userId) {
        if (userId == null) return;
        repository.removeByUserId(userId);
    }

    @Override
    @Nullable
    public Project findOneByIndex(@Nullable final String userId, int index) throws IndexOutOfBoundsException {
        if (userId == null) return null;
        return repository.findAllByUserId(userId).get(index - 1);
    }

    @Override
    @NotNull
    public List<Project> findAllByUserId(@Nullable final String userId) {
        if (userId == null) return new ArrayList<>();
        return repository.findAllByUserId(userId);
    }
}
