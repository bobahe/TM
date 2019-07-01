package ru.levin.tm.repository;

import ru.levin.tm.api.repository.IUserRepository;
import ru.levin.tm.entity.User;

public final class UserRepository extends AbstractRepository<User> implements IUserRepository {
    public User findOneByLoginAndPassword(final String login, final String password) {
        return storageMap.values().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst().orElse(null);
    }
}
