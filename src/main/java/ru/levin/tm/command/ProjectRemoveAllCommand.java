package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;

import java.util.Scanner;

public class ProjectRemoveAllCommand extends Command {
    private static final String ALL_PROJECTS_REMOVED_MESSAGE = "[ALL PROJECTS REMOVED]\n";
    private ProjectService projectService = ProjectService.getInstance();

    public ProjectRemoveAllCommand(Scanner scanner) {
        super(scanner);
        this.name = "project-clear";
        this.description = "Remove all projects";
    }

    @Override
    public void run() {
        projectService.deleteAll();
        selectedProject = null;
        System.out.println(ALL_PROJECTS_REMOVED_MESSAGE);
    }
}
