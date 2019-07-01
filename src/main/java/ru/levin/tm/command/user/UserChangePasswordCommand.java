package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.User;

public final class UserChangePasswordCommand extends AbstractCommand {
    protected static final String PASSWORD_PROMPT = "ENTER PASSWORD:";
    protected static final String PASSWORD_AGAIN_PROMPT = "ENTER PASSWORD AGAIN:";

    private final IUserService userService;

    public UserChangePasswordCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.name = "change-password";
        this.description = "Change password";
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

        System.out.println(PASSWORD_PROMPT);
        final String passwordOne = scanner.nextLine();
        System.out.println(PASSWORD_AGAIN_PROMPT);
        final String passwordTwo = scanner.nextLine();

        if (!passwordOne.equals(passwordTwo)) {
            System.out.println("You have entered different passwords. Abort.");
            return;
        }

        final User user = userService.setNewPassword(bootstrap.getUserService().getCurrentUser(), passwordOne);

        try {
            userService.update(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Your password has changed successfully");
    }
}
