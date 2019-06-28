package ru.levin.tm.command.task;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.entity.Task;
import ru.levin.tm.service.TaskService;
import ru.levin.tm.util.CommandUtil;

import java.text.ParseException;
import java.util.Date;

public class TaskChangeSelectedCommand extends AbstractCommand {
    private final TaskService taskService;
    private final Bootstrap bootstrap;

    public TaskChangeSelectedCommand(Bootstrap bootstrap) {
        super(bootstrap);
        this.name = "task-change";
        this.description = "Change selected task";
        this.title = "[CHANGE TASK]";
        this.taskService = bootstrap.getTaskService();
        this.bootstrap = bootstrap;
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

    private Date parseDate(boolean isStartDate) {
        String date = scanner.nextLine();

        if ("".equals(date)) {
            return isStartDate ? selectedProject.getStartDate() : selectedProject.getEndDate();
        }

        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException pe) {
            System.out.println(ERR_PARSE_DATE_MESSAGE);
            return null;
        }
    }
}
