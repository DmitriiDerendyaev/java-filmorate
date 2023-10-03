package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidUser;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@Service
public class UserService extends AbstractService<User> {

    @Override
    public void validate(User data) {
        if(data == null){
            throw new InvalidUser("Invalid user");
        }
    }
}
