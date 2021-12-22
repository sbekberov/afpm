CREATE TABLE board
(
    id           UUID PRIMARY KEY               NOT NULL,
    workspace_id UUID REFERENCES workspace (id) NOT NULL,
    updated_by   VARCHAR(25),
    created_by   VARCHAR(25)                    NOT NULL,
    created_date TIMESTAMP                      NOT NULL,
    updated_date TIMESTAMP,
    name         VARCHAR(20)                    NOT NULL,
    description  VARCHAR(50),
    archived     boolean                        NOT NULL DEFAULT FALSE,
    visibility   VARCHAR(10)                    NOT NULL DEFAULT 'WORKSPACE'
);