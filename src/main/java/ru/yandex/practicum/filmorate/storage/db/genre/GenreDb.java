package ru.yandex.practicum.filmorate.storage.db.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDb {
    List<Genre> getGenres();

    Genre getGenreById(Long id);

    boolean containsGenre(Long id);
}
