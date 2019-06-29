package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.service.UserService;

public class UserLogoutCommand extends AbstractCommand {
    UserService userService;
    Bootstrap bootstrap;

    public UserLogoutCommand(IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
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
        System.out.println("Good bye, " + bootstrap.getCurrentUser().getLogin());
        bootstrap.setCurrentUser(null);
    }
}
