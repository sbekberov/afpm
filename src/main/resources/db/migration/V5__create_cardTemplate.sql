CREATE TABLE card_template
(
    id           UUID PRIMARY KEY               NOT NULL,
    updated_by   VARCHAR(25),
    created_by   VARCHAR(25)                    NOT NULL,
    created_date DATE                      NOT NULL,
    updated_date DATE,
    name         VARCHAR(20)                    NOT NULL,
    description  VARCHAR(50)
);