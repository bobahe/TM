package ru.levin.tm.command.user;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.exception.NoCurrentUserException;
import ru.levin.tm.exception.UpdateException;

public final class UserEditProfileCommand extends AbstractCommand {

    @NotNull
    protected static final String LOGIN_PROMPT = "ENTER LOGIN:";

    @NotNull
    private final IUserService userService;

    @NotNull
    private final ITerminalService terminalService;

    public UserEditProfileCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "edit-profile";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Edit user profile";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) throw new NoCurrentUserException();
        terminalService.println(LOGIN_PROMPT);
        bootstrap.getUserService().getCurrentUser().setLogin(terminalService.getLine());
        if (userService.update(bootstrap.getUserService().getCurrentUser()) == null) throw new UpdateException();
        terminalService.println("Your profile changed successfully");
    }

}
