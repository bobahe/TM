package ru.levin.tm.command.project;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Project;
import ru.levin.tm.service.ProjectService;

import java.util.List;
import java.util.Scanner;

public class ProjectListCommand extends AbstractCommand {
    private ProjectService projectService;

    public ProjectListCommand(Scanner scanner, ProjectService service) {
        super(scanner);
        this.name = "project-list";
        this.title = "[PROJECT LIST]";
        this.description = "Show all projects";
        this.projectService = service;
    }

    @Override
    public void run() {
        List<Project> projectList = projectService.findAll();
        for (int i = 0; i < projectList.size(); i++) {
            Project project = projectList.get(i);
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
