package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.RoleType;
import ru.levin.tm.entity.User;

public final class UserRegisterCommand extends AbstractCommand {
    protected static final String LOGIN_PROMPT = "ENTER LOGIN:";
    protected static final String PASSWORD_PROMPT = "ENTER PASSWORD:";

    private final IUserService userService;
    private final ITerminalService terminalService;

    public UserRegisterCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Register user in the application";
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

        final User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRoleType(RoleType.USER);

        try {
            userService.save(user);
        } catch (Exception e) {
            terminalService.printerr(e.getMessage());
        }

        terminalService.println("You are registered successfully");
    }
}
