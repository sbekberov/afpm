CREATE TABLE card_list
(
    id           uuid PRIMARY KEY           NOT NULL,
    board_id     uuid REFERENCES board (id) ,
    updated_by   varchar(25)                NOT NULL,
    created_by   varchar(25)                NOT NULL,
    created_date TIMESTAMP                  NOT NULL,
    updated_date TIMESTAMP,
    name         VARCHAR(200)               NOT NULL,
    archived     BOOLEAN                    NOT NULL DEFAULT FALSE
);