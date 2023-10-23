package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.InvalidUser;
import ru.yandex.practicum.filmorate.model.User;

@Component
@Slf4j
public class InMemoryUserStorage extends AbstractStorage<User> implements UserStorage<User>{
    @Override
    public void validate(User data) {
        if (data == null) {
            throw new InvalidUser("Invalid user");
        }
    }

    @Override
    public User getById(Long userId) {
        if(!storage.containsKey(userId)){
            throw new DataNotFoundException(String.format("Пользователь с ID: %d не найден!", userId));
        } else {
            return storage.get(userId);
        }
    }
}
