package ru.levin.tm.command.task;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.service.TaskService;

public class TaskSelectCommand extends AbstractCommand {
    private static final String SELECTED_TASK_MESSAGE = "SELECTED TASK: ";
    private static final String SERIAL_NUMBER_PROMPT = "ENTER SERIAL NUMBER:";

    private final TaskService taskService;

    public TaskSelectCommand(Bootstrap bootstrap) {
        super(bootstrap);
        this.name = "task-select";
        this.title = "[TASK SELECT]";
        this.description = "Select task";
        this.taskService = bootstrap.getTaskService();
    }

    public void execute() {
        System.out.println(this.title);
        System.out.println(SERIAL_NUMBER_PROMPT);
        try {
            int index = Integer.parseInt(scanner.nextLine());
            selectedTask = taskService.findAll().get(index - 1);
            System.out.println(SELECTED_TASK_MESSAGE + selectedTask);
        } catch (NumberFormatException nfe) {
            System.out.println(ERROR_MESSAGE);
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println(NO_SUCH_ITEM);
        }
    }
}
