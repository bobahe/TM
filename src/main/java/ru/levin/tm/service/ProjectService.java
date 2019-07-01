package ru.levin.tm.service;

import ru.levin.tm.api.repository.IProjectRepository;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.entity.Project;
import ru.levin.tm.util.ServiceUtil;

import java.util.List;

public final class ProjectService extends AbstractEntityService<Project, IProjectRepository> implements IProjectService {
    private static final String PROP_NAME = "name";

    public ProjectService(final IProjectRepository repository) {
        super(repository);
    }

    @Override
    public void save(final Project entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getName(), PROP_NAME);

        repository.persist(entity);
    }

    @Override
    public void update(final Project entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getId(), PROP_ID);
        ServiceUtil.checkNullOrEmpty(entity.getName(), PROP_NAME);

        if (repository.findOne(entity.getId()) == null) {
            throw new IllegalStateException("Can not update project. There is no such project in storage.");
        }

        repository.merge(entity);
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
