//package ru.yandex.practicum.filmorate.controller;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import ru.yandex.practicum.filmorate.exception.InvalidUser;
//import ru.yandex.practicum.filmorate.model.User;
//import ru.yandex.practicum.filmorate.service.UserService;
//import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
//import ru.yandex.practicum.filmorate.storage.StorageCRUD;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//public class UserControllerTest {
//
//    @Test
//    public void testGetAllUsers() {
//
//        StorageCRUD<User> userStorage = Mockito.mock(InMemoryUserStorage.class);
//
//        List<User> users = new ArrayList<>();
//        users.add(User.builder()
//                .email("mail1@mail.ru")
//                .login("login1")
//                .name("Name1")
//                .birthday(LocalDate.of(1990, 1, 1))
//                .build());
//        users.add(User.builder()
//                .email("mail2@mail.ru")
//                .login("login2")
//                .name("Name2")
//                .birthday(LocalDate.of(1985, 5, 15))
//                .build());
//
//        when(userStorage.getAll()).thenReturn(users);
//
//        UserController userController = new UserController((UserService) userStorage);
//
//        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(users, responseEntity.getBody());
//    }
//
//
//    @Test
//    public void testAddUser() {
//        StorageCRUD<User> userStorage = Mockito.mock(InMemoryUserStorage.class);
//
//
//        User userToCreate = User.builder()
//                .email("mail1@mail.ru")
//                .login("login1")
//                .name("Name1")
//                .birthday(LocalDate.of(1990, 1, 1))
//                .build();
//
//        User createdUser = User.builder()
//                .email("mail1@mail.ru")
//                .login("login1")
//                .name("Name1")
//                .birthday(LocalDate.of(1990, 1, 1))
//                .build();
//
//
//        when(userStorage.create(userToCreate)).thenReturn(createdUser);
//
//        UserController userController = new UserController((UserService) userStorage);
//
//        ResponseEntity<User> responseEntity = userController.addUser(userToCreate);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(createdUser, responseEntity.getBody());
//    }
//
//    @Test
//    public void testUpdateUser() {
//        StorageCRUD<User> userStorage = Mockito.mock(InMemoryUserStorage.class);
//
//        User userToUpdate = User.builder()
//                .email("mail1@mail.ru")
//                .login("login1")
//                .name("Name1")
//                .birthday(LocalDate.of(1990, 1, 1))
//                .build();
//
//        when(userStorage.update(userToUpdate)).thenReturn(userToUpdate);
//
//        UserController userController = new UserController((UserService) userStorage);
//
//        ResponseEntity<User> responseEntity = userController.updateUser(userToUpdate);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(userToUpdate, responseEntity.getBody());
//    }
//
//    @Test
//    public void testUpdateUserInvalidUser() {
//        StorageCRUD<User> userStorage = Mockito.mock(InMemoryUserStorage.class);
//
//        User validUser = User.builder()
//                .email("valid@mail.ru")
//                .login("valid")
//                .name("Name1")
//                .birthday(LocalDate.of(1990, 1, 1))
//                .build();
//
//        UserController userController = new UserController((UserService) userStorage);
//
//        InvalidUser invalidUserException = assertThrows(InvalidUser.class, () -> {
//            when(userStorage.update(validUser)).thenThrow(new InvalidUser("Unknown user"));
//            ResponseEntity<User> responseEntity = userController.updateUser(validUser);
//        });
//
//        assertEquals("Unknown user", invalidUserException.getMessage());
//    }
//}
