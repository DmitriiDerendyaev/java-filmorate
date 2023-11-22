package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NoInformationFoundException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.db.film.FilmDb;
import ru.yandex.practicum.filmorate.storage.db.user.UserDb;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmDb filmStorage;
    private final UserDb userStorage;
    private static final String FILM_BIRTHDAY = "1895-12-28";

    public Film addLike(Long filmId, Long userId) {
        log.info("addLike() method called with filmId: {} and userId: {}", filmId, userId);
        if (!filmStorage.containsFilm(filmId)) {
            throw new NoInformationFoundException(String.format("Film with id=%d not found", filmId));
        }
        if (!userStorage.containsUser(userId)) {
            throw new NoInformationFoundException(String.format("User with id=%d not found", userId));
        }
        filmStorage.likeFilm(filmId, userId);
        log.info("Liked the movie");
        return filmStorage.findById(filmId);
    }

    public Film deleteLike(Long filmId, Long userId) {
        log.info("deleteLike() method called with filmId: {} and userId: {}", filmId, userId);
        if (filmId <= 0 || userId <= 0) {
            throw new ObjectNotFoundException("Film's and User's id must be over 0");
        }

        if (!filmStorage.containsFilm(filmId)) {
            throw new NoInformationFoundException(String.format("Film with id=%d not found", filmId));
        }
        if (!userStorage.containsUser(userId)) {
            throw new NoInformationFoundException(String.format("User with id=%d not found", userId));
        }
        filmStorage.deleteLike(filmId, userId);
        log.info("Like deleted");
        return filmStorage.findById(filmId);
    }

    public List<Film> getPopularFilms(long count) {
        log.info("getPopularFilms() method called with count: {}", count);
        if (count <= 0) {
            throw new ValidationException("Count must be over 0");
        }
        return filmStorage.bestFilms(count);
    }

    public List<Film> getAll() {
        return filmStorage.findAllFilms();
    }

    public Film create(Film film) {
        if (film.getId() != null) {
            throw new ValidationException("ID must be empty");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse(FILM_BIRTHDAY))) {
            throw new ValidationException("Time of release must be after" + FILM_BIRTHDAY);
        }
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        log.info("update() method called with film: {}", film);
        if (!filmStorage.containsFilm(film.getId())) {
            throw new ObjectNotFoundException("There is no such film in the database");
        }

        if (film.getReleaseDate().isBefore(LocalDate.parse(FILM_BIRTHDAY))) {
            throw new ValidationException("Time of release must be after" + FILM_BIRTHDAY);
        }
        return filmStorage.rewriteFilm(film);
    }

    public Film getFilmById(Long filmId) {
        log.info("getFilmById() method called with filmId: {}", filmId);
        if (!filmStorage.containsFilm(filmId)) {
            throw new ObjectNotFoundException("Film with not found, id = " + filmId);
        }
        return filmStorage.findById(filmId);
    }
}

