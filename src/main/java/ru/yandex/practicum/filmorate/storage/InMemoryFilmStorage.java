package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IncorrectCountException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    @Getter
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;
    private static final LocalDate MIN_DATE = LocalDate.of(1895, Month.DECEMBER, 28);
    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film get(Integer id) {
        if (id == null) {
            throw new ValidationException("ID не может быть null!");
        } else if (id <= 0) {
            throw new IncorrectCountException("Отрицательный ID!");
        } else if (!films.containsKey(id)) {
            throw new ValidationException("Такого id не существует!");
        } else {
            return films.get(id);
        }
    }

    @Override
    public Film update(Film film) {
        if (checkReleaseDate(film) && films.containsKey(film.getId())) {
            films.remove(film.getId());
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("Такого id не существует!");
        }
        return film;
    }

    @Override
    public Film add(Film film) {
        film.setId(id);
        if (checkReleaseDate(film)) {
            films.put(id, film);
            id += 1;
        }
        return film;
    }

    @Override
    public void remove(Integer id) {
        if (id == null) {
            throw new ValidationException("ID не может быть null!");
        } else if (id <= 0) {
            throw new IncorrectCountException("Отрицательный ID!");
        } else if (!films.containsKey(id)) {
            throw new ValidationException("Такого id не существует!");
        } else {
            films.remove(id);
        }
    }

    @Override
    public void removeAll() {
        for (Integer id : films.keySet()) {
            films.remove(id);
        }
    }

    private boolean checkReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(MIN_DATE)) {
            throw new IncorrectCountException("Дата релиза раньше 28 декабря 1895 года!");
        } else {
            return true;
        }
    }
}
