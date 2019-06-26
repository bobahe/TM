package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;
import ru.levin.tm.entity.Task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskCreateCommand extends Command {
    private TaskService taskService = TaskService.getInstance();

    public TaskCreateCommand() {
        this.name = "task-create";
        this.title = "[TASK CREATE]";
        this.description = "Create new task";
        this.argNameList = new ArrayList<>(2);

        this.argNameList.add("NAME");
        this.argNameList.add("START DATE");
    }

    public String run(List<String> args) {
        if (args == null) {
            return Command.ERROR_MESSAGE;
        }

        Task task = new Task();
        try {
            task.setName(args.get(0));
            task.setStartDate(dateFormat.parse(args.get(1)));
        } catch (ParseException pe) {
            return Command.ERROR_MESSAGE;
        }

        boolean result = taskService.save(task);

        return result ? Command.SUCCESS_MESSAGE : Command.ERROR_MESSAGE;
    }
}
