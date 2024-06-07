package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.model.validateGroup.UpdateGroup;

import javax.validation.constraints.*;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User extends Entity {
    @Email(message = "Invalid email format", groups = {UpdateGroup.class})
    @NotBlank(message = "Email is required", groups = {UpdateGroup.class})
    private String email;

    @NotBlank(message = "Login is required", groups = {UpdateGroup.class})
    @Pattern(regexp = "^[^\\s]+$", message = "Login cannot contain spaces", groups = {UpdateGroup.class})
    private String login;

    private String name;

    @NotBlank(message = "Password is required", groups = {UpdateGroup.class})
    private String password;

    @Past(message = "Birthday cannot be in the future", groups = {UpdateGroup.class})
    private LocalDate birthday;

    public String getName() {
        return (name != null && !name.isEmpty()) ? name : login;
    }
}
