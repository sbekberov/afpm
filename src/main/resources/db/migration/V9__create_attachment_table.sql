CREATE TABLE attachment
(
    id            uuid PRIMARY KEY,
    link          VARCHAR(255)                    NOT NULL,
    name          VARCHAR(200)                    NOT NULL,
    updated_by    varchar(25)                     NOT NULL,
    created_by    varchar(25)                     NOT NULL,
    created_date  TIMESTAMP                       NOT NULL DEFAULT now(),
    updated_date  TIMESTAMP,
    attachment_id uuid REFERENCES attachment (id) NOT NULL
);