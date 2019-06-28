package ru.levin.tm.command.user;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.service.UserService;

public class UserEditProfileCommand extends AbstractCommand {
    UserService userService;
    Bootstrap bootstrap;

    public UserEditProfileCommand(Bootstrap bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.name = "edit-profile";
        this.description = "Edit user profile";
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

        System.out.println(LOGIN_PROMPT);
        bootstrap.getCurrentUser().setLogin(scanner.nextLine());

        try {
            userService.update(bootstrap.getCurrentUser());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Your profile changed successfully");
    }
}
