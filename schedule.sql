USE schedule;

CREATE TABLE schedules (
                           id INT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 ID',
                           name VARCHAR(255) NOT NULL COMMENT '작성자명',
                           password VARCHAR(255) NOT NULL COMMENT '비밀번호',
                           todo VARCHAR(255) NOT NULL COMMENT '할 일',
                           createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
                           updatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '생성일'
);
