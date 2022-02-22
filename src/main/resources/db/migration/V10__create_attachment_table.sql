CREATE TABLE attachment
(
    id            uuid PRIMARY KEY,
    link          VARCHAR(255)                    NOT NULL,
    name          VARCHAR(200)                    NOT NULL,
    updated_by    varchar(25)                     ,
    created_by    varchar(25)                     NOT NULL,
    created_date  DATE                       NOT NULL DEFAULT now(),
    updated_date  DATE,
    comment_id   UUID,
    card_id      UUID,
    FOREIGN KEY (card_id) REFERENCES card (id),
    FOREIGN KEY (comment_id) REFERENCES comment (id)
);