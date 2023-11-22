package ru.yandex.practicum.filmorate.storage.db.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmDb{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Film> findAllFilms() {
        return jdbcTemplate.query(
                "SELECT * FROM films",
                (resultSet, rowNum) -> filmParameters(resultSet));
    }

    @Override
public Film create(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlQuery = "INSERT INTO films (name, description, release_date, duration, mpa_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setLong(4, film.getDuration());
            stmt.setLong(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);

        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        if (film.getGenres().stream().mapToLong(Genre::getId).toArray() != null) {
            insertGenresInTable(film);
            film.setGenres(findGenresFilm(film));
        }
        return film;
    }

    @Override
    public Film rewriteFilm(Film film) {
        String sqlQuery = "UPDATE films SET name=?, description=?, release_date=?, duration=?, MPA_ID=? WHERE film_id=? ";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());

        // Удаление старых записей в таблице film_genre
        jdbcTemplate.update("DELETE FROM film_genre WHERE film_id=?", film.getId());

        // Вставка новых записей в таблицу film_genre
        insertGenresInTable(film);

        Long mpaId = film.getMpa().getId();
        film.setMpa(findMpaFilm(mpaId));
        film.setGenres(findGenresFilm(film));
        film.setRate(findRateFilm(Math.toIntExact(film.getId())));
        return film;
    }

    private void insertGenresInTable(Film film) {
        for (Genre genre : film.getGenres()) {
            // Проверка наличия записи в таблице перед вставкой
            if (!genreExistsInFilmGenreTable(film.getId(), genre.getId())) {
                jdbcTemplate.update("INSERT INTO film_genre(film_id, genre_id) VALUES (?, ?)", film.getId(), genre.getId());
            }
        }
    }

//    public void insertGenresInTable(Film film) {
//        long[] genresIds = film.getGenres().stream().mapToLong(Genre::getId).toArray();
//        for (long genreId : genresIds) {
//            jdbcTemplate.update("INSERT INTO film_genre(film_id, genre_id) VALUES (?,?)", film.getId(), genreId);
//        }
//    }

    private boolean genreExistsInFilmGenreTable(Long filmId, Long genreId) {
        String sqlQuery = "SELECT COUNT(*) FROM film_genre WHERE film_id = ? AND genre_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class, filmId, genreId) > 0;
    }


    @Override
    public Film findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM films WHERE film_id=?",
                (resultSet, rowNum) -> filmParameters(resultSet), id);
    }

    @Override
    public boolean containsFilm(Long id) {
        Long count = jdbcTemplate.queryForObject("select count(film_id) from films where film_id = ?", Long.class, id);
        return count == 1;
    }

    @Override
    public void likeFilm(Long id, Long userId) {
        jdbcTemplate.update(
                "INSERT INTO likes (film_id, user_id) VALUES (?, ?)", id, userId);
        findById(id).setRate(findById(id).getRate() + 1);
    }

    @Override
    public void deleteLike(Long id, Long userId) {
        jdbcTemplate.update(
                "DELETE FROM likes WHERE film_id=? and user_id=?", id, userId);
        if (findById(id).getRate() > 0) {
            findById(id).setRate(findById(id).getRate() - 1);
        }
    }

    @Override
    public List<Film> bestFilms(Long count) {
        List<Film> films = jdbcTemplate.query(
                "SELECT * FROM films",
                (resultSet, rowNum) -> filmParameters(resultSet));
        return films.stream()
                .sorted((x1, x2) -> x2.getRate() - x1.getRate())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film filmParameters(ResultSet resultSet) {
        try {
            Film film = new Film();
            film.setId((long) resultSet.getInt("film_id"));
            film.setName(resultSet.getString("name"));
            film.setDescription(resultSet.getString("description"));
            film.setReleaseDate(resultSet.getDate("release_date").toLocalDate());
            film.setDuration(resultSet.getInt("duration"));
            film.setMpa(findMpaFilm(resultSet.getLong("mpa_id")));
            film.setGenres(findGenresFilm(film));
            film.setRate(findRateFilm(Math.toIntExact(film.getId())));
            return film;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer findRateFilm(Integer id) {
        return this.jdbcTemplate.queryForObject("select count(user_id) from likes WHERE film_id=?", Integer.class, id);
    }

    public Mpa findMpaFilm(Long mpaId) {
        return new Mpa(mpaId, jdbcTemplate.queryForObject(
                "SELECT RATING FROM mpa where mpa_id = ?",
                String.class, mpaId));
    }

    public List<Genre> findGenresFilm(Film film) {
        List<Integer> genresIds = jdbcTemplate.queryForList("SELECT film_genre.genre_id FROM film_genre WHERE film_id=?", Integer.class, film.getId());
        List<Genre> genres = new ArrayList<>();
        for (Integer j : genresIds) {
            Genre genre = new Genre();
            String str = jdbcTemplate.queryForObject("SELECT name FROM genres WHERE genre_id=?", String.class, j);
            genre.setName(str);
            genre.setId(Long.valueOf(j));
            if (genres.contains(genre)) {
                break;
            }
            genres.add(genre);
        }
        return genres;
    }
}
