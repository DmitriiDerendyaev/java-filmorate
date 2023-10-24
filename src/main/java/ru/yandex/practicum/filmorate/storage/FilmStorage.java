package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage<T extends Entity> extends StorageCRUD<Film> {

    public Film getById(Long filmId);

}
