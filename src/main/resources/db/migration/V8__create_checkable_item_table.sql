CREATE TABLE checkable_Item
(
    id           uuid PRIMARY KEY               NOT NULL,
    name         VARCHAR(200)                   NOT NULL,
    checked      boolean                        NOT NULL DEFAULT FALSE,
    checklist_id uuid REFERENCES checklist (id)
);