package ru.levin.tm.command.user;

import ru.levin.tm.api.IUserHandlerServiceLocator;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.User;

public final class UserAuthorizeCommand extends AbstractCommand {
    private final IUserService userService;
    private final IUserHandlerServiceLocator bootstrap;

    public UserAuthorizeCommand(final IUserHandlerServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.name = "login";
        this.description = "Log in to application";
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
        final String login = scanner.nextLine();
        System.out.println(PASSWORD_PROMPT);
        final String password = scanner.nextLine();

        final User user = userService.getUserByLoginAndPassword(login, password);

        if (user == null) {
            System.err.println("There is no such user with entered login and password");
            return;
        }

        bootstrap.setCurrentUser(user);
    }
}
