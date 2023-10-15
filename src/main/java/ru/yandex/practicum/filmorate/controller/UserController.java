package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.StorageCRUD;

import javax.validation.ReportAsSingleViolation;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final StorageCRUD<User> userStorage;

    @Autowired
    public UserController(InMemoryUserStorage inMemoryUserStorage){
        this.userStorage = inMemoryUserStorage;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userStorage.getAll();
        return ResponseEntity.ok(userList);
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User addedUser = userStorage.create(user);
        return ResponseEntity.ok(addedUser);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        User updatedUser = userStorage.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Long> addFriend(@PathVariable Long id, @PathVariable Long friendId){
        return null;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Long> deleteFriend(@PathVariable Long id, @PathVariable Long friendId){
        return null;
    }

    @GetMapping("/users/{id}/friends")
    public ResponseEntity<Long> getFriends(@PathVariable Long id){
        return null;
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public ResponseEntity<List<Long>> getMutualFriends(@PathVariable Long id, @PathVariable Long otherId){
        return null;
    }
}
