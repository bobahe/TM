package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;
import ru.levin.tm.entity.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRemoveByIdCommand extends Command {
    private TaskService taskService = TaskService.getInstance();

    public TaskRemoveByIdCommand() {
        this.name = "task-remove";
        this.description = "Remove selected task";
        this.argNameList = new ArrayList<>(1);

        this.argNameList.add("ID");
    }

    public String run(List<String> args) {
        if (args == null) {
            return Command.ERROR_MESSAGE;
        }

        Optional<Task> optionalTask = taskService.get(Long.parseLong(args.get(0)));

        if (!optionalTask.isPresent()) {
            return NO_SUCH_ITEM;
        }

        boolean result = taskService.delete(optionalTask.get());

        return result ? Command.SUCCESS_MESSAGE : Command.ERROR_MESSAGE;
    }
}
