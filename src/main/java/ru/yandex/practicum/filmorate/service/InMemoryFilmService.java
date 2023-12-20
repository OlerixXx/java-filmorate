package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InMemoryFilmService implements FilmService {

    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private Film film;
    private List<Integer> likeList;

    @Override
    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    @Override
    public Film get(Integer id) {
        return filmStorage.get(id);
    }

    @Override
    public Film update(Film film) {
        log.info("Получен PUT запрос - FilmController! Обновление фильма");
        return filmStorage.update(film);
    }

    @Override
    public Film add(Film film) {
        log.info("Получен POST запрос - FilmController! Добавление фильма");
        return filmStorage.add(film);
    }

    @Override
    public void remove(Integer id) {
        if (id <= 0) {
            throw new ValidationException("Отрицательный ID!");
        } else {
            log.info("Получен DELETE запрос - FilmController! Удаление фильма");
            filmStorage.remove(id);
        }
    }

    @Override
    public void removeAll() {
        log.info("Получен DELETE запрос - FilmController! Удаление всех фильмов");
        filmStorage.removeAll();
    }

    @Override
    public Film addLike(Integer filmId, Integer userId) {
        if (filmId <= 0 || userId <= 0) {
            throw new ValidationException("Отрицательный ID!");
        } else {
            log.info("Получен PUT запрос - FilmController! Добавление лайка фильму");
            film = this.get(filmId);
            if (userStorage.get(userId) != null) {
                likeList = film.getLikes();
                likeList.add(userId);
                update(film);
            }
            return film;
        }
    }

    @Override
    public Film removeLike(Integer filmId, Integer userId) {
        if (filmId <= 0 || userId <= 0) {
            throw new ValidationException("Отрицательный ID!");
        } else {
            log.info("Получен DELETE запрос - FilmController! Удаления лайка у фильма");
            film = this.get(filmId);
            if (userStorage.get(userId) != null) {
                likeList = film.getLikes();
                likeList.remove(userId);
                update(film);
                return film;
            }
            return film;
        }
    }

    @Override
    public List<Film> getMostPopular(Integer count) {
        if (count == null) {
            count = 10;
        } else if (count <= 0) {
            throw new ValidationException("Отрицательный count!");
        }
        return this.getAll().stream()
                .sorted((p0, p1) -> p1.getLikes().size() - p0.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}