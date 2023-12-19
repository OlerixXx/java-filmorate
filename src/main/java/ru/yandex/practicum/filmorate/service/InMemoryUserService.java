package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InMemoryUserService implements UserService {

    private final UserStorage userStorage;

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }

    @Override
    public User get(Integer id) {
        return userStorage.get(id);
    }

    @Override
    public User update(User user) {
        log.info("Получен PUT запрос - UserController! Обновление пользователя");
        return userStorage.update(user);
    }

    @Override
    public User add(User user) {
        log.info("Получен POST запрос - UserController! Создание пользователя");
        return userStorage.add(user);
    }

    @Override
    public void remove(Integer id) {
        log.info("Получен DELETE запрос - UserController! Удаление пользователя");
        userStorage.remove(id);
    }

    @Override
    public void removeAll() {
        log.info("Получен DELETE запрос - UserController! Удаление всех пользователей");
        userStorage.removeAll();
    }

    @Override
    public User addFriend(Integer userId, Integer friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new ValidationException("Отрицательный ID!");
        } else {
            log.info("Получен POST запрос - UserController! Добавление друга");
            User user = get(userId);
            User friend = get(friendId);
            List<Integer> friendList = user.getFriends();
            friendList.add(friendId);
            user.setFriends(friendList);
            update(user);
            friendList = friend.getFriends();
            friendList.add(userId);
            friend.setFriends(friendList);
            update(friend);
            return user;
        }
    }

    @Override
    public User removeFriend(Integer userId, Integer friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new ValidationException("Отрицательный ID!");
        } else {
            log.info("Получен DELETE запрос - UserController! Удаление друга");
            User user = get(userId);
            User friend = get(friendId);
            List<Integer> friendList = user.getFriends();
            friendList.remove(friendId);
            user.setFriends(friendList);
            update(user);
            friendList = friend.getFriends();
            friendList.remove(userId);
            friend.setFriends(friendList);
            update(friend);
            return user;
        }
    }

    @Override
    public List<User> getAllFriends(Integer userId) {
        if (userId <= 0) {
            throw new ValidationException("Отрицательный ID!");
        } else {
            User user = get(userId);
            List<Integer> friendList = user.getFriends();
            return friendList.stream()
                    .sorted(Integer::compareTo)
                    .map(this::get)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        if (userId <= 0 || otherId <= 0) {
            throw new ValidationException("Отрицательный ID!");
        } else {
            List<User> userFriends = getAllFriends(userId);
            List<User> otherUserFriends = getAllFriends(otherId);
            userFriends.retainAll(otherUserFriends);
            return userFriends;
        }
    }
}
