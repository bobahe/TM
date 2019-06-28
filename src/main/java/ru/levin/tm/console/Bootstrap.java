package ru.levin.tm.console;

import ru.levin.tm.api.IRepository;
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

        try {
            userService = new UserService(userRepository);
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println(nsae.getMessage());
            return;
        }

        createDefaultUsers();

        addHelpCommand();
        addUnauthorizedCommands();
        process();
    }

    private void createDefaultUsers() {
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setRoleType(RoleType.ADMIN);

        User user = new User();
        user.setLogin("user");
        user.setPassword("user");
        user.setRoleType(RoleType.USER);

        userService.save(admin);
        userService.save(user);
    }

    private void addHelpCommand() {
        HelpCommand helpCommand = new HelpCommand(this);
        commands.put(helpCommand.getName(), helpCommand);
    }

    private void addUnauthorizedCommands() {
        UserAuthorizeCommand userAuthorizeCommand = new UserAuthorizeCommand(this);
        UserRegisterCommand userRegisterCommand = new UserRegisterCommand(this);

        commands.put(userAuthorizeCommand.getName(), userAuthorizeCommand);
        commands.put(userRegisterCommand.getName(), userRegisterCommand);
    }

    private void addAuthorizedCommands() {
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

        UserLogoutCommand userLogoutCommand = new UserLogoutCommand(this);
        UserChangePasswordCommand userChangePasswordCommand = new UserChangePasswordCommand(this);
        UserShowProfileCommand userShowProfileCommand = new UserShowProfileCommand(this);
        UserEditProfileCommand userEditProfileCommand = new UserEditProfileCommand(this);

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
