package ru.levin.tm.command.task;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Task;
import ru.levin.tm.service.TaskService;

import java.util.List;
import java.util.Scanner;

public class TaskListInSelectedProjectCommand extends AbstractCommand {
    private static final String SELECT_PROJECT_MESSAGE = "You must select a project before";

    private TaskService taskService;

    public TaskListInSelectedProjectCommand(Scanner scanner, TaskService service) {
        super(scanner);
        this.name = "project-task-list";
        this.title = "[PROJECT TASK LIST]";
        this.description = "Show all tasks for selected project";
        this.taskService = service;
    }

    @Override
    public void run() {
        if (selectedProject == null) {
            System.out.println(SELECT_PROJECT_MESSAGE);
            return;
        }

        System.out.println(title + " for " + selectedProject.getName());

        List<Task> taskList = taskService.findByProjectId(selectedProject.getId());

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
