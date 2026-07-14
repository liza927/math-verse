CREATE TABLE topics
(
    topic_id   BIGSERIAL primary key,
    name_topic VARCHAR(100) NOT NULL unique
);

CREATE TABLE users
(
    user_id  BIGSERIAL primary key,
    email    VARCHAR(100) NOT NULL unique,
    password VARCHAR(50)  NOT NULL,
    role     VARCHAR(50)  NOT NULL
);

CREATE TABLE task_template
(
    task_template_id BIGSERIAL primary key,
    topic_id         BIGINT       NOT NULL,
    CONSTRAINT fk_task_template_topic foreign key (topic_id) REFERENCES topics (topic_id) ON DELETE CASCADE,
    operation        VARCHAR(100) NOT NULL,
    complexity       INT          NOT NULL,
    generation_param VARCHAR(100) NOT NULL
);

CREATE TABLE attempt
(
    attempt_id       BIGSERIAL primary key,
    student_answer   VARCHAR(100) NOT NULL,
    correct_answer   VARCHAR(100) NOT NULL,
    time_answer      TIMESTAMP    NOT NULL,
    generated_task   TEXT         NOT NULL,
    user_id          BIGINT       NOT NULL,
    constraint fk_attempt_user foreign key (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    task_template_id BIGINT       NOT NULL,
    constraint fk_attempt_task_template foreign key (task_template_id) REFERENCES task_template (task_template_id) ON DELETE CASCADE
);

