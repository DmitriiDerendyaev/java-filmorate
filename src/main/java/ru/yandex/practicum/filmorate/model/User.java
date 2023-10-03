package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class User extends Entity {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    String email;
    @NotBlank(message = "Login is required")
    @Pattern(regexp = "^[^\\s]+$", message = "Login cannot contain spaces")
    String login;
    String name;
    @Past(message = "Birthday cannot be in the future")
    LocalDate birthday;

    public String getName() {
        return (name != null && !name.isEmpty()) ? name : login;
    }
}
