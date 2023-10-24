package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService {
    private final UserStorage<User> inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public List<User> getAll() {
        log.info("getAll() method called.");
        List<User> result = inMemoryUserStorage.getAll();
        log.info("getAll() method completed successfully.");
        return result;
    }

    public User create(User user) {
        log.info("create() method called with user: {}", user);
        User result = inMemoryUserStorage.create(user);
        log.info("create() method completed successfully. Created user: {}", result);
        return result;
    }

    public User update(User user) {
        log.info("update() method called with user: {}", user);
        User result = inMemoryUserStorage.update(user);
        log.info("update() method completed successfully. Updated user: {}", result);
        return result;
    }

    public User getUserById(Long id) {
        log.info("getUserById() method called with id: {}", id);
        User result = inMemoryUserStorage.getById(id);
        log.info("getUserById() method completed successfully. User: {}", result);
        return result;
    }

    public List<User> getFriendsList(Long id) {
        log.info("getFriendsList() method called with id: {}", id);
        User user = inMemoryUserStorage.getById(id);
        List<User> friendsList = new ArrayList<>();

        for (Long friend : user.getFriends()) {
            friendsList.add(inMemoryUserStorage.getById(friend));
        }
        log.info("getFriendsList() method completed successfully.");
        return friendsList;
    }

    public User addFriend(Long userId, Long friendId) {
        log.info("addFriend() method called with userId: {} and friendId: {}", userId, friendId);
        User user = inMemoryUserStorage.getById(userId);
        User friend = inMemoryUserStorage.getById(friendId);

        if (user != null && friend != null) {
            user.addFriend(friendId);
            friend.addFriend(userId);
            inMemoryUserStorage.update(friend);
            inMemoryUserStorage.update(user);
        }
        log.info("addFriend() method completed successfully.");
        return user;
    }

    public User deleteFriend(Long userId, Long friendId) {
        log.info("deleteFriend() method called with userId: {} and friendId: {}", userId, friendId);
        User user = inMemoryUserStorage.getById(userId);
        User friend = inMemoryUserStorage.getById(friendId);

        if (user != null && friend != null) {
            user.removeFriend(friendId);
            friend.removeFriend(userId);
            inMemoryUserStorage.update(friend);
            inMemoryUserStorage.update(user);
        }
        log.info("deleteFriend() method completed successfully.");
        return user;
    }

    public List<User> getCommonFriends(Long userId, Long otherId) {
        log.info("getCommonFriends() method called with userId: {} and otherId: {}", userId, otherId);
        User user = inMemoryUserStorage.getById(userId);
        User otherUser = inMemoryUserStorage.getById(otherId);

        if (user != null && otherUser != null) {
            Set<Long> userFriends = user.getFriends();
            Set<Long> otherUserFriends = otherUser.getFriends();
            Set<Long> commonFriends = new HashSet<>(userFriends);
            commonFriends.retainAll(otherUserFriends);

            List<User> commonFriendsList = new ArrayList<>();
            for (Long friendId : commonFriends) {
                User commonFriend = inMemoryUserStorage.getById(friendId);
                if (commonFriend != null) {
                    commonFriendsList.add(commonFriend);
                }
            }
            log.info("getCommonFriends() method completed successfully.");
            return commonFriendsList;
        } else {
            log.info("getCommonFriends() method completed with no common friends.");
            return Collections.emptyList();
        }
    }
}
