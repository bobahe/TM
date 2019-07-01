package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.command.AbstractCommand;

public final class UserShowProfileCommand extends AbstractCommand {
    public UserShowProfileCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "show-profile";
        this.description = "Shows user profile";
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
        if (bootstrap.getUserService().getCurrentUser() == null) {
            return;
        }

        System.out.println("Id: " + bootstrap.getUserService().getCurrentUser().getId());
        System.out.println("Login: " + bootstrap.getUserService().getCurrentUser().getLogin());
        System.out.println("Password: " + bootstrap.getUserService().getCurrentUser().getPassword());
        System.out.println("Role: " + bootstrap.getUserService().getCurrentUser().getRoleType().name());
    }
}
