package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage<T extends User> extends StorageCRUD<User> {

    public User getById(Long userId);
}
