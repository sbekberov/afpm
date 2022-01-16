CREATE TABLE member
(
    id                   uuid PRIMARY KEY               NOT NULL,
    role                 VARCHAR(10)                    NOT NULL DEFAULT 'GUEST',
    user_id              uuid
        CONSTRAINT member_user_id_fk REFERENCES "user" (id),
    workspace_visibility VARCHAR(10)                    NOT NULL DEFAULT 'PUBLIC',
    workspace_id         uuid REFERENCES workspace (id)
);