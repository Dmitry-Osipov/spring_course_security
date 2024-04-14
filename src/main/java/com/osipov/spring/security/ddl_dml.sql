CREATE TABLE my_db.users (
    username VARCHAR(15),
    password VARCHAR(100),
    enabled tinyint(1),
    PRIMARY KEY (username)
);

CREATE TABLE my_db.authorities (
    username VARCHAR(15),
    authority VARCHAR(25),
    FOREIGN KEY (username) REFERENCES my_db.users (username)
);

-- Для БД важно указывать алгоритм шифрования (например, {noop} для хранения самого пароля в БД или {bcrypt} для
-- шифрованного пароля)
INSERT INTO my_db.users (username, password, enabled) VALUES
('dmitry', '{noop}dmitry', 1),
('elena', '{noop}elena', 1),
('ivan', '{noop}ivan', 1);

INSERT INTO my_db.authorities (username, authority) VALUES
('dmitry', 'ROLE_EMPLOYEE'),
('elena', 'ROLE_HR'),
('ivan', 'ROLE_HR'),
('ivan', 'ROLE_MANAGER');
