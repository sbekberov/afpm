CREATE TABLE card
(
    id           uuid PRIMARY KEY              NOT NULL,
    cardList_id  uuid REFERENCES card_list (id) ,
    updated_by   varchar(25)                   NOT NULL,
    created_by   varchar(25)                   NOT NULL,
    created_date TIMESTAMP                     NOT NULL DEFAULT now(),
    updated_date TIMESTAMP,
    name         VARCHAR(200)                  NOT NULL,
    description  VARCHAR(200),
    archived     BOOLEAN                       NOT NULL DEFAULT FALSE
);