package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.InvalidValidation;
import ru.yandex.practicum.filmorate.model.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractService<T extends Entity> {
    private final HashMap<Integer, T> storage = new HashMap<>();
    private int counter = 0;

    public abstract void validate(T data);

    public T create(final T data) {
        validate(data);
        data.setId(++counter);
        storage.put(data.getId(), data);
        return data;
    }

    public T update(final T data) {
        if (!storage.containsKey(data.getId())) {
            throw new InvalidValidation("Not found key: " + data.getId());
        }
        validate(data);
        storage.put(data.getId(), data);
        return data;
    }

    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }


}