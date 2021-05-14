
CREATE SEQUENCE seq_board;

CREATE TABLE board (
    board_no NUMBER(10),
    writer VARCHAR2(20) NOT NULL,
    title VARCHAR2(200) NOT NULL,
    content VARCHAR2(2000),
    view_cnt NUMBER(10) DEFAULT 0,
    CONSTRAINT pk_board PRIMARY KEY (board_no)
);