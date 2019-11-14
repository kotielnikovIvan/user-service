CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    link     VARCHAR(255) NOT NULL,
    active   BOOLEAN      NOT NULL DEFAULT false,
    PRIMARY KEY (id),
    UNIQUE (email)
);