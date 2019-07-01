package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;

public final class UserEditProfileCommand extends AbstractCommand {
    protected static final String LOGIN_PROMPT = "ENTER LOGIN:";

    private final IUserService userService;

    public UserEditProfileCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.name = "edit-profile";
        this.description = "Edit user profile";
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

        System.out.println(LOGIN_PROMPT);
        bootstrap.getUserService().getCurrentUser().setLogin(scanner.nextLine());

        try {
            userService.update(bootstrap.getUserService().getCurrentUser());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Your profile changed successfully");
    }
}
