package ru.levin.tm.command.task;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class TaskRemoveAllCommand extends AbstractCommand {

    @NotNull
    private static final String ALL_TASK_REMOVED_MESSAGE = "[ALL TASKS REMOVED]\n";

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final ITerminalService terminalService;

    public TaskRemoveAllCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "task-clear";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Remove all tasks";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) return;
        taskService.removeByUserId(bootstrap.getUserService().getCurrentUser().getId());
        selectedTask = null;
        terminalService.println(ALL_TASK_REMOVED_MESSAGE);
    }

}
