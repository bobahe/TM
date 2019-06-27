package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;

import java.util.Scanner;

public class ProjectRemoveSelectedCommand extends Command {
    private static final String PROJECT_NOT_SELECTED = "PROJECT IS NOT SELECTED";

    private ProjectService projectService = ProjectService.getInstance();

    public ProjectRemoveSelectedCommand(Scanner scanner) {
        super(scanner);
        this.name = "project-remove";
        this.description = "Remove selected project";
    }

    @Override
    public void run() {
        if (selectedProject == null) {
            System.out.println(PROJECT_NOT_SELECTED);
            return;
        }

        projectService.delete(selectedProject);
        selectedProject = null;
        System.out.println(SUCCESS_MESSAGE);
    }
}
