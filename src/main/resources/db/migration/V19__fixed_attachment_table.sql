ALTER TABLE attachment DROP COLUMN attachment_id;
ALTER TABLE attachment ADD COLUMN comment_id uuid REFERENCES comment(id);
ALTER TABLE attachment ADD COLUMN card_id uuid REFERENCES card(id);