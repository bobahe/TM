package ru.levin.tm.command.user;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.entity.User;
import ru.levin.tm.service.UserService;

import java.security.NoSuchAlgorithmException;

public class UserAuthorizeCommand extends AbstractCommand {
    UserService userService;
    Bootstrap bootstrap;

    public UserAuthorizeCommand(Bootstrap bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.name = "login";
        this.description = "log in to application";
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
        System.out.println(LOGIN_PROMPT);
        String login = scanner.nextLine();
        System.out.println(PASSWORD_PROMPT);
        String password = scanner.nextLine();

        User user = null;
        try {
            user = userService.getUserByLoginAndPassword(login, password);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

        if (user == null) {
            System.err.println("There is no such user with entered login and password");
            return;
        }

        bootstrap.setCurrentUser(user);
    }
}
