CREATE TABLE label
(
    id      uuid PRIMARY KEY NOT NULL,
    name    VARCHAR(200)     NOT NULL,
    color_id UUID             NOT NULL,
    FOREIGN KEY (color_id) REFERENCES color (id),
    card_id      UUID             NOT NULL,
    FOREIGN KEY (card_id) REFERENCES card (id)
);