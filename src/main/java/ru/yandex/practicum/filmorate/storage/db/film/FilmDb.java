package ru.yandex.practicum.filmorate.storage.db.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDb {
    List<Film> findAllFilms();
    Film create(Film film);
    Film rewriteFilm(Film film);
    Film findById(Long id);
    boolean containsFilm(Long id);

    void likeFilm(Long id, Long userId);

    void deleteLike(Long id, Long userId);

    List<Film> bestFilms(Long count);
}
