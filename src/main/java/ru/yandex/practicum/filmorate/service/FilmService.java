package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage<Film> inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }


    public Film addLike(Long filmId, Long userId) {
        Film film = inMemoryFilmStorage.getById(filmId);
        film.addLike(userId);
        inMemoryFilmStorage.update(film);

        return film;
    }

    public Film deleteLike(Long filmId, Long userId) {
        Film film = inMemoryFilmStorage.getById(filmId);

        if (film != null) {
            Set<Long> likes = film.getLikes();
            if (likes.contains(userId)) {
                likes.remove(userId);
                inMemoryFilmStorage.update(film);
            } else {
                throw new DataNotFoundException("Like not found");
            }
        }

        return film;
    }

    public List<Film> getPopularFilms(int count) {
        List<Film> popularFilms = inMemoryFilmStorage.getAll();

        popularFilms.sort((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()));

        if (count < popularFilms.size()) {
            return popularFilms.subList(0, count);
        } else {
            return popularFilms;
        }
    }


    public List<Film> getAll() {
        return inMemoryFilmStorage.getAll();
    }

    public Film create(Film film) {
        return inMemoryFilmStorage.create(film);
    }

    public Film update(Film film) {
        return inMemoryFilmStorage.update(film);
    }

    public Film getFilmById(Long filmId) {
        return inMemoryFilmStorage.getById(filmId);
    }
}
