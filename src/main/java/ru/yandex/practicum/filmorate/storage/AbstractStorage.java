package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.InvalidValidation;
import ru.yandex.practicum.filmorate.model.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractStorage<T extends Entity> implements StorageCRUD<T>{

    private final HashMap<Integer, T> storage = new HashMap<>();
    private int counter = 0;

    public abstract void validate(T data);

    public T create(T data) {
        validate(data);
        data.setId(++counter);
        storage.put(data.getId(), data);
        return data;
    }

    public T update(T data) {
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
