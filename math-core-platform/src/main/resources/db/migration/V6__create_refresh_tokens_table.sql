CREATE TABLE refresh_tokens (
                                token_id    BIGSERIAL PRIMARY KEY,
                                user_id     BIGINT       NOT NULL UNIQUE,
                                token       VARCHAR(255) NOT NULL UNIQUE,
                                expiry_date TIMESTAMP    NOT NULL,
                                CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);