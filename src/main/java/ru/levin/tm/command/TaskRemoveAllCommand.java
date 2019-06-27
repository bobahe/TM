package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;

import java.util.Scanner;

public class TaskRemoveAllCommand extends Command {
    private static final String ALL_TASK_REMOVED_MESSAGE = "[ALL TASKS REMOVED]\n";

    private TaskService taskService = TaskService.getInstance();

    public TaskRemoveAllCommand(Scanner scanner) {
        super(scanner);
        this.name = "task-clear";
        this.description = "Remove all tasks";
    }

    @Override
    public void run() {
        taskService.deleteAll();
        selectedTask = null;
        System.out.println(ALL_TASK_REMOVED_MESSAGE);
    }
}
