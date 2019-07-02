package ru.levin.tm.console;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.ICommandHandlerServiceLocator;
import ru.levin.tm.api.repository.IProjectRepository;
import ru.levin.tm.api.repository.ITaskRepository;
import ru.levin.tm.api.repository.IUserRepository;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.command.project.*;
import ru.levin.tm.command.system.AboutCommand;
import ru.levin.tm.command.system.HelpCommand;
import ru.levin.tm.command.task.*;
import ru.levin.tm.command.user.*;
import ru.levin.tm.entity.RoleType;
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
    @NotNull
    private final Map<String, AbstractCommand> commands = new LinkedHashMap<>();

    @NotNull
    private final ITerminalService terminalService = new TerminalService();

    @NotNull
    private final IProjectRepository projectRepository = new ProjectRepository();

    @NotNull
    private final ITaskRepository taskRepository = new TaskRepository();

    @NotNull
    private final IUserRepository userRepository = new UserRepository();

    @NotNull
    private final IProjectService projectService = new ProjectService(projectRepository);

    @NotNull
    private final ITaskService taskService = new TaskService(taskRepository);

    @NotNull
    private final IUserService userService = new UserService(userRepository);

    public void init() {
        createDefaultUsers();
        addCommands();
        process();
    }

    private void addCommands() {
        @NotNull final HelpCommand helpCommand = new HelpCommand(this);
        @NotNull final UserLoginCommand userLoginCommand = new UserLoginCommand(this);
        @NotNull final UserRegisterCommand userRegisterCommand = new UserRegisterCommand(this);
        @NotNull final AboutCommand aboutCommand = new AboutCommand(this);

        @NotNull final ProjectListCommand projectListCommand = new ProjectListCommand(this);
        @NotNull final ProjectCreateCommand projectCreateCommand = new ProjectCreateCommand(this);
        @NotNull final ProjectSelectCommand projectSelectCommand = new ProjectSelectCommand(this);
        @NotNull final ProjectRemoveAllCommand projectRemoveAllCommand = new ProjectRemoveAllCommand(this);
        @NotNull final ProjectChangeSelectedCommand projectChangeSelectedCommand = new ProjectChangeSelectedCommand(this);
        @NotNull final ProjectRemoveSelectedCommand projectRemoveSelectedCommand = new ProjectRemoveSelectedCommand(this);

        @NotNull final TaskProjectTaskListCommand taskProjectTaskListCommand = new TaskProjectTaskListCommand(this);
        @NotNull final TaskRemoveAllCommand taskRemoveAllCommand = new TaskRemoveAllCommand(this);
        @NotNull final TaskCreateCommand taskCreateCommand = new TaskCreateCommand(this);
        @NotNull final TaskSelectCommand taskSelectCommand = new TaskSelectCommand(this);
        @NotNull final TaskListCommand taskListCommand = new TaskListCommand(this);
        @NotNull final TaskChangeSelectedCommand taskChangeSelectedCommand = new TaskChangeSelectedCommand(this);
        @NotNull final TaskRemoveSelectedCommand taskRemoveSelectedCommand = new TaskRemoveSelectedCommand(this);
        @NotNull final TaskJoinCommand taskJoinCommand = new TaskJoinCommand(this);

        @NotNull final UserLogoutCommand userLogoutCommand = new UserLogoutCommand(this);
        @NotNull final UserChangePasswordCommand userChangePasswordCommand = new UserChangePasswordCommand(this);
        @NotNull final UserShowProfileCommand userShowProfileCommand = new UserShowProfileCommand(this);
        @NotNull final UserEditProfileCommand userEditProfileCommand = new UserEditProfileCommand(this);

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
        @NotNull final User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setRoleType(RoleType.ADMIN);

        @NotNull final User user = new User();
        user.setLogin("user");
        user.setPassword("user");
        user.setRoleType(RoleType.USER);

        userService.save(admin);
        userService.save(user);
    }

    private void process() {
        terminalService.println("*** WELCOME TO TASK MANAGER ***");
        @NotNull String command = terminalService.getLine();

        while (!"exit".equals(command)) {
            if (command.isEmpty()) {
                command = terminalService.getLine();
                continue;
            }
            invokeCommand(command);
            command = terminalService.getLine();
        }
    }

    private void invokeCommand(String commandName) {
        @Nullable final AbstractCommand command = commands.get(commandName);

        if (command == null) {
            terminalService.printerr("There is not such command");
            return;
        }
        if (userService.getCurrentUser() == null && command.isRequiredAuthorization()) {
            terminalService.println("You have to log in first.");
            return;
        }
        command.execute();
    }

    @NotNull
    public Map<String, AbstractCommand> getCommands() {
        return commands;
    }

    @NotNull
    public IProjectService getProjectService() {
        return projectService;
    }

    @NotNull
    public ITaskService getTaskService() {
        return taskService;
    }

    @NotNull
    public IUserService getUserService() {
        return userService;
    }

    @NotNull
    public ITerminalService getTerminalService() {
        return terminalService;
    }

    private void registerCommand(AbstractCommand command) {
        commands.put(command.getName(), command);
    }
}
