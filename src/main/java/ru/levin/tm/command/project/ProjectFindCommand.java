package ru.levin.tm.command.project;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Project;
import ru.levin.tm.exception.NoCurrentUserException;

import java.util.List;

public final class ProjectFindCommand extends AbstractCommand {

    @NotNull
    private final IProjectService projectService;

    @NotNull
    private final ITerminalService terminalService;

    public ProjectFindCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.projectService = bootstrap.getProjectService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "project-find";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[PROJECT FIND]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Find project by name or description";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) throw new NoCurrentUserException();
        terminalService.println("Enter (part) of name or description:");
        @NotNull final List<Project> projects = projectService.findAllByPartOfNameOrDescription(terminalService.getLine());
        projects.forEach(project -> terminalService.println(project.toString()));
    }

}
