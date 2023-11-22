package ru.yandex.practicum.filmorate.storage.db.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MpaDbStorage implements MpaDb {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getMpa() {
        String sqlQuery = "SELECT * FROM mpa";
        return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        Long id = resultSet.getLong("mpa_id");
        String rating = resultSet.getString("rating");

        Mpa mpa = new Mpa(id, rating);
        return mpa;
    }


    @Override
    public Mpa getMpaById(Long id) {
        if (!containsMpa(id)) {
            throw new ObjectNotFoundException(String.format("Mpa with id=%d not found", id));
        }
        return jdbcTemplate.queryForObject("SELECT * FROM mpa WHERE mpa_id=?",
                (resultSet, rowNum) -> {
                    Mpa newMpa = new Mpa();
                    newMpa.setId(id);
                    newMpa.setName(resultSet.getString("rating"));
                    return newMpa;
                }, id);
    }

    @Override
    public boolean containsMpa(Long id) {
        try {
            Long count = jdbcTemplate.queryForObject("select count(mpa_id) from mpa where mpa_id = ?", Long.class, id);
            return count == 1;
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(String.format("Mpa with id=%d not found", id));
        }
    }

}
