package ru.levin.tm.command.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Task;
import ru.levin.tm.entity.User;
import ru.levin.tm.util.CommandUtil;

public final class TaskCreateCommand extends AbstractCommand {
    @NotNull
    protected static final String NAME_PROMPT = "ENTER NAME:";

    @NotNull
    protected static final String DESCRIPTION_PROMPT = "ENTER DESCRIPTION:";

    @NotNull
    protected static final String START_DATE_PROMPT = "ENTER START DATE:";

    @NotNull
    protected static final String END_DATE_PROMPT = "ENTER END DATE:";

    @NotNull
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    @NotNull
    private static final String JOIN_TO_PROJECT_PROMPT = "Would you like to attach this task to selected project? (Y/n)";

    @NotNull
    private final ITaskService taskService;

    @NotNull
    private final ITerminalService terminalService;

    public TaskCreateCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "task-create";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[TASK CREATE]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Create new task";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    public void execute() {
        @Nullable final User currentUser = bootstrap.getUserService().getCurrentUser();
        if (currentUser == null || currentUser.getId() == null) return;
        if (selectedProject == null || selectedProject.getId() == null) return;
        @NotNull final Task task = new Task();

        terminalService.println(this.getTitle());
        terminalService.println(NAME_PROMPT);
        task.setName(terminalService.getLine());
        terminalService.println(DESCRIPTION_PROMPT);
        task.setDescription(terminalService.getLine());
        terminalService.println(START_DATE_PROMPT);
        task.setStartDate(CommandUtil.parseDate(terminalService.getLine()));
        terminalService.println(END_DATE_PROMPT);
        task.setEndDate(CommandUtil.parseDate(terminalService.getLine()));
        task.setUserId(currentUser.getId());

        terminalService.println(JOIN_TO_PROJECT_PROMPT);
        @NotNull final String joinAnswer = terminalService.getLine();
        if (!"n".equals(joinAnswer)) task.setProjectId(selectedProject.getId());

        try {
            taskService.save(task);
        } catch (Exception e) {
            terminalService.printerr(e.getMessage());
            return;
        }
        terminalService.println(SUCCESS_MESSAGE);
    }
}
