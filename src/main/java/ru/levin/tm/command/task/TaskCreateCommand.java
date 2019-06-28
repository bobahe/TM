package ru.levin.tm.command.task;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.entity.Task;
import ru.levin.tm.service.TaskService;
import ru.levin.tm.util.CommandUtil;

public class TaskCreateCommand extends AbstractCommand {
    private static final String JOIN_TO_PROJECT_PROMPT = "Would you like to attach this task to selected project? (Y/n)";

    private TaskService taskService;

    public TaskCreateCommand(Bootstrap bootstrap) {
        super(bootstrap);
        this.name = "task-create";
        this.title = "[TASK CREATE]";
        this.description = "Create new task";
        this.taskService = bootstrap.getTaskService();
    }

    public void execute() {
        Task task = new Task();

        System.out.println(this.title);
        System.out.println(NAME_PROMPT);
        task.setName(scanner.nextLine());
        System.out.println(DESCRIPTION_PROMPT);
        task.setDescription(scanner.nextLine());
        System.out.println(START_DATE_PROMPT);
        task.setStartDate(CommandUtil.parseDate(scanner));
        System.out.println(END_DATE_PROMPT);
        task.setEndDate(CommandUtil.parseDate(scanner));

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

        try {
            taskService.save(task);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(SUCCESS_MESSAGE);
    }
}
