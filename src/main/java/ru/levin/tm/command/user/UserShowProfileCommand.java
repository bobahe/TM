package ru.levin.tm.command.user;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class UserShowProfileCommand extends AbstractCommand {

    @NotNull
    private final ITerminalService terminalService;

    public UserShowProfileCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "show-profile";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Shows user profile";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) return;
        if (bootstrap.getUserService().getCurrentUser().getRoleType() == null) return;

        terminalService.println("Id: " + bootstrap.getUserService().getCurrentUser().getId());
        terminalService.println("Login: " + bootstrap.getUserService().getCurrentUser().getLogin());
        terminalService.println("Password: " + bootstrap.getUserService().getCurrentUser().getPassword());
        terminalService.println("Role: " + bootstrap.getUserService().getCurrentUser().getRoleType().name());
    }

}
