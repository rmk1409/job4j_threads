package ru.job4j.ustorage;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return Objects.isNull(users.putIfAbsent(user.getId(), user));
    }

    public synchronized boolean update(User user) {
        return Objects.nonNull(users.replace(user.getId(), user));
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User fromUser = users.get(fromId);
        if (Objects.isNull(fromUser)) {
            throw new IllegalArgumentException(String.format("User with id %d not found", fromId));
        }
        User toUser = users.get(toId);
        if (Objects.isNull(toUser)) {
            throw new IllegalArgumentException(String.format("User with id %d not found", toId));
        }
        if (amount < fromUser.getAmount()) {
            throw new IllegalArgumentException("Not enough money");
        }
        fromUser.setAmount(fromUser.getAmount() - amount);
        toUser.setAmount(toUser.getAmount() + amount);
    }
}
