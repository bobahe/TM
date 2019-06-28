package ru.levin.tm.command.user;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.service.UserService;

public class UserShowProfileCommand extends AbstractCommand {
    UserService userService;
    Bootstrap bootstrap;

    public UserShowProfileCommand(Bootstrap bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.name = "show-profile";
        this.description = "Shows user profile";
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
        if (bootstrap.getCurrentUser() == null) {
            return;
        }

        System.out.println("Id: " + bootstrap.getCurrentUser().getId());
        System.out.println("Login: " + bootstrap.getCurrentUser().getLogin());
        System.out.println("Password: " + bootstrap.getCurrentUser().getPassword());
        System.out.println("Role: " + bootstrap.getCurrentUser().getRoleType().name());
    }
}
