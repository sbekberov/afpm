CREATE TABLE reminder
(
    id           uuid PRIMARY KEY NOT NULL,
    start        TIMESTAMP        NOT NULL,
    "end"        TIMESTAMP        NOT NULL,
    remind_on    TIMESTAMP        NOT NULL,
    active       BOOLEAN                   DEFAULT FALSE,
    updated_by   varchar(25)      NOT NULL,
    created_by   varchar(25)      NOT NULL,
    created_date TIMESTAMP        NOT NULL DEFAULT now(),
    updated_date TIMESTAMP,
    card_id      uuid             NOT NULL
        CONSTRAINT reminder_card_id_fk REFERENCES card (id)
);