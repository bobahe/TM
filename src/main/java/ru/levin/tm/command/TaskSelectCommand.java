package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;

import java.util.Scanner;

public class TaskSelectCommand extends Command {
    private static final String SELECTED_TASK_MESSAGE = "SELECTED TASK: ";
    private static final String SERIAL_NUMBER_PROMPT = "ENTER SERIAL NUMBER:";

    private final TaskService taskService = TaskService.getInstance();

    public TaskSelectCommand(Scanner scanner) {
        super(scanner);
        this.name = "task-select";
        this.title = "[TASK SELECT]";
        this.description = "Select task";
    }

    public void run() {
        System.out.println(this.title);
        System.out.println(SERIAL_NUMBER_PROMPT);
        try {
            int index = Integer.parseInt(scanner.nextLine());
            selectedTask = taskService.getAll().get(index - 1);
            System.out.println(SELECTED_TASK_MESSAGE + selectedTask);
            System.out.println(SUCCESS_MESSAGE);
        } catch (NumberFormatException nfe) {
            System.out.println(ERROR_MESSAGE);
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println(NO_SUCH_ITEM);
        }
    }
}
