package ru.yandex.practicum.filmorate.storage.db.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaDb {
    List<Mpa> getMpa();

    Mpa getMpaById(Long id);

    boolean containsMpa(Long id);
}
