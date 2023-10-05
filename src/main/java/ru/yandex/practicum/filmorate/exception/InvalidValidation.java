package ru.yandex.practicum.filmorate.exception;

public class InvalidValidation extends RuntimeException {
    public InvalidValidation(String message) {
        super(message);
    }
}
