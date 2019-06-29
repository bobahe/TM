package ru.levin.tm.command.project;

import ru.levin.tm.api.IUserHandlerServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Project;
import ru.levin.tm.service.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

public final class ProjectListCommand extends AbstractCommand {
    private final ProjectService projectService;
    private final IUserHandlerServiceLocator bootstrap;

    public ProjectListCommand(final IUserHandlerServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "project-list";
        this.title = "[PROJECT LIST]";
        this.description = "Show all projects";
        this.projectService = bootstrap.getProjectService();
        this.bootstrap = bootstrap;
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
        final List<Project> projectList = projectService.findAll().stream()
                .filter(project -> project.getUserId().equals(bootstrap.getCurrentUser().getId()))
                .collect(Collectors.toList());
        for (int i = 0; i < projectList.size(); i++) {
            final Project project = projectList.get(i);

            System.out.println(title);
            System.out.println((i + 1) + ". " + project.getName());
            System.out.println("\tDescription: " + project.getDescription());
            if (project.getStartDate() != null) {
                System.out.println("\tStart date: " + DATE_FORMAT.format(project.getStartDate()));
            } else {
                System.out.println("\tStar date: not set");
            }
            if (project.getEndDate() != null) {
                System.out.println("\tEnd date: " + DATE_FORMAT.format(project.getEndDate()));
            } else {
                System.out.println("\tEnd date: not set");
            }
        }
    }
}
