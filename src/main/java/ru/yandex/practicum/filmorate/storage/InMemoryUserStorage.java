package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IncorrectCountException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User get(Integer id) {
        if (id == null) {
            throw new ValidationException("ID не может быть null!");
        } else if (id <= 0) {
            throw new IncorrectCountException("Отрицательный ID!");
        } else if (!users.containsKey(id)) {
            throw new ValidationException("Такого id не существует!");
        } else {
            return users.get(id);
        }
    }

    @Override
    public User update(User user) {
        if (user.getId() == null || !users.containsKey(user.getId())) {
            throw new ValidationException("Такого id не существует!");
        } else {
            if (checkName(user)) {
                user.setName(user.getLogin());
            }
            users.remove(user.getId());
            users.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public User add(User user) {
        user.setId(id);
        if (checkName(user)) {
            user.setName(user.getLogin());
        }
        users.put(id, user);
        id += 1;
        return user;
    }

    @Override
    public void remove(Integer id) {
        if (id == null) {
            throw new ValidationException("ID не может быть null!");
        } else if (id <= 0) {
            throw new IncorrectCountException("Отрицательный ID!");
        } else if (!users.containsKey(id)) {
            throw new ValidationException("Такого id не существует!");
        } else {
            users.remove(id);
        }
    }

    @Override
    public void removeAll() {
        for (Integer id : users.keySet()) {
            users.remove(id);
        }
    }

    private boolean checkName(User user) {
        return user.getName() == null || user.getName().isEmpty();
    }

}
