package ru.levin.tm.command.project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class ProjectRemoveAllCommand extends AbstractCommand {
    @NotNull
    private static final String ALL_PROJECTS_REMOVED_MESSAGE = "[ALL PROJECTS REMOVED]\n";

    @NotNull
    private final IProjectService projectService;

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final ITerminalService terminalService;

    public ProjectRemoveAllCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.projectService = bootstrap.getProjectService();
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "project-clear";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Remove all projects";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) return;
        @Nullable final String userId = bootstrap.getUserService().getCurrentUser().getId();
        if (userId == null) return;
        projectService.removeByUserId(userId);

        taskService.getAll().forEach(task -> {
            if (task.getProjectId() != null && userId.equals(task.getUserId())) {
                taskService.remove(task);
            }
        });

        selectedProject = null;
        terminalService.println(ALL_PROJECTS_REMOVED_MESSAGE);
    }
}
