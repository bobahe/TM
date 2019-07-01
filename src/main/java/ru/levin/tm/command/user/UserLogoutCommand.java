package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class UserLogoutCommand extends AbstractCommand {
    private final ITerminalService terminalService;

    public UserLogoutCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "logout";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Log out";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        selectedProject = null;
        selectedTask = null;
        terminalService.println("Good bye, " + bootstrap.getUserService().getCurrentUser().getLogin());
        bootstrap.getUserService().setCurrentUser(null);
    }
}
