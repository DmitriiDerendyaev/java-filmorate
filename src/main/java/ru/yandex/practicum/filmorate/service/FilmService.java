package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage<Film> inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public Film addLike(Long filmId, Long userId) {
        log.info("addLike() method called with filmId: {} and userId: {}", filmId, userId);
        Film film = inMemoryFilmStorage.getById(filmId);
        film.addLike(userId);
        inMemoryFilmStorage.update(film);
        log.info("addLike() method completed successfully.");
        return film;
    }

    public Film deleteLike(Long filmId, Long userId) {
        log.info("deleteLike() method called with filmId: {} and userId: {}", filmId, userId);
        Film film = inMemoryFilmStorage.getById(filmId);

        if (film != null) {
            Set<Long> likes = film.getLikes();
            if (likes.contains(userId)) {
                likes.remove(userId);
                inMemoryFilmStorage.update(film);
            } else {
                log.error("Like not found.");
                throw new DataNotFoundException("Like not found");
            }
        }
        log.info("deleteLike() method completed successfully.");
        return film;
    }

    public List<Film> getPopularFilms(int count) {
        log.info("getPopularFilms() method called with count: {}", count);
        List<Film> popularFilms = inMemoryFilmStorage.getAll();

        popularFilms.sort((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()));

        if (count < popularFilms.size()) {
            log.info("getPopularFilms() method completed successfully.");
            return popularFilms.subList(0, count);
        } else {
            log.info("getPopularFilms() method completed successfully.");
            return popularFilms;
        }
    }

    public List<Film> getAll() {
        log.info("getAll() method called.");
        List<Film> result = inMemoryFilmStorage.getAll();
        log.info("getAll() method completed successfully.");
        return result;
    }

    public Film create(Film film) {
        log.info("create() method called with film: {}", film);
        Film result = inMemoryFilmStorage.create(film);
        log.info("create() method completed successfully. Created film: {}", result);
        return result;
    }

    public Film update(Film film) {
        log.info("update() method called with film: {}", film);
        Film result = inMemoryFilmStorage.update(film);
        log.info("update() method completed successfully. Updated film: {}", result);
        return result;
    }

    public Film getFilmById(Long filmId) {
        log.info("getFilmById() method called with filmId: {}", filmId);
        Film result = inMemoryFilmStorage.getById(filmId);
        log.info("getFilmById() method completed successfully. Film: {}", result);
        return result;
    }
}

