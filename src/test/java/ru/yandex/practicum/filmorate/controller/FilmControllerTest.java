package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.exception.InvalidFilm;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.StorageCRUD;

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
        StorageCRUD<Film> filmStorage = Mockito.mock(InMemoryFilmStorage.class);

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

        when(filmStorage.getAll()).thenReturn(films);

        FilmController filmController = new FilmController((InMemoryFilmStorage) filmStorage);

        ResponseEntity<List<Film>> responseEntity = filmController.getAllFilms();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(films, responseEntity.getBody());
    }

    @Test
    public void testAddFilm() {
        StorageCRUD<Film> filmStorage = Mockito.mock(InMemoryFilmStorage.class);

        Film filmToAdd = Film.builder()
                .name("Film 1")
                .description("Description 1")
                .releaseDate(LocalDate.now())
                .duration(Duration.ofMinutes(120))
                .build();

        when(filmStorage.create(filmToAdd)).thenReturn(filmToAdd);

        FilmController filmController = new FilmController((InMemoryFilmStorage) filmStorage);

        ResponseEntity<Film> responseEntity = filmController.addFilm(filmToAdd);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(filmToAdd, responseEntity.getBody());
    }

    @Test
    public void testUpdateFilm() {
        StorageCRUD<Film> filmStorage = Mockito.mock(InMemoryFilmStorage.class);

        Film filmToUpdate = Film.builder()
                .name("Updated Film")
                .description("Updated Description")
                .releaseDate(LocalDate.now())
                .duration(Duration.ofMinutes(150))
                .build();

        when(filmStorage.update(filmToUpdate)).thenReturn(filmToUpdate);

        FilmController filmController = new FilmController((InMemoryFilmStorage) filmStorage);

        ResponseEntity<Film> responseEntity = filmController.updateFilm(filmToUpdate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(filmToUpdate, responseEntity.getBody());
    }

    @Test
    public void testUpdateFilmInvalidFilm() {
        StorageCRUD<Film> filmStorage = Mockito.mock(InMemoryFilmStorage.class);

        Film filmToUpdate = Film.builder()
                .name("Updated Film")
                .description("Updated Description")
                .releaseDate(LocalDate.now())
                .duration(Duration.ofMinutes(150))
                .build();

        when(filmStorage.update(filmToUpdate)).thenThrow(new InvalidFilm("Unknown film"));

        FilmController filmController = new FilmController((InMemoryFilmStorage) filmStorage);

        InvalidFilm invalidFilmException = assertThrows(InvalidFilm.class, () -> {
            filmController.updateFilm(filmToUpdate);
        });

        assertEquals("Unknown film", invalidFilmException.getMessage());
    }


}
