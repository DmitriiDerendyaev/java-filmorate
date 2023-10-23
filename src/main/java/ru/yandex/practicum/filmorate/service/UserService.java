package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidUser;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService  {

    private final UserStorage<User> inMemoryUserStorage;

    @Autowired
    public UserService (InMemoryUserStorage inMemoryUserStorage){
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public List<User> getAll(){
        return inMemoryUserStorage.getAll();
    }

    public User create(User user){
        return inMemoryUserStorage.create(user);
    }

    public User update(User user){
        return inMemoryUserStorage.update(user);
    }

    public User getUserById(Long id){
        return inMemoryUserStorage.getById(id);
    }

    public List<User> getFriendsList(Long id){
        List<User> friendsList = new ArrayList<>();
        User user = inMemoryUserStorage.getById(id);

        for (Long friend : user.getFriends()) {
            friendsList.add(inMemoryUserStorage.getById(friend));
        }

        return friendsList;
    }

    public User addFriend(Long userId, Long friendId) {
        User user = inMemoryUserStorage.getById(userId);
        User friend = inMemoryUserStorage.getById(friendId);

        if(user != null && friend != null){
            user.addFriend(friendId);
            friend.addFriend(userId);

            inMemoryUserStorage.update(friend);
            inMemoryUserStorage.update(user);
        }

        return user;
    }

    public User deleteFriend(Long userId, Long friendId) {
        User user = inMemoryUserStorage.getById(userId);
        User friend = inMemoryUserStorage.getById(friendId);

        if(user != null && friend != null){
            user.removeFriend(friendId);
            friend.removeFriend(userId);

            inMemoryUserStorage.update(friend);
            inMemoryUserStorage.update(user);
        }

        return user;
    }

    public List<User> getCommonFriends(Long userId, Long otherId) {
        User user = inMemoryUserStorage.getById(userId);
        User otherUser = inMemoryUserStorage.getById(otherId);

        if (user != null && otherUser != null) {
            Set<Long> userFriends = user.getFriends();
            Set<Long> otherUserFriends = otherUser.getFriends();

            Set<Long> commonFriends = new HashSet<>(userFriends);
            commonFriends.retainAll(otherUserFriends);

        }

        List<User> friendsList = new ArrayList<>();

        for (Long friend : user.getFriends()) {
            friendsList.add(inMemoryUserStorage.getById(friend));
        }

        return friendsList;
    }

}
