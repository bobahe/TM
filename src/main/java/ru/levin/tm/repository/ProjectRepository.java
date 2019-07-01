package ru.levin.tm.repository;

import ru.levin.tm.api.repository.IProjectRepository;
import ru.levin.tm.entity.Project;

import java.util.List;
import java.util.stream.Collectors;

public final class ProjectRepository extends AbstractRepository<Project> implements IProjectRepository {
    @Override
    public void removeByUserId(String userId) {
        storageMap.values().forEach(project -> {
            if (project.getUserId().equals(userId)) {
                remove(project);
            }
        });
    }

    @Override
    public List<Project> findAllByUserId(String userId) {
        return storageMap.values().stream()
                .filter(project -> project.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
