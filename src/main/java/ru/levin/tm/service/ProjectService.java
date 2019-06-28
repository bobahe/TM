package ru.levin.tm.service;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.Project;
import ru.levin.tm.util.ServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService extends AbstractEntityService<Project> {
    private static final String PROP_NAME = "name";
    private static final String PROP_ID = "id";

    private final IRepository<Project> repository;

    public ProjectService(IRepository<Project> repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> findAll() {
        return new ArrayList<>(repository.findAll().values());
    }

    @Override
    public void save(Project entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getName(), PROP_NAME);

        repository.persist(entity);
    }

    @Override
    public void update(Project entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getId(), PROP_ID);
        ServiceUtil.checkNullOrEmpty(entity.getName(), PROP_NAME);

        if (repository.findOne(entity.getId()) == null) {
            throw new IllegalStateException("Can not update project. There is no such project in storage.");
        }

        repository.merge(entity);
    }

    @Override
    public void remove(Project entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getId(), PROP_ID);
        repository.remove(entity);
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }

    public void removeByUserId(String id) {
        List<Project> userProjects = repository.findAll().values().stream()
                .filter(project -> project.getUserId().equals(id))
                .collect(Collectors.toList());

        userProjects.forEach(repository::remove);
    }
}
