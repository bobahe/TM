package ru.levin.tm.command.task;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Task;
import ru.levin.tm.util.CommandUtil;

public final class TaskCreateCommand extends AbstractCommand {
    protected static final String NAME_PROMPT = "ENTER NAME:";
    protected static final String DESCRIPTION_PROMPT = "ENTER DESCRIPTION:";
    protected static final String START_DATE_PROMPT = "ENTER START DATE:";
    protected static final String END_DATE_PROMPT = "ENTER END DATE:";
    protected static final String SUCCESS_MESSAGE = "[OK]\n";
    private static final String JOIN_TO_PROJECT_PROMPT = "Would you like to attach this task to selected project? (Y/n)";

    private final ITaskService taskService;
    private final ITerminalService terminalService;

    public TaskCreateCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "task-create";
    }

    @Override
    public String getTitle() {
        return "[TASK CREATE]";
    }

    @Override
    public String getDescription() {
        return "Create new task";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    public void execute() {
        final Task task = new Task();

        terminalService.println(this.getTitle());
        terminalService.println(NAME_PROMPT);
        task.setName(terminalService.getLine());
        terminalService.println(DESCRIPTION_PROMPT);
        task.setDescription(terminalService.getLine());
        terminalService.println(START_DATE_PROMPT);
        task.setStartDate(CommandUtil.parseDate(terminalService.getLine()));
        terminalService.println(END_DATE_PROMPT);
        task.setEndDate(CommandUtil.parseDate(terminalService.getLine()));

        task.setUserId(bootstrap.getUserService().getCurrentUser().getId());

        if (selectedProject != null) {
            terminalService.println(JOIN_TO_PROJECT_PROMPT);
            final String joinAnswer = terminalService.getLine();
            switch (joinAnswer) {
                case "n":
                    break;
                default:
                    task.setProjectId(selectedProject.getId());
                    break;
            }
        }

        try {
            taskService.save(task);
        } catch (Exception e) {
            terminalService.println(e.getMessage());
            return;
        }
        terminalService.println(SUCCESS_MESSAGE);
    }
}
