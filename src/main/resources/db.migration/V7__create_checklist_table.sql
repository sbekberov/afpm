CREATE TABLE checklist
(
    id           uuid PRIMARY KEY NOT NULL,
    name         VARCHAR(200)     NOT NULL,
    updated_by   VARCHAR(25),
    created_by   VARCHAR(25)      NOT NULL,
    created_date TIMESTAMP        NOT NULL,
    updated_date TIMESTAMP,
    card_id      uuid             NOT NULL
        CONSTRAINT check_list_card_id_fk REFERENCES card (id)
);