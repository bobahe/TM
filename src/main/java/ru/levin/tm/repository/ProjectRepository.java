package ru.levin.tm.repository;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.Project;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ProjectRepository implements IRepository<Project> {
    Map<String, Project> projectMap = new LinkedHashMap<>();

    @Override
    public Map<String, Project> findAll() {
        return projectMap;
    }

    @Override
    public Project findOne(String id) {
        return projectMap.get(id);
    }

    @Override
    public void persist(Project entity) {
        String id = UUID.randomUUID().toString();
        entity.setId(id);
        projectMap.put(id, entity);
    }

    @Override
    public void merge(Project entity) {
        projectMap.put(entity.getId(), entity);
    }

    @Override
    public void remove(Project entity) {
        projectMap.remove(entity.getId());
    }

    @Override
    public void removeAll() {
        projectMap.clear();
    }
}
