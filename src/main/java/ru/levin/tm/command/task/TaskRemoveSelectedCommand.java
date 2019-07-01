package ru.levin.tm.command.task;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class TaskRemoveSelectedCommand extends AbstractCommand {
    private static final String TASK_NOT_SELECTED = "TASK IS NOT SELECTED";
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    private final ITaskService taskService;
    private final ITerminalService terminalService;

    public TaskRemoveSelectedCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "task-remove";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
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
