package ru.levin.tm.command.project;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.service.ProjectService;

import java.util.Scanner;

public class ProjectSelectCommand extends AbstractCommand {
    private static final String SELECTED_PROJECT_MESSAGE = "SELECTED PROJECT: ";
    private static final String SERIAL_NUMBER_PROMPT = "ENTER SERIAL NUMBER:";

    private final ProjectService projectService;

    public ProjectSelectCommand(Scanner scanner, ProjectService service) {
        super(scanner);
        this.name = "project-select";
        this.title = "[PROJECT SELECT]";
        this.description = "Select project";
        this.projectService = service;
    }

    public void run() {
        System.out.println(this.title);
        System.out.println(SERIAL_NUMBER_PROMPT);
        try {
            int index = Integer.parseInt(scanner.nextLine());
            selectedProject = projectService.findAll().get(index - 1);
            System.out.println(SELECTED_PROJECT_MESSAGE + selectedProject);
        } catch (NumberFormatException nfe) {
            System.out.println(ERROR_MESSAGE);
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println(NO_SUCH_ITEM);
        }
    }
}
