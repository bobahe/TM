package ru.levin.tm.api.repository;

import ru.levin.tm.entity.User;

public interface IUserRepository extends IRepository<User> {
    User findOneByLoginAndPassword(final String login, final String hash);
}
