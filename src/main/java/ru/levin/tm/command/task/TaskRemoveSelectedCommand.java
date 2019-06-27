package ru.levin.tm.command.task;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.service.TaskService;

import java.util.Scanner;

public class TaskRemoveSelectedCommand extends AbstractCommand {
    private static final String TASK_NOT_SELECTED = "TASK IS NOT SELECTED";

    private TaskService taskService;

    public TaskRemoveSelectedCommand(Scanner scanner, TaskService service) {
        super(scanner);
        this.name = "task-remove";
        this.description = "Remove selected task";
        this.taskService = service;
    }

    @Override
    public void run() {
        if (selectedTask == null) {
            System.out.println(TASK_NOT_SELECTED);
            return;
        }

        try {
            taskService.remove(selectedTask);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        selectedTask = null;
        System.out.println(SUCCESS_MESSAGE);
    }
}
