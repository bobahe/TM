package ru.levin.tm.api;

import ru.levin.tm.entity.User;

public interface IUserHandlerServiceLocator extends IServiceLocator {
    User getCurrentUser();
    void setCurrentUser(final User user);
}
