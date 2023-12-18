package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ErrorResponse;
import ru.yandex.practicum.filmorate.exceptions.IncorrectCountException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.InMemoryFilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{filmId}")
    public Film get(@PathVariable Integer filmId) {
        return filmService.get(filmId);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        return filmService.add(film);
    }

    @DeleteMapping("/{filmId}")
    public void remove(@PathVariable Integer filmId) {
        filmService.remove(filmId);
    }

    @DeleteMapping
    public void removeAll() {
        filmService.removeAll();
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable Integer filmId, @PathVariable  Integer userId) {
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film removeLike(@PathVariable Integer filmId, @PathVariable  Integer userId) {
        return filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getCountFilms(@RequestParam(required = false) @DefaultValue(value = "10") Integer count) {
        return filmService.getMostPopular(count);
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
