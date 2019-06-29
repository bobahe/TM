package ru.levin.tm.console;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.command.project.*;
import ru.levin.tm.command.system.HelpCommand;
import ru.levin.tm.command.task.*;
import ru.levin.tm.command.user.*;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.RoleType;
import ru.levin.tm.entity.Task;
import ru.levin.tm.entity.User;
import ru.levin.tm.repository.ProjectRepository;
import ru.levin.tm.repository.TaskRepository;
import ru.levin.tm.repository.UserRepository;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.service.TaskService;
import ru.levin.tm.service.UserService;

import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Bootstrap implements IServiceLocator {
    private final Scanner scanner = new Scanner(new InputStreamReader(System.in));
    private final Map<String, AbstractCommand> commands;

    private final ProjectService projectService;
    private final TaskService taskService;
    private UserService userService;

    private User currentUser;

    public Bootstrap() {
        commands = new LinkedHashMap<>();

        IRepository<Project> projectRepository = new ProjectRepository();
        IRepository<Task> taskRepository = new TaskRepository();
        IRepository<User> userRepository = new UserRepository();

        projectService = new ProjectService(projectRepository);
        taskService = new TaskService(taskRepository);

        try {
            userService = new UserService(userRepository);
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println(nsae.getMessage());
        }
    }

    public void init() {
        createDefaultUsers();

        addHelpCommand();
        addUnauthorizedCommands();
        process();
    }

    private void createDefaultUsers() {
        final User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setRoleType(RoleType.ADMIN);

        final User user = new User();
        user.setLogin("user");
        user.setPassword("user");
        user.setRoleType(RoleType.USER);

        userService.save(admin);
        userService.save(user);
    }

    private void addHelpCommand() {
        final HelpCommand helpCommand = new HelpCommand(this);
        commands.put(helpCommand.getName(), helpCommand);
    }

    private void addUnauthorizedCommands() {
        final UserAuthorizeCommand userAuthorizeCommand = new UserAuthorizeCommand(this);
        final UserRegisterCommand userRegisterCommand = new UserRegisterCommand(this);

        commands.put(userAuthorizeCommand.getName(), userAuthorizeCommand);
        commands.put(userRegisterCommand.getName(), userRegisterCommand);
    }

    private void addAuthorizedCommands() {
        final ProjectListCommand projectListCommand = new ProjectListCommand(this);
        final ProjectCreateCommand projectCreateCommand = new ProjectCreateCommand(this);
        final ProjectSelectCommand projectSelectCommand = new ProjectSelectCommand(this);
        final ProjectRemoveAllCommand projectRemoveAllCommand = new ProjectRemoveAllCommand(this);
        final ProjectChangeSelectedCommand projectChangeSelectedCommand = new ProjectChangeSelectedCommand(this);
        final ProjectRemoveSelectedCommand projectRemoveSelectedCommand = new ProjectRemoveSelectedCommand(this);

        final TaskProjectTaskListCommand taskProjectTaskListCommand = new TaskProjectTaskListCommand(this);
        final TaskRemoveAllCommand taskRemoveAllCommand = new TaskRemoveAllCommand(this);
        final TaskCreateCommand taskCreateCommand = new TaskCreateCommand(this);
        final TaskSelectCommand taskSelectCommand = new TaskSelectCommand(this);
        final TaskListCommand taskListCommand = new TaskListCommand(this);
        final TaskChangeSelectedCommand taskChangeSelectedCommand = new TaskChangeSelectedCommand(this);
        final TaskRemoveSelectedCommand taskRemoveSelectedCommand = new TaskRemoveSelectedCommand(this);
        final TaskJoinCommand taskJoinCommand = new TaskJoinCommand(this);

        final UserLogoutCommand userLogoutCommand = new UserLogoutCommand(this);
        final UserChangePasswordCommand userChangePasswordCommand = new UserChangePasswordCommand(this);
        final UserShowProfileCommand userShowProfileCommand = new UserShowProfileCommand(this);
        final UserEditProfileCommand userEditProfileCommand = new UserEditProfileCommand(this);

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
        commands.put(userShowProfileCommand.getName(), userShowProfileCommand);
        commands.put(userEditProfileCommand.getName(), userEditProfileCommand);
        commands.put(userChangePasswordCommand.getName(), userChangePasswordCommand);
        commands.put(userLogoutCommand.getName(), userLogoutCommand);
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
        final AbstractCommand command = commands.get(commandName);

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

        if (currentUser == null) {
            commands.clear();
            addHelpCommand();
            addUnauthorizedCommands();
        } else {
            System.out.println("Hi, " + currentUser.getLogin() + "!");
            commands.clear();
            addHelpCommand();
            addAuthorizedCommands();
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
