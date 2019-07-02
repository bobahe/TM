package ru.levin.tm.command.task;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Task;
import ru.levin.tm.util.CommandUtil;

import java.util.List;

public final class TaskListCommand extends AbstractCommand {
    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final ITerminalService terminalService;

    public TaskListCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "task-list";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[TASK LIST]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Show all tasks";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) return;
        @NotNull final List<Task> taskList = taskService.findAllByUserId(bootstrap.getUserService().getCurrentUser().getId());
        for (int i = 0; i < taskList.size(); i++) {
            @NotNull final Task task = taskList.get(i);
            terminalService.println((i + 1) + ". " + task.getName());
            terminalService.println("\tDescription: " + task.getDescription());
            if (task.getStartDate() != null) {
                terminalService.println("\tStart date: " + CommandUtil.DATE_FORMAT.format(task.getStartDate()));
            } else {
                terminalService.println("\tStar date: not set");
            }
            if (task.getEndDate() != null) {
                terminalService.println("\tEnd date: " + CommandUtil.DATE_FORMAT.format(task.getEndDate()));
            } else {
                terminalService.println("\tEnd date: not set");
            }

            terminalService.println("\tProject: " + task.getProjectId());
        }
    }
}
