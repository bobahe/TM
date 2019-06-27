package ru.levin.tm.crud;

import ru.levin.tm.api.AbstractService;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectService implements AbstractService<Project> {
    private TaskService taskService = TaskService.getInstance();
    private static ProjectService INSTANCE = new ProjectService();
    private List<Project> projectList;

    private ProjectService() {
        this.projectList = new ArrayList<>();
    }

    public static ProjectService getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Project> get(UUID id) {
        for (Project project : projectList) {
            if (project.getId().equals(id)) {
                return Optional.of(project);
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
        if (projectList.stream().anyMatch(prj -> prj.getId().equals(project.getId()))) {
            return false;
        }

        project.setId(UUID.randomUUID());
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
        List<Task> taskList = taskService.getAllByProjectId(project.getId());
        taskList.forEach(taskService::delete);
        return projectList.removeIf(prj -> prj.getId().equals(project.getId()));
    }

    @Override
    public void deleteAll() {
        projectList.clear();
    }
}
