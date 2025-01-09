CREATE TABLE topic (
    topic_id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    message TEXT NOT NULL,
    date DATETIME NOT NULL,
    status BOOLEAN NOT NULL,
    course VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (topic_id),

    CONSTRAINT fk_user_topic FOREIGN KEY (user_id) REFERENCES user (user_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
