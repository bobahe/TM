package ru.levin.tm.repository;

import ru.levin.tm.api.IRepository;
import ru.levin.tm.entity.User;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepository implements IRepository<User> {
    Map<String, User> userMap = new LinkedHashMap<>();

    @Override
    public Map<String, User> findAll() {
        return userMap;
    }

    @Override
    public User findOne(String id) {
        return userMap.get(id);
    }

    @Override
    public void persist(User entity) {
        String id = UUID.randomUUID().toString();
        entity.setId(id);
        userMap.put(id, entity);
    }

    @Override
    public void merge(User entity) {
        userMap.put(entity.getId(), entity);
    }

    @Override
    public void remove(User entity) {
        userMap.remove(entity.getId());
    }

    @Override
    public void removeAll() {
        userMap.clear();
    }

    public User findOneByLoginAndPassword(String login, String password) {
        return userMap.values().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst().orElse(null);
    }
}
