package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.StorageCRUD;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final StorageCRUD<Film> filmStorage;

    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage){
        this.filmStorage = inMemoryFilmStorage;
    }


    @GetMapping()
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> filmList = filmStorage.getAll();
        return ResponseEntity.ok(filmList);
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@RequestBody @Valid Film film) {
        Film addedFilm = filmStorage.create(film);
        return ResponseEntity.ok(addedFilm);
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@RequestBody @Valid Film film) {
        Film updatedFilm = filmStorage.update(film);
        return ResponseEntity.ok(updatedFilm);
    }

}
