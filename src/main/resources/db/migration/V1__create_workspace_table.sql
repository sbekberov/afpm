CREATE TABLE workspace
(
    id                   UUID PRIMARY KEY NOT NULL,
    updated_by           VARCHAR(25),
    created_by           VARCHAR(25)      NOT NULL,
    created_date         DATE             NOT NULL,
    updated_date         DATE,
    name                 VARCHAR(20)      NOT NULL,
    description          VARCHAR(50),
    workspace_visibility VARCHAR(10)      NOT NULL DEFAULT 'PUBLIC'
);