package ru.levin.tm.command.project;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.service.ProjectService;

import java.util.Scanner;

public class ProjectRemoveSelectedCommand extends AbstractCommand {
    private static final String PROJECT_NOT_SELECTED = "PROJECT IS NOT SELECTED";

    private ProjectService projectService;

    public ProjectRemoveSelectedCommand(Scanner scanner, ProjectService service) {
        super(scanner);
        this.name = "project-remove";
        this.description = "Remove selected project";
        this.projectService = service;
    }

    @Override
    public void run() {
        if (selectedProject == null) {
            System.out.println(PROJECT_NOT_SELECTED);
            return;
        }

        try {
            projectService.remove(selectedProject);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        selectedProject = null;
        System.out.println(SUCCESS_MESSAGE);
    }
}
