package ru.levin.tm.command.task;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class TaskJoinCommand extends AbstractCommand {
    private static final String SELECTED_PROJECT_MESSAGE = "SELECTED TASK: ";
    private static final String SELECTED_TASK_MESSAGE = "SELECTED TASK: ";
    private static final String SELECT_PROJECT_MESSAGE = "You must select a project before";
    private static final String SELECT_TASK_MESSAGE = "You must select a task before";
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    private final ITaskService taskService;
    private final ITerminalService terminalService;

    public TaskJoinCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "task-join";
    }

    @Override
    public String getTitle() {
        return "[JOIN TASK TO PROJECT]";
    }

    @Override
    public String getDescription() {
        return "Join selected task to selected project";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    public void execute() {
        if (selectedProject == null) {
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
        final String joinAnswer = terminalService.getLine();
        switch (joinAnswer) {
            case "n":
                break;
            default:
                selectedTask.setProjectId(selectedProject.getId());
                break;
        }

        try {
            taskService.update(selectedTask);
        } catch (Exception e) {
            terminalService.println(e.getMessage());
            return;
        }
        terminalService.println(SUCCESS_MESSAGE);
    }
}
