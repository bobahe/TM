package ru.levin.tm.command.project;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.command.AbstractCommand;

public class ProjectRemoveSelectedCommand extends AbstractCommand {
    private static final String PROJECT_NOT_SELECTED = "PROJECT IS NOT SELECTED";
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    private final IProjectService projectService;
    private final ITaskService taskService;

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
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (selectedProject == null) {
            System.out.println(PROJECT_NOT_SELECTED);
            return;
        }

        try {
            projectService.remove(selectedProject);
            final String userId = bootstrap.getUserService().getCurrentUser().getId();
            taskService.removeAllByUserIdAndProjectId(userId, selectedProject.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        selectedProject = null;
        System.out.println(SUCCESS_MESSAGE);
    }
}
