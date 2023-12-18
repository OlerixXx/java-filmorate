package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    List<Film> getAll();

    Film get(Integer id);

    Film update(Film film);

    Film add(Film film);

    void remove(Integer id);

    void removeAll();

    Film addLike(Integer filmId, Integer userId);

    Film removeLike(Integer filmId, Integer userId);

    List<Film> getMostPopular(Integer count);
}
