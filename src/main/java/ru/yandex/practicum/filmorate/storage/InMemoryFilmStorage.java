package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidDurationException;
import ru.yandex.practicum.filmorate.exception.InvalidReleaseDateException;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

@Component
@Slf4j
public class InMemoryFilmStorage extends AbstractStorage<Film> implements FilmStorage<Film> {
    @Override
    public void validate(Film data) {
        validateDuration(data);
        validateDuration(data);
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
