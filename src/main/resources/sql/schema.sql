-- 초기화
DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS ARTICLE;
DROP TABLE IF EXISTS USERS;

-- USERS 테이블 생성
CREATE TABLE USERS(
    userId VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    name VARCHAR(255),
    email VARCHAR(255)
);


-- article 테이블 생성
CREATE TABLE ARTICLE(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    createdAt TIMESTAMP,
    authorId VARCHAR(255),
    POINT INT,
    STATUS VARCHAR(10) DEFAULT 'OPEN' CHECK (STATUS IN ('OPEN', 'CLOSE'))
);

-- comment 테이블 생성
CREATE TABLE COMMENT(
    id INT AUTO_INCREMENT PRIMARY KEY,
    articleId INT,
    title VARCHAR(255),
    content TEXT,
    createdAt TIMESTAMP,
    authorId VARCHAR(255),
    STATUS VARCHAR(10) DEFAULT 'OPEN' CHECK (STATUS IN ('OPEN', 'CLOSE'))
);



ALTER TABLE USERS ADD CONSTRAINT unique_userid_name UNIQUE (USERID, NAME);
-- article 테이블에 외래 키(FK) 및 ON DELETE CASCADE 설정
ALTER TABLE ARTICLE ADD CONSTRAINT fk_authorId FOREIGN KEY (authorId) REFERENCES USERS (userId) ON DELETE CASCADE;

-- comment 테이블에 외래 키(FK) 및 ON DELETE CASCADE 설정
ALTER TABLE COMMENT ADD CONSTRAINT fk_articleId FOREIGN KEY (articleId) REFERENCES ARTICLE (id) ON DELETE CASCADE;
ALTER TABLE COMMENT ADD CONSTRAINT fk_authorId_c FOREIGN KEY (authorId) REFERENCES USERS (userId) ON DELETE CASCADE;
