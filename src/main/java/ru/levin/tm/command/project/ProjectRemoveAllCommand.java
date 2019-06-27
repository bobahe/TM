package ru.levin.tm.command.project;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.service.ProjectService;

import java.util.Scanner;

public class ProjectRemoveAllCommand extends AbstractCommand {
    private static final String ALL_PROJECTS_REMOVED_MESSAGE = "[ALL PROJECTS REMOVED]\n";
    private ProjectService projectService;

    public ProjectRemoveAllCommand(Scanner scanner, ProjectService service) {
        super(scanner);
        this.name = "project-clear";
        this.description = "Remove all projects";
        this.projectService = service;
    }

    @Override
    public void run() {
        projectService.removeAll();
        selectedProject = null;
        System.out.println(ALL_PROJECTS_REMOVED_MESSAGE);
    }
}
