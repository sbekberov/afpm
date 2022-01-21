CREATE TABLE member
(
    id                   uuid PRIMARY KEY               NOT NULL,
    role                 VARCHAR(10)                    NOT NULL DEFAULT 'GUEST',
    user_id              uuid
        CONSTRAINT member_user_id_fk REFERENCES "user" (id),
    updated_by   varchar(25),
    created_by   varchar(25)  ,
    created_date DATE          ,
    updated_date DATE
);