package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.command.AbstractCommand;

public final class UserLogoutCommand extends AbstractCommand {
    public UserLogoutCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "logout";
        this.description = "Log out";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        selectedProject = null;
        selectedTask = null;
        System.out.println("Good bye, " + bootstrap.getUserService().getCurrentUser().getLogin());
        bootstrap.getUserService().setCurrentUser(null);
    }
}
