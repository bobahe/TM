package ru.levin.tm.command.project;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.service.TaskService;

public class ProjectRemoveAllCommand extends AbstractCommand {
    private static final String ALL_PROJECTS_REMOVED_MESSAGE = "[ALL PROJECTS REMOVED]\n";
    private ProjectService projectService;
    private TaskService taskService;

    public ProjectRemoveAllCommand(Bootstrap bootstrap) {
        super(bootstrap);
        this.name = "project-clear";
        this.description = "Remove all projects";
        this.projectService = bootstrap.getProjectService();
        this.taskService = bootstrap.getTaskService();
    }

    @Override
    public void execute() {
        projectService.removeAll();

        taskService.findAll().forEach(task -> {
            if (task.getProjectId() != null) {
                taskService.remove(task);
            }
        });

        selectedProject = null;
        System.out.println(ALL_PROJECTS_REMOVED_MESSAGE);
    }
}
