-- 切换到你的数据库
-- 请将 'javahw' 替换为你的实际数据库名称
USE javahw;

-- 删除已有表（如果存在），方便重复运行脚本
DROP TABLE IF EXISTS `grading`;
DROP TABLE IF EXISTS `submission`;
DROP TABLE IF EXISTS `experiment`;
DROP TABLE IF EXISTS `teacher`;
DROP TABLE IF EXISTS `student`;

-- 创建 student 表
CREATE TABLE `student` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `student_no` varchar(255) DEFAULT NULL COMMENT '学号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`) -- 用户名唯一
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- 创建 teacher 表
CREATE TABLE `teacher` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`) -- 用户名唯一
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表';

-- 创建 experiment 表
CREATE TABLE `experiment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL COMMENT '实验名称',
  `description` text COMMENT '实验描述',
  `deadline` datetime DEFAULT NULL COMMENT '截止时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='实验表';

-- 创建 submission 表
CREATE TABLE `submission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `experiment_id` bigint NOT NULL COMMENT '实验ID',
  `code_text` longtext COMMENT '提交的代码文本',
  `code_zip` varchar(255) DEFAULT NULL COMMENT '提交的代码压缩包路径',
  `result_img` varchar(255) DEFAULT NULL COMMENT '结果图片路径',
  `create_time` datetime DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`student_id`) REFERENCES `student` (`id`), -- 外键关联 student 表
  FOREIGN KEY (`experiment_id`) REFERENCES `experiment` (`id`) -- 外键关联 experiment 表
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='提交表';

-- 创建 grading 表
CREATE TABLE `grading` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `submission_id` bigint NOT NULL COMMENT '提交ID',
  `teacher_id` bigint NOT NULL COMMENT '教师ID',
  `grade` varchar(50) DEFAULT NULL COMMENT '分数或等级',
  `comment` text COMMENT '教师评语',
  `create_time` datetime DEFAULT NULL COMMENT '批改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_submission_teacher` (`submission_id`, `teacher_id`), -- 确保同一提交同一教师只批改一次
  FOREIGN KEY (`submission_id`) REFERENCES `submission` (`id`), -- 外键关联 submission 表
  FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) -- 外键关联 teacher 表
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='批改表';