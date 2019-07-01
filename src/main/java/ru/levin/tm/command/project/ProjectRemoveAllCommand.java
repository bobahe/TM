package ru.levin.tm.command.project;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class ProjectRemoveAllCommand extends AbstractCommand {
    private static final String ALL_PROJECTS_REMOVED_MESSAGE = "[ALL PROJECTS REMOVED]\n";
    private final IProjectService projectService;
    private final ITaskService taskService;
    private final ITerminalService terminalService;

    public ProjectRemoveAllCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.projectService = bootstrap.getProjectService();
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "project-clear";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Remove all projects";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        final String userId = bootstrap.getUserService().getCurrentUser().getId();
        projectService.removeByUserId(userId);

        taskService.getAll().forEach(task -> {
            if (task.getProjectId() != null && task.getUserId().equals(userId)) {
                taskService.remove(task);
            }
        });

        selectedProject = null;
        terminalService.println(ALL_PROJECTS_REMOVED_MESSAGE);
    }
}
