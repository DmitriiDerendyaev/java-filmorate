package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidReleaseDateException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping()
    public List<Film> getAllFilms(){
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        validateReleaseDate(film.getReleaseDate());
        films.put(film.getId(), film);

        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film){
        validateReleaseDate(film.getReleaseDate());
        films.put(film.getId(), film);

        return film;
    }

    private void validateReleaseDate(LocalDate releaseDate) {
        LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);
        if (releaseDate.isBefore(minReleaseDate)) {
            throw new InvalidReleaseDateException("Release date must not be earlier than 28 December 1895");
        }
    }
}
