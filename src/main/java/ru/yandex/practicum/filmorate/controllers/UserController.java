package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @GetMapping
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Получен запрос!");
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

    @PostMapping
    public User add(@Valid @RequestBody User user) {
        log.info("Получен запрос!");
        user.setId(id);
        if (checkName(user)) {
            user.setName(user.getLogin());
        }
        users.put(id, user);
        id += 1;
        return user;
    }

    private boolean checkName(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            log.info("Пустое поле <name>! Использован логин для заполнения поля!");
            return true;
        }
        return false;
    }
}
