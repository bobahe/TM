package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class TaskChangeSelectedCommand extends Command {
    private static final String TASK_NOT_SELECTED_ERR = "TASK IS NOT SELECTED\n";

    private TaskService taskService = TaskService.getInstance();

    public TaskChangeSelectedCommand(Scanner scanner) {
        super(scanner);
        this.name = "task-change";
        this.description = "Change selected task";
        this.title = "[CHANGE TASK]";
    }

    @Override
    public void run() {
        if (selectedTask == null) {
            System.out.println(TASK_NOT_SELECTED_ERR);
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

        taskService.update(selectedTask);
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
