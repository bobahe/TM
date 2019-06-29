package ru.levin.tm.command.task;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.service.TaskService;

public class TaskRemoveSelectedCommand extends AbstractCommand {
    private static final String TASK_NOT_SELECTED = "TASK IS NOT SELECTED";

    private final TaskService taskService;

    public TaskRemoveSelectedCommand(IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "task-remove";
        this.description = "Remove selected task";
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
        if (selectedTask == null) {
            System.out.println(TASK_NOT_SELECTED);
            return;
        }

        try {
            taskService.remove(selectedTask);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        selectedTask = null;
        System.out.println(SUCCESS_MESSAGE);
    }
}
