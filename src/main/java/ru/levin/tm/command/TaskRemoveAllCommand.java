package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;

import java.util.List;

public class TaskRemoveAllCommand extends Command {
    private static final String ALL_TASK_REMOVED_MESSAGE = "[ALL TASKS REMOVED]\n";

    private TaskService taskService = TaskService.getInstance();

    public TaskRemoveAllCommand() {
        this.name = "task-clear";
        this.description = "Remove all tasks";
        this.argNameList = null;
    }

    public String run(List<String> args) {
        taskService.deleteAll();
        return ALL_TASK_REMOVED_MESSAGE;
    }
}
