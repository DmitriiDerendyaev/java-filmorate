package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidDurationException;
import ru.yandex.practicum.filmorate.exception.InvalidFilm;
import ru.yandex.practicum.filmorate.exception.InvalidReleaseDateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

@Slf4j
@Service
public class FilmService extends AbstractService<Film> {

    @Override
    public void validate(Film film) {
        validateReleaseDate(film);
        validateDuration(film);
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
