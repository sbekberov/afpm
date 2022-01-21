CREATE TABLE card
(
    id           uuid PRIMARY KEY              NOT NULL,
    cardlist_id  uuid REFERENCES card_list (id) ,
    updated_by   varchar(25)                   NOT NULL,
    created_by   varchar(25)                   NOT NULL,
    created_date DATE                     NOT NULL DEFAULT now(),
    updated_date DATE,
    name         VARCHAR(200)                  NOT NULL,
    description  VARCHAR(200),
    archived     BOOLEAN                       NOT NULL DEFAULT FALSE
);