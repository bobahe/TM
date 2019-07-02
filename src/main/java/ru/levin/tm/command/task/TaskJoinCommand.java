package ru.levin.tm.command.task;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class TaskJoinCommand extends AbstractCommand {

    @NotNull
    private static final String SELECTED_PROJECT_MESSAGE = "SELECTED TASK: ";

    @NotNull
    private static final String SELECTED_TASK_MESSAGE = "SELECTED TASK: ";

    @NotNull
    private static final String SELECT_PROJECT_MESSAGE = "You must select a project before";

    @NotNull
    private static final String SELECT_TASK_MESSAGE = "You must select a task before";

    @NotNull
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final ITerminalService terminalService;

    public TaskJoinCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "task-join";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[JOIN TASK TO PROJECT]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Join selected task to selected project";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    public void execute() {
        if (selectedProject == null || selectedProject.getId() == null) {
            terminalService.println(SELECT_PROJECT_MESSAGE);
            return;
        }
        if (selectedTask == null) {
            terminalService.println(SELECT_TASK_MESSAGE);
            return;
        }

        terminalService.println(this.getTitle());
        terminalService.println(SELECTED_PROJECT_MESSAGE + selectedProject);
        terminalService.println(SELECTED_TASK_MESSAGE + selectedTask);
        terminalService.println(getDescription() + "? (Y/n)");
        @NotNull final String joinAnswer = terminalService.getLine();
        if (!"n".equals(joinAnswer)) selectedTask.setProjectId(selectedProject.getId());

        try {
            taskService.update(selectedTask);
        } catch (Exception e) {
            terminalService.printerr(e.getMessage());
            return;
        }
        terminalService.println(SUCCESS_MESSAGE);
    }

}
