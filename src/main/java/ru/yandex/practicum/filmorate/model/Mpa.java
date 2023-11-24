package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mpa extends Entity {

    @NotBlank
    private String name;

    public Mpa(Long id, String name) {
        super(id);
        this.name = name;
    }
}
