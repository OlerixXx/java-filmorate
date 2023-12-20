package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> getAll();

    Film get(Integer id);

    Film update(Film film);

    Film add(Film film);

    void remove(Integer id);

    void removeAll();
}
