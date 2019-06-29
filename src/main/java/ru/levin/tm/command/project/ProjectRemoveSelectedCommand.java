package ru.levin.tm.command.project;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Task;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.service.TaskService;

import java.util.List;

public class ProjectRemoveSelectedCommand extends AbstractCommand {
    private static final String PROJECT_NOT_SELECTED = "PROJECT IS NOT SELECTED";

    private final ProjectService projectService;
    private final TaskService taskService;

    public ProjectRemoveSelectedCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "project-remove";
        this.description = "Remove selected project";
        this.projectService = bootstrap.getProjectService();
        this.taskService = bootstrap.getTaskService();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute() {
        if (selectedProject == null) {
            System.out.println(PROJECT_NOT_SELECTED);
            return;
        }

        try {
            projectService.remove(selectedProject);
            final List<Task> tasksInProjectList = taskService.findByProjectId(selectedProject.getId());
            tasksInProjectList.forEach(taskService::remove);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        selectedProject = null;
        System.out.println(SUCCESS_MESSAGE);
    }
}
