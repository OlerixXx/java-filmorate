package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(Integer id);

    User update(User user);

    User add(User user);

    void remove(Integer id);

    void removeAll();

    User addFriend(Integer userId, Integer friendId);

    User removeFriend(Integer userId, Integer friendId);

    List<User> getAllFriends(Integer userId);

    List<User> getCommonFriends(Integer userId, Integer otherId);
}
