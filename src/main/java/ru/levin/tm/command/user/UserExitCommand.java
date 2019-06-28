package ru.levin.tm.command.user;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.service.UserService;

public class UserExitCommand extends AbstractCommand {
    UserService userService;
    Bootstrap bootstrap;

    public UserExitCommand(Bootstrap bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.name = "logout";
        this.description = "log out";
        this.bootstrap = bootstrap;
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
        bootstrap.setCurrentUser(null);
    }
}
