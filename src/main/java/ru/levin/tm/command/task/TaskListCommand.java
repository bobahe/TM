package ru.levin.tm.command.task;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Task;
import ru.levin.tm.service.TaskService;

import java.util.List;

public final class TaskListCommand extends AbstractCommand {
    private final TaskService taskService;
    private final IUserHandlerServiceLocator bootstrap;

    public TaskListCommand(final IServiceLocator bootstrap) {
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
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        final List<Task> taskList = taskService.findAll().stream()
                .filter(task -> task.getUserId().equals(bootstrap.getCurrentUser().getId()))
                .collect(Collectors.toList());
        for (int i = 0; i < taskList.size(); i++) {
            final Task task = taskList.get(i);
            System.out.println((i + 1) + ". " + task.getName());
            System.out.println("\tDescription: " + task.getDescription());
            if (task.getStartDate() != null) {
                System.out.println("\tStart date: " + CommandUtil.DATE_FORMAT.format(task.getStartDate()));
            } else {
                System.out.println("\tStar date: not set");
            }
            if (task.getEndDate() != null) {
                System.out.println("\tEnd date: " + CommandUtil.DATE_FORMAT.format(task.getEndDate()));
            } else {
                System.out.println("\tEnd date: not set");
            }

            System.out.println("\tProject: " + task.getProjectId());
        }
    }
}
