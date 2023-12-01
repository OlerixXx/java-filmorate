package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public Map<Integer, User> getAll() {
        return users;
    }

    @PutMapping("/user")
    public User update(@Valid @RequestBody User user) {
        log.info("Получен запрос!");
        User checkedUser = checkValidate(user);
        users.remove(checkedUser.getId());
        users.put(checkedUser.getId(), checkedUser);
        return checkedUser;
    }
    @PostMapping("/user")
    public User add(@Valid @RequestBody User user) {
        log.info("Получен запрос!");
        users.put(user.getId(), checkValidate(user));
        return checkValidate(user);
    }

    private User checkValidate(User user) {
        if (user.getName().isEmpty()) {
            log.info("Пустое поле <name>! Использован логин для заполнения поля!");
            return user.toBuilder().name(user.getLogin()).build();
        } else {
            return user;
        }
    }
}
