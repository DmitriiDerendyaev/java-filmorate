package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Genre extends Entity {

    @NotBlank
    private String name;

    @Builder
    public Genre(Long id, @NotBlank String name) {
        super(id);
        this.name = name;
    }
}
