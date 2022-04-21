CREATE TABLE reminder
(
    id           uuid PRIMARY KEY NOT NULL,
    start        DATE        NOT NULL,
    "end"        DATE       NOT NULL,
    remind_on    DATE       NOT NULL,
    active       BOOLEAN                   DEFAULT FALSE
);