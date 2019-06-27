package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;

import java.util.Scanner;

public class TaskToProjectCommand extends Command {
    private static final String SELECTED_PROJECT_MESSAGE = "SELECTED TASK: ";
    private static final String SELECTED_TASK_MESSAGE = "SELECTED TASK: ";
    private static final String SELECT_PROJECT_MESSAGE = "You must select a project before";
    private static final String SELECT_TASK_MESSAGE = "You must select a task before";

    private final TaskService taskService = TaskService.getInstance();

    public TaskToProjectCommand(Scanner scanner) {
        super(scanner);
        this.name = "task-join";
        this.title = "[JOIN TASK TO PROJECT]";
        this.description = "Join selected task to selected project";
    }

    public void run() {
        if (selectedProject == null) {
            System.out.println(SELECT_PROJECT_MESSAGE);
            return;
        }
        if (selectedTask == null) {
            System.out.println(SELECT_TASK_MESSAGE);
            return;
        }

        System.out.println(this.title);
        System.out.println(SELECTED_PROJECT_MESSAGE + selectedProject);
        System.out.println(SELECTED_TASK_MESSAGE + selectedTask);
        System.out.println(description + "? (Y/n)");
        String joinAnswer = scanner.nextLine();
        switch (joinAnswer) {
            case "Y":
            case "y":
            case "":
                selectedTask.setProjectId(selectedProject.getId());
                break;
        }

        taskService.update(selectedTask);
        System.out.println(SUCCESS_MESSAGE);
    }
}
