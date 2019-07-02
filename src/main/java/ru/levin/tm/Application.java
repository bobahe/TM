package ru.levin.tm;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.command.project.*;
import ru.levin.tm.command.system.AboutCommand;
import ru.levin.tm.command.system.HelpCommand;
import ru.levin.tm.command.task.*;
import ru.levin.tm.command.user.*;
import ru.levin.tm.console.Bootstrap;

public final class Application {
    @NotNull
    private static final Class[] COMMANDS = {
            HelpCommand.class, UserLoginCommand.class, UserRegisterCommand.class,
            AboutCommand.class, ProjectListCommand.class, ProjectCreateCommand.class,
            ProjectSelectCommand.class, ProjectRemoveAllCommand.class, ProjectChangeSelectedCommand.class,
            ProjectRemoveSelectedCommand.class, TaskProjectTaskListCommand.class,
            TaskRemoveAllCommand.class, TaskCreateCommand.class, TaskSelectCommand.class,
            TaskListCommand.class, TaskChangeSelectedCommand.class, TaskRemoveSelectedCommand.class,
            TaskJoinCommand.class, UserLogoutCommand.class, UserChangePasswordCommand.class,
            UserShowProfileCommand.class, UserEditProfileCommand.class
    };

    public static void main(String[] args) {
        @NotNull final Bootstrap bootstrap = new Bootstrap();
        bootstrap.init(COMMANDS);
    }
}
