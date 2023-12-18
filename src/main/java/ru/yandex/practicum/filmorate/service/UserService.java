package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface UserService {
    List<User> getAll();

    User get(Integer id);

    User update(User user);

    User add(User user);

    void remove(Integer id);

    void removeAll();

    User addFriend(Integer userId, Integer friendId);

    User removeFriend(Integer userId, Integer friendId);

    List<User> getAllFriends(Integer UserId);

    List<User> getCommonFriends(Integer UserId, Integer otherId);
}
