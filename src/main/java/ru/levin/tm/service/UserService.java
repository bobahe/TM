package ru.levin.tm.service;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.User;
import ru.levin.tm.repository.UserRepository;
import ru.levin.tm.util.ServiceUtil;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class UserService extends AbstractEntityService<User> {
    private static final String PROP_LOGIN = "login";
    private static final String PROP_PASSWORD = "password";

    private final MessageDigest md;

    public UserService(final IRepository<User> repository) throws NoSuchAlgorithmException {
        super(repository);

        md = MessageDigest.getInstance("MD5");
    }

    @Override
    public void save(final User entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getLogin(), PROP_LOGIN);
        ServiceUtil.checkNullOrEmpty(entity.getPassword(), PROP_PASSWORD);
        String hash = DatatypeConverter.printHexBinary(md.digest(entity.getPassword().getBytes()));
        entity.setPassword(hash);
        repository.persist(entity);
    }

    @Override
    public void update(final User entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getLogin(), PROP_LOGIN);
        ServiceUtil.checkNullOrEmpty(entity.getPassword(), PROP_PASSWORD);

        if (repository.findOne(entity.getId()) == null) {
            throw new IllegalStateException("Can not update user. There is no such user in storage.");
        }

        repository.merge(entity);
    }

    public User getUserByLoginAndPassword(final String login, final String password) {
        final String hash = DatatypeConverter.printHexBinary(md.digest(password.getBytes()));
        return ((UserRepository) repository).findOneByLoginAndPassword(login, hash);
    }

    public User setNewPassword(final User user, final String password) {
        final String hash = DatatypeConverter.printHexBinary(md.digest(password.getBytes()));
        user.setPassword(hash);
        return user;
    }
}
