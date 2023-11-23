package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.model.validateGroup.UpdateGroup;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Film extends Entity {

    @NotBlank(message = "Name is required", groups = {UpdateGroup.class})
    private String name;

    @Size(max = 200, message = "Description cannot be more 200 character", groups = {UpdateGroup.class})
    private String description;

    @Past(message = "Release must be in past", groups = {UpdateGroup.class})
    private LocalDate releaseDate;

    @Positive(message = "Duration must be greater then zero", groups = {UpdateGroup.class})
    private long duration;

    @NotNull(groups = {UpdateGroup.class})
    private Mpa mpa;

    private List<Genre> genres = new ArrayList<>();

    private int rate = 0;

    public long getDuration() {
        return duration;
    }
}
