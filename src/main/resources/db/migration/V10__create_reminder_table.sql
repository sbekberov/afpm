CREATE TABLE reminder
(
    id           uuid PRIMARY KEY NOT NULL,
    start        DATE        NOT NULL,
    "end"        DATE       NOT NULL,
    remind_on    DATE       NOT NULL,
    active       BOOLEAN                   DEFAULT FALSE,
    updated_by   varchar(25)      ,
    created_by   varchar(25)      NOT NULL,
    created_date DATE        NOT NULL DEFAULT now(),
    updated_date DATE,
    card_id      uuid
        CONSTRAINT reminder_card_id_fk REFERENCES card (id)
);