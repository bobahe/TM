package ru.levin.tm.console;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.command.project.*;
import ru.levin.tm.command.system.HelpCommand;
import ru.levin.tm.command.task.*;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.Task;
import ru.levin.tm.repository.ProjectRepository;
import ru.levin.tm.repository.TaskRepository;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.service.TaskService;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bootstrap {
    private Scanner scanner = new Scanner(new InputStreamReader(System.in));
    private List<AbstractCommand> commands;

    private IRepository<Project> projectRepository;
    private IRepository<Task> taskRepository;
    private ProjectService projectService;
    private TaskService taskService;

    public void init() {
        commands = new ArrayList<>();

        projectRepository = new ProjectRepository();
        taskRepository = new TaskRepository();
        projectService = new ProjectService(projectRepository);
        taskService = new TaskService(taskRepository);

        addCommands();
        process();
    }

    private void addCommands() {
        commands.add(new HelpCommand(scanner, commands));
        commands.add(new ProjectListCommand(scanner, projectService));
        commands.add(new ProjectCreateCommand(scanner, projectService));
        commands.add(new ProjectSelectCommand(scanner, projectService));
        commands.add(new ProjectRemoveAllCommand(scanner, projectService));
        commands.add(new ProjectChangeSelectedCommand(scanner, projectService));
        commands.add(new ProjectRemoveSelectedCommand(scanner, projectService));
        commands.add(new TaskListInSelectedProjectCommand(scanner, taskService));
        commands.add(new TaskRemoveAllCommand(scanner, taskService));
        commands.add(new TaskCreateCommand(scanner, taskService));
        commands.add(new TaskSelectCommand(scanner, taskService));
        commands.add(new TaskListCommand(scanner, taskService));
        commands.add(new TaskChangeSelectedCommand(scanner, taskService));
        commands.add(new TaskRemoveSelectedCommand(scanner, taskService));
        commands.add(new TaskToProjectCommand(scanner, taskService));
    }

    private void process() {
        System.out.println("*** WELCOME TO TASK MANAGER ***");
        String command = scanner.nextLine();

        while (!"exit".equals(command)) {
            if ("".equals(command)) {
                command = scanner.nextLine();
                continue;
            }

            invokeCommand(command);

            command = scanner.nextLine();
        }
    }

    private void invokeCommand(String command) {
        for (AbstractCommand cmd : commands) {
            if (command.equals(cmd.getName())) {
                cmd.run();
                return;
            }
        }

        System.err.println("There is not such command");
    }
}
