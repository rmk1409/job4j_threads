package ru.job4j.ustorage;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    public boolean add(User user) {
        return Objects.isNull(users.put(user.getId(), user));
    }

    public boolean update(User user) {
        return !add(user);
    }

    public boolean delete(User user) {
        return Objects.nonNull(users.remove(user.getId()));
    }

    public void transfer(int fromId, int toId, int amount) {
        User fromUser = users.get(fromId);
        User toUser = users.get(toId);
        if (amount < fromUser.getAmount()) {
            throw new IllegalArgumentException("Not enough money");
        }
        User lock1 = fromUser;
        User lock2 = toUser;
        if (toId < fromId) {
            User tmp = lock1;
            lock1 = lock2;
            lock2 = tmp;
        }
        synchronized (lock1) {
            synchronized (lock2) {
                fromUser.setAmount(fromUser.getAmount() - amount);
                toUser.setAmount(toUser.getAmount() + amount);
            }
        }
    }
}
