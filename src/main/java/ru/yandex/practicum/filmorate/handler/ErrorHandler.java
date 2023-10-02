package ru.yandex.practicum.filmorate.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentNotValidException ex) {
        String message = "Invalid parameter type: " + ex.getMessage();
        return ResponseEntity.badRequest().body(message);
    }

}
