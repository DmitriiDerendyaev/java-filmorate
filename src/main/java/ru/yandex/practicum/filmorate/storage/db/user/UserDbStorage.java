package ru.yandex.practicum.filmorate.storage.db.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserDbStorage implements UserDb {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAllUsers() {
        return jdbcTemplate.query(
                "SELECT * FROM users",
                (resultSet, rowNum) -> userParameters(resultSet));
    }

    @Override
    public User createUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlQuery = "INSERT INTO users (email, login, user_name, birthday) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);

        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "UPDATE users SET email=?, login=?, user_name=?, birthday=? WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE user_id=?",
                (resultSet, rowNum) -> userParameters(resultSet), id);
    }

    @Override
    public boolean containsUser(Long id) {
        Long count = jdbcTemplate.queryForObject(
                "select count(user_id) from users where user_id = ?", Long.class, id);
        return count == 1;
    }

    @Override
    public void addFriends(Long id, Long friendId) {
        jdbcTemplate.update(
                "INSERT INTO friendship (friend_id, user_id, status) VALUES (?, ?, ?)",
                friendId, id, true);
    }

    @Override
    public void deleteFriendsById(Long id, Long friendId) {
        jdbcTemplate.update(
                "DELETE FROM friendship WHERE friend_id = ?",
                friendId);
    }

    @Override
    public List<User> getFriends(Long id) {
        return this.jdbcTemplate.query(
                "SELECT * FROM users WHERE user_id in (SELECT friend_id FROM friendship WHERE user_id=?)",
                (resultSet, rowNum) -> userParameters(resultSet), id);
    }

    @Override
    public List<User> commonFriends(Long id, Long otherId) {
        List<User> firstId = getFriends(id);
        List<User> secondOtherId = getFriends(otherId);
        firstId.retainAll(secondOtherId);
        return firstId;
    }

    public User userParameters(ResultSet resultSet) {
        try {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setEmail(resultSet.getString("email"));
            user.setLogin(resultSet.getString("login"));
            user.setName(resultSet.getString("user_name"));
            user.setBirthday(resultSet.getDate("birthday").toLocalDate());
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
