package ru.levin.tm.command.task;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Task;

import java.util.List;

public final class TaskFindCommand extends AbstractCommand {

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final ITerminalService terminalService;

    public TaskFindCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "task-find";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[TASK FIND]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Find task by name or description";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) return;
        terminalService.println("Enter (part) of name or description:");
        @NotNull final List<Task> tasks = taskService.findAllByPartOfNameOrDescription(terminalService.getLine());
        tasks.forEach(project -> terminalService.println(project.toString()));
    }

}
