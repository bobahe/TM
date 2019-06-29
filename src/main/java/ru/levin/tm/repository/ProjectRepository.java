package ru.levin.tm.repository;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.Project;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ProjectRepository implements IRepository<Project> {
    private final Map<String, Project> projectMap = new LinkedHashMap<>();

    @Override
    public Map<String, Project> findAll() {
        return projectMap;
    }

    @Override
    public Project findOne(final String id) {
        return projectMap.get(id);
    }

    @Override
    public void persist(final Project entity) {
        final String id = UUID.randomUUID().toString();
        entity.setId(id);
        projectMap.put(id, entity);
    }

    @Override
    public void merge(final Project entity) {
        projectMap.put(entity.getId(), entity);
    }

    @Override
    public void remove(final Project entity) {
        projectMap.remove(entity.getId());
    }

    @Override
    public void removeAll() {
        projectMap.clear();
    }
}
