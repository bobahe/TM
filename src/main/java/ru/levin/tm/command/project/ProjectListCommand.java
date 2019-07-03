package ru.levin.tm.command.project;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Project;
import ru.levin.tm.exception.NoCurrentUserException;
import ru.levin.tm.util.CommandUtil;

import java.util.List;

public final class ProjectListCommand extends AbstractCommand {

    @NotNull
    private final IProjectService projectService;

    @NotNull
    private final ITerminalService terminalService;

    public ProjectListCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.projectService = bootstrap.getProjectService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "project-list";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[PROJECT LIST]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Show all projects";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) throw new NoCurrentUserException();
        @NotNull final List<Project> projectList =
                projectService.findAllByUserId(bootstrap.getUserService().getCurrentUser().getId());
        terminalService.println("Select option to sort list (1 by default):");
        terminalService.println("1. Saved order");
        terminalService.println("2. Start date");
        terminalService.println("3. End date");
        terminalService.println("4. Status");
        @NotNull final String orderType = terminalService.getLine();
        CommandUtil.sort(orderType, projectList);
        for (int i = 0; i < projectList.size(); i++) {
            @NotNull final Project project = projectList.get(i);
            if (project.getStatus() == null) return;

            terminalService.println(this.getTitle());
            terminalService.println((i + 1) + ". " + project.getName());
            terminalService.println("\tDescription: " + project.getDescription());
            if (project.getStartDate() != null) {
                terminalService.println("\tStart date: " + CommandUtil.DATE_FORMAT.format(project.getStartDate()));
            } else {
                terminalService.println("\tStar date: not set");
            }
            if (project.getEndDate() != null) {
                terminalService.println("\tEnd date: " + CommandUtil.DATE_FORMAT.format(project.getEndDate()));
            } else {
                terminalService.println("\tEnd date: not set");
            }
            terminalService.println("\tStatus: " + project.getStatus().getDisplayName());
        }
    }

}
