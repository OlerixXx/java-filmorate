package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ErrorResponse;
import ru.yandex.practicum.filmorate.exceptions.IncorrectCountException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public User get(@PathVariable Integer userId) {
        return userService.get(userId);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping
    public User add(@Valid @RequestBody User user) {
        return userService.add(user);
    }

    @DeleteMapping("/{userId}")
    public void remove(@PathVariable Integer userId) {
        userService.remove(userId);
    }

    @DeleteMapping()
    public void removeAll() {
        userService.removeAll();
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public User addFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        return userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public User removeFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        return userService.removeFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getAllFriends(@PathVariable Integer userId) {
        return userService.getAllFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Integer userId, @PathVariable Integer otherId) {
        return userService.getCommonFriends(userId, otherId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(final IncorrectCountException e) {
        return new ErrorResponse("Некорректно передан параметр <count> или <id>!", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(final ValidationException e) {
        return new ErrorResponse("Значение по переданным параметрам не найдено!", e.getMessage());
    }

}
