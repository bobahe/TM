package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.entity.RoleType;
import ru.levin.tm.entity.User;
import ru.levin.tm.service.UserService;

public class UserRegisterCommand extends AbstractCommand {
    UserService userService;
    Bootstrap bootstrap;

    public UserRegisterCommand(IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.name = "register";
        this.description = "Register user in the application";
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
        System.out.println(LOGIN_PROMPT);
        String login = scanner.nextLine();
        System.out.println(PASSWORD_PROMPT);
        String password = scanner.nextLine();

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRoleType(RoleType.USER);

        try {
            userService.save(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("You are registered successfully");
    }
}
