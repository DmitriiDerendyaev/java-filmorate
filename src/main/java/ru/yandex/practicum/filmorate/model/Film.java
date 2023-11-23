package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Builder
public class Film extends Entity {

    @NotBlank(message = "Name is required")
    private String name;

    @Size(max = 200, message = "Description cannot be more 200 character")
    private String description;

    @Past(message = "Release must be in past")
    private LocalDate releaseDate;

    @Positive(message = "Duration must be greater then zero")
    private long duration;

    @NotNull
    private Mpa mpa;

    private List<Genre> genres = new ArrayList<>();

    private int rate = 0;

    private Set<Long> likes;

    public Film() {
        likes = new HashSet<>();
    }

    public Set<Long> getLikes() {
        return likes;
    }

    public void addLike(Long userId) {
        likes.add(userId);
    }

    public void removeLike(Long userId) {
        likes.remove(userId);
    }

    public long getDuration() {
        return duration;
    }

    public long getDurationValue() {
        return duration;
    }
}
