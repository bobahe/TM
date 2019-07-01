package ru.levin.tm.service;

import ru.levin.tm.api.repository.IUserRepository;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.entity.User;
import ru.levin.tm.util.ServiceUtil;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public final class UserService extends AbstractEntityService<User, IUserRepository> implements IUserService {
    private final IUserRepository repository;
    private User currentUser;

    public UserService(final IUserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public User save(final User entity) {
        if (entity == null) return null;
        if (entity.getLogin() == null || entity.getLogin().isEmpty()) return null;
        if (entity.getPassword() == null || entity.getPassword().isEmpty()) return null;

        final MessageDigest messageDigest = ServiceUtil.getMD5();
        if (messageDigest == null) return null;

        final String hash = DatatypeConverter.printHexBinary(messageDigest.digest(entity.getPassword().getBytes()));
        entity.setPassword(hash);
        repository.persist(entity);
        return entity;
    }

    @Override
    public User update(final User entity) {
        if (entity == null) return null;
        if (entity.getLogin() == null || entity.getLogin().isEmpty()) return null;
        if (entity.getPassword() == null || entity.getPassword().isEmpty()) return null;

        if (repository.findOne(entity.getId()) == null) {
            throw new IllegalStateException("Can not update user. There is no such user in storage.");
        }

        repository.merge(entity);
        return entity;
    }

    public User getUserByLoginAndPassword(final String login, final String password) {
        if (login == null || login.isEmpty()) return null;
        if (password == null || password.isEmpty()) return null;

        final MessageDigest messageDigest = ServiceUtil.getMD5();
        if (messageDigest == null) return null;
        final String hash = DatatypeConverter.printHexBinary(messageDigest.digest(password.getBytes()));
        return repository.findOneByLoginAndPassword(login, hash);
    }

    public User setNewPassword(final User user, final String password) {
        if (password == null || password.isEmpty()) return null;

        final MessageDigest messageDigest = ServiceUtil.getMD5();
        if (messageDigest == null) return null;
        final String hash = DatatypeConverter.printHexBinary(messageDigest.digest(password.getBytes()));
        user.setPassword(hash);
        return user;
    }

    @Override
    public void setCurrentUser(final User user) {
        currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
