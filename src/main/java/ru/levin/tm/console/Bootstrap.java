package ru.levin.tm.console;

import ru.levin.tm.api.ICommandHandlerServiceLocator;
import ru.levin.tm.api.IRepository;
import ru.levin.tm.api.IUserHandlerServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.command.project.*;
import ru.levin.tm.command.system.AboutCommand;
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
import ru.levin.tm.service.TerminalService;
import ru.levin.tm.service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Bootstrap implements ICommandHandlerServiceLocator {
    private final Map<String, AbstractCommand> commands = new LinkedHashMap<>();

    private final ITerminalService terminalService = new TerminalService();

    private final IProjectRepository projectRepository = new ProjectRepository();
    private final ITaskRepository taskRepository = new TaskRepository();
    private final IUserRepository userRepository = new UserRepository();
    private final IProjectService projectService = new ProjectService(projectRepository);
    private final ITaskService taskService = new TaskService(taskRepository);
    private final IUserService userService = new UserService(userRepository);

    public void init() {
        createDefaultUsers();
        addCommands();
        process();
    }

    private void addCommands() {
        final HelpCommand helpCommand = new HelpCommand(this);
        final UserLoginCommand userLoginCommand = new UserLoginCommand(this);
        final UserRegisterCommand userRegisterCommand = new UserRegisterCommand(this);
        final AboutCommand aboutCommand = new AboutCommand(this);

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

        registerCommand(helpCommand);
        registerCommand(userLoginCommand);
        registerCommand(userRegisterCommand);
        registerCommand(projectListCommand);
        registerCommand(projectCreateCommand);
        registerCommand(projectSelectCommand);
        registerCommand(projectRemoveAllCommand);
        registerCommand(projectChangeSelectedCommand);
        registerCommand(projectRemoveSelectedCommand);
        registerCommand(taskProjectTaskListCommand);
        registerCommand(taskRemoveAllCommand);
        registerCommand(taskCreateCommand);
        registerCommand(taskSelectCommand);
        registerCommand(taskListCommand);
        registerCommand(taskChangeSelectedCommand);
        registerCommand(taskRemoveSelectedCommand);
        registerCommand(taskJoinCommand);
        registerCommand(userShowProfileCommand);
        registerCommand(userEditProfileCommand);
        registerCommand(userChangePasswordCommand);
        registerCommand(userLogoutCommand);
        registerCommand(aboutCommand);
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

    private void process() {
        System.out.println("*** WELCOME TO TASK MANAGER ***");
        String command = terminalService.getScanner().nextLine();

        while (!"exit".equals(command)) {
            if ("".equals(command)) {
                command = terminalService.getScanner().nextLine();
                continue;
            }
            invokeCommand(command);
            command = terminalService.getScanner().nextLine();
        }
    }

    private void invokeCommand(String commandName) {
        final AbstractCommand command = commands.get(commandName);

        if (command == null) {
            System.err.println("There is not such command");
            return;
        }
        if (userService.getCurrentUser() == null && command.isRequiredAuthorization()) {
            System.out.println("You have to log in first.");
            return;
        }
        command.execute();
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

    public ITerminalService getTerminalService() {
        return terminalService;
    }

    private void registerCommand(AbstractCommand command) {
        commands.put(command.getName(), command);
    }
}
