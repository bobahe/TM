package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;

public final class UserShowProfileCommand extends AbstractCommand {
    private final Bootstrap bootstrap;

    public UserShowProfileCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "show-profile";
        this.description = "Shows user profile";
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
        if (bootstrap.getCurrentUser() == null) {
            return;
        }

        System.out.println("Id: " + bootstrap.getCurrentUser().getId());
        System.out.println("Login: " + bootstrap.getCurrentUser().getLogin());
        System.out.println("Password: " + bootstrap.getCurrentUser().getPassword());
        System.out.println("Role: " + bootstrap.getCurrentUser().getRoleType().name());
    }
}
