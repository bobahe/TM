package ru.levin.tm.service;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.Project;
import ru.levin.tm.util.ServiceUtil;

public final class ProjectService extends AbstractEntityWithOwnerService<Project> {
    private static final String PROP_NAME = "name";

    public ProjectService(final IRepository<Project> repository) {
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
}
