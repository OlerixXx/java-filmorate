package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {

    List<User> getAll();

    User get(Integer id);

    User update(User user);

    User add(User user);

    void remove(Integer id);

    void removeAll();
}
