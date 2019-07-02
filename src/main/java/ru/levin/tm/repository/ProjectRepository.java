package ru.levin.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.repository.IProjectRepository;
import ru.levin.tm.entity.Project;

import java.util.List;
import java.util.stream.Collectors;

public final class ProjectRepository extends AbstractRepository<Project> implements IProjectRepository {
    @Override
    public void removeByUserId(@NotNull String userId) {
        storageMap.values().forEach(project -> {
            if (userId.equals(project.getUserId())) {
                remove(project);
            }
        });
    }

    @Override
    public @NotNull List<Project> findAllByUserId(@NotNull String userId) {
        return storageMap.values().stream()
                .filter(project -> userId.equals(project.getUserId()))
                .collect(Collectors.toList());
    }
}
