CREATE TABLE ai_hints (
                          hints_id   BIGSERIAL PRIMARY KEY,
                          attempt_id BIGINT       NOT NULL,
                          CONSTRAINT fk_ai_hints_attempt FOREIGN KEY (attempt_id) REFERENCES attempt (attempt_id) ON DELETE CASCADE,
                          created_at TIMESTAMP    NOT NULL,
                          hint_text  TEXT         NOT NULL
);