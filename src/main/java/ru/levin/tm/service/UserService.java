package ru.levin.tm.service;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.User;
import ru.levin.tm.repository.UserRepository;
import ru.levin.tm.util.ServiceUtil;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserService extends AbstractEntityService<User> {
    private static final String PROP_LOGIN = "login";
    private static final String PROP_PASSWORD = "password";

    private IRepository<User> repository;
    MessageDigest md;

    public UserService(IRepository<User> userRepository) throws NoSuchAlgorithmException {
        this.repository = userRepository;

        md = MessageDigest.getInstance("MD5");
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(repository.findAll().values());
    }

    @Override
    public void save(User entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getLogin(), PROP_LOGIN);
        ServiceUtil.checkNullOrEmpty(entity.getPassword(), PROP_PASSWORD);
        String hash = DatatypeConverter.printHexBinary(md.digest(entity.getPassword().getBytes()));
        entity.setPassword(hash);
        repository.persist(entity);
    }

    @Override
    public void update(User entity) {
        ServiceUtil.checkNull(entity);
        ServiceUtil.checkNullOrEmpty(entity.getLogin(), PROP_LOGIN);
        ServiceUtil.checkNullOrEmpty(entity.getPassword(), PROP_PASSWORD);
        repository.merge(entity);
    }

    @Override
    public void remove(User entity) {
        repository.remove(entity);
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }

    public User getUserByLoginAndPassword(String login, String password) throws NoSuchAlgorithmException {
        String hash = DatatypeConverter.printHexBinary(md.digest(password.getBytes()));
        return ((UserRepository) repository).findOneByLoginAndPassword(login, hash);
    }

    public User setNewPassword(User user, String password) {
        String hash = DatatypeConverter.printHexBinary(md.digest(password.getBytes()));
        user.setPassword(hash);
        return user;
    }
}
