package ru.levin.tm.command.task;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Status;
import ru.levin.tm.exception.NoSelectedTaskException;
import ru.levin.tm.exception.UpdateException;
import ru.levin.tm.util.CommandUtil;

public final class TaskChangeSelectedCommand extends AbstractCommand {

    @NotNull
    protected static final String NAME_PROMPT = "ENTER NAME:";

    @NotNull
    protected static final String DESCRIPTION_PROMPT = "ENTER DESCRIPTION:";

    @NotNull
    protected static final String START_DATE_PROMPT = "ENTER START DATE:";

    @NotNull
    protected static final String END_DATE_PROMPT = "ENTER END DATE:";

    @NotNull
    protected static final String STATUS_PROMPT = "ENTER STATUS:";

    @NotNull
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final ITerminalService terminalService;

    public TaskChangeSelectedCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "task-change";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[CHANGE TASK]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Change selected task";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (selectedTask == null) throw new NoSelectedTaskException();

        terminalService.println(this.getTitle());
        terminalService.println(NAME_PROMPT);
        selectedTask.setName(terminalService.getLine());
        terminalService.println(DESCRIPTION_PROMPT);
        selectedTask.setDescription(terminalService.getLine());
        terminalService.println(START_DATE_PROMPT);
        selectedTask.setStartDate(CommandUtil.parseDate(terminalService.getLine()));
        terminalService.println(END_DATE_PROMPT);
        selectedTask.setEndDate(CommandUtil.parseDate(terminalService.getLine()));
        terminalService.println(STATUS_PROMPT);
        @NotNull final Status[] values = Status.values();
        for (int i = 0; i < values.length; i++) {
            @NotNull final Status status = values[i];
            terminalService.println("\t" + (i + 1) + ". " + status.toString());
        }
        @NotNull final int statusType = Integer.parseInt(terminalService.getLine());
        selectedTask.setStatus(Status.values()[statusType - 1]);

        if (taskService.update(selectedTask) == null) throw new UpdateException();
        terminalService.println(SUCCESS_MESSAGE);
    }

}
