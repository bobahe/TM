package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;
import ru.levin.tm.entity.Task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskChangeByIdCommand extends Command {
    private TaskService taskService = TaskService.getInstance();

    public TaskChangeByIdCommand() {
        this.name = "task-change";
        this.description = "Change selected task";
        this.title = "[CHANGE TASK]";
        this.argNameList = new ArrayList<>(1);

        this.argNameList.add("ID TO CHANGE");
        this.argNameList.add("NAME");
        this.argNameList.add("START DATE");
        this.argNameList.add("END DATE");
    }

    public String run(List<String> args) {
        if (args == null) {
            return Command.ERROR_MESSAGE;
        }

        Optional<Task> optionalTask = taskService.get(Long.parseLong(args.get(0)));

        if (!optionalTask.isPresent()) {
            return NO_SUCH_ITEM;
        }

        Task task = optionalTask.get();
        task.setName(args.get(1));
        try {
            task.setStartDate(dateFormat.parse(args.get(2)));
            task.setEndDate(dateFormat.parse(args.get(3)));
        } catch (ParseException e) {
            return ERROR_MESSAGE;
        }

        boolean result = taskService.update(task);

        return result ? Command.SUCCESS_MESSAGE : Command.ERROR_MESSAGE;
    }
}
