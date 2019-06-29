package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;

public final class UserLogoutCommand extends AbstractCommand {
    private final Bootstrap bootstrap;

    public UserLogoutCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "logout";
        this.description = "Log out";
        this.bootstrap = ((Bootstrap) bootstrap);
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
    public void execute() {
        selectedProject = null;
        selectedTask = null;
        System.out.println("Good bye, " + bootstrap.getCurrentUser().getLogin());
        bootstrap.setCurrentUser(null);
    }
}
