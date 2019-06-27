package ru.levin.tm.command.task;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.service.TaskService;

import java.util.Scanner;

public class TaskRemoveAllCommand extends AbstractCommand {
    private static final String ALL_TASK_REMOVED_MESSAGE = "[ALL TASKS REMOVED]\n";

    private TaskService taskService;

    public TaskRemoveAllCommand(Scanner scanner, TaskService service) {
        super(scanner);
        this.name = "task-clear";
        this.description = "Remove all tasks";
        this.taskService = service;
    }

    @Override
    public void run() {
        taskService.removeAll();
        selectedTask = null;
        System.out.println(ALL_TASK_REMOVED_MESSAGE);
    }
}
