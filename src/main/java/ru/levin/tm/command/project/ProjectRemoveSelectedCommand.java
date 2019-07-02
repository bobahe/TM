package ru.levin.tm.command.project;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public class ProjectRemoveSelectedCommand extends AbstractCommand {

    @NotNull
    private static final String PROJECT_NOT_SELECTED = "PROJECT IS NOT SELECTED";

    @NotNull
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    @NotNull
    private final IProjectService projectService;

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final ITerminalService terminalService;

    public ProjectRemoveSelectedCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.projectService = bootstrap.getProjectService();
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "project-remove";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Remove selected project";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (selectedProject == null) {
            terminalService.println(PROJECT_NOT_SELECTED);
            return;
        }
        if (bootstrap.getUserService().getCurrentUser() == null) {
            return;
        }

        try {
            projectService.remove(selectedProject);
            final String userId = bootstrap.getUserService().getCurrentUser().getId();
            taskService.removeAllByUserIdAndProjectId(userId, selectedProject.getId());
        } catch (Exception e) {
            terminalService.println(e.getMessage());
            return;
        }

        selectedProject = null;
        terminalService.println(SUCCESS_MESSAGE);
    }

}
