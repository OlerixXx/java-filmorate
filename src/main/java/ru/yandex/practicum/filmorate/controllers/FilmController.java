package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;
    private static final LocalDate MIN_DATE = LocalDate.of(1895, Month.DECEMBER, 28);

    @GetMapping
    public List<Film> getAll() {
        log.info("Получен GET запрос - FilmController!");
        return new ArrayList<>(films.values());
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен PUT запрос - FilmController!");
        if (checkReleaseDate(film) && films.containsKey(film.getId())) {
            films.remove(film.getId());
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("Такого id не существует!");
        }
        return film;
    }

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        log.info("Получен POST запрос - FilmController!");
        film.setId(id);
        if (checkReleaseDate(film)) {
            films.put(id, film);
            id += 1;
        }
        return film;
    }

    private boolean checkReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(MIN_DATE)) {
            log.error("Дата релиза указана раньше 28 декабря 1895 года!");
            throw new ValidationException("Дата релиза раньше 28 декабря 1895 года!");
        } else {
            return true;
        }
    }
}
