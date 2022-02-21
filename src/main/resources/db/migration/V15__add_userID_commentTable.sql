ALTER TABLE comment ADD COLUMN  user_id uuid
    CONSTRAINT comment_user_id_fk REFERENCES "user" (id);