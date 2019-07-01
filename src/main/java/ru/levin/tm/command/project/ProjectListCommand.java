package ru.levin.tm.command.project;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Project;
import ru.levin.tm.util.CommandUtil;

import java.util.List;

public final class ProjectListCommand extends AbstractCommand {
    private final IProjectService projectService;
    private final ITerminalService terminalService;

    public ProjectListCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.projectService = bootstrap.getProjectService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "project-list";
    }

    @Override
    public String getTitle() {
        return "[PROJECT LIST]";
    }

    @Override
    public String getDescription() {
        return "Show all projects";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        final List<Project> projectList = projectService.findAllByUserId(bootstrap.getUserService().getCurrentUser().getId());
        for (int i = 0; i < projectList.size(); i++) {
            final Project project = projectList.get(i);

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
        }
    }
}
