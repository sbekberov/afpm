CREATE TABLE "user"
(
    id        uuid PRIMARY KEY NOT NULL,
    firstname VARCHAR(200)     NOT NULL,
    lastname  VARCHAR(200)     NOT NULL,
    email     VARCHAR(255)     NOT NULL,
    time_zone TIMESTAMP        NOT NULL
);