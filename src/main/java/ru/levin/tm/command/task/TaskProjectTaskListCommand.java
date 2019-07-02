package ru.levin.tm.command.task;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Task;
import ru.levin.tm.util.CommandUtil;

import java.util.List;

public final class TaskProjectTaskListCommand extends AbstractCommand {
    @NotNull
    private static final String SELECT_PROJECT_MESSAGE = "You must select a project before";

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final ITerminalService terminalService;

    public TaskProjectTaskListCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "project-task-list";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[PROJECT TASK LIST]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Show all tasks for selected project";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (selectedProject == null) {
            terminalService.println(SELECT_PROJECT_MESSAGE);
            return;
        }
        if (bootstrap.getUserService().getCurrentUser() == null) return;

        terminalService.println(getTitle() + " for " + selectedProject.getName());
        @NotNull final List<Task> taskList = taskService
                .findAllByUserIdAndProjectId(bootstrap.getUserService().getCurrentUser().getId(), selectedProject.getId());

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
        }
    }
}
