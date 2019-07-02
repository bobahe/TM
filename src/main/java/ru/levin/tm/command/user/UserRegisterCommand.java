package ru.levin.tm.command.user;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.RoleType;
import ru.levin.tm.entity.User;

public final class UserRegisterCommand extends AbstractCommand {
    @NotNull
    protected static final String LOGIN_PROMPT = "ENTER LOGIN:";

    @NotNull
    protected static final String PASSWORD_PROMPT = "ENTER PASSWORD:";

    @NotNull
    private final IUserService userService;

    @NotNull
    private final ITerminalService terminalService;

    public UserRegisterCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "register";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
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
        @NotNull final String login = terminalService.getLine();
        terminalService.println(PASSWORD_PROMPT);
        @NotNull final String password = terminalService.getLine();

        @NotNull final User user = new User();
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
