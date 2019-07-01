package ru.levin.tm.command.task;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.command.AbstractCommand;

public final class TaskRemoveAllCommand extends AbstractCommand {
    private static final String ALL_TASK_REMOVED_MESSAGE = "[ALL TASKS REMOVED]\n";

    private final ITaskService taskService;

    public TaskRemoveAllCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "task-clear";
        this.description = "Remove all tasks";
        this.taskService = bootstrap.getTaskService();
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
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        taskService.removeByUserId(bootstrap.getUserService().getCurrentUser().getId());
        selectedTask = null;
        System.out.println(ALL_TASK_REMOVED_MESSAGE);
    }
}
