CREATE TABLE checklist
(
    id           uuid PRIMARY KEY NOT NULL,
    name         VARCHAR(200)     NOT NULL,
    updated_by   VARCHAR(25),
    created_by   VARCHAR(25)      NOT NULL,
    created_date DATE        NOT NULL,
    updated_date DATE,
    card_id      uuid
        CONSTRAINT check_list_card_id_fk REFERENCES card (id)
);