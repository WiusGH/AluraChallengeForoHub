CREATE TABLE user (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(60) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,

    PRIMARY KEY (user_id)
);
