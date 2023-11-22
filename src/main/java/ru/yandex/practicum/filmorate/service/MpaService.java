package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.mpa.MpaDb;
import ru.yandex.practicum.filmorate.storage.db.mpa.MpaDbStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MpaService {

    private final MpaDb mpaStorage;

    public List<Mpa> getMpa() {
        return mpaStorage.getMpa();
    }

    public Mpa getMpaById(Long id) {
        if (!mpaStorage.containsMpa(id)) {
            throw new ObjectNotFoundException(String.format("Mpa with id=%d not found", id));
        }
        log.info("Info about genre id=" + id);
        return mpaStorage.getMpaById(id);
    }
}
