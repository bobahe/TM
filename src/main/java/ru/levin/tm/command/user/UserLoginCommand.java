package ru.levin.tm.command.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.User;
import ru.levin.tm.exception.AuthorizationException;

public final class UserLoginCommand extends AbstractCommand {

    @NotNull
    protected static final String LOGIN_PROMPT = "ENTER LOGIN:";

    @NotNull
    protected static final String PASSWORD_PROMPT = "ENTER PASSWORD:";

    @NotNull
    private final IUserService userService;

    @NotNull
    private final ITerminalService terminalService;

    public UserLoginCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.terminalService = bootstrap.getTerminalService();
        selectedProject = null;
        selectedTask = null;
    }

    @Override
    @NotNull
    public String getName() {
        return "login";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Log in to application";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public void execute() {
        terminalService.println(LOGIN_PROMPT);
        @NotNull final String login = terminalService.getLine();
        terminalService.println(PASSWORD_PROMPT);
        @NotNull final String password = terminalService.getLine();
        @Nullable final User user = userService.getUserByLoginAndPassword(login, password);
        if (user == null) throw new AuthorizationException();
        bootstrap.getUserService().setCurrentUser(user);
        terminalService.println("Welcome, " + user.getLogin() + "!");
    }

}
