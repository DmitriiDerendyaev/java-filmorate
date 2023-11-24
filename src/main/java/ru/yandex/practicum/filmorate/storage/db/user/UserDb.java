package ru.yandex.practicum.filmorate.storage.db.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDb {

    List<User> findAllUsers();

    User createUser(User user);

    User updateUser(User user);

    User findById(Long id);

    boolean containsUser(Long id);

    void addFriends(Long id, Long friendId);

    void deleteFriendsById(Long id, Long friendId);

    List<User> getFriends(Long id);

    public List<User> commonFriends(Long id, Long otherId);
}
