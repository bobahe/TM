package ru.levin.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.entity.User;

public interface IUserRepository extends IRepository<User> {
    @Nullable User findOneByLoginAndPassword(@NotNull final String login, @NotNull final String hash);
}
