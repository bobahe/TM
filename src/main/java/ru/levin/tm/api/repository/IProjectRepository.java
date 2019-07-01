package ru.levin.tm.api.repository;

import ru.levin.tm.entity.Project;

import java.util.List;

public interface IProjectRepository extends IRepository<Project> {
    void removeByUserId(final String userId);
    List<Project> findAllByUserId(final String userId);
}
