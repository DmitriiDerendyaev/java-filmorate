package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.controller.FilmController;
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
        // Create a mock FilmService
        FilmService filmService = Mockito.mock(FilmService.class);

        // Create a test list of films
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Film 1", "Description 1", LocalDate.now(), Duration.ofMinutes(120)));
        films.add(new Film(2, "Film 2", "Description 2", LocalDate.now(), Duration.ofMinutes(90)));

        // Configure the mock to return the test list when getAllFilms is called
        when(filmService.getAllFilms()).thenReturn(films);

        // Create a FilmController with the mock FilmService
        FilmController filmController = new FilmController(filmService);

        // Call the controller method
        ResponseEntity<List<Film>> responseEntity = filmController.getAllFilms();

        // Assert the response status code and the response body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(films, responseEntity.getBody());
    }

    @Test
    public void testAddFilm() {
        // Create a mock FilmService
        FilmService filmService = Mockito.mock(FilmService.class);

        // Create a test film to add
        Film filmToAdd = new Film(1, "Film 1", "Description 1", LocalDate.now(), Duration.ofMinutes(120));

        // Configure the mock to return the test film when addFilm is called
        when(filmService.addFilm(filmToAdd)).thenReturn(filmToAdd);

        // Create a FilmController with the mock FilmService
        FilmController filmController = new FilmController(filmService);

        // Call the controller method
        ResponseEntity<Film> responseEntity = filmController.addFilm(filmToAdd);

        // Assert the response status code and the response body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(filmToAdd, responseEntity.getBody());
    }

    @Test
    public void testUpdateFilm() {
        // Create a mock FilmService
        FilmService filmService = Mockito.mock(FilmService.class);

        // Create a test film to update
        Film filmToUpdate = new Film(1, "Updated Film", "Updated Description", LocalDate.now(), Duration.ofMinutes(150));

        // Configure the mock to return the test film when updateFilm is called
        when(filmService.updateFilm(filmToUpdate)).thenReturn(filmToUpdate);

        // Create a FilmController with the mock FilmService
        FilmController filmController = new FilmController(filmService);

        // Call the controller method
        ResponseEntity<Film> responseEntity = filmController.updateFilm(filmToUpdate);

        // Assert the response status code and the response body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(filmToUpdate, responseEntity.getBody());
    }

    @Test
    public void testUpdateFilmInvalidFilm() {
        // Create a mock FilmService
        FilmService filmService = Mockito.mock(FilmService.class);

        // Create a test film to update
        Film filmToUpdate = new Film(1, "Updated Film", "Updated Description", LocalDate.now(), Duration.ofMinutes(150));

        // Configure the mock to throw an InvalidFilm exception when updateFilm is called with an unknown film
        when(filmService.updateFilm(filmToUpdate)).thenThrow(new InvalidFilm("Unknown film"));

        // Create a FilmController with the mock FilmService
        FilmController filmController = new FilmController(filmService);

        // Use assertThrows to check if the updateFilm method throws the expected exception
        InvalidFilm invalidFilmException = assertThrows(InvalidFilm.class, () -> {
            filmController.updateFilm(filmToUpdate);
        });

        // Assert the exception message
        assertEquals("Unknown film", invalidFilmException.getMessage());
    }


}
