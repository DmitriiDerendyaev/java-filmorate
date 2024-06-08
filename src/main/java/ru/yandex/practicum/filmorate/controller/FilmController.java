package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.validateGroup.UpdateGroup;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping()
    public List<Film> getAllFilms() {
        return filmService.getAll();
    }

    @GetMapping("/news")
    public String getNews() {
        return "news in progress to deploy";
    }

    @PostMapping
    public Film addFilm(@Validated(UpdateGroup.class) @RequestBody final Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film updateFilm(@Validated(UpdateGroup.class) @RequestBody final Film film) {
        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getFilms(@RequestParam(required = false, defaultValue = "10") int count) {
        return filmService.getPopularFilms(count);
    }
}