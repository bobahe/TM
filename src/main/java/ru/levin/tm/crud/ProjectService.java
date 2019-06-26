package ru.levin.tm.crud;

import ru.levin.tm.api.AbstractService;
import ru.levin.tm.entity.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectService implements AbstractService<Project> {
    private static ProjectService INSTANCE = new ProjectService();
    private List<Project> projectList;
    private long lastIndex;

    private ProjectService() {
        this.projectList = new ArrayList<>();
        lastIndex = 1;
    }

    public static ProjectService getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Project> get(long id) {
        for (Project prj : projectList) {
            if (prj.getId() == id) {
                return Optional.of(prj);
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Project> getAll() {
        return projectList;
    }

    @Override
    public boolean save(Project project) {
        if (projectList.stream().anyMatch(prj -> prj.getId() == project.getId())) {
            return false;
        }

        project.setId(lastIndex++);
        projectList.add(project);
        return true;
    }

    @Override
    public boolean update(Project project) {
        for (int i = 0; i < projectList.size(); i++) {
            if (projectList.get(i).getId() == project.getId()) {
                projectList.set(i, project);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(Project project) {
        return projectList.removeIf(prj -> prj.getId() == project.getId());
    }

    @Override
    public void deleteAll() {
        projectList.clear();
    }
}
