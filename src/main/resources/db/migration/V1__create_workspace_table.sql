CREATE TABLE workspace
(
    id           UUID PRIMARY KEY NOT NULL,
    updated_by   VARCHAR(25) ,
    created_by   VARCHAR(25)      NOT NULL ,
    created_date TIMESTAMP        NOT NULL ,
    updated_date TIMESTAMP,
    name         VARCHAR(20)      NOT NULL,
    description  VARCHAR(50)
);