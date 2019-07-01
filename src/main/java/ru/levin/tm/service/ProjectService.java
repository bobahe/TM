package ru.levin.tm.service;

import ru.levin.tm.api.repository.IProjectRepository;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.entity.Project;

import java.util.List;

public final class ProjectService extends AbstractEntityService<Project, IProjectRepository> implements IProjectService {
    public ProjectService(final IProjectRepository repository) {
        super(repository);
    }

    @Override
    public Project save(final Project entity) {
        if (entity == null) return null;
        if (entity.getName() == null || entity.getName().isEmpty()) return null;

        repository.persist(entity);
        return entity;
    }

    @Override
    public Project update(final Project entity) {
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
    public void removeByUserId(String userId) {
        repository.removeByUserId(userId);
    }

    @Override
    public Project findOneByIndex(String userId, int index) throws IndexOutOfBoundsException {
        return repository.findAllByUserId(userId).get(index - 1);
    }

    @Override
    public List<Project> findAllByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }
}
