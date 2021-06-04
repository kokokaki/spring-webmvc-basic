-- 회원 관리 테이블
CREATE TABLE member (
    account VARCHAR2(50),
    password VARCHAR2(150) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    email VARCHAR2(100) NOT NULL UNIQUE,
    auth VARCHAR2(20) DEFAULT 'COMMON',
    reg_date DATE DEFAULT SYSDATE,
    CONSTRAINT pk_member PRIMARY KEY (account)
);

INSERT INTO member (account, password, name, email, auth)
VALUES ('admin', '1234', '관리자', 'admin@gmail.com', 'ADMIN');

COMMIT;

-- 자동로그인 관련 컬럼 추가
ALTER TABLE member ADD session_id VARCHAR2(200) DEFAULT 'none';
ALTER TABLE member ADD limit_time DATE;

SELECT * FROM member;
