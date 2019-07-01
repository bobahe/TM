package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.User;

public final class UserLoginCommand extends AbstractCommand {
    protected static final String LOGIN_PROMPT = "ENTER LOGIN:";
    protected static final String PASSWORD_PROMPT = "ENTER PASSWORD:";

    private final IUserService userService;
    private final ITerminalService terminalService;

    public UserLoginCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
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
        final String login = terminalService.getLine();
        terminalService.println(PASSWORD_PROMPT);
        final String password = terminalService.getLine();

        final User user = userService.getUserByLoginAndPassword(login, password);

        if (user == null) {
            terminalService.printerr("There is no such user with entered login and password");
            return;
        }

        bootstrap.getUserService().setCurrentUser(user);
        terminalService.println("Welcome, " + user.getLogin() + "!");
    }
}
