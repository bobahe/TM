package ru.levin.tm.console;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.repository.IProjectRepository;
import ru.levin.tm.api.repository.ITaskRepository;
import ru.levin.tm.api.repository.IUserRepository;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.RoleType;
import ru.levin.tm.entity.User;
import ru.levin.tm.repository.ProjectRepository;
import ru.levin.tm.repository.TaskRepository;
import ru.levin.tm.repository.UserRepository;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.service.TaskService;
import ru.levin.tm.service.TerminalService;
import ru.levin.tm.service.UserService;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor
public final class Bootstrap implements IServiceLocator {
    @NotNull
    @Getter
    private final Map<String, AbstractCommand> commands = new LinkedHashMap<>();

    @NotNull
    @Getter
    private final ITerminalService terminalService = new TerminalService();

    @NotNull
    private final IProjectRepository projectRepository = new ProjectRepository();

    @NotNull
    private final ITaskRepository taskRepository = new TaskRepository();

    @NotNull
    private final IUserRepository userRepository = new UserRepository();

    @NotNull
    @Getter
    private final IProjectService projectService = new ProjectService(projectRepository);

    @NotNull
    @Getter
    private final ITaskService taskService = new TaskService(taskRepository);

    @NotNull
    @Getter
    private final IUserService userService = new UserService(userRepository);

    public void init(@NotNull final Class[] commands) {
        createDefaultUsers();
        registerCommands(commands);
        process();
    }

    private void registerCommands(@NotNull final Class[] commands) {
        for (final Class<?> cmdClass : commands) {
            if (cmdClass.getSuperclass().equals(AbstractCommand.class)) {
                try {
                    @NotNull final Constructor<?> constructor = cmdClass.getConstructor(IServiceLocator.class);
                    AbstractCommand command =
                            ((AbstractCommand) constructor.newInstance(this));
                    this.commands.put(command.getName(), command);
                } catch (Exception e) {
                    terminalService.printerr(e.getMessage());
                }
            }
        }
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
}
