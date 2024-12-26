CREATE TABLE users
(
    username VARCHAR(50)  NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    enabled  BOOLEAN      NOT NULL
);

CREATE TABLE authorities
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);

/* add role admin */
INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN'),
       ('admin', 'ROLE_USER');

/* admin password = "admin123" */
INSERT INTO users (username, password, enabled) VALUES
    ('admin', '$2a$10$D9cCwKml.DbdE.DqcL2WuOaRmvKs0mXlPHVwFvTfPbYgpH.dKb0F.', true);