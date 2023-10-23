package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Builder
public class Film extends Entity {

    @NotNull
    @NotBlank(message = "Name is required")
    String name;

    @Size(max = 200, message = "Description cannot be more 200 character")
    String description;

    @Past(message = "Release must be in past")
    LocalDate releaseDate;

    Duration duration;

    private Set<Long> likes;
    
    public Film() {
        // Инициализация likes в конструкторе
        likes = new HashSet<>();
    }

    public Set<Long> getLikes(){
        return likes;
    }

    public void addLike(Long userId){
        likes.add(userId);
    }

    public void removeLike(Long userId){
        likes.remove(userId);
    }

    public long getDuration() {
        return duration.getSeconds();
    }

    public Duration getDurationValue() {
        return duration;
    }
}
