package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;

import java.util.Scanner;

public class ProjectSelectCommand extends Command {
    private static final String SELECTED_PROJECT_MESSAGE = "SELECTED PROJECT: ";
    private static final String SERIAL_NUMBER_PROMPT = "ENTER SERIAL NUMBER:";

    private final ProjectService projectService = ProjectService.getInstance();

    public ProjectSelectCommand(Scanner scanner) {
        super(scanner);
        this.name = "project-select";
        this.title = "[PROJECT SELECT]";
        this.description = "Select project";
    }

    public void run() {
        System.out.println(this.title);
        System.out.println(SERIAL_NUMBER_PROMPT);
        try {
            int index = Integer.parseInt(scanner.nextLine());
            selectedProject = projectService.getAll().get(index - 1);
            System.out.println(SELECTED_PROJECT_MESSAGE + selectedProject);
            System.out.println(SUCCESS_MESSAGE);
        } catch (NumberFormatException nfe) {
            System.out.println(ERROR_MESSAGE);
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println(NO_SUCH_ITEM);
        }
    }
}
