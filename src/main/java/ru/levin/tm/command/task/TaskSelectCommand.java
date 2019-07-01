package ru.levin.tm.command.task;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.command.AbstractCommand;

public final class TaskSelectCommand extends AbstractCommand {
    private static final String SELECTED_TASK_MESSAGE = "SELECTED TASK: ";
    private static final String SERIAL_NUMBER_PROMPT = "ENTER SERIAL NUMBER:";
    protected static final String ERROR_MESSAGE = "[ERROR]\n";
    protected static final String NO_SUCH_ITEM = "[THERE IS NO SUCH ITEM]\n";

    private final ITaskService taskService;

    public TaskSelectCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "task-select";
        this.title = "[TASK SELECT]";
        this.description = "Select task";
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
        System.out.println(this.title);
        System.out.println(SERIAL_NUMBER_PROMPT);
        try {
            final int index = Integer.parseInt(scanner.nextLine());
            selectedTask = taskService.findOneByIndex(bootstrap.getUserService().getCurrentUser().getId(), index);
            System.out.println(SELECTED_TASK_MESSAGE + selectedTask);
        } catch (NumberFormatException nfe) {
            System.out.println(ERROR_MESSAGE);
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println(NO_SUCH_ITEM);
        }
    }
}
