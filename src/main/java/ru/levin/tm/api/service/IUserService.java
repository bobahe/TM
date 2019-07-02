package ru.levin.tm.api.service;

import org.jetbrains.annotations.Nullable;
import ru.levin.tm.entity.User;

public interface IUserService extends IEntityService<User> {
    @Nullable User getUserByLoginAndPassword(@Nullable final String login, @Nullable final String password);
    @Nullable User setNewPassword(@Nullable final User user, @Nullable final String password);
    void setCurrentUser(@Nullable final User user);
    @Nullable User getCurrentUser();
}
