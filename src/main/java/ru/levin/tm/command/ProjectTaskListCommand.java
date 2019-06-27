package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;
import ru.levin.tm.entity.Task;

import java.util.List;
import java.util.Scanner;

public class ProjectTaskListCommand extends Command {
    private static final String SELECT_PROJECT_MESSAGE = "You must select a project before";

    private TaskService taskService = TaskService.getInstance();

    public ProjectTaskListCommand(Scanner scanner) {
        super(scanner);
        this.name = "project-task-list";
        this.title = "[PROJECT TASK LIST]";
        this.description = "Show all tasks for selected project";
    }

    @Override
    public void run() {
        if (selectedProject == null) {
            System.out.println(SELECT_PROJECT_MESSAGE);
            return;
        }

        System.out.println(title + " for " + selectedProject.getName());

        List<Task> taskList = taskService.getAllByProjectId(selectedProject.getId());

        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.println((i + 1) + ". " + task.getName());
            System.out.println("\tDescription: " + task.getDescription());
            if (task.getStartDate() != null) {
                System.out.println("\tStart date: " + DATE_FORMAT.format(task.getStartDate()));
            } else {
                System.out.println("\tStar date: not set");
            }
            if (task.getEndDate() != null) {
                System.out.println("\tEnd date: " + DATE_FORMAT.format(task.getEndDate()));
            } else {
                System.out.println("\tEnd date: not set");
            }
        }
    }
}
