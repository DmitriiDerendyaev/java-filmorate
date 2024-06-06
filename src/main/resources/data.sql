INSERT INTO mpa (mpa_id, rating)
values (1, 'G');
INSERT INTO mpa (mpa_id, rating)
values (2, 'PG');
INSERT INTO mpa (mpa_id, rating)
values (3, 'PG-13');
INSERT INTO mpa (mpa_id, rating)
values (4, 'R');
INSERT INTO mpa (mpa_id, rating)
values (5, 'NC-17');

INSERT INTO genres (genre_id, name)
values (1, 'Комедия');
INSERT INTO genres (genre_id, name)
values (2, 'Драма');
INSERT INTO genres (genre_id, name)
values (3, 'Мультфильм');
INSERT INTO genres (genre_id, name)
values (4, 'Триллер');
INSERT INTO genres (genre_id, name)
values (5, 'Документальный');
INSERT INTO genres (genre_id, name)
values (6, 'Боевик');

-- Заполнение таблицы Users с зашифрованными паролями
INSERT INTO users (email, login, user_name, password, birthday)
VALUES ('user1@example.com', 'user1', 'User One', '$2a$10$nG4mHrc1VoqTuXLuBaOW7eRjdVkYSQ2fBxk6ApL.SF2N39yww8Qiq',
        '1990-01-01'),
       ('user2@example.com', 'user2', 'User Two', '$2a$10$uznaeWsRfy58bf05I9lU8u5ndiVYhfdp5byUirl/.xyCoBAM5nk4u',
        '1995-05-05'),
       ('user3@example.com', 'user3', 'User Three', '$2a$10$/UcPkff5fU7aSCH1gOM27OLtA2bHzh62aWaxQe7179NQE7KDAkh8W',
        '2000-10-10');


-- Заполнение таблицы Films
INSERT INTO films (name, description, release_date, duration, mpa_id)
VALUES ('Фильм 1', 'Описание фильма 1', '2022-01-01', 120, 1),
       ('Фильм 2', 'Описание фильма 2', '2023-02-02', 130, 2),
       ('Фильм 3', 'Описание фильма 3', '2024-03-03', 140, 3);

-- Заполнение таблицы Film_Genre
INSERT INTO film_genre (film_id, genre_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

-- Заполнение таблицы Friendship
INSERT INTO friendship (user_id, friend_id, status)
VALUES (1, 2, true),
       (1, 3, true),
       (2, 3, true);