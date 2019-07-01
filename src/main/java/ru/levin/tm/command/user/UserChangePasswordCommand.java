package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.User;

public final class UserChangePasswordCommand extends AbstractCommand {
    protected static final String PASSWORD_PROMPT = "ENTER PASSWORD:";
    protected static final String PASSWORD_AGAIN_PROMPT = "ENTER PASSWORD AGAIN:";

    private final IUserService userService;
    private final ITerminalService terminalService;

    public UserChangePasswordCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "change-password";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Change password";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) {
            return;
        }

        terminalService.println(PASSWORD_PROMPT);
        final String passwordOne = terminalService.getLine();
        terminalService.println(PASSWORD_AGAIN_PROMPT);
        final String passwordTwo = terminalService.getLine();

        if (!passwordOne.equals(passwordTwo)) {
            terminalService.println("You have entered different passwords. Abort.");
            return;
        }

        final User user = userService.setNewPassword(bootstrap.getUserService().getCurrentUser(), passwordOne);

        try {
            userService.update(user);
        } catch (Exception e) {
            terminalService.printerr(e.getMessage());
            return;
        }

        terminalService.println("Your password has changed successfully");
    }
}
