package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Film extends Entity{

    @NotNull
    @NotBlank(message = "Name is required")
    String name;

    @Size(max = 200, message = "Description cannot be more 200 character")
    String description;

    @Past(message = "Release must be in past")
    LocalDate releaseDate;

    Duration duration;

    public long getDuration() {
        return duration.getSeconds();
    }

    public Duration getDurationValue() {
        return duration;
    }
}
