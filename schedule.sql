USE schedule;

CREATE TABLE schedule (
                          id INT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 ID',
                          name VARCHAR(255) NOT NULL COMMENT '작성자명',
                          password VARCHAR(255) NOT NULL COMMENT '비밀번호',
                          todo VARCHAR(255) NOT NULL COMMENT '할 일',
                          created_date DATETIME NOT NULL COMMENT '작성일',
                          updated_date DATETIME NOT NULL COMMENT '생성일'
);