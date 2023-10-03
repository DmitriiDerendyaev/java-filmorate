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
        films.add(Film.builder()
                .name("Film 1")
                .description("Description 1")
                .releaseDate(LocalDate.now())
                .duration(Duration.ofMinutes(120))
                .build());
        films.add(Film.builder()
                .name("Film 2")
                .description("Description 2")
                .releaseDate(LocalDate.now())
                .duration(Duration.ofMinutes(90))
                .build());

        when(filmService.getAllFilms()).thenReturn(films);

        FilmController filmController = new FilmController(filmService);

        ResponseEntity<List<Film>> responseEntity = filmController.getAllFilms();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(films, responseEntity.getBody());
    }

    @Test
    public void testAddFilm() {
        FilmService filmService = Mockito.mock(FilmService.class);

        Film filmToAdd = Film.builder()
                .name("Film 1")
                .description("Description 1")
                .releaseDate(LocalDate.now())
                .duration(Duration.ofMinutes(120))
                .build();

        when(filmService.addFilm(filmToAdd)).thenReturn(filmToAdd);

        FilmController filmController = new FilmController(filmService);

        ResponseEntity<Film> responseEntity = filmController.addFilm(filmToAdd);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(filmToAdd, responseEntity.getBody());
    }

    @Test
    public void testUpdateFilm() {
        FilmService filmService = Mockito.mock(FilmService.class);

        Film filmToUpdate = Film.builder()
                .name("Updated Film")
                .description("Updated Description")
                .releaseDate(LocalDate.now())
                .duration(Duration.ofMinutes(150))
                .build();

        when(filmService.updateFilm(filmToUpdate)).thenReturn(filmToUpdate);

        FilmController filmController = new FilmController(filmService);

        ResponseEntity<Film> responseEntity = filmController.updateFilm(filmToUpdate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(filmToUpdate, responseEntity.getBody());
    }

    @Test
    public void testUpdateFilmInvalidFilm() {
        FilmService filmService = Mockito.mock(FilmService.class);

        Film filmToUpdate = Film.builder()
                .name("Updated Film")
                .description("Updated Description")
                .releaseDate(LocalDate.now())
                .duration(Duration.ofMinutes(150))
                .build();

        when(filmService.update(filmToUpdate)).thenThrow(new InvalidFilm("Unknown film"));

        FilmController filmController = new FilmController(filmService);

        InvalidFilm invalidFilmException = assertThrows(InvalidFilm.class, () -> {
            filmController.updateFilm(filmToUpdate);
        });

        assertEquals("Unknown film", invalidFilmException.getMessage());
    }


}
