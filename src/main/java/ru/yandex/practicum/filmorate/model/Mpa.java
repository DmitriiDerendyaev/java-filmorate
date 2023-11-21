package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mpa extends Entity {

    @NotBlank
    private String rating;

    public Mpa(Long id, String rating) {
        super(id);
        this.rating = rating;
    }
}
