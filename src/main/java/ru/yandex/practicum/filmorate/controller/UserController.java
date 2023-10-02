package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidUser;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private HashMap<Integer, User> users = new HashMap<>();
    private int currentId = 1;

    @GetMapping()
    public List<User> getAllUsers(){
        log.info("Fetching all users");

        return new ArrayList<>(users.values());
    }

    @PostMapping()
    public User addUser(@Valid @RequestBody User user){
        user.setId(currentId++);
        log.info("Adding a new user: {}", user);

        users.put(user.getId(), user);

        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user){
        if (!users.containsKey(user.getId())){
            log.warn("Unknown user: {}", user);
            throw new InvalidUser("Unknown user");
        }
        log.info("Update user {} to user: {} ", user, users.get(user.getId()));
        users.put(user.getId(), user);

        return user;
    }
}
