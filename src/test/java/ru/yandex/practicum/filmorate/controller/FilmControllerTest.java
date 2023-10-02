package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.exception.InvalidFilm;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class FilmControllerTest {

    @Test
    public void testGetAllFilms() {
        FilmService filmService = Mockito.mock(FilmService.class);

        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Film 1", "Description 1", LocalDate.now(), Duration.ofMinutes(120)));
        films.add(new Film(2, "Film 2", "Description 2", LocalDate.now(), Duration.ofMinutes(90)));

        when(filmService.getAllFilms()).thenReturn(films);

        FilmController filmController = new FilmController(filmService);

        ResponseEntity<List<Film>> responseEntity = filmController.getAllFilms();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(films, responseEntity.getBody());
    }

    @Test
    public void testAddFilm() {
        FilmService filmService = Mockito.mock(FilmService.class);

        Film filmToAdd = new Film(1, "Film 1", "Description 1", LocalDate.now(), Duration.ofMinutes(120));

        when(filmService.addFilm(filmToAdd)).thenReturn(filmToAdd);

        FilmController filmController = new FilmController(filmService);

        ResponseEntity<Film> responseEntity = filmController.addFilm(filmToAdd);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(filmToAdd, responseEntity.getBody());
    }

    @Test
    public void testUpdateFilm() {
        FilmService filmService = Mockito.mock(FilmService.class);

        Film filmToUpdate = new Film(1, "Updated Film", "Updated Description", LocalDate.now(), Duration.ofMinutes(150));

        when(filmService.updateFilm(filmToUpdate)).thenReturn(filmToUpdate);

        FilmController filmController = new FilmController(filmService);

        ResponseEntity<Film> responseEntity = filmController.updateFilm(filmToUpdate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(filmToUpdate, responseEntity.getBody());
    }

    @Test
    public void testUpdateFilmInvalidFilm() {
        FilmService filmService = Mockito.mock(FilmService.class);

        Film filmToUpdate = new Film(1, "Updated Film", "Updated Description", LocalDate.now(), Duration.ofMinutes(150));

        when(filmService.updateFilm(filmToUpdate)).thenThrow(new InvalidFilm("Unknown film"));

        FilmController filmController = new FilmController(filmService);

        InvalidFilm invalidFilmException = assertThrows(InvalidFilm.class, () -> {
            filmController.updateFilm(filmToUpdate);
        });

        assertEquals("Unknown film", invalidFilmException.getMessage());
    }


}
