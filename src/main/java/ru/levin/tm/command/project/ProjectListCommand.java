package ru.levin.tm.command.project;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.entity.Project;
import ru.levin.tm.service.ProjectService;

import java.util.List;

public class ProjectListCommand extends AbstractCommand {
    private ProjectService projectService;

    public ProjectListCommand(Bootstrap bootstrap) {
        super(bootstrap);
        this.name = "project-list";
        this.title = "[PROJECT LIST]";
        this.description = "Show all projects";
        this.projectService = bootstrap.getProjectService();
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
        List<Project> projectList = projectService.findAll();
        for (int i = 0; i < projectList.size(); i++) {
            Project project = projectList.get(i);
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
