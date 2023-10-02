package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidDurationException;
import ru.yandex.practicum.filmorate.exception.InvalidFilm;
import ru.yandex.practicum.filmorate.exception.InvalidReleaseDateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int currentId = 1;

    public List<Film> getAllFilms() {
        log.info("Fetching all films");

        return new ArrayList<>(films.values());
    }

    public Film addFilm(Film film) {
        validateReleaseDate(film);
        validateDuration(film);

        film.setId(currentId++);
        log.info("Adding a new film: {}", film);

        films.put(film.getId(), film);

        return film;
    }

    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new InvalidFilm("Unknown film");
        }
        validateReleaseDate(film);
        validateDuration(film);

        films.put(film.getId(), film);
        log.info("Update film {} to film: {} ", film, films.get(film.getId()));

        return film;
    }

    private void validateReleaseDate(Film film) {
        LocalDate releaseDate = film.getReleaseDate();
        LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);
        if (releaseDate.isBefore(minReleaseDate)) {
            log.warn("Release date must not be earlier than 28 December 1895, but is: {}", releaseDate);
            throw new InvalidReleaseDateException("Release date must not be earlier than 28 December 1895");
        }
    }

    private void validateDuration(Film film) {
        Duration duration = film.getDurationValue();
        if (duration.isNegative()) {
            log.warn("Duration cannot be negative.");
            throw new InvalidDurationException("Duration cannot be negative.");
        }
    }
}
