package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.RoleType;
import ru.levin.tm.entity.User;

public final class UserRegisterCommand extends AbstractCommand {
    protected static final String LOGIN_PROMPT = "ENTER LOGIN:";
    protected static final String PASSWORD_PROMPT = "ENTER PASSWORD:";

    private final IUserService userService;

    public UserRegisterCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.name = "register";
        this.description = "Register user in the application";
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
        return false;
    }

    @Override
    public void execute() {
        System.out.println(LOGIN_PROMPT);
        final String login = scanner.nextLine();
        System.out.println(PASSWORD_PROMPT);
        final String password = scanner.nextLine();

        final User user = new User();
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
