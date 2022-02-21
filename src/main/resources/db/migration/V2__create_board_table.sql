CREATE TABLE board
(
    id           UUID PRIMARY KEY               NOT NULL,
    workspace_id UUID REFERENCES workspaces (id) ,
    updated_by   VARCHAR(25),
    created_by   VARCHAR(25)                    NOT NULL,
    created_date DATE                      NOT NULL,
    updated_date DATE,
    name         VARCHAR(20)                    NOT NULL,
    description  VARCHAR(50),
    archived     boolean                        NOT NULL DEFAULT FALSE,
    visibility   VARCHAR(10)                    NOT NULL DEFAULT 'WORKSPACE'
);