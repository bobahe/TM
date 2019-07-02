package ru.levin.tm.command.task;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class TaskRemoveSelectedCommand extends AbstractCommand {

    @NotNull
    private static final String TASK_NOT_SELECTED = "TASK IS NOT SELECTED";

    @NotNull
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final ITerminalService terminalService;

    public TaskRemoveSelectedCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "task-remove";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Remove selected task";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (selectedTask == null) {
            terminalService.println(TASK_NOT_SELECTED);
            return;
        }

        try {
            taskService.remove(selectedTask);
        } catch (Exception e) {
            terminalService.println(e.getMessage());
            return;
        }
        selectedTask = null;
        terminalService.println(SUCCESS_MESSAGE);
    }

}
