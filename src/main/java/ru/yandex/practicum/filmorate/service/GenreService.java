package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.genre.GenreDb;
import ru.yandex.practicum.filmorate.storage.db.genre.GenreDbStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {

    private final GenreDb genreStorage;

    @Autowired
    public GenreService(GenreDbStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getGenres() {
        return genreStorage.getGenres();
    }

    public Genre getGenreById(Long id) {
        if (!genreStorage.containsGenre(id)) {
            throw new ObjectNotFoundException(String.format("Genre with id=%d not found", id));
        }
        log.info("Info about genre id=" + id);
        return genreStorage.getGenreById(id);
    }

}
