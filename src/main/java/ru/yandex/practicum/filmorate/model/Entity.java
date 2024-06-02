package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
public abstract class Entity {
    protected Long id;

}
