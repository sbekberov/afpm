ALTER TABLE comment ADD COLUMN  user_id uuid         NOT NULL
    CONSTRAINT comment_user_id_fk REFERENCES "user" (id);