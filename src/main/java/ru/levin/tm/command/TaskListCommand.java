package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;
import ru.levin.tm.entity.Task;

import java.util.List;

public class TaskListCommand extends Command {
    private TaskService taskService = TaskService.getInstance();

    public TaskListCommand() {
        this.name = "task-list";
        this.title = "[TASK LIST]";
        this.description = "Show all tasks";
        this.argNameList = null;
    }

    public String run(List<String> args) {
        StringBuilder result = new StringBuilder();
        List<Task> taskList = taskService.getAll();
        for (Task task : taskList) {
            result.append(task.getId()).append(". ").append(task.getName()).append(System.lineSeparator());
        }

        return result.toString();
    }
}
