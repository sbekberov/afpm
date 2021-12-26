CREATE TABLE workspace_member
(
    workspace_id uuid NOT NULL
        CONSTRAINT workspace_member_id_fk REFERENCES workspace (id),
    member_id    uuid NOT NULL
        CONSTRAINT member_workspace_id_fk REFERENCES member (id),
    CONSTRAINT workspace_member_pk PRIMARY KEY (workspace_id, member_id)
);

CREATE TABLE board_member
(
    board_id  uuid NOT NULL
        CONSTRAINT board_member_id_fk REFERENCES board (id),
    member_id uuid NOT NULL
        CONSTRAINT member_board_id_fk REFERENCES member (id),
    CONSTRAINT board_member_pk PRIMARY KEY (board_id, member_id)
);

CREATE TABLE card_member
(
    card_id   uuid NOT NULL
        CONSTRAINT card_member_id_fk REFERENCES card (id),
    member_id uuid NOT NULL
        CONSTRAINT member_id_fk REFERENCES member (id),
    CONSTRAINT card_member_pk PRIMARY KEY (card_id, member_id)
);
CREATE TABLE card_label
(
    card_id   uuid NOT NULL
        CONSTRAINT card_label_id_fk REFERENCES card (id),
    label_id uuid NOT NULL
        CONSTRAINT card_id_fk REFERENCES label (id),
    CONSTRAINT card_label_pk PRIMARY KEY (card_id, label_id)
);
