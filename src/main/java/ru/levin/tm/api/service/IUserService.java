package ru.levin.tm.api.service;

import ru.levin.tm.entity.User;

public interface IUserService extends IEntityService<User> {
    User getUserByLoginAndPassword(final String login, final String password);
    User setNewPassword(final User user, final String password);
}
