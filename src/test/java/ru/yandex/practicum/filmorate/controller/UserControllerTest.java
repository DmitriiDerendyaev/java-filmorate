package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        users.add(User.builder()
                .email("mail1@mail.ru")
                .login("login1")
                .name("Name1")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());
        users.add(User.builder()
                .email("mail2@mail.ru")
                .login("login2")
                .name("Name2")
                .birthday(LocalDate.of(1985, 5, 15))
                .build());

        when(userService.getAll()).thenReturn(users);

        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());
    }


    @Test
    public void testAddUser() {
        User userToCreate = User.builder()
                .email("mail1@mail.ru")
                .login("login1")
                .name("Name1")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        User createdUser = User.builder()
                .email("mail1@mail.ru")
                .login("login1")
                .name("Name1")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();


        when(userService.create(userToCreate)).thenReturn(createdUser);

        ResponseEntity<User> responseEntity = userController.addUser(userToCreate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(createdUser, responseEntity.getBody());
    }

    @Test
    public void testUpdateUser() {
        User userToUpdate = User.builder()
                .email("mail1@mail.ru")
                .login("login1")
                .name("Name1")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        when(userService.update(userToUpdate)).thenReturn(userToUpdate);

        ResponseEntity<User> responseEntity = userController.updateUser(userToUpdate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userToUpdate, responseEntity.getBody());
    }

    @Test
    public void testUpdateUserInvalidUser() {
        User validUser = User.builder()
                .email("valid@mail.ru")
                .login("valid")
                .name("Name1")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        InvalidUser invalidUserException = assertThrows(InvalidUser.class, () -> {
            when(userService.update(validUser)).thenThrow(new InvalidUser("Unknown user"));
            ResponseEntity<User> responseEntity = userController.updateUser(validUser);
        });

        assertEquals("Unknown user", invalidUserException.getMessage());
    }
}
