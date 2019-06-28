package ru.levin.tm.service;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.User;
import ru.levin.tm.repository.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserService extends AbstractEntityService<User> {
    private IRepository<User> repository;

    public UserService(IRepository<User> userRepository) {
        this.repository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(repository.findAll().values());
    }

    @Override
    public void save(User entity) {
        repository.persist(entity);
    }

    @Override
    public void update(User entity) {
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
        MessageDigest md = MessageDigest.getInstance("MD5");
        String hash = new String(md.digest(password.getBytes()));
        return ((UserRepository) repository).findOneByLoginAndPassword(login, hash);
    }
}
