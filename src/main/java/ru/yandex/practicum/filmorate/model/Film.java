package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.lang.model.type.UnionType;
import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;


@Data
public class Film {
    int id;

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

    public Duration getDurationValue(){
        return duration;
    }
}
