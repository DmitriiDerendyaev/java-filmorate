package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User extends Entity {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Login is required")
    @Pattern(regexp = "^[^\\s]+$", message = "Login cannot contain spaces")
    private String login;

    private String name;
    @Past(message = "Birthday cannot be in the future")
    private LocalDate birthday;

    public String getName() {
        return (name != null && !name.isEmpty()) ? name : login;
    }
}
