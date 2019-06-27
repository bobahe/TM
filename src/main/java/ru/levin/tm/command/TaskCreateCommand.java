package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;
import ru.levin.tm.entity.Task;

import java.util.Scanner;

public class TaskCreateCommand extends Command {
    private static final String JOIN_TO_PROJECT_PROMPT = "Would you like to attach this task to selected project? (Y/n)";

    private TaskService taskService = TaskService.getInstance();

    public TaskCreateCommand(Scanner scanner) {
        super(scanner);
        this.name = "task-create";
        this.title = "[TASK CREATE]";
        this.description = "Create new task";
    }

    public void run() {
        Task task = new Task();

        System.out.println(this.title);
        System.out.println(NAME_PROMPT);
        task.setName(scanner.nextLine());
        System.out.println(DESCRIPTION_PROMPT);
        task.setDescription(scanner.nextLine());
        System.out.println(START_DATE_PROMPT);
        task.setStartDate(CommandUtils.parseDate(scanner));
        System.out.println(END_DATE_PROMPT);
        task.setEndDate(CommandUtils.parseDate(scanner));

        if (selectedProject != null) {
            System.out.println(JOIN_TO_PROJECT_PROMPT);
            String joinAnswer = scanner.nextLine();
            switch (joinAnswer) {
                case "Y":
                case "y":
                case "":
                    task.setProjectId(selectedProject.getId());
                    break;
            }
        }

        taskService.save(task);
        System.out.println(SUCCESS_MESSAGE);
    }
}
