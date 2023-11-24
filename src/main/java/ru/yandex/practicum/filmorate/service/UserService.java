package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NoInformationFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.user.UserDb;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;


import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDb userStorage;

    public List<User> getAll() {
        log.info("getAll() method called.");
        List<User> result = userStorage.findAllUsers();
        log.info("getAll() method completed successfully.");
        return result;
    }

    public User create(User user) {
        log.info("create() method called with user: {}", user);
        if (user.getId() != null) {
            throw new ValidationException("ID must be empty");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("The login cannot contain spaces");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.createUser(user);
    }

    public User update(User user) {
        log.info("update() method called with user: {}", user);
        if (!userStorage.containsUser(user.getId())) {
            throw new ObjectNotFoundException("There is no such user in the database");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("The login cannot contain spaces");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.updateUser(user);
    }

    public User getUserById(Long id) {
        log.info("getUserById() method called with id: {}", id);
        if (!userStorage.containsUser(id)) {
            throw new ObjectNotFoundException(String.format("User c id=%d not exist", id));
        }
        return userStorage.findById(id);
    }

    public List<User> getFriendsList(Long id) {
        log.info("getFriendsList() method called with id: {}", id);
        if (id <= 0) {
            throw new ObjectNotFoundException("User's and friend's id must be over 0");
        }
        if (userStorage.findAllUsers().isEmpty()) {
            return null;
        }
        if (!userStorage.containsUser(id)) {
            throw new NoInformationFoundException(String.format("User with id=%d not found", id));
        }
        log.info("List of friends done");
        return userStorage.getFriends(id);
    }

    public User addFriend(Long userId, Long friendId) {
        log.info("addFriend() method called with userId: {} and friendId: {}", userId, friendId);
        if (userId <= 0 || friendId <= 0) {
            throw new ObjectNotFoundException("User's and friend's id must be over 0");
        }
        if (!userStorage.containsUser(userId)) {
            throw new NoInformationFoundException(String.format("User c id=%d not exist", userId));
        }
        if (!userStorage.containsUser(friendId)) {
            throw new NoInformationFoundException(String.format("User c id=%d not exist", friendId));
        }
        userStorage.addFriends(userId, friendId);
        log.info("Friend added");
        return userStorage.findById(userId);
    }

    public User deleteFriend(Long userId, Long friendId) {
        log.info("deleteFriend() method called with userId: {} and friendId: {}", userId, friendId);
        if (userId <= 0 || friendId <= 0) {
            throw new ObjectNotFoundException("User's and friend's id must be over 0");
        }
        if (!userStorage.containsUser(userId)) {
            throw new NoInformationFoundException(String.format("User with id=%d not found", userId));
        }
        if (!userStorage.containsUser(friendId)) {
            throw new NoInformationFoundException(String.format("User with id=%d not found", friendId));
        }
        userStorage.deleteFriendsById(userId, friendId);
        return userStorage.findById(userId);
    }

    public List<User> getCommonFriends(Long userId, Long otherId) {
        log.info("getCommonFriends() method called with userId: {} and otherId: {}", userId, otherId);
        if (userId <= 0) {
            throw new ObjectNotFoundException("User's and friend's id must be over 0");
        }
        if (!userStorage.containsUser(userId)) {
            throw new NoInformationFoundException(String.format("User with id=%d not found", userId));
        }
        if (!userStorage.containsUser(otherId)) {
            throw new NoInformationFoundException(String.format("User with id=%d not found", otherId));
        }
        log.info("List of common friends done");
        return userStorage.commonFriends(userId, otherId);
    }
}
