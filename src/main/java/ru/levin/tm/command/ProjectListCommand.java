package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;
import ru.levin.tm.entity.Project;

import java.util.List;
import java.util.Scanner;

public class ProjectListCommand extends Command {
    private ProjectService projectService = ProjectService.getInstance();

    public ProjectListCommand(Scanner scanner) {
        super(scanner);
        this.name = "project-list";
        this.title = "[PROJECT LIST]";
        this.description = "Show all projects";
    }

    @Override
    public void run() {
        List<Project> projectList = projectService.getAll();
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
