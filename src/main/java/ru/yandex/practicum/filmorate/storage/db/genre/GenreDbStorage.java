package ru.yandex.practicum.filmorate.storage.db.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class GenreDbStorage implements GenreDb {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenres() {
        String sqlQuery = "SELECT * FROM genres";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public Genre getGenreById(Long id) {
        if (!containsGenre(id)) {
            throw new ObjectNotFoundException(String.format("Genre with id=%d not found", id));
        }
        return jdbcTemplate.queryForObject("SELECT * FROM genres WHERE genre_id=?",
                (resultSet, rowNum) -> {
                    Genre newGenre = new Genre();
                    newGenre.setId(id);
                    newGenre.setName(resultSet.getString("genre_name"));
                    return newGenre;
                }, id);
    }

    @Override
    public boolean containsGenre(Long id) {
        try {
            Long count = jdbcTemplate.queryForObject("select count(genre_id) from genres where genre_id = ?", Long.class, id);
            return count == 1;
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(String.format("Genre with id=%d not found", id));
        }
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Long id = resultSet.getLong("genre_id");
        String name = resultSet.getString("genre_name");

        Genre genre = new Genre(id, name);
        return genre;
    }
}
