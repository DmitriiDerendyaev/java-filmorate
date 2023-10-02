package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private HashMap<Integer, User> users = new HashMap<>();

    @GetMapping()
    public List<User> getAllUsers(){
        return new ArrayList<>(users.values());
    }

    @PostMapping()
    public User addUser(@RequestBody @Valid User user){
        users.put(user.getId(), user);

        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user){
        users.put(user.getId(), user);

        return user;
    }
}
