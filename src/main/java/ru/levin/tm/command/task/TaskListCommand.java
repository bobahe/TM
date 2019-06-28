package ru.levin.tm.command.task;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.entity.Task;
import ru.levin.tm.service.TaskService;

import java.util.List;

public class TaskListCommand extends AbstractCommand {
    private TaskService taskService;

    public TaskListCommand(Bootstrap bootstrap) {
        super(bootstrap);
        this.name = "task-list";
        this.title = "[TASK LIST]";
        this.description = "Show all tasks";
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
    public void execute() {
        List<Task> taskList = taskService.findAll();
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

            System.out.println("\tProject: " + task.getProjectId());
        }
    }
}
