package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

@Deprecated
public interface UserStorage<T extends User> extends StorageCRUD<User> {

    public User getById(Long userId);
}
