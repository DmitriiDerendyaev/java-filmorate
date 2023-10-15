package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidUser;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Slf4j
@Service
public class UserService  {

    private final UserStorage<User> inMemoryUserStorage;

    @Autowired
    public UserService (InMemoryUserStorage inMemoryUserStorage){
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void addFriend(Long userId, Long friendId) {
    }

    public void deleteFriend(Long userId, Long friendId) {
    }

    public List<Long> getCommonFriends(Long userId, Long otherId) {
        return null;
    }

}
