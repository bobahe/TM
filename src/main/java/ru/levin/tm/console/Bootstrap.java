package ru.levin.tm.console;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.command.project.*;
import ru.levin.tm.command.system.HelpCommand;
import ru.levin.tm.command.task.*;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.Task;
import ru.levin.tm.entity.User;
import ru.levin.tm.repository.ProjectRepository;
import ru.levin.tm.repository.TaskRepository;
import ru.levin.tm.repository.UserRepository;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.service.TaskService;
import ru.levin.tm.service.UserService;

import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Bootstrap {
    private Scanner scanner = new Scanner(new InputStreamReader(System.in));
    private Map<String, AbstractCommand> commands;

    private IRepository<Project> projectRepository;
    private IRepository<Task> taskRepository;
    private IRepository<User> userRepository;
    private ProjectService projectService;
    private TaskService taskService;
    private UserService userService;

    private User currentUser;

    public void init() {
        commands = new LinkedHashMap<>();

        projectRepository = new ProjectRepository();
        taskRepository = new TaskRepository();
        userRepository = new UserRepository();
        projectService = new ProjectService(projectRepository);
        taskService = new TaskService(taskRepository);
        userService = new UserService(userRepository);

        addCommands();
        process();
    }

    private void addCommands() {
        HelpCommand helpCommand = new HelpCommand(this);

        ProjectListCommand projectListCommand = new ProjectListCommand(this);
        ProjectCreateCommand projectCreateCommand = new ProjectCreateCommand(this);
        ProjectSelectCommand projectSelectCommand = new ProjectSelectCommand(this);
        ProjectRemoveAllCommand projectRemoveAllCommand = new ProjectRemoveAllCommand(this);
        ProjectChangeSelectedCommand projectChangeSelectedCommand = new ProjectChangeSelectedCommand(this);
        ProjectRemoveSelectedCommand projectRemoveSelectedCommand = new ProjectRemoveSelectedCommand(this);

        TaskProjectTaskListCommand taskProjectTaskListCommand = new TaskProjectTaskListCommand(this);
        TaskRemoveAllCommand taskRemoveAllCommand = new TaskRemoveAllCommand(this);
        TaskCreateCommand taskCreateCommand = new TaskCreateCommand(this);
        TaskSelectCommand taskSelectCommand = new TaskSelectCommand(this);
        TaskListCommand taskListCommand = new TaskListCommand(this);
        TaskChangeSelectedCommand taskChangeSelectedCommand = new TaskChangeSelectedCommand(this);
        TaskRemoveSelectedCommand taskRemoveSelectedCommand = new TaskRemoveSelectedCommand(this);
        TaskJoinCommand taskJoinCommand = new TaskJoinCommand(this);

        commands.put(helpCommand.getName(), helpCommand);
        commands.put(projectListCommand.getName(), projectListCommand);
        commands.put(projectCreateCommand.getName(), projectCreateCommand);
        commands.put(projectSelectCommand.getName(), projectSelectCommand);
        commands.put(projectRemoveAllCommand.getName(), projectRemoveAllCommand);
        commands.put(projectChangeSelectedCommand.getName(), projectChangeSelectedCommand);
        commands.put(projectRemoveSelectedCommand.getName(), projectRemoveSelectedCommand);
        commands.put(taskProjectTaskListCommand.getName(), taskProjectTaskListCommand);
        commands.put(taskRemoveAllCommand.getName(), taskRemoveAllCommand);
        commands.put(taskCreateCommand.getName(), taskCreateCommand);
        commands.put(taskSelectCommand.getName(), taskSelectCommand);
        commands.put(taskListCommand.getName(), taskListCommand);
        commands.put(taskChangeSelectedCommand.getName(), taskChangeSelectedCommand);
        commands.put(taskRemoveSelectedCommand.getName(), taskRemoveSelectedCommand);
        commands.put(taskJoinCommand.getName(), taskJoinCommand);
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

    private void invokeCommand(String commandName) {
        AbstractCommand command = commands.get(commandName);

        if (command == null) {
            System.err.println("There is not such command");
            return;
        }

        command.execute();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Map<String, AbstractCommand> getCommands() {
        return commands;
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
