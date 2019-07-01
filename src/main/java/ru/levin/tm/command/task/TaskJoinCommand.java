package ru.levin.tm.command.task;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.command.AbstractCommand;

public final class TaskJoinCommand extends AbstractCommand {
    private static final String SELECTED_PROJECT_MESSAGE = "SELECTED TASK: ";
    private static final String SELECTED_TASK_MESSAGE = "SELECTED TASK: ";
    private static final String SELECT_PROJECT_MESSAGE = "You must select a project before";
    private static final String SELECT_TASK_MESSAGE = "You must select a task before";
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    private final ITaskService taskService;

    public TaskJoinCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "task-join";
        this.title = "[JOIN TASK TO PROJECT]";
        this.description = "Join selected task to selected project";
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

    public void execute() {
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
        final String joinAnswer = scanner.nextLine();
        switch (joinAnswer) {
            case "Y":
            case "y":
            case "":
                selectedTask.setProjectId(selectedProject.getId());
                break;
        }

        try {
            taskService.update(selectedTask);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(SUCCESS_MESSAGE);
    }
}
