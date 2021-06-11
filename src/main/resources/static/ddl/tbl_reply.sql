CREATE SEQUENCE seq_reply;

CREATE TABLE tbl_reply (
    reply_no NUMBER(10),
    board_no NUMBER(10) NOT NULL,
    reply_text VARCHAR2(1000) NOT NULL,
    reply_writer VARCHAR2(50) NOT NULL,
    reply_date DATE default SYSDATE,
    CONSTRAINT pk_reply PRIMARY KEY (reply_no),
    CONSTRAINT fk_reply_free_board
    FOREIGN KEY (board_no)
    REFERENCES board (board_no)
);

ALTER TABLE tbl_reply
ADD CONSTRAINT pk_reply
PRIMARY KEY (reply_no);

ALTER TABLE tbl_reply
ADD CONSTRAINT fk_reply_free_board
FOREIGN KEY (board_no)
REFERENCES board (board_no);