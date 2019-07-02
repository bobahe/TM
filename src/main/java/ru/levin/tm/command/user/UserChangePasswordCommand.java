package ru.levin.tm.command.user;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.User;

public final class UserChangePasswordCommand extends AbstractCommand {
    @NotNull
    protected static final String PASSWORD_PROMPT = "ENTER PASSWORD:";

    @NotNull
    protected static final String PASSWORD_AGAIN_PROMPT = "ENTER PASSWORD AGAIN:";

    @NotNull
    private final IUserService userService;

    @NotNull
    private final ITerminalService terminalService;

    public UserChangePasswordCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "change-password";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
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
