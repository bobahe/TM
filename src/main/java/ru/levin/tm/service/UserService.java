package ru.levin.tm.service;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.User;
import ru.levin.tm.repository.UserRepository;
import ru.levin.tm.util.ServiceUtil;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class UserService extends AbstractEntityService<User, IUserRepository> implements IUserService {
    private final IUserRepository repository;
    private User currentUser;

    public UserService(final IRepository<User> repository) throws NoSuchAlgorithmException {
        super(repository);

        md = MessageDigest.getInstance("MD5");
    }

    @Override
    public User save(final User entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getLogin() == null || "".equals(entity.getLogin())) {
            return null;
        }
        if (entity.getPassword() == null || "".equals(entity.getPassword())) {
            return null;
        }

        final MessageDigest messageDigest = getMD5();
        if (messageDigest == null) {
            return null;
        }

        final String hash = DatatypeConverter.printHexBinary(messageDigest.digest(entity.getPassword().getBytes()));
        entity.setPassword(hash);
        repository.persist(entity);
        return entity;
    }

    @Override
    public User update(final User entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getLogin() == null || "".equals(entity.getLogin())) {
            return null;
        }
        if (entity.getPassword() == null || "".equals(entity.getPassword())) {
            return null;
        }

        if (repository.findOne(entity.getId()) == null) {
            throw new IllegalStateException("Can not update user. There is no such user in storage.");
        }

        repository.merge(entity);
        return entity;
    }

    public User getUserByLoginAndPassword(final String login, final String password) {
        final String hash = DatatypeConverter.printHexBinary(md.digest(password.getBytes()));
        return ((UserRepository) repository).findOneByLoginAndPassword(login, hash);
    }

    public User setNewPassword(final User user, final String password) {
        if ("".equals(password)) {
            return null;
        }

        final MessageDigest messageDigest = getMD5();
        if (messageDigest == null) {
            return null;
        }
        final String hash = DatatypeConverter.printHexBinary(getMD5().digest(password.getBytes()));
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

    private MessageDigest getMD5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
