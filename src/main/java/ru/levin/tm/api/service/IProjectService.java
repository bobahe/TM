package ru.levin.tm.api.service;

import ru.levin.tm.entity.Project;

import java.util.List;

public interface IProjectService extends IEntityService<Project> {
    void removeByUserId(final String userId);
    Project findOneByIndex(final String userId, final int index);
    List<Project> findAllByUserId(final String userId);
}
