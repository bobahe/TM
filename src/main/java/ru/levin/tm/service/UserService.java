package ru.levin.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.repository.IUserRepository;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.entity.User;
import ru.levin.tm.util.ServiceUtil;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public final class UserService extends AbstractEntityService<User, IUserRepository> implements IUserService {

    @NotNull
    private final IUserRepository repository;

    @Nullable
    private User currentUser;

    public UserService(@NotNull final IUserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Nullable
    public User save(@Nullable final User entity) {
        if (entity == null) return null;
        if (entity.getLogin() == null || entity.getLogin().isEmpty()) return null;
        if (entity.getPassword() == null || entity.getPassword().isEmpty()) return null;

        @Nullable final MessageDigest messageDigest = ServiceUtil.getMD5();
        if (messageDigest == null) return null;

        @NotNull final String hash = DatatypeConverter.printHexBinary(messageDigest.digest(entity.getPassword().getBytes()));
        entity.setPassword(hash);
        repository.persist(entity);
        return entity;
    }

    @Override
    @Nullable
    public User update(@Nullable final User entity) {
        if (entity == null) return null;
        if (entity.getLogin() == null || entity.getLogin().isEmpty()) return null;
        if (entity.getPassword() == null || entity.getPassword().isEmpty()) return null;
        if (entity.getId() == null || entity.getId().isEmpty()) return null;

        if (repository.findOne(entity.getId()) == null) {
            throw new IllegalStateException("Can not update user. There is no such user in storage.");
        }

        repository.merge(entity);
        return entity;
    }

    @Override
    @Nullable
    public User getUserByLoginAndPassword(@Nullable final String login, @Nullable final String password) {
        if (login == null || login.isEmpty()) return null;
        if (password == null || password.isEmpty()) return null;

        @Nullable final MessageDigest messageDigest = ServiceUtil.getMD5();
        if (messageDigest == null) return null;
        @NotNull final String hash = DatatypeConverter.printHexBinary(messageDigest.digest(password.getBytes()));
        return repository.findOneByLoginAndPassword(login, hash);
    }

    @Override
    @Nullable
    public User setNewPassword(@Nullable final User user, @Nullable final String password) {
        if (password == null || password.isEmpty()) return null;
        if (user == null) return null;

        @Nullable final MessageDigest messageDigest = ServiceUtil.getMD5();
        if (messageDigest == null) return null;
        @NotNull final String hash = DatatypeConverter.printHexBinary(messageDigest.digest(password.getBytes()));
        user.setPassword(hash);
        return user;
    }

    @Override
    public void setCurrentUser(@Nullable final User user) {
        currentUser = user;
    }

    @Override
    @Nullable
    public User getCurrentUser() {
        return currentUser;
    }

}
