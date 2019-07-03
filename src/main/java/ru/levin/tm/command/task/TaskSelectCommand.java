package ru.levin.tm.command.task;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.exception.NoCurrentUserException;

public final class TaskSelectCommand extends AbstractCommand {

    @NotNull
    private static final String SELECTED_TASK_MESSAGE = "SELECTED TASK: ";

    @NotNull
    private static final String SERIAL_NUMBER_PROMPT = "ENTER SERIAL NUMBER:";

    @NotNull
    protected static final String ERROR_MESSAGE = "[ERROR]\n";

    @NotNull
    protected static final String NO_SUCH_ITEM = "[THERE IS NO SUCH ITEM]\n";

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final ITerminalService terminalService;

    public TaskSelectCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "task-select";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[TASK SELECT]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Select task";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) throw new NoCurrentUserException();
        terminalService.println(this.getTitle());
        terminalService.println(SERIAL_NUMBER_PROMPT);
        final int index = Integer.parseInt(terminalService.getLine());
        selectedTask = taskService.findOneByIndex(bootstrap.getUserService().getCurrentUser().getId(), index);
        terminalService.println(SELECTED_TASK_MESSAGE + selectedTask);
    }

}
