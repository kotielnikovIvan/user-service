CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT,
                                     email VARCHAR(255),
                                     password VARCHAR(255),
                                     link VARCHAR(255),
                                     active BOOLEAN DEFAULT false,
                                     PRIMARY KEY(id), UNIQUE (email)
);