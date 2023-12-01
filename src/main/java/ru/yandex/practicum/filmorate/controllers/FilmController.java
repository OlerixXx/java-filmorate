package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    public Map<Integer, Film> getAll() {
        return films;
    }

    @PutMapping("/film")
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос!");
        Film checkedFilm = checkValidate(film);
            films.remove(checkedFilm.getId());
            films.put(checkedFilm.getId(), checkedFilm);
        return checkedFilm;
    }
    @PostMapping("/film")
    public Film add(@Valid @RequestBody Film film) {
        log.info("Получен запрос!");
        films.put(film.getId(), checkValidate(film));
        return checkValidate(film);
    }

    private Film checkValidate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            log.error("Дата релиза указана раньше 28 декабря 1895 года!");
            throw new ValidationException("Дата релиза раньше 28 декабря 1895 года!");
        } else if (film.getDuration().isNegative()) {
            log.error("Поле <duration> отрицательное!");
            throw new ValidationException("Продолжительность фильма должна быть положительной!");
        } else {
            return film;
        }
    }

}
