package ru.levin.tm.command.task;

import ru.levin.tm.api.IUserHandlerServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.service.TaskService;

public final class TaskRemoveAllCommand extends AbstractCommand {
    private static final String ALL_TASK_REMOVED_MESSAGE = "[ALL TASKS REMOVED]\n";

    private final TaskService taskService;
    private final IUserHandlerServiceLocator bootstrap;

    public TaskRemoveAllCommand(final IUserHandlerServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "task-clear";
        this.description = "Remove all tasks";
        this.taskService = bootstrap.getTaskService();
        this.bootstrap = bootstrap;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute() {
        taskService.removeByUserId(bootstrap.getCurrentUser().getId());
        selectedTask = null;
        System.out.println(ALL_TASK_REMOVED_MESSAGE);
    }
}
