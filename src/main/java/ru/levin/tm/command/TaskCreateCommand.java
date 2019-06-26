package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;
import ru.levin.tm.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskCreateCommand extends Command {
    private TaskService taskService = TaskService.getInstance();

    public TaskCreateCommand() {
        this.name = "task-create";
        this.title = "[TASK CREATE]";
        this.description = "Create new task";
        this.argNameList = new ArrayList<>(1);

        this.argNameList.add("NAME");
    }

    public String run(List<String> args) {
        if (args == null) {
            return Command.ERROR_MESSAGE;
        }

        boolean result = taskService.save(new Task(args.get(0)));

        return result ? Command.SUCCESS_MESSAGE : Command.ERROR_MESSAGE;
    }
}
