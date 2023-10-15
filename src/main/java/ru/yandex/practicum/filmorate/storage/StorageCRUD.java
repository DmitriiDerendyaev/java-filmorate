package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Entity;

import java.util.List;

public interface StorageCRUD<T extends Entity> {

    public List<T> getAll();

    public T update(final T data);

    public T create(final T data);
}
