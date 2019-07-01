package ru.levin.tm.command.task;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Task;
import ru.levin.tm.util.CommandUtil;

import java.text.ParseException;
import java.util.Date;

public final class TaskChangeSelectedCommand extends AbstractCommand {
    protected static final String NAME_PROMPT = "ENTER NAME:";
    protected static final String DESCRIPTION_PROMPT = "ENTER DESCRIPTION:";
    protected static final String START_DATE_PROMPT = "ENTER START DATE:";
    protected static final String END_DATE_PROMPT = "ENTER END DATE:";
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    private final ITaskService taskService;

    public TaskChangeSelectedCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "task-change";
        this.description = "Change selected task";
        this.title = "[CHANGE TASK]";
        this.taskService = bootstrap.getTaskService();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (CommandUtil.isSelectedObjectNull(selectedTask, Task.class)) {
            return;
        }

        System.out.println(this.title);
        System.out.println(NAME_PROMPT);
        selectedTask.setName(scanner.nextLine());
        System.out.println(DESCRIPTION_PROMPT);
        selectedTask.setDescription(scanner.nextLine());
        System.out.println(START_DATE_PROMPT);
        selectedTask.setStartDate(parseDate(true));
        System.out.println(END_DATE_PROMPT);
        selectedTask.setEndDate(parseDate(false));

        try {
            taskService.update(selectedTask);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(SUCCESS_MESSAGE);
    }

    private Date parseDate(final boolean isStartDate) {
        final String date = scanner.nextLine();

        if ("".equals(date)) {
            return isStartDate ? selectedTask.getStartDate() : selectedTask.getEndDate();
        }

        try {
            return CommandUtil.DATE_FORMAT.parse(date);
        } catch (ParseException pe) {
            System.out.println(ERR_PARSE_DATE_MESSAGE);
            return null;
        }
    }
}
