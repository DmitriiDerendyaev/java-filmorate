package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.HashMap;
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
    private HashMap<Integer, Film> films = new HashMap<>();
    private int currentId = 1;

    @GetMapping()
    public ResponseEntity<List<Film>> getAllFilms(){
        List<Film> filmList = filmService.getAllFilms();
        return ResponseEntity.ok(filmList);
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@RequestBody @Valid Film film) {
        Film addedFilm = filmService.addFilm(film);
        return ResponseEntity.ok(addedFilm);
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@RequestBody @Valid Film film){
        Film updatedFilm = filmService.updateFilm(film);
        return ResponseEntity.ok(updatedFilm);
    }

}
