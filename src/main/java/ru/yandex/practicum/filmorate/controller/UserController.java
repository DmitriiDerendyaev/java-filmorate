package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.validateGroup.UpdateGroup;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:8081")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        log.info("Users data: {}", user.toString());
        if (userService.authenticateUser(user.getLogin(), user.getPassword())) {
            return ResponseEntity.ok(userService.getUserByLogin(user.getLogin()));
        } else {
            throw new DataNotFoundException("User with ID: " + user.getId() + " not found");
        }
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping()
    public User addUser(@Validated(UpdateGroup.class) final @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User updateUser(@Validated(UpdateGroup.class) final @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Long id) {
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
