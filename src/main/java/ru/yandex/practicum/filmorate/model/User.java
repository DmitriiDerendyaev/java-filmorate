package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User{
    int id;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    String email;
    @NotBlank(message = "Login is required")
    @Pattern(regexp = "^\\S+$", message = "Login cannot contain spaces")
    String login;
    String name;
    @PastOrPresent(message = "Birthday cannot be in the future")
    LocalDate birthday;

    public String getDisplayName() {
        return (name != null && !name.isEmpty()) ? name : login;
    }
}
