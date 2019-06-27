package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;

import java.util.Scanner;

public class TaskRemoveSelectedCommand extends Command {
    private static final String TASK_NOT_SELECTED = "TASK IS NOT SELECTED";

    private TaskService taskService = TaskService.getInstance();

    public TaskRemoveSelectedCommand(Scanner scanner) {
        super(scanner);
        this.name = "task-remove";
        this.description = "Remove selected task";
    }

    @Override
    public void run() {
        if (selectedTask == null) {
            System.out.println(TASK_NOT_SELECTED);
            return;
        }

        taskService.delete(selectedTask);
        selectedTask = null;
        System.out.println(SUCCESS_MESSAGE);
    }
}
