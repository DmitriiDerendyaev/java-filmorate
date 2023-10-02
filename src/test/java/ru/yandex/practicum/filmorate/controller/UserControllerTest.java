package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.InvalidUser;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() { // попытался разобраться с Мок тестированием, не знаю что из этого вышло
        List<User> users = new ArrayList<>();
        users.add(new User(1, "mail1@mail.ru", "login1", "Name1", LocalDate.of(1990, 1, 1)));
        users.add(new User(2, "mail2@mail.ru", "login2", "Name2", LocalDate.of(1985, 5, 15)));

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());
    }


    @Test
    public void testAddUser() {
        User userToCreate = new User(1, "mail1@mail.ru", "login1", "Name1", LocalDate.of(1990, 1, 1));
        User createdUser = new User(1, "mail1@mail.ru", "login1", "Name1", LocalDate.of(1990, 1, 1));

        when(userService.addUser(userToCreate)).thenReturn(createdUser);

        ResponseEntity<User> responseEntity = userController.addUser(userToCreate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(createdUser, responseEntity.getBody());
    }

    @Test
    public void testUpdateUser() {
        User userToUpdate = new User(1, "mail1@mail.ru", "login1", "Name1", LocalDate.of(1990, 1, 1));

        when(userService.updateUser(userToUpdate)).thenReturn(userToUpdate);

        ResponseEntity<User> responseEntity = userController.updateUser(userToUpdate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userToUpdate, responseEntity.getBody());
    }

    @Test
    public void testUpdateUserInvalidUser() {
        User validUser = new User(1, "valid@mail.ru", "valid", "Name1", LocalDate.of(1990, 1, 1));

        InvalidUser invalidUserException = assertThrows(InvalidUser.class, () -> {
            when(userService.updateUser(validUser)).thenThrow(new InvalidUser("Unknown user"));
            ResponseEntity<User> responseEntity = userController.updateUser(validUser);
        });

        assertEquals("Unknown user", invalidUserException.getMessage());
    }
}
