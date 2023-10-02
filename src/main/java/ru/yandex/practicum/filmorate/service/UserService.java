package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidUser;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final HashMap<Integer, User> users = new HashMap<>();
    private int currentId = 1;

    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return new ArrayList<>(users.values());
    }

    public User addUser(@Valid User user) {
        user.setId(currentId++);
        log.info("Adding a new user: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(@Valid User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Unknown user: {}", user);
            throw new InvalidUser("Unknown user");
        }
        log.info("Updating user: {}", user);
        users.put(user.getId(), user);
        return user;
    }
}
