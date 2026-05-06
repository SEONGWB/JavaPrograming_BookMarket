-- 1. 데이터베이스 생성 및 선택
CREATE DATABASE IF NOT EXISTS bookmarket_db;
USE bookmarket_db;

-- 2. 사용자 테이블 (user_tb)
CREATE TABLE user_tb (
    user_id VARCHAR(50) PRIMARY KEY,      -- 사용자 ID (String)
    password VARCHAR(100) NOT NULL,       -- 비밀번호
    name VARCHAR(50) NOT NULL,            -- 이름
    email VARCHAR(100),                   -- 이메일
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. 도서 테이블 (book_tb)
CREATE TABLE book_tb (
    book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(13) NOT NULL,            -- 도서고유번호
    title VARCHAR(200) NOT NULL,          -- 제목
    author VARCHAR(100),                  -- 저자
    publisher VARCHAR(100),               -- 출판사
    price INT DEFAULT 0,                  -- 가격
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 4. 장바구니 테이블 (cart_tb)
CREATE TABLE cart_tb (
    cart_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50),                  -- 사용자 테이블과 1:1 관계
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_tb(user_id)
);

-- 5. 장바구니 상세 테이블 (cart_item_tb)
CREATE TABLE cart_item_tb (
    cart_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT,                       -- 장바구니 ID 연결
    book_id BIGINT,                       -- 도서 ID 연결
    quantity INT NOT NULL DEFAULT 1,      -- 담은 수량
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES cart_tb(cart_id),
    FOREIGN KEY (book_id) REFERENCES book_tb(book_id)
);

-- 6. 주문 테이블 (order_tb)
CREATE TABLE order_tb (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50),                  -- 주문자 정보
    status VARCHAR(20) DEFAULT 'ORDERED', -- 주문 상태
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_tb(user_id)
);

-- 7. 주문 상세 테이블 (order_item_tb)
CREATE TABLE order_item_tb (
    order_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,                      -- 주문 ID 연결
    book_id BIGINT,                       -- 도서 ID 연결
    order_price INT NOT NULL,             -- 주문 당시 가격
    count INT NOT NULL,                   -- 주문 수량
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES order_tb(order_id),
    FOREIGN KEY (book_id) REFERENCES book_tb(book_id)
);

INSERT INTO book_tb (isbn, title, author, price) VALUES ('12345', '자바의 정석', '남궁성', 30000);
